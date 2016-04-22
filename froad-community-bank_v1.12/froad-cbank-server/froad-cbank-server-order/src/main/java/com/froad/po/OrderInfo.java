package com.froad.po;

import java.util.List;

import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.shoppingcart.req.OrderShoppingListReq;
import com.froad.thrift.vo.order.DeliverInfoDetailVo;
import com.froad.thrift.vo.order.MerchantReturnVo;

public class OrderInfo {
	/**
	 * 订单
	 */
	OrderMongo order;
	
	/**
	 * 子订单
	 */
	List<SubOrderMongo> subOrderList;
	
	/**
	 * 库存
	 */
	List<Store> storeList;
	
	/**
	 * 购物车
	 */
	List<OrderShoppingListReq> shopingListReq;
	
	/** 订单创建返回信息（订单确认信息） */
	/**
	 * 收货|提货详情
	 */
	DeliverInfoDetailVo deliverInfoDetailVo;
	
	/**
	 * 商户信息
	 */
	List<MerchantReturnVo> merchantReturnVoList;
	
	/**
	 * 线下网点礼品支付标识
	 */
	Boolean offlinePayFlag;
	
	/**
	 * 是否参与营销活动
	 */
	Boolean isJoinMarketActive;
	
	public OrderMongo getOrder() {
		return order;
	}
	public void setOrder(OrderMongo order) {
		this.order = order;
	}
	public List<SubOrderMongo> getSubOrderList() {
		return subOrderList;
	}
	public void setSubOrderList(List<SubOrderMongo> subOrderList) {
		this.subOrderList = subOrderList;
	}
	public List<Store> getStoreList() {
		return storeList;
	}
	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}
	public List<OrderShoppingListReq> getShopingListReq() {
		return shopingListReq;
	}
	public void setShopingListReq(List<OrderShoppingListReq> shopingListReq) {
		this.shopingListReq = shopingListReq;
	}
	public DeliverInfoDetailVo getDeliverInfoDetailVo() {
		return deliverInfoDetailVo;
	}
	public void setDeliverInfoDetailVo(DeliverInfoDetailVo deliverInfoDetailVo) {
		this.deliverInfoDetailVo = deliverInfoDetailVo;
	}
	public List<MerchantReturnVo> getMerchantReturnVoList() {
		return merchantReturnVoList;
	}
	public void setMerchantReturnVoList(List<MerchantReturnVo> merchantReturnVoList) {
		this.merchantReturnVoList = merchantReturnVoList;
	}
	public Boolean getOfflinePayFlag() {
		return offlinePayFlag;
	}
	public void setOfflinePayFlag(Boolean offlinePayFlag) {
		this.offlinePayFlag = offlinePayFlag;
	}
	public Boolean getIsJoinMarketActive() {
		return isJoinMarketActive;
	}
	public void setIsJoinMarketActive(Boolean isJoinMarketActive) {
		this.isJoinMarketActive = isJoinMarketActive;
	}
	
}
