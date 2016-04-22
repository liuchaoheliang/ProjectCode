package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.GoodsPresellRackImagesDao;
import com.froad.CB.po.GoodsPresellRackImages;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;

public class GoodsPresellRackImagesDaoImpl implements GoodsPresellRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate ;	
	
	@Override
	public Integer add(GoodsPresellRackImages goodsPresellRackImages) {
		return (Integer) sqlMapClientTemplate.insert("goodsPresellRackImages.insert", goodsPresellRackImages);
	}

	@Override
	public boolean updateById(GoodsPresellRackImages goodsPresellRackImages) {
		int n=0;
		n=sqlMapClientTemplate.update("goodsPresellRackImages.updateById",goodsPresellRackImages);
		if(n>0){
			return true;
		}
		return false;
	}

	@Override
	public GoodsPresellRackImages getById(Integer id) {
		return (GoodsPresellRackImages) sqlMapClientTemplate.queryForObject("goodsPresellRackImages.getById", id);
	}

	@Override
	public List<GoodsPresellRackImages> getByConditions(
			GoodsPresellRackImages goodsPresellRackImages) {
		return sqlMapClientTemplate.queryForList("goodsPresellRackImages.getByConditions", goodsPresellRackImages);
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public boolean updateObject(GoodsPresellRackImages goodsPresellRackImages) {
		int n=0;
		n=sqlMapClientTemplate.update("goodsPresellRackImages.updateObject",goodsPresellRackImages);
		if(n>0){
			return true;
		}
		return false;
	}



	
}
