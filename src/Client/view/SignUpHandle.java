package Client.view;

import Client.MainThread;
import CommonBase.CSLinker.CSLinker;
import CommonBase.Data.BasicInfo;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.Executors;

public class SignUpHandle {
    //******************注册***************************
    String errorMessage=null;
    MainThread cur;
    @FXML
    private TextField regist_nickname;
    @FXML
    private TextField regist_password;
    @FXML
    private TextField regist_passwordConfirm;
    @FXML
    private TextField regist_signature;
    @FXML
    private TextField regist_age;
    @FXML
    private TextField regist_department;
    @FXML
    private TextField regist_phoneNumber;
    @FXML
    private DatePicker regist_birthday;
    @FXML
    private RadioButton regist_FEMALE;
    @FXML
    private RadioButton regist_MALE;
    private ToggleGroup gender=new ToggleGroup();
    public boolean inputValid() throws IOException, ClassNotFoundException {
        if(regist_nickname.getText()==null||regist_nickname.getText().length()==0)
            errorMessage+="Please input one nickname\n";
        if(regist_password.getText()==null||regist_password.getText().length()==0)
            errorMessage+="Please input one password\n";
        //System.out.println(password.getText()+" "+passwordConfirm.getText());
        if(!regist_password.getText().equals(regist_passwordConfirm.getText()))
            errorMessage+="The two password are not the same\n";
        //if(gender.getSelectedToggle()==null)
        //	errorMessage+="Please choose your gender";
        if(errorMessage!=null&&errorMessage.length()!=0) {
            MainThread.showExceptionDialog(errorMessage, Alert.AlertType.WARNING);
            errorMessage="";
            return false;
        }
        else
            return true;
    }
    @FXML
    public void initialize() {
        regist_MALE.setToggleGroup(gender);
        regist_FEMALE.setToggleGroup(gender);
    }
    @FXML
    public void handleOK() {
        System.out.println(regist_nickname.getText());
        try {
            if(inputValid()&&regist_age.getText().length()!=0) {
                BasicInfo bf = new BasicInfo.BasicInfoBuilder(regist_nickname.getText(), null).SetAge(new Integer(regist_age.getText()))
                        .SetJob(regist_department.getText()).SetNick_name(regist_nickname.getText()).SetMailAdress(null).
                                SetPhone_number(regist_phoneNumber.getText()).SetBirthday(Date.from(regist_birthday.getValue().
                                atStartOfDay(ZoneId.systemDefault()).toInstant()))
                        .SetSex(gender.getSelectedToggle().toString().equals("FEMALE")).SetPassword(regist_password.getText()).
                                SetSignatrue(regist_signature.getText()).Builder();
                String id = CSLinker.Regist(bf);
                cur.RegistStage.close();
                cur.LoginStage.show();
                MainThread.showExceptionDialog("Your account:"+id, Alert.AlertType.INFORMATION);
            }
            else
            {
                MainThread.showExceptionDialog("填写基本信息有误\n", Alert.AlertType.INFORMATION);
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
            }

    public void setCur(MainThread cur) {
        this.cur = cur;
    }


}
