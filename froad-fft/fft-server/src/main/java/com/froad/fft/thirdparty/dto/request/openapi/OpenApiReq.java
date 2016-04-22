package com.froad.fft.thirdparty.dto.request.openapi;

import java.util.List;

import com.froad.fft.persistent.common.enums.PayChannel;
import com.froad.fft.thirdparty.common.OpenApiCommand;
import com.froad.fft.thirdparty.common.SystemCommand;
import com.froad.fft.thirdparty.util.DESUtil;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: OpenApiReq.java </p>
 *<p> 描述: *-- <b>用于请求openapi的参数</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014年1月15日 上午11:48:35 </p>
 ********************************************************
 */
public class OpenApiReq {
	
	//代收请求/代扣请求
	public enum ReqType{
		COLLECT(0),//代收
		DEDUCT(1);//代扣
		
		private int value;
		private ReqType(int value){
			this.value = value;
		}
		public int getVal(){
			return value;
		}
	}
	
	//退款类型
	public enum RefundType{
		ALL("01"),//全部退款
		PART("02");//部分退款
		
		private String value;
		private RefundType(String value){
			this.value = value;
		}
		public String getVal(){
			return value;
		}
	}
	
	//校验类型
	public enum CheckType{
		ACCOUNT_SELECT("1"),//账户查询
		ACCOUNT_CHECK("2"),//账户校验
		ACCOUNT_MOBILE("3");//贴膜卡校验
		
		private String value;
		private CheckType(String value){
			this.value = value;
		}
		public String getVal(){
			return value;
		}
	}
	
	//转账类型
	public enum TransferType{
		ONE("1");//单笔转账
		//MORE("2") //多笔转账（暂不支持）
		
		private String value;
		private TransferType(String value){
			this.value = value;
		}
		public String getVal(){
			return value;
		}
	}
	
	//客户端信息
	public enum Client{
		pc("100"),
		android("200"),
		iphone("300"),
		ipad("400");
		
		private String value;
		private Client(String value){
			this.value = value;
		}
		public String getVal(){
			return value;
		}
	}
	
	//订单类型
	public enum OrderType{		
		CASH("1201"),//即时交易-实体商品-直接优惠
		SCORE("1202"),//即时交易-实体商品-返利积分
		
		FORTHWITH("1000"),//即时交易
		COMBINE("5000"); //合并支付即时交易
		
		private String value;
		private OrderType(String value){
			this.value = value;
		}
		public String getVal(){
			return value;
		}
	}
	
	//付款方类型
	public enum PayerType{
		CUSTOMER("1");//,//客户
	//	MERCHANT("2"),//商户
	//	BANK("3"),//银行
	//	FROAD("4");//方付通
		
		private String value;
		private PayerType(String value){
			this.value = value;
		}
		public String getVal(){
			return value;
		}
	}
	
	//付款方式
	public enum PayerWay{
		PHONE_NUM("1"),//手机号码
		BANK_NUM("2");//银行卡号
		
		private String value;
		private PayerWay(String value){
			this.value = value;
		}
		public String getVal(){
			return value;
		}
	}
	
	//收款方类型
	public enum PayeeType{
		//CUSTOMER("1"),//客户
		MERCHANT("2");//,商户
		//	BANK("3"),//银行
		//	FROAD("4");//方付通
			
		private String value;
		private PayeeType(String value){
			this.value = value;
		}
		public String getVal(){
			return value;
		}
	}
	
	//收款方式
	public enum PayeeWay{
		PHONE_NUM("1"),//手机号码
		BANK_NUM("2");//银行卡号
		
		private String value;
		private PayeeWay(String value){
			this.value = value;
		}
		public String getVal(){
			return value;
		}
	}
	
	
	
	
//	//订单货币类型
//	public enum OrderCurrency{
//		RMB("1");//人民币	
//		private String value;
//		private OrderCurrency(String value){
//			this.value = value;
//		}
//		public String getVal(){
//			return value;
//		}
//	}
	
	public enum PayDirect{
		FROAD("10"),//方付通
		
		F_MERCHANT("20");//方付通商户
		private String value;
		private PayDirect(String value){
			this.value = value;
		}
		public String getVal(){
			return value;
		}
	}
	
	
	private ReqType reqType;
	private String partnerID;//
	
	private String transferOrg;  //转账积分机构号
	private String refundOrderID;//退款订单编号	
	private String orderID;//订单编号	
	private String orderAmount;//订单金额	
	private String refundAmount;//退款订单金额	
	private RefundType refundType;	
	private String orderSupplier;//订单供应商	
	private String refundReason;//退款原因	

	
	private String checkOrg;//校验机构
	private CheckType checkType;
	private String checkContent;//校验信息内容
	private String checkRemark;//校验扩展信息
	
	
	private String transferID;//转账编号
	private TransferType transferType;
	private String transferAmount;//转账金额
	private String payerMember;//付款方 （账户号|账户名 如: 62000123|方付通）
	private String payerMemberMsg;//付款方帐户加密信息
	private String payeeMember;//收款方 （账户号|账户名 如: 62000123|方付通）
	private String payeeMemberMsg;//收款方加密信息
	private String transferRemark;//拓展信息
	private Client client;
	
	
	//-----
	private String orderRemark;//备注信息
	private OrderType orderType;
	private String orderFailureTime;//订单失效时间（暂无用）
	private PayChannel payChannel;
	private String payOrg;//支付机构
	private PayerType payerType;
	private PayerWay payerWay;
	private String payerAmount; //付款金额
	private PayeeType payeeType;
	private PayeeWay payeeWay;
	private String payeeAmount;//收款金额
	private String returnUrl;//返回地址（暂无用）
	//-----
	
	
	
	//-------------------
	private List<OpenApiOrderDetail> orderDetails; //多条订单信息
	private String totalAmount;//订单总金额
	private PayDirect payDirect;//设置收款方在方付通的会员身份描述
	private String orderDisplay;//订单显示信息
	//-------------------
	
	
	
	//-------------------------限定构造参数列表
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>退款申请 必要构造参数</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月15日 上午11:48:35 </p>
	 ********************************************************
	 */
	public OpenApiReq(String refundOrderID,String orderID,String orderAmount,
			String refundAmount,RefundType refundType,String orderSupplier
			,String refundReason,String partnerID){
		this.refundOrderID = refundOrderID;
		this.orderID = SystemCommand.FFT_CLIENT_PREFIX+orderID;
		this.orderAmount = orderAmount;
		this.refundAmount = refundAmount;
		this.refundType = refundType;
		this.orderSupplier = orderSupplier;
		this.refundReason = refundReason;
		this.partnerID = partnerID;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>校验查询 必要构造参数</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月15日 上午11:48:35 </p>
	 ********************************************************
	 */
	public OpenApiReq(String checkOrg,CheckType checkType,String checkContent,String checkRemark,String partnerID){
		this.checkOrg = checkOrg;
		this.checkType = checkType;
		this.checkContent = checkContent;
		this.checkRemark = checkRemark;
		this.partnerID = partnerID;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>转账 必要构造参数</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月15日 上午11:48:35 </p>
	 ********************************************************
	 */
	public OpenApiReq(String transferID,TransferType transferType,String transferAmount,String payerAccountNum,
			String payerAccountName,String payeeAccountNum,String payeeAccountName,String transferRemark,Client client,String transferOrg,String partnerID){
		this.transferID = transferID;
		this.transferType = transferType;
		this.transferAmount = transferAmount;
		this.payerMember = payerAccountNum+"|"+payerAccountName;
		try {
			this.payerMemberMsg = new DESUtil(OpenApiCommand.OPENAPI_CUSTOM_KEY).encrypt(payerAccountNum+"<"+payerAccountName+">");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.payeeMember = payeeAccountNum+"|"+payeeAccountName;
		try {
			this.payeeMemberMsg = new DESUtil(OpenApiCommand.OPENAPI_CUSTOM_KEY).encrypt(payeeAccountNum+"<"+payeeAccountName+">");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.transferRemark = transferRemark;
		this.client = client;		
		this.transferOrg = transferOrg;
		this.partnerID = partnerID;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>代收|代扣 必要构造参数</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月15日 上午11:48:35 </p>
	 ********************************************************
	 */
	public OpenApiReq(String orderID,OrderType orderType,String orderAmount,String orderRemark,PayChannel payChannel,String payOrg,
			PayerType payerType,PayerWay payerWay,String payerMember,String payerAmount,PayeeType payeeType,PayeeWay payeeWay,
			String payeeMember,String payeeAmount,Client client,ReqType reqType,String partnerID
			){
		this.orderID = orderID;
		this.orderType = orderType;
		this.orderAmount = orderAmount;
		this.orderRemark = orderRemark;
		this.payChannel = payChannel;
		this.payOrg = payOrg;
		this.payerType = payerType;
		this.payerWay = payerWay;
		this.payerMember = payerMember;
		this.payerAmount = payerAmount;
		this.payeeType = payeeType;
		this.payeeWay = payeeWay;
		this.payeeMember = payeeMember;
		this.payeeAmount = payeeAmount;
		this.client = client;
		try {
			String[] temp = payerMember.split("\\|");
			this.payerMemberMsg = new DESUtil(OpenApiCommand.OPENAPI_CUSTOM_KEY).encrypt(temp[1]+"<"+temp[2]+">");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String[] temp = payeeMember.split("\\|");
			this.payeeMemberMsg = new DESUtil(OpenApiCommand.OPENAPI_CUSTOM_KEY).encrypt(temp[1]+"<"+temp[2]+">");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.reqType = reqType;
		this.partnerID = partnerID;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>合并支付构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年3月21日 上午11:48:35 </p>
	 ********************************************************
	 */
	public OpenApiReq(List<OpenApiOrderDetail> orderDetails,
			OrderType orderType,String totalAmount,PayChannel payChannel,
			Client client,String payOrg,String payerMember,
			PayDirect payDirect,String orderDisplay,String partnerID){
		this.orderDetails = orderDetails;
		this.orderType = orderType;
		this.totalAmount = totalAmount;
		this.payChannel = payChannel;
		this.payOrg = payOrg; 
		this.payerMember = payerMember;
		this.payDirect = payDirect;
		this.client = client;
		this.orderDisplay=orderDisplay;
		this.partnerID=partnerID;
	}
	
	//-------------------------限定构造参数列表
	
	
	public String getRefundOrderID() {
		return refundOrderID;
	}

	public String getOrderID() {
		return orderID;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public RefundType getRefundType() {
		return refundType;
	}

	public String getOrderSupplier() {
		return orderSupplier;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public String getCheckOrg() {
		return checkOrg;
	}

	public CheckType getCheckType() {
		return checkType;
	}

	public String getCheckContent() {
		return checkContent;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public String getTransferID() {
		return transferID;
	}

	public TransferType getTransferType() {
		return transferType;
	}

	public String getTransferAmount() {
		return transferAmount;
	}

	public String getPayerMember() {
		return payerMember;
	}

	public String getPayerMemberMsg() {
		return payerMemberMsg;
	}

	public String getPayeeMember() {
		return payeeMember;
	}

	public String getPayeeMemberMsg() {
		return payeeMemberMsg;
	}

	public String getTransferRemark() {
		return transferRemark;
	}

	public Client getClient() {
		return client;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public String getOrderFailureTime() {
		return orderFailureTime;
	}

	public PayerType getPayerType() {
		return payerType;
	}

	public PayerWay getPayerWay() {
		return payerWay;
	}

	public PayeeType getPayeeType() {
		return payeeType;
	}

	public PayeeWay getPayeeWay() {
		return payeeWay;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public String getPayOrg() {
		return payOrg;
	}

	public String getOrderRemark() {
		return orderRemark;
	}

	public String getPayerAmount() {
		return payerAmount;
	}

	public String getPayeeAmount() {
		return payeeAmount;
	}

	public ReqType getReqType() {
		return reqType;
	}

	public List<OpenApiOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public PayDirect getPayDirect() {
		return payDirect;
	}

	public String getTransferOrg() {
		return transferOrg;
	}

	public String getOrderDisplay() {
		return orderDisplay;
	}

	public void setOrderDisplay(String orderDisplay) {
		this.orderDisplay = orderDisplay;
	}

	public String getPartnerID() {
		return partnerID;
	}

	public PayChannel getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(PayChannel payChannel) {
		this.payChannel = payChannel;
	}
	
	
}
