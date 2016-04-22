/**
 * 
 * @Title: ProductCategoryLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;
import java.util.Map;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ProductCategory;
import com.froad.po.Result;
import com.froad.thrift.vo.AddProductCategoryVoRes;

/**
 * 
 * <p>@Title: ProductCategoryLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface ProductCategoryLogic {

    /**
     * 增加 ProductCategory
     * @param productCategory
     * @return Long    主键ID
     */
	public Long addProductCategory(ProductCategory productCategory);

    /**
     * 删除 ProductCategory
     * @param productCategory
     * @return Result
     */
	public Result deleteProductCategory(ProductCategory productCategory);

	/**
     * 修改 ProductCategory
     * @param productCategory
     * @return Result
     */
	public Result updateProductCategory(ProductCategory productCategory);

    /**
     * 查询 ProductCategory
     * @param productCategory
     * @return ProductCategory    结果集合 
     */
	public ProductCategory getProductCategoryById(ProductCategory productCategory);

	/**
     * 判断商品分类是否已经存在，（新加商品以及更新商品时候需要判断：同一个客户端同一个路径下只能有一个）
     * @param productCategory
     * @return Boolean    是否成功
     */
    public Boolean isProductCategoryExist(ProductCategory productCategory);

    /**
     * 查询 ProductCategory
     * @param productCategory
     * @return List<ProductCategory>    结果集合 
     */
    public List<ProductCategory> findCategorysByPage(Page<ProductCategory> page,ProductCategory productCategory);
    
    /**
	 * 获取商品分类对象
	 * @param clientId
	 * @param categoryId
	 * @return
	 */
    public ProductCategory findCategoryById(String clientId,Long categoryId);
    
    
    /**
     * 查询 ProductCategory
     * @param productCategory
     * @return List<ProductCategory>    结果集合 
     */
    public List<ProductCategory> findCategorys(String clientId);
    
    /**
     * 批量增加 ProductCategory
     * @param productCategorys
     * @return List<AddProductCategoryVoRes>
     */
    public List<AddProductCategoryVoRes> addProductCategoryBatch(List<ProductCategory> productCategorys);

    
    /**
     * 分级查询商品分类:商户新加团购商品时候用
     * @param param
     * @return
     */
    public Map<String,Object> getManageProductCategorys(Map<String,Object> param);
    
    /**
     * 查询商品分类:特惠商品分类首页商品分类查询-H5用户端
     * @param clientId 客户端ID
     * @param parentId 商品分类ID
     * @param areaId 地区ID
     * @return
     */
    public Map<String,Object> queryProductCategorys(String clientId, Long parentId, Long areaId);

    /**
     * 按照parentId去查询商品分类，不会带是否有下级分类的标志
     * @param param
     * @return
     */
    public List<ProductCategory> getGoodsCategorys(Map<String, Object> param);

	/**
	 * 
	 * getProductCategoryByIds:根据分类id集合查询商品分类集合
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月25日 下午4:10:11
	 * @param clientId
	 * @param categoryIds
	 * @return
	 */
	public List<ProductCategory> getProductCategoryByIds(String clientId,
			List<String> categoryIds);
}