/**
 * Project Name:froad-cbank-server-operating-report-1.12.0-SNAPSHOT
 * File Name:UserMyBatisManager.java
 * Package Name:com.froad.db.cbank.mysql.mapper
 * Date:2016年1月29日下午4:29:57
 * Copyright (c) 2016, F-Road, Inc.
 *
*/

package com.froad.db.user.mysql;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.froad.logback.LogCvt;

/**
 * ClassName:UserMyBatisManager
 * Reason:	 TODO ADD REASON.
 * Date:     2016年1月29日 下午4:29:57
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class UserMyBatisManager {
	
	private static SqlSessionFactory sqlSessionFactory;

	static {
		String resource = "user-mybatis-config.xml";
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
		
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
}
