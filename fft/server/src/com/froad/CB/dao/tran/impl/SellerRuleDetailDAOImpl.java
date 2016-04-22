package com.froad.CB.dao.tran.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.tran.SellerRuleDetailDAO;
import com.froad.CB.po.transaction.SellerRuleDetail;

public class SellerRuleDetailDAOImpl  implements SellerRuleDetailDAO {
	private SqlMapClientTemplate sqlMapClientTemplate;
    
//    public SellerRuleDetailDAOImpl(DaoManager daoManager) {
//        super(daoManager);
//    }

    
    public void insert(SellerRuleDetail record) {
    	sqlMapClientTemplate.insert("transSellerRuleDetail.insert", record);
    }

    
    public int updateByPrimaryKeySelective(SellerRuleDetail record) {
        int rows = sqlMapClientTemplate.update("transSellerRuleDetail.updateByPrimaryKeySelective", record);
        return rows;
    }

    
    public SellerRuleDetail selectByPrimaryKey(Integer id) {
        SellerRuleDetail key = new SellerRuleDetail();
        key.setId(id);
        SellerRuleDetail record = (SellerRuleDetail) sqlMapClientTemplate.queryForObject("transSellerRuleDetail.selectByPrimaryKey", key);
        return record;
    }

    
    public int deleteByPrimaryKey(Integer id) {
        SellerRuleDetail key = new SellerRuleDetail();
        key.setId(id);
        int rows = sqlMapClientTemplate.delete("transSellerRuleDetail.deleteByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<SellerRuleDetail> selectSellerRuleDetail(
			SellerRuleDetail queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("transSellerRuleDetail.selectSellerRuleDetail", queryCon);
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}


	@Override
	public boolean updateSellerRuleDetailStateByRule(SellerRuleDetail con) {
		// TODO Auto-generated method stub
		if(con==null)
			return false;
		if(con.getSellerRuleId()==null)
			return false;
		if(con.getDetailRuleFrom()==null)
			return false;
		if(con.getState()==null)
			return false;
        int rows = sqlMapClientTemplate.update("transSellerRuleDetail.updateStateByRule", con);
        return rows>0?true:false;
	}
	
}