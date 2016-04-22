package com.froad.jetty.vo;

/**
 * 下单响应
 * 
 * @author wangzhangxu
 * @date 2015年4月16日 下午1:59:01
 * @version v1.0
 */
public class PlaceOrderResponseVo {
	
	/** 受理订单号 */
	private String acceptOrderId;
	
	/** 间隔秒数 */
	private int intervalSeconds;
	
	public PlaceOrderResponseVo(){}
	
	public String getAcceptOrderId() {
		return acceptOrderId;
	}

	public void setAcceptOrderId(String acceptOrderId) {
		this.acceptOrderId = acceptOrderId;
	}

	public int getIntervalSeconds() {
		return intervalSeconds;
	}

	public void setIntervalSeconds(int intervalSeconds) {
		this.intervalSeconds = intervalSeconds;
	}
	
	
}
