package com.froad.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ActiveDetailRule implements Serializable {
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
	 * 金额下限
	 */
	private Long minLimit;
	/**
	 * 活动奖励人次限制
	 */
	private Long maxCount;
	/**
	 * 每人时间间隔单位为时或者日, 0-是时 1-是日
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
	 * 全局时间间隔为时或者日, 0-时 1-日
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
	 * 是否支持代金券支付
	 */
	private Boolean isPaperPay;
	/**
	 * 是true-支付前 false-支付后
	 */
	private Boolean isPrePay;
	/**
	 * 减金额(is_pre_pay为true的时候有效)
	 */
	private Long retMoney;
	/**
	 * 满减金额总值(is_pre_pay为true的时候有效)
	 */
	private Long maxMoney;
	/**
	 * 支付后送优惠活动ID(is_pre_pay为true的时候有效)
	 */
	private String prePayActiveId;
	/**
	 * 支付后送商品ID
	 */
	private String productId;
	/**
	 * 支付后送商品总数限制
	 */
	private Integer productCount;
	/**
	 * 支付后送积分数量
	 */
	private Integer point;
	/**
	 * 支付后送积分类型
	 */
	private String pointType;
	/**
	 * 支付后送积分总数量
	 */
	private Integer pointCount;
	/**
	 * 支付方式限制类型，参考社区银行支付类型枚举, (is_pre_pay为true的时候有效)
	 */
	private String payMethod;

	public ActiveDetailRule() {
	}

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

	public String getPrePayActiveId() {
		return prePayActiveId;
	}

	public void setPrePayActiveId(String prePayActiveId) {
		this.prePayActiveId = prePayActiveId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getProductCount() {
		return productCount;
	}

	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}

	public String getPointType() {
		return pointType;
	}

	public void setPointType(String pointType) {
		this.pointType = pointType;
	}

	public Integer getPointCount() {
		return pointCount;
	}

	public void setPointCount(Integer pointCount) {
		this.pointCount = pointCount;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Long getMinLimit() {
		return minLimit;
	}

	public void setMinLimit(Long minLimit) {
		this.minLimit = minLimit;
	}

	public Long getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Long maxCount) {
		this.maxCount = maxCount;
	}

	public Long getRetMoney() {
		return retMoney;
	}

	public void setRetMoney(Long retMoney) {
		this.retMoney = retMoney;
	}

	public Long getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Long maxMoney) {
		this.maxMoney = maxMoney;
	}

	public Long getPerCount() {
		return perCount;
	}

	public void setPerCount(Long perCount) {
		this.perCount = perCount;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public ActiveDetailRule(Long id, String clientId, String activeId,
			Long minLimit, Long maxCount, Boolean isPerDay, Integer perDay,
			Long perCount, Boolean isTotalDay, Integer totalDay,
			Long totalCount, Boolean isPaperPay, Boolean isPrePay,
			Long retMoney, Long maxMoney, String prePayActiveId,
			String productId, Integer productCount, Integer point,
			String pointType, Integer pointCount, String payMethod) {
		super();
		this.id = id;
		this.clientId = clientId;
		this.activeId = activeId;
		this.minLimit = minLimit;
		this.maxCount = maxCount;
		this.isPerDay = isPerDay;
		this.perDay = perDay;
		this.perCount = perCount;
		this.isTotalDay = isTotalDay;
		this.totalDay = totalDay;
		this.totalCount = totalCount;
		this.isPaperPay = isPaperPay;
		this.isPrePay = isPrePay;
		this.retMoney = retMoney;
		this.maxMoney = maxMoney;
		this.prePayActiveId = prePayActiveId;
		this.productId = productId;
		this.productCount = productCount;
		this.point = point;
		this.pointType = pointType;
		this.pointCount = pointCount;
		this.payMethod = payMethod;
	}
	
	

}