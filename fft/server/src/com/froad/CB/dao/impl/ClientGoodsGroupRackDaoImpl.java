package com.froad.CB.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.ClientGoodsGroupRackDao;
import com.froad.CB.po.ClientGoodsGroupRack;
import com.froad.CB.po.GoodsGroupRack;

public class ClientGoodsGroupRackDaoImpl implements ClientGoodsGroupRackDao{
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Integer addGoodsGroupRack(ClientGoodsGroupRack goodsGroupRack) {
		return (Integer)sqlMapClientTemplate.insert("clientGoodsGroupRack.insert",goodsGroupRack);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("clientGoodsGroupRack.deleteByPrimaryKey",id);
	}

	@Override
	public ClientGoodsGroupRack getGoodsGroupRackById(Integer id) {
		return (ClientGoodsGroupRack)sqlMapClientTemplate.queryForObject("clientGoodsGroupRack.selectByPrimaryKey",id);
	}

	@Override
	public void updateById(ClientGoodsGroupRack goodsGroupRack) {
		sqlMapClientTemplate.update("clientGoodsGroupRack.updateById",goodsGroupRack);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		HashMap<String,Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("clientGoodsGroupRack.updateStateById",params);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	@Override
	public ClientGoodsGroupRack getClientGoodsGroupRackByPager(ClientGoodsGroupRack goodsGroupRack) {
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("clientGoodsGroupRack.getByPagerCount",goodsGroupRack);
		List list=sqlMapClientTemplate.queryForList("clientGoodsGroupRack.getByPager",goodsGroupRack);
		goodsGroupRack.setTotalCount(totalCount);
		goodsGroupRack.setList(list);
		return goodsGroupRack;
	}

	@Override
	public void addSaleNumberById(int addNumber, Integer id) {
		HashMap<String, Integer> params=new HashMap<String, Integer>();
		params.put("num", addNumber);
		params.put("id", id);
		sqlMapClientTemplate.update("clientGoodsGroupRack.addSaleNumberById",params);
	}
}
