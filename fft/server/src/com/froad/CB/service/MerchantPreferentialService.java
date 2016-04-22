package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.merchant.MerchantPreferential;

/** 
 * @author FQ 
 * @date 2013-2-4 下午03:28:47
 * @version 1.0
 * 商户优惠信息
 */
@WebService
public interface MerchantPreferentialService {
	
	/**
	 * 增加商户优惠信息
	 * @param merchantPreferential
	 * @return
	 */
	public Integer addMerchantPreferential(MerchantPreferential merchantPreferential);
	
	
	/**
	 * ID 查找优惠活动
	 * @param id
	 * @return
	 */
	public MerchantPreferential getMerchantPreferentialById(Integer id);
	
	
	/**
	 * 通过商户优惠信息ID,更新商户优惠信息
	 * @param merchantPreferential
	 * @return 更新后的商户优惠信息
	 */
	public boolean updMerchantPreferential(MerchantPreferential merchantPreferential);
	
	
	/**
	 * 删除商户优惠信息
	 * @param merchantPreferential
	 * @return
	 */
	public Integer deletePreferentialById(MerchantPreferential merchantPreferential);
	
	
	/**
	 * 通过商户ID查询出商户优惠信息
	 * @param merchantId
	 * @return 商户优惠信息
	 */
	public List<MerchantPreferential> getMerchantPreferentialInfoByMerchantId(String merchantId);
	
	/**
	 * 通过会员ID查询出商户优惠信息
	 * @param userId
	 * @return 商户优惠信息
	 */
	public List<MerchantPreferential> getMerchantPreferentialInfoByUserId(String userId);

	
	/**
	  * 方法描述：多条件分页查询商户优惠
	  * @param: MerchantPreferential
	  * @return: MerchantPreferential
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 10, 2013 4:03:34 PM
	  */
	public MerchantPreferential getMerchantPreferentialByPager(MerchantPreferential pager);
	
	/**
	 * 通过商户优惠信息ID,更新商户优惠信息
	 * @param merchantPreferential
	 * @return 更新后的商户优惠信息
	 * @throws Exception 
	 */
	MerchantPreferential updMerchantPreferentialInfo(MerchantPreferential merchantPreferential) throws Exception;
	
	/**
	 * 通过商户ID,查询商户优惠列表
	 * @param merchantPreferential
	 * @return List<MerchantPreferential>
	 * @throws Exception 
	 */
	public List<MerchantPreferential> getMerchantPreferentialListByMerchantId(MerchantPreferential merchantPreferential);
}
