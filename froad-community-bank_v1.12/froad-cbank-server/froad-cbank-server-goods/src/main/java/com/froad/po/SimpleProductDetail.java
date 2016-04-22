package com.froad.po;

import java.util.Date;


public class SimpleProductDetail {

    private String id;
    private String type;         // 商品类型
    private String clientId;
    private String isMarketable;
    private String merchantId;          // 商户ID
    private String name;                // 商品名称
    private String fullName;  //商品全名
    private Integer price;                  // 价格
    private Integer marketPrice;
    private Integer sellCount;
    private Date startTime;
    private Date endTime;
    private Date rackTime;//上架时间
    private String isSeckill;// 是否秒杀 0非秒杀,1秒杀,2秒杀未上架
    private String briefIntroduction; // 简介
    private String smallImgUrl;//小图片
    private Long activitiesId;//送积分活动id
    private Integer vipPrice;//vip价格
    private String midImgUrl;//中图片
    private Integer isRecommend;//是否推荐 荐 
	private Integer isNew;//是否新品 热
	private Integer isHot;//是否热销  新
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getIsMarketable() {
        return isMarketable;
    }
    public void setIsMarketable(String isMarketable) {
        this.isMarketable = isMarketable;
    }
    public String getMerchantId() {
        return merchantId;
    }
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
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
    public Integer getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }
    public Integer getSellCount() {
        return sellCount;
    }
    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
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
    public Date getRackTime() {
        return rackTime;
    }
    public void setRackTime(Date rackTime) {
        this.rackTime = rackTime;
    }
    public String getIsSeckill() {
        return isSeckill;
    }
    public void setIsSeckill(String isSeckill) {
        this.isSeckill = isSeckill;
    }
    public String getBriefIntroduction() {
        return briefIntroduction;
    }
    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }
    public String getSmallImgUrl() {
        return smallImgUrl;
    }
    public void setSmallImgUrl(String smallImgUrl) {
        this.smallImgUrl = smallImgUrl;
    }
    
    public Long getActivitiesId() {
        return activitiesId;
    }
    public void setActivitiesId(Long activitiesId) {
        this.activitiesId = activitiesId;
    }
    
    public Integer getVipPrice() {
        return vipPrice;
    }
    public void setVipPrice(Integer vipPrice) {
        this.vipPrice = vipPrice;
    }
    
    public String getMidImgUrl() {
		return midImgUrl;
	}
	public void setMidImgUrl(String midImgUrl) {
		this.midImgUrl = midImgUrl;
	}
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	public Integer getIsHot() {
		return isHot;
	}
	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}
    
}
