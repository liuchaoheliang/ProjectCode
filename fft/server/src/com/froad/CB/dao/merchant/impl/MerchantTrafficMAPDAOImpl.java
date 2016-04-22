package com.froad.CB.dao.merchant.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.AppException.PKIDEmptyException;
import com.froad.CB.AppException.ToSaveEntityException;
import com.froad.CB.AppException.ToUpdateEntityEmptyException;
import com.froad.CB.dao.merchant.MerchantTrafficMAPDAO;
import com.froad.CB.po.merchant.MerchantTrafficMAP;
import com.froad.util.Assert;
import com.froad.util.DateUtil;

public class MerchantTrafficMAPDAOImpl implements MerchantTrafficMAPDAO {
	
	
	private SqlMapClientTemplate sqlMapClientTemplate;

	
	private void checkToSaveObject(MerchantTrafficMAP record){
		if(record==null)
    		throw new ToSaveEntityException("添加的商户地图为空。添加的商户地图信息为："+record);
		if(Assert.empty(record.getState()))
			throw new ToSaveEntityException("添加商户地图的状态为空。添加的商户地图信息为："+record);
	}
	
	private void checkToUpadteObject(MerchantTrafficMAP record){
		if(record==null)
    		throw new ToUpdateEntityEmptyException("更新的商户地图为空。更新的商户地图信息为："+record);
		if(Assert.empty(record.getId()))
			throw new PKIDEmptyException("更新的商户地图的主键，id为空。更新的商户地图信息为："+record);
	}

    
    public Integer insert(MerchantTrafficMAP record) {
    	checkToSaveObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setCreateTime(now);
    	record.setUpdateTime(now);
    	return (Integer)sqlMapClientTemplate.insert("merchantTrafficMAP.insert", record);
    }

    
    public int updateByPrimaryKeySelective(MerchantTrafficMAP record) {
        int rows = sqlMapClientTemplate.update("merchantTrafficMAP.updateByPrimaryKeySelective", record);
        return rows;
    }

    
    public MerchantTrafficMAP selectByPrimaryKey(Integer id) {
       return (MerchantTrafficMAP) sqlMapClientTemplate.queryForObject("merchantTrafficMAP.selectByPrimaryKey", id);
    }

    
    public int deleteByPrimaryKey(Integer id) {
        return sqlMapClientTemplate.delete("merchantTrafficMAP.deleteByPrimaryKey", id);
    }

	@Override
	public List<MerchantTrafficMAP> selectMerchantTrafficMAPs(
			MerchantTrafficMAP queryCon) {
		return sqlMapClientTemplate.queryForList("merchantTrafficMAP.selectMerchantTrafficMAPs", queryCon);
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public MerchantTrafficMAP getByMerchantId(String merchantId) {
		return (MerchantTrafficMAP)sqlMapClientTemplate.queryForObject("merchantTrafficMAP.selectByMerchantId",merchantId);
	}

	@Override
	public MerchantTrafficMAP getByUserId(String userId) {
		return (MerchantTrafficMAP)sqlMapClientTemplate.queryForObject("merchantTrafficMAP.selectByUserId",userId);
	}

}