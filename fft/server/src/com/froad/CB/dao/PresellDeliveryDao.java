package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.*;

public interface PresellDeliveryDao {
	/**
	  * 方法描述：添加提货点信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午01:50:45
	  */
	public Integer add(PresellDelivery presellDelivery);
	
	
	
	/**
	  * 方法描述：根据Id更新数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午02:07:23
	  */
	public Integer updateById(PresellDelivery presellDelivery);
	
	
	/**
	  * 方法描述：根据Id查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午02:07:26
	  */
	public PresellDelivery getById(Integer id);
	
	
	/**
	  * 方法描述：根据条件查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午02:07:29
	  */
	public List<PresellDelivery> getByConditions(PresellDelivery presellDelivery);
	
	
	
	/**
	  * 方法描述：提货点分页接口
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-28 下午04:14:27
	  */
	public PresellDelivery getByPager(PresellDelivery presellDelivery);


    /**
      	  * 方法描述：根据商品编号获取提货点列表
      	  * @param:
      	  * @return:
      	  * @version: 1.0
      	  * @author: 侯国权  houguoquan@f-road.com.cn
      	  * @time: 2014-3-9 下午02:07:29
      	  */
     public List<PresellDelivery> getByRackId(Integer rackId);
     
     
    /**
      * 方法描述：按交易编号查询提货点
      * @param: transId
      * @return: PresellDelivery
      * @version: 1.0
      * @author: 李金魁 lijinkui@f-road.com.cn
      * @time: Mar 18, 2014 10:13:58 AM
      */
    public PresellDelivery queryByTransId(Integer transId);



}
