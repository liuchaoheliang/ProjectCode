package com.froad.po;

import java.io.Serializable;
import java.util.Date;

public class Vouchers implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO
	 */	
	private static final long serialVersionUID = 1L;
	
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
	
	 /**
     * 项⽬类型 0-商户1-门店(预留) 2-商品
    */
    private String itemType;
    /**
     * 商户/门店/商品标签ID
    */
    private String itemId;
    
    /**
	 * 代金券最小金额
	 */
	private Double minMoney;
	/**
	 * 代金券最大金额
	 */
	private Double maxMoney;
	/**
	 * 代金券总金额
	 */
	private Double totalMoney;
	/**
	 * 时间段限制单位0小时1天
	 */
	private Boolean isTotalDay;
	/**
	 * 时间段限制数量
	 */
	private Integer totalDay;
	/**
	 * 时间段限制次数
	 */
	private Integer totalCount;
	/**
	 * 订单最小限额-才能使用
	 */
	private Double orderMinMoney;
	/**
	 * 每人限购-时间段单位
	 */
	private Boolean isPerDay;
	/**
	 * 每人限购-时间段数量
	 */
	private Integer perDay;
	/**
	 * 每人限购-数量
	 */
	private Integer perCount;
	/**
	 * 是否可以和其它红包重复使用
	 */
	private Boolean isRepeat;
	/**
	 * 是否支持参与其它促销
	 */
	private Boolean isOtherActive;
	/**
	 * 0-⽀付前 1-⽀付后
	 */
	private Boolean isPrePay;
	/**
	 * 是否支持面对面
	 */
	private Boolean isFtof;
	/**
	 * ⽀付⽅式限制类型
	 */
	private String payMethod;
	
	private Boolean isOnlyNewUsers;
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
	
	public Double getOrderMinMoney() {
		return orderMinMoney;
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
	
	
	public Double getMinMoney() {
		return minMoney;
	}
	public void setMinMoney(Double minMoney) {
		this.minMoney = minMoney;
	}
	public Double getMaxMoney() {
		return maxMoney;
	}
	public void setMaxMoney(Double maxMoney) {
		this.maxMoney = maxMoney;
	}
	public Double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public void setOrderMinMoney(Double orderMinMoney) {
		this.orderMinMoney = orderMinMoney;
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
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
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
	public Integer getPerCount() {
		return perCount;
	}
	public void setPerCount(Integer perCount) {
		this.perCount = perCount;
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
	public Boolean getIsPrePay() {
		return isPrePay;
	}
	public void setIsPrePay(Boolean isPrePay) {
		this.isPrePay = isPrePay;
	}
	public Boolean getIsFtof() {
		return isFtof;
	}
	public void setIsFtof(Boolean isFtof) {
		this.isFtof = isFtof;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public Boolean getIsOnlyNewUsers() {
		return isOnlyNewUsers;
	}
	public void setIsOnlyNewUsers(Boolean isOnlyNewUsers) {
		this.isOnlyNewUsers = isOnlyNewUsers;
	}
	
	
}
