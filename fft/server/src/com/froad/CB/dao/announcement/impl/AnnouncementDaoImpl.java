package com.froad.CB.dao.announcement.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.common.Pager;
import com.froad.CB.dao.announcement.AnnouncementDao;
import com.froad.CB.po.announcement.Announcement;



public class AnnouncementDaoImpl implements AnnouncementDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	private static Logger logger = Logger.getLogger(AnnouncementDaoImpl.class);	
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Announcement getAnnouncementById(Integer id) {
		return (Announcement)sqlMapClientTemplate.queryForObject("announcement.getAnnouncementById",id);
	}

	@Override
	public Announcement getAnnouncementByPager(Announcement pager) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("announcement.getByPagerCount",pager);
		List<Announcement> list=sqlMapClientTemplate.queryForList("announcement.getByPager", pager);
		
		pager.setTotalCount(totalCount);
		pager.setList(list);
		return pager;
	}

	@Override
	public List<Announcement> getAnnouncementOrderByImportantList() {
		return sqlMapClientTemplate.queryForList("announcement.getAnnouncementOrderByImportant");
	}

	@Override
	public Integer add(Announcement announce) {
		return (Integer) sqlMapClientTemplate.insert("announcement.insert", announce);
	}

	@Override
	public Boolean updateById(Announcement announce) {
		int n=0;
		n=sqlMapClientTemplate.update("announcement.updateById", announce);
		if(n>0){
			return true;
		}
		return false;
	}
}
