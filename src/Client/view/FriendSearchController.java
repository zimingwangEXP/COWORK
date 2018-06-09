package Client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FriendSearchController {
	@FXML
	private TextField ID;
	@FXML
	private ImageView protrait;
	@FXML
	private Button nickame;
	
	public FriendSearchController() {}
	public void initialize() {
		protrait.setVisible(false);
		
	}
	@FXML
	public void handleSearch() {
		
	}
	
}
