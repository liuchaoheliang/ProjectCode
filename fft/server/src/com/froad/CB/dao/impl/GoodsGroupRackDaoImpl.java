package com.froad.CB.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.GoodsGroupRackDao;
import com.froad.CB.po.GoodsGroupRack;

public class GoodsGroupRackDaoImpl implements GoodsGroupRackDao{
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Integer addGoodsGroupRack(GoodsGroupRack goodsGroupRack) {
		return (Integer)sqlMapClientTemplate.insert("goodsGroupRack.insert",goodsGroupRack);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("goodsGroupRack.deleteByPrimaryKey",id);
	}

	@Override
	public GoodsGroupRack getGoodsGroupRackById(Integer id) {
		return (GoodsGroupRack)sqlMapClientTemplate.queryForObject("goodsGroupRack.selectByPrimaryKey",id);
	}

	@Override
	public void updateById(GoodsGroupRack goodsGroupRack) {
		sqlMapClientTemplate.update("goodsGroupRack.updateById",goodsGroupRack);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		HashMap<String,Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("goodsGroupRack.updateStateById",params);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public GoodsGroupRack getGoodsGroupRackByPager(GoodsGroupRack goodsGroupRack) {
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("goodsGroupRack.getByPagerCount",goodsGroupRack);
		List list=sqlMapClientTemplate.queryForList("goodsGroupRack.getByPager",goodsGroupRack);
		goodsGroupRack.setTotalCount(totalCount);
		goodsGroupRack.setList(list);
		return goodsGroupRack;
	}
	
	
	@Override
	public void addSaleNumberById(int addNumber, Integer id) {
		HashMap<String, Integer> params=new HashMap<String, Integer>();
		params.put("num", addNumber);
		params.put("id", id);
		sqlMapClientTemplate.update("goodsGroupRack.addSaleNumberById",params);
	}

	@Override
	public List<GoodsGroupRack> getIndexGoodsRack() {
		return sqlMapClientTemplate.queryForList("goodsGroupRack.getIndexGoodsRack");
	}

}
