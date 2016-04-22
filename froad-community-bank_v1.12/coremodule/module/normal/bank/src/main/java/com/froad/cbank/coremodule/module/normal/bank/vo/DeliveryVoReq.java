package com.froad.cbank.coremodule.module.normal.bank.vo;

import java.io.Serializable;
import java.util.ArrayList;

public class DeliveryVoReq extends BaseVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234567890L;
	
	private String orgCode;      //机构号
	private String operatorId;      //当前操作员ID
	private String takeCode;      //提货码
	private ArrayList<DeliveryVo> ticketList; //提货list 
	private String takeState;       //提货状态
	private String userId;
	private String token;
	private String orderId; //订单编号
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getTakeCode() {
		return takeCode;
	}
	public void setTakeCode(String takeCode) {
		this.takeCode = takeCode;
	}
	public ArrayList<DeliveryVo> getTicketList() {
		return ticketList;
	}
	public void setTicketList(ArrayList<DeliveryVo> ticketList) {
		this.ticketList = ticketList;
	}
	public String getTakeState() {
		return takeState;
	}
	public void setTakeState(String takeState) {
		this.takeState = takeState;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
