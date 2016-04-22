package com.froad.action.support.activity;

import java.util.List;

import com.froad.client.activityCustInfo.ActivityCustInfo;
import com.froad.client.activityCustInfo.ActivityCustInfoService;
import com.froad.client.activityLotteryResult.ActivityLotteryResult;
import com.froad.client.activityLotteryResult.ActivityLotteryResultService;
import com.froad.client.activityLotteryResult.AppException_Exception;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchant.MerchantService;

public class ActivityMovieActionSupport {

	private ActivityLotteryResultService activityLotteryResultService;
	private ActivityCustInfoService activityCustInfoService;
	private MerchantService merchantService;
	/**
	 * *******************************************************
	 * @函数名: getAllCustInfo  
	 * @功能描述: 查询所有中奖名单
	 * @输入参数: @return <说明>
	 * @返回类型: List<ActivityCustInfo>
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-24 下午05:34:10
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public List<ActivityCustInfo> getAllCustInfo(ActivityCustInfo acityity){		
		return activityCustInfoService.getActivityCustInfoBySelective(acityity);
	}
	/**
	 * *******************************************************
	 * @函数名: getCustInfoByCondition  
	 * @功能描述: 通过条件查询相关信息
	 * @输入参数: @param acInfo
	 * @输入参数: @return <说明>
	 * @返回类型: ActivityCustInfo
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-24 下午05:48:48
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public ActivityCustInfo getCustInfoByCondition(ActivityCustInfo acInfo){
		List<ActivityCustInfo> list = activityCustInfoService.getActivityCustInfoBySelective(acInfo);
		if(list != null && list.size() != 0 ){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * *******************************************************
	 * @函数名: checkIsSecuritiesNoExist  
	 * @功能描述: 判断验证券是否存在 
	 * @输入参数: @param alr
	 * @输入参数: @return <说明>
	 * @返回类型: ActivityLotteryResult
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-25 下午03:16:12
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public ActivityLotteryResult checkIsSecuritiesNoExist(ActivityLotteryResult alr){
		List<ActivityLotteryResult> list = activityLotteryResultService.getActivityLotteryResultBySelective(alr);
		if(list != null && list.size() != 0 ){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * *******************************************************
	 * @函数名: getMyTicket  
	 * @功能描述: 根据用户Id查询我的兑换券信息
	 * @输入参数: @return <说明>
	 * @返回类型: List<ActivityCustInfo>
	 * @作者: 刘超 
	 * @日期: 2013-7-24 下午05:34:10
	 * @修改: lc 
	 * @日期: 
	 **********************************************************
	 */
	public ActivityLotteryResult getMyTicket(ActivityLotteryResult activityLotteryResult ){
		return activityLotteryResultService.getActivityLotteryResultByPager(activityLotteryResult);
	}
	/**
	 * *******************************************************
	 * @函数名: getTicketInfoByNo  
	 * @功能描述: 根据securitiesNo查询兑换券信息
	 * @输入参数: @return <说明>
	 * @返回类型: List<ActivityCustInfo>
	 * @作者: 刘超 
	 * @日期: 2013-7-24 下午05:34:10
	 * @修改: Lc 
	 * @日期: 
	 **********************************************************
	 */
	
	/**
	 * *******************************************************
	 * @函数名: updateActivityLotteryResultAndCustInfo  
	 * @功能描述: 更新数据并发送短信
	 * @输入参数: @param aci
	 * @输入参数: @param alr
	 * @输入参数: @param mobilePhone
	 * @输入参数: @return <说明>
	 * @返回类型: boolean
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-25 下午03:16:24
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public boolean updateActivityLotteryResultAndCustInfo(com.froad.client.activityLotteryResult.ActivityCustInfo aci,ActivityLotteryResult alr,String mobilePhone,com.froad.client.activityLotteryResult.Merchant merchant){
		try {
			return activityLotteryResultService.updateActivityLotteryAndActivityCustInfo(aci, alr, mobilePhone, merchant);
		} catch (AppException_Exception e) {			
			e.printStackTrace();
			return false;
		}		
	}
	public List<Merchant> getActivityMerchant(Merchant merchant){
		return merchantService.getAllMerchant(merchant);
	}
	/**
	 * *******************************************************
	 * @函数名:  getTicketBysecuritiesNo
	 * @功能描述: 根据兑换券号查找活动消费信息
	 * @输入参数: @return <说明>
	 * @返回类型: 
	 * @作者: 刘超 
	 * @日期: 2013-7-24 下午05:34:10
	 * @修改: Lc 
	 * @日期: 
	 **********************************************************
	 */
	public List<ActivityLotteryResult> getTicketBysecuritiesNo(String securitiesNo){
		ActivityLotteryResult activityLotteryResult=new ActivityLotteryResult();
		activityLotteryResult.setSecuritiesNo(securitiesNo);
		return activityLotteryResultService.getActivityLotteryResultBySelective(activityLotteryResult);
	}
	
	
	/**
	 * *******************************************************
	 * @函数名:  updateActivityLotteryResultById
	 * @功能描述: 认证成功，修改活动消费信息表
	 * @输入参数: @return <说明>
	 * @返回类型: 
	 * @作者: 刘超 
	 * @日期: 2013-7-24 下午05:34:10
	 * @修改: Lc 
	 * @日期: 
	 **********************************************************
	 */
	public void updateActivityLotteryResultById(ActivityLotteryResult activityLotteryResult){
		 activityLotteryResultService.updateById(activityLotteryResult);
	}
	/**
	 * *******************************************************
	 * @函数名:  resendActivityMessage
	 * @功能描述: 重发短息
	 * @输入参数: @return <说明>
	 * @返回类型: 
	 * @作者: 刘超 
	 * @日期: 2013-7-24 下午05:34:10
	 * @修改: Lc 
	 * @日期: 
	 **********************************************************
	 */
	public boolean resendActivityMessage(ActivityLotteryResult activityLotteryResult,String phone,com.froad.client.activityLotteryResult.Merchant merchant){
		return activityLotteryResultService.retryAcrivityMsg(activityLotteryResult, phone, merchant);
	} 
	
	/**
	 * *******************************************************
	 * @函数名:  getActivityLotteryResultById
	 * @功能描述: 根据id查找活动消费信息
	 * @输入参数: @return <说明>
	 * @返回类型: 
	 * @作者: 刘超 
	 * @日期: 2013-7-24 下午05:34:10
	 * @修改: Lc 
	 * @日期: 
	 **********************************************************
	 */
	
	public ActivityLotteryResult getActivityLotteryResultById(Integer id){
		return activityLotteryResultService.getActivityLotteryResultById(id);
	}
	
	
	
	
	
	public ActivityLotteryResult getMerchantActivityLotteryResult(ActivityLotteryResult activityLotteryResult){
		return activityLotteryResultService.getActivityLotteryResultByPager(activityLotteryResult);
	}
	
	
	public ActivityLotteryResultService getActivityLotteryResultService() {
		return activityLotteryResultService;
	}
	public void setActivityLotteryResultService(
			ActivityLotteryResultService activityLotteryResultService) {
		this.activityLotteryResultService = activityLotteryResultService;
	}
	public ActivityCustInfoService getActivityCustInfoService() {
		return activityCustInfoService;
	}
	public void setActivityCustInfoService(
			ActivityCustInfoService activityCustInfoService) {
		this.activityCustInfoService = activityCustInfoService;
	}
	public MerchantService getMerchantService() {
		return merchantService;
	}
	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
}
