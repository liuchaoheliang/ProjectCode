package com.froad.po;

import java.io.Serializable;

/**
 * 商户管理平台订单查询接口
 * 
 * @author Arron
 * 
 */
public class OrderQueryMerchantManageCondition implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4216145589221996003L;
    /**
     * 客户端ID
     */
    private String              clientId;

    /**
     * 商户ID
     */
    private String            merchantId;

    /**
     * 所属门店
     */
    private String            outletId;
    /**
     * 开始时间
     */
    private long              startTime;
    /**
     * 结束时间
     */
    private long              endTime;
    /**
     * 交易类型（4团购，5面对面，3名优特惠）
     */
    private String            type;
    /**
     * 订单状态(1. 订单创建，2.订单关闭，3.订单系统关闭，4.订单支付中，5. 订单支付失败，6.订单支付完成)
     */
    private String            orderStatus;
    /**
     * 操作类型（1.查询，2.下载）
     */
    private String            operType;
    
    
    /**
     * 发货状态（0.未发货，1.已发货，2.已收货）
     */
    private String deliveryStatus;

    /**
     * @return the clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId
     *            the clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * @return the merchantId
     */
    public String getMerchantId() {
        return merchantId;
    }

    /**
     * @param merchantId
     *            the merchantId to set
     */
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     *            the endTime to set
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the orderStatus
     */
    public String getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus
     *            the orderStatus to set
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the operType
     */
    public String getOperType() {
        return operType;
    }

    /**
     * @param operType
     *            the operType to set
     */
    public void setOperType(String operType) {
        this.operType = operType;
    }

    /**
     * @return the outletId
     */
    public String getOutletId() {
        return outletId;
    }

    /**
     * @param outletId
     *            the outletId to set
     */
    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

    
}
