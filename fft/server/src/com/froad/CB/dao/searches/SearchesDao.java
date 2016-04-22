package com.froad.CB.dao.searches;

import java.util.List;

import com.froad.CB.common.Pager;
import com.froad.CB.po.searches.Searches;

/** 
 * @author FQ 
 * @date 2012-12-5 下午02:32:48
 * @version 1.0
 * 
 */
public interface SearchesDao {
	
	/**
	 * 增加 热门搜索
	 * @param searches
	 * @return
	 */
	public Integer addSearches(Searches searches);
	
	/**
	 * 根据ID 查找
	 * @param id
	 * @return
	 */
	public Searches getSearchesById(String id);
	
	/**
	 * 查询当前条件的热门搜索历史记录
	 * @param searches
	 * @return
	 */
	public List<Searches> getSearchesByHis(Searches searches);
	
	/**
	 * 更新当前条件的热门搜索历史记录次数
	 * @param searches
	 * @return
	 */
	public Integer updSearchesByHis(Searches searches);
	
	/**
	 * 分页查找 热门搜索
	 * @param pager
	 * @return
	 */
	public Pager getSearchesByPager(Pager pager);
}
