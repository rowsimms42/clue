/***************************************
 * Handles client/cerver communication during game
 ***************************************/

import java.io.Serializable;

public class Message implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int messageID;
	private Object data;
	
	public Message(int messageID, Object data) {
		setData(data);
		setMessageID(messageID);
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
		System.out.println("test print of data: " + data);
	}

}