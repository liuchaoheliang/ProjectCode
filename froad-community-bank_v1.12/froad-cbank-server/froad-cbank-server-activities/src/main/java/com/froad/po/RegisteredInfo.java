package com.froad.po;

import java.io.Serializable;
import java.util.Date;

public class RegisteredInfo implements Serializable{
	/**
	 * @Fields serialVersionUID : TODO
	 */	
	private static final long serialVersionUID = 1L;
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
	
	 /**
     * 项⽬类型 0-商户1-门店(预留) 2-商品
    */
    private String itemType;
    /**
     * 商户/门店/商品标签ID
    */
    private String itemId;
    
    private Boolean triggerType;
	private String awardType;
	private Long limitMoney;
	private Long cutMoney;
	private Long totalMoney;
	private String vouchersActiveId;
	private String productId;
	private Integer productCount;
	private Integer awardCount;
	private Boolean isTotalDay;
	private Integer totalDay;
	private Integer totalCount;
	private Boolean isAwardCre;
	private Boolean creAwardType;
	private String creVouchersActiveId;
	private String creProductId;
	private Boolean isLimitCreCount;
	private Integer creAwardCount;
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
	public Boolean getTriggerType() {
		return triggerType;
	}
	public void setTriggerType(Boolean triggerType) {
		this.triggerType = triggerType;
	}
	public String getAwardType() {
		return awardType;
	}
	public void setAwardType(String awardType) {
		this.awardType = awardType;
	}
	public Long getLimitMoney() {
		return limitMoney;
	}
	public void setLimitMoney(Long limitMoney) {
		this.limitMoney = limitMoney;
	}
	public Long getCutMoney() {
		return cutMoney;
	}
	public void setCutMoney(Long cutMoney) {
		this.cutMoney = cutMoney;
	}
	public String getVouchersActiveId() {
		return vouchersActiveId;
	}
	public void setVouchersActiveId(String vouchersActiveId) {
		this.vouchersActiveId = vouchersActiveId;
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
	public Integer getAwardCount() {
		return awardCount;
	}
	public void setAwardCount(Integer awardCount) {
		this.awardCount = awardCount;
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
	public Boolean getIsAwardCre() {
		return isAwardCre;
	}
	public void setIsAwardCre(Boolean isAwardCre) {
		this.isAwardCre = isAwardCre;
	}
	public Boolean getCreAwardType() {
		return creAwardType;
	}
	public void setCreAwardType(Boolean creAwardType) {
		this.creAwardType = creAwardType;
	}
	public String getCreVouchersActiveId() {
		return creVouchersActiveId;
	}
	public void setCreVouchersActiveId(String creVouchersActiveId) {
		this.creVouchersActiveId = creVouchersActiveId;
	}
	public String getCreProductId() {
		return creProductId;
	}
	public void setCreProductId(String creProductId) {
		this.creProductId = creProductId;
	}
	public Boolean getIsLimitCreCount() {
		return isLimitCreCount;
	}
	public void setIsLimitCreCount(Boolean isLimitCreCount) {
		this.isLimitCreCount = isLimitCreCount;
	}
	public Integer getCreAwardCount() {
		return creAwardCount;
	}
	public void setCreAwardCount(Integer creAwardCount) {
		this.creAwardCount = creAwardCount;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Long getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}
	
	
}
