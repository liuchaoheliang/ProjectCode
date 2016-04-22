package com.froad.po;

import java.util.Date;

/**
 * 
 * @author chenhao
 *
 */
public class ProductSeckill implements java.io.Serializable {

	// Fields

	/**
     * 
     */
    private static final long serialVersionUID = -7720174188947823158L;
    
    private Long id;
	private String clientId;
	private String merchantId;
	private Date createTime;
	private String productId;
	private String type;
	private Integer secStore;
	private Integer secPrice;
	private Integer vipSecPrice;
	private Date startTime;
	private Date endTime;
	private Integer trueBuyerNumber;
	private Integer buyLimit;
	private String isMarketable;
	private String auditStartOrgCode;
	private String auditEndOrgCode;
	private String auditState;
	private String auditOrgCode;
	private String auditStage;
	private String reviewStaff;
	private String auditStaff;
	private Date auditTime;
	private String auditComment;

	private String name;
	private String fullName;
	private Integer price;
	private Integer marketPrice;
	private Date deliveryStartTime;
	private Date deliveryEndTime;
    private String briefIntroduction;
    private String phone;
    private String deliveryOption;
    private String buyKnow;
    private String introduction;
    private String orgCode;
    private String orgName;
    private String status;
    private String merchantName;
    private Integer deliveryMoney;
    private Integer store;
	private String proOrgCode;//一级机构
	private String cityOrgCode;//二级机构
	private String countryOrgCode;//三级机构
    
    
    
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
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getSecStore() {
		return secStore;
	}
	public void setSecStore(Integer secStore) {
		this.secStore = secStore;
	}
	public Integer getSecPrice() {
		return secPrice;
	}
	public void setSecPrice(Integer secPrice) {
		this.secPrice = secPrice;
	}
	public Integer getVipSecPrice() {
		return vipSecPrice;
	}
	public void setVipSecPrice(Integer vipSecPrice) {
		this.vipSecPrice = vipSecPrice;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getTrueBuyerNumber() {
		return trueBuyerNumber;
	}
	public void setTrueBuyerNumber(Integer trueBuyerNumber) {
		this.trueBuyerNumber = trueBuyerNumber;
	}
	public Integer getBuyLimit() {
		return buyLimit;
	}
	public void setBuyLimit(Integer buyLimit) {
		this.buyLimit = buyLimit;
	}
	public String getIsMarketable() {
		return isMarketable;
	}
	public void setIsMarketable(String isMarketable) {
		this.isMarketable = isMarketable;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getAuditOrgCode() {
		return auditOrgCode;
	}
	public void setAuditOrgCode(String auditOrgCode) {
		this.auditOrgCode = auditOrgCode;
	}
	public String getAuditStage() {
		return auditStage;
	}
	public void setAuditStage(String auditStage) {
		this.auditStage = auditStage;
	}
	public String getReviewStaff() {
		return reviewStaff;
	}
	public void setReviewStaff(String reviewStaff) {
		this.reviewStaff = reviewStaff;
	}
	public String getAuditStaff() {
		return auditStaff;
	}
	public void setAuditStaff(String auditStaff) {
		this.auditStaff = auditStaff;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullname) {
		this.fullName = fullname;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Integer marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Date getDeliveryStartTime() {
		return deliveryStartTime;
	}
	public void setDeliveryStartTime(Date deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}
	public Date getDeliveryEndTime() {
		return deliveryEndTime;
	}
	public void setDeliveryEndTime(Date deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}
	public String getBriefIntroduction() {
		return briefIntroduction;
	}
	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDeliveryOption() {
		return deliveryOption;
	}
	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}
	public String getBuyKnow() {
		return buyKnow;
	}
	public void setBuyKnow(String buyKnow) {
		this.buyKnow = buyKnow;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getAuditStartOrgCode() {
		return auditStartOrgCode;
	}
	public void setAuditStartOrgCode(String auditStartOrgCode) {
		this.auditStartOrgCode = auditStartOrgCode;
	}
	public String getAuditEndOrgCode() {
		return auditEndOrgCode;
	}
	public void setAuditEndOrgCode(String auditEndOrgCode) {
		this.auditEndOrgCode = auditEndOrgCode;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditComment() {
		return auditComment;
	}
	public void setAuditComment(String auditComment) {
		this.auditComment = auditComment;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public Integer getDeliveryMoney() {
		return deliveryMoney;
	}
	public void setDeliveryMoney(Integer deliveryMoney) {
		this.deliveryMoney = deliveryMoney;
	}
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	public String getProOrgCode() {
		return proOrgCode;
	}
	public void setProOrgCode(String proOrgCode) {
		this.proOrgCode = proOrgCode;
	}
	public String getCityOrgCode() {
		return cityOrgCode;
	}
	public void setCityOrgCode(String cityOrgCode) {
		this.cityOrgCode = cityOrgCode;
	}
	public String getCountryOrgCode() {
		return countryOrgCode;
	}
	public void setCountryOrgCode(String countryOrgCode) {
		this.countryOrgCode = countryOrgCode;
	}
	
}