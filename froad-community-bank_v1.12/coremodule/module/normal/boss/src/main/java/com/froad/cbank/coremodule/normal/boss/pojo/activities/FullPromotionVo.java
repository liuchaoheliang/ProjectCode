package com.froad.cbank.coremodule.normal.boss.pojo.activities;

/**
 * 满额促销详情实体类
 * @author yfy
 */
public class FullPromotionVo {
	/**
	 * 营销活动基础规则
	 * ActiveBaseRuleVo的id
	 */
	private Long id;
	/**
	 * 营销活动详细规则
	 * ActiveDetailRuleVo的ID
	 */
	private Long detailId;
	/**
	 * 营销活动标签关联
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
	 * 活动名称
	 */
	private String activeName;
	/**
	 * 活动logo
	 */
	private String activeLogo;
	/**
	 * 金额下限
	 */
	private String minLimit;
	/**
	 * 有效期开始时间
	 */
	private Long expireStartTime;
	/**
	 * 有效期结束时间
	 */
	private Long expireEndTime;
	/**
	 * 减金额
	 */
	private String retMoney;
	/**
	 * 满减金额总值
	 */
	private String maxMoney;
	/**
	 * 每人时间间隔单位为false时或者true日
	 */
	private Boolean isPerDay;
	/**
	 * 每人限定的间隔量
	 */
	private Integer perDay;
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
	private Long totalCount;
	/**
	 * 是否支持代金券
	 */
	private Boolean isPaperPay;
	/**
	 * 支付后红包活动id
	 */
	private String prePayActiveId;
	/**
	 * 支付后红包活动名称
	 */
	private String prePayActiveName;
	/**
	 * 返现方式 true-0-支付前 false-1-支付后
	 */
	private Boolean isPrePay;
	/**
	 * 支付方式（暂无）
	 */
	private String payMethod;
	/**
	 * 活动范围0-不限制 1-限制商户 2-限制门店(预留) 3-限制商品
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
	 * 结算方式0-实时结算 1-延期结算
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
	 * 标签类型0商户/1门店/2商品
	 */
	private String itemType;
	/**
	 * 商户/门店/商品标签ID
	 */
	private String itemId;
	/**
	 * 支付后积分数量
	 */
	private int point;
	/**
	 * 支付后积分总数量
	 */
	private int pointCount;
	/**
	 * 支付后积分类型
	 */
	private String pointType;
	
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
	public String getMinLimit() {
		return minLimit;
	}
	public void setMinLimit(String minLimit) {
		this.minLimit = minLimit;
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
	public String getRetMoney() {
		return retMoney;
	}
	public void setRetMoney(String retMoney) {
		this.retMoney = retMoney;
	}
	public String getMaxMoney() {
		return maxMoney;
	}
	public void setMaxMoney(String maxMoney) {
		this.maxMoney = maxMoney;
	}
	public Boolean getIsPerDay() {
		return isPerDay;
	}
	public void setIsPerDay(Boolean isPerDay) {
		this.isPerDay = isPerDay;
	}
	public Integer getPerDay() {
		return perDay;
	}
	public void setPerDay(Integer perDay) {
		this.perDay = perDay;
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
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public Boolean getIsPaperPay() {
		return isPaperPay;
	}
	public void setIsPaperPay(Boolean isPaperPay) {
		this.isPaperPay = isPaperPay;
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
	public String getPrePayActiveId() {
		return prePayActiveId;
	}
	public void setPrePayActiveId(String prePayActiveId) {
		this.prePayActiveId = prePayActiveId;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getPointCount() {
		return pointCount;
	}
	public void setPointCount(int pointCount) {
		this.pointCount = pointCount;
	}
	public String getPointType() {
		return pointType;
	}
	public void setPointType(String pointType) {
		this.pointType = pointType;
	}
	public String getPrePayActiveName() {
		return prePayActiveName;
	}
	public void setPrePayActiveName(String prePayActiveName) {
		this.prePayActiveName = prePayActiveName;
	}
	
}
