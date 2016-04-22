package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.buyerChannel.BuyerChannel;
import com.froad.client.buyerChannel.BuyerChannelService;


/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 买家支付渠道 client service  ActionSupport
 */
public class BuyerChannelActionSupport {
	private static Logger logger = Logger.getLogger(BuyerChannelActionSupport.class);
	private BuyerChannelService buyersDepositChannelService;
	
	public BuyerChannel getBuyerChannelByUserId(String userId){
		return buyersDepositChannelService.getBuyerChannelByUserId(userId);
	}
	
	public BuyerChannelService getBuyerChannelService() {
		return buyersDepositChannelService;
	}
	public void setBuyerChannelService(
			BuyerChannelService buyersDepositChannelService) {
		this.buyersDepositChannelService = buyersDepositChannelService;
	}
	
}
