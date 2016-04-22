package com.froad.action.support;

import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.clientGoodsExchangeRack.ClientGoodsExchangeRack;
import com.froad.client.clientGoodsExchangeRack.ClientGoodsExchangeRackService;
 
public class ClientGoodsExchangeRackActionSupport {

	private static Logger logger = Logger.getLogger(ClientGoodsExchangeRackActionSupport.class);
	private ClientGoodsExchangeRackService clientGoodsExchangeRackService;
	
	
	public ClientGoodsExchangeRack getClientGoodsExchangeRackListByPager(ClientGoodsExchangeRack clientGoodsExchangeRack){
		return clientGoodsExchangeRackService.getClientGoodsExchangeRackListByPager(clientGoodsExchangeRack);
	}
	
	
	public ClientGoodsExchangeRackService getClientGoodsExchangeRackService() {
		return clientGoodsExchangeRackService;
	}
	public void setClientGoodsExchangeRackService(
			ClientGoodsExchangeRackService clientGoodsExchangeRackService) {
		this.clientGoodsExchangeRackService = clientGoodsExchangeRackService;
	}
	
	public ClientGoodsExchangeRack selectById(String id) {
		return clientGoodsExchangeRackService.selectById(Integer.parseInt(id));
	}
	
	public void getListByPager(ClientGoodsExchangeRack clientGoodsExchangeRack){
			clientGoodsExchangeRackService.getClientGoodsExchangeRackListByPager(clientGoodsExchangeRack);
	}
	
	public List<ClientGoodsExchangeRack> getClientGoodsExchangeRackByGoodsCategoryId(String goodsCategoryId) {
		return clientGoodsExchangeRackService.getClientGoodsExchangeRackByGoodsCategoryId(goodsCategoryId);
	}
	
	
} 
