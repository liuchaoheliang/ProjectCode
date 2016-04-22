package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportMerchantProductLogic;
import com.froad.logic.impl.ReportMerchantProductLogicImpl;
import com.froad.po.BusinessSaleDetail;
import com.froad.po.CommonParam;
import com.froad.po.MerchantSaleDetail;
import com.froad.po.SaleCountDetail;
import com.froad.po.SaleTrend;
import com.froad.po.TypePercentInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ReportProductInfoService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.report.BusinessSaleDetailResVo;
import com.froad.thrift.vo.report.BusinessSaleDetailVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.thrift.vo.report.MerchantSaleDetailResVo;
import com.froad.thrift.vo.report.MerchantSaleDetailVo;
import com.froad.thrift.vo.report.SaleCountDetailPageVo;
import com.froad.thrift.vo.report.SaleCountDetailResVo;
import com.froad.thrift.vo.report.SaleCountDetailVo;
import com.froad.thrift.vo.report.SaleTrendResVo;
import com.froad.thrift.vo.report.SaleTrendVo;
import com.froad.thrift.vo.report.TypePercentResVo;
import com.froad.thrift.vo.report.TypePercentVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

public class ReportProductInfoServiceImpl extends BizMonitorBaseService implements ReportProductInfoService.Iface {
	
	private ReportMerchantProductLogic reportMerchantProductLogic = new ReportMerchantProductLogicImpl();
	/**
	 * 销售走势图
	* @Title: getSaleTrend 
	* @Description: 
	* @author longyunbo
	* @param @return
	* @param @throws TException
	* @return 
	* @throws
	 */
	public SaleTrendResVo getSaleTrend(CommonParamVo commonParamVo)
			throws TException {
		long begin = System.currentTimeMillis();
		LogCvt.info("查询销售走势图");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		SaleTrendResVo vo = new SaleTrendResVo();
		ResultVo resultVo = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			resultVo.setResultCode(ResultCode.notAllParameters.getCode());
			resultVo.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(resultVo);
			vo.setSaleTrendVos(new ArrayList<SaleTrendVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<SaleTrendVo> saleTrendList = new ArrayList<SaleTrendVo>();
			List<SaleTrend> saleTrends = reportMerchantProductLogic.getSaleTrend(param);
			saleTrendList = (List<SaleTrendVo>) BeanUtil.copyProperties(SaleTrendVo.class, saleTrends);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(resultVo);
			vo.setSaleTrendVos(saleTrendList);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(),e); 
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			vo.setResultVo(resultVo);
			vo.setSaleTrendVos(new ArrayList<SaleTrendVo>());
		} catch (Exception e) {
			LogCvt.error("统计销售走势图获取失败，原因:" + e.getMessage(),e); 
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(resultVo);
			vo.setSaleTrendVos(new ArrayList<SaleTrendVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}
	/**
	 * 业务销售类型占比
	* @Title: getSaleTypePercent 
	* @Description: 
	* @author longyunbo
	* @param @param commonParamVo
	* @param @return
	* @param @throws TException
	* @return 
	* @throws
	 */
	public TypePercentResVo getSaleTypePercent(CommonParamVo commonParamVo)
		throws TException{
		long begin = System.currentTimeMillis();
		LogCvt.info("查询业务销售类型占比");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		TypePercentResVo vo = new TypePercentResVo();
		ResultVo resultVo = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			resultVo.setResultCode(ResultCode.notAllParameters.getCode());
			resultVo.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<TypePercentInfo> infos = reportMerchantProductLogic.getSaleTypePercent(param);
			List<TypePercentVo> vos = (List<TypePercentVo>) BeanUtil.copyProperties(TypePercentVo.class, infos);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(),e); 
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}
	
	/**
	 * 支付方式占比
	* @Title: getPayTypePercent 
	* @Description: 
	* @author longyunbo
	* @param @param commonParamVo
	* @param @return
	* @param @throws TException
	* @return 
	* @throws
	 */
	public TypePercentResVo getPayTypePercent(CommonParamVo commonParamVo)
			throws TException{
		long begin = System.currentTimeMillis();
		LogCvt.info("查询支付方式占比");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		TypePercentResVo vo = new TypePercentResVo();
		ResultVo resultVo = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			resultVo.setResultCode(ResultCode.notAllParameters.getCode());
			resultVo.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<TypePercentInfo> infos = reportMerchantProductLogic.getPayTypePercent(param);
			List<TypePercentVo> vos = (List<TypePercentVo>) BeanUtil.copyProperties(TypePercentVo.class, infos);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(),e); 
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}

	/**
	 * 业务销售统计详情列表
	* @Title: getSaleCountDetail 
	* @Description: 
	* @author longyunbo
	* @param @param commonParamVo
	* @param @return
	* @return 
	* @throws
	 */
	public SaleCountDetailResVo getSaleCountDetail(CommonParamVo commonParamVo){
		long begin = System.currentTimeMillis();
		LogCvt.info("查询统计业务销售统计详情列表");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		SaleCountDetailResVo vo = new SaleCountDetailResVo();
		ResultVo resultVo = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			resultVo.setResultCode(ResultCode.notAllParameters.getCode());
			resultVo.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(resultVo);
			vo.setSaleCountDetailVos(new ArrayList<SaleCountDetailVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<SaleCountDetail> infos = reportMerchantProductLogic.getSaleCountDetail(param);
			List<SaleCountDetailVo> vos = (List<SaleCountDetailVo>) BeanUtil.copyProperties(SaleCountDetailVo.class, infos);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(resultVo);
			vo.setSaleCountDetailVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(),e); 
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			vo.setResultVo(resultVo);
			vo.setSaleCountDetailVos(new ArrayList<SaleCountDetailVo>());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(resultVo);
			vo.setSaleCountDetailVos(new ArrayList<SaleCountDetailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
		
	}
	
	/**
	 * 业务销售统计详情列表(分页)
	* @Title: getSaleCountDetailListByPage 
	* @Description: 
	* @author longyunbo
	* @param @param pageVo
	* @param @param commonParamVo
	* @param @return
	* @return 
	* @throws
	 */
	public SaleCountDetailPageVo getSaleCountDetailListByPage(PageVo pageVo, CommonParamVo commonParamVo){
		long begin = System.currentTimeMillis();
		LogCvt.info("分页查询统计业务销售统计详情列表");
		LogCvt.info("分页参数: ["+pageVo.toString()+"]");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		SaleCountDetailPageVo pageRes = new SaleCountDetailPageVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			pageRes.setPageVo(pageVo);
			pageRes.setSaleCountDetailVos(new ArrayList<SaleCountDetailVo>());
			return pageRes;
		}
		try {
			Page<SaleCountDetail> page = (Page<SaleCountDetail>) BeanUtil.copyProperties(Page.class, pageVo);
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			
			page = reportMerchantProductLogic.getSaleCountDetailListByPage(page, param);
			List<SaleCountDetailVo> vos = (List<SaleCountDetailVo>) BeanUtil.copyProperties(SaleCountDetailVo.class, page.getResultsContent());
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			
			pageRes.setPageVo(pageVo);
			pageRes.setSaleCountDetailVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(),e); 
			pageRes.setPageVo(pageVo);
			pageRes.setSaleCountDetailVos(new ArrayList<SaleCountDetailVo>());
		} catch (Exception e) {
			LogCvt.error("统计业务销售统计详情列表分页失败，原因:" + e.getMessage(),e);
			pageRes.setPageVo(pageVo);
			pageRes.setSaleCountDetailVos(new ArrayList<SaleCountDetailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return pageRes;
		
	}
	
	
	/**
	 * 商户业务销售详情
	* @Title: getMerchantSaleDetail 
	* @Description: 
	* @author longyunbo
	* @param @param commonParamVo
	* @param @return
	* @return MerchantSaleDetailResVo
	* @throws
	 */
	public MerchantSaleDetailResVo getMerchantSaleDetail(CommonParamVo commonParamVo){
		long begin = System.currentTimeMillis();
		LogCvt.info("查询统计商户业务销售详情列表");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		MerchantSaleDetailResVo vo = new MerchantSaleDetailResVo();
		ResultVo resultVo = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			resultVo.setResultCode(ResultCode.notAllParameters.getCode());
			resultVo.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(resultVo);
			vo.setMerchantSaleDetailVos(new ArrayList<MerchantSaleDetailVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<MerchantSaleDetail> infos = reportMerchantProductLogic.getMerchantSaleDetail(param);
			List<MerchantSaleDetailVo> vos = (List<MerchantSaleDetailVo>) BeanUtil.copyProperties(MerchantSaleDetailVo.class, infos);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(resultVo);
			vo.setMerchantSaleDetailVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(),e); 
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			vo.setResultVo(resultVo);
			vo.setMerchantSaleDetailVos(new ArrayList<MerchantSaleDetailVo>());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(resultVo);
			vo.setMerchantSaleDetailVos(new ArrayList<MerchantSaleDetailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
		
	}
	
	/**
	 * 业务类型销售统计详情列表
	* @Title: getBusinessSaleDetailResVo 
	* @Description: 
	* @author longyunbo
	* @param @param commonParamVo
	* @param @return
	* @return BusinessSaleDetailResVo
	* @throws
	 */
	public BusinessSaleDetailResVo getBusinessSaleDetail(CommonParamVo commonParamVo){
		long begin = System.currentTimeMillis();
		LogCvt.info("查询统计业务类型销售统计详情列表");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		BusinessSaleDetailResVo vo = new BusinessSaleDetailResVo();
		ResultVo resultVo = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			resultVo.setResultCode(ResultCode.notAllParameters.getCode());
			resultVo.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(resultVo);
			vo.setBusinessSaleDetailVos(new ArrayList<BusinessSaleDetailVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<BusinessSaleDetail> infos = reportMerchantProductLogic.getBusinessSaleDetail(param);
			List<BusinessSaleDetailVo> vos = (List<BusinessSaleDetailVo>) BeanUtil.copyProperties(BusinessSaleDetailVo.class, infos);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(resultVo);
			vo.setBusinessSaleDetailVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(),e); 
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			vo.setResultVo(resultVo);
			vo.setBusinessSaleDetailVos(new ArrayList<BusinessSaleDetailVo>());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(resultVo);
			vo.setBusinessSaleDetailVos(new ArrayList<BusinessSaleDetailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}

}
