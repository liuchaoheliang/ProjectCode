package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportMerchantContractLogic;
import com.froad.logic.impl.ReportMerchantContractLogicImpl;
import com.froad.po.CommonParam;
import com.froad.po.MerchantContractDeatailRes;
import com.froad.po.MerchantContractRankRes;
import com.froad.po.TypePercentInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ReportMerchantContractService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.thrift.vo.report.MerchantContractRankResVo;
import com.froad.thrift.vo.report.MerchantContractRankVo;
import com.froad.thrift.vo.report.ReportMerchantContractDeatailPageVo;
import com.froad.thrift.vo.report.ReportMerchantContractDeatailResVo;
import com.froad.thrift.vo.report.ReportMerchantContractDeatailVo;
import com.froad.thrift.vo.report.TypePercentResVo;
import com.froad.thrift.vo.report.TypePercentVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

public class ReportMerchantContractServiceImpl extends BizMonitorBaseService implements ReportMerchantContractService.Iface {
	
	private ReportMerchantContractLogic reportMerchantContractLogic = new ReportMerchantContractLogicImpl();
	
	@Override
	public MerchantContractRankResVo merchantContractRank(CommonParamVo commonParamVo) throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info(new StringBuffer("查询商户签约人排行榜").append("参数: [clientId: ")
				.append(commonParamVo.toString()).append("]").toString());
	
		MerchantContractRankResVo vo = new MerchantContractRankResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractRankVos(new ArrayList<MerchantContractRankVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<MerchantContractRankRes> res = reportMerchantContractLogic.merchantContractRank(param);
			List<MerchantContractRankVo> vos = (List<MerchantContractRankVo>) BeanUtil.copyProperties(MerchantContractRankVo.class, res);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractRankVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractRankVos(new ArrayList<MerchantContractRankVo>());
		} catch (Exception e) {
			LogCvt.error("查询商户签约人排行异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractRankVos(new ArrayList<MerchantContractRankVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}


	@Override
	public TypePercentResVo merchantTypeAddPercent(CommonParamVo commonParamVo)
			throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info(new StringBuffer("查询商户新增类型占比").append("参数: [").append(commonParamVo.toString()).append("]").toString());
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
			List<TypePercentInfo> infos = reportMerchantContractLogic.merchantTypeAddPercent(param);
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
			LogCvt.error("查询商户新增类型占比异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(result);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}


	@Override
	public MerchantContractRankResVo merchantContractAddRank(
			CommonParamVo commonParamVo) throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info(new StringBuffer("查询签约人新增商户数排行榜").append("参数: [").append(commonParamVo.toString()).append("]").toString());
		
		MerchantContractRankResVo vo = new MerchantContractRankResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractRankVos(new ArrayList<MerchantContractRankVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<MerchantContractRankRes> res = reportMerchantContractLogic.merchantContractAddRank(param);
			List<MerchantContractRankVo> vos = (List<MerchantContractRankVo>) BeanUtil.copyProperties(MerchantContractRankVo.class, res);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractRankVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractRankVos(new ArrayList<MerchantContractRankVo>());
		} catch (Exception e) {
			LogCvt.error("查询签约人新增商户数排行异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractRankVos(new ArrayList<MerchantContractRankVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}


	@Override
	public ReportMerchantContractDeatailResVo merchantContractDetailList(
			CommonParamVo commonParamVo) throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info(new StringBuffer("查询签约人商户统计详细列表").append("参数: [").append(commonParamVo.toString()).append("]").toString());
		
		ReportMerchantContractDeatailResVo vo = new ReportMerchantContractDeatailResVo();
		ResultVo result = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			result.setResultCode(ResultCode.notAllParameters.getCode());
			result.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractDeatailVos(new ArrayList<ReportMerchantContractDeatailVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<MerchantContractDeatailRes> res = reportMerchantContractLogic.merchantContractDetailList(param);
			List<ReportMerchantContractDeatailVo> vos = (List<ReportMerchantContractDeatailVo>) BeanUtil.copyProperties(ReportMerchantContractDeatailVo.class, res);
			result.setResultCode(ResultCode.success.getCode());
			result.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractDeatailVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			result.setResultCode(e.getCode());
			result.setResultDesc(e.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractDeatailVos(new ArrayList<ReportMerchantContractDeatailVo>());
		} catch (Exception e) {
			LogCvt.error("查询签约人商户统计详细列表异常", e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(result);
			vo.setMerchantContractDeatailVos(new ArrayList<ReportMerchantContractDeatailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}


	@Override
	public ReportMerchantContractDeatailPageVo merchantContractDetailListByPage(
			PageVo pageVo, CommonParamVo commonParamVo) throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("分页查询签约人商户统计详细列表");
		LogCvt.info("分页参数: ["+pageVo.toString()+"]");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		ReportMerchantContractDeatailPageVo pageRes = new ReportMerchantContractDeatailPageVo();
		
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			pageRes.setPageVo(pageVo);
			pageRes.setMerchantContractDeatailVos(new ArrayList<ReportMerchantContractDeatailVo>());
			return pageRes;
		}
		try {
			Page<MerchantContractDeatailRes> page = (Page<MerchantContractDeatailRes>) BeanUtil.copyProperties(Page.class, pageVo);
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			
			page = reportMerchantContractLogic.merchantContractDetailListByPage(page, param);
			List<ReportMerchantContractDeatailVo> vos = (List<ReportMerchantContractDeatailVo>) BeanUtil.copyProperties(ReportMerchantContractDeatailVo.class, page.getResultsContent());
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			
			pageRes.setPageVo(pageVo);
			pageRes.setMerchantContractDeatailVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e);
			pageRes.setPageVo(pageVo);
			pageRes.setMerchantContractDeatailVos(new ArrayList<ReportMerchantContractDeatailVo>());
		} catch (Exception e) {
			LogCvt.error("分页查询签约人商户统计详细列表异常", e);
			pageRes.setPageVo(pageVo);
			pageRes.setMerchantContractDeatailVos(new ArrayList<ReportMerchantContractDeatailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return pageRes;
	}


}
