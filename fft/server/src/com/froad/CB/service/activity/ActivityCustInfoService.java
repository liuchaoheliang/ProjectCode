package com.froad.CB.service.activity;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.po.activity.ActivityCustInfo;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
@WebService
public interface ActivityCustInfoService {
	/**
	  * 方法描述：添加客户活动中奖信息
	  * @param: ActivityCustInfo
	  * @version: 1.0
	  */
	public Integer add(ActivityCustInfo activityCustInfo);
	
	
	/**
	  * 方法描述：查询活动中奖信息
	  * @param: id
	  * @return: ActivityCustInfo
	  * @version: 1.0
	  */
	public ActivityCustInfo getActivityCustInfoById(Integer id);
	
	
	/**
	  * 方法描述：更新活动中奖信息
	  * @param: ActivityCustInfo
	  * @return: Integer
	  * @version: 1.0
	  */
	public Integer updateById(ActivityCustInfo activityCustInfo);
	
	
	/**
	  * 方法描述：查询中奖客户记录
	  * @param: ActivityCustInfo
	  * @return: List<ActivityCustInfo>
	  * @version: 1.0
	  */
	public List<ActivityCustInfo> getActivityCustInfoBySelective(ActivityCustInfo activityCustInfo);
	
	
	/**
	  * 方法描述：分页查询中奖记录
	  * @param: ActivityCustInfo
	  * @return: ActivityCustInfo
	  * @version: 1.0
	  */
	public ActivityCustInfo getActivityCustInfoByPager(ActivityCustInfo activityCustInfo);
}
