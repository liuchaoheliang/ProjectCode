package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.TempPoint;

public interface TempPointDao {

	
	/**
	  * 方法描述：添加临时积分信息
	  * @param: TempPoint
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 7, 2013 9:57:58 AM
	  */
	public Integer add(TempPoint point);
	
	
	/**
	  * 方法描述：查询临时积分信息
	  * @param: id
	  * @return: TempPoint
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 7, 2013 9:59:21 AM
	  */
	public TempPoint getTempPointById(Integer id);
	
	
	/**
	  * 方法描述：修改临时积分信息
	  * @param: TempPoint
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 7, 2013 10:00:38 AM
	  */
	public boolean updateById(TempPoint point);
	
	
	/**
	  * 方法描述：删除临时积分信息
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 7, 2013 10:01:29 AM
	  */
	public boolean deleteById(Integer id);
	
	
	/**
	  * 方法描述：查询临时积分记录
	  * @param: TempPoint
	  * @return: List<TempPoint>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 7, 2013 1:54:08 PM
	  */
	public List<TempPoint> getTempPointBySelective(TempPoint point);
	
	
	/**
	  * 方法描述：分页查询临时积分记录
	  * @param: TempPoint
	  * @return: TempPoint
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 13, 2013 2:43:10 PM
	  */
	public TempPoint getTempPointByPager(TempPoint point);
}
