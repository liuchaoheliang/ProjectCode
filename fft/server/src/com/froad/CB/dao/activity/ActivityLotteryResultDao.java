package com.froad.CB.dao.activity;

import java.util.List;
import com.froad.CB.po.activity.ActivityLotteryResult;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
public interface ActivityLotteryResultDao {
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
}
