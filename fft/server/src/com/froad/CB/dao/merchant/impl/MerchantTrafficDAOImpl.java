package com.froad.CB.dao.merchant.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.AppException.PKIDEmptyException;
import com.froad.CB.AppException.ToSaveEntityException;
import com.froad.CB.AppException.ToUpdateEntityEmptyException;
import com.froad.CB.dao.merchant.MerchantTrafficDAO;
import com.froad.CB.po.merchant.MerchantTraffic;
import com.froad.util.Assert;
import com.froad.util.DateUtil;

public class MerchantTrafficDAOImpl implements MerchantTrafficDAO {
	private SqlMapClientTemplate sqlMapClientTemplate;
    
//    public MerchantTrafficDAOImpl(DaoManager daoManager) {
//        super(daoManager);
//    }
	
	private void checkToSaveObject(MerchantTraffic record){
		if(record==null)
    		throw new ToSaveEntityException("添加的商户交通为空。添加的商户交通信息为："+record);
		if(Assert.empty(record.getState()))
			throw new ToSaveEntityException("添加商户交通的状态为空。添加的商户交通信息为："+record);
	}
	
	private void checkToUpadteObject(MerchantTraffic record){
		if(record==null)
    		throw new ToUpdateEntityEmptyException("更新的商户交通为空。更新的商户交通信息为："+record);
		if(Assert.empty(record.getId()))
			throw new PKIDEmptyException("更新的商户交通的主键，id为空。更新的商户交通信息为："+record);
	}

    
    public Integer insert(MerchantTraffic record) {
    	checkToSaveObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setCreateTime(now);
    	record.setUpdateTime(now);
    	return (Integer)sqlMapClientTemplate.insert("merchantTraffic.insert", record);
    }

    
    public int updateByPrimaryKeySelective(MerchantTraffic record) {
    	checkToUpadteObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setUpdateTime(now);
        int rows = sqlMapClientTemplate.update("merchantTraffic.updateByPrimaryKeySelective", record);
        return rows;
    }

    
    public MerchantTraffic selectByPrimaryKey(Integer id) {
        return (MerchantTraffic) sqlMapClientTemplate.queryForObject("merchantTraffic.selectByPrimaryKey", id);
    }

    
    public int deleteByPrimaryKey(Integer id) {
        return sqlMapClientTemplate.delete("merchantTraffic.deleteByPrimaryKey", id);
    }

	@Override
	public List<MerchantTraffic> selectMerchantTraffics(MerchantTraffic queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("merchantTraffic.selectMerchantTraffics", queryCon);
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	
}