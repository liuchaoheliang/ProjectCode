package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.TransGoodsAttribute;

@WebService
public interface TransGoodsAttributeService {

	/**
	  * 方法描述：添加交易商品属性
	  * @param: TransGoodsAttribute
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 7:50:55 PM
	  */
	public boolean add(TransGoodsAttribute attr);
	
	
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
