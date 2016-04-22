package com.froad.action.support;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.pointCashRule.PointCashRule;
import com.froad.client.pointCashRule.PointCashRuleService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 积分换现金  client service  ActionSupport
 */
public class PointCashRuleActionSupport {
	private static Logger logger = Logger.getLogger(PointCashRuleActionSupport.class);
	private PointCashRuleService pointCashRuleService;
	public PointCashRuleService getPointCashRuleService() {
		return pointCashRuleService;
	}
	public void setPointCashRuleService(PointCashRuleService pointCashRuleService) {
		this.pointCashRuleService = pointCashRuleService;
	}
	/**
	 * 
	  * 方法描述：查询积分换金额比例 
	  * @param: type 1:分分通积分:现金,2:珠海银行积分:现金
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-3-1 上午10:54:03
	 */
	public BigDecimal queryPointCashRule(String type){ 
		String[] rates=type.split(":");
		BigDecimal pointsRatio=new BigDecimal(rates[0]);//积分比例值
		BigDecimal cashRatio=new BigDecimal(rates[1]);//现金比例值
	//	BigDecimal PointToCashRatio =(cashRatio).divide(pointsRatio,2,RoundingMode.DOWN);
		return (cashRatio).divide(pointsRatio,2,RoundingMode.DOWN);
		/*
		List<PointCashRule> rule = pointCashRuleService.getAllPointCashRule();
		if(rule!=null){
			for(int i=0;i<rule.size();i++){
				if(type.equals(rule.get(i).getPointType())){
					String point = rule.get(i).getPointValue();
					String cash = rule.get(i).getCashValue();
					if(point==null||cash==null){
						return null;
					}
					 
					BigDecimal PointToCashRatio =(new BigDecimal(cash)).divide(new BigDecimal(point))  ;
					return PointToCashRatio;
				}
			}
		}*/
		 
	}
	/**
	 * 
	  * 方法描述：积分换算金额
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-3-1 上午11:09:57
	 */
	public String getCashByPoint(String point,String type){
		BigDecimal ratio = queryPointCashRule(type);
		if(ratio==null||ratio.equals("0")) return null;
		return (new BigDecimal(point)).multiply(ratio).toString();
	}
	
	public String getPointByCash(String cash,String type){
		BigDecimal ratio = queryPointCashRule(type);
		if(ratio==null||ratio.equals("0")) return null;
		return (new BigDecimal(cash)).divide(ratio).toString();
	}
	 
	
	
}
