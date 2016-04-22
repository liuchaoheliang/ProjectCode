package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.dto.ProductCategoryDto;
import com.froad.fft.persistent.api.ProductCategoryMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductCategory;
import com.froad.fft.service.ProductCategoryService;

@Service("productCategoryServiceImpl")
public class ProductCategoryServiceImpl implements ProductCategoryService {

	private static Logger logger = Logger.getLogger(ProductCategoryServiceImpl.class);
	
	@Resource
	private ProductCategoryMapper productCategoryMapper;
	
	@Override
	public Long saveProductCategory(ProductCategory productCategory) {
		return productCategoryMapper.saveProductCategory(productCategory);
	}

	@Override
	public Boolean updateProductCategoryById(ProductCategory productCategory) {
		if(productCategory.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return productCategoryMapper.updateProductCategoryById(productCategory);
	}

	@Override
	public ProductCategory selectProductCategoryById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return productCategoryMapper.selectProductCategoryById(id);
	}

	@Override
	public Page findProductCategoryByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(productCategoryMapper.findProductCategoryByPage(page));
		page.setTotalCount(productCategoryMapper.findProductCategoryByPageCount(page));
		return page;
	}

//	@Override
//	public ProductCategory getProductCategoryById(Long id) {
//		return productCategoryMapper.getProductCategoryById(id);
//	}

}
