package com.froad.CB.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.ClientGoodsRebateRackDao;
import com.froad.CB.po.ClientGoodsRebateRack;

public class ClientGoodsRebateRackDaoImpl implements ClientGoodsRebateRackDao{
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Integer addClientGoodsRebateRack(ClientGoodsRebateRack goodsRebateRack) {
		return (Integer)sqlMapClientTemplate.insert("clientGoodsRebateRack.insert",goodsRebateRack);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("clientGoodsRebateRack.deleteByPrimaryKey",id);
	}

	@Override
	public ClientGoodsRebateRack getClientGoodsRebateRackById(Integer id) {
		return (ClientGoodsRebateRack)sqlMapClientTemplate.queryForObject("clientGoodsRebateRack.selectByPrimaryKey",id);
	}

	@Override
	public void updateById(ClientGoodsRebateRack goodsRebateRack) {
		sqlMapClientTemplate.update("clientGoodsRebateRack.updateById",goodsRebateRack);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id",id);
		params.put("state", state);
		sqlMapClientTemplate.update("clientGoodsRebateRack.updateStateById",params);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public ClientGoodsRebateRack getGoodsRebateRackByPager(
			ClientGoodsRebateRack clientGoodsRebateRack) {
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("clientGoodsRebateRack.getByPagerCount",clientGoodsRebateRack);
		List list=sqlMapClientTemplate.queryForList("clientGoodsRebateRack.getByPager",clientGoodsRebateRack);
		clientGoodsRebateRack.setTotalCount(totalCount);
		clientGoodsRebateRack.setList(list);
		return clientGoodsRebateRack;
	}

}
