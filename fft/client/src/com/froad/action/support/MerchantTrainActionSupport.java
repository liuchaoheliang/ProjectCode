package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.merchantPresent.MerchantPresent;
import com.froad.client.merchantTrain.MerchantTrain;
import com.froad.client.merchantTrain.MerchantTrainService;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 直通车  client service  ActionSupport
 */
public class MerchantTrainActionSupport {
	private static Logger logger = Logger.getLogger(MerchantTrainActionSupport.class);
	private MerchantTrainService merchantTrainService;
	
	/**
	 * 通过商户ID或者用户id查询出商户直通车信息
	 * @param merchantId
	 * @return 商户直通车信息
	 */
	public MerchantTrain getMerchantTrainByMerchantId(String merchantId,String userId){
		MerchantTrain merchantTrain=null;
		try{
			if(!Assert.empty(merchantId)){
				merchantTrain=merchantTrainService.getMerchantTrainByMerchantId(merchantId);					
			}else if(!Assert.empty(userId)){
				merchantTrain=merchantTrainService.getMerchantTrainByUserId(userId);	
			}
		}
		catch(Exception e){
			logger.error("MerchantTrainActionSupport.getMerchantTrainByMerchantId查询商户直通车异常", e);
		}
		return merchantTrain;
	}
	
	
	/**
	 * 更新商户
	 * @param merchantTrain
	 * @return 
	 */
	public boolean updMerchantTrain(MerchantTrain merchantTrain){
		boolean result=false;
		try{
			result=merchantTrainService.updateById(merchantTrain);
		}
		catch(Exception e){
			logger.error("更新商户直通车异常", e);
		}
		return result;
	}
	
	
	public MerchantTrainService getMerchantTrainService() {
		return merchantTrainService;
	}
	public void setMerchantTrainService(MerchantTrainService merchantTrainService) {
		this.merchantTrainService = merchantTrainService;
	}
	
}
