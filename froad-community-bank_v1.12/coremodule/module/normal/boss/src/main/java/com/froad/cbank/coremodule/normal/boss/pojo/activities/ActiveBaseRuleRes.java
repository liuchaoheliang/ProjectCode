package com.froad.cbank.coremodule.normal.boss.pojo.activities;

/**
 * 营销活动基础规则响应类
 * @author luwanquan@f-road.com.cn
 * @date 2015年12月3日 上午11:20:44
 */
public class ActiveBaseRuleRes {
	private String clientId;//客户端ID
	private String activeId;//活动ID
	private String type;//活动类型
	private String status;//活动状态（0-待提交、1-审核中、2-审核不通过、3-启用、4-禁用）
	private Long createTime;//创建时间
	private Long updateTime;//更新时间
	private String activeName;//活动名称
	private String activeLogo;//活动Logo
	private Long expireStartTime;//有效期开始时间
	private Long expireEndTime;//有效期结束时间
	private String limitType;//限制类型（0–不限制、1–限制商户、2–限制门店、3–限制商品）
	private Integer merchantRate;//商户补贴比例，实际值乘以1000入库
	private Integer bankRate;//银行补贴比例，实际值乘以1000入库
	private Integer fftRate;//方付通贴比例，实际值乘以1000入库
	private String settleType;//结算方式
	private String description;//活动描述
	private String operator;//操作员
	
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
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
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
	public String getSettleType() {
		return settleType;
	}
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
}
