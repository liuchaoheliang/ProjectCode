package com.froad.CB.dao.tag;

import java.util.List;

import com.froad.CB.po.tag.TagDistrictA;

public interface TagDistrictADAO {

	void insert(TagDistrictA record);

	int updateByPrimaryKeySelective(TagDistrictA record);

	TagDistrictA selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	List<TagDistrictA> selectTagDistrictA(TagDistrictA queryCon);

	List<TagDistrictA> getMerchantDistrictA(String merchantId);
	
	/**
	 * 查询所有分类一级商圈
	 * @return
	 */
	List<TagDistrictA> getAllTagDistrictA();
	
	/**
	 * 查询所有启用分类一级商圈
	 * @return
	 */
	List<TagDistrictA> getAllTagDistrictAByEnabled();
}