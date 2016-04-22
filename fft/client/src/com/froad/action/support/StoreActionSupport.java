package com.froad.action.support;

import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.Store.Store;
import com.froad.client.Store.StoreService;

/**
 * *******************************************************
 * @项目名称: communityBusiness_client 
 * @类名: StoreActionSupport.java  
 * @功能描述: 分店相关信息Support
 * @作者: 赵肖瑶
 * @日期: 2013-6-27 下午02:12:34
 *********************************************************
 */
public class StoreActionSupport {
	private static Logger logger = Logger.getLogger(StoreActionSupport.class);
	
	private StoreService storeService;

	public Store getStoreByPager(Store store){
		try {
			return storeService.getStoreByPager(store);
		} catch (Exception e) {
			logger.info("查询Store异常 向访问方法返回null");
			return null;
		}
	}
		
	
	public List<Store> getStoresByMerchantId(int merchantId){
			return storeService.getStoreByMerchantId(merchantId);
		}
	
	public Store getStoreById(String storeId){
		return storeService.getStoreById(Integer.valueOf(storeId));
	}
	
	
	public StoreService getStoreService() {
		return storeService;
	}
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
	
	
	
}
