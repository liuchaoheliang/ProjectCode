/**
 * Project Name:froad-cbank-server-boss
 * File Name:ProductActivityTagServiceImpl.java
 * Package Name:com.froad.thrift.service.impl
 * Date:2015年10月28日上午11:59:03
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.ProductActivityTagLogic;
import com.froad.logic.impl.ProductActivityTagLogicImpl;
import com.froad.po.InputRelateProductActivityPo;
import com.froad.po.ProductWeightActivityTag;
import com.froad.po.RecommendActivityTag;
import com.froad.po.RelateProductActivityPo;
import com.froad.po.WeightActivityTag;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.ProductActivityTagService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.recommendactivitytag.AdjustProductWeightReqVo;
import com.froad.thrift.vo.recommendactivitytag.DeleteRelateProductReqVo;
import com.froad.thrift.vo.recommendactivitytag.EnableProductActivityReqVo;
import com.froad.thrift.vo.recommendactivitytag.ExportRelateActivityTagReqVo;
import com.froad.thrift.vo.recommendactivitytag.InputRelateProductActivityReqVo;
import com.froad.thrift.vo.recommendactivitytag.ProductActivityTagDetailReqVo;
import com.froad.thrift.vo.recommendactivitytag.ProductActivityTagDetailResVo;
import com.froad.thrift.vo.recommendactivitytag.ProductNameResVo;
import com.froad.thrift.vo.recommendactivitytag.ProductWeightActivityTagPageReqVo;
import com.froad.thrift.vo.recommendactivitytag.ProductWeightActivityTagPageVo;
import com.froad.thrift.vo.recommendactivitytag.ProductWeightActivityTagVo;
import com.froad.thrift.vo.recommendactivitytag.RecommendActivityTagPageVo;
import com.froad.thrift.vo.recommendactivitytag.RecommendActivityTagVo;
import com.froad.thrift.vo.recommendactivitytag.RelateProductActivityVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

/**
 * ClassName:ProductActivityTagServiceImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月28日 上午11:59:03
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProductActivityTagServiceImpl extends BizMonitorBaseService implements ProductActivityTagService.Iface {

	private ProductActivityTagLogic logic = new ProductActivityTagLogicImpl();
	
	public ProductActivityTagServiceImpl(String name, String version){
		super(name, version);
	}
	
	@Override
	public RecommendActivityTagPageVo findProductTagByPage(RecommendActivityTagVo vo, PageVo pageVo) throws TException {
		RecommendActivityTagPageVo resVo = new RecommendActivityTagPageVo();
		ResultVo resultVo = new ResultVo();
		try {
			Page<RecommendActivityTag> page = (Page<RecommendActivityTag>) BeanUtil.copyProperties(Page.class, pageVo);
			RecommendActivityTag rat = (RecommendActivityTag) BeanUtil.copyProperties(RecommendActivityTag.class, vo);
			
			page = logic.findProductTagByPage(rat, page);
			List<RecommendActivityTagVo> list = (List<RecommendActivityTagVo>) BeanUtil.copyProperties(RecommendActivityTagVo.class, page.getResultsContent());
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			
			resVo.setPageVo(pageVo);
			resVo.setRecommendvos(list);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(resultVo);
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("商品推荐活动列表查询异常");
			resVo.setResultVo(resultVo);
			resVo.setPageVo(pageVo);
			resVo.setRecommendvos(new ArrayList<RecommendActivityTagVo>());
		}
		return resVo;
	}

	@Override
	public ProductActivityTagDetailResVo findProductTagDetail(ProductActivityTagDetailReqVo reqVo) throws TException {
		ProductActivityTagDetailResVo detailVo = new ProductActivityTagDetailResVo();
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = logic.findProductTagDetail(reqVo.getActivityId(), reqVo.getClientId(), reqVo.getActivityNo(), reqVo.getOperator());
			
			RecommendActivityTagVo recommendVo = (RecommendActivityTagVo) BeanUtil.copyProperties(RecommendActivityTagVo.class, recommend);
			detailVo.setRecommendVo(recommendVo);
			
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			detailVo.setResultVo(resultVo);
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			detailVo.setResultVo(resultVo);
			detailVo.setRecommendVo(new RecommendActivityTagVo());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("商品推荐活动详情查询异常");
			detailVo.setResultVo(resultVo);
			detailVo.setRecommendVo(new RecommendActivityTagVo());
		}
		return detailVo;
	}

	@Override
	public ProductWeightActivityTagPageVo findRelateProductInfoByPage(ProductWeightActivityTagPageReqVo pageReqVo)
			throws TException {
		ProductWeightActivityTagPageVo pageRes = new ProductWeightActivityTagPageVo();
		ResultVo resultVo = new ResultVo();
		PageVo pageVo = pageReqVo.getPageVo();
		try {
			Page<ProductWeightActivityTag> page = (Page<ProductWeightActivityTag>) BeanUtil.copyProperties(Page.class, pageVo);
			WeightActivityTag weightTag = new WeightActivityTag();
			weightTag.setClientId(pageReqVo.getClientId());
			weightTag.setActivityId(pageReqVo.getActivityId() == 0l ? null : pageReqVo.getActivityId());
			weightTag.setActivityNo(pageReqVo.getActivityNo());
			page = logic.findRelateProductInfoByPage(weightTag, page);
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			List<ProductWeightActivityTagVo> details = (List<ProductWeightActivityTagVo>) BeanUtil.copyProperties(ProductWeightActivityTagVo.class, page.getResultsContent());
			pageRes.setPageVo(pageVo);
			pageRes.setRelateProducts(details);
			
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			pageRes.setResultVo(resultVo);
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			pageRes.setResultVo(resultVo);
			pageRes.setPageVo(pageVo);
			pageRes.setRelateProducts(new ArrayList<ProductWeightActivityTagVo>());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("商品推荐活动详情查询异常");
			pageRes.setResultVo(resultVo);
			pageRes.setPageVo(pageVo);
			pageRes.setRelateProducts(new ArrayList<ProductWeightActivityTagVo>());
		}
		return pageRes;
	}

	@Override
	public ResultVo enableProductRecommendActivityTag(EnableProductActivityReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = new RecommendActivityTag();
			recommend.setId(reqVo.getId());
			recommend.setClientId(reqVo.getClientId());
			recommend.setStatus(reqVo.getStatus());
			recommend.setOperator(reqVo.getOperator());
			
			Boolean isSuccess = logic.enableProductRecommendActivityTag(recommend);
			if(isSuccess){
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}else{
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("启用/禁用商品推荐活动标签异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo adjustProductWeight(AdjustProductWeightReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			WeightActivityTag weightTag = new WeightActivityTag();
			weightTag.setId(reqVo.getId());
			weightTag.setClientId(reqVo.getClientId());
			weightTag.setWeight(reqVo.getWeight());
			weightTag.setOperator(reqVo.getOperator());
			
			Boolean isSuccess = logic.adjustProductWeight(weightTag);
			if(isSuccess){
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}else{
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("调整关联商品权重异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo deleteRelateProduct(DeleteRelateProductReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			WeightActivityTag weightTag = new WeightActivityTag();
			weightTag.setId(reqVo.getId());
			weightTag.setClientId(reqVo.getClientId());
			weightTag.setActivityNo(reqVo.getActivityNo());
			weightTag.setOperator(reqVo.getOperator());
			Boolean isSuccess = logic.deleteRelateProduct(weightTag);
			if(isSuccess){
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}else{
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("删除关联商品异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo addProductActivityTag(RecommendActivityTagVo recommendVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = (RecommendActivityTag) BeanUtil.copyProperties(RecommendActivityTag.class, recommendVo);
			
			Boolean isSuccess = logic.addProductActivityTag(recommend);
			
			if(isSuccess){
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}else{
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("添加商品推荐活动异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo updateProductActivityTag(RecommendActivityTagVo recommendVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = (RecommendActivityTag) BeanUtil.copyProperties(RecommendActivityTag.class, recommendVo);
			Boolean isSuccess = logic.updateProductActivityTag(recommend);
			if(isSuccess){
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}else{
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("修改商品推荐活动异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo relateProductInfo(RelateProductActivityVo vo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RelateProductActivityPo po = (RelateProductActivityPo) BeanUtil.copyProperties(RelateProductActivityPo.class, vo);
			Boolean isSuccess = logic.relateProductInfo(po);
			if(isSuccess){
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}else{
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("关联商品异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo inputRelateProductInfo(InputRelateProductActivityReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			List<InputRelateProductActivityPo> infos = (List<InputRelateProductActivityPo>) BeanUtil.copyProperties(InputRelateProductActivityPo.class, reqVo.getVos());
			
			Boolean isSuccess = logic.inputrelateProductInfo(infos, reqVo.getClientId(), reqVo.getActivityId(), reqVo.getActivityNo(), reqVo.getOperator());
			if(isSuccess){
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}else{
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("导入关联商品信息异常");
		}
		return resultVo;
	}

	@Override
	public ProductNameResVo queryProductNameByProductId(String clientId, String productId) throws TException {
		ProductNameResVo resVo = new ProductNameResVo();
		ResultVo resultVo = new ResultVo();
		try {
			String productName = logic.queryProductNameByProductId(clientId, productId);
			resVo.setProductName(productName);
			
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(resultVo);
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			resVo.setResultVo(resultVo);
			resVo.setProductName("");
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("根据商品id查询商品名称异常");
			resVo.setResultVo(resultVo);
			resVo.setProductName("");
		}
		return resVo;
	}

	@Override
	public ExportResultRes exportProductRelateActivityTag(ExportRelateActivityTagReqVo reqVo) throws TException {
		ExportResultRes resVo = new ExportResultRes();
		ResultVo resultVo = null;
		String url = "";
		try {
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("商品名称");
			header.add("商品ID");
			header.add("权重");
			header.add("活动编号");
			
			List<WeightActivityTag> list = logic.findAllRelateProducts(reqVo.getActivityId(), reqVo.getClientId(), reqVo.getActivityNo());
			List<List<String>> allData = convertExportRelateActivityInfo(list);
			
			ExcelWriter excelWriter = new ExcelWriter(allData.size() == 0 ? 1 : allData.size());
			excelWriter.write(header, allData, "商品推荐活动关联商品明细_"+reqVo.getActivityNo(), "商品推荐活动关联商品明细_"+reqVo.getActivityNo());
			url = excelWriter.getUrl();
			
			if(StringUtils.isNotEmpty(url)) {
				resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
			}else{
				resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			}
			resVo.setResultVo(resultVo);
			resVo.setUrl(url);
		} catch (Exception e) {
			LogCvt.error("导出关联商品标签信息异常", e);
			resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			resVo.setResultVo(resultVo);
			resVo.setUrl(url);
		}
		return resVo;
	}
	
	private List<List<String>> convertExportRelateActivityInfo(List<WeightActivityTag> list) {
		List<List<String>> perList = new ArrayList<List<String>>();
		if(Checker.isEmpty(list)) {
			return perList;
		}
		List<String> rowList = null;
		WeightActivityTag tag = null;
		for(int i = 0; i < list.size(); i++){
			tag = list.get(i);
			rowList = new ArrayList<String>();
			rowList.add(String.valueOf(i+1));
			rowList.add(tag.getElementName());
			rowList.add(tag.getElementId());
			rowList.add(tag.getWeight());
			rowList.add(tag.getActivityNo());
			perList.add(rowList);
		}
		return perList;
	}
	
}
