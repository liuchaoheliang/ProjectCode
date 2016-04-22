package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.Advert;
import com.froad.CB.po.PointCashRule;

/** 
 * @author TXL
 * @date 2013-2-25 pm
 * @version 1.0
 * 
 */
public interface PointCashRuleDao {

	/**
	 * 
	 * SELECT PointCashRule BY ID
	 */
	PointCashRule getByID(Integer id);
	
	/**
	 * 
	 * SELECT ALL PointCashRule
	 */
	List<PointCashRule> getAllPointCashRule();
	
	

}
