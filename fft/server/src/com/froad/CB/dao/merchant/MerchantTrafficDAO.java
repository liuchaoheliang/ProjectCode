package com.froad.CB.dao.merchant;

import java.util.List;

import com.froad.CB.po.merchant.MerchantTraffic;

public interface MerchantTrafficDAO {

	
	/**
     * 方法描述：添加商户交通信息
     * @param: id 主键编号
     * @return: MerchantTraffic
     * @version: 1.0
     */
	Integer insert(MerchantTraffic record);

	
	/**
     * 方法描述：按主键更新商户交通信息
     * @param: MerchantTraffic
     * @return: MerchantTraffic
     * @version: 1.0
     */
	int updateByPrimaryKeySelective(MerchantTraffic record);

	
	/**
     * 方法描述：按主键查询商户交通信息
     * @param: id 主键编号
     * @return: MerchantTraffic
     * @version: 1.0
     */
	MerchantTraffic selectByPrimaryKey(Integer id);

	
	/**
     * 方法描述：按主键删除商户交通信息
     * @param: id 主键编号
     * @return: 受影响行数
     * @version: 1.0
     */
	int deleteByPrimaryKey(Integer id);

	
	/**
     * 方法描述：查询商户交通信息
     * @param: MerchantTraffic 查询条件
     * @return: List<MerchantTraffic>
     * @version: 1.0
     */
	List<MerchantTraffic> selectMerchantTraffics(MerchantTraffic queryCon);
}