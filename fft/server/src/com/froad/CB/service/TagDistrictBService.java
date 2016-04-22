package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.tag.TagDistrictA;
import com.froad.CB.po.tag.TagDistrictB;

@WebService
public interface TagDistrictBService {
	
	/**
	 * 查询所有 二级商圈
	 * @return
	 */
	public List<TagDistrictB> getAllTagDistrictB();
	
	
	/**
	 * 查询所有启用 二级商圈
	 * @return
	 */
	public List<TagDistrictB> getAllTagDistrictBByEnabled();

	
	/**
	 * 根据ID 查找二级商圈
	 * @param id
	 * @return
	 */
	public TagDistrictB getTagDistrictBById(Integer id);
	
	public List<TagDistrictB> getTagDistrictBByValue(TagDistrictB queryCon);
}
