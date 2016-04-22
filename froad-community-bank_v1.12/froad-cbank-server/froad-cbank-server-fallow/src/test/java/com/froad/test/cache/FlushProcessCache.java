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
import com.froad.db.mysql.mapper.ProcessMapper;
import com.froad.db.redis.ProcessRedis;
import com.froad.po.mysql.Process;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:FlushProcessCache Reason: 刷入流程缓存 ADD REASON. Date: 2015年10月29日 下午5:17:15
 * 
 * @author vania
 * @version
 * @see
 */
public class FlushProcessCache {
	static {
		PropertiesUtil.load();
	}

	public static void main(String[] args) {
		SqlSession sqlSession = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProcessMapper processMapper = sqlSession.getMapper(ProcessMapper.class);
			Process queryProcess = new Process();
//			queryProcess.setClientId(clientId);
			List<Process> list = processMapper.findProcess(queryProcess, null);
			if (CollectionUtils.isNotEmpty(list)) {
				for (Process process : list) {
					String clientId = process.getClientId();
					String key = RedisKeyUtil.cbbank_fallow_audit_process_client_id_process_id(clientId, process.getProcessId());
					System.out.println("刷入审核流程缓存: " + JSON.toJSONString(process));
					ProcessRedis.set_cbbank_process(key, process);

				}
//				System.out.println("刷入" + clientId + "流程缓存成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}

	}

}
