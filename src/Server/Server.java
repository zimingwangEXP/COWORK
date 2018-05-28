package Server;

import Connection.Connection;
import Data.BasicInfo;
import Data.ClientStatus;
import Executors.Excutor;
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
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Connection.*;
import Log.Log;

public class Server extends Application {
    protected  Log log=new Log();
    ConcurrentHashMap<String,String[]>  sub_server_list=new ConcurrentHashMap<>();
    protected ServerSocket server=null;
    protected String[] temp=new String[]{"terminal","login","regist","chat_text","id_to_ip","add_friend",
            "basic_info_block","get_basic_info_by_id","user_status"};
    public  HashMap<String,Integer>  help=new HashMap<>();
    int online_number=0;
    ExecutorService exe=Executors.newFixedThreadPool(10);
    void FillHelp()
    {
        for(int i=0;i<temp.length;i++)
            help.put(temp[i],i);
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        /*Parent parent = FXMLLoader.load(getClass().getResource("BasicView.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
        core code
        exe.shutdown();
        */
       ServerSocket temp=new ServerSocket(12345);
       BasicInfoTransition bf_link=new BasicInfoTransition(new Connection(temp.accept()));
       bf_link.SendMessage("debuggg orz\n");
       File trans=new  File("D://中兴事件及其说明的问题.docx");
       bf_link.SendFile(trans);
    }
    public void runServer(){
        try {
            server=new ServerSocket(12345,1000);
        } catch (IOException e) {

        }
        while(true)
         {
             Socket socket=null;
             try {
                  socket = server.accept();
                 online_number++;
                 ICSY_USER user = new ICSY_USER(socket);
                 exe.execute(user);
             }
             catch(IOException e){
                 log.Write("诡异的连接中断\n");
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
            private void LoginOut()
            {
                 sub_server_list.remove(id);
                 /*
                     设置用户状态为离线。。。。。
                     更新数据库
                  */
            }
        private void LoginHandle() throws IOException, ClassNotFoundException {
           id=bf_trans.ReceiveMessage();
            String password=bf_trans.ReceiveMessage();
            ClientStatus status=bf_trans.ReceiveStatus();
            String realpassword=null;
            boolean exist=false;
            sub_server_list.put(id,new String[]{socket.getInetAddress().toString(),
                    Integer.toString(socket.getPort())});
            /*
                判定用户是否存在？是exist为true，realpassword置为获取对应用户的密码

           if(realpassword.equals(password))
           {
            //返回LoginStatus.find
               根据用户id从数据库中提取数据
                bf_trans.SendBasicInfo();
                bf_trans.SendSuperInfo();
           }
           else
           {
               //返回LoginStatus.password_error
           }
           */
        }

        private void RegistHandle() throws IOException {
              BasicInfo info=bf_trans.ReceiveBasicInfo();
              int userid=100000;
            /*
                userid+=numof(user);
                 将新用户相关信息写入数据库
            */
            bf_trans.SendMessage(Integer.toString(userid));
        }
        private void IdToIpHandle() throws IOException, ClassNotFoundException {
               String others_id=bf_trans.ReceiveMessage();
               /*

                */
            String[] temp=sub_server_list.get(others_id);
              if(temp==null)
              {
                  bf_trans.SendMessage(null);
                  bf_trans.SendMessage(null);
              }
              else
              {
                  bf_trans.SendMessage(temp[0]);
                  bf_trans.SendMessage(temp[1]);
              }
        }

        private void RequestBasicInfoByIdHandle()
        {
            BasicInfo info=null;
           /*
               根据用户id查找数据库找出基本数据
            */
            bf_trans.SendBasicInfo(info);
        }
        private  void RequsetStatusHandle()
        {
            BasicInfo info=null;
           /*
               根据用户id查找数据库找出好友列表的相关状态信息，不加转换的送到前端，由前端转换显示
            */
            bf_trans.SendBasicInfo(info);
        }
        @Override
        public void run() {
            int hash_val=0;
            do
            {
                try {
                    String head = bf_trans.ReceiveMessage();
                     hash_val=help.get(head);
                     switch(hash_val)
                     {
                         case 1:  LoginHandle();  break;
                         case 2:   break;
                         case 3:   break;
                         case 4:   break;
                         case 5:   break;
                         case 6:   break;
                         case 7:   break;
                         case 8:   break;

                     }
                }
                catch(IOException e)
                {
                    log.Write("在服务器处理函数发生IO异常\n");

                }
                catch (ClassNotFoundException e)
                {
                    log.Write("读取到未知的数据类型\n");
                }
            }while(hash_val>0);
        }
    }
}
