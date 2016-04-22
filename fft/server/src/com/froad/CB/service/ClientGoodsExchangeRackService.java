package com.froad.CB.service;

import java.util.List;
import javax.jws.WebService;
import com.froad.CB.po.ClientGoodsExchangeRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 */
@WebService
public interface ClientGoodsExchangeRackService {
	/**
	  * 方法描述：添加商品兑换架
	  * @param: ClientGoodsExchangeRack
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addClientGoodsExchangeRack(ClientGoodsExchangeRack ClientGoodsExchangeRack);
	
	/**
	  * 方法描述：按主键更新商品兑换架信息
	  * @param: ClientGoodsExchangeRack
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(ClientGoodsExchangeRack ClientGoodsExchangeRack);
	
	/**
	  * 方法描述：查询商品兑换架
	  * @param: id
	  * @return: ClientGoodsExchangeRack
	  * @version: 1.0
	  */
	ClientGoodsExchangeRack selectById(Integer id);
	
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
	  * @param: ClientGoodsExchangeRack
	  * @return: List<ClientGoodsExchangeRack>
	  * @version: 1.0
	  */
	List<ClientGoodsExchangeRack> getClientGoodsExchangeRack(ClientGoodsExchangeRack ClientGoodsExchangeRack);
	
	/**
	 * 分页查询商品兑换架信息
	 * @param ClientGoodsExchangeRack
	 * @return ClientGoodsExchangeRack
	 */
	public ClientGoodsExchangeRack getClientGoodsExchangeRackListByPager(ClientGoodsExchangeRack ClientGoodsExchangeRack);
}
