package com.froad.CB.po;

import java.util.ArrayList;
import java.util.List;

import com.froad.CB.common.Pager;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.user.User;

public class Trans extends Pager{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Buyers buyer;//关联出的买家	
	private Integer id;	
	private String refundOrderId;//退款订单号
	private List<AuthTicket> authTicketList;//关联出来的 团购认证券
	private String trackNo;	 //业务跟踪号
	private String billNo;	 //账单平台号
	private String pointBillNo;//积分平台号
	private String lotteryBillNo;//彩票交易返回的第三方交易号
	private String transSn;	 //交易编号
	private String transType;//交易类型 参见TranCommand里的交易类型transType
	private String sellerType;//卖家类型 同TranCommand里的交易类型transType
	private String payMethod;//支付方式 见TranCommand里的支付方式payMethod
	private String sellerId;	//卖家ID	
	private Seller seller;//关联出的seller
	private Integer merchantId;//商户编号
	private Merchant merchant;//关联出的merchant
	private String buyersId;	//买家ID
	private String userId;//用户编号 给用户返积分时，收积分的用户编号
	private User user;
	private String channelId;//fundsChannel里的id
	private String sellerChannelId;//卖家收款渠道ID
	private SellerChannel sellerChannel;//关联出的 卖家收款渠道
	private String buyerChannelId;//买家支付渠道ID
	private BuyerChannel buyerChannel;//关联出的 买家支付渠道
	private String currency;	//货币单位
	private String costpriceTotal;		//原价总和
	private String currencyValueAll;	//总货币值，所有交易明细累计
	private String currencyValueRealAll;//总实际货币数值，由规则计算出,买家实际支付金额
	private String bankPointsAccount;	//银行积分账户
	private String bankPointsValueAll;	//银行总积分值，所有交易明细累计
	private String bankPointsValueRealAll;//银行总实际积分数值，由规则计算出
	private String fftPointsAccount; //分分通积分账户		
	private String fftPointsValueAll;//分分通总积分值，所有交易明细累计
	private String fftPointsValueRealAll;//分分通总实际积分数值，由规则计算出
	private String failureTime;//失效时间
	private String state;	   //状态
	private String createTime; //创建时间
	private String updateTime; //更新时间
	private String remark;	   //备注
	private String clientType;//客户端类型 100-pc 200-android 300-iphone	
	private String payChannel;//支付渠道{20:贴膜卡支付 30:银行卡支付}
	private String fftFactorage;//方付通手续费(提现手续费或卖家买积分手续费)
	private String belongUserBecode;//交易归属
	private String phone;//用于接收券短信的手机号
	private String pointsAmountValue;//卖家买积分所需总金额(总金额=实际金额+手续费)
	private String pointsAmountRealValue;//卖家买积分所需实际金额
	private String virtualType;//虚拟商品的类型 值与GoodsCategory表里的id一致
	private String presentPointsValue;//赠送积分数(积分兑换商品)
	private List<Pay> payList=new ArrayList<Pay>();
	private List<TransGoodsAttribute> transGoodsAttrList=new ArrayList<TransGoodsAttribute>();
	private List<TransDetails> transDetailsList=new ArrayList<TransDetails>();
	private GoodsGatherRack goodsGatherRack;
	
	//用于查询条件
	private String isConsume;//是否消费 0-否 1-是(与AuthTicket的此属性一致)
	//查询出团购和兑换的相关认证券
	private String authTicketId;//认证券ID
	private String securitiesNo;//认证券
	private String smsNumber;//发送团购券次数
	private String consumeTime;	//消费时间
	private String belongUserBecodeAuth;//兑换券里面保存的操作员信息	
	
	private List<Integer> sellerIdList;
	
	private String respCode;
	
	private String respMsg;
	
	private List<String> payMethodList;//payMethod 作查询条件用的字段:用在transType字段
	private List<String> payStateList;//用于按支付状态查交易
	
	private List<String> MerchantIdList;//用于mgmt根据商户名称查询商户的交易
	
	private String paymentUrl;//用于支付宝支付的瞬态字段
	
	//=================财务相关临时字段
	
	private String beCode; //操作员工号
	private String beName; //操作员昵称
	private String storeStortName; //所属分店短名
	private String financeExcel;
	private String goodsName; //商品名称
	
	private Integer presentRuleId;//商品对应送分规则Id

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public List<String> getPayMethodList() {
		return payMethodList;
	}

	public void setPayMethodList(List<String> payMethodList) {
		this.payMethodList = payMethodList;
	}
	
	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public List<Pay> getPayList() {
		return payList;
	}

	public void setPayList(List<Pay> payList) {
		this.payList = payList;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTrackNo() {
		return trackNo;
	}

	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo == null ? null : trackNo.trim();
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo == null ? null : billNo.trim();
	}

	public String getTransSn() {
		return transSn;
	}

	public void setTransSn(String transSn) {
		this.transSn = transSn == null ? null : transSn.trim();
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType == null ? null : transType.trim();
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId == null ? null : sellerId.trim();
	}

	public String getBuyersId() {
		return buyersId;
	}

	public void setBuyersId(String buyersId) {
		this.buyersId = buyersId == null ? null : buyersId.trim();
	}

	public String getBuyerChannelId() {
		return buyerChannelId;
	}

	public void setBuyerChannelId(String buyerChannelId) {
		this.buyerChannelId = buyerChannelId == null ? null
				: buyerChannelId.trim();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public String getCostpriceTotal() {
		return costpriceTotal;
	}

	public void setCostpriceTotal(String costpriceTotal) {
		this.costpriceTotal = costpriceTotal == null ? null : costpriceTotal
				.trim();
	}

	public String getCurrencyValueAll() {
		return currencyValueAll;
	}

	public void setCurrencyValueAll(String currencyValueAll) {
		this.currencyValueAll = currencyValueAll == null ? null
				: currencyValueAll.trim();
	}

	public String getCurrencyValueRealAll() {
		return currencyValueRealAll;
	}

	public void setCurrencyValueRealAll(String currencyValueRealAll) {
		this.currencyValueRealAll = currencyValueRealAll == null ? null
				: currencyValueRealAll.trim();
	}

	public String getBankPointsAccount() {
		return bankPointsAccount;
	}

	public void setBankPointsAccount(String bankPointsAccount) {
		this.bankPointsAccount = bankPointsAccount == null ? null
				: bankPointsAccount.trim();
	}

	public String getBankPointsValueAll() {
		return bankPointsValueAll;
	}

	public void setBankPointsValueAll(String bankPointsValueAll) {
		this.bankPointsValueAll = bankPointsValueAll == null ? null
				: bankPointsValueAll.trim();
	}

	public String getBankPointsValueRealAll() {
		return bankPointsValueRealAll;
	}

	public void setBankPointsValueRealAll(String bankPointsValueRealAll) {
		this.bankPointsValueRealAll = bankPointsValueRealAll == null ? null
				: bankPointsValueRealAll.trim();
	}

	public String getFftPointsAccount() {
		return fftPointsAccount;
	}

	public void setFftPointsAccount(String fftPointsAccount) {
		this.fftPointsAccount = fftPointsAccount == null ? null
				: fftPointsAccount.trim();
	}

	public String getFftPointsValueAll() {
		return fftPointsValueAll;
	}

	public void setFftPointsValueAll(String fftPointsValueAll) {
		this.fftPointsValueAll = fftPointsValueAll == null ? null
				: fftPointsValueAll.trim();
	}

	public String getFftPointsValueRealAll() {
		return fftPointsValueRealAll;
	}

	public void setFftPointsValueRealAll(String fftPointsValueRealAll) {
		this.fftPointsValueRealAll = fftPointsValueRealAll == null ? null
				: fftPointsValueRealAll.trim();
	}

	public String getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime == null ? null : failureTime.trim();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state == null ? null : state.trim();
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime == null ? null : createTime.trim();
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime == null ? null : updateTime.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public List<TransDetails> getTransDetailsList() {
		return transDetailsList;
	}

	public void setTransDetailsList(List<TransDetails> transDetailsList) {
		this.transDetailsList = transDetailsList;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public List<TransGoodsAttribute> getTransGoodsAttrList() {
		return transGoodsAttrList;
	}

	public void setTransGoodsAttrList(List<TransGoodsAttribute> transGoodsAttrList) {
		this.transGoodsAttrList = transGoodsAttrList;
	}

	public SellerChannel getSellerChannel() {
		return sellerChannel;
	}

	public void setSellerChannel(SellerChannel sellerChannel) {
		this.sellerChannel = sellerChannel;
	}

	public BuyerChannel getBuyerChannel() {
		return buyerChannel;
	}

	public void setBuyerChannel(BuyerChannel buyerChannel) {
		this.buyerChannel = buyerChannel;
	}

	public String getFftFactorage() {
		return fftFactorage;
	}

	public void setFftFactorage(String fftFactorage) {
		this.fftFactorage = fftFactorage;
	}

	public String getSellerChannelId() {
		return sellerChannelId;
	}

	public void setSellerChannelId(String sellerChannelId) {
		this.sellerChannelId = sellerChannelId;
	}

	public Buyers getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyers buyer) {
		this.buyer = buyer;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getPointBillNo() {
		return pointBillNo;
	}

	public void setPointBillNo(String pointBillNo) {
		this.pointBillNo = pointBillNo;
	}


	public String getIsConsume() {
		return isConsume;
	}

	public void setIsConsume(String isConsume) {
		this.isConsume = isConsume;
	}

	public GoodsGatherRack getGoodsGatherRack() {
		return goodsGatherRack;
	}

	public void setGoodsGatherRack(GoodsGatherRack goodsGatherRack) {
		this.goodsGatherRack = goodsGatherRack;
	}

	public String getBelongUserBecode() {
		return belongUserBecode;
	}

	public void setBelongUserBecode(String belongUserBecode) {
		this.belongUserBecode = belongUserBecode;
	}

	public String getLotteryBillNo() {
		return lotteryBillNo;
	}

	public void setLotteryBillNo(String lotteryBillNo) {
		this.lotteryBillNo = lotteryBillNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String getSellerType() {
		return sellerType;
	}

	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
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

	public List<Integer> getSellerIdList() {
		return sellerIdList;
	}

	public void setSellerIdList(List<Integer> sellerIdList) {
		this.sellerIdList = sellerIdList;
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

	public String getVirtualType() {
		return virtualType;
	}

	public void setVirtualType(String virtualType) {
		this.virtualType = virtualType;
	}

	public String getAuthTicketId() {
		return authTicketId;
	}

	public void setAuthTicketId(String authTicketId) {
		this.authTicketId = authTicketId;
	}

	public String getSecuritiesNo() {
		return securitiesNo;
	}

	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo;
	}

	public String getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}

	public List<AuthTicket> getAuthTicketList() {
		return authTicketList;
	}

	public void setAuthTicketList(List<AuthTicket> authTicketList) {
		this.authTicketList = authTicketList;
	}

	public String getConsumeTime() {
		return consumeTime;
	}

	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}

	public List<String> getPayStateList() {
		return payStateList;
	}

	public void setPayStateList(List<String> payStateList) {
		this.payStateList = payStateList;
	}

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public String getBelongUserBecodeAuth() {
		return belongUserBecodeAuth;
	}

	public void setBelongUserBecodeAuth(String belongUserBecodeAuth) {
		this.belongUserBecodeAuth = belongUserBecodeAuth;
	}

	public String getBeCode() {
		return beCode;
	}

	public void setBeCode(String beCode) {
		this.beCode = beCode;
	}

	public String getBeName() {
		return beName;
	}

	public void setBeName(String beName) {
		this.beName = beName;
	}

	public String getStoreStortName() {
		return storeStortName;
	}

	public void setStoreStortName(String storeStortName) {
		this.storeStortName = storeStortName;
	}

	public String getFinanceExcel() {
		return financeExcel;
	}

	public void setFinanceExcel(String financeExcel) {
		this.financeExcel = financeExcel;
	}

	public List<String> getMerchantIdList() {
		return MerchantIdList;
	}

	public void setMerchantIdList(List<String> merchantIdList) {
		MerchantIdList = merchantIdList;
	}

	public String getPresentPointsValue() {
		return presentPointsValue;
	}

	public void setPresentPointsValue(String presentPointsValue) {
		this.presentPointsValue = presentPointsValue;
	}

	public Integer getPresentRuleId() {
		return presentRuleId;
	}

	public void setPresentRuleId(Integer presentRuleId) {
		this.presentRuleId = presentRuleId;
	}

	public String getPaymentUrl() {
		return paymentUrl;
	}

	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}	
	

}