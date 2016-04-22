package com.froad.db.chonggou.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.util.JSonUtil;

public class OutletProduct{

    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 商品id
     */
    private String productId;
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
    private double cost;
    
    @JSONField(name = "_id")
    public String getProductId() {
        return productId;
    }
    
    @JSONField(name = "_id")
    public void setProductId(String productId) {
        this.productId = productId;
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
    public double getCost() {
        return cost;
    }
    
    @JSONField(name = "cost")
    public void setCost(double cost) {
        this.cost = cost;
    }
    
}
