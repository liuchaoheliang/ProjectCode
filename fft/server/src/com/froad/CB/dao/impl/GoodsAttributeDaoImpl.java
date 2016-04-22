package com.froad.CB.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.GoodsAttributeDao;
import com.froad.CB.po.GoodsAttribute;
import com.froad.util.DateUtil;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:17:45
 * @version 1.0
 * 
 */
public class GoodsAttributeDaoImpl implements GoodsAttributeDao {

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	@Override
	public Integer addGoodsAttribute(GoodsAttribute goodsAttribute) {
		return (Integer) sqlMapClientTemplate.insert("goodsAttribute.addGoodsAttribute", goodsAttribute);
	}

	@Override
	public GoodsAttribute getGoodsAttributeById(Integer id) {
		return (GoodsAttribute) sqlMapClientTemplate.queryForObject("goodsAttribute.getGoodsAttributeById", id);
	}

	@Override
	public List<GoodsAttribute> getGoodsAttributeList(
			GoodsAttribute goodsAttribute) {
		return sqlMapClientTemplate.queryForList("goodsAttribute.getGoodsAttributeList", goodsAttribute);
	}

	@Override
	public boolean updateGoodsAttribute(GoodsAttribute goodsAttribute) {
		sqlMapClientTemplate.update("goodsAttribute.updateGoodsAttribute", goodsAttribute);
		return true;
	}

}
