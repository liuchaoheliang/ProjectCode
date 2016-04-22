package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.SellerChannel;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-30  
 * @version 1.0
 */
public interface SellerChannelDao {
	/**
	  * 方法描述：添加卖家收款渠道
	  * @param: SellerChannel
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer insert(SellerChannel sellerChannel);
	
	/**
	  * 方法描述：批量添加卖家资金渠道
	  * @param: List<SellerChannel>
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 27, 2013 5:37:53 PM
	  */
	void batchInsert(final List<SellerChannel> sellerChannelList);
	
	/**
	  * 方法描述：按主键更新卖家收款渠道信息
	  * @param: SellerChannel
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(SellerChannel sellerChannel);
	
	/**
	  * 方法描述：查询卖家收款渠道
	  * @param: id
	  * @return: SellerChannel
	  * @version: 1.0
	  */
	SellerChannel selectByPrimaryKey(Integer id);
	
	/**
	  * 方法描述：按主键删除卖家收款渠道
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteByPrimaryKey(String id);
	
	/**
	 * 逻辑删除 卖家收款渠道
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateByPrimaryKey(String id);
	
	/**
	  * 方法描述：查询卖家收款渠道
	  * @param: userId
	  * @return: SellerChannel
	  * @version: 1.0
	  */
	SellerChannel getSellerChannelByUserId(String userId);
	
	/**
	  * 方法描述：查询卖家收款渠道
	  * @param: merchantId
	  * @return: SellerChannel
	  * @version: 1.0
	  */
	List<SellerChannel> getSellerChannelByMerchantId(String merchantId);
	
	/**
	  * 方法描述：卖家id查询卖家收款渠道
	  * @param: sellerId
	  * @return: SellerChannel
	  * @version: 1.0
	  */
	SellerChannel getSellerChannelBySellerId(String sellerId);
}