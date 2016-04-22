package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.SellerDao;
import com.froad.CB.po.Seller;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-30  
 * @version 1.0
 */
public class SellerDaoImpl implements SellerDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer insert(Seller seller) {
    	return (Integer) sqlMapClientTemplate.insert("seller.addSeller", seller);
	}

	@Override
	public Integer updateById(Seller seller) {
        Integer rows = sqlMapClientTemplate.update("seller.updateSellerById", seller);
        return rows;
	}

	@Override
	public Seller selectByPrimaryKey(Integer id) {
       return (Seller)sqlMapClientTemplate.queryForObject("seller.selectByPrimaryKey", id);
	}

	@Override
	public Integer deleteByPrimaryKey(String id) {
		Seller key = new Seller();        
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.delete("seller.deleteByPrimaryKey", key);
        return rows;
	}

	@Override
	public Integer deleteStateByPrimaryKey(String id) {
		Seller key = new Seller();
        key.setId(Integer.valueOf(id));
        Integer rows = sqlMapClientTemplate.update("seller.deleteStateByPrimaryKey", key);
        return rows;
	}

	@Override
	public List<Seller> getSellerByUserId(String userId) {
		return sqlMapClientTemplate.queryForList("seller.getSellerByUserId",userId);
	}

	@Override
	public List<Seller> getSellerByMerchantId(String merchantId) {
		return sqlMapClientTemplate.queryForList("seller.getSellerByMerchantId",merchantId);
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<Seller> getBySelective(Seller seller) {
		return sqlMapClientTemplate.queryForList("seller.selectBySelective",seller);
	}
	
}
