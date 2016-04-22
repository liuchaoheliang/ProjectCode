package com.froad.CB.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.GoodsRebateRackDao;
import com.froad.CB.po.GoodsRebateRack;

public class GoodsRebateRackDaoImpl implements GoodsRebateRackDao{
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	@Override
	public Integer addGoodsRebateRack(GoodsRebateRack goodsRebateRack) {
		return (Integer)sqlMapClientTemplate.insert("goodsRebateRack.insert",goodsRebateRack);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("goodsRebateRack.deleteByPrimaryKey",id);
	}

	@Override
	public GoodsRebateRack getGoodsRebateRackById(Integer id) {
		return (GoodsRebateRack)sqlMapClientTemplate.queryForObject("goodsRebateRack.selectByPrimaryKey",id);
	}

	@Override
	public void updateById(GoodsRebateRack goodsRebateRack) {
		sqlMapClientTemplate.update("goodsRebateRack.updateById",goodsRebateRack);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id",id);
		params.put("state", state);
		sqlMapClientTemplate.update("goodsRebateRack.updateStateById",params);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public GoodsRebateRack getGoodsRebateRackByPager(
			GoodsRebateRack goodsRebateRack) {
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("goodsRebateRack.getByPagerCount",goodsRebateRack);
		List list=sqlMapClientTemplate.queryForList("goodsRebateRack.getByPager",goodsRebateRack);
		goodsRebateRack.setTotalCount(totalCount);
		goodsRebateRack.setList(list);
		return goodsRebateRack;
	}

}
