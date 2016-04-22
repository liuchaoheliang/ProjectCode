package com.froad.po;

import com.alibaba.fastjson.annotation.JSONField;

public class OutletProduct{

    /**
     * 商品id
     */
    private String id;
    /**
     * 客户端id
     */
    private String clientId;
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
     * 商户用户id
     */
    private String userId;
    
    /** 消费总金额 */
    private Integer consumeAmount;
    /** 不参与优惠金额 */
    private Integer notDiscountAmount;
    /** 折扣优惠率 */
    private Integer discountRate;

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JSONField(name = "_id")
    public String getId() {
        return id;
    }

    @JSONField(name = "_id")
    public void setId(String id) {
        this.id = id;
    }
    
    @JSONField(name = "client_id")
    public String getClientId() {
        return clientId;
    }

    @JSONField(name = "client_id")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    @JSONField(name = "merchant_id")
    public String getMerchantId() {
        return merchantId;
    }
    
    @JSONField(name = "merchant_id")
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    
    @JSONField(name = "outlet_id")
    public String getOutletId() {
        return outletId;
    }
    
    @JSONField(name = "outlet_id")
    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }
    
    @JSONField(name = "cost")
    public Integer getCost() {
        return cost;
    }
    
    @JSONField(name = "cost")
    public void setCost(Integer cost) {
        this.cost = cost;
    }
    
    @JSONField(name = "consume_amount")
	public Integer getConsumeAmount() {
		return consumeAmount;
	}
    
    @JSONField(name = "consume_amount")
	public void setConsumeAmount(Integer consumeAmount) {
		this.consumeAmount = consumeAmount;
	}
    
    @JSONField(name = "not_discount_amount")
	public Integer getNotDiscountAmount() {
		return notDiscountAmount;
	}
    
    @JSONField(name = "not_discount_amount")
	public void setNotDiscountAmount(Integer notDiscountAmount) {
		this.notDiscountAmount = notDiscountAmount;
	}
    
    @JSONField(name = "discount_rate")
	public Integer getDiscountRate() {
		return discountRate;
	}
    
    @JSONField(name = "discount_rate")
	public void setDiscountRate(Integer discountRate) {
		this.discountRate = discountRate;
	}
    
}
