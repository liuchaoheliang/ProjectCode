
	 /**
  * 文件名：MerchantGroupUserSupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月6日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import java.util.List;

import com.froad.fft.dto.MerchantGroupUserDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月6日 下午1:46:40 
 */
public interface MerchantGroupUserSupport {
	
	/**
	  * 方法描述：根据系统用户查询商户用户组
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月6日 下午1:48:34
	  */
	public MerchantGroupUserDto getBySysUserId(Long sysUserId);
	
	
	/**
	  * 方法描述：条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月8日 下午5:43:27
	  */
	public List<MerchantGroupUserDto> getByConditions(MerchantGroupUserDto merchantGroupUserDto);
	
}
