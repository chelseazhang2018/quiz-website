package support;

public class Announcement {
	private String content;
	private String administrator;
	private String announceTime;
	public Announcement(String content, String administrator, String announceTime) {
		this.content = content;
		this.administrator = administrator;
		this.announceTime = announceTime;
	}
	public String getContent() {
		return content;
	}
	public String getAdministrator() {
		return administrator;
	}
	public String getAnnounceTime() {
		return announceTime;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Announcement:" + content + "\n");
		sb.append("By:" + administrator + ", created on:" + announceTime);
		return sb.toString();
	}
}
