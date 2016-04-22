package com.froad.CB.dao.activity.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.activity.ActivityLotteryResultDao;
import com.froad.CB.po.activity.ActivityLotteryResult;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
public class ActivityLotteryResultDaoImpl implements ActivityLotteryResultDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer add(ActivityLotteryResult activityLotteryResult) {
		return (Integer)sqlMapClientTemplate.insert("activityLotteryResult.insert",activityLotteryResult);
	}

	@Override
	public ActivityLotteryResult getActivityLotteryResultById(Integer id) {
		return (ActivityLotteryResult)sqlMapClientTemplate.queryForObject("activityLotteryResult.selectByPrimaryKey",id);
	}

	@Override
	public Integer updateById(ActivityLotteryResult activityLotteryResult) {
		return sqlMapClientTemplate.update("activityLotteryResult.updateById",activityLotteryResult);
	}

	@Override
	public Integer updateBySmsCountId(
			ActivityLotteryResult activityLotteryResult) {
		return sqlMapClientTemplate.update("activityLotteryResult.updateActivityLotteryMsgCount",activityLotteryResult);
	}

	@Override
	public List<ActivityLotteryResult> getActivityLotteryResultBySelective(
			ActivityLotteryResult activityLotteryResult) {
		return sqlMapClientTemplate.queryForList("activityLotteryResult.getActivityLotteryResultBySelective",activityLotteryResult);
	}

	@Override
	public ActivityLotteryResult getActivityLotteryResultByPager(
			ActivityLotteryResult activityLotteryResult) {
		List list=sqlMapClientTemplate.queryForList("activityLotteryResult.getActivityLotteryByPager",activityLotteryResult);
		Integer count=(Integer)sqlMapClientTemplate.queryForObject("activityLotteryResult.getActivityLotteryByPagerCount",activityLotteryResult);
		activityLotteryResult.setList(list);
		activityLotteryResult.setTotalCount(count);
		return activityLotteryResult;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
