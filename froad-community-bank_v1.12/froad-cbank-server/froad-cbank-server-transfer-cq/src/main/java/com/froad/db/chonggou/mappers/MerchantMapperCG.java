package com.froad.db.chonggou.mappers;


import org.apache.ibatis.annotations.Param;

import com.froad.db.chonggou.entity.MerchantCG;

/**
 * 
 * <p>@Title: MerchantMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月10日
 */
public interface MerchantMapperCG {

	
	 /**
     * 增加 Merchant
     * @param merchant
     * @return Long    主键ID
     */
	public Long addMerchant(MerchantCG merchant);



	/**
	 * 查询单个merchant
	 * @Title: findOneMerchant 
	 * @author: froad-huangyihao 2015年5月3日
	 * @modify: froad-huangyihao 2015年5月3日
	 * @param merchant
	 * @return
	 * @throws
	 */
	public MerchantCG findOneMerchant(MerchantCG merchant);
	
	/**
	 * 根据商户id查询Merchant对象
	 * @param merchantId
	 * @return
	 */
	public MerchantCG findMerchantByMerchantId(String merchantId);
	
	

	
	public boolean removeMerchant(@Param("clientId") String clientId);
	
	/**
	 * delete org merchants
	 * 
	 * @param clientId
	 */
	public void deleteOrgMerchant(String clientId);
}