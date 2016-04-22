package com.froad.CB.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.PayDao;
import com.froad.CB.po.Pay;
import com.ibatis.sqlmap.client.SqlMapExecutor;

public class PayDaoImpl implements PayDao{

	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Integer addPay(Pay pay) {
		return (Integer)sqlMapClientTemplate.insert("pay.insert",pay);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("pay.deleteByPrimaryKey",id);
	}

	@Override
	public Pay getPayById(Integer id) {
		return (Pay)sqlMapClientTemplate.queryForObject("pay.selectByPrimaryKey",id);
	}

	@Override
	public void updateById(Pay pay) {
		sqlMapClientTemplate.update("pay.updateById",pay);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("pay.updateStateById",params);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public void batchInsert(final List<Pay> payList) {
		sqlMapClientTemplate.execute(new SqlMapClientCallback(){

			@Override
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (int i = 0; i <payList.size(); i++) {
					executor.insert("pay.insert",payList.get(i));
				}
				executor.executeBatch();
				return null;
			}

		});
	}

	@Override
	public void updatePay(Pay pay) {
		sqlMapClientTemplate.update("pay.updatePay",pay);
	}

	@Override
	public List<Pay> getPayByTransId(String transId) {
		return sqlMapClientTemplate.queryForList("pay.selectByTranId",transId);
	}

	@Override
	public List<Pay> getAllWithoutPays() {
		return sqlMapClientTemplate.queryForList("pay.getAllWithoutPays");
	}

    public String aliPayResultCheck(String transId)
    {
        String  result = "";
        Object o =  sqlMapClientTemplate.queryForObject("pay.aliPayResultCheck",transId);
        if(null!=o)
        {
            result = (String)o;
        }
        return result;
    }

}
