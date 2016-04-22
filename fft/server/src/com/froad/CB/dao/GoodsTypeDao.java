package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.GoodsType;

/** 
 * @author FQ 
 * @date 2013-1-29 下午03:16:27
 * @version 1.0
 * 商品类型
 */
public interface GoodsTypeDao {
	
	/**
	 * 增加商品类型
	 * @param goodsType
	 * @return
	 */
	public Integer addGoodsType(GoodsType goodsType);
	
	/**
	 * 根据ID 查找商品类型
	 * @param id
	 * @return
	 */
	public GoodsType getGoodsTypeById(Integer id);
	
	/**
	 * 根据 GoodsType 更新商品类型
	 * @param goodsType
	 * @return
	 */
	public boolean updateGoodsType(GoodsType goodsType);
	
	/**
	 * 根据 GoodsType条件 查找商品类型
	 * @param goodsType
	 * @return
	 */
	public List<GoodsType> getGoodsTypeList(GoodsType goodsType);
	
	/**
	 * 查询所有商品类型
	 * @return
	 */
	public List<GoodsType> getAllGoodsType();
}
