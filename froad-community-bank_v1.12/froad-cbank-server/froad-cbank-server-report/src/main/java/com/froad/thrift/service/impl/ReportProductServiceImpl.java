package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.logic.ReportProductLogic;
import com.froad.logic.impl.ReportProductServiceLogicImpl;
import com.froad.po.CommonParam;
import com.froad.po.ProductSaleDetail;
import com.froad.po.ProductSaleTrend;
import com.froad.po.TypePercentInfo;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ReportProductService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.thrift.vo.report.ProductSaleDetailPageVo;
import com.froad.thrift.vo.report.ProductSaleDetailResVo;
import com.froad.thrift.vo.report.ProductSaleDetailVo;
import com.froad.thrift.vo.report.ProductSaleTrendResVo;
import com.froad.thrift.vo.report.ProductSaleTrendVo;
import com.froad.thrift.vo.report.TypePercentResVo;
import com.froad.thrift.vo.report.TypePercentVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

public class ReportProductServiceImpl  extends BizMonitorBaseService implements ReportProductService.Iface{

	private ReportProductLogic reportProductLogic = new ReportProductServiceLogicImpl();
	
    /**
     * 商品销售走势
     * 
     * @param commonParamVo
     */
	public ProductSaleTrendResVo getProductSaleTrend(CommonParamVo commonParamVo)
			throws TException{
		long begin = System.currentTimeMillis();
		LogCvt.info("统计商品销售走势");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		ProductSaleTrendResVo vo = new ProductSaleTrendResVo();
		ResultVo resultVo = new ResultVo();
		if(Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())){
			resultVo.setResultCode(ResultCode.notAllParameters.getCode());
			resultVo.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(resultVo);
			vo.setProductSaleTrendVos(new ArrayList<ProductSaleTrendVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<ProductSaleTrendVo> saleTrendList = new ArrayList<ProductSaleTrendVo>();
			List<ProductSaleTrend> saleTrends = reportProductLogic.getProductSaleTrend(param);
			saleTrendList = (List<ProductSaleTrendVo>) BeanUtil.copyProperties(ProductSaleTrendVo.class, saleTrends);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(resultVo);
			vo.setProductSaleTrendVos(saleTrendList);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e); 
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			vo.setResultVo(resultVo);
			vo.setProductSaleTrendVos(new ArrayList<ProductSaleTrendVo>());
		} catch (Exception e) {
			LogCvt.error("统计商品销售走势失败，原因:" + e.getMessage(),e); 
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(resultVo);
			vo.setProductSaleTrendVos(new ArrayList<ProductSaleTrendVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}
	
	
	
	/**
     * 商品业务类型占比
     * 
     * @param commonParamVo
     */
	public TypePercentResVo getProductTypePercent(CommonParamVo commonParamVo) {
		long begin = System.currentTimeMillis();
		LogCvt.info("统计商品业务类型占比");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		TypePercentResVo vo = new TypePercentResVo();
		ResultVo resultVo = new ResultVo();
		if (Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())) {
			resultVo.setResultCode(ResultCode.notAllParameters.getCode());
			resultVo.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<TypePercentInfo> infos = reportProductLogic.getProductTypePercent(param);
			List<TypePercentVo> vos = (List<TypePercentVo>) BeanUtil.copyProperties(TypePercentVo.class, infos);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(vos);
		} catch (Exception e) {
			LogCvt.error("统计商品业务类型占比失败，原因:" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}
	
	
	 
	/**
	  * 商品类目占比
	  * 
	  * @param commonParamVo
	  */
	public TypePercentResVo getProductCategoryPercent(
			CommonParamVo commonParamVo) {
		long begin = System.currentTimeMillis();
		LogCvt.info("统计商品类目占比");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		TypePercentResVo vo = new TypePercentResVo();
		ResultVo resultVo = new ResultVo();
		if (Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())) {
			resultVo.setResultCode(ResultCode.notAllParameters.getCode());
			resultVo.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
			return vo;
		}
		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);
			List<TypePercentInfo> infos = reportProductLogic.getProductCategoryPercent(param);
			List<TypePercentVo> vos = (List<TypePercentVo>) BeanUtil.copyProperties(TypePercentVo.class, infos);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(vos);
		} catch (Exception e) {
			LogCvt.error("统计商品类目占比失败，原因:" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(resultVo);
			vo.setTypePercentVos(new ArrayList<TypePercentVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;
	}
	    
	    
	/**
	  * 商品销售详情列表
	  * 
	  * @param commonParamVo
	  */
	public ProductSaleDetailResVo getProductSaleDetail(
			CommonParamVo commonParamVo) {
		long begin = System.currentTimeMillis();
		LogCvt.info("统计商品销售详情列表");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		ProductSaleDetailResVo vo = new ProductSaleDetailResVo();
		ResultVo resultVo = new ResultVo();
		if (Checker.isEmpty(commonParamVo.getOrgCode()) || Checker.isEmpty(commonParamVo.getClientId())) {
			resultVo.setResultCode(ResultCode.notAllParameters.getCode());
			resultVo.setResultDesc(ResultCode.notAllParameters.getMsg());
			vo.setResultVo(resultVo);
			vo.setProductSaleDetailVos(new ArrayList<ProductSaleDetailVo>());
			return vo;
		}

		try {
			CommonParam param = (CommonParam) BeanUtil.copyProperties( CommonParam.class, commonParamVo);
			List<ProductSaleDetailVo> productSaleDetailList = new ArrayList<ProductSaleDetailVo>();
			List<ProductSaleDetail> productSaleDetails = reportProductLogic
					.getProductSaleDetail(param);
			productSaleDetailList = (List<ProductSaleDetailVo>) BeanUtil.copyProperties(ProductSaleDetailVo.class, productSaleDetails);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			vo.setResultVo(resultVo);
			vo.setProductSaleDetailVos(productSaleDetailList);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e); 
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			vo.setResultVo(resultVo);
			vo.setProductSaleDetailVos(new ArrayList<ProductSaleDetailVo>());
		} catch (Exception e) {
			LogCvt.error("统计商品销售详情列表失败，原因:" + e.getMessage(), e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc(ResultCode.failed.getMsg());
			vo.setResultVo(resultVo);
			vo.setProductSaleDetailVos(new ArrayList<ProductSaleDetailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return vo;

	}
	    
	/**
	  * 商品销售详情列表(分页)
	  * @Title: getProductSaleDetailListByPage 
	  * @Description: 
	  * @author longyunbo
	  * @param @param pageVo
	  * @param @param commonParamVo
	  * @param @return
	  * @return 
	  * @throws
	  */
	public ProductSaleDetailPageVo getProductSaleDetailListByPage(
			PageVo pageVo, CommonParamVo commonParamVo) {
		long begin = System.currentTimeMillis();
		LogCvt.info("分页统计商品销售详情列表");
		LogCvt.info("分页参数: ["+pageVo.toString()+"]");
		LogCvt.info("参数: ["+commonParamVo.toString()+"]");
		
		ProductSaleDetailPageVo pageRes = new ProductSaleDetailPageVo();
		if (Checker.isEmpty(commonParamVo.getOrgCode())
				|| Checker.isEmpty(commonParamVo.getClientId())) {
			pageRes.setPageVo(pageVo);
			pageRes.setProductSaleDetailVos(new ArrayList<ProductSaleDetailVo>());
			return pageRes;
		}

		try {
			Page<ProductSaleDetail> page = (Page<ProductSaleDetail>) BeanUtil.copyProperties(Page.class, pageVo);
			CommonParam param = (CommonParam) BeanUtil.copyProperties(CommonParam.class, commonParamVo);

			page = reportProductLogic.getProductSaleDetailListByPage(page, param);

			List<ProductSaleDetailVo> vos = (List<ProductSaleDetailVo>) BeanUtil.copyProperties(ProductSaleDetailVo.class, page.getResultsContent());
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);

			pageRes.setPageVo(pageVo);
			pageRes.setProductSaleDetailVos(vos);
		} catch (FroadBusinessException e) {
			LogCvt.error(e.getMsg(), e); 
			pageRes.setPageVo(pageVo);
			pageRes.setProductSaleDetailVos(new ArrayList<ProductSaleDetailVo>());
		} catch (Exception e) {
			LogCvt.error("统计商品销售详情分页列表失败，原因:" + e.getMessage(), e);
			pageRes.setPageVo(pageVo);
			pageRes.setProductSaleDetailVos(new ArrayList<ProductSaleDetailVo>());
		}
		LogCvt.info("耗时共 "+(System.currentTimeMillis()-begin) +" ms");
		return pageRes;
	}
	
}
