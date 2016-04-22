package com.froad.CB.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.BrandDao;
import com.froad.CB.po.Brand;
import com.froad.util.DateUtil;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:16:28
 * @version 1.0
 * 
 */
public class BrandDaoImpl implements BrandDao {
	
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addBrand(Brand brand) {
		return (Integer) sqlMapClientTemplate.insert("brand.addBrand", brand);
	}

	@Override
	public List<Brand> getAllBrand() {
		return sqlMapClientTemplate.queryForList("brand.getAllBrand");
	}

	@Override
	public Brand getBrandById(Integer id) {
		return (Brand) sqlMapClientTemplate.queryForObject("brand.getBrandById", id);
	}

	@Override
	public List<Brand> getBrandList(Brand brand) {
		return sqlMapClientTemplate.queryForList("brand.getBrandList", brand);
	}

	@Override
	public boolean updateBrand(Brand brand) {
		sqlMapClientTemplate.update("brand.updateBrand", brand);
		return true;
	}
}
