package com.froad.CB.dao.impl;

import java.util.List;

import org.apache.openjpa.lib.log.Log;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.MerchantUserSetDao;
import com.froad.CB.po.MerchantUserSet;

public class MerchantUserSetDaoImpl implements MerchantUserSetDao{

	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer addMerchantUserSet(MerchantUserSet merchantUserSet) {
		return (Integer) sqlMapClientTemplate.insert("merchantUserSet.addMerchantUserSet",merchantUserSet);
	}
	
	@Override
	public List<MerchantUserSet> getMerchantClerk(MerchantUserSet merchantUserSet){
		List<MerchantUserSet> record = sqlMapClientTemplate.queryForList("merchantUserSet.selectClerkSeletive",merchantUserSet);
		return record;
	}
	
	@Override
	public String getMaxClerkBeCode(MerchantUserSet merchantUserSet){
		return (String)sqlMapClientTemplate.queryForObject("merchantUserSet.selectClerkMAXBeCode",merchantUserSet);
	}
	
	
	@Override
	public Integer updateByUserIdAndBecode(MerchantUserSet merchantUserSet){
		Integer rows = sqlMapClientTemplate.update("merchantUserSet.updateByUserIdAndBecode", merchantUserSet);
        return rows;
	}
	
	@Override
	public Integer updatePwdByUserIdAndBecode(MerchantUserSet merchantUserSet){
		Integer rows = sqlMapClientTemplate.update("merchantUserSet.updClerkPassword", merchantUserSet);
        return rows;
	}
	
	@Override
	public Integer deleteByUserIdAndBecode(MerchantUserSet merchantUserSet){
        Integer rows = sqlMapClientTemplate.update("merchantUserSet.updateByUserIdAndBecode", merchantUserSet);
        return rows;
	}
	
	@Override
	public MerchantUserSet getMerchantUserSetListByPager(MerchantUserSet merchantUserSet) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("merchantUserSet.getMerchantUserSetListByPagerCount",merchantUserSet);
		List<MerchantUserSet> list = sqlMapClientTemplate.queryForList("merchantUserSet.getMerchantUserSetListByPager", merchantUserSet);
		merchantUserSet.setTotalCount(totalCount);
		merchantUserSet.setList(list);
		return merchantUserSet;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	
	@Override
	public String getBelongUserBecode(String storeId) {
		if(storeId == null || "".equals(storeId)){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		List<?> userBecodes = sqlMapClientTemplate.queryForList("merchantUserSet.getBelongUserBecode", storeId);
		if(userBecodes == null || userBecodes.size() == 0){
			return null;
		}else{						
			int size = userBecodes.size()-1;
			int index = 0;
			for (Object object : userBecodes) {
				sb.append("'");
				sb.append(object);
				sb.append("'");
				if(index != size){
					sb.append(",");
				}
				index ++;
			}
		}
		return sb.toString();
	}

}
