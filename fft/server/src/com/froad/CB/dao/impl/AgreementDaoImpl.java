package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.AgreementDao;
import com.froad.CB.po.Agreement;

/** 
 * @author FQ 
 * @date 2013-1-31 下午02:20:15
 * @version 1.0
 * 
 */
public class AgreementDaoImpl implements AgreementDao {

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	@Override
	public Integer addAgreement(Agreement agreement) {
		return (Integer) sqlMapClientTemplate.insert("agreement.addAgreement", agreement);
	}

	@Override
	public Agreement getAgreementById(Integer id) {
		return (Agreement) sqlMapClientTemplate.queryForObject("agreement.getAgreementById", id);
	}

	@Override
	public boolean updateAgreement(Agreement agreement) {
		sqlMapClientTemplate.update("agreement.updateAgreement", agreement);
		return true;
	}

	@Override
	public List<Agreement> getAgreement(Agreement agreement) {
		return sqlMapClientTemplate.queryForList("agreement.selectAgreement", agreement);
	}

}
