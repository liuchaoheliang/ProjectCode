/**
 * Project Name:Froad Cbank Server Bank
 * File Name:FlushCache.java
 * Package Name:com.froad.thrift
 * Date:2015年10月29日下午5:17:15
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.test.cache;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ProcessNodeMapper;
import com.froad.db.redis.ProcessNodeRedis;
import com.froad.po.mysql.Process;
import com.froad.po.mysql.ProcessNode;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:FlushProcessNodeCache Reason: 刷入流程节点缓存 ADD REASON. Date:
 * 2015年10月29日 下午5:17:15
 * 
 * @author vania
 * @version
 * @see
 */
public class FlushProcessNodeCache {
	static {
		PropertiesUtil.load();
	}

	public static void main(String[] args) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProcessNodeMapper processNodeMapper = sqlSession.getMapper(ProcessNodeMapper.class);
			ProcessNode queryProcessNode = new ProcessNode();
//			queryProcessNode.setClientId(clientId);
			List<ProcessNode> list = processNodeMapper.findProcessNode(queryProcessNode, null);
			if (CollectionUtils.isNotEmpty(list)) {
				for (ProcessNode processNode : list) {
					String clientId = processNode.getClientId();
					String key = RedisKeyUtil.cbbank_fallow_audit_processnode_client_id_process_id_node_id(clientId, processNode.getProcessId(), processNode.getNodeId());
					System.out.println("刷入审核流程节点缓存: " + JSON.toJSONString(processNode));
					ProcessNodeRedis.set_cbbank_process_node(key, processNode);

				}
			}
//			System.out.println("刷入" + clientId + "流程节点缓存成功!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}

	}

}
