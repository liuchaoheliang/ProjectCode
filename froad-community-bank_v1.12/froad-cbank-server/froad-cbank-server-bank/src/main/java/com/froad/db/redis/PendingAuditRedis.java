//package com.froad.db.redis;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.froad.db.redis.impl.RedisManager;
//import com.froad.util.RedisKeyUtil;
//
///**
// * Copyright © 2015 F-Road. All rights reserved.
// * @ClassName: PendingAuditRedis
// * @Description: 待审核数量redis
// * @Author: ll
// * @Date: 2015年4月3日 上午10:00:49
// */
//public class PendingAuditRedis {
//	
//	private static RedisManager redisManager = new RedisManager();
//
//
//	//待审核商户cbbank:preaudit:merchant:count:org_code
//	//待审核预售商品cbbank:preaudit:presell:count:org_code
//	//待审核团购商品cbbank:preaudit:group:count:org_code
//	//待审核名优特惠cbbank:preaudit:mingyou:count:org_code
//	//积分兑换商品cbbank:preaudit:duihuan:count:org_code
//	
//	
//	/**
//	 * 
//	 * @Title: get_cbbank_preaudit_merchant_count_org_code_value 
//	 * @Description: 获取待审核商户数目
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @modify: froad-huangyihao 2015年4月7日
//	 * @param org_code
//	 * @return
//	 * @throws
//	 */
//	public static int get_cbbank_preaudit_merchant_count_org_code(String clientId,String orgCode){
//		String key = RedisKeyUtil.cbank_preaudit_merchant_count_client_id_org_code(clientId,orgCode);
//		return getInt(redisManager.getString(key));
//	}
//	
//	/**
//	 * 
//	 * @Title: set_cbbank_preaudit_merchant_count_org_code_value 
//	 * @Description: 设置待审核商户数目
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @modify: froad-huangyihao 2015年4月7日
//	 * @param org_code
//	 * @param value
//	 * @throws
//	 */
//	public static boolean set_cbbank_preaudit_merchant_count_org_code(String clientId,String orgCode, String value){
//		String key = RedisKeyUtil.cbank_preaudit_merchant_count_client_id_org_code(clientId,orgCode);
//		return redisManager.putString(key, value).equals("OK")?true:false;
//	}
//	
//	
//	/**
//	 * 
//	 * @Title: incr_cbbank_preaudit_merchant_count_org_code 
//	 * @Description: 待审核商户数自增长
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @modify: froad-huangyihao 2015年4月7日
//	 * @param org_code
//	 * @param max
//	 * @return
//	 * @throws
//	 */
//	public static int incr_cbbank_preaudit_merchant_count_org_code(String clientId,String orgCode,Long increment){
//		String key = RedisKeyUtil.cbank_preaudit_merchant_count_client_id_org_code(clientId,orgCode);
//		return getInt(String.valueOf(redisManager.incrBy(key, increment)));
//	}
//	
//	/**
//	 * 
//	 * @Title: get_cbbank_preaudit_presell_count_org_code 
//	 * @Description: 获取待审核预售商品数目
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @modify: froad-huangyihao 2015年4月7日
//	 * @param org_code
//	 * @return
//	 * @throws
//	 */
//	public static int get_cbbank_preaudit_presell_count_org_code(String clientId,String orgCode){
//		String key = RedisKeyUtil.cbbank_preaudit_presell_count_client_id_org_code(clientId,orgCode);
//		return getInt(redisManager.getString(key));
//	}
//	
//	/**
//	 * 预售
//	 * @Title: set_cbbank_preaudit_presell_count_org_code 
//	 * @Description: 设置待审核预售商品数目
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @param clientId 
//	 * @param org_code
//	 * @param value
//	 * @param boolean
//	 * @throws
//	 */
//	public static boolean set_cbbank_preaudit_presell_count_org_code(String clientId,String orgCode, String value){
//		String key = RedisKeyUtil.cbbank_preaudit_presell_count_client_id_org_code(clientId,orgCode);
//		return redisManager.putString(key, value).equals("OK")?true:false;
//	}
//	
//	
//	/**
//	 * 
//	 * @Title: incr_cbbank_preaudit_presell_count_org_code 
//	 * @Description: 待审核预售商品自增长
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @modify: froad-huangyihao 2015年4月7日
//	 * @param org_code
//	 * @param max
//	 * @return
//	 * @throws
//	 */
//	public static int incr_cbbank_preaudit_presell_count_org_code(String clientId,String orgCode,Long increment){
//		String key = RedisKeyUtil.cbbank_preaudit_presell_count_client_id_org_code(clientId,orgCode);
//		return getInt(String.valueOf(redisManager.incrBy(key, increment)));
//	}
//	
//	/**
//	 * 
//	 * @Title: get_cbbank_preaudit_group_count_org_code 
//	 * @Description: 获取待审核团购商品数目
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @modify: froad-huangyihao 2015年4月7日
//	 * @param org_code
//	 * @return
//	 * @throws
//	 */
//	public static int get_cbbank_preaudit_group_count_org_code(String clientId,String orgCode){
//		String key = RedisKeyUtil.cbbank_preaudit_group_count_client_id_org_code(clientId,orgCode);
//		return getInt(redisManager.getString(key));
//	}
//	
//	/**
//	 * 团购
//	 * @Title: set_cbbank_preaudit_presell_count_org_code 
//	 * @Description: 设置待审核团购商品数目
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @param clientId 
//	 * @param org_code
//	 * @param value
//	 * @param boolean
//	 * @throws
//	 */
//	public static boolean set_cbbank_preaudit_group_count_org_code(String clientId,String orgCode, String value){
//		String key = RedisKeyUtil.cbbank_preaudit_group_count_client_id_org_code(clientId,orgCode);
//		return redisManager.putString(key, value).equals("OK")?true:false;
//	}
//	
//	
//	/**
//	 * 
//	 * @Title: incr_cbbank_preaudit_presell_count_org_code 
//	 * @Description: 待审核团购商品自增长
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @modify: froad-huangyihao 2015年4月7日
//	 * @param org_code
//	 * @param max
//	 * @return
//	 * @throws
//	 */
//	public static int incr_cbbank_preaudit_group_count_org_code(String clientId,String orgCode,Long increment){
//		String key = RedisKeyUtil.cbbank_preaudit_group_count_client_id_org_code(clientId,orgCode);
//		return getInt(String.valueOf(redisManager.incrBy(key, increment)));
//	}
//	
//	/**
//	 * 待审核名优特惠数量cbbank:preaudit:mingyou:count:org_code
//	 * @param orgCode 机构编号
//	 * @return
//	 */
//	public static int get_cbbank_preaudit_mingyou_count_org_code(String clientId,String orgCode) {
//		String key = RedisKeyUtil.cbbank_preaudit_mingyou_count_client_id_org_code(clientId,orgCode);
//		return getInt(redisManager.getString(key));
//	}
//
//	/**
//	 * 名优特惠
//	 * @Title: set_cbbank_preaudit_mingyou_count_org_code 
//	 * @Description: 设置待审核名优特惠商品数目
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @param clientId 
//	 * @param org_code
//	 * @param value
//	 * @param boolean
//	 * @throws
//	 */
//	public static boolean set_cbbank_preaudit_mingyou_count_org_code(String clientId,String orgCode, String value){
//		String key = RedisKeyUtil.cbbank_preaudit_mingyou_count_client_id_org_code(clientId,orgCode);
//		return redisManager.putString(key, value).equals("OK")?true:false;
//	}
//	
//	/**
//	 * 待审核名优特惠数量cbbank:preaudit:mingyou:count:org_code
//	 * @param orgCode 机构编号
//	 * @return
//	 */
//	public static int incr_cbbank_preaudit_mingyou_count_org_code(String clientId,String orgCode,Long increment) {
//		String key = RedisKeyUtil.cbbank_preaudit_mingyou_count_client_id_org_code(clientId,orgCode);
//		return getInt(String.valueOf(redisManager.incrBy(key, increment)));
//	}
//	
//	
//	
//	/**
//	 *  待审核积分兑换商品数量cbbank:preaudit:duihuan:count:org_code
//	 * @param orgCode 机构编号
//	 * @return
//	 */
//	public static int get_cbbank_preaudit_duihuan_count_org_code(String clientId,String orgCode) {
//		String key = RedisKeyUtil.cbbank_preaudit_duihuan_count_client_id_org_code(clientId,orgCode);
//		return getInt(redisManager.getString(key));
//	}
//
//	/**
//	 * 兑换
//	 * @Title: set_cbbank_preaudit_duihuan_count_org_code 
//	 * @Description: 设置待审核兑换商品数目
//	 * @author: froad-huangyihao 2015年4月7日
//	 * @param clientId 
//	 * @param org_code
//	 * @param value
//	 * @param boolean
//	 * @throws
//	 */
//	public static boolean set_cbbank_preaudit_duihuan_count_org_code(String clientId,String orgCode, String value){
//		String key = RedisKeyUtil.cbbank_preaudit_duihuan_count_client_id_org_code(clientId,orgCode);
//		return redisManager.putString(key, value).equals("OK")?true:false;
//	}
//	
//
//	/**
//	 * 待审核积分兑换商品数量cbbank:preaudit:duihuan:count:org_code
//	 * @param orgCode 机构编号
//	 * @return
//	 */
//	public static int incr_cbbank_preaudit_duihuan_count_org_code(String clientId,String orgCode,Long increment) {
//		String key = RedisKeyUtil.cbbank_preaudit_duihuan_count_client_id_org_code(clientId,orgCode);
//		return getInt(String.valueOf(redisManager.incrBy(key, increment)));
//	}
//	
//	/**
//	 * int转换
//	 * @param key
//	 * @return 若没值返回-1
//	 */
//	private static int getInt(String value) {
//		if (StringUtils.isNotEmpty(value)) {
//			return Integer.parseInt(value);
//		} else {
//			return -1;
//		}
//	}
//	
//
//}
