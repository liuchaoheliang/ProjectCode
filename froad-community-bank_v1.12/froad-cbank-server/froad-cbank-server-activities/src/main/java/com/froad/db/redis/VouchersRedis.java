/**
 * @Title: VouchersRedis.java
 * @Package com.froad.db.redis
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年11月27日
 * @version V1.0
 **/

package com.froad.db.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ActiveStatus;
import com.froad.handler.ActiveBaseRuleHandler;
import com.froad.handler.ActiveOrderHandler;
import com.froad.handler.ActiveTagRelationHandler;
import com.froad.handler.VouchersDetailRuleHandler;
import com.froad.handler.impl.ActiveBaseRuleHandlerImpl;
import com.froad.handler.impl.ActiveOrderHandlerImpl;
import com.froad.handler.impl.ActiveTagRelationHandlerImpl;
import com.froad.handler.impl.VouchersDetailRuleHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveOrder;
import com.froad.po.ActiveTagRelation;
import com.froad.po.VouchersDetailRule;
import com.froad.util.ActiveUtils;
import com.froad.util.DateUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.Validator;

/**
 * @ClassName: VouchersRedis
 * @Description: TODO
 * @author froad-Joker 2015年11月27日
 * @modify froad-Joker 2015年11月27日
 */

public class VouchersRedis {
	private static final RedisManager redisManager = new RedisManager();

	/**
	  * @Title: rollbackPersonLimit
	  * @Description: 回退个人资格
	  * @author: lf 2015年11月03日
	  * @modify: lf 2015年11月03日
	  * @param clientId
	  * @param activeId
	  * @param memberCode
	  * @return
	 */
	public static int rollbackPersonLimit(String clientId,String activeId,long memberCode, String orderId, int type){
		try{
			String key=RedisKeyUtil.cbbank_vouchers_person_limit_active_id_member_code(activeId, Long.toString(memberCode));
			LogCvt.info("--rollbackPersonLimit--"+key);
			if(redisManager.exists(key)){
				
				if(!checkRuleVilad(clientId,activeId)){
					  return 18016;
				}
				
				Map<String,String> tmap = redisManager.getMap(key);
				String perDay = tmap.get("perDay");
				String perCount = tmap.get("perCount");
				String resCount = tmap.get("resCount");
				String startDate = tmap.get("startDate");
				String endDate = tmap.get("endDate");
				LogCvt.debug("--rollbackPersonLimit--"+tmap);
				
				if(perDay==null||"0".equals(perDay)){//0天N次 不限资格
					redisManager.del(key);
					return 0;
				}
				   
				if(perCount==null||"0".equals(perCount)){//0天N次 不限资格
					redisManager.del(key);
					return 0;
				}
				
				if (type == 0) {// 订单平台，创建订单，无条件回滚
					LogCvt.debug("---订单平台，创建订单，无条件回滚---");
					LogCvt.debug("--回滚个人变量：：" + tmap + "-resCount" + (Integer.parseInt(resCount) + 1));
					redisManager.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount) + 1));
					return 0;
				}
				
				ActiveOrderHandler activeOrderHandle = new ActiveOrderHandlerImpl();
				ActiveOrder activeOrder = new ActiveOrder();
				activeOrder.setClientId(clientId);
				activeOrder.setOrderId(orderId);
				activeOrder = activeOrderHandle.findOneActiveOrder(activeOrder);
				if (activeOrder != null) {
					long createTime = activeOrder.getCreateTime().getTime();
					long start = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2,startDate).getTime();
					long end = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2,endDate).getTime();
					if (createTime >= start && createTime <= end) {
						if(Integer.parseInt(resCount)<Integer.parseInt(perCount)){
							LogCvt.debug("--个人变量回滚：：：-"+tmap+"-resCount "+(Integer.parseInt(resCount)+1));
							redisManager.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)+1)); 
							return 0;
						}else{
							return 18007;
						}
					} else {
						LogCvt.debug("--订单不在同时段，无需回滚个人资格--createTime:" + createTime + " start-" + start + " end-" + end);
					}
				} else {
					return 18011;
				}
			}
			return 0;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18007;
		}
	}
	
	/**
	  * @Title: checkPersonLimit
	  * @Description: 检查个人资格
	  * @author: lf 2015年11月30日
	  * @modify: lf 2015年11月30日
	  * @param clientId
	  * @param activeId
	  * @param memberCode
	  * @return
	 */
	public static boolean checkPersonLimit(String clientId,String activeId,long memberCode){
		try{
			String key=RedisKeyUtil.cbbank_vouchers_person_limit_active_id_member_code(activeId, Long.toString(memberCode));
			LogCvt.info("检查个人资格 key:"+key);
			if(redisManager.exists(key)){
			   Map<String,String> tmap = redisManager.getMap(key);
			   LogCvt.debug("个人资格 value:"+tmap);
			   String perDay = tmap.get("perDay"); // 单位天数
			   String perCount = tmap.get("perCount"); // 每人每时/天限购数量
			   String resCount = tmap.get("resCount"); // 每人每时/天限购剩余数量
			   
			   if(perDay==null||"0".equals(perDay)){//0时/天 不限资格
				   return true;
			   }
			   if(perCount==null||"0".equals(perCount)){//N时/天0次 不限资格
				   return true;
			   }

			   if(Integer.parseInt(resCount)<=Integer.parseInt(perCount)&&Integer.parseInt(resCount)>0){
					return true;
			   }else{
				   return false;
			   }
			}
			return true;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return false;
		}
	}
	
	/**
	  * @Title: cutPersonLimit
	  * @Description: 扣减个人资格
	  * @author: lf 2015年11月30日
	  * @modify: lf 2015年11月30日
	  * @return   0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	 */
	public static int cutPersonLimit(String clientId,String activeId,long memberCode){
		try{
			String key=RedisKeyUtil.cbbank_vouchers_person_limit_active_id_member_code(activeId, Long.toString(memberCode) );
			LogCvt.info("扣减个人资格 key:"+key);
			if(!redisManager.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result=initPersonLimit( clientId, activeId, memberCode);
				if(result!=0){
					return result;
				}
			}
			
		   Map<String,String> tmap = redisManager.getMap(key);
		   String perDay = tmap.get("perDay");
		   String perCount = tmap.get("perCount");
		   String resCount = tmap.get("resCount");
		   LogCvt.debug("个人资格 value:"+tmap);
		   if(perDay==null||"0".equals(perDay)){
			   redisManager.del(key);
			   return 0;
		   }
		   
		   if(perCount==null||"0".equals(perCount)){//N时/天0次 不限资格
			   return 0;
		   }
		   
		  if(Integer.parseInt(resCount)>0 && Integer.parseInt(resCount)<=Integer.parseInt(perCount)){
			   LogCvt.debug("个人资格扣:"+tmap+" - resCount "+(Integer.parseInt(resCount)-1));
			   redisManager.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)-1));
			   return 0;
		   }else{//无资格
			   return 18005;
		   }
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(),ex);
			return 18006;
		}
	}
	
	/**
	  * @Title: initPersonLimit
	  * @Description: 初始化个人购买资格
	  * @author: lf 2015年11月30日
	  * @modify: lf 2015年11月30日
	  * @param clientId
	  * @param activeId
	  * @param memberCode
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  4  初始化个人资格失败
	 */
	public static int initPersonLimit(String clientId,String activeId,long memberCode){
		String key=RedisKeyUtil.cbbank_vouchers_person_limit_active_id_member_code(activeId, Long.toString(memberCode));
		LogCvt.info("初始化个人资格 key:"+key);
		try{
			// 如果不存在,读取所属活动细则进行初始化
			if(!redisManager.exists(key)){
				Map<String,String> vouchers_detail_map=readVouchersActive(clientId,activeId);
				int rt=Integer.parseInt(vouchers_detail_map.get("result"));
				if(rt==0){ // 所属活动细则读取成功
					
					Map<String,String> person=new HashMap<String,String>();
					person.put("isPerDay", vouchers_detail_map.get("isPerDay"));//单位0-小时  1-天//
					
					person.put("perDay", vouchers_detail_map.get("perDay")); //单位次数
					
					person.put("perCount",vouchers_detail_map.get("perCount"));//限定总数
					
					person.put("resCount", vouchers_detail_map.get("perCount"));//初始化资格
				   
					if(vouchers_detail_map.get("totalDay")==null||"0".equals(vouchers_detail_map.get("totalDay"))){
						Date start=new Date(System.currentTimeMillis());
						Date end=DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, vouchers_detail_map.get("expireEndTime"));
						
						long expireTime=Validator.checkActiveVilidTime(start,end);
						LogCvt.debug("--初始化当日全局变量：：：-不限制资格");
						redisManager.putMap(key, person);
						redisManager.expire(key, (int)expireTime);
						return 0;
					}
					
					if(vouchers_detail_map.get("totalCount")==null||"0".equals(vouchers_detail_map.get("totalCount"))){
						Date start=new Date(System.currentTimeMillis());
						Date end=DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, vouchers_detail_map.get("expireEndTime"));
						long expireTime=Validator.checkActiveVilidTime(start,end);
						LogCvt.debug("--初始化当日全局变量：：：-不限制资格");
						redisManager.putMap(key, person);
						redisManager.expire(key, (int)expireTime);
						return 0;
					}
					
					int exprie=(int)RedisCommon.expireOpt(0,vouchers_detail_map.get("isPerDay"),vouchers_detail_map.get("perDay"));
					person.put("startDate", DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(System.currentTimeMillis())));
					person.put("endDate", DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(System.currentTimeMillis()+exprie*1000)));
					if( exprie <= 0 ){
						LogCvt.debug("--初始化个人资格变量失败-"+person);
						return 18001;
					}else{
						LogCvt.debug("初始化个人资格:"+person+" time:"+exprie);
						redisManager.putMap(key, person);
						redisManager.expire(key, exprie);
						return 0;
					}
				}else{ // 所属活动细则读取失败
					return rt;
				}
			}
			return 0;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18004;
		}
	}
	
	public static Boolean checkExistsByMemberCode(Long memberCode) {
		String key = RedisKeyUtil.cbbank_vouchers_check_failure(memberCode);
		if (redisManager.exists(key)) {
			return true;
		}
		return false;

	}

	public static int getFailureByMemberCode(Long memberCode) {

		String key = RedisKeyUtil.cbbank_vouchers_check_failure(memberCode);
		Integer count = 0;
		if (checkExistsByMemberCode(memberCode)) {
			count = Integer.parseInt(redisManager.getString(key));
			LogCvt.info("用户[" + memberCode + "] 存在,且已经输入[" + count + "]错误");
		}
		return count;
	}

	public static Boolean setFailCountByMemberCode(Long memberCode) {
		String key = RedisKeyUtil.cbbank_vouchers_check_failure(memberCode);
		if (checkExistsByMemberCode(memberCode)) {
			Integer count = Integer.parseInt(redisManager.getString(key)) + 1;

			redisManager.putString(key, count + "");

			if (count == 3) {
				redisManager.expire(key, 3 * 3600); // 设置当输入3次后锁定3小时
				LogCvt.info("用户[" + memberCode + "] 输入错误3次,锁定3小时");
			}
		} else {
			redisManager.putString(key, "1");
		}
		return true;
	}

	public static Boolean delFailCountByMemberCode(Long memberCode) {
		String key = RedisKeyUtil.cbbank_vouchers_check_failure(memberCode);
		if (checkExistsByMemberCode(memberCode)) {
			redisManager.del(key);
		}
		return true;
	}

	public static int initVouchersPersonLimit(String clientId, String activeId,
			long memberCode) {
		String key = RedisKeyUtil
				.cbbank_vouchers_person_limit_active_id_member_code(activeId,
						Long.toString(memberCode));
		LogCvt.info("--initVouchersPersonLimit--" + key);
		try {
			if (!redisManager.exists(key)) {
				Map<String, String> vouchers_detail_map = readVouchersActive(
						clientId, activeId);
				int rt = Integer.parseInt(vouchers_detail_map.get("result"));
				if (rt == 0) {
					Map<String, String> person = new HashMap<String, String>();
					person.put("isPerDay", vouchers_detail_map.get("isPerDay"));// 0-小时
																				// 1-天
					person.put("perDay", vouchers_detail_map.get("perDay")); // 限定次数
					person.put("perCount", vouchers_detail_map.get("perCount"));// 限定总数
					person.put("resCount", vouchers_detail_map.get("perCount"));// 初始化资格

					if (vouchers_detail_map.get("perDay") == null
							|| "0".equals(vouchers_detail_map.get("perDay"))) {
						LogCvt.debug("--初始化个人变量：：：-不限制资格");
						redisManager.putMap(key, person);
						return 0;
					}

					if (vouchers_detail_map.get("perCount") == null
							|| "0".equals(vouchers_detail_map.get("perCount"))) {
						LogCvt.debug("--初始化个人变量：：：-不限制资格");
						redisManager.putMap(key, person);
						return 0;
					}

					int exprie = (int) RedisCommon.expireOpt(0,
							vouchers_detail_map.get("isPerDay"),
							vouchers_detail_map.get("perDay"));
					if (exprie <= 0) {
						return 18001;
					} else {
						LogCvt.debug("--初始化个人变量：：：-" + person + " time:"
								+ exprie);
						redisManager.putMap(key, person);
						redisManager.expire(key, exprie);
						return 0;
					}
				} else {
					return rt;
				}
			}
			return 0;
		} catch (Exception ex) {
			LogCvt.error(ex.getMessage(), ex);
			return 18004;
		}
	}

	/**
	 * @Title: readFullCutActive
	 * @Description: 读取Redis 里面的代金券活动细则 默认从Redis里面读取，如读取不到从Mysql里面加载读取
	 * @author: zengfanting 2015年11月8日
	 * @modify: zengfanting 2015年11月8日
	 * @param clientId
	 * @param activeId
	 * @return 0-初始化成功 18001-活动失效 18002-初始化活动失败 18003-读取活动失败
	 */
	public static Map<String, String> readVouchersActive(String clientId,
			String activeId) {
		Map<String, String> tempMap = new HashMap<String, String>();
		String redis_key = RedisKeyUtil.cbbank_vouchers_client_id_active_id(
				clientId, activeId);
		LogCvt.info("--readVouchersActive--" + redis_key);
		try {
			if (redisManager.exists(redis_key)) {
				LogCvt.debug("----------读取代金券细则----Redis获取-------");
				tempMap = redisManager.getMap(redis_key);

				if (checkRuleVilad(clientId, activeId)) {// 活动生效
					tempMap.put("result", "0");
					return tempMap;
				} else {// 活动还没启动
					tempMap.put("result", "18016");
					return tempMap;
				}

			} else {
				LogCvt.debug("----------读取促销活动细则----DB获取--写入Redis-------");
				ActiveBaseRuleHandler activeBaseRuleHandler = new ActiveBaseRuleHandlerImpl();
				VouchersDetailRuleHandler vouchersDetailRuleHandler = new VouchersDetailRuleHandlerImpl();
				ActiveTagRelationHandler activeTagRelationHandler = new ActiveTagRelationHandlerImpl();

				ActiveBaseRule activeBaseRule = new ActiveBaseRule();
				activeBaseRule.setClientId(clientId);
				activeBaseRule.setActiveId(activeId);
				activeBaseRule.setStatus(ActiveStatus.launch.getCode());// 启用状态
				activeBaseRule = activeBaseRuleHandler
						.findOneActiveBaseRule(activeBaseRule);

				VouchersDetailRule vouchersDetailRule = new VouchersDetailRule();
				vouchersDetailRule.setClientId(clientId);
				vouchersDetailRule.setActiveId(activeId);
				// activeDetailRule.setIsPrePay(true);
				vouchersDetailRule = vouchersDetailRuleHandler
						.findOneVouchersDetailRule(vouchersDetailRule);

				ActiveTagRelation activeTagRelation = new ActiveTagRelation();
				activeTagRelation.setActiveId(activeId);
				activeTagRelation.setClientId(clientId);

				activeTagRelation = activeTagRelationHandler
						.findOneActiveTagRelation(activeTagRelation);

				if (activeBaseRule == null || vouchersDetailRule == null) {
					tempMap.put("result", "18002");
					return tempMap;
				}

				int result = initVouchersActive(activeBaseRule,
						vouchersDetailRule, activeTagRelation);
				if (result == 0) {
					Map<String, String> activeBaseRuleMap = ActiveUtils
							.PO2Map(activeBaseRule);
					Map<String, String> activeDetailRuleMap = ActiveUtils
							.PO2Map(vouchersDetailRule);
					Map<String, String> activeTagRelationMap = ActiveUtils
							.PO2Map(activeTagRelation);
					activeDetailRuleMap.putAll(activeBaseRuleMap);
					activeDetailRuleMap.putAll(activeTagRelationMap);
					activeDetailRuleMap.put("result", "0");
					LogCvt.debug("------" + activeDetailRuleMap.toString());
					return activeDetailRuleMap;
				} else if (result == 1) {
					tempMap.put("result", "18001");
					return tempMap;
				} else if (result == 3) {// 活动还没生效
					tempMap.put("result", "18003");
					return tempMap;
				} else {
					tempMap.put("result", "18002");
					return tempMap;
				}
			}
		} catch (Exception ex) {
			LogCvt.error(ex.getMessage(), ex);
			tempMap.put("result", "18003");
			return tempMap;
		}

	}

	public static boolean checkRuleVilad(String clientId, String activeId) {
		String redis_key = RedisKeyUtil.cbbank_vouchers_client_id_active_id(
				clientId, activeId);
		Map<String, String> tempMap = null;
		if (!redisManager.exists(redis_key)) {
			tempMap = readVouchersActive(clientId, activeId);
			if (Integer.parseInt(tempMap.get("result")) != 0) {
				return false;
			}
		}

		tempMap = redisManager.getMap(redis_key);

		String expireStartTime = tempMap.get("expireStartTime");
		Date start = new Date(System.currentTimeMillis());
		Date end = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireStartTime);
		long expireTime = Validator.checkActiveVilidTime(start, end);

		if (expireTime <= 0) {// 活动生效
			return true;
		} else {// 活动还没启动
			String keyGlobal = RedisKeyUtil
					.cbbank_vouchers_global_limit_active_id(activeId);
			String keyBase = RedisKeyUtil
					.cbbank_vouchers_base_global_limit_active_id(activeId);
			redisManager.del(redis_key); // 清除细则Redis
			redisManager.del(keyGlobal); // 清除全局Redis
			redisManager.del(keyBase); // 清楚全局Reids
			return false;
		}
	}

	public static int initVouchersActive(ActiveBaseRule activeBaseRule,
			VouchersDetailRule vouchersDetailRule,
			ActiveTagRelation activeTagRelation) {
		try {
			String redis_key = RedisKeyUtil
					.cbbank_vouchers_client_id_active_id(
							activeBaseRule.getClientId(),
							activeBaseRule.getActiveId());
			LogCvt.info("--initVouchersActive-" + redis_key);
			// 全部重新设置
			Map<String, String> activeBaseRuleMap = ActiveUtils
					.PO2Map(activeBaseRule);
			Map<String, String> vouchersDetailRuleMap = ActiveUtils
					.PO2Map(vouchersDetailRule);
			Map<String, String> activeTagRelationMap = ActiveUtils
					.PO2Map(activeTagRelation);
			vouchersDetailRuleMap.putAll(activeBaseRuleMap);
			vouchersDetailRuleMap.putAll(activeTagRelationMap);

			// 校验是否有效
			Date startTime = new Date(System.currentTimeMillis());
			Date endTime = activeBaseRule.getExpireStartTime();
			long expire = Validator.checkActiveVilidTime(startTime, endTime);

			if (expire > 0) {// 活动还未生效
				return 3;
			}

			long expireTime = -1;
			Date start = new Date(System.currentTimeMillis());
			Date end = activeBaseRule.getExpireEndTime();
			expireTime = Validator.checkActiveVilidTime(start, end);

			if (expireTime >= 0) {
				redisManager.putMap(redis_key, vouchersDetailRuleMap);
				redisManager.expire(redis_key, (int) expireTime);
				LogCvt.debug("----设置活动成功----初始化代金券活动细则-----" + expireTime);
				return 0;
			} else {
				// SupportsRedis.del_cbbank_active_merchant_info(activeBaseRule.getActiveId());
				// SupportsRedis.del_cbbank_active_product_info(activeBaseRule.getActiveId());
				LogCvt.debug("----活动失效----初始化代金券活动细则-----");
				return 1;
			}
		} catch (Exception ex) {
			LogCvt.error(ex.getMessage(), ex);
			return 2;
		} finally {
			LogCvt.info("----end----初始化代金券活动细则-----");
		}

	}


	public static int rollbackVouchersPersonLimit(String clientId,
			String activeId, long memberCode) {
		try {
			String key = RedisKeyUtil
					.cbbank_vouchers_person_limit_active_id_member_code(
							activeId, Long.toString(memberCode));
			LogCvt.info("--rollbackVouchersPersonLimit--" + key);
			if (redisManager.exists(key)) {

				if (!checkRuleVilad(clientId, activeId)) {
					return 18016;
				}

				Map<String, String> tmap = redisManager.getMap(key);
				String perDay = tmap.get("perDay");
				String perCount = tmap.get("perCount");
				String resCount = tmap.get("resCount");
				LogCvt.debug("--rollbackVouchersPersonLimit--" + tmap);
				if (perDay == null || "0".equals(perDay)) {// 0天N次 不限资格
					redisManager.del(key);
					return 0;
				}

				if (perCount == null || "0".equals(perCount)) {// 0天N次 不限资格
					redisManager.del(key);
					return 0;
				}

				if (Integer.parseInt(resCount) < Integer.parseInt(perCount)
						&& Integer.parseInt(resCount) > 0) {
					LogCvt.debug("--个人变量回滚：：：-" + tmap + "-resCount "
							+ (Integer.parseInt(resCount) + 1));
					redisManager.hset(key, "resCount",
							Integer.toString(Integer.parseInt(resCount) + 1));
					return 0;
				} else {
					return 18007;
				}
			}
			return 0;
		} catch (Exception ex) {
			LogCvt.error(ex.getMessage(), ex);
			return 18007;
		}
	}

	public static int rollbackVouchersGlobalLimit(String clientId,
			String activeId, String orderId, int type) {
		try {
			String key = RedisKeyUtil
					.cbbank_vouchers_global_limit_active_id(activeId);
			LogCvt.info("--rollbackVouchersGlobalLimit--" + key);
			if (!redisManager.exists(key)) {// 直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result = initVouchersGlobalLimit(clientId, activeId);
				if (result != 0) {
					return result;
				}
			}

			if (!checkRuleVilad(clientId, activeId)) {
				return 18016;
			}

			Map<String, String> tmap = redisManager.getMap(key);

			LogCvt.debug("--rollbackGlobalLimit--" + tmap);
			// String isTotalDay = tmap.get("isTotalDay");
			String totalDay = tmap.get("totalDay");
			String totalCount = tmap.get("totalCount");
			String resCount = tmap.get("resCount");
			String startDate = tmap.get("startDate");
			String endDate = tmap.get("endDate");

			if (totalDay == null || "0".equals(totalDay)) {
				LogCvt.debug("--时/日回退全局变量：：：-不限定资格");
				return 0;
			}

			if (totalCount == null || "0".equals(totalCount)) {
				LogCvt.debug("--时/日回退全局变量：：：-不限定资格");
				return 0;
			}

			if (Integer.parseInt(resCount) == Integer.parseInt(totalCount)) {
				LogCvt.debug("---当天/小时全局资格已满，无需回退---");
				return 0;
			}

			if (type == 0) {// 订单平台，创建订单，无条件回滚
				LogCvt.debug("---订单平台，创建订单，无条件回滚---");
				LogCvt.debug("--时/日回滚全局变量：：" + tmap + "-resCount"
						+ (Integer.parseInt(resCount) + 1));
				redisManager.hset(key, "resCount",
						Integer.toString(Integer.parseInt(resCount) + 1));
				return 0;
			}

			ActiveOrderHandler activeOrderHandle = new ActiveOrderHandlerImpl();
			ActiveOrder activeOrder = new ActiveOrder();
			activeOrder.setClientId(clientId);
			activeOrder.setOrderId(orderId);

			activeOrder = activeOrderHandle.findOneActiveOrder(activeOrder);
			if (activeOrder != null) {
				long createTime = activeOrder.getCreateTime().getTime();
				long start = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2,
						startDate).getTime();
				long end = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, endDate)
						.getTime();
				if (createTime >= start && createTime <= end) {
					LogCvt.debug("--时/日回滚全局变量：：" + tmap + "-resCount:"
							+ (Integer.parseInt(resCount) + 1));
					redisManager.hset(key, "resCount",
							Integer.toString(Integer.parseInt(resCount) + 1));
				} else {
					LogCvt.debug("--订单失效，无需回滚当日全局资格--createTime:" + createTime
							+ " start-" + start + " end-" + end);
				}
			} else {
				return 18011;
			}
			return 0;
		} catch (Exception ex) {
			LogCvt.error(ex.getMessage(), ex);
			return 18011;
		}
	}

	public static int initVouchersGlobalLimit(String clientId, String activeId) {
		String key = RedisKeyUtil
				.cbbank_vouchers_global_limit_active_id(activeId);
		LogCvt.info("--initVouchersGlobalLimit--" + key);
		try {
			if (!redisManager.exists(key)) {

				Map<String, String> cut_detail_map = readVouchersActive(
						clientId, activeId);

				int rt = Integer.parseInt(cut_detail_map.get("result"));
				if (rt == 0) {

					Map<String, String> everyDay = new HashMap<String, String>();
					everyDay.put("isTotalDay", cut_detail_map.get("isTotalDay"));// 0-小时
																					// 1-天
					everyDay.put("totalDay", cut_detail_map.get("totalDay")); // 限定次数
																				// =0
																				// 不限制次数
					everyDay.put("totalCount", cut_detail_map.get("totalCount"));// 限定总数=0
					everyDay.put("resCount", cut_detail_map.get("totalCount"));// 初始化资格

					if (cut_detail_map.get("totalDay") == null
							|| "0".equals(cut_detail_map.get("totalDay"))) {
						Date start = new Date(System.currentTimeMillis());
						Date end = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2,
								cut_detail_map.get("expireEndTime"));

						long expireTime = Validator.checkActiveVilidTime(start,
								end);
						LogCvt.debug("--初始化当日全局变量：：：-不限制资格");
						redisManager.putMap(key, everyDay);
						redisManager.expire(key, (int) expireTime);
						return 0;
					}

					if (cut_detail_map.get("totalCount") == null
							|| "0".equals(cut_detail_map.get("totalCount"))) {
						Date start = new Date(System.currentTimeMillis());
						Date end = DateUtil.parse(DateUtil.DATE_TIME_FORMAT2,
								cut_detail_map.get("expireEndTime"));
						long expireTime = Validator.checkActiveVilidTime(start,
								end);
						LogCvt.debug("--初始化当日全局变量：：：-不限制资格");
						redisManager.putMap(key, everyDay);
						redisManager.expire(key, (int) expireTime);
						return 0;
					}

					long expireTime = (long) RedisCommon.expireOpt(0,
							cut_detail_map.get("isTotalDay"),
							cut_detail_map.get("totalDay"));
					everyDay.put("startDate", DateUtil.formatDateTime(
							DateUtil.DATE_TIME_FORMAT2,
							new Date(System.currentTimeMillis())));
					everyDay.put("endDate", DateUtil.formatDateTime(
							DateUtil.DATE_TIME_FORMAT2,
							new Date(System.currentTimeMillis() + expireTime
									* 1000)));

					if (expireTime <= 0) {
						LogCvt.debug("--初始化当日全局变量失败-" + everyDay);
						return 18001;
					} else {
						LogCvt.debug("--初始化当日全局变量：：：-" + everyDay + " time:"
								+ expireTime);
						redisManager.putMap(key, everyDay);
						redisManager.expire(key, (int) expireTime);
						LogCvt.debug("--initGlobalLimit--" + everyDay);
						return 0;
					}
				} else {
					return rt;
				}
			}
			return 0;
		} catch (Exception ex) {
			LogCvt.error(ex.getMessage(), ex);
			return 18008;
		}
	}

	public static int rollbackVouchersBaseGlobalLimit(String clientId,
			String activeId) {
		try {
			String key = RedisKeyUtil
					.cbbank_vouchers_base_global_limit_active_id(activeId);
			LogCvt.info("--rollbackVouchersBaseGlobalLimit--" + key);
			if (!redisManager.exists(key)) {// 直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result = initVouchersBaseGlobalLimit(clientId, activeId);
				if (result != 0) {
					return result;
				}
			}

			if (!checkRuleVilad(clientId, activeId)) {
				return 18016;
			}

			Map<String, String> tmap = redisManager.getMap(key);
			LogCvt.debug("--rollbackVouchersBaseGlobalLimit--" + tmap);

			String retMoney = tmap.get("retMoney");
			String maxMoney = tmap.get("maxMoney");
			String resMoney = tmap.get("resMoney");
			String maxCount = tmap.get("maxCount");
			String resCount = tmap.get("resCount");
			if (Integer.parseInt(resCount) >= Integer.parseInt(maxCount)) {
				return 18015;
			}

			if (Integer.parseInt(resMoney) >= Integer.parseInt(maxMoney)) {
				return 18015;
			}

			LogCvt.debug("--回滚全局变量：：" + tmap + "-resCount "
					+ (Integer.parseInt(resCount) + 1));
			redisManager.hset(key, "resCount",
					Integer.toString(Integer.parseInt(resCount) + 1));
			redisManager.hset(
					key,
					"resMoney",
					Integer.toString(Integer.parseInt(resMoney)
							+ Integer.parseInt(retMoney)));

			return 0;
		} catch (Exception ex) {
			LogCvt.error(ex.getMessage(), ex);
			return 18015;
		}
	}

	public static int initVouchersBaseGlobalLimit(String clientId,
			String activeId) {
		String key = RedisKeyUtil
				.cbbank_vouchers_base_global_limit_active_id(activeId);
		LogCvt.info("--initVouchersBaseGlobalLimit--" + key);

		try {
			if (!redisManager.exists(key)) {
				Map<String, String> cut_detail_map = readVouchersActive(
						clientId, activeId);

				int rt = Integer.parseInt(cut_detail_map.get("result"));
				if (rt == 0) {
					Map<String, String> publicPare = new HashMap<String, String>();

					publicPare.put("retMoney", cut_detail_map.get("retMoney"));// 代金券金额
					publicPare.put("maxMoney", cut_detail_map.get("maxMoney")); // 代金券总金额
					publicPare.put("resMoney", cut_detail_map.get("maxMoney"));// 限定总数

					int resCount = Integer.parseInt(cut_detail_map
							.get("maxMoney"))
							/ Integer.parseInt(cut_detail_map.get("retMoney"));

					publicPare.put("maxCount", Integer.toString(resCount));// 资格总数
					publicPare.put("resCount", Integer.toString(resCount));// 初始化资格

					String expireEndTime = cut_detail_map.get("expireEndTime");
					int expireTime = (int) ((DateUtil.parse(
							DateUtil.DATE_TIME_FORMAT2, expireEndTime)
							.getTime() - System.currentTimeMillis()) / 1000);

					if (expireTime <= 0) {
						LogCvt.debug("--初始化全局变量失败-" + publicPare);
						return 18001;
					} else {
						LogCvt.debug("--初始化全局变量：：：-" + publicPare + " time:"
								+ expireTime);
						redisManager.putMap(key, publicPare);
						redisManager.expire(key, expireTime);
						LogCvt.debug("--initVouchersBaseGlobalLimit--"
								+ publicPare);
						return 0;
					}
				} else {
					return rt;
				}
			}
			return 0;
		} catch (Exception ex) {
			LogCvt.error(ex.getMessage(), ex);
			return 18012;
		}
	}

	public static int delRedisDataByKey(String key) {
		int result = 0;
		if (redisManager.exists(key)) {
			redisManager.del(key);
			result = 1;
		}

		LogCvt.debug("已删除key " + key);
		return result;
	}
	
	
	 /**
	  * @Title: getSustainActiveIds
	  * @Description: 获得代金券关联的其他营销活动（如:满减）
	  * @author: yefeifei 2015年12月1日
	  * @modify: yefeifei 2015年12月1日
	  * @param clientId 客户端ID
	  * @param activeId 活动ID
	  * @param sustainActiveidSet 支持的活动ID集
	  * @return
	 */	
	public static Set<String> getSustainActiveIds(String clientId,
			String activeId) {
		String key = RedisKeyUtil
				.cbbank_vouchers_active_sustain_client_id_active_id(clientId,
						activeId);

		if (redisManager.exists(key)) {
			return redisManager.getSet(key);
		}
		return null;
	}

	 /**
	  * @Title: setSustainActive
	  * @Description: 初始化红包规则支持的活动
	  * @author: shenshaocheng 2015年12月1日
	  * @modify: shenshaocheng 2015年12月1日
	  * @param clientId 客户端ID
	  * @param activeId 活动ID
	  * @param sustainActiveidSet 支持的活动ID集
	  * @return
	 */	
	public static boolean setSustainActive(String clientId, String activeId,
			Set<String> sustainActiveidSet) {
		boolean flag = false;
		if (!redisManager.exists(RedisKeyUtil
				.cbbank_vouchers_active_sustain_client_id_active_id(clientId,
						activeId))) {
			// 初始化代金券-支持的其他促销活动
			redisManager.putSet(RedisKeyUtil
					.cbbank_vouchers_active_sustain_client_id_active_id(
							clientId, activeId), sustainActiveidSet);
			flag = true;
		}

		return flag;
	}

	/**
	  * @Title: checkGlobalLimit
	  * @Description: 检查客户是否有当日/小时资格
	  * @author: lf 2016年01月06日
	  * @param clientId
	  * @param activeId
	  * @param orderId
	  * @param type
	  * @return
	 */
	public static boolean checkGlobalLimit(String clientId,String activeId,String orderId){
		try{
			String key=RedisKeyUtil.cbbank_vouchers_global_limit_active_id(activeId);
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
	
	/**
	  * @Title: initGlobalLimit
	  * @Description: 初始化每日/小时全局资格
	  * @author: lf 2016年01月06日
	  * @param clientId
	  * @param activeId
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败
	 */
	public static int initGlobalLimit(String clientId,String activeId){
		String key=RedisKeyUtil.cbbank_vouchers_global_limit_active_id(activeId);
		LogCvt.info("--initGlobalLimit--"+key);
		try{
			if(!redisManager.exists(key)){
				
				Map<String,String> cut_detail_map=readVouchersActive(clientId,activeId);
				
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

	/**
	  * @Title: cutGlobalLimit
	  * @Description: 扣减每日/小时全局资格
	  * @author: lf 2016年01月06日
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败  9-当天资格用满  10-当天资格扣减失败 
	 */
	public static int cutGlobalLimit(String clientId,String activeId){
		try{
			String key=RedisKeyUtil.cbbank_vouchers_global_limit_active_id(activeId);
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

	/**
	  * @Title: rollbackGlobalLimit
	  * @Description: 回滚每日/小时全局资格
	  * 			  type=0 订单创建失败 无条件回滚
	  *               type=1订单创建成功，检查订单在回滚
	  * @author: lf 2016年01月06日
	  * @param clientId
	  * @param activeId
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败  9-当天资格用满  10-当天资格扣减失败  11-回退当天资格失败
	 */
	public static int rollbackGlobalLimit(String clientId,String activeId,String orderId,int type){
		try{
			String key=RedisKeyUtil.cbbank_vouchers_global_limit_active_id(activeId);
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
	 * 获取代金券使用次数
	 * // TODO 2016-07-01 00:00:00 去掉此方法
	 * */
	public static int getVouchersUseNumber(String vouchersId){
		try{
			String key=RedisKeyUtil.cbbank_vouchers_use_number_vouchers_id(vouchersId);
			LogCvt.info("--getVouchersUseNumber--"+key);
			if(!redisManager.exists(key)){
				return 999999;
			}
			Integer number = Integer.parseInt(redisManager.getString(key));
			LogCvt.info("--getVouchersUseNumber--count: "+ number);
			return number;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return -1;
		}
	}
	
	/**
	 * 设置代金券使用次数
	 * // TODO 2016-07-01 00:00:00 去掉此方法
	 * */
	public static void putVouchersUseNumber(String vouchersId, int number){
		try{
			String key=RedisKeyUtil.cbbank_vouchers_use_number_vouchers_id(vouchersId);
			LogCvt.info("--putVouchersUseNumber--"+key);
			if( redisManager.exists(key) ){
				redisManager.putString(key, ""+number);
			}
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
		}
	}
	
	/**
	 * 获取代金券使用人
	 * // TODO 2016-07-01 00:00:00 去掉此方法
	 * */
	public static Set<String> getVouchersUsers(String vouchersId){
		String key = RedisKeyUtil.cbbank_vouchers_users_vouchers_id(vouchersId);
		LogCvt.info("--getVouchersUsers--"+key);
		try{
			if(!redisManager.exists(key)){
				return null;
			}
			for(String user : redisManager.getSet(key)) {
				LogCvt.info("--getVouchersUsers--user : "+user);
			}
			return redisManager.getSet(key);
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return null;
		}
	}
	
	/**
	 * 累加 代金券使用人
	 * // TODO 2016-07-01 00:00:00 去掉此方法
	 * */
	public static void accumulateVouchersUser(String vouchersId, Long memberCode){
		String key = RedisKeyUtil.cbbank_vouchers_users_vouchers_id(vouchersId);
		LogCvt.info("--accumulateVouchersUser--"+key);
		try{
			Set<String> users = getVouchersUsers(vouchersId);
			if( users == null ){
				users = new HashSet<String>();
			}
			users.add(""+memberCode);
			LogCvt.info("--getVouchersUsers--key["+key+"]--add users : "+memberCode);
			redisManager.putSet(key, users);
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
		}
	}
}
