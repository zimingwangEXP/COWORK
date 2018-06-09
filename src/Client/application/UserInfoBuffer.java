package Client.application;

import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.BasicInfo;
import CommonBase.Data.ClientStatus;
import CommonBase.Data.SuperInfo;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class UserInfoBuffer {
  public  static BasicInfo bf=null;
  public static SuperInfo sf=null;
  public static BasicInfoTransition bf_trans_to_server;

    static {
        try {
            bf_trans_to_server = new BasicInfoTransition(new Connection(new Socket("localhost",12345)));
        } catch (IOException e) {
            System.out.println("bf_trans_to_server初始化失败");
            e.printStackTrace();
        }
    }

    public static  void Init(String name, String id, ClientStatus status){
      bf=new BasicInfo.BasicInfoBuilder(name,id).SetClientStatus(status).Builder();
      sf=new SuperInfo(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),0);
  }
}
