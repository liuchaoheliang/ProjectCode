/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:AuditRedis.java
 * Package Name:com.froad.db.redis
 * Date:2015年10月29日下午1:56:57
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.po.mysql.Process;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:ProcessRedis Reason: 流程操作redis Date: 2015年10月29日 下午1:56:57
 * 
 * @author vania
 * @version
 * @see
 */
public class ProcessRedis {
	private static RedisService redisService = new RedisManager();

	public static Process get_cbbank_process(String clientId, String processId) {
		return get_cbbank_process(RedisKeyUtil.cbbank_fallow_audit_process_client_id_process_id(clientId, processId));
	}

	public static Process get_cbbank_process(String key) {
		Process process = null;
		Map<String, String> map = getAll_cbbank_fallow_audit_process_client_id_process_id(key);
		if (MapUtils.isNotEmpty(map)) {
			process = new Process();
			process.setId(StringUtils.isNotBlank(map.get("id")) ? Long.parseLong(map.get("id")) : null);
			// process.setCreateTime(createTime);
			// process.setUpdateTime(updateTime);
			process.setClientId(map.get("client_id"));
			process.setProcessId(map.get("process_id"));
			process.setParentProcessId(map.get("parent_process_id"));
			process.setName(map.get("name"));
			process.setDisplayName(map.get("display_name"));
			process.setType(map.get("type"));
			process.setTypeDetail(map.get("type_detail"));
			process.setStatus(map.get("status"));
			process.setVersion(StringUtils.isNotBlank(map.get("version")) ? Integer.parseInt(map.get("version")) : null);
			process.setCreator(map.get("creator"));
		}
		return process;
	}

	/**
	 * 根据client_id+processId查询审核流程信息<key结构cbbank:fallow:audit:process:{0}:{1}>
	 * 
	 * @param clientId
	 *            客户端id
	 * @param processId
	 *            流程
	 * @return
	 */
	public static Map<String, String> getAll_cbbank_fallow_audit_process_client_id_process_id(String clientId, String processId) {
		return getAll_cbbank_fallow_audit_process_client_id_process_id(RedisKeyUtil.cbbank_fallow_audit_process_client_id_process_id(clientId, processId));
	}

	/**
	 * 根据client_id+processId查询审核流程信息<key结构cbbank:fallow:audit:process:{0}:{1}>
	 *
	 * @author vania 2015年10月30日 下午1:08:54
	 * @param key
	 * @return
	 *
	 */
	public static Map<String, String> getAll_cbbank_fallow_audit_process_client_id_process_id(String key) {
		return redisService.getMap(key);
	}

	public static boolean set_cbbank_process(String clientId, String processId, Process process) {
		return set_cbbank_process(RedisKeyUtil.cbbank_fallow_audit_process_client_id_process_id(clientId, processId), process);
	}

	/**
	 * 设置审核流程Redis信息 <key结构cbbank:org:client_id_org_code>
	 * <key结构cbbank:outlet_org:client_id_outlet_id>
	 * <key结构cbbank:merchant_org:client_id_merchant_id>
	 * 
	 * @param process
	 *            流程对象
	 * @return
	 */
	public static boolean set_cbbank_process(String key, Process process) {
		if (null == process) {
			throw new NullPointerException("process must not be null.");
//			throw new NullArgumentException("process");
		}
		boolean result = false;
		try {
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("id", ObjectUtils.toString(process.getId())); // ID
			// hash.put("create_time",
			// ObjectUtils.toString(process.getCreateTime())); // 创建时间
			// hash.put("update_time",
			// ObjectUtils.toString(process.getUpdateTime())); // 更新时间
			hash.put("client_id", ObjectUtils.toString(process.getClientId())); // 客户端ID
			hash.put("process_id", ObjectUtils.toString(process.getProcessId())); // 流程ID
			hash.put("parent_process_id", ObjectUtils.toString(process.getParentProcessId())); // 父流程ID
			hash.put("name", ObjectUtils.toString(process.getName())); // 名称
			hash.put("display_name", ObjectUtils.toString(process.getDisplayName())); // 显示名称
			hash.put("type", ObjectUtils.toString(process.getType())); // 流程类型
			hash.put("type_detail", ObjectUtils.toString(process.getTypeDetail())); // 类型详情
			hash.put("status", ObjectUtils.toString(process.getStatus())); // 状态
			hash.put("version", ObjectUtils.toString(process.getVersion())); // 版本号
			hash.put("creator", ObjectUtils.toString(process.getCreator())); // 创建者

			redisService.putMap(key, hash);
			result = true;

		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
		return result;
	}

}
