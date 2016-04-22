package com.froad.fft.service.impl;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.froad.fft.persistent.api.ProductAttributeMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.ProductAttribute;
import com.froad.fft.service.ProductAttributeService;

@Service("productAttributeServiceImpl")
public class ProductAttributeServiceImpl implements ProductAttributeService {

	private static Logger logger = Logger.getLogger(ProductAttributeServiceImpl.class);

	@Resource
	private ProductAttributeMapper productAttributeMapper;
	
	@Override
	public Long saveProductAttribute(ProductAttribute productAttribute) {
		return productAttributeMapper.saveProductAttribute(productAttribute);
	}

	@Override
	public Boolean updateProductAttributeById(ProductAttribute productAttribute) {
		if(productAttribute.getId() == null){
			logger.error("更新对象缺少必要Id字段值");
			return false;
		}
		return productAttributeMapper.updateProductAttributeById(productAttribute);
	}

	@Override
	public ProductAttribute selectProductAttributeById(Long id) {
		if(id == null){
			logger.error("查询数据id为空");
			return null;
		}
		return productAttributeMapper.selectProductAttributeById(id);
	}

	@Override
	public Page findProductAttributeByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(productAttributeMapper.findProductAttributeByPage(page));
		page.setTotalCount(productAttributeMapper.findProductAttributeByPageCount(page));
		return page;
	}

}
