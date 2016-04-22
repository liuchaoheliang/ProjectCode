
	 /**
  * 文件名：MerchantOutletSupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月6日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import java.util.List;
import java.util.Map;

import com.froad.fft.dto.MerchantOutletDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.enums.trans.TransType;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月6日 下午1:50:16 
 */
public interface MerchantOutletSupport {
	

	/**
	  * 方法描述：条件查询门店信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月6日 下午2:13:04
	  */
	public  List<MerchantOutletDto> getByConditions(MerchantOutletDto merchantOutletDto);
	
	/**
	  * 方法描述：门店管理员，查询该门店信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月6日 下午1:53:29
	  */
	public MerchantOutletDto getById(Long id);
	
	
	
	/**
	  * 方法描述：根据系统用户查询其对应权限下所有门店信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月9日 下午12:36:47
	  */
	public List<MerchantOutletDto> getOutletBySysUser(SysUserDto userDto);
	
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月30日 上午9:49:19
	  */
	public List<Map<String,String>>  getSaleRank(Long merchantId,TransType transType);
}
