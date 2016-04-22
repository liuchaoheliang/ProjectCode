package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ProductTypeMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductType;

public class ProductTypeMapperImpl implements ProductTypeMapper {

	@Resource
	private ProductTypeMapper productTypeMapper;
	
	@Override
	public Long saveProductType(ProductType productType) {
		return productTypeMapper.saveProductType(productType);
	}

	@Override
	public Boolean updateProductTypeById(ProductType productType) {
		return productTypeMapper.updateProductTypeById(productType);
	}

	@Override
	public ProductType selectProductTypeById(Long id) {
		return productTypeMapper.selectProductTypeById(id);
	}

	@Override
	public List findProductTypeByPage(Page page) {
		return productTypeMapper.findProductTypeByPage(page);
	}

	@Override
	public Integer findProductTypeByPageCount(Page page) {
		return productTypeMapper.findProductTypeByPageCount(page);
	}

}
