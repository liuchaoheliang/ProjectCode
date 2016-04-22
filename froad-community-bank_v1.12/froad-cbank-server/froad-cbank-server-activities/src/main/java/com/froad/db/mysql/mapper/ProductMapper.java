package com.froad.db.mysql.mapper;

import java.util.List;

import com.froad.po.Product;


public interface ProductMapper {
	/**
	 * @Title: findProductByMerchantId
	 * @Description: 通过商户ID找所有的该商户下的产品
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param page，activeRuleInfo
	 * @return
	*/
	public List<Product> findProductByMerchantId(String merchantId);
}
