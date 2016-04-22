package com.froad.CB.po.user;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.froad.util.TimestampAdapter;


	/**
	 * 类描述：登陆管理
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:49:13 PM 
	 */
public class LoginManager implements Serializable {
	
	private static final long serialVersionUID = -6899162610222362477L;
	 private String ID;//主键
		private String sessionID;// sessionid 

		private String userID;// 用户ID 用户唯一标识

		private String username;// 用户登录名

		private String password;// 用户密码

		private String uname;// 用户自然名

		private Timestamp loginTime; // 登录时间

		private String RespCode = "1";// 响应码,0表示交易成功,非0表示交易失败 默认响应失败

		private String RespMsg;// 响应信息

		private String activateURL;// 激活超链接

		private String toEmailAddress;// 用户注册邮箱，用来接收激活超链接

		private String bank;// 签约银行

		private String securityCode;// 验证码

		private String email;// 邮箱

		private String lastIP;// 上次登录的IP

		private Timestamp lastTime;// 上次登录时间

		private String status;// 用户活动状态 0：激活状态 1：未激活

		private String mobilephone;// 电话号码

		private String activatecode;// 激活码

		private int userrole;// 用户角色 0：管理员 1：卖家 2:买家 3：客服
		
		

		private User user;
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		/**
		 * @return 获得 activatecode
		 */
		public String getActivatecode() {
			return activatecode;
		}

		/**
		 * @param activatecode 设置 activatecode
		 */
		public void setActivatecode(String activatecode) {
			this.activatecode = activatecode;
		}

		/**
		 * @return 获得 bank
		 */
		public String getBank() {
			return bank;
		}

		/**
		 * @param bank 设置 bank
		 */
		public void setBank(String bank) {
			this.bank = bank;
		}

		/**
		 * @return 获得 email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * @param email 设置 email
		 */
		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * @return 获得 lastIP
		 */
		public String getLastIP() {
			return lastIP;
		}

		/**
		 * @param lastIP 设置 lastIP
		 */
		public void setLastIP(String lastIP) {
			this.lastIP = lastIP;
		}

		/**
		 * @return 获得 lastTime
		 */
		@XmlJavaTypeAdapter(TimestampAdapter.class)
		public Timestamp getLastTime() {
			return lastTime;
		}

		/**
		 * @param lastTime 设置 lastTime
		 */
		public void setLastTime(Timestamp lastTime) {
			this.lastTime = lastTime;
		}

		/**
		 * @return 获得 mobilephone
		 */
		public String getMobilephone() {
			return mobilephone;
		}

		/**
		 * @param mobilephone 设置 mobilephone
		 */
		public void setMobilephone(String mobilephone) {
			this.mobilephone = mobilephone;
		}

		/**
		 * @return 获得 securityCode
		 */
		public String getSecurityCode() {
			return securityCode;
		}

		/**
		 * @param securityCode 设置 securityCode
		 */
		public void setSecurityCode(String securityCode) {
			this.securityCode = securityCode;
		}

		/**
		 * @return 获得 status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * @param status 设置 status
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return 获得 userrole
		 */
		public int getUserrole() {
			return userrole;
		}

		/**
		 * @param userrole 设置 userrole
		 */
		public void setUserrole(int userrole) {
			this.userrole = userrole;
		}

		/**
		 * @return 获得 toEmailAddress
		 */
		public String getToEmailAddress() {
			return toEmailAddress;
		}

		/**
		 * @param toEmailAddress
		 *            设置 toEmailAddress
		 */
		public void setToEmailAddress(String toEmailAddress) {
			this.toEmailAddress = toEmailAddress;
		}

		/**
		 * @return 获得 activateURL
		 */
		public String getActivateURL() {
			return activateURL;
		}

		/**
		 * @param activateURL
		 *            设置 activateURL
		 */
		public void setActivateURL(String activateURL) {
			this.activateURL = activateURL;
		}

		public String getRespCode() {
			return RespCode;
		}

		public void setRespCode(String respCode) {
			RespCode = respCode;
		}

		public String getRespMsg() {
			return RespMsg;
		}

		public void setRespMsg(String respMsg) {
			RespMsg = respMsg;
		}

		public String getSessionID() {
			return sessionID;
		}

		public void setSessionID(String sessionID) {
			this.sessionID = sessionID;
		}

		public String getUserID() {
			return userID;
		}

		public void setUserID(String userID) {
			this.userID = userID;
		}

		@XmlJavaTypeAdapter(TimestampAdapter.class)
		public Timestamp getLoginTime() {
			return loginTime;
		}

		public void setLoginTime(Timestamp loginTime) {
			this.loginTime = loginTime;
		}


		/**
		 * @return the uname
		 */
		public String getUname() {
			return uname;
		}

		/**
		 * @param uname
		 *            the uname to set
		 */
		public void setUname(String uname) {
			this.uname = uname;
		}

		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @param username
		 *            the username to set
		 */
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		 * @return 获得 password
		 */
		public String getPassword() {
			return password;
		}

		/**
		 * @param password
		 *            设置 password
		 */
		public void setPassword(String password) {
			this.password = password;
		}

		public String getID() {
			return ID;
		}

		public void setID(String id) {
			ID = id;
		}
	
}
