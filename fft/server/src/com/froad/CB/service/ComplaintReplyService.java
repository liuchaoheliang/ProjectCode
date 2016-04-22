package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.po.ComplaintReply;

@WebService
public interface ComplaintReplyService {

	
	/**
	  * 方法描述：添加投诉的回复
	  * @param: ComplaintReply
	  * @return: ComplaintReply
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 14, 2013 4:21:09 PM
	  */
	public ComplaintReply addComplaintReply(ComplaintReply reply);
	
	
	/**
	  * 方法描述：修改回复的状态
	  * @param: id,state
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 14, 2013 4:24:07 PM
	  */
	public boolean updateStateById(Integer id,String state);
}
