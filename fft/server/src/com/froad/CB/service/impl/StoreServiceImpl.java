package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.StoreDao;
import com.froad.CB.po.Store;
import com.froad.CB.service.StoreService;

@WebService(endpointInterface="com.froad.CB.service.StoreService")
public class StoreServiceImpl implements StoreService{
	
	private static final Logger logger=Logger.getLogger(StoreServiceImpl.class);
	
	private StoreDao storeDao;

	@Override
	public Integer addStore(Store store) {
		if(store==null){
			logger.error("参数为空，门店添加失败");
		}
		return storeDao.addStore(store);
	}

	@Override
	public boolean deleteById(Integer id) {
		if(id==null){
			logger.error("门店编号为空，删除操作失败");
			return false;
		}
		storeDao.deleteById(id);
		return true;
	}

	@Override
	public Store getStoreById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return storeDao.getStoreById(id);
	}

	@Override
	public List<Store> getStoreByMerchantId(Integer merchantId) {
		if(merchantId==null){
			logger.error("商户编号为空，查询失败");
			return null;
		}
		return storeDao.getStoreByMerchantId(merchantId);
	}

	@Override
	public Store getStoreByPager(Store store) {
		if(store==null){
			logger.error("参数为空，分页查询失败");
			return null;
		}
		return storeDao.getStoreByPager(store);
	}

	@Override
	public boolean updateById(Store store) {
		if(store==null||store.getId()==null){
			logger.error("传入门店信息为空，更新失败");
			return false;
		}
		storeDao.updateById(store);
		return true;
	}

	public void setStoreDao(StoreDao storeDao) {
		this.storeDao = storeDao;
	}

	

}
