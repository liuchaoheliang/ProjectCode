package com.froad.db.mongo.test;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;

public class DBHelper {

	public static final String driver = "com.mysql.jdbc.Driver";
	
	//重庆社区银行    生产 
//	public static final String url = "jdbc:mysql://xx/froad_cbank?useUnicode=true&characterEncoding=utf8";
//	public static final String username = "xx";
//	public static final String password = "xx";
	
	//重构社区银行  生产
//	public static final String url = "jdbc:mysql://xx/froad_cbank?useUnicode=true&characterEncoding=utf8";
//	public static final String username = "xx";
//	public static final String password = "xx";
	
	//测试环境
	public static final String url = "jdbc:mysql://172.18.3.10:3306/froadfft_ah_pro3?useUnicode=true&characterEncoding=utf8";
	public static final String username = "test_froad";
	public static final String password = "test123";
	


	public Connection conn = null;
	public PreparedStatement pst = null;

	public DBHelper(String sql) {
		try {
			Class.forName(driver);// 指定连接类型
			conn = DriverManager.getConnection(url, username, password);// 获取连接
			pst = conn.prepareStatement(sql);// 准备执行语句
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.conn.close();
			this.pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
