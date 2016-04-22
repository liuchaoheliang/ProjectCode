package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.MerchantUserSeller;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-6-3  
 * @version 1.0
 */
public interface MerchantUserSellerDao {
	/**
	  * 方法描述：添加商户会员卖家记录
	  * @param: MerchantUserSeller
	  * @return: boolean
	  * @version: 1.0
	  * @author: liqiaopeng
	  */
	public Integer addMerchantUserSeller(MerchantUserSeller merchantUserSeller);
	
	/**
	  * 方法描述：查询操作员卖家记录
	  * @param: id或者merchantId,merchantUserId,sellerId
	  * @return: List<MerchantUserSeller>
	  * @version: 1.0
	  * @author: liqiaopeng
	  */
	public List<MerchantUserSeller> getMerchantUserSellers(MerchantUserSeller merchantUserSeller);
	
	/**
	  * 方法描述：修改操作员卖家
	  * @param: MerchantUserSeller
	  * @return: void
	  * @version: 1.0
	  * @author: liqiaopeng
	  */
	public Integer updateByMerchantSeller(MerchantUserSeller merchantUserSeller);
	
	/**
	  * 方法描述：商户删除操作员卖家(逻辑删除)
	  * @param:  id或者merchantId,merchantUserId,sellerId
	  * @return: void
	  * @version: 1.0
	  * @author: liqiaopeng
	  */
	public Integer deleteByMerchantSeller(MerchantUserSeller merchantUserSeller);
}
