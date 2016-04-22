package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.GoodsExchangeRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-1  
 * @version 1.0
 */
@WebService
public interface GoodsExchangeRackService {
	
	
	/**
	  * 方法描述：添加商品兑换架
	  * @param: GoodsExchangeRack
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addGoodsExchangeRack(GoodsExchangeRack goodsExchangeRack);
	
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
	GoodsExchangeRack selectById(Integer id);
	
	/**
	  * 方法描述：按主键删除商品兑换架
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteById(String id);
	
	/**
	 * 逻辑删除 商品兑换架
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateById(String id);
	
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
	  * 方法描述：获取首页可以展示的商品列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-14 下午05:07:45
	  */
	public List<GoodsExchangeRack> getIndexGoodsRack();
	
	
	/**
	  * 方法描述：管理平台调用接口
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-21 下午06:32:27
	  */
	public GoodsExchangeRack getByPagerForMgmt(GoodsExchangeRack goodsExchangeRack);
}
