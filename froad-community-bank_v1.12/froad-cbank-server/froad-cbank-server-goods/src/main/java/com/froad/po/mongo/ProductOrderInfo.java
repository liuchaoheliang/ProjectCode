package com.froad.po.mongo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class ProductOrderInfo implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8248901877389326366L;

    /**
     * 交易编号
     */
    private String orderId;
    /**
     * 交易类型
     */
    private String orderType;
    
    @JSONField(name="order_id")
    public String getOrderId() {
        return orderId;
    }
    
    @JSONField(name="order_id")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    @JSONField(name="order_type")
    public String getOrderType() {
        return orderType;
    }
    
    @JSONField(name="order_type")
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
