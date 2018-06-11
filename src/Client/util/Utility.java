package Client.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

import Client.application.MainApp;
import CommonBase.CSLinker.CSLinker;
import CommonBase.Data.ClientStatus;
import CommonBase.Data.LoginStatus;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import Client.model.Account;


public class Utility {
	public static boolean IDExist(String id) {
		return true;
	}
	public static boolean passwordCorrect(String ID,String password) {
		return true;
	}
	public static Account getAccountForID(String id,String password) {//修改状态栏
		if(id.length()!=0) {
			Account acount=new Account();
			LoginStatus back=CSLinker.Login(id,password,ClientStatus.online);
			if(back==LoginStatus.find)
			{
				return CSLinker.TranseToAcount();
			}
			else if(back==LoginStatus.password_error)
			{
				Utility.showExceptionDialog("密码输入错误", AlertType.INFORMATION);
				return null;
			}
			else if(back==LoginStatus.username_error)
			{
				Utility.showExceptionDialog("用户名不存在", AlertType.INFORMATION);
                return null;
			}
			else
				return null;
		}
		else
			return null;
	}
	public static  int   getIndex(ClientStatus status){
		if(status==ClientStatus.online)
			return 1;
		if(status==ClientStatus.susupend)
			return 2;
		if(status==ClientStatus.busy)
			return 3;
		if(status==ClientStatus.stealth)
			return 4;
		return 0;
	}
	public static String getStatus(Integer i) {
		switch(i) {
		case 1:
			return "ONLINE";
		case 2:
			return "OFFLINE";
		case 3:
			return "BUSY";
		case 4:
			return "CLOAKING";
		default:
				return "";
		}
	}
	public static void showExceptionDialog(String e,AlertType type) {
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
	public static void showExceptionDialog(Exception e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText("Ooops, an Exception happened");
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
}
