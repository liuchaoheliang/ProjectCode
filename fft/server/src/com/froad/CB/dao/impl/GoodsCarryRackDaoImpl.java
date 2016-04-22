package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.GoodsCarryRackDao;
import com.froad.CB.po.GoodsCarryRack;
import com.froad.CB.po.GoodsExchangeRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-31  
 * @version 1.0
 */
public class GoodsCarryRackDaoImpl implements GoodsCarryRackDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer insert(GoodsCarryRack goodsCarryRack) {
    	return (Integer) sqlMapClientTemplate.insert("goodsCarryRack.addGoodsCarryRack", goodsCarryRack);
	}

	@Override
	public Integer updateById(GoodsCarryRack goodsCarryRack) {
        Integer rows = sqlMapClientTemplate.update("goodsCarryRack.updateGoodsCarryRackById", goodsCarryRack);
        return rows;
	}

	@Override
	public GoodsCarryRack selectByPrimaryKey(Integer id) {
        return (GoodsCarryRack) sqlMapClientTemplate.queryForObject("goodsCarryRack.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		GoodsCarryRack key = new GoodsCarryRack();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("goodsCarryRack.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		GoodsCarryRack key = new GoodsCarryRack();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("goodsCarryRack.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public List<GoodsCarryRack> getGoodsCarryRackByGoodsId(String goodsId) {
		List<GoodsCarryRack> resultList = sqlMapClientTemplate.queryForList("goodsCarryRack.getGoodsCarryRackByGoodsId",goodsId);
		return resultList;
	}

	@Override
	public List<GoodsCarryRack> getGoodsCarryRack(GoodsCarryRack goodsCarryRack) {
		List<GoodsCarryRack> resultList = sqlMapClientTemplate.queryForList("goodsCarryRack.getGoodsCarryRackBySelective",goodsCarryRack);
		return resultList;
	}

	@Override
	public GoodsCarryRack getGoodsCarryRackListByPager(
			GoodsCarryRack goodsCarryRack) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("goodsCarryRack.getGoodsCarryRackListByPagerCount",goodsCarryRack);
		List<GoodsExchangeRack> list = sqlMapClientTemplate.queryForList("goodsCarryRack.getGoodsCarryRackListByPager", goodsCarryRack);
		goodsCarryRack.setTotalCount(totalCount);
		goodsCarryRack.setList(list);
		return goodsCarryRack;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
