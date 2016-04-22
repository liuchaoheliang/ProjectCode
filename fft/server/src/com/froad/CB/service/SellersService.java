package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.Seller;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-1  
 * @version 1.0
 */
@WebService
public interface SellersService {
	/**
	  * 方法描述：添加卖家
	  * @param: Seller
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addSeller(Seller seller);
	
	
	/**
	  * 方法描述：绑定卖家账户
	  * @param: Seller
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 5, 2013 4:41:27 PM
	  */
	boolean bindingAccount(Seller seller)throws AppException;
	
	
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
	Seller selectById(Integer id);
	
	/**
	  * 方法描述：按主键删除卖家
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteById(String id);
	
	/**
	 * 逻辑删除 卖家
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateById(String id);
	
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
	  * @return: List<Seller>
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
