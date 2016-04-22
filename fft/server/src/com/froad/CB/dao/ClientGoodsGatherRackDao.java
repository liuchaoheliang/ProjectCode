package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.ClientGoodsGatherRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 * 手机客户端     商品收款架DAO
 */
public interface ClientGoodsGatherRackDao {
	/**
	  * 方法描述：添加商品收款架
	  * @param: ClientGoodsGatherRack
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer insert(ClientGoodsGatherRack ClientGoodsGatherRack);
	
	/**
	  * 方法描述：按主键更新商品收款架信息
	  * @param: ClientGoodsGatherRack
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(ClientGoodsGatherRack ClientGoodsGatherRack);
	
	/**
	  * 方法描述：查询商品收款架
	  * @param: id
	  * @return: ClientGoodsGatherRack
	  * @version: 1.0
	  */
	ClientGoodsGatherRack selectByPrimaryKey(Integer id);
	
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
	  * @return: List<ClientGoodsGatherRack>
	  * @version: 1.0
	  */
	List<ClientGoodsGatherRack> getClientGoodsGatherRackByGoodsId(String goodsId);
	
	/**
	  * 方法描述：查询商品收款架
	  * @param: ClientGoodsGatherRack
	  * @return: List<ClientGoodsGatherRack>
	  * @version: 1.0
	  */
	List<ClientGoodsGatherRack> getClientGoodsGatherRack(ClientGoodsGatherRack ClientGoodsGatherRack);
	
	/**
	 * 分页查询商品收款架信息
	 * @param ClientGoodsGatherRack
	 * @return ClientGoodsGatherRack
	 */
	public ClientGoodsGatherRack getClientGoodsGatherRackListByPager(ClientGoodsGatherRack ClientGoodsGatherRack);
}
