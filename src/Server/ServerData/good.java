package Server.ServerData;

public class good{
    private String id1=null;
    private String id2=null;

    public good(String id1, String id2) {
        super();
        this.id1 = id1;
        this.id2 = id2;

    }
    public good()
    {};
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

    public String toString(){
        return id1+","+id2;
    }
}

