package Client.model;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MessageRecord {
	private StringProperty messageRecord=new SimpleStringProperty();
	private LocalDateTime sendTime;
	private StringProperty sender=new SimpleStringProperty();
	private StringProperty receiver=new SimpleStringProperty();
	
	public MessageRecord() {};
	public MessageRecord(String message) {
		messageRecord.set(message);
		sendTime=LocalDateTime.now();
	}
	public void setSendTime() {
		sendTime=LocalDateTime.now();
	}
	public LocalDateTime getSendTime() {
		return sendTime;
	}
	public String getMessage() {
		return messageRecord.get();
	}
	public void setMessage(String message) {
		this.messageRecord.set(message);
	}
	public String getReceiver() {
		return receiver.get();
	}
	public void setReceiver(String receiver) {
		this.receiver.set(receiver);
	}
	public String getSender() {
		return sender.get();
	}
	public void setSender(String sender) {
		this.sender.set(sender);
	}
}
