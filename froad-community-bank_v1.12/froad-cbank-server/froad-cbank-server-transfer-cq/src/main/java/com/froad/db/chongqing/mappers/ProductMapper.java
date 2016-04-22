package com.froad.db.chongqing.mappers;

import java.util.List;
import java.util.Map;

import com.froad.cbank.persistent.entity.Product;
import com.froad.cbank.persistent.entity.base.PageEntity;

/**
 * 商品接口
 * 
 * @author dongrui
 * @version $Id: ProductMapper.java, v 0.1 2014年12月23日 下午4:28:26 Exp $
 */
public interface ProductMapper {
	
	/**
	 * 
	  * @Title: selectAllProduct
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月25日
	  * @modify: Yaren Liang 2015年6月25日
	  * @param @return    
	  * @return List<Product>    
	  * @throws
	 */
	public List<Product> selectAllProduct();
	
	
	public List<Product> selectAllProductImage();
	
    /**
     * 
      * @Title: selectProductById
      * @Description: TODO
      * @author: Yaren Liang 2015年5月3日
      * @modify: Yaren Liang 2015年5月3日
      * @param @param product
      * @param @return    
      * @return Product    
      * @throws
     */
    public Product selectMerchantByProductId(Long id);
}
