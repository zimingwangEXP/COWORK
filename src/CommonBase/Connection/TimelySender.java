package CommonBase.Connection;

import CommonBase.Connection.PlayPane;

import javax.media.*;
import javax.media.cdm.CaptureDeviceManager;
import javax.media.control.QualityControl;
import javax.media.control.TrackControl;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.*;
import javax.media.rtp.RTPManager;
import javax.media.rtp.SendStream;
import javax.media.rtp.SessionAddress;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Vector;

public class TimelySender {

    private String ipAddress;
    private int portBase;
    private MediaLocator audioLocator = null, vedioLocator = null;
    private Processor audioProcessor = null;
    private Processor videoProcessor = null;
    private DataSource audioDataLocal = null, videoDataLocal = null;
    private DataSource audioDataOutput = null, videoDataOutput = null;
    private RTPManager rtpMgrs[];
    private DataSource mediaData = null;
    private DataSource dataLocalClone = null;

    private PlayPane playFrame;

    public TimelySender(String ipAddress, String pb) {
        this.ipAddress = ipAddress;
        Integer integer = Integer.valueOf(pb);
        if (integer != null) {
            this.portBase = integer.intValue();
        }
        // /////////////////////////////////////////////
        playFrame = new PlayPane();
        JFrame jf = new JFrame("视频实例");

        jf.add(playFrame);
        jf.pack();
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);
        jf.setVisible(true);
        // ////////////////////////////////////////////
        Vector<CaptureDeviceInfo> video = CaptureDeviceManager
                .getDeviceList(new VideoFormat(null));
        Vector<CaptureDeviceInfo> audio = CaptureDeviceManager
                .getDeviceList(new AudioFormat(null));
        // MediaLocator mediaLocator = new
        // MediaLocator("file:/C:/纯音乐 - 忧伤还是快乐.mp3");
        if (audio != null && audio.size() > 0) {
            audioLocator = ((CaptureDeviceInfo) audio.get(0)).getLocator();
            if ((audioProcessor = createProcessor(audioLocator)) != null) {
                audioDataLocal = mediaData;
                audioDataOutput = audioProcessor.getDataOutput();
            }
        }
        else {
            System.out.println("******错误：没有检测到您的音频采集设备！！！");
        }
        // /////////////////////////////////////////////////////////
        if (video != null && video.size() > 0) {
            vedioLocator = ((CaptureDeviceInfo) video.get(0)).getLocator();
            if ((videoProcessor = createProcessor(vedioLocator)) != null) {
                videoDataLocal = mediaData;
                videoDataOutput = videoProcessor.getDataOutput();
            }
        }
        else {
            System.out.println("******错误：没有检测到您的视频设备！！！");
        }
        // /////////////////////////////////////////////////////////
        final DataSource[] dataSources = new DataSource[2];
        dataSources[0] = audioDataLocal;
        dataSources[1] = videoDataLocal;
        try {
            DataSource dsLocal = Manager.createMergingDataSource(dataSources);
            playFrame.localPlay(dsLocal);
        } catch (IncompatibleSourceException e) {
            e.printStackTrace();
            return;
        }
        // ////////////////////////////////////////////////远程传输
        dataSources[1] = audioDataOutput;
        dataSources[0] = videoDataOutput;

        try {
            DataSource dsoutput = Manager.createMergingDataSource(dataSources);
            createTransmitter(dsoutput);
        } catch (IncompatibleSourceException e) {
            e.printStackTrace();
            return;
        }
        audioProcessor.start();
        videoProcessor.start();
    }

    private Processor createProcessor(MediaLocator locator) {
        Processor processor = null;
        if (locator == null)
            return null;
        // 通过设备定位器得到数据源，
        try {
            mediaData = Manager.createDataSource(locator);
            // 创建可克隆数据源
            mediaData = Manager.createCloneableDataSource(mediaData);
            // 克隆数据源，用于传输到远程
            dataLocalClone = ((SourceCloneable) mediaData).createClone();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        try {
            processor = javax.media.Manager.createProcessor(dataLocalClone);

        } catch (NoProcessorException npe) {
            npe.printStackTrace();
            return null;
        } catch (IOException ioe) {
            return null;
        }
        boolean result = waitForState(processor, Processor.Configured);
        if (result == false)
            return null;

        TrackControl[] tracks = processor.getTrackControls();
        if (tracks == null || tracks.length < 1)
            return null;
        ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.RAW_RTP);
        processor.setContentDescriptor(cd);
        Format supportedFormats[];
        Format chosen;
        boolean atLeastOneTrack = false;
        for (int i = 0; i < tracks.length; i++) {
            if (tracks[i].isEnabled()) {
                supportedFormats = tracks[i].getSupportedFormats();
                if (supportedFormats.length > 0) {
                    if (supportedFormats[0] instanceof VideoFormat) {
                        chosen = checkForVideoSizes(tracks[i].getFormat(),
                                supportedFormats[0]);
                    } else
                        chosen = supportedFormats[0];
                    tracks[i].setFormat(chosen);
                    System.err
                            .println("Track " + i + " is set to transmit as:");
                    System.err.println("  " + chosen);
                    atLeastOneTrack = true;
                } else
                    tracks[i].setEnabled(false);
            } else
                tracks[i].setEnabled(false);
        }

        if (!atLeastOneTrack)
            return null;
        result = waitForState(processor, Controller.Realized);
        if (result == false)
            return null;
        setJPEGQuality(processor, 0.5f);

        return processor;
    }

    private String createTransmitter(DataSource dataOutput) {
        PushBufferDataSource pbds = (PushBufferDataSource) dataOutput;
        PushBufferStream pbss[] = pbds.getStreams();
        System.out.println("pbss.length:" + pbss.length);
        rtpMgrs = new RTPManager[pbss.length];
        SendStream sendStream;
        int port;
        // SourceDescription srcDesList[];

        for (int i = 0; i < pbss.length; i++) {
            try {
                rtpMgrs[i] = RTPManager.newInstance();

                port = portBase + 2 * i;
                SessionAddress localAddr = new SessionAddress(
                        InetAddress.getLocalHost(), port);
                SessionAddress destAddr = new SessionAddress(
                        InetAddress.getByName(ipAddress), port);
                rtpMgrs[i].initialize(localAddr);
                rtpMgrs[i].addTarget(destAddr);
                System.out.println("Created RTP session: "
                        + InetAddress.getLocalHost() + " " + port);
                sendStream = rtpMgrs[i].createSendStream(dataOutput, i);
                sendStream.start();
            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }

        return null;
    }
    Format checkForVideoSizes(Format original, Format supported) {

        int width, height;
        Dimension size = ((VideoFormat) original).getSize();
        Format jpegFmt = new Format(VideoFormat.JPEG_RTP);
        Format h263Fmt = new Format(VideoFormat.H263_RTP);

        if (supported.matches(jpegFmt)) {
            width = (size.width % 8 == 0 ? size.width
                    : (int) (size.width / 8) * 8);
            height = (size.height % 8 == 0 ? size.height
                    : (int) (size.height / 8) * 8);
        } else if (supported.matches(h263Fmt)) {
            if (size.width < 128) {
                width = 128;
                height = 96;
            } else if (size.width < 176) {
                width = 176;
                height = 144;
            } else {
                width = 352;
                height = 288;
            }
        } else {
            return supported;
        }

        return (new VideoFormat(null, new Dimension(width, height),
                Format.NOT_SPECIFIED, null, Format.NOT_SPECIFIED))
                .intersects(supported);
    }
    void setJPEGQuality(Player p, float val) {

        Control cs[] = p.getControls();
        QualityControl qc = null;
        VideoFormat jpegFmt = new VideoFormat(VideoFormat.JPEG);
        for (int i = 0; i < cs.length; i++) {

            if (cs[i] instanceof QualityControl && cs[i] instanceof Owned) {
                Object owner = ((Owned) cs[i]).getOwner();
                if (owner instanceof Codec) {
                    Format fmts[] = ((Codec) owner)
                            .getSupportedOutputFormats(null);
                    for (int j = 0; j < fmts.length; j++) {
                        if (fmts[j].matches(jpegFmt)) {
                            qc = (QualityControl) cs[i];
                            qc.setQuality(val);
                            System.err.println("- Setting quality to " + val
                                    + " on " + qc);
                            break;
                        }
                    }
                }
                if (qc != null)
                    break;
            }
        }
    }
    private Integer stateLock = new Integer(0);
    private boolean failed = false;

    Integer getStateLock() {
        return stateLock;
    }

    void setFailed() {
        failed = true;
    }

    private synchronized boolean waitForState(Processor p, int state) {
        p.addControllerListener(new StateListener());
        failed = false;
        if (state == Processor.Configured) {
            p.configure();
        } else if (state == Processor.Realized) {
            p.realize();
        }
        while (p.getState() < state && !failed) {
            synchronized (getStateLock()) {
                try {
                    getStateLock().wait();
                } catch (InterruptedException ie) {
                    return false;
                }
            }
        }

        if (failed)
            return false;
        else
            return true;
    }
    class StateListener implements ControllerListener {

        public void controllerUpdate(ControllerEvent ce) {

            if (ce instanceof ControllerClosedEvent)
                setFailed();

            if (ce instanceof ControllerEvent) {
                synchronized (getStateLock()) {
                    getStateLock().notifyAll();
                }
            }
        }
    }
    public static void main(String[] args) {
        String[] strs = { "localhost", "9994" };
        new TimelySender(strs[0], strs[1]);
    }
}
