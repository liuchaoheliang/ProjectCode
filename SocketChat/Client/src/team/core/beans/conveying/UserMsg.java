package team.core.beans.conveying;

import java.io.Serializable;

public class UserMsg implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userId; //user表id
	
	private String userInfoId; //userInfo表id
	
	private String name;//帐号
	
	private String pwd;//密码

	private String mail;//邮箱
	
	private String sex;//性别
	
	private String nickname;//昵称

	private String autograph;//个性签名
	
	
	private String isOnline = "N";//是否在线 Y:是 | N:否
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserInfoId() {
		return userInfoId;
	}

	public void setUserInfoId(String userInfoId) {
		this.userInfoId = userInfoId;
	}

	public String getName() {
		return name;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getAutograph() {
		return autograph;
	}

	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	
}
