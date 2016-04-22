package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.GoodsRackAttribute;

public interface ClientGoodsRackAttributeDao {

	
	/**
	  * 方法描述：添加上架商品属性信息
	  * @param: GoodsRackAttribute
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 4:20:09 PM
	  */
	public Integer addGoodsRackAttribute(GoodsRackAttribute goodsRackAttribute);
	
	
	/**
	  * 方法描述：查询上架商品属性
	  * @param: id
	  * @return: GoodsRackAttribute
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 4:20:45 PM
	  */
	public GoodsRackAttribute getGoodsRackAttributeById(Integer id);
	
	
	/**
	  * 方法描述：查询所有上架商品属性
	  * @return: List<GoodsRackAttribute>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 1, 2013 4:48:52 PM
	  */
	public List<GoodsRackAttribute> getGoodsRackAttributeList();
	
	
	/**
	  * 方法描述：修改上架商品属性记录中的状态
	  * @param: id,state
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 4:21:14 PM
	  */
	public boolean updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：修改
	  * @param: GoodsRackAttribute
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 4:22:17 PM
	  */
	public boolean updateById(GoodsRackAttribute goodsRackAttribute);
	
	
	/**
	  * 方法描述：删除上架商品属性记录
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 4:28:07 PM
	  */
	public boolean deleteById(Integer id);
}
