package com.froad.CB.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.GoodsDao;
import com.froad.CB.po.Goods;

public class GoodsDaoImpl implements GoodsDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addGoods(Goods goods) {
		return (Integer)sqlMapClientTemplate.insert("goods.insert",goods);
	}

	@Override
	public void deleteById(Integer id) {
		sqlMapClientTemplate.delete("goods.deleteById",id);
	}

	@Override
	public Goods getGoodsById(Integer id) {
		return (Goods)sqlMapClientTemplate.queryForObject("goods.selectById",id);
	}

	@Override
	public void updateById(Goods goods) {
		sqlMapClientTemplate.update("goods.updateById",goods);
	}

	@Override
	public void updateStateById(Integer id, String state) {
		HashMap<String, Object> params=new HashMap<String, Object>();
		params.put("id",id);
		params.put("state", state);
		sqlMapClientTemplate.update("goods.updateStateById",params);
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<Goods> getGoodsByMerchantId(String merchantId) {
		return sqlMapClientTemplate.queryForList("goods.getGoodsByMerchantId",merchantId);
	}

	@Override
	public Goods getGoodsByPager(Goods goods) {
		Integer totalCount=(Integer)sqlMapClientTemplate.queryForObject("goods.getByPagerCount",goods);
		List list=sqlMapClientTemplate.queryForList("goods.getByPager",goods);
		goods.setTotalCount(totalCount);
		goods.setList(list);
		return goods;
	}

	@Override
	public void reduceStoreById(int reduceNumber, Integer id) {
		HashMap<String, Integer> params=new HashMap<String, Integer>();
		params.put("num", reduceNumber);
		params.put("id", id);
		sqlMapClientTemplate.update("goods.reduceStoreById",params);
	}

	@Override
	public int getStoreById(Integer id) {
		Object obj=sqlMapClientTemplate.queryForObject("goods.getStoreById",id);
		if(obj==null){
			return 0;
		}
		return (Integer)obj;
	}

	@Override
	public String useGoodsRackIdAndTableGetGoodsName(Goods goods) {
		Object obj = sqlMapClientTemplate.queryForObject("goods.getGoodsInfoByGoodsRackId",goods);
		return (String)obj;
	}

}
