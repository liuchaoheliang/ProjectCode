package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.ProductAttributeValueMapper;
import com.froad.fft.persistent.entity.ProductAttributeValue;
import com.froad.fft.service.ProductAttributeValueService;

@Service("productAttributeValueServiceImpl")
public class ProductAttributeValueServiceImpl implements
		ProductAttributeValueService {

	private static Logger logger = Logger.getLogger(ProductAttributeValueServiceImpl.class);
	
	@Resource
	private ProductAttributeValueMapper productAttributeValueMapper;
	
	@Override
	public Boolean saveProductAttributeValue(ProductAttributeValue productAttributeValue) {
		try {
			productAttributeValueMapper.saveProductAttributeValue(productAttributeValue);
			return true;
		} catch (Exception e) {
			logger.error("保存对象失败: " + JSONObject.toJSONString(productAttributeValue),e);
			return false;
		}
		
	}

}
