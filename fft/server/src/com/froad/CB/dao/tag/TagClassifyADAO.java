package com.froad.CB.dao.tag;

import java.util.List;

import com.froad.CB.po.tag.TagClassifyA;

public interface TagClassifyADAO {

	void insert(TagClassifyA record);

	int updateByPrimaryKeySelective(TagClassifyA record);

	TagClassifyA selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	List<TagClassifyA> selectTagClassifyA(TagClassifyA queryCon);

	List<TagClassifyA> getMerchantTagClassifyA(String merchantId);
	
	/**
	 * 查询所有分类一级标签
	 * @return
	 */
	List<TagClassifyA> getAllTagClassifyA();
	
	/**
	 * 查询所有启用分类一级标签
	 * @return
	 */
	List<TagClassifyA> getAllTagClassifyAByEnabled();
}