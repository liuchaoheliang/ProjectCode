package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.ClientGoodsCarryRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 * 手机客户端      商品提现架DAO
 */
public interface ClientGoodsCarryRackDao {
	/**
	  * 方法描述：添加商品提现架
	  * @param: ClientGoodsCarryRack
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer insert(ClientGoodsCarryRack ClientGoodsCarryRack);
	
	/**
	  * 方法描述：按主键更新商品提现架信息
	  * @param: ClientGoodsCarryRack
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(ClientGoodsCarryRack ClientGoodsCarryRack);
	
	/**
	  * 方法描述：查询商品提现架
	  * @param: id
	  * @return: ClientGoodsCarryRack
	  * @version: 1.0
	  */
	ClientGoodsCarryRack selectByPrimaryKey(Integer id);
	
	/**
	  * 方法描述：按主键删除商品提现架
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteByPrimaryKey(String id);
	
	/**
	 * 逻辑删除 商品提现架
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateByPrimaryKey(String id);
	
	/**
	  * 方法描述：查询商品提现架
	  * @param: goodsId
	  * @return: List<ClientGoodsCarryRack>
	  * @version: 1.0
	  */
	List<ClientGoodsCarryRack> getClientGoodsCarryRackByGoodsId(String goodsId);
	
	/**
	  * 方法描述：查询商品提现架
	  * @param: ClientGoodsCarryRack
	  * @return: List<ClientGoodsCarryRack>
	  * @version: 1.0
	  */
	List<ClientGoodsCarryRack> getClientGoodsCarryRack(ClientGoodsCarryRack ClientGoodsCarryRack);
	
	/**
	 * 分页查询商品提现架信息
	 * @param ClientGoodsCarryRack
	 * @return ClientGoodsCarryRack
	 */
	public ClientGoodsCarryRack getClientGoodsCarryRackListByPager(ClientGoodsCarryRack ClientGoodsCarryRack);
}
