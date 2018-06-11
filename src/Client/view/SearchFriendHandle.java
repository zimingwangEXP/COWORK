package Client.view;

import Client.MainThread;
import CommonBase.CSLinker.CSLinker;
import CommonBase.Data.BasicInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class SearchFriendHandle {
    String errorMessage=null;
    BasicInfo temp=null;
    MainThread cur;
    @FXML
    TextField id;

    @FXML
    TextArea info;
    @FXML
    TextField group;
    @FXML
    TextArea pass;
    @FXML
    public void SearchHandle(){
       temp= CSLinker.GetUserInfoById(id.getText());
       if(temp==null)
       {
           MainThread.showExceptionDialog("没有此id的用户",Alert.AlertType.INFORMATION);
           return;
       }
       info.appendText(temp.ToString());
    }
    @FXML
    public void OkHandle(){
        if(temp==null)
        {
            MainThread.showExceptionDialog("没有此id的用户",Alert.AlertType.INFORMATION);
            return;
        }
      /*  if(group.getText()==null||group.getText().length()==0)
        {

        }
        else
        {

        }*/
       int res=CSLinker.SendFriendRequest(id.getText(),pass.getText());
       if(res==1)
       {
           MainThread.showExceptionDialog("此用户已经将你加入黑名单",Alert.AlertType.INFORMATION);
       }
       else if(res==3)
       {
           MainThread.showExceptionDialog("用户暂不在线，已转为离线发送",Alert.AlertType.INFORMATION);
       }
       else if(res==4)
       {
           MainThread.showExceptionDialog("在线好友请求发送成功",Alert.AlertType.INFORMATION);
       }
    }
    public void setCur(MainThread cur) {
        this.cur = cur;
    }
}
