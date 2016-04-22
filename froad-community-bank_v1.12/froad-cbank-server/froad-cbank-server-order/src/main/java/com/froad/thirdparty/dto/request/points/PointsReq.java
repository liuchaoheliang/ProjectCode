package com.froad.thirdparty.dto.request.points;

import com.froad.thirdparty.common.PointsCommand;
import com.froad.thirdparty.enums.ProtocolType;


/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: PointsReq.java </p>
 *<p> 描述: *-- <b>请求积分平台的入参对象数据</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014年1月13日 下午12:09:27 </p>
 ********************************************************
 */
public class PointsReq{

	private String partnerNo; //合作伙伴编号ID
	private String orgNo; //积分机构号
	private String accountMarked; //会员标识
	private String accountMarkedType; //会员标识类型
	
	private String objectNo;//消费积分的一个标识ID 如：交易号，商品ID
	private String objectDes;//消费积分的具体描述信息
	private String objectType;//消费积分的一个东西的类型，如：订单，订单中的某商品,若是订单单，其值为1
	private String points;//具体积分数
	private String accountId;//积分帐号
	private String checkCode;//验证码
	private String remark;//备注信息
	
	private String payPointsNo;//成功消费积分的消费积分单编号
	
	private String pointsCateNo;//积分类型编号
	private String orgPoints;//积分机构的积分额
	private String phone;//客户在积分机构的手机号
	
	private String realName; //银行实名信息
	private String bankId; //银行编号
	private String bankName; //银行名称
	private String bankCard; //银行卡号
	private String certType;//证件类型
	private String certNo;//证件号
	private String businessType;//业务类型
	
	private String identityNo;//身份证号
	private String mobileNum;//用户预留手机号
	private String cardType;//银行卡卡种
	private String protocolNo;//银行协议号
	
	
	private String cardPassword;//充值卡卡密
	private String cardNo;//充值卡卡号
	
	private ProtocolType protocolType;//协议类型
	private Integer pageSize;//分页容量
	private Integer pageNum;//查询页码
	
	private String fromTime;//开始时间
	private String toTime;//结束时间
	

	private String extend1;//用于扩展字段1
	
	private String extend2;//用于扩展字段2
	
	private String orderType;

	public enum QueryOrderType{
		consume("01"),refund("02"),give("03");
		private String code;
		
		private QueryOrderType(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
		
	}
	//--------------------------------------------------------------构造参数列表限定
	
	public PointsReq(){}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询积分  必填的参数列表构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsReq(String orgNo,String accountMarked,String accountMarkedType,String partnerNo){
		this.orgNo = orgNo;
		this.accountMarked = accountMarked;
		this.accountMarkedType = accountMarkedType;
		this.partnerNo = partnerNo;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询积分比例  必填的参数列表构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsReq(String orgNo,String partnerNo){
		this.orgNo = orgNo;
		this.partnerNo = partnerNo;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>消费积分  必填的参数列表构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsReq(String orgNo,String objectNo,Double points,String accountMarked,
			String partnerNo,String businessType,Double orgPoints,String accountId){
		this.accountMarked = accountMarked;
		this.objectNo = objectNo;
		this.points = String.valueOf(points);
		this.partnerNo = partnerNo;
		this.orgNo = orgNo;
		this.orgPoints = orgPoints == null ? "" : String.valueOf(orgPoints);
		this.businessType=businessType;
		this.accountId = accountId;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>退还积分  必填的参数列表构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsReq(String objectNo,String accountId,String points,
			String accountMarked,String payPointsNo,
			String partnerNo,String businessType,String remark,String orgNo){
		this.accountMarked = accountMarked;
		this.objectNo = objectNo;
		this.accountId = accountId;
		this.points = points;
		this.payPointsNo=payPointsNo;
		this.partnerNo = partnerNo;
		this.businessType=businessType;
		this.remark=remark;
		this.orgNo = orgNo;
	}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>赠送积分  必填的参数列表构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsReq(String orgNo,String accountMarked,String accountMarkedType,
			String objectNo,String objectDes,String objectType,
			String remark,String points,String partnerNo,String businessType){
		this.orgNo = orgNo;
		this.accountMarked = accountMarked;
		this.accountMarkedType = accountMarkedType;
		this.objectNo = objectNo;
		this.objectDes = objectDes;
		this.objectType = objectType;
		this.remark = remark;
		this.points = points;
		this.partnerNo = partnerNo;
		this.businessType=businessType;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>兑充积分  必填的参数列表构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsReq(String orgNo,String pointsCateNo,String orgPoints,String objectNo,String objectDes,String objectType,
				String remark,String accountMarked,String accountMarkedType,Long phone,String partnerNo){
		this.orgNo = orgNo;
		this.pointsCateNo = pointsCateNo;
		this.orgPoints = orgPoints;
		this.objectNo = objectNo;
		this.objectDes = objectDes;
		this.objectType = objectType;
		this.remark = remark;
		this.accountMarked = accountMarked;
		this.accountMarkedType = accountMarkedType;
		this.phone = phone.toString();
		this.partnerNo = partnerNo;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>积分提现申请  必填的参数列表构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsReq(String orgNo,String accountMarked,String accountMarkedType,
			String realName,String bankId,String bankName,String bankCard,
			String objectNo,String objectDes,String objectType,String points,
			String businessType,String remark,String partnerNo,String certNo){
		this.orgNo = orgNo;
		this.accountMarked = accountMarked;
		this.accountMarkedType = accountMarkedType;
		this.realName = realName;
		this.bankId = bankId;
		this.bankName = bankName;
		this.bankCard = bankCard;
		this.objectNo = objectNo;
		this.objectDes = objectDes;
		this.objectType = objectType;
		this.points = points;
		this.businessType = businessType;
		this.remark = remark;
		this.partnerNo = partnerNo;
		this.certNo=certNo;
	}
	
	/**
	  * 签约关系通知接口
	  * 创建一个新的实例 PointsReq.
	  * @param 
	  * 刘超   2014年1月13日 上午11:52:50
	  */
	public PointsReq(String accountMarked,String accountMarkedType,String partnerNo,String identityNo,String mobileNum,String realName,String bankId,String bankName,String bankCard,String cardType, String protocolNo){
		this.accountMarked = accountMarked;
		this.accountMarkedType = accountMarkedType;
		this.partnerNo = partnerNo;
		this.identityNo=identityNo;
		this.mobileNum=mobileNum;
		this.realName=realName;
		this.bankId=bankId;
		this.bankName=bankName;
		this.bankCard=bankCard;
		this.cardType=cardType;
		this.protocolNo=protocolNo;
	};
	
	
	/**
	  * 账户充值接口
	  * 类的构造方法
	  * 创建一个新的实例 PointsReq.
	  * @param (cardNo:可以为"")
	  */
	public PointsReq(String accountMarked,String accountMarkedType,String cardNo,String cardPassword,String partnerNo){
		this.accountMarked = accountMarked;
		this.accountMarkedType = accountMarkedType;
		this.partnerNo = partnerNo;
		this.cardPassword=cardPassword;
		this.cardNo=cardNo;
	}
	
	/**
	  * 充值卡信息查询接口
	  * 类的构造方法
	  * 创建一个新的实例 PointsReq.
	  * @param 
	  */
	public PointsReq(String cardPassword,String cardNo,String partnerNo){
		this.partnerNo = partnerNo;
		this.cardPassword=cardPassword;
		this.cardNo=cardNo;
	}
	
	
	
	
	/**
	  * 银行账户签约接口必要参数
	  * 类的构造方法
	  * 创建一个新的实例 PointsReq.
	  * @param extend1和extend2选填
	  */
	public PointsReq(String partnerNo, String accountMarked,
			String accountMarkedType, String realName, String bankId,
			String bankName, String bankCard, String identityNo,
			String mobileNum, String cardType, String extend1, String extend2) {
		this.partnerNo = partnerNo;
		this.accountMarked = accountMarked;
		this.accountMarkedType = accountMarkedType;
		this.realName = realName;
		this.bankId = bankId;
		this.bankName = bankName;
		this.bankCard = bankCard;
		this.identityNo = identityNo;
		this.mobileNum = mobileNum;
		this.cardType = cardType;
		this.extend1 = extend1;
		this.extend2 = extend2;
	}
	
	
	/**
	  * 类的构造方法,银行账户解约接口必要参数
	  * 创建一个新的实例 PointsReq.
	  * @param 
	  * @param partnerNo
	  * @param accountMarked
	  * @param accountMarkedType
	  * @param bankCard
	  * @param temp  --预留参数，可以不传
	  */
	public PointsReq(String partnerNo, String accountMarked,
			String accountMarkedType, String bankCard,Integer temp) {
		this.partnerNo = partnerNo;
		this.accountMarked = accountMarked;
		this.accountMarkedType = accountMarkedType;
		this.bankCard = bankCard;
	}
	
	
	
	/**
	  * 类的构造方法，用户积分消费分页查询
	  * 创建一个新的实例 PointsReq.
	  * @param 
	  * @param partnerNo
	  * @param orgNo
	  * @param accountMarked
	  * @param protocolNo
	  * @param protocolType
	  * @param pageSize
	  * @param pageNum
	  * @param fromTime
	  * @param toTime
	  */
	public PointsReq(String partnerNo, String orgNo, String accountMarked,
			String protocolNo, ProtocolType protocolType, Integer pageSize,
			Integer pageNum, String fromTime, String toTime) {
		this.partnerNo = partnerNo;
		this.orgNo = orgNo;
		this.accountMarked = accountMarked;
		this.protocolNo = protocolNo;
		this.protocolType = protocolType;
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.fromTime = fromTime;
		this.toTime = toTime;
	}
	

	public PointsReq(String accountMarked){
		this.accountMarked = accountMarked;
		this.accountMarkedType = PointsCommand.ACCOUNT_MARKED_TYPE_USERNAME;
	}
	
	//--------------------------------------------------------------构造参数列表限定


	public String getOrgNo() {
		return orgNo;
	}

	public String getAccountMarked() {
		return accountMarked;
	}

	public String getAccountMarkedType() {
		return accountMarkedType;
	}

	public String getObjectNo() {
		return objectNo;
	}

	public String getObjectDes() {
		return objectDes;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getPoints() {
		return points;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getRemark() {
		return remark;
	}

	public String getPayPointsNo() {
		return payPointsNo;
	}

	public String getPointsCateNo() {
		return pointsCateNo;
	}

	public String getOrgPoints() {
		return orgPoints;
	}

	public String getPhone() {
		return phone;
	}

	public String getRealName() {
		return realName;
	}

	public String getBankId() {
		return bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public String getBusinessType() {
		return businessType;
	}

	public String getPartnerNo() {
		return partnerNo;
	}

	public String getCertType() {
		return certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public String getCardType() {
		return cardType;
	}

	public String getProtocolNo() {
		return protocolNo;
	}

	public String getCardPassword() {
		return cardPassword;
	}

	public String getCardNo() {
		return cardNo;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public void setAccountMarked(String accountMarked) {
		this.accountMarked = accountMarked;
	}

	public void setAccountMarkedType(String accountMarkedType) {
		this.accountMarkedType = accountMarkedType;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public void setObjectDes(String objectDes) {
		this.objectDes = objectDes;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setPayPointsNo(String payPointsNo) {
		this.payPointsNo = payPointsNo;
	}

	public void setPointsCateNo(String pointsCateNo) {
		this.pointsCateNo = pointsCateNo;
	}

	public void setOrgPoints(String orgPoints) {
		this.orgPoints = orgPoints;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public void setProtocolNo(String protocolNo) {
		this.protocolNo = protocolNo;
	}

	public void setCardPassword(String cardPassword) {
		this.cardPassword = cardPassword;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}

	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public ProtocolType getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(ProtocolType protocolType) {
		this.protocolType = protocolType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public void setOrderType(QueryOrderType orderType) {
		this.orderType = orderType.getCode();
	}

	
}
