package com.froad.CB.common.loader;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.util.datasource.DbContextHolder;
import com.froad.util.datasource.DbType;

public class JdbcUtilsMall extends SqlMapClientTemplate{

	public JdbcUtilsMall(){
		System.out.println("==================开始构造JdbcUtilsMall================");
		DbContextHolder.setDbType(DbType.MALL);
		System.out.println("==================结束构造JdbcUtilsMall================");
	}
}
