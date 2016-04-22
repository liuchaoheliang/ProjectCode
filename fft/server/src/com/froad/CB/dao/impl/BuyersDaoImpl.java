package com.froad.CB.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.BuyersDao;
import com.froad.CB.po.Buyers;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-30  
 * @version 1.0
 */
public class BuyersDaoImpl implements BuyersDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer insert(Buyers buyer) {
    	return (Integer) sqlMapClientTemplate.insert("buyers.insert", buyer);
	}

	@Override
	public Integer updateById(Buyers buyer) {
        Integer rows = sqlMapClientTemplate.update("buyers.updateBuyersById", buyer);
        return rows;
	}

	@Override
	public Buyers selectByPrimaryKey(Integer id) {
        return (Buyers) sqlMapClientTemplate.queryForObject("buyers.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		Buyers key = new Buyers();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("buyers.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		Buyers key = new Buyers();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("buyers.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public Buyers getBuyersByUserId(String userId) {		
		Buyers buyers = (Buyers)sqlMapClientTemplate.queryForObject("buyers.getBuyersByUserId",userId);
		return buyers;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
}
