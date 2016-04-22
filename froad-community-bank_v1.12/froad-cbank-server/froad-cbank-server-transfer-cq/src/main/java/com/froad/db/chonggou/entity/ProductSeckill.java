package com.froad.db.chonggou.entity;

import java.util.Date;
import java.util.List;



/**
 * 
 * @author wangyan
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
	private String auditState;
	private String auditOrgCode;
	private String auditStage;
	private String reviewStaff;
	private String auditStaff;

	private String name;
	private Integer price;
	private Integer marketPrice;
	private Date deliveryStartTime;
	private Date deliveryEndTime;
    private String briefIntroduction;
    private String phone;
    private String deliveryOption;
    private String buyKnow;
    private String introduction;
    
    
    
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
    
	
}