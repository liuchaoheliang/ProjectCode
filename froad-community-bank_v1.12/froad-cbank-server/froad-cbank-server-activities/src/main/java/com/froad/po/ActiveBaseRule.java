package com.froad.po;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ActiveBaseRule implements Serializable {
	/**
	 * 主键ID
	 */
	private Long id;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 活动ID
	 */
	private String activeId;
	/**
	 * 活动类型
	 */
	private String type;
	/**
	 * 活动状态 0-待提交1-审核中2-审核不通过3-启用4-禁用
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 活动名称
	 */
	private String activeName;
	/**
	 * 活动Logo
	 */
	private String activeLogo;
	/**
	 * 有效期开始时间
	 */
	private Date expireStartTime;
	/**
	 * 有效期结束时间
	 */
	private Date expireEndTime;
	/**
	 * 限制类型 0-不限制 1-限制商户 2-限制门 店(预留) 3-限制商品
	 */
	private String limitType;
	/**
	 * 商户补贴比例, 实际值乘以1000入库
	 */
	private Integer merchantRate;
	/**
	 * 银行补贴比例, 实际值乘以1000入库
	 */
	private Integer bankRate;
	/**
	 * 方付通贴比例, 实际值乘以1000入库
	 */
	private Integer fftRate;
	/**
	 * 结算⽅式 0-实时结算 1-延期结算
	 */
	private String settleType;
	/**
	 * 活动描述
	 */
	private String description;
	/**
	 * 操作员
	 */
	private String operator;

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

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public String getActiveLogo() {
		return activeLogo;
	}

	public void setActiveLogo(String activeLogo) {
		this.activeLogo = activeLogo;
	}

	public Date getExpireStartTime() {
		return expireStartTime;
	}

	public void setExpireStartTime(Date expireStartTime) {
		this.expireStartTime = expireStartTime;
	}

	public Date getExpireEndTime() {
		return expireEndTime;
	}

	public void setExpireEndTime(Date expireEndTime) {
		this.expireEndTime = expireEndTime;
	}

	public String getLimitType() {
		return limitType;
	}

	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	public Integer getMerchantRate() {
		return merchantRate;
	}

	public void setMerchantRate(Integer merchantRate) {
		this.merchantRate = merchantRate;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	public Integer getBankRate() {
		return bankRate;
	}

	public void setBankRate(Integer bankRate) {
		this.bankRate = bankRate;
	}

	public Integer getFftRate() {
		return fftRate;
	}

	public void setFftRate(Integer fftRate) {
		this.fftRate = fftRate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ActiveBaseRule() {
	}

	public String getSettleType() {
		return settleType;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public ActiveBaseRule(Long id, String clientId, String activeId,
			String type, String status, Date createTime, Date updateTime,
			String activeName, String activeLogo, Date expireStartTime,
			Date expireEndTime, String limitType, Integer merchantRate,
			Integer bankRate, Integer fftRate, String settleType,
			String description, String operator) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.activeId = activeId;
		this.type = type;
		this.status = status;
		this.createTime = createTime;
		this.updateTime = updateTime;
		this.activeName = activeName;
		this.activeLogo = activeLogo;
		this.expireStartTime = expireStartTime;
		this.expireEndTime = expireEndTime;
		this.limitType = limitType;
		this.merchantRate = merchantRate;
		this.bankRate = bankRate;
		this.fftRate = fftRate;
		this.settleType = settleType;
		this.description = description;
		this.operator = operator;
	}

}
