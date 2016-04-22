package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.StoreDao;
import com.froad.CB.po.Store;

public class StoreDaoImpl implements StoreDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addStore(Store store) {
		return (Integer)sqlMapClientTemplate.insert("store.insert",store);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("store.deleteByPrimaryKey",id);
	}

	@Override
	public Store getStoreById(Integer id) {
		return (Store)sqlMapClientTemplate.queryForObject("store.selectByPrimaryKey",id);
	}

	@Override
	public List<Store> getStoreByMerchantId(Integer merchantId) {
		return sqlMapClientTemplate.queryForList("store.selectByMerchantId",merchantId);
	}

	@Override
	public Store getStoreByPager(Store store) {
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("store.getStoreByPagerCount",store);
		List<?> list=sqlMapClientTemplate.queryForList("store.getStoreByPager",store);
		store.setTotalCount(totalCount);
		store.setList(list);
		return store;
	}

	@Override
	public void updateById(Store store) {
		sqlMapClientTemplate.update("store.updateById",store);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
