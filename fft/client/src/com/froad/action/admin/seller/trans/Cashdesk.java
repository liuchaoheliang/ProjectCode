package com.froad.action.admin.seller.trans;

import java.util.List;
import java.util.Map;

import com.froad.client.sellers.SellerChannel;
import com.froad.client.sellers.Seller;
import com.froad.client.transRule.TransRule;


public class Cashdesk {
	private List<SellerChannel> sellerDepositChannelList;
	private TransRule sellerRule;
	private Seller seller;
	private Map<String,SellerChannel> sellerAccountMap;//KEY=sellerAccountID,VALUE=sellerAccount
	public List<SellerChannel> getSellerDepositChannelList() {
		return sellerDepositChannelList;
	}
	public void setSellerDepositChannelList(
			List<SellerChannel> sellerDepositChannelList) {
		this.sellerDepositChannelList = sellerDepositChannelList;
	}
	public Seller getSeller() {
		return seller;
	}
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	public Map<String, SellerChannel> getSellerAccountMap() {
		return sellerAccountMap;
	}
	public void setSellerAccountMap(Map<String, SellerChannel> sellerAccountMap) {
		this.sellerAccountMap = sellerAccountMap;
	}
	public TransRule getSellerRule() {
		return sellerRule;
	}
	public void setSellerRule(TransRule sellerRule) {
		this.sellerRule = sellerRule;
	}
	
	
	
	
}
