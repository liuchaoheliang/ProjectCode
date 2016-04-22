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
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.po.mysql.ProcessNode;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:ProcessNodeRedis Reason: 流程节点操作redis Date: 2015年10月29日 下午1:56:57
 * 
 * @author vania
 * @version
 * @see
 */
public class ProcessNodeRedis {
	private static RedisService redisService = new RedisManager();

	public static ProcessNode get_cbbank_process_node(String clientId, String processId, String nodeId) {
		return get_cbbank_process_node(RedisKeyUtil.cbbank_fallow_audit_processnode_client_id_process_id_node_id(clientId, processId, nodeId));
	}

	public static ProcessNode get_cbbank_process_node(String key) {
		ProcessNode processNode = null;
		Map<String, String> map = getAll_cbbank_fallow_audit_processnode_client_id_process_id_node_id(key);
		if (MapUtils.isNotEmpty(map)) {
			processNode = new ProcessNode();
			processNode.setId(StringUtils.isNotBlank(map.get("id")) ? Long.parseLong(map.get("id")) : null);
			// process.setCreateTime(createTime);
			// process.setUpdateTime(updateTime);
			processNode.setClientId(map.get("client_id"));
			processNode.setProcessId(map.get("process_id"));
			processNode.setNodeId(map.get("node_id"));
			processNode.setName(map.get("name"));
			processNode.setPreNodeId(map.get("pre_node_id"));
			processNode.setNextNodeId(map.get("next_node_id"));
			processNode.setType(map.get("type"));
			processNode.setNodeLogic(map.get("node_logic"));
			processNode.setRunnerFlag(map.get("runner_flag"));
			processNode.setNextRunnerOrg(map.get("next_runner_org"));
			processNode.setStatus(map.get("status"));
			processNode.setRunnerUserId(StringUtils.isNotBlank(map.get("runner_user_id")) ? Long.parseLong(map.get("runner_user_id")) : null);
			processNode.setRunnerPostId(StringUtils.isNotBlank(map.get("runner_post_id")) ? Long.parseLong(map.get("runner_post_id")) : null);
			processNode.setRunnerDepartId(StringUtils.isNotBlank(map.get("runner_depart_id")) ? Long.parseLong(map.get("runner_depart_id")) : null);
			processNode.setRunnerUsergroupId(StringUtils.isNotBlank(map.get("runner_usergroup_id")) ? Long.parseLong(map.get("runner_usergroup_id")) : null);
			processNode.setRunnerOrgLevel(map.get("runner_org_level"));
			processNode.setIsMultiselect(BooleanUtils.toBooleanObject(map.get("is_multiselect")));
			processNode.setIsOtherMan(BooleanUtils.toBooleanObject(map.get("is_other_man")));
			processNode.setIsAssignMan(BooleanUtils.toBooleanObject(map.get("is_assign_man")));
			processNode.setIsRevoke(BooleanUtils.toBooleanObject(map.get("is_revoke")));
			processNode.setIsFallback(BooleanUtils.toBooleanObject(map.get("is_fallback")));
		}
		return processNode;
	}

	/**
	 * 根据client_id+processId+node查询审核流程信息<key结构cbbank:fallow:audit:processnode:{0}:{1}:{2}>
	 * 
	 * @param clientId
	 *            客户端id
	 * @param processId
	 *            流程id
	 * @param nodeId
	 *            流程节点id
	 * @return
	 */
	public static Map<String, String> getAll_cbbank_fallow_audit_processnode_client_id_process_id_node_id(String clientId, String processId, String nodeId) {
		return getAll_cbbank_fallow_audit_processnode_client_id_process_id_node_id(RedisKeyUtil.cbbank_fallow_audit_processnode_client_id_process_id_node_id(clientId, processId, nodeId));
	}

	/**
	 * 根据client_id+processId查询审核流程信息<key结构cbbank:fallow:audit:process:{0}:{1}>
	 *
	 * @author vania 2015年10月30日 下午1:08:54
	 * @param key
	 * @return
	 *
	 */
	public static Map<String, String> getAll_cbbank_fallow_audit_processnode_client_id_process_id_node_id(String key) {
		return redisService.getMap(key);
	}

	public static boolean set_cbbank_process_node(String clientId, String processId, String nodeId, ProcessNode processNode) {
		return set_cbbank_process_node(RedisKeyUtil.cbbank_fallow_audit_processnode_client_id_process_id_node_id(clientId, processId, nodeId), processNode);
	}

	/**
	 * 设置审核流程Redis信息 <key结构cbbank:org:client_id_org_code>
	 * <key结构cbbank:outlet_org:client_id_outlet_id>
	 * <key结构cbbank:merchant_org:client_id_merchant_id>
	 * 
	 * @param processNode
	 *            流程对象
	 * @return
	 */
	public static boolean set_cbbank_process_node(String key, ProcessNode processNode) {
		if (null == processNode) {
			throw new NullPointerException("processNode must not be null.");
			// throw new NullArgumentException("processNode");
		}
		boolean result = false;
		try {
			Map<String, String> hash = new HashMap<String, String>();
			hash.put("id", ObjectUtils.toString(processNode.getId())); // ID
			// hash.put("create_time",
			// ObjectUtils.toString(process.getCreateTime())); // 创建时间
			// hash.put("update_time",
			// ObjectUtils.toString(process.getUpdateTime())); // 更新时间
			hash.put("client_id", ObjectUtils.toString(processNode.getClientId())); // 客户端ID
			hash.put("process_id", ObjectUtils.toString(processNode.getProcessId())); // 流程ID
			hash.put("node_id", ObjectUtils.toString(processNode.getNodeId())); // 流程节点ID
			hash.put("name", ObjectUtils.toString(processNode.getName())); // 名称
			hash.put("pre_node_id", ObjectUtils.toString(processNode.getPreNodeId())); // 前序节点ID
			hash.put("next_node_id", ObjectUtils.toString(processNode.getNextNodeId())); // 后续节点ID
			hash.put("type", ObjectUtils.toString(processNode.getType())); // 节点类型
			hash.put("node_logic", ObjectUtils.toString(processNode.getNodeLogic())); // 节点逻辑
			hash.put("runner_flag", ObjectUtils.toString(processNode.getRunnerFlag())); // 执行类型
			hash.put("next_runner_org", ObjectUtils.toString(processNode.getNextRunnerOrg())); // 后续执行机构
			hash.put("status", ObjectUtils.toString(processNode.getStatus())); // 状态
			hash.put("runner_user_id", ObjectUtils.toString(processNode.getRunnerUserId())); // 执行用户ID
			hash.put("runner_post_id", ObjectUtils.toString(processNode.getRunnerPostId())); // 执行岗位ID
			hash.put("runner_depart_id", ObjectUtils.toString(processNode.getRunnerDepartId())); // 执行部门ID
			hash.put("runner_usergroup_id", ObjectUtils.toString(processNode.getRunnerUsergroupId())); // 执行用户组ID
			hash.put("runner_org_level", ObjectUtils.toString(processNode.getRunnerOrgLevel())); // 执行机构等级
			hash.put("is_multiselect", BooleanUtils.toString(processNode.getIsMultiselect(), "1", "0")); // 是否允许多选
			hash.put("is_other_man", BooleanUtils.toString(processNode.getIsOtherMan(), "1", "0")); // 是否允许转发其它人
			hash.put("is_assign_man", BooleanUtils.toString(processNode.getIsAssignMan(), "1", "0")); // 是否允许由代理人处理
			hash.put("is_revoke", BooleanUtils.toString(processNode.getIsRevoke(), "1", "0")); // 是否允许撤销
			hash.put("is_fallback", BooleanUtils.toString(processNode.getIsFallback(), "1", "0")); // 是否允许回退

			redisService.putMap(key, hash);
			result = true;

		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
		return result;
	}

}
