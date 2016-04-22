/**
 * Project Name:froad-cbank-server-boss
 * File Name:OutletActivityServiceImpl.java
 * Package Name:com.froad.thrift.service.impl
 * Date:2015年10月26日上午11:46:42
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
import com.froad.logic.OutletActivityLogic;
import com.froad.logic.impl.OutletActivityLogicImpl;
import com.froad.po.InputRelateOutletActivityPo;
import com.froad.po.OutletWeightActivityTag;
import com.froad.po.RecommendActivityTag;
import com.froad.po.RelateOutletActivityPo;
import com.froad.po.WeightActivityTag;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.OutletActivityService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.outletActivity.AdjustOutletWeightReqVo;
import com.froad.thrift.vo.outletActivity.DeleteRelateOutletReqVo;
import com.froad.thrift.vo.outletActivity.EnableOutletActivityReqVo;
import com.froad.thrift.vo.outletActivity.ExportRelateActivityTagReqVo;
import com.froad.thrift.vo.outletActivity.InputRelateOutletActivityReqVo;
import com.froad.thrift.vo.outletActivity.OutletActivityTagDetailReqVo;
import com.froad.thrift.vo.outletActivity.OutletActivityTagDetailResVo;
import com.froad.thrift.vo.outletActivity.OutletNameAndMerchantNameResVo;
import com.froad.thrift.vo.outletActivity.OutletWeightActivityTagPageReqVo;
import com.froad.thrift.vo.outletActivity.OutletWeightActivityTagPageVo;
import com.froad.thrift.vo.outletActivity.OutletWeightActivityTagVo;
import com.froad.thrift.vo.outletActivity.RecommendActivityTagVo;
import com.froad.thrift.vo.outletActivity.RecommendActivityTagPageVo;
import com.froad.thrift.vo.outletActivity.RelateOutletActivityVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;


/**
 * ClassName:OutletActivityServiceImpl
 * Reason:	门店推荐活动标签管理
 * Date:     2015年10月26日 上午11:46:42
 * @author   liuyanyun
 * @version  
 * @see 	 
 */
public class OutletActivityServiceImpl extends BizMonitorBaseService implements OutletActivityService.Iface{

	private OutletActivityLogic logic = new OutletActivityLogicImpl();
	
	public OutletActivityServiceImpl(String name, String version){
		super(name, version);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RecommendActivityTagPageVo findOutletTagByPage(RecommendActivityTagVo vo, PageVo pageVo) throws TException {
		RecommendActivityTagPageVo resVo = new RecommendActivityTagPageVo();
		ResultVo resultVo = new ResultVo();
		try {
			Page<RecommendActivityTag> page = (Page<RecommendActivityTag>) BeanUtil.copyProperties(Page.class, pageVo);
			RecommendActivityTag rat = (RecommendActivityTag) BeanUtil.copyProperties(RecommendActivityTag.class, vo);
			
			page = logic.findOutletTagByPage(rat, page);
			List<RecommendActivityTagVo> list = (List<RecommendActivityTagVo>) BeanUtil.copyProperties(RecommendActivityTagVo.class, page.getResultsContent());
			if (Checker.isNotEmpty(page)) {
				pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			}
			
			resVo.setPageVo(pageVo);
			resVo.setRecommendvos(list);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(resultVo);
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("门店推荐活动列表查询异常");
			resVo.setResultVo(resultVo);
			resVo.setPageVo(pageVo);
			resVo.setRecommendvos(new ArrayList<RecommendActivityTagVo>());
		}
		return resVo;
	}

	@Override
	public OutletActivityTagDetailResVo findOutletTagDetail(OutletActivityTagDetailReqVo reqVo) throws TException {
		OutletActivityTagDetailResVo detailVo = new OutletActivityTagDetailResVo();
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = logic.findOutletTagDetail(reqVo.getActivityId(), reqVo.getClientId(), reqVo.getActivityNo(), reqVo.getOperator());
			
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
			resultVo.setResultDesc("门店推荐活动详情查询异常");
			detailVo.setResultVo(resultVo);
			detailVo.setRecommendVo(new RecommendActivityTagVo());
		}
		return detailVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public OutletWeightActivityTagPageVo findRelateOutletInfoByPage(OutletWeightActivityTagPageReqVo pageReqVo)
			throws TException {
		OutletWeightActivityTagPageVo pageRes = new OutletWeightActivityTagPageVo();
		ResultVo resultVo = new ResultVo();
		PageVo pageVo = pageReqVo.getPageVo();
		try {
			Page<OutletWeightActivityTag> page = (Page<OutletWeightActivityTag>) BeanUtil.copyProperties(Page.class, pageVo);
			WeightActivityTag weightTag = new WeightActivityTag();
			weightTag.setClientId(pageReqVo.getClientId());
			weightTag.setActivityId(pageReqVo.getActivityId() == 0l ? null : pageReqVo.getActivityId());
			weightTag.setActivityNo(pageReqVo.getActivityNo());
			
			page = logic.findRelateOutletInfoByPage(weightTag, page);
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			List<OutletWeightActivityTagVo> details = (List<OutletWeightActivityTagVo>) BeanUtil.copyProperties(OutletWeightActivityTagVo.class, page.getResultsContent());
			
			pageRes.setPageVo(pageVo);
			pageRes.setRelateOutlets(details);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			pageRes.setResultVo(resultVo);
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			pageRes.setResultVo(resultVo);
			pageRes.setPageVo(pageVo);
			pageRes.setRelateOutlets(new ArrayList<OutletWeightActivityTagVo>());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("门店推荐活动详情查询异常");
			pageRes.setResultVo(resultVo);
			pageRes.setPageVo(pageVo);
			pageRes.setRelateOutlets(new ArrayList<OutletWeightActivityTagVo>());
		}
		return pageRes;
	}

	@Override
	public ResultVo enableOutletRecommendActivityTag(EnableOutletActivityReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = new RecommendActivityTag();
			recommend.setId(reqVo.getId());
			recommend.setClientId(reqVo.getClientId());
			recommend.setStatus(reqVo.getStatus());
			recommend.setOperator(reqVo.getOperator());
			
			Boolean isSuccess = logic.enableOutletRecommendActivityTag(recommend);
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
			resultVo.setResultDesc("启用/禁用门店推荐活动标签异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo adjustOutletWeight(AdjustOutletWeightReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			WeightActivityTag weightTag = new WeightActivityTag();
			weightTag.setId(reqVo.getId());
			weightTag.setClientId(reqVo.getClientId());
			weightTag.setWeight(reqVo.getWeight());
			weightTag.setOperator(reqVo.getOperator());
			
			Boolean isSuccess = logic.adjustOutletWeight(weightTag);
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
			resultVo.setResultDesc("调整关联门店权重异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo deleteRelateOutlet(DeleteRelateOutletReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			WeightActivityTag weightTag = new WeightActivityTag();
			weightTag.setId(reqVo.getId());
			weightTag.setClientId(reqVo.getClientId());
			weightTag.setActivityNo(reqVo.getActivityNo());
			weightTag.setOperator(reqVo.getOperator());
			Boolean isSuccess = logic.deleteRelateOutlet(weightTag);
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
			resultVo.setResultDesc("删除关联门店异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo addOutletActivityTag(RecommendActivityTagVo recommendVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = (RecommendActivityTag) BeanUtil.copyProperties(RecommendActivityTag.class, recommendVo);
			
			Boolean isSuccess = logic.addOutletActivityTag(recommend);
			
			if(isSuccess){
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}else{
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("添加门店推荐活动异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo updateOutletActivityTag(RecommendActivityTagVo recommendVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = (RecommendActivityTag) BeanUtil.copyProperties(RecommendActivityTag.class, recommendVo);
			Boolean isSuccess = logic.updateOutletActivityTag(recommend);
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
			resultVo.setResultDesc("修改门店推荐活动异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo relateOutletInfo(RelateOutletActivityVo vo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RelateOutletActivityPo po = (RelateOutletActivityPo) BeanUtil.copyProperties(RelateOutletActivityPo.class, vo);
			Boolean isSuccess = logic.relateOutletInfo(po);
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
			resultVo.setResultDesc("关联商户门店异常");
		}
		return resultVo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultVo inputRelateOutletInfo(InputRelateOutletActivityReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			List<InputRelateOutletActivityPo> infos = (List<InputRelateOutletActivityPo>) BeanUtil.copyProperties(InputRelateOutletActivityPo.class, reqVo.getVos());
			
			Boolean isSuccess = logic.inputrelateOutletInfo(infos, reqVo.getClientId(), reqVo.getActivityId(), reqVo.getActivityNo(), reqVo.getOperator());
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
			resultVo.setResultDesc("导入关联门店信息异常");
		}
		return resultVo;
	}

	@Override
	public OutletNameAndMerchantNameResVo queryOutletNameAndMerchantNameByOutletId(String clientId, String outletId)
			throws TException {
		OutletNameAndMerchantNameResVo resVo = new OutletNameAndMerchantNameResVo();
		ResultVo resultVo = new ResultVo();
		try {
			String name = logic.queryOutletNameAndMerchantNameByOutletId(clientId, outletId);
			String[] names = name.split(",");
			
			resVo.setOutletName(names[0]);
			resVo.setMerchantName(names[1]);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(resultVo);
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			resVo.setResultVo(resultVo);
			resVo.setOutletName("");
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("查询门店名称和商户名称信息异常");
			resVo.setResultVo(resultVo);
			resVo.setOutletName("");
		}
		return resVo;
	}

	@Override
	public ExportResultRes exportOutletRelateActivityTag(ExportRelateActivityTagReqVo reqVo) throws TException {
		ExportResultRes resVo = new ExportResultRes();
		ResultVo resultVo = null;
		String url = "";
		try {
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("门店名称");
			header.add("门店ID");
			header.add("权重");
			header.add("活动编号");
			
			List<WeightActivityTag> list = logic.findAllRelateOutlets(reqVo.getActivityId(), reqVo.getClientId(), reqVo.getActivityNo());
			List<List<String>> allData = convertExportRelateActivityInfo(list);
			
			ExcelWriter excelWriter = new ExcelWriter(allData.size() == 0 ? 1 : allData.size());
			excelWriter.write(header, allData, "门店推荐活动关联门店明细_"+reqVo.getActivityNo(), "门店推荐活动关联门店明细_"+reqVo.getActivityNo());
			url = excelWriter.getUrl();
			
			if(StringUtils.isNotEmpty(url)) {
				resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
			}else{
				resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			}
			resVo.setResultVo(resultVo);
			resVo.setUrl(url);
		} catch (Exception e) {
			LogCvt.error("导出关联门店标签信息异常", e);
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
