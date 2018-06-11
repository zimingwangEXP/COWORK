package Client.view;

import Client.MainThread;
import com.sun.scenario.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.text.Text;

public class BarStageHandle {
    MainThread cur;
    @FXML
    Text nick_name;
    @FXML
    Text id;
    @FXML
    Text status;
    @FXML
    TextField search;
    @FXML
    TreeView<String> group_list;
    @FXML
   public void AddFriend(){

    }
    @FXML
   public void ModifyBlackList(){

    }

    @FXML
   public void  Settings(){

    }
    @FXML
    public void  ModifyBasicInfo(){

    }
    public void setCur(MainThread cur){
        this.cur=cur;
    }
}
