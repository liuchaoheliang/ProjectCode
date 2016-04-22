package com.froad.po.mongo;

import com.alibaba.fastjson.annotation.JSONField;

public class OutletProduct{

    /**
     * 商品id
     */
	@JSONField(name = "_id")
    private String id;
    /**
     * 客户端id
     */
	@JSONField(name = "client_id")
    private String clientId;
    /**
     * 商户id
     */
	@JSONField(name = "merchant_id")
    private String merchantId;
    /**
     * 门店id
     */
	@JSONField(name = "outlet_id")
    private String outletId;
    /**
     * 价格
     */
	@JSONField(name = "cost")
    private Integer cost;
    
	@JSONField(name = "total_products")
    private Integer totalProducts;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

	public Integer getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(Integer totalProducts) {
		this.totalProducts = totalProducts;
	}

    
}
