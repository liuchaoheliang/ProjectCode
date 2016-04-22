/**
 * 
 * @Title: ProductCategoryMapper.java
 * @Package com.froad.db.mysql.mapper
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.db.mysql.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
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
     * 增加 ProductCategory
     * @param productCategory
     * @return Long    主键ID
     */
	public void addProductCategory(ProductCategory productCategory);

    /**
     * 批量增加 ProductCategory
     * @param List<ProductCategory>
     * @return Boolean    是否成功
     */
	public void addProductCategoryByBatch(List<ProductCategory> productCategoryList);

	/**
	 * 逻辑删除 ProductCategory
	 * @param productCategory
	 * @return void
	 */
	public void deleteLogicCategory(ProductCategory productCategory);

    /**
     * 修改 ProductCategory
     * @param productCategory
     * @return Boolean    是否成功
     */
	public void updateProductCategory(ProductCategory productCategory);

    /**
     * 查询 ProductCategory
     * @param productCategory
     * @return ProductCategory    返回结果集
     */
	public List<ProductCategory> getProductCategorys(ProductCategory productCategory);

	/**
	 * 查询是否存在
	 * @param productCategory
	 * @return Integer
	 */
	public Integer getProductCategoryCount(ProductCategory productCategory);
	
	/**
	 * 分页查询
	 * @param page
	 * @param productCategory
	 * @return List<ProductCategory>
	 */
	public List<ProductCategory> findCategorysByPage(Page<ProductCategory> page,@Param(value = "pc")ProductCategory productCategory);
	
	/**
     * 批量增加 ProductCategory
     * @param productCategory
     * @return 
     */
    public void addProductCategoryBatch(List<ProductCategory> productCategorys);
    
    /**
     * 不分页查询
     * @param page
     * @param productCategory
     * @return List<ProductCategory>
     */
    public List<ProductCategory> findProductCategorys(Map<String,Object> param);

    /**
     * 查询商品分类对象
     * @param Long
     * @return String
     */
    public ProductCategory findProductCategoryById(Long id);
    
    /**
     * 查询商品分类:特惠商品分类首页商品分类查询-H5用户端
     * @param param
     * @return
     */
    public List<ProductCategory> findCategorys(Map<String,Object> param);
    
    /**
     * 获取分类下一集子分类的数量
     * @param param
     * @return
     */
    public List<Map<String,Long>> findCategoryChildNum(Map<String,Object> param);
    
    
}