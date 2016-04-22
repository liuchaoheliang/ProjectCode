package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.dao.tag.TagClassifyBDAO;
import com.froad.CB.po.tag.TagClassifyB;
import com.froad.CB.service.TagClassifyBService;
import com.froad.util.cache.CacheKey;
import com.froad.util.cache.EhCacheUtil;
@WebService(endpointInterface="com.froad.CB.service.TagClassifyBService")
public class TagClassifyBServiceImpl implements TagClassifyBService {
	private Logger logger=Logger.getLogger(TagClassifyBServiceImpl.class);	
	private TagClassifyBDAO tagClassifyBDAO;
	
	@Override
	public List<TagClassifyB> getAllTagClassifyB() {
		EhCacheUtil ehCacheUtil=EhCacheUtil.getInstance();
		List<TagClassifyB> list=(List<TagClassifyB>) ehCacheUtil.get(EhCacheUtil.CACHE_NAME,CacheKey.TAGCLASSIFYB_CACHE_KEY);
		if(list!=null){
			return list;
		}
		
		logger.info("缓存 key=tagClassifyB 不存在数据,查找数据库！");
		list=tagClassifyBDAO.getAllTagClassifyB();
		ehCacheUtil.put(EhCacheUtil.CACHE_NAME,CacheKey.TAGCLASSIFYB_CACHE_KEY, list);
		
		return list;
	}
	
	@Override
	public List<TagClassifyB> getAllTagClassifyBByEnabled() {
		logger.info("查询所有启用 二级标签");
		return tagClassifyBDAO.getAllTagClassifyBByEnabled();
	}
	
	@Override
	public List<TagClassifyB> getTagClassifyBByValue(TagClassifyB queryCon) {
		return tagClassifyBDAO.selectTagClassifyB(queryCon);
	}
	
	@Override
	public TagClassifyB getTagClassifyBById(Integer id) {
		logger.info("ID 查询 二级分类");
		if(id==null){
			logger.info("ID为空！");
			return null;
		}
		return tagClassifyBDAO.selectByPrimaryKey(id);
	}
	
	
	
	
	public TagClassifyBDAO getTagClassifyBDAO() {
		return tagClassifyBDAO;
	}
	public void setTagClassifyBDAO(TagClassifyBDAO tagClassifyBDAO) {
		this.tagClassifyBDAO = tagClassifyBDAO;
	}

	
}
