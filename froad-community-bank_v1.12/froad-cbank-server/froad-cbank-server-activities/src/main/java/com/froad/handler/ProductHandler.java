package com.froad.handler;

import java.util.List;

import com.froad.po.Product;
import com.froad.po.ProductDetail;

public interface ProductHandler {
	/**
	 * @Title: findProductByMerchantId
	 * @Description: 通过商户ID获得商品列表
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param merchantId
	 * @return
	*/
	public List<Product> findProductByMerchantId(String merchantId);
	/**
	 * @Title: getProductIdAndMerchantIdByClientId
	 * @Description: 通过客户端获得所有的商户ID和商品ID
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param clientId
	 * @return
	*/
	public List<ProductDetail> getProductIdAndMerchantIdByClientId(String clientId);
}
