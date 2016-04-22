package com.froad.po;

public class ProductBaseInfo {

    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 商户ID
     */
    private String merchantId;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 是否上架
     */
    private String isMarketable;
    /**
     * 商品类型
     */
    private String type;
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
     * 商品图片
     */
    private String imagePic;
    /**
     * VIP价
     */
    private Integer vipPrice;
    
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
    
    public String getImagePic() {
        return imagePic;
    }
    
    public void setImagePic(String imagePic) {
        this.imagePic = imagePic;
    }
    
    public Integer getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(Integer vipPrice) {
		this.vipPrice = vipPrice;
	}
    
}
