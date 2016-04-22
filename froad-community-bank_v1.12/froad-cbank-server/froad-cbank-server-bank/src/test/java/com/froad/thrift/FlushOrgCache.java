/**
 * Project Name:Froad Cbank Server Bank
 * File Name:FlushCache.java
 * Package Name:com.froad.thrift
 * Date:2015年10月29日下午5:17:15
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.thrift;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.OrgCommonMapper;
import com.froad.db.redis.OrgRedis;
import com.froad.po.Org;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;

/**
 * ClassName:FlushCache Reason: 刷入机构缓存 ADD REASON. Date: 2015年10月29日 下午5:17:15
 * 
 * @author vania
 * @version
 * @see
 */
public class FlushOrgCache {
	static {
		PropertiesUtil.load();
	}

	public static void main(String[] args) {
		SqlSession sqlSession = null;
		String clientId = "chongqing";
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			OrgCommonMapper orgMapper = sqlSession.getMapper(OrgCommonMapper.class);
			Org queryOrg = new Org();
			queryOrg.setClientId(clientId);
			List<Org> list = orgMapper.findOrg(queryOrg);
			if (CollectionUtils.isNotEmpty(list)) {
				for (Org org : list) {
					String orgCode = org.getOrgCode();
					String key = RedisKeyUtil.cbbank_org_client_id_org_code(clientId, orgCode);
					System.out.println("刷入机构缓存: " + JSON.toJSONString(org));
					OrgRedis.set_cbbank_org(key, org);

				}
			}
			System.out.println("刷入" + clientId + "机构缓存成功!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}

	}

}
