package com.froad.CB.dao.tran.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.tran.PointsCurrencyFormulaDAO;
import com.froad.CB.po.transaction.PointsCurrencyFormula;

public class PointsCurrencyFormulaDAOImpl  implements PointsCurrencyFormulaDAO {
	private SqlMapClientTemplate sqlMapClientTemplate;
    
//    public PointsCurrencyFormulaDAOImpl(DaoManager daoManager) {
//        super(daoManager);
//    }

    
    public void insert(PointsCurrencyFormula record) {
    	sqlMapClientTemplate.insert("pointsCurrencyFormula.insert", record);
    }

    
    public int updateByPrimaryKeySelective(PointsCurrencyFormula record) {
        int rows = sqlMapClientTemplate.update("pointsCurrencyFormula.updateByPrimaryKeySelective", record);
        return rows;
    }

    
    public PointsCurrencyFormula selectByPrimaryKey(Integer id) {
        PointsCurrencyFormula key = new PointsCurrencyFormula();
        key.setId(id);
        PointsCurrencyFormula record = (PointsCurrencyFormula) sqlMapClientTemplate.queryForObject("pointsCurrencyFormula.selectByPrimaryKey", key);
        return record;
    }

    
    public int deleteByPrimaryKey(Integer id) {
        PointsCurrencyFormula key = new PointsCurrencyFormula();
        key.setId(id);
        int rows = sqlMapClientTemplate.delete("pointsCurrencyFormula.deleteByPrimaryKey", key);
        return rows;
    }

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<PointsCurrencyFormula> getPointsCurrencyFormulaList(
			PointsCurrencyFormula queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("pointsCurrencyFormula.getPointsCurrencyFormulaList", queryCon);
	}
    
    
}