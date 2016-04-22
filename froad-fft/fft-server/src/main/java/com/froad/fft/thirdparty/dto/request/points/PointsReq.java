package com.froad.fft.thirdparty.dto.request.points;


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
	private String remark;//备注信息
	
	private String payPointsNo;//成功消费积分的消费积分单编号
	
	private String pointsCateNo;//积分类型编号
	private String orgPoints;//积分机构的积分额
	private String phone;//客户在积分机构的手机号
	
	private String realName; //银行实名信息
	private String bankId; //银行编号
	private String bankName; //银行名称
	private String bankCard; //银行卡号
	private String businessType;//业务类型
	
	//--------------------------------------------------------------构造参数列表限定
	
	
	
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
	 *<p> 描述: *-- <b>消费积分  必填的参数列表构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsReq(String objectNo,String accountId,String points,String accountMarked,String partnerNo){
		this.accountMarked = accountMarked;
		this.objectNo = objectNo;
		this.accountId = accountId;
		this.points = points;
		this.partnerNo = partnerNo;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>退还积分  必填的参数列表构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsReq(String objectNo,String accountId,String points,String accountMarked,String payPointsNo,String partnerNo){
		this.accountMarked = accountMarked;
		this.objectNo = objectNo;
		this.accountId = accountId;
		this.points = points;
		this.payPointsNo=payPointsNo;
		this.partnerNo = partnerNo;
	}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>赠送积分  必填的参数列表构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsReq(String orgNo,String accountMarked,String accountMarkedType,String objectNo,String objectDes,String objectType,String remark,String points,String partnerNo){
		this.orgNo = orgNo;
		this.accountMarked = accountMarked;
		this.accountMarkedType = accountMarkedType;
		this.objectNo = objectNo;
		this.objectDes = objectDes;
		this.objectType = objectType;
		this.remark = remark;
		this.points = points;
		this.partnerNo = partnerNo;
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
	public PointsReq(String orgNo,String accountMarked,String accountMarkedType,String realName,String bankId,String bankName,String bankCard,
			String objectNo,String objectDes,String objectType,String points,String businessType,String remark,String partnerNo){
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

	
}
