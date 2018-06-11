package Client.view;

import Client.util.Utility;
import CommonBase.CSLinker.CSLinker;
import CommonBase.Data.BasicInfo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FriendSearchController {
	BasicInfo temp_info=null;
	Stage dialogStage;
	@FXML
	private TextField ID;
	@FXML
	private ImageView protrait;
	@FXML
	private Button nickame;
	@FXML
	private  TextField id;
	@FXML
	private Button search;
	@FXML
	private Button ok;
	@FXML
	private TextArea info;
	@FXML
	private  TextArea pass;
	@FXML
	private TextField group;
	public FriendSearchController() {}
	@FXML
	public void SearchHandle(){
		if(id.getText()==null)
		{
			Utility.showExceptionDialog("查找的id不能为空", Alert.AlertType.WARNING);
			return;
		}
		 temp_info=CSLinker.GetUserInfoById(id.getText());
		if(temp_info==null)
		{
			Utility.showExceptionDialog("不存在id号为"+id.getText()+"的用户", Alert.AlertType.WARNING);
			return ;
		}
		info.appendText(temp_info.ToString());
		info.setEditable(false);
	}
	public void initialize() {
		protrait.setVisible(false);
		
	}
	@FXML
	public void SendFriendRequest(){
		if(temp_info==null)
		{
			Utility.showExceptionDialog("不存在id号为"+id.getText()+"的用户", Alert.AlertType.WARNING);
			return ;
		}
		int res=CSLinker.SendFriendRequest(temp_info.getId(),pass.getText());
		if(res==1)
		{
				Utility.showExceptionDialog("id号为"+id.getText()+"的用户"+"已经将你加入黑名单", Alert.AlertType.WARNING);
		}
		else if(res==3)
		{
			Utility.showExceptionDialog("对方不在线，转为离线发送", Alert.AlertType.WARNING);
		}
		else
		{
			Utility.showExceptionDialog("在线发送成功，等待对方回复", Alert.AlertType.WARNING);
		}

	}

	public void setStage(Stage stage) {
		dialogStage=stage;
	}
	@FXML
	public void handleSearch() {
		
	}
	
}
