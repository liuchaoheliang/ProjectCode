package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportMerchantInfoLogic;
import com.froad.logic.impl.ReportMerchantInfoLogicImpl;
import com.froad.po.CommonParam;
import com.froad.po.MerchantBussinessRes;
import com.froad.po.MerchantDetailRes;
import com.froad.po.MerchantTrend;
import com.froad.po.TypePercentInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ReportMerchantInfoService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.thrift.vo.report.MerchantTrendResVo;
import com.froad.thrift.vo.report.MerchantTrendVo;
import com.froad.thrift.vo.report.ReportMerchantBussinessResVo;
import com.froad.thrift.vo.report.ReportMerchantBussinessVo;
import com.froad.thrift.vo.report.ReportMerchantDetailPageVo;
import com.froad.thrift.vo.report.ReportMerchantDetailResVo;
import com.froad.thrift.vo.report.ReportMerchantDetailVo;
import com.froad.thrift.vo.report.ReportMerchantOutletResVo;
import com.froad.thrift.vo.report.ReportMerchantOutletVo;
import com.froad.thrift.vo.report.TypePercentResVo;
import com.froad.thrift.vo.report.TypePercentVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

public class ReportMerchantInfoServiceImpl extends BizMonitorBaseService implements ReportMerchantInfoService.Iface {
	
	private ReportMerchantInfoLogic reportMerchantInfoLogic = new ReportMerchantInfoLogicImpl();
	
	@Override
	public MerchantTrendResVo getMerchantTrend(CommonParamVo commonParamVo)
			throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询商户走势图");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		MerchantTrendResVo vo = new MerchantTrendResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(result);
			vo.setMerchantTrendVo(new ArrayList<MerchantTrendVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<MerchantTrend> trends = reportMerchantInfoLogic.getMerchantTrend(param);
			List<MerchantTrendVo> trendVos = (List<MerchantTrendVo>)BeanUtil.copyProperties(MerchantTrendVo.class, trends);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(result);
			vo.setMerchantTrendVo(trendVos);
		} catch (FroadBusinessException e){
			LogCvt.error(e.getMsg(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getMsg());
			vo.setResultVo(result);
			vo.setMerchantTrendVo(new ArrayList<MerchantTrendVo>());
		} catch (Exception e) {
			LogCvt.error("查询商户走势异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(result);
			vo.setMerchantTrendVo(new ArrayList<MerchantTrendVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}

	@Override
	public TypePercentResVo getMerchantTypePercent(
			CommonParamVo commonParamVo) throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询商户类型占比");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		TypePercentResVo vo = new TypePercentResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(result);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<TypePercentInfo> infos = reportMerchantInfoLogic.getMerchantTypePercent(param);
			List<TypePercentVo> vos = (List<TypePercentVo>) BeanUtil.copyProperties(TypePercentVo.class, infos);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(result);
			vo.setTypePercentVos(vos);
		} catch (FroadBusinessException e){
			LogCvt.error(e.getMsg(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getMsg());
			vo.setResultVo(result);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		} catch (Exception e) {
			LogCvt.error("查询商户类型占比异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(result);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}

	@Override
	public TypePercentResVo getMerchantBussinessPercent(
			CommonParamVo commonParamVo) throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询商户业务占比");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		TypePercentResVo vo = new TypePercentResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(result);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<TypePercentInfo> infos = reportMerchantInfoLogic.getMerchantBussinessPercent(param);
			List<TypePercentVo> vos = (List<TypePercentVo>) BeanUtil.copyProperties(TypePercentVo.class, infos);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(result);
			vo.setTypePercentVos(vos);
		} catch (FroadBusinessException e){
			LogCvt.error(e.getMsg(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getMsg());
			vo.setResultVo(result);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		} catch (Exception e) {
			LogCvt.error("查询商户业务占比异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(result);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}

	@Override
	public ReportMerchantDetailResVo getMerchantDetailList(
			CommonParamVo commonParamVo) throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询商户信息统计详细列表");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		ReportMerchantDetailResVo vo = new ReportMerchantDetailResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(result);
			vo.setMerchantDetailVos(new ArrayList<ReportMerchantDetailVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<MerchantDetailRes> detailRes = reportMerchantInfoLogic.getMerchantDetailList(param);
			List<ReportMerchantDetailVo> vos = (List<ReportMerchantDetailVo>) BeanUtil.copyProperties(ReportMerchantDetailVo.class, detailRes);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(result);
			vo.setMerchantDetailVos(vos);
		} catch (FroadBusinessException e){
			LogCvt.error(e.getMsg(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getMsg());
			vo.setResultVo(result);
			vo.setMerchantDetailVos(new ArrayList<ReportMerchantDetailVo>());
		} catch (Exception e) {
			LogCvt.error("查询商户信息统计详细列表异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(result);
			vo.setMerchantDetailVos(new ArrayList<ReportMerchantDetailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}

	@Override
	public ReportMerchantBussinessResVo getMerchantBussinessList(
			CommonParamVo commonParamVo) throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询商户业务统计信息");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		ReportMerchantBussinessResVo vo = new ReportMerchantBussinessResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(result);
			vo.setMerchantBussinessVos(new ArrayList<ReportMerchantBussinessVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<MerchantBussinessRes> res = reportMerchantInfoLogic.getMerchantBussinessList(param);
			List<ReportMerchantBussinessVo> vos = (List<ReportMerchantBussinessVo>) BeanUtil.copyProperties(ReportMerchantBussinessVo.class, res);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(result);
			vo.setMerchantBussinessVos(vos);
		} catch (FroadBusinessException e){
			LogCvt.error(e.getMsg(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getMsg());
			vo.setResultVo(result);
			vo.setMerchantBussinessVos(new ArrayList<ReportMerchantBussinessVo>());
		} catch (Exception e) {
			LogCvt.error("查询商户业务统计信息异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(result);
			vo.setMerchantBussinessVos(new ArrayList<ReportMerchantBussinessVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}

	@Override
	public ReportMerchantDetailPageVo getMerchantDetailListByPage(
			PageVo pageVo, CommonParamVo commonParamVo) throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("分页查询商户信息统计详细列表");
		LogCvt.info("分页参数: ["+pageVo.toString()+"]");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		ReportMerchantDetailPageVo pageRes = new ReportMerchantDetailPageVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			pageRes.setPageVo(pageVo);
			pageRes.setMerchantDetailVos(new ArrayList<ReportMerchantDetailVo>());
			return pageRes;
		}
		try {
			Page<MerchantDetailRes> page = (Page<MerchantDetailRes>) BeanUtil.copyProperties(Page.class, pageVo);
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			
			page = reportMerchantInfoLogic.getMerchantDetailListByPage(page, param);
			List<ReportMerchantDetailVo> vos = (List<ReportMerchantDetailVo>) BeanUtil.copyProperties(ReportMerchantDetailVo.class, page.getResultsContent());
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			
			pageRes.setPageVo(pageVo);
			pageRes.setMerchantDetailVos(vos);
		} catch (FroadBusinessException e){
			LogCvt.error(e.getMsg(), e);
			pageRes.setPageVo(pageVo);
			pageRes.setMerchantDetailVos(new ArrayList<ReportMerchantDetailVo>());
		} catch (Exception e) {
			LogCvt.error("分页查询商户信息统计详细列表异常", e);
			pageRes.setPageVo(pageVo);
			pageRes.setMerchantDetailVos(new ArrayList<ReportMerchantDetailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return pageRes;
	}

	@Override
	public ReportMerchantOutletResVo getMerchantOutletList(
			CommonParamVo commonParamVo) throws TException {
		ReportMerchantOutletResVo responseVo = null;
		ResultVo resultVo = null;
		List<ReportMerchantOutletVo> merchantOutletVos = null;
		CommonParam param = null;
		long begin = System.currentTimeMillis();
		
		LogCvt.info(new StringBuffer("分页查询商户信息统计详细列表  参数: [").append(commonParamVo.toString()).append("]").toString());
		
		merchantOutletVos = new ArrayList<ReportMerchantOutletVo>();
		resultVo = new ResultVo();
		responseVo = new ReportMerchantOutletResVo();
		
		try {
			param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			merchantOutletVos = reportMerchantInfoLogic.getMerchantOutletList(param);
			
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
		}catch (Exception e) {
			LogCvt.error("商户门店信息查找异常", e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("商户门店信息查找异常");
		}
		
		responseVo.setResultVo(resultVo);
		responseVo.setMerchantOutletVos(merchantOutletVos);
		
		LogCvt.info(new StringBuffer("耗时共 ").append(System.currentTimeMillis()-begin).append(" ms").toString());
		
		return responseVo;
	}

}
