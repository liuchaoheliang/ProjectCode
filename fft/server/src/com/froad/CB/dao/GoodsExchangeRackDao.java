package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.GoodsExchangeRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-31  
 * @version 1.0
 */

	/**
	 * 类描述：
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2014-3-14 下午05:07:40 
	 */
public interface GoodsExchangeRackDao {
	/**
	  * 方法描述：添加商品兑换架
	  * @param: GoodsExchangeRack
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer insert(GoodsExchangeRack goodsExchangeRack);
	
	/**
	  * 方法描述：按主键更新商品兑换架信息
	  * @param: GoodsExchangeRack
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(GoodsExchangeRack goodsExchangeRack);
	
	/**
	  * 方法描述：查询商品兑换架
	  * @param: id
	  * @return: GoodsExchangeRack
	  * @version: 1.0
	  */
	GoodsExchangeRack selectByPrimaryKey(Integer id);
	
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
	  * @return: List<GoodsExchangeRack>
	  * @version: 1.0
	  */
	List<GoodsExchangeRack> getGoodsExchangeRackByGoodsId(String goodsId);
	
	/**
	  * 方法描述：查询商品兑换架
	  * @param: GoodsExchangeRack
	  * @return: List<GoodsExchangeRack>
	  * @version: 1.0
	  */
	List<GoodsExchangeRack> getGoodsExchangeRack(GoodsExchangeRack goodsExchangeRack);
	
	/**
	 * 分页查询商品兑换架信息
	 * @param GoodsExchangeRack
	 * @return GoodsExchangeRack
	 */
	public GoodsExchangeRack getGoodsExchangeRackListByPager(GoodsExchangeRack goodsExchangeRack);


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
	
	
	
	/**
	  * 方法描述：获取首页可以展示的商品列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-14 下午05:07:45
	  */
	public List<GoodsExchangeRack> getIndexGoodsRack();
}
