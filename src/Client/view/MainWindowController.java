package Client.view;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import Client.application.MainApp;
import Client.application.UserInfoBuffer;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.BasicInfo;
import CommonBase.Data.ClientStatus;
import CommonBase.Data.TempSavedInfo;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Client.model.Account;
import Client.model.Friend;
import Client.util.TextFieldTreeCellImpl;

public class MainWindowController implements  Runnable {
	private Account curAccount=new Account();
	private Friend curFriend=new Friend();
	private String errorMessage="";
	private Stage dialogStage;
	@FXML
	private Button curFriendNote=new Button();
	@FXML
	private TextArea message=new TextArea();
	@FXML
	private ImageView protrait=new ImageView("resources/Icon/ICSY.png");
	@FXML
	private TextField friendSearch=new TextField();
	@FXML
	private TreeView<String> friendList=new TreeView<>();
	@FXML
	private ListView<Button> recentDialog=new ListView<>();
	@FXML
	
	private ListView<String> messageRecords=new ListView<>(); 
	@FXML
	private VBox menuShow=new VBox();//������ڶ���
	private MainApp mainApp;
	private Socket socket;
	private BasicInfoTransition bf_tran;
    MainWindowController(Socket socket){
    	this.socket=socket;
    	bf_tran=new BasicInfoTransition(new Connection(socket));
	}
	@Override
	public void run() {
    	String message=null;
        do
		{
			try {
				message=bf_tran.ReceiveMessage();
				if(message.equals("status_broad"))
				{
					String from=bf_tran.ReceiveMessage();
					ClientStatus cur=bf_tran.ReceiveStatus();
					/*
					    将用户列表中from的状态置为cur，显示
					 */
				}
				else if(message.equals("text"))
				{
					TempSavedInfo info=(TempSavedInfo)bf_tran.TemplateReceive();
				}
				else if(message.equals("file"))
				{
					TempSavedInfo info=(TempSavedInfo)bf_tran.TemplateReceive();
                    bf_tran.ReceiveFile("/receive");
                    //显示相关信息到前端
				}
				else if(message.equals("friend_request"))
				{
					TempSavedInfo info=(TempSavedInfo)bf_tran.TemplateReceive();
					//显示相关信息,获取是否同意
					boolean accept;
					String[] ip=null;
					 ip=UserInfoBuffer.bf_trans_to_server.IdToIp("id");
					 if(ip[0].equals("not_exist"))
					 {

					 }
					 else if((ip[0].equals("not_on_line")))
					 {
					 	 UserInfoBuffer.bf_trans_to_server.SendMessage("friend_request_back");
					 	// UserInfoBuffer.bf_trans_to_server.TemplateSend(构造的tempInfo，content为Bool表示是否接受);
					 }
					 else
					 {
					 	BasicInfoTransition rqt=new BasicInfoTransition(new Connection(new Socket(ip[0],12345)));
					 	rqt.SendMessage("friend_request_back");
					 	rqt.TemplateSend("构造的tempInfo，content为Bool表示是否接受");
					 }
				}
				else if(message.equals("friend_request_back"))
				{
					TempSavedInfo back=(TempSavedInfo)bf_tran.TemplateReceive();
					//获取信息
					//更新界面
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}while(!message.equals("end"));
	}
	public MainWindowController() {};
	@FXML
	public void initialize() {
		test();
		friendList.setVisible(false);
		recentDialog.setVisible(false);
		menuShow.setVisible(false);
		initFriendList();
		initRecentDialog();
		curFriendNote.setText(curFriend.getFriendNote());
	}
	private void initFriendList() {
		TreeItem<String> root=new TreeItem<>("");
		friendList.setRoot(root);
		for(Friend friend:curAccount.getFriendList()) {
			TreeItem<String> friendNode=new TreeItem<>(friend.getFriendNote());
			boolean found=false;
			for(TreeItem<String> groupNode:root.getChildren()) {
				if(groupNode.getValue().equals(friend.getFriendNote())) {
					found=true;
					groupNode.getChildren().add(friendNode);
					break;
				}
			}
			if(!found) {
				TreeItem<String> groupNode=new TreeItem<>(friend.getFriendGroup());
				root.getChildren().add(groupNode);
				groupNode.getChildren().add(friendNode);
			}
		}
		friendList.setEditable(true);
		friendList.setCellFactory((TreeView<String> p)->
			new TextFieldTreeCellImpl());
	}
	private void initRecentDialog() {
		
	}
	public void setMainApp(MainApp main) {
		mainApp=main;
	}
	@FXML
	public void handleExit() {
		System.exit(0);
	}
	@FXML
	public void handleLogOut() {
		/*
		 * δʵ��ע�����ܣ���dialogStage����ΪprimaryStage�޷�ʵ���˳������
		 * ��ʾSignIn���棬��dialogStage����Ϊ��ͨstage��
		 * װ��scene���޷�����������κ����
		 * */
		System.exit(0);
		//mainApp.showSignInWindow();
	}
	@FXML
	public void handleTheme() {
		//ѡ������
	}
	@FXML
	public void handleSetting() {
		//����
	}
	@FXML
	public void handleAbout() {
		//��ʾ������Ա��Ϣ
	}
	@FXML
	public void handleChangePassword() {
		//�޸�����
	}
	@FXML
	public void handlePersonZone() {//修改个人资料

		UserInfoBuffer.bf_trans_to_server.SendBasicInfo(UserInfoBuffer.bf);
	}
	@FXML
	public void handleCloaking() {
		//�л��û�״̬Ϊ����
	}
	@FXML
	public void handleBusy() {
		//�л��û�״̬æµ
	}
	@FXML
	public void handleOnLine() {
		//�л��û�״̬Ϊ����
	}
	public void setStage(Stage stage) {
		dialogStage=stage;
	}
	public void setCurAccount(Account account) {
		curAccount=account;
	}
	private Friend getCurFriend() {
		return curFriend;
	}
	private void setCurFriend(Friend friend) {
		curFriend=friend;
	}
	public Account getCurAccount() {
		return curAccount;
	}
	@FXML
	public void handleSendFile() {
		//��������ӿ�
	}
	@FXML
	public void handleAddFriend() {
		//��ʾ���Һ��Ѵ���
	}
	@FXML
	public void handleSendMessage() {
		BasicInfoTransition bf_trans= null;
		try {
			bf_trans = new BasicInfoTransition(new Connection(new Socket("localhost",12345)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//bf_trans.IdToIp();
		message.clear();
	}
	@FXML
	public void handleMessageRecords() {
		//show message recordss
	}
	@FXML
	public void handleFriendNicknameClicked() {
		//show friend's personalZone
	}
	@FXML
	public void handleMenuShow() {
		if(menuShow.visibleProperty().get()==false)
			menuShow.setVisible(true);
		else
			menuShow.setVisible(false);
	}
	@FXML
	public void handleFileRecords() {
		
	}
	@FXML
	public void handleFriendList() {
		friendList.setVisible(true);
		recentDialog.setVisible(false);
		menuShow.setVisible(false);
	}
	@FXML
	public void handleRecentDialog() {
		
	}
	public void test() {
		//���Ժ���
		Account account=new Account();
		ObservableList<Friend> friends=account.getFriendList();
		for(int i=0;i<5;++i) {
			for(int j=0;j<5;++j) {
				Friend friend=new Friend("Trump "+j,"Trump "+i);
				friends.add(friend);
			}
		}
		account.setAge(10);
		account.setStatus(1);
		account.setSignature("I am stupid");
		account.setPassword("stupid");
		account.setNickname("Donald Trump");
		account.setGender("MALE");
		account.setDepartment("Saler");
		this.setCurAccount(account);
	}

}
