package Data;

import java.io.Serializable;

public class UserSnapShot implements Serializable {
    protected String id=null;
    protected String user_name=null;
    protected ClientStatus status=null;
    protected  String group_id=null;
    protected  String signatrue=null;
    public UserSnapShot(String id,String user_name,ClientStatus status,String signatrue,String group_id){
        this.id=id;
        this.signatrue=signatrue;
        this.user_name=user_name;
        this.status=status;
        this.group_id=group_id;
    }

    public String getGroup_id() {
        return group_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getId() {
        return id;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public String getSignatrue() {
        return signatrue;
    }
}