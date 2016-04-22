package com.froad.cbank.coremodule.normal.boss.pojo.order;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;


/**
 * 订单列表查询请求对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月2日 下午5:09:33
 */
public class OrderListReq extends Page {
	private String orderId;//订单编号
	private String paymentId;//支付编号
	private Long memberCode;//用户编号
	private String phone;//提货手机号
	private String createSource;//订单来源
	private String orderStatus;//订单状态
	private String paymentMethod;//支付方式
	private String clientId;//所属客户端
	private String startTime;//开始时间
	private String endTime;//结束时间
	
	private String orgCodes;
	
	public String getOrgCodes() {
		return orgCodes;
	}

	public void setOrgCodes(String orgCodes) {
		this.orgCodes = orgCodes;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCreateSource() {
		return createSource;
	}

	public void setCreateSource(String createSource) {
		this.createSource = createSource;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
