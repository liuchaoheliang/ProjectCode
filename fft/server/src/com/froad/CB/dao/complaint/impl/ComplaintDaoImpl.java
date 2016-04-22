package com.froad.CB.dao.complaint.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.complaint.ComplaintDao;
import com.froad.CB.po.complaint.Complaint;

public class ComplaintDaoImpl implements ComplaintDao{

	
	private SqlMapClientTemplate sqlMapClientTemplate;

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addComplaint(Complaint complaint) {
		return (Integer)sqlMapClientTemplate.insert("complaint.insert",complaint);
	}

	@Override
	public boolean deleteComplaintById(Integer id) {
		sqlMapClientTemplate.delete("complaint.deleteByPrimaryKey",id);
		return true;
	}

	@Override
	public Complaint getComplaintById(Integer id) {
		return (Complaint)sqlMapClientTemplate.queryForObject("complaint.selectByPrimaryKey",id);
	}

	@Override
	public Complaint getComplaintByPager(Complaint complaint) {
		List list=sqlMapClientTemplate.queryForList("complaint.getComplaintByPager",complaint);
		Integer count=(Integer)sqlMapClientTemplate.queryForObject("complaint.getComplaintByPagerCount",complaint);
		complaint.setList(list);
		complaint.setTotalCount(count);
		return complaint;
	}

	@Override
	public boolean updateComplaintById(Complaint complaint) {
		sqlMapClientTemplate.update("complaint.updateByPrimaryKeySelective",complaint);
		return true;
	}

	@Override
	public boolean updateComplaintStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("complaint.updateComplaintStateById",params);
		return true;
	}

	@Override
	public boolean stopComplaintById(Integer id) {
		sqlMapClientTemplate.update("complaint.stopComplaintById",id);
		return true;
	} 
}
