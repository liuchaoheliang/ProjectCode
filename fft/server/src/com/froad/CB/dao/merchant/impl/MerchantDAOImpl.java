package com.froad.CB.dao.merchant.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.AppException.PKIDEmptyException;
import com.froad.CB.AppException.ToSaveEntityException;
import com.froad.CB.AppException.ToUpdateEntityEmptyException;
import com.froad.CB.common.PreferType;
import com.froad.CB.dao.merchant.MerchantDAO;
import com.froad.CB.po.merchant.Merchant;
import com.froad.util.Assert;
import com.froad.util.DateUtil;

public class MerchantDAOImpl  implements MerchantDAO {
	
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	
	private void checkToSaveObject(Merchant record){
		if(record==null)
    		throw new ToSaveEntityException("添加的商户为空。添加的商户信息为："+record);
		if(Assert.empty(record.getState()))
			throw new ToSaveEntityException("添加商户的状态为空。添加的商户信息为："+record);
		if(Assert.empty(record.getDcHyperlink()))
			throw new ToSaveEntityException("添加商户的商户直连URL为空。添加的商户信息为："+record);
	}
	
	private void checkToUpadteObject(Merchant record){
		if(record==null)
    		throw new ToUpdateEntityEmptyException("更新的商户为空。更新的商户信息为："+record);
		if(Assert.empty(record.getId()))
			throw new PKIDEmptyException("更新的商户的主键，id为空。更新的商户信息为："+record);
	}

    public Integer insert(Merchant record) throws Exception {
    	checkToSaveObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setCreateTime(now);
    	record.setUpdateTime(now);
    	return (Integer)sqlMapClientTemplate.insert("merchant.insert", record);
    }

    public int updateById(Merchant record)throws Exception{
    	checkToUpadteObject(record);
    	String now=DateUtil.formatDate2Str(new Date());
    	record.setUpdateTime(now);
        int rows = sqlMapClientTemplate.update("merchant.updateById", record);
        return rows;
    }

    public Merchant selectByPrimaryKey(Integer id){
       return (Merchant)sqlMapClientTemplate.queryForObject("merchant.selectByPrimaryKey", id);
    }

    public void deleteByPrimaryKey(Integer id)throws Exception {
        sqlMapClientTemplate.delete("merchant.deleteByPrimaryKey", id);
    }

	@Override
	public List<Merchant> select(Merchant queryCon) throws SQLException{
		return sqlMapClientTemplate.queryForList("merchant.selectMerchants", queryCon);
	}

	
	@Override
	public Integer getMerchantIdByUserId(String userId){
		return (Integer)sqlMapClientTemplate.queryForObject("merchant.getMerchantIdByUserId",userId);
	}
	
	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public List<Merchant> getMerchantByUserId(String userId) {
		return sqlMapClientTemplate.queryForList("merchant.getMerchantByUserId",userId);
	}
	
	public List<Merchant> getMerchantListByTagId(Merchant merchant){
		return sqlMapClientTemplate.queryForList("merchant.FIND-MERCHANTS-BY-TAGID", merchant);
	}
	
	public Integer getMerchantListByTagIdCount(Merchant merchant){
		Object count = sqlMapClientTemplate.queryForObject("merchant.FIND-MERCHANTS-BY-TAGID-COUNT", merchant);
		return count != null ? (Integer) count : 0 ;
	}
	

	/**
	 * 描述：根据标签ID(分类AID和分类BID商圈bID)查询商户 
	 * @param 
	 * @return List<Merchant>
	 */
	public List<Merchant> getMerchantListByClassAClassBDistrictBId(Merchant merchant){
		return sqlMapClientTemplate.queryForList("merchant.FIND-MERCHANTS-BY-CLASSAID-CLASSBID-DISTRICTBID", merchant);
	}
	
	public Integer getMerchantListByClassAClassBDistrictBIdCount(Merchant merchant){
		Object count = sqlMapClientTemplate.queryForObject("merchant.FIND-MERCHANTS-BY-CLASSAID-CLASSBID-DISTRICTBID-COUNT", merchant);
		return count != null ? (Integer) count : 0 ;
	}
		
	/**
	 * 描述：根据(分类A标签id和分类B标签id和商圈A标签id商圈B标签id)查找商户
	 * @param 
	 * @return List<Merchant>
	 */
	public List<Merchant> getMerchantListByAllTagId(Merchant merchant){
		return sqlMapClientTemplate.queryForList("merchant.FIND-MERCHANTS-BY-CLASSAID-CLASSBID-DISTRICTAID-DISTRICTBID", merchant);
	}
	
	public Integer getMerchantListByAllTagIdCount(Merchant merchant){
		Object count = sqlMapClientTemplate.queryForObject("merchant.FIND-MERCHANTS-BY-CLASSAID-CLASSBID-DISTRICTAID-DISTRICTBID-COUNT", merchant);
		return count != null ? (Integer) count : 0 ;
	}

	@Override
	public List<Merchant> selectByRegTime(String beginTime, String endTime)
			throws SQLException {
		HashMap<String,String> params=new HashMap<String, String>();
		params.put("beginTime", beginTime);
		params.put("endTime", endTime);
		return sqlMapClientTemplate.queryForList("merchant.selectByRegTime", params);
	}

	@Override
	public Merchant getMerchantByPager(Merchant merchant) { //ajax查询hot商家排序  ajax查询最新商家
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("merchant.getMerchantByPagerCount",merchant);
		List<Merchant> list=sqlMapClientTemplate.queryForList("merchant.getMerchantByPager", merchant);
		merchant.setTotalCount(totalCount);
		merchant.setList(list);
		return merchant;
	}

	@Override
	public void auditMerchant(Merchant merchant)throws SQLException {
		sqlMapClientTemplate.update("merchant.auditMerchant",merchant);
	}

	@Override
	public void reviewMerchant(Merchant merchant) throws SQLException {
		sqlMapClientTemplate.update("merchant.reviewMerchant",merchant);
	}

	@Override
	public List<Merchant> getAllMerchant(Merchant merchant) {
		return sqlMapClientTemplate.queryForList("merchant.getAllMerchant", merchant);
	}

	@Override
	public List<Merchant> getMerchantByType(PreferType preferType) {
		return sqlMapClientTemplate.queryForList("merchant.getMerchantByType",preferType.value());
	}

	@Override
	public List<Merchant> getInnerMerchant() {
		return sqlMapClientTemplate.queryForList("merchant.getInnerMerchant");
	}

	@Override
	public Merchant getMerchantsPreferentialType(Merchant merchant) {
		Integer totalCount=(Integer) sqlMapClientTemplate.queryForObject("merchant.getMerchantsPreferentialTypeCount",merchant);
		List<Merchant> list=sqlMapClientTemplate.queryForList("merchant.getMerchantsPreferentialType",merchant);
		merchant.setTotalCount(totalCount);
		merchant.setList(list);
		return merchant;
	}
	
	
}