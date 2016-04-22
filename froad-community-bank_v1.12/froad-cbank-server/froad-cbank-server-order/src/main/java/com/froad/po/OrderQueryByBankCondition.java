package com.froad.po;

import java.io.Serializable;
import java.util.List;

import com.froad.enums.OrgLevelEnum;
import com.froad.po.order.OrderOrgCode;

/**
 * 银行管理-订单查询参数
 * 
 * @author Arron
 * 
 */
public class OrderQueryByBankCondition implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8278597526016314825L;

    /**
     * 客户端ID
     */
    private String            clientId;

    /**
     * 银行采用机构号
     */
    private String            orgCode;

    /**
     * 机构级别
     */
    private OrgLevelEnum      orgLevel;
    
    /**
     * 机构级别（登录人所属机构与所选条件的机构两者中低级别的一个）
     */
    private OrgLevelEnum      minOrgLevel;
    
    /**
     *  登录人所属机构与所选条件的机构两者中低级别的一个标识（所选机构：orgCode   当前登录人的机构：myOrgCode）
     */
    private String orgCodeFlag;

    /**
     * 开始时间
     */
    private long              startTime;
    /**
     * 结束时间
     */
    private long              endTime;
    /**
     * 订单状态
     */
    private List<String>            orderStatus;
    /**
     * 大订单号
     */
    private String            orderId;
    /**
     * 交易类型
     */
    private String            type;
    /**
     * 商户名
     */
    private String            merchantName;
    
    /**
     * 子订单号
     */ 
    private String  subOrderId;
    /**
     * 配送方式 自提、配送
     */ 
    private String  deliveryOption;
    /**
     * 积分兑换查询类型 线上积分兑换、线下积分兑换(默认)
     */ 
    private String  pointOrderType;
    
    
    /**
     * 当前最小的机构号
     */
    private String myOrgCode;
    
    /**
     * 网点orgCode列表
     */
    private List<String> orgCodes;
    
    /**
     * 
     */
    private OrderOrgCode orderOrgCode;
    
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

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgCode() {
        return orgCode;
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
     * @return the orderStatus
     */
    public List<String> getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param orderStatus
     *            the orderStatus to set
     */
    public void setOrderStatus(List<String> orderStatus) {
        this.orderStatus = orderStatus;
    }

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
     * @return the merchantName
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * @param merchantName
     *            the merchantName to set
     */
    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    /**
     * @return the orgLevel
     */
    public OrgLevelEnum getOrgLevel() {
        return orgLevel;
    }

    /**
     * @param orgLevel
     *            the orgLevel to set
     */
    public void setOrgLevel(OrgLevelEnum orgLevel) {
        this.orgLevel = orgLevel;
    }

	public String getSubOrderId() {
		return subOrderId;
	}

	public void setSubOrderId(String subOrderId) {
		this.subOrderId = subOrderId;
	}

	public String getDeliveryOption() {
		return deliveryOption;
	}

	public void setDeliveryOption(String deliveryOption) {
		this.deliveryOption = deliveryOption;
	}

	public String getPointOrderType() {
		return pointOrderType;
	}

	public void setPointOrderType(String pointOrderType) {
		this.pointOrderType = pointOrderType;
	}

	public List<String> getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(List<String> orgCodes) {
		this.orgCodes = orgCodes;
	}

	public String getMyOrgCode() {
		return myOrgCode;
	}

	public void setMyOrgCode(String myOrgCode) {
		this.myOrgCode = myOrgCode;
	}

	public OrgLevelEnum getMinOrgLevel() {
		return minOrgLevel;
	}

	public void setMinOrgLevel(OrgLevelEnum minOrgLevel) {
		this.minOrgLevel = minOrgLevel;
	}

	public String getOrgCodeFlag() {
		return orgCodeFlag;
	}

	public void setOrgCodeFlag(String orgCodeFlag) {
		this.orgCodeFlag = orgCodeFlag;
	}

	public OrderOrgCode getOrderOrgCode() {
		return orderOrgCode;
	}

	public void setOrderOrgCode(OrderOrgCode orderOrgCode) {
		this.orderOrgCode = orderOrgCode;
	}
	
	

}
