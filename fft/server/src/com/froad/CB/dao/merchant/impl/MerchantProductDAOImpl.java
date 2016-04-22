package com.froad.CB.dao.merchant.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.AppException.PKIDEmptyException;
import com.froad.CB.AppException.ToSaveEntityException;
import com.froad.CB.AppException.ToUpdateEntityEmptyException;
import com.froad.CB.dao.merchant.MerchantProductDAO;
import com.froad.CB.po.merchant.MerchantProduct;
import com.froad.util.Assert;
import com.froad.util.DateUtil;

public class MerchantProductDAOImpl implements MerchantProductDAO {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	private void checkToSaveObject(MerchantProduct record){
		if(record==null)
    		throw new ToSaveEntityException("添加的商户产品为空。添加的商户产品信息为："+record);
		if(Assert.empty(record.getState()))
			throw new ToSaveEntityException("添加商户产品的状态为空。添加的商户产品信息为："+record);
	}
	
	private void checkToUpadteObject(MerchantProduct record){
		if(record==null)
    		throw new ToUpdateEntityEmptyException("更新的商户产品为空。更新的商户产品信息为："+record);
		if(Assert.empty(record.getId()))
			throw new PKIDEmptyException("更新的商户产品的主键，id为空。更新的商户产品信息为："+record);
	}
    
    public Integer insert(MerchantProduct record) {
    	checkToSaveObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setCreateTime(now);
    	record.setUpdateTime(now);
    	return (Integer) sqlMapClientTemplate.insert("merchantProducts.insert", record);
    }

    
    public int updateByPrimaryKeySelective(MerchantProduct record) {
    	checkToUpadteObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setUpdateTime(now);
        int rows = sqlMapClientTemplate.update("merchantProducts.updateByPrimaryKeySelective", record);
        return rows;
    }
    /**
     * 根据商户ID更新产品信息
     * @param record
     * @return
     */
    public int updateByMerchantId(MerchantProduct record) {
    	checkToUpadteObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setUpdateTime(now);
        int rows = sqlMapClientTemplate.update("merchantProducts.updateByMerchantIdSelective", record);
        return rows;
    }
    
    public MerchantProduct selectByPrimaryKey(Integer id) {
        return  (MerchantProduct) sqlMapClientTemplate.queryForObject("merchantProducts.selectByPrimaryKey", id);
    }
    
    public int deleteByPrimaryKey(String id) {
        MerchantProduct key = new MerchantProduct();
        key.setId(Integer.valueOf(id));
        int rows = sqlMapClientTemplate.delete("merchantProducts.deleteByPrimaryKey", key);
        return rows;
    }
    
    public int deleteStateByPrimaryKey(String id) {
    	MerchantProduct key = new MerchantProduct();
        String now=DateUtil.formatDate2Str(new Date());
        key.setUpdateTime(now);
        key.setId(Integer.valueOf(id));
        int rows = sqlMapClientTemplate.update("merchantProducts.deleteStateByPrimaryKey", key);
        return rows;
    }

	@Override
	public List<MerchantProduct> selectMerchantProducts(MerchantProduct queryCon) {
		return sqlMapClientTemplate.queryForList("merchantProducts.selectMerchantProducts", queryCon);
	}
	
	
	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public MerchantProduct getMerchantProductByPager(MerchantProduct product) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("merchantProducts.getMerchantProductByPagerCount",product);
		List<MerchantProduct> list=sqlMapClientTemplate.queryForList("merchantProducts.getMerchantProductByPager", product);
		product.setTotalCount(totalCount);
		product.setList(list);
		return product;
	}
	
	
}