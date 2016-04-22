package com.froad.CB.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.SmsLogDao;
import com.froad.CB.po.SmsLog;

public class SmsLogDaoImpl implements SmsLogDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer add(SmsLog smsLog) {
		return (Integer)sqlMapClientTemplate.insert("smsLog.insert",smsLog);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("smsLog.deleteByPrimaryKey",id);
	}

	@Override
	public SmsLog getById(Integer id) {
		return (SmsLog)sqlMapClientTemplate.queryForObject("smsLog.selectByPrimaryKey",id);
	}

	@Override
	public void updateById(SmsLog smsLog) {
		sqlMapClientTemplate.update("smsLog.updateById",smsLog);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public void updateResultById(Integer id, boolean result) {
		SmsLog smsLog=new SmsLog();
		smsLog.setId(id);
		smsLog.setResult(result?"1":"0");
		sqlMapClientTemplate.update("smsLog.updateResultById",smsLog);
	}

}
