package com.froad.action.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.merchantIndustry.MerchantIndustry;
import com.froad.client.merchantIndustry.MerchantIndustryService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商户行业  client service  ActionSupport
 */
public class MerchantIndustryActionSupport {
	private static Logger logger = Logger.getLogger(MerchantIndustryActionSupport.class);
	private MerchantIndustryService merchantIndustryService;
	
	public List<MerchantIndustry> getMerchantIndustryList(){
		List<MerchantIndustry> merchantIndustryList = new ArrayList<MerchantIndustry>();
		merchantIndustryList = merchantIndustryService.getAll();
		return merchantIndustryList;
	}
	
	public MerchantIndustryService getMerchantIndustryService() {
		return merchantIndustryService;
	}
	public void setMerchantIndustryService(
			MerchantIndustryService merchantIndustryService) {
		this.merchantIndustryService = merchantIndustryService;
	}
	
}
