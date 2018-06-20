package Client;
import Client.view.*;
import CommonBase.CSLinker.CSLinker;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.*;
import Server.ServerData.message;
import com.sun.deploy.util.BlackList;
import com.sun.javafx.collections.ObservableListWrapper;
import com.sun.scenario.Settings;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.internal.org.objectweb.asm.Handle;
import sun.plugin2.message.Message;
import sun.reflect.generics.tree.Tree;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThread extends Application {
   // public  class SignInHandle {
    HashMap<String,ChatStage> StageList=new HashMap<>();
    int total=0;
    TempSavedInfo temp;
    ExecutorService exe=Executors.newFixedThreadPool(20);
   public  Stage ReceiveStage=new Stage();
   public   Stage BarStage;
   public   Stage LoginStage=new Stage();
   public   Stage RegistStage=new Stage();
   public   Stage  ChatStage=new Stage();
   public   Stage Person=new Stage();
   public Stage  FriendStage=new Stage();
    public Stage  FileStage=new Stage();
    Button BasicInfo;
    Button  BlackList;
    Button Settings;
    Button AddFriend;
    Button refuse;
    Button ok;
    TextArea content;
    TreeView<Button> tree;
    class FriendRequest{
        Stage root=new Stage();
        BorderPane bp=new BorderPane();
        Text content=new Text();
        Button ok=new Button("我知道了");
        public FriendRequest(String info)
        {
            content.setFont(Font.font(21));
            content.setText(info);
            bp.setCenter(content);
            bp.setBottom(ok);
            bp.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            bp.setPrefSize(308,97);
            root.setScene(new Scene(bp));
            root.show();
            ok.setOnAction(e->{root.close();});
        }
    }
    class ChatStage{
     public   UserSnapShot who=null;
        public   Stage chat=new Stage();
        public  SplitPane sp=new SplitPane();
        public  VBox up=new VBox();
        public  VBox down=new VBox();
        public Text sign=new Text();
        public TextArea up_text=new TextArea();
        public TextArea down_text=new TextArea();
        public Button vidio_chat=new Button("视频聊天");
        public Button history=new Button("查询全部历史记录");
        public Button biaoqing=new Button("表情");
        public Button send_audio=new Button("发送语音");
        public Button send_file=new Button("上传文件");
        public Button send_text=new Button("发送文本");
        HBox control=new HBox();
         public  ChatStage(UserSnapShot who){
             System.out.println("who "+who.getId());
            this.who=who;
             sp.getItems().addAll(up,down);
             sp.setDividerPosition(0,0.6);
             sp.setVisible(true);
             sp.setOrientation(Orientation.VERTICAL);
             sign.setFont(Font.font("Comic Sans MS",FontWeight.SEMI_BOLD,17));
            sign.setText(who.getUser_name()+"        "+who.getSignatrue());
             control.setPrefHeight(47);
             control.setFillHeight(true);
             vidio_chat.setPrefSize(102,46);
             history.setPrefSize(102,46);
             up_text.setPrefSize(599,271);
             biaoqing.setPrefSize(102,46);
             send_audio.setPrefSize(102,46);
             send_file.setPrefSize(102,46);
             send_text.setPrefSize(102,46);
             down_text.setPrefSize(598,140);
             control.getChildren().addAll(vidio_chat,history,biaoqing,send_audio,send_file,send_text);
             down.getChildren().addAll(down_text,control);
             send_file.setOnAction(e->{
                 LoadFileTranse(who,true,null);
             });
             history.setOnAction(e->{
                 LinkedList<message> all_text= CSLinker.GetHistory(who.getId());
                 String res=null;
                 for(message one:all_text)
                 {
                     res+=one.getContent();
                 }
                 MainThread.showExceptionDialog(res,Alert.AlertType.INFORMATION);

             });
             up.getChildren().addAll(sign,up_text);
             sp.setPrefSize(600,477);
             chat.setScene(new Scene(sp));
             up_text.setEditable(false);
             send_text.setOnAction(e->{
                 String content= LocalTime.now().toString()+":\n"+CSLinker.bf.getNick_name()+": " +down_text.getText()+"\n";
                 up_text.appendText(content);
                 down_text.clear();
                 CSLinker.SendText(who.getId(),content);
                 System.out.println("send_text"+who.getId());
             });
         }
         public void Show()
         {
             chat.show();
         }
    }
    public static void showExceptionDialog(String e,Alert.AlertType type) {
        Alert alert=new Alert(null);
        switch(type) {
            case INFORMATION:
                alert.setHeaderText(e);
            case CONFIRMATION:
                alert.setHeaderText(e);
            case WARNING:
                alert.setHeaderText(e);
            default:
                break;
        }
        alert.setAlertType(type);
        alert.show();
    }
    public static void main(String[]args){
       launch(args);
    }
     public void Init(Stage primaryStage){
         BarStage=primaryStage;
    }
    public void LoadReceiveStage(){
        BorderPane  ap=new BorderPane();
        ap.setPrefSize(220,140);
        content=new TextArea();
        content.setMinSize(236,83);
        HBox tx=new HBox();
        refuse=new Button("拒绝");
        ok=new Button("接受");
        tx.getChildren().addAll(ok,refuse);
        tx.setPadding(new Insets(10,50,10,50));
        tx.setSpacing(25);
        ap.setCenter(content);
        ap.setBottom(tx);
       ReceiveStage.setScene(new Scene(ap));
        refuse.setOnAction(e->{
            CSLinker.SendFriendRequestBack(temp.getFrom(),false);
            ReceiveStage.close();
        });
        ok.setOnAction(e->{
            CSLinker.SendFriendRequestBack(temp.getFrom(),true);
            BasicInfo info= CSLinker.GetUserInfoById(temp.getFrom());
                CSLinker.sf.getFriend_list().add(new UserSnapShot(info.getId(), info.getNick_name(), info.getStatus(), info.getSignature(), "default"));
                Button new_one = new Button(info.getNick_name() + "(" + info.getId() + ")" + "-----" + info.getStatus().toString());
                new_one.setOnAction(b -> {

                    ChatStage chat2 = new ChatStage(CSLinker.sf.getFriend_list().get(tree.getFocusModel().getFocusedIndex() - 1));
                    StageList.put(chat2.who.getId(), chat2);
                    chat2.Show();
                });
                tree.getRoot().getChildren().add(new TreeItem<>(new_one));
            ReceiveStage.close();
        });
    }
     public void LoadRegistStage(){
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainThread.class.getResource("/Client/view/SignUp.fxml"));
                AnchorPane pane = (AnchorPane) loader.load();
                RegistStage.setScene(new Scene(pane));
                RegistStage.setTitle("ICSY注册");
                RegistStage.getIcons().add(new Image("/Client/resources/Icon/ICSY.png"));
                //LoginStage.initOwner(BarStage);
                //LoginStage.initModality(Modality.WINDOW_MODAL);
                SignUpHandle signInController=loader.getController();
                signInController.setCur(this);
                RegistStage.show();

            }catch (IOException e){
                e.printStackTrace();
            }
     }
     public void LoadPerson(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainThread.class.getResource("/Client/view/PersonalZone.fxml"));
            AnchorPane ap = (AnchorPane) loader.load();
            Person.setScene(new Scene(ap));
            Person.setTitle("修改个人信息");
            FriendStage.getIcons().add(new Image("/Client/resources/Icon/ICSY.png"));
            Person.show();
        }catch(IOException e)
        {
            e.printStackTrace();
        }
     }
     public void LoadFileTranse(UserSnapShot who,boolean pd,BasicInfoTransition link){
         try {
             FXMLLoader loader = new FXMLLoader();
             loader.setLocation(MainThread.class.getResource("/Client/view/FileTransport.fxml"));
             AnchorPane pane = (AnchorPane) loader.load();
             FileStage.setScene(new Scene(pane));
             FileStage.setTitle("发送文件");
             FileStage.getIcons().add(new Image("/Client/resources/Icon/ICSY.png"));
             //LoginStage.initOwner(BarStage);
             //LoginStage.initModality(Modality.WINDOW_MODAL);
             FileTransportHandle signInController=loader.getController();
             signInController.setMain(this);
             signInController.setStage(FileStage);
             signInController.setWho(who);
             signInController.setPd(pd);
             signInController.setSub_trans(link);
             FileStage.show();

         }catch (IOException e){
             e.printStackTrace();
         }
     }
    public void LoadFriendStage(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainThread.class.getResource("/Client/view/FriendSearch.fxml"));
            SplitPane pane = (SplitPane) loader.load();
            FriendStage.setScene(new Scene(pane));
            FriendStage.setTitle("查找好友");
            FriendStage.getIcons().add(new Image("/Client/resources/Icon/ICSY.png"));
            //LoginStage.initOwner(BarStage);
            //LoginStage.initModality(Modality.WINDOW_MODAL);
            SearchFriendHandle signInController=loader.getController();
            signInController.setCur(this);
            FriendStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
     public void LoadBarStage(){
         VBox root=new VBox();
         BarStage.setTitle("ICSY");
         root.setPrefSize(247,554);
         BorderPane combine=new BorderPane();
         VBox help=new VBox();
         Text id=new Text(CSLinker.bf.getId());
         id.setFont(Font.font("Comic Sans MS",16));
         Text  status=new Text("on_line");
         status.setFont(Font.font("Comic Sans MS",16));
         Text nick_name=new Text(CSLinker.bf.getNick_name());
         nick_name.setFont(Font.font("Comic Sans MS",16));
         ImageView show=new ImageView(new Image("/Client/resources/Icon/login.jpg"));
         show.setFitHeight(88);
         show.setFitWidth(91);
         help.getChildren().addAll(nick_name,id,status);
         combine.setLeft(show);
         combine.setCenter(help);
         TextField search=new TextField();
         search.setPromptText("搜索");
         TabPane group_tab=new TabPane();
         Tab friend_list=new Tab("好友列表");
         Tab chat=new Tab("会话");
        // tree=new TreeView<>();
         group_tab.getTabs().addAll(friend_list,chat);
         HBox bottom=new HBox();
         BasicInfo=new Button("修改基本信息");
         BasicInfo.setWrapText(true);
         BasicInfo.setPrefSize(67,59);
         AddFriend=new Button("在线添加好友");
         AddFriend.setPrefSize(67,59);
         AddFriend.setWrapText(true);
         Settings=new Button("个人设置");
         Settings.setPrefSize(67,59);
         Settings.setWrapText(true);
         bottom.setMinSize(0,59);
         BlackList=new Button("修改黑名单");
         BlackList.setPrefSize(67,59);
         BlackList.setWrapText(true);
         //tree.setPrefSize();
         AddFriend.setOnAction(e->{
             Platform.runLater(new Runnable() {
                 @Override
                 public void run() {
                     LoadFriendStage();
                 }
             });
         });
         BasicInfo.setOnAction(e->{
             LoadPerson();
         });
         Button Inbox =new Button("default");
         ImageView rootIcon=new ImageView(new Image("file:/Client/resources/Icon/login.png"));
         TreeItem<Button> rootItem = new TreeItem<> (Inbox, rootIcon);
         // Inbox.setOnAction(e->);
         rootItem.setExpanded(true);
         for (UserSnapShot one:CSLinker.sf.getFriend_list()) {
             Button bt=new Button(one.getUser_name()+"("+one.getId()+")"+"-----"+one.getStatus().toString());
             TreeItem<Button> item = new TreeItem<> (bt);
             bt.setOnAction(
                     e->{
                         Platform.runLater(new Runnable() {
                                               @Override
                                               public void run() {
                                                   System.out.println(tree.getSelectionModel().getSelectedIndex());
                                 ChatStage chat2 = new ChatStage(CSLinker.sf.getFriend_list().get(tree.getFocusModel().getFocusedIndex()-1));
                         StageList.put(chat2.who.getId(),chat2);
                         chat2.Show();
                                               }
                         });
                     }
             );
             rootItem.getChildren().add(item);
         }
          tree = new TreeView<> (rootItem);
         friend_list.setContent(tree);
         bottom.getChildren().addAll(BasicInfo,AddFriend,Settings,BlackList);
         root.getChildren().addAll(combine,search,group_tab,bottom);
         BarStage.setScene(new Scene(root));
         BarStage.show();

         return;
    }
     public void LoadLoginStage(){
        try{
         FXMLLoader loader=new FXMLLoader();
         System.out.println((MainThread.class.getResource("/Client/view/SignIn.fxml")));
         loader.setLocation(MainThread.class.getResource("/Client/view/SignIn.fxml"));
         AnchorPane pane=(AnchorPane)loader.load();
         LoginStage.setScene(new Scene(pane));
         LoginStage.setTitle("ICSY登陆");
         LoginStage.getIcons().add(new Image("/Client/resources/Icon/ICSY.png"));
         //LoginStage.initOwner(BarStage);
         //LoginStage.initModality(Modality.WINDOW_MODAL);
            SignInHandle signInController=loader.getController();
            signInController.setCur(this);
         LoginStage.show();
     }catch(IOException e) {
        e.printStackTrace();
    }
     }

    @Override
    public void start(Stage primaryStage) throws Exception {

         Init(primaryStage);
         LoadLoginStage();
        exe.submit(new KeepThread());

    }
    class TextThread implements  Runnable{
        UserSnapShot one=null;
        public TextThread(UserSnapShot one){
            this.one=one;
        }
        @Override
        public void run() {
            ChatStage t = new ChatStage(one);
            t.up_text.appendText((String) temp.getContent());
            StageList.put(one.getId(),t);
            t.Show();
        }
    }
    class InnerThread implements  Runnable{
        Socket connection=null;
        BasicInfoTransition sub_trans=null ;
        public InnerThread(Socket connection){

            this.connection=connection;
            sub_trans = new BasicInfoTransition(new Connection(connection));
        }
        public void run(){
            String back=null;
                back = sub_trans.ReceiveMessage();
                temp=(TempSavedInfo)sub_trans.TemplateReceive();
                if (back.equals("text"))
                {
                     if(StageList.containsKey(temp.getFrom()))
                     {
                         ChatStage t=StageList.get(temp.getFrom());
                        t.up_text.appendText((String) temp.getContent());
                     }
                     else
                     {
                         UserSnapShot one=null;
                         ArrayList<UserSnapShot> list=CSLinker.sf.getFriend_list();
                         for(int i=0;i<list.size();i++)
                         {
                             one=list.get(i);
                             if(one.getId().equals(temp.getFrom())) {
                                Platform.runLater(new TextThread(one));
                             }
                         }

                     }
                }
                else if (back.equals("file")) {
                      Platform.runLater(new Runnable() {
                          @Override
                          public void run() {
                              MainThread.showExceptionDialog(temp.getFrom()+"向你发送了文件"+temp.getContent()+"",Alert.AlertType.INFORMATION);
                              LoadFileTranse(new UserSnapShot(temp.getFrom(),null,null,null,null),false,sub_trans);
                          }
                      });

                } else if (back.equals("add_friend")) {
                    Platform.runLater(new Runnable() {
                                          @Override
                                          public void run() {
                                              LoadReceiveStage();
                                              content.setText("来自于" + temp.getFrom() + "的好友申请:\n" + "验证消息:" + temp.getContent());
                                              ReceiveStage.show();
                                          }
                                      });
                }
                else if (back.equals("friend_request_back")) {
                    if(temp.getContent().equals("accept")) {
                        Platform.runLater(new Runnable() {
                                              @Override
                                              public void run() {
                                                  FriendRequest req = new FriendRequest("用户" + temp.getFrom() + "接受了你的好友请求");
                                                  BasicInfo info = CSLinker.GetUserInfoById(temp.getFrom());
                                                  Button new_one = new Button(info.getNick_name() + "(" + info.getId() + ")" + "-----" + info.getStatus().toString());
                                                  new_one.setOnAction(b -> {
                                                      ChatStage chat2 = new ChatStage(CSLinker.sf.getFriend_list().get(tree.getFocusModel().getFocusedIndex()-1));
                                                      StageList.put(chat2.who.getId(), chat2);
                                                      chat2.Show();
                                                  });
                                                  tree.getRoot().getChildren().add(new TreeItem<>(new_one));
                                                  CSLinker.sf.getFriend_list().add(new UserSnapShot(temp.getFrom(), info.getNick_name(), info.getStatus(), info.getSignature(), "default"));
                                                  CSLinker.UpdateSuperInfo(CSLinker.sf);
                                              }});
                    }
                    else {
                        Platform.runLater(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        MainThread.showExceptionDialog(temp.getFrom() + "拒绝了你的好友请求", Alert.AlertType.INFORMATION);
                                    }});
                    }
                }
            //}while(!back.equals("terminal"));
        }
    }
    class KeepThread implements   Runnable{
        ServerSocket sub_server=null;
        @Override
        public void run() {
            while(!CSLinker.HasInit()) {

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                try {
                    int ttemp = CSLinker.bf_trans_to_server.getPort() + CSLinker.bias;
                    sub_server = new ServerSocket(CSLinker.bf_trans_to_server.getPort() + CSLinker.bias, 100);
                    System.out.println("KeepThread:" + ttemp);
                }catch (IOException e) {
                    e.printStackTrace();
                }

            while(true)
                    {
                        try {
                            exe.submit(new InnerThread(sub_server.accept()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                }
            }


