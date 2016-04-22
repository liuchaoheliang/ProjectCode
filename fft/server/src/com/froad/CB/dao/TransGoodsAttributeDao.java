package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.TransGoodsAttribute;

public interface TransGoodsAttributeDao {

	
	/**
	  * 方法描述：添加交易商品属性
	  * @param: TransGoodsAttribute
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 7:50:55 PM
	  */
	public void add(TransGoodsAttribute attr);
	
	
	/**
	  * 方法描述：批量添加交易商品属性
	  * @param: List<TransGoodsAttribute>
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 27, 2013 6:49:19 PM
	  */
	public void batchInsert(final List<TransGoodsAttribute> attrList);
	
	
	/**
	  * 方法描述：查询交易商品属性
	  * @param: transId
	  * @return: List<TransGoodsAttribute>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 7:50:59 PM
	  */
	public List<TransGoodsAttribute> getByTransId(String transId);
}
