package com.froad.CB.dao.complaint;

import java.util.List;

import com.froad.CB.po.complaint.ComplaintCategory;

public interface ComplaintCategoryDao {
	/**
	  * 方法描述：添加投诉分类信息
	  * @param: category
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 5:48:56 PM
	  */
	public Integer addComplaintCategory(ComplaintCategory category);
	
	
	/**
	  * 方法描述：删除投诉分类记录
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 1:36:50 PM
	  */
	public boolean deleteComplaintCategoryById(Integer id);
	
	
	/**
	  * 方法描述：按编号更新投诉分类信息
	  * @param: category
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 1:37:27 PM
	  */
	public boolean updateComplaintCategoryById(ComplaintCategory category);
	
	
	/**
	  * 方法描述：更新投诉分类的状态
	  * @param: id,state
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 2:02:02 PM
	  */
	public boolean updateStateById(Integer id,String state);
	
	
	/**
	  * 方法描述：按编号查询投诉分类信息
	  * @param: id
	  * @return: ComplaintCategory
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 1:38:29 PM
	  */
	public ComplaintCategory getComplaintCategoryById(Integer id);
	
	
	/**
	  * 方法描述：查询所有投诉分类信息
	  * @return: List<ComplaintCategory>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 1:39:29 PM
	  */
	public List<ComplaintCategory> getComplaintCategoryList();

}
