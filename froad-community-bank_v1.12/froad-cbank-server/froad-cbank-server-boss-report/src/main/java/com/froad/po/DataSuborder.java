package com.froad.po;

import java.util.Date;

public class DataSuborder {
    private Long id;

    private Integer reportDate;

    private Date reportBegDate;

    private Date reportEndDate;

    private Date createTime;

    private String orderFlag;

    private String orderStatus;
    
    private String orderType;

    private Date orderTime;

    private String clientId;

    private String orgCode1;

    private String orgName1;

    private String orgCode2;

    private String orgName2;

    private String orgCode3;

    private String orgName3;

    private String orderId;

    private String subOrderId;

    private Long memberCode;

    private String memberName;

    private String merchantId;

    private String merchantName;
    
    private String merchantStatus;

    private String merchantCategoryIds;

    private String merchantCategoryNames;

    private String productId;

    private String productName;

    private String productType;

    private Integer price;

    private Integer quantity;

    private Integer vipPrice;

    private Integer vipQuantity;

    private Integer deliveryMoney;

    private Integer totalAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getReportDate() {
        return reportDate;
    }

    public void setReportDate(Integer reportDate) {
        this.reportDate = reportDate;
    }

    public Date getReportBegDate() {
        return reportBegDate;
    }

    public void setReportBegDate(Date reportBegDate) {
        this.reportBegDate = reportBegDate;
    }

    public Date getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(Date reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(String orderFlag) {
        this.orderFlag = orderFlag == null ? null : orderFlag.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getOrgCode1() {
        return orgCode1;
    }

    public void setOrgCode1(String orgCode1) {
        this.orgCode1 = orgCode1 == null ? null : orgCode1.trim();
    }

    public String getOrgName1() {
        return orgName1;
    }

    public void setOrgName1(String orgName1) {
        this.orgName1 = orgName1 == null ? null : orgName1.trim();
    }

    public String getOrgCode2() {
        return orgCode2;
    }

    public void setOrgCode2(String orgCode2) {
        this.orgCode2 = orgCode2 == null ? null : orgCode2.trim();
    }

    public String getOrgName2() {
        return orgName2;
    }

    public void setOrgName2(String orgName2) {
        this.orgName2 = orgName2 == null ? null : orgName2.trim();
    }

    public String getOrgCode3() {
        return orgCode3;
    }

    public void setOrgCode3(String orgCode3) {
        this.orgCode3 = orgCode3 == null ? null : orgCode3.trim();
    }

    public String getOrgName3() {
        return orgName3;
    }

    public void setOrgName3(String orgName3) {
        this.orgName3 = orgName3 == null ? null : orgName3.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(String subOrderId) {
        this.subOrderId = subOrderId == null ? null : subOrderId.trim();
    }

    public Long getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(Long memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getMerchantStatus() {
		return merchantStatus;
	}

	public void setMerchantStatus(String merchantStatus) {
		this.merchantStatus = merchantStatus;
	}

	public String getMerchantCategoryIds() {
        return merchantCategoryIds;
    }

    public void setMerchantCategoryIds(String merchantCategoryIds) {
        this.merchantCategoryIds = merchantCategoryIds;
    }

    public String getMerchantCategoryNames() {
        return merchantCategoryNames;
    }

    public void setMerchantCategoryNames(String merchantCategoryNames) {
        this.merchantCategoryNames = merchantCategoryNames == null ? null : merchantCategoryNames.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType == null ? null : productType.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Integer vipPrice) {
        this.vipPrice = vipPrice;
    }

    public Integer getVipQuantity() {
        return vipQuantity;
    }

    public void setVipQuantity(Integer vipQuantity) {
        this.vipQuantity = vipQuantity;
    }

    public Integer getDeliveryMoney() {
        return deliveryMoney;
    }

    public void setDeliveryMoney(Integer deliveryMoney) {
        this.deliveryMoney = deliveryMoney;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
}