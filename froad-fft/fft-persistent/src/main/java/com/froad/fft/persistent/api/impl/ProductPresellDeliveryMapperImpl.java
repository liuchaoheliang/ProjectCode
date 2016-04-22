
	 /**
  * 文件名：ProductPresellDeliveryMapperImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月1日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ProductPresellDeliveryMapper;
import com.froad.fft.persistent.entity.ProductPresellDelivery;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月1日 下午8:14:43 
 */
public class ProductPresellDeliveryMapperImpl implements
		ProductPresellDeliveryMapper {

	@Resource
	private ProductPresellDeliveryMapper productPresellDeliveryMapper;

	@Override
	public Long saveProductPresellDelivery(
			ProductPresellDelivery productPresellDelivery) {
		productPresellDeliveryMapper.saveProductPresellDelivery(productPresellDelivery);
		return productPresellDelivery.getId();
	}


	@Override
	public List<ProductPresellDelivery> selectByProductPresellId(Long productId) {
		return productPresellDeliveryMapper.selectByProductPresellId(productId);
	}


	@Override
	public Boolean deleteById(Long id) {
		return productPresellDeliveryMapper.deleteById(id);
	}

}
