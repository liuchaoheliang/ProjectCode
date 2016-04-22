package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ActiveStatus;
import com.froad.enums.ResultCode;
import com.froad.handler.ActiveBaseRuleHandler;
import com.froad.handler.impl.ActiveBaseRuleHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveBaseRuleLogic;
import com.froad.po.ActiveBaseRule;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ActiveBaseRuleVo;
import com.froad.thrift.vo.active.FindAllPromotionActiveVO;
import com.froad.thrift.vo.active.FindPromotionActiveByPageVO;
import com.froad.util.BeanUtil;
import com.froad.util.Validator;

public class ActiveBaseRuleLogicImpl implements ActiveBaseRuleLogic {
	
	private ActiveBaseRuleHandler  activeBaseRuleHandler = new ActiveBaseRuleHandlerImpl();
	
	@Override
	public ResultVo verification(ResultVo resultVo, //AddResultVo addResultVo,
			ActiveBaseRule activeBaseRule) {
		
		if(null == activeBaseRule)
		{
			LogCvt.error("添加activeRuleInfo失败,原因:activeBaseRule不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动基础信息内容不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(null != activeBaseRuleHandler.findOneActiveBaseRuleByActiveNameAndClientId(activeBaseRule))
		{
			LogCvt.error("该营销活动名称已经存在!");
			resultVo.setResultDesc("该营销活动名称已经存在!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeBaseRule.getClientId())){
			LogCvt.error("添加activeBaseRule失败,原因:ClientId不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:客户端不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
//		else if(Validator.isEmptyStr(activeBaseRule.getStatus()))
//		{
//			LogCvt.error("添加activeBaseRule失败,原因:Status不能为空!");
//			resultVo.setResultDesc("添加活动失败,原因:Status不能为空!");
//			resultVo.setResultCode(ResultCode.failed.getCode());
//			return resultVo;
//		}
//		else if(!ActiveStatus.launch.equals(ActiveStatus.getType(activeBaseRule.getStatus())))
//		{
//			LogCvt.error("添加activeBaseRule失败,原因:Status不是审核!");
//			resultVo.setResultDesc("添加活动失败,原因:Status不是审核!");
//			resultVo.setResultCode(ResultCode.failed.getCode());
//			return resultVo;
//		}
		else if(Validator.isEmptyStr(activeBaseRule.getLimitType()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:LimitType不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动范围不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(null == ActiveStatus.getType(activeBaseRule.getLimitType()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:LimitType不在范围内!");
			resultVo.setResultDesc("添加活动失败,原因:活动范围不在范围内!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeBaseRule.getSettleType()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:SettleType不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:结算方式不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(null == ActiveStatus.getType(activeBaseRule.getSettleType()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:SettleType不在范围内!");
			resultVo.setResultDesc("添加活动失败,原因:结算方式不在范围内!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeBaseRule.getType()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:Type不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动类型不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeBaseRule.getActiveName()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:ActiveName不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动名称不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}	
		else if(activeBaseRule.getExpireEndTime().before(activeBaseRule.getExpireStartTime()))
		{
			LogCvt.error("添加activeBaseRule失败,原因:活动结束时间不能早于活动开始时间!");
			resultVo.setResultDesc("添加活动失败,原因:活动结束时间不能早于活动开始时间!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}		
//		else if(!ActiveType.fullCut.getCode().equals(activeBaseRule.getType()))
//		{
//			LogCvt.error("添加activeBaseRule失败,原因:Type不是满减!");
//			resultVo.setResultDesc("添加活动失败,原因:Type不是满减!");
//			resultVo.setResultCode(ResultCode.failed.getCode());
//			return resultVo;
//		}		
		else if((activeBaseRule.getMerchantRate())
				+(activeBaseRule.getBankRate())
				+(activeBaseRule.getFftRate())!=100000)
		{
			LogCvt.error("添加activeBaseRule失败,原因:补贴比例加起来不是100%!");
			resultVo.setResultDesc("添加活动失败,原因:补贴比例加起来不是100%!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
			
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("验证营销基础信息正确");
		return resultVo;
	}

	 /**
	  * @Title: findAllPromotionActive
	  * @Description: 获取促销活动列表
	  * @author: shenshaocheng 2015年11月27日
	  * @modify: shenshaocheng 2015年11月27日
	  * @param activeBaseRule
	  * @return
	  * @see com.froad.logic.ActiveBaseRuleLogic#findAllPromotionActive(com.froad.po.ActiveBaseRule)
	  */	
	@Override
	public FindAllPromotionActiveVO findAllPromotionActive(
			ActiveBaseRule activeBaseRule) {
		FindAllPromotionActiveVO activeVO = new FindAllPromotionActiveVO();
		activeVO.activeBaseRuleVoList = new ArrayList<ActiveBaseRuleVo>();
		try {
			// 如果传入为0，则需要查询全部，此时type不作为条件，设为空
			if(activeBaseRule.getType() != null && activeBaseRule.getType().equals("0")) {
				activeBaseRule.setType(null);
			}
			List<ActiveBaseRule> activeBaseRuleList = 
					this.activeBaseRuleHandler.findSuntainActiveBaseRule(activeBaseRule);
			for(ActiveBaseRule activeRule : activeBaseRuleList) {
				ActiveBaseRuleVo vo = new ActiveBaseRuleVo();
				vo = (ActiveBaseRuleVo) BeanUtil.copyProperties(ActiveBaseRuleVo.class, activeRule);
				activeVO.activeBaseRuleVoList.add(vo);
			}
		} catch (Exception e) {
			LogCvt.error("获取促销活动列表异常 " + e.getMessage(), e);			
		}
		
		return activeVO;
	}

	 /**
	  * @Title: findActiveBaseRuleByPage
	  * @Description: 分页查询 ActiveBaseRule(红包支持活动)
	  * @author: shenshaocheng 2015年11月29日
	  * @modify: shenshaocheng 2015年11月29日
	  * @param page
	  * @param activeBaseRule
	  * @return
	  * @see com.froad.logic.ActiveBaseRuleLogic#findActiveBaseRuleByPage(com.froad.db.mysql.bean.Page, com.froad.po.ActiveBaseRule)
	  */	
	@Override
	public FindPromotionActiveByPageVO findActiveBaseRuleByPage(
			Page<ActiveBaseRule> page, ActiveBaseRule activeBaseRule) {
		FindPromotionActiveByPageVO promotionActiveByPageVO = new FindPromotionActiveByPageVO();
		try { 
			/**********************操作MySQL数据库**********************/
			page = activeBaseRuleHandler.findActiveBaseRuleByPage(page, activeBaseRule);
			List<ActiveBaseRule> activeBaseRulesList = page.getResultsContent();
			promotionActiveByPageVO.activeBaseRuleVoList = new ArrayList<ActiveBaseRuleVo>();
			for(ActiveBaseRule po : activeBaseRulesList) {
				ActiveBaseRuleVo vo = new ActiveBaseRuleVo();
				vo = (ActiveBaseRuleVo) BeanUtil.copyProperties(ActiveBaseRuleVo.class, po);
				promotionActiveByPageVO.activeBaseRuleVoList.add(vo);
			}
			
			PageVo pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			promotionActiveByPageVO.page = pageVo;
		} catch (Exception e) { 
			LogCvt.error("分页查询ActiveBaseRule失败，原因:" + e.getMessage(), e); 
		} 
		
		return promotionActiveByPageVO; 

	}

}
