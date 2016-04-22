package com.froad.CB.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.GoodsPresellRackDao;
import com.froad.CB.po.GoodsPresellRack;
public class GoodsPresellRackDaoImpl implements GoodsPresellRackDao {
	
	private SqlMapClientTemplate sqlMapClientTemplate; 
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public Integer add(GoodsPresellRack goodsPresellRack) {
		return (Integer) sqlMapClientTemplate.insert("goodsPresellRack.insert", goodsPresellRack);
	}

	@Override
	public boolean updateById(GoodsPresellRack goodsPresellRack) {
		int n=0;
		n=sqlMapClientTemplate.update("goodsPresellRack.updateById", goodsPresellRack);
		if(n>0){
			return true;
		}
		return false;
	}

	@Override
	public GoodsPresellRack getById(Integer id) {
		return (GoodsPresellRack) sqlMapClientTemplate.queryForObject("goodsPresellRack.selectByPrimaryKey", id);
	}

	@Override
	public List<GoodsPresellRack> getByConditions(
			GoodsPresellRack goodsPresellRack) {
		return sqlMapClientTemplate.queryForList("goodsPresellRack.getByConditions", goodsPresellRack);
	}

	@Override
	public GoodsPresellRack getByPager(GoodsPresellRack goodsPresellRack) {
		int count=(Integer) sqlMapClientTemplate.queryForObject("goodsPresellRack.getByPagerCount", goodsPresellRack);
		List<GoodsPresellRack> list=sqlMapClientTemplate.queryForList("goodsPresellRack.getByPager", goodsPresellRack);
		goodsPresellRack.setTotalCount(count);
		goodsPresellRack.setList(list);	
		return goodsPresellRack;
	}
	
	

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<GoodsPresellRack> getHistory(GoodsPresellRack goodsPresellRack) {
		goodsPresellRack.setEndTime(sdf.format(new Date()));		
		return sqlMapClientTemplate.queryForList("goodsPresellRack.getHistory", goodsPresellRack);
	}

	
}
