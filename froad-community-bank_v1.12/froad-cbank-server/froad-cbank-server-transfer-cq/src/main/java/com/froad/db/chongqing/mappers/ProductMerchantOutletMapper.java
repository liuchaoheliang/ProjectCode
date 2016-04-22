package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.ProductMerchantOutlet;

/**
 *  mybatis接口
  * @ClassName: ProductMerchantOutletMapper
  * @Description: TODO
  * @author share 2014年12月29日
  * @modify share 2014年12月29日
 */
public interface ProductMerchantOutletMapper {
/**
 * 
  * @Title: findProductOutletByProductId
  * @Description: TODO
  * @author: Yaren Liang 2015年6月30日
  * @modify: Yaren Liang 2015年6月30日
  * @param @param id
  * @param @return    
  * @return List<ProductMerchantOutlet>    
  * @throws
 */
	public List<ProductMerchantOutlet> findProductOutletByProductId(Long id);
}

