/**
 * 
 * @Title: ProductCategoryMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import com.froad.po.ProductCategory;

/**
 * 
 * <p>@Title: ProductCategoryMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProductCategoryMapper {

    /**
     * 查询商品分类对象
     * @param Long
     * @return String
     */
    public ProductCategory findProductCategoryById(Long id);
    
    
}