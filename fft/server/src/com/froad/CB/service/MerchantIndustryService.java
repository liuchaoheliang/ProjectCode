package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.MerchantIndustry;


	/**
	 * 类描述：商户行业service
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Feb 7, 2013 11:37:38 AM 
	 */
@WebService
public interface MerchantIndustryService {

	/**
	  * 方法描述：添加行业信息
	  * @param: MerchantIndustry
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 11:35:01 AM
	  */
	public Integer add(MerchantIndustry merchantIndustry);
	
	
	/**
	  * 方法描述：查询行业信息
	  * @param: id
	  * @return: MerchantIndustry
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 11:35:20 AM
	  */
	public MerchantIndustry getById(Integer id);
	
	
	/**
	  * 方法描述：修改行业信息
	  * @param: MerchantIndustry
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 11:35:41 AM
	  */
	public void updateById(MerchantIndustry merchantIndustry);
	
	
	/**
	  * 方法描述：删除行业信息
	  * @param: id
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 11:36:01 AM
	  */
	public void deleteById(Integer id);
	
	
	/**
	  * 方法描述：查询所有行业信息
	  * @return: List<MerchantIndustry>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 4, 2013 5:38:43 PM
	  */
	public List<MerchantIndustry> getAll();
}
