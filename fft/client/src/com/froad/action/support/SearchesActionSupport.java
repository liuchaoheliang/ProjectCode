package com.froad.action.support;

import java.util.List;

import org.apache.log4j.Logger;
import com.froad.client.searches.Pager;
import com.froad.client.searches.Searches;
import com.froad.client.searches.SearchesService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-20  
 * @version 1.0
 * 热门搜索 client service  ActionSupport
 */
public class SearchesActionSupport {
	private static Logger logger = Logger.getLogger(SearchesActionSupport.class);
	private SearchesService searchesService;
	
	/**
	 * 分页查找 
	 * @param pager
	 * @return
	 */
	public Pager querySearchesByPager(Pager pager){
		try{
			pager=searchesService.querySearchesByPager(pager);
		}
		catch(Exception e){
			logger.error("分页查找热门搜索异常", e);
		}
		return pager;
	}
	
	public Integer updSearchesByHis(Searches searches){
		Integer num = null;
		try {
			num = searchesService.updSearchesByHis(searches);
		} catch (Exception e) {
			logger.info("SearchesActionSupport.updSearchesByHis出错",e);
		}
		return num;
	}
	
	public Integer addSearches(Searches searches){
		Integer num=null;
		try {
			num = searchesService.addSearches(searches);
		} catch (Exception e) {
			logger.info("SearchesActionSupport.addSearches出错",e);
		}
		return num;
	}
	public List<Searches> getSearchesByHis(Searches searches){
		return searchesService.getSearchesByHis(searches);
	}
	public SearchesService getSearchesService() {
		return searchesService;
	}
	public void setSearchesService(SearchesService searchesService) {
		this.searchesService = searchesService;
	}
	
}
