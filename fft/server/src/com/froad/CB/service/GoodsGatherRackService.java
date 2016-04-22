package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.GoodsGatherRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-1  
 * @version 1.0
 */
@WebService
public interface GoodsGatherRackService {
	/**
	  * 方法描述：添加商品收款架
	  * @param: GoodsGatherRack
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addGoodsGatherRack(GoodsGatherRack goodsGatherRack);
	
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
	GoodsGatherRack selectById(Integer id);
	
	/**
	  * 方法描述：按主键删除商品收款架
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteById(String id);
	
	/**
	 * 逻辑删除 商品收款架
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateById(String id);
	
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
