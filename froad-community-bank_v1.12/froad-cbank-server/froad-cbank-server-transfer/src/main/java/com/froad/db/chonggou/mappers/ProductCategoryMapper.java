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
import com.froad.fft.persistent.entity.ProductCategory;


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
	public void addProductCategory(ProductCategoryCG productCategory);

    /**
     * 批量增加 ProductCategory
     * @param List<ProductCategory>
     * @return Boolean    是否成功
     */
	public void addProductCategoryByBatch(List<ProductCategoryCG> productCategoryList);

	/**
	 * 逻辑删除 ProductCategory
	 * @param productCategory
	 * @return void
	 */
	public void deleteLogicCategory(ProductCategoryCG productCategory);

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
	 * 查询是否存在
	 * @param productCategory
	 * @return Integer
	 */
	public Integer getProductCategoryCount(ProductCategoryCG productCategory);
	
	/**
	 * 分页查询
	 * @param page
	 * @param productCategory
	 * @return List<ProductCategory>
	 */
	public List<ProductCategoryCG> findCategorysByPage(Page<ProductCategoryCG> page,@Param(value = "pc")ProductCategoryCG productCategory);
	
	/**
     * 批量增加 ProductCategory
     * @param productCategory
     * @return 
     */
    public void addProductCategoryBatch(List<ProductCategoryCG> productCategorys);
    
    /**
     * 不分页查询
     * @param page
     * @param productCategory
     * @return List<ProductCategory>
     */
    public List<ProductCategoryCG> findProductCategorys(Map<String,Object> param);
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
}