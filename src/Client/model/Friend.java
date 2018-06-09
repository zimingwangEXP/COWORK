package Client.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Friend {
	private StringProperty friendID=new SimpleStringProperty();
	private ObservableList<MessageRecord> communicateRecords=FXCollections.observableArrayList();
	private StringProperty friendGroup=new SimpleStringProperty();
	private StringProperty friendNote=new SimpleStringProperty();
	
	public Friend() {}
	public Friend(String id) {
		friendID.set(id);
		friendGroup.set(id);
		friendNote.set(id);
	}
	public Friend(String id,String group) {
		friendID.set(id);
		friendNote.set(id);
		friendGroup.set(group);
	}
	public void setFriendID(String ID) {
		friendID.set(ID);
	}
	public String getFriendID() {
		return friendID.get();
	}
	public void setFriendNote(String note) {
		friendNote.set(note);
	}
	public String getFriendNote() {
		return friendNote.get();
	}
	public ObservableList<MessageRecord> getCommunicateRecords() {
		return communicateRecords;
	}
	public String getFriendGroup() {
		return friendGroup.get();
	}
	public void setFriendGroup(String friendGroup) {
		this.friendGroup.set(friendGroup);
	}
}
