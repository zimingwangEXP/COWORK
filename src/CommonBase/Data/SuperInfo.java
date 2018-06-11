package CommonBase.Data;


import CommonBase.Data.Remark;
import org.omg.CORBA.Object;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
public class SuperInfo implements Serializable {//负责客户端到服务器端所有高级信息的传送
   protected  ArrayList<UserSnapShot> black_list=null;
   protected  ArrayList<UserSnapShot> friend_list=null;
   protected  ArrayList<Remark> remark_list=null;

   protected  Integer good=0;
  public SuperInfo(ArrayList<UserSnapShot> black_list, ArrayList<UserSnapShot> friend_list,
                   ArrayList<Remark> remark_list, Integer good){
      this.black_list=black_list;
      this.remark_list=remark_list;
      this.friend_list=friend_list;
      this.good=good;
  }
  public void copy(SuperInfo osf) {
      this.black_list=osf.black_list;
      this.remark_list=osf.remark_list;
      this.friend_list=osf.friend_list;
      this.good=osf.good;
  }
    public void setBlack_list(ArrayList<UserSnapShot> black_list) {
        this.black_list = black_list;
    }

    public void setFriend_list(ArrayList<UserSnapShot> friend_list) {
        this.friend_list = friend_list;
    }

    public void setGood(Integer good) {
        this.good = good;
    }

    public void setRemark_list(ArrayList<Remark> remark_list) {
        this.remark_list = remark_list;
    }

    public ArrayList<Remark> getRemark_list() {
        return remark_list;
    }

    public ArrayList<UserSnapShot> getBlack_list() {
        return black_list;
    }

    public ArrayList<UserSnapShot> getFriend_list() {
        return friend_list;
    }

    public Integer getGood() {
        return good;
    }
}
