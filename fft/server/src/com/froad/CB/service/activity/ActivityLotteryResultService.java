package com.froad.CB.service.activity;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.activity.ActivityCustInfo;
import com.froad.CB.po.activity.ActivityLotteryResult;
import com.froad.CB.po.merchant.Merchant;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
@WebService
public interface ActivityLotteryResultService {
	/**
	  * 方法描述：添加客户活动中奖的消费领取信息记录
	  * @param: ActivityLotteryResult
	  * @version: 1.0
	  */
	public Integer add(ActivityLotteryResult activityLotteryResult);
	
	
	/**
	  * 方法描述：查询活动中奖领取消费信息
	  * @param: id
	  * @return: ActivityLotteryResult
	  * @version: 1.0
	  */
	public ActivityLotteryResult getActivityLotteryResultById(Integer id);
	
	
	/**
	  * 方法描述：更新活动中奖领取消费信息
	  * @param: ActivityLotteryResult
	  * @return: Integer
	  * @version: 1.0
	  */
	public Integer updateById(ActivityLotteryResult activityLotteryResult);
	
	/**
	  * 方法描述：更新活动中奖领取消费发送短信券次数信息
	  * @param: ActivityLotteryResult
	  * @return: Integer
	  * @version: 1.0
	  */
	public Integer updateBySmsCountId(ActivityLotteryResult activityLotteryResult);
	
	/**
	  * 方法描述：查询中奖客户领取消费记录
	  * @param: ActivityLotteryResult
	  * @return: List<ActivityLotteryResult>
	  * @version: 1.0
	  */
	public List<ActivityLotteryResult> getActivityLotteryResultBySelective(ActivityLotteryResult activityLotteryResult);
	
	
	/**
	  * 方法描述：分页查询中奖领取消费记录
	  * @param: ActivityLotteryResult
	  * @return: ActivityLotteryResult
	  * @version: 1.0
	  */
	public ActivityLotteryResult getActivityLotteryResultByPager(ActivityLotteryResult activityLotteryResult);
	
	/**
	 * *******************************************************
	 * @函数名: updateActivityLotteryAndActivityCustInfo  
	 * @功能描述: 事物同时更新
	 * @输入参数: @param acrivityCustInfo
	 * @输入参数: @param activityLotteryResult
	 * @输入参数: @return <说明>
	 * @返回类型: boolean
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-25 上午11:13:42
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public boolean updateActivityLotteryAndActivityCustInfo(ActivityCustInfo acrivityCustInfo,ActivityLotteryResult activityLotteryResult,String mobilePhone,Merchant merchant) throws AppException;

	/**
	 * *******************************************************
	 * @函数名: retryAcrivityMsg  
	 * @功能描述: 重发验证券短信
	 * @输入参数: @param activityLotteryResult
	 * @输入参数: @param mobilePhone
	 * @输入参数: @param merchant
	 * @输入参数: @return <说明>
	 * @返回类型: boolean
	 * @作者: 赵肖瑶 
	 * @日期: 2013-7-25 下午07:21:00
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public boolean retryAcrivityMsg(ActivityLotteryResult activityLotteryResult,String mobilePhone,Merchant merchant);
}
