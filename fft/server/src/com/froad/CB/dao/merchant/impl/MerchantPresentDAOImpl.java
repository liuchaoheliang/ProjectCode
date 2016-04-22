package com.froad.CB.dao.merchant.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.AppException.PKIDEmptyException;
import com.froad.CB.AppException.ToSaveEntityException;
import com.froad.CB.AppException.ToUpdateEntityEmptyException;
import com.froad.CB.dao.merchant.MerchantPresentDAO;
import com.froad.CB.po.merchant.MerchantPresent;
import com.froad.util.Assert;
import com.froad.util.DateUtil;

public class MerchantPresentDAOImpl implements MerchantPresentDAO {
	private SqlMapClientTemplate sqlMapClientTemplate;
    
//    public MerchantPresentDAOImpl(DaoManager daoManager) {
//        super(daoManager);
//    }

	private void checkToSaveObject(MerchantPresent record){
		if(record==null)
    		throw new ToSaveEntityException("添加的商户介绍为空。添加的商户介绍信息为："+record);
		if(Assert.empty(record.getState()))
			throw new ToSaveEntityException("添加商户介绍的状态为空。添加的商户介绍信息为："+record);
	}
	
	private void checkToUpadteObject(MerchantPresent record){
		if(record==null)
    		throw new ToUpdateEntityEmptyException("更新的商户介绍为空。更新的商户介绍信息为："+record);
		if(Assert.empty(record.getId()))
			throw new PKIDEmptyException("更新的商户介绍的主键，id为空。更新的商户介绍信息为："+record);
	}
	
    
    public Integer insert(MerchantPresent record) {
    	checkToSaveObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setCreateTime(now);
    	record.setUpdateTime(now);
    	return (Integer)sqlMapClientTemplate.insert("merchantPresent.insert", record);
    }

    
    public int updateByPrimaryKeySelective(MerchantPresent record) {
    	checkToUpadteObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setUpdateTime(now);
        int rows = sqlMapClientTemplate.update("merchantPresent.updateByPrimaryKeySelective", record);
        return rows;
    }
    
    public int updateByMerchantId(MerchantPresent record) {
    	//checkToUpadteObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setUpdateTime(now);
        int rows = sqlMapClientTemplate.update("merchantPresent.updateByMerchantId", record);
        return rows;
    }
    
    public MerchantPresent selectByPrimaryKey(Integer id) {
        return (MerchantPresent) sqlMapClientTemplate.queryForObject("merchantPresent.selectByPrimaryKey", id);
    }
    
    public MerchantPresent selectByMerchantId(String merchantId) {
        return (MerchantPresent)sqlMapClientTemplate.queryForObject("merchantPresent.selectByMerchantId", merchantId);
    }
    
    public int deleteByPrimaryKey(Integer id) {
        return sqlMapClientTemplate.delete("merchantPresent.deleteByPrimaryKey", id);
    }

	@Override
	public List<MerchantPresent> selectMerchantPresents(MerchantPresent queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("merchantPresent.selectMerchantPresents", queryCon);
	}
	
	/**
	 * 描述：根据商户ID查询其优惠信息
	 * @param merchantId
	 * @return List<MerchantPresent>
	 */
	public MerchantPresent getMerchantPresent(String merchantId){
		return (MerchantPresent)sqlMapClientTemplate.queryForObject("merchantPresent.getMerchantPresents", merchantId);
	}
	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
}