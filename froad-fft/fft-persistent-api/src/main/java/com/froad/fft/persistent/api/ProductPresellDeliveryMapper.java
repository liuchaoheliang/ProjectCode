
	 /**
  * 文件名：ProductPresellDeliveryMapper.java
  * 版本信息：Version 1.0
  * 日期：2014年4月1日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.entity.ProductPresellDelivery;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月1日 下午8:10:44 
 */
public interface ProductPresellDeliveryMapper {
	
	/**
	  * 方法描述：添加数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月1日 下午8:12:06
	  */
	public Long saveProductPresellDelivery(ProductPresellDelivery productPresellDelivery);
	
	
	/**
	  * 方法描述：根据预售商品Id查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月1日 下午8:12:17
	  */
	public List<ProductPresellDelivery> selectByProductPresellId(Long productId);
	
	
	
	/**
	  * 方法描述：根据Id删除关系
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月1日 下午8:13:46
	  */
	public Boolean deleteById(Long id);
}
