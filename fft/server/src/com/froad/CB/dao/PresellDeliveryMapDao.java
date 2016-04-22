package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.PresellDeliveryMap;

public interface PresellDeliveryMapDao {

	/**
	  * 方法描述：添加
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午01:50:45
	  */
	public Integer add(PresellDeliveryMap presellDeliveryMap);
	
	
	/**
	  * 方法描述：根据预售商品Id删除关系数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午05:23:32
	  */
	public Integer deleteByRackId(Integer RackId);
	
	
	/**
	  * 方法描述：根据Id更新数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午02:07:23
	  */
	public Integer updateById(PresellDeliveryMap presellDeliveryMap);
	
	/**
	  * 方法描述：根据Id查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午02:07:26
	  */
	public PresellDeliveryMap getById(Integer id);
	
	
	/**
	  * 方法描述：根据条件查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午02:07:29
	  */
	public List<PresellDeliveryMap> getByConditions(PresellDeliveryMap presellDeliveryMap);
	
	/**
	  * 方法描述：根据条件查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午02:07:29
	  */
	public PresellDeliveryMap getBypager(PresellDeliveryMap presellDeliveryMap);

	
}
