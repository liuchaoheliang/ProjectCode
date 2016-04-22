package com.froad.po;

public class OutletProductInfo {
    
    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 二维码
     */
    private String qrCode;
    /**
     * 商户id
     */
    private String merchantId;
    /**
     * 门店id
     */
    private String outletId;
    /**
     * 价格
     */
    private Integer cost;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户商标
     */
    private String logo;
    /**
     * 门店名称
     */
    private String outletName;
    /** 
     * 消费总金额 
     */
    private Integer consumeAmount;
    /** 
     * 不参与优惠金额
     */
    private Integer notDiscountAmount;
    /** 
     * 折扣优惠率
     */
    private Integer discountRate;
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    public String getQrCode() {
        return qrCode;
    }
    
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    
    public String getMerchantId() {
        return merchantId;
    }
    
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    
    public String getOutletId() {
        return outletId;
    }
    
    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }
    
    public Integer getCost() {
        return cost;
    }
    
    public void setCost(Integer cost) {
        this.cost = cost;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public String getMerchantName() {
        return merchantName;
    }
    
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
    
    public String getLogo() {
        return logo;
    }
    
    public void setLogo(String logo) {
        this.logo = logo;
    }
    
    public String getOutletName() {
        return outletName;
    }
    
    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public Integer getConsumeAmount() {
		return consumeAmount;
	}

	public void setConsumeAmount(Integer consumeAmount) {
		this.consumeAmount = consumeAmount;
	}

	public Integer getNotDiscountAmount() {
		return notDiscountAmount;
	}

	public void setNotDiscountAmount(Integer notDiscountAmount) {
		this.notDiscountAmount = notDiscountAmount;
	}

	public Integer getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}
	
}
