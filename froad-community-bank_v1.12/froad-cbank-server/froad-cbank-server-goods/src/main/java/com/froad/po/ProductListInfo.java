package com.froad.po;

import java.util.Date;

public class ProductListInfo {

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
     * 销售价
     */
    private Integer price;
    /**
     * 市场价
     */
    private Integer marketPrice;
    /**
     * 商品库存数量
     */
    private Integer store;
    /**
     * 商品审核状态
     */
    private String auditState;
    /**
     * 销售有效期开始
     */
    private Date startTime;
    /**
     * 销售有效期结束
     */
    private Date endTime;
    /**
     * 提货-开始
     */
    private Date deliveryStartTime;
    /**
     * 提货-结束
     */
    private Date deliveryEndTime;
    /**
     * 团购或预售券有效起始日
     */
    private Date expireStartTime;
    /**
     * 团购或预售券有效结束日
     */
    private Date expireEndTime;
    /**
     * 是否秒杀
     */
    private String isSeckill;//0非秒杀,1秒杀,2秒杀未上架
    /**
     * 商品分类路径
     */
    private String categoryTreePath;
    /**
     * 商品分类名称
     */
    private String categoryName;
    /**
     * 审核备注
     */
    private String auditComment;
    /**
     * 上架时间
     */
    private Date rackTime;
    /**
     * 审核时间
     */
    private Date auditTime;
    /**
     * 审核人
     */
    private String auditStaff;
    
    private String fullName;
    private Integer vipPrice;
    
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
    
    public Integer getStore() {
        return store;
    }
    public void setStore(Integer store) {
        this.store = store;
    }
    
    public String getAuditState() {
        return auditState;
    }
    public void setAuditState(String auditState) {
        this.auditState = auditState;
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
    
    public String getIsSeckill() {
        return isSeckill;
    }
    public void setIsSeckill(String isSeckill) {
        this.isSeckill = isSeckill;
    }
    
    public String getCategoryTreePath() {
        return categoryTreePath;
    }
    public void setCategoryTreePath(String categoryTreePath) {
        this.categoryTreePath = categoryTreePath;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getAuditComment() {
        return auditComment;
    }
    public void setAuditComment(String auditComment) {
        this.auditComment = auditComment;
    }
    
    public Date getRackTime() {
        return rackTime;
    }
    public void setRackTime(Date rackTime) {
        this.rackTime = rackTime;
    }
    
    public Date getAuditTime() {
        return auditTime;
    }
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
    
    public String getAuditStaff() {
        return auditStaff;
    }
    public void setAuditStaff(String auditStaff) {
        this.auditStaff = auditStaff;
    }
    
    public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Integer getVipPrice() {
		return vipPrice;
	}
	public void setVipPrice(Integer vipPrice) {
		this.vipPrice = vipPrice;
	}
    
}
