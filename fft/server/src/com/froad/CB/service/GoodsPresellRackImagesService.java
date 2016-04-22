package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.dao.GoodsPresellRackImagesDao;
import com.froad.CB.po.GoodsPresellRackImages;
@WebService
public interface GoodsPresellRackImagesService {
	
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
	
	
	  /** 方法描述：更新预售商品图片
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-3 下午04:58:23
	  */
	public boolean updateByRackId(GoodsPresellRackImages goodsPresellRackImages);
}
