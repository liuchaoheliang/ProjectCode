package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.goodsGatherRack.GoodsGatherRackService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商品收款架 client service  ActionSupport
 */
public class GoodsGatherRackActionSupport {
	private static Logger logger = Logger.getLogger(GoodsGatherRackActionSupport.class);
	private GoodsGatherRackService goodsGatherRackService;
	public GoodsGatherRackService getGoodsGatherRackService() {
		return goodsGatherRackService;
	}
	public void setGoodsGatherRackService(
			GoodsGatherRackService goodsGatherRackService) {
		this.goodsGatherRackService = goodsGatherRackService;
	}
	
}
