package Client.view;

import Client.application.UserInfoBuffer;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.BasicInfo;
import CommonBase.Data.ClientStatus;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import Client.model.Account;
import Client.util.Utility;

import java.io.IOException;
import java.net.Socket;

public class SignUpController {
	private Account account;
	private String errorMessage="";
	private Stage dialogStage;
	@FXML
	private TextField nickname;
	@FXML
	private TextField password;
	@FXML
	private TextField passwordConfirm;
	@FXML
	private TextField signature;
	@FXML
	private TextField age;
	@FXML
	private TextField department;
	@FXML
	private TextField phoneNumber;
	@FXML
	private DatePicker birthday;
	@FXML
	private ToggleGroup gender=new ToggleGroup();
	private boolean okClicked=false;
	
	public boolean inputValid() throws IOException, ClassNotFoundException {
		//������չÿ���ı���Ҫ���LocalDateת��ΪString�ĸ�����
		if(nickname.getText()==null||nickname.getText().length()==0) 
			errorMessage+="Please input one nickname\n";
		if(password.getText()==null||password.getText().length()==0) 
			errorMessage+="Please input one password\n";
		//System.out.println(password.getText()+" "+passwordConfirm.getText());
		if(!password.getText().equals(passwordConfirm.getText()))
			errorMessage+="The two password are not the same\n";
		//if(gender.getSelectedToggle()==null)
		//	errorMessage+="Please choose your gender";

		BasicInfo bf=new BasicInfo.BasicInfoBuilder(nickname.getText(),null).SetAge(new Integer(age.getText()))
				.SetJob(department.getText()).SetNick_name(nickname.getText()).SetMailAdress(null).
						SetPhone_number(phoneNumber.getText()).SetBirthday(birthday.getValue().toString()).
						SetSex(gender.getSelectedToggle().getUserData().toString().equals("MALE")).
						SetSignatrue(signature.getText()).Builder();
		UserInfoBuffer.bf_trans_to_server.SendRegistInfo(bf);
		String id=UserInfoBuffer.bf_trans_to_server.ReceiveMessage();
		/*
		   返回登录界面，将id栏设为id
		 */




		if(errorMessage.length()!=0) {
			Utility.showExceptionDialog(errorMessage, AlertType.WARNING);
			errorMessage="";
			return false;
		}
		else
			return true;
	}
	public void setStage(Stage stage) {
		dialogStage=stage;
	}
	public Account getNewAccount() {
		return account;
	}
	@FXML
	public void initialize() {}
	public SignUpController() {
	}
	public boolean isOkClicked() {
		return okClicked;
	}
	@FXML
	public void handleOK() {
		try {
			if(inputValid()) {
				account=new Account();
				account.applyOneNewAccount();
				if(age.getText().length()!=0)
					account.setAge(Integer.parseInt(age.getText()));
				account.setDepartment(department.getText());
				//account.setGender(gender.getSelectedToggle().getUserData().toString());
				//�˴�bug�д��޸�
				account.setNickname(nickname.getText());
				account.setPassword(password.getText());
				account.setBirthday(birthday.getValue());
				account.setSignature(signature.getText());
				account.setPhoneNumber(phoneNumber.getText());
				Utility.showExceptionDialog("Your account:"+account.getID(), AlertType.INFORMATION);
				okClicked=true;
			}
			else 
				okClicked=false;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
