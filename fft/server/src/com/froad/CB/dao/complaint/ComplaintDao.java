package com.froad.CB.dao.complaint;

import com.froad.CB.po.complaint.Complaint;

public interface ComplaintDao {

	/**
	  * 方法描述：添加投诉信息
	  * @param: complaint
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 5:13:31 PM
	  */
	public Integer addComplaint(Complaint complaint);
	
	
	/**
	  * 方法描述：删除投诉信息
	  * @param: id 
	  * @return: true--成功 false--失败
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 5:16:49 PM
	  */
	public boolean deleteComplaintById(Integer id);
	
	
	/**
	  * 方法描述：更新的投诉信息
	  * @param: complaint
	  * @return: true--成功 false--失败
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 5:43:13 PM
	  */
	public boolean updateComplaintById(Complaint complaint);
	
	
	/**
	  * 方法描述：更新状态
	  * @param: id 主键
	  * @param: state 状态
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 28, 2012 5:58:04 PM
	  */
	public boolean updateComplaintStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：查询投诉信息
	  * @param: id 主键编号
	  * @return: Complaint
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 5:44:00 PM
	  */
	public Complaint getComplaintById(Integer id);
	
	
	/**
	  * 方法描述：以分页的形式查询投诉信息
	  * @param: complaint
	  * @return: Complaint
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 5:45:31 PM
	  */
	public Complaint getComplaintByPager(Complaint complaint);
	
	
	/**
	  * 方法描述：停用投诉记录
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 10:22:30 AM
	  */
	public boolean stopComplaintById(Integer id);

}
