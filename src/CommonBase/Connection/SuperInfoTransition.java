package CommonBase.Connection;

import java.util.ArrayList;

import CommonBase.Connection.Connection;
import CommonBase.Data.Remark;
import CommonBase.Data.SuperInfo;
import CommonBase.Data.UserSnapShot;
public class SuperInfoTransition {
    protected Connection link=null;
    public SuperInfoTransition(Connection link){
        this.link=link;
    }
    public void SendBlackList(ArrayList<UserSnapShot> black_list) {

    }
    public void SendFriendList(ArrayList<UserSnapShot> friend_list){

    }
    public ArrayList<UserSnapShot>  ReceiveBlackList() {
       return  null;
    }
    public ArrayList<UserSnapShot> ReceiveFriendList(){
        return  null;
    }
    public void SendRemark(ArrayList<Remark> remarks){

    }
    public ArrayList<Remark>  ReceiveRemark() {
        return  null;
    }
    public void SendSuperInfo(SuperInfo info){

    }
    public SuperInfo ReceiveSuperInfo() {
        return  null;
    }
}
