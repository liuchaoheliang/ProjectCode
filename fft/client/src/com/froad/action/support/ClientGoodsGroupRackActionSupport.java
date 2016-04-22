package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.clientGoodsGroupRack.ClientGoodsGroupRack;
import com.froad.client.clientGoodsGroupRack.ClientGoodsGroupRackService;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
 

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 团购 client service  ActionSupport
 */
public class ClientGoodsGroupRackActionSupport {
	private static Logger logger = Logger.getLogger(ClientGoodsGroupRackActionSupport.class);
	private ClientGoodsGroupRackService clientGoodsGroupRackService;
	 
	public ClientGoodsGroupRackService getClientGoodsGroupRackService() {
		return clientGoodsGroupRackService;
	}

	public void setClientGoodsGroupRackService(
			ClientGoodsGroupRackService clientGoodsGroupRackService) {
		this.clientGoodsGroupRackService = clientGoodsGroupRackService;
	}

	public ClientGoodsGroupRack getGoodsGroupRackById(String id){
		ClientGoodsGroupRack result=null;
		if(id==null)
			return result;
		try {
			result = (ClientGoodsGroupRack) clientGoodsGroupRackService.getGoodsGroupRackById(Integer.parseInt(id));
		} catch (Exception e) {
			logger.error("查询团购商品的交易品失败!", e);
		}
		return result;
	}
	public ClientGoodsGroupRack getGoodsGroupRackByPager(ClientGoodsGroupRack clientGoodsGroupRack){
		return clientGoodsGroupRackService.getClientGoodsGroupRackByPager(clientGoodsGroupRack) ;
	}
}
