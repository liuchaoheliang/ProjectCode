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
import com.froad.logic.MerchantActivityTagLogic;
import com.froad.logic.impl.MerchantActivityTagLogicImpl;
import com.froad.po.InputRelateMerchantActivityPo;
import com.froad.po.MerchantWeightActivityTag;
import com.froad.po.RecommendActivityTag;
import com.froad.po.RelateMerchantActivityPo;
import com.froad.po.WeightActivityTag;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MerchantActivityTagService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.recommendactivitytag.AdjustMerchantWeightReqVo;
import com.froad.thrift.vo.recommendactivitytag.DeleteRelateMerchantReqVo;
import com.froad.thrift.vo.recommendactivitytag.EnableMerchantActivityReqVo;
import com.froad.thrift.vo.recommendactivitytag.ExportRelateActivityTagReqVo;
import com.froad.thrift.vo.recommendactivitytag.InputRelateMerchantActivityReqVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantActivityTagDetailReqVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantActivityTagDetailResVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantNameResVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantWeightActivityTagPageReqVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantWeightActivityTagPageVo;
import com.froad.thrift.vo.recommendactivitytag.MerchantWeightActivityTagVo;
import com.froad.thrift.vo.recommendactivitytag.RecommendActivityTagPageVo;
import com.froad.thrift.vo.recommendactivitytag.RecommendActivityTagVo;
import com.froad.thrift.vo.recommendactivitytag.RelateMerchantActivityVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;

public class MerchantActivityTagServiceImpl extends BizMonitorBaseService implements MerchantActivityTagService.Iface {
	
	private MerchantActivityTagLogic logic = new MerchantActivityTagLogicImpl();
	
	public MerchantActivityTagServiceImpl(String name, String version){
		super(name, version);
	}

	@Override
	public RecommendActivityTagPageVo findMerchantTagByPage(
			RecommendActivityTagVo vo, PageVo pageVo) throws TException {
		RecommendActivityTagPageVo resVo = new RecommendActivityTagPageVo();
		ResultVo resultVo = new ResultVo();
		try {
			Page<RecommendActivityTag> page = (Page<RecommendActivityTag>) BeanUtil.copyProperties(Page.class, pageVo);
			RecommendActivityTag rat = (RecommendActivityTag) BeanUtil.copyProperties(RecommendActivityTag.class, vo);
			
			page = logic.findMerchantTagByPage(rat, page);
			List<RecommendActivityTagVo> list = (List<RecommendActivityTagVo>) BeanUtil.copyProperties(RecommendActivityTagVo.class, page.getResultsContent());
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			
			resVo.setPageVo(pageVo);
			resVo.setRecommendvos(list);
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(resultVo);
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("商户推荐活动列表查询异常");
			resVo.setResultVo(resultVo);
			resVo.setPageVo(pageVo);
			resVo.setRecommendvos(new ArrayList<RecommendActivityTagVo>());
		}
		return resVo;
	}

	@Override
	public MerchantActivityTagDetailResVo findMerchantTagDetail(MerchantActivityTagDetailReqVo reqVo) throws TException {
		MerchantActivityTagDetailResVo detailVo = new MerchantActivityTagDetailResVo();
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = logic.findMerchantTagDetail(reqVo.getActivityId(), reqVo.getClientId(), reqVo.getActivityNo(), reqVo.getOperator());
			
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
			resultVo.setResultDesc("商户推荐活动详情查询异常");
			detailVo.setResultVo(resultVo);
			detailVo.setRecommendVo(new RecommendActivityTagVo());
		}
		return detailVo;
	}
	
	
	@Override
	public MerchantWeightActivityTagPageVo findRelateMerchantInfoByPage(MerchantWeightActivityTagPageReqVo pageReqVo)
			throws TException {
		MerchantWeightActivityTagPageVo pageRes = new MerchantWeightActivityTagPageVo();
		ResultVo resultVo = new ResultVo();
		PageVo pageVo = pageReqVo.getPageVo();
		try {
			Page<MerchantWeightActivityTag> page = (Page<MerchantWeightActivityTag>) BeanUtil.copyProperties(Page.class, pageVo);
			WeightActivityTag weightTag = new WeightActivityTag();
			weightTag.setClientId(pageReqVo.getClientId());
			weightTag.setActivityId(pageReqVo.getActivityId() == 0l ? null : pageReqVo.getActivityId());
			weightTag.setActivityNo(pageReqVo.getActivityNo());
			page = logic.findRelateMerchantInfoByPage(weightTag, page);
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			List<MerchantWeightActivityTagVo> details = (List<MerchantWeightActivityTagVo>) BeanUtil.copyProperties(MerchantWeightActivityTagVo.class, page.getResultsContent());
			pageRes.setPageVo(pageVo);
			pageRes.setRelateMerchants(details);
			
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			pageRes.setResultVo(resultVo);
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			pageRes.setResultVo(resultVo);
			pageRes.setPageVo(pageVo);
			pageRes.setRelateMerchants(new ArrayList<MerchantWeightActivityTagVo>());
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("商户推荐活动详情查询异常");
			pageRes.setResultVo(resultVo);
			pageRes.setPageVo(pageVo);
			pageRes.setRelateMerchants(new ArrayList<MerchantWeightActivityTagVo>());
		}
		return pageRes;
	}

	
	@Override
	public ResultVo enableMerchantRecommendActivityTag(EnableMerchantActivityReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = new RecommendActivityTag();
			recommend.setId(reqVo.getId());
			recommend.setClientId(reqVo.getClientId());
			recommend.setStatus(reqVo.getStatus());
			recommend.setOperator(reqVo.getOperator());
			
			Boolean isSuccess = logic.enableMerchantRecommendActivityTag(recommend);
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
			resultVo.setResultDesc("启用/禁用商户推荐活动标签异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo adjustMerchantWeight(AdjustMerchantWeightReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			WeightActivityTag weightTag = new WeightActivityTag();
			weightTag.setId(reqVo.getId());
			weightTag.setClientId(reqVo.getClientId());
			weightTag.setWeight(reqVo.getWeight());
			weightTag.setOperator(reqVo.getOperator());
			
			Boolean isSuccess = logic.adjustMerchantWeight(weightTag);
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
			resultVo.setResultDesc("调整关联商户权重异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo deleteRelateMerchant(DeleteRelateMerchantReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			WeightActivityTag weightTag = new WeightActivityTag();
			weightTag.setId(reqVo.getId());
			weightTag.setClientId(reqVo.getClientId());
			weightTag.setActivityNo(reqVo.getActivityNo());
			weightTag.setOperator(reqVo.getOperator());
			Boolean isSuccess = logic.deleteRelateMerchant(weightTag);
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
			resultVo.setResultDesc("删除关联商户异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo addMerchantActivityTag(RecommendActivityTagVo recommendVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = (RecommendActivityTag) BeanUtil.copyProperties(RecommendActivityTag.class, recommendVo);
			
			Boolean isSuccess = logic.addMerchantActivityTag(recommend);
			
			if(isSuccess){
				resultVo.setResultCode(ResultCode.success.getCode());
				resultVo.setResultDesc(ResultCode.success.getMsg());
			}else{
				resultVo.setResultCode(ResultCode.failed.getCode());
				resultVo.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("添加商户推荐活动异常");
		}
		return resultVo;
	}

	
	@Override
	public ResultVo updateMerchantActivityTag(RecommendActivityTagVo recommendVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RecommendActivityTag recommend = (RecommendActivityTag) BeanUtil.copyProperties(RecommendActivityTag.class, recommendVo);
			Boolean isSuccess = logic.updateMerchantActivityTag(recommend);
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
			resultVo.setResultDesc("修改商户推荐活动异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo relateMerchantInfo(RelateMerchantActivityVo vo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			RelateMerchantActivityPo po = (RelateMerchantActivityPo) BeanUtil.copyProperties(RelateMerchantActivityPo.class, vo);
			Boolean isSuccess = logic.relateMerchantInfo(po);
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
			resultVo.setResultDesc("关联商户异常");
		}
		return resultVo;
	}

	@Override
	public ResultVo inputRelateMerchantInfo(InputRelateMerchantActivityReqVo reqVo) throws TException {
		ResultVo resultVo = new ResultVo();
		try {
			List<InputRelateMerchantActivityPo> infos = (List<InputRelateMerchantActivityPo>) BeanUtil.copyProperties(InputRelateMerchantActivityPo.class, reqVo.getVos());
			
			Boolean isSuccess = logic.inputrelateMerchantInfo(infos, reqVo.getClientId(), reqVo.getActivityId(), reqVo.getActivityNo(), reqVo.getOperator());
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
			resultVo.setResultDesc("导入关联商户信息异常");
		}
		return resultVo;
	}

	
	@Override
	public MerchantNameResVo queryMerchantNameByLicense(String clientId, String license) throws TException {
		MerchantNameResVo resVo = new MerchantNameResVo();
		ResultVo resultVo = new ResultVo();
		try {
			String merchantName = logic.queryMerchantNameByLicense(clientId, license);
			resVo.setMerchantName(merchantName);
			
			resultVo.setResultCode(ResultCode.success.getCode());
			resultVo.setResultDesc(ResultCode.success.getMsg());
			resVo.setResultVo(resultVo);
		} catch (FroadBusinessException e) {
			resultVo.setResultCode(e.getCode());
			resultVo.setResultDesc(e.getMsg());
			resVo.setResultVo(resultVo);
			resVo.setMerchantName("");
		} catch (Exception e) {
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("根据营业执照查询商户名称异常");
			resVo.setResultVo(resultVo);
			resVo.setMerchantName("");
		}
		return resVo;
	}

	@Override
	public ExportResultRes exportMerchantRelateActivityTag(ExportRelateActivityTagReqVo reqVo) throws TException {
		ExportResultRes resVo = new ExportResultRes();
		ResultVo resultVo = null;
		String url = "";
		try {
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("商户名称");
			header.add("商户ID");
			header.add("权重");
			header.add("活动编号");
			
			List<WeightActivityTag> list = logic.findAllRelateMerchants(reqVo.getActivityId(), reqVo.getClientId(), reqVo.getActivityNo());
			List<List<String>> allData = convertExportRelateActivityInfo(list);
			
			ExcelWriter excelWriter = new ExcelWriter(allData.size() == 0 ? 1 : allData.size());
			excelWriter.write(header, allData, "商户推荐活动关联商户明细_"+reqVo.getActivityNo(), "商户推荐活动关联商户明细_"+reqVo.getActivityNo());
			url = excelWriter.getUrl();
			
			if(StringUtils.isNotEmpty(url)) {
				resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
			}else{
				resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			}
			resVo.setResultVo(resultVo);
			resVo.setUrl(url);
		} catch (Exception e) {
			LogCvt.error("导出关联商户标签信息异常", e);
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
