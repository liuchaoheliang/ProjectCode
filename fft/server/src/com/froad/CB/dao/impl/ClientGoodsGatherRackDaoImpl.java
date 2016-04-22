package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.ClientGoodsGatherRackDao;
import com.froad.CB.po.ClientGoodsGatherRack;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-7  
 * @version 1.0
 */
public class ClientGoodsGatherRackDaoImpl implements ClientGoodsGatherRackDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer insert(ClientGoodsGatherRack ClientGoodsGatherRack) {
    	return (Integer) sqlMapClientTemplate.insert("clientGoodsGatherRack.addClientGoodsGatherRack", ClientGoodsGatherRack);
	}

	@Override
	public Integer updateById(ClientGoodsGatherRack ClientGoodsGatherRack) {
        Integer rows = sqlMapClientTemplate.update("clientGoodsGatherRack.updateClientGoodsGatherRackById", ClientGoodsGatherRack);
        return rows;
	}

	@Override
	public ClientGoodsGatherRack selectByPrimaryKey(Integer id) {
       return (ClientGoodsGatherRack) sqlMapClientTemplate.queryForObject("clientGoodsGatherRack.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		ClientGoodsGatherRack key = new ClientGoodsGatherRack();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("clientGoodsGatherRack.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		ClientGoodsGatherRack key = new ClientGoodsGatherRack();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("clientGoodsGatherRack.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public List<ClientGoodsGatherRack> getClientGoodsGatherRackByGoodsId(String goodsId) {
		List<ClientGoodsGatherRack> ClientGoodsGatherRacks = sqlMapClientTemplate.queryForList("clientGoodsGatherRack.getClientGoodsGatherRackByGoodsId",goodsId);
		return ClientGoodsGatherRacks;
	}

	@Override
	public List<ClientGoodsGatherRack> getClientGoodsGatherRack(ClientGoodsGatherRack ClientGoodsGatherRack) {
		List<ClientGoodsGatherRack> record = sqlMapClientTemplate.queryForList("clientGoodsGatherRack.getClientGoodsGatherRackBySelective",ClientGoodsGatherRack);
		return record;
	}

	@Override
	public ClientGoodsGatherRack getClientGoodsGatherRackListByPager(
			ClientGoodsGatherRack ClientGoodsGatherRack) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("clientGoodsGatherRack.getClientGoodsGatherRackListByPagerCount",ClientGoodsGatherRack);
		List<ClientGoodsGatherRack> list = sqlMapClientTemplate.queryForList("clientGoodsGatherRack.getClientGoodsGatherRackListByPager", ClientGoodsGatherRack);
		ClientGoodsGatherRack.setTotalCount(totalCount);
		ClientGoodsGatherRack.setList(list);
		return ClientGoodsGatherRack;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
