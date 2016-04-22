package com.froad.CB.po.user;

import java.io.Serializable;


	/**
	 * 类描述：用户信息简要说明
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:56:30 PM 
	 */
public class Suggest implements Serializable {

	private static final long serialVersionUID = -5475791104891295192L;
	private String ID;
	private String userID;
	private String username;// 用户名
	private String suggesttittle;
	private String suggestcontext;

	private String submittime;
	private String transactionID;

	private String RespCode = "";
	private String RespMsg = "";

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSuggesttittle() {
		return suggesttittle;
	}

	public void setSuggesttittle(String suggesttittle) {
		this.suggesttittle = suggesttittle;
	}

	public String getSuggestcontext() {
		return suggestcontext;
	}

	public void setSuggestcontext(String suggestcontext) {
		this.suggestcontext = suggestcontext;
	}

	public String getSubmittime() {
		return submittime;
	}

	public void setSubmittime(String submittime) {
		this.submittime = submittime;
	}

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
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

}
