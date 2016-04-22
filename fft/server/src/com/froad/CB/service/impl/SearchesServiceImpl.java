package com.froad.CB.service.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.common.Pager;
import com.froad.CB.dao.searches.SearchesDao;
import com.froad.CB.po.searches.Searches;
import com.froad.CB.service.SearchesService;
import com.froad.util.DateUtil;

/** 
 * @author FQ 
 * @date 2012-12-5 下午02:57:22
 * @version 1.0
 * 
 */
@WebService(endpointInterface = "com.froad.CB.service.SearchesService")
public class SearchesServiceImpl implements SearchesService {
	
	private Logger logger=Logger.getLogger(SearchesServiceImpl.class);
	private SearchesDao searchesDao;

	@Override
	public Integer addSearches(Searches searches) {
		if(searches==null){
			logger.info("searches为空！");
			return -1;
		}
		String time=DateUtil.formatDate2Str(new Date());
		searches.setCreateTime(time);
		searches.setUpdateTime(time);
		return searchesDao.addSearches(searches);
	}

	@Override
	public Searches getSearchesById(String id) {
		if(id==null){
			logger.info("searches为空！");
			return null;
		}
		return searchesDao.getSearchesById(id);
	}

	@Override
	public Pager querySearchesByPager(Pager pager) {
		logger.info("分页查询 热门搜索");
		if(pager==null){
			logger.info("pager为空！");
			return pager;
		}
		return searchesDao.getSearchesByPager(pager);
	}
	
	@Override
	public List<Searches> getSearchesByHis(Searches searches) {
		return searchesDao.getSearchesByHis(searches);
	}
	
	@Override
	public int updSearchesByHis(Searches searches) {
		return searchesDao.updSearchesByHis(searches);
	}
	
	public SearchesDao getSearchesDao() {
		return searchesDao;
	}

	public void setSearchesDao(SearchesDao searchesDao) {
		this.searchesDao = searchesDao;
	}

}
