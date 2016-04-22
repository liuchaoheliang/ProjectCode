package com.froad.thirdparty.dto.request.openapi;

import java.util.List;

import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.common.SystemCommand;
import com.froad.thirdparty.util.DESUtil;

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
	
	//订单类型
	public enum BillOrderType{		
		CASH("1201"),//即时交易-实体商品-直接优惠
		SCORE("1202"),//即时交易-实体商品-返利积分
		
		FORTHWITH("1000"),//即时交易
		COMBINE("5000"); //合并支付即时交易
		
		private String value;
		private BillOrderType(String value){
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
	
	/**
	 * Copyright © 2015 F-Road. All rights reserved.
	 * @ClassName: Client
	 * @Description: 订单来源客户端类型
	 * @Author: zhaoxiaoyao@f-road.com.cn
	 * @Date: 2015年3月19日 下午2:27:58
	 */
	public enum Client {
		PC("100"),
		ANDROID("200"),
		ANDROID_SD("201"),
		IPHONE("300"),
		IPAD("400")		
		;
		private String code;
		private Client(String code){
			this.code=code;
		}
		public String getCode() {
			return code;
		}		
		public static final Client getClient(String code){
			for(Client source:Client.values()) {
				if(source.code.equals(code)) {
					return source;
				}
			}
			return null;
		}
	}
	
	//支付类型
	public enum PayType{
		FOIL_CARD("20"),
		FAST_PAY("51"),
		TIMELY_PAY("55"),
		BANK_ONLINE("50"),
		;
		private String code;
		private PayType(String code){
			this.code=code;
		}
		public String getCode() {
			return code;
		}		
		public static final Client getClient(String code){
			for(Client source:Client.values()) {
				if(source.code.equals(code)) {
					return source;
				}
			}
			return null;
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
		FROAD("10"),//间联
		
		F_MERCHANT("20");//直联
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
	private String mobileToken;//手机验证码(快捷支付时必填)
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
	private String payerIdentity;//付款方会员标识
	private String payeeMember;//收款方 （账户号|账户名 如: 62000123|方付通）
	private String payeeMemberMsg;//收款方加密信息
	private String transferRemark;//拓展信息
	
	
	
	private Client client; //订单来源来源客户端类型
	
	//-----
	private String orderRemark;//备注信息
	private BillOrderType billOrderType;
	private String orderFailureTime;//订单失效时间（暂无用）
	private PayType payType;
	private String payOrg;//支付机构
	private PayerType payerType;
	private PayerWay payerWay;
	private String payerAmount; //付款金额
	private PayeeType payeeType;
	private PayeeWay payeeWay;
	private String payeeAmount;//收款金额
	private String returnUrl;//返回地址（暂无用）
	private String noticeUrl;//通知地址
	//-----
	
	
	
	public String getNoticeUrl() {
		return noticeUrl;
	}

	//-------------------
	private List<OpenApiOrderDetail> orderDetails; //多条订单信息
	private String totalAmount;//订单总金额
	private PayDirect payDirect;//设置收款方在方付通的会员身份描述
	private String orderDisplay;//订单显示信息
	private String orderSubmitTime;//订单提交时间
	//-------------------
	
	
	//-----------------
	private String merchantID;
	private String merchantName;
	private String accountName;
	private String accountNo;
	private String mac;
	private String optionType;
	//-----------------
	
	private String verifyOrg;//验证机构
	private String employeeCode;//员工号
	private String password;//密码 3DES
	
	private String bankGroup; //资金机构代号
	private String queryTime;// 请求时间
	
	private String certificateNo;//证件号码
	private String certificateType;//证件类型
	
	
	
	
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
			,String refundReason,String partnerID,String noticeUrl){
		this.refundOrderID = refundOrderID;
		this.orderID = SystemCommand.FROAD_CBANK_PREFIX+orderID;
		this.orderAmount = orderAmount;
		this.refundAmount = refundAmount;
		this.refundType = refundType;
		this.orderSupplier = orderSupplier;
		this.refundReason = refundReason;
		this.partnerID = partnerID;
		this.noticeUrl=noticeUrl;
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
	 * 设置白名单必要构造
	* <p>Title: </p>
	* <p>Description: </p>
	* @param payOrg
	* @param merchantID
	* @param merchantName
	* @param accountName
	* @param accountNo
	* @param mac
	* @param optionType
	* @param client
	 */
	public OpenApiReq(String payOrg,String merchantID,String merchantName,String accountName,String accountNo,String mac,String optionType,Client client,String partnerID){
		this.payOrg = payOrg;
		this.merchantID = merchantID;
		this.merchantName = merchantName;
		this.accountName = accountName;
		this.accountNo = accountNo;
		this.mac = mac;
		this.optionType = optionType;
		this.client = client;
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
	public OpenApiReq(String orderID,BillOrderType billOrderType,String orderAmount,
			String orderRemark,PayType payType,String payOrg,
			PayerType payerType,PayerWay payerWay,String payerMember,
			String payerAmount,PayeeType payeeType,PayeeWay payeeWay,
			String payeeMember,String payeeAmount,Client client,
			ReqType reqType,String partnerID){
		this.orderID = orderID;
		this.billOrderType = billOrderType;
		this.orderAmount = orderAmount;
		this.orderRemark = orderRemark;
		this.payType = payType;
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
			BillOrderType billOrderType,String totalAmount,PayType payType,
			Client client,String payOrg,String payerMember,
			PayDirect payDirect,String orderDisplay,
			String partnerID,String orderRemark,
			String mobileToken,String returnUrl,
			String orderSubmitTime,String payeeMember,
			String payerIdentity){
		this.orderDetails = orderDetails;
		this.billOrderType = billOrderType;
		this.totalAmount = totalAmount;
		this.payType = payType;
		this.payOrg = payOrg; 
		this.payerMember = payerMember;
		this.payDirect = payDirect;
		this.client = client;
		this.orderDisplay=orderDisplay;
		this.partnerID=partnerID;
		this.orderRemark=orderRemark;
		this.mobileToken=mobileToken;
		this.returnUrl=returnUrl;
		this.orderSubmitTime=orderSubmitTime;
		this.payeeMember=payeeMember;
		this.payerIdentity=payerIdentity;
	}

	/**
	 * 登录验密接口 构造参数限定
	* <p>Title: </p>
	* <p>Description: </p>
	* @param verifyOrg
	* @param employeeCode
	* @param password
	 */
	public OpenApiReq(String verifyOrg,String employeeCode,String password,Client client,String partnerID){
		this.verifyOrg = verifyOrg;
		this.employeeCode = employeeCode;
		this.password = password;
		this.client = client;
		this.partnerID = partnerID;
	}
	
	/**
	 * 审核状态查询
	 *
	 * @param bankGroup
	 * @param accountName
	 * @param accountNo
	 * @param partnerID
	 */
	public OpenApiReq(String bankGroup,String accountName,String accountNo,String partnerID){
		this.bankGroup = bankGroup;
		this.accountName = accountName;
		this.accountNo = accountNo;
		this.partnerID = partnerID;
	}
	public OpenApiReq(String bankGroup,String accountName,String accountNo,String certificateNo,String certificateType,String partnerID){
		this.bankGroup = bankGroup;
		this.accountName = accountName;
		this.accountNo = accountNo;
		this.partnerID = partnerID;
		this.certificateNo = certificateNo;
		this.certificateType = certificateType;
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

	public BillOrderType getBillOrderType() {
		return billOrderType;
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

	public String getMobileToken() {
		return mobileToken;
	}

	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}

	public String getOrderSubmitTime() {
		return orderSubmitTime;
	}

	public void setOrderSubmitTime(String orderSubmitTime) {
		this.orderSubmitTime = orderSubmitTime;
	}

	public String getPayerIdentity() {
		return payerIdentity;
	}

	public void setPayerIdentity(String payerIdentity) {
		this.payerIdentity = payerIdentity;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public PayType getPayType() {
		return payType;
	}

	public String getMerchantID() {
		return merchantID;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public String getMac() {
		return mac;
	}
	
	public String getOptionType() {
		return optionType;
	}

	public String getVerifyOrg() {
		return verifyOrg;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public String getPassword() {
		return password;
	}

	/**
	 * bankGroup.
	 *
	 * @return  the bankGroup
	 */
	public String getBankGroup() {
		return bankGroup;
	}

	/**
	 * queryTime.
	 *
	 * @return  the queryTime
	 */
	public String getQueryTime() {
		return queryTime;
	}

	public String getCertificateNo() {
		return certificateNo;
	}

	public String getCertificateType() {
		return certificateType;
	}

	
	
	
}
