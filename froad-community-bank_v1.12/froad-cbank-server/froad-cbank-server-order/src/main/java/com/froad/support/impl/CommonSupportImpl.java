package com.froad.support.impl;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.po.mongo.ProductDetail;
import com.froad.po.qrcodeproduct.OutletProduct;
import com.froad.support.CommonSupport;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *  门店虚拟商品
  * @ClassName: OutletProductSupportImpl
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public class CommonSupportImpl implements CommonSupport {

	private MongoManager mongo = new MongoManager();
	
	private final String CB_OUTLET_PRODUCT = "cb_outlet_product";
	private final String  CB_PRODUCT_DETAILS = "cb_product_details";
	
	/**
	 *  根据商品ID获取虚拟商品
	  * @Title: queryOutletProduct
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param productId
	  * @param @return
	  * @throws
	  * @see com.froad.support.OutletProductSupport#queryOutletProduct(java.lang.String)
	 */
	@Override
	public OutletProduct queryOutletProduct(String productId) {
		// TODO Auto-generated method stub
		DBObject dbo = new BasicDBObject();
		dbo.put("_id", productId);
		return mongo.findOne(dbo, CB_OUTLET_PRODUCT, OutletProduct.class);
	}
	
	
	/**
	 *  获取商品详情 MongoDb
	  * @Title: queryProductDetail
	  * @Description: TODO
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @param productId
	  * @param @return
	  * @throws
	  * @see com.froad.support.ShoppingCartSupport#queryProductDetail(java.lang.String)
	 */
	@Override
	public ProductDetail queryProductDetail(String productId) {
		// TODO Auto-generated method stub
		return mongo.findOneById(productId, CB_PRODUCT_DETAILS, ProductDetail.class);
	}

	
}

