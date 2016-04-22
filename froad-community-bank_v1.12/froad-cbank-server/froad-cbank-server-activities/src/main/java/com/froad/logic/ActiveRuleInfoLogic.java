package com.froad.logic;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.ActiveRuleInfo;
import com.froad.thrift.vo.active.AddResultVo;

public interface ActiveRuleInfoLogic {
    /**
     * 增加 ActiveRuleInfo
     * @param activeRuleInfo
     * @return Long    主键ID
     */
	public AddResultVo addActiveRuleInfo(ActiveRuleInfo activeRuleInfo);
	
	public ResultBean disableActiveRuleInfo(String clientId,String activeId, String operator);
	
	public ResultBean updateActiveRuleInfo(ActiveRuleInfo activeRuleInfo);
	
	public ActiveRuleInfo getActiveRuleInfo(String clientid, String activeId);
	
	public Page<ActiveRuleInfo> getAllActiveRuleInfoByPage(Page<ActiveRuleInfo> page, ActiveRuleInfo activeRuleInfo);
	
	public String exportReport(ActiveRuleInfo activeRuleInfo);
}
