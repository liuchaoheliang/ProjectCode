package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportUserLogic;
import com.froad.logic.impl.ReportUserLogicImpl;
import com.froad.po.CommonParam;
import com.froad.po.TypePercentInfo;
import com.froad.po.UserSummary;
import com.froad.po.UserTradeDetail;
import com.froad.po.UserTradeInfo;
import com.froad.po.UserTrend;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ReportUserService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.thrift.vo.report.TypePercentResVo;
import com.froad.thrift.vo.report.TypePercentVo;
import com.froad.thrift.vo.report.UserSummaryPageVo;
import com.froad.thrift.vo.report.UserSummaryResVo;
import com.froad.thrift.vo.report.UserSummaryVo;
import com.froad.thrift.vo.report.UserTradeDetailResVo;
import com.froad.thrift.vo.report.UserTradeDetailVo;
import com.froad.thrift.vo.report.UserTradeInfoResVo;
import com.froad.thrift.vo.report.UserTradeInfoVo;
import com.froad.thrift.vo.report.UserTrendResVo;
import com.froad.thrift.vo.report.UserTrendVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

public class ReportUserServiceImpl extends BizMonitorBaseService implements ReportUserService.Iface {
	
	private ReportUserLogic reportUserLogic = new ReportUserLogicImpl();
	
	@Override
	public UserTrendResVo userTrend(CommonParamVo commonParamVo)
			throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询用户走势图");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		UserTrendResVo resVo = new UserTrendResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTrendVos(new ArrayList<UserTrendVo>());
			return resVo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<UserTrend> trends = reportUserLogic.userTrend(param);
			List<UserTrendVo> vos = (List<UserTrendVo>) BeanUtil.copyProperties(UserTrendVo.class, trends);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTrendVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.info(e.getMsg(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTrendVos(new ArrayList<UserTrendVo>());
		} catch (Exception e) {
			LogCvt.info("查询用户走势图异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTrendVos(new ArrayList<UserTrendVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return resVo;
	}

	@Override
	public TypePercentResVo userTradeTypePercent(CommonParamVo commonParamVo)
			throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询用户交易类型占比");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		TypePercentResVo resVo = new TypePercentResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			resVo.setResultVo(result);
			resVo.setTypePercentVos(new ArrayList<TypePercentVo>());
			return resVo;
		}
		
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<TypePercentInfo> infos = reportUserLogic.userTradeTypePercent(param);
			List<TypePercentVo> vos = (List<TypePercentVo>) BeanUtil.copyProperties(TypePercentVo.class, infos);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(result);
			resVo.setTypePercentVos(vos);
		} catch (FroadBusinessException e){
			LogCvt.error(e.getMsg(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getMsg());
			resVo.setResultVo(result);
			resVo.setTypePercentVos(new ArrayList<TypePercentVo>());
		} catch (Exception e) {
			LogCvt.error("查询用户交易类型占比异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			resVo.setResultVo(result);
			resVo.setTypePercentVos(new ArrayList<TypePercentVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return resVo;
	}

	@Override
	public TypePercentResVo userConsumeTypePercent(CommonParamVo commonParamVo)
			throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询用户消费类型占比");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		TypePercentResVo resVo = new TypePercentResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			resVo.setResultVo(result);
			resVo.setTypePercentVos(new ArrayList<TypePercentVo>());
			return resVo;
		}
		
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<TypePercentInfo> infos = reportUserLogic.userConsumeTypePercent(param);
			List<TypePercentVo> vos = (List<TypePercentVo>) BeanUtil.copyProperties(TypePercentVo.class, infos);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(result);
			resVo.setTypePercentVos(vos);
		} catch (FroadBusinessException e){
			LogCvt.error(e.getMsg(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getMsg());
			resVo.setResultVo(result);
			resVo.setTypePercentVos(new ArrayList<TypePercentVo>());
		} catch (Exception e) {
			LogCvt.error("查询用户交易类型占比异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			resVo.setResultVo(result);
			resVo.setTypePercentVos(new ArrayList<TypePercentVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return resVo;
	}

	@Override
	public UserSummaryResVo userSummaryList(CommonParamVo commonParamVo)
			throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询用户统计详情列表");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		UserSummaryResVo resVo = new UserSummaryResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			resVo.setResultVo(result);
			resVo.setUserSummaryVos(new ArrayList<UserSummaryVo>());
			return resVo;
		}
		
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<UserSummary> summarys = reportUserLogic.userSummaryList(param);
			List<UserSummaryVo> vos = (List<UserSummaryVo>) BeanUtil.copyProperties(UserSummaryVo.class, summarys);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(result);
			resVo.setUserSummaryVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.info(e.getMsg(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			resVo.setResultVo(result);
			resVo.setUserSummaryVos(new ArrayList<UserSummaryVo>());
		} catch (Exception e) {
			LogCvt.error("查询用户统计详情列表异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			resVo.setResultVo(result);
			resVo.setUserSummaryVos(new ArrayList<UserSummaryVo>());
		}
		
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return resVo;
	}

	@Override
	public UserSummaryPageVo userSummaryListByPage(PageVo pageVo,
			CommonParamVo commonParamVo) throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("分页查询用户统计详情列表");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		UserSummaryPageVo pageRes = new UserSummaryPageVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			pageRes.setPageVo(pageVo);
			pageRes.setUserSummaryVos(new ArrayList<UserSummaryVo>());
			return pageRes;
		}
		
		try {
			Page<UserSummary> page = (Page<UserSummary>) BeanUtil.copyProperties(Page.class, pageVo);
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			page = reportUserLogic.userSummaryListByPage(param, page);
			List<UserSummaryVo> vos = (List<UserSummaryVo>) BeanUtil.copyProperties(UserSummaryVo.class, page.getResultsContent());
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			pageRes.setPageVo(pageVo);
			pageRes.setUserSummaryVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.info(e.getMsg(), e);
			pageRes.setPageVo(pageVo);
			pageRes.setUserSummaryVos(new ArrayList<UserSummaryVo>());
		} catch (Exception e) {
			LogCvt.error("分页查询用户统计详情列表异常", e);
			pageRes.setPageVo(pageVo);
			pageRes.setUserSummaryVos(new ArrayList<UserSummaryVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return pageRes;
	}

	@Override
	public UserTradeDetailResVo userTradeDetailList(CommonParamVo commonParamVo)
			throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询用户交易支付详情列表");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		UserTradeDetailResVo resVo = new UserTradeDetailResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTradeDetailVos(new ArrayList<UserTradeDetailVo>());
			return resVo;
		}
		
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<UserTradeDetail> details = reportUserLogic.userTradeDetailList(param);
			List<UserTradeDetailVo> vos = (List<UserTradeDetailVo>) BeanUtil.copyProperties(UserTradeDetailVo.class, details);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTradeDetailVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.info(e.getMsg(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTradeDetailVos(new ArrayList<UserTradeDetailVo>());
		} catch (Exception e) {
			LogCvt.error("查询用户交易支付详情列表异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTradeDetailVos(new ArrayList<UserTradeDetailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return resVo;
	}

	@Override
	public UserTradeInfoResVo userTradeInfoList(CommonParamVo commonParamVo)
			throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询用户交易信息列表");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		UserTradeInfoResVo resVo = new UserTradeInfoResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTradeInfoVos(new ArrayList<UserTradeInfoVo>());
			return resVo;
		}
		
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<UserTradeInfo> infos = reportUserLogic.userTradeInfoList(param);
			List<UserTradeInfoVo> vos = (List<UserTradeInfoVo>) BeanUtil.copyProperties(UserTradeInfoVo.class, infos);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTradeInfoVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.info(e.getMsg(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTradeInfoVos(new ArrayList<UserTradeInfoVo>());
		} catch (Exception e) {
			LogCvt.error("查询用户交易信息列表异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			resVo.setResultVo(result);
			resVo.setUserTradeInfoVos(new ArrayList<UserTradeInfoVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return resVo;
	}

}
