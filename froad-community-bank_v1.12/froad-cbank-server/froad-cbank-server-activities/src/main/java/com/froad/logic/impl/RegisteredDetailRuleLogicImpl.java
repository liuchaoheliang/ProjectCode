/**
 * @Title: RegisteredDetailRuleLogicImpl.java
 * @Package com.froad.logic.impl
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月2日
 * @version V1.0
**/

package com.froad.logic.impl;

import com.froad.enums.ActiveAwardType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.RegisteredDetailRuleLogic;
import com.froad.po.RegistDetailRule;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.Validator;

 /**
 * @ClassName: RegisteredDetailRuleLogicImpl
 * @Description: TODO
 * @author froad-Joker 2015年12月2日
 * @modify froad-Joker 2015年12月2日
 */

public class RegisteredDetailRuleLogicImpl implements RegisteredDetailRuleLogic {

	/**
	 * @Title: verification
	 * @Description: TODO
	 * @author: Joker 2015年12月2日
	 * @modify: Joker 2015年12月2日
	 * @param resultVo
	 * @param registeredDetailRule
	 * @return
	 * @see com.froad.logic.RegisteredDetailRuleLogic#verification(com.froad.thrift.vo.ResultVo, com.froad.po.RegistDetailRule)
	 */

	@Override
	public ResultVo verification(ResultVo resultVo,
			RegistDetailRule registeredDetailRule) {
		
		if(null == registeredDetailRule)
		{
			LogCvt.error("添加RegisteredRuleInfo失败,原因:registeredDetailRule不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:registeredDetailRule不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(registeredDetailRule.getClientId())){
			LogCvt.error("添加registeredDetailRule失败,原因:ClientId不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:ClientId不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(null == ActiveAwardType.getType(registeredDetailRule.getAwardType()))
		{
			LogCvt.error("添加registeredDetailRule失败,原因:AwardType不在范围内!");
			resultVo.setResultDesc("添加活动失败,原因:AwardType不在范围内!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		if(ActiveAwardType.fullCut.getCode().equals(registeredDetailRule.getAwardType()))
		{
			if(registeredDetailRule.getCutMoney() <= 0) {
				resultVo.setResultDesc("添加活动失败,原因:注册活动奖励方式为满减时,满减额度不能少于或者等于0!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				return resultVo;
			}

		}
		else if(ActiveAwardType.vouchers.getCode().equals(registeredDetailRule.getAwardType()))
		{
			if(Validator.isEmptyStr(registeredDetailRule.getVouchersActiveId())) {
				resultVo.setResultDesc("添加活动失败,原因:注册活动奖励方式为红包时,红包规则ID不能为空!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				return resultVo;
			}
		}
		else if(ActiveAwardType.product.getCode().equals(registeredDetailRule.getAwardType()))
		{
			if(Validator.isEmptyStr(registeredDetailRule.getProductId())) {
				resultVo.setResultDesc("添加活动失败,原因:注册活动奖励方式为实物时,实物的ID不能为空!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				return resultVo;
			}
			else if(registeredDetailRule.getProductCount() <=0) {
				resultVo.setResultDesc("添加活动失败,原因:注册活动奖励方式为实物时,实物的数量不能少于或者等于0!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				return resultVo;
			}
		}		
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("验证registeredDetailRule正确");
		return resultVo;
	}

}
