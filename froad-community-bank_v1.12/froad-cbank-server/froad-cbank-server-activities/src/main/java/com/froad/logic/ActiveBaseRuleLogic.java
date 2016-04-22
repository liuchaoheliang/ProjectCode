package com.froad.logic;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveBaseRule;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.FindAllPromotionActiveVO;
import com.froad.thrift.vo.active.FindPromotionActiveByPageVO;

public interface ActiveBaseRuleLogic {
	
	public ResultVo verification(ResultVo resultVo,ActiveBaseRule activeBaseRule);
	
	 /**
	  * @Title: findAllPromotionActive
	  * @Description: 促销活动列表接口
	  * @author: shenshaocheng 2015年11月27日
	  * @modify: shenshaocheng 2015年11月27日
	  * @param activeBaseRule 活动基础信息
	  * @return 活动VO
	 */	
	public FindAllPromotionActiveVO findAllPromotionActive(ActiveBaseRule activeBaseRule);
	
	/**
     * 分页查询 ActiveBaseRule(红包支持活动)
     * @param page
     * @param vouchersInfo
     * @return Page<VouchersInfo>    VouchersInfo分页结果 
     */
	public FindPromotionActiveByPageVO findActiveBaseRuleByPage(Page<ActiveBaseRule> page, ActiveBaseRule activeBaseRule) ;
}
