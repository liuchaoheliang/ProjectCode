package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.GoodsGatherRackDao;
import com.froad.CB.po.GoodsGatherRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-31  
 * @version 1.0
 */
public class GoodsGatherRackDaoImpl implements GoodsGatherRackDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer insert(GoodsGatherRack goodsGatherRack) {
    	return (Integer) sqlMapClientTemplate.insert("goodsGatherRack.addGoodsGatherRack", goodsGatherRack);
	}

	@Override
	public Integer updateById(GoodsGatherRack goodsGatherRack) {
        Integer rows = sqlMapClientTemplate.update("goodsGatherRack.updateGoodsGatherRackById", goodsGatherRack);
        return rows;
	}

	@Override
	public GoodsGatherRack selectByPrimaryKey(Integer id) {
       return (GoodsGatherRack) sqlMapClientTemplate.queryForObject("goodsGatherRack.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		GoodsGatherRack key = new GoodsGatherRack();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("goodsGatherRack.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		GoodsGatherRack key = new GoodsGatherRack();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("goodsGatherRack.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public List<GoodsGatherRack> getGoodsGatherRackByGoodsId(String goodsId) {
		List<GoodsGatherRack> goodsGatherRacks = sqlMapClientTemplate.queryForList("goodsGatherRack.getGoodsGatherRackByGoodsId",goodsId);
		return goodsGatherRacks;
	}

	@Override
	public List<GoodsGatherRack> getGoodsGatherRack(GoodsGatherRack goodsGatherRack) {
		List<GoodsGatherRack> record = sqlMapClientTemplate.queryForList("goodsGatherRack.getGoodsGatherRackBySelective",goodsGatherRack);
		return record;
	}

	@Override
	public GoodsGatherRack getGoodsGatherRackListByPager(
			GoodsGatherRack goodsGatherRack) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("goodsGatherRack.getGoodsGatherRackListByPagerCount",goodsGatherRack);
		List<GoodsGatherRack> list = sqlMapClientTemplate.queryForList("goodsGatherRack.getGoodsGatherRackListByPager", goodsGatherRack);
		goodsGatherRack.setTotalCount(totalCount);
		goodsGatherRack.setList(list);
		return goodsGatherRack;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
}
