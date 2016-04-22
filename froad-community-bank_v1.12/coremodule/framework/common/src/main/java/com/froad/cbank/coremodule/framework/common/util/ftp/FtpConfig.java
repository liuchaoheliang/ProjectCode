package com.froad.cbank.coremodule.framework.common.util.ftp;

/**
 * ftp连接配置
 * @ClassName FtpConfig
 * @author zxl
 * @date 2015年4月1日 下午2:45:13
 */
public class FtpConfig {
	
	private String ip;
	
	private int port;
	
	private String userName;
	
	private String passWord;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	public FtpConfig(String ip, String userName, String passWord) {
		super();
		this.ip = ip;
		this.userName = userName;
		this.passWord = passWord;
	}

	public FtpConfig(String ip, int port, String userName, String passWord) {
		super();
		this.ip = ip;
		this.port = port;
		this.userName = userName;
		this.passWord = passWord;
	}
	
}
