package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.bean.ResultBean;
import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveRuleInfoLogic;
import com.froad.logic.impl.ActiveRuleInfoLogicImpl;
import com.froad.po.ActiveBaseRule;
import com.froad.po.ActiveRuleInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ActiveRuleInfoService.Iface;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.ActiveRuleInfoPageVoRes;
import com.froad.thrift.vo.active.ActiveRuleInfoVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.ExportActiveOrderInfoRes;
import com.froad.thrift.vo.active.FindActiveRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindAllActiveRuleInfoVoResultVo;
import com.froad.thrift.vo.active.FindPageActiveRuleInfoVoResultVo;
import com.froad.util.ActiveUtils;
import com.froad.util.BeanUtil;

public class ActiveRuleInfoServiceImpl extends BizMonitorBaseService implements Iface {
	
	private ActiveRuleInfoLogic activeRuleInfoLogic = new ActiveRuleInfoLogicImpl();
	public ActiveRuleInfoServiceImpl() {}

	public ActiveRuleInfoServiceImpl(String name, String version) {
		super(name, version);
	}

	public AddResultVo addActiveRuleInfo(OriginVo originVo,
			ActiveRuleInfoVo activeRuleInfoVo) throws TException {
		LogCvt.info("添加[ctiveRuleInfo]方法开始");
		AddResultVo addResultVo = new AddResultVo();
		ActiveRuleInfo activeRuleInfo = (ActiveRuleInfo)BeanUtil.copyProperties(ActiveRuleInfo.class, activeRuleInfoVo);
		addResultVo = activeRuleInfoLogic.addActiveRuleInfo(activeRuleInfo);
		//addResultVo.setId((Long)result.getData());
		LogCvt.info("添加[ctiveRuleInfo]方法结束");
		return addResultVo;
	}

	@Override
	public ResultVo disableActiveRuleInfo(OriginVo originVo, String clientId,
			String activeId, String operator) throws TException {
		LogCvt.info("禁止[ActiveRuleInfo]方法开始");
		ResultBean result = activeRuleInfoLogic.disableActiveRuleInfo(clientId, activeId, operator);
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"禁止活动成功");
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		LogCvt.info("禁止[ActiveRuleInfo]方法结束");
		return resultVo;
	}

	@Override
	public ResultVo updateActiveRuleInfo(OriginVo originVo,
			ActiveRuleInfoVo activeRuleInfoVo) throws TException {
		LogCvt.info("更新[ActiveRuleInfo]方法开始");
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"更新活动成功");
		ActiveRuleInfo activeRuleInfo = (ActiveRuleInfo)BeanUtil.copyProperties(ActiveRuleInfo.class, activeRuleInfoVo);
		ResultBean result = activeRuleInfoLogic.updateActiveRuleInfo(activeRuleInfo);
		if(!StringUtils.equals(ResultCode.success.getCode(), result.getResultCode())){
			LogCvt.error(result.getResultDesc());
			resultVo.setResultCode(result.getResultCode());
			resultVo.setResultDesc(result.getResultDesc());
		}
		LogCvt.info("更新[ActiveRuleInfo]方法结束");
		return resultVo;
	}

	@Override
	public FindAllActiveRuleInfoVoResultVo getActiveRuleInfo(
			ActiveRuleInfoVo activeRuleInfoVo) throws TException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public FindPageActiveRuleInfoVoResultVo getActiveRuleInfoByPage(
			PageVo pageVo, ActiveRuleInfoVo activeRuleInfoVo) throws TException {
		LogCvt.info("分页查询[FindPageActiveRuleInfoVoResultVo]方法开始");		
		Page<ActiveRuleInfo> page = (Page<ActiveRuleInfo>)BeanUtil.copyProperties(Page.class, pageVo);
		ActiveRuleInfo activeRuleInfo = (ActiveRuleInfo)BeanUtil.copyProperties(ActiveRuleInfo.class, activeRuleInfoVo);
		if(activeRuleInfo !=null)
		{
			if(activeRuleInfo.getActiveBaseRule() != null)
			{
				if(activeRuleInfoVo.getActiveBaseRule().getExpireStartTime() == 0)
				{
					activeRuleInfo.getActiveBaseRule().setExpireStartTime(null);
				}
				if(activeRuleInfoVo.getActiveBaseRule().getExpireEndTime() == 0)
				{
					activeRuleInfo.getActiveBaseRule().setExpireEndTime(null);
				}
			}
		}
		ActiveBaseRule baseRule = new ActiveBaseRule();
		baseRule = ActiveUtils.processingNullData(
				activeRuleInfoVo.getActiveBaseRule(), activeRuleInfo.getActiveBaseRule());
		//baseRule.setType(ActiveType.fullCut.getCode());
		activeRuleInfo.setActiveBaseRule(baseRule);
		page = activeRuleInfoLogic.getAllActiveRuleInfoByPage(page, activeRuleInfo);
		ActiveRuleInfoPageVoRes activeRuleInfoPageVoRes = new ActiveRuleInfoPageVoRes();
		List<ActiveRuleInfoVo> voList = new ArrayList<ActiveRuleInfoVo>();
		for(ActiveRuleInfo detail : page.getResultsContent())
		{
			ActiveRuleInfoVo vo = (ActiveRuleInfoVo)BeanUtil.copyProperties(ActiveRuleInfoVo.class, detail);
			voList.add(vo);
		}
		pageVo = (PageVo)BeanUtil.copyProperties(PageVo.class, page);
		FindPageActiveRuleInfoVoResultVo findResult = new FindPageActiveRuleInfoVoResultVo();
		activeRuleInfoPageVoRes.setPage(pageVo);
		activeRuleInfoPageVoRes.setActiveRuleInfoVoList(voList);
		findResult.setActiveRuleInfoPageVoRes(activeRuleInfoPageVoRes);
		LogCvt.info("分页查询[FindPageActiveRuleInfoVoResultVo]方法结束");
		return findResult;
	}

	@Override
	public FindActiveRuleInfoVoResultVo getActiveRuleInfoById(String clientId,
			String activeId) throws TException {
		LogCvt.info("查询[getActiveRuleInfoById]方法开始");
		FindActiveRuleInfoVoResultVo result = new FindActiveRuleInfoVoResultVo();
		ResultVo resultVo=new ResultVo(ResultCode.success.getCode(),"更新活动成功");
		ActiveRuleInfo activeRuleInfo = activeRuleInfoLogic.getActiveRuleInfo(clientId, activeId);
		ActiveRuleInfoVo activeRuleInfoVo = (ActiveRuleInfoVo)BeanUtil.copyProperties(ActiveRuleInfoVo.class, activeRuleInfo);
		result.setActiveRuleInfoVo(activeRuleInfoVo);
		result.setResultVo(resultVo);
		if(null == activeRuleInfoVo){
			LogCvt.error("activeRuleInfoVo 为空,查询 活动 ID"+activeId+"详情失败");
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("activeRuleInfoVo 为空,查询 活动 ID"+activeId+"详情失败");
		}
		LogCvt.info("查询[getActiveRuleInfoById]方法结束");
		return result;
	}

	@Override
	public ExportActiveOrderInfoRes exportActiveOrderInfoUrl(ActiveRuleInfoVo vo)
			throws TException {
		LogCvt.info("下载[exportActiveOrderInfoUrl]方法开始");
		ActiveRuleInfo activeRuleInfo = (ActiveRuleInfo)BeanUtil.copyProperties(ActiveRuleInfo.class, vo);
		String url = activeRuleInfoLogic.exportReport(activeRuleInfo);
		ExportActiveOrderInfoRes res = new ExportActiveOrderInfoRes();
		res.setExportUrl(url);
		LogCvt.info("下载[exportActiveOrderInfoUrl]方法结束");
		return res;
	}


}
