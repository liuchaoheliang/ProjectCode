package com.froad.action.support;

import org.apache.log4j.Logger;

import com.froad.client.goodsGoodsAttributeMapStore.GoodsGoodsAttributeMapStoreService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商品属性存储 client service  ActionSupport
 */
public class GoodsGoodsAttributeMapStoreActionSupport {
	private static Logger logger = Logger.getLogger(GoodsGoodsAttributeMapStoreActionSupport.class);
	private GoodsGoodsAttributeMapStoreService goodsGoodsAttributeMapStoreService;
	
	
	public GoodsGoodsAttributeMapStoreService getGoodsGoodsAttributeMapStoreService() {
		return goodsGoodsAttributeMapStoreService;
	}
	public void setGoodsGoodsAttributeMapStoreService(
			GoodsGoodsAttributeMapStoreService goodsGoodsAttributeMapStoreService) {
		this.goodsGoodsAttributeMapStoreService = goodsGoodsAttributeMapStoreService;
	}
	
}
