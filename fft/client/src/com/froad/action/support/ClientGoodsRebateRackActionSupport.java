package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.clientGoodsRebateRack.ClientGoodsRebateRack;
import com.froad.client.clientGoodsRebateRack.ClientGoodsRebateRackService;

public class ClientGoodsRebateRackActionSupport {
	private static Logger logger = Logger.getLogger(ClientGoodsRebateRackActionSupport.class);
	private ClientGoodsRebateRackService clientGoodsRebateRackService; 
	
	/**
	 * 分页查询 商户积分返利上架商品列表
	 * @param pager
	 * @return
	 */
	public ClientGoodsRebateRack getGoodsRebateRackByPager(ClientGoodsRebateRack goodsRebateRack){
		try{
			goodsRebateRack=clientGoodsRebateRackService.getGoodsRebateRackByPager(goodsRebateRack);
		}
		catch(Exception e){
			logger.error("GoodsRebateRackActionSupport.getGoodsRebateRackByPager分页查找商户上架商品异常 商户ID："+goodsRebateRack.getGoods().getMerchantId(), e);
		}
		return goodsRebateRack;
	}

	public ClientGoodsRebateRackService getClientGoodsRebateRackService() {
		return clientGoodsRebateRackService;
	}

	public void setClientGoodsRebateRackService(
			ClientGoodsRebateRackService clientGoodsRebateRackService) {
		this.clientGoodsRebateRackService = clientGoodsRebateRackService;
	}
	

}
