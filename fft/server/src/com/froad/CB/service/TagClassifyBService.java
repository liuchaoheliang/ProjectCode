package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;
import com.froad.CB.po.tag.TagClassifyB;

@WebService
public interface TagClassifyBService {
	
	/**
	 * 查询所有启用 二级分类
	 * @return
	 */
	public List<TagClassifyB> getAllTagClassifyBByEnabled();
	
	
	/**
	 * 查询所有二级分类
	 * @return
	 */
	public List<TagClassifyB> getAllTagClassifyB();
	
	/**
	 * 根据ID 查找 二级分类
	 * @param id
	 * @return
	 */
	public TagClassifyB getTagClassifyBById(Integer id);
	
	public List<TagClassifyB> getTagClassifyBByValue(TagClassifyB queryCon);
}
