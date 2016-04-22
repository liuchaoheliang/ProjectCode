package com.froad.jetty.vo;

/**
 * 查询订单响应
 * 
 * @author wangzhangxu
 * @date 2015年4月16日 下午1:59:01
 * @version v1.0
 */
public class QueryOrderResponseVo {
	
	/** 结果标识（0-等待中，1-下单成功，2-下单失败） */
	private String resultFlag;
	
	/** 下一次请求间隔秒数 */
	private int intervalSeconds;
	
	/** 订单号 */
	private String orderId;
	
	public QueryOrderResponseVo(){}

	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}
	
	public int getIntervalSeconds() {
		return intervalSeconds;
	}
	
	public void setIntervalSeconds(int intervalSeconds) {
		this.intervalSeconds = intervalSeconds;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
