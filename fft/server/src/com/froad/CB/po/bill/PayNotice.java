package com.froad.CB.po.bill;


	/**
	 * 类描述：从账单平台接收到的通知实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:29:23 PM 
	 */
public class PayNotice {

	private String  orderID;//商户订单编号(在此是商城)
	private String orderAmount ;//订单金额
	private String orderCurrency ;//订单货币
	private String orderRemark ;//订单扩展信息
	private String  stateCode;//状态码
	private String orderAcquiringTime ;//收单时间 20101218020101
	private String orderCompleteTime ;//处理完成时间 20101218020101
	private String payType ;//支付类型 10：全部  20：贴膜卡  30: SD卡  31: SD卡定制支付 41：银联无卡支付
	private String payAmount ;//支付金额
	private String froadBillNo ;//方付通支付号
	private String resultCode ;//结果码
	private String partnerID ;//合作伙伴ID
	private String charset ;//编码方式
	private String signType ;//签名类型
	private String signMsg ;//签名字符串


	/*----------------新增属性--------------------*/
	private String displayData;//交易号接口查询结果内容
	private String desc;//结果描述
	private String respTime;//请求时间
	private String payData;//支付信息接口查询结果内容
	private String payOrg;//支付渠道
	private String payOrgNo;//资金机构订单号
	//账单查询
	private String queryType;	//设置查询类型。 1：单笔查询  2：批量查询
	private String queryOrderID;	//设置查询订单号。仅单笔查询生效。
	private String queryOrderState;	//查询订单状态
	private String queryTime;	//查询时间段      例如：2010年12月18日2点01分01秒 到 2010年12月19日2点01分01秒    20101218020101|20101219020101
	private String client;	//设置当前访问者的客户端信息。 100：pc   200：android  201: android_sd  300：iphone   400：ipad
	private String refundType;//01,全额退款   02,部分退款 
	private String RespCode;// 响应码,0表示交易成功,非0表示交易失败
	private String RespMsg;// 响应信息

	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public String getOrderID() {
		return orderID;
	}
	public String getPayOrgNo() {
		return payOrgNo;
	}
	public void setPayOrgNo(String payOrgNo) {
		this.payOrgNo = payOrgNo;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getOrderCurrency() {
		return orderCurrency;
	}
	public void setOrderCurrency(String orderCurrency) {
		this.orderCurrency = orderCurrency;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public String getPayOrg() {
		return payOrg;
	}
	public void setPayOrg(String payOrg) {
		this.payOrg = payOrg;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	public String getOrderAcquiringTime() {
		return orderAcquiringTime;
	}
	public void setOrderAcquiringTime(String orderAcquiringTime) {
		this.orderAcquiringTime = orderAcquiringTime;
	}
	public String getOrderCompleteTime() {
		return orderCompleteTime;
	}
	public void setOrderCompleteTime(String orderCompleteTime) {
		this.orderCompleteTime = orderCompleteTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(String payAmount) {
		this.payAmount = payAmount;
	}
	public String getFroadBillNo() {
		return froadBillNo;
	}
	public void setFroadBillNo(String froadBillNo) {
		this.froadBillNo = froadBillNo;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getPartnerID() {
		return partnerID;
	}
	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getSignMsg() {
		return signMsg;
	}
	public void setSignMsg(String signMsg) {
		this.signMsg = signMsg;
	}
	public String getDisplayData() {
		return displayData;
	}
	public void setDisplayData(String displayData) {
		this.displayData = displayData;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getRespTime() {
		return respTime;
	}
	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}
	public String getPayData() {
		return payData;
	}
	public void setPayData(String payData) {
		this.payData = payData;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
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
	public String getRespCode() {
		return RespCode;
	}
	public void setRespCode(String respCode) {
		RespCode = respCode;
	}
	public String getRespMsg() {
		return RespMsg;
	}
	public void setRespMsg(String respMsg) {
		RespMsg = respMsg;
	}
}
