package com.froad.fft.persistent.api.impl;

import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.persistent.api.ProductCategoryMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductCategory;

public class ProductCategoryMapperImpl implements ProductCategoryMapper {

	@Resource
	private ProductCategoryMapper productCategoryMapper;
	
	@Override
	public Long saveProductCategory(ProductCategory productCategory) {
		productCategoryMapper.saveProductCategory(productCategory);
		return productCategory.getId();
	}

	@Override
	public Boolean updateProductCategoryById(ProductCategory productCategory) {
		return productCategoryMapper.updateProductCategoryById(productCategory);
	}

	@Override
	public ProductCategory selectProductCategoryById(Long id) {
		return productCategoryMapper.selectProductCategoryById(id);
	}

	@Override
	public List<ProductCategory> findProductCategoryByPage(Page page) {
		return productCategoryMapper.findProductCategoryByPage(page);
	}

	@Override
	public Integer findProductCategoryByPageCount(Page page) {
		return productCategoryMapper.findProductCategoryByPageCount(page);
	}

	@Override
	public ProductCategory getProductCategoryById(Long id) {
		return productCategoryMapper.getProductCategoryById(id);
	}

}
