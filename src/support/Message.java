package support;

public class Message {
	private String recepient;
	private String sender;
	private String content;
	private boolean unread;
	private String sendTime;
	public Message(String recepient, String sender, String content, boolean unread, String sendTime) {
		this.recepient = recepient;
		this.sender = sender;
		this.content = content;
		this.unread = unread;
		this.sendTime = sendTime;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("To:" + recepient + " From:" + sender + " Status:" + (unread? "unread": "read") + " Time:" + sendTime + "\n");
		sb.append("\t" + content + "\n");
		return sb.toString();
	}
	// implement a bunch of getter methods if necessary;
	public String getRecepient() {
		return recepient;
	}

	public String getSender() {
		return sender;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getTime() {
		return sendTime;
	}
	public boolean isUnread(){
		return  unread;
	}
}
