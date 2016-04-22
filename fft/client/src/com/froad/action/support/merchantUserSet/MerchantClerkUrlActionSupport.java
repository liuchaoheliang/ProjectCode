package com.froad.action.support.merchantUserSet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.froad.client.merchantClerkUrl.MerchantClerkUrl;
import com.froad.client.merchantClerkUrl.MerchantClerkUrlService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-23  
 * @version 1.0
 */
public class MerchantClerkUrlActionSupport {
	private static Logger logger = Logger.getLogger(MerchantClerkUrlActionSupport.class);
	private MerchantClerkUrlService merchantClerkUrlService;
	
	public Map<String,String> getUrlMap(){
		List<MerchantClerkUrl> urlList = null;
		//map<url,"0">,map<url,"1">
		Map<String,String> urlMap = new HashMap<String,String>();
		urlList = merchantClerkUrlService.getMerchantClerkUrl();
		if(urlList!=null && urlList.size() >0){
			for(MerchantClerkUrl url:urlList){
				urlMap.put(url.getUrl().trim(), url.getUrlType());
			}
		}
		return urlMap;
	}
	
	public MerchantClerkUrlService getMerchantClerkUrlService() {
		return merchantClerkUrlService;
	}
	public void setMerchantClerkUrlService(
			MerchantClerkUrlService merchantClerkUrlService) {
		this.merchantClerkUrlService = merchantClerkUrlService;
	}
	
}
