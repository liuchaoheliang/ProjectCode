package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.ClientProductAudit;
import com.froad.util.RedisKeyUtil;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: ProductAuditRedis
 * @Description: 商品审核配置Redis
 * @Author:froad-ll
 * @Date: 2015年6月10日 上午11:00:49
 */
public class ProductAuditRedis {
	
	private static RedisManager redisManager = new RedisManager();
	
	/**
	 * 获取单个fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:product_audit:client_id:org_level:product_type
	 * field包含client_id/org_level/product_type/start_org_level/end_org_level
	 * @author: froad-ll 2015年6月10日
	 * @param client_id 客户端id
	 * @param org_level 机构级别
	 * @param productType 商品类型（1团购 2预售 3名优特惠 4在线积分兑换 5网点礼品）
	 * @return field 取值的name，String类型
	 * @throws
	 */
	public static String get_cbbank_product_audit_client_id_org_level_product_type(String client_id, String org_level,String product_type,String filed){
		String key = RedisKeyUtil.cbbank_product_audit_client_id_org_level_product_type(client_id, org_level,product_type);
		return redisManager.getMapValue(key, filed);
	}
	
	/**
	 * 获取所有fields值
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:product_audit:client_id:org_level:product_type
	 * field包含client_id/org_level/product_type/start_org_level/end_org_level
	 * @author: froad-ll 2015年6月10日
	 * @param client_id 客户端id
	 * @param org_level 机构级别
	 * @param productType 商品类型（1团购 2预售 3名优特惠 4在线积分兑换 5网点礼品）
	 * @return Map<String, String>
	 * @throws
	 */
	public static Map<String, String> getAll_cbbank_product_audit_client_id_org_level_product_type(String client_id, String org_level,String product_type){
		String key = RedisKeyUtil.cbbank_product_audit_client_id_org_level_product_type(client_id, org_level,product_type);
		return redisManager.getMap(key);
	}
	
	/**
	 * 设置所有fields的value值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:product_audit:client_id:org_level:product_type
	 * field包含client_id/org_level/product_type/start_org_level/end_org_level
	 * @author: froad-ll 2015年6月10日
	 * @param ClientMerchantAudit对象
	 * @return 是否设置成功
	 * @throws
	 */
	public static Boolean set_cbbank_product_audit_client_id_org_level_product_type(ClientProductAudit... clientProductAudits){
		for(ClientProductAudit clientProductAudit : clientProductAudits){
			String key = RedisKeyUtil.cbbank_product_audit_client_id_org_level_product_type(clientProductAudit.getClientId(), clientProductAudit.getOrgLevel(),clientProductAudit.getProductType());
			
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("client_id", clientProductAudit.getClientId());
			hash.put("org_level", clientProductAudit.getOrgLevel());
			hash.put("product_type", clientProductAudit.getProductType());
			hash.put("start_org_level", clientProductAudit.getStartOrgLevel());
			hash.put("end_org_level", clientProductAudit.getEndOrgLevel());
			
			redisManager.putMap(key, hash);
		}
		return true;
	}
	
	/**
	 * 删除客户端key缓存<key:cbbank:product_audit:client_id:org_level:product_type>
	 * @param ClientMerchantAudit 
	 * @return 是否删除成功
	 */
	public static Boolean del_cbbank_product_audit_client_id_org_level_product_type(ClientProductAudit... clientProductAudits){
		for(ClientProductAudit clientProductAudit : clientProductAudits){
			String key = RedisKeyUtil.cbbank_product_audit_client_id_org_level_product_type(clientProductAudit.getClientId(), clientProductAudit.getOrgLevel(),clientProductAudit.getProductType());
			redisManager.del(key);
		}
		return true;
	}
	
	
}
