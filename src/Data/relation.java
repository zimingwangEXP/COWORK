package Data;

public class relation{
    private String id1=null;
    private String id2=null;
    private String group_id=null;
    public relation()
    {}
    public relation(String id1, String id2,String group_id) {
        super();
        this.id1 = id1;
        this.id2 = id2;
        this.group_id=group_id;
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
    public String getgroupid() {
        return group_id;
    }
    public void setgroupid(String group_id) {
        this.group_id = group_id;
    }
    public String toString(){
        return id1+","+id2+","+group_id;
    }
}

