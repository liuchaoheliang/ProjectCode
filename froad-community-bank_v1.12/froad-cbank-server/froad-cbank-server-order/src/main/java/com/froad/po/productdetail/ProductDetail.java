package com.froad.po.productdetail;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  商品详情
  * @ClassName: ProductDetail
  * @Description: TODO
  * @author share 2015年3月21日
  * @modify share 2015年3月21日
 */
@Deprecated
public class ProductDetail implements Serializable{
	
	/**
     * 
     */
    private static final long serialVersionUID = 7718913202579304210L;
    
    private String id; 					// 商品ID productId
	private Integer isBest;				// 是否精品
	private Integer isLimit;			// 是否限购
	private String name;				// 商品名称
	private String productType;			// 商品类型 
	private Integer price;				// 价格
	
	private Integer marketPrice;
	private String clientId;
	private String isMarketable;
	private String deliverOption;
	private Integer sellCount;
	private Long startTime;
	private Long endTime;
	
	private String merchantId;			// 商户ID
	private String merchantName;		//（商户名称）
	private List<ProductActivitiesInfo> activitiesInfo;//商品活动
	private List<ProductOutlet> outletInfo;	// 门店详情
	private List<ProductImage> imageInfo;	// 图片详情
	private List<ProductCategoryInfo> productCategoryInfo;// 商品分类
	private ProductBuyLimit buyLimit;		// 限购信息
	
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
    public String getDeliverOption() {
        return deliverOption;
    }
    
    @JSONField(name="delivery_option")
    public void setDeliverOption(String deliverOption) {
        this.deliverOption = deliverOption;
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
    public Long getStartTime() {
        return startTime;
    }
    
    @JSONField(name="start_time")
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    
    @JSONField(name="end_time")
    public Long getEndTime() {
        return endTime;
    }
    
    @JSONField(name="end_time")
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
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
    
    @JSONField(name="outlet_info")
    public List<ProductOutlet> getOutletInfo() {
        return outletInfo;
    }
    
    @JSONField(name="outlet_info")
    public void setOutletInfo(List<ProductOutlet> outletInfo) {
        this.outletInfo = outletInfo;
    }
    
    @JSONField(name="image_info")
    public List<ProductImage> getImageInfo() {
        return imageInfo;
    }
    
    @JSONField(name="image_info")
    public void setImageInfo(List<ProductImage> imageInfo) {
        this.imageInfo = imageInfo;
    }
    
    @JSONField(name="product_category_info")
    public List<ProductCategoryInfo> getProductCategoryInfo() {
        return productCategoryInfo;
    }
    
    @JSONField(name="product_category_info")
    public void setProductCategoryInfo(List<ProductCategoryInfo> productCategoryInfo) {
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
	
	
}



