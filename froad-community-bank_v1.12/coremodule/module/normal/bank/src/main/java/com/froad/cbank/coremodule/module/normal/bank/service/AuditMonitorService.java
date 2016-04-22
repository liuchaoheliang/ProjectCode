/**
 * Project Name:coremodule-bank
 * File Name:AuditMonitorService.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.service
 * Date:2015-8-18上午10:52:17
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.bank.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.BessDataEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProcessTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.BessDataUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditProcessRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditTaskReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditTaskRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantAuditDetailRes;
import com.froad.thrift.service.AuditProcessService;
import com.froad.thrift.service.AuditTaskService;
import com.froad.thrift.service.FallowQueryService;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.MerchantAuditService;
import com.froad.thrift.service.MerchantCategoryService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.MerchantTypeService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.vo.AuditInstanceDetailVo;
import com.froad.thrift.vo.AuditProcessVo;
import com.froad.thrift.vo.AuditTaskFilterVo;
import com.froad.thrift.vo.AuditTaskPageDetailVo;
import com.froad.thrift.vo.AuditTaskPageVoRes;
import com.froad.thrift.vo.AuditTaskVo;
import com.froad.thrift.vo.GetAlreadyAuitListPageVo;
import com.froad.thrift.vo.GetAlreadyAuitListReqVo;
import com.froad.thrift.vo.GetApplyAuitListPageVo;
import com.froad.thrift.vo.GetApplyAuitListReqVo;
import com.froad.thrift.vo.MerchantTempVo;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.Restrictions;

import net.sf.json.JSONObject;

/**

 * @author   wufei
 * @version  
 * @see 	 
 */
@Service
public class AuditMonitorService {

	@Resource
	AuditTaskService.Iface auditTaskService;
	@Resource
	MerchantAuditService.Iface merchantAuditService;
	@Resource
	MerchantUserService.Iface merchantUserService;
	@Resource
	AuditProcessService.Iface auditProcessService;
	@Resource
	MerchantAccountService.Iface merchantAccountService;
	@Resource
	MerchantCategoryService.Iface merchantCategoryService;
	@Resource
	MerchantTypeService.Iface merchantTypeService;
	@Resource
	MerchantService.Iface merchantService;
	@Resource
	OrgService.Iface orgService;
	@Resource
	FallowQueryService.Iface fallowQueryService;
	
	/**
	 * 
	 * auditMonitorList:(审核监控列表查询).
	 *

	 * @return
	 * @throws TException 
	 * @throws ParseException 
	 *
	 */
	/**
	 * @param auditTaskReq
	 * @param originVo
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	public HashMap<String,Object> auditMonitorList(AuditTaskReq auditTaskReq,OriginVo originVo) throws TException, ParseException{
		HashMap<String,Object> resMap = new HashMap<String,Object>();
		
		//封装分页对象
		PageVo page = setPageReq(auditTaskReq);
		
		//封装请求实体
		AuditTaskFilterVo auditTaskFilterVo = new AuditTaskFilterVo();
		if(StringUtil.isNotEmpty(auditTaskReq.getAuditId())){
			auditTaskFilterVo.setAuditId(auditTaskReq.getAuditId());  //审核流水号
		}
		if(StringUtil.isNotEmpty(auditTaskReq.getBusinessType())){
			auditTaskFilterVo.setType(auditTaskReq.getBusinessType()); //业务类型
		}
		if(StringUtil.isNotEmpty(auditTaskReq.getStartTime())){

		}
		if(StringUtil.isNotEmpty(auditTaskReq.getEndTime())){

		}
		if(StringUtil.isNotEmpty(auditTaskReq.getName()) ){
			auditTaskFilterVo.setName(auditTaskReq.getName());	//名称
		}
		if(StringUtil.isNotEmpty(auditTaskReq.getBusinessNo())){
			auditTaskFilterVo.setBusCode(auditTaskReq.getBusinessNo());	//业务号码
		}
		auditTaskFilterVo.setClientId(auditTaskReq.getClientId());
		auditTaskFilterVo.setOrgCode(auditTaskReq.getOrgCode());
		
		
		List<AuditTaskRes> auditTaskResList = new ArrayList<AuditTaskRes>();
		LogCvt.info("审核监控查询参数："+JSON.toJSONString(auditTaskFilterVo));
		AuditTaskPageVoRes pageVoRes = auditTaskService.getAuditTaskByPage(
				page, auditTaskFilterVo, 1); // flag 查询权限标志 1-查当前机构
												// 2-查当前机构所有下属机构
		LogCvt.info("auditTaskService.getAuditTaskByPage审核监控列表查询返回信息:"
				+ JSON.toJSONString(pageVoRes));
		List<AuditTaskPageDetailVo> auditTaskPageDetailVo = pageVoRes.getAuditTaskVoList();
		if(auditTaskPageDetailVo != null && auditTaskPageDetailVo.size()>0){
			for(AuditTaskPageDetailVo auditTaskVo : auditTaskPageDetailVo){
				AuditTaskRes auditTaskRes = new AuditTaskRes();
				auditTaskRes.setAuditId(auditTaskVo.getAuditId()); //审核流水号
				auditTaskRes.setBusinessType(auditTaskVo.getType());//业务类型
				auditTaskRes.setName(auditTaskVo.getName());//名称
				if(auditTaskVo.getCreateTime() != 0){
					auditTaskRes.setCreateTime(auditTaskVo.getCreateTime()); //创建时间
				}else{
					auditTaskRes.setCreateTime(null);
				}
				auditTaskRes.setAuditStatus(auditTaskVo.getAuditState()); //审核状态
				MerchantTempVo merchantTempVo = merchantAuditService.getAuditMerchant(originVo, auditTaskVo.getAuditId());
				if(merchantTempVo != null){
					auditTaskRes.setMerchantId(merchantTempVo.getMerchantId()); //商户id   
				}
				MerchantUserVo merchantUserVo = new MerchantUserVo();
				merchantUserVo.setMerchantId(auditTaskRes.getMerchantId());
				merchantUserVo.setMerchantRoleId(Constants.MERCHANT_ROLE_ID);
				merchantUserVo.setClientId(auditTaskReq.getClientId());
				List<MerchantUserVo> merchantUserList = merchantUserService.getMerchantUser(merchantUserVo);

				
				if(merchantUserList.size()>0){
					MerchantUserVo merchantUser = merchantUserList.get(0);
					LogCvt.info("merchantUserId:"+merchantUser.getId());
					if(merchantUser != null){
						//auditTaskRes.setMerchantUserId(merchantUser.getId());  //商户用户id
					}
				}
				auditTaskResList.add(auditTaskRes);
			}
		}
		resMap.put("merchantTaskVo", auditTaskResList);
		if(pageVoRes.getPage()!= null){
			resMap.put("pageCount", pageVoRes.getPage().getPageCount());
			resMap.put("totalCount", pageVoRes.getPage().getTotalCount());
			resMap.put("pageNumber", pageVoRes.getPage().getPageNumber());
			resMap.put("lastPageNumber", pageVoRes.getPage().getLastPageNumber());
			resMap.put("firstRecordTime", pageVoRes.getPage().getFirstRecordTime());
			resMap.put("lastRecordTime", pageVoRes.getPage().getLastRecordTime());
		}else{
			resMap.put("pageCount", 0);
			resMap.put("totalCount", 0);
			resMap.put("pageNumber", 1);
			resMap.put("lastPageNumber", 0);
			resMap.put("firstRecordTime", 0);
			resMap.put("lastRecordTime", 0);
		}
		return resMap;
	}

	private PageVo setPageReq(AuditTaskReq auditTaskReq) {
		PageVo page = new PageVo();
		page.setPageNumber(auditTaskReq.getPageNumber() == null ? 1 : auditTaskReq.getPageNumber());
		page.setPageSize(auditTaskReq.getPageSize() == null ? 10 : auditTaskReq.getPageSize());
		if (auditTaskReq.getLastPageNumber() != null) {
			page.setLastPageNumber(auditTaskReq.getLastPageNumber());
		}
		if (auditTaskReq.getFirstRecordTime() != null) {
			page.setFirstRecordTime(auditTaskReq.getFirstRecordTime());
		}
		if (auditTaskReq.getLastRecordTime() != null) {
			page.setLastRecordTime(auditTaskReq.getLastRecordTime());
		}
		return page;
	}
	
	/**
	 * 审核监控调用审核引擎新接口
	 * @param auditTaskReq
	 * @param originVo
	 * @return
	 * @throws TException
	 * @throws ParseException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public HashMap<String,Object> auditMonitorListNew(AuditTaskReq auditTaskReq,OriginVo originVo) throws TException, ParseException, JsonGenerationException, JsonMappingException, IOException{
		LogCvt.info("审核监控请求参数："+JSON.toJSONString(auditTaskReq)+"========originVo"+JSON.toJSONString(originVo));
		HashMap<String,Object> resMap = new HashMap<String,Object>();
		PageVo page = setPageReq(auditTaskReq);
		
		GetApplyAuitListReqVo applyAuitListReqVo = new GetApplyAuitListReqVo();
		//封装请求实体
		this.setApplyAuitListReqVo(auditTaskReq, originVo, applyAuitListReqVo);
		
		LogCvt.info("审核监控查询参数："+JSON.toJSONString(applyAuitListReqVo));
		GetApplyAuitListPageVo applyAuitListPageVo = fallowQueryService.getApplyAuitList(applyAuitListReqVo,page);
		LogCvt.info("审核监控返回数据："+JSON.toJSONString(applyAuitListPageVo));
		List<AuditTaskRes> auditTaskResList = new ArrayList<AuditTaskRes>();
		if(applyAuitListPageVo.getResult() != null){
			if(EnumTypes.success.getCode().equals(applyAuitListPageVo.getResult().getResultCode())){
				if(applyAuitListPageVo.getAuditDetailList() != null && applyAuitListPageVo.getAuditDetailList().size()>0){
					AuditTaskRes auditTaskRes = null;
					for(AuditInstanceDetailVo applyAuitListResVo : applyAuitListPageVo.getAuditDetailList()){
						auditTaskRes = new AuditTaskRes();
						auditTaskRes.setAuditId(applyAuitListResVo.getInstanceId());    //审核流水号
						if(applyAuitListResVo.getCreateTime() != 0){
							auditTaskRes.setCreateTime(applyAuitListResVo.getCreateTime()); //创建时间
						}else{
							auditTaskRes.setCreateTime(null);
						}
						auditTaskRes.setAuditStatus(applyAuitListResVo.getAuditState());//审核状态
						auditTaskRes.setAuditType(applyAuitListResVo.getProcessTypeDetail());//审核类型
						auditTaskRes.setBusinessType(applyAuitListResVo.getProcessType()); //业务类型
						JSONObject json = JSONObject.fromObject(applyAuitListResVo.getBessData());
						
						if (json.containsKey(
								BessDataEnum.merchantName.getKey())) {
							auditTaskRes.setName(json.getString(
									BessDataEnum.merchantName.getKey())); // 商户名称
						}
						if (json.containsKey(
								BessDataEnum.outletName.getKey())) {
							auditTaskRes.setName(json.getString(
									BessDataEnum.outletName.getKey())); // 门店名称
						}
						if (json.containsKey(
								BessDataEnum.productName.getKey())) {
							auditTaskRes.setName(json.getString(
									BessDataEnum.productName.getKey())); // 商品名称
						}
						if (json.containsKey(
								BessDataEnum.merchantUserId.getKey())) {
							auditTaskRes.setMerchantUserId(json.getString(
									BessDataEnum.merchantUserId.getKey()));// 商户用户id
						}
						if (json.containsKey(
								BessDataEnum.merchantId.getKey())) {
							auditTaskRes.setMerchantId(json.getString(
									BessDataEnum.merchantId.getKey())); // 商户id
						}
						if (json.containsKey(BessDataEnum.outletId.getKey())) {
							auditTaskRes.setOutletId(json
									.getString(BessDataEnum.outletId.getKey())); // 门店id
						}
						if (json.containsKey(BessDataEnum.productId.getKey())) {
							auditTaskRes.setProductId(json.getString(
									BessDataEnum.productId.getKey())); // 商品id
						}
						auditTaskResList.add(auditTaskRes);
					}
					
				}
				PageVo pageVo = applyAuitListPageVo.getPage();
				setPageVoRes(resMap, pageVo);
				
			}else{
				resMap.put("code", EnumTypes.fail.getCode());
				resMap.put("message", applyAuitListPageVo.getResult().getResultDesc());
			}
		}
		resMap.put("merchantTaskVo", auditTaskResList);
		return resMap;
	}

	/**
	 * 
	 * setApplyAuitListReqVo:(封装审核监控请求参数).
	 *
	 * @author wufei
	 * 2015-10-26 下午05:51:35
	 * @param auditTaskReq
	 * @param originVo
	 * @param applyAuitListReqVo
	 * @throws ParseException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 *
	 */
	private void setApplyAuitListReqVo(AuditTaskReq auditTaskReq, OriginVo originVo,
			GetApplyAuitListReqVo applyAuitListReqVo) throws ParseException, JsonGenerationException,
			JsonMappingException, IOException {
		//originVo.setOrgId(auditTaskReq.getMyOrgCode());
		//originVo.setOperatorUserName(auditTaskReq.getOperator());
		applyAuitListReqVo.setOrigin(originVo);
		if(StringUtil.isNotBlank(auditTaskReq.getAuditId())){
			applyAuitListReqVo.setInstanceId(auditTaskReq.getAuditId()); //审核流水号
		}
		
		if(StringUtil.isNotBlank(auditTaskReq.getStartTime())){
			applyAuitListReqVo.setStarTime(DateUtil.DateFormat(auditTaskReq.getStartTime()));//创建时间
		}
		if(StringUtil.isNotBlank(auditTaskReq.getEndTime())){
			applyAuitListReqVo.setEndTime(DateUtil.DateFormat(auditTaskReq.getEndTime()));
		}
		if(StringUtil.isNotBlank(auditTaskReq.getBusinessType())){
			applyAuitListReqVo.setProcessType(auditTaskReq.getBusinessType()); //业务类型：1、商户  2、门店  3、团购商品  4、预售商品
		}
		Map<Restrictions, String> orBessData = new HashMap<Restrictions, String>();
		// Map<Restrictions, String> andBessData = new HashMap<Restrictions,
		// String>();
		if(StringUtil.isNotBlank(auditTaskReq.getName())){ //名称
			if(auditTaskReq.getBusinessType().equals(ProcessTypeEnum.MERCHANT.getCode())){
				orBessData = BessDataUtil
						.getOrBessDataMapWithMerchantNameAndOutName(
								auditTaskReq.getName(), null, null, null,
								orBessData);
			}
			if(auditTaskReq.getBusinessType().equals(ProcessTypeEnum.OUTLET.getCode())){
				orBessData = BessDataUtil
						.getOrBessDataMapWithMerchantNameAndOutName(null,
								auditTaskReq.getName(), null, null, orBessData);
			}
			if (auditTaskReq.getBusinessType()
					.equals(ProcessTypeEnum.GROUPPRODUCT.getCode())) {
				orBessData = BessDataUtil
						.getOrBessDataMapWithMerchantNameAndOutName(null, null,
								auditTaskReq.getName(), null, orBessData);
			}
		}
		//根据业务筛选，如业务为商户则精确匹配营业执照号，最多可输入64个字符，如门店则精确匹配门店编号 outletId
		if(StringUtil.isNotBlank(auditTaskReq.getBusinessNo())){
			Map<String, Object> tempMap = new HashMap<String, Object>();
			if(auditTaskReq.getBusinessType().equals(ProcessTypeEnum.MERCHANT.getCode())){
				tempMap.put(BessDataEnum.license.getKey(), auditTaskReq.getBusinessNo());
				orBessData.put(Restrictions.EQ, JSON.toJSONString(tempMap)); // 匹配营业执照
			}
			if(auditTaskReq.getBusinessType().equals(ProcessTypeEnum.OUTLET.getCode())){
				tempMap.put(BessDataEnum.outletId.getKey(), auditTaskReq.getBusinessNo());
				orBessData.put(Restrictions.EQ, JSON.toJSONString(tempMap)); // 匹配门店编号
			}
			
			if (auditTaskReq.getBusinessType()
					.equals(ProcessTypeEnum.GROUPPRODUCT.getCode())) {
				tempMap.put(BessDataEnum.productId.getKey(),
						auditTaskReq.getBusinessNo());
				orBessData.put(Restrictions.EQ, JSON.toJSONString(tempMap)); // 匹配商品id
			}
		}
		if(orBessData.size()>0){
			applyAuitListReqVo.setOrBessData(orBessData);
		}
		// if (andBessData.size()>0) {
		// applyAuitListReqVo.setAndBessData(andBessData);
		// }
	}

	/**
	 * 封装返回分页信息
	 * setPageVoRes:(这里用一句话描述这个方法的作用).
	 *
	 * @author wufei
	 * 2015-10-26 下午05:42:39
	 * @param resMap
	 * @param pageVo
	 *
	 */
	private void setPageVoRes(HashMap<String, Object> resMap, PageVo pageVo) {
		if(pageVo != null){
			resMap.put("pageCount", pageVo.getPageCount());
			resMap.put("totalCount", pageVo.getTotalCount());
			resMap.put("pageNumber", pageVo.getPageNumber());
			resMap.put("lastPageNumber", pageVo.getLastPageNumber());
			resMap.put("firstRecordTime", pageVo.getFirstRecordTime());
			resMap.put("lastRecordTime", pageVo.getLastRecordTime());
		}else{
			resMap.put("pageCount", 0);
			resMap.put("totalCount", 0);
			resMap.put("pageNumber", 1);
			resMap.put("lastPageNumber", 0);
			resMap.put("firstRecordTime", 0);
			resMap.put("lastRecordTime", 0);
		}
	}


	/**
	 * 综合查询调用审核引擎新接口
	 * @param auditTaskReq
	 * @param originVo
	 * @return
	 * @throws TException
	 * @throws ParseException
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public HashMap<String,Object> compositeQueryNew(AuditTaskReq auditTaskReq,OriginVo originVo) throws TException, ParseException, JsonGenerationException, JsonMappingException, IOException{
		LogCvt.info("综合查询请求参数："+JSON.toJSONString(auditTaskReq));
		HashMap<String,Object> resMap = new HashMap<String,Object>();
		PageVo page = setPageReq(auditTaskReq);
		String orgCode = null;
		GetAlreadyAuitListReqVo alreadyAuitListReqVo = new GetAlreadyAuitListReqVo();
		/**
		 * 封装请求参数
		 */
		this.setAlreadyAuitListReqVo(auditTaskReq, originVo, alreadyAuitListReqVo);
		
		LogCvt.info("综合查询查询fallowQueryService.getAlreadyAuitList"+"======查询参数："+JSON.toJSONString(alreadyAuitListReqVo));
		GetAlreadyAuitListPageVo  alreadyAuitListPageVo = fallowQueryService.getAlreadyAuitList(alreadyAuitListReqVo, page);
		LogCvt.info("综合查询返回数据："+JSON.toJSONString(alreadyAuitListPageVo));
		List<AuditTaskRes> auditTaskResList = new ArrayList<AuditTaskRes>();
			if(EnumTypes.success.getCode().equals(alreadyAuitListPageVo.getResult().getResultCode())){
				if(alreadyAuitListPageVo.getAuditDetailList() != null && alreadyAuitListPageVo.getAuditDetailList().size()>0){
					for(AuditInstanceDetailVo alreadyAuitListResVo : alreadyAuitListPageVo.getAuditDetailList()){
						AuditTaskRes auditTaskRes = new AuditTaskRes();
						auditTaskRes.setAuditId(alreadyAuitListResVo.getInstanceId()); //审核流水号
						//归档的审核状态要从auditActor已执行过的审核人信息里面取审核状态
//						if(auditTaskReq.getIsPigeonhole().equals("2")){
//							if(alreadyAuitListResVo.getAuditActor() != null && alreadyAuitListResVo.getAuditActor().size()>1){
//								auditTaskRes.setAuditStatus(alreadyAuitListResVo.getAuditActor().get(alreadyAuitListResVo.getAuditActor().size()-1).getAuditState());
//							}else if(alreadyAuitListResVo.getAuditActor() != null && alreadyAuitListResVo.getAuditActor().size()>0){
//								auditTaskRes.setAuditStatus(alreadyAuitListResVo.getAuditActor().get(0).getAuditState());
//							}
//							
//						}else{
							auditTaskRes.setAuditStatus(alreadyAuitListResVo.getAuditState());//审核状态
//						}
						
						auditTaskRes.setCreateTime(alreadyAuitListResVo.getCreateTime());//创建时间
						/**
						 * 所属机构
						 */
						if (auditTaskReq.getBusinessType().equals(ProcessTypeEnum.OUTLET.getCode())) {
							orgCode = alreadyAuitListResVo.getOrgCode();
						}
						
						auditTaskRes.setPigeonholeTime(alreadyAuitListResVo.getFinishTime()); //归档时间
						auditTaskRes.setAuditType(alreadyAuitListResVo.getProcessTypeDetail());//审核类型
						auditTaskRes.setBusinessType(alreadyAuitListResVo.getProcessType());//业务类型
						JSONObject json = JSONObject.fromObject(alreadyAuitListResVo.getBessData());
						
					if (auditTaskReq.getBusinessType()
							.equals(ProcessTypeEnum.MERCHANT.getCode())) {
						if (json.containsKey(
								BessDataEnum.newOrgCode.getKey())) {
							orgCode = json.getString(
									BessDataEnum.newOrgCode.getKey());
						}
					}
					// 团购商品类型的机构获取
					if (auditTaskReq.getBusinessType()
							.equals(ProcessTypeEnum.GROUPPRODUCT.getCode())) {
						if (json.containsKey(
								BessDataEnum.userOrgCode.getKey())) {
							orgCode = json.getString(
									BessDataEnum.userOrgCode.getKey());
						}
						}
						if (orgCode != null) {
							OrgVo orgVo = orgService.getOrgByIdSuperOrgName(auditTaskReq.getClientId(), orgCode);
							LogCvt.info("创建人所属机构返回数据" + JSON.toJSONString(orgVo));
							auditTaskRes.setOrgName(orgVo.getOrgName()); //所属机构
							auditTaskRes.setParentOrgName(orgVo.getSuperOrgName()); //上级机构
						}
						
					if (json.containsKey(BessDataEnum.merchantName.getKey())) {
						auditTaskRes.setName(json
								.getString(BessDataEnum.merchantName.getKey())); // 商户名称
						}
					if (json.containsKey(BessDataEnum.outletName.getKey())) {
						auditTaskRes.setName(json
								.getString(BessDataEnum.outletName.getKey())); // 门店名称
						}
					if (json.containsKey(BessDataEnum.productName.getKey())) {
						auditTaskRes.setName(json
								.getString(BessDataEnum.productName.getKey())); // 商品名称
					}
					if (json.containsKey(BessDataEnum.merchantName.getKey())) {
						auditTaskRes.setMerchantName(json
								.getString(BessDataEnum.merchantName.getKey())); // 商户名称
					}
					if (json.containsKey(
							BessDataEnum.merchantUserId.getKey())) {
						auditTaskRes.setMerchantUserId(json.getString(
								BessDataEnum.merchantUserId.getKey()));// 商户用户id
						}
					if (json.containsKey(BessDataEnum.merchantId.getKey())) {
						auditTaskRes.setMerchantId(json
								.getString(BessDataEnum.merchantId.getKey())); // 商户id
						}
					if (json.containsKey(BessDataEnum.outletId.getKey())) {
						auditTaskRes.setOutletId(
								json.getString(BessDataEnum.outletId.getKey())); // 门店id
						}
					if (json.containsKey(BessDataEnum.productId.getKey())) {
						auditTaskRes.setProductId(json
								.getString(BessDataEnum.productId.getKey())); // 商品id
					}
						auditTaskResList.add(auditTaskRes);
						
					}
				}
				PageVo pageVo = alreadyAuitListPageVo.getPage();
				setPageVoRes(resMap, pageVo);
				
				
			}else{
			resMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			resMap.put(ResultEnum.MESSAGE.getCode(),
					alreadyAuitListPageVo.getResult().getResultDesc());
		}
		
		resMap.put("merchantTaskVo", auditTaskResList);
		return resMap;
	}

	/**
	 * 
	 * setAlreadyAuitListReqVo:(封装综合查询请求参数).
	 *
	 * @author wufei
	 * 2015-10-26 下午05:52:44
	 * @param auditTaskReq
	 * @param originVo
	 * @param alreadyAuitListReqVo
	 * @throws ParseException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws TException
	 *
	 */
	private void setAlreadyAuitListReqVo(AuditTaskReq auditTaskReq, OriginVo originVo,
			GetAlreadyAuitListReqVo alreadyAuitListReqVo) throws ParseException, JsonGenerationException,
			JsonMappingException, IOException, TException {
		alreadyAuitListReqVo.setOrigin(originVo);
		if(StringUtil.isNotBlank(auditTaskReq.getAuditId())){
			alreadyAuitListReqVo.setInstanceId(auditTaskReq.getAuditId()); //审核流水号
		}
		
		if(StringUtil.isNotBlank(auditTaskReq.getStartTime())){
			alreadyAuitListReqVo.setStarTime(DateUtil.DateFormat(auditTaskReq.getStartTime()));//创建时间
		}
		if(StringUtil.isNotBlank(auditTaskReq.getEndTime())){
			alreadyAuitListReqVo.setEndTime(DateUtil.DateFormat(auditTaskReq.getEndTime()));
		}
		if(StringUtil.isNotBlank(auditTaskReq.getAuditState())){
			alreadyAuitListReqVo.setAuditState(auditTaskReq.getAuditState()); //审核状态
		}
		if(StringUtil.isNotEmpty(auditTaskReq.getIsPigeonhole())){
			alreadyAuitListReqVo.setType(auditTaskReq.getIsPigeonhole()); // 是否归档
			if(auditTaskReq.getIsPigeonhole().equals("2")){ // 1-在途    2-归档
				if(StringUtil.isNotBlank(auditTaskReq.getPigeonholeStartTime())){
					alreadyAuitListReqVo.setFinishStarTime(DateUtil.DateFormat(auditTaskReq.getPigeonholeStartTime()));// 归档开始时间
				}
				if(StringUtil.isNotBlank(auditTaskReq.getPigeonholeEndTime())){
					alreadyAuitListReqVo.setFinishEndTime(DateUtil.DateFormat(auditTaskReq.getPigeonholeEndTime())); // 归档结束时间
				}
			}
		}
		if(StringUtil.isNotBlank(auditTaskReq.getBusinessType())){
			alreadyAuitListReqVo.setProcessType(auditTaskReq.getBusinessType()); //业务类型：1、商户  2、门店  3、团购商品  4、预售商品
		}
		//所属机构
		if(StringUtil.isNotBlank(auditTaskReq.getOrgCode())){
			alreadyAuitListReqVo.setAuditOrgCode(auditTaskReq.getOrgCode());
		}
		Map<Restrictions, String> orBessData = new HashMap<Restrictions, String>();
		// Map<Restrictions, String> andBessData = new HashMap<Restrictions,
		// String>();
		if(StringUtil.isNotBlank(auditTaskReq.getName())){ //名称
			if(auditTaskReq.getBusinessType().equals(ProcessTypeEnum.MERCHANT.getCode())){
				orBessData = BessDataUtil
						.getOrBessDataMapWithMerchantNameAndOutName(
								auditTaskReq.getName(), null, null, null,
								orBessData);
			}
			if(auditTaskReq.getBusinessType().equals(ProcessTypeEnum.OUTLET.getCode())){
				orBessData = BessDataUtil
						.getOrBessDataMapWithMerchantNameAndOutName(null,
								auditTaskReq.getName(), null, null, orBessData);
			}
			if (auditTaskReq.getBusinessType()
					.equals(ProcessTypeEnum.GROUPPRODUCT.getCode())) {
				orBessData = BessDataUtil
						.getOrBessDataMapWithMerchantNameAndOutName(null, null,
								auditTaskReq.getName(), null, orBessData);
			}
		}
		//根据业务筛选，如业务为商户则精确匹配营业执照号，最多可输入64个字符，如门店则精确匹配门店编号 outletId
		if(StringUtil.isNotBlank(auditTaskReq.getBusinessNo())){
			Map<String, Object> tempMap = new HashMap<String, Object>();
			if (auditTaskReq.getBusinessType()
					.equals(ProcessTypeEnum.MERCHANT.getCode())) {
				tempMap.put(BessDataEnum.license.getKey(), auditTaskReq.getBusinessNo());
				orBessData.put(Restrictions.EQ, JSON.toJSONString(tempMap));// 匹配商户id
			}
			if(auditTaskReq.getBusinessType().equals(ProcessTypeEnum.OUTLET.getCode())){
				tempMap.put(BessDataEnum.outletId.getKey(), auditTaskReq.getBusinessNo());
				orBessData.put(Restrictions.EQ, JSON.toJSONString(tempMap)); // 匹配门店id
			}
			if (auditTaskReq.getBusinessType()
					.equals(ProcessTypeEnum.GROUPPRODUCT.getCode())) {
				tempMap.put(BessDataEnum.productId.getKey(),
						auditTaskReq.getBusinessNo());
				orBessData.put(Restrictions.EQ, JSON.toJSONString(tempMap)); // 匹配商品id
			}
		}
		if(orBessData.size()>0){
			alreadyAuitListReqVo.setOrBessData(orBessData);
		}
		// if (andBessData.size()>0) {
		// alreadyAuitListReqVo.setAndBessData(andBessData);
		// }
	}
	
	/**
	 * 
	 * compositeQuery:(综合查询).
	 *

	 * @return
	 * @throws TException 
	 * @throws ParseException 
	 *
	 */
	public HashMap<String,Object> compositeQuery(AuditTaskReq auditTaskReq,OriginVo originVo) throws TException, ParseException{
		HashMap<String,Object> resMap = new HashMap<String,Object>();
		LogCvt.info("综合查询请求参数："+JSON.toJSONString(auditTaskReq));
		PageVo page = setPageReq(auditTaskReq);
		
		//封装请求实体
		AuditTaskFilterVo auditTaskFilterVo = new AuditTaskFilterVo();
		if(StringUtil.isNotEmpty(auditTaskReq.getAuditId())){
			auditTaskFilterVo.setAuditId(auditTaskReq.getAuditId());  //审核流水号
		}
		if(StringUtil.isNotEmpty(auditTaskReq.getBusinessType())){
			auditTaskFilterVo.setType(auditTaskReq.getBusinessType()); //业务类型
		}
		if(StringUtil.isNotEmpty(auditTaskReq.getName()) ){
			auditTaskFilterVo.setName(auditTaskReq.getName());	//名称
		}
		if(StringUtil.isNotEmpty(auditTaskReq.getBusinessNo()) ){
			auditTaskFilterVo.setBusCode(auditTaskReq.getBusinessNo());	//业务号码
		}
		if(StringUtil.isNotEmpty(auditTaskReq.getStartTime())){
			auditTaskFilterVo.setStartCreateTime(DateUtil
					.DateFormat(auditTaskReq.getStartTime() + "")); // 创建起始时间
		}
		if(StringUtil.isNotEmpty(auditTaskReq.getEndTime())){
			auditTaskFilterVo.setEndCreateTime(DateUtil.DateFormat(auditTaskReq
					.getEndTime() + "")); // 创建结束时间
		}
		if(StringUtil.isNotEmpty(auditTaskReq.getIsPigeonhole())){
			auditTaskFilterVo.setState(auditTaskReq.getIsPigeonhole()); // 是否归档
																		// 0-在途
																		// 1-归档
			if(auditTaskReq.getIsPigeonhole().equals("1")){
				if(StringUtil.isNotEmpty(auditTaskReq.getPigeonholeStartTime())){
					auditTaskFilterVo
							.setStartAuditTime(DateUtil
									.DateFormat((auditTaskReq
											.getPigeonholeStartTime() + "")));// 归档开始时间
				}
				if(StringUtil.isNotEmpty(auditTaskReq.getPigeonholeEndTime())){
					auditTaskFilterVo.setEndAuditTime(DateUtil
							.DateFormat(auditTaskReq.getPigeonholeEndTime()
									+ "")); // 归档结束时间
				}
			}
		}
		auditTaskFilterVo.setOrgCode(auditTaskReq.getOrgCode()); //所属机构
		auditTaskFilterVo.setClientId(auditTaskReq.getClientId());
		
		List<AuditTaskRes> auditTaskResList = new ArrayList<AuditTaskRes>();
		LogCvt.info("综合查询条件参数："+JSON.toJSONString(auditTaskFilterVo));
		AuditTaskPageVoRes pageVoRes = auditTaskService.getAuditTaskByPage(page, auditTaskFilterVo, 2); //flag 查询权限标志 1-查当前机构 2-查当前机构所有下属机构
		LogCvt.info("auditTaskService.getAuditTaskByPage综合查询返回信息:"
				+ JSON.toJSONString(pageVoRes));
		List<AuditTaskPageDetailVo> auditTaskPageDetailVo = pageVoRes.getAuditTaskVoList();
		if(auditTaskPageDetailVo != null && auditTaskPageDetailVo.size()>0){
			for(AuditTaskPageDetailVo auditTaskVo : auditTaskPageDetailVo){
				AuditTaskRes auditTaskRes = new AuditTaskRes();
				auditTaskRes.setAuditId(auditTaskVo.getAuditId());    //审核流水号
				auditTaskRes.setBusinessType(auditTaskVo.getType());  //业务类型
				auditTaskRes.setName(auditTaskVo.getName());  //名称
				if(auditTaskVo.getCreateTime() != 0){
					auditTaskRes.setCreateTime(auditTaskVo.getCreateTime()); //创建时间
				}else{
					auditTaskRes.setCreateTime(null);
				}
				auditTaskRes.setAuditStatus(auditTaskVo.getAuditState());//审核状态
				if(auditTaskVo.getAuditTime() != 0){
					auditTaskRes.setPigeonholeTime(auditTaskVo.getAuditTime());//归档时间
				}else{
					auditTaskRes.setPigeonholeTime(null);
				}
				auditTaskRes.setOrgName(auditTaskVo.getOrgName()); //所属机构
				auditTaskRes.setParentOrgName(auditTaskVo.getSuperOrgName());//所属机构上级机构
				MerchantTempVo merchantTempVo = merchantAuditService.getAuditMerchant(originVo, auditTaskVo.getAuditId());
				if(merchantTempVo != null){
					auditTaskRes.setMerchantId(merchantTempVo.getMerchantId()); //商户id   
				}
				//查询商户用户信息
				MerchantUserVo merchantUserVo = new MerchantUserVo();
				merchantUserVo.setMerchantId(auditTaskRes.getMerchantId());
				merchantUserVo.setMerchantRoleId(Constants.MERCHANT_ROLE_ID);
				merchantUserVo.setClientId(auditTaskReq.getClientId());
				List<MerchantUserVo> merchantUserList = merchantUserService.getMerchantUser(merchantUserVo);

				if(merchantUserList.size()>0){
					MerchantUserVo merchantUser = merchantUserList.get(0);
					LogCvt.info("merchantUserId:"+merchantUser.getId());
					if(merchantUser != null){
						//auditTaskRes.setMerchantUserId(merchantUser.getId());  //商户用户id
					}
				}
				auditTaskResList.add(auditTaskRes);
			}
		}
		resMap.put("merchantTaskVo", auditTaskResList);
		if(pageVoRes.getPage()!= null){
			resMap.put("pageCount", pageVoRes.getPage().getPageCount());
			resMap.put("totalCount", pageVoRes.getPage().getTotalCount());
			resMap.put("pageNumber", pageVoRes.getPage().getPageNumber());
			resMap.put("lastPageNumber", pageVoRes.getPage().getLastPageNumber());
			resMap.put("firstRecordTime", pageVoRes.getPage().getFirstRecordTime());
			resMap.put("lastRecordTime", pageVoRes.getPage().getLastRecordTime());
		}else{
			resMap.put("pageCount", 0);
			resMap.put("totalCount", 0);
			resMap.put("pageNumber", 1);
			resMap.put("lastPageNumber", 0);
			resMap.put("firstRecordTime", 0);
			resMap.put("lastRecordTime", 0);
		}
		return resMap;
		
	}
	
	/**
	 * 
	 * getAuditTaskDetail:(审核监控和综合查询详情).
	 *

	 * @param auditId
	 * @param merchantId
	 * @param merchantUserId
	 * @param originVo
	 * @return
	 * @throws TException
	 *
	 */
	public HashMap<String, Object> getAuditTaskDetail(String auditId,String merchantId,String merchantUserId,OriginVo originVo,String clientId) throws TException{
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		
		AuditTaskRes auditTaskRes = new AuditTaskRes();
		//查询审核任务订单信息

		AuditTaskVo auditTaskVo = auditTaskService.getAuditTaskByAuditId(auditId);

		LogCvt.info("任务单列表查询返回数据："+JSON.toJSONString(auditTaskVo));
		if(auditTaskVo != null){
			auditTaskRes.setAuditId(auditTaskVo.getAuditId()); //审核流水号
			auditTaskRes.setAuditStatus(auditTaskVo.getAuditState()); //审核状态
			if(auditTaskVo.getCreateTime() !=  0){
				auditTaskRes.setCreateTime(auditTaskVo.getCreateTime()); //创建时间
			}else{
				auditTaskRes.setCreateTime(null);
			}
			auditTaskRes.setName(auditTaskVo.getName()); //名称
			auditTaskRes.setLincese(auditTaskVo.getBusCode()); //营业执照
			auditTaskRes.setCreater(auditTaskVo.getUserName());//创建人
			auditTaskRes.setCreateOrgName(auditTaskVo.getOrgName());//创建机构
			if(auditTaskVo.getAuditTime() != 0){
				auditTaskRes.setPigeonholeTime(auditTaskVo.getAuditTime());//归档时间
			}else{
				auditTaskRes.setPigeonholeTime(null);
			}
			auditTaskRes.setBusinessType(auditTaskVo.getType());//业务类型
			
		}
		resMap.put("auditTaskOrder", auditTaskRes);
		
		MerchantAuditDetailRes merchant = new MerchantAuditDetailRes();
		
		MerchantTempVo merchantTempVo = null;
		
		List<AuditProcessRes> auditProcessResList = new ArrayList<AuditProcessRes>();
		if(auditTaskVo != null && auditTaskVo.getAuditId() != null){
			//查询商户新值信息

			merchantTempVo = merchantAuditService.getAuditMerchant(originVo, auditId);

			LogCvt.info("商户新值返回数据："+JSON.toJSONString(merchantTempVo));
			if(merchantTempVo != null){
				merchant.setNewContactName(merchantTempVo.getContactName());
				merchant.setNewLegalName(merchantTempVo.getLegalName());
				merchant.setNewLegalCredentType(merchantTempVo.getLegalCredentType());
				merchant.setNewLegalCredentNo(merchantTempVo.getLegalCredentNo());
				merchant.setNewAcctName(merchantTempVo.getAccountName());
				merchant.setNewAcctNumber(merchantTempVo.getAcountNo());
				merchant.setNewLoginPhone(merchantTempVo.getLoginMobile());
				merchant.setNewOrgNames(merchantTempVo.getCountyOrgName());
				merchant.setNewParentOrgNames(merchantTempVo.getCityOrgName());
				merchant.setNewCategory(merchantTempVo.getMerchantCategoryName());
				merchant.setNewCategoryType(merchantTempVo.getMerchantTypeName());
				
				//商户原值信息通过一个字段primeval以json格式返回来 
				LogCvt.info("======商户原值信息==="+JSON.toJSONString(merchantTempVo.getPrimeval()));
				if(StringUtil.isNotBlank(merchantTempVo.getPrimeval())){
					String merchantPrimeval = merchantTempVo.getPrimeval();
					JSONObject json = JSONObject.fromObject(merchantPrimeval);
					merchant.setMerchantFullName(json
							.getString(BessDataEnum.merchantFullName.getKey()));// 商户全称
					merchant.setMerchantName(
							json.getString(BessDataEnum.merchantName.getKey())); // 商户简称
					merchant.setOldContactName(
							json.getString(BessDataEnum.contactName.getKey()));// 联系人姓名
					merchant.setOldLegalName(
							json.getString(BessDataEnum.legalName.getKey()));// 法人名

					merchant.setOldCategory(
							json.getString(BessDataEnum.category.getKey())); // 所属分类
					merchant.setOldCategoryType(
							json.getString(BessDataEnum.categoryType.getKey())); // 商户类型
					merchant.setOldAcctName(
							json.getString(BessDataEnum.acctName.getKey())); // 收款账户名
					merchant.setOldAcctNumber(
							json.getString(BessDataEnum.acctNumber.getKey())); // 收货人账号
					merchant.setOldLoginPhone(
							json.getString(BessDataEnum.phone.getKey())); // 登录人手机
					//所属机构
					String orgCode = json
							.getString(BessDataEnum.orgCode.getKey());
					if(StringUtil.isNotBlank(orgCode)){
						OrgVo org = orgService.getOrgByIdSuperOrgName(clientId, orgCode);
						if(org != null){
							merchant.setOldOrgNames(org.getOrgName());//机构名称
							merchant.setOldParentOrgNames(org.getSuperOrgName()); //上级机构名称
						}
					}
				}
			}
			
			//查询审核任务列表
			LogCvt.info("======商户审核详情，根据审核流水号auditId" + auditTaskVo.getAuditId() + "任务单列表:开始======");
			List<AuditProcessVo> auditProcessVoList = auditProcessService.getAuditProcess(auditId);
			LogCvt.info("======商户审核详情，根据审核流水号auditId" + auditTaskVo.getAuditId() + "任务单列表:开始======");
			LogCvt.info("任务单列表查询返回数据："+JSON.toJSONString(auditProcessVoList));
			if(auditProcessVoList != null && auditProcessVoList.size()>0){
				for(AuditProcessVo auditProcessVo : auditProcessVoList){
					AuditProcessRes auditProcessRes = new AuditProcessRes();
					auditProcessRes.setTaskId(auditProcessVo.getTaskId());
					if(auditProcessVo.getCreateTime() != 0){
						auditProcessRes.setCreateTime(auditProcessVo.getCreateTime());
					}else{
						auditProcessRes.setCreateTime(null);
					}
					if(auditProcessVo.getAuditTime() != 0){
						auditProcessRes.setAuditTime(auditProcessVo.getAuditTime());
					}else{
						auditProcessRes.setAuditTime(null);
					}
					auditProcessRes.setAuditOrgName(auditProcessVo.getOrgName());
					auditProcessRes.setAuditor(auditProcessVo.getAuditStaff());
					auditProcessRes.setAuditStatus(auditProcessVo.getAuditState());
					auditProcessRes.setComment(auditProcessVo.getAuditComment());
					auditProcessResList.add(auditProcessRes);
				}
			}
		}
		resMap.put("merchant", merchant);
		resMap.put("merchantTaskList", auditProcessResList);
		
		return resMap;
		
	}
	
	
}
