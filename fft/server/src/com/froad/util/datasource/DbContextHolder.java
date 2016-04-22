package com.froad.util.datasource;


	/**
	 * 类描述：数据源上下文
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 24, 2013 11:06:14 AM 
	 */
public class DbContextHolder {

	private static final ThreadLocal<DbType> contextHolder=new ThreadLocal<DbType>();
	
	public static void setDbType(DbType dbType){
		contextHolder.set(dbType);
	}
	
	public static DbType getDbType(){
		return contextHolder.get();
	}
	
	public static void clearDbType(){
		contextHolder.remove();
	}
}
