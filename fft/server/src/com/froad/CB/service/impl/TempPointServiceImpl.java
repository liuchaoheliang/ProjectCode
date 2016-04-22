package com.froad.CB.service.impl;

import java.math.BigDecimal;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.AppException.AppException;
import com.froad.CB.dao.TempPointDao;
import com.froad.CB.po.TempPoint;
import com.froad.CB.po.transaction.Points;
import com.froad.CB.service.BuyerChannelService;
import com.froad.CB.service.BuyersService;
import com.froad.CB.service.PointService;
import com.froad.CB.service.TempPointService;
import com.froad.util.Assert;

@WebService(endpointInterface="com.froad.CB.service.TempPointService")
public class TempPointServiceImpl implements TempPointService{

	private static final Logger logger=Logger.getLogger(TempPointServiceImpl.class);
	
	private TempPointDao tempPointDao;
	private BuyerChannelService buyerChannelService;
	private BuyersService buyersService;
	private PointService pointService;
	
	
	@Override
	public Integer add(TempPoint point) {
		if(point==null){
			logger.error("参数为空，添加失败");
			return null;
		}
		return tempPointDao.add(point);
	}

	@Override
	public boolean deleteById(Integer id) {
		if(id==null){
			logger.error("编号为空，删除失败");
			return false;
		}
		return tempPointDao.deleteById(id);
	}

	@Override
	public TempPoint getTempPointById(Integer id) {
		if(id==null){
			logger.error("编号为空，查询失败");
			return null;
		}
		return tempPointDao.getTempPointById(id);
	}

	@Override
	public boolean updateById(TempPoint point) {
		if(point==null||point.getId()==null){
			logger.error("参数为空，修改失败");
			return false;
		}
		return tempPointDao.updateById(point);
	}

	public void setTempPointDao(TempPointDao tempPointDao) {
		this.tempPointDao = tempPointDao;
	}

	@Override
	public List<TempPoint> getTempPointBySelective(TempPoint point) {
		return tempPointDao.getTempPointBySelective(point);
	}

	@Override
	public TempPoint getTempPointByPager(TempPoint point) {
		if(point==null){
			logger.error("参数为空，分页查询失败");
			return null;
		}
		return tempPointDao.getTempPointByPager(point);
	}
	
	public boolean updateTempPointAndFillPoint(String userId,String userName,String mobile,List<TempPoint> pointList) throws AppException{
//		TempPoint pointReq = new TempPoint();
//		//更新临时积分
//		pointReq.setActivateAccount(userId);
//		pointReq.setIsActivate("1");//激活
//		pointReq.setId(point.getId());
//		tempPointDao.updateById(pointReq);		
		//成为买家 
//		Buyers buyers=null;
//		buyers=buyersService.getBuyerByUserId(userId);
//		if(buyers==null){
//			logger.info("用户："+userId+"买家不存在");
//			//增加买家
//			buyers=new Buyers();
//			buyers.setUserId(userId);
//			buyers.setState(Command.STATE_START);
//			Integer buyerId=buyersService.addBuyer(buyers);
//			buyers.setId(buyerId);
//			logger.info("增加用户"+userId+"买家"+buyerId+"成功");
//		}
//		
//		//买家资金渠道
//		BuyerChannel buyerChannel=new BuyerChannel();
//		buyerChannel.setUserId(userId);
//		buyerChannel.setBuyersId(buyers.getId()+"");
//		buyerChannel.setChannelId(TranCommand.CHANNEL_ID);
//		buyerChannel.setBuyersRuleId("100001001");
//		buyerChannel.setAccountName(point.getAccountName());
//		buyerChannel.setAccountNumber(point.getAccountNumber());
//		buyerChannel.setState(Command.STATE_START);
//		
//		int rows=buyerChannelService.updateChannelByBuyerId(buyerChannel);
//		logger.info(rows==0?"没有 ":"已存在"+"买家："+buyers.getId()+"资金渠道");
//		if(rows<1){
//			Integer buyerChannelId=null;
//			
//			try{
//				buyerChannelId=buyerChannelService.addBuyerChannel(buyerChannel);
//				
//			}
//			catch(Exception e){
//				logger.error("增加买家资金渠道出错", e);
//				return false;
//			}
//			
//			if(buyerChannelId==null){
//				logger.info("增加买家资金渠道失败");
//				return false;
//			}
//			logger.info("增加买家"+buyers.getId()+"资金渠道成功");
//		}
		boolean flag = false;
		BigDecimal points = new BigDecimal("0");
		TempPoint pointReq = null;
		for(TempPoint point:pointList){
			if(!Assert.empty(point.getIsActivate()) && "0".equals(point.getIsActivate())){//未激活
				pointReq = new TempPoint();
				//更新临时积分
				pointReq.setActivateAccount(userId);
				pointReq.setIsActivate("1");//激活
				pointReq.setId(point.getId());
				try {
					tempPointDao.updateById(pointReq);
					if(!Assert.empty(point.getPointValue())){
						points = points.add(new BigDecimal(point.getPointValue())).setScale(2, BigDecimal.ROUND_DOWN);
					}
				} catch (Exception e) {
					logger.info("更新临时积分信息失败 临时积分id："+pointReq.getId());
					throw new AppException("兑充积分失败");
				}						
			}
		}
		if(points!=null && !Assert.empty(points.toString()) && !"0".equals(points.toString())){
			logger.info("兑充积分开始");
			//兑充积分
			Points pointRes=new Points();
			pointRes.setAccountMarked(userName);
			pointRes.setMobilePhone(mobile);
			pointRes.setOrgPoints(points.toString());
			
			Points pointResult = new Points();
			try {
				pointResult = pointService.fillPoints(pointRes);
			} catch (AppException e) {
				logger.info("兑充积分出错",e);
				throw new AppException("兑充积分失败");
			}
			if(!("0").equals(pointResult.getRespCode())){
				logger.info("兑充积分失败 point:"+points.toString());
				throw new AppException("兑充积分失败");
			}else{
				flag = true;
			}			
					
		}else{//没有积分
			logger.error("激活积分时，没有要兑充的积分。");
		}
		return true;
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
