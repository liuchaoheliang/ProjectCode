package com.froad.db.redis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ActiveStatus;
import com.froad.enums.ActiveType;
import com.froad.handler.ActiveBaseRuleHandler;
import com.froad.handler.ActiveDetailRuleHandler;
import com.froad.handler.ActiveOrderHandler;
import com.froad.handler.VouchersInfoHandler;
import com.froad.handler.impl.ActiveBaseRuleHandlerImpl;
import com.froad.handler.impl.ActiveDetailRuleHandlerImpl;
import com.froad.handler.impl.ActiveOrderHandlerImpl;
import com.froad.handler.impl.VouchersInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveDetailRule;
import com.froad.po.ActiveOrder;
import com.froad.po.ActiveResultInfo;
import com.froad.util.ActiveUtils;
import com.froad.util.DateUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.Validator;

/**
 * 
  * @ClassName: RedisCommon
  * @Description: RedisCommon 操作
  * @author froad-zengfanting 2015年11月7日
  * @modify froad-zengfanting 2015年11月7日
 */
public class RedisCommon {
	
	private static final RedisManager redis = new RedisManager();
	
	
	/**
	  * @Title: initBaseGlobalLimit
	  * @Description: 初始化全局资格BaseGlobalLimit
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败  9-当天资格用满  10-当天资格扣减失败  11-回退当天资格失败
	  *          12-初始化全局资格失败 13-活动还未生效
	 */
	public static int initBaseGlobalLimit(String clientId,String activeId){
		String key=RedisKeyUtil.cbbank_active_base_global_limit_active_id(activeId);
		LogCvt.info("--initBaseGlobalLimit--"+key);
		VouchersInfoHandler vouchersInfoHandler = new VouchersInfoHandlerImpl();
		try{
			if(!redis.exists(key))
			{
				Map<String,String> cut_detail_map=readFullCutActive(clientId,activeId);
				
				int rt=Integer.parseInt(cut_detail_map.get("result"));
				if(rt==0){
					Map<String,String> publicPare=new HashMap<String,String>();
					String retMoney = (cut_detail_map.get("retMoney") == null ? "0" : cut_detail_map.get("retMoney")).toString();
					String maxMoney = (cut_detail_map.get("maxMoney") == null ? "0" : cut_detail_map.get("maxMoney")).toString();
					publicPare.put("retMoney", retMoney);//满减金额
					publicPare.put("maxMoney", maxMoney); //满减总金额
					publicPare.put("resMoney",maxMoney);//限定总数
					
					//分别判断满减，满赠送积分，满赠送红包
					int resCount = 0;
					if(cut_detail_map.get("isPrePay").equals("1")) {
						resCount = Integer.parseInt(cut_detail_map.get("maxMoney"))/Integer.parseInt(cut_detail_map.get("retMoney"));						
					} else if(cut_detail_map.get("isPrePay").equals("0") 
							&& !Validator.isEmptyStr(cut_detail_map.get("pointCount"))){
						resCount = Integer.parseInt(cut_detail_map.get("pointCount"))/Integer.parseInt(cut_detail_map.get("point"));						
					} else if(cut_detail_map.get("isPrePay").equals("0") 
							&& !Validator.isEmptyStr(cut_detail_map.get("prePayActiveId"))){
						resCount
						= vouchersInfoHandler.findAvailableVouchersListByActiveId(activeId).size();
					}					
					
					publicPare.put("maxCount", Integer.toString(resCount));//资格总数
					publicPare.put("resCount", Integer.toString(resCount));//初始化资格
					publicPare.put("type", cut_detail_map.get("isPrePay"));// 支付类型
					String expireEndTime=cut_detail_map.get("expireEndTime");
					int expireTime=(int)((DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireEndTime).getTime()-System.currentTimeMillis())/1000);

					if(expireTime<=0){
						LogCvt.debug("--初始化全局变量失败-"+publicPare);
						return 18001;
					}else{
						LogCvt.debug("--初始化全局变量：：：-"+publicPare+" time:"+expireTime);
						redis.putMap(key, publicPare);
						redis.expire(key, expireTime);
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
	  * @Title: checkBaseGlobalLimit
	  * @Description: 全局资格检查
	  * @author: zengfanting 2015年11月16日
	  * @modify: zengfanting 2015年11月16日
	  * @param clientId
	  * @param activeId
	  * @return
	 */
	public static boolean checkBaseGlobalLimit(String clientId,String activeId){
		try{
			String key=RedisKeyUtil.cbbank_active_base_global_limit_active_id(activeId);
			LogCvt.info("--checkBaseGlobalLimit--"+key);
			if(!redis.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
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
			
		   Map<String,String> tmap = redis.getMap(key);
		   
		   LogCvt.debug("--checkBaseGlobalLimit--"+tmap);
		   
		   String maxMoney = tmap.get("maxMoney");
		   String resMoney = tmap.get("resMoney");
		   String maxCount = tmap.get("maxCount");
		   String resCount = tmap.get("resCount");
		   String type = tmap.get("type");
		   if(Integer.parseInt(resCount)<=0||Integer.parseInt(resCount)>Integer.parseInt(maxCount)){
			   return false;
		   }
		   
		   if(type.equals(ActiveType.fullCut.getCode()) && Integer.parseInt(resMoney)<=0||Integer.parseInt(resMoney)>Integer.parseInt(maxMoney)){
			   return false;
		   }
		   
		   return true;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return false;
		}
	}
	
	
	/**
	 * 获取活动的全局资格
	 * */
	public static Map<String, String> getBaseGlobalLimit(String clientId,String activeId){
		String key=RedisKeyUtil.cbbank_active_base_global_limit_active_id(activeId);
		LogCvt.info("--getBaseGlobalLimit--"+key);
		try{
			// 如果没有重新初始化一次
			if(!redis.exists(key)){
				int result = RedisCommon.initBaseGlobalLimit(clientId, activeId);
				LogCvt.debug("不存在此key,重新初始化-结果:"+result);
			}
			return redis.getMap(key);
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return null;
		}
	}
	
	/**
	 * 
	  * @Title: cutBaseGlobalLimit
	  * @Description: 扣减全局资格
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @param clientId
	  * @param activeId
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败  9-当天资格用满  10-当天资格扣减失败  11-回退当天资格失败
	  *          12-初始化全局资格失败  13-无全局资格  14-扣减全局资格失败
	 */
	public static int cutBaseGlobalLimit(String clientId,String activeId){
		try{
			String key=RedisKeyUtil.cbbank_active_base_global_limit_active_id(activeId);
			LogCvt.info("--cutBaseGlobalLimit--"+key);
			if(!redis.exists(key)){
				int result=initBaseGlobalLimit( clientId, activeId);
				if(result!=0){
					return result;
				}
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return 18016;
			}
			
		   Map<String,String> tmap = redis.getMap(key);
		   
		   LogCvt.debug("--cutBaseGlobalLimit--"+tmap);
		   
		   String type = tmap.get("type");
		   
		   String retMoney = tmap.get("retMoney"); // 减多少
		   String maxMoney = tmap.get("maxMoney"); // 满减总额
		   String resMoney = tmap.get("resMoney"); // 剩余满减总额
		   String maxCount = tmap.get("maxCount"); // 总次数
		   String resCount = tmap.get("resCount"); // 剩余总次数
		   
		   if(Integer.parseInt(maxCount)==0){//无全局资格
			   return 18013;
		   }
		   
		   if( type.equals(ActiveType.fullCut.getCode()) && Integer.parseInt(maxMoney)==0){//无全局资格
			   return 18013;
		   }
		   
		   if( type.equals(ActiveType.fullCut.getCode()) ){
			   int checkMoney=Integer.parseInt(resMoney)-Integer.parseInt(retMoney);
			   if(Integer.parseInt(resCount)>0&&checkMoney>=0){
				   LogCvt.debug("--扣减全局变量：：：-"+tmap+"-resCount"+(Integer.parseInt(resCount)-1));
				   
				   redis.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)-1));
				   redis.hset(key, "resMoney", Integer.toString(checkMoney));
				   return 0;
			   }else{//无资格
				   return 18013;
			   }
		   }else if( type.equals(ActiveType.fullGive.getCode()) ){
			   if(Integer.parseInt(resCount)>0){
				   LogCvt.debug("--扣减全局变量：：：-"+tmap+"-resCount"+(Integer.parseInt(resCount)-1));
				   
				   redis.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)-1));
				   return 0;
			   }else{//无资格
				   return 18013;
			   }
		   }else{
			   LogCvt.debug("--扣减全局变量：：：失败 - redis细则类型有误");
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
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @param clientId
	  * @param activeId
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败  9-当天资格用满  10-当天资格扣减失败  11-回退当天资格失败
	  *          12-初始化全局资格失败  13-无全局资格  14-扣减全局资格失败  15-资格回滚异常
	 */
	public static int rollbackBaseGlobalLimit(String clientId,String activeId){
		try{
			String key=RedisKeyUtil.cbbank_active_base_global_limit_active_id(activeId);
			LogCvt.info("--rollbackBaseGlobalLimit--"+key);
			if(!redis.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result=initBaseGlobalLimit( clientId, activeId);
				if(result!=0){
					return result;
				}
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return 18016;
			}
			
		   Map<String,String> tmap = redis.getMap(key);
		   LogCvt.debug("--rollbackBaseGlobalLimit--"+tmap);
		   
		   String retMoney = tmap.get("retMoney");
		   String maxMoney = tmap.get("maxMoney");
		   String resMoney = tmap.get("resMoney");
		   String maxCount = tmap.get("maxCount");
		   String resCount = tmap.get("resCount");
		   if(Integer.parseInt(resCount)>=Integer.parseInt(maxCount)){
			   return 18015;
		   }
		   
		   if(Integer.parseInt(resMoney)>=Integer.parseInt(maxMoney)){
				return 18015;
		   }
		   
		   LogCvt.debug("--回滚全局变量：："+tmap+"-resCount "+(Integer.parseInt(resCount)+1));
		   redis.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)+1));
		   redis.hset(key, "resMoney", Integer.toString(Integer.parseInt(resMoney)+Integer.parseInt(retMoney)));
		  
		   return 0;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18015;
		}
	}
	
	
	/**
	  * @Title: initGlobalLimit
	  * @Description: 初始化每日/小时全局资格
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @param clientId
	  * @param activeId
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败
	 */
	public static int initGlobalLimit(String clientId,String activeId){
		String key=RedisKeyUtil.cbbank_active_global_limit_active_id(activeId);
		LogCvt.info("--initGlobalLimit--"+key);
		try{
			if(!redis.exists(key)){
				
				Map<String,String> cut_detail_map=readFullCutActive(clientId,activeId);
				
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
						redis.putMap(key, everyDay);
						redis.expire(key, (int)expireTime);
						return 0;
					}
					
					if(cut_detail_map.get("totalCount")==null||"0".equals(cut_detail_map.get("totalCount"))){
						Date start=new Date(System.currentTimeMillis());
						Date end=DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, cut_detail_map.get("expireEndTime"));
						long expireTime=Validator.checkActiveVilidTime(start,end);
						LogCvt.debug("--初始化当日全局变量：：：-不限制资格");
						redis.putMap(key, everyDay);
						redis.expire(key, (int)expireTime);
						return 0;
					}
					
					
					long expireTime=(long)expireOpt(0,cut_detail_map.get("isTotalDay"),cut_detail_map.get("totalDay"));
					everyDay.put("startDate", DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(System.currentTimeMillis())));
					everyDay.put("endDate", DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(System.currentTimeMillis()+expireTime*1000)));
					
					if(expireTime<=0){
						LogCvt.debug("--初始化当日全局变量失败-"+everyDay);
						return 18001;
					}else{
						LogCvt.debug("--初始化当日全局变量：：：-"+everyDay+" time:"+expireTime);
						redis.putMap(key, everyDay);
						redis.expire(key, (int)expireTime);
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
	 * 获取活动的每日/小时全局资格
	 * */
	public static Map<String, String> getGlobalLimit(String clientId,String activeId){
		String key=RedisKeyUtil.cbbank_active_global_limit_active_id(activeId);
		LogCvt.info("--getGlobalLimit--"+key);
		try{
			// 如果没有重新初始化一次
			if(!redis.exists(key)){
				RedisCommon.initGlobalLimit(clientId, activeId);
			}
			return redis.getMap(key);
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return new HashMap<String, String>();
		}
	}
	
	
	/**
	  * @Title: checkGlobalLimit
	  * @Description: 检查客户是否有当日/小时资格
	  * @author: zengfanting 2015年11月16日
	  * @modify: zengfanting 2015年11月16日
	  * @param clientId
	  * @param activeId
	  * @param orderId
	  * @param type
	  * @return
	 */
	public static boolean checkGlobalLimit(String clientId,String activeId,String orderId){
		try{
			String key=RedisKeyUtil.cbbank_active_global_limit_active_id(activeId);
			LogCvt.info("--checkGlobalLimit--"+key);
			if(!redis.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
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
			
		   Map<String,String> tmap = redis.getMap(key);
		   
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
	  * @Title: cutGlobalLimit
	  * @Description: 扣减每日/小时全局资格
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败  9-当天资格用满  10-当天资格扣减失败 
	 */
	public static int cutGlobalLimit(String clientId,String activeId){
		try{
			String key=RedisKeyUtil.cbbank_active_global_limit_active_id(activeId);
			LogCvt.info("--cutGlobalLimit--"+key);
			if(!redis.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result=initGlobalLimit( clientId, activeId);
				if(result!=0){
					return result;
				}
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return 18016;
			}
			
		   Map<String,String> tmap = redis.getMap(key);
		   
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
			   redis.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)-1));
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
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @param clientId
	  * @param activeId
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  
	  *          4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	  *          8-初始化日全局资格失败  9-当天资格用满  10-当天资格扣减失败  11-回退当天资格失败
	 */
	public static int rollbackGlobalLimit(String clientId,String activeId,String orderId,int type){
		try{
			String key=RedisKeyUtil.cbbank_active_global_limit_active_id(activeId);
			LogCvt.info("--rollbackGlobalLimit--"+key);
			if(!redis.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result=initGlobalLimit( clientId, activeId);
				if(result!=0){
					return result;
				}
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return 18016;
			}
			
		   Map<String,String> tmap = redis.getMap(key);
		   
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
			   redis.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)+1));
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
				   redis.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)+1));
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
	  * @Title: initPersonLimit
	  * @Description: 初始化个人购买资格
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @param clientId
	  * @param activeId
	  * @param memberCode
	  * @return  0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  4  初始化个人资格失败
	 */
	public static int initPersonLimit(String clientId,String activeId,long memberCode){
		String key=RedisKeyUtil.cbbank_active_person_limit_active_id_member_code(activeId, Long.toString(memberCode));
		LogCvt.info("--initPersonLimit--"+key);
		try{
			if(!redis.exists(key)){
				Map<String,String> cut_detail_map=readFullCutActive(clientId,activeId);
				int rt=Integer.parseInt(cut_detail_map.get("result"));
				if(rt==0){
					Map<String,String> person=new HashMap<String,String>();
					person.put("isPerDay", cut_detail_map.get("isPerDay"));//0-小时  1-天//
					
					person.put("perDay", cut_detail_map.get("perDay")); //限定次数
					
					person.put("perCount",cut_detail_map.get("perCount"));//限定总数
					
					person.put("resCount", cut_detail_map.get("perCount"));//初始化资格
					
					
				   if(cut_detail_map.get("perDay")==null||"0".equals(cut_detail_map.get("perDay"))){
					   LogCvt.debug("--初始化个人变量：：：-不限制资格");
					   redis.putMap(key, person);
					   return 0;
				   }
					   
				   if(cut_detail_map.get("perCount")==null||"0".equals(cut_detail_map.get("perCount"))){
					   LogCvt.debug("--初始化个人变量：：：-不限制资格");
					   redis.putMap(key, person);
					   return 0;
				   }
				   
				   int exprie=(int)expireOpt(0,cut_detail_map.get("isPerDay"),cut_detail_map.get("perDay"));
				   if(exprie<=0){
					   return 18001;
				   }else{
					   LogCvt.debug("--初始化个人变量：：：-"+person+" time:"+exprie);
					   redis.putMap(key, person);
					   redis.expire(key,exprie);
					   LogCvt.debug("--initPersonLimit--"+person);
					   return 0;
				   }
				}else{
					return rt;
				}
			}
			return 0;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18004;
		}
	}
	
	/**
	 * 获取活动的个人资格
	 * */
	public static Map<String, String> getPersonLimit(String clientId,String activeId,long memberCode){
		String key=RedisKeyUtil.cbbank_active_person_limit_active_id_member_code(activeId, Long.toString(memberCode));
		LogCvt.info("--getPersonLimit--"+key);
		try{
			return redis.getMap(key);
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return null;
		}
	}
	
	
	/**
	  * @Title: checkPersonLimit
	  * @Description: 检查个人资格
	  * @author: zengfanting 2015年11月16日
	  * @modify: zengfanting 2015年11月16日
	  * @param clientId
	  * @param activeId
	  * @param memberCode
	  * @return
	 */
	public static boolean checkPersonLimit(String clientId,String activeId,long memberCode){
		try{
			String key=RedisKeyUtil.cbbank_active_person_limit_active_id_member_code(activeId, Long.toString(memberCode));
			LogCvt.info("--checkPersonLimit--"+key);
			if(redis.exists(key)){
			   Map<String,String> tmap = redis.getMap(key);
			   String perDay = tmap.get("perDay");
			   String perCount = tmap.get("perCount");
			   String resCount = tmap.get("resCount");
			   
			   LogCvt.debug("--checkPersonLimit--"+tmap);
			   
			   if(perDay==null||"0".equals(perDay)){//0天N次 不限资格
				   return true;
			   }
			   
			   if(perCount==null||"0".equals(perCount)){//0天N次 不限资格
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
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @return   0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  4  初始化个人资格失败  5-无购买资格  6-扣减购买资格异常  7-回退资格异常
	 */
	public static int cutPersonLimit(String clientId,String activeId,long memberCode){
		try{
			String key=RedisKeyUtil.cbbank_active_person_limit_active_id_member_code(activeId, Long.toString(memberCode) );
			LogCvt.info("--cutPersonLimit--"+key);
			if(!redis.exists(key)){//直接判断是否有资格，有直接扣取，没有提示无法扣取
				int result=initPersonLimit( clientId, activeId, memberCode);
				if(result!=0){
					return result;
				}
			}
			
			if(!checkRuleVilad(clientId,activeId)){
				  return 18016;
			}
			
		   Map<String,String> tmap = redis.getMap(key);
		   String perDay = tmap.get("perDay");
		   String perCount = tmap.get("perCount");
		   String resCount = tmap.get("resCount");
		   LogCvt.debug("--cutPersonLimit--"+tmap);
		   if(perDay==null||"0".equals(perDay)){
			   redis.del(key);
			   return 0;
		   }
		   
		   if(perCount==null||"0".equals(perCount)){
			   redis.del(key);
			   return 0;
		   }
		   
		  if(Integer.parseInt(resCount)>0 && Integer.parseInt(resCount)<=Integer.parseInt(perCount)){
			   LogCvt.debug("--个人扣减变量：：：-"+tmap+"-resCount "+(Integer.parseInt(resCount)-1));
			   redis.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)-1));
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
	  * @Title: rollbackPersonLimit
	  * @Description: TODO
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @return0-初始化成功  1-活动失效  2-初始化活动失败 3-读取活动失败  4  初始化个人资格失败 5-无购买资格  7-回退资格异常
	 */
	public static int rollbackPersonLimit(String clientId,String activeId, long memberCode){
		try{
			String key=RedisKeyUtil.cbbank_active_person_limit_active_id_member_code(activeId, Long.toString(memberCode));
			LogCvt.info("--rollbackPersonLimit--"+key);
			if(redis.exists(key)){
				
				if(!checkRuleVilad(clientId,activeId)){
					  return 18016;
				}
				
			   Map<String,String> tmap = redis.getMap(key);
			   String perDay = tmap.get("perDay");
			   String perCount = tmap.get("perCount");
			   String resCount = tmap.get("resCount");
			   LogCvt.debug("--rollbackPersonLimit--"+tmap);
			   if(perDay==null||"0".equals(perDay)){//0天N次 不限资格
				   redis.del(key);
				   return 0;
			   }
			   
			   if(perCount==null||"0".equals(perCount)){//0天N次 不限资格
				   redis.del(key);
				   return 0;
			   }
 
			   if(Integer.parseInt(resCount)<Integer.parseInt(perCount)){
				   LogCvt.debug("--个人变量回滚：：：-"+tmap+"-resCount "+(Integer.parseInt(resCount)+1));
				   redis.hset(key, "resCount", Integer.toString(Integer.parseInt(resCount)+1)); 
					return 0;
			   }else{
				   return 18007;
			   }
			}
			return 0;
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 18007;
		}
		
	}
	
	
	
	
	/**
	  * @Title: expireOpt
	  * @Description: 计算资格有效期
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @return type=0 自然日/小时   type==1 整点/整日
	 */
	public static long expireOpt(int type,String isPerDay,String perDay){
		if(type==0){
			if("0".equals(isPerDay)){//按小时计算
				long expireTime=DateUtil.parse(DateUtil.DATE_TIME_FROMAT6, DateUtil.formatDateTime(DateUtil.DATE_TIME_FROMAT6, System.currentTimeMillis())).getTime();
				expireTime=expireTime+Integer.parseInt(perDay)*60*60*1000;
				expireTime=(expireTime-System.currentTimeMillis())/1000;
				return expireTime;
			}else{//按日计算
				long expireTime=DateUtil.parse(DateUtil.DATE_FORMAT1, DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, System.currentTimeMillis())).getTime();
				expireTime=expireTime+Integer.parseInt(perDay)*24*60*60*1000;
				expireTime=(expireTime-System.currentTimeMillis())/1000;
				return expireTime;
			}
		}else{
			if("0".equals(isPerDay)){//按小时计算
				long expireTime=System.currentTimeMillis();
				expireTime=expireTime+Integer.parseInt(perDay)*60*60*1000;
				expireTime=(expireTime-System.currentTimeMillis())/1000;
				return expireTime;
			}else{//按日计算
				long expireTime=System.currentTimeMillis();
				expireTime=expireTime+Integer.parseInt(perDay)*24*60*60*1000;
				expireTime=(expireTime-System.currentTimeMillis())/1000;
				return expireTime;
			}
			
		}
	}
	
	
	
	/**
	  * @Title: readFullCutActive
	  * @Description: 读取Redis 里面的满减活动细则
	  * 			  默认从Redis里面读取，如读取不到从Mysql里面加载读取
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @param clientId
	  * @param activeId
	  * @return  0-初始化成功  18001-活动失效  18002-初始化活动失败 18003-读取活动失败
	 */
	public static Map<String,String> readFullCutActive(String clientId,String activeId){
		Map<String,String> tempMap=new HashMap<String,String>();
		String redis_key=RedisKeyUtil.cbbank_full_cut_active_client_id_active_id(clientId, activeId);
		LogCvt.info("--readFullCutActive--"+redis_key);
		try{
			if(redis.exists(redis_key)){
				LogCvt.debug("----------读取促销活动细则----Redis获取-------");
				tempMap=redis.getMap(redis_key);
				
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
				ActiveDetailRuleHandler activeDetailRuleHandler=new ActiveDetailRuleHandlerImpl();
				
				ActiveBaseRule activeBaseRule=new ActiveBaseRule();
				activeBaseRule.setClientId(clientId);
				activeBaseRule.setActiveId(activeId);
				activeBaseRule.setStatus(ActiveStatus.launch.getCode());//启用状态
				activeBaseRule=activeBaseRuleHandler.findOneActiveBaseRule(activeBaseRule);
				

				ActiveDetailRule activeDetailRule=new ActiveDetailRule();
				activeDetailRule.setClientId(clientId);
				activeDetailRule.setActiveId(activeId);
				//activeDetailRule.setIsPrePay(true);
				activeDetailRule=activeDetailRuleHandler.findOneActiveDetailRule(activeDetailRule);
				if(activeBaseRule==null||activeDetailRule==null){
					tempMap.put("result", "18002");
					return tempMap;
				}
				
				int result=initFullCutActive(activeBaseRule,activeDetailRule);
				if(result==0){
					Map<String, String> activeBaseRuleMap= ActiveUtils.PO2Map(activeBaseRule);
					Map<String, String> activeDetailRuleMap= ActiveUtils.PO2Map(activeDetailRule);
					activeDetailRuleMap.putAll(activeBaseRuleMap);
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
	  * @Title: initFullCutActive
	  * @Description:初始化满减    Add活动 或者修改活动
	  * @author: zengfanting 2015年11月8日
	  * @modify: zengfanting 2015年11月8日
	  * @param activeBaseRule
	  * @param activeDetailRule
	  * @return 0-初始化成功  1-活动失效  2-初始化活动失败
	 */
	public static int initFullCutActive(ActiveBaseRule activeBaseRule,ActiveDetailRule activeDetailRule){
		try{
			String redis_key=RedisKeyUtil.cbbank_full_cut_active_client_id_active_id(activeBaseRule.getClientId(), activeBaseRule.getActiveId());
			LogCvt.info("--initFullCutActive-"+redis_key);
		   //全部重新设置
			Map<String, String> activeBaseRuleMap= ActiveUtils.PO2Map(activeBaseRule);
			Map<String, String> activeDetailRuleMap= ActiveUtils.PO2Map(activeDetailRule);
			activeDetailRuleMap.putAll(activeBaseRuleMap);
			
			
			//校验是否有效
			Date startTime=new Date(System.currentTimeMillis()); 
			Date endTime=activeBaseRule.getExpireStartTime();
			long expire=Validator.checkActiveVilidTime(startTime,endTime);
			
			if(expire>0){//活动还未生效
				return 3;
			}
			
			long expireTime=-1;
			Date start=new Date(System.currentTimeMillis());
			Date end=activeBaseRule.getExpireEndTime();
			expireTime=Validator.checkActiveVilidTime(start,end);
			
			if(expireTime>=0){
				redis.putMap(redis_key, activeDetailRuleMap);
				redis.expire(redis_key, (int)expireTime);
				LogCvt.debug("----设置活动成功----初始化满减活动细则-----"+expireTime);
				return 0;
			}else{
				SupportsRedis.del_cbbank_active_merchant_info(activeBaseRule.getActiveId());
				SupportsRedis.del_cbbank_active_product_info(activeBaseRule.getActiveId());
				LogCvt.debug("----活动失效----初始化满减活动细则-----");
			   return 1;
			}
		}catch(Exception ex){
			LogCvt.error(ex.getMessage(), ex);
			return 2;	
		}finally{
			LogCvt.info("----end----初始化满减活动细则-----");
		}
		
	}
	
	
	/**
	 * @param tempMap
	 * @param clientId
	 * @param activeId
	 * @return
	 */
	public static boolean   checkRuleVilad(String clientId,String  activeId){
		String redis_key=RedisKeyUtil.cbbank_full_cut_active_client_id_active_id(clientId, activeId);
		Map<String, String> tempMap=null;
		if (!redis.exists(redis_key)){
			tempMap=readFullCutActive( clientId,activeId);
			if(Integer.parseInt(tempMap.get("result"))!=0){
				return false;
			}
		}
		
	   tempMap	=redis.getMap(redis_key);
	   
		String expireStartTime=tempMap.get("expireStartTime");
		Date start=new Date(System.currentTimeMillis()); 
		Date end=DateUtil.parse(DateUtil.DATE_TIME_FORMAT2, expireStartTime); 
		long expireTime=Validator.checkActiveVilidTime(start,end);
		
		if(expireTime<=0){//活动生效
			return true;
		}else{//活动还没启动
			String keyGlobal=RedisKeyUtil.cbbank_active_global_limit_active_id(activeId);
			String keyBase=RedisKeyUtil.cbbank_active_base_global_limit_active_id(activeId);
			redis.del(redis_key); //清除细则Redis
			redis.del(keyGlobal); //清除全局Redis
			redis.del(keyBase);   //清楚全局Reids
			return false;
		}
	}
	
	/**
	 * 移除过期活动Redis
	 * @param activeIdList
	 * @param isOverdue
	 * @return
	 */
	public static List<ActiveResultInfo> delOverdueActivitiesRedisByActiveId(
			List<String> activeIdList) {
		List<ActiveResultInfo> activeResultList = new ArrayList<ActiveResultInfo>();
		if(activeIdList != null) {
			for(String activeId : activeIdList) {
				SupportsRedis.del_cbbank_active_product_info(activeId);
				SupportsRedis.del_cbbank_active_merchant_info(activeId);
				ActiveResultInfo activeResultInfo = new ActiveResultInfo();
				activeResultInfo.setActiveId(activeId);
				activeResultList.add(activeResultInfo);
			}			
		} 
		return activeResultList;
	}	
}

