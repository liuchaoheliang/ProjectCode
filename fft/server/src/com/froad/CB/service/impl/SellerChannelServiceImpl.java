package com.froad.CB.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import com.froad.CB.dao.SellerChannelDao;
import com.froad.CB.po.SellerChannel;
import com.froad.CB.service.SellerChannelService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-1  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.SellerChannelService")
public class SellerChannelServiceImpl implements SellerChannelService {
	private static final Logger logger = Logger.getLogger(SellerChannelServiceImpl.class);
	private SellerChannelDao sellerChannelDao;
	
	@Override
	public Integer addSellerChannel(SellerChannel sellerChannel) {
		if(sellerChannel==null){
			logger.info("增加卖家付款渠道参数不能为空！");
			return null;
		}
		return sellerChannelDao.insert(sellerChannel);
	}

	@Override
	public Integer updateById(SellerChannel sellerChannel) {
		if(sellerChannel==null){
			logger.info("更新卖家付款渠道参数不能为空！");
			return null;
		}
		return sellerChannelDao.updateById(sellerChannel);
	}

	@Override
	public SellerChannel selectById(Integer id) {
		if(id==null){
			logger.info("查询卖家付款渠道参数id不能为空！");
			return null;
		}
		return sellerChannelDao.selectByPrimaryKey(id);
	}

	@Override
	public Integer deleteById(String id) {
		if(id==null){
			logger.info("删除卖家付款渠道参数id不能为空！");
			return null;
		}
		return sellerChannelDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(id==null){
			logger.info("删除卖家付款渠道参数id不能为空！");
			return null;
		}
		return sellerChannelDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public SellerChannel getSellerChannelByUserId(String userId) {
		if(userId==null){
			logger.info("查询卖家付款渠道参数userId不能为空！");
			return null;
		}
		return sellerChannelDao.getSellerChannelByUserId(userId);
	}

	@Override
	public List<SellerChannel> getSellerChannelByMerchantId(String merchantId) {
		if(merchantId==null){
			logger.info("查询卖家付款渠道参数merchantId不能为空！");
			return null;
		}
		return sellerChannelDao.getSellerChannelByMerchantId(merchantId);
	}

	@Override
	public SellerChannel getSellerChannelBySellerId(String sellerId) {
		if(sellerId==null){
			logger.info("查询卖家付款渠道参数sellerId不能为空！");
			return null;
		}
		return sellerChannelDao.getSellerChannelBySellerId(sellerId);
	}

	public SellerChannelDao getSellerChannelDao() {
		return sellerChannelDao;
	}

	public void setSellerChannelDao(SellerChannelDao sellerChannelDao) {
		this.sellerChannelDao = sellerChannelDao;
	}

}
