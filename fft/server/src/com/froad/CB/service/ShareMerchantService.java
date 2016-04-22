package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.merchant.ShareMerchant;


	/**
	 * 类描述：商户分享的service接口
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Jan 15, 2013 11:39:50 AM 
	 */
@WebService
public interface ShareMerchantService {
	
	
	/**
	  * 方法描述：添加分享
	  * @param: ShareMerchant
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 15, 2013 11:33:40 AM
	  */
	public Integer addShareMerchant(ShareMerchant shareMerchant);
	
	
	/**
	  * 方法描述：查询分享
	  * @param: id
	  * @return: ShareMerchant
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 15, 2013 11:34:00 AM
	  */
	public ShareMerchant getShareMerchantById(Integer id);
	
	
	/**
	  * 方法描述：查询分享列表
	  * @param: userId
	  * @return: List<ShareMerchant>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 15, 2013 11:34:24 AM
	  */
	public List<ShareMerchant> getShareMerchantByUserId(String userId);
	
	
	/**
	  * 方法描述：分页查询分享
	  * @param: ShareMerchant
	  * @return: ShareMerchant
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 15, 2013 11:34:51 AM
	  */
	public ShareMerchant getShareMerchantByPager(ShareMerchant shareMerchant);
	
	
	/**
	  * 方法描述：更新分享状态
	  * @param: id,state
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 15, 2013 11:35:11 AM
	  */
	public boolean updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：更新分享信息
	  * @param: ShareMerchant
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 15, 2013 11:35:34 AM
	  */
	public boolean updateById(ShareMerchant shareMerchant);
	
	
	/**
	  * 方法描述：删除分享
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 15, 2013 11:35:54 AM
	  */
	public boolean deleteById(Integer id);
}
