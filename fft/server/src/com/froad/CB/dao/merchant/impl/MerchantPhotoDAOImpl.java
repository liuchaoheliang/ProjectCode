package com.froad.CB.dao.merchant.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.AppException.PKIDEmptyException;
import com.froad.CB.AppException.ToSaveEntityException;
import com.froad.CB.AppException.ToUpdateEntityEmptyException;
import com.froad.CB.dao.merchant.MerchantPhotoDAO;
import com.froad.CB.po.merchant.MerchantPhoto;
import com.froad.util.Assert;
import com.froad.util.DateUtil;

public class MerchantPhotoDAOImpl implements MerchantPhotoDAO {
	private SqlMapClientTemplate sqlMapClientTemplate;
    
//    public MerchantPhotoDAOImpl(DaoManager daoManager) {
//        super(daoManager);
//    }
	
	private void checkToSaveObject(MerchantPhoto record){
		if(record==null)
    		throw new ToSaveEntityException("添加的商户相册为空。添加的商户相册信息为："+record);
		if(Assert.empty(record.getState()))
			throw new ToSaveEntityException("添加商户相册的状态为空。添加的商户相册信息为："+record);
	}
	
	private void checkToUpadteObject(MerchantPhoto record){
		if(record==null)
    		throw new ToUpdateEntityEmptyException("更新的商户相册为空。更新的商户相册信息为："+record);
		if(Assert.empty(record.getId()))
			throw new PKIDEmptyException("更新的商户的主键，id为空。更新的商户信息为："+record);
	}
    

    
    public Integer insert(MerchantPhoto record) {
    	checkToSaveObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setCreateTime(now);
    	record.setUpdateTime(now);
    	return (Integer) sqlMapClientTemplate.insert("merchantPhotos.insert", record);
    }


    
    public Integer updateById(MerchantPhoto record) {
    	checkToUpadteObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setUpdateTime(now);
        Integer rows = sqlMapClientTemplate.update("merchantPhotos.updateById", record);
        return rows;
    }

    
    public MerchantPhoto selectByPrimaryKey(Integer id) {
       return (MerchantPhoto) sqlMapClientTemplate.queryForObject("merchantPhotos.selectByPrimaryKey", id);
    }

    
    public int deleteByPrimaryKey(String id) {
        MerchantPhoto key = new MerchantPhoto();        
        key.setId(Integer.valueOf(id));
        int rows = sqlMapClientTemplate.delete("merchantPhotos.deleteByPrimaryKey", key);
        return rows;
    }
    
    public int deleteStateByPrimaryKey(String id) {
        MerchantPhoto key = new MerchantPhoto();
        String now=DateUtil.formatDate2Str(new Date());
        key.setUpdateTime(now);
        key.setId(Integer.valueOf(id));
        int rows = sqlMapClientTemplate.update("merchantPhotos.deleteStateByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<MerchantPhoto> selectMerchantPhotos(MerchantPhoto queryCon) {
		// TODO Auto-generated method stub
		return sqlMapClientTemplate.queryForList("merchantPhotos.selectMerchantPhotos", queryCon);
	}
	
	@Override
	public List<MerchantPhoto> getMerchantPhotosByByMerchantId(String merchantId) {
		return sqlMapClientTemplate.queryForList("merchantPhotos.getMerchantPhotosByByMerchantId", merchantId);
	}

	@Override
	public MerchantPhoto getMerchantPhotoByPager(MerchantPhoto pager) {
		List list=sqlMapClientTemplate.queryForList("merchantPhotos.getByPager", pager);
		Integer count=(Integer)sqlMapClientTemplate.queryForObject("merchantPhotos.getByPagerCount",pager);
		
		pager.setList(list);
		pager.setTotalCount(count);
		return pager;
	}
	
	
	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	
	
	
}