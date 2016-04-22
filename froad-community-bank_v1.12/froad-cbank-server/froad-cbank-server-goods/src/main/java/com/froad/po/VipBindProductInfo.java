package com.froad.po;

import java.io.Serializable;
import java.util.Date;

public class VipBindProductInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8395741680330266816L;
    
    /**
     * VIP规则id
     */
    private String vipId;
    /**
     * 商品id
     */
    private String productId;
    /**
     * 商品创建时间
     */
    private Date createTime;
    /**
     * VIP1价
     */
    private Integer vipPrice;
    /**
     * VIP1限购
     */
    private Integer vipLimit;
    
    public String getVipId() {
        return vipId;
    }
    public void setVipId(String vipId) {
        this.vipId = vipId;
    }
    
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
