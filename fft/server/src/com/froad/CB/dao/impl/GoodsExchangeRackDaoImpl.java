package com.froad.CB.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.GoodsExchangeRackDao;
import com.froad.CB.po.GoodsExchangeRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-31  
 * @version 1.0
 * 商品兑换架DAO
 */
public class GoodsExchangeRackDaoImpl implements GoodsExchangeRackDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer insert(GoodsExchangeRack goodsExchangeRack) {
    	return (Integer) sqlMapClientTemplate.insert("goodsExchangeRack.addGoodsExchangeRack", goodsExchangeRack);
	}

	@Override
	public Integer updateById(GoodsExchangeRack goodsExchangeRack) {
        Integer rows = sqlMapClientTemplate.update("goodsExchangeRack.updateGoodsExchangeRackById", goodsExchangeRack);
        return rows;
	}

	@Override
	public GoodsExchangeRack selectByPrimaryKey(Integer id) {
        return (GoodsExchangeRack) sqlMapClientTemplate.queryForObject("goodsExchangeRack.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		GoodsExchangeRack key = new GoodsExchangeRack();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("goodsExchangeRack.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		GoodsExchangeRack key = new GoodsExchangeRack();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("goodsExchangeRack.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public List<GoodsExchangeRack> getGoodsExchangeRackByGoodsId(String goodsId) {
		List<GoodsExchangeRack> goodsExchangeRacks = sqlMapClientTemplate.queryForList("goodsExchangeRack.getGoodsExchangeRackByGoodsId",goodsId);
		return goodsExchangeRacks;
	}

	@Override
	public List<GoodsExchangeRack> getGoodsExchangeRack(
			GoodsExchangeRack goodsExchangeRack) {
		List<GoodsExchangeRack> record = sqlMapClientTemplate.queryForList("goodsExchangeRack.getGoodsExchangeRackBySelective",goodsExchangeRack);
		return record;
	}

	@Override
	public GoodsExchangeRack getGoodsExchangeRackListByPager(
			GoodsExchangeRack goodsExchangeRack) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("goodsExchangeRack.getGoodsExchangeRackListByPagerCount",goodsExchangeRack);
		List<GoodsExchangeRack> list = sqlMapClientTemplate.queryForList("goodsExchangeRack.getGoodsExchangeRackListByPager", goodsExchangeRack);
		goodsExchangeRack.setTotalCount(totalCount);
		goodsExchangeRack.setList(list);
		return goodsExchangeRack;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	
	@Override
	public void addSaleNumberById(int addNumber, Integer id) {
		HashMap<String, Integer> params=new HashMap<String, Integer>();
		params.put("num", addNumber);
		params.put("id", id);
		sqlMapClientTemplate.update("goodsExchangeRack.addSaleNumberById",params);
	}

	@Override
	public List<GoodsExchangeRack> getIndexGoodsRack() {
		return sqlMapClientTemplate.queryForList("goodsExchangeRack.getIndexGoodsRack");
	}
}
