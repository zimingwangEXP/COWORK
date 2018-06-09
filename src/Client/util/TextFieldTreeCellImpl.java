package Client.util;

import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

//����������ʾ�����б�ʱ��ֱ�ӵ���޸ı�ע
public class TextFieldTreeCellImpl extends TreeCell<String>{
	private TextField input;
	
	public TextFieldTreeCellImpl() {}
	@Override
	public void startEdit() {
		super.startEdit();
		if(input==null)
			createTextField();
		setText(null);
		input.selectAll();
	}
	@Override
	public void cancelEdit() {
		super.cancelEdit();
		setText((String)getItem());
	}
	@Override
	public void updateItem(String item,boolean empty) {
		super.updateItem(item, empty);
		if(empty)
			setText(null);
		else {
			if(isEditing()) {
				if(input!=null)
					input.setText(getString());
				setText(null);
			}
			else {
				setText(getString());
			}
		}
	}
	private String getString() {
		return getItem()==null?"":getItem().toString();
	}
	private void createTextField() {
		input=new TextField(getString());
		input.setOnKeyReleased((KeyEvent t)->{
			if(t.getCode()==KeyCode.ENTER)
				commitEdit(input.getText());
			else if(t.getCode()==KeyCode.ESCAPE)
				cancelEdit();
		});
	}
}	
