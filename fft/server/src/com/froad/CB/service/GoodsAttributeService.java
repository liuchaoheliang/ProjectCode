package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.GoodsAttribute;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:21:55
 * @version 1.0
 * 
 */
@WebService
public interface GoodsAttributeService {
	
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
