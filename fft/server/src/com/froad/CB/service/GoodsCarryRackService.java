package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.GoodsCarryRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-1  
 * @version 1.0
 */
@WebService
public interface GoodsCarryRackService {
	/**
	  * 方法描述：添加商品提现架
	  * @param: GoodsCarryRack
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addGoodsCarryRack(GoodsCarryRack goodsCarryRack);
	
	/**
	  * 方法描述：按主键更新商品提现架信息
	  * @param: GoodsCarryRack
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(GoodsCarryRack goodsCarryRack);
	
	/**
	  * 方法描述：查询商品提现架
	  * @param: id
	  * @return: GoodsCarryRack
	  * @version: 1.0
	  */
	GoodsCarryRack selectById(Integer id);
	
	/**
	  * 方法描述：按主键删除商品提现架
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteById(String id);
	
	/**
	 * 逻辑删除 商品提现架
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateById(String id);
	
	/**
	  * 方法描述：查询商品提现架
	  * @param: goodsId
	  * @return: List<GoodsCarryRack>
	  * @version: 1.0
	  */
	List<GoodsCarryRack> getGoodsCarryRackByGoodsId(String goodsId);
	
	/**
	  * 方法描述：查询商品提现架
	  * @param: GoodsCarryRack
	  * @return: List<GoodsCarryRack>
	  * @version: 1.0
	  */
	List<GoodsCarryRack> getGoodsCarryRack(GoodsCarryRack goodsCarryRack);
	
	/**
	 * 分页查询商品提现架信息
	 * @param GoodsCarryRack
	 * @return GoodsCarryRack
	 */
	public GoodsCarryRack getGoodsCarryRackListByPager(GoodsCarryRack goodsCarryRack);
}
