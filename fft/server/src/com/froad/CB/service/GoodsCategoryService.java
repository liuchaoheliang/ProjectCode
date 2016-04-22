package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.GoodsCategory;

/** 
 * @author FQ 
 * @date 2013-1-29 下午05:15:58
 * @version 1.0
 * 商品分类
 */
@WebService
public interface GoodsCategoryService {
	
	/**
	 * 增加商品分类
	 * @param goodsCategory
	 * @return
	 */
	public Integer addGoodsCategory(GoodsCategory goodsCategory);
	
	/**
	 * 根据ID查找商品分类
	 * @param id
	 * @return
	 */
	public GoodsCategory getGoodsCategoryById(Integer id);
	
	/**
	 * 根据 GoodsCategory 更新商品分类
	 * @param goodsCategory
	 * @return
	 */
	public boolean updateGoodsCategory(GoodsCategory goodsCategory);
	
	/**
	 * 获取所有顶级分类
	 * @return
	 */
	public List<GoodsCategory> getRootGoodsCategoryList();
	
	/**
	 * 根据 GoodsCategory 所有子类集合
	 * @param goodsCategory
	 * @return
	 */
	public List<GoodsCategory> getChildrenGoodsCategoryList(GoodsCategory goodsCategory);
	
	/**
	 * 获取所有 商品分类
	 * @return
	 */
	public List<GoodsCategory> getAllGoodsCategory();
}
