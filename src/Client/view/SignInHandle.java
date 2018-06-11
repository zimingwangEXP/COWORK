package Client.view;

import Client.MainThread;
import CommonBase.CSLinker.CSLinker;
import CommonBase.Data.ClientStatus;
import CommonBase.Data.LoginStatus;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class  SignInHandle{
//********************登陆******************************
String errorMessage=null;
    MainThread cur;
@FXML
private TextField login_id;
@FXML
private PasswordField login_password;
@FXML
public void handleSignIn() {
        if (login_id.getText() == null || login_password.getText().length() == 0)
        errorMessage += "Please input one Account ID\n";
        if (login_password.getText() == null || login_password.getText().length() == 0)
        errorMessage += "Please input the correspond password\n";
        LoginStatus back=CSLinker.Login(login_id.getText(),login_password.getText(),ClientStatus.online);//改
        if(back==LoginStatus.find)
        {
                   errorMessage="";
                   Platform.runLater(new Runnable() {
                                             @Override
                                             public void run() {
                                                     cur.LoadBarStage();
                                                     cur.LoginStage.close();
                                             }
                                     });



             //cur.BarStage.show();
                return;
        }
        else if(back==LoginStatus.username_error)
        {
        errorMessage+="the user name doesn't exist\n";
        }
        else if(back==LoginStatus.password_error)
        {
        errorMessage+="the corresponding password is wrong\n";
        }
        if (errorMessage!=null&&errorMessage.length() != 0) {
        MainThread.showExceptionDialog(errorMessage, Alert.AlertType.INFORMATION);
        errorMessage = "";
        }
        }
@FXML
public void handleSignUp(){
        cur.LoadRegistStage();
        cur.LoginStage.close();
        }
        public void setCur(MainThread cur) {
            this.cur = cur;
        }
//*****************************************************************************************************
        //  }
    }

