package com.froad.CB.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.TransDetailsDao;
import com.froad.CB.po.TransDetails;
import com.ibatis.sqlmap.client.SqlMapExecutor;

public class TransDetailsDaoImpl implements TransDetailsDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addTransDetails(TransDetails transDetails) {
		return (Integer)sqlMapClientTemplate.insert("transDetails.insert",transDetails);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("transDetails.deleteByPrimaryKey",id);
	}

	@Override
	public TransDetails getTransDetailsById(Integer id) {
		return (TransDetails)sqlMapClientTemplate.queryForObject("transDetails.selectByPrimaryKey",id);
	}

	@Override
	public void updateById(TransDetails transDetails) {
		sqlMapClientTemplate.update("transDetails.updateById",transDetails);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("transDetails.updateStateById",params);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public void batchInsert(final List<TransDetails> detailsList) {
		sqlMapClientTemplate.execute(new SqlMapClientCallback(){

			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (int i = 0; i < detailsList.size(); i++) {
					executor.insert("transDetails.insert",detailsList.get(i));
				}
				executor.executeBatch();
				return null;
			}
			
		});
	}

}
