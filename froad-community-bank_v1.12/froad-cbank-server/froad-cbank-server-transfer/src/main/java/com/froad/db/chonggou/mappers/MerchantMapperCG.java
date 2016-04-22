package com.froad.db.chonggou.mappers;

import java.util.List;

import com.froad.po.Merchant;

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
	public Long addMerchant(Merchant merchant);



    /**
     * 批量增加 Merchant
     * @param List<Merchant>
     * @return Boolean    是否成功
     */
	public Boolean addMerchantByBatch(List<Merchant> merchantList);

	/**
	 * 查询单个merchant
	 * @Title: findOneMerchant 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年5月3日
	 * @modify: froad-huangyihao 2015年5月3日
	 * @param merchant
	 * @return
	 * @throws
	 */
	public Merchant findOneMerchant(Merchant merchant);
	
	/**
	 * 根据商户id查询Merchant对象
	 * @param merchantId
	 * @return
	 */
	public Merchant findMerchantByMerchantId(String merchantId);
	
	
	public Integer countMerchant(Merchant merchant);

	
	
}