package com.froad.CB.dao.impl;

import java.util.HashMap;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.ClientGoodsExchangeRackDao;
import com.froad.CB.po.ClientGoodsExchangeRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 */
public class ClientGoodsExchangeRackDaoImpl implements
		ClientGoodsExchangeRackDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer insert(ClientGoodsExchangeRack ClientGoodsExchangeRack) {
    	return (Integer) sqlMapClientTemplate.insert("clientGoodsExchangeRack.addClientGoodsExchangeRack", ClientGoodsExchangeRack);
	}

	@Override
	public Integer updateById(ClientGoodsExchangeRack ClientGoodsExchangeRack) {
        Integer rows = sqlMapClientTemplate.update("clientGoodsExchangeRack.updateClientGoodsExchangeRackById", ClientGoodsExchangeRack);
        return rows;
	}

	@Override
	public ClientGoodsExchangeRack selectByPrimaryKey(Integer id) {
        return (ClientGoodsExchangeRack) sqlMapClientTemplate.queryForObject("clientGoodsExchangeRack.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		ClientGoodsExchangeRack key = new ClientGoodsExchangeRack();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("clientGoodsExchangeRack.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		ClientGoodsExchangeRack key = new ClientGoodsExchangeRack();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("clientGoodsExchangeRack.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public List<ClientGoodsExchangeRack> getClientGoodsExchangeRackByGoodsId(String goodsId) {
		List<ClientGoodsExchangeRack> ClientGoodsExchangeRacks = sqlMapClientTemplate.queryForList("clientGoodsExchangeRack.getClientGoodsExchangeRackByGoodsId",goodsId);
		return ClientGoodsExchangeRacks;
	}

	@Override
	public List<ClientGoodsExchangeRack> getClientGoodsExchangeRack(
			ClientGoodsExchangeRack ClientGoodsExchangeRack) {
		List<ClientGoodsExchangeRack> record = sqlMapClientTemplate.queryForList("clientGoodsExchangeRack.getClientGoodsExchangeRackBySelective",ClientGoodsExchangeRack);
		return record;
	}

	@Override
	public ClientGoodsExchangeRack getClientGoodsExchangeRackListByPager(
			ClientGoodsExchangeRack ClientGoodsExchangeRack) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("clientGoodsExchangeRack.getClientGoodsExchangeRackListByPagerCount",ClientGoodsExchangeRack);
		List<ClientGoodsExchangeRack> list = sqlMapClientTemplate.queryForList("clientGoodsExchangeRack.getClientGoodsExchangeRackListByPager", ClientGoodsExchangeRack);
		ClientGoodsExchangeRack.setTotalCount(totalCount);
		ClientGoodsExchangeRack.setList(list);
		return ClientGoodsExchangeRack;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<ClientGoodsExchangeRack> getClientGoodsExchangeRackByGoodsCategoryId(String goodsCategoryId) {
		return sqlMapClientTemplate.queryForList("clientGoodsExchangeRack.getClientGoodsExchangeRackByGoodsCategoryId",goodsCategoryId);
	}
	
	@Override
	public void addSaleNumberById(int addNumber, Integer id) {
		HashMap<String, Integer> params=new HashMap<String, Integer>();
		params.put("num", addNumber);
		params.put("id", id);
		sqlMapClientTemplate.update("clientGoodsExchangeRack.addSaleNumberById",params);
	}

}
