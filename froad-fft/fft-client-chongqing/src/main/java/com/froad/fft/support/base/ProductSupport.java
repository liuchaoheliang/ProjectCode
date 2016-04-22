
	 /**
  * 文件名：ProductSupport.java
  * 版本信息：Version 1.0
  * 日期：2014年4月15日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base;

import com.froad.fft.dto.ProductDto;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月15日 下午8:03:59 
 */
public interface ProductSupport {
	
	/**
	  * 方法描述：根据ID查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月15日 下午8:04:23
	  */
	public ProductDto getById(Long id);
}
