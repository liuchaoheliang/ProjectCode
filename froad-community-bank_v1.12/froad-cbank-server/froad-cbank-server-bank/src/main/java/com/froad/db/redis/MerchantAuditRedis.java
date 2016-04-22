package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;
import com.froad.db.redis.impl.RedisManager;
import com.froad.po.ClientMerchantAudit;
import com.froad.util.RedisKeyUtil;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: MerchantAuditRedis
 * @Description: 商户审核配置Redis
 * @Author:froad-ll
 * @Date: 2015年6月10日 上午11:00:49
 */
public class MerchantAuditRedis {
	
	private static RedisManager redisManager = new RedisManager();
	
	/**
	 * 获取单个fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:merchant_audit:client_id:org_level:type
	 * field包含client_id/org_level/type/start_org_level/end_org_level
	 * @author: froad-ll 2015年6月10日
	 * @param client_id 客户端id
	 * @param org_level 机构级别
	 * @param type 1-审核 2-商户重置密码
	 * @return field 取值的name，String类型
	 * @throws
	 */
	public static String get_cbbank_merchant_audit_client_id_org_level_type(String client_id, String org_level,String type,String filed){
		String key = RedisKeyUtil.cbbank_merchant_audit_client_id_org_level_type(client_id, org_level,type);
		return redisManager.getMapValue(key, filed);
	}
	
	/**
	 * 获取所有fields值
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:merchant_audit:client_id:org_level:type
	 * field包含client_id/org_level/type/start_org_level/end_org_level
	 * @author: froad-ll 2015年6月10日
	 * @param client_id 客户端id
	 * @param org_level 机构级别
	 * @param type 1-审核 2-商户重置密码
	 * @return Map<String, String>
	 * @throws
	 */
	public static Map<String, String> getAll_cbbank_merchant_audit_client_id_org_level_type(String client_id, String org_level,String type){
		String key = RedisKeyUtil.cbbank_merchant_audit_client_id_org_level_type(client_id, org_level,type);
		return redisManager.getMap(key);
	}
	
	/**
	 * 设置所有fields的value值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:merchant_audit:client_id:org_level:type
	 * field包含client_id/org_level/type/start_org_level/end_org_level
	 * @author: froad-ll 2015年6月10日
	 * @param ClientMerchantAudit对象
	 * @return 是否设置成功
	 * @throws
	 */
	public static Boolean set_cbbank_merchant_audit_client_id_org_level_type(ClientMerchantAudit... clientMerchantAudits){
		for(ClientMerchantAudit clientMerchantAudit : clientMerchantAudits){
			String key = RedisKeyUtil.cbbank_merchant_audit_client_id_org_level_type(clientMerchantAudit.getClientId(), clientMerchantAudit.getOrgLevel(),clientMerchantAudit.getType());
			
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("client_id", clientMerchantAudit.getClientId());
			hash.put("org_level", clientMerchantAudit.getOrgLevel());
			hash.put("type", clientMerchantAudit.getType());
			hash.put("start_org_level", clientMerchantAudit.getStartOrgLevel());
			hash.put("end_org_level", clientMerchantAudit.getEndOrgLevel());
			
			redisManager.putMap(key, hash);
		}
		return true;
	}
	
	/**
	 * 删除客户端key缓存<key:cbbank:merchant_audit:client_id:org_level:type>
	 * @param ClientMerchantAudit 
	 * @return 是否删除成功
	 */
	public static Boolean del_cbbank_merchant_audit_client_id_org_level_type(ClientMerchantAudit... clientMerchantAudits){
		for(ClientMerchantAudit clientMerchantAudit : clientMerchantAudits){
			String key = RedisKeyUtil.cbbank_merchant_audit_client_id_org_level_type(clientMerchantAudit.getClientId(), clientMerchantAudit.getOrgLevel(),clientMerchantAudit.getType());
			redisManager.del(key);
		}
		return true;
	}
	
	
}
