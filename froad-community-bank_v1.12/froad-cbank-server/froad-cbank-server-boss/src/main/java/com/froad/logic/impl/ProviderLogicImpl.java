/**
 * Project Name:froad-cbank-server-boss-1.8.0-SNAPSHOT
 * File Name:ProviderLogicImpl.java
 * Package Name:com.froad.logic.impl
 * Date:2015年12月2日下午4:24:04
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ProviderCommonMapper;
import com.froad.logback.LogCvt;
import com.froad.logic.ProviderLogic;
import com.froad.po.Provider;

/**
 * ClassName:ProviderLogicImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月2日 下午4:24:04
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProviderLogicImpl implements ProviderLogic {

	@Override
	public List<Provider> findAllProviders() throws Exception {
		SqlSession session = null;
		List<Provider> providers = new ArrayList<Provider>();
		try {
			session = MyBatisManager.getSqlSessionFactory().openSession();
			ProviderCommonMapper providerCommonMapper = session.getMapper(ProviderCommonMapper.class);
			
			providers = providerCommonMapper.findAllProviders();
		} catch (Exception e) {
			LogCvt.error("查询供应商列表异常", e);
			throw e;
		} finally {
			if(session != null) session.close();
		}
		return providers;
	}

}
