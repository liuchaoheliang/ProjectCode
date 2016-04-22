package com.froad.CB.dao.searches.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.common.Pager;
import com.froad.CB.dao.searches.SearchesDao;
import com.froad.CB.po.searches.Searches;

/** 
 * @author FQ 
 * @date 2012-12-5 下午02:33:54
 * @version 1.0
 * 
 */
public class SearchesDaoImpl implements SearchesDao{
	private Logger logger=Logger.getLogger(SearchesDaoImpl.class);
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addSearches(Searches searches) {
		try {
			return (Integer)sqlMapClientTemplate.insert("searches.addSearches",searches);
		} catch (Exception e) {
			logger.error("添加热门搜索时，出现异常!",e);
		}
		return -1;
	}

	@Override
	public Searches getSearchesById(String id) {
		return (Searches)sqlMapClientTemplate.queryForObject("searches.getSearchesById",id);
	}
	
	@Override
	public List<Searches> getSearchesByHis(Searches searches) {
		return sqlMapClientTemplate.queryForList("searches.FIND-SAME-SEARCH-HISTORY",searches);
	}
	@Override
	public Integer updSearchesByHis(Searches searches) {
		return (Integer)sqlMapClientTemplate.update("searches.UPDATE-SEARCH-HISTORY",searches);
	}
	
	@Override
	public Pager getSearchesByPager(Pager pager) {
		Map pagerMap=new HashMap();
		if(pager.getProperty()!=null){
			pagerMap.put("property", pager.getProperty());
			pagerMap.put("keyword", pager.getKeyword());
		}
		pagerMap.put("orderBy", pager.getOrderBy());
		pagerMap.put("orderType", pager.getOrderType());
		pagerMap.put("startRow", pager.getStartRow());
		pagerMap.put("pageSize", pager.getPageSize());
		
		Map pagerCountMap=new HashMap();
		if(pager.getProperty()!=null){
			pagerMap.put("property", pager.getProperty());
			pagerMap.put("keyword", pager.getKeyword());
		}
		
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("searches.getSearchesListByPagerCount",pagerCountMap);
		List<Searches> list=sqlMapClientTemplate.queryForList("searches.getSearchesListByPager", pagerMap);
		if(list!=null && list.size()>0){
			list = fingTagValue(list);//查询标签值
		}
		pager.setTotalCount(totalCount);
		pager.setList(list);
		
		return pager;
	}
	public List fingTagValue(List<Searches> list){
		List listtemp = new ArrayList<Searches>();
		Searches searcheres = null;
		Map paramMap=new HashMap();
		for(Searches search:list){	
				if(search.getTagClassifyAId()!=null && !"".equals(search.getTagClassifyAId())){
					paramMap.put("tagClassifyAId", search.getTagClassifyAId());
					searcheres =(Searches) sqlMapClientTemplate.queryForObject("searches.getSearchesTagClassifyAValueByTagId",paramMap);	
					if(searcheres!=null){
						search.setTagClassifyAValue(searcheres.getTagClassifyAValue());
					}
				}
				if(search.getTagDistrictBId()!=null && !"".equals(search.getTagDistrictBId())){
					paramMap.put("tagDistrictBId", search.getTagDistrictBId());
					searcheres =(Searches) sqlMapClientTemplate.queryForObject("searches.getSearchesTagDistrictBValueByTagId",paramMap);	
					if(searcheres!=null){
						search.setTagDistrictBValue(searcheres.getTagDistrictBValue());
					}
				}
				listtemp.add(search);
		}
		return listtemp;
	}
}
