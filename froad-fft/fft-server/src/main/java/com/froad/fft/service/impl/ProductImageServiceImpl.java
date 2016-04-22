package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.ProductImageMapper;
import com.froad.fft.persistent.entity.ProductImage;
import com.froad.fft.service.ProductImageService;

@Service("productImageServiceImpl")
public class ProductImageServiceImpl implements ProductImageService {

	private static Logger logger = Logger.getLogger(ProductImageServiceImpl.class);
	
	@Resource
	private ProductImageMapper productImageMapper;
	
	@Override
	public Long saveProductImage(ProductImage productImage) {
		return productImageMapper.saveProductImage(productImage);
	}

	@Override
	public Boolean updateProductImageById(ProductImage productImage) {
		if(productImage.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return productImageMapper.updateProductImageById(productImage);
	}

	@Override
	public ProductImage selectProductImageById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return productImageMapper.selectProductImageById(id);
	}

}
