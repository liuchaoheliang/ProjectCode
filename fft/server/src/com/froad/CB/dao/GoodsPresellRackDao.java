package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.GoodsPresellRack;
import com.froad.CB.po.GoodsPresellRackImages;

public interface GoodsPresellRackDao {
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-3 下午05:01:13
	  */
	public Integer add(GoodsPresellRack goodsPresellRack  );
	
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-3 下午05:01:25
	  */
	public boolean updateById(GoodsPresellRack goodsPresellRack);
	
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-3 下午05:01:28
	  */
	public GoodsPresellRack getById(Integer id);
	
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-3 下午05:01:31
	  */
	public List<GoodsPresellRack> getByConditions(GoodsPresellRack goodsPresellRack);
	
	
	
	
	
	
	/**
	  * 方法描述：预售商品分页接口
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-3 下午05:13:14
	  */
	public GoodsPresellRack getByPager(GoodsPresellRack goodsPresellRack);
	
	
	
	/**
	  * 方法描述：获取当前已过期的预售商品列表
	  * @param: 需要指定pager.size
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-12 上午09:57:22
	  */
	public List<GoodsPresellRack> getHistory(GoodsPresellRack goodsPresellRack);
}
