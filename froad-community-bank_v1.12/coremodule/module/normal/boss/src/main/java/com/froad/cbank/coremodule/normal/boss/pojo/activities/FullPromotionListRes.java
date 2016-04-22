package com.froad.cbank.coremodule.normal.boss.pojo.activities;

/**
 * 满额促销列表查询返回结果实体类
 * @author yfy
 */
public class FullPromotionListRes {
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 客户端名称
	 */
	private String clientName;
	/**
	 * 活动编号
	 */
	private String activeId;
	/**
	 * 活动名称
	 */
	private String activeName;
	/**
	 * 有效期开始时间
	 */
	private Long expireStartTime;
	/**
	 * 有效期结束时间
	 */
	private Long expireEndTime;
	/**
	 * 金额下限
	 */
	private String minLimit;
	/**
	 * 返现方式 true-0-支付前 false-1-支付后
	 */
	private Boolean isPrePay;
	/**
	 * 支付方式
	 */
	private String payMethod;
	/**
	 * 活动范围0-不限制 1-限制商户 2-限制门店(预留) 3-限制商品
	 */
	private String limitType;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 状态0-待提交1-审核中2-审核不通过3-启动 4-禁止
	 */
	private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getActiveId() {
		return activeId;
	}
	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
	public String getActiveName() {
		return activeName;
	}
	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}
	public Long getExpireStartTime() {
		return expireStartTime;
	}
	public void setExpireStartTime(Long expireStartTime) {
		this.expireStartTime = expireStartTime;
	}
	public Long getExpireEndTime() {
		return expireEndTime;
	}
	public void setExpireEndTime(Long expireEndTime) {
		this.expireEndTime = expireEndTime;
	}
	public String getMinLimit() {
		return minLimit;
	}
	public void setMinLimit(String minLimit) {
		this.minLimit = minLimit;
	}
	public Boolean getIsPrePay() {
		return isPrePay;
	}
	public void setIsPrePay(Boolean isPrePay) {
		this.isPrePay = isPrePay;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getLimitType() {
		return limitType;
	}
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
