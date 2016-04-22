package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.po.BuyerChannel;



	/**
	 * 类描述：买家资金渠道service
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 2, 2013 5:00:40 PM 
	 */
@WebService
public interface BuyerChannelService {
	/**
	  * 方法描述：添加买家支付渠道
	  * @param: BuyerChannel
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer addBuyerChannel(BuyerChannel buyerChannel);
	
	/**
	  * 方法描述：按主键更新买家支付渠道信息
	  * @param: BuyerChannel
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(BuyerChannel buyerChannel);
	
	
	/**
	  * 方法描述：查询买家支付渠道
	  * @param: id
	  * @return: BuyerChannel
	  * @version: 1.0
	  */
	BuyerChannel selectById(Integer id);
	
	/**
	  * 方法描述：按主键删除买家支付渠道
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteById(String id);
	
	/**
	 * 逻辑删除 买家支付渠道
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateById(String id);
	
	/**
	  * 方法描述：查询买家支付渠道
	  * @param: userId
	  * @return: BuyerChannel
	  * @version: 1.0
	  */
	BuyerChannel getBuyerChannelByUserId(String userId);
	
	/**
	  * 方法描述：查询买家支付渠道
	  * @param: merchantId
	  * @return: BuyerChannel
	  * @version: 1.0
	  */
	BuyerChannel getBuyerChannelByMerchantId(String merchantId);
	
	
	/**
	  * 方法描述：设置默认的资金渠道
	  * @param: id
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 23, 2013 2:42:14 PM
	  */
	boolean setDefaultChannel(Integer id);
	
	/**
	 * 根据买家ID更新买家资金渠道
	 * @param buyerChannel
	 * @return
	 */
	public int updateChannelByBuyerId(BuyerChannel buyerChannel);
}
