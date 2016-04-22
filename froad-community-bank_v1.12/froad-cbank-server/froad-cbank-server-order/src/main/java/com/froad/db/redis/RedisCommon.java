package com.froad.db.redis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.OrderMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.logic.impl.order.OrderLogger;
import com.froad.po.OrderValidateInfo;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.RedisKeyUtil;
import com.froad.util.payment.TimeHelper;
import com.froad.util.payment.TimeHelper.TimeType;

/**
 *  Redis获取数据公共类
  * @ClassName: RedisCommon
  * @Description: order模块内部公共类
  * @author share 2015年3月25日
  * @modify share 2015年3月25日
 */
public class RedisCommon {
	
	private static final RedisManager redis = new RedisManager();
	
	/**
	 *  Rdis获取的Map，如果为空业务返回Map不是空对象
	 *  如果size是空返回null，利于前端检查
	  * @Title: converRedisMap
	  * @author: share 2015年4月11日
	  * @modify: share 2015年4月11日
	  * @param @param redisMap
	  * @param @return    
	  * @return Map<String,String>    
	  * @throws
	 */
	private static Map<String,String> converRedisMap(Map<String,String> redisMap){
		if(redisMap != null && redisMap.isEmpty()){
			return null;
		}
		return redisMap;
	}
	
	/**
	 *  获取门店Redis信息
	  * @Title: xx
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @return    
	  * @return Map<String,String>    
	  * @throws
	 */
	public static Map<String,String> getOutletRedis(String clientId,String merchantId,String outletId){
		String outletRedisKey = RedisKeyUtil.cbbank_outlet_client_id_merchant_id_outlet_id(clientId, merchantId, outletId);
		Map<String,String> outletMap = redis.getMap(outletRedisKey);
		
		SqlSession sqlSession = null;
        OrderMapper orderMapper = null;
        try {
        	sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            orderMapper = sqlSession.getMapper(OrderMapper.class);
            //如果redis为空，从mysql取
    		if (Checker.isNotEmpty(outletMap) || !outletMap.containsKey("outlet_name")) {
    			LogCvt.info("redis获取商品信息查询为空，从mysql查询商品信息，查询条件：clientId:" + clientId + ",merchantId:" + merchantId + ",outletId:"+outletId);
    			String outletName = orderMapper.getOutletNameByOutletId(clientId, merchantId, outletId);
    			if(EmptyChecker.isNotEmpty(outletName)){
    				outletMap.put("outlet_name", outletName);
    				
    				//将mysql信息刷回redis
    				redis.putMap(outletRedisKey, outletMap);
    			}
    		}
        } catch (Exception e) {
            LogCvt.error("[严重错误]-获取门店名称失败，原因:" + e); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            }
        }
		
		return converRedisMap(outletMap);
	}
	
	/**
	 *  获取活动Redis信息
	  * @Title: getActivityRedis
	  * @author: share 2015年3月25日
	  * @modify: share 2015年3月25日
	  * @param @param clientId
	  * @param @param activityId
	  * @param @return    
	  * @return Map<String,String>    
	  * @throws
	 */
	public static Map<String,String> getActivityRedis(String clientId,long activityId){
		String activityRedisKey = RedisKeyUtil.cbbank_activities_client_id_activities_id(clientId, activityId);
		Map<String,String> activityMap = redis.getMap(activityRedisKey);
		return converRedisMap(activityMap);
	}
	
	/**
	 *  获取用户订单记录
	  * @Title: getUserOrderCountRedis
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param clientId
	  * @param @param merchantId
	  * @param @param productId
	  * @param @return    
	  * @return Map<String,String>    
	  * @throws
	 */
	public static Map<String,String> getUserOrderCountRedis(String clientId,long memberCode,String productId){
		String storeKey = RedisKeyUtil.cbbank_product_limit_client_id_member_code_product_id(clientId,memberCode,productId);
		Map<String,String> storeMap = redis.getMap(storeKey);
		return converRedisMap(storeMap);
	}
	
	/**
	 *  获取用户秒杀订单记录
	 * @Title: getUserOrderCountRedis
	 * @author: share 2015年4月2日
	 * @modify: share 2015年4月2日
	 * @param @param clientId
	 * @param @param merchantId
	 * @param @param productId
	 * @param @return    
	 * @return Map<String,String>    
	 * @throws
	 */
	public static int getUserSeckillOrderCountRedis(String memberCode,String productId,String endDateYYMMdd){
		String storeKey = RedisKeyUtil.cbbank_seckill_memcnt_member_code_product_id_end_date(memberCode,productId,endDateYYMMdd);
		String count = redis.getString(storeKey);
		if(EmptyChecker.isEmpty(count)){
			count = "0";
		}else{
			if(Integer.valueOf(count) < 0){
				count = "0";
			}
		}
		LogCvt.info("[秒杀模块]-查询用户购买记录，用户购买数为："+count+"，请求Key：" +  RedisKeyUtil.cbbank_seckill_memcnt_member_code_product_id_end_date(memberCode,productId,endDateYYMMdd));
		return Integer.valueOf(count);
	}
	
	
	/**
	 *  获取秒杀商品缓存
	 * @Title: getSeckillProductRedis
	 * @author: share 2015年4月2日
	 * @modify: share 2015年4月2日
	 * @param @param clientId
	 * @param @param merchantId
	 * @param @param productId
	 * @param @return    
	 * @return Map<String,String>    
	 * @throws
	 */
	public static OrderValidateInfo getSeckillProductRedis(String clientId,String productId){
		String storeKey = RedisKeyUtil.cbbank_seckill_product_client_id_product_id(clientId,productId);
		Map<String,String> storeMap = redis.getMap(storeKey);
		OrderValidateInfo data = new OrderValidateInfo();
		if(EmptyChecker.isNotEmpty(storeMap)){
			String buyLimit = storeMap.get("buy_limit");
			if(EmptyChecker.isNotEmpty(buyLimit)){
				data.setSeckillProductBuyLimit(Integer.valueOf(buyLimit));
			}
			String endTimeStr = storeMap.get("end_time");
			if(EmptyChecker.isNotEmpty(endTimeStr)){
				Long endTime = Long.valueOf(endTimeStr);
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
				String endDateYYMMdd = String.valueOf(df.format(new Date(endTime)));
				data.setProductEndTime(endDateYYMMdd);
			}
		}
		LogCvt.info("[秒杀模块]-查询用户购买记录，商品限购数为："+data.getSeckillProductBuyLimit()+"，请求Key：" +  RedisKeyUtil.cbbank_seckill_product_client_id_product_id(clientId,productId));
		return data;
	}
	
	
	/**
	 *  获取预售商品在网点累计已提货数
	 * @Title: getUserOrderCountRedis
	 * @Description: 获取商品在网点累计已提货数
	 * @author: share 2015年4月2日
	 * @modify: share 2015年4月2日
	 * @param @param clientId
	 * @param @param merchantId
	 * @param @param productId
	 * @param @return    
	 * @return Map<String,String>    
	 * @throws
	 */
	public static int getOutletPresellProductTokenCountRedis(String clientId,String orgCode,String productId){
		String key = RedisKeyUtil.cbbank_product_presell_token_org_code_product_id(clientId,orgCode,productId);
		String count = redis.getString(key);
		return count == null ? 0 : Integer.valueOf(count);
	}
	
	/**
	 *  更新该商品在该门店已经提货数量Redis
	 * @Title: getProductPresellMaxRedis
	 * @Description: 获取商品在每个门店最大提货数量Redis
	 * @author: share 2015年3月25日
	 * @modify: share 2015年3月25日
	 * @param @param productId
	 * @param @return    
	 * @return Map<String,String>    
	 * @throws
	 */
	public static void updatePresellProductTokenRedis(String clientId, String orgCode,String productId,Long value){
		String tokenKey = RedisKeyUtil.cbbank_product_presell_token_org_code_product_id(clientId,orgCode,productId);
		redis.incrBy(tokenKey, value);
	}
	
	/**
	 *  更新商品销售数量Redis（count>0增加,count<0减少）
	 * @Title: getProductPresellMaxRedis
	 * @Description: 更新商品销售数量Redis
	 * @author: share 2015年3月25日
	 * @modify: share 2015年3月25日
	 * @param clientId
	 * @param merchantId    
	 * @param productId    
	 * @param count    
	 * @throws
	 */
	public static void updateProductSellCountRedis(String clientId, String merchantId,String productId,int count){
		String sellCountKey = RedisKeyUtil.cbbank_product_sellcount_client_id_merchant_id_product_id(clientId,merchantId,productId);
		if(count >= 0){
			redis.incrBy(sellCountKey, Long.valueOf(count));
		}else{
			redis.decrBy(sellCountKey, Long.valueOf(count));
		}
	}
	
	/**
	 *  更新秒杀商品销售数量Redis（count>0增加,count<0减少）
	 * @Title: getProductPresellMaxRedis
	 * @Description: 更新商品销售数量Redis
	 * @author: share 2015年3月25日
	 * @modify: share 2015年3月25日
	 * @param clientId
	 * @param merchantId    
	 * @param productId    
	 * @param count    
	 * @throws
	 */
	public static void updateSeckillProductSellCountRedis(String clientId, String merchantId,String productId,int count){
		String sellCountKey = RedisKeyUtil.cbbank_seckill_product_soldcnt_client_id_product_id(clientId,productId);
		if(count >= 0){
			redis.incrBy(sellCountKey, Long.valueOf(count));
		}else{
			redis.decrBy(sellCountKey, Long.valueOf(count));
		}
	}
	
	/**
	 *  获取商品销售数量Redis
	 * @Title: getProductSellCountRedis
	 * @Description: 获取商品销售数量Redis
	 * @author: share 2015年3月25日
	 * @modify: share 2015年3月25日
	 * @param clientId
	 * @param merchantId    
	 * @param productId    
	 * @param count    
	 * @throws
	 */
	public static int getProductSellCountRedis(String clientId, String merchantId,String productId){
		String sellCountKey = RedisKeyUtil.cbbank_product_sellcount_client_id_merchant_id_product_id(clientId,merchantId,productId);
		String count = redis.getString(sellCountKey);
		if(EmptyChecker.isNotEmpty(count)){
			return Integer.valueOf(count);
		}else{
			return -1;
		}
	}
	
	
	/**
	 *  查询用户赠送积分记录
	 * @Title: getProductPresellMaxRedis
	 * @Description: 更新商品销售数量Redis
	 * @author: share 2015年3月25日
	 * @modify: share 2015年3月25日
	 * @param clientId
	 * @param productId    
	 * @param memberCode    
	 * @return true:用户已经购买，false:用户未购买
	 * @throws
	 */
	public static boolean getUserGivePointsRedis(String clientId,Long memberCode, String productId){
		String key = RedisKeyUtil.cbbank_give_points_client_id_member_code_product_id(clientId,memberCode,productId);
		String flag = redis.getString(key);
		boolean result = false;
		if(EmptyChecker.isNotEmpty(flag)){
			if(StringUtils.equals(flag, "1")){
				result = true;
			}
		}
		LogCvt.info("Redis 查询用户是否赠送积分结果：{key：" + key + ",是否已赠送:" + result + "}");
		return result;
	}
	
	/**
	 *  更新用户赠送积分记录
	 * @Title: getProductPresellMaxRedis
	 * @Description: 更新用户赠送积分记录
	 * @author: share 2015年3月25日
	 * @modify: share 2015年3月25日
	 * @param clientId
	 * @param productId    
	 * @param isSuccess  true:赠送成功，false:赠送失败
	 * @return true:更新成功，false:更新失败
	 * @throws
	 */
	public static void updateUserGivePointsRedis(String clientId,Long memberCode, String productId,boolean isSuccess){
		String key = RedisKeyUtil.cbbank_give_points_client_id_member_code_product_id(clientId,memberCode,productId);
		LogCvt.info("Redis 更新用户赠送积分：{key：" + key + ",是否赠送成功:" + isSuccess + "}");
		if(getUserGivePointsRedis( clientId, memberCode, productId)){
			LogCvt.info("用户已赠送积分，不能更新积分赠送状态");
		}else{
			LogCvt.info("用户未赠送积分，更新赠送积分状态");
			redis.putString(key, String.valueOf(BooleanUtils.toInteger(isSuccess)));
		}
	}
	
	/**
	 *  查询用户是否有VIP订单
	 * @Title: getUserVipOrderRedis
	 * @Description: 查询用户VIP开通记录
	 * @author: share 2015年3月25日
	 * @modify: share 2015年3月25日
	 * @param clientId
	 * @param productId    
	 * @param memberCode    
	 * @return true:已经有VIP订单，false:没有VIP订单
	 * @throws
	 */
	public static boolean getUserVipOrderRedis(String clientId,Long memberCode){
		String key = RedisKeyUtil.cbbank_vip_order_client_id_member_code(clientId,memberCode);
		String flag = redis.getMapValue(key, "order_created");
		boolean result = false;
		if(EmptyChecker.isNotEmpty(flag)){
			if(StringUtils.equals(flag, "1")){
				result = true;
			}
		}
		LogCvt.info("Redis 查询用户是否开通VIP结果：{key：" + key + ",是否开通VIP:" + result + "}");
		return result;
	}
	
	/**
	 * 查询用户VIP订单缓存信息
	 * @param clientId
	 * @param memberCode
	 * @return  例如：{"order_created"：1,"due_date":"20151207","open_vip_count":1}
	 */
	public static Map<String,String> getUserVipOrderInfoRedis(String clientId,Long memberCode){
		OrderLogger.info("订单模块", "Redis查询用户VIP订单信息", "请求信息", new Object[]{"clientId",clientId,"memberCode",memberCode});
		String key = RedisKeyUtil.cbbank_vip_order_client_id_member_code(clientId,memberCode);
		Map<String,String> result = redis.getMap(key);
		OrderLogger.info("订单模块", "Redis查询用户VIP订单信息", "响应信息："+JSON.toJSONString(result),null);
		return result;
	}
	
	/**
	 *  更新用户VIP购买记录
	 * @Title: updateUserVIPOrderRedis
	 * @Description: 更新用户VIP购买记录
	 * @author: share 2015年3月25日
	 * @modify: share 2015年3月25日
	 * @param clientId
	 * @param productId    
	 * @param isSuccess  true:已经有VIP订单，false:没有VIP订单或者关闭
	 * @throws
	 */
	public static void updateUserVIPOrderRedis(String clientId,Long memberCode,boolean isSuccess){
		String key = RedisKeyUtil.cbbank_vip_order_client_id_member_code(clientId,memberCode);
		LogCvt.info("Redis 更新用户VIP订单状态：{key：" + key + ",是否开通:" + isSuccess + "}");
		
		Map<String,String> valueMap = new HashMap<String,String>();
		if(isSuccess){
			LogCvt.info("用户创建VIP订单：已创建");
			valueMap.put("order_created", String.valueOf(BooleanUtils.toInteger(isSuccess)));
		}else{
			LogCvt.info("用户创建VIP订单：未创建");
			valueMap.put("order_created", String.valueOf(BooleanUtils.toInteger(isSuccess)));
		}
		if(EmptyChecker.isNotEmpty(valueMap)){
			redis.putMap(key, valueMap);
		}
	}
	
	public static boolean checkUserVipRefund(String clientId,Long memberCode){
		String key = RedisKeyUtil.cbbank_vip_order_client_id_member_code(clientId,memberCode);
		Map<String,String> valueMap = redis.getMap(key);
		int openVipCount = 0;
		if(EmptyChecker.isNotEmpty(valueMap) && EmptyChecker.isNotEmpty(valueMap.get("open_vip_count"))){
			openVipCount = Integer.valueOf(valueMap.get("open_vip_count"));
		}
		if(openVipCount == 1){
			if(EmptyChecker.isNotEmpty(valueMap) && EmptyChecker.isNotEmpty(valueMap.get("due_date"))){
				Date dueDate = TimeHelper.parseDate(valueMap.get("due_date"), TimeType.yyyyMMddHHmmss);
				dueDate = TimeHelper.offsetDate(dueDate, Calendar.YEAR, -1);
				dueDate = TimeHelper.offsetDate(dueDate, Calendar.DAY_OF_YEAR, 30);
				if(new Date().before(dueDate)){
					OrderLogger.info("订单模块", "获取用户VIP订单是否可退款", "用户在体验期内，可以退款", new Object[]{"clientId",clientId,"memberCode",memberCode,"会员到期时间",valueMap.get("due_date"),"会员开通时间",JSON.toJSON(dueDate),"会员成功开通次数",openVipCount});
					return true;
				}
			}
		}
		OrderLogger.info("订单模块", "获取用户VIP订单是否可退款", "用户在不在体验期内，或者不是首次购买，不可以退款", new Object[]{"clientId",clientId,"memberCode",memberCode,"会员到期时间",EmptyChecker.isEmpty(valueMap.get("due_date"))?"":valueMap.get("due_date"),"会员成功开通次数",openVipCount});
		return false;
	} 
	
	/**
	 * 开通VIP后更新用户VIP开通成功次数和会有到期时间
	 * @param clientId 客户端ID
	 * @param memberCode 会员号
	 * @param date 会员到期时间
	 */
	public static void updateUserVIPOrderSuccessRedis(String clientId,Long memberCode,Date date){
		String key = RedisKeyUtil.cbbank_vip_order_client_id_member_code(clientId, memberCode);
		OrderLogger.info("订单模块", "开通VIP", "Redis 开通VIP后更新用户VIP开通成功次数和会有到期时间", new Object[]{"key",key,"field","due_date","due_date",DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, date)});
		
		Map<String,String> valueMap = new HashMap<String,String>();
		if (date != null) {
			LogCvt.info("用户创建VIP订单：已创建");
			valueMap.put("due_date", DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT4, date));
		}
		
		if(EmptyChecker.isNotEmpty(valueMap)){
			redis.putMap(key, valueMap);
			redis.hincrBy(key, "open_vip_count", 1L);
		}
	}
	
	/**
	 * 更新redis订单历史优惠金额
	 * @param clientId 客户端ID
	 * @param memberCode 会员号
	 * @param vipDisCount vip优惠金额
	 * @param isAdd 是否增加，true:增加  false:减少
	 */
	public static void updateVipDiscount(String clientId,Long memberCode,Integer vipDisCount,boolean isAdd){
		//cbbank:history_vip:client_id:member_code
		long st = System.currentTimeMillis();
		String historyVipKey = RedisKeyUtil.cbbank_history_vip_client_id_member_code_key(clientId,memberCode);
		LogCvt.info("redis 更新会员历史vip优惠金额  key="+historyVipKey+",原值:"+redis.getString(historyVipKey)+"，修改值:"+(isAdd?vipDisCount:(-vipDisCount)));
		Long vipDiscount = vipDisCount == null ? 0L : vipDisCount;
		if(isAdd){
			redis.incrBy(historyVipKey, vipDiscount);
		}else{
			redis.decrBy(historyVipKey, vipDiscount);
		}
		LogCvt.info("[VIP历史优惠金额]- 缓存增加会员历史vip优惠金额，耗时:"+(System.currentTimeMillis()-st));
	}
	
	/**
	 * 获取门店LOGO
	 * @param clientId
	 * @param merchantId
	 * @param outletId
	 * @return
	 */
	public static String getOutletImg(String clientId,String merchantId,String outletId){
		String key= RedisKeyUtil.cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(clientId,merchantId,outletId);
		Map<String,String> result = redis.getMap(key);//这里整个map对象获取出来，然后自己在取里面的source
		
		String img = "";
		if(EmptyChecker.isNotEmpty(result)){
			List<String> imgList = redis.hmget(key, "source");//这里直接获取Map中的source，即logo
			if(EmptyChecker.isNotEmpty(imgList)){
				img = imgList.get(0);
			}
		}
		
		return img;
	}
	
}

