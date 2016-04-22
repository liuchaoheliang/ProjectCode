package com.froad.support;

import com.froad.po.mongo.ProductDetail;
import com.froad.po.qrcodeproduct.OutletProduct;

/**
 *  面对面虚拟商品
  * @ClassName: OutletProductSupport
  * @Description: TODO
  * @author share 2015年3月26日
  * @modify share 2015年3月26日
 */
public interface CommonSupport {
	
	/**
	 *  获取门店虚拟商品信息
	  * @Title: queryOutletProduct
	  * @Description: TODO
	  * @author: share 2015年3月26日
	  * @modify: share 2015年3月26日
	  * @param @param productId
	  * @param @return    
	  * @return OutletProduct    
	  * @throws
	 */
	public abstract OutletProduct queryOutletProduct(String productId);
	
	
	/**
	 *  获取商品详情
	  * @Title: queryProductDetail
	  * @Description: TODO
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @param prodcutId
	  * @param @return    
	  * @return ProductDetail    
	  * @throws
	 */
	public abstract ProductDetail queryProductDetail(String prodcutId);

}

