package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import com.froad.db.redis.impl.RedisManager;
import com.froad.po.Client;
import com.froad.util.RedisKeyUtil;



/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: ClientRedis
 * @Description: 客户端管理Redis
 * @Author: ll
 * @Date: 2015年3月20日 上午10:00:49
 */
public class ClientRedis {

	private static RedisManager redisManager = new RedisManager();
	
	/**
	 * 获取单个fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client:client_id
	 * field包含 name/point_partner_no/openapi_partner_no/appkey/appsecret/order_display/return_url/bank_name/qr_logo/bank_type
	 * @author ll 20150320
	 * @param client_id客户端id
	 * @param fields 要取的field的某一名称
	 * @return String value值
	 * @throws
	 */
	public static String get_cbbank_client_client_id(String client_id, String fields) {
		String key = RedisKeyUtil.cbbank_client_client_id(client_id) ;
		return redisManager.getMapValue(key, fields);
	}
	
	/**
	 * 获取所有fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client:client_id
	 * field包含 name/point_partner_no/openapi_partner_no/appkey/appsecret/order_display/return_url/bank_name/qr_logo/bank_type
	 * @author ll 20150320
	 * @param client_id客户端id
	 * @return Map<String,String>
	 * @throws
	 */
	public static Map<String,String> getAll_cbbank_client_client_id(String client_id) {
		String key = RedisKeyUtil.cbbank_client_client_id(client_id) ;
		return redisManager.getMap(key);
	}
	
	
	/**
	 * 设置所有fields的value值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client:client_id
	 * field包含 name/point_partner_no/openapi_partner_no/appkey/appsecret/order_display/return_url/bank_name/qr_logo/bank_type
	 * @author ll 20150320
	 * @param Client客户端对象
	 * @return boolean是否设置成功
	 * @throws
	 */
	public static Boolean set_cbbank_client_client_id(Client... clients) {
		for (Client client : clients) {
			/* 缓存该客户端下信息 */
			String key = RedisKeyUtil.cbbank_client_client_id(client.getClientId()) ;

			Map<String, String> hash = new HashMap<String, String>();
			hash.put("name", client.getName());//客户端Id
			hash.put("point_partner_no", client.getPointPartnerNo());//积分平台商户号
			hash.put("openapi_partner_no", client.getOpenapiPartnerNo());//支付平台商户号
			hash.put("appkey", ObjectUtils.toString(client.getAppkey(), ""));//手机客户端appkey
			hash.put("appsecret", ObjectUtils.toString(client.getAppsecret() , ""));//私钥
			hash.put("order_display", client.getOrderDisplay());//订单显示名
			hash.put("return_url", ObjectUtils.toString(client.getReturnUrl(),""));//支付结果通知地址
			hash.put("bank_name", ObjectUtils.toString(client.getBankName(),""));//银行名称
			hash.put("qr_logo", ObjectUtils.toString(client.getQrLogo(), ""));//银行logo图片地址
			hash.put("bank_type", ObjectUtils.toString(client.getBankType(),""));//银行类型
			hash.put("bank_id", ObjectUtils.toString(client.getBankId(),""));//积分平台银行代码
			hash.put("settle_pay_org", ObjectUtils.toString(client.getSettlePayOrg(),""));//结算组织机构代码
			hash.put("bank_org", ObjectUtils.toString(client.getBankOrg(),""));//vip所属银行标签
			hash.put("bank_ZH", ObjectUtils.toString(client.getBankZH(),""));//银行组号
			
			redisManager.putMap(key, hash);
		}
		return true;

	}
	
	
	/**
	 * 删除客户端key缓存<key:cbbank:client:client_id>
	 * @param Client
	 * @return 是否删除成功
	 */
	public static boolean del_cbbank_client_client_id(Client... clients) {
		for (Client client : clients) {
			String key = RedisKeyUtil.cbbank_client_client_id(client.getClientId()) ;
			
			redisManager.del(key);
		}
		return true;
	}
	
	
	
	
	
	
	
	
	
}
