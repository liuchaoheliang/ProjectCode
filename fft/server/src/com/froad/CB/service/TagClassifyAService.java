package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.tag.TagClassifyA;

@WebService
public interface TagClassifyAService {
	
	/**
	 * 查询所有分类一级标签
	 * @return
	 */
	public List<TagClassifyA> getAllTagClassifyA();
	
	public List<TagClassifyA> getTagClassifyAByValue(TagClassifyA queryCon);
	
	/**
	 * 查询所有启用分类一级标签
	 * @return
	 */
	public List<TagClassifyA> getAllTagClassifyAByEnabled();
}
