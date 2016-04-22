package com.froad.CB.dao;

import com.froad.CB.po.ClientGoodsRebateRack;

public interface ClientGoodsRebateRackDao {

	
	/**
	  * 方法描述：添加返利信息
	  * @param: GoodsRebateRack
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:02:52 PM
	  */
	public Integer addClientGoodsRebateRack(ClientGoodsRebateRack clientGoodsRebateRack);
	
	
	/**
	  * 方法描述：查询返利
	  * @param: id
	  * @return: ClientGoodsRebateRack
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:03:18 PM
	  */
	public ClientGoodsRebateRack getClientGoodsRebateRackById(Integer id);
	
	
	/**
	  * 方法描述：修改返利状态
	  * @param: id,state
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:03:32 PM
	  */
	public void updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：更新返利信息
	  * @param: ClientGoodsRebateRack
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:03:48 PM
	  */
	public void updateById(ClientGoodsRebateRack clientGoodsRebateRack);
	
	
	/**
	  * 方法描述：删除返利信息
	  * @param: id
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:04:03 PM
	  */
	public void deleteById(Integer id);
	
	
	/**
	  * 方法描述：分页多条件查询返利商品信息
	  * @param: ClientGoodsRebateRack
	  * @return: ClientGoodsRebateRack
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 22, 2013 3:30:06 PM
	  */
	public ClientGoodsRebateRack getGoodsRebateRackByPager(ClientGoodsRebateRack clientGoodsRebateRack);
}
