package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import com.froad.db.redis.impl.RedisManager;
import com.froad.po.BankOperator;
import com.froad.util.RedisKeyUtil;



/**
 * Copyright © 2015 F-Road. All rights reserved.
 * @ClassName: ClientRedis
 * @Description: 银行用户管理Redis
 * @Author: ll
 * @Date: 2015年3月20日 上午10:00:49
 */
public class BankOperatorRedis {

	private static RedisManager redisManager = new RedisManager();
	
	/**
	 * 获取单个fields值
	 * 
	 * @Description:<使用Redis的哈希结构存储HSET key field value
	 *	key为cbbank:bank_user:client_id:user_id
	 *	field包含username/password/org_code/status/is_reset/name/role_id/department/position>
	 * @author ll 20150321
	 * @param client_id 客户端id
	 * @param user_id 用户编号
	 * @param fields 要取的field的某一名称
	 * @return value值
	 * @throws
	 */
	public static String get_cbbank_bank_user_client_id_user_id(String client_id,Long user_id, String fields) {
		String key = RedisKeyUtil.cbbank_bank_user_client_id_user_id(client_id,user_id) ;
		return redisManager.getMapValue(key, fields);
	}
	
	
	/**
	 * 获取所有fields值
	 * 
	 * @Description:<使用Redis的哈希结构存储HSET key field value
	 *	key为cbbank:bank_user:client_id:user_id
	 *	field包含username/password/org_code/status/is_reset/name/role_id/department/position>
	 * @author ll 20150321
	 * @param client_id 客户端id
	 * @param user_id 用户编号
	 * @return Map<String,String> 所有value值
	 * @throws
	 */
	public static Map<String,String> getAll_cbbank_bank_user_client_id_user_id(String client_id,Long user_id) {
		String key = RedisKeyUtil.cbbank_bank_user_client_id_user_id(client_id,user_id) ;
		return redisManager.getMap(key);
	}
	
	
	/**
	 * 设置所有fields的value值
	 * 
	 * @Description:<使用Redis的哈希结构存储HSET key field value
	 *	key为cbbank:bank_user:client_id:user_id
	 *	field包含username/password/org_code/status/is_reset/name/role_id/department/position>
	 * @author ll 20150321
	 * @param BankOperator 银行用户对象
	 * @return Boolean 操作是否成功
	 * @throws
	 */
	public static Boolean set_cbbank_bank_user_client_id_user_id(BankOperator... bankOperators) {
		for (BankOperator bankOperator : bankOperators) {
			/* 缓存该客户端下信息 */
			String key = RedisKeyUtil.cbbank_bank_user_client_id_user_id(bankOperator.getClientId(),bankOperator.getId()) ;

			Map<String, String> hash = new HashMap<String, String>();
			hash.put("username", bankOperator.getUsername());//登录名
			hash.put("password", bankOperator.getPassword());//登录密码
			hash.put("org_code", bankOperator.getOrgCode());//所属机构编号
			//redis中对bool类型存0和1
			hash.put("status", BooleanUtils.toString(bankOperator.getStatus(), "1", "0", ""));//0-不可用 1-可用
			hash.put("is_reset", BooleanUtils.toString(bankOperator.getIsReset(), "1", "0", ""));//密码是否重置 1-是 0-否
			hash.put("name", ObjectUtils.toString(bankOperator.getName() , ""));//操作员姓名
			hash.put("role_id", ObjectUtils.toString(bankOperator.getRoleId() , ""));//角色ID
			hash.put("department", ObjectUtils.toString(bankOperator.getDepartment() , ""));//部门
			hash.put("position", ObjectUtils.toString(bankOperator.getPosition() , ""));//职位
			hash.put("remark", ObjectUtils.toString(bankOperator.getRemark() , ""));//备注
			
			//hash.put("type", ObjectUtils.toString(bankOperator.getRoleId() , ""));// 用户类型：1、平台用户 2、银行用户
			//hash.put("last_login_ip", ObjectUtils.toString(bankOperator.getLastLoginIp() , ""));//最后登录ip
			//hash.put("last_login_time", ObjectUtils.toString(bankOperator.getLastLoginTime() , ""));//最后登录时间
			
			redisManager.putMap(key, hash);
		}
		return true;

	}
	
	
	/**
	 * 根据client_id和user_id获取value值
	 * 
	 * @Description:<key为cbbank:bank_user_login:client_id:user_id 
	 * value为login_failure_count
	 * 到达5次之后再次登陆需要输入验证码，登陆成功之后重置为0>
	 * @author ll 20150323
	 * @param client_id 客户端id
	 * @param user_id 用户编号
	 * @return login_failure_count返回失败次数
	 * @throws
	 */
	public static String get_cbbank_bank_user_login_client_id_user_id(String client_id,Long user_id) {
		String key = RedisKeyUtil.cbbank_bank_user_login_client_id_user_id(client_id,user_id) ;
		return redisManager.getString(key);
	}
	
	
	/**
	 * 设置value值为0
	 * 
	 * @Description:<key为cbbank:bank_user_login:client_id:user_id 
	 * value为login_failure_count
	 * 到达5次之后再次登陆需要输入验证码，登陆成功之后重置为0>
	 * @author ll 20150323
	 * @param client_id 客户端id
	 * @param user_id 用户编号
	 * @return boolean 设置是否成功
	 * @throws
	 */
	public static Boolean set_cbbank_bank_user_login_client_id_user_id(String client_id,Long user_id) {
		String key = RedisKeyUtil.cbbank_bank_user_login_client_id_user_id(client_id,user_id) ;
		return redisManager.putString(key, "0").equals("OK")?true:false;
	}
	
	/**
	 * 联合登录帐号的key设置value值为0
	 * @param clientId
	 * @param username 登录名
	 * @return
	 */
	public static Boolean set_cbbank_bank_user_login_client_id_username(String clientId,String username) {
		String key = RedisKeyUtil.cbbank_bank_user_login_client_id_username(clientId,username) ;
		return redisManager.putString(key, "0").equals("OK")?true:false;
	}
	
	
	/**
	 * 将key的value值自增
	 * 
	 * @Description:<key为cbbank:bank_user_login:client_id:user_id 
	 * value为login_failure_count
	 * 到达5次之后再次登陆需要输入验证码，登陆成功之后重置为0>
	 * @author ll 20150323
	 * @param client_id 客户端id
	 * @param user_id 用户编号
	 * @return boolean 设置是否成功
	 * @throws
	 */
	public static Long incr_cbbank_bank_user_login_client_id_user_id(String client_id,Long user_id) {
		String key = RedisKeyUtil.cbbank_bank_user_login_client_id_user_id(client_id,user_id) ;
		return redisManager.incr(key, Long.MAX_VALUE);
		
	}
	
	
	/**
	 * 联合登录错误次数
	 * @param clientId 客户端Id
	 * @param username 登录名
	 * @return
	 */
	public static Long incr_cbbank_bank_user_login_client_id_username(String clientId,String username) {
		String key = RedisKeyUtil.cbbank_bank_user_login_client_id_username(clientId,username) ;
		return redisManager.incr(key, Long.MAX_VALUE);
	}
	
	/**
	 * 获取联合登录错误次数
	 * @param clientId
	 * @param username 登录名
	 * @return
	 */
	public static String get_cbbank_bank_user_login_client_id_username(String clientId,String username) {
		String key = RedisKeyUtil.cbbank_bank_user_login_client_id_username(clientId,username) ;
		return redisManager.getString(key);
	}
	
	/**
	 * 删除银行用户key缓存<key:cbbank:bank_user:client_id:user_id>
	 * @param bankOperators
	 * @return
	 */
	public static boolean del_cbbank_bank_user_client_id_user_id(BankOperator... bankOperators) {
		for (BankOperator bankOperator : bankOperators) {
			String key = RedisKeyUtil.cbbank_bank_user_client_id_user_id(bankOperator.getClientId(),bankOperator.getId()) ;
			redisManager.del(key);
		}
		return true;
	}
	
	/**
	 * 删除银行用户登录失败次数key缓存<key:cbbank:bank_user_login:client_id:user_id>
	 * @param bankOperators
	 * @return
	 */
	public static boolean del_cbbank_bank_user_login_client_id_user_id(BankOperator... bankOperators) {
		for (BankOperator bankOperator : bankOperators) {
			String key = RedisKeyUtil.cbbank_bank_user_login_client_id_user_id(bankOperator.getClientId(),bankOperator.getId()) ;
			redisManager.del(key);
		}
		return true;
	}
	
	
	
}
