package com.froad.fft.persistent.api.impl;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ProductImageMapper;
import com.froad.fft.persistent.entity.ProductImage;

public class ProductImageMapperImpl implements ProductImageMapper {

	@Resource
	private ProductImageMapper productImageMapper;
	
	@Override
	public Long saveProductImage(ProductImage productImage) {
		productImageMapper.saveProductImage(productImage);
		return productImage.getId();
	}

	@Override
	public Boolean updateProductImageById(ProductImage productImage) {
		return productImageMapper.updateProductImageById(productImage);
	}

	@Override
	public ProductImage selectProductImageById(Long id) {
		return productImageMapper.selectProductImageById(id);
	}

}
