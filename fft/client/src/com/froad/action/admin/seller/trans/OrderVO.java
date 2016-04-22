package com.froad.action.admin.seller.trans;

import java.util.List;

import com.froad.client.buyers.BuyerChannel;
import com.froad.client.trans.GoodsGatherRack;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;


public class OrderVO {
	private String trackNo;
	private String trackNoOfTransPoints;
	private String merchantId;
	private String buyerPhone;
	private TransDetails tranDetail;
	private Trans tran;
	private Double fftToPay;
	private String orderTypeId;
	private List<TransDetails> allTranDetails;
	private List<BuyerChannel> buyerPayChannelList;
	private List<SellerChannel> sellerAccountList;
	private String sellerAccountIdOfCurrentUsed;
	private String sellerAccountNumberOfCurrentUsed;
//	private TransPoints transPoints;
	private String points;//赠送积分数
	private String payChannel;//支付渠道 1：手机银行卡 2：刷卡 3：现金
	private String tranRealCurrencyVal;
	private String toSendPoints;
	private String isPresentPoints;//是赠送积分 0:是;1否,仅是录入交易信息和赠送积分,并不进行收款
	
	private GoodsGatherRack goodsGatherRack;
	
	
	public String getIsPresentPoints() {
		return isPresentPoints;
	}
	public void setIsPresentPoints(String isPresentPoints) {
		this.isPresentPoints = isPresentPoints;
	}
	public GoodsGatherRack getGoodsGatherRack() {
		return goodsGatherRack;
	}
	public void setGoodsGatherRack(GoodsGatherRack goodsGatherRack) {
		this.goodsGatherRack = goodsGatherRack;
	}
	public String getToSendPoints() {
		return toSendPoints;
	}
	public void setToSendPoints(String toSendPoints) {
		this.toSendPoints = toSendPoints;
	}
	public String getTranRealCurrencyVal() {
		return tranRealCurrencyVal;
	}
	public void setTranRealCurrencyVal(String tranRealCurrencyVal) {
		this.tranRealCurrencyVal = tranRealCurrencyVal;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public String getSellerAccountNumberOfCurrentUsed() {
		return sellerAccountNumberOfCurrentUsed;
	}
	public void setSellerAccountNumberOfCurrentUsed(
			String sellerAccountNumberOfCurrentUsed) {
		this.sellerAccountNumberOfCurrentUsed = sellerAccountNumberOfCurrentUsed;
	}
	public String getTrackNoOfTransPoints() {
		return trackNoOfTransPoints;
	}
	public void setTrackNoOfTransPoints(String trackNoOfTransPoints) {
		this.trackNoOfTransPoints = trackNoOfTransPoints;
	}
//	public TransPoints getTransPoints() {
//		return transPoints;
//	}
//	public void setTransPoints(TransPoints transPoints) {
//		this.transPoints = transPoints;
//	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
//	public String getProduct() {
//		return product;
//	}
//	public void setProduct(String product) {
//		this.product = product;
//	}
	
	
	public Double getFftToPay() {
		return fftToPay;
	}
	public TransDetails getTranDetail() {
		return tranDetail;
	}
	public void setTranDetail(TransDetails tranDetail) {
		this.tranDetail = tranDetail;
	}
	public Trans getTran() {
		return tran;
	}
	public void setTran(Trans tran) {
		this.tran = tran;
	}
	
	public void setFftToPay(Double fftToPay) {
		this.fftToPay = fftToPay;
	}
	public String getTrackNo() {
		return trackNo;
	}
	public void setTrackNo(String trackNo) {
		this.trackNo = trackNo;
	}
	public String getOrderTypeId() {
		return orderTypeId;
	}
	public void setOrderTypeId(String orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	
	public String getSellerAccountIdOfCurrentUsed() {
		return sellerAccountIdOfCurrentUsed;
	}
	public void setSellerAccountIdOfCurrentUsed(String sellerAccountIdOfCurrentUsed) {
		this.sellerAccountIdOfCurrentUsed = sellerAccountIdOfCurrentUsed;
	}
	public List<TransDetails> getAllTranDetails() {
		return allTranDetails;
	}
	public void setAllTranDetails(List<TransDetails> allTranDetails) {
		this.allTranDetails = allTranDetails;
	}
	public List<BuyerChannel> getBuyerPayChannelList() {
		return buyerPayChannelList;
	}
	public void setBuyerPayChannelList(
			List<BuyerChannel> buyerPayChannelList) {
		this.buyerPayChannelList = buyerPayChannelList;
	}
	public List<SellerChannel> getSellerAccountList() {
		return sellerAccountList;
	}
	public void setSellerAccountList(List<SellerChannel> sellerAccountList) {
		this.sellerAccountList = sellerAccountList;
	}
	
	
	
	
	
	
}
