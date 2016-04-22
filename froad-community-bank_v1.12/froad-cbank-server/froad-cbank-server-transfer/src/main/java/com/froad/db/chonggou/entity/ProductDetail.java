package com.froad.db.chonggou.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;



/**
 *  商品详情
  * @ClassName: ProductDetail
  * @Description: TODO
  * @author share 2015年3月21日
  * @modify share 2015年3月21日
 */
public class ProductDetail implements Serializable{
	
	/**
	  * @Fields serialVersionUID : TODO
	*/
	
	private static final long serialVersionUID = 3819183057210402340L;
	/**
     * 
     */
    
    private String id;
	private Integer isBest;			// 是否精品
	private Integer isLimit;			// 是否限购
	private String name;				// 商品名称
	private String productType;			// 商品类型 0-团购 1-预售
	private Integer price;					// 价格
	
	private Integer marketPrice;
	private String clientId;
	private String isMarketable;
	private String deliveryOption;
	private Integer sellCount;
	private Date startTime;
	private Date endTime;
	private String isSeckill;// 是否秒杀
	private String fullName;  //商品全名
	private String briefIntroduction; // 简介
	private Integer storeCount; //用户收藏数
	private Date rackTime;//上架时间
	private String merchantId;			// 商户ID
	private String merchantName;//（商户名称）
	private List<ProductActivitiesInfo> activitiesInfo;//商品活动
	private List<ProductOutlet> outletInfo;// 门店详情
	private List<ProductImageCG> imageInfo;// 图片详情
	private List<ProductCategoryInfoCG> productCategoryInfo;// 商品分类
	private ProductBuyLimit buyLimit;		// 限购信息
	private List<String> orgCodes;//商品对应网店所属的机构代码列表
	private Long provinceId;//团购商品所属商户对应的区域对应的省id 
	private Long cityId;//团购商品所属商户对应的区域对应的市id
	private Long areaId;//团购商品所属商户对应的区域
	private List<ProductCityArea> cityAreas;//市级-区关系
    private List<ProductCityOutlet> orgOutlets;//市级-门店关系
    private Date createTime;//创建时间
   
	@JSONField(name="_id")
    public String getId() {
        return id;
    }
	
	@JSONField(name="_id")
    public void setId(String id) {
        this.id = id;
    }
	
    @JSONField(name="is_best")
    public Integer getIsBest() {
        return isBest;
    }
    
    @JSONField(name="is_best")
    public void setIsBest(Integer isBest) {
        this.isBest = isBest;
    }
    
    @JSONField(name="is_limit")
    public Integer getIsLimit() {
        return isLimit;
    }
    
    @JSONField(name="is_limit")
    public void setIsLimit(Integer isLimit) {
        this.isLimit = isLimit;
    }
    
    @JSONField(name="name")
    public String getName() {
        return name;
    }
    
    @JSONField(name="name")
    public void setName(String name) {
        this.name = name;
    }
    
    @JSONField(name="product_type")
    public String getProductType() {
        return productType;
    }
    
    @JSONField(name="product_type")
    public void setProductType(String productType) {
        this.productType = productType;
    }
    
    @JSONField(name="price")
    public Integer getPrice() {
        return price;
    }
    
    @JSONField(name="price")
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    @JSONField(name="market_price")
    public Integer getMarketPrice() {
        return marketPrice;
    }
    
    @JSONField(name="market_price")
    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }
    
    @JSONField(name="client_id")
    public String getClientId() {
        return clientId;
    }
    
    @JSONField(name="client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    @JSONField(name="is_marketable")
    public String getIsMarketable() {
        return isMarketable;
    }
    
    @JSONField(name="is_marketable")
    public void setIsMarketable(String isMarketable) {
        this.isMarketable = isMarketable;
    }
    
    @JSONField(name="delivery_option")
    public String getDeliveryOption() {
        return deliveryOption;
    }
    
    @JSONField(name="delivery_option")
    public void setDeliveryOption(String deliveryOption) {
        this.deliveryOption = deliveryOption;
    }
    
    @JSONField(name="sell_count")
    public Integer getSellCount() {
        return sellCount;
    }
    
    @JSONField(name="sell_count")
    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }
    
    @JSONField(name="start_time")
    public Date getStartTime() {
        return startTime;
    }
    
    @JSONField(name="start_time")
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    @JSONField(name="end_time")
    public Date getEndTime() {
        return endTime;
    }
    
    @JSONField(name="end_time")
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    @JSONField(name="is_seckill")
    public String getIsSeckill() {
        return isSeckill;
    }

    @JSONField(name="is_seckill")
    public void setIsSeckill(String isSeckill) {
        this.isSeckill = isSeckill;
    }
    
    @JSONField(name="full_name")
    public String getFullName() {
        return fullName;
    }

    @JSONField(name="full_name")
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @JSONField(name="brief_introduction")
    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    @JSONField(name="brief_introduction")
    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }
    
    @JSONField(name="merchant_id")
    public String getMerchantId() {
        return merchantId;
    }
    
    @JSONField(name="merchant_id")
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    
    @JSONField(name="merchant_name")
    public String getMerchantName() {
        return merchantName;
    }
    
    @JSONField(name="merchant_name")
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    
    @JSONField(name="activities_info")
    public List<ProductActivitiesInfo> getActivitiesInfo() {
        return activitiesInfo;
    }

    @JSONField(name="activities_info")
    public void setActivitiesInfo(List<ProductActivitiesInfo> activitiesInfo) {
        this.activitiesInfo = activitiesInfo;
    }
    
//  @JSONField(name="outlet_info")
  public List<ProductOutlet> getOutletInfo() {
      return outletInfo;
  }
  
//  @JSONField(name="outlet_info")
  public void setOutletInfo(List<ProductOutlet> outletInfo) {
      this.outletInfo = outletInfo;
  }
  
  @JSONField(name="city_areas")
  public List<ProductCityArea> getCityAreas() {
      return cityAreas;
  }
  
  @JSONField(name="city_areas")
  public void setCityAreas(List<ProductCityArea> cityAreas) {
      this.cityAreas = cityAreas;
  }
  
  @JSONField(name="org_outlets")
  public List<ProductCityOutlet> getOrgOutlets() {
      return orgOutlets;
  }
  
  @JSONField(name="org_outlets")
  public void setOrgOutlets(List<ProductCityOutlet> orgOutlets) {
      this.orgOutlets = orgOutlets;
  }
    
    @JSONField(name="image_info")
    public List<ProductImageCG> getImageInfo() {
        return imageInfo;
    }
    
    @JSONField(name="image_info")
    public void setImageInfo(List<ProductImageCG> imageInfo) {
        this.imageInfo = imageInfo;
    }
    
    @JSONField(name="product_category_info")
    public List<ProductCategoryInfoCG> getProductCategoryInfo() {
        return productCategoryInfo;
    }
    
    @JSONField(name="product_category_info")
    public void setProductCategoryInfo(List<ProductCategoryInfoCG> productCategoryInfo) {
        this.productCategoryInfo = productCategoryInfo;
    }
    
    @JSONField(name="buy_limit")
    public ProductBuyLimit getBuyLimit() {
        return buyLimit;
    }
    
    @JSONField(name="buy_limit")
    public void setBuyLimit(ProductBuyLimit buyLimit) {
        this.buyLimit = buyLimit;
    }
    
    @JSONField(name="store_count")
	public Integer getStoreCount() {
		return storeCount;
	}

    @JSONField(name="store_count")
	public void setStoreCount(Integer storeCount) {
		this.storeCount = storeCount;
	}
	
    @JSONField(name="org_codes")
    public List<String> getOrgCodes() {
        return orgCodes;
    }

    @JSONField(name="org_codes")
    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    @JSONField(name="rack_time")
    public Date getRackTime() {
        return rackTime;
    }

    @JSONField(name="rack_time")
    public void setRackTime(Date rackTime) {
        this.rackTime = rackTime;
    }
    @JSONField(name="province_id")
    public Long getProvinceId() {
        return provinceId;
    }

    @JSONField(name="province_id")
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
    
    @JSONField(name="city_id")
    public Long getCityId() {
        return cityId;
    }

    @JSONField(name="city_id")
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
	
    @JSONField(name="area_id")
    public Long getAreaId() {
        return areaId;
    }

    @JSONField(name="area_id")
    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }
    @JSONField(name="create_time")
    public Date getCreateTime() {
        return createTime;
    }

    @JSONField(name="create_time")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
   
}



