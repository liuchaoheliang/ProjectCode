package com.froad.action.web;

import java.util.List;

import com.froad.action.support.GoodsActionSupport;
import com.froad.action.support.GoodsRebateRackActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.SellersActionSupport;
import com.froad.action.support.TagActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.goods.Goods;
import com.froad.client.goodsRebateRack.GoodsRebateRack;
import com.froad.client.merchant.Merchant;
import com.froad.client.sellers.Seller;
import com.froad.client.trans.Trans;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.command.TransType;

/** 
 * @author FQ 
 * @date 2013-2-19 下午03:50:25
 * @version 1.0
 * 积分返利
 */
public class RebateAction extends BaseActionSupport {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -1730472629521652624L;
	private TagActionSupport tagActionSupport;
	private TransActionSupport transActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private GoodsRebateRackActionSupport goodsRebateRackActionSupport;
	private GoodsActionSupport goodsActionSupport;
	private SellersActionSupport sellersActionSupport;
	private Goods good;
	private Merchant merchant;
	private GoodsRebateRack pager;
	private Trans trans;
	
	/**
	 * 返利商品列表页面
	 * @return
	 */
	public String getGoodsRebateList(){
		if(Assert.empty(id)){
			return "failse";
		}
		if(pager == null){
			pager = new GoodsRebateRack();
		}
		
		Seller seller=new Seller();
		seller.setMerchantId(id);
		seller.setSellerType(TransType.Trans_Points.getValue());
		seller.setState(Command.FBU_USERED_STATE);
		List<Seller> sellerList=sellersActionSupport.getSellerListBySelective(seller);
		
		if(sellerList!=null && !sellerList.isEmpty() ){
			Seller sel=sellerList.get(0);
			pager.setPageSize(12);
			pager.setState(Command.FBU_USERED_STATE);
			pager.setSellerId(sel.getId());//商户ID	
			pager=goodsRebateRackActionSupport.getGoodsRebateRackByPager(pager);
		}
		
		merchant = merchantActionSupport.getMerchantById(id);
		return "merchant_goods_rebate_list";
	}
	
	/**
	 * 返利商品详情页面
	 * @return
	 */
	public String getMerchantGoodsRebateInfo(){
		good = goodsActionSupport.getGoodById(id);
		if(good == null){
			good = new Goods();
		}
		return "merchant_goods_rebate_info";
	}
	
	/**
	 * 查询积分返利交易列表
	 * @return String
	 */
	public String rebateTransList(){
		//查询积分返利交易列表
		return "success";
	}
	
	
	/**
	 * 查询交易详情
	 * @return
	 */
	public String getRebateDetailInfo(){
		if(trans == null || Assert.empty(trans.getId())){
			trans = new Trans();
			return "success";
		}
		trans = transActionSupport.getTransById(trans.getId());
		if(trans == null){
			trans = new Trans();
		}
		return "success";
	}
	
	public TagActionSupport getTagActionSupport() {
		return tagActionSupport;
	}
	public void setTagActionSupport(TagActionSupport tagActionSupport) {
		this.tagActionSupport = tagActionSupport;
	}
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	public GoodsRebateRack getPager() {
		return pager;
	}

	public void setPager(GoodsRebateRack pager) {
		this.pager = pager;
	}

	public GoodsRebateRackActionSupport getGoodsRebateRackActionSupport() {
		return goodsRebateRackActionSupport;
	}

	public void setGoodsRebateRackActionSupport(
			GoodsRebateRackActionSupport goodsRebateRackActionSupport) {
		this.goodsRebateRackActionSupport = goodsRebateRackActionSupport;
	}

	public Goods getGood() {
		return good;
	}

	public void setGood(Goods good) {
		this.good = good;
	}

	public GoodsActionSupport getGoodsActionSupport() {
		return goodsActionSupport;
	}

	public void setGoodsActionSupport(GoodsActionSupport goodsActionSupport) {
		this.goodsActionSupport = goodsActionSupport;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}
	public SellersActionSupport getSellersActionSupport() {
		return sellersActionSupport;
	}

	public void setSellersActionSupport(SellersActionSupport sellersActionSupport) {
		this.sellersActionSupport = sellersActionSupport;
	}

	
}
