package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.UserCertificationDao;
import com.froad.CB.po.UserCertification;

public class UserCertificationDaoImpl implements UserCertificationDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public UserCertification getUserCertByUserId(String userId) {
		List<UserCertification> list = null;
		UserCertification certRes = null;		
		list = sqlMapClientTemplate.queryForList("userCertification.getUserCertByUserId",userId);
		if(list != null && list.size()>0){
			certRes = list.get(0);
		}
		return certRes;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer add(UserCertification cert) {
		return (Integer)sqlMapClientTemplate.insert("userCertification.insert",cert);
	}

	@Override
	public List<UserCertification> getUserCertBySelective(UserCertification cert) {
		return sqlMapClientTemplate.queryForList("userCertification.getUserCertBySelective",cert);
	}


	@Override
	public Integer updateUserCertByUserId(UserCertification cert) {
		return (Integer)sqlMapClientTemplate.update("userCertification.updateUserCertByUserId",cert);
	}
}
