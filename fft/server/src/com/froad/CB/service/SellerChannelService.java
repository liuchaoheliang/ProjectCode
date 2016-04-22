package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.SellerChannel;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-1  
 * @version 1.0
 */
@WebService
public interface SellerChannelService {
	/**
	  * 方法描述：添加卖家收款渠道
	  * @param: SellerChannel
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addSellerChannel(SellerChannel sellerChannel);
	
	
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
	SellerChannel selectById(Integer id);
	
	/**
	  * 方法描述：按主键删除卖家收款渠道
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteById(String id);
	
	/**
	 * 逻辑删除 卖家收款渠道
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateById(String id);
	
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
