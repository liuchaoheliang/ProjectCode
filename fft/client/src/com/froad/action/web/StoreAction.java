package com.froad.action.web;

import java.util.ArrayList;
import java.util.List;


import com.froad.action.support.GoodsExchangeRackActionSupport;
import com.froad.action.support.GoodsGroupRackActionSupport;
import com.froad.action.support.MemberCollectActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantTrainActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.goodsGroupRack.GoodsGroupRack;
import com.froad.client.memberCollect.MemberCollect;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantTrain.MerchantTrain;
import com.froad.util.command.MallCommand;

/**
 * *******************************************************
 * @项目名称: communityBusiness_client 
 * @类名: StoreAction.java  
 * @功能描述: 新版商户分页测试 
 * @作者: 赵肖瑶
 * @日期: 2013-6-27 下午03:57:13
 *********************************************************
 */
public class StoreAction extends BaseActionSupport {
	
	private static final long serialVersionUID = -5536663963185404110L;
	
	private GoodsGroupRackActionSupport goodsGroupRackActionSupport;
	private GoodsExchangeRackActionSupport goodsExchangeRackActionSupport;
	private MemberCollectActionSupport memberCollectActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private MerchantTrainActionSupport merchantTrainActionSupport;
	private StoreActionSupport storeActionSupport;
	private Store pager = new Store();
	private MemberCollect memberCollectPager = new MemberCollect();
	private MerchantTrain merchantTrain;
	private Merchant merchant;
	private List<GoodsGroupRack> goodsGroupRackList;
	private List<GoodsExchangeRack> goodsExchangeRackList;
	//======================================================== 商户新版展示功能 
	//分店查询
	public String merchantAnnexInfo(){
		
		//点击人数查询
		merchantTrain=merchantTrainActionSupport.getMerchantTrainByMerchantId(pager.getMerchantId()+"",null);
		//商户相关信息查询
		merchant=merchantActionSupport.getMerchantById(pager.getMerchantId()+"");
		
		//当前用户的ID
		String userId=(String) getSession(MallCommand.USER_ID);
		if(userId!=null){	
			//获得登录用户收藏的商家集合
			memberCollectPager.setUserid(userId);
			memberCollectPager.setMerchantId(pager.getMerchantId()+"");
			memberCollectPager=memberCollectActionSupport.getMemberCollectByPager(memberCollectPager);
		}
		
		GoodsGroupRack ggr = new GoodsGroupRack();
		ggr.setMerchantId(pager.getMerchantId()+"");
		ggr.setPageSize(2);
		ggr.setIsRack("1");
		ggr.setState("30");
		//查询该商户下的团购商品
		List<Object> list = goodsGroupRackActionSupport.findGoodsGroupByPager(ggr).getList();
		goodsGroupRackList = new ArrayList<GoodsGroupRack>();
		for (Object object : list) {
			goodsGroupRackList.add((GoodsGroupRack)object);
		}
		
		//查询该商户下的积分兑换商品
		GoodsExchangeRack ger = new GoodsExchangeRack();
		ger.setMerchantId(pager.getMerchantId()+"");
		ger.setState("30");
		ger.setIsRack("1");
		ger.setPageSize(2);
		List<Object> objList = goodsExchangeRackActionSupport.getGoodsExchangeRacks(ger).getList();
		goodsExchangeRackList = new ArrayList<GoodsExchangeRack>();
		for (Object object : objList) {
			goodsExchangeRackList.add((GoodsExchangeRack)object);
		}
		
		
		//分店信息查询 begin
		pager.setPageSize(10);
		pager = storeActionSupport.getStoreByPager(pager);
		//分店信息查询 over		
		return SUCCESS;
	}

	public Store getPager() {
		return pager;
	}

	public void setPager(Store pager) {
		this.pager = pager;
	}

	public MemberCollectActionSupport getMemberCollectActionSupport() {
		return memberCollectActionSupport;
	}

	public void setMemberCollectActionSupport(
			MemberCollectActionSupport memberCollectActionSupport) {
		this.memberCollectActionSupport = memberCollectActionSupport;
	}

	public MemberCollect getMemberCollectPager() {
		return memberCollectPager;
	}

	public void setMemberCollectPager(MemberCollect memberCollectPager) {
		this.memberCollectPager = memberCollectPager;
	}

	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	public MerchantTrain getMerchantTrain() {
		return merchantTrain;
	}

	public void setMerchantTrain(MerchantTrain merchantTrain) {
		this.merchantTrain = merchantTrain;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public GoodsGroupRackActionSupport getGoodsGroupRackActionSupport() {
		return goodsGroupRackActionSupport;
	}

	public void setGoodsGroupRackActionSupport(
			GoodsGroupRackActionSupport goodsGroupRackActionSupport) {
		this.goodsGroupRackActionSupport = goodsGroupRackActionSupport;
	}

	public GoodsExchangeRackActionSupport getGoodsExchangeRackActionSupport() {
		return goodsExchangeRackActionSupport;
	}

	public void setGoodsExchangeRackActionSupport(
			GoodsExchangeRackActionSupport goodsExchangeRackActionSupport) {
		this.goodsExchangeRackActionSupport = goodsExchangeRackActionSupport;
	}

	public List<GoodsGroupRack> getGoodsGroupRackList() {
		return goodsGroupRackList;
	}

	public void setGoodsGroupRackList(List<GoodsGroupRack> goodsGroupRackList) {
		this.goodsGroupRackList = goodsGroupRackList;
	}

	public List<GoodsExchangeRack> getGoodsExchangeRackList() {
		return goodsExchangeRackList;
	}

	public void setGoodsExchangeRackList(
			List<GoodsExchangeRack> goodsExchangeRackList) {
		this.goodsExchangeRackList = goodsExchangeRackList;
	}

	public StoreActionSupport getStoreActionSupport() {
		return storeActionSupport;
	}

	public void setStoreActionSupport(StoreActionSupport storeActionSupport) {
		this.storeActionSupport = storeActionSupport;
	}

	public MerchantTrainActionSupport getMerchantTrainActionSupport() {
		return merchantTrainActionSupport;
	}

	public void setMerchantTrainActionSupport(
			MerchantTrainActionSupport merchantTrainActionSupport) {
		this.merchantTrainActionSupport = merchantTrainActionSupport;
	}
	
}
