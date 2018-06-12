import CommonBase.CSLinker.CSLinker;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.*;
import Server.DataBase.dao;
import Server.ServerData.relation;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Test {

    public static  void main(String[]args) throws IOException {
        //模拟客户端
      //  System.out.println(new dao().getFriend_list("100023")==null);
       // System.out.println(new dao().getFriend_list("100027")==null);
       // new dao().addFriendlist(new relation("100027","100023","default"));
        for (UserSnapShot one : new dao().getFriend_list("100023"))
        {
              System.out.println(one.getId());
        }
    }

    }



