package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.OrgUserRole;
import com.froad.util.RedisKeyUtil;

/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: OrgUserRoleRedis
 * @Description: 银行联合登录-帐号表Redis
 * @Author:froad-huangyihao
 * @Date: 2015年3月26日 上午10:00:49
 */
public class OrgUserRoleRedis {

	private static RedisManager redisManager = new RedisManager();
	
	/**
	 * 获取单个fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:bank_level_role:client_id:org_code:username
	 * field包含role_id
	 * @author: froad-huangyihao 2015年3月26日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param clientId 客户端id
	 * @param orgCode 机构编号
	 * @param username 登录名
	 * @param fields 需要取的field值
	 * @return value值
	 * @throws
	 */
	public static Long get_cbbank_bank_level_role_client_id_org_code_username(String clientId, String orgCode, String username, String fields){
		String key = RedisKeyUtil.cbbank_bank_level_role_client_id_org_code_username(clientId, orgCode, username);
		String roleId= redisManager.getMapValue(key, fields);
		
		return StringUtils.isBlank(roleId)?-1:Long.parseLong(roleId);
	}
	
	/**
	 * 获取所有fields值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:bank_level_role:client_id:org_code:username
	 * field包含role_id
	 * @author: froad-huangyihao 2015年3月26日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param clientId 客户端id
	 * @param orgCode 机构编号
	 * @param username 登录名
	 * @param fields 需要取的field值
	 * @return value值
	 * @throws
	 */
	public static Map<String, String> getAll_cbbank_bank_level_role_client_id_org_code_username(String clientId, String orgCode, String username){
		String key = RedisKeyUtil.cbbank_bank_level_role_client_id_org_code_username(clientId, orgCode, username);
		return redisManager.getMap(key);
	}
	
	/**
	 * 设置所有fields的value值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:bank_level_role:client_id:org_code:username
	 * field包含role_id
	 * @author: froad-huangyihao 2015年3月26日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param orgUserRoles对象
	 * @return 设置是否成功
	 * @throws
	 */
	public static Boolean set_cbbank_bank_level_role_client_id_org_code_username(OrgUserRole... orgUserRoles){
		for(OrgUserRole orgUserRole : orgUserRoles){
			String key = RedisKeyUtil.cbbank_bank_level_role_client_id_org_code_username(orgUserRole.getClientId(), orgUserRole.getOrgCode(), orgUserRole.getUsername());
			
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("role_id", orgUserRole.getRoleId()+"");
			redisManager.putMap(key, hash);
		}
		return true;
	}
	
	/**
	 * 删除银行联合登录帐号key缓存<cbbank:bank_level_role:client_id:org_code:username>
	 * @author: froad-huangyihao 2015年3月26日
	 * @modify: froad-huangyihao 2015年3月26日
	 * @param OrgUserRole
	 * @return 是否删除成功
	 */
	public static Boolean del_cbbank_bank_level_role_client_id_org_code_username(OrgUserRole... orgUserRoles){
		for(OrgUserRole orgUserRole : orgUserRoles){
			String key = RedisKeyUtil.cbbank_bank_level_role_client_id_org_code_username(orgUserRole.getClientId(), orgUserRole.getOrgCode(), orgUserRole.getUsername());
			redisManager.del(key);
		}
		return true;
	}
}
