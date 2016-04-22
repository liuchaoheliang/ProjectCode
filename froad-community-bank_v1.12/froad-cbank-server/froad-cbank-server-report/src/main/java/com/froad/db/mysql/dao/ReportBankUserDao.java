package com.froad.db.mysql.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.froad.po.ReportBankUser;

public class ReportBankUserDao {

	protected static String SQL_INSERT_BY_BATCH = null;
	
	/**
	 * insert records by batch
	 * 
	 * @param conn
	 * @param bankUserList
	 */
	public void insertByBatch(Connection conn, List<ReportBankUser> bankUserList){
		StringBuffer sql = null;
		PreparedStatement stmt = null;
		ReportBankUser bankUser = null;
		
		if (null == SQL_INSERT_BY_BATCH){
			sql = new StringBuffer();
			sql.append("INSERT INTO cb_report_bank_user(");
			sql.append("client_id, create_time, forg_code, forg_name, sorg_code, sorg_name, ");
			sql.append("torg_code, torg_name, lorg_code, lorg_name, sign_user_name, remark");
			sql.append(") VALUES(");
			sql.append("?, ?, ?, ?, ?, ?, ");
			sql.append("?, ?, ?, ?, ?, ?");
			sql.append(")");
			SQL_INSERT_BY_BATCH = sql.toString();
		}
		
		try {
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(SQL_INSERT_BY_BATCH, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			for (int i = 0; i < bankUserList.size(); i++){
				bankUser = bankUserList.get(i);
				stmt.setString(1, bankUser.getClientId());
				stmt.setTimestamp(2, new Timestamp(bankUser.getCreateTime().getTime()));
				stmt.setString(3, bankUser.getForgCode());
				stmt.setString(4, bankUser.getForgName());
				stmt.setString(5, bankUser.getSorgCode());
				stmt.setString(6, bankUser.getSorgName());
				stmt.setString(7, bankUser.getTorgCode());
				stmt.setString(8, bankUser.getTorgName());
				stmt.setString(9, bankUser.getLorgCode());
				stmt.setString(10, bankUser.getLorgName());
				stmt.setString(11, bankUser.getSignUserName());
				stmt.setString(12, bankUser.getRemark());
				stmt.addBatch();
			}
			stmt.executeBatch();
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
