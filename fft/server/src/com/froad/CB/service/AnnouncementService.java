package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;
import com.froad.CB.po.announcement.Announcement;


@WebService
public interface AnnouncementService {
	
	
	
	
	/**
	  * 方法描述：添加公告
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-7 下午12:48:24
	  */
	public Integer insert(Announcement annoucement);
	
	
	
	
	/**
	  * 方法描述：根据Id更新数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014-3-7 下午12:49:36
	  */
	public boolean updateById(Announcement annoucement);
	
	/**
	 * 根据ID 查找Announcement
	 * @param id
	 * @return
	 */
	public Announcement getAnnouncementById(Integer id);
	
	
	/**
	  * 方法描述：分页多条件查询公告信息
	  * @param: Announcement
	  * @return: Announcement
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 15, 2013 5:52:44 PM
	  */
	public Announcement getAnnouncementByPager(Announcement annoucement);
	
	/**
	 * 根据等级排序查询启用的公告
	 * @return
	 */
	public List<Announcement> getAnnouncementOrderByImportantList();
}
