package Connection;

import java.util.ArrayList;

import Data.Remark;
import Data.SuperInfo;
import Data.UserSnapShot;
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

    }
    public ArrayList<UserSnapShot> ReceiveFriendList(){

    }
    public void SendFriendList(ArrayList<Remark> remarks){

    }
    public ArrayList<Remark>  ReceiveBlackList() {

    }
    public void SendSuperInfo(SuperInfo info){

    }
    public SuperInfo ReceiveSuperInfo() {

    }
}
