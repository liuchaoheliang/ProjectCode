package com.froad.CB.dao.merchant;

import java.util.List;

import com.froad.CB.po.merchant.MerchantPreferential;

public interface MerchantPreferentialDAO {

	
	/**
	  * 方法描述：添加商户优惠信息
	  * @param: MerchantPreferential
	  * @return: void
	  * @version: 1.0
	  */
	Integer insert(MerchantPreferential record);

	
	/**
	  * 方法描述：按主键更新商户优惠信息
	  * @param: MerchantPreferential
	  * @return: 受影响行数
	  * @version: 1.0
	  */
	int updateByPrimaryKeySelective(MerchantPreferential record);

	
	/**
	  * 方法描述：查询商户优惠信息
	  * @param: id
	  * @return: MerchantPreferential
	  * @version: 1.0
	  */
	MerchantPreferential selectByPrimaryKey(Integer id);

	
	/**
	  * 方法描述：删除商户优惠信息
	  * @param: id
	  * @return: 受影响行数
	  * @version: 1.0
	  */
	int deleteByPrimaryKey(Integer id);

	public int deleteStateByPrimaryKey(Integer id);
	/**
	  * 方法描述：查询商户优惠信息列表
	  * @param: MerchantPreferential 查询条件
	  * @return: List<MerchantPreferential>
	  * @version: 1.0
	  */
	List<MerchantPreferential> selectMerchantPreferentials(
			MerchantPreferential queryCon);

	
	/**
	  * 方法描述：查询商户优惠信息列表
	  * @param: merchantId 商户编号
	  * @return: List<MerchantPreferential>
	  * @version: 1.0
	  */
	public List<MerchantPreferential> getMerchantPreferential(String merchantId);

	
	/**
	  * 方法描述：多条件分页查询商户优惠活动
	  * @param: MerchantPreferential
	  * @return: MerchantPreferential
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 10, 2013 3:35:07 PM
	  */
	public MerchantPreferential getMerchantPreferentialByPager(MerchantPreferential prefer);
}