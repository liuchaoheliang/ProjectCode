package com.froad.handler;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.ActiveRuleInfo;
import com.froad.po.ActiveTagRelation;

public interface ActiveRuleInfoHandler {
	/**
	 * @Title: addActiveRuleInfo
	 * @Description: 新增活动信息
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param activeRuleInfo
	 * @return
	*/	
	public ResultBean addActiveRuleInfo(ActiveRuleInfo activeRuleInfo);
	
	//public ResultBean  disableActiveRuleInfo(String clientId, Long id);
	/**
	 * @Title: updateActiveRuleInfoByActiveId
	 * @Description: 更新活动信息
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param activeRuleInfo
	 * @return
	*/	
	public ResultBean updateActiveRuleInfoByActiveId(ActiveRuleInfo activeRuleInfo);
	/**
	 * @Title: getActiveRuleInfo
	 * @Description: 获得单个活动信息
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param clientId,activeId
	 * @return
	*/
	public ActiveRuleInfo getActiveRuleInfo(String clientId, String activeId);
	/**
	 * @Title: getAllActiveRuleInfoByPage
	 * @Description: 获得所有活动信息
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param clientId,activeId
	 * @return
	*/	
	public Page<ActiveRuleInfo> getAllActiveRuleInfoByPage(Page<ActiveRuleInfo> page, ActiveRuleInfo activeRuleInfo);
	
	
	public ActiveTagRelation isExistProductActive(String clientId, String itemType, String itemId, String activeId, String type);
	
	
}
