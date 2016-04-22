package com.froad.fft.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.ProductTypeMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductType;
import com.froad.fft.service.ProductTypeService;

@Service("productTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

	private static Logger logger = Logger.getLogger(ProductTypeServiceImpl.class);
	
	@Resource
	private ProductTypeMapper productTypeMapper;

	@Override
	public Long saveProductType(ProductType productType) {
		return productTypeMapper.saveProductType(productType);
	}

	@Override
	public Boolean updateProductTypeById(ProductType productType) {
		if(productType.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return productTypeMapper.updateProductTypeById(productType);
	}

	@Override
	public ProductType selectProductTypeById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return productTypeMapper.selectProductTypeById(id);
	}

	@Override
	public Page findProductTypeByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(productTypeMapper.findProductTypeByPage(page));
		page.setTotalCount(productTypeMapper.findProductTypeByPageCount(page));
		return page;
	}

}
