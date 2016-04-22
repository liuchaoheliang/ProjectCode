package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.merchantTrafficMAP.MerchantTrafficMAP;
import com.froad.client.merchantTrafficMAP.MerchantTrafficMAPService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商户地图  client service  ActionSupport
 */
public class MerchantTrafficMAPActionSupport {
	private static Logger logger = Logger.getLogger(MerchantTrafficMAPActionSupport.class);
	private MerchantTrafficMAPService merchantTrafficMAPService;
	
	
	public MerchantTrafficMAP getMerchantTrafficMapByMerchantId(String merchantId){
		return merchantTrafficMAPService.getMerchantTrafficMapByMerchantId(merchantId);
	}
	
	
	public MerchantTrafficMAPService getMerchantTrafficMAPService() {
		return merchantTrafficMAPService;
	}
	public void setMerchantTrafficMAPService(
			MerchantTrafficMAPService merchantTrafficMAPService) {
		this.merchantTrafficMAPService = merchantTrafficMAPService;
	}
	
}
