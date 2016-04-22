package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ProductAttributeValueMapper;
import com.froad.fft.persistent.entity.ProductAttributeValue;

public class ProductAttributeValueMapperImpl implements
		ProductAttributeValueMapper {

	@Resource
	private ProductAttributeValueMapper productAttributeValueMapper;
	
	@Override
	public Long saveProductAttributeValue(
			ProductAttributeValue productAttributeValue) {	
		return productAttributeValueMapper.saveProductAttributeValue(productAttributeValue);
	}

}
