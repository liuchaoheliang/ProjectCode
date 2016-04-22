package com.froad.CB.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.GoodsTypeDao;
import com.froad.CB.po.GoodsType;
import com.froad.util.DateUtil;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:20:03
 * @version 1.0
 * 
 */
public class GoodsTypeDaoImpl implements GoodsTypeDao {

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	@Override
	public Integer addGoodsType(GoodsType goodsType) {
		return (Integer) sqlMapClientTemplate.insert("goodsType.addGoodsType", goodsType);
	}

	@Override
	public List<GoodsType> getGoodsTypeList(GoodsType goodsType) {
		return sqlMapClientTemplate.queryForList("goodsType.getGoodsTypeList", goodsType);
	}

	@Override
	public GoodsType getGoodsTypeById(Integer id) {
		return (GoodsType) sqlMapClientTemplate.queryForObject("goodsType.getGoodsTypeById", id);
	}

	@Override
	public boolean updateGoodsType(GoodsType goodsType) {
		sqlMapClientTemplate.update("goodsType.updateGoodsType", goodsType);
		return true;
	}

	@Override
	public List<GoodsType> getAllGoodsType() {
		return sqlMapClientTemplate.queryForList("goodsType.getAllGoodsType");
	}

}
