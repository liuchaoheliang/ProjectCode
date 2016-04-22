package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.po.GoodsGoodsAttributeMapStore;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:22:55
 * @version 1.0
 * 
 */
@WebService
public interface GoodsGoodsAttributeMapStoreService {
	
	/**
	 * 增加 商品属性存储
	 * @param goodsGoodsAttributeMapStore
	 */
	public void addGoodsGoodsAttributeMapStore(GoodsGoodsAttributeMapStore goodsGoodsAttributeMapStore);
}
