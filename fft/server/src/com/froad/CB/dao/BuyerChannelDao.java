package com.froad.CB.dao;

import com.froad.CB.po.BuyerChannel;



	/**
	 * 类描述：买家资金渠道dao
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 2, 2013 4:58:43 PM 
	 */
public interface BuyerChannelDao {
	/**
	  * 方法描述：添加买家支付渠道
	  * @param: BuyerChannel
	  * @return: Integer
	  * @version: 1.0
	  */
	Integer insert(BuyerChannel buyerChannel);
	
	/**
	  * 方法描述：按主键更新买家支付渠道信息
	  * @param: BuyerChannel
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer updateById(BuyerChannel buyerChannel);
	
	
	/**
	  * 方法描述：更新买家账户信息
	  * @param: BuyerChannel(channelId,accountName,accountNumber,
	  *                      state,createTime,updateTime,buyersId)
	  * @return: int 受影响行数
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 28, 2013 1:46:41 PM
	  */
	int updateChannelByBuyerId(BuyerChannel buyerChannel);
	
	
	/**
	  * 方法描述：查询买家支付渠道
	  * @param: id
	  * @return: BuyerChannel
	  * @version: 1.0
	  */
	BuyerChannel selectByPrimaryKey(Integer id);
	
	/**
	  * 方法描述：按主键删除买家支付渠道
	  * @param: id
	  * @return: 受影响行数 Integer
	  * @version: 1.0
	  */
	Integer deleteByPrimaryKey(String id);
	
	/**
	 * 逻辑删除 买家支付渠道
	 * @param id
	 * @return Integer
	 */
	public Integer deleteStateByPrimaryKey(String id);
	
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
	BuyerChannel getBuyerChannelByBuyerId(String buyerId);
	
	
	/**
	  * 方法描述：将所有买家资金渠道设为非默认
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 23, 2013 2:32:52 PM
	  */
	void initAllDefault();
	
	
	/**
	  * 方法描述：将指定买家资金渠道设为默认
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 23, 2013 2:34:04 PM
	  */
	void setDefault(Integer id);
}
