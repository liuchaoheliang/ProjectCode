package com.froad.db.redis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import com.froad.db.redis.impl.RedisManager;
import com.froad.po.ClientPaymentChannel;
import com.froad.util.RedisKeyUtil;



/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: ClientPaymentChannelRedis
 * @Description: 客户端支付渠道管理Redis
 * @Author: ll
 * @Date: 2015年3月20日 上午10:00:49
 */
public class ClientPaymentChannelRedis {
	
	private static RedisManager redisManager = new RedisManager();
	
	/**
	 * 获取单个fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client_channel:client_id:payment_channel_id
	 * field包含name/full_name/type/ico_url/payment_org_no/is_froad_check_token/point_rate
	 * @author ll 20150320
	 * @param client_id 客户端id
	 * @param payment_channel_id 支付渠道id
	 * @param fields 需要取的field值
	 * @return String
	 * @throws
	 */
	public static String get_cbbank_client_channel_client_id_payment_channel_id(String client_id,String payment_channel_id, String fields) {
		String key = RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(client_id,payment_channel_id) ;
		return redisManager.getMapValue(key, fields);
	}
	
	/**
	 * 获取所有fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client_channel:client_id:payment_channel_id
	 * field包含name/full_name/type/ico_url/payment_org_no/is_froad_check_token/point_rate
	 * @author ll 20150320
	 * @param client_id 客户端id
	 * @param payment_channel_id 支付渠道id
	 * @return String
	 * @throws
	 */
	public static Map<String, String> getAll_cbbank_client_channel_client_id_payment_channel_id(String client_id,String payment_channel_id){
		String key = RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(client_id,payment_channel_id) ;
		return redisManager.getMap(key);
	}
	
	
	/**
	 * 设置所有fields的value值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client_channel:client_id:payment_channel_id
	 * field包含name/full_name/type/ico_url/payment_org_no/is_froad_check_token/point_rate
	 * @author ll 20150320
	 * @param ClientPaymentChannel支付渠道对象 
	 * @return boolean 是否操作成功
	 * @throws
	 */
	public static Boolean set_cbbank_client_channel_client_id_payment_channel_id(ClientPaymentChannel... clientPaymentChannels) {
		for (ClientPaymentChannel clientPaymentChannel : clientPaymentChannels) {
			/* 缓存该客户端下支付渠道信息 */
			String key = RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(clientPaymentChannel.getClientId(), clientPaymentChannel.getPaymentChannelId()) ;

			Map<String, String> hash = new HashMap<String, String>();
			hash.put("name", clientPaymentChannel.getName());//资金机构名称
			hash.put("full_name", clientPaymentChannel.getFullName());//资金机构全名
			hash.put("type", clientPaymentChannel.getType());//渠道类型
			hash.put("ico_url", ObjectUtils.toString(clientPaymentChannel.getIcoUrl(),""));//支付方式图标
			hash.put("payment_org_no", ObjectUtils.toString(clientPaymentChannel.getPaymentOrgNo(), ""));//资金机构代号（⽀支付系统）
			hash.put("is_froad_check_token", BooleanUtils.toString(clientPaymentChannel.getIsFroadCheckToken(), "1", "0",""));//是否方付通验码
			hash.put("point_rate", clientPaymentChannel.getPointRate());//积分兑换比例(实时获取)

			redisManager.putMap(key, hash);
			
		}
		return true;

	}
	
	
	
	/**
	 * 获取Set集合中的payment_channel_id
	 * 
	 * @Description:使用Redis的集合结构存储SADD key value
	 * key为cbbank:client_channels:client_id
	 * value为payment_channel_id
	 * @author ll 20150323
	 * @param client_id 客户端id
	 * @return Set<String> 返回类型
	 * @throws
	 */
	public static Set<String> get_cbbank_client_channels_client_id(String client_id) {
		String key = RedisKeyUtil.cbbank_client_channels_client_id(client_id) ;
		return redisManager.getSet(key);
	}
	
	
	
	/**
	 * 设置Set集合中的payment_channel_id
	 * 
	 * @Description:使用Redis的集合结构存储SADD key value
	 * key为cbbank:client_channels:client_id
	 * value为payment_channel_id
	 * @author ll 20150323
	 * @param clientPaymentChannels支付渠道对象
	 * @return boolean是否操作成功
	 * @throws
	 */
	public static Boolean set_cbbank_client_channels_client_id(ClientPaymentChannel... clientPaymentChannels) {
		for (ClientPaymentChannel clientPaymentChannel : clientPaymentChannels) {
			/* 缓存该客户端下支付渠道Set集合信息 */
			String key = RedisKeyUtil.cbbank_client_channels_client_id(clientPaymentChannel.getClientId()) ;
			
			Set<String> valueSet = new HashSet<String>();
			valueSet.add(String.valueOf(clientPaymentChannel.getPaymentChannelId()));
			redisManager.putSet(key, valueSet);
		}
		return true;

	}
	
	
	/**
	 * 删除key缓存<key:cbbank:client_channel:client_id:payment_channel_id>
	 * @param merchantAccounts
	 * @return
	 */
	public static boolean del_cbbank_client_channel_client_id_payment_channel_id(ClientPaymentChannel... clientPaymentChannels) {
		for (ClientPaymentChannel clientPaymentChannel : clientPaymentChannels) {
			String key = RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(clientPaymentChannel.getClientId(), clientPaymentChannel.getPaymentChannelId()) ;
			redisManager.del(key);
		}
		return true;
	}
	
	/**
	 * 删除key缓存<key:cbbank:client_channel:client_id> Set集合
	 * @param merchantAccounts
	 * @return
	 */
	public static boolean srem_cbbank_client_channels_client_id(ClientPaymentChannel... clientPaymentChannels) {
		
		Long result=0l;
		for (ClientPaymentChannel clientPaymentChannel : clientPaymentChannels) {
			String key =RedisKeyUtil.cbbank_client_channels_client_id(clientPaymentChannel.getClientId()) ;
			//移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略
			result=redisManager.srem(key, clientPaymentChannel.getPaymentChannelId());
		}
		return result==0l?true:false;
		
		
		
	}
	
	
}
