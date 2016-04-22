package com.froad.CB.dao.complaint.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.complaint.ComplaintCategoryDao;
import com.froad.CB.po.complaint.ComplaintCategory;

public class ComplaintCategoryDaoImpl implements ComplaintCategoryDao{

	
	private SqlMapClientTemplate sqlMapClientTemplate;

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addComplaintCategory(ComplaintCategory category) {
		return (Integer)sqlMapClientTemplate.insert("complaintCategory.insert",category);
	}

	@Override
	public boolean deleteComplaintCategoryById(Integer id) {
		sqlMapClientTemplate.delete("complaintCategory.deleteByPrimaryKey",id);
		return true;
	}

	@Override
	public ComplaintCategory getComplaintCategoryById(Integer id) {
		return (ComplaintCategory)sqlMapClientTemplate.queryForObject("complaintCategory.selectByPrimaryKey",id);
	}

	@Override
	public List<ComplaintCategory> getComplaintCategoryList() {
		return sqlMapClientTemplate.queryForList("complaintCategory.selectAll");
	}

	@Override
	public boolean updateComplaintCategoryById(ComplaintCategory category) {
		sqlMapClientTemplate.update("complaintCategory.updateByPrimaryKeySelective",category);
		return true;
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("complaintCategory.updateStateById",params);
		return true;
	} 
}
