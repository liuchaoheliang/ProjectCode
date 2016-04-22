package com.froad.po;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ActiveRuleInfoDetail implements Serializable {
	private String clientId;
	private String activeId;
	private String type;
	private String status;
	private Date createTime;
	private Date updateTime;
	private String activeName;
	private String activeLogo;
	private Date expireStartTime;
	private Date expireEndTime;
	private String limitType;
	private Integer merchantRate;
	private Integer bankRate;
	private Integer fftRate;
	private String settleType;
	private String description;
	private String operator;
	private Long minLimit;
	private Long maxCount;
	private Boolean isPerDay;
	private Integer perDay;
	private Long perCount;
	private Boolean isTotalDay;
	private Integer totalDay;
	private Long totalCount;
	private Boolean isPaperPay;
	private Boolean isPrePay;
	private Long retMoney;
	private Long maxMoney;
	private String prePayActiveId;
	private String productId;
	private Integer productCount;
	private Integer point;
	private String pointType;
	private Integer pointCount;
	private String payMethod;
	private String itemType;
	private String itemId;

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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
