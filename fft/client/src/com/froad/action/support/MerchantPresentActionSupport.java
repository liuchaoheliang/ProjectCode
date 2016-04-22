package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.merchantPresent.MerchantPresent;
import com.froad.client.merchantPresent.MerchantPresentService;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商户介绍信息  client service  ActionSupport
 */
public class MerchantPresentActionSupport {
	private static Logger logger = Logger.getLogger(MerchantPresentActionSupport.class);
	private MerchantPresentService merchantPresentService;
	
	/**
	 * 通过商户ID或者会员ID查询出商户介绍信息
	 * @param merchantId
	 * @param userId
	 * @return 商户介绍信息
	 */
	public MerchantPresent getMerchantPresentInfo(String merchantId,String userId){
		MerchantPresent merchantPresent=null;
			try {
				if(!Assert.empty(merchantId)){
					merchantPresent=merchantPresentService.getMerchantPresentByMerchantId(merchantId);		
				}else if(!Assert.empty(userId)){
					merchantPresent=merchantPresentService.getMerchantPresentByUserId(userId);	
				}
			} catch (Exception e) {
				logger.error("MerchantPresentActionSupport.getMerchantPresentInfo出错 merchantId:"+merchantId+",userId:"+userId);
			}
		return merchantPresent;
	}
	
	/**
	 * 更新商户介绍信息
	 * @param mp
	 * @return
	 */
	public MerchantPresent updateInfoByMerchantId(MerchantPresent mp){
		MerchantPresent mpresent = null; 
		try {
			mpresent = merchantPresentService.updateByMerchantId(mp);
		} catch (Exception e) {
			logger.error("MerchantPresentActionSupport.updateInfoByMerchantId出错 id:"+mp.getId());
		}	
		return mpresent;
	}
	
	public MerchantPresentService getMerchantPresentService() {
		return merchantPresentService;
	}
	public void setMerchantPresentService(
			MerchantPresentService merchantPresentService) {
		this.merchantPresentService = merchantPresentService;
	}
	
}
