package com.froad.CB.common.loader;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.util.datasource.DbContextHolder;
import com.froad.util.datasource.DbType;


	/**
	 * 类描述：JDBC工具类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 24, 2013 3:24:51 PM 
	 */
public class JdbcUtils extends SqlMapClientTemplate{

	public JdbcUtils(){
		System.out.println("==================开始构造JdbcUtils================");
		DbContextHolder.setDbType(DbType.FFT);
		System.out.println("==================结束构造JdbcUtils================");
	}
}
