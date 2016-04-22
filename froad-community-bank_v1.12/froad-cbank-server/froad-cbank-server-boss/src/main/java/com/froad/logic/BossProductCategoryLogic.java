package com.froad.logic;

import java.util.List;
import java.util.Map;

import com.froad.po.ProductCategory;
import com.froad.po.ProductCategoryInput;
import com.froad.thrift.vo.product.BossProductCategoryVo;

public interface BossProductCategoryLogic {
	
	/**
	 * 查询商品分类列表
	 * @param clientId
	 * @param iscludeDisable
	 * @param isMall 是否精品商城商品分类
	 * @param originVo
	 * 
	 * @return List<ProductCategory>
	 * @author liuyanyun 2015-9-18 下午15:50
	 */
	public List<ProductCategory> findCategorys(String clientId, boolean iscludeDisable,boolean isMall);
	
	/**
	 * 查询商品分类信息
	 * @param id
	 * @param clientId
	 * 
	 * @return ProductCategory
	 * @author liuyanyun 2015-9-18 下午15:50
	 */
	public BossProductCategoryVo getBossProductCategoryById(long id, String clientId);
	
	/**
	 * 新增商品分类
	 * @param po
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-18 下午15:50
	 */
	public int addBossProductCategoryVo(ProductCategory po);
	
	/**
	 * 修改增商品分类
	 * @param po
	 * 
	 * @return int
	 * @author liuyanyun 2015-9-18 下午15:50
	 */
	public int updateBossProductCategoryVo(ProductCategory po);
	
	/**
	 * 
	 * productCategoryInput:(商品分类的商品导入).
	 *
	 * @author liuyanyun
	 * 2015年11月3日 下午18:50:45
	 * @param inputs
	 * @param isMall 是否精品商城
	 * @return
	 * @throws Exception
	 *
	 */
	public List<ProductCategoryInput> productCategoryInput(List<ProductCategoryInput> inputs,boolean isMall) throws Exception;
	
	/**
	 * 
	 * productCategoryDetailExport:(商品分类的商品明细导出).
	 *
	 * @author liuyanyun
	 * 2015年11月4日 下午18:58:17
	 * @param clientId
	 * @param categoryId
	 * @return
	 * @throws Exception
	 *
	 */
	public Map<String, Object> productCategoryDetailExport(String clientId, Long categoryId, Boolean isMall) throws Exception;
	
}
