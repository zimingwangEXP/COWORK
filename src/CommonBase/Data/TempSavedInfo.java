package CommonBase.Data;

import com.sun.xml.internal.ws.developer.Serialization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class TempSavedInfo implements Serializable {
    //可能是消息，文件名，验证消息等
    protected String from=null,to=null;
    protected  String InfoType=null;
    protected Serializable  Content=null;
    public TempSavedInfo(String from,String to,String info_type,Serializable content){
        InfoType=info_type;
        Content=content;
        this.from=from;
        this.to=to;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setContent(ArrayList<Serializable> content) {
        Content = content;
    }

    public void setInfoType(String infoType) {
        InfoType = infoType;
    }

    public Serializable getContent() {
        return Content;
    }

    public String getInfoType() {
        return InfoType;
    }
}
