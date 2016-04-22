package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.common.Pager;
import com.froad.CB.po.searches.Searches;

/** 
 * @author FQ 
 * @date 2012-12-5 下午02:54:07
 * @version 1.0
 * 热门搜索
 */
@WebService
public interface SearchesService {
	
	/**
	 * 增加 Searches
	 * @param searches
	 * @return
	 */
	public Integer addSearches(Searches searches);
	
	/**
	 * 根据ID 查找Searches
	 * @param id
	 * @return
	 */
	public Searches getSearchesById(String id);
	
	/**
	 * 分页查找
	 * @param pager
	 * @return
	 */
	public Pager querySearchesByPager(Pager pager);
	
	/**
	 * 查询当前搜索条件历史记录
	 * @param searches
	 * @return
	 */
	public List<Searches> getSearchesByHis(Searches searches);
	
	/**
	 * 更新当前搜索条件历史记录次数
	 * @param searches
	 * @return
	 */
	public int updSearchesByHis(Searches searches);
}
