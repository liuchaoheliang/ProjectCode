package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.SellerRule;

public interface SellerRuleDao {

	
	/**
	  * 方法描述：添加规则
	  * @param: SellerRule
	  * @return: id
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 3, 2013 12:26:31 AM
	  */
	public Integer addSellerRule(SellerRule sellerRule);
	
	
	/**
	  * 方法描述：多条件查询卖家规则
	  * @param: SellerRule
	  * @return: List<SellerRule>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 3, 2013 12:26:49 AM
	  */
	public List<SellerRule> getBySelective(SellerRule sellerRule);
	
	
	/**
	  * 方法描述：修改卖家规则
	  * @param: SellerRule
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 3, 2013 12:27:17 AM
	  */
	public void updateById(SellerRule sellerRule);
}
