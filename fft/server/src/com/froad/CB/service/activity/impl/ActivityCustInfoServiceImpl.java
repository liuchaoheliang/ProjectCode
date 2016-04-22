package com.froad.CB.service.activity.impl;

import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import com.froad.CB.dao.activity.ActivityCustInfoDao;
import com.froad.CB.po.activity.ActivityCustInfo;
import com.froad.CB.service.activity.ActivityCustInfoService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
@WebService(endpointInterface="com.froad.CB.service.activity.ActivityCustInfoService")
public class ActivityCustInfoServiceImpl implements ActivityCustInfoService {
	private static final Logger logger=Logger.getLogger(ActivityCustInfoServiceImpl.class);	
	private ActivityCustInfoDao activityCustInfoDao;
	
	@Override
	public Integer add(ActivityCustInfo activityCustInfo) {
		if(activityCustInfo == null){
			logger.info("add:activityCustInfo对象为空");
			return null;
		}
		return activityCustInfoDao.add(activityCustInfo);
	}

	@Override
	public ActivityCustInfo getActivityCustInfoById(Integer id) {
		if(id == null){
			logger.info("getActivityCustInfoById:id为空");
			return null;
		}
		return activityCustInfoDao.getActivityCustInfoById(id);
	}

	@Override
	public Integer updateById(ActivityCustInfo activityCustInfo) {
		if(activityCustInfo == null){
			logger.info("updateById:activityCustInfo对象为空");
			return 0;
		}
		return activityCustInfoDao.updateById(activityCustInfo);
	}

	@Override
	public List<ActivityCustInfo> getActivityCustInfoBySelective(
			ActivityCustInfo activityCustInfo) {
		if(activityCustInfo == null){
			logger.info("getActivityCustInfoBySelective:activityCustInfo对象为空");
			return null;
		}
		return activityCustInfoDao.getActivityCustInfoBySelective(activityCustInfo);
	}

	@Override
	public ActivityCustInfo getActivityCustInfoByPager(
			ActivityCustInfo activityCustInfo) {
		if(activityCustInfo == null){
			logger.info("getActivityCustInfoByPager:activityCustInfo对象为空");
			return new ActivityCustInfo();
		}
		return activityCustInfoDao.getActivityCustInfoByPager(activityCustInfo);
	}

	public ActivityCustInfoDao getActivityCustInfoDao() {
		return activityCustInfoDao;
	}

	public void setActivityCustInfoDao(ActivityCustInfoDao activityCustInfoDao) {
		this.activityCustInfoDao = activityCustInfoDao;
	}
	
}
