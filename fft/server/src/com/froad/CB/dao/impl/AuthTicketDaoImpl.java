package com.froad.CB.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.AppException.AppException;
import com.froad.CB.dao.AuthTicketDao;
import com.froad.CB.po.AuthTicket;

public class AuthTicketDaoImpl implements AuthTicketDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addAuthTicket(AuthTicket authTicket)throws AppException{
		try {
			return (Integer)sqlMapClientTemplate.insert("authTicket.insert",authTicket);
		} catch (Exception e) {
			throw new AppException(e);
		}
	}

	@Override
	public boolean deleteById(Integer id) {
		sqlMapClientTemplate.delete("authTicket.deleteByPrimaryKey",id);
		return true;
	}

	@Override
	public AuthTicket getAuthTicketById(Integer id) {
		return (AuthTicket)sqlMapClientTemplate.queryForObject("authTicket.selectByPrimaryKey",id);
	}

	@Override
	public boolean updateById(AuthTicket authTicket) {
		int num = 0;
		num = sqlMapClientTemplate.update("authTicket.updateById",authTicket);
		return num>0?true:false;
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		HashMap<String,Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("authTicket.updateStateById",params);
		return true;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<AuthTicket> getAuthTickBySelective(AuthTicket authTicket) {
		return sqlMapClientTemplate.queryForList("authTicket.getAuthTickBySelective",authTicket);
	}

	@Override
	public boolean isNotExist(String securitiesNo) {
		Object obj=sqlMapClientTemplate.queryForObject("authTicket.getIdBySecureNo",securitiesNo);
		return obj==null;
	}

	@Override
	public boolean updateAuthTickMsgCount(AuthTicket authTicket) {
		sqlMapClientTemplate.update("authTicket.updateAuthTickeMsgCount",authTicket);
		return true;
	}

	@Override
	public List<AuthTicket> getAuthTickByTransId(Integer transId) {
		return sqlMapClientTemplate.queryForList("authTicket.getAuthTickByTransId",transId);
	}

}
