package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import com.froad.CB.dao.announcement.AnnouncementDao;
import com.froad.CB.po.announcement.Announcement;
import com.froad.CB.service.AnnouncementService;

@WebService(endpointInterface = "com.froad.CB.service.AnnouncementService")
public class AnnouncementServiceImpl implements AnnouncementService {
	private Logger logger=Logger.getLogger(AnnouncementServiceImpl.class);	
	private AnnouncementDao announcementDao;
	
	@Override
	public Announcement getAnnouncementById(Integer id) {
		if(id==null){
			logger.info("ID为空！");
			return null;
		}
		return announcementDao.getAnnouncementById(id);
	}

	@Override
	public Announcement getAnnouncementByPager(Announcement pager) {
		if(pager==null){
			logger.error("参数为空，分页查询失败！");
			return null;
		}
		return announcementDao.getAnnouncementByPager(pager);
	}
	
	@Override
	public List<Announcement> getAnnouncementOrderByImportantList() {
		return announcementDao.getAnnouncementOrderByImportantList();
	}

	public AnnouncementDao getAnnouncementDao() {
		return announcementDao;
	}

	public void setAnnouncementDao(AnnouncementDao announcementDao) {
		this.announcementDao = announcementDao;
	}

	@Override
	public Integer insert(Announcement annoucement) {
		if(annoucement!=null){
			return announcementDao.add(annoucement);
		}else{	
			logger.info("参数为空");
			return null;
		}
	}

	@Override
	public boolean updateById(Announcement annoucement) {
		if(annoucement==null || "".equals(annoucement.getId())){
			logger.info("传入参数有误");
			return false;
		}
		return announcementDao.updateById(annoucement);
	}
}
