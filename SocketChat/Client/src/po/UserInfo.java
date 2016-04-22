package po;

public class UserInfo {

	private String id;
	private String lid;
	private String mail;
	private String mailactiva;
	private String sex;
	private String nickname;
	private String type;
	private String createTime;
	private String updateTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getMailactiva() {
		return mailactiva;
	}
	public void setMailactiva(String mailactiva) {
		this.mailactiva = mailactiva;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UserInfo(String mail, String sex, String nickname) {
		this.mail = mail;
		this.sex = sex;
		this.nickname = nickname;
	}
	
}
