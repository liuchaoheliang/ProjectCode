package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.tag.TagClassifyADAO;
import com.froad.CB.po.tag.TagClassifyA;
import com.froad.CB.service.TagClassifyAService;
import com.froad.util.cache.CacheKey;
import com.froad.util.cache.EhCacheUtil;
@WebService(endpointInterface="com.froad.CB.service.TagClassifyAService")
public class TagClassifyAServiceImpl implements TagClassifyAService {
	
	private Logger logger=Logger.getLogger(TagClassifyAServiceImpl.class);
	
	private TagClassifyADAO tagClassifyADAO;
	

	@Override
	public List<TagClassifyA> getAllTagClassifyA() {
		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance();
		List<TagClassifyA> list=(List<TagClassifyA>) ehCacheUtil.get(EhCacheUtil.CACHE_NAME,CacheKey.TAGCLASSIFYA_CACHE_KEY);
		if(list!=null){
			return list;
		}
		
		logger.info("缓存 key=tagClassifyA 不存在数据,查找数据库！");
		list=tagClassifyADAO.getAllTagClassifyA();
		ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.TAGCLASSIFYA_CACHE_KEY, list);
		
		return list;
	}
	
	@Override
	public List<TagClassifyA> getAllTagClassifyAByEnabled() {
		return tagClassifyADAO.getAllTagClassifyAByEnabled();
	}
	
	@Override
	public List<TagClassifyA> getTagClassifyAByValue(TagClassifyA queryCon) {
		return tagClassifyADAO.selectTagClassifyA(queryCon);
	}
	
	public TagClassifyADAO getTagClassifyADAO() {
		return tagClassifyADAO;
	}
	public void setTagClassifyADAO(TagClassifyADAO tagClassifyADAO) {
		this.tagClassifyADAO = tagClassifyADAO;
	}
}
