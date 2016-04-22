package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack;
import com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRackService;

/**
	 * 类描述：client 商品提现
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: guoming guoming@f-road.com.cn
	 * @time: 2013-2-22 下午01:54:03
 */
public class ClientGoodsCarryRackActionSupport {
	private static Logger logger = Logger.getLogger(ClientGoodsCarryRackActionSupport.class);
	private ClientGoodsCarryRackService clientGoodsCarryRackService;
	 
	public ClientGoodsCarryRackService getClientGoodsCarryRackService() {
		return clientGoodsCarryRackService;
	}

	public void setClientGoodsCarryRackService(
			ClientGoodsCarryRackService clientGoodsCarryRackService) {
		this.clientGoodsCarryRackService = clientGoodsCarryRackService;
	}

	 
	public ClientGoodsCarryRack getClientGoodsCarryRackListByPager(ClientGoodsCarryRack group){
		return  clientGoodsCarryRackService.getClientGoodsCarryRackListByPager(group);
	}

}
