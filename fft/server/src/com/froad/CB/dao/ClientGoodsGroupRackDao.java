package com.froad.CB.dao;

import com.froad.CB.po.ClientGoodsGroupRack;

public interface ClientGoodsGroupRackDao {

	
	/**
	  * 方法描述：添加团购信息
	  * @param: ClientGoodsGroupRack
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:02:52 PM
	  */
	public Integer addGoodsGroupRack(ClientGoodsGroupRack goodsGroupRack);
	
	
	/**
	  * 方法描述：查询团购
	  * @param: id
	  * @return: ClientGoodsGroupRack
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:03:18 PM
	  */
	public ClientGoodsGroupRack getGoodsGroupRackById(Integer id);
	
	
	/**
	  * 方法描述：修改团购状态
	  * @param: id,state
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:03:32 PM
	  */
	public void updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：更新团购信息
	  * @param: ClientGoodsGroupRack
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:03:48 PM
	  */
	public void updateById(ClientGoodsGroupRack goodsGroupRack);
	
	
	/**
	  * 方法描述：删除团购信息
	  * @param: id
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:04:03 PM
	  */
	public void deleteById(Integer id);
	
	
	/**
	  * 方法描述：分页多条件查询团购信息
	  * @param: GoodsGroupRack
	  * @return: GoodsGroupRack
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 20, 2013 4:57:28 PM
	  */
	public ClientGoodsGroupRack getClientGoodsGroupRackByPager(ClientGoodsGroupRack goodsGroupRack);

	
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
