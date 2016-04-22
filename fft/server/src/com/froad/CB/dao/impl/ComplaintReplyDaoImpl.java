package com.froad.CB.dao.impl;

import java.util.HashMap;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.ComplaintReplyDao;
import com.froad.CB.po.ComplaintReply;

public class ComplaintReplyDaoImpl implements ComplaintReplyDao{
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Integer addComplaintReply(ComplaintReply reply) {
		return (Integer)sqlMapClientTemplate.insert("complaintReply.insert",reply);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("complaintReply.updateStateById",params);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
