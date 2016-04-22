/**
 * Project Name:coremodule-bank
 * File Name:MerchantAuditService.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.service
 * Date:2015-8-17下午04:09:23
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditProcessRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditTaskRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantAuditDetailRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantEditReq;
import com.froad.thrift.service.AuditProcessService;
import com.froad.thrift.service.AuditTaskService;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.MerchantAuditService;
import com.froad.thrift.service.MerchantCategoryService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.MerchantTypeService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.vo.AuditProcessVo;
import com.froad.thrift.vo.AuditTaskVo;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.MerchantAuditVoRes;
import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.MerchantTempVo;
import com.froad.thrift.vo.MerchantTempVoReq;
import com.froad.thrift.vo.MerchantTypeVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.MerchantVoReq;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;

import net.sf.json.JSONObject;

/**
 * 
 * ClassName: AuditMerchantService Function: 商户审核 date: 2015-8-18 下午02:23:48
 *
 * @author wufei
 * @version
 */
@Service
public class AuditMerchantService {
	
	@Resource
	MerchantAuditService.Iface merchantAuditService;
	@Resource
	MerchantService.Iface merchantService;
	@Resource
	AuditTaskService.Iface auditTaskService;
	@Resource
	AuditProcessService.Iface auditProcessService;
	@Resource
	MerchantAccountService.Iface merchantAccountService;
	@Resource
	MerchantUserService.Iface merchantUserService;
	@Resource
	OrgService.Iface orgService;
	@Resource
	private LoginService loginService;
	@Resource
	MerchantCategoryService.Iface merchantCategoryService;
	@Resource
	MerchantTypeService.Iface merchantTypeService;
	
	/**
	 * 
	 * merchantAuditDetail:(商户审核信息详情查询).
	 *
	 * @author wufei 2015-8-17 下午05:44:30
	 * @param merchantId
	 * @return
	 * @throws TException
	 *
	 */
	public HashMap<String,Object> merchantAuditDetail(String merchantId,OriginVo originVo,String merchantUserId,String clientId) throws TException{
		HashMap<String,Object> resMap = new HashMap<String,Object>();
		
		// 查询审核任务订单信息
		AuditTaskRes auditTaskRes = new AuditTaskRes();
		LogCvt.info("======审核任务订单信息查询，根据商户ID" + merchantId
				+ "获取审核任务订单:开始======");
		AuditTaskVo auditTaskVo = auditTaskService.getAuditTaskWait(merchantId);
		LogCvt.info("======审核任务订单信息查询，根据商户ID" + merchantId
				+ "获取审核任务订单:结束======");
		LogCvt.info("任务单列表查询返回数据：" + JSON.toJSONString(auditTaskVo));
		if(auditTaskVo != null){
			auditTaskRes.setAuditId(auditTaskVo.getAuditId()); // 审核流水号
			auditTaskRes.setAuditStatus(auditTaskVo.getAuditState()); // 审核状态
			if(auditTaskVo.getCreateTime() !=  0){
				auditTaskRes.setCreateTime(auditTaskVo.getCreateTime()); // 创建时间
			}else{
				auditTaskRes.setCreateTime(null);
			}
			auditTaskRes.setName(auditTaskVo.getName()); // 名称
			auditTaskRes.setLincese(auditTaskVo.getBusCode()); // 营业执照
			auditTaskRes.setCreater(auditTaskVo.getUserName());// 创建人
			auditTaskRes.setCreateOrgName(auditTaskVo.getOrgName());// 创建机构
		}
		resMap.put("auditTaskOrder", auditTaskRes);
		
		MerchantAuditDetailRes merchant = new MerchantAuditDetailRes();
		
		// //查询商户原值信息
		// LogCvt.info("======商户审核详情，根据商户ID" + merchantId +
		// "商户原值信息查询:开始======");
//		MerchantVo merchantVo = merchantService.getMerchantByMerchantId(merchantId);
		// LogCvt.info("======商户审核详情，根据商户ID" + merchantId +
		// "商户原值信息查询:结束======");
//		if(merchant != null){
//			merchant.setMerchantFullName(merchantVo.getMerchantFullname());
//			merchant.setOldContactName(merchantVo.getContactName());
//			merchant.setOldLegalName(merchantVo.getLegalName());
//			merchant.setOldLegalCredentType(merchantVo.getLegalCredentType());
//			merchant.setOldLegalCredentNo(merchantVo.getLegalCredentNo());
//		}
//		
		// //商户分类和类型
//		getAndSetCategoryAndType(merchantId, merchant);
//		
		// //获取所属机构
//		getAndSetOrgName(merchant, merchantVo);
//		
		// //获取商户账号信息
//		getAndSetMerchantAccount(merchantId, merchant);
//		
		// //获取商户用户信息
//		getAndSetMerchantUser(merchantUserId, merchant); 
		
		List<AuditProcessRes> auditProcessResList = new ArrayList<AuditProcessRes>();
		
		MerchantTempVo merchantTempVo = null;
		if(auditTaskVo != null && auditTaskVo.getAuditId() != null){
			// 查询商户新值信息
			LogCvt.info("======商户审核详情，根据审核流水号auditId"
					+ auditTaskVo.getAuditId() + "商户新值信息:开始======");
			merchantTempVo = merchantAuditService.getAuditMerchant(originVo, auditTaskVo.getAuditId());
			LogCvt.info("======商户审核详情，根据审核流水号auditId"
					+ auditTaskVo.getAuditId() + "商户新值信息:结束======");
			LogCvt.info("商户信息返回数据：" + JSON.toJSONString(merchantTempVo));
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
				
				// 商户原值信息通过一个字段primeval以json格式返回来
				LogCvt.info("======商户原值信息==="
						+ JSON.toJSONString(merchantTempVo.getPrimeval()));
				if(StringUtil.isNotBlank(merchantTempVo.getPrimeval())){
					String merchantPrimeval = merchantTempVo.getPrimeval();
					JSONObject json = JSONObject.fromObject(merchantPrimeval);
					merchant.setMerchantFullName(json
							.getString("merchantFullname"));// 商户全称
					merchant.setMerchantName(json.getString("merchantName")); // 商户简称
					merchant.setOldContactName(json.getString("contactName"));// 联系人姓名
					merchant.setOldLegalName(json.getString("legalName"));// 法人名
					merchant.setOldLegalCredentType(json
							.getString("legalCredentType"));// 证件类型
					merchant.setOldLegalCredentNo(json
							.getString("legalCredentNo"));// 证件号
					merchant.setOldCategory(json.getString("category")); // 所属分类
					merchant.setOldCategoryType(json.getString("categoryType")); // 商户类型
					merchant.setOldAcctName(json.getString("acctName")); // 收款账户名
					merchant.setOldAcctNumber(json.getString("acctNumber")); // 收货人账号
					merchant.setOldLoginPhone(json.getString("phone")); // 登录人手机
					// 所属机构
					String orgCode = json.getString("orgCode");
					if(StringUtil.isNotBlank(orgCode)){
						OrgVo org = orgService.getOrgByIdSuperOrgName(clientId, orgCode);
						if(org != null){
							merchant.setOldOrgNames(org.getOrgName());// 机构名称
							merchant.setOldParentOrgNames(org.getSuperOrgName()); // 上级机构名称
						}
					}
				}
				
			}
			
			// 查询审核任务列表
			LogCvt.info("======商户审核详情，根据审核流水号auditId"
					+ auditTaskVo.getAuditId() + "任务单列表:开始======");
			List<AuditProcessVo> auditProcessVoList = auditProcessService.getAuditProcess(auditTaskVo.getAuditId());
			LogCvt.info("======商户审核详情，根据审核流水号auditId"
					+ auditTaskVo.getAuditId() + "任务单列表:开始======");
			LogCvt.info("任务单列表查询返回数据：" + JSON.toJSONString(auditProcessVoList));
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

	
	/**
	 * 
	 * merchantEdit:(商户审核信息编辑).
	 *
	 * @author wufei 2015-8-18 上午10:29:02
	 * @return
	 * @throws TException
	 *
	 */
	public HashMap<String,Object> merchantEdit(MerchantEditReq merchantEditReq,HttpServletRequest req) throws TException{
		HashMap<String,Object> resMap = new HashMap<String,Object>();
		LogCvt.info("**********商户审核编辑接口请求参数"
				+ JSON.toJSONString(merchantEditReq));
		LogCvt.info("======商户信息查询，根据商户Id" + merchantEditReq.getMerchantId()
				+ "商户:开始======");
		MerchantVo merchantVo = merchantService.getMerchantByMerchantId(merchantEditReq.getMerchantId());
		LogCvt.info("======商户信息查询，根据商户Id" + merchantEditReq.getMerchantId()
				+ "商户:结束======");
		
		BankOperatorVo bankOperatorVoRes = (BankOperatorVo) req.getAttribute(Constants.BANKOPERATOR);
		if(StringUtil.isNotEmpty(merchantVo.getUserOrgCode())){
			if(!merchantVo.getUserOrgCode().equals(bankOperatorVoRes.getOrgCode())){
				resMap.put("code", EnumTypes.fail.getCode());
				resMap.put("message", "不能修改他人录入的商户");
				return resMap;
			}
		}
		
		if(StringUtil.isEmpty(merchantEditReq.getMerchantName()) && StringUtil.isEmpty(merchantEditReq.getMerchantFullName()) &&
				StringUtil.isEmpty(merchantEditReq.getAreaId()) && StringUtil.isEmpty(merchantEditReq.getAddress()) &&
				StringUtil.isEmpty(merchantEditReq.getPhone()) && StringUtil.isEmpty(merchantEditReq.getContactPhone()) &&
				StringUtil.isEmpty(merchantEditReq.getComplaintPhone()) && StringUtil.isEmpty(merchantEditReq.getContactEmail()) &&
				StringUtil.isEmpty(merchantEditReq.getCompanyCredential()) && StringUtil.isEmpty(merchantEditReq.getTaxReg()) &&
				StringUtil.isEmpty(merchantEditReq.getIntroduce()) && 
				StringUtil.isEmpty(merchantEditReq.getContactName()) && StringUtil.isEmpty(merchantEditReq.getLegalName()) &&
				StringUtil.isEmpty(merchantEditReq.getOpeningBank()) && StringUtil.isEmpty(merchantEditReq.getLegalCredentType()) &&
				StringUtil.isEmpty(merchantEditReq.getLegalCredentNo()) && StringUtil.isEmpty(merchantEditReq.getCategory()) &&
				StringUtil.isEmpty(merchantEditReq.getCategoryType()) && StringUtil.isEmpty(merchantEditReq.getAcctName()) &&
				StringUtil.isEmpty(merchantEditReq.getAcctNumber()) && StringUtil.isEmpty(merchantEditReq.getConfirmAcctNumber()) &&
				StringUtil.isEmpty(merchantEditReq.getLoginPhone())){
			
			resMap.put("code", EnumTypes.success.getCode());
			resMap.put("message", EnumTypes.success.getMessage());
		}else{
			
			// 免审信息编辑
			if(StringUtil.isNotEmpty(merchantEditReq.getMerchantName()) || StringUtil.isNotEmpty(merchantEditReq.getMerchantFullName()) ||
					StringUtil.isNotEmpty(merchantEditReq.getAreaId()) || StringUtil.isNotEmpty(merchantEditReq.getAddress()) ||
					StringUtil.isNotEmpty(merchantEditReq.getPhone()) || StringUtil.isNotEmpty(merchantEditReq.getContactPhone()) ||
					StringUtil.isNotEmpty(merchantEditReq.getComplaintPhone()) || StringUtil.isNotEmpty(merchantEditReq.getContactEmail()) ||
					StringUtil.isNotEmpty(merchantEditReq.getCompanyCredential()) || StringUtil.isNotEmpty(merchantEditReq.getTaxReg()) ||
					StringUtil.isNotEmpty(merchantEditReq.getIntroduce())){
				
				MerchantVo merchant = new MerchantVo ();
				merchant.setMerchantId(merchantEditReq.getMerchantId());
				if(StringUtil.isNotEmpty(merchantEditReq.getMerchantName())){
					merchant.setMerchantName(merchantEditReq.getMerchantName()); // 商户简称
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getMerchantFullName())){
					merchant.setMerchantFullname(merchantEditReq
							.getMerchantFullName()); // 商户全称
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getAreaId())){
					merchant.setAreaId(Integer.parseInt(merchantEditReq
							.getAreaId())); // 地址
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getAddress())){
					merchant.setAddress(merchantEditReq.getAddress()); // 详细地址
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getContactPhone())){
					merchant.setContactPhone(merchantEditReq.getContactPhone()); // 联系电话
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getPhone())){
					merchant.setPhone(merchantEditReq.getPhone()); // 联系人手机
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getComplaintPhone())){
					merchant.setComplaintPhone(merchantEditReq
							.getComplaintPhone());// 投诉电话
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getContactEmail())){
					merchant.setContactEmail(merchantEditReq.getContactEmail()); // 联系人邮箱
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getCompanyCredential())){
					merchant.setCompanyCredential(merchantEditReq
							.getCompanyCredential()); // 组织机构代码
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getTaxReg())){
					merchant.setTaxReg(merchantEditReq.getTaxReg()); // 税务登记证
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getIntroduce())){
					merchant.setIntroduce(merchantEditReq.getIntroduce()); // 商户介绍
				}
				MerchantVoReq merchantReq = new MerchantVoReq();
				merchantReq.setMerchantVo(merchant);
				
				// 免审核信息修改调用原接口
				ResultVo updateFreeAuditResultVo = merchantService.updateMerchantByAuditThrough(loginService.getOriginVo(req), merchantReq);
				LogCvt.info("merchantService.updateMerchantByAuditThrough更新商户免审信息返回:"
						+ JSON.toJSONString(updateFreeAuditResultVo));
				if(EnumTypes.success.getCode().equals(updateFreeAuditResultVo.getResultCode())){
					resMap.put("code", updateFreeAuditResultVo.getResultCode());
					resMap.put("message", updateFreeAuditResultVo.getResultDesc());
				}else{
					resMap.put("code", EnumTypes.fail.getCode());
					resMap.put("message", updateFreeAuditResultVo.getResultDesc());
				}
				
			}
			
			if(StringUtil.isNotEmpty(merchantEditReq.getContactName()) || StringUtil.isNotEmpty(merchantEditReq.getLegalName()) ||
					StringUtil.isNotEmpty(merchantEditReq.getOpeningBank()) || StringUtil.isNotEmpty(merchantEditReq.getLegalCredentType()) ||
					StringUtil.isNotEmpty(merchantEditReq.getLegalCredentNo()) || StringUtil.isNotEmpty(merchantEditReq.getCategory()) ||
					StringUtil.isNotEmpty(merchantEditReq.getCategoryType()) || StringUtil.isNotEmpty(merchantEditReq.getAcctName()) ||
					StringUtil.isNotEmpty(merchantEditReq.getAcctNumber()) || StringUtil.isNotEmpty(merchantEditReq.getConfirmAcctNumber()) ||
					StringUtil.isNotEmpty(merchantEditReq.getLoginPhone())){
				
				// 审核信息
				MerchantTempVoReq merchantTempVoReq = new MerchantTempVoReq();
				MerchantTempVo merchantTempVo = new MerchantTempVo();
				merchantTempVo.setMerchantId(merchantEditReq.getMerchantId());
				if(StringUtil.isNotEmpty(merchantEditReq.getContactName())){
					merchantTempVo.setContactName(merchantEditReq
							.getContactName()); // 联系人姓名
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getLegalName())){
					merchantTempVo.setLegalName(merchantEditReq.getLegalName()); // 法人名
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getOpeningBank())){
					merchantTempVo.setOrgCode(merchantEditReq.getOpeningBank()); // 开户行机构号
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getLegalCredentType())){
					merchantTempVo.setLegalCredentType(String
							.valueOf(merchantEditReq.getLegalCredentType())); // 证件类型
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getLegalCredentNo())){
					merchantTempVo.setLegalCredentNo(merchantEditReq
							.getLegalCredentNo()); // 证件号码
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getCategoryType())){
					merchantTempVo.setMerchantTypeId(merchantEditReq
							.getCategoryType()); // 商户类型
					if(merchantEditReq.getCategoryType().contains(",")){
						String[] categoryTypes = merchantEditReq.getCategoryType().split(",");
						 StringBuilder name= new StringBuilder();
						 StringBuilder type= new StringBuilder();
				        for (int i = 0; i < categoryTypes.length; i++) {
				            MerchantTypeVo merchantType = merchantTypeService.getMerchantTypeById(Long.parseLong(categoryTypes[i]),merchantEditReq.getClientId());
							LogCvt.info("**********商户类型***********"
									+ JSON.toJSONString(merchantType));
				            name.append(merchantType.getTypeName());
				            name.append(",");
				            type.append(merchantType.getType());
				            type.append(",");
				        }
				        merchantTempVo.setMerchantTypeName(name.toString());
						merchantTempVo.setMerchantTypeValue(type.toString());
					}else{
						MerchantTypeVo merchantType = merchantTypeService.getMerchantTypeById(Long.parseLong(merchantEditReq.getCategoryType()),merchantEditReq.getClientId());
						merchantTempVo.setMerchantTypeName(merchantType.getTypeName());
						merchantTempVo.setMerchantTypeValue(merchantType.getType());
					}
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getCategory())){
					merchantTempVo.setMerchantCategoryId(Long
							.parseLong(merchantEditReq.getCategory()));// 商户分类
					MerchantCategoryVo  merchantCategory =merchantCategoryService.getMerchantCategoryById(merchantEditReq.getClientId(),Long.parseLong(merchantEditReq.getCategory()));
					merchantTempVo.setMerchantCategoryName(merchantCategory.getName());
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getAcctName())){
					merchantTempVo
							.setAccountName(merchantEditReq.getAcctName()); // 收款账户名
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getAcctNumber())){
					merchantTempVo.setAcountNo(merchantEditReq.getAcctNumber()); // 收款账户号
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getAcctNumber()) && StringUtil.isNotEmpty(merchantEditReq.getConfirmAcctNumber())){
					if(!merchantEditReq.getAcctNumber().equals(merchantEditReq.getConfirmAcctNumber())){
						resMap.put("code", EnumTypes.fail.getCode());
						resMap.put("message", "收款账户号输入不一致！");
						return resMap;
					}
				}
				if(StringUtil.isNotEmpty(merchantEditReq.getLoginPhone())){
					merchantTempVo.setLoginMobile(merchantEditReq
							.getLoginPhone()); // 登录人手机
				}
				merchantTempVo.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
				merchantTempVoReq.setMerchantTempVo(merchantTempVo);
				
				// 审核信息修改调用新接口
				MerchantAuditVoRes  merchantAuditVoRes = merchantAuditService.auditMerchant(loginService.getOriginVo(req), merchantTempVoReq);
				LogCvt.info("merchantAuditService.auditMerchant 更新商户审核信息返回:"
						+ JSON.toJSONString(merchantAuditVoRes));
				if(EnumTypes.success.getCode().equals(merchantAuditVoRes.getResult().getResultCode())){
					resMap.put("code", merchantAuditVoRes.getResult().getResultCode());
					resMap.put("message", merchantAuditVoRes.getResult().getResultDesc());
				}else{
					resMap.put("code", EnumTypes.fail.getCode());
					resMap.put("message", merchantAuditVoRes.getResult().getResultDesc());
				}
				
			}
		}
		
		return resMap;
	}

	
	/**
	 * 
	 * getAuditStatus:(商户审核状态查询).
	 *
	 * @author wufei 2015-8-18 下午05:46:48
	 * @param merchantId
	 * @return
	 * @throws TException
	 *
	 */
	public HashMap<String,Object> getAuditStatus(String merchantId) throws TException{
		HashMap<String,Object> resMap = new HashMap<String,Object>();
		String auditStatus = null;
		String editAuditStatus = null;
		MerchantVo m = merchantService.getMerchantByMerchantId(merchantId);
		if(m != null){
			auditStatus = m.getAuditState();
			editAuditStatus = m.getEditAuditState();
		}
		resMap.put("auditStatus", auditStatus);
		resMap.put("editAuditStatus", editAuditStatus);
		return resMap;
	}
}
