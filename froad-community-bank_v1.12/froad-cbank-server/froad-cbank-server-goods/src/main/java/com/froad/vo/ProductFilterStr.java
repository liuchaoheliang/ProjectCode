package com.froad.vo;

import java.util.LinkedHashMap;

public class ProductFilterStr {

	private String filterStatuts;
    private String clientId;
    private String merchantId;
    private String merchantName;
    private Long categoryId;
    private String orgCode;
    private String productId;
    private String isMarketable;
    private String presellNum;
    private Long startRackTime;
    private Long endRackTime;
    private Long startCreateTime;
    private Long endCreateTime;
    private String type;
    private String types;
    private String deliveryOption;
    private String name;
    private String fullName;
    private Double priceStart;
    private Double priceEnd;
    private Double costStart;
    private Double costEnd;
    private Double marketPriceStart;
    private Double marketPriceEnd;
    private Integer storeStart;
    private Integer storeEnd;
    private Boolean isBest;
    private Boolean isLimit;
    private String isHot;
    private Integer pointStart;
    private Integer pointEnd;
    private String auditState;
    private String auditOrgCode;
    private String auditStage;
    private String reviewStaff;
    private String auditStaff;
    private Integer orderValue;
    private Integer storeCountStart;
    private Integer storeCountEnd;
    private Integer sellCountStart;
    private Integer sellCountEnd;
    private Long startDownTime;
    private Long endDownTime;
    private String productSupplier;
    private Double deliveryMoneyStart;
    private Double deliveryMoneyEnd;
    private Integer maxPerOutletStart;
    private Integer maxPerOutletEnd;
    private Long startTimeStart;
    private Long startTimeEnd;
    private Long endTimeStart;
    private Long endTimeEnd;
    private Long deliveryStartTimeStart;
    private Long deliveryStartTimeEnd;
    private Long deliveryEndTimeStart;
    private Long deliveryEndTimeEnd;
    private Integer trueBuyerNumberStart;
    private Integer trueBuyerNumberEnd;
    private Integer virtualBuyerNumberStart;
    private Integer virtualBuyerNumberEnd;
    private Boolean clusterState;
    private Boolean clusterType;
    private Long expireStartTimeStart;
    private Long expireStartTimeEnd;
    private Long expireEndTimeStart;
    private Long expireEndTimeEnd;
    private Long areaId;
    private String outletId;
    private String isSeckill;
    private String filterAuditState;
    private Long cityId;//市id
    private String areaCode;//区code
    private Long auditStartTime; //审核开始时间
    private Long auditEndTime;  //审核结束时间
    
    public Long getAuditStartTime() {
		return auditStartTime;
	}
	public void setAuditStartTime(Long auditStartTime) {
		this.auditStartTime = auditStartTime;
	}
	public Long getAuditEndTime() {
		return auditEndTime;
	}
	public void setAuditEndTime(Long auditEndTime) {
		this.auditEndTime = auditEndTime;
	}
	/**
     * 排序权重
     */
    private String orderField;
    private LinkedHashMap<String, String> orderFileds;
    
    public String getFilterStatuts() {
        return filterStatuts;
    }
    public void setFilterStatuts(String filterStatuts) {
        this.filterStatuts = filterStatuts;
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
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getIsMarketable() {
        return isMarketable;
    }
    public void setIsMarketable(String isMarketable) {
        this.isMarketable = isMarketable;
    }
    public String getPresellNum() {
        return presellNum;
    }
    public void setPresellNum(String presellNum) {
        this.presellNum = presellNum;
    }
    public Long getStartRackTime() {
        return startRackTime;
    }
    public void setStartRackTime(Long startRackTime) {
        this.startRackTime = startRackTime;
    }
    public Long getEndRackTime() {
        return endRackTime;
    }
    public void setEndRackTime(Long endRackTime) {
        this.endRackTime = endRackTime;
    }
    public Long getStartCreateTime() {
        return startCreateTime;
    }
    public void setStartCreateTime(Long startCreateTime) {
        this.startCreateTime = startCreateTime;
    }
    public Long getEndCreateTime() {
        return endCreateTime;
    }
    public void setEndCreateTime(Long endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getTypes() {
        return types;
    }
    public void setTypes(String types) {
        this.types = types;
    }
    public String getDeliveryOption() {
        return deliveryOption;
    }
    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
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
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Double getPriceStart() {
        return priceStart;
    }
    public void setPriceStart(Double priceStart) {
        this.priceStart = priceStart;
    }
    public Double getPriceEnd() {
        return priceEnd;
    }
    public void setPriceEnd(Double priceEnd) {
        this.priceEnd = priceEnd;
    }
    public Double getCostStart() {
        return costStart;
    }
    public void setCostStart(Double costStart) {
        this.costStart = costStart;
    }
    public Double getCostEnd() {
        return costEnd;
    }
    public void setCostEnd(Double costEnd) {
        this.costEnd = costEnd;
    }
    public Double getMarketPriceStart() {
        return marketPriceStart;
    }
    public void setMarketPriceStart(Double marketPriceStart) {
        this.marketPriceStart = marketPriceStart;
    }
    public Double getMarketPriceEnd() {
        return marketPriceEnd;
    }
    public void setMarketPriceEnd(Double marketPriceEnd) {
        this.marketPriceEnd = marketPriceEnd;
    }
    public Integer getStoreStart() {
        return storeStart;
    }
    public void setStoreStart(Integer storeStart) {
        this.storeStart = storeStart;
    }
    public Integer getStoreEnd() {
        return storeEnd;
    }
    public void setStoreEnd(Integer storeEnd) {
        this.storeEnd = storeEnd;
    }
    public Boolean getIsBest() {
        return isBest;
    }
    public void setIsBest(Boolean isBest) {
        this.isBest = isBest;
    }
    public Boolean getIsLimit() {
        return isLimit;
    }
    public void setIsLimit(Boolean isLimit) {
        this.isLimit = isLimit;
    }
    public String getIsHot() {
        return isHot;
    }
    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }
    public Integer getPointStart() {
        return pointStart;
    }
    public void setPointStart(Integer pointStart) {
        this.pointStart = pointStart;
    }
    public Integer getPointEnd() {
        return pointEnd;
    }
    public void setPointEnd(Integer pointEnd) {
        this.pointEnd = pointEnd;
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
    public Integer getOrderValue() {
        return orderValue;
    }
    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
    }
    public Integer getStoreCountStart() {
        return storeCountStart;
    }
    public void setStoreCountStart(Integer storeCountStart) {
        this.storeCountStart = storeCountStart;
    }
    public Integer getStoreCountEnd() {
        return storeCountEnd;
    }
    public void setStoreCountEnd(Integer storeCountEnd) {
        this.storeCountEnd = storeCountEnd;
    }
    public Integer getSellCountStart() {
        return sellCountStart;
    }
    public void setSellCountStart(Integer sellCountStart) {
        this.sellCountStart = sellCountStart;
    }
    public Integer getSellCountEnd() {
        return sellCountEnd;
    }
    public void setSellCountEnd(Integer sellCountEnd) {
        this.sellCountEnd = sellCountEnd;
    }
    public Long getStartDownTime() {
        return startDownTime;
    }
    public void setStartDownTime(Long startDownTime) {
        this.startDownTime = startDownTime;
    }
    public Long getEndDownTime() {
        return endDownTime;
    }
    public void setEndDownTime(Long endDownTime) {
        this.endDownTime = endDownTime;
    }
    public String getProductSupplier() {
        return productSupplier;
    }
    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }
    public Double getDeliveryMoneyStart() {
        return deliveryMoneyStart;
    }
    public void setDeliveryMoneyStart(Double deliveryMoneyStart) {
        this.deliveryMoneyStart = deliveryMoneyStart;
    }
    public Double getDeliveryMoneyEnd() {
        return deliveryMoneyEnd;
    }
    public void setDeliveryMoneyEnd(Double deliveryMoneyEnd) {
        this.deliveryMoneyEnd = deliveryMoneyEnd;
    }
    public Integer getMaxPerOutletStart() {
        return maxPerOutletStart;
    }
    public void setMaxPerOutletStart(Integer maxPerOutletStart) {
        this.maxPerOutletStart = maxPerOutletStart;
    }
    public Integer getMaxPerOutletEnd() {
        return maxPerOutletEnd;
    }
    public void setMaxPerOutletEnd(Integer maxPerOutletEnd) {
        this.maxPerOutletEnd = maxPerOutletEnd;
    }
    public Long getStartTimeStart() {
        return startTimeStart;
    }
    public void setStartTimeStart(Long startTimeStart) {
        this.startTimeStart = startTimeStart;
    }
    public Long getStartTimeEnd() {
        return startTimeEnd;
    }
    public void setStartTimeEnd(Long startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }
    public Long getEndTimeStart() {
        return endTimeStart;
    }
    public void setEndTimeStart(Long endTimeStart) {
        this.endTimeStart = endTimeStart;
    }
    public Long getEndTimeEnd() {
        return endTimeEnd;
    }
    public void setEndTimeEnd(Long endTimeEnd) {
        this.endTimeEnd = endTimeEnd;
    }
    public Long getDeliveryStartTimeStart() {
        return deliveryStartTimeStart;
    }
    public void setDeliveryStartTimeStart(Long deliveryStartTimeStart) {
        this.deliveryStartTimeStart = deliveryStartTimeStart;
    }
    public Long getDeliveryStartTimeEnd() {
        return deliveryStartTimeEnd;
    }
    public void setDeliveryStartTimeEnd(Long deliveryStartTimeEnd) {
        this.deliveryStartTimeEnd = deliveryStartTimeEnd;
    }
    public Long getDeliveryEndTimeStart() {
        return deliveryEndTimeStart;
    }
    public void setDeliveryEndTimeStart(Long deliveryEndTimeStart) {
        this.deliveryEndTimeStart = deliveryEndTimeStart;
    }
    public Long getDeliveryEndTimeEnd() {
        return deliveryEndTimeEnd;
    }
    public void setDeliveryEndTimeEnd(Long deliveryEndTimeEnd) {
        this.deliveryEndTimeEnd = deliveryEndTimeEnd;
    }
    public Integer getTrueBuyerNumberStart() {
        return trueBuyerNumberStart;
    }
    public void setTrueBuyerNumberStart(Integer trueBuyerNumberStart) {
        this.trueBuyerNumberStart = trueBuyerNumberStart;
    }
    public Integer getTrueBuyerNumberEnd() {
        return trueBuyerNumberEnd;
    }
    public void setTrueBuyerNumberEnd(Integer trueBuyerNumberEnd) {
        this.trueBuyerNumberEnd = trueBuyerNumberEnd;
    }
    public Integer getVirtualBuyerNumberStart() {
        return virtualBuyerNumberStart;
    }
    public void setVirtualBuyerNumberStart(Integer virtualBuyerNumberStart) {
        this.virtualBuyerNumberStart = virtualBuyerNumberStart;
    }
    public Integer getVirtualBuyerNumberEnd() {
        return virtualBuyerNumberEnd;
    }
    public void setVirtualBuyerNumberEnd(Integer virtualBuyerNumberEnd) {
        this.virtualBuyerNumberEnd = virtualBuyerNumberEnd;
    }
    public Boolean getClusterState() {
        return clusterState;
    }
    public void setClusterState(Boolean clusterState) {
        this.clusterState = clusterState;
    }
    public Boolean getClusterType() {
        return clusterType;
    }
    public void setClusterType(Boolean clusterType) {
        this.clusterType = clusterType;
    }
    public Long getExpireStartTimeStart() {
        return expireStartTimeStart;
    }
    public void setExpireStartTimeStart(Long expireStartTimeStart) {
        this.expireStartTimeStart = expireStartTimeStart;
    }
    public Long getExpireStartTimeEnd() {
        return expireStartTimeEnd;
    }
    public void setExpireStartTimeEnd(Long expireStartTimeEnd) {
        this.expireStartTimeEnd = expireStartTimeEnd;
    }
    public Long getExpireEndTimeStart() {
        return expireEndTimeStart;
    }
    public void setExpireEndTimeStart(Long expireEndTimeStart) {
        this.expireEndTimeStart = expireEndTimeStart;
    }
    public Long getExpireEndTimeEnd() {
        return expireEndTimeEnd;
    }
    public void setExpireEndTimeEnd(Long expireEndTimeEnd) {
        this.expireEndTimeEnd = expireEndTimeEnd;
    }
    public Long getAreaId() {
        return areaId;
    }
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
    public String getOutletId() {
        return outletId;
    }
    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }
    public String getIsSeckill() {
        return isSeckill;
    }
    public void setIsSeckill(String isSeckill) {
        this.isSeckill = isSeckill;
    }
    public String getOrderField() {
        return orderField;
    }
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }
    public LinkedHashMap<String, String> getOrderFileds() {
        return orderFileds;
    }
    public void setOrderFileds(LinkedHashMap<String, String> orderFileds) {
        this.orderFileds = orderFileds;
    }
    
    public String getFilterAuditState() {
        return filterAuditState;
    }
    public void setFilterAuditState(String filterAuditState) {
        this.filterAuditState = filterAuditState;
    }
    
    public Long getCityId() {
        return cityId;
    }
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
    
    public String getAreaCode() {
        return areaCode;
    }
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
}
