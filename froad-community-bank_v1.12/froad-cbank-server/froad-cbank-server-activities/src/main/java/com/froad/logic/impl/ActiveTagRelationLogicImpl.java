package com.froad.logic.impl;

import com.froad.enums.ActiveItemType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveTagRelationLogic;
import com.froad.po.ActiveTagRelation;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.Validator;

public class ActiveTagRelationLogicImpl implements ActiveTagRelationLogic {

	@Override
	public ResultVo verification(ResultVo resultVo,ActiveTagRelation activeTagRelation)
	{
		if(null == activeTagRelation)
		{
			LogCvt.error("添加activeRuleInfo失败,原因:activeTagRelation不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动范围相关信息不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeTagRelation.getClientId())){
			LogCvt.error("添加activeTagRelation失败,原因:ClientId不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:客户端不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if(Validator.isEmptyStr(activeTagRelation.getItemType())){
			LogCvt.error("添加activeTagRelation失败,原因:ItemType不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:活动范围不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			return resultVo;
		}
		else if( ActiveItemType.getType(activeTagRelation.getItemType()) == null ){
				LogCvt.error("添加activeTagRelation失败,原因:ItemType不在有效值范围内!");
				resultVo.setResultDesc("添加营销活动失败,原因:活动范围不在有效值范围内!");
				resultVo.setResultCode(ResultCode.failed.getCode());
				return resultVo;
		}
		return resultVo;
	}

}
