package com.froad.CB.service.activity.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.apache.openjpa.lib.log.Log;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.SmsLogType;
import com.froad.CB.dao.activity.ActivityLotteryResultDao;
import com.froad.CB.po.SmsLog;
import com.froad.CB.po.activity.ActivityCustInfo;
import com.froad.CB.po.activity.ActivityLotteryResult;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.service.MessageService;
import com.froad.CB.service.activity.ActivityCustInfoService;
import com.froad.CB.service.activity.ActivityLotteryResultService;
import com.froad.util.MessageSourceUtil;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.activity.ActivityLotteryResultService")
public class ActivityLotteryResultServiceImpl implements
		ActivityLotteryResultService {
	private static final Logger logger=Logger.getLogger(ActivityLotteryResultServiceImpl.class);	
	private ActivityLotteryResultDao activityLotteryResultDao;
	private ActivityCustInfoService activityCustInfoService;
	private MessageService messageService;
	
	@Override
	public Integer add(ActivityLotteryResult activityLotteryResult) {
		if(activityLotteryResult == null){
			logger.info("add:activityLotteryResult对象为空");
			return null;
		}
		return activityLotteryResultDao.add(activityLotteryResult);
	}

	@Override
	public ActivityLotteryResult getActivityLotteryResultById(Integer id) {
		if(id == null){
			logger.info("getActivityLotteryResultById:id为空");
			return null;
		}
		return activityLotteryResultDao.getActivityLotteryResultById(id);
	}

	@Override
	public Integer updateById(ActivityLotteryResult activityLotteryResult) {
		if(activityLotteryResult == null){
			logger.info("updateById:activityLotteryResult对象为空");
			return 0;
		}
		return activityLotteryResultDao.updateById(activityLotteryResult);
	}

	@Override
	public Integer updateBySmsCountId(
			ActivityLotteryResult activityLotteryResult) {
		if(activityLotteryResult == null){
			logger.info("updateBySmsCountId:activityLotteryResult对象为空");
			return 0;
		}
		return activityLotteryResultDao.updateBySmsCountId(activityLotteryResult);
	}

	@Override
	public List<ActivityLotteryResult> getActivityLotteryResultBySelective(
			ActivityLotteryResult activityLotteryResult) {
		if(activityLotteryResult == null){
			logger.info("getActivityLotteryResultBySelective:activityLotteryResult对象为空");
			return null;
		}
		return activityLotteryResultDao.getActivityLotteryResultBySelective(activityLotteryResult);
	}

	@Override
	public ActivityLotteryResult getActivityLotteryResultByPager(
			ActivityLotteryResult activityLotteryResult) {
		if(activityLotteryResult == null){
			logger.info("getActivityLotteryResultByPager:activityLotteryResult对象为空");
			return new ActivityLotteryResult();
		}
		return activityLotteryResultDao.getActivityLotteryResultByPager(activityLotteryResult);
	}

	public ActivityLotteryResultDao getActivityLotteryResultDao() {
		return activityLotteryResultDao;
	}

	public void setActivityLotteryResultDao(
			ActivityLotteryResultDao activityLotteryResultDao) {
		this.activityLotteryResultDao = activityLotteryResultDao;
	}

	@Override
	public boolean updateActivityLotteryAndActivityCustInfo(ActivityCustInfo acrivityCustInfo,ActivityLotteryResult activityLotteryResult,String mobilePhone,Merchant merchant) throws AppException {
		Integer result = activityCustInfoService.updateById(acrivityCustInfo);
		boolean flag = false;
		if(result != null && result > 0){
			result = this.add(activityLotteryResult);
			if(result == null || result <= 0){			
				throw new AppException("执行更新ActivityLotteryResult失败");
			}else{
				//开始发送短信
				
				String lastTime = activityLotteryResult.getExpireTime().split("\\|")[0].replace("-", "\\");

				String[] msg = new String[]{
						activityLotteryResult.getSecuritiesNo(),
						lastTime,
						merchant.getMstoreFullName(),
						merchant.getMstoreTel()
				};
				String content = MessageSourceUtil.getSource().getMessage("activity_movie", msg, null);
				logger.info("开始发送短信");
				SmsLog smsLog = new SmsLog();
				smsLog.setMobile(mobilePhone);
				smsLog.setMessage(content);
				smsLog.setType(SmsLogType.ACT_FILM);				
				flag = messageService.sendMessage(smsLog);
				logger.info("向"+mobilePhone+"发送活动验证券号  状态: " + flag);
			}
		}else{
			throw new AppException("执行更新ActivityCustInfo失败");
		}			
		return flag;
	}
	public ActivityCustInfoService getActivityCustInfoService() {
		return activityCustInfoService;
	}

	public void setActivityCustInfoService(
			ActivityCustInfoService activityCustInfoService) {
		this.activityCustInfoService = activityCustInfoService;
	}
	public MessageService getMessageService() {
		return messageService;
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public boolean retryAcrivityMsg(
			ActivityLotteryResult activityLotteryResult, String mobilePhone,
			Merchant merchant) {
		boolean flag = false;
		String lastTime = activityLotteryResult.getExpireTime().split("\\|")[0].replace("-", "\\");

		String[] msg = new String[]{
				activityLotteryResult.getSecuritiesNo(),
				lastTime,
				merchant.getMstoreFullName(),
				merchant.getMstoreTel()
		};
		String content = MessageSourceUtil.getSource().getMessage("activity_movie", msg, null);
		logger.info("开始重发短信");
		SmsLog smsLog = new SmsLog();
		smsLog.setMobile(mobilePhone);
		smsLog.setMessage(content);
		smsLog.setType(SmsLogType.ACT_FILM);				
		flag = messageService.sendMessage(smsLog);
		logger.info("向"+mobilePhone+"发送活动验证券号  状态: " + flag);
		return flag;
	}
	
}
