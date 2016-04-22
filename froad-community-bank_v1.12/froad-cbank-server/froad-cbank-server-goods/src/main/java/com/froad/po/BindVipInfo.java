package com.froad.po;

import java.io.Serializable;

public class BindVipInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2120239048661741696L;
    
    /**
     * 商品id
     */
    private String productId;
    /**
     * VIP1价
     */
    private Integer vipPrice;
    /**
     * VIP1限购
     */
    private Integer vipLimit;
    
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public Integer getVipPrice() {
        return vipPrice;
    }
    public void setVipPrice(Integer vipPrice) {
        this.vipPrice = vipPrice;
    }
    
    public Integer getVipLimit() {
        return vipLimit;
    }
    public void setVipLimit(Integer vipLimit) {
        this.vipLimit = vipLimit;
    }

}
