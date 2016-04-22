package com.froad.db.chongqing.mappers;

import java.util.List;
import java.util.Map;

import com.froad.cbank.persistent.entity.GroupProduct;
import com.froad.cbank.persistent.entity.ProductGroup;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface ProductGroupMapper {

	 /**
     * 
      * @Title: selectAllProductGroup
      * @Description: TODO
      * @author: Yaren Liang 2015年5月3日
      * @modify: Yaren Liang 2015年5月3日
      * @param @return    
      * @return ProductGroup    
      * @throws
     */
    public ProductGroup selectProductGroupById(Long id);
}
