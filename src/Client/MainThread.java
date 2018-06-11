package Client;
import Client.view.*;
import CommonBase.CSLinker.CSLinker;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.BasicInfo;
import CommonBase.Data.ClientStatus;
import CommonBase.Data.LoginStatus;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainThread extends Application {
   // public  class SignInHandle {
   public  Stage ReceiveStage=new Stage();
   public   Stage BarStage;
   public   Stage LoginStage=new Stage();
   public   Stage RegistStage=new Stage();
   public   Stage  ChatStage=new Stage();
   public Stage  FriendStage=new Stage();
    Button BasicInfo;
    Button  BlackList;
    Button Settings;
    Button AddFriend;
    TreeView<String> tree;
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
        alert.showAndWait();
    }
    public static void main(String[]args){
       launch(args);
    }
     public void Init(Stage primaryStage){
         BarStage=primaryStage;

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
         Text id=new Text("id");
         id.setFont(Font.font("Comic Sans MS",16));
         Text  status=new Text("status");
         status.setFont(Font.font("Comic Sans MS",16));
         Text nick_name=new Text("nick name");
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
         tree=new TreeView<>();
         friend_list.setContent(tree);
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
         bottom.getChildren().addAll(BasicInfo,AddFriend,Settings,BlackList);
         root.getChildren().addAll(combine,search,group_tab,bottom);
         BarStage.setScene(new Scene(root));
         BarStage.show();
        AddFriend.setOnAction(e->{
             Platform.runLater(new Runnable() {
                 @Override
                 public void run() {
                     LoadFriendStage();
                 }
             });
        });
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

    }
    class KeepThread implements   Runnable{
        ServerSocket sub_server=null;
        @Override
        public void run() {
            while(!CSLinker.HasInit()) {
                try {
                    ExecutorService exe=Executors.newFixedThreadPool(20);
                    sub_server = new ServerSocket(CSLinker.bf_trans_to_server.getPort()+1,100);
                    while(true)
                    {
                        Socket connection=sub_server.accept();
                        exe.submit(new Runnable() {
                                       @Override
                                       public void run()
                                       {
                                           BasicInfoTransition sub_trans = new BasicInfoTransition(new Connection(connection));
                                           String back=sub_trans.ReceiveMessage();
                                            if(back.equals("text"))
                                           {

                                           }
                                            else if(back.equals("file"))
                                            {

                                            }
                                            else if(back.equals("add_friend"))
                                            {

                                            }
                                            else if(back.equals("friend_request_back"))
                                      }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
