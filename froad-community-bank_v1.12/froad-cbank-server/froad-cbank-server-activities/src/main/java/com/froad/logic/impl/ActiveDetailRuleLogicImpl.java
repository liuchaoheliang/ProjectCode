package com.froad.logic.impl;

import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveDetailRuleLogic;
import com.froad.po.ActiveDetailRule;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.Validator;

public class ActiveDetailRuleLogicImpl implements ActiveDetailRuleLogic {

	@Override
	public ResultVo verification(ResultVo resultVo,
			ActiveDetailRule activeDetailRule) {
		if(null == activeDetailRule)
		{
			LogCvt.error("添加activeRuleInfo失败,原因:activeDetailRule不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动信息内容不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeDetailRule.getClientId())){
			LogCvt.error("添加activeDetailRule失败,原因:客户端不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:客户端不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(activeDetailRule.getIsPrePay())
		{
//			if(activeDetailRule.getMaxMoney()%activeDetailRule.getRetMoney() !=0)
//			{
//				LogCvt.error("添加activeDetailRule失败,原因:总金额必须是满减金额整数倍!");
//				resultVo.setResultDesc("添加活动失败,原因:总金额必须是满减金额整数倍!");
//				resultVo.setResultCode(ResultCode.failed.getCode());
//				return resultVo;
//			}

		}	
		else if(activeDetailRule.getMinLimit() < 0 && !activeDetailRule.getIsPrePay()){
			LogCvt.error("添加activeDetailRule失败,原因:金额下限有误!");
			resultVo.setResultDesc("添加活动失败,原因:金额下限有误!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(activeDetailRule.getPerCount() > activeDetailRule.getTotalCount()){
			LogCvt.error("添加activeDetailRule失败,原因:每人限制数大于全局限定次数!");
			resultVo.setResultDesc("添加活动失败,原因:每人限制数大于全局限定次数!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}	
		else if(activeDetailRule.getPerCount() >= 10000000000L){
			LogCvt.error("添加activeDetailRule失败,原因:每人间隔量限定的次数有误!");
			resultVo.setResultDesc("添加活动失败,原因:每人间隔量限定的次数有误!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(activeDetailRule.getTotalCount() > 10000000000L){
			LogCvt.error("添加activeDetailRule失败,原因:全局限定的次数有误!");
			resultVo.setResultDesc("添加活动失败,原因:全局限定的次数有误!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(activeDetailRule.getTotalDay() > 100000){
			LogCvt.error("添加activeDetailRule失败,原因:全局限定的间隔有误!");
			resultVo.setResultDesc("添加活动失败,原因:全局限定的间隔有误!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}	
		else if(activeDetailRule.getPerDay() > 100000){
			LogCvt.error("添加activeDetailRule失败,原因: 每人限定的间隔量有误!");
			resultVo.setResultDesc("添加活动失败,原因:每人限定的间隔量有误!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}	
		else if(activeDetailRule.getPerDay() > activeDetailRule.getTotalDay())
		{
			LogCvt.error("添加activeDetailRule失败,原因:每人限定的间隔两大于全局限定的间隔有误!");
			resultVo.setResultDesc("添加活动失败,原因:每人限定的间隔两大于全局限定的间隔有误!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		
		return resultVo;
	}
}
