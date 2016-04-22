package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.MerchantUserSellerDao;
import com.froad.CB.po.MerchantUserSeller;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-6-3  
 * @version 1.0
 */
public class MerchantUserSellerDaoImpl implements MerchantUserSellerDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addMerchantUserSeller(MerchantUserSeller merchantUserSeller) {
		return (Integer) sqlMapClientTemplate.insert("merchantUserSeller.addMerchantUserSeller",merchantUserSeller);
	}

	@Override
	public List<MerchantUserSeller> getMerchantUserSellers(
			MerchantUserSeller merchantUserSeller) {
		List<MerchantUserSeller> record = sqlMapClientTemplate.queryForList("merchantUserSeller.selectMerchantUserSellerSeletive",merchantUserSeller);
		return record;
	}

	@Override
	public Integer updateByMerchantSeller(MerchantUserSeller merchantUserSeller) {
		Integer rows = sqlMapClientTemplate.update("merchantUserSeller.updateByMerchantSeller", merchantUserSeller);
        return rows;
	}

	@Override
	public Integer deleteByMerchantSeller(MerchantUserSeller merchantUserSeller) {
		Integer rows = sqlMapClientTemplate.update("merchantUserSeller.updateByMerchantSeller", merchantUserSeller);
        return rows;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
}
