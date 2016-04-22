package com.froad.CB.dao.activity.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.activity.ActivityCustInfoDao;
import com.froad.CB.po.activity.ActivityCustInfo;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-7-24  
 * @version 1.0
 */
public class ActivityCustInfoDaoImpl implements ActivityCustInfoDao {
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	@Override
	public Integer add(ActivityCustInfo activityCustInfo) {		
		return (Integer)sqlMapClientTemplate.insert("activityCustInfo.insert",activityCustInfo);
	}

	@Override
	public ActivityCustInfo getActivityCustInfoById(Integer id) {
		return (ActivityCustInfo)sqlMapClientTemplate.queryForObject("activityCustInfo.selectByPrimaryKey",id);
	}

	@Override
	public Integer updateById(ActivityCustInfo activityCustInfo) {
		return sqlMapClientTemplate.update("activityCustInfo.updateById",activityCustInfo);
	}

	@Override
	public List<ActivityCustInfo> getActivityCustInfoBySelective(
			ActivityCustInfo activityCustInfo) {
		return sqlMapClientTemplate.queryForList("activityCustInfo.getActivityCustInfoBySelective",activityCustInfo);
	}

	@Override
	public ActivityCustInfo getActivityCustInfoByPager(
			ActivityCustInfo activityCustInfo) {
		List list=sqlMapClientTemplate.queryForList("activityCustInfo.getActivityCustInfoByPager",activityCustInfo);
		Integer count=(Integer)sqlMapClientTemplate.queryForObject("activityCustInfo.getActivityCustInfoByPagerCount",activityCustInfo);
		activityCustInfo.setList(list);
		activityCustInfo.setTotalCount(count);
		return activityCustInfo;
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

}
