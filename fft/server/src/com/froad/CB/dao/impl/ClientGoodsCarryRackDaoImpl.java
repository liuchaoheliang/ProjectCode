package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.ClientGoodsCarryRackDao;
import com.froad.CB.po.ClientGoodsCarryRack;
import com.froad.CB.po.GoodsExchangeRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 */
public class ClientGoodsCarryRackDaoImpl implements ClientGoodsCarryRackDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer insert(ClientGoodsCarryRack ClientGoodsCarryRack) {
    	return (Integer) sqlMapClientTemplate.insert("clientGoodsCarryRack.addClientGoodsCarryRack", ClientGoodsCarryRack);
	}

	@Override
	public Integer updateById(ClientGoodsCarryRack ClientGoodsCarryRack) {
        Integer rows = sqlMapClientTemplate.update("clientGoodsCarryRack.updateClientGoodsCarryRackById", ClientGoodsCarryRack);
        return rows;
	}

	@Override
	public ClientGoodsCarryRack selectByPrimaryKey(Integer id) {
        return (ClientGoodsCarryRack) sqlMapClientTemplate.queryForObject("clientGoodsCarryRack.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		ClientGoodsCarryRack key = new ClientGoodsCarryRack();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("clientGoodsCarryRack.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		ClientGoodsCarryRack key = new ClientGoodsCarryRack();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("clientGoodsCarryRack.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public List<ClientGoodsCarryRack> getClientGoodsCarryRackByGoodsId(String goodsId) {
		List<ClientGoodsCarryRack> resultList = sqlMapClientTemplate.queryForList("clientGoodsCarryRack.getClientGoodsCarryRackByGoodsId",goodsId);
		return resultList;
	}

	@Override
	public List<ClientGoodsCarryRack> getClientGoodsCarryRack(ClientGoodsCarryRack ClientGoodsCarryRack) {
		List<ClientGoodsCarryRack> resultList = sqlMapClientTemplate.queryForList("clientGoodsCarryRack.getClientGoodsCarryRackBySelective",ClientGoodsCarryRack);
		return resultList;
	}

	@Override
	public ClientGoodsCarryRack getClientGoodsCarryRackListByPager(
			ClientGoodsCarryRack ClientGoodsCarryRack) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("clientGoodsCarryRack.getClientGoodsCarryRackListByPagerCount",ClientGoodsCarryRack);
		List<GoodsExchangeRack> list = sqlMapClientTemplate.queryForList("clientGoodsCarryRack.getClientGoodsCarryRackListByPager", ClientGoodsCarryRack);
		ClientGoodsCarryRack.setTotalCount(totalCount);
		ClientGoodsCarryRack.setList(list);
		return ClientGoodsCarryRack;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
