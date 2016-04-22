package com.froad.cbank.coremodule.normal.boss.pojo.member;

import java.io.Serializable;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 会员查询条件实体
 * @author: yfy
 */
public class MemberVoReq extends Page implements Serializable{
	
	private static final long serialVersionUID = -7109643516507218342L;
	
	private Long msgID;       //ID
	private String loginID;   //用户名
	private String mobile;    //手机号
	private String state;    //状态
	private String clientId;  //客户端ID
	private Boolean isVip;    //是否vip
	
	public Long getMsgID() {
		return msgID;
	}
	public void setMsgID(Long msgID) {
		this.msgID = msgID;
	}
	public String getLoginID() {
		return loginID;
	}
	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Boolean getIsVip() {
		return isVip;
	}
	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}
	
}
