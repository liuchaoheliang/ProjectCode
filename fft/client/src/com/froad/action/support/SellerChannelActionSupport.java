package com.froad.action.support;

import org.apache.log4j.Logger;
import com.froad.client.sellerChannel.SellerChannelService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 卖家付款渠道 client service  ActionSupport
 */
public class SellerChannelActionSupport {
	private static Logger logger = Logger.getLogger(SellerChannelActionSupport.class);
	private SellerChannelService sellerChannelService;
	
	
	
	public SellerChannelService getSellerChannelService() {
		return sellerChannelService;
	}
	public void setSellerChannelService(SellerChannelService sellerChannelService) {
		this.sellerChannelService = sellerChannelService;
	}
	
}
