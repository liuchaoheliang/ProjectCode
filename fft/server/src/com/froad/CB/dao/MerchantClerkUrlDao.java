package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.MerchantClerkUrl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-23  
 * @version 1.0
 */
public interface MerchantClerkUrlDao {
	/**
	  * 方法描述：添加商户资源
	  * @param: MerchantClerkUrl
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 8:38:37 PM
	  */
	public Integer addMerchantClerkUrl(MerchantClerkUrl merchantClerkUrl);
	
	/**
	  * 方法描述：查询商户所有资源
	  * @param: merchantClerkUrl
	  * @return: List<MerchantUserSet>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 5, 2013 6:04:09 PM
	  */
	public List<MerchantClerkUrl> getMerchantClerkUrl();
}
