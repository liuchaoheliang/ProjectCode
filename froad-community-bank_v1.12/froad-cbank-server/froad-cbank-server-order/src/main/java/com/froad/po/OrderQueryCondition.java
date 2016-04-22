package com.froad.po;

public class OrderQueryCondition implements java.io.Serializable {

	private static final long serialVersionUID = 3695144773826027470L;
	
    /****************获取订单概要****************/
	/** 客户端ID */
    private String clientId; 
    
    /** 会员ID */
    private Long memberCode;

    /** 订单状态 */
    private String orderStatus;     
    
    /** 开始时间 */
    private Long startTime;        
    
    /** 结束时间 */
    private Long endTime;
    
    /****************获取订单详情****************/
    /** 订单ID */
    private String orderId; 
    
    /****************获取积分兑换****************/
    /** 查询标识 */
    private String queryFlag;  

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}      
}
