package com.froad.db.chonggou.entity;

import java.util.Date;
import java.util.List;



public class ProductViewInfo {
    
    private Long id;
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 商户ID
     */
    private String merchantId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商品对应商户所属机构编号
     */
    private String orgCode;
    /**
     * 商品对应商户所属机构名称
     */
    private String orgName;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 是否上架 0未上架,1已上架,2已下架,3已删除
     */
    private String isMarketable;
    /**
     * 上架时间
     */
    private Date rackTime;
    /**
     * 下架时间
     */
    private Date downTime;
    /**
     * createTime
     */
    private Date createTime;
    /**
     * 商品类型
     */
    private String type;
    /**
     * 1配送,2自提3配送或自提二者皆
     */
    private String deliveryOption;
    /**
     * 商品名
     */
    private String name;
    /**
     * 商品全名
     */
    private String fullName;
    /**
     * 销售价
     */
    private Integer price;
    /**
     * 成本价
     */
    private Integer cost;
    /**
     * 市场价
     */
    private Integer marketPrice;
    /**
     * 商品重量
     */
    private String weight;
    /**
     * 重量单位
     */
    private String weightUnit;
    /**
     * 商品库存数量
     */
    private Integer store;
    /**
     * 销售数量
     */
    private Integer sellCount;
    /**
     * 排序(1-10)
     */
    private Integer orderValue;
    /**
     * 是否为精品
     */
    private Boolean isBest;
    /**
     * 是否限购
     */
    private Boolean isLimit;
    /**
     * 评分
     */
    private Integer point;
    /**
     * 简介
     */
    private String briefIntroduction;
    /**
     * 介绍
     */
    private String introduction;
    /**
     * 商品须知
     */
    private String buyKnow;
    /**
     * 商品审核状态
     */
    private String auditState;
    /**
     * 待审核机构编号
     */
    private String auditOrgCode;
    /**
     * 起始审核机构编号
     */
    private String auditStartOrgCode;
    /**
     * 最终审核机编号
     */
    private String auditEndOrgCode;
    /**
     * 审核步骤
     */
    private String auditStage;
    /**
     * 复核人
     */
    private String reviewStaff;
    /**
     * 审核人
     */
    private String auditStaff;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 审核备注
     */
    private String auditComment;
    /**
     * 用户收藏数
     */
    private Integer storeCount;
    /**
     * 团购或预售有效期开始
     */
    private Date startTime;
    /**
     * 团购或预售有效期结束
     */
    private Date endTime;
    /**
     * 团购或预售券有效起始日
     */
    private Date expireStartTime;
    /**
     * 团购或预售券有效结束日
     */
    private Date expireEndTime;
    /**
     * 团购或预售真实购买数量
     */
    private Integer trueBuyerNumber;
    /**
     * 团购或预售虚拟购买数量
     */
    private Integer virtualBuyerNumber;
    /**
     * 供货商
     */
    private String productSupplier;
    /**
     * 运费
     */
    private Integer deliveryMoney;
    /**
     * 自提商品每家门店最大支持数量
     */
    private Integer maxPerOutlet;
    /**
     * 提货-开始
     */
    private Date deliveryStartTime;
    /**
     * 提货-结束
     */
    private Date deliveryEndTime;
    /**
     * 是否成功成团
     */
    private Boolean clusterState;
    /**
     * 成团类型
     */
    private Boolean clusterType;
    /**
     * 是否秒杀
     */
    private Boolean isSeckill;

    
    private ProductCategoryCG productCategory;
    private ProductBuyLimit buyLimit;
    private List<ProductImageCG> productImages;
    private List<ProductOutlet> productOutlets;
    
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
    public String getMerchantName() {
        return merchantName;
    }
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
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
    public Date getRackTime() {
        return rackTime;
    }
    public void setRackTime(Date rackTime) {
        this.rackTime = rackTime;
    }
    public Date getDownTime() {
        return downTime;
    }
    public void setDownTime(Date downTime) {
        this.downTime = downTime;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public Integer getCost() {
        return cost;
    }
    public void setCost(Integer cost) {
        this.cost = cost;
    }
    public Integer getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }
    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public String getWeightUnit() {
        return weightUnit;
    }
    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }
    public Integer getStore() {
        return store;
    }
    public void setStore(Integer store) {
        this.store = store;
    }
    public Integer getSellCount() {
        return sellCount;
    }
    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }
    public Integer getOrderValue() {
        return orderValue;
    }
    public void setOrderValue(Integer orderValue) {
        this.orderValue = orderValue;
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
    public Integer getPoint() {
        return point;
    }
    public void setPoint(Integer point) {
        this.point = point;
    }
    public String getBriefIntroduction() {
        return briefIntroduction;
    }
    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getBuyKnow() {
        return buyKnow;
    }
    public void setBuyKnow(String buyKnow) {
        this.buyKnow = buyKnow;
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
    public Integer getStoreCount() {
        return storeCount;
    }
    public void setStoreCount(Integer storeCount) {
        this.storeCount = storeCount;
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
    public Integer getTrueBuyerNumber() {
        return trueBuyerNumber;
    }
    public void setTrueBuyerNumber(Integer trueBuyerNumber) {
        this.trueBuyerNumber = trueBuyerNumber;
    }
    public Integer getVirtualBuyerNumber() {
        return virtualBuyerNumber;
    }
    public void setVirtualBuyerNumber(Integer virtualBuyerNumber) {
        this.virtualBuyerNumber = virtualBuyerNumber;
    }
    public String getProductSupplier() {
        return productSupplier;
    }
    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }
    public Integer getDeliveryMoney() {
        return deliveryMoney;
    }
    public void setDeliveryMoney(Integer deliveryMoney) {
        this.deliveryMoney = deliveryMoney;
    }
    public Integer getMaxPerOutlet() {
        return maxPerOutlet;
    }
    public void setMaxPerOutlet(Integer maxPerOutlet) {
        this.maxPerOutlet = maxPerOutlet;
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
    public Boolean getIsSeckill() {
        return isSeckill;
    }
    public void setIsSeckill(Boolean isSeckill) {
        this.isSeckill = isSeckill;
    }
    public ProductCategoryCG getProductCategory() {
        return productCategory;
    }
    public void setProductCategory(ProductCategoryCG productCategory) {
        this.productCategory = productCategory;
    }
    public ProductBuyLimit getBuyLimit() {
        return buyLimit;
    }
    public void setBuyLimit(ProductBuyLimit buyLimit) {
        this.buyLimit = buyLimit;
    }
    public List<ProductImageCG> getProductImages() {
        return productImages;
    }
    public void setProductImages(List<ProductImageCG> productImages) {
        this.productImages = productImages;
    }
    public List<ProductOutlet> getProductOutlets() {
        return productOutlets;
    }
    public void setProductOutlets(List<ProductOutlet> productOutlets) {
        this.productOutlets = productOutlets;
    }
    
}
