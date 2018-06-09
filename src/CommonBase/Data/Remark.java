package CommonBase.Data;

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

    @Override

    public boolean equals(Object obj) {
        Remark temp=(Remark)obj;
        return content.equals(temp.content)&&from.equals(temp.from)&&to.equals(temp.to);
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
