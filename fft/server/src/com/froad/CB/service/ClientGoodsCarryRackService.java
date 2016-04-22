package com.froad.CB.service;

import java.util.List;
import javax.jws.WebService;
import com.froad.CB.po.ClientGoodsCarryRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 * 手机客户端    商品提现架service
 */
@WebService
public interface ClientGoodsCarryRackService {
	/**
	  * 方法描述：添加商品提现架
	  * @param: ClientGoodsCarryRack
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addClientGoodsCarryRack(ClientGoodsCarryRack ClientGoodsCarryRack);
	
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
	ClientGoodsCarryRack selectById(Integer id);
	
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
