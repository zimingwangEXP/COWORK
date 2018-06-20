package Client.view;

import Client.MainThread;
import CommonBase.CSLinker.CSLinker;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Data.BasicInfo;
import CommonBase.Data.UserSnapShot;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sun.applet.Main;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileTransportHandle {
    boolean pd=true;//true为发送方，false为接收方
    UserSnapShot who;
    BasicInfoTransition sub_trans=null;
    private Stage show;
    MainThread main;
    File file=null;
    private final Desktop desktop = Desktop.getDesktop();
    public DoubleProperty cur=new SimpleDoubleProperty(0);
    @FXML
    public ProgressBar bar;
    @FXML
    public ProgressIndicator indicate;
    @FXML
    public  Label file_name;
    @FXML
    public  Label file_size;
    @FXML
    public  Label rest_time;
    @FXML
    public  Label cur_speed;
    @FXML
    private void ChooseHandle(){
        if(pd) {
            FileChooser fileChooser = new FileChooser();
            configureFileChooser(fileChooser);
             file = fileChooser.showOpenDialog(show);
        }
        else
        {
            final DirectoryChooser directoryChooser=new DirectoryChooser();
             file= directoryChooser.showDialog(show);

        }
    }
    @FXML
    private void StartHandle(){
        if(file!=null)
        {
            cur.addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    bar.setProgress((double)newValue);
                    indicate.setProgress((double)newValue);
                }
            });
            if(pd)
            {
                CSLinker.SendFile(who.getId(),file,this);
            }
            else
            {
                sub_trans.ReceiveFile(file.toString(),this);
            }
        }
        else
        {
            Platform.runLater(
                    new Runnable() {
                        @Override
                        public void run() {
                            MainThread.showExceptionDialog("您尚未选择文件/目录",Alert.AlertType.WARNING);
                        }
                    }
            );
        }

    }
    @FXML
    private void  PauseHandle(){

    }
    private  void OpenAndTransferFile(File file){
        EventQueue.invokeLater(() -> {
            try {
                desktop.open(file);
            } catch (IOException ex) {
                Logger.getLogger(FileTransportHandle.
                        class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        });
        cur.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                bar.setProgress((Double)newValue);
                indicate.setProgress((Double)newValue);
            }
        });


    }
    private  void configureFileChooser(FileChooser fileChooser){
        fileChooser.setTitle("选择文件");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("DOC","*.doc"),
                new FileChooser.ExtensionFilter("MUSIC" ,"*.")
        );
    }
    public void setStage(Stage show){

    }
    // Define a getter for the property's value
    public final double  getCur(){return cur.get();}

    // Define a setter for the property's value
    public final void setCur(double value){cur.set(value);}

    // Define a getter for the property itself
    public DoubleProperty curProperty() {return cur;}
    public void setMain(MainThread main){
        this.main=main;
    }
    public void setWho(UserSnapShot who){
        this.who=who;
    }
    public void setPd(boolean pd){
        this.pd=pd;
    }
    public void setSub_trans(BasicInfoTransition sub_trans){
        this.sub_trans=sub_trans;
    }

}
