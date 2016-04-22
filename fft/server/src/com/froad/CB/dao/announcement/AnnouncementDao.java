package com.froad.CB.dao.announcement;

import java.util.List;

import com.froad.CB.po.announcement.Announcement;

public interface AnnouncementDao {
	
	
	/**
	  * 方法描述：添加公告
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-7 下午12:38:07
	  */
	public Integer add(Announcement announce);
	
	
	/**
	  * 方法描述：修改公告
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-7 下午12:40:13
	  */
	public Boolean updateById(Announcement announce);
	
	
	/**
	  * 方法描述：多条件分页查询公告信息
	  * @param: Announcement
	  * @return: Announcement
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 15, 2013 5:23:47 PM
	  */
	public Announcement getAnnouncementByPager(Announcement announce);
	
	/**
	 * 按主键编号查询公告信息
	 * @param id
	 * @return
	 */
	public Announcement getAnnouncementById(Integer id);
	
	/**
	 * 根据等级排序查询启用的公告
	 * @return
	 */
	public List<Announcement> getAnnouncementOrderByImportantList();
	
}
