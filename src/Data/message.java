package Data;

import java.util.Date;

public class message{
    private String id1;
    private String id2;
    private Date date;
    private String content;
    public message(String id1, String id2, Date date, String content) {
        super();
        this.id1 = id1;
        this.id2 = id2;
        this.date = date;
        this.content = content;
    }
    public String getId1() {
        return id1;
    }
    public void setId1(String id1) {
        this.id1 = id1;
    }
    public String getId2() {
        return id2;
    }
    public void setId2(String id2) {
        this.id2 = id2;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String toString(){
        return id1+","+id2+","+date.toLocaleString()+","+content;
    }
}
