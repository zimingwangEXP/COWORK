package Client.view;

import Client.util.*;

import java.awt.Checkbox;
import java.io.IOException;
import java.net.Socket;

import Client.application.MainApp;
import CommonBase.Connection.BasicInfoTransition;
import CommonBase.Connection.Connection;
import CommonBase.Data.BasicInfo;
import CommonBase.Data.ClientStatus;
import CommonBase.Data.LoginStatus;
import CommonBase.Data.SuperInfo;
import CommonBase.Log.Log;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import Client.model.Account;

public class SignInController {
	private Log  log=new Log();
	private Account curAccount;
	private String errorMessage="";
	@FXML
	private ImageView protrait;
	@FXML
	private PasswordField password;
	@FXML
	private TextField ID;
	@FXML
	private CheckBox autoLogIn;
	@FXML
	private CheckBox passwordRemember;

	private boolean signUpClicked=false;
	private boolean passwordForget=false;
	private boolean okClicked=false;
	private MainApp mainApp;
	private Stage dialogStage;
	
	public Account getAccount() {
		return curAccount;
	}
	@FXML
	public void handleSignUp() {
		mainApp.showSignUpWindow();
	}
	@FXML
	public boolean handleSignIn() {
		if(ID.getText()==null||ID.getText().length()==0)
			errorMessage+="Please input one Account ID\n";
		if(password.getText()==null||password.getText().length()==0)
			errorMessage+="Please input the correspond password\n";
		curAccount=Utility.getAccountForID(ID.getText(), password.getText());
		if(errorMessage.length()!=0) {
			Utility.showExceptionDialog(errorMessage, AlertType.INFORMATION);
			errorMessage="";
			okClicked=false;
		}
		else {
			if(curAccount==null)
				okClicked=false;
			else
				okClicked=true;
		}
		if(okClicked) {
			//dialogStage.close();
			mainApp.showMainWindow();
		}

		return okClicked;
	}
	public boolean passwordValid() {
		return Utility.passwordCorrect(idToString(),password.getText());
	}
	public boolean IDValid() {
		return Utility.IDExist(idToString());
	}
	public void setStage(Stage stage) {
		dialogStage=stage;
	}
	public boolean okClicked() {
		return okClicked;
	}
	public void setMainApp(MainApp main) {
		this.mainApp=main;
	}
	public SignInController() {}
	public String idToString() {
		return ID.getText();
	}
	@FXML
	public void initialize() {}
}
