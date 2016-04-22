package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;
import com.froad.CB.po.tag.TagDistrictA;

@WebService
public interface TagDistrictAService {
	
	/**
	 * 查询所有一级商圈
	 * @return
	 */
	public List<TagDistrictA> getAllTagDistrictA();
	
	/**
	 * 查询所有启用一级商圈
	 * @return
	 */
	public List<TagDistrictA> getAllTagDistrictAByEnabled();
	
	public List<TagDistrictA> getTagDistrictAByValue(TagDistrictA queryCon);
}
