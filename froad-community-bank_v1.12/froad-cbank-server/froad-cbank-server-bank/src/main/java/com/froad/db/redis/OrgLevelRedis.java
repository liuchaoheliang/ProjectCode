package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.OrgLevel;
import com.froad.util.RedisKeyUtil;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: OrgLevelRedis
 * @Description: 银行联合登录-机构级别角色表Redis
 * @Author:froad-huangyihao
 * @Date: 2015年3月26日 上午10:00:49
 */
public class OrgLevelRedis {
	
	private static RedisManager redisManager = new RedisManager();
	
	/**
	 * 获取单个fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:bank_level_role:client_id:org_level
	 * field包含role_id
	 * @author: froad-huangyihao 2015年3月26日
	 * @modify: froad-ll 2015年3月28日  改为取单个roleId
	 * @param client_id 客户端id
	 * @param org_level 机构级别
	 * @param field 取值的name，String类型
	 * @param 缓存中存储的value值为role_id角色id
	 * @return
	 * @throws
	 */
	public static Long get_cbbank_bank_level_role_client_id_org_level(String client_id, String org_level,String filed){
		String key = RedisKeyUtil.cbbank_bank_level_role_client_id_org_level(client_id, org_level);
		String roleId=redisManager.getMapValue(key, filed);
		
		return StringUtils.isBlank(roleId)?-1:Long.parseLong(roleId);
	}
	
	/**
	 * 获取所有fields值
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:bank_level_role:client_id:org_level
	 * field包含role_id
	 * @author: froad-huangyihao 2015年3月26日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param client_id 客户端id
	 * @param org_level 机构级别
	 * @return Map<String, String>
	 * @throws
	 */
	public static Map<String, String> getAll_cbbank_bank_level_role_client_id_org_level(String client_id, String org_level){
		String key = RedisKeyUtil.cbbank_bank_level_role_client_id_org_level(client_id, org_level);
		return redisManager.getMap(key);
	}
	
	/**
	 * 设置所有fields的value值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:bank_level_role:client_id:org_level
	 * field包含role_id
	 * @author: froad-huangyihao 2015年3月26日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param OrgLevel对象
	 * @return 是否设置成功
	 * @throws
	 */
	public static Boolean set_cbbank_bank_level_role_client_id_org_level(OrgLevel... orgLevels){
		for(OrgLevel orgLevel : orgLevels){
			String key = RedisKeyUtil.cbbank_bank_level_role_client_id_org_level(orgLevel.getClientId(), orgLevel.getOrgLevel());
			
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("role_id", orgLevel.getRoleId()+"");
			redisManager.putMap(key, hash);
		}
		return true;
	}
	
	/**
	 * 删除客户端key缓存<key:cbbank:bank_level_role:client_id:org_level>
	 * @param OrgLevel
	 * @return 是否删除成功
	 */
	public static Boolean del_cbbank_bank_level_role_client_id_org_level(OrgLevel... orgLevels){
		for(OrgLevel orgLevel : orgLevels){
			String key = RedisKeyUtil.cbbank_bank_level_role_client_id_org_level(orgLevel.getClientId(), orgLevel.getOrgLevel());
			redisManager.del(key);
		}
		return true;
	}
	
	
}
