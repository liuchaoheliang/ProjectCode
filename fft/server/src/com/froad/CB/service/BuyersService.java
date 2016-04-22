package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.po.Buyers;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-29  
 * @version 1.0
 */
@WebService
public interface BuyersService {
	/**
	  * 方法描述：添加买家
	  * @param: Buyers
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addBuyer(Buyers buyer);

	
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
	Buyers getById(Integer id);
	
	/**
	  * 方法描述：按主键删除买家
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteById(String id);
	
	/**
	 * 逻辑删除 买家
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateById(String id);
	
	/**
	  * 方法描述：查询买家
	  * @param: userId
	  * @return: Buyers
	  * @version: 1.0
	  */
	Buyers getBuyerByUserId(String userId);
	
	/**
	 * 增加或更新 买家和买家资金渠道
	 * @param userId
	 * @param mobile
	 * @return
	 */
	public boolean updateBuyerAndBuyerChannel(String userId,String mobile);
}
