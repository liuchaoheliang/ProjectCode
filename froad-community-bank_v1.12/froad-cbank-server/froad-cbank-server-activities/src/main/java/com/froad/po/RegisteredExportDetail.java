package com.froad.po;

import java.util.Date;

 /**
  * @ClassName: RegisteredExportDetail
  * @Description: 注册活动清单（红包和事物礼品明细）
  * @author froad-shenshaocheng 2015年12月2日
  * @modify froad-shenshaocheng 2015年12月2日
 */
public class RegisteredExportDetail {
	/**
	 * 序号
	 */	
	private Integer rowNum;
	
	/**
	 * 用户编号
	 */	
	private Long menberCode;
	
	/**
	 * 电话号码
	 */	
	private String phone;
	
	/**
	 * 注册时间
	 */	
	private Date registeredTime;
	
	/**
	 * 红包名称或者实物礼品名称
	 */	
	private String activeName;
	
	/**
	 * 所属客户端（中文）
	 */
	private String clientName;

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public Long getMenberCode() {
		return menberCode;
	}

	public void setMenberCode(Long menberCode) {
		this.menberCode = menberCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getRegisteredTime() {
		return registeredTime;
	}

	public void setRegisteredTime(Date registeredTime) {
		this.registeredTime = registeredTime;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	
	
}
