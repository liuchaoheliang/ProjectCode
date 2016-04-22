package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.GoodsPresellRackImages;

public interface GoodsPresellRackImagesDao {
	
	/**
	  * 方法描述：添加数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-3 下午04:58:21
	  */
	public Integer add(GoodsPresellRackImages goodsPresellRackImages );
	
	
	/**
	  * 方法描述：根据Id更新数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-3 下午04:58:23
	  */
	public boolean updateById(GoodsPresellRackImages goodsPresellRackImages);
	
	
	/**
	  * 方法描述：根据Id获取数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-3 下午04:58:26
	  */
	public GoodsPresellRackImages getById(Integer id);
	
	
	/**
	  * 方法描述：根据条件查找数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-3 下午04:58:29
	  */
	public List<GoodsPresellRackImages> getByConditions(GoodsPresellRackImages goodsPresellRackImages);
	
	
	
	/**
	  * 方法描述：修改商品图片
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-17 下午04:07:07
	  */
	public boolean updateObject(GoodsPresellRackImages goodsPresellRackImages);
}
