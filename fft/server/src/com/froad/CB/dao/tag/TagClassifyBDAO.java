package com.froad.CB.dao.tag;

import java.util.List;

import com.froad.CB.po.tag.TagClassifyB;

public interface TagClassifyBDAO {

	void insert(TagClassifyB record);

	int updateByPrimaryKeySelective(TagClassifyB record);

	TagClassifyB selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	List<TagClassifyB> selectTagClassifyB(TagClassifyB queryCon);

	List<TagClassifyB> getMerchantTagClassifyB(String merchantId);

	
	
	/**
	 * 查询所有启用 二级分类
	 * 
	 * @return
	 */
	public List<TagClassifyB> getAllTagClassifyB();
	
	
	/**
	 * 查询所有启用 二级分类
	 * 
	 * @return
	 */
	public List<TagClassifyB> getAllTagClassifyBByEnabled();
}