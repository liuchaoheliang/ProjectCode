package com.froad.CB.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.GoodsRackAttributeDao;
import com.froad.CB.po.GoodsRackAttribute;

public class ClientGoodsRackAttributeDaoImpl implements GoodsRackAttributeDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addGoodsRackAttribute(GoodsRackAttribute goodsRackAttribute) {
		return (Integer)sqlMapClientTemplate.insert("clientGoodsRackAttribute.insert",goodsRackAttribute);
	}

	@Override
	public boolean deleteById(Integer id) {
		sqlMapClientTemplate.delete("clientGoodsRackAttribute.deleteByPrimaryKey",id);
		return true;
	}

	@Override
	public GoodsRackAttribute getGoodsRackAttributeById(Integer id) {
		return (GoodsRackAttribute)sqlMapClientTemplate.queryForObject("clientGoodsRackAttribute.selectByPrimaryKey",id);
	}

	@Override
	public boolean updateById(GoodsRackAttribute goodsRackAttribute) {
		sqlMapClientTemplate.update("clientGoodsRackAttribute.updateById",goodsRackAttribute);
		return true;
	}

	@Override
	public boolean updateStateById(Integer id, String state) {
		HashMap<String,Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("state", state);
		sqlMapClientTemplate.update("clientGoodsRackAttribute.updateStateById",params);
		return true;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<GoodsRackAttribute> getGoodsRackAttributeList() {
		return sqlMapClientTemplate.queryForList("clientGoodsRackAttribute.selectAll");
	}

}
