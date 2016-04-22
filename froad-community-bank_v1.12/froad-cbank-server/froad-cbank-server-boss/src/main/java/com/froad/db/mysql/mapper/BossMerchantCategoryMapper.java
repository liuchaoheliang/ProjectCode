package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.MerchantCategory;
import com.froad.po.ProductCategory;

public interface BossMerchantCategoryMapper {
	/**
	 * 查询商户分类列表
	 * @param clientId
	 * @param iscludeDisable
	 * @param originVo
	 * 
	 * @return List<MerchantCategory>
	 * @author liuyanyun 2015-9-21 下午14:00
	 */
	public List<MerchantCategory> findCategorys(@Param(value = "clientId")String clientId, @Param(value = "iscludeDisable")boolean iscludeDisable);
	
	/**
	 * 查询商户分类信息
	 * @param id
	 * @param clientId
	 * 
	 * @return MerchantCategory
	 * @author liuyanyun 2015-9-21 下午14:02
	 */
	public MerchantCategory getBossMerchantCategoryById(@Param(value = "id")long id, @Param(value = "clientId")String clientId);
	
	/**
	 * 新增商户分类
	 * @param mo
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-21 下午14:03
	 */
	public int addBossMerchantCategoryVo(@Param(value = "mo")MerchantCategory mo);
	
	/**
	 * 修改商户分类信息的treePath
	 * @param mo
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-21 下午14:04
	 */
	public int updateBossMerchantCategoryVoTreePath(@Param(value = "mo")MerchantCategory mo);
	
	/**
	 * 根据分类名称查询是否存在该分类信息
	 * @param clientId
	 * @param name
	 * 
	 * @return 
	 * @author liuyanyun 2015-9-29  下午18:36
	 */
	public int findByName(@Param(value = "clientId")String clientId, @Param(value = "name")String name);
	
	/**
	 * 修改商户分类信息
	 * @param mo
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-21 下午14:04
	 */
	public int updateBossMerchantCategoryVo(@Param(value = "mo")MerchantCategory mo);
	
	/**
	 * 根据分类名称查询商品分类信息
	 * @param clientId
	 * @param name
	 * 
	 * @return MerchantCategory
	 * @author liuyanyun 2015-10-13 下午15:10
	 */
	public MerchantCategory getBossMerchantCategoryByName(@Param(value = "clientId")String id, @Param(value = "name")String name);
	
	
	public MerchantCategory findByCategoryId(@Param(value = "categoryId")Long categoryId, @Param("clientId")String clientId);
	
	public List<MerchantCategory> findChildList(@Param(value = "categoryId")String categoryId, @Param("clientId")String clientId);
	
	
	public List<MerchantCategory> findNoDeleteParentList(@Param(value = "clientId")String clientId);
	
	
	
}	
