/**
 * @Title: RegisterRedis.java
 * @Package com.froad.db.redis
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月5日
 * @version V1.0
**/

package com.froad.db.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ActiveAwardType;
import com.froad.enums.ActiveStatus;
import com.froad.enums.ActiveType;
import com.froad.handler.ActiveBaseRuleHandler;
import com.froad.handler.ActiveOrderHandler;
import com.froad.handler.RegistDetailRuleHandler;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.impl.ActiveBaseRuleHandlerImpl;
import com.froad.handler.impl.ActiveOrderHandlerImpl;
import com.froad.handler.impl.RegistDetailRuleHandlerImpl;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveOrder;
import com.froad.po.RegistDetailRule;
import com.froad.util.ActiveUtils;
import com.froad.util.DateUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.Validator;

 /**
 * @ClassName: RegisterRedis
 * @Description: TODO
 * @author froad-Joker 2015年12月5日
 * @modify froad-Joker 2015年12月5日
 */

public class RegisterRedis {
	private static final RedisManager redisManager = new RedisManager();
	
	public static int initRegisterActive(ActiveBaseRule activeBaseRule, RegistDetailRule registDetailRule) {
		
		try {
			String key = RedisKeyUtil.cbbank_full_regist_active_client_id_active_id(activeBaseRule.getClientId(), activeBaseRule.getActiveId());
			
			LogCvt.info("--initRegisterActive-" + key);
			
			// 全部重新设置
			Map<String, String> activeBaseRuleMap = ActiveUtils
					.PO2Map(activeBaseRule);
			Map<String, String> registDetailRuleMap = ActiveUtils
					.PO2Map(registDetailRule);
			registDetailRuleMap.putAll(activeBaseRuleMap);
			
			//校验是否有效
			Date startTime = new Date(System.currentTimeMillis());
			Date endTime = activeBaseRule.getExpireStartTime();
			long expire = Validator.checkActiveVilidTime(startTime, endTime);
			
			if(expire > 0) { 
				LogCvt.info("--initRegisterActive-活动未开始" + key);
				return 3;
			}
			
			long expireTime = -1;
			Date start = new Date(System.currentTimeMillis());
			Date end = activeBaseRule.getExpireEndTime();
			expireTime = Validator.checkActiveVilidTime(start, end);

			if (expireTime >= 0) {
				if("0".equals(registDetailRuleMap.get("triggerType")) && ActiveAwardType.unionIntegral.getCode().equals(registDetailRuleMap.get("awardType"))){
					registDetailRuleMap.put("resUnionIntegral", registDetailRuleMap.get("totalUnionIntegral"));
				}
				redisManager.putMap(key, registDetailRuleMap);
				redisManager.expire(key, (int) expireTime);
				LogCvt.debug("----设置活动成功----初始注册送活动细则-----" + expireTime);
				return 0;
			} else {
				LogCvt.debug("----活动失效----初始注册送活细则-----");
				return 1;
			}
						
		} catch(Exception e) {
			LogCvt.error("initRegisterActive error" + e.getMessage(), e);
			return 2;
		}finally {
			LogCvt.info("----end----初始化代金券活动细则-----");
		}
	}
	
	public static Map<String,String> readRegisterActive(String clientId,String activeId){
		Map<String,String> tempMap=new HashMap<String,String>();
		String redis_key=RedisKeyUtil.cbbank_full_regist_active_client_id_active_id(clientId, activeId);
		LogCvt.info("读取注册规则 readRegisterActive--"+redis_key);
		try{
			if(redisManager.exists(redis_key)){
				LogCvt.debug("----------读取促销活动细则----Redis获取-------");
				tempMap=redisManager.getMap(redis_key);
				if(checkRuleVilad(clientId,activeId)){//活动生效
					tempMap.put("result","0");
					return tempMap;
				}else{//活动还没启动
					tempMap.put("result","18016");
					return tempMap;
				}
			}else{
				LogCvt.debug("----------读取促销活动细则----DB获取--写入Redis-------");
				ActiveBaseRuleHandler activeBaseRuleHandler= new ActiveBaseRuleHandlerImpl();
				RegistDetailRuleHandler registDetailRuleHandler=new RegistDetailRuleHandlerImpl();
				
				ActiveBaseRule activeBaseRule=new ActiveBaseRule();
				activeBaseRule.setClientId(clientId);
				activeBaseRule.setActiveId(activeId);
				activeBaseRule.setStatus(ActiveStatus.launch.getCode());//启用状态
				activeBaseRule=activeBaseRuleHandler.findOneActiveBaseRule(activeBaseRule);
				

				RegistDetailRule registDetailRule=new RegistDetailRule();
				registDetailRule.setClientId(clientId);
				registDetailRule.setActiveId(activeId);
				//activeDetailRule.setIsPrePay(true);
				registDetailRule=registDetailRuleHandler.findOneRegistDetailRule(registDetailRule);
				if(activeBaseRule==null||registDetailRule==null){
					tempMap.put("result", "18002");
					return tempMap;
				}
				
				int result=initRegisterActive(activeBaseRule,registDetailRule);
				if(result==0){
					Map<String, String> activeBaseRuleMap= ActiveUtils.PO2Map(activeBaseRule);
					Map<String, String> activeDetailRuleMap= ActiveUtils.PO2Map(registDetailRule);
					activeDetailRuleMap.putAll(activeBaseRuleMap);
					if("0".equals(activeDetailRuleMap.get("triggerType")) && ActiveAwardType.unionIntegral.getCode().equals(activeDetailRuleMap.get("awardType"))){
						activeDetailRuleMap.put("resUnionIntegral", activeDetailRuleMap.get("totalUnionIntegral"));
					}
					//activeDetailRuleMap.put("resUnionIntegral", activeDetailRuleMap.get("totalUnionIntegral"));
					activeDetailRuleMap.put("result", "0");
					LogCvt.debug("------"+activeDetailRuleMap.toString());
					return activeDetailRuleMap;
				}else if(result==1) {
					tempMap.put("result", "18001");
					return tempMap;
				}else if(result==3){//活动还没生效
					tempMap.put("result", "18003");
					return tempMap;
				}else{
					tempMap.put("result", "18002");
					return tempMap;
				}
			}
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(),ex);
			tempMap.put("result", "18003");
			return tempMap;
		}
	}
	
	/**
	 * @param tempMap
	 * @param clientId
	 * @param activeId
	 * @return
	 */
	public static boolean   checkRuleVilad(String clientId,String  activeId){
		String redis_key=RedisKeyUtil.cbbank_full_regist_active_client_id_active_id(clientId, activeId);
		Map<String, String> tempMap=null;
		if (!redisManager.exists(redis_key)){
			tempMap=readRegisterActive( clientId,activeId);
			if(Integer.parseInt(tempMap.get("result"))!=0){
				return false;
			}
		}
		
	   tempMap	=redisManager.getMap(redis_key);
	   
		String expireStartTime=tempMap.get("expireStartTime");
		Date start=new Date(System.currentTimeMillis()); 
		Date end=DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireStartTime); 
		long expireTime=Validator.checkActiveVilidTime(start,end);
		
		if(expireTime<=0){//活动生效
			return true;
		}else{//活动还没启动
//			String keyGlobal=RedisKeyUtil.cbbank_active_global_limit_active_id(activeId);
//			String keyBase=RedisKeyUtil.cbbank_active_base_global_limit_active_id(activeId);
//			redis.del(redis_key); //清除细则Redis
//			redis.del(keyGlobal); //清除全局Redis
//			redis.del(keyBase);   //清楚全局Reids
			return false;
		}
	}
	
	public static int cutIntegralLimit(String clientId,String activeId){
		try{
			String redis_key=RedisKeyUtil.cbbank_full_regist_active_client_id_active_id(clientId, activeId);
			LogCvt.info("--cutIntegralLimit--"+redis_key);
			if(!redisManager.exists(redis_key)){
				LogCvt.debug("--cutIntegralLimit--"+18016);
				return 18016;
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return 18016;
			}
			
		   Map<String,String> tmap = redisManager.getMap(redis_key);
		   
		   String totalUnionIntegral = tmap.get("totalUnionIntegral");
		   String resUnionIntegral = tmap.get("resUnionIntegral");
		   String perUnionIntegral = tmap.get("perUnionIntegral");
		   
//		   if(Integer.parseInt(resUnionIntegral)==0){//无全局资格
//			   return 18013;
//		   }
		   
//		   if(Integer.parseInt(totalUnionIntegral)==0){//无全局资格
//			   return 18013;
//		   }
		   
		   int checkMoney=Integer.parseInt(resUnionIntegral)-Integer.parseInt(perUnionIntegral);
		   if(Integer.parseInt(totalUnionIntegral)!=0) {//总联盟积分有限
			   
			   if(checkMoney>=0){
				   LogCvt.debug("--扣减全局积分：：：-"+tmap+"-resUnionIntegral-"+(Integer.parseInt(perUnionIntegral)));
			
				   redisManager.hset(redis_key, "resUnionIntegral", Integer.toString(checkMoney));
				   return 0;
			   }else{//无资格
				   return 18013;
			   }
		   } else {//无联盟积分上限
			   redisManager.hset(redis_key, "resUnionIntegral", Integer.toString(checkMoney));
			   return 0;
		   }
		  
		   
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18014;
		}
	}
	
	public static boolean checkGlobalLimit(String clientId,String activeId,String orderId){
		try{
			String key=RedisKeyUtil.cbbank_register_global_limit_active_id(activeId);
			LogCvt.info("--checkGlobalLimit--"+key);
			if(!redisManager.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result=initGlobalLimit( clientId, activeId);
				if(result!=0){
					return false;
				}else{
					return true;
				}
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return false;
			}
			
		   Map<String,String> tmap = redisManager.getMap(key);
		   
		   LogCvt.debug("--checkGlobalLimit--"+tmap);
		   
		   String totalDay = tmap.get("totalDay");
		   String totalCount = tmap.get("totalCount");
		   String resCount = tmap.get("resCount");
		   
		   if(totalDay==null||"0".equals(totalDay)){
			   LogCvt.debug("--时/日校验全局变量：：：-不限定资格");
			   return true;
		   }
		   
		   if(totalCount==null||"0".equals(totalCount)){
			   LogCvt.debug("--时/日校验全局变量：：：-不限定资格");
			   return true;
		   }
		   
		   if(Integer.parseInt(resCount)>0 && Integer.parseInt(resCount)<=Integer.parseInt(totalCount)){
			   LogCvt.debug("---当天/小时校验全局资格--资格有效--");
			   return true; 
		   }else{
			 return false;  
		   }
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return false;
		}
	}
	
	public static int initGlobalLimit(String clientId,String activeId){
		String key=RedisKeyUtil.cbbank_register_global_limit_active_id(activeId);
		LogCvt.info("--initGlobalLimit--"+key);
		try{
			if(!redisManager.exists(key)){
				
				Map<String,String> cut_detail_map=readRegisterActive(clientId,activeId);
				
				int rt=Integer.parseInt(cut_detail_map.get("result"));
				if(rt==0){
					
					Map<String,String> everyDay=new HashMap<String,String>();
					everyDay.put("isTotalDay", cut_detail_map.get("isTotalDay"));//0-小时  1-天
					everyDay.put("totalDay", cut_detail_map.get("totalDay")); //限定次数 =0 不限制次数
					everyDay.put("totalCount",cut_detail_map.get("totalCount"));//限定总数=0
					everyDay.put("resCount", cut_detail_map.get("totalCount"));//初始化资格
					
					if(cut_detail_map.get("totalDay")==null||"0".equals(cut_detail_map.get("totalDay"))){
						Date start=new Date(System.currentTimeMillis());
						Date end=DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, cut_detail_map.get("expireEndTime"));
						
						long expireTime=Validator.checkActiveVilidTime(start,end);
						LogCvt.debug("--初始化当日全局变量：：：-不限制资格");
						redisManager.putMap(key, everyDay);
						redisManager.expire(key, (int)expireTime);
						return 0;
					}
					
					if(cut_detail_map.get("totalCount")==null||"0".equals(cut_detail_map.get("totalCount"))){
						Date start=new Date(System.currentTimeMillis());
						Date end=DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, cut_detail_map.get("expireEndTime"));
						long expireTime=Validator.checkActiveVilidTime(start,end);
						LogCvt.debug("--初始化当日全局变量：：：-不限制资格");
						redisManager.putMap(key, everyDay);
						redisManager.expire(key, (int)expireTime);
						return 0;
					}
					
					
					long expireTime=(long)RedisCommon.expireOpt(0,cut_detail_map.get("isTotalDay"),cut_detail_map.get("totalDay"));
					everyDay.put("startDate", DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(System.currentTimeMillis())));
					everyDay.put("endDate", DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(System.currentTimeMillis()+expireTime*1000)));
					
					if(expireTime<=0){
						LogCvt.debug("--初始化当日全局变量失败-"+everyDay);
						return 18001;
					}else{
						LogCvt.debug("--初始化当日全局变量：：：-"+everyDay+" time:"+expireTime);
						redisManager.putMap(key, everyDay);
						redisManager.expire(key, (int)expireTime);
						 LogCvt.debug("--initGlobalLimit--"+everyDay);
						return 0;
					}
				}else{
					return rt;
				}
			}
			return 0;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18008;
		}
	}
	
	public static int cutGlobalLimit(String clientId,String activeId){
		try{
			String key=RedisKeyUtil.cbbank_register_global_limit_active_id(activeId);
			LogCvt.info("--cutGlobalLimit--"+key);
			if(!redisManager.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result=initGlobalLimit( clientId, activeId);
				if(result!=0){
					return result;
				}
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return 18016;
			}
			
		   Map<String,String> tmap = redisManager.getMap(key);
		   
		   LogCvt.debug("--cutGlobalLimit--"+tmap);
		   
		   //String isTotalDay = tmap.get("isTotalDay");
		   String totalDay = tmap.get("totalDay");
		   String totalCount = tmap.get("totalCount");
		   String resCount = tmap.get("resCount");
		   
		   if(totalDay==null||"0".equals(totalDay)){
			   LogCvt.debug("--时/日扣减全局变量：：：-不限定资格");
			   return 0;
		   }
		   
		   if(totalCount==null||"0".equals(totalCount)){
			   LogCvt.debug("--时/日扣减全局变量：：：-不限定资格");
			   return 0;
		   }
		   
		   
		   if(Integer.parseInt(resCount)>0 && Integer.parseInt(resCount)<=Integer.parseInt(totalCount)){
			   LogCvt.debug("--时/日扣减全局变量：：：-"+tmap+"-resCount"+(Integer.parseInt(resCount)-1));
			   redisManager.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)-1));
			   return 0;
		   }else{//无资格
			   return 18009;
		   }
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18010;
		}
	}
	
	public static int rollbackGlobalLimit(String clientId,String activeId,String orderId,int type){
		try{
			String key=RedisKeyUtil.cbbank_register_global_limit_active_id(activeId);
			LogCvt.info("--rollbackGlobalLimit--"+key);
			if(!redisManager.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result=initGlobalLimit( clientId, activeId);
				if(result!=0){
					return result;
				}
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return 18016;
			}
			
		   Map<String,String> tmap = redisManager.getMap(key);
		   
		   LogCvt.debug("--rollbackGlobalLimit--"+tmap);
		   //String isTotalDay = tmap.get("isTotalDay");
		   String totalDay = tmap.get("totalDay");
		   String totalCount = tmap.get("totalCount");
		   String resCount = tmap.get("resCount");
		   String startDate =tmap.get("startDate");
		   String endDate =tmap.get("endDate");
		   
		   if(totalDay==null||"0".equals(totalDay)){
			   LogCvt.debug("--时/日回退全局变量：：：-不限定资格");
			   return 0;
		   }
		   
		   if(totalCount==null||"0".equals(totalCount)){
			   LogCvt.debug("--时/日回退全局变量：：：-不限定资格");
			   return 0;
		   }
		   
		   if(Integer.parseInt(resCount)==Integer.parseInt(totalCount)){
			   LogCvt.debug("---当天/小时全局资格已满，无需回退---");
			   return 0; 
		   }
		   
		   if(type==0){//订单平台，创建订单，无条件回滚
			   LogCvt.debug("---订单平台，创建订单，无条件回滚---");
			   LogCvt.debug("--时/日回滚全局变量：："+tmap+"-resCount"+(Integer.parseInt(resCount)+1));
			   redisManager.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)+1));
			   return 0;
		   }
		   
		   ActiveOrderHandler activeOrderHandle=new ActiveOrderHandlerImpl();
		   ActiveOrder activeOrder=new ActiveOrder();
		   activeOrder.setClientId(clientId);
		   activeOrder.setOrderId(orderId);
		   
		   activeOrder=activeOrderHandle.findOneActiveOrder(activeOrder);
		   if(activeOrder!=null){
			  long createTime= activeOrder.getCreateTime().getTime();
			  long start=DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, startDate).getTime();
			  long end=DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, endDate).getTime();
			   if(createTime>=start && createTime<=end){
				   LogCvt.debug("--时/日回滚全局变量：："+tmap+"-resCount:"+(Integer.parseInt(resCount)+1));
				   redisManager.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)+1));
			   }else{
				   LogCvt.debug("--订单失效，无需回滚当日全局资格--createTime:"+createTime+" start-"+start+" end-"+end); 
			   }
		   }else{
			   return 18011;  
		   }
		   return 0;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18011;
		}
	 
	}
	
	/**
	  * @Title: checkBaseGlobalLimit
	  * @Description: 全局资格检查
	  * @author: lf 2016年03月01日
	  * @param clientId
	  * @param activeId
	  * @return
	 */
	public static boolean checkBaseGlobalLimit(String clientId,String activeId){
		try{
			String key=RedisKeyUtil.cbbank_register_base_global_limit_active_id(activeId);
			LogCvt.info("--checkBaseGlobalLimit--"+key);
			if(!redisManager.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result=initBaseGlobalLimit( clientId, activeId);
				if(result!=0){
					return false;
				}else{
					return true;
				}
			}
			
			
			if(!checkRuleVilad(clientId,activeId)){
				  return false;
			}
			
		   Map<String,String> tmap = redisManager.getMap(key);
		   
		   LogCvt.debug("--checkBaseGlobalLimit--"+tmap);
		   
		   String totalMoney = tmap.get("totalMoney"); // 满减总金额
		   String resMoney = tmap.get("resMoney"); // 剩余总额
		   String maxCount = tmap.get("maxCount"); // 资格总数
		   String resCount = tmap.get("resCount"); // 剩余总数

		   if(Integer.parseInt(resCount)<=0||Integer.parseInt(resCount)>Integer.parseInt(maxCount)){
			   return false;
		   }
		   
		   if(Integer.parseInt(resMoney)<=0||Integer.parseInt(resMoney)>Integer.parseInt(totalMoney)){
			   return false;
		   }
		   
		   return true;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return false;
		}
	}
	
	/**
	  * @Title: initBaseGlobalLimit
	  * @Description: 初始化全局资格BaseGlobalLimit
	  * @author: lf 2016年03月01日
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败  9-当天资格用满  10-当天资格扣减失败  11-回退当天资格失败
	  *          12-初始化全局资格失败 13-活动还未生效
	 */
	public static int initBaseGlobalLimit(String clientId,String activeId){
		String key=RedisKeyUtil.cbbank_register_base_global_limit_active_id(activeId);
		LogCvt.info("--initBaseGlobalLimit--"+key);
		try{
			if(!redisManager.exists(key))
			{
				Map<String,String> detail_map=readRegisterActive(clientId,activeId);
				
				int rt=Integer.parseInt(detail_map.get("result"));
				if(rt==0){
					Map<String,String> publicPare=new HashMap<String,String>();
					String cutMoney = (detail_map.get("cutMoney") == null ? "0" : detail_map.get("cutMoney")).toString();
					String totalMoney = (detail_map.get("totalMoney") == null ? "0" : detail_map.get("totalMoney")).toString();
					publicPare.put("cutMoney", cutMoney);//满减金额
					publicPare.put("totalMoney", totalMoney); //满减总金额
					publicPare.put("resMoney", totalMoney);//剩余总额
					
					//分别判断满减，满赠送积分，满赠送红包
					int resCount = Integer.parseInt(totalMoney)/Integer.parseInt(cutMoney);
					
					publicPare.put("maxCount", Integer.toString(resCount));//资格总数
					publicPare.put("resCount", Integer.toString(resCount));//剩余总数

					String expireEndTime=detail_map.get("expireEndTime");
					int expireTime=(int)((DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireEndTime).getTime()-System.currentTimeMillis())/1000);

					if(expireTime<=0){
						LogCvt.debug("--初始化全局变量失败-"+publicPare);
						return 18001;
					}else{
						LogCvt.debug("--初始化全局变量：：：-"+publicPare+" time:"+expireTime);
						redisManager.putMap(key, publicPare);
						redisManager.expire(key, expireTime);
						  LogCvt.debug("--initBaseGlobalLimit--"+publicPare);
						return 0;
					}
				}else{
					return rt;
				}
			}
			return 0;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18012;
		}
	}
	
	/**
	 * 
	  * @Title: cutBaseGlobalLimit
	  * @Description: 扣减全局资格
	  * @author: lf 2016年3月2日
	  * @param clientId
	  * @param activeId
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败  9-当天资格用满  10-当天资格扣减失败  11-回退当天资格失败
	  *          12-初始化全局资格失败  13-无全局资格  14-扣减全局资格失败
	 */
	public static int cutBaseGlobalLimit(String clientId,String activeId){
		try{
			String key=RedisKeyUtil.cbbank_register_base_global_limit_active_id(activeId);
			LogCvt.info("--cutBaseGlobalLimit--"+key);
			if(!redisManager.exists(key)){
				int result=initBaseGlobalLimit( clientId, activeId);
				if(result!=0){
					return result;
				}
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return 18016;
			}
			
		   Map<String,String> tmap = redisManager.getMap(key);
		   
		   LogCvt.debug("--cutBaseGlobalLimit--"+tmap);
		   
		   String cutMoney = tmap.get("cutMoney"); // 满减金额
		   String totalMoney = tmap.get("totalMoney"); // 满减总额
		   String resMoney = tmap.get("resMoney"); // 剩余满减总额
		   String maxCount = tmap.get("maxCount"); // 总次数
		   String resCount = tmap.get("resCount"); // 剩余总次数
		   
		   if(Integer.parseInt(maxCount)==0){//无全局资格
			   return 18013;
		   }
		   
		   if( Integer.parseInt(totalMoney)==0){//无全局资格
			   return 18013;
		   }
		   
		   int checkMoney=Integer.parseInt(resMoney)-Integer.parseInt(cutMoney);
		   if(Integer.parseInt(resCount)>0&&checkMoney>=0){
			   LogCvt.debug("--扣减全局变量：：：-"+tmap+"-resCount"+(Integer.parseInt(resCount)-1));
				   
			   redisManager.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)-1));
			   redisManager.hset(key, "resMoney", Integer.toString(checkMoney));
			   return 0;
		   }else{//无资格
			   return 18013;
		   }
		  
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18014;
		}
	}
	
	/**
	 * 
	  * @Title: rollbackBaseGlobalLimit
	  * @Description: 回滚全局资格
	  * @author: lf 2016年3月2日
	  * @param clientId
	  * @param activeId
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败  9-当天资格用满  10-当天资格扣减失败  11-回退当天资格失败
	  *          12-初始化全局资格失败  13-无全局资格  14-扣减全局资格失败  15-资格回滚异常
	 */
	public static int rollbackBaseGlobalLimit(String clientId,String activeId){
		try{
			String key=RedisKeyUtil.cbbank_register_base_global_limit_active_id(activeId);
			LogCvt.info("--rollbackBaseGlobalLimit--"+key);
			if(!redisManager.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result=initBaseGlobalLimit( clientId, activeId);
				if(result!=0){
					return result;
				}
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return 18016;
			}
			
		   Map<String,String> tmap = redisManager.getMap(key);
		   LogCvt.debug("--rollbackBaseGlobalLimit--"+tmap);
		   
		   String cutMoney = tmap.get("cutMoney");
		   String totalMoney = tmap.get("totalMoney");
		   String resMoney = tmap.get("resMoney");
		   String maxCount = tmap.get("maxCount");
		   String resCount = tmap.get("resCount");
		   if(Integer.parseInt(resCount)>=Integer.parseInt(maxCount)){
			   return 18015;
		   }
		   
		   if(Integer.parseInt(resMoney)>=Integer.parseInt(totalMoney)){
				return 18015;
		   }
		   
		   LogCvt.debug("--回滚全局变量：："+tmap+"-resCount "+(Integer.parseInt(resCount)+1));
		   redisManager.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)+1));
		   redisManager.hset(key, "resMoney", Integer.toString(Integer.parseInt(resMoney)+Integer.parseInt(cutMoney)));
		  
		   return 0;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18015;
		}
	}
}
