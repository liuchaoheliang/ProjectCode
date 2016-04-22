package com.froad.CB.service.impl;

import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.BuyerChannelDao;
import com.froad.CB.po.BuyerChannel;
import com.froad.CB.service.BuyerChannelService;



	/**
	 * 类描述：买家资金渠道service实现
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 2, 2013 5:00:18 PM 
	 */
@WebService(endpointInterface="com.froad.CB.service.BuyerChannelService")
public class BuyerChannelServiceImpl implements
		BuyerChannelService {
	private static final Logger logger=Logger.getLogger(BuyerChannelServiceImpl.class);
	private BuyerChannelDao buyerChannelDao;
	
	@Override
	public Integer addBuyerChannel(
			BuyerChannel buyerChannel) {
		if(buyerChannel==null){
			logger.info("增加买家支付渠道参数不能为空！");
			return null;
		}
		return buyerChannelDao.insert(buyerChannel);
	}

	@Override
	public Integer updateById(BuyerChannel buyerChannel) {
		if(buyerChannel==null){
			logger.info("更新买家支付渠道参数不能为空！");
			return null;
		}
		return buyerChannelDao.updateById(buyerChannel);
	}

	@Override
	public BuyerChannel selectById(Integer id) {
		if(id==null){
			logger.info("查询买家支付渠道参数id不能为空！");
			return null;
		}
		return buyerChannelDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteById(String id) {
		if(id==null){
			logger.info("删除买家支付渠道参数id不能为空！");
			return null;
		}
		return buyerChannelDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(id==null){
			logger.info("删除买家支付渠道参数id不能为空！");
			return null;
		}
		return buyerChannelDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public BuyerChannel getBuyerChannelByUserId(String userId) {
		if(userId==null){
			logger.info("查询买家支付渠道参数userId不能为空！");
			return null;
		}
		return buyerChannelDao.getBuyerChannelByUserId(userId);
	}

	@Override
	public BuyerChannel getBuyerChannelByMerchantId(
			String merchantId) {
		if(merchantId==null){
			logger.info("查询买家支付渠道参数merchantId不能为空！");
			return null;
		}
		return buyerChannelDao.getBuyerChannelByBuyerId(merchantId);
	}

	public void setBuyerChannelDao(
			BuyerChannelDao buyerChannelDao) {
		this.buyerChannelDao = buyerChannelDao;
	}

	@Override
	public boolean setDefaultChannel(Integer id) {
		if(id==null){
			logger.error("参数为空");
			return false;
		}
		buyerChannelDao.initAllDefault();
		buyerChannelDao.setDefault(id);
		return true;
	}
	
	public int updateChannelByBuyerId(BuyerChannel buyerChannel) {
		return buyerChannelDao.updateChannelByBuyerId(buyerChannel);
	}
	
}
