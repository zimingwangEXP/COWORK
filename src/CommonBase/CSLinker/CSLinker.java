package CommonBase.CSLinker;

import Client.application.UserInfoBuffer;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Connection.SuperInfoTransition;
import CommonBase.Data.*;
import com.sun.xml.internal.bind.v2.model.core.ID;
import CommonBase.Log.*;
import Server.ServerData.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

/*
    Connection,BasicInfoTransition,SuperInfoTransition已经为客户端和服务器提供了多样的网络操作
    CSLinker按照客户端所需的网络功能分类并实现相关的操作以供客户端使用
 */
public class CSLinker {
   private static Log log=new Log();
   private static BasicInfo bf=null;
   private static SuperInfo sf=null;
   private static BasicInfoTransition bf_trans_to_server;
   static {
      try {
         bf_trans_to_server = new BasicInfoTransition(new Connection(new Socket("localhost",12345)));
      } catch (IOException e) {
         System.out.println("bf_trans_to_server初始化失败");
         e.printStackTrace();
      }
   }
   public  CSLinker(){}
   public static LoginStatus Login(String user_name,String password,ClientStatus status){
    //  bf_trans_to_server.
      bf_trans_to_server.SendLoginInfo(user_name,password,status);//注意修改用户状态
      LoginStatus rt_status=bf_trans_to_server.ClientReceiveLoginInfo(bf,sf);
      if(rt_status==LoginStatus.find)
      {
         //切换到登录界面，开始装载数据
         UserInfoBuffer.bf=bf;
         UserInfoBuffer.sf=sf;
      }
      return rt_status;
   }
   public static String Regist(BasicInfo bf){//返回获得的系统分配用户id
           bf_trans_to_server.SendMessage("regist");
           bf_trans_to_server.SendBasicInfo(bf);
           return bf_trans_to_server.ReceiveMessage();
   }
   public static  void UpdateBasicInfo(BasicInfo bf){
          CSLinker.bf=bf;
          bf_trans_to_server.SendMessage("update_basic_info");
          bf_trans_to_server.SendBasicInfo(bf);

   }
   public static  void UpdateSuperInfo(SuperInfo sf){
      CSLinker.sf=sf;
      bf_trans_to_server.SendMessage("update_super_info");
      bf_trans_to_server.SendSuperInfo(sf);

   }
   public static void UpdateStatus(ClientStatus status){
      bf.setStatus(status);
      bf_trans_to_server.SendMessage("set_user_status");
      bf_trans_to_server.SendStatus(bf.getStatus());
   }
   public static BasicInfo GetUserInfoById(String id){
     bf_trans_to_server.SendMessage("get_basic_info_by_id");
     bf_trans_to_server.SendMessage(id);
      return bf_trans_to_server.ReceiveBasicInfo();
   }
   public static Integer SendFriendRequest(String id,String content){//返回值为1代表已经被对方加入黑名单，2代表此id不存在，3代表验证消息已离线发送成功,4代表验证消息已在线发送成功
      String[] res=bf_trans_to_server.IdToIp("id");
      if(res[0].equals("not_online")) {
         bf_trans_to_server.SendMessage("add_friend");
         //add_friend应背客户端接受线程处理
         bf_trans_to_server.TemplateSend(new TempSavedInfo(bf.getId(),id,"add_friend",content));
         String an=bf_trans_to_server.ReceiveMessage();
         if(an.equals("fail"))//被加入黑名单
            return 1;
         else
            return 3;
      }
      else if(res[1].equals("no_exist"))
      {
         return 2;
      }
      else {
         String ip=bf_trans_to_server.ReceiveMessage();
         BasicInfoTransition direct_connect= null;
         try {
            direct_connect = new BasicInfoTransition(new Connection(new Socket(ip,12345)));
         } catch (IOException e) {
            log.StandardWrite("直接发送对好友的连接请求失败\n");
            e.printStackTrace();
         }
         direct_connect.TemplateSend(new TempSavedInfo(bf.getId(),id,"add_friend",content));
         return 4;
      }
   }
   public static ArrayList<UserSnapShot> getFriendList(){
      return sf.getFriend_list();
   }
   public static void ModifyBlackList(ArrayList<UserSnapShot> black_list){
      sf.setBlack_list(black_list);
      bf_trans_to_server.SendMessage("update_blacklist");
      bf_trans_to_server.TemplateSend(black_list);
   }
  public static  Integer SendText(String id,String content) {
     String[] back=bf_trans_to_server.IdToIp(id);
     if(back[0].equals("no_exist"))
     {
        return 2;
     }
     else if(back[0].equals("not_on_line"))
     {
        bf_trans_to_server.SendMessage("save_text");
        bf_trans_to_server.TemplateSend(new TempSavedInfo(bf.getId(),id,"text",content));
        return 1;
     }
     else {
        BasicInfoTransition dir_connect = null;
        try {
           dir_connect = new BasicInfoTransition(new Connection(new Socket(back[0], new Integer(back[1]))));
        } catch (IOException e) {
           log.StandardWrite("发送文件时与好友的直接连接的建立失败\n");
           e.printStackTrace();
        }
        bf_trans_to_server.SendMessage("save_history");
        bf_trans_to_server.SendMessage(bf.getId());
        bf_trans_to_server.SendMessage(content);
        dir_connect.SendMessage("text");
        dir_connect.TemplateSend(new TempSavedInfo(bf.getId(), id, "text", content));
        //
        return 0;
     }
  }
   public static Integer SendFile(String id,File file){//0表示在线发送成功，1,表示离线发送，2表示此用户不存在
        String[] back=bf_trans_to_server.IdToIp(id);
        if(back[0].equals("no_exist"))
        {
            return 2;
        }
        else if(back[0].equals("not_on_line"))
        {
           bf_trans_to_server.SendMessage("save_file");
           bf_trans_to_server.TemplateSend(new TempSavedInfo(bf.getId(),id,"file",file.getName()));
           bf_trans_to_server.SendFile(file);
              return 1;
        }
        else
        {
           BasicInfoTransition dir_connect=null;
           try {
               dir_connect=new BasicInfoTransition(new Connection(new Socket(back[0],new Integer(back[1]))));
           } catch (IOException e) {
              log.StandardWrite("发送文件时与好友的直接连接的建立失败\n");
              e.printStackTrace();
           }
           bf_trans_to_server.SendMessage("save_history");
           bf_trans_to_server.SendMessage(bf.getId());
           bf_trans_to_server.SendMessage(file.getName());
           dir_connect.SendMessage("file");
           dir_connect.TemplateSend(new TempSavedInfo(bf.getId(),id,"file",file.getName()));
           dir_connect.SendFile(file);

           return 0;
        }

   }
   public LinkedList<message> GetHistory(String id){
      bf_trans_to_server.SendMessage("get_history");
      bf_trans_to_server.SendMessage(id);
      LinkedList<message> res=(LinkedList<message>)bf_trans_to_server.TemplateReceive();
      return res;
   }
   public void EndConnection(){
      bf_trans_to_server.SendMessage("terminal");
   }
}
