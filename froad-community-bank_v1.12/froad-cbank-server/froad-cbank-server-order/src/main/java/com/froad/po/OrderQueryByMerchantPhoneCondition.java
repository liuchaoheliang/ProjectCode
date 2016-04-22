package com.froad.po;

import java.io.Serializable;

/**
 * 商户H5查询面对面订单
 * 
 * @author Arron
 * 
 */
public class OrderQueryByMerchantPhoneCondition implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6295430442164963333L;

    /**
     * 客户端ID
     */
    private String              clientId;
    /**
     * 商户号
     */
    private String            merchantId;
    /**
     * 订单类型
     */
    private String            type;
    /**
     * 所属门店
     */
    private String            outletId;
    /**
     * 发货状态
     */
    private String            deliveryStatus;

    /**
     * 订单状态
     */
    private String            status;

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

    /**
     * @return the deliveryStatus
     */
    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    /**
     * @param deliveryStatus
     *            the deliveryStatus to set
     */
    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
