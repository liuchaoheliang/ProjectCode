package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.po.SeckillProduct;
import com.froad.util.RedisKeyUtil;

/**
 * 秒杀业务Redis操作
 * 
 * @author wangzhangxu
 * @date 2015年4月15日 下午5:48:04
 * @version v1.0
 */
public class SeckillRedis {

	private static RedisService redis = new RedisManager();
	
	/**
	 * 获取秒杀受理结果
	 * 
	 * @param acceptOrderId 受理订单号
	 * 
	 * @return Map<String, String> 秒杀受理结果
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月15日 下午5:48:04
	 */
	public static Map<String, String> get_cbbank_seckill_res_req_id(String acceptOrderId){
		String key = RedisKeyUtil.cbbank_seckill_res_req_id(acceptOrderId);
		return redis.getMap(key);
	}
	
	/**
	 * 初始化秒杀受理结果
	 * 
	 * @param resultFlag 结果标识（0-等待中，1-下单成功，2-下单失败）
	 * @param orderId 订单号
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月15日 下午5:48:04
	 */
	public static boolean set_cbbank_seckill_res_req_id(String acceptOrderId, String resultFlag){
		String key = RedisKeyUtil.cbbank_seckill_res_req_id(acceptOrderId);
		Map<String, String> valueMap = new HashMap<String, String>();
		valueMap.put("result_flag", resultFlag);
		String value = redis.putMap(key, valueMap);
		return "OK".equals(value);
	}
	
	/**
	 * 保存用户参与秒杀
	 * 
	 * @param memberCode 用户代码
	 * @param productId 产品ID
	 * 
	 * @param acceptOrderId 受理订单号
	 * 
	 * @return boolean
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月15日 下午5:48:04
	 */
	public static boolean set_cbank_seckill_join_member_code_product_id(String memberCode, String productId, String acceptOrderId){
		String key = RedisKeyUtil.cbbank_seckill_join_member_code_product_id(memberCode, productId);
		String result = redis.putString(key, acceptOrderId);
		return "OK".equals(result);
	}
	
	/**
	 * 获取用户是否参与了秒杀
	 * 
	 * @param memberCode 用户代码
	 * @param productId 产品ID
	 * 
	 * @return acceptOrderId 受理订单号
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月15日 下午5:48:04
	 */
	public static String get_cbbank_seckill_join_member_code_product_id(String memberCode, String productId){
		String key = RedisKeyUtil.cbbank_seckill_join_member_code_product_id(memberCode, productId);
		return redis.getString(key);
	}
	
	/**
	 * 获取秒杀商品信息
	 * 
	 * @param clientId 用户代码
	 * @param productId 产品ID
	 * 
	 * @return String 秒杀商品信息
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月15日 下午5:48:04
	 */
	public static Map<String, String> get_cbbank_seckill_product_client_id_product_id(String clientId, String productId){
		String key = RedisKeyUtil.cbbank_seckill_product_client_id_product_id(clientId, productId);
		Map<String, String> mp = redis.getMap(key);
		if (mp != null) {
			String storeKey = RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(clientId, productId);
			String store = redis.getString(storeKey);
			if (StringUtils.isEmpty(store)) {
				store = "0";
			}
			mp.put("sec_store", store);
			LogCvt.debug("秒杀商品库存: " + storeKey + " --> " + store);
		}
		return mp;
	}
	
	/**
	 * 获取基本商品信息
	 * 
	 * @param clientId 用户代码
	 * @param merchantId 商户ID
	 * @param productId 产品ID
	 * 
	 * @return String 基本商品信息
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月15日 下午5:48:04
	 */
	public static Map<String, String> get_cbbank_product_client_id_merchant_id_product_id(String clientId, String merchantId, String productId){
		String key = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(clientId, merchantId, productId);
		return redis.getMap(key);
	}
	
	/**
	 * 获取秒杀商品信息销售数量
	 * 
	 * @param clientId 用户代码
	 * @param productId 产品ID
	 * 
	 * @return int 秒杀商品销售数量
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月15日 下午5:48:04
	 */
	public static int get_cbbank_seckill_product_soldcnt_client_id_product_id(String clientId, String productId){
		String key = RedisKeyUtil.cbbank_seckill_product_soldcnt_client_id_product_id(clientId, productId);
		String store = redis.getString(key);
		if (StringUtils.isEmpty(store)) {
			return 0;
		}
		return Integer.parseInt(store);
	}
	
	/**
	 * 减去秒杀商品信息库存
	 * 
	 * @param clientId 用户代码
	 * @param productId 产品ID
	 * @param buyNum 购买数量
	 * @param store 商品库存
	 * 
	 * @return int 已售卖数量（大于0成功）
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月15日 下午5:48:04
	 */
	public static long incr_cbbank_seckill_product_soldcnt_client_id_product_id(String clientId, String productId, int buyNum, int store){
		String key = RedisKeyUtil.cbbank_seckill_product_soldcnt_client_id_product_id(clientId, productId);
		long soldCount = redis.incrBy(key, new Long(buyNum));
		if (soldCount == -1) {
			return soldCount;
		}
		
		if (soldCount > 0 && soldCount > store) {
			// 回滚操作
			soldCount = redis.decrBy(key, new Long(buyNum));
			if (soldCount == -1) {
				return soldCount;
			}
			
			// 库存不足
			soldCount = -100;
		}
		
		return soldCount;
	}
	
	/**
	 * 获取已用户购买秒杀产品的数量
	 * 
	 * @param memberCode 用户唯一标识
	 * @param productId 产品ID
	 * 
	 * @return int 已购买数量
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月20日 下午3:23:18
	 */
	public static int get_cbbank_seckill_memcnt_member_code_product_id(Long memberCode, String productId, String endDateYYMMdd){
		String key = RedisKeyUtil.cbbank_seckill_memcnt_member_code_product_id_end_date(memberCode + "", productId, endDateYYMMdd);
		String value = redis.getString(key);
		if (StringUtils.isEmpty(value)) {
			return 0;
		}
		return Integer.parseInt(value);
	}
	
	/**
	 * 记录用户本次购买秒杀产品的数量
	 * 
	 * @param memberCode 用户唯一标识
	 * @param productId 产品ID
	 * @param buyNum 购买数量
	 * @param buyLimit 用户的购买额度
	 * 
	 * @return int 已购买数量（大于0成功）
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月20日 下午3:23:18
	 */
	public static long incr_cbbank_seckill_memcnt_member_code_product_id(Long memberCode, String productId, String endDateYYMMdd, int buyNum, int buyLimit){
		String key = RedisKeyUtil.cbbank_seckill_memcnt_member_code_product_id_end_date(memberCode + "", productId, endDateYYMMdd);
		long buyCount = redis.incrBy(key, new Long(buyNum));
		if (buyCount == -1) {
			return buyCount;
		}
		
		if (buyCount > 0 && buyCount > buyLimit) {
			// 回滚操作
			buyCount = redis.decrBy(key, new Long(buyNum));
			if (buyCount == -1) {
				return buyCount;
			}
			
			// 额度不足
			buyCount = -100;
		}
		
		return buyCount;
	}
	
	/**
	 * 获取队列的数量
	 */
	public static long get_cbbank_seckill_product_queue_count(){
		String key = RedisKeyUtil.cbbank_seckill_product_queue_count();
		String value = redis.getString(key);
		if (StringUtils.isEmpty(value)) {
			return 0l;
		}
		return Long.parseLong(value);
	}
	
	/**
	 * 增加队列的数量，增量1
	 */
	public static long incr_cbbank_seckill_product_queue_count(){
		String key = RedisKeyUtil.cbbank_seckill_product_queue_count();
		return redis.incrBy(key, 1L);
	}
	
	public static void testSetProduct(SeckillProduct product){
		String key = RedisKeyUtil.cbbank_seckill_product_client_id_product_id(product.getClientId(), product.getProductId());
		System.out.println(key);
		String result = redis.putMap(key, product.toMap());
		System.out.println(result);
	}
	
}
