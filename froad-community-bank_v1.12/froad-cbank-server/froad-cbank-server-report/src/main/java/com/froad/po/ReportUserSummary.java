package com.froad.po;

import java.util.Date;

public class ReportUserSummary {

	private Long id;			//id
	private Date createTime;	//日期
	private String clientId;	//客户端
	private String bankCardId;	//银行卡id(银行卡10-11位数字)
	private String forgCode;	//一级机构码
	private String forgName;	//一级机构名称
	private String sorgCode;	//二级机构码
	private String sorgName;	//二级机构名称
	private String torgCode;	//三级机构码
	private String torgName;	//三级机构名称
	private String lorgCode;	//四级机构码
	private String lorgName;	//四级机构名称
	private Long totalUser;		//用户总数
	
	private Integer week;		//周
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getBankCardId() {
		return bankCardId;
	}
	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}
	public String getForgCode() {
		return forgCode;
	}
	public void setForgCode(String forgCode) {
		this.forgCode = forgCode;
	}
	public String getForgName() {
		return forgName;
	}
	public void setForgName(String forgName) {
		this.forgName = forgName;
	}
	public String getSorgCode() {
		return sorgCode;
	}
	public void setSorgCode(String sorgCode) {
		this.sorgCode = sorgCode;
	}
	public String getSorgName() {
		return sorgName;
	}
	public void setSorgName(String sorgName) {
		this.sorgName = sorgName;
	}
	public String getTorgCode() {
		return torgCode;
	}
	public void setTorgCode(String torgCode) {
		this.torgCode = torgCode;
	}
	public String getTorgName() {
		return torgName;
	}
	public void setTorgName(String torgName) {
		this.torgName = torgName;
	}
	public String getLorgCode() {
		return lorgCode;
	}
	public void setLorgCode(String lorgCode) {
		this.lorgCode = lorgCode;
	}
	public String getLorgName() {
		return lorgName;
	}
	public void setLorgName(String lorgName) {
		this.lorgName = lorgName;
	}
	public Long getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(Long totalUser) {
		this.totalUser = totalUser;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	
	
	
}
