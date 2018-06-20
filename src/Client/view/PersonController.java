package Client.view;

import Client.MainThread;
import CommonBase.CSLinker.CSLinker;
import CommonBase.Data.BasicInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.ZoneId;
import java.util.Date;

public class PersonController {
    String errorMessage=null;
    MainThread cur;
    public void setCur(MainThread cur) {
        this.cur = cur;
    }
    @FXML
    Label name;
    @FXML
    Label signature;
    @FXML
    Label status;
    @FXML
    private DatePicker person_birthday;
    @FXML
    private TextField person_name;
    @FXML
    private  TextField person_age;
    @FXML
    private TextField person_signature;
    @FXML
    private TextField person_address;
    @FXML
    private TextField person_job;
    @FXML
    private TextField person_email;
    @FXML
    private TextField person_phone;
    @FXML
    public void initialize() {
       flush();
    }
    void flush(){
        status.setText(CSLinker.bf.getStatus().toString());
        name.setText(CSLinker.bf.getNick_name());
        signature.setText(CSLinker.bf.getSignature());
    }
    @FXML
    private void ModifyHandle(){
        CSLinker.bf.setNick_name(person_name.getText());
        CSLinker.bf.setBirthday(Date.from(person_birthday.getValue().
                atStartOfDay(ZoneId.systemDefault()).toInstant()));
        CSLinker.bf.setAge(new Integer(person_age.getText()));
        CSLinker.bf.setSignature(person_signature.getText());
        CSLinker.bf.setAdrress(person_address.getText());
        CSLinker.bf.setJob(person_job.getText());
        CSLinker.bf.setMail_address(person_email.getText());
        CSLinker.bf.setPhone_number(person_phone.getText());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CSLinker.UpdateBasicInfo(CSLinker.bf);
                flush();
                MainThread.showExceptionDialog("基本信息更新成功",Alert.AlertType.INFORMATION);
            }
        });
    }

}
