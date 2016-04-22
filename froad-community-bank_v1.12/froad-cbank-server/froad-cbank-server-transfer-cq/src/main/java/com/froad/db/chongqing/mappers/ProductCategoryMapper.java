package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.ProductCategory;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface ProductCategoryMapper {

 
   /**
    * 
     * @Title: findAllProductCategory
     * @Description: TODO
     * @author: Yaren Liang 2015年6月25日
     * @modify: Yaren Liang 2015年6月25日
     * @param @return    
     * @return List<ProductCategory>    
     * @throws
    */
    public List<ProductCategory> findAllProductCategory();

}
