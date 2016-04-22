package com.froad.CB.dao.user.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.user.MemberCollectDao;
import com.froad.CB.po.user.MemberCollect;

public class MemberCollectDaoImpl implements MemberCollectDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addMemberCollect(MemberCollect collect) {
		return (Integer)sqlMapClientTemplate.insert("memberCollect.insert",collect);
	}

	@Override
	public boolean deleteById(Integer id) {
		sqlMapClientTemplate.delete("memberCollect.deleteByPrimaryKey",id);
		return true;
	}

	@Override
	public MemberCollect getMemberCollectByPager(MemberCollect collect) {
		List list=sqlMapClientTemplate.queryForList("memberCollect.getMemberCollectByPager",collect);
		Integer count=(Integer)sqlMapClientTemplate.queryForObject("memberCollect.getMemberCollectByPagerCount",collect);
		collect.setList(list);
		collect.setTotalCount(count);
		return collect;
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("memberCollect.updateStateById",params);
		return true;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public MemberCollect getMemberCollectById(Integer id) {
		return (MemberCollect)sqlMapClientTemplate.queryForObject("memberCollect.selectByPrimaryKey",id);
	}

	@Override
	public List<MemberCollect> getMemberCollectByMerchantId(String merchantId) {
		return sqlMapClientTemplate.queryForList("memberCollect.getMemberCollectByMerchantId",merchantId);
	}

	@Override
	public List<MemberCollect> getMemberCollectByUserId(String userId) {
		return sqlMapClientTemplate.queryForList("memberCollect.getMemberCollectByUserId",userId);
	}

}
