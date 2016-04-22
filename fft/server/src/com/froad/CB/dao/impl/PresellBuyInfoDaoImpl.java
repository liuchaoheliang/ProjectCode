package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.PresellBuyInfoDao;
import com.froad.CB.po.PresellBuyInfo;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;

public class PresellBuyInfoDaoImpl implements PresellBuyInfoDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
		
	@Override
	public Integer add(PresellBuyInfo presellBuyInfo) {
		return (Integer) sqlMapClientTemplate.insert("presellBuyInfo.insert", presellBuyInfo);
	}

	@Override
	public Integer updateById(PresellBuyInfo presellBuyInfo) {
		return sqlMapClientTemplate.update("presellBuyInfo.updateById", presellBuyInfo);
	}

	@Override
	public PresellBuyInfo getById(Integer id) {
		return (PresellBuyInfo) sqlMapClientTemplate.queryForObject("presellBuyInfo.getById", id);
	}

	@Override
	public List<PresellBuyInfo> getByConditions(PresellBuyInfo presellBuyInfo) {
		return sqlMapClientTemplate.queryForList("presellBuyInfo.getByConditions", presellBuyInfo);
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public PresellBuyInfo getPresellDeliveryByPager(
			PresellBuyInfo presellBuyInfo) {
		return null;
	}

	@Override
	public List<PresellBuyInfo> getByTransId(List<String> transId) {
		return sqlMapClientTemplate.queryForList("presellBuyInfo.getByTransId", transId);
	}

	
	
}
