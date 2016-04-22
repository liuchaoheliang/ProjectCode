package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.MerchantOnlineApply;

public interface MerchantOnlineApplyDao {

	
	/**
	  * 方法描述：添加在线申请信息
	  * @param: MerchantOnlineApply
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 2:45:13 PM
	  */
	public Integer add(MerchantOnlineApply merchantOnlineApply);
	
	
	/**
	  * 方法描述：查询在线申请信息
	  * @param: id
	  * @return: MerchantOnlineApply
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 2:45:30 PM
	  */
	public MerchantOnlineApply getById(Integer id);
	
	
	/**
	  * 方法描述：查询在线申请信息
	  * @param: userId
	  * @return: MerchantOnlineApply
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 5, 2013 6:04:09 PM
	  */
	public List<MerchantOnlineApply> getByUserId(String userId);
	
	
	/**
	  * 方法描述：修改在线申请信息
	  * @param: MerchantOnlineApply
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 2:45:59 PM
	  */
	public void updateById(MerchantOnlineApply apply);
	
	
	/**
	  * 方法描述：删除在线申请信息
	  * @param: id
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 2:46:18 PM
	  */
	public void deleteById(Integer id);
	
	
	/**
	  * 方法描述：分页查询商户在线申请信息
	  * @param: MerchantOnlineApply
	  * @return: MerchantOnlineApply
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 19, 2013 11:30:17 AM
	  */
	public MerchantOnlineApply getMerchantApplyByPager(MerchantOnlineApply apply);
}
