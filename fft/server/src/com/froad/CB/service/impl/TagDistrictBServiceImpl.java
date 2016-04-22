package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.tag.TagDistrictBDAO;
import com.froad.CB.po.tag.TagDistrictA;
import com.froad.CB.po.tag.TagDistrictB;
import com.froad.CB.service.TagDistrictBService;
import com.froad.util.cache.CacheKey;
import com.froad.util.cache.EhCacheUtil;
@WebService(endpointInterface="com.froad.CB.service.TagDistrictBService")
public class TagDistrictBServiceImpl implements TagDistrictBService {
	private Logger logger=Logger.getLogger(TagDistrictBServiceImpl.class);	
	private TagDistrictBDAO tagDistrictBDAO;
	
	@Override
	public List<TagDistrictB> getAllTagDistrictB() {
		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance();
		List<TagDistrictB> list=(List<TagDistrictB>) ehCacheUtil.get(EhCacheUtil.CACHE_NAME,CacheKey.TAGDISTRICTB_CACHE_KEY);
		if(list!=null){
			return list;
		}
		
		logger.info("缓存 key=tagDistrictB 不存在数据,查找数据库！");
		list=tagDistrictBDAO.getAllTagDistrictB();
		ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.TAGDISTRICTB_CACHE_KEY, list);
		
		return list;
	}
	
	@Override
	public List<TagDistrictB> getAllTagDistrictBByEnabled() {
		logger.info("查询所有启用的二级商圈");
		return tagDistrictBDAO.getAllTagDistrictBByEnabled();
	}
	
	@Override
	public List<TagDistrictB> getTagDistrictBByValue(TagDistrictB queryCon) {
		return tagDistrictBDAO.selectTagDistrictB(queryCon);
	}
	
	@Override
	public TagDistrictB getTagDistrictBById(Integer id) {
		logger.info("ID 查询 二级商圈");
		if(id==null){
			logger.info("ID为空！");
			return null;
		}
		return tagDistrictBDAO.selectByPrimaryKey(id);
	}
	
	
	public TagDistrictBDAO getTagDistrictBDAO() {
		return tagDistrictBDAO;
	}
	public void setTagDistrictBDAO(TagDistrictBDAO tagDistrictBDAO) {
		this.tagDistrictBDAO = tagDistrictBDAO;
	}
}
