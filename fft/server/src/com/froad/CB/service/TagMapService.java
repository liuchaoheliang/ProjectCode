package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.tag.TagMAP;

@WebService
public interface TagMapService {
	
	/**
	  * 方法描述：按商户编号查询tagMap
	  * @param: merchantId 商户编号
	  * @return: List<TagMAP>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 11:53:03 AM
	  */
	public List<TagMAP> getTagMapsByMerchantId(String merchantId);
	
	/**
	  * 方法描述：按商户名查询tagMap
	  * @param: merchantName 商户名
	  * @return: List<TagMAP>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 27, 2012 11:53:03 AM
	  */
	public List<TagMAP> getTagMapsByMerchantName(String merchantName);
	
	
	
	/**
	  * 方法描述：查询商户分类标签
	  * @param: merchantId
	  * @return: List<TagMAP>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 5, 2013 6:19:23 PM
	  */
	List<TagMAP> getTagsByMerchantId(String merchantId);
	
	
	/**
	  * 方法描述：添加TagMAP
	  * @param: TagMAP
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 25, 2013 8:21:16 PM
	  */
	public Integer add(TagMAP tagMap);
	
	public boolean updateById(TagMAP tagMap);
	
	public void insertBatch(List tagMAP);
}
