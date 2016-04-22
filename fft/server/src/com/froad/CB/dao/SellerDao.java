package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.Seller;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-30  
 * @version 1.0
 */
public interface SellerDao {
	
	/**
	  * 方法描述：添加卖家
	  * @param: Seller
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer insert(Seller seller);
	
	/**
	  * 方法描述：按主键更新卖家信息
	  * @param: Seller
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(Seller seller);
	
	/**
	  * 方法描述：查询卖家
	  * @param: id
	  * @return: Seller
	  * @version: 1.0
	  */
	Seller selectByPrimaryKey(Integer id);
	
	/**
	  * 方法描述：按主键删除卖家
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteByPrimaryKey(String id);
	
	/**
	 * 逻辑删除 卖家
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateByPrimaryKey(String id);
	
	/**
	  * 方法描述：查询卖家
	  * @param: userId
	  * @return: Seller
	  * @version: 1.0
	  */
	List<Seller> getSellerByUserId(String userId);
	
	/**
	  * 方法描述：查询卖家
	  * @param: merchantId
	  * @return: Seller
	  * @version: 1.0
	  */
	List<Seller> getSellerByMerchantId(String merchantId);
	
	
	/**
	  * 方法描述：多条件查询卖家信息
	  * @param: List<Seller>
	  * @return: Seller
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 2, 2013 4:37:07 PM
	  */
	List<Seller> getBySelective(Seller seller);
}
