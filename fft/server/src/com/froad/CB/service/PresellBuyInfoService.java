package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.PresellBuyInfo;
@WebService
public interface PresellBuyInfoService {
	/**
	  * 方法描述：添加购买预售商品交易信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午01:50:45
	  */
	public Integer add(PresellBuyInfo presellBuyInfo);
	
	
	
	/**
	  * 方法描述：根据Id更新数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午02:07:23
	  */
	public Integer updateById(PresellBuyInfo presellBuyInfo);
	
	
	/**
	  * 方法描述：根据Id查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午02:07:26
	  */
	public PresellBuyInfo getById(Integer id);
	
	
	/**
	  * 方法描述：根据条件查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午02:07:29
	  */
	public List<PresellBuyInfo> getByConditions(PresellBuyInfo presellBuyInfo);
	
	
	
	/**
	  * 方法描述：查询分页
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-2-27 下午03:31:18
	  */
	public PresellBuyInfo getPresellDeliveryByPager(PresellBuyInfo presellBuyInfo);
	/**
	  * 方法描述：根据交易Id查询数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-24 下午06:24:09
	  */
	public List<PresellBuyInfo> getByTransId(List<String> transId);
}
