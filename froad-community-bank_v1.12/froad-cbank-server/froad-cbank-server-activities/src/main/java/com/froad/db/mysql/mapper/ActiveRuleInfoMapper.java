package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveRuleInfo;
import com.froad.po.ActiveRuleInfoDetail;

public interface ActiveRuleInfoMapper {
	/**
	 * @Title: findAllActiveRuleInfoByPage
	 * @Description: 分页查找所有的活动
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param page，activeRuleInfo
	 * @return
	*/
	public List<ActiveRuleInfoDetail> findAllActiveRuleInfoByPage(Page<ActiveRuleInfo> page, @Param("activeRuleInfo")ActiveRuleInfo activeRuleInfo);
}
