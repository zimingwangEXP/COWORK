package Data;

import java.io.Serializable;

public class Remark implements Serializable {
    protected  String content=null;
    protected  String from=null;//应该是id号
    protected  String  to=null;
    public Remark(String content,String from,String to){
         this.content=content;
         this.from=from;
         this.to=to;
    }

    public String getContent() {
        return content;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
