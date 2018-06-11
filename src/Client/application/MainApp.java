package Client.application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import Client.view.*;
import Client.view.SignUpController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Client.model.Account;
import Client.model.AccountsWrapper;
import Client.util.Utility;

public class MainApp extends Application{
	private Stage primaryStage;
	private ObservableList<Account> accounts=FXCollections.observableArrayList();
	private Account curAccount;
	
	public void showFileTransportWindow() {
		
	}
	public void showPersonalZone() {
		
	}
	public void showEditPersonalZone() {
		
	}
	public void setAccountsCachePath(File file) {
		Preferences prefs=Preferences.userNodeForPackage(MainApp.class);
		if(file!=null) 
			prefs.put("filepath", file.getPath());
		else
			prefs.remove("filepath");
	}
	public File getAccountsCachePath() {
		Preferences prefs=Preferences.userNodeForPackage(MainApp.class);
		String filePath=prefs.get("filepath", null);
		if(filePath!=null)
			return new File(filePath);
		else
			return null;
	}
	public void loadAccountsFromCacheFile(File file) {
		try {
			JAXBContext content=JAXBContext
					.newInstance(AccountsWrapper.class);
			Unmarshaller um=content.createUnmarshaller();
			AccountsWrapper wrapper=(AccountsWrapper)um.unmarshal(file);
			accounts.clear();
			accounts.addAll(wrapper.getAcounts());
			setAccountsCachePath(file);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void saveAccountsToCacheFile(File file) {
		try {
			JAXBContext content=JAXBContext.newInstance(AccountsWrapper.class);
			Marshaller ma=content.createMarshaller();
			ma.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			AccountsWrapper warpper=new AccountsWrapper();
			ma.marshal(warpper, file);
			setAccountsCachePath(file);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void showSearChFriend(){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/Client/view/FriendSearch.fxml"));
			SplitPane pane = (SplitPane) loader.load();
			Stage dialogStage=new Stage();
			dialogStage.setScene(new Scene(pane));
			dialogStage.setTitle("查找好友");
			dialogStage.getIcons().add(new Image("/Client/resources/Icon/ICSY.png"));
			dialogStage.initOwner(primaryStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			FriendSearchController friendSearchController=loader.getController();
			friendSearchController.setStage(dialogStage);
			dialogStage.showAndWait();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void showSignInWindow() {
		try {
			FXMLLoader loader=new FXMLLoader();
			System.out.println(Main.class.getResource("")+"\n");
			System.out.println(Main.class.getResource("/")+"\n");
			System.out.println(MainApp.class.getResource("/Client/view/SignIn.fxml"));
			loader.setLocation(MainApp.class.getResource("/Client/view/SignIn.fxml"));
			AnchorPane pane=(AnchorPane)loader.load();
			Stage dialogStage=new Stage();
			dialogStage.setScene(new Scene(pane));
			dialogStage.setTitle("ICSY_1.0");
			dialogStage.getIcons().add(new Image("/Client/resources/Icon/ICSY.png"));
			dialogStage.initOwner(primaryStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			SignInController signInController=loader.getController();
			signInController.setMainApp(this);
			signInController.setStage(dialogStage);
			dialogStage.showAndWait();
			curAccount=signInController.getAccount();
			dialogStage.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void showSignUpWindow() {
		try {
			FXMLLoader loader=new FXMLLoader();
			System.out.println(MainApp.class.getResource("/Client/view/SignUp.fxml"));
			loader.setLocation(MainApp.class.getResource("/Client/view/SignUp.fxml"));
			AnchorPane pane=(AnchorPane) loader.load();
			Stage dialogStage=new Stage();
			dialogStage.setTitle("注册");
			dialogStage.initOwner(primaryStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.getIcons().add(new Image("/Client/resources/Icon/ICSY.png"));
			dialogStage.setScene(new Scene(pane));
			SignUpController controller=loader.getController();
			controller.setStage(dialogStage);
			dialogStage.showAndWait();
			if(controller.isOkClicked()) {
				accounts.add(controller.getNewAccount());
				dialogStage.close();
			}
				
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void showMainWindow() {
		try {
			FXMLLoader loader=new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/Client/view/MainWindow.fxml"));
			SplitPane pane=(SplitPane)loader.load();
			MainWindowController controller=new MainWindowController();
			controller.setMainApp(new MainApp());
			controller.setCurAccount(curAccount);
			Stage primaryStage=new Stage();
			primaryStage.setTitle("ICSY");
			primaryStage.getIcons().add(new Image("/Client/resources/Icon/ICSY.png"));
			primaryStage.setScene(new Scene(pane));
			//dialogStage.initModality(Modality.APPLICATION_MODAL);
			//dialogStage.initOwner(primaryStage);
			controller.setStage(primaryStage);
			primaryStage.show();
			//dialogStage.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public Stage getPrimaryStage() {
		return primaryStage;
	}
	public ObservableList<Account> getAccounts(){
		return accounts;
	}
	public Account getCurAccount() {
		return curAccount;
	}
	public void setCurAccount(Account account) {
		curAccount=account;
	}
	public MainApp() {}
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage=primaryStage;
		showSignInWindow();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
