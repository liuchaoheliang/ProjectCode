package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.TempPointDao;
import com.froad.CB.po.TempPoint;

public class TempPointDaoImpl implements TempPointDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer add(TempPoint point) {
		return (Integer)sqlMapClientTemplate.insert("tempPoint.insert",point);
	}

	@Override
	public boolean deleteById(Integer id) {
		sqlMapClientTemplate.delete("tempPoint.deleteByPrimaryKey",id);
		return true;
	}

	@Override
	public TempPoint getTempPointById(Integer id) {
		return (TempPoint)sqlMapClientTemplate.queryForObject("tempPoint.selectByPrimaryKey",id);
	}

	@Override
	public boolean updateById(TempPoint point) {
		Integer count = sqlMapClientTemplate.update("tempPoint.updateById",point);
		return count!=null && count>0?true:false;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<TempPoint> getTempPointBySelective(TempPoint point) {
		return sqlMapClientTemplate.queryForList("tempPoint.getTempPointBySelective",point);
	}

	@Override
	public TempPoint getTempPointByPager(TempPoint point) {
		List list=sqlMapClientTemplate.queryForList("tempPoint.getTempPointByPager",point);
		Integer count=(Integer)sqlMapClientTemplate.queryForObject("tempPoint.getTempPointByPagerCount",point);
		point.setList(list);
		point.setTotalCount(count);
		return point;
	}

}
