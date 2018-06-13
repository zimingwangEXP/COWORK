import CommonBase.CSLinker.CSLinker;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.*;
import Server.DataBase.dao;
import Server.ServerData.message;
import Server.ServerData.relation;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

public class Test {

    public static  void main(String[]args) throws IOException {
        //模拟客户端
      //  System.out.println(new dao().getFriend_list("100023")==null);
       // System.out.println(new dao().getFriend_list("100027")==null);
       // new dao().addFriendlist(new relation("100027","100023","default"));
       new dao().addmessage(new message("100023","100030",new Date(),"hello_world"));
        new dao().addmessage(new message("100030","100023",new Date(),"gray"));
        new dao().addmessage(new message("100023","100030",new Date(),"so"));

        LinkedList<message> list=(LinkedList<message> )new dao().getMessage("100023","100030");
        for(message one:list)
        {
            System.out.println(one.getContent());
        }
    }

    }



