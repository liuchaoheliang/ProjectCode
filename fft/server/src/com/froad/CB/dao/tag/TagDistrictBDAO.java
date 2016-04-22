package com.froad.CB.dao.tag;

import java.util.List;

import com.froad.CB.common.Pager;
import com.froad.CB.po.tag.TagDistrictB;

public interface TagDistrictBDAO {

	void insert(TagDistrictB record);

	int updateByPrimaryKeySelective(TagDistrictB record);

	TagDistrictB selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	List<TagDistrictB> selectTagDistrictB(TagDistrictB queryCon);

	/**
	 * 描述：查询商户的商圈二标签
	 * @param merchantId
	 * @return List<TagDistrictB>
	 */
	List<TagDistrictB> getMerchantDistrictB(String merchantId);
	
	
	/**
	 * 查询所有 二级商圈
	 * @return List<TagDistrictB>
	 */
	public List<TagDistrictB> getAllTagDistrictB();
	

	/**
	 * 查询所有启用 二级商圈
	 * @return List<TagDistrictB>
	 */
	public List<TagDistrictB> getAllTagDistrictBByEnabled();

	/**
	 * 分页查询 二级商圈
	 * @param pager
	 * @return Pager
	 */
	public Pager getTagDistrictBByPager(Pager pager);

}