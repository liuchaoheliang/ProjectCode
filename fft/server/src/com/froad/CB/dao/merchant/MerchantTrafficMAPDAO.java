package com.froad.CB.dao.merchant;

import java.util.List;

import com.froad.CB.po.merchant.MerchantTrafficMAP;

public interface MerchantTrafficMAPDAO {

	
	
	/**
	  * 方法描述：按商户编号查询地图
	  * @param: merchantId
	  * @return: MerchantTrafficMAP
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 6, 2013 2:49:35 PM
	  */
	MerchantTrafficMAP getByMerchantId(String merchantId);
	
	
	/**
	  * 方法描述：按用户编号查询地图
	  * @param: userId
	  * @return: MerchantTrafficMAP
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 6, 2013 2:50:25 PM
	  */
	MerchantTrafficMAP getByUserId(String userId);
	
	
	/**
     * 方法描述：添加商户地图信息
     * @param: MerchantTrafficMAP
     * @return: 
     * @version: 1.0
     */
	Integer insert(MerchantTrafficMAP record);

	
	/**
     * 方法描述：按主键更新商户地图信息
     * @param: MerchantTrafficMAP
     * @return: 受影响行数
     * @version: 1.0
     */
	int updateByPrimaryKeySelective(MerchantTrafficMAP record);

	
	/**
     * 方法描述：按主键查询商户地图信息
     * @param: id 主键编号
     * @return: MerchantTrafficMAP
     * @version: 1.0
     */
	MerchantTrafficMAP selectByPrimaryKey(Integer id);

	
	/**
     * 方法描述：按主键删除商户地图信息
     * @param: id 主键编号
     * @return: 受影响行数
     * @version: 1.0
     */
	int deleteByPrimaryKey(Integer id);

	
	/**
     * 方法描述：按主键查询商户地图信息
     * @param: MerchantTrafficMAP 查询条件
     * @return: List<MerchantTrafficMAP>
     * @version: 1.0
     */
	List<MerchantTrafficMAP> selectMerchantTrafficMAPs(
			MerchantTrafficMAP queryCon);
}