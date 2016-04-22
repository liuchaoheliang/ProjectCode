package com.froad.cbank.coremodule.normal.boss.pojo.order;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 
 * @ClassName: DiscontPayListVoReq
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年12月29日 下午1:15:41 
 * @desc <p>惠付订单列表查询请求实体</p>
 */
public class DiscontPayListVoReq extends Page{
	
	 /**
	   * 订单编号
	   */
	  private String orderId; // optional
	  /**
	   * 用户会员名
	   */
	  private String memberName; // optional
	  /**
	   * 所属客户端
	   */
	  private String clientId; // optional
	  /**
	   * 订单创建来源
	   */
	  private String createSource; // optional
	  /**
	   * 支付类型
	   */
	  private String orderType; // optional
	  /**
	   * 支付方式
	   */
	  private String paymentMethod; // optional
	  /**
	   * 订单状态
	   */
	  private String orderStatus; // optional
	  /**
	   * 订单创建开始时间
	   */
	  private String begTime; // optional
	  /**
	   * 订单创建结束时间
	   */
	  private String endTime; // optional
	  
	  
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getCreateSource() {
		return createSource;
	}
	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getBegTime() {
		return begTime;
	}
	public void setBegTime(String begTime) {
		this.begTime = begTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	  
	  
	
}
