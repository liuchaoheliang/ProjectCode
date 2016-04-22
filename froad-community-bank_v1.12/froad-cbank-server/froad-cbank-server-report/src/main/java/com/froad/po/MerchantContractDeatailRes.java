package com.froad.po;

public class MerchantContractDeatailRes {
	
	private String constractStaff;	//商户签约人
	private Integer addCount;		//新增商户数
	private Integer changeCount;	//动账商户数
	private Integer totalCount;		//结余商户数
	private Double	addPercent;		//新增占比
	private Integer orderCount;		//订单数
	private Double totalAmount;		//订单金额
	
	public String getConstractStaff() {
		return constractStaff;
	}
	public void setConstractStaff(String constractStaff) {
		this.constractStaff = constractStaff;
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
	public Double getAddPercent() {
		return addPercent;
	}
	public void setAddPercent(Double addPercent) {
		this.addPercent = addPercent;
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
