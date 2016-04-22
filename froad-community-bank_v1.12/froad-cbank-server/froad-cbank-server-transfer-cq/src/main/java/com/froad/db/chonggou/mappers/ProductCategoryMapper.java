/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: ProductCategoryMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.chonggou.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.ProductCategoryCG;
import com.froad.db.mysql.bean.Page;


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
     * 增加 ProductCategory
     * @param productCategory
     * @return Long    主键ID
     */
	public Long addProductCategory(ProductCategoryCG productCategory);

	

    /**
     * 修改 ProductCategory
     * @param productCategory
     * @return Boolean    是否成功
     */
	public void updateProductCategory(ProductCategoryCG productCategory);

    /**
     * 查询 ProductCategory
     * @param productCategory
     * @return ProductCategory    返回结果集
     */
	public List<ProductCategoryCG> getProductCategorys(ProductCategoryCG productCategory);

	
	
	
	

    /**
     *  查出所有记录
      * @Title: findAllProductCategory
      * @Description: TODO
      * @author: Yaren Liang 2015年5月2日
      * @modify: Yaren Liang 2015年5月2日
      * @param @return    
      * @return List<ProductCategory>    
      * @throws
     */
    public List<ProductCategoryCG> findAllProductCategory();
    /**
     * 根据ID寻找
      * @Title: findProductCategoryById
      * @Description: TODO
      * @author: Yaren Liang 2015年5月2日
      * @modify: Yaren Liang 2015年5月2日
      * @param @return    
      * @return ProductCategoryCG    
      * @throws
     */
    public ProductCategoryCG findProductCategoryById(Long id);
    
    /**
     * 
      * @Title: deleteProductCategory
      * @Description: TODO
      * @author: Yaren Liang 2015年6月26日
      * @modify: Yaren Liang 2015年6月26日
      * @param     
      * @return void    
      * @throws
     */
    public void deleteProductCategory(String id);
}