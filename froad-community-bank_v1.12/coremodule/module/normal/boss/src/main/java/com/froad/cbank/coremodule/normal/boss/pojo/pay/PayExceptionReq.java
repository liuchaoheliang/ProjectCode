package com.froad.cbank.coremodule.normal.boss.pojo.pay;

import com.froad.cbank.coremodule.normal.boss.pojo.Page;

/**
 * 支付异常订单请求
 * @ClassName PayExceptionReq
 * @author zxl
 * @date 2015年9月18日 下午2:43:16
 */
public class PayExceptionReq extends Page{
	
	/**
	 * 客户端id
	 */
	private String clientId;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	
	/**
	 * 1 退积分失败
	 * 2 退现金失败
	 */
	private String exceptionType;
	
	
	public String getExceptionType() {
		return exceptionType;
	}
	public void setExceptionType(String exceptionType) {
		this.exceptionType = exceptionType;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
