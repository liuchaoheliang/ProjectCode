package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.MerchantIndustryDao;
import com.froad.CB.po.MerchantIndustry;

public class MerchantIndustryDaoImpl implements MerchantIndustryDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer add(MerchantIndustry merchantIndustry) {
		return (Integer)sqlMapClientTemplate.insert("merchantIndustry.insert",merchantIndustry);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("merchantIndustry.deleteByPrimaryKey",id);
	}

	@Override
	public MerchantIndustry getById(Integer id) {
		return (MerchantIndustry)sqlMapClientTemplate.queryForObject("merchantIndustry.selectByPrimaryKey",id);
	}

	@Override
	public void updateById(MerchantIndustry merchantIndustry) {
		sqlMapClientTemplate.update("merchantIndustry.updateById",merchantIndustry);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<MerchantIndustry> getAll() {
		return sqlMapClientTemplate.queryForList("merchantIndustry.selectAll");
	}

}
