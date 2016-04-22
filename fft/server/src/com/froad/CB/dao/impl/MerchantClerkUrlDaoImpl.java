package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.MerchantClerkUrlDao;
import com.froad.CB.po.MerchantClerkUrl;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-23  
 * @version 1.0
 */
public class MerchantClerkUrlDaoImpl implements MerchantClerkUrlDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addMerchantClerkUrl(MerchantClerkUrl merchantClerkUrl) {
		return (Integer) sqlMapClientTemplate.insert("merchantClerkUrl.addMerchantClerkUrl",merchantClerkUrl);
	}

	@Override
	public List<MerchantClerkUrl> getMerchantClerkUrl() {
		List<MerchantClerkUrl> record = sqlMapClientTemplate.queryForList("merchantClerkUrl.selectAll");
		return record;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
