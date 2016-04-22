package com.froad.CB.dao.merchant.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.AppException.PKIDEmptyException;
import com.froad.CB.AppException.ToSaveEntityException;
import com.froad.CB.AppException.ToUpdateEntityEmptyException;
import com.froad.CB.common.Pager;
import com.froad.CB.dao.merchant.MerchantPreferentialDAO;
import com.froad.CB.po.announcement.Announcement;
import com.froad.CB.po.merchant.MerchantPreferential;
import com.froad.util.Assert;
import com.froad.util.DateUtil;

public class MerchantPreferentialDAOImpl implements MerchantPreferentialDAO {
	private SqlMapClientTemplate sqlMapClientTemplate;
    
//    public MerchantPreferentialDAOImpl(DaoManager daoManager) {
//        super(daoManager);
//    }
	
	private void checkToSaveObject(MerchantPreferential record){
		if(record==null)
    		throw new ToSaveEntityException("添加的商户优惠为空。添加的商户优惠信息为："+record);
		if(Assert.empty(record.getState()))
			throw new ToSaveEntityException("添加商户优惠的状态为空。添加的商户优惠信息为："+record);
	}
	
	private void checkToUpadteObject(MerchantPreferential record){
		if(record==null)
    		throw new ToUpdateEntityEmptyException("更新的商户优惠为空。更新的商户优惠信息为："+record);
		if(Assert.empty(record.getId()))
			throw new PKIDEmptyException("更新的商户优惠的主键，id为空。更新的商户优惠信息为："+record);
	}

    
    public Integer insert(MerchantPreferential record) {
    	checkToSaveObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setCreateTime(now);
    	record.setUpdateTime(now);
    	return (Integer)sqlMapClientTemplate.insert("merchantPreferential.insert", record);
    }
    
    public int updateByPrimaryKeySelective(MerchantPreferential record) {
    	checkToUpadteObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setUpdateTime(now);
        int rows = sqlMapClientTemplate.update("merchantPreferential.updateByPrimaryKeySelective", record);
        return rows;
    }

    
    public MerchantPreferential selectByPrimaryKey(Integer id) {
        return (MerchantPreferential) sqlMapClientTemplate.queryForObject("merchantPreferential.selectByPrimaryKey", id);
    }

    
    public int deleteByPrimaryKey(Integer id) {
        MerchantPreferential key = new MerchantPreferential();
        key.setId(id);
        int rows = sqlMapClientTemplate.delete("merchantPreferential.deleteByPrimaryKey", key);
        return rows;
    }
    
    public int deleteStateByPrimaryKey(Integer id) {
        MerchantPreferential key = new MerchantPreferential();
        key.setId(id);
        int rows = sqlMapClientTemplate.delete("merchantPreferential.deleteStateByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<MerchantPreferential> selectMerchantPreferentials(
			MerchantPreferential queryCon) {
		return sqlMapClientTemplate.queryForList("merchantPreferential.selectMerchantPreferentials", queryCon);
	}

	/**
	 * 描述：根据商户ID查询其优惠信息
	 * @param merchantId
	 * @return List<MerchantPresent>
	 */
	public List<MerchantPreferential> getMerchantPreferential(String merchantId){
		return sqlMapClientTemplate.queryForList("merchantPreferential.FIND-YOUHUI-BY-MERCHANTID", merchantId);
	}
	
	@Override
	public MerchantPreferential getMerchantPreferentialByPager(MerchantPreferential pager){
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("merchantPreferential.getByPagerCount",pager);
		List<MerchantPreferential> list=sqlMapClientTemplate.queryForList("merchantPreferential.getByPager", pager);
		
		pager.setTotalCount(totalCount);
		pager.setList(list);
		return pager;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	
}