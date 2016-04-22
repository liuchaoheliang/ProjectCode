package com.froad.CB.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.GoodsGoodsAttributeMapStoreDao;
import com.froad.CB.po.GoodsGoodsAttributeMapStore;
import com.froad.CB.service.GoodsGoodsAttributeMapStoreService;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:31:17
 * @version 1.0
 * 
 */
@WebService(endpointInterface="com.froad.CB.service.GoodsGoodsAttributeMapStoreService")
public class GoodsGoodsAttributeMapStoreServiceImpl implements
		GoodsGoodsAttributeMapStoreService {

	private static final Logger logger=Logger.getLogger(GoodsGoodsAttributeMapStoreServiceImpl.class);
	
	private GoodsGoodsAttributeMapStoreDao goodsGoodsAttributeMapStoreDao;
	

	@Override
	public void addGoodsGoodsAttributeMapStore(
			GoodsGoodsAttributeMapStore goodsGoodsAttributeMapStore) {
		
	}
	
	public GoodsGoodsAttributeMapStoreDao getGoodsGoodsAttributeMapStoreDao() {
		return goodsGoodsAttributeMapStoreDao;
	}

	public void setGoodsGoodsAttributeMapStoreDao(
			GoodsGoodsAttributeMapStoreDao goodsGoodsAttributeMapStoreDao) {
		this.goodsGoodsAttributeMapStoreDao = goodsGoodsAttributeMapStoreDao;
	}

}
