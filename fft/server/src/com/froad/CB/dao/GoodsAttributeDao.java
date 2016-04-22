package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.GoodsAttribute;

/** 
 * @author FQ 
 * @date 2013-1-29 下午03:15:32
 * @version 1.0
 * 商品属性
 */
public interface GoodsAttributeDao {
	
	/**
	 * 增加 商品属性
	 * @param goodsAttribute
	 * @return
	 */
	public Integer addGoodsAttribute(GoodsAttribute goodsAttribute);
	
	/**
	 * 根据ID 查找 商品属性
	 * @param id
	 * @return
	 */
	public GoodsAttribute getGoodsAttributeById(Integer id);
	
	/**
	 * 根据 GoodsAttribute条件 更新商品属性
	 * @param goodsAttribute
	 * @return
	 */
	public boolean updateGoodsAttribute(GoodsAttribute goodsAttribute);
	
	/**
	 * 根据 GoodsAttribute条件 查找商品属性
	 * @param goodsTypeId
	 * @return
	 */
	public List<GoodsAttribute> getGoodsAttributeList(GoodsAttribute goodsAttribute);
}
