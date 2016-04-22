package com.froad.CB.dao.tag;

import java.util.List;

import com.froad.CB.po.tag.TagMAP;

public interface TagMAPDAO {

	Integer insert(TagMAP record);

	TagMAP selectByPrimaryKey(Integer id);

	void updateById(TagMAP tagMap);

	void deleteByPrimaryKey(Integer id);

	List<TagMAP> selectTagMAPs(TagMAP queryCon);

	/**
	 * 描述：查询商户的分类一标签
	 * 
	 * @param merchantId
	 * @return List<TagClassifyA>
	 */
	List<TagMAP> selectTagsByMerchantId(String merchantId);
	
	
	/**
	  * 方法描述：按商户编号查询tagMap
	  * @param: merchantId 商户编号
	  * @return: List<TagMAP>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 4:15:57 PM
	  */
	List<TagMAP> getTagMapsByMerchantId(String merchantId);
	
	
	
	/**
	  * 方法描述：按商户名查询tagMap
	  * @param: merchantName 商户名
	  * @return: List<TagMAP>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 4:16:43 PM
	  */
	List<TagMAP> getTagMapsByMerchantName(String merchantName);
	
	
	/**
	  * 方法描述：查询出前30个tagMap记录(按时间排序)
	  * @return: List<TagMAP>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 4:46:41 PM
	  */
	List<TagMAP> getTopTagMaps();
	
	public void insertBatch(final List tagMAP);
}