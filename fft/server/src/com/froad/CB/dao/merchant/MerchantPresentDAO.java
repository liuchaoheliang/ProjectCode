package com.froad.CB.dao.merchant;

import java.util.List;

import com.froad.CB.po.merchant.MerchantPresent;

public interface MerchantPresentDAO {

	
	/**
	 * 方法描述：添加商户介绍信息
	 * @param: MerchantPresent
	 * @return: Integer
	 * @version: 1.0
	 */
	Integer insert(MerchantPresent record);

	
	/**
	 * 方法描述：按主键更新商户介绍信息
	 * @param: MerchantPresent
	 * @return: 受影响行数
	 * @version: 1.0
	 */
	int updateByPrimaryKeySelective(MerchantPresent record);
	
	/**
	 * 方法描述：按商户ID更新商户介绍信息
	 * @param: MerchantPresent
	 * @return: 受影响行数
	 * @version: 1.0
	 */
	public int updateByMerchantId(MerchantPresent record);
	
	/**
	 * 方法描述：按主键查询商户介绍信息
	 * @param: id 主键
	 * @return: MerchantPresent
	 * @version: 1.0
	 */
	MerchantPresent selectByPrimaryKey(Integer id);

	/**
	 * 方法描述：按商户ID查询商户介绍信息
	 * @return: MerchantPresent
	 * @version: 1.0
	 */
	MerchantPresent selectByMerchantId(String merchantId);
	/**
	 * 方法描述：删除商户介绍信息
	 * @param: id 主键
	 * @return: 受影响行数
	 * @version: 1.0
	 */
	int deleteByPrimaryKey(Integer id);

	
	/**
	 * 方法描述：查询商户介绍列表
	 * @param: MerchantPresent 查询条件
	 * @return: List<MerchantPresent>
	 * @version: 1.0
	 */
	List<MerchantPresent> selectMerchantPresents(MerchantPresent queryCon);

	
	/**
	 * 描述：根据商户ID查询其优惠信息
	 * @param merchantId 商户编号
	 * @return MerchantPresent
	 */
	public MerchantPresent getMerchantPresent(String merchantId);
}