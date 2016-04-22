package com.froad.CB.dao.merchant.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.AppException.PKIDEmptyException;
import com.froad.CB.AppException.ToSaveEntityException;
import com.froad.CB.AppException.ToUpdateEntityEmptyException;
import com.froad.CB.dao.merchant.MerchantTrainDAO;
import com.froad.CB.po.merchant.MerchantTrain;
import com.froad.util.Assert;
import com.froad.util.DateUtil;

public class MerchantTrainDAOImpl implements MerchantTrainDAO {
	
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	private void checkToSaveObject(MerchantTrain record){
		if(record==null)
    		throw new ToSaveEntityException("添加的商户直通车为空。添加的商户直通车信息为："+record);
		if(Assert.empty(record.getState()))
			throw new ToSaveEntityException("添加商户直通车的状态为空。添加的商户直通车信息为："+record);
	}
	
	private void checkToUpadteObject(MerchantTrain record){
		if(record==null)
    		throw new ToUpdateEntityEmptyException("更新的商户直通车为空。更新的商户直通车信息为："+record);
		if(Assert.empty(record.getId()))
			throw new PKIDEmptyException("更新的商户直通车的主键，id为空。更新的商户直通车信息为："+record);
	}

    
    public Integer insert(MerchantTrain record) {
    	checkToSaveObject(record);
    	return (Integer)sqlMapClientTemplate.insert("merchantTrain.insert", record);
    }

    
    public int updateByPrimaryKeySelective(MerchantTrain record) {
    	checkToUpadteObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setUpdateTime(now);
        int rows = sqlMapClientTemplate.update("merchantTrain.updateByPrimaryKeySelective", record);
        return rows;
    }

    
    public MerchantTrain selectByPrimaryKey(Integer id) {
        return (MerchantTrain) sqlMapClientTemplate.queryForObject("merchantTrain.selectByPrimaryKey", id);
    }

    
    public int deleteByPrimaryKey(Integer id) {
        return sqlMapClientTemplate.delete("merchantTrain.deleteByPrimaryKey", id);
    }

	@Override
	public List<MerchantTrain> selectMerchantTrains(MerchantTrain queryCon) {
		return sqlMapClientTemplate.queryForList("merchantTrain.selectMerchantTrains", queryCon);
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public MerchantTrain selectByMerchantId(String merchantId) {
		return (MerchantTrain)sqlMapClientTemplate.queryForObject("merchantTrain.selectByMerchantId",merchantId);
	}

	@Override
	public MerchantTrain selectByUserId(String userId) {
		return (MerchantTrain)sqlMapClientTemplate.queryForObject("merchantTrain.selectByUserId",userId);
	}
	
	
}