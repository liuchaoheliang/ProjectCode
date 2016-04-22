package com.froad.logic.impl;

import com.alibaba.druid.util.StringUtils;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ResultCode;
import com.froad.handler.ActiveBaseRuleHandler;
import com.froad.handler.ActiveMainReportHandler;
import com.froad.handler.ActiveRuleInfoHandler;
import com.froad.handler.impl.ActiveBaseRuleHandlerImpl;
import com.froad.handler.impl.ActiveMainReportHandlerImpl;
import com.froad.handler.impl.ActiveRuleInfoHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveBaseRuleLogic;
import com.froad.logic.ActiveDetailRuleLogic;
import com.froad.logic.ActiveRuleInfoLogic;
import com.froad.logic.ActiveTagRelationLogic;
import com.froad.po.ActiveRuleInfo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.AddResultVo;

public class ActiveRuleInfoLogicImpl implements ActiveRuleInfoLogic {

	private ActiveBaseRuleLogic activeBaseRuleLogic = new ActiveBaseRuleLogicImpl();
	private ActiveDetailRuleLogic activeDetailRulelogic = new ActiveDetailRuleLogicImpl();
	private ActiveTagRelationLogic activeTagRelationLogic = new ActiveTagRelationLogicImpl();

	private ActiveMainReportHandler activeMainReportHandler = new ActiveMainReportHandlerImpl();

	ActiveRuleInfoHandler activeRuleInfoHandler = new ActiveRuleInfoHandlerImpl();

	ActiveBaseRuleHandler activeBaseRuleHandler = new ActiveBaseRuleHandlerImpl();

	@Override
	public AddResultVo addActiveRuleInfo(ActiveRuleInfo activeRuleInfo) {
		AddResultVo addResultVo = new AddResultVo();
		ResultVo resultVo = new ResultVo();
		if (null == activeRuleInfo) {
			LogCvt.error("添加activeRuleInfo失败,原因:activeRuleInfo不能为空!");
			resultVo.setResultDesc("添加活动失败,原因:activeRuleInfo不能为空!");
			resultVo.setResultCode(ResultCode.failed.getCode());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}

		addResultVo.setResultVo(activeBaseRuleLogic.verification(resultVo,
				activeRuleInfo.getActiveBaseRule()));
		if (!StringUtils.equals(ResultCode.success.getCode(), addResultVo
				.getResultVo().getResultCode())) {
			return addResultVo;
		}		
		addResultVo.setResultVo(activeDetailRulelogic.verification(resultVo,
				activeRuleInfo.getActiveDetailRule()));
		if (!StringUtils.equals(ResultCode.success.getCode(), addResultVo
				.getResultVo().getResultCode())) {
			return addResultVo;
		}		
		addResultVo.setResultVo(activeTagRelationLogic.verification(resultVo,
				activeRuleInfo.getActiveTagRelation()));

		if (!StringUtils.equals(ResultCode.success.getCode(), addResultVo
				.getResultVo().getResultCode())) {
			return addResultVo;
		}

		ResultBean result = activeRuleInfoHandler
				.addActiveRuleInfo(activeRuleInfo);
		if (!StringUtils.equals(ResultCode.success.getCode(),
				result.getResultCode())) {
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
			addResultVo.setResultVo(resultVo);
			return addResultVo;
		}
		resultVo.setResultCode(ResultCode.success.getCode());
		resultVo.setResultDesc("添加活动信息成功");
		addResultVo.setResultVo(resultVo);

		return addResultVo;
	}

	@Override
	public ResultBean disableActiveRuleInfo(String clientId, String activeId,
			String operator) {
		ResultBean result = null;
		try {
			result = activeBaseRuleHandler.disableActiveBaseRuleByActiveId(
					clientId, activeId, operator);
		} catch (Exception e) {
			LogCvt.info("[disableActiveRuleInfo]失败"+e.getMessage());
		}
		return result;
	}

	@Override
	public ResultBean updateActiveRuleInfo(ActiveRuleInfo activeRuleInfo) {
		ResultBean resultBean = new ResultBean(ResultCode.success, "更新活动信息成功");
		ResultVo resultVo = new ResultVo();
		activeBaseRuleLogic.verification(resultVo,
				activeRuleInfo.getActiveBaseRule());
		if (!StringUtils.equals(ResultCode.success.getCode(), resultVo
				.getResultCode())) {
			resultBean = new ResultBean(ResultCode.failed,
					resultVo.getResultDesc());
			return resultBean;
		}
		activeDetailRulelogic.verification(resultVo,
				activeRuleInfo.getActiveDetailRule());
		if (!StringUtils.equals(ResultCode.success.getCode(), resultVo
				.getResultCode())) {
			resultBean = new ResultBean(ResultCode.failed,
					resultVo.getResultDesc());
			return resultBean;
		}
		activeTagRelationLogic.verification(resultVo,
				activeRuleInfo.getActiveTagRelation());

		if (!StringUtils.equals(ResultCode.success.getCode(), resultVo
				.getResultCode())) {
			resultBean = new ResultBean(ResultCode.failed,
					resultVo.getResultDesc());
			return resultBean;
		}
		resultBean = activeRuleInfoHandler
				.updateActiveRuleInfoByActiveId(activeRuleInfo);
		return resultBean;
	}

	@Override
	public ActiveRuleInfo getActiveRuleInfo(String clientId, String activeId) {

		return activeRuleInfoHandler.getActiveRuleInfo(clientId, activeId);
	}

	@Override
	public Page<ActiveRuleInfo> getAllActiveRuleInfoByPage(
			Page<ActiveRuleInfo> page, ActiveRuleInfo activeRuleInfo) {

		return activeRuleInfoHandler.getAllActiveRuleInfoByPage(page,
				activeRuleInfo);
	}

	@Override
	public String exportReport(ActiveRuleInfo activeRuleInfo) {
		String activeId = activeRuleInfo.getActiveBaseRule().getActiveId();
		String clientId = activeRuleInfo.getActiveBaseRule().getClientId();
		boolean isPrepay = activeRuleInfo.getActiveDetailRule().getIsPrePay();
		String type = null;
		if (isPrepay) {
			type = "1";// 满减
		} else {
			type = "0";// 满送
		}
		String url = null;
		try {
			url = activeMainReportHandler.getActiveMainReportURL(activeId,
					clientId, type, ActiveIdCode.MJ.getCode());
		} catch (Exception e) {
			LogCvt.info("[exportReport]失败"+e.getMessage());
		}
		return url;
	}

}
