package com.froad.cbank.coremodule.normal.boss.common.db.mysql.datasource;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceFactory extends UnpooledDataSourceFactory {

	public DruidDataSourceFactory() {
		this.dataSource = new DruidDataSource();
	}
}
