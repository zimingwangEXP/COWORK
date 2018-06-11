package Client.model;

import java.io.File;
import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import Client.util.Utility;

public class Account {
	private IntegerProperty status=new SimpleIntegerProperty();
	private StringProperty ID=new SimpleStringProperty();
	private StringProperty nickname=new SimpleStringProperty();
	private StringProperty password=new SimpleStringProperty();
	private StringProperty gender=new SimpleStringProperty();
	private IntegerProperty age=new SimpleIntegerProperty();
	private ObservableList<Friend> friendList=FXCollections.observableArrayList();
	private ObservableList<Friend> blackList=FXCollections.observableArrayList();
	private StringProperty signature=new SimpleStringProperty();
	private LocalDate birthday;
	private Image protrait=new Image("file:resources/images/Head/User/1_350.png");
	private StringProperty phoneNumber=new SimpleStringProperty();
	private StringProperty department=new SimpleStringProperty();
	static Integer accountCount=new Integer(0);//删掉?
	
	public Account() {	}
	public void applyOneNewAccount() {//删掉?
		ID.set("10086"+accountCount.toString());
		accountCount++;
	}
	public ObservableList<Friend> getBlackList(){
		return blackList;
	}
	public String getStatus() {
		return Utility.getStatus(status.get());
	}
	public void setStatus(Integer s) {
		status.set(s);
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate date) {
		birthday=date;
	}
	public Image getProtrait() {
		return protrait;
	}
	public void setProtraitFile(File file) {
		if(!file.exists())
			Utility.showExceptionDialog("File doesn't exist", AlertType.WARNING);
		else
			protrait=new Image(file.toString());
	}
	public String getDepartment() {
		return department.get();
	}
	public void setDepartment(String department) {
		this.department.set(department);
	}
	public String getPhoneNumber() {
		return phoneNumber.get();
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}
	public String getSignature() {
		return signature.get();
	}
	public void setSignature(String siganature) {
		signature.set(siganature);
	}
	public ObservableList<Friend> getFriendList(){
		return friendList;
	}
	public Integer getAge() {
		return age.get();
	}
	public void setAge(Integer age) {
		this.age.set(age);
	}
	public String getGender() {
		return gender.get();
	}
	public void setGender(String gender){
		this.gender.set(gender);
	}
	public String getPassword() {
		return password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	public String getNickname() {
		return nickname.get();
	}
	public void setNickname(String name) {
		nickname.set(name);
	}
	public String getID() {
		return ID.get();
	}
	public void setStatus(int status) {
		this.status.set(status);
	}
	public void setAge(int age) {
		this.age.set(age);
	}
	public void setID(String ID) {
		this.ID.set(ID);
	}
	public static void setAccountCount(Integer accountCount) {
		Account.accountCount = accountCount;
	}
	public void setBlackList(ObservableList<Friend> blackList) {
		this.blackList = blackList;
	}
	public void setFriendList(ObservableList<Friend> friendList) {
		this.friendList = friendList;
	}
	public void setProtrait(Image protrait) {
		this.protrait = protrait;
	}
}
