package com.froad.action.web.bankPointActivate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.TempPointActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.UserCertificationActionSupport;
import com.froad.action.support.fundsChannel.FundsChannelActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.point.PointsAccount;
import com.froad.client.tempPoint.TempPoint;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.common.SessionKey;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-7  
 * @version 1.0
 */
public class BankPointActivateAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 7126903966310797411L;
	private UserActionSupport userActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private FundsChannelActionSupport fundsChannelActionSupport;
	private UserCertificationActionSupport userCertificationActionSupport;
	private TempPointActionSupport tempPointActionSupport;
	private PointActionSupport pointActionSupport;
	private UserCertification userCertification;
	private TempPoint tempPoint;
	private String bankPoint;
	
	/**
	 * 进入银行积分激活认证页面
	 * 如果没有登陆返回login.ftl
	 * @return String
	 */
	public String toActivate(){
		String userId = (String)getSession(MallCommand.USER_ID);
		if(Assert.empty(userId)){
			return "failse";
		}
		PointsAccount pointaccount = null;
		PointsAccount pointaccountBank = null;
		Map<String,PointsAccount> pointtmp = (Map<String,PointsAccount>)getSession(SessionKey.POINTS_ACCOUNT_MAP);
		String uname = (String)getSession(userId);
		if(pointtmp == null){	
			Map<String,PointsAccount> pointsTypePointsAccountMap =pointActionSupport.queryPointsByUsername(uname);
			if(!Assert.empty(pointsTypePointsAccountMap)){
				//setSession("pointsTypePointsccountMap", pointsTypePointsAccountMap);
				pointaccount = pointsTypePointsAccountMap.get("FFTPlatform");
				pointaccountBank = pointsTypePointsAccountMap.get("ZHBank");
			}							
		}else{
			Boolean changedPoints = true;
			changedPoints = (Boolean)getSession("changedPoints");
			if(changedPoints != null && changedPoints){
				Map<String,PointsAccount> pointsTypePointsAccountMap =pointActionSupport.queryPointsByUsername(uname);
				if(!Assert.empty(pointsTypePointsAccountMap)){
					//setSession(POINTS_ACCOUNT_MAP, pointsTypePointsAccountMap);
					pointaccount = pointsTypePointsAccountMap.get("FFTPlatform");
					pointaccountBank = pointsTypePointsAccountMap.get("ZHBank");
				}							
			}else{
				pointaccount = pointtmp.get("FFTPlatform");
				pointaccountBank = pointtmp.get("ZHBank");
			}
		}
		bankPoint = pointaccountBank==null || pointaccountBank.getPoints()==null?"0":pointaccountBank.getPoints();
		return "success";
	}
	
	/**
	 * fenfentong
	 * 会员银行积分激活
	 * @return void
	 */
	public String bankPointActivate(){
		List<TempPoint> pointList = null;
		log.info("激活积分开始！");
		User u = getLoginUser();
		User tpReq1 = new User();
//		tpReq1
		tpReq1.setUserID((String)getSession(MallCommand.LOGIN_USER_ID));
		User user = null;
		try {
			user = userActionSupport.getUserInfoByUsername(u.getUsername());
		} catch (AppException_Exception e1) {
			log.info("激活积分根据用户名查询用户信息出错",e1);
		}
		log.info("账户号："+tempPoint.getAccountNumber()+"身份证号："+tempPoint.getIdentificationCard()+"开户人姓名："+tempPoint.getAccountName());
		Map<String,String> resultMap=new HashMap<String,String>();
		
		if(tempPoint==null || Assert.empty(tempPoint.getAccountName()) || Assert.empty(tempPoint.getIdentificationCard())){
			resultMap.put("status","error");
			resultMap.put("message","请输入账户开户名和证件号信息！");
			return ajaxJson(resultMap);
		}
		
		tempPoint.setCardType("2");//金海洋/信通卡
		tempPoint.setState("30");
		
		pointList=tempPointActionSupport.getTempPoint(tempPoint);//查询临时银行积分信息（一份身份证可能对应多个银行账户）
		
		//判断是否锁定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
		Date currTime = new Date();
		String nowTime = sdf.format(new Date());
		BigDecimal apartTime = Command.TEMP_POINT_ACTIVATE_LOCKED_TIMES;//当前时间和被锁定的时间差(小时)
		BigDecimal timeTemp = new BigDecimal("0");
		String lockedTime = apartTime.toString();
		String flagTimeType = "小时";
		if(apartTime!=null && apartTime.compareTo(new BigDecimal("1")) < 0){
			flagTimeType = "分钟";
			lockedTime = apartTime.multiply(new BigDecimal("60")).toString();
		}
		try {
			if(!Assert.empty(user.getPointActivateLockedDate())){
				Date lockTime = sdf.parse(user.getPointActivateLockedDate());
				int numberBei = 1000*60*60;
				apartTime =  (new BigDecimal(currTime.getTime()).subtract(new BigDecimal(lockTime.getTime()))).divide(new BigDecimal(String.valueOf(numberBei)),2,RoundingMode.DOWN);
				timeTemp = (new BigDecimal(currTime.getTime()).subtract(new BigDecimal(lockTime.getTime()))).divide(new BigDecimal(String.valueOf(numberBei)),6,RoundingMode.DOWN);
			}
		} catch (ParseException e) {
			log.info("激活积分判断锁定时间是否超过"+lockedTime+flagTimeType+"出错！",e);
			log.error("激活积分判断锁定时间是否超过"+lockedTime+flagTimeType+"出错！",e);
		}
		if("0".equals(user.getIsPointActivateAccountLocked()) && apartTime.compareTo(Command.TEMP_POINT_ACTIVATE_LOCKED_TIMES) < 0){
			resultMap.put("status","error");
			resultMap.put("message","当天您已三次激活失败，基于安全激活积分被锁定。");
			return ajaxJson(resultMap);
		}
		if("0".equals(user.getIsPointActivateAccountLocked()) && timeTemp.compareTo(Command.TEMP_POINT_ACTIVATE_LOCKED_TIMES) > 0){
			//次数清零,超多多顶时间(24小时)
			tpReq1.setPointActivateFailureCount(0);
			tpReq1.setUsername(user.getUsername());
			try {
				userActionSupport.updateUserLastInfoByUsername(tpReq1);
			} catch (AppException_Exception e) {
				log.info("激活积分锁定次数清零信息出错",e);
				log.error("激活积分锁定次数清零信息出错",e);
			}
			user.setPointActivateFailureCount(0);
		}
		if(pointList == null || pointList.size() == 0){
			log.info("激活失败，无相记录");
			resultMap = lockedCheck(tempPoint,user);
			resultMap.put("message","激活失败！您可能不是珠海农商银行客户，或您的账户积分未达积分使用标准，详情请查看下方提示。");
			return ajaxJson(resultMap);
			//没有找到相关信息激活积分失败
			
			
//			//条件正确，但已经激活，不算失败
//			TempPoint tp = tempPoint;
//			tp.setIsActivate("1");
//			List<TempPoint> pList=tempPointActionSupport.getTempPoint(tp);
//			if(pList != null && pList.size()>0){
//				resultMap.put("status","error");
//				resultMap.put("message","激活积分失败！您已有银行积分激活记录，无法再次激活！");
//				return ajaxJson(resultMap);
//			}
//			//如果同一个账号一天内三次激活失败将被锁定
//			resultMap = lockedCheck(tempPoint,user);
////			resultMap.put("status","error");
////			resultMap.put("message","激活失败！。");
//			return ajaxJson(resultMap);
		}
//		boolean activateFlag = false;//true:存在未激活的银行积分  false:不存在未激活的银行积分
//		for(TempPoint pt:pointList){
//			if("0".equals(pt.getIsActivate())){
//				activateFlag = true;
//				break;
//			}
//		}
//		if(!activateFlag){
//			resultMap.put("status","error");
//			resultMap.put("message","激活失败！您的积分已被激活。");
//			return ajaxJson(resultMap);
//		}	
		
		boolean falg=false;
		
		//查询未激活的所有记录
		tempPoint.setIsActivate("0");
		tempPoint.setAccountNumber(null);
		pointList=tempPointActionSupport.getTempPoint(tempPoint);
		if(pointList == null || pointList.size() == 0){
			log.info("激活失败，无未激活记录");
			resultMap.put("status","error");
			resultMap.put("message","激活失败！您的银行积分已经激活，无需重复操作。");
			return ajaxJson(resultMap);
		}
		
		falg = tempPointActionSupport.updateTempPointAndFillPoint(u.getUserID(),u.getUsername(),u.getMobilephone(), pointList);
		
		if(falg){
			setSession("changedPoints",true);
		}
		
		
		//重新查验积分
		PointsAccount pointaccountBank = null;
		Map<String,PointsAccount> pointsTypePointsAccountMap =pointActionSupport.queryPointsByUsername(u.getUsername());
		if(!Assert.empty(pointsTypePointsAccountMap)){
			pointaccountBank = pointsTypePointsAccountMap.get("ZHBank");
		}	
		
		if(falg){
			resultMap.put("status","success");
			resultMap.put("message","激活成功！");
			//解除锁（激活成功就更新锁状态为初始状态，失败次数清零）
			tpReq1.setIsPointActivateAccountLocked("1");
			tpReq1.setPointActivateFailureCount(0);
			tpReq1.setLastPointActivateTime(nowTime);
			tpReq1.setUsername(user.getUsername());
			try {
				userActionSupport.updateUserLastInfoByUsername(tpReq1);
			} catch (AppException_Exception e) {
				log.info("激活成功后更新用户激活积分锁定信息出错",e);
				log.error("激活成功后更新用户激活积分锁定信息出错",e);
			}
			
			if(pointaccountBank!=null){
				resultMap.put("zhbank",pointaccountBank.getPoints());
			}
			else{
				resultMap.put("zhbank","0");
			}
			
			return ajaxJson(resultMap);
		}else{
			resultMap.put("status","error");
			resultMap.put("message","激活失败！");
			return ajaxJson(resultMap);
		}
	}
	
	/**
	 * 积分失败进行操作：账号激活安全check
	 * 
	 * @param tempPoint
	 */
	public Map<String,String> lockedCheck(TempPoint tp,User user){
		Map<String,String> resultMap=new HashMap<String,String>();
		User tpReq = new User();
		tpReq.setUserID((String)getSession(MallCommand.LOGIN_USER_ID));
		//判断是否已锁定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
		Date currTime = new Date();
		String nowTime = sdf.format(new Date());
		BigDecimal apartTime = Command.TEMP_POINT_ACTIVATE_LOCKED_TIMES;//当前时间和被锁定的时间差(小时)
		String lockedTime = apartTime.toString();
		String flagTimeType = "小时";
		if(apartTime!=null && apartTime.compareTo(new BigDecimal("1")) < 0){
			flagTimeType = "分钟";
			lockedTime = apartTime.multiply(new BigDecimal("60")).toString();
		}
		try {
			if(!Assert.empty(user.getPointActivateLockedDate())){
				Date lockTime = sdf.parse(user.getPointActivateLockedDate());
				//apartTime =  (int)((currTime.getTime() - lockTime.getTime())/1000/60/60);	
				int numberBei = 1000*60*60;
				apartTime =  (new BigDecimal(currTime.getTime()).subtract(new BigDecimal(lockTime.getTime()))).divide(new BigDecimal(String.valueOf(numberBei)),2,RoundingMode.DOWN);
			}
		} catch (ParseException e) {
			log.info("激活积分判断锁定时间是否超过"+lockedTime+flagTimeType+"出错！",e);
			log.error("激活积分判断锁定时间是否超过"+lockedTime+flagTimeType+"出错！",e);
		}
		if("1".equals(user.getIsPointActivateAccountLocked()) || ("0".equals(user.getIsPointActivateAccountLocked()) && apartTime.compareTo(Command.TEMP_POINT_ACTIVATE_LOCKED_TIMES) > 0)){
			Integer lockedNum = user.getPointActivateFailureCount()+1;
			
			if(lockedNum >= Command.TEMP_POINT_ACTIVATE_TIMES){
				resultMap.put("status","error");
				resultMap.put("message","激活积分账户失败已达三次，将被锁定"+lockedTime+flagTimeType+"！");
				tpReq.setIsPointActivateAccountLocked("0");
				tpReq.setPointActivateFailureCount(lockedNum);
				tpReq.setPointActivateLockedDate(nowTime);
			}else{
				resultMap.put("status","error");
				resultMap.put("message","激活积分失败！您已失败&nbsp;<b style=\"font:14px/24px '微软雅黑'\">"+lockedNum+"&nbsp;</b>次，若连续&nbsp;<b style=\"font:14px/24px '微软雅黑'\">3</b>&nbsp;次激活失败，24小时内将无法继续使用激活积分功能！");
				tpReq.setIsPointActivateAccountLocked("1");
				tpReq.setPointActivateFailureCount(lockedNum);
			}
		}
		tpReq.setLastPointActivateTime(nowTime);
		tpReq.setUsername(user.getUsername());
		try {
			userActionSupport.updateUserLastInfoByUsername(tpReq);
		} catch (AppException_Exception e) {
			log.info("更新用户激活积分锁定信息出错",e);
			log.error("更新用户激活积分锁定信息出错",e);
		}
		return resultMap;
	}
	
	
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}
	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	public UserCertificationActionSupport getUserCertificationActionSupport() {
		return userCertificationActionSupport;
	}
	public void setUserCertificationActionSupport(
			UserCertificationActionSupport userCertificationActionSupport) {
		this.userCertificationActionSupport = userCertificationActionSupport;
	}
	public TempPointActionSupport getTempPointActionSupport() {
		return tempPointActionSupport;
	}
	public void setTempPointActionSupport(
			TempPointActionSupport tempPointActionSupport) {
		this.tempPointActionSupport = tempPointActionSupport;
	}

	public UserCertification getUserCertification() {
		return userCertification;
	}

	public void setUserCertification(UserCertification userCertification) {
		this.userCertification = userCertification;
	}

	public TempPoint getTempPoint() {
		return tempPoint;
	}

	public void setTempPoint(TempPoint tempPoint) {
		this.tempPoint = tempPoint;
	}

	public FundsChannelActionSupport getFundsChannelActionSupport() {
		return fundsChannelActionSupport;
	}

	public void setFundsChannelActionSupport(
			FundsChannelActionSupport fundsChannelActionSupport) {
		this.fundsChannelActionSupport = fundsChannelActionSupport;
	}

	public String getBankPoint() {
		return bankPoint;
	}

	public void setBankPoint(String bankPoint) {
		this.bankPoint = bankPoint;
	}

	public PointActionSupport getPointActionSupport() {
		return pointActionSupport;
	}

	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}
	
	
}
