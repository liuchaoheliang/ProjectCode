package com.froad.db.chonggou.entity;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductMerchant implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1438357000920736520L;
    
    private String merchantId;
    private String merchantName;//商户名称
    
    @JSONField(name = "merchant_id")
    public String getMerchantId() {
        return merchantId;
    }
    
    @JSONField(name = "merchant_id")
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }
    
    @JSONField(name = "merchant_name")
    public String getMerchantName() {
        return merchantName;
    }
    
    @JSONField(name = "merchant_name")
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

}
