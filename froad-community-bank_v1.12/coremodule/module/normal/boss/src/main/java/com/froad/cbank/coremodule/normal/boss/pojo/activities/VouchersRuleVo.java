package com.froad.cbank.coremodule.normal.boss.pojo.activities;

import java.util.List;

/**
 * 红包规则详情实体类
 * @author yfy
 */
public class VouchersRuleVo {
	/**
	 * 红包基础规则
	 * ActiveBaseRuleVo的id
	 */
	private Long id;
	/**
	 * 红包详细规则
	 * VouchersDetailRuleVo的ID
	 */
	private Long detailId;
	/**
	 * 红包标签关联
	 * ActiveTagRelationVo的ID
	 */
	private Long tagId;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 客户端名称
	 */
	private String clientName;
	/**
	 * 活动ID
	 */
	private String activeId;
	/**
	 * 红包名称
	 */
	private String activeName;
	/**
	 * 活动logo
	 */
	private String activeLogo;
	/**
	 * 有效期开始时间
	 */
	private String expireStartTime;
	/**
	 * 有效期结束时间
	 */
	private String expireEndTime;
	/**
	 * 红包最小金额
	 */
	private String minMoney;
	/**
	 * 红包最大金额
	 */
	private String maxMoney;
	/**
	 * 红包总额
	 */
	private String totalMoney;
	/**
	 * 每人时间间隔单位为false时或者true日
	 */
	private Boolean isPerDay;
	/**
	 * 每人间隔量限定的次数
	 */
	private Long perCount;
	/**
	 * 每人时间间隔单位为false时或者true日
	 */
	private Boolean isTotalDay;
	/**
	 * 全局限定的日间隔
	 */
	private Integer totalDay;
	/**
	 * 全局限定的次数
	 */
	private int totCount;
	/**
	 * 是否可以和其它红包重复使用
	 */
	private Boolean isRepeat;
	/**
	 * 是否支持参与其它促销
	 */
	private Boolean isOtherActive;
	/**
	 * 是否仅限新用户使用
	 */
	private Boolean isOnlyNewUsers;
	/**
	 * 是否支持面对面
	 */
	private Boolean isFtof;
	/**
	 * 支付方式限制类型
	 */
	private String payMethod;
	/**
	 * 活动范围0-不限制 1-限制商户 2-限制门店 3-限制商品
	 */
	private String limitType;
	/**
	 * 商户补贴比例
	 */
	private String merchantRate;
	/**
	 * 银行补贴比例
	 */
	private String bankRate;
	/**
	 * 方付通补贴比例
	 */
	private String fftRate;
	/**
	 * 结算方式0-实时结算 1-后期结算
	 */
	private String settleType;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 状态0-待提交1-审核中2-审核不通过3-启动 4-禁止
	 */
	private String status;
	/**
	 * 操作人
	 */
	private String operator;
	/**
	 * 标签类型0商户/1门店/2商品
	 */
	private String itemType;
	/**
	 * 商户/门店/商品标签ID
	 */
	private String itemId;
	/**
	 * 支持参与的促销活动列表
	 */
	private List<SustainActiveRelationVo> sustainActiveList;

	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDetailId() {
		return detailId;
	}
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
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
	public String getActiveLogo() {
		return activeLogo;
	}
	public void setActiveLogo(String activeLogo) {
		this.activeLogo = activeLogo;
	}
	public String getExpireStartTime() {
		return expireStartTime;
	}
	public void setExpireStartTime(String expireStartTime) {
		this.expireStartTime = expireStartTime;
	}
	public String getExpireEndTime() {
		return expireEndTime;
	}
	public void setExpireEndTime(String expireEndTime) {
		this.expireEndTime = expireEndTime;
	}
	public String getMinMoney() {
		return minMoney;
	}
	public void setMinMoney(String minMoney) {
		this.minMoney = minMoney;
	}
	public String getMaxMoney() {
		return maxMoney;
	}
	public void setMaxMoney(String maxMoney) {
		this.maxMoney = maxMoney;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public Boolean getIsPerDay() {
		return isPerDay;
	}
	public void setIsPerDay(Boolean isPerDay) {
		this.isPerDay = isPerDay;
	}
	public Long getPerCount() {
		return perCount;
	}
	public void setPerCount(Long perCount) {
		this.perCount = perCount;
	}
	public Boolean getIsTotalDay() {
		return isTotalDay;
	}
	public void setIsTotalDay(Boolean isTotalDay) {
		this.isTotalDay = isTotalDay;
	}
	public Integer getTotalDay() {
		return totalDay;
	}
	public void setTotalDay(Integer totalDay) {
		this.totalDay = totalDay;
	}
	
	public int getTotCount() {
		return totCount;
	}
	public void setTotCount(int totCount) {
		this.totCount = totCount;
	}
	public Boolean getIsRepeat() {
		return isRepeat;
	}
	public void setIsRepeat(Boolean isRepeat) {
		this.isRepeat = isRepeat;
	}
	public Boolean getIsOtherActive() {
		return isOtherActive;
	}
	public void setIsOtherActive(Boolean isOtherActive) {
		this.isOtherActive = isOtherActive;
	}
	public Boolean getIsFtof() {
		return isFtof;
	}
	public void setIsFtof(Boolean isFtof) {
		this.isFtof = isFtof;
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
	public String getMerchantRate() {
		return merchantRate;
	}
	public void setMerchantRate(String merchantRate) {
		this.merchantRate = merchantRate;
	}
	public String getBankRate() {
		return bankRate;
	}
	public void setBankRate(String bankRate) {
		this.bankRate = bankRate;
	}
	public String getFftRate() {
		return fftRate;
	}
	public void setFftRate(String fftRate) {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public List<SustainActiveRelationVo> getSustainActiveList() {
		return sustainActiveList;
	}
	public void setSustainActiveList(List<SustainActiveRelationVo> sustainActiveList) {
		this.sustainActiveList = sustainActiveList;
	}
	public Boolean getIsOnlyNewUsers() {
		return isOnlyNewUsers;
	}
	public void setIsOnlyNewUsers(Boolean isOnlyNewUsers) {
		this.isOnlyNewUsers = isOnlyNewUsers;
	}
	
}
