package com.froad.CB.service.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.froad.CB.common.Command;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.dao.BuyersDao;
import com.froad.CB.dao.user.UserDao;
import com.froad.CB.po.BuyerChannel;
import com.froad.CB.po.Buyers;
import com.froad.CB.po.user.User;
import com.froad.CB.service.BuyerChannelService;
import com.froad.CB.service.BuyersService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-29  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.BuyersService")
public class BuyersServiceImpl implements BuyersService {
	private static final Logger logger=Logger.getLogger(BuyersServiceImpl.class);
	private BuyersDao buyersDao;
	private UserDao userDao;
	private BuyerChannelService buyerChannelService;

	@Override
	public Integer addBuyer(Buyers buyer) {
		if(buyer==null){
			logger.info("增加买家参数不能为空！");
			return null;
		}
		return buyersDao.insert(buyer);
	}

	@Override
	public Integer updateById(Buyers buyer) {
		if(buyer==null){
			logger.info("修改买家参数不能为空！");
			return null;
		}
		return buyersDao.updateById(buyer);
	}

	@Override
	public Buyers getById(Integer id) {
		if(id==null){
			logger.info("查询买家参数不能为空！");
			return null;
		}
		Buyers buyer=buyersDao.selectByPrimaryKey(id);
		setUserForBuyer(buyer);
		return buyer;
	}

	private void setUserForBuyer(Buyers buyer){
		if(buyer!=null&&buyer.getUserId()!=null){
			User user=userDao.queryUserAllByUserID(buyer.getUserId());
			if(user==null){
				logger.error("买家对应的用户不存在");
			}else{
				buyer.setUser(user);
			}
		}
	}
	
	@Override
	public Integer deleteById(String id) {
		if(id==null){
			logger.info("删除买家参数不能为空！");
			return null;
		}
		return buyersDao.deleteByPrimaryKey(id);
	}

	@Override
	public Integer deleteStateById(String id) {
		if(id==null){
			logger.info("删除买家参数不能为空！");
			return null;
		}
		return buyersDao.deleteStateByPrimaryKey(id);
	}

	@Override
	public Buyers getBuyerByUserId(String userId) {
		if(userId==null){
			logger.info("查询买家参数userId不能为空！");
			return null;
		}
		Buyers buyer=buyersDao.getBuyersByUserId(userId);
		setUserForBuyer(buyer);
		return buyer;
	}
	
	@Override
	public boolean updateBuyerAndBuyerChannel(String userId,String mobile){
		//成为买家 
		Buyers buyers=null;
		buyers=buyersDao.getBuyersByUserId(userId);
		if(buyers==null){
			logger.info("用户："+userId+"买家不存在");
			//增加买家
			buyers=new Buyers();
			buyers.setUserId(userId);
			buyers.setState(Command.STATE_START);
			Integer buyerId=buyersDao.insert(buyers);
			buyers.setId(buyerId);
			logger.info("增加用户"+userId+"买家"+buyerId+"成功");
		}
		
		//买家资金渠道
		BuyerChannel buyerChannel=new BuyerChannel();
		buyerChannel.setUserId(userId);
		buyerChannel.setBuyersId(buyers.getId()+"");
		buyerChannel.setChannelId(TranCommand.CHANNEL_ID);
		buyerChannel.setBuyersRuleId("100001001");
		buyerChannel.setState(Command.STATE_START);
		buyerChannel.setPhone(mobile);
		
		int rows=buyerChannelService.updateChannelByBuyerId(buyerChannel);
		logger.info(rows==0?"没有 ":"已存在"+"买家："+buyers.getId()+"资金渠道");
		if(rows<1){
			Integer buyerChannelId=null;
			
			try{
				buyerChannelId=buyerChannelService.addBuyerChannel(buyerChannel);
			}
			catch(Exception e){
				logger.error("增加买家资金渠道出错", e);
				return false;
			}
			
			if(buyerChannelId==null){
				logger.info("增加买家资金渠道失败");
				return false;
			}
			logger.info("增加买家"+buyers.getId()+"资金渠道成功");
		}
		
		return true;
	}

	public BuyersDao getBuyersDao() {
		return buyersDao;
	}

	public void setBuyersDao(BuyersDao buyersDao) {
		this.buyersDao = buyersDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	
	public BuyerChannelService getBuyerChannelService() {
		return buyerChannelService;
	}

	public void setBuyerChannelService(BuyerChannelService buyerChannelService) {
		this.buyerChannelService = buyerChannelService;
	}
	
}
