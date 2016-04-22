package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.Store;

public interface StoreDao {

	
	/**
	  * 方法描述：添加门店
	  * @param: Store
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 26, 2013 4:32:33 PM
	  */
	public Integer addStore(Store store);
	
	
	/**
	  * 方法描述：删除门店
	  * @param: id
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 26, 2013 4:33:45 PM
	  */
	public void deleteById(Integer id);
	
	
	/**
	  * 方法描述：修改门店信息
	  * @param: Store
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 26, 2013 4:34:39 PM
	  */
	public void updateById(Store store);
	
	
	/**
	  * 方法描述：查询门店信息
	  * @param: id
	  * @return: Store
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 26, 2013 4:35:40 PM
	  */
	public Store getStoreById(Integer id);
	
	
	/**
	  * 方法描述：查询商户的门店列表
	  * @param: merchantId 商户编号
	  * @return: List<Store>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 26, 2013 4:37:24 PM
	  */
	public List<Store> getStoreByMerchantId(Integer merchantId);
	
	
	/**
	  * 方法描述：分页查询门店信息
	  * @param: Store
	  * @return: Store
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 26, 2013 4:36:33 PM
	  */
	public Store getStoreByPager(Store store);
}
