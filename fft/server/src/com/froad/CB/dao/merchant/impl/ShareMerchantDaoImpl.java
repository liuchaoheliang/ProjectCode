package com.froad.CB.dao.merchant.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.merchant.ShareMerchantDao;
import com.froad.CB.po.merchant.ShareMerchant;

public class ShareMerchantDaoImpl implements ShareMerchantDao{
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Integer addShareMerchant(ShareMerchant shareMerchant) {
		return (Integer)sqlMapClientTemplate.insert("shareMerchant.insert",shareMerchant);
	}

	@Override
	public boolean deleteById(Integer id) {
		sqlMapClientTemplate.delete("shareMerchant.deleteByPrimaryKey",id);
		return true;
	}

	@Override
	public ShareMerchant getShareMerchantById(Integer id) {
		return (ShareMerchant)sqlMapClientTemplate.queryForObject("shareMerchant.selectByPrimaryKey",id);
	}

	@Override
	public ShareMerchant getShareMerchantByPager(ShareMerchant shareMerchant) {
		List list=sqlMapClientTemplate.queryForList("shareMerchant.getByPager",shareMerchant);
		Integer count=(Integer)sqlMapClientTemplate.queryForObject("shareMerchant.getByPagerCount",shareMerchant);
		
		shareMerchant.setList(list);
		shareMerchant.setTotalCount(count);
		return shareMerchant;
	}

	@Override
	public List<ShareMerchant> getShareMerchantByUserId(String userId) {
		return sqlMapClientTemplate.queryForList("shareMerchant.getByUserId",userId);
	}

	@Override
	public boolean updateById(ShareMerchant shareMerchant) {
		sqlMapClientTemplate.update("shareMerchant.updateById",shareMerchant);
		return true;
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("shareMerchant.updateStateById",params);
		return true;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
