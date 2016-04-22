package com.froad.CB.dao.merchant;

import java.util.List;

import com.froad.CB.po.merchant.MerchantTrain;

public interface MerchantTrainDAO {

	
	/**
     * 方法描述：添加商户直通车信息
     * @param: MerchantTrain
     * @return: 
     * @version: 1.0
     */
	Integer insert(MerchantTrain record);

	
	/**
     * 方法描述：更新商户直通车信息
     * @param: MerchantTrain
     * @return: 受影响行数
     * @version: 1.0
     */
	int updateByPrimaryKeySelective(MerchantTrain record);

	
	/**
     * 方法描述：按主键查询商户直通车信息
     * @param: id 主键编号
     * @return: MerchantTrain
     * @version: 1.0
     */
	MerchantTrain selectByPrimaryKey(Integer id);
	
	
	/**
	  * 方法描述：查询商户直通车
	  * @param: merchantId
	  * @return: MerchantTrain
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 6, 2013 4:07:56 PM
	  */
	MerchantTrain selectByMerchantId(String merchantId);

	
	/**
	  * 方法描述：查询商户直通车
	  * @param: userId
	  * @return: MerchantTrain
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 6, 2013 4:09:04 PM
	  */
	MerchantTrain selectByUserId(String userId);
	
	
	/**
     * 方法描述：按主键删除商户直通车信息
     * @param: id 主键编号
     * @return: 受影响行数
     * @version: 1.0
     */
	int deleteByPrimaryKey(Integer id);

	
	/**
     * 方法描述：查询商户直通车信息
     * @param: MerchantTrain 查询条件
     * @return: List<MerchantTrain>
     * @version: 1.0
     */
	List<MerchantTrain> selectMerchantTrains(MerchantTrain queryCon);
}