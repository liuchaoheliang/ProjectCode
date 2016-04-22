package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.GoodsExchangeRack;
import com.froad.CB.po.GoodsGroupRack;

@WebService
public interface GoodsGroupRackService {

	
	/**
	  * 方法描述：添加团购信息
	  * @param: GoodsGroupRack
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:02:52 PM
	  */
	public Integer addGoodsGroupRack(GoodsGroupRack goodsGroupRack);
	
	
	/**
	  * 方法描述：查询团购
	  * @param: id
	  * @return: GoodsGroupRack
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:03:18 PM
	  */
	public GoodsGroupRack getGoodsGroupRackById(Integer id);
	
	
	/**
	  * 方法描述：修改团购状态
	  * @param: id,state
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:03:32 PM
	  */
	public boolean updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：更新团购信息
	  * @param: GoodsGroupRack
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:03:48 PM
	  */
	public boolean updateById(GoodsGroupRack goodsGroupRack);
	
	
	/**
	  * 方法描述：删除团购信息
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 1, 2013 4:04:03 PM
	  */
	public boolean deleteById(Integer id);
	
	
	/**
	  * 方法描述：分页多条件查询团购信息
	  * @param: GoodsGroupRack
	  * @return: GoodsGroupRack
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 20, 2013 4:57:28 PM
	  */
	public GoodsGroupRack getGoodsGroupRackByPager(GoodsGroupRack goodsGroupRack);
	
	/**
	  * 方法描述：获取首页可以展示的商品列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-14 下午05:07:45
	  */
	public List<GoodsGroupRack> getIndexGoodsRack();
}
