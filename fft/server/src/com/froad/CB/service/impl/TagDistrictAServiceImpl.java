package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.tag.TagDistrictADAO;
import com.froad.CB.po.tag.TagClassifyB;
import com.froad.CB.po.tag.TagDistrictA;
import com.froad.CB.service.TagDistrictAService;
import com.froad.util.cache.CacheKey;
import com.froad.util.cache.EhCacheUtil;
@WebService(endpointInterface="com.froad.CB.service.TagDistrictAService")
public class TagDistrictAServiceImpl implements TagDistrictAService {
	private Logger logger=Logger.getLogger(TagDistrictAServiceImpl.class);
	
	private TagDistrictADAO tagDistrictADAO;
	
	@Override
	public List<TagDistrictA> getAllTagDistrictA() {
		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance();
		List<TagDistrictA> list=(List<TagDistrictA>) ehCacheUtil.get(EhCacheUtil.CACHE_NAME,CacheKey.TAGDISTRICTA_CACHE_KEY);
		if(list!=null){
			return list;
		}
		
		logger.info("缓存 key=tagDistrictA 不存在数据,查找数据库！");
		list=tagDistrictADAO.getAllTagDistrictA();
		ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.TAGDISTRICTA_CACHE_KEY, list);
		
		return list;
	}

	@Override
	public List<TagDistrictA> getAllTagDistrictAByEnabled() {
		return tagDistrictADAO.getAllTagDistrictAByEnabled();
	}
	
	@Override
	public List<TagDistrictA> getTagDistrictAByValue(TagDistrictA queryCon) {
		return tagDistrictADAO.selectTagDistrictA(queryCon);
	}
	
	public TagDistrictADAO getTagDistrictADAO() {
		return tagDistrictADAO;
	}
	public void setTagDistrictADAO(TagDistrictADAO tagDistrictADAO) {
		this.tagDistrictADAO = tagDistrictADAO;
	}
}
