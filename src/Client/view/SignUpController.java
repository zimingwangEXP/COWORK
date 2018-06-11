package Client.view;
import CommonBase.CSLinker.CSLinker;
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
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

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
	private RadioButton FEMALE;
	@FXML
	private RadioButton MALE;
	private ToggleGroup gender=new ToggleGroup();
	private boolean okClicked=false;
	
	public boolean inputValid() throws IOException, ClassNotFoundException {
		if(nickname.getText()==null||nickname.getText().length()==0)
			errorMessage+="Please input one nickname\n";
		if(password.getText()==null||password.getText().length()==0)
			errorMessage+="Please input one password\n";
		//System.out.println(password.getText()+" "+passwordConfirm.getText());
		if(!password.getText().equals(passwordConfirm.getText()))
			errorMessage+="The two password are not the same\n";
		//if(gender.getSelectedToggle()==null)
		//	errorMessage+="Please choose your gender";
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
	public void initialize() {
		MALE.setToggleGroup(gender);
		FEMALE.setToggleGroup(gender);
	}
	public SignUpController() {
	}
	public boolean isOkClicked() {
		return okClicked;
	}
	@FXML
	public void handleOK() {
		System.out.println(nickname.getText());
		try {
			if(inputValid()) {
				account=new Account();
				account.applyOneNewAccount();
				if(age.getText().length()!=0)
					account.setAge(Integer.parseInt(age.getText()));
				account.setDepartment(department.getText());
				account.setNickname(nickname.getText());
				account.setPassword(password.getText());
				account.setBirthday(birthday.getValue());
				account.setSignature(signature.getText());
				account.setPhoneNumber(phoneNumber.getText());
				BasicInfo bf=new BasicInfo.BasicInfoBuilder(nickname.getText(),null).SetAge(new Integer(age.getText()))
						.SetJob(department.getText()).SetNick_name(nickname.getText()).SetMailAdress(null).
								SetPhone_number(phoneNumber.getText()).SetBirthday(Date.from(birthday.getValue().
								atStartOfDay(ZoneId.systemDefault()).toInstant()))
						       .SetSex(gender.getSelectedToggle().toString().equals("FEMALE")).SetPassword(password.getText()).
								SetSignatrue(signature.getText()).Builder();
				String id=CSLinker.Regist(bf);
				Utility.showExceptionDialog("Your account:"+id, AlertType.INFORMATION);
				okClicked=true;
			}
			else 
				okClicked=false;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
