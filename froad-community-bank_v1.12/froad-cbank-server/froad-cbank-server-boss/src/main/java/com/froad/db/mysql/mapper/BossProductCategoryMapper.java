package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.froad.po.ProductCategory;

public interface BossProductCategoryMapper {
	
	/**
	 * 查询商品分类列表
	 * @param clientId
	 * @param iscludeDisable
	 * @param isMall 是否精品商城商品分类
	 * @param originVo
	 * 
	 * @return List<ProductCategory>
	 * @author liuyanyun 2015-9-18 上午11:33
	 */
	public List<ProductCategory> findCategorys(@Param(value = "clientId")String clientId, @Param(value = "iscludeDisable")boolean iscludeDisable, @Param(value = "isMall") boolean isMall);
	
	/**
	 * 查询商品分类信息
	 * @param id
	 * 
	 * @return ProductCategory
	 * @author liuyanyun 2015-9-18 下午14:42
	 */
	public ProductCategory getBossProductCategoryById(@Param(value = "id")long id, @Param(value = "clientId")String clientId);
	
	/**
	 * 新增商品分类
	 * @param po
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-18 下午15:42
	 */
	public int addBossProductCategoryVo(@Param(value = "po")ProductCategory po);
	
	/**
	 * 修改商品分类信息treePath
	 * @param po
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-18 下午16:53
	 */
	public int updateBossProductCategoryVoTreePath(@Param(value = "po")ProductCategory po);
	
	/**
	 * 根据分类名称查询是否存在该分类信息
	 * @param clientId
	 * @param name
	 * 
	 * @return 
	 * @author liuyanyun 2015-9-29  下午18:36
	 */
	public int findByName(@Param(value = "clientId")String clientId, @Param(value = "name")String name,@Param(value = "isMall") Boolean isMall);
	
	/**
	 * 修改商品分类信息
	 * @param po
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-18 下午16:53
	 */
	public int updateBossProductCategoryVo(@Param(value = "po")ProductCategory po);
	
	/**
	 * 根据分类名称查询商品分类信息
	 * @param clientId
	 * @param name
	 * 
	 * @return ProductCategory
	 * @author liuyanyun 2015-10-13 下午14:53
	 */
	public ProductCategory getBossProductCategoryByName(@Param(value = "clientId")String id, @Param(value = "name")String name, @Param(value = "isMall")boolean isMall);
	
	/**
	 * 查询分类id的子分类信息
	 * @param categoryId
	 * @param clientId
	 * 
	 * @return findChildList
	 * @author liuyanyun 2015-11-4 下午18:53
	 */
	public List<ProductCategory> findChildList(@Param(value = "categoryId")String categoryId, @Param("clientId")String clientId);
	
	public ProductCategory findByCategoryId(@Param("categoryId") Long categoryId, @Param("clientId")String clientId);
	
	
	public List<ProductCategory> findNoDeleteParentList(@Param("clientId")String clientId, @Param(value = "isMall")boolean isMall);
	
	
	
}	
