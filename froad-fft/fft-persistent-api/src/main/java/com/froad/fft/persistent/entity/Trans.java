package com.froad.fft.persistent.entity;


import java.util.List;

import com.froad.fft.persistent.common.enums.PayChannel;
import com.froad.fft.persistent.common.enums.PayMethod;
import com.froad.fft.persistent.common.enums.TransCreateSource;
import com.froad.fft.persistent.common.enums.TransPayState;
import com.froad.fft.persistent.common.enums.TransState;
import com.froad.fft.persistent.common.enums.TransType;

/**
 * 类描述：交易实体
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 李金魁 lijinkui@f-road.com.cn
 * @time: 2014年2月13日 下午4:20:53
 */
public class Trans extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String sn;//编号
	private TransCreateSource createSource;// 交易创建来源
	private TransType type;// 交易类型
	private PayMethod payMethod;// 支付方式
	private PayChannel payChannel;//支付渠道
	private TransState state;// 交易状态
	private Long memberCode;// 账户服务ID(买东西的用户或者返积分的用户)
	private TransPayState payState;//支付状态
	private Long clientId;//客户端编号(SysClient主键)
	private String clientNumber;//三位数的客户端号(瞬态字段)
	
	//现金或者积分
	private String totalPrice;// 总货币值
	private String realPrice;// 实际总货币数值
	private String fftPoints;// 消费或提现的分分通积分
	private String bankPoints;// 银行积分
	private String pointsAmountValue;//买积分所需总金额(总金额=实际金额+手续费)
	private String pointsAmountRealValue;//买积分所需实际金额
	private String buyPoints;//购买的积分数
	private String buyPointsFactorage;// 买积分手续费
	private String gatheringValue;//收款金额
	private String pointsWithdrawFactorage;// 积分提现手续费
	private String givePoints; // 赠送积分数(积分兑换商品送积分)
	
	private Long merchantId;//商户编号(商户发起的交易)
	
	private String reason;//事由
	
	private String filmMobile;//付款贴膜卡手机号
	
	private String phone;//用于接收券短信的手机号
	private String remark;//备注
	
	private Long deliveryId;//提货点编号
	private String deliveryName;//提货人姓名
	
	/**用于传参的瞬态字段**/
	private Member member;
	
	/***与交易实体相关联的实体**/
	private List<TransDetails> detailsList;
	private List<Pay> payList;
	
    private List<TransPayState> payStates;
	
	private List<Long> sysUserIds; 
	
	public List<TransPayState> getPayStates() {
		return payStates;
	}
	public void setPayStates(List<TransPayState> payStates) {
		this.payStates = payStates;
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getRealPrice() {
		return realPrice;
	}
	public void setRealPrice(String realPrice) {
		this.realPrice = realPrice;
	}
	public String getBuyPoints() {
		return buyPoints;
	}
	public void setBuyPoints(String buyPoints) {
		this.buyPoints = buyPoints;
	}
	public String getBuyPointsFactorage() {
		return buyPointsFactorage;
	}
	public void setBuyPointsFactorage(String buyPointsFactorage) {
		this.buyPointsFactorage = buyPointsFactorage;
	}
	public String getGatheringValue() {
		return gatheringValue;
	}
	public void setGatheringValue(String gatheringValue) {
		this.gatheringValue = gatheringValue;
	}
	public String getPointsWithdrawFactorage() {
		return pointsWithdrawFactorage;
	}
	public void setPointsWithdrawFactorage(String pointsWithdrawFactorage) {
		this.pointsWithdrawFactorage = pointsWithdrawFactorage;
	}
	public String getGivePoints() {
		return givePoints;
	}
	public void setGivePoints(String givePoints) {
		this.givePoints = givePoints;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getFilmMobile() {
		return filmMobile;
	}
	public void setFilmMobile(String filmMobile) {
		this.filmMobile = filmMobile;
	}
	
	public TransCreateSource getCreateSource() {
		return createSource;
	}
	public void setCreateSource(TransCreateSource createSource) {
		this.createSource = createSource;
	}
	public TransType getType() {
		return type;
	}
	public void setType(TransType type) {
		this.type = type;
	}
	public PayMethod getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(PayMethod payMethod) {
		this.payMethod = payMethod;
	}
	public PayChannel getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(PayChannel payChannel) {
		this.payChannel = payChannel;
	}
	public TransState getState() {
		return state;
	}
	public void setState(TransState state) {
		this.state = state;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public TransPayState getPayState() {
		return payState;
	}
	public void setPayState(TransPayState payState) {
		this.payState = payState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPhone() {
		return phone==null?null:phone.trim();
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPointsAmountValue() {
		return pointsAmountValue;
	}
	public void setPointsAmountValue(String pointsAmountValue) {
		this.pointsAmountValue = pointsAmountValue;
	}
	public String getPointsAmountRealValue() {
		return pointsAmountRealValue;
	}
	public void setPointsAmountRealValue(String pointsAmountRealValue) {
		this.pointsAmountRealValue = pointsAmountRealValue;
	}
	public String getBankPoints() {
		return bankPoints;
	}
	public void setBankPoints(String bankPoints) {
		this.bankPoints = bankPoints;
	}
	public String getFftPoints() {
		return fftPoints;
	}
	public void setFftPoints(String fftPoints) {
		this.fftPoints = fftPoints;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Long getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(Long deliveryId) {
		this.deliveryId = deliveryId;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getClientNumber() {
		return clientNumber;
	}
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}
	public List<TransDetails> getDetailsList() {
		return detailsList;
	}
	public void setDetailsList(List<TransDetails> detailsList) {
		this.detailsList = detailsList;
	}
	public List<Pay> getPayList() {
		return payList;
	}
	public void setPayList(List<Pay> payList) {
		this.payList = payList;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public List<Long> getSysUserIds() {
		return sysUserIds;
	}
	public void setSysUserIds(List<Long> sysUserIds) {
		this.sysUserIds = sysUserIds;
	}
	
	
}
