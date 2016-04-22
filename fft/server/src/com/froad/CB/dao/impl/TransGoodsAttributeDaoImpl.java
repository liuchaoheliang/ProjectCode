package com.froad.CB.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.TransGoodsAttributeDao;
import com.froad.CB.po.TransGoodsAttribute;
import com.ibatis.sqlmap.client.SqlMapExecutor;

public class TransGoodsAttributeDaoImpl implements TransGoodsAttributeDao{
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public void add(TransGoodsAttribute attr) {
		sqlMapClientTemplate.insert("transGoodsAttribute.insert",attr);
	}

	@Override
	public List<TransGoodsAttribute> getByTransId(String transId) {
		return sqlMapClientTemplate.queryForList("transGoodsAttribute.getByTransId",transId);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public void batchInsert(final List<TransGoodsAttribute> attrList) {
		sqlMapClientTemplate.execute(new SqlMapClientCallback(){

			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (int i = 0; i <attrList.size(); i++) {
					executor.insert("transGoodsAttribute.insert",attrList.get(i));
				}
				executor.executeBatch();
				return null;
			}
			
		});
	}

}
