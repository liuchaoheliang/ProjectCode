package com.froad.db.mongo;

import com.froad.po.mongo.ProductDetail;

public interface ProductDetailMongoService {
	
	/**
	 * 
	 * @Title: findByProductId 
	 * @Description: 根据productId查询
	 * @author: froad-huangyihao 2015年4月23日
	 * @modify: froad-huangyihao 2015年4月23日
	 * @param productId
	 * @return
	 * @throws
	 */
	ProductDetail findByProductId(String productId);
}
