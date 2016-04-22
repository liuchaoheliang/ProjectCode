
	 /**
  * 文件名：QueryApiReq.java
  * 版本信息：Version 1.0
  * 日期：2014年9月3日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.thirdparty.dto.request.openapi;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年9月3日 上午9:52:35 
 */
public class QueryParamApiReq {
	
	
	private String queryType; //设置查询类型。 1：单笔查询  2：批量查询
	
	private String  queryOrderType;//设置查询订单类型。 1001：交易订单  1002：退款订单
	
	private String queryOrderID;//查询订单号  
	private String queryOrderState;//查询订单状态
	private String queryTime;//查询时间段
	private String partnerID;//合作伙伴ID
	private String client;//客户端  100：pc 200：android 201: android_sd 300：iphone 400：ipad


	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getQueryOrderType() {
		return queryOrderType;
	}
	public void setQueryOrderType(String queryOrderType) {
		this.queryOrderType = queryOrderType;
	}
	public String getQueryOrderID() {
		return queryOrderID;
	}
	public void setQueryOrderID(String queryOrderID) {
		this.queryOrderID = queryOrderID;
	}
	public String getQueryOrderState() {
		return queryOrderState;
	}
	public void setQueryOrderState(String queryOrderState) {
		this.queryOrderState = queryOrderState;
	}
	public String getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(String queryTime) {
		this.queryTime = queryTime;
	}
	public String getPartnerID() {
		return partnerID;
	}
	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}
	
	/**
	  * 类的构造方法
	  * 创建一个新的实例 QueryParamApiReq.
	  * @param 
	  * @param queryType 设置查询类型。 1：单笔查询  2：批量查询
	  * @param queryOrderType  设置查询订单类型。 1001：交易订单  1002：退款订单
	  * @param queryOrderID  查询订单号  
	  * @param partnerID
	  */
	public QueryParamApiReq(String queryType, String queryOrderType,
			String queryOrderID,String queryOrderState, String partnerID,String client) {
		this.queryType = queryType;
		this.queryOrderType = queryOrderType;
		this.queryOrderID = queryOrderID;
		this.partnerID = partnerID;
		this.queryOrderState=queryOrderState;
		this.client=client;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}

	
}
