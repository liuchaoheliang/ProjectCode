package com.froad.CB.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.GoodsCategoryDao;
import com.froad.CB.po.GoodsCategory;
import com.froad.util.DateUtil;

/** 
 * @author FQ 
 * @date 2013-1-29 下午04:41:46
 * @version 1.0
 * 
 */
public class GoodsCategoryDaoImpl implements GoodsCategoryDao {

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addGoodsCategory(GoodsCategory goodsCategory) {
		return (Integer) sqlMapClientTemplate.insert("goodsCategory.addGoodsCategory", goodsCategory);
	}

	@Override
	public List<GoodsCategory> getAllGoodsCategory() {
		return sqlMapClientTemplate.queryForList("goodsCategory.getAllGoodsCategory");
	}

	@Override
	public List<GoodsCategory> getChildrenGoodsCategoryList(
			GoodsCategory goodsCategory) {
		return sqlMapClientTemplate.queryForList("goodsCategory.getChildrenGoodsCategoryList", goodsCategory);
	}

	@Override
	public GoodsCategory getGoodsCategoryById(Integer id) {
		return (GoodsCategory) sqlMapClientTemplate.queryForObject("goodsCategory.getGoodsCategoryById",id);
	}

	@Override
	public List<GoodsCategory> getRootGoodsCategoryList() {
		return sqlMapClientTemplate.queryForList("goodsCategory.getRootGoodsCategoryList");
	}
	
	@Override
	public boolean updateGoodsCategory(GoodsCategory goodsCategory) {
		sqlMapClientTemplate.update("goodsCategory.updateGoodsCategory", goodsCategory);
		return true;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
}
