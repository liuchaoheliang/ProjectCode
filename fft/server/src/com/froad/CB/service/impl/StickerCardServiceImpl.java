package com.froad.CB.service.impl;

import javax.jws.WebService;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.Command;
import com.froad.CB.common.StickerCareBean;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.po.BuyerChannel;
import com.froad.CB.po.Buyers;
import com.froad.CB.po.transaction.Points;
import com.froad.CB.po.user.User;
import com.froad.CB.service.BuyerChannelService;
import com.froad.CB.service.BuyersService;
import com.froad.CB.service.PointService;
import com.froad.CB.service.StickerCardService;
import com.froad.CB.service.user.MUserService;
@WebService(endpointInterface="com.froad.CB.service.StickerCardService")
public class StickerCardServiceImpl implements StickerCardService {

	private static Logger logger = Logger.getLogger(StickerCardServiceImpl.class);
	
	private MUserService userService;
	private BuyerChannelService buyerChannelService;
	private BuyersService buyersService;
	private PointService pointService;

	public boolean addStickerCard(StickerCareBean stickerCardBean) {
		
		logger.info("输入数据："+JSONObject.fromObject(stickerCardBean));
		
		//验证手机号
		User user=null;
		try {
			user=userService.selectUserMobilephone(stickerCardBean.getPhone());
		} catch (AppException e) {
			logger.error("手机号码查询用户", e);
			return false;
		}
		
		//不存在用户 注册
		if(!("0").equals(user.getRespCode())){
			logger.info("不存在用户："+stickerCardBean.getPhone());
			user=new User();
			user.setMobilephone(stickerCardBean.getPhone());
			user.setPassword(stickerCardBean.getIdentificationCard().substring(stickerCardBean.getIdentificationCard().length()-6, stickerCardBean.getIdentificationCard().length()));
			user.setCreateChannel("FFT");
			user=userService.clientRegister(user,false);
			
			//注册失败
			if(!("0").equals(user.getRespCode())){
				logger.info("注册会员失败");
				return false;
			}
			logger.info("注册会员成功 userID："+user.getUserID()+" username："+user.getUsername()+" mobilePhone："+user.getMobilephone());
		}
		
		//成为买家 
		Buyers buyers=null;
		buyers=buyersService.getBuyerByUserId(user.getUserID());
		if(buyers==null){
			logger.info("用户："+user.getUserID()+"买家不存在");
			//增加买家
			buyers=new Buyers();
			buyers.setUserId(user.getUserID());
			buyers.setState(Command.STATE_START);
			Integer buyerId=buyersService.addBuyer(buyers);
			buyers.setId(buyerId);
			logger.info("增加用户"+user.getUserID()+"买家"+buyerId+"成功");
		}
		
		//买家资金渠道
		BuyerChannel buyerChannel=new BuyerChannel();
		buyerChannel.setUserId(user.getUserID());
		buyerChannel.setBuyersId(buyers.getId()+"");
		buyerChannel.setChannelId(TranCommand.CHANNEL_ID);
		buyerChannel.setBuyersRuleId("100001001");
		buyerChannel.setAccountName(stickerCardBean.getAccountName());
		buyerChannel.setAccountNumber(stickerCardBean.getAccountNumber());
		buyerChannel.setPhone(stickerCardBean.getPhone());
		buyerChannel.setState(Command.STATE_START);
		
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
		
		//兑充积分
		Points points=new Points();
		points.setAccountMarked(user.getUsername());
		points.setMobilePhone(user.getMobilephone());
		points.setOrgPoints(stickerCardBean.getPointValue());
		
		try {
			points = pointService.fillPoints(points);
			
			if(!("0").equals(points.getRespCode())){
				logger.info("兑充积分失败");
				return false;
			}
		} catch (AppException e) {
			logger.error("兑充积分出错", e);
			return false;
		}
		
		return true;
	}
	
	public MUserService getUserService() {
		return userService;
	}

	public void setUserService(MUserService userService) {
		this.userService = userService;
	}
	
	public BuyerChannelService getBuyerChannelService() {
		return buyerChannelService;
	}

	public void setBuyerChannelService(BuyerChannelService buyerChannelService) {
		this.buyerChannelService = buyerChannelService;
	}
	
	public BuyersService getBuyersService() {
		return buyersService;
	}

	public void setBuyersService(BuyersService buyersService) {
		this.buyersService = buyersService;
	}
	
	public PointService getPointService() {
		return pointService;
	}

	public void setPointService(PointService pointService) {
		this.pointService = pointService;
	}

}
