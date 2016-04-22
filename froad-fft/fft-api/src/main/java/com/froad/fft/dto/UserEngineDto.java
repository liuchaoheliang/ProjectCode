package com.froad.fft.dto;

import java.io.Serializable;

/**
 * *******************************************************
 * <p>工程: fft-api</p>
 * <p>类名: UserEngineDto.java</p>
 * <p>描述: *-- <b>调用服务端用户系统传输Dto</b> --*</p>
 * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn</p>
 * <p>时间: 2014年3月28日 下午4:25:01</p>
 ******************************************************** 
 */
public class UserEngineDto implements Serializable {

	private static final long serialVersionUID = -97072570278491085L;

	/**
	 * memberCode
	 */
	private Long memberCode;

	
	/**
	 * 帐号
	 */
	private String loginID;
	
	/**
	 * 密码
	 */
	private String loginPwd;

	/**
	 * 邮箱地址
	 */
	private String email;

	/**
	 *  用户活动状态 1正常状态(已激活)，2.未激活，3已冻结
	 */
	private Integer status;
	
	/**
	 * 注册IP
	 */
	private String registerIP;
	
	/**
	 * 手机号码
	 */
	private String mobile;
	
	/**
	 * 注册渠道
	 */
	private String createChannel;
	
	/**
	 * 分分通积分
	 */
	private String froadPoints;
	/**
	 * 银行积分
	 */
	private String bankPoints;
	
	/**
	 * 用户自然名
	 */
	private String uname;
	/**
	 * 推荐人信息
	 */
	private String introducer;
	
	//------------------------------------------加密后的信息
	private String mobileEncrypt;
	//------------------------------------------加密后的信息
	
	
	//-------------------------------------------------Result Prope------------------------------------------------
	private Boolean result;
	private Integer msgCode;
	private String errorMsg;
	private String demo;
	//-------------------------------------------------Result Prope------------------------------------------------
	
	public UserEngineDto(){}
	
	/**
	 *  注册使用的构造函数 email选填，
	 * @param loginID
	 * @param loginPwd
	 * @param registerIP
	 * @param mobile
	 * @param email
	 */
	public UserEngineDto(String loginID,String loginPwd,String registerIP,String mobile,String email,String createChannel){
		this.loginID = loginID;
		this.loginPwd = loginPwd;
		this.registerIP = registerIP;
		this.mobile = mobile;
		this.email = email;
		this.createChannel = createChannel;
	}


	public UserEngineDto(Long memberCode, String loginID, String loginPwd,String email, Integer status, String registerIP, String mobile,
			String createChannel, Boolean result, Integer msgCode,
			String errorMsg, String demo,String uname,String introducer) {
		this.memberCode = memberCode;
		this.loginID = loginID;
		this.loginPwd = loginPwd;
		this.email = email;
		this.status = status;
		this.registerIP = registerIP;
		this.mobile = mobile;
		this.createChannel = createChannel;
		this.result = result;
		this.msgCode = msgCode;
		this.errorMsg = errorMsg;
		this.demo = demo;
		this.uname = uname;
		this.introducer = introducer;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRegisterIP() {
		return registerIP;
	}

	public void setRegisterIP(String registerIP) {
		this.registerIP = registerIP;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateChannel() {
		return createChannel;
	}

	public void setCreateChannel(String createChannel) {
		this.createChannel = createChannel;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public Boolean getResult() {
		return result;
	}

	public Integer getMsgCode() {
		return msgCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getDemo() {
		return demo;
	}

	public String getFroadPoints() {
		return froadPoints;
	}

	public String getBankPoints() {
		return bankPoints;
	}

	public void setFroadPoints(String froadPoints) {
		this.froadPoints = froadPoints;
	}

	public void setBankPoints(String bankPoints) {
		this.bankPoints = bankPoints;
	}

	public String getMobileEncrypt() {
		return mobileEncrypt;
	}

	public void setMobileEncrypt(String mobileEncrypt) {
		this.mobileEncrypt = mobileEncrypt;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}
}
