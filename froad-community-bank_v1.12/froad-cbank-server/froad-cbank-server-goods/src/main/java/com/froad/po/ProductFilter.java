package com.froad.po;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public class ProductFilter {
    
    private String filterStatuts;
    private String clientId;
    private String merchantId;
    private String merchantName;
    private Long categoryId;
    private String orgCode;
    private String productId;
    private String isMarketable;
    private String presellNum;
    private Date startRackTime;
    private Date endRackTime;
    private Date startCreateTime;
    private Date endCreateTime;
    private String type;
    private String types;
    private String deliveryOption;
    private String name;
    private String fullName;
    private Integer priceStart;
    private Integer priceEnd;
    private Integer costStart;
    private Integer costEnd;
    private Integer marketPriceStart;
    private Integer marketPriceEnd;
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
    private Date startDownTime;
    private Date endDownTime;
    private String productSupplier;
    private Integer deliveryMoneyStart;
    private Integer deliveryMoneyEnd;
    private Integer maxPerOutletStart;
    private Integer maxPerOutletEnd;
    private Date startTimeStart;
    private Date startTimeEnd;
    private Date endTimeStart;
    private Date endTimeEnd;
    private Date deliveryStartTimeStart;
    private Date deliveryStartTimeEnd;
    private Date deliveryEndTimeStart;
    private Date deliveryEndTimeEnd;
    private Integer trueBuyerNumberStart;
    private Integer trueBuyerNumberEnd;
    private Integer virtualBuyerNumberStart;
    private Integer virtualBuyerNumberEnd;
    private Boolean clusterState;
    private Boolean clusterType;
    private Date expireStartTimeStart;
    private Date expireStartTimeEnd;
    private Date expireEndTimeStart;
    private Date expireEndTimeEnd;
    private Long areaId;//区id
    private String outletId;
    private String isSeckill;//0非秒杀,1秒杀,2秒杀未上架
    private String categoryTreePath;
    private String filterAuditState;
    private Long cityId;//市id
    private String areaCode;//区code
    
    private String proOrgCode;//一级机构
    private String cityOrgCode;//二级机构
    private String countryOrgCode;//三级机构
    
    private String orderField;//排序权重
    
    private Date auditStartTime; //审核开始时间
    private Date auditEndTime;  //审核结束时间
    
    /**
     * 不能查出的平台来源
     */
    private String excludePlatType;
    
    private String seoKeyWords;//搜索关键词
    private String productName;
     
    public String getExcludePlatType() {
        return excludePlatType;
    }
    public void setExcludePlatType(String excludePlatType) {
        this.excludePlatType = excludePlatType;
    }
    
    public Date getAuditStartTime() {
		return auditStartTime;
	}
	public void setAuditStartTime(Date auditStartTime) {
		this.auditStartTime = auditStartTime;
	}
	public Date getAuditEndTime() {
		return auditEndTime;
	}
	public void setAuditEndTime(Date auditEndTime) {
		this.auditEndTime = auditEndTime;
	}
	private LinkedHashMap<String, String> orderFileds;
    
    private List<String> typeList;
    
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
    public Date getStartRackTime() {
        return startRackTime;
    }
    public void setStartRackTime(Date startRackTime) {
        this.startRackTime = startRackTime;
    }
    public Date getEndRackTime() {
        return endRackTime;
    }
    public void setEndRackTime(Date endRackTime) {
        this.endRackTime = endRackTime;
    }
    public Date getStartCreateTime() {
        return startCreateTime;
    }
    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }
    public Date getEndCreateTime() {
        return endCreateTime;
    }
    public void setEndCreateTime(Date endCreateTime) {
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
    public Integer getPriceStart() {
        return priceStart;
    }
    public void setPriceStart(Integer priceStart) {
        this.priceStart = priceStart;
    }
    public Integer getPriceEnd() {
        return priceEnd;
    }
    public void setPriceEnd(Integer priceEnd) {
        this.priceEnd = priceEnd;
    }
    public Integer getCostStart() {
        return costStart;
    }
    public void setCostStart(Integer costStart) {
        this.costStart = costStart;
    }
    public Integer getCostEnd() {
        return costEnd;
    }
    public void setCostEnd(Integer costEnd) {
        this.costEnd = costEnd;
    }
    public Integer getMarketPriceStart() {
        return marketPriceStart;
    }
    public void setMarketPriceStart(Integer marketPriceStart) {
        this.marketPriceStart = marketPriceStart;
    }
    public Integer getMarketPriceEnd() {
        return marketPriceEnd;
    }
    public void setMarketPriceEnd(Integer marketPriceEnd) {
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
    public Date getStartDownTime() {
        return startDownTime;
    }
    public void setStartDownTime(Date startDownTime) {
        this.startDownTime = startDownTime;
    }
    public Date getEndDownTime() {
        return endDownTime;
    }
    public void setEndDownTime(Date endDownTime) {
        this.endDownTime = endDownTime;
    }
    public String getProductSupplier() {
        return productSupplier;
    }
    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }
    public Integer getDeliveryMoneyStart() {
        return deliveryMoneyStart;
    }
    public void setDeliveryMoneyStart(Integer deliveryMoneyStart) {
        this.deliveryMoneyStart = deliveryMoneyStart;
    }
    public Integer getDeliveryMoneyEnd() {
        return deliveryMoneyEnd;
    }
    public void setDeliveryMoneyEnd(Integer deliveryMoneyEnd) {
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
    public Date getStartTimeStart() {
        return startTimeStart;
    }
    public void setStartTimeStart(Date startTimeStart) {
        this.startTimeStart = startTimeStart;
    }
    public Date getStartTimeEnd() {
        return startTimeEnd;
    }
    public void setStartTimeEnd(Date startTimeEnd) {
        this.startTimeEnd = startTimeEnd;
    }
    public Date getEndTimeStart() {
        return endTimeStart;
    }
    public void setEndTimeStart(Date endTimeStart) {
        this.endTimeStart = endTimeStart;
    }
    public Date getEndTimeEnd() {
        return endTimeEnd;
    }
    public void setEndTimeEnd(Date endTimeEnd) {
        this.endTimeEnd = endTimeEnd;
    }
    public Date getDeliveryStartTimeStart() {
        return deliveryStartTimeStart;
    }
    public void setDeliveryStartTimeStart(Date deliveryStartTimeStart) {
        this.deliveryStartTimeStart = deliveryStartTimeStart;
    }
    public Date getDeliveryStartTimeEnd() {
        return deliveryStartTimeEnd;
    }
    public void setDeliveryStartTimeEnd(Date deliveryStartTimeEnd) {
        this.deliveryStartTimeEnd = deliveryStartTimeEnd;
    }
    public Date getDeliveryEndTimeStart() {
        return deliveryEndTimeStart;
    }
    public void setDeliveryEndTimeStart(Date deliveryEndTimeStart) {
        this.deliveryEndTimeStart = deliveryEndTimeStart;
    }
    public Date getDeliveryEndTimeEnd() {
        return deliveryEndTimeEnd;
    }
    public void setDeliveryEndTimeEnd(Date deliveryEndTimeEnd) {
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
    public Date getExpireStartTimeStart() {
        return expireStartTimeStart;
    }
    public void setExpireStartTimeStart(Date expireStartTimeStart) {
        this.expireStartTimeStart = expireStartTimeStart;
    }
    public Date getExpireStartTimeEnd() {
        return expireStartTimeEnd;
    }
    public void setExpireStartTimeEnd(Date expireStartTimeEnd) {
        this.expireStartTimeEnd = expireStartTimeEnd;
    }
    public Date getExpireEndTimeStart() {
        return expireEndTimeStart;
    }
    public void setExpireEndTimeStart(Date expireEndTimeStart) {
        this.expireEndTimeStart = expireEndTimeStart;
    }
    public Date getExpireEndTimeEnd() {
        return expireEndTimeEnd;
    }
    public void setExpireEndTimeEnd(Date expireEndTimeEnd) {
        this.expireEndTimeEnd = expireEndTimeEnd;
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
    
    public List<String> getTypeList() {
        return typeList;
    }
    public void setTypeList(List<String> typeList) {
        this.typeList = typeList;
    }
    
    public String getCategoryTreePath() {
        return categoryTreePath;
    }
    public void setCategoryTreePath(String categoryTreePath) {
        this.categoryTreePath = categoryTreePath;
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

    public String getSeoKeyWords() {
		return seoKeyWords;
	}
	public void setSeoKeyWords(String seoKeyWords) {
		this.seoKeyWords = seoKeyWords;
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
}
