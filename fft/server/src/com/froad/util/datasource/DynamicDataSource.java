package com.froad.util.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


	/**
	 * 类描述：动态数据源路由
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 24, 2013 11:01:11 AM 
	 */
public class DynamicDataSource extends AbstractRoutingDataSource{

		@Override
		protected Object determineCurrentLookupKey() {
			logger.info("===========DB type is: "+DbContextHolder.getDbType());
			return DbContextHolder.getDbType();
		}

}
