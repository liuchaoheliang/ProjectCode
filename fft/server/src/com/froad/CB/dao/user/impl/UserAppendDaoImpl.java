package com.froad.CB.dao.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.user.UserAppendDao;
import com.froad.CB.po.user.Authorities;
import com.froad.CB.po.user.Resources;
import com.froad.CB.po.user.User;
import com.froad.CB.po.user.UserAppend;

public class UserAppendDaoImpl implements UserAppendDao{
	private SqlMapClientTemplate sqlMapClientTemplate;
		
	@Override
	public UserAppend getUserAppendByUserId(String userId) {
		return (UserAppend) sqlMapClientTemplate.queryForObject("userAppend.getUserAppendByUserId", userId);
	}

	@Override
	public Integer addUserAppend(UserAppend userAppend) {
		return (Integer) sqlMapClientTemplate.insert("userAppend.addUserAppend", userAppend);
	}

	@Override
	public boolean updateUserAppend(UserAppend userAppend) {
		boolean flag=false;
		int n= sqlMapClientTemplate.update("userAppend.updateUserAppend", userAppend);
		if(n>0){
			flag=true;
		}
		return  flag;
	}

	@Override
	public Integer queryMaxRoleID() {
		return (Integer)sqlMapClientTemplate.queryForObject("userAppend.queryMaxRoleID");
	}	
	
	
	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public void addrole(User user) {
		sqlMapClientTemplate.insert("userAppend.addrole",user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Authorities> queryAuthoritiesByUserID(String userID) {
		List<Authorities> list = new ArrayList<Authorities>();
		list = sqlMapClientTemplate.queryForList("userAppend.queryAuthoritiesByUsername",userID);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Authorities> queryAuthorities() {
		List<Authorities> list = new ArrayList<Authorities>();
		list = sqlMapClientTemplate.queryForList("userAppend.queryAuthorities");
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Resources> queryResourcesByAuthorities(String auth) {
		List<Resources> list = new ArrayList<Resources>();
		list = sqlMapClientTemplate.queryForList("userAppend.queryResourcesByAuthorities",auth);
		return list;
	}

}
