package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.goodsRebateRack.GoodsRebateRack;
import com.froad.client.goodsRebateRack.GoodsRebateRackService;
import com.froad.client.merchant.Merchant;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 返利 client service  ActionSupport
 */
public class GoodsRebateRackActionSupport {
	private static Logger logger = Logger.getLogger(GoodsRebateRackActionSupport.class);
	private GoodsRebateRackService goodsRebateRackService;
	
	/**
	 * 分页查询 商户积分返利上架商品列表
	 * @param pager
	 * @return
	 */
	public GoodsRebateRack getGoodsRebateRackByPager(GoodsRebateRack goodsRebateRack){
		try{
			goodsRebateRack=goodsRebateRackService.getGoodsRebateRackByPager(goodsRebateRack);
		}
		catch(Exception e){
			logger.error("GoodsRebateRackActionSupport.getGoodsRebateRackByPager分页查找商户上架商品异常 商户ID："+goodsRebateRack.getGoods().getMerchantId(), e);
		}
		return goodsRebateRack;
	}
	
	public GoodsRebateRackService getGoodsRebateRackService() {
		return goodsRebateRackService;
	}
	public void setGoodsRebateRackService(
			GoodsRebateRackService goodsRebateRackService) {
		this.goodsRebateRackService = goodsRebateRackService;
	}
	
}
