package com.froad.CB.po.transaction;

import java.io.Serializable;
import java.util.List;


	/**
	 * 类描述：积分实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jan 5, 2013 3:53:02 PM 
	 */
public class Points implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private String orgNo;//积分机构编号
	private String accountMarked;//会员标识
	private String accountMarkedType;//会员标识类型
	private String partnerNo ;//合作伙伴ID
	private String resultCode ;//结果码
	private String charset ;//编码方式
	private String receiveReqTime;//接收到请求的时间
	private String signType ;//签名类型 
	private String signMsg ;//签名字符串	
	private List<PointsAccount> pointsAccountList;
	private WithdrawPoints withdrawPoints;//存积分提现申请的部分响应信息
	
	private String objectNo;//对象的编号
	private String objectDes;//对象的描述
	private String accountId;//积分账号
	private String points;//积分数
	
	private String payPointsNo;//积分消费号
	private String objectType;//对象的类型
	
	private String refundPointsNo;//退积分的流水号
	
	private String presentPointsNo;//增送积分号
	
	private String requestNo;//请求编号
	
	//积分兑充增加属性
	private String pointsCateNo;	//积分类型编号
	private String orgPoints;	//可以充的积分机构的积分
	private String mobilePhone;	//兑充积分手机号
	private String exchPointsNo;//积分平台中充积分记录的Id
	private String respCode;// 响应码,0表示交易成功,非0表示交易失败
	private String respMsg;// 响应信息
	
	//积分提现相关的属性
	private String realName;//银行实名信息
	private String bankId; //银行编号
	private String bankName; //银行名称
	private String bankCard; //银行卡号
	private String certType;//证件类型
	private String certNo;//证件号码
	private String businessType;//业务类型
	private String remark;//备注
	
	
	public Points(){}
	
	public String getOrgNo() {
		return orgNo;
	}
	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}
	public String getAccountMarked() {
		return accountMarked;
	}
	public void setAccountMarked(String accountMarked) {
		this.accountMarked = accountMarked;
	}
	public String getAccountMarkedType() {
		return accountMarkedType;
	}
	public void setAccountMarkedType(String accountMarkedType) {
		this.accountMarkedType = accountMarkedType;
	}
	public String getPartnerNo() {
		return partnerNo;
	}
	public void setPartnerNo(String partnerNo) {
		this.partnerNo = partnerNo;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getReceiveReqTime() {
		return receiveReqTime;
	}
	public void setReceiveReqTime(String receiveReqTime) {
		this.receiveReqTime = receiveReqTime;
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
	public List<PointsAccount> getPointsAccountList() {
		return pointsAccountList;
	}
	public void setPointsAccountList(List<PointsAccount> pointsAccountList) {
		this.pointsAccountList = pointsAccountList;
	}

	public String getObjectNo() {
		return objectNo;
	}

	public void setObjectNo(String objectNo) {
		this.objectNo = objectNo;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getObjectDes() {
		return objectDes;
	}

	public void setObjectDes(String objectDes) {
		this.objectDes = objectDes;
	}

	public String getPayPointsNo() {
		return payPointsNo;
	}

	public void setPayPointsNo(String payPointsNo) {
		this.payPointsNo = payPointsNo;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getRefundPointsNo() {
		return refundPointsNo;
	}

	public void setRefundPointsNo(String refundPointsNo) {
		this.refundPointsNo = refundPointsNo;
	}

	public String getPresentPointsNo() {
		return presentPointsNo;
	}

	public void setPresentPointsNo(String presentPointsNo) {
		this.presentPointsNo = presentPointsNo;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getPointsCateNo() {
		return pointsCateNo;
	}

	public void setPointsCateNo(String pointsCateNo) {
		this.pointsCateNo = pointsCateNo;
	}

	public String getOrgPoints() {
		return orgPoints;
	}

	public void setOrgPoints(String orgPoints) {
		this.orgPoints = orgPoints;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getExchPointsNo() {
		return exchPointsNo;
	}

	public void setExchPointsNo(String exchPointsNo) {
		this.exchPointsNo = exchPointsNo;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WithdrawPoints getWithdrawPoints() {
		return withdrawPoints;
	}

	public void setWithdrawPoints(WithdrawPoints withdrawPoints) {
		this.withdrawPoints = withdrawPoints;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

}
