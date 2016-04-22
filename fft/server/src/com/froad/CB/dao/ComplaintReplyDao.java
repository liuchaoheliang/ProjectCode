package com.froad.CB.dao;

import com.froad.CB.po.ComplaintReply;

public interface ComplaintReplyDao {

	
	/**
	  * 方法描述：添加投诉的回复
	  * @param: ComplaintReply
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 14, 2013 4:21:09 PM
	  */
	public Integer addComplaintReply(ComplaintReply reply);
	
	
	/**
	  * 方法描述：修改回复的状态
	  * @param: id,state
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 14, 2013 4:24:07 PM
	  */
	public void updateStateById(Integer id,String state);
}
