package com.froad.CB.dao;

import com.froad.CB.po.Buyers;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-30  
 * @version 1.0
 */
public interface BuyersDao {
	/**
	  * 方法描述：添加买家
	  * @param: Buyers
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer insert(Buyers buyer);

	
	/**
	  * 方法描述：按主键更新买家信息
	  * @param: Buyers
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(Buyers buyer);

	
	/**
	  * 方法描述：查询买家
	  * @param: id
	  * @return: Buyers
	  * @version: 1.0
	  */
	Buyers selectByPrimaryKey(Integer id);
	
	/**
	  * 方法描述：按主键删除买家
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteByPrimaryKey(String id);
	
	/**
	 * 逻辑删除 买家
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateByPrimaryKey(String id);
	
	/**
	  * 方法描述：查询买家
	  * @param: userId
	  * @return: Buyers
	  * @version: 1.0
	  */
	Buyers getBuyersByUserId(String userId);
}
