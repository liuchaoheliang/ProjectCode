package com.froad.db.chongqing.mappers;

import java.util.List;
import java.util.Map;



import com.froad.cbank.persistent.entity.ProductPresell;
import com.froad.cbank.persistent.entity.base.PageEntity;

/**
 * 预售商品接口
 * 
 * @author dongrui
 * @version $Id: ProductPresellMapper.java, v 0.1 2014年12月23日 下午4:45:29 Exp $
 */
public interface ProductPresellMapper {
	 /**
     * 
      * @Title: selectAllProductPresell
      * @Description: TODO
      * @author: Yaren Liang 2015年5月3日
      * @modify: Yaren Liang 2015年5月3日
      * @param @return    
      * @return List<ProductPresell>    
      * @throws
     */
    public ProductPresell selectProductPresellById(Long id);
   
}
