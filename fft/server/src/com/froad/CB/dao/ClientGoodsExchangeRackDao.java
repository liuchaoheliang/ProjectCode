package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.ClientGoodsExchangeRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 * 手机客户端    商品兑换架
 */
public interface ClientGoodsExchangeRackDao {
	/**
	  * 方法描述：添加商品兑换架
	  * @param: ClientGoodsExchangeRack
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer insert(ClientGoodsExchangeRack goodsExchangeRack);
	
	/**
	  * 方法描述：按主键更新商品兑换架信息
	  * @param: ClientGoodsExchangeRack
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(ClientGoodsExchangeRack goodsExchangeRack);
	
	/**
	  * 方法描述：查询商品兑换架
	  * @param: id
	  * @return: ClientGoodsExchangeRack
	  * @version: 1.0
	  */
	ClientGoodsExchangeRack selectByPrimaryKey(Integer id);
	
	/**
	  * 方法描述：按主键删除商品兑换架
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteByPrimaryKey(String id);
	
	/**
	 * 逻辑删除 商品兑换架
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateByPrimaryKey(String id);
	
	/**
	  * 方法描述：查询商品兑换架
	  * @param: goodsId
	  * @return: List<ClientGoodsExchangeRack>
	  * @version: 1.0
	  */
	List<ClientGoodsExchangeRack> getClientGoodsExchangeRackByGoodsId(String goodsId);
	
	/**
	  * 方法描述：查询商品兑换架 BY goods_category_id
	  * @param: goodsId
	  * @return: List<ClientGoodsExchangeRack>
	  * @version: 1.0
	  */
	List<ClientGoodsExchangeRack> getClientGoodsExchangeRackByGoodsCategoryId(String goodsCategoryId);
	
	/**
	  * 方法描述：查询商品兑换架
	  * @param: GoodsExchangeRack
	  * @return: List<ClientGoodsExchangeRack>
	  * @version: 1.0
	  */
	List<ClientGoodsExchangeRack> getClientGoodsExchangeRack(ClientGoodsExchangeRack goodsExchangeRack);
	
	/**
	 * 分页查询商品兑换架信息
	 * @param ClientGoodsExchangeRack
	 * @return ClientGoodsExchangeRack
	 */
	public ClientGoodsExchangeRack getClientGoodsExchangeRackListByPager(ClientGoodsExchangeRack goodsExchangeRack);

	
	/**
	  * 方法描述：添加商品销售数量
	  * @param: addNumber 销售数量
	  * @param: id 上架商品编号
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 9, 2013 5:29:56 PM
	  */
	public void addSaleNumberById(int addNumber,Integer id);
}
