package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.MerchantOnlineApplyDao;
import com.froad.CB.po.MerchantOnlineApply;

public class MerchantOnlineApplyDaoImpl implements MerchantOnlineApplyDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer add(MerchantOnlineApply merchantOnlineApply) {
		return (Integer)sqlMapClientTemplate.insert("merchantOnlineApply.insert",merchantOnlineApply);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("merchantOnlineApply.deleteByPrimaryKey",id);
	}

	@Override
	public MerchantOnlineApply getById(Integer id) {
		return (MerchantOnlineApply)sqlMapClientTemplate.queryForObject("merchantOnlineApply.selectByPrimaryKey",id);
	}

	@Override
	public void updateById(MerchantOnlineApply apply) {
		sqlMapClientTemplate.update("merchantOnlineApply.updateById",apply);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<MerchantOnlineApply> getByUserId(String userId) {
		return sqlMapClientTemplate.queryForList("merchantOnlineApply.getByUserId",userId);
	}

	@Override
	public MerchantOnlineApply getMerchantApplyByPager(MerchantOnlineApply apply) {
		List<?> list=sqlMapClientTemplate.queryForList("merchantOnlineApply.getByPager",apply);
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("merchantOnlineApply.getByPagerCount",apply);
		apply.setList(list);
		apply.setTotalCount(totalCount);
		return apply;
	}

}
