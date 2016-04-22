package com.froad.action.support;

import org.apache.log4j.Logger;
import com.froad.client.merchantTraffic.MerchantTrafficService;
import com.froad.client.merchantTrafficMAP.MerchantTrafficMAP;
import com.froad.client.merchantTrain.MerchantTrain;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商户交通  client service  ActionSupport
 */
public class MerchantTrafficActionSupport {
	private static Logger logger = Logger.getLogger(MerchantTrafficActionSupport.class);
	private MerchantTrafficService merchantTrafficService;
	
	/**
	 * 通过商户ID或者会员ID查询出商户地图信息
	 * @param merchantId
	 * @param userId
	 * @return 商户地图信息
	 */
	public MerchantTrafficMAP getMerchantTrafficMapInfo(String merchantId,String userId){
		MerchantTrafficMAP merchantTrafficMAP = null;
		try{
			if(!Assert.empty(merchantId)){
				merchantTrafficService.getMerchantTrafficInfoByMerchantId(merchantId);					
			}else if(!Assert.empty(userId)){
				merchantTrafficService.getMerchantTrafficInfoByUserId(userId);	
			}
		}
		catch(Exception e){
			logger.error("MerchantTrafficActionSupport.getMerchantTrafficMapInfo查询商户直通车异常 merchantId:"+merchantId+" userId:"+userId, e);
		}
		
		return merchantTrafficMAP;
	}
	
	public MerchantTrafficService getMerchantTrafficService() {
		return merchantTrafficService;
	}
	public void setMerchantTrafficService(
			MerchantTrafficService merchantTrafficService) {
		this.merchantTrafficService = merchantTrafficService;
	}
	
}
