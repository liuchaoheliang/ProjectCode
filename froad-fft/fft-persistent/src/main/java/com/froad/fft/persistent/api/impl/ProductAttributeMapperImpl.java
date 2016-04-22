package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ProductAttributeMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductAttribute;

public class ProductAttributeMapperImpl implements ProductAttributeMapper {

	@Resource
	private ProductAttributeMapper productAttributeMapper;
	
	@Override
	public Long saveProductAttribute(ProductAttribute productAttribute) {
		return productAttributeMapper.saveProductAttribute(productAttribute);
	}

	@Override
	public Boolean updateProductAttributeById(ProductAttribute productAttribute) {
		return productAttributeMapper.updateProductAttributeById(productAttribute);
	}

	@Override
	public ProductAttribute selectProductAttributeById(Long id) {
		return productAttributeMapper.selectProductAttributeById(id);
	}

	@Override
	public List findProductAttributeByPage(Page page) {
		return productAttributeMapper.findProductAttributeByPage(page);
	}

	@Override
	public Integer findProductAttributeByPageCount(Page page) {
		return productAttributeMapper.findProductAttributeByPageCount(page);
	}

}
