package com.froad.CB.service;

import java.util.List;
import javax.jws.WebService;
import com.froad.CB.po.PointCashRule;

/** 
 * @author TXL 
 * @date 2013-2-25 am
 * @version 1.0
 * 
 */
@WebService
public interface PointCashRuleService {

	/**
	 * 
	 * SELECT ALL PointCashRule
	 */
	List<PointCashRule> getAllPointCashRule();

}
