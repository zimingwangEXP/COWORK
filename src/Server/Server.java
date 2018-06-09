package Server;

import CommonBase.Connection.Connection;
import CommonBase.Data.*;
import Server.DataBase.dao;
import Server.ServerData.failrelation;
import Server.ServerData.message;
import Server.ServerData.relation;
import com.sun.deploy.util.BlackList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import CommonBase.Connection.*;
import CommonBase.Log.Log;

public class Server extends Application {
    protected  dao database_op=new dao();
    protected  Log log=new Log();
    protected Stage primaryStage=null;
    ConcurrentHashMap<String,String[]>  sub_server_list=new ConcurrentHashMap<>();
    ConcurrentHashMap<String,ArrayList<TempSavedInfo> > temp_info_list=new ConcurrentHashMap<>();
    //index 0:ip地址,index 1:端口
    protected ServerSocket server=null;

    public  HashMap<String,Integer>  help=new HashMap<>();
    ExecutorService exe=Executors.newFixedThreadPool(1000);
    //注意调整线程池中的线程数
    void FillHelp()
    {
        for(int i = 0; i<KeyWords.key_words.length; i++)
            help.put(KeyWords.key_words[i],i);
    }
    public static void main(String[] args) {
        launch(args);
    }

    void BuildGraph() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("BasicView.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        FillHelp();
        this.primaryStage=primaryStage;
        BuildGraph();
        exe.submit(new Runnable() {
            @Override//握手点监控器线程
            public void run() {
                runServer();
            }
        });
        /*
        core code
        exe.shutdown();
       */

    }
    public void runServer() {
        try {
            server = new ServerSocket(12345, 1000);
            //考虑创建配置文件实现默认端口设置
        } catch (IOException e) {
            log.Write("握手点建交失败\n");
        }
        while (true) {
            Socket socket = null;
            try {
                socket = server.accept();
                ICSY_USER user = new ICSY_USER(socket);
                exe.execute(user);
            }
            catch (IOException e) {
                log.Write("发生了非常诡异的错误\n");
            }
        }
    }
    class ICSY_USER implements  Runnable{
        Connection link=null;
        String id=null;
        BasicInfoTransition bf_trans=null;
        SuperInfoTransition sf_trans=null;
         Socket socket=null;
        public ICSY_USER(Socket socket) {
            this.socket = socket;
            link = new Connection(socket);
            bf_trans = new BasicInfoTransition(link);
            sf_trans = new SuperInfoTransition(link);
        }
       private void  BroadCast(String key,Object obj){//对当前用户的所有好友发起广播信息更新
            exe.submit(new Runnable() {
                @Override
                public void run() {
                    ArrayList<UserSnapShot> list = new dao().getFriend_list(id);
                    for (UserSnapShot one : list) {
                        if (sub_server_list.containsKey(one.getId()))
                        {
                            BasicInfoTransition tbf=null;
                            try {
                                 tbf=new BasicInfoTransition(new Connection(
                                        new Socket(sub_server_list.get(one.getId())[0],12345)));
                                  tbf.SendMessage(key);
                                  tbf.SendMessage(id);
                                  tbf.TemplateSend(obj);
                            } catch (IOException e) {
                                log.Write(key+"广播失败\n");
                                e.printStackTrace();
                            }

                        }
                } }
            });
       }
       private void ListUpdate(ArrayList<Object> list,ArrayList<Object> s_list){

       }
        private void LogoutHandle(){
           sub_server_list.remove(id);
            link.close();
            new dao().SetStatus(id,"suspend");
            BroadCast("status_broad",ClientStatus.susupend);
            link=null;
            sf_trans=null;
            socket=null;
            bf_trans=null;
        }
        private void LoginHandle() throws IOException, ClassNotFoundException {
           id=bf_trans.ReceiveMessage();//login
          BasicInfo bf=new dao().getBasicInfoById(id);
          if(bf!=null) {//判断用户是否存在
              String password = bf_trans.ReceiveMessage();
              ClientStatus status = bf_trans.ReceiveStatus();
              String realpassword = new dao().getPwd(id);
              if (realpassword.equals(password)) {
                  sub_server_list.put(id, new String[]{socket.getInetAddress().toString(),
                          Integer.toString(socket.getPort())});
                  new dao().SetStatus(id, status.toString());
                  BroadCast("status_broad", status);
                  bf_trans.TemplateSend(LoginStatus.find);
                  SuperInfo sf=database_op.getSuperInfoById(id);
                  bf_trans.SendBasicInfo(bf);
                  bf_trans.SendSuperInfo(sf);
                  if(temp_info_list.containsKey(id))
                  {
                      ArrayList<TempSavedInfo> savedInfos=temp_info_list.get(id);
                      //可能会出现刚刚上线，立即下线的情况，为解决这种情况，本地必须保存缓冲
                      for(TempSavedInfo info:savedInfos)//什么时候应该中断连接
                      {
                          bf_trans.SendMessage(info.getInfoType());
                          bf_trans.TemplateSend(info);
                          if(bf_trans.equals("file"))
                          {
                              File file=new File("/temp/"+info.getFrom()+"/"+info.getTo()+"/"+(String)info.getContent());
                              bf_trans.SendFile(file);
                          }
                          savedInfos.remove(info);
                      }
                  }
              }
              else
              {
                  bf_trans.TemplateSend(LoginStatus.password_error);
              }
          }
            else
            {
               bf_trans.TemplateSend(LoginStatus.username_error);
            }
        }

        private void RegistHandle() throws IOException {
              BasicInfo info=bf_trans.ReceiveBasicInfo();
              int userid=100000+database_op.NumberOfID();
              info.setId(Integer.toString(userid));
             database_op.addBasicInfo(info);
              bf_trans.SendMessage(Integer.toString(userid));
              System.out.println(info);
        }
        private void IdToIpHandle() throws IOException, ClassNotFoundException {
            String others_id = bf_trans.ReceiveMessage();
            BasicInfo tbf = database_op.getBasicInfoById(others_id);
            if (tbf == null) {
                bf_trans.SendMessage("no_exist");
                bf_trans.SendMessage("no_exist");
            } else {
                String[] temp = sub_server_list.get(others_id);
                if (temp == null) {
                    bf_trans.SendMessage("not_on_line");
                    bf_trans.SendMessage("not_on_line");
                } else {
                    bf_trans.SendMessage(temp[0]);
                    bf_trans.SendMessage(temp[1]);
                }
            }
        }
        private void AddFriendHandle() throws IOException, ClassNotFoundException {//适用于对未上线用户的离线请求
             TempSavedInfo entry=(TempSavedInfo)bf_trans.TemplateReceive();
             if(!database_op.getBlack_list(entry.getTo()).contains(entry.getFrom())) {//判断两人是否是黑名单关系
                 if (temp_info_list.containsKey(entry.getTo())) {
                     temp_info_list.get(entry.getTo()).add(entry);
                 }
                 else {
                     ArrayList<TempSavedInfo> temp = new ArrayList<>();
                     temp.add(entry);
                     temp_info_list.put(entry.getTo(), temp);
                 }
                 bf_trans.SendMessage("success");
             }
             else
             {
                 bf_trans.SendMessage("fail");
             }
        }

        private void UpdateBlacklistHandle(){
            ArrayList<UserSnapShot> black=(ArrayList<UserSnapShot>)bf_trans.TemplateReceive();
            ArrayList<UserSnapShot> s_black=database_op.getBlack_list(id);
            for(UserSnapShot one:s_black)
            {
                if(!black.contains(one)){
                    s_black.remove(one);
                    database_op.deleteBlacklist(new failrelation(id,one.getId()));
                }
            }
            for(UserSnapShot one:black)
            {
                if(!s_black.contains(one)){
                    s_black.add(one);
                    database_op.addBlacklist(new failrelation(id,one.getId()));
                }
            }
        }
        private void GetBasicInfoByIdHandle() throws IOException, ClassNotFoundException {
            String others_id= bf_trans.ReceiveMessage();
            BasicInfo bf=database_op.getBasicInfoById(others_id);
                bf_trans.TemplateSend(bf);

        }
        private  void GetSuperInfoByIdHandle() throws IOException, ClassNotFoundException {
            String others_id= bf_trans.ReceiveMessage();
            SuperInfo sf=database_op.getSuperInfoById(others_id);
            bf_trans.TemplateSend(sf);
        }
        private void UpdateBasicInfoHandle(){
             BasicInfo bf=bf_trans.ReceiveBasicInfo();
             database_op.updateBasicInfo(bf);
        }

        private  void UpdateSuperInfoHandle(){
             SuperInfo sf=(SuperInfo) bf_trans.TemplateReceive();
            ArrayList<UserSnapShot> black=sf.getBlack_list();
            ArrayList<UserSnapShot> friend=sf.getFriend_list();
            ArrayList<Remark> remarks=sf.getRemark_list();
             ArrayList<UserSnapShot> s_black=database_op.getBlack_list(id);
            ArrayList<UserSnapShot> s_friend=database_op.getFriend_list(id);
            ArrayList<Remark> s_remarks=database_op.getRemark_list(id);
            for(UserSnapShot one:s_black)
            {
                if(!black.contains(one)){
                    s_black.remove(one);
                    database_op.deleteBlacklist(new failrelation(id,one.getId()));
                }
            }
            for(UserSnapShot one:black)
            {
                if(!s_black.contains(one)){
                    s_black.add(one);
                    database_op.addBlacklist(new failrelation(id,one.getId()));
                }
            }
            for(UserSnapShot one:s_friend)
            {
                if(!friend.contains(one)){
                    s_friend.remove(one);
                    database_op.deleteFriendlist(new relation(id,one.getId(),one.getGroup_id()));
                }
            }
            for(UserSnapShot one:friend)
            {
                if(!s_friend.contains(one)){
                    s_friend.add(one);
                    database_op.addFriendlist(new relation(id,one.getId(),one.getGroup_id()));
                }
            }
            for(Remark one:remarks)
            {
                if(!s_remarks.contains(one)){
                    s_remarks.add(one);
                    database_op.addRemark(one);
                }
            }
        }
        private  void  SetUserStatusHandle() throws IOException, ClassNotFoundException {
              ClientStatus cur=bf_trans.ReceiveStatus();
              database_op.SetStatus(id,cur.toString());//flag
              BroadCast("status_broad",cur);
        }
        private void GetFriendListHandle(){
            bf_trans.TemplateSend(database_op.getFriend_list(id));
        }
        private  void SaveTextHandle(){
              TempSavedInfo tf=(TempSavedInfo)bf_trans.TemplateReceive();
              database_op.addmessage(new message(id,tf.getTo(),new Date(),(String)tf.getContent()));
            if (temp_info_list.containsKey(tf.getTo())) {
                temp_info_list.get(tf.getTo()).add(tf);
            } else {
                ArrayList<TempSavedInfo> temp = new ArrayList<>();
                temp.add(tf);
                temp_info_list.put(tf.getTo(), temp);
            }
        }
        private void FriendRequestBackHandle(){
            TempSavedInfo tf=(TempSavedInfo)bf_trans.TemplateReceive();
            if (temp_info_list.containsKey(tf.getTo())) {
                temp_info_list.get(tf.getTo()).add(tf);
            } else {
                ArrayList<TempSavedInfo> temp = new ArrayList<>();
                temp.add(tf);
                temp_info_list.put(tf.getTo(), temp);
            }
        }
        private void SaveFile(){
            TempSavedInfo tf=(TempSavedInfo)bf_trans.TemplateReceive();
            File dic=new File("/temp/"+tf.getFrom()+"/"+tf.getTo());
            if(!dic.exists())
            {
                try {
                    dic.createNewFile();
                } catch (IOException e) {
                    log.Write("创建目标目录失败\n");
                    e.printStackTrace();

                }
            }
            database_op.addmessage(new message(id,tf.getTo(),new Date(),(String)tf.getContent()));
            bf_trans.ReceiveFile(dic.toString());
            if (temp_info_list.containsKey(tf.getTo())) {
                temp_info_list.get(tf.getTo()).add(tf);
            } else {
                ArrayList<TempSavedInfo> temp = new ArrayList<>();
                temp.add(tf);
                temp_info_list.put(tf.getTo(), temp);
            }
        }
       private void  SaveHistoryHandle(){
            String to=bf_trans.ReceiveMessage();
            String content=bf_trans.ReceiveMessage();
            database_op.addmessage(new message(id,to,new Date(),content));//获取当前时间
       }
       private void GetHistoryHandle(){
           String to=bf_trans.ReceiveMessage();
           bf_trans.TemplateSend(database_op.getMessage(id,to));
       }
        @Override
        public void run() {
            int hash_val=-1;
            do
            {
                try {
                    //System.out.println(socket.getInetAddress()+"&&&&&"+socket.getPort());
                     String head = bf_trans.ReceiveMessage();
                     hash_val=help.get(head);
                     switch(hash_val)
                     {
                         case 0:  LogoutHandle(); break;
                         case 1:  LoginHandle();  break;
                         case 2:  RegistHandle(); break;
                         case 3:  IdToIpHandle(); break;
                         case 4:  AddFriendHandle(); break;
                         case 5:  UpdateBlacklistHandle(); break;//轻量化操作
                         case 6:  GetBasicInfoByIdHandle();break;
                         case 7:  GetSuperInfoByIdHandle();break;//为管理员客户端设置的
                         case 8:  UpdateBasicInfoHandle(); break;
                         case 9:  UpdateSuperInfoHandle(); break;
                         case 10: SetUserStatusHandle(); break;
                         case 11: GetFriendListHandle(); break;//**************
                         case 12: SaveTextHandle();break;
                         case 13: SaveFile(); break;
                         case 14: FriendRequestBackHandle(); break;
                         case 15: SaveHistoryHandle(); break;
                         case 16: GetHistoryHandle(); break;
                     }
                }
                catch(IOException e)
                {
                    log.Write("在服务器处理函数发生IO异常\n");
                    LogoutHandle();
                }
                catch (ClassNotFoundException e)
                {
                    log.Write("读取到未知的数据类型\n");
                }
            }while(hash_val>=0);
        }
    }
}
