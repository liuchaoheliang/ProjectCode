package com.froad.po;

import java.io.Serializable;
import java.util.List;

/**
 * Boss查询订单列表条件
 * 
 * @author Arron
 * 
 */
public class OrderQueryByBossCondition implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2884440444475753090L;
    /**
     * 订单编号
     */
    private String             orderId;
    /**
     * 支付编号
     */
    private String             paymentId;
    /**
     * 用户编号
     */
    private long               memberCode;
    /**
     * 提货手机号
     */
    private String             phone;
    /**
     * 订单来源
     */
    private String             createSource;
    /**
     * 订单状态
     */
    private String             orderStatus;
    /**
     * 支付方式
     */
    private String             paymentMethod;
    /**
     * 支付时间
     */
    private String             paymentTime;
    /**
     * 所属客户端
     */
    private String             clientId;
    /**
     * 开始时间
     */
    private long               startTime;
    /**
     * 结束时间
     */
    private long               endTime;
    
    /**
     * 商品销售开始时间
     */
    private long               sellStartTime;
    /**
     * 商品销售结束时间
     */
    private long               sellEndTime;
    
    /**
     * 一级机构号
     */
    private String             fOrgCode;
    
    /**
     * 二级机构号
     */
    private String             sOrgCode;

    /** 收货地址编号 */
    private List<String>       recvId;

    /**
     * @return the orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     *            the orderId to set
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the paymentId
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId
     *            the paymentId to set
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @return the memberCode
     */
    public long getMemberCode() {
        return memberCode;
    }

    /**
     * @param memberCode
     *            the memberCode to set
     */
    public void setMemberCode(long memberCode) {
        this.memberCode = memberCode;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the createSource
     */
    public String getCreateSource() {
        return createSource;
    }

    /**
     * @param createSource
     *            the createSource to set
     */
    public void setCreateSource(String createSource) {
        this.createSource = createSource;
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
     * @return the paymentMethod
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * @param paymentMethod
     *            the paymentMethod to set
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

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

    public List<String> getRecvId() {
        return recvId;
    }

    public void setRecvId(List<String> recvId) {
        this.recvId = recvId;
    }

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public long getSellStartTime() {
		return sellStartTime;
	}

	public void setSellStartTime(long sellStartTime) {
		this.sellStartTime = sellStartTime;
	}

	public long getSellEndTime() {
		return sellEndTime;
	}

	public void setSellEndTime(long sellEndTime) {
		this.sellEndTime = sellEndTime;
	}

	public String getfOrgCode() {
		return fOrgCode;
	}

	public void setfOrgCode(String fOrgCode) {
		this.fOrgCode = fOrgCode;
	}

	public String getsOrgCode() {
		return sOrgCode;
	}

	public void setsOrgCode(String sOrgCode) {
		this.sOrgCode = sOrgCode;
	}
	
	
    

}
