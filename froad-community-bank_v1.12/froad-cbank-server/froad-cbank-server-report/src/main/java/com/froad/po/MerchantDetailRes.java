package com.froad.po;

import java.io.Serializable;

public class MerchantDetailRes implements Serializable {
	
	private static final long serialVersionUID = 5212406356563737406L;

	private Integer id;				//id
	
	private String clientId;		//客户端号
	
	private String orgCode;			//机构号
	
	private String orgName;			//机构名
	
	private Integer addCount;		//新增商户数
	
	private Integer changeCount;	//动账商户数
	
	private Integer totalCount;		//结余商户数
	
	private Double percent;			//商户占比
	
	private Integer orderCount;		//订单数
	
	private Double totalAmount;		//订单金额

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getAddCount() {
		return addCount;
	}

	public void setAddCount(Integer addCount) {
		this.addCount = addCount;
	}

	public Integer getChangeCount() {
		return changeCount;
	}

	public void setChangeCount(Integer changeCount) {
		this.changeCount = changeCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	
}
