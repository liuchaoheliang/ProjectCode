package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.TransDetails;

public interface TransDetailsDao {

	
	/**
	  * 方法描述：添加交易详细信息
	  * @param: TransDetails
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:38:19 PM
	  */
	public Integer addTransDetails(TransDetails transDetails);
	
	
	/**
	  * 方法描述：批量插入交易详情
	  * @param: List<TransDetails>
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 27, 2013 11:10:23 AM
	  */
	public void batchInsert(final List<TransDetails> detailsList);
	
	
	/**
	  * 方法描述：查询交易详情
	  * @param: id
	  * @return: TransDetails
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:38:51 PM
	  */
	public TransDetails getTransDetailsById(Integer id);
	
	
	/**
	  * 方法描述：修改交易详情的状态
	  * @param: id,state
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:39:17 PM
	  */
	public void updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：修改交易详情
	  * @param: TransDetails
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:39:38 PM
	  */
	public void updateById(TransDetails transDetails);
	
	
	/**
	  * 方法描述：删除交易详细信息
	  * @param: id
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 29, 2013 5:40:05 PM
	  */
	public void deleteById(Integer id);
}
