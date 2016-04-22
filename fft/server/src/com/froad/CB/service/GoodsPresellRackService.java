package com.froad.CB.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.froad.CB.dao.GoodsPresellRackDao;
import com.froad.CB.po.GoodsPresellRack;
@WebService
public interface GoodsPresellRackService {
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
	  * 方法描述：获取服务端当前时间
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-11 下午02:07:14
	  */
	public Date getServerNowTime();
	
	
	/**
	  * 方法描述：获取当前过期预售商品列表
	  * @param: 需要指定pager.size（设置条数）
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-12 上午10:06:04
	  */
	public List<GoodsPresellRack> getHistory(GoodsPresellRack goodsPresellRack);
}
