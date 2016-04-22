package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.GoodsGatherRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-30  
 * @version 1.0
 * 商品收款架DAO
 */
public interface GoodsGatherRackDao {
	/**
	  * 方法描述：添加商品收款架
	  * @param: GoodsGatherRack
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer insert(GoodsGatherRack goodsGatherRack);
	
	/**
	  * 方法描述：按主键更新商品收款架信息
	  * @param: GoodsGatherRack
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(GoodsGatherRack goodsGatherRack);
	
	/**
	  * 方法描述：查询商品收款架
	  * @param: id
	  * @return: GoodsGatherRack
	  * @version: 1.0
	  */
	GoodsGatherRack selectByPrimaryKey(Integer id);
	
	/**
	  * 方法描述：按主键删除商品收款架
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteByPrimaryKey(String id);
	
	/**
	 * 逻辑删除 商品收款架
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateByPrimaryKey(String id);
	
	/**
	  * 方法描述：查询商品收款架
	  * @param: goodsId
	  * @return: List<GoodsGatherRack>
	  * @version: 1.0
	  */
	List<GoodsGatherRack> getGoodsGatherRackByGoodsId(String goodsId);
	
	/**
	  * 方法描述：查询商品收款架
	  * @param: GoodsGatherRack
	  * @return: List<GoodsGatherRack>
	  * @version: 1.0
	  */
	List<GoodsGatherRack> getGoodsGatherRack(GoodsGatherRack goodsGatherRack);
	
	/**
	 * 分页查询商品收款架信息
	 * @param GoodsGatherRack
	 * @return GoodsGatherRack
	 */
	public GoodsGatherRack getGoodsGatherRackListByPager(GoodsGatherRack goodsGatherRack);
}
