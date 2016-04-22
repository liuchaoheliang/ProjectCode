package com.froad.action.support;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.support.fundsChannel.FundsChannelActionSupport;
import com.froad.client.point.AppException_Exception;
import com.froad.client.point.PointService;
import com.froad.client.point.Points;
import com.froad.client.point.PointsAccount;
import com.froad.client.user.User;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.Result;
import com.froad.util.command.PayCommand;
import com.opensymphony.xwork2.ActionContext;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 积分  client service  ActionSupport
 */
public class PointActionSupport {
	private static Logger logger = Logger.getLogger(PointActionSupport.class);
	private PointService pointService;
	private UserActionSupport userActionSupport;
	private FundsChannelActionSupport fundsChannelActionSupport;
	
	
	
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}
	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	public FundsChannelActionSupport getFundsChannelActionSupport() {
		return fundsChannelActionSupport;
	}
	public void setFundsChannelActionSupport(
			FundsChannelActionSupport fundsChannelActionSupport) {
		this.fundsChannelActionSupport = fundsChannelActionSupport;
	}
	public PointService getPointService() {
		return pointService;
	}
	public void setPointService(PointService pointService) {
		this.pointService = pointService;
	}
	
	/**
	 * 
	  * 方法描述：查询积分
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-19 下午04:25:32
	 */
	public Points queryPoints(Points points){
		Points point = new Points();
		try {
			point = pointService.queryPoints(points);
		} catch (Exception e) {
			logger.error("PointActionSupport.queryPoints查询积分出错 name:"+points.getAccountMarked(), e);
		}
		return point;
	}
	
	
	/**
	 * 
	  * 方法描述：查询分分通积分
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-26 下午08:28:54
	 */
	public PointsAccount queryfftPoints(String userName){
		PointsAccount pointsAccount = null;
		Map<String,PointsAccount> pointsTypePointsAccountMap =queryPointsByUsername(userName);
		if(Assert.empty(pointsTypePointsAccountMap))
		return pointsAccount;
		return pointsTypePointsAccountMap.get("FFTPlatform");
	}
	
	
	/**
	 * 
	  * 方法描述：查询珠海银行积分
	  * 
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-26 下午08:28:54
	 */
	public PointsAccount queryBankPointsByUserName(String userName){
		PointsAccount pointsAccount = null;
		Map<String,PointsAccount> pointsTypePointsAccountMap =queryPointsByUsername(userName);
		if(Assert.empty(pointsTypePointsAccountMap))
		return pointsAccount;
		return pointsTypePointsAccountMap.get("ZHBank");
	}

	
	/**
	  * 方法描述：更新session里的积分账户信息
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 8, 2013 10:02:40 PM
	  */
	public void updateSessionPointsAccount(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get(SessionKey.USER_ID);
		if(userId==null||"".equals(userId)){
			return;
		}
		Map<String,PointsAccount> acctMap = this.queryPointsByUserId(userId);
		if(acctMap!=null){
			session.put(SessionKey.POINTS_ACCOUNT_MAP,acctMap);
		}
	}
	

	/**
	 * 
	  * 方法描述：查询会员的珠海银行积分和分分通积分
	  * 返回值的key为：accountType,即  珠海银行积分：ZHBank,FFTPlatform
	  * 
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-26 下午08:28:54
	 */
	public Map<String,PointsAccount> queryPointsByUsername(String userName){
		Map<String,PointsAccount> pointsTypePointsAccountMap =new HashMap<String,PointsAccount>();
		Points point= new Points();
		point.setPartnerNo(TranCommand.PARTNER_ID);
		point.setAccountMarked(userName);
		point.setAccountMarkedType(FroadAPICommand.ACCOUNT_MARKED_TYPE);
		Points pointsRes=this.queryPoints(point);
		
		if(pointsRes==null||!PayCommand.RESPCODE_SUCCESS.equals(pointsRes.getResultCode())){
			return pointsTypePointsAccountMap;
		}	
		for(PointsAccount pointsAccount:pointsRes.getPointsAccountList()){
			if(TranCommand.FFT_ORG_NO.equals(pointsAccount.getOrgNo())){
				pointsTypePointsAccountMap.put(SessionKey.FFT_POINTS_ACCOUNT, pointsAccount);
			}
			if(TranCommand.BANK_ORG_NO.equals(pointsAccount.getOrgNo())){
				pointsTypePointsAccountMap.put(SessionKey.BANK_POINTS_ACCOUNT, pointsAccount);
			}
		}
		return pointsTypePointsAccountMap;
	}
	
	
	/**
	  * 方法描述：查询会员所有的积分账户信息
	  * @param: userId 会员编号
	  * @return: 返回值的key为：accountType,即  珠海银行积分：ZHBank,FFTPlatform
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 8, 2013 10:10:28 PM
	  */
	public Map<String,PointsAccount> queryPointsByUserId(String userId){
		Map<String,Object> session=ActionContext.getContext().getSession();
		String username=(String)session.get(userId);
		if(username==null||"".equals(username)){
			return null;
		}
		return this.queryPointsByUsername(username);
	}
	
	
	 /* 
	  * 方法描述：查询会员的珠海银行积分和分分通积分
	  * 返回值的key为：accountType,即  珠海银行积分：ZHBank,FFTPlatform
	  * 
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-26 下午08:28:54
	 */
	public Map<String,PointsAccount> queryBankAndFftPointsByUserId(String userId){
		 User user=userActionSupport.queryUserAllByUserID(userId);
			if(user==null)
				return null;
			String userName=user.getUsername();
			return this.queryPointsByUsername(userName);
	}
	
	
	/**
	 * 
	  * 方法描述：查询珠海银行积分
	  * 
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-26 下午08:28:54
	 */
	public PointsAccount queryBankPointsByUserId(String userId){
		User user=userActionSupport.queryUserAllByUserID(userId);
		if(user==null)
			return null;
		String userName=user.getUsername();
		return this.queryBankPointsByUserName(userName);
	}
	
	
	
	
	/**
	 * 
	  * 方法描述：查询分分通积分
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-26 下午08:28:54
	 */
	public PointsAccount queryfftPointsByUserId(String userId){
		User user=userActionSupport.queryUserAllByUserID(userId);
		if(user==null)
			return null;
		String username=user.getUsername();
		return this.queryfftPoints(username);
	}
	
	
	/**
	  * 方法描述：校验用户积分
	  * @param: username 用户名
	  * @param: payMethod 支付方式
	  * @param：bankPoints 银行积分
	  * @param: fftPoints 分分通积分
	  * @return: Result
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 15, 2013 9:56:27 PM
	  */
	public Result checkPoints(String username,String payMethod,String bankPoints,String fftPoints){
		if(!TranCommand.CASH.equals(payMethod)){//不是纯现金支付，则校验积分
			Map<String,PointsAccount> acctMap =this.queryPointsByUsername(username);
			PointsAccount pointsAccount=null;
			if(TranCommand.POINTS_BANK.equals(payMethod)||
					TranCommand.POINTS_BANK_CASH.equals(payMethod)||
					TranCommand.POINTS_BANK_CASH_SCOPE.equals(payMethod)){
				pointsAccount=acctMap.get(SessionKey.BANK_POINTS_ACCOUNT);
				if(pointsAccount==null){
					return new Result(Result.FAIL,"您还没有银行积分账户，不能使用积分支付。");
				}
				BigDecimal points_remain=new BigDecimal(pointsAccount.getPoints());
				BigDecimal points_using=new BigDecimal(bankPoints);
				if(points_remain.compareTo(points_using)==-1){
					return new Result(Result.FAIL,"您的银行积分不够。");
				}
			}
			if(TranCommand.POINTS_FFT.equals(payMethod)||
					TranCommand.POINTS_FFT_CASH.equals(payMethod)||
					TranCommand.POINTS_FFT_CASH_SCOPE.equals(payMethod)){
				pointsAccount=acctMap.get(SessionKey.FFT_POINTS_ACCOUNT);
				if(pointsAccount==null){
					return new Result(Result.FAIL,"您还没有分分通积分账户，不能使用积分支付。");
				}
				BigDecimal points_remain=new BigDecimal(pointsAccount.getPoints());
				BigDecimal points_using=new BigDecimal(fftPoints);
				if(points_remain.compareTo(points_using)==-1){
					return new Result(Result.FAIL,"您的分分通积分不够。");
				}
			}
		}
		
		return new Result(Result.SUCCESS,"参数校验通过");
	}
	
	
	/**
	 * 
	  * 方法描述：赠送积分(如果赠送积分为零且积分平台没有此账户对应的积分账户，则相当于自动创建积分账户。)
	  * @param: Points
	  * @return: Points(resultCode为0000表示赠送成功，否则失败)
	  * @version: 1.0
	  * @time: 2013-3-6 下午04:25:32
	 */
	public Points presentPoints(Points points){
		Points pointRes = new Points();
		try {
			pointRes = pointService.presentPoints(points);
		} catch (AppException_Exception e) {
			logger.error("PointActionSupport.presentPoints赠送积分积分出错 name:"+points.getAccountMarked()+" point："+points.getPoints(), e);
		}
		return pointRes;
	}
}
