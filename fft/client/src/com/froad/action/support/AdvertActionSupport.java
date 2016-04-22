package com.froad.action.support;

import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.advert.Advert;
import com.froad.client.advert.AdvertService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 广告 client service  ActionSupport
 */
public class AdvertActionSupport {
	private static Logger logger = Logger.getLogger(AdvertActionSupport.class);
	private AdvertService advertService;
	
	/**
	 * 根据   Advert 条件查询广告
	 * @param advert
	 * @return
	 */
	public List<Advert> getAdvertById(Advert advert){
		List<Advert> advertList=null;
		try{
			advertList=advertService.getAdverts(advert);
		}
		catch(Exception e){
			logger.error("根据 Advert 条件查询出错！", e);
		}
		return advertList;
	}
	
	public AdvertService getAdvertService() {
		return advertService;
	}
	public void setAdvertService(AdvertService advertService) {
		this.advertService = advertService;
	}
	
}
