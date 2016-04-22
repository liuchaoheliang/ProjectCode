package com.froad.action.support.fundsChannel;

import java.util.List;
import org.apache.log4j.Logger;

import com.froad.client.fundsChannel.FundsChannel;
import com.froad.client.fundsChannel.FundsChannelService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-7  
 * @version 1.0
 */
public class FundsChannelActionSupport {
	private static Logger logger = Logger.getLogger(FundsChannelActionSupport.class);
	private FundsChannelService fundsChannelService;
	
	public List<FundsChannel> getFundsChannels(FundsChannel queryCon){
		List<FundsChannel> list = null;
		try {
			list = fundsChannelService.getFundsChannels(queryCon);
		} catch (Exception e) {
			logger.error("FundsChannelActionSupport.getFundsChannels出错", e);
		}
		return list;
	}
	
	public FundsChannelService getFundsChannelService() {
		return fundsChannelService;
	}
	public void setFundsChannelService(FundsChannelService fundsChannelService) {
		this.fundsChannelService = fundsChannelService;
	}
	
}
