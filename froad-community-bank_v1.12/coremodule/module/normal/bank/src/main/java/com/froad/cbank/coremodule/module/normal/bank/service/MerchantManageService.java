package com.froad.cbank.coremodule.module.normal.bank.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.BessDataEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProcessTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.BankStringUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.BessDataUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.ApprovalMerchantRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.AreaVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditProcessRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditTaskRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankCarCheckReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankCompanyResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.CategoryVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantAuditDetailRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantManageAccountVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantManageUserVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantManageVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantTypeRes;
import com.froad.thrift.service.BankAuditService;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.ClientMerchantAuditService;
import com.froad.thrift.service.DictionaryService;
import com.froad.thrift.service.FallowExecuteService;
import com.froad.thrift.service.FallowQueryService;
import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.service.FinityUserResourceService;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.MerchantAuditService;
import com.froad.thrift.service.MerchantCategoryService;
import com.froad.thrift.service.MerchantRoleResourceService;
import com.froad.thrift.service.MerchantRoleService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.MerchantTypeService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.AuditInstanceDetailVo;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.ClientMerchantAuditOrgCodeVo;
import com.froad.thrift.vo.CreateInstanceReqVo;
import com.froad.thrift.vo.CreateInstanceResVo;
import com.froad.thrift.vo.DictionaryItemVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.GetInstanceIdByAttrReqVo;
import com.froad.thrift.vo.GetInstanceIdByAttrResVo;
import com.froad.thrift.vo.GetPendAuitListPageVo;
import com.froad.thrift.vo.GetPendAuitListReqVo;
import com.froad.thrift.vo.GetTaskOverviewReqVo;
import com.froad.thrift.vo.GetTaskOverviewResVo;
import com.froad.thrift.vo.GetTaskReqVo;
import com.froad.thrift.vo.GetTaskResVo;
import com.froad.thrift.vo.InvalidProductBatchVo;
import com.froad.thrift.vo.MerchantAccountAddVoRes;
import com.froad.thrift.vo.MerchantAccountVo;
import com.froad.thrift.vo.MerchantAddVoRes;
import com.froad.thrift.vo.MerchantAuditVoRes;
import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.MerchantDetailPageVoRes;
import com.froad.thrift.vo.MerchantDetailVo;
import com.froad.thrift.vo.MerchantPageVoRes;
import com.froad.thrift.vo.MerchantRoleResourceVo;
import com.froad.thrift.vo.MerchantTempVo;
import com.froad.thrift.vo.MerchantTempVoReq;
import com.froad.thrift.vo.MerchantTypeVo;
import com.froad.thrift.vo.MerchantUserAddVoRes;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.MerchantVoReq;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResourceVo;
import com.froad.thrift.vo.Restrictions;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.thrift.vo.SmsTypeEnum;
import com.froad.thrift.vo.TaskListResVo;
import com.froad.thrift.vo.TypeInfoVo;
import com.froad.thrift.vo.finity.UserResourceVo;

import net.sf.json.JSONObject;

/**
 * 商户
 * 
 * @author ylchu
 *
 */
@Service
public class MerchantManageService {

	@Resource
	MerchantService.Iface merchantService;
	@Resource
	MerchantUserService.Iface merchantUserService;
	@Resource
	OutletService.Iface outletService;
	@Resource
	OrgService.Iface orgService;
	@Resource
	MerchantCategoryService.Iface merchantCategoryService;
	@Resource
	MerchantTypeService.Iface merchantTypeService;
	@Resource
	BankAuditService.Iface bankAuditService;
	@Resource
	MerchantAccountService.Iface merchantAccountService;
	@Resource
	ProductService.Iface productService;
	@Resource
	BankOrgService bankOrgService;
	@Resource
	private LoginService loginService;
	@Resource
	ClientMerchantAuditService.Iface clientMerchantAuditService;
	@Resource
	SmsMessageService.Iface smsMessageService;
	@Resource
	MerchantRoleService.Iface merchantRoleService;
	@Resource
	BankCardService.Iface bankCardService;
	@Resource
	FinityResourceService.Iface finityResourceService;
	@Resource
	FinityUserResourceService.Iface finityUserResourceService;
	@Resource
	MerchantRoleResourceService.Iface merchantRoleResourceService;
	@Resource
	FallowQueryService.Iface fallowQueryService;
	@Resource
	FallowExecuteService.Iface fallowExecuteService;
	@Resource
	MerchantAuditService.Iface merchantAuditService;
	@Resource
	BankOperatorService.Iface bankOperatorService;
	@Resource
	DictionaryService.Iface dictionaryService;

	/**
	 * 分页列表条件查询
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws ParseException
	 */
	public HashMap<String, Object> findPageByConditions(MerchantReq merchant)
			throws TException, ParseException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		LogCvt.info("商户列表请求参数：" + JSON.toJSONString(merchant));
		// 分页请求参数封装
		PageVo page = reqPageValue(merchant);
		MerchantVo m = new MerchantVo();
		// 商户查询请求参数封装
		this.setMerchantValueToReqVo(merchant, m);
		// 请求server
		LogCvt.info("商户列表查询条件：" + JSON.toJSONString(m));
		MerchantPageVoRes pageRes = merchantService.getMerchantByPage(page, m);
		LogCvt.info("商户列表查询返回数据：" + JSON.toJSONString(pageRes));

		List<MerchantRes> merchantResList = new ArrayList<MerchantRes>();
		List<MerchantVo> merchantList = pageRes.getMerchantVoList();
		List<String> orgCodeList = new ArrayList<String>();
		List<String> merchantIdList = new ArrayList<String>(); // 从商户详情获取类型和分类
		if (merchantList != null && merchantList.size() > 0) {
			for (int i = 0; i < merchantList.size(); i++) {
				merchantIdList.add(merchantList.get(i).getMerchantId());
			}
			LogCvt.info("======商户列表查询，根据商户id集合查询详情:开始======");
			List<MerchantDetailVo> merchantDetailList = merchantService
					.getMerchantDetailbyMerchantIdList(merchantIdList);
			LogCvt.info("======商户列表查询，根据商户id集合查询详情:结束======");
			LogCvt.info("======商户列表查询，根据商户id集合和商户角色为超级管理员查询商户用户信息:开始======");

			/*
			 * //查询商户角色 Long merchantId = null ;
			 * LogCvt.info("======查询merchantRole===查询条件==clientId======"
			 * +merchant.getClientId()+"========"); MerchantRoleVo
			 * merchantRoleVo =
			 * merchantRoleService.getMerchantRoleByClientIdAndRoleDesc
			 * (merchant.getClientId(), "Role_Administrators");
			 * LogCvt.info("======查询merchantRole===返回数据===roleid==="
			 * +merchantRoleVo.getId()); if(merchantRoleVo != null){ merchantId
			 * = merchantRoleVo.getId(); }
			 */
			List<MerchantUserVo> merchantUserList = merchantUserService
					.getMerchantUserByRoleIdAndMerchantIds(
							Constants.MERCHANT_ROLE_ID, merchantIdList);
			LogCvt.info("======商户列表查询，根据商户id集合和商户角色为超级管理员查询商户用户信息:结束======");
			for (int i = 0; i < merchantList.size(); i++) {
				MerchantRes res = new MerchantRes();
				for (int j = 0; j < merchantUserList.size(); j++) {
					// 商户用户信息
					this.setMerchantUserValue(merchantList, merchantUserList, i, res,
							j);
				}
				// 商户类型和所属分类
				if (merchantDetailList != null
						&& merchantDetailList.size() > 0) {
					this.setCategoryAndTypeName(merchantList, merchantDetailList, i,
							res);
				}
				// 其他信息
				this.setSomeOtherValueToResVo(merchantResList, merchantList,
						orgCodeList, i, res);
			}
			// 所属机构
			LogCvt.info("======商户列表查询，根据orgCode集合查询orgName:开始======");
			if (orgCodeList != null && orgCodeList.size() > 0) {
				this.setOrgNameValue(merchant, merchantResList, orgCodeList);
			}
			LogCvt.info("======商户列表查询，根据orgCode集合查询orgName:开始======");
		}
		reMap.put("merchantList", merchantResList);
		// 返回分页信息
		this.setPagesValueToResMap(reMap, pageRes.getPage());
		return reMap;
	}

	/**
	 * 待审核商户列表
	 * 
	 * @param merchant
	 * @return
	 * @throws TException
	 * @throws ParseException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public HashMap<String, Object> findApprovalList(MerchantReq merchant,
			OriginVo originVo) throws TException, ParseException,
					JsonGenerationException, JsonMappingException, IOException {
		LogCvt.info("待审核商户列表请求参数" + JSON.toJSONString(merchant));
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		// 分页请求参数封装
		PageVo page = reqPageValue(merchant);
		GetPendAuitListReqVo pendAuitListReqVo = new GetPendAuitListReqVo();
		// 封装请求参数
		this.setPendAuitListReqVo(merchant, originVo, pendAuitListReqVo);

		List<ApprovalMerchantRes> merchantResList = new ArrayList<ApprovalMerchantRes>();

		LogCvt.info("待审核商户列表查询fallowQueryService.getPendAuitList======查询条件："
				+ JSON.toJSONString(pendAuitListReqVo) + "page："
				+ JSON.toJSONString(page));
		GetPendAuitListPageVo pendAuitListPageVo = fallowQueryService
				.getPendAuitList(pendAuitListReqVo, page);
		LogCvt.info("待审核商户列表查询返回数据：待审核列表"
				+ JSON.toJSONString(pendAuitListPageVo.getAuditDetailList())
				+ "******result："
				+ JSON.toJSONString(pendAuitListPageVo.getResult()));
		if (EnumTypes.success.getCode()
				.equals(pendAuitListPageVo.getResult().getResultCode())) {
			if (pendAuitListPageVo.getAuditDetailList() != null
					&& pendAuitListPageVo.getAuditDetailList().size() > 0) {
				for (AuditInstanceDetailVo pendAuitListResVo : pendAuitListPageVo
						.getAuditDetailList()) {
					ApprovalMerchantRes approvalMerchantRes = new ApprovalMerchantRes();
					approvalMerchantRes
							.setAuditId(pendAuitListResVo.getInstanceId()); // 审核流水号
					approvalMerchantRes
							.setCreateTime(pendAuitListResVo.getCreateTime()); // 创建时间
					approvalMerchantRes.setAuditType(
							pendAuitListResVo.getProcessTypeDetail()); // 审核类型
					// if(pendAuitListResVo.getCreateActor().getOrgId() !=
					// null){
					// OrgVo orgVo =
					// orgService.getOrgById(merchant.getClientId(),
					// pendAuitListResVo.getCreateActor().getOrgId());
					// LogCvt.info("所属机构："+orgVo.getOrgName());
					// approvalMerchantRes.setOrgName(orgVo.getOrgName());
					// //所属机构
					// }
					LogCvt.info("待审核商户列表返回bessData数据：" + JSON
							.toJSONString(pendAuitListResVo.getBessData()));
					JSONObject json = JSONObject
							.fromObject(pendAuitListResVo.getBessData());
					if (json.containsKey(
							BessDataEnum.merchantUserId.getKey())) {
						approvalMerchantRes.setMerchantUserId(json.getString(
								BessDataEnum.merchantUserId.getKey()));// 商户用户id
					}
					// if (json.containsKey("merchantId")) {
					// approvalMerchantRes
					// .setMerchantId(json.getString("merchantId")); // 商户id
					// }
					approvalMerchantRes
							.setMerchantId(pendAuitListResVo.getBessId());
					if (json.containsKey(BessDataEnum.merchantName.getKey())) {
						approvalMerchantRes.setMerchantName(json
								.getString(BessDataEnum.merchantName.getKey())); // 商户名称
					}
					if (json.containsKey(BessDataEnum.newOrgCode.getKey())) {
						OrgVo orgVo = orgService.getOrgById(
								merchant.getClientId(), json.getString(
										BessDataEnum.newOrgCode.getKey()));
						LogCvt.info("所属机构：" + orgVo.getOrgName());
						approvalMerchantRes.setOrgName(orgVo.getOrgName()); // 所属机构
					}
					// 商户类型
					StringBuilder name = new StringBuilder();
					if (json.containsKey(BessDataEnum.typeInfo.getKey())) {
						LogCvt.info("商户类型:" + json
								.getString(BessDataEnum.typeInfo.getKey()));
						String type = json
								.getString(BessDataEnum.typeInfo.getKey())
								.substring(1,
										json.getString(
												BessDataEnum.typeInfo.getKey())
										.length() - 1);
						if (type.contains(",")) {
							String[] typeId1 = type.split(",");
							for (int i = 0; i < typeId1.length; i++) {
								MerchantTypeVo merchantType = merchantTypeService
										.getMerchantTypeById(
												Long.parseLong(typeId1[i]),
												merchant.getClientId());
								LogCvt.info("**********商户类型***********"
										+ JSON.toJSONString(merchantType));
								name.append(merchantType.getTypeName());
								name.append(",");
								approvalMerchantRes.setType(
										name.substring(0, name.length() - 1)); // 商户类型
							}
						} else {
							MerchantTypeVo merchantType = merchantTypeService
									.getMerchantTypeById(Long.parseLong(type),
											merchant.getClientId());
							approvalMerchantRes
									.setType(merchantType.getTypeName());
						}

					}

					approvalMerchantRes.setTaskId(
							pendAuitListResVo.getNextActor().getTaskId()); // 审核任务id
					merchantResList.add(approvalMerchantRes);
				}
			}
			// 封装返回分页信息
			setPagesValueToResMap(reMap, pendAuitListPageVo.getPage());
		} else {
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(),
					pendAuitListPageVo.getResult().getResultDesc());
		}

		reMap.put("merchantList", merchantResList);
		return reMap;
	}

	/**
	 * 
	 * setPendAuitListReqVo:(封装待审核商户列表查询请求参数).
	 *
	 * @author wufei 2015-10-26 下午05:34:12
	 * @param merchant
	 * @param originVo
	 * @return
	 * @throws ParseException
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 *
	 */
	private GetPendAuitListReqVo setPendAuitListReqVo(MerchantReq merchant,
			OriginVo originVo, GetPendAuitListReqVo pendAuitListReqVo)
					throws ParseException, JsonGenerationException,
					JsonMappingException, IOException {
		Map<Restrictions, String> orBessData = new HashMap<Restrictions, String>();
		Map<Restrictions, String> andBessData = new HashMap<Restrictions, String>();
		pendAuitListReqVo.setOrigin(originVo);
		pendAuitListReqVo.setProcessType(ProcessTypeEnum.MERCHANT.getCode());

		if (StringUtil.isNotBlank(merchant.getStartDate())) {
			pendAuitListReqVo
					.setStarTime(DateUtil.DateFormat(merchant.getStartDate())); // 开始时间
		}
		if (StringUtil.isNotBlank(merchant.getEndDate())) {
			pendAuitListReqVo
					.setEndTime(DateUtil.DateFormat(merchant.getEndDate())); // 结束时间
		}
		Map<String, Object> tempMap = new HashMap<String, Object>();
		orBessData = BessDataUtil.getOrBessDataMapWithMerchantNameAndOutName(
				merchant.getMerchantName(), null, null, null, orBessData);
		LogCvt.info("商户分类：" + merchant.getCategory());
		if (StringUtil.isNotBlank(merchant.getCategory())) {
			tempMap.put(BessDataEnum.categoryInfo.getKey(), merchant.getCategory());
			andBessData.put(Restrictions.EQ, JSON.toJSONString(tempMap));
		}
		if(andBessData.size()>0){
			pendAuitListReqVo.setAndBessData(andBessData);
			LogCvt.info("andBessData:" + JSON.toJSONString(andBessData));
		}
		if (orBessData.size()>0) {
			pendAuitListReqVo.setOrBessData(orBessData);
			LogCvt.info("orBessData:" + JSON.toJSONString(orBessData));
		}
		return pendAuitListReqVo;
	}

	/**
	 * 
	 * setOrgNameValue:匹配所属机构名
	 *
	 * @author 明灿 2015年9月30日 上午11:13:40
	 * @param merchant
	 * @param merchantResList
	 * @param orgCodeList
	 *
	 */
	private void setOrgNameValue(MerchantReq merchant,
			List<MerchantRes> merchantResList, List<String> orgCodeList) {
		List<BankOrgVo> orgVoList = bankOrgService
				.getByOrgCode(merchant.getClientId(), orgCodeList);
		for (MerchantRes res : merchantResList) {
			for (BankOrgVo bankorgVo : orgVoList) {
				if (StringUtil.isNotEmpty(res.getOrgCode())) {
					if (res.getOrgCode().equals(bankorgVo.getOrgCode())) {
						res.setOrgName(bankorgVo.getOrgName());
						break;
					}
				}
			}
		}
	}

	/**
	 * 
	 * setCategoryAndTypeName:商户类型和所属分类
	 *
	 * @author 明灿 2015年9月30日 上午11:09:41
	 * @param merchantList
	 * @param merchantDetailList
	 * @param i
	 * @param res
	 *
	 */
	private void setCategoryAndTypeName(List<MerchantVo> merchantList,
			List<MerchantDetailVo> merchantDetailList, int i, MerchantRes res) {
		for (MerchantDetailVo merchantDetail : merchantDetailList) {
			if (merchantDetail.getCategoryInfo() != null
					&& merchantDetail.getCategoryInfo().size() > 0
					&& merchantDetail.getId()
							.equals(merchantList.get(i).getMerchantId())) {
				res.getCategory().append(
						merchantDetail.getCategoryInfo().get(0).getName());
			}
			List<TypeInfoVo> merchantTypeList = merchantDetail.getTypeInfo();
			if (merchantTypeList != null && merchantTypeList.size() > 0
					&& merchantDetail.getId()
							.equals(merchantList.get(i).getMerchantId())) {
				for (int j = 0; j < merchantTypeList.size(); j++) {
					if ((j + 1) == merchantTypeList.size()) {
						res.getType()
								.append(merchantTypeList.get(j).getTypeName());
					} else {
						res.getType().append(
								merchantTypeList.get(j).getTypeName() + ",");
					}
				}
			}
		}
	}

	/**
	 * 
	 * setSomeOtherValueToResVo:其他信息
	 *
	 * @author 明灿 2015年9月30日 上午11:03:49
	 * @param merchantResList
	 * @param merchantList
	 * @param orgCodeList
	 * @param i
	 * @param res
	 *
	 */
	private void setSomeOtherValueToResVo(List<MerchantRes> merchantResList,
			List<MerchantVo> merchantList, List<String> orgCodeList, int i,
			MerchantRes res) {
		// 营业执照
		res.setLicense(merchantList.get(i).getLicense());
		res.setMerchantId(merchantList.get(i).getMerchantId());
		res.setMerchantName(merchantList.get(i).getMerchantName());
		res.setContractBegintime(merchantList.get(i).getContractBegintime());
		res.setContractEndtime(merchantList.get(i).getContractEndtime());
		// 签约人员
		res.setContractStaff(merchantList.get(i).getContractStaff());
		if (StringUtil.isNotEmpty(merchantList.get(i).getOrgCode())) {
			res.setOrgCode(merchantList.get(i).getOrgCode());
			orgCodeList.add(merchantList.get(i).getOrgCode());
		}
		res.setUserOrgCode(merchantList.get(i).getUserOrgCode());
		res.setCreateTime(merchantList.get(i).getCreateTime());
		res.setAuditStage(merchantList.get(i).getAuditStage());
		res.setAuditState(merchantList.get(i).getAuditState());
		// 编辑审核状态 0-更新审核中1-正常(编辑审核通过)2-更新审核未通过
		res.setEditAuditState(merchantList.get(i).getEditAuditState());
		// 审核人
		res.setAuditStaff(merchantList.get(i).getAuditStaff());
		res.setAuditTime(merchantList.get(i).getAuditTime());
		res.setIsEnable(merchantList.get(i).isIsEnable());
		if ("0".equals(merchantList.get(i).getDisableStatus())) {
			res.setDisableStatus(merchantList.get(i).getDisableStatus());
			res.setEnable("签约");
		} else if ("1".equals(merchantList.get(i).getDisableStatus())) {
			res.setDisableStatus(merchantList.get(i).getDisableStatus());
			res.setEnable("禁用");
		} else {
			res.setDisableStatus(merchantList.get(i).getDisableStatus());
			res.setEnable("已解约");
		}
		// 商户组织机构代码
		res.setCompanyCredential(merchantList.get(i).getCompanyCredential());
		merchantResList.add(res);
	}

	/**
	 * 
	 * setMerchantUserValue:封装商户用户信息
	 *
	 * @author 明灿 2015年9月29日 上午11:01:31
	 * @param merchantList
	 *            server商户vo
	 * @param merchantUserList
	 *            返回前端商户用户vo
	 * @param i
	 *            外循环角标
	 * @param res
	 *            返回前端商户vo
	 * @param j
	 *            内循环角标
	 *
	 */
	private void setMerchantUserValue(List<MerchantVo> merchantList,
			List<MerchantUserVo> merchantUserList, int i, MerchantRes res,
			int j) {
		if (merchantList.get(i).getMerchantId()
				.equals(merchantUserList.get(j).getMerchantId())) {
			if (StringUtil.isNotEmpty(merchantUserList.get(j).getUsername())) {
				res.setMerchantUserName(merchantUserList.get(j).getUsername());
			} else {
				res.setMerchantUserName("");
			}
			res.setMerchantUserId(merchantUserList.get(j).getId());
			if (StringUtil.isNotEmpty(merchantUserList.get(j).getPhone())) {
				res.setPhone(merchantUserList.get(j).getPhone());
			} else {
				res.setPhone("");
			}
		}
	}

	/**
	 * 
	 * reqPageValue:分页请求参数
	 *
	 * @author 明灿 2015年9月29日 上午10:45:54
	 * @param merchant
	 *            请求vo
	 * @return
	 *
	 */
	private PageVo reqPageValue(MerchantReq merchant) {
		PageVo page = new PageVo();
		page.setPageNumber(merchant.getPageNumber());
		page.setPageSize(merchant.getPageSize());
		page.setLastPageNumber(merchant.getLastPageNumber());
		page.setFirstRecordTime(merchant.getFirstRecordTime());
		page.setLastRecordTime(merchant.getLastRecordTime());
		return page;
	}

	/**
	 * 
	 * setPagesValueToResMap:封装分页信息
	 *
	 * @author 明灿 2015年9月29日 上午10:42:10
	 * @param reMap
	 *            返回前端的Map集合
	 * @param pageRes
	 *            server返回的page信息
	 *
	 */
	private void setPagesValueToResMap(HashMap<String, Object> reMap,
			PageVo pageVo) {
		if (pageVo != null) {
			reMap.put("pageCount", pageVo.getPageCount());
			reMap.put("totalCount", pageVo.getTotalCount());
			reMap.put("pageNumber", pageVo.getPageNumber());
			reMap.put("lastPageNumber", pageVo.getLastPageNumber());
			reMap.put("firstRecordTime", pageVo.getFirstRecordTime());
			reMap.put("lastRecordTime", pageVo.getLastRecordTime());
		} else {
			reMap.put("pageCount", 0);
			reMap.put("totalCount", 0);
			reMap.put("pageNumber", 1);
			reMap.put("lastPageNumber", 0);
			reMap.put("firstRecordTime", 0);
			reMap.put("lastRecordTime", 0);
		}
	}

	/**
	 * 商户新增
	 * 
	 * @param merchantManageVo
	 * @return
	 * @throws TException
	 * @throws ParseException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public HashMap<String, Object> merchantManageAdd(MerchantManageVo mm,
			HttpServletRequest req, OriginVo originVo)
					throws TException, ParseException, JsonGenerationException,
					JsonMappingException, IOException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();

		/**
		 * 商户添加非空校验
		 */
		Map<String, Object> verifyMap = notNull4MerchantAdd(mm);
		if (verifyMap.size() > 0) {
			return (HashMap<String, Object>) verifyMap;
		}
		MerchantUserVo user = new MerchantUserVo();
		Long merchantUserId = null;
		/**
		 * 新增前判断商户用户是否存在
		 */
		user.setUsername(mm.getLoginName());
		LogCvt.info("======商户新增，判断商户用户名是否存在:开始======");
		MerchantUserVo merchantUser = merchantUserService
				.getMerchantUserByUsername(mm.getLoginName(),
						loginService.getOriginVo(req));
		LogCvt.info("======商户新增，判断商户用户名是否存在:结束======");
		if (merchantUser != null
				&& StringUtil.isNotEmpty(merchantUser.getUsername())) {
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "商户用户已存在");
			return reMap;
		} else {
			/**
			 * 不存在,添加商户
			 */
			BankOperatorVo bankOperatorVoRes = (BankOperatorVo) req
					.getAttribute(Constants.BANKOPERATOR);
			MerchantVoReq merchantReq = new MerchantVoReq();
			MerchantVo m = new MerchantVo();
			/**
			 * 增加外包公司字段
			 */
			if (mm.getIsOutsource()) {
				m.setIsOutsource(true);
				m.setCompanyId(mm.getCompanyId());
			} else {
				m.setIsOutsource(false);
			}
			/**
			 * 其他信息注入
			 */
			setSomeOtherValue4Add(mm, bankOperatorVoRes, m);

			if (mm.getCategoryType() != null
					&& mm.getCategoryType().size() > 0) {
				/**
				 * 商户分类
				 */
				setTypeInfo4Add(mm, merchantReq);
			}

			if (mm.getCategory() != null) {
				/**
				 * 商户类型
				 */
				setCategoryInfo4Add(mm, merchantReq);
			}
			// LogCvt.info("======商户新增，根据当前机构" + mm.getMyOrgCode()
			// + "计算审核机构:开始======");
			// ClientMerchantAuditOrgCodeVo merchantAuditVo =
			// clientMerchantAuditService
			// .getClientMerchantAuditByOrgCode(m.getClientId(),
			// bankOperatorVoRes.getOrgCode(), "1");// 1-审核 2-重置密码
			// LogCvt.info("商户新增，根据当前机构计算审核机构返回数据："
			// + JSON.toJSONString(merchantAuditVo));
			//
			// if(!StringUtil.isNotEmpty(merchantAuditVo.getStartAuditOrgCode())
			// &&
			// !StringUtil.isNotEmpty(merchantAuditVo.getEndAuditOrgCode())){
			// // 起始审核机构和终审机构为空
			// reMap.put("code", EnumTypes.fail.getCode());
			// reMap.put("message", "不能录入商户");
			// return reMap;
			// }else{
			// /**
			// * 注入审核机构信息
			// */
			// setAuditOrgCodeInfo(m, merchantAuditVo);
			// }

			// LogCvt.info("======商户新增，根据当前机构" + mm.getMyOrgCode()
			// + "计算审核机构:结束======");
			merchantReq.setMerchantVo(m);
			// 新增商户信息
			LogCvt.info("======商户新增，新增商户:开始======");
			MerchantAddVoRes merchantAddRes = merchantService
					.addMerchant(loginService.getOriginVo(req), merchantReq);
			LogCvt.info("======商户新增，新增商户:结束======" + " 返回数据"
					+ JSON.toJSONString(merchantAddRes));
			if (EnumTypes.success.getCode()
					.equals(merchantAddRes.getResult().getResultCode())) {
				/**
				 * 商户添加成功,添加一个虚拟的门店挂在商户下
				 */
				MerchantAccountVo account = new MerchantAccountVo();
				/**
				 * 注入虚拟的商户门店属性
				 */
				this.setMerchantAccountVo4AddMerchant(mm, merchantAddRes, account);
				// 新增商户门店账号
				LogCvt.info("======商户新增，新增商户门店账号:开始======");
				MerchantAccountAddVoRes merchantAccountRes = merchantAccountService
						.addMerchantAccount(loginService.getOriginVo(req),
								account);
				LogCvt.info(
						"======商户新增，新增商户门店账号:结束======" + "  商户新增，新增商户门店账号返回:"
								+ JSON.toJSONString(merchantAccountRes));
				if (EnumTypes.success.getCode().equals(
						merchantAccountRes.getResult().getResultCode())) {
					/**
					 * 添加商户门店成功,添加商户用户
					 */
					MerchantUserVo merchantUserVo = new MerchantUserVo();
					/**
					 * 商户用户属性注入
					 */
					this.setMerchantUserValue4AddMerchantAccount(mm, merchantAddRes,
							merchantUserVo);
					// 新增商户用户信息
					LogCvt.info("======商户新增，新增商户用户:开始======");
					MerchantUserAddVoRes merchantUserRes = merchantUserService
							.addMerchantUser(loginService.getOriginVo(req),
									merchantUserVo);
					LogCvt.info(
							"======商户新增，新增商户用户:结束======" + "  商户新增，新增商户用户返回:"
									+ JSON.toJSONString(merchantUserRes));

					if (EnumTypes.success.getCode()
							.equals(merchantUserRes.getResult().getResultCode())
							&& merchantUserRes.getId() > 0) {
						merchantUserId = merchantUserRes.getId(); // 商户用户id

						ResultVo rVo = this.addFinityUserResource(
								merchantUserRes.getId(), m.getClientId());// 增加商户资源信息
						if (!EnumTypes.success.getCode()
								.equals(rVo.getResultCode())) {
							// 新增商户资源失败 删除新增的商户用户和商户门店
							LogCvt.info("======新增商户资源失败，删除商户:开始======");
							merchantService.removeMerchant(
									loginService.getOriginVo(req),
									merchantAddRes.getMerchantId());
							LogCvt.info("======新增商户资源失败，删除商户:结束======");

							LogCvt.info("======新增商户资源失败，删除商户门店账号:开始======");
							merchantAccountService.removeMerchantAccount(
									loginService.getOriginVo(req),
									merchantAccountRes.getId());
							LogCvt.info("======新增商户资源失败，删除商户门店账号:结束======");

							LogCvt.info("======新增商户资源失败，删除商户用户:开始======");
							merchantUserVo.setId(merchantUserRes.getId());
							merchantUserService.deleteMerchantUser(
									loginService.getOriginVo(req),
									merchantUserVo);
							LogCvt.info("======新增商户资源失败，删除商户用户:结束======");

							reMap.put(ResultEnum.CODE.getCode(),
									EnumTypes.fail.getCode());
							reMap.put(ResultEnum.MESSAGE.getCode(),
									rVo.getResultDesc());
							return reMap;
						}
						MerchantVo merchant = merchantService
								.getMerchantByMerchantId(
										merchantAddRes.getMerchantId());

						/*
						 * //审核流程号从配置文件读取 String processId =
						 * Constants.get("processId.merchant.add");
						 * LogCvt.info("审核流程号从配置文件读取："+processId);
						 */

						// 审核类型详情:0-新增,1-更新
						String processTypeDetail = "0"; // 新增

						// 新增成功后进入审核流程，创建审核实例
						CreateInstanceResVo createInstanceResVo = this.createInstance(
								mm, originVo, merchant,
								merchantAddRes.getMerchantId(),
								String.valueOf(merchantUserId),
								processTypeDetail);
						if (!EnumTypes.success.getCode()
								.equals(createInstanceResVo.getResult()
										.getResultCode())) {
							// 创建审核实例失败 删除新增的商户用户和商户门店
							LogCvt.info("======创建审核实例失败，删除商户:开始======");
							merchantService.removeMerchant(
									loginService.getOriginVo(req),
									merchantAddRes.getMerchantId());
							LogCvt.info("======创建审核实例失败，删除商户:结束======");

							LogCvt.info("======创建审核实例失败，删除商户门店账号:开始======");
							merchantAccountService.removeMerchantAccount(
									loginService.getOriginVo(req),
									merchantAccountRes.getId());
							LogCvt.info("======创建审核实例失败，删除商户门店账号:结束======");

							LogCvt.info("======创建审核实例失败，删除商户用户:开始======");
							merchantUserVo.setId(merchantUserRes.getId());
							merchantUserService.deleteMerchantUser(
									loginService.getOriginVo(req),
									merchantUserVo);
							LogCvt.info("======创建审核实例失败，删除商户用户:结束======");

							reMap.put(ResultEnum.CODE.getCode(),
									EnumTypes.fail.getCode());
							reMap.put(ResultEnum.MESSAGE.getCode(),
									createInstanceResVo.getResult()
									.getResultDesc());
						} else {
							// 商户用户添加成功
							if (m.getAuditState()
									.equals(AuditFlagEnum.ACCEPTED.getCode())) {
								/**
								 * 新增成功发送短信,登录账号和密码等
								 */
								SmsMessageVo smsMessage = new SmsMessageVo();
								/**
								 * 短信内容注入
								 */
								sendMessageWhenAddSuccess(mm, smsMessage);

								LogCvt.info(
										"======商户新增，审核状态为" + m.getAuditState()
												+ ",则发送短信:开始======");
								smsMessageService.sendSMS(smsMessage);
								LogCvt.info(
										"======商户新增，审核状态为" + m.getAuditState()
												+ ",则发送短信:结束======");
							}
							reMap.put(ResultEnum.CODE.getCode(),
									merchantAddRes.getResult().getResultCode());
							reMap.put(ResultEnum.MESSAGE.getCode(),
									merchantAddRes.getResult().getResultDesc());
						}
						return reMap;
					} else {
						/**
						 * 添加商户用户失败
						 */
						// 新增商户用户不成功则删除上面新增的商户信息以及商户门店账号信息
						LogCvt.info("======商户用户新增失败，删除商户:开始======");
						merchantService.removeMerchant(
								loginService.getOriginVo(req),
								merchantAddRes.merchantId);
						LogCvt.info("======商户用户新增失败，删除商户:结束======");

						LogCvt.info("======商户用户新增失败，删除商户门店账号:开始======");
						merchantAccountService.removeMerchantAccount(
								loginService.getOriginVo(req),
								merchantAccountRes.getId());
						LogCvt.info("======商户用户新增失败，删除商户门店账号:结束======");

						reMap.put(ResultEnum.CODE.getCode(),
								EnumTypes.fail.getCode());
						reMap.put(ResultEnum.MESSAGE.getCode(),
								merchantUserRes.getResult().getResultDesc());
						return reMap;
					}
				} else {
					/**
					 * 添加商户门店失败
					 */
					// 新增商户门店账号不成功则删除上面新增的商户信息
					MerchantVo meVo = new MerchantVo();
					meVo.setMerchantId(merchantAddRes.merchantId);
					LogCvt.info("======商户门店账号新增失败，删除商户:开始======");
					merchantService.removeMerchant(
							loginService.getOriginVo(req),
							merchantAddRes.merchantId);
					LogCvt.info("======商户门店账号新增失败，删除商户:结束======");

					reMap.put(ResultEnum.CODE.getCode(),
							EnumTypes.fail.getCode());
					reMap.put(ResultEnum.MESSAGE.getCode(),
							merchantAccountRes.getResult().getResultDesc());
					return reMap;
				}
			} else {
				reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
				reMap.put(ResultEnum.MESSAGE.getCode(),
						merchantAddRes.getResult().getResultDesc());
				return reMap;
			}
		}
	}

	@SuppressWarnings("unused")
	private String getProcessId(String propertyName) throws IOException {
		Properties prop = new Properties();
		InputStream in = Object.class
				.getResourceAsStream("/coremodule-bank.properties");
		prop.load(in);
		String processId = prop.getProperty(propertyName).trim();
		return processId;
	}

	/**
	 * 
	 * sendMessageWhenAddSuccess:商户添加成功短信推送内容注入
	 *
	 * @author 明灿 2015年9月30日 下午2:26:19
	 * @param mm
	 * @param m
	 * @param smsMessage
	 *
	 */
	private void sendMessageWhenAddSuccess(MerchantManageVo mm,
			SmsMessageVo smsMessage) {
		smsMessage.setClientId(mm.getClientId());
		smsMessage.setMobile(mm.getLoginPhone());
		smsMessage.setSendUser(mm.getLoginName());
		List<String> str = new ArrayList<String>();
		str.add(mm.getLoginName());
		str.add(mm.getLoginPhone());
		str.add("111111");
		smsMessage.setValues(str);
		smsMessage.setSmsType(SmsTypeEnum.merchantAddUser.getValue());
	}

	/**
	 * 
	 * setMerchantUserValue4AddMerchantAccount:商户用户属性注入
	 *
	 * @author 明灿 2015年9月30日 下午2:22:22
	 * @param mm
	 * @param merchantAddRes
	 * @param merchantUserVo
	 *
	 */
	private void setMerchantUserValue4AddMerchantAccount(MerchantManageVo mm,
			MerchantAddVoRes merchantAddRes, MerchantUserVo merchantUserVo) {
		merchantUserVo.setMerchantId(merchantAddRes.getMerchantId());
		merchantUserVo.setClientId(mm.getClientId());
		merchantUserVo.setEmail(null);
		// merchantUserVo.setMerchantRoleId(merchantRoleService.getMerchantRoleByClientIdAndRoleDesc(mm.getClientId(),
		// "Role_Administrators").getId());
		merchantUserVo.setMerchantRoleId(Constants.MERCHANT_ROLE_ID);
		merchantUserVo.setUsername(mm.getLoginName());
		merchantUserVo.setPhone(mm.getLoginPhone());
		merchantUserVo.setIsRest(false);
		merchantUserVo.setIsDelete(false);
		merchantUserVo.setOutletId("0");
		merchantUserVo.setPassword(mm.getLoginPassword());
	}

	/**
	 * 
	 * setMerchantAccountVo4AddMerchant:注入虚拟的商户门店属性
	 *
	 * @author 明灿 2015年9月30日 下午2:19:12
	 * @param mm
	 * @param merchantAddRes
	 * @param account
	 *
	 */
	private void setMerchantAccountVo4AddMerchant(MerchantManageVo mm,
			MerchantAddVoRes merchantAddRes, MerchantAccountVo account) {
		account.setAcctName(mm.getAcctName());
		account.setAcctNumber(mm.getAcctNumber());
		account.setAcctType("1");
		account.setClientId(mm.getClientId());
		account.setMerchantId(merchantAddRes.getMerchantId());
		account.setOpeningBank(mm.getOpeningBank());
		account.setOutletId("0");
		account.setIsDelete(false);
	}

	/**
	 * 
	 * setAuditOrgCodeInfo:注入审核机构信息,起审和终审等
	 *
	 * @author 明灿 2015年9月30日 下午2:19:42
	 * @param m
	 * @param merchantAuditVo
	 *
	 */
	@SuppressWarnings("unused")
	private void setAuditOrgCodeInfo(MerchantVo m,
			ClientMerchantAuditOrgCodeVo merchantAuditVo) {
		if (StringUtil.isNotEmpty(merchantAuditVo.getStartAuditOrgCode())) {
			m.setAuditStartOrgCode(merchantAuditVo.getStartAuditOrgCode());
		} else {
			m.setAuditStartOrgCode(null);
		}
		if (StringUtil.isNotEmpty(merchantAuditVo.getEndAuditOrgCode())) {
			m.setAuditEndOrgCode(merchantAuditVo.getEndAuditOrgCode());
		} else {
			m.setAuditEndOrgCode(null);
		}
		if (StringUtil.isNotEmpty(merchantAuditVo.getStartAuditOrgCode())) {
			m.setAuditOrgCode(merchantAuditVo.getStartAuditOrgCode());
		} else {
			m.setAuditOrgCode(merchantAuditVo.getEndAuditOrgCode());
		}
	}

	/**
	 * 
	 * setSomeOtherValue4Add:添加商户时部分商户信息注入
	 *
	 * @author 明灿 2015年9月30日 下午2:06:36
	 * @param mm
	 * @param bankOperatorVoRes
	 * @param m
	 *
	 */
	private void setSomeOtherValue4Add(MerchantManageVo mm,
			BankOperatorVo bankOperatorVoRes, MerchantVo m) {
		m.setUserOrgCode(bankOperatorVoRes.getOrgCode());
		// 商户取全称
		m.setMerchantName(mm.getMerchantName());
		m.setPhone(mm.getPhone()); // 联系人电话
		m.setContactPhone(mm.getContactPhone()); // 联系人手机
		m.setContactName(mm.getContactName());
		m.setLegalName(mm.getLegalName());
		m.setLegalCredentType(mm.getLegalCredentType());
		m.setLegalCredentNo(mm.getLegalCredentNo());
		m.setLicense(mm.getLicense());
		// 签约人
		m.setContractStaff(mm.getContractStaff());
		m.setOrgCode(mm.getOpeningBank());
		m.setClientId(mm.getClientId());
		// 商户组织机构代码
		m.setCompanyCredential(mm.getCompanyCredential());
		m.setMerchantStatus(false);
		m.setIsEnable(false);
		m.setAuditState(AuditFlagEnum.NEW.getCode()); // 默认值-待审核
		m.setIsTop(false);
	}

	/**
	 * 
	 * setCategoryInfo4Add:商户类型
	 *
	 * @author 明灿 2015年9月30日 下午2:01:24
	 * @param mm
	 * @param merchantReq
	 *
	 */
	private void setCategoryInfo4Add(MerchantManageVo mm,
			MerchantVoReq merchantReq) {
		List<CategoryInfoVo> categoryList = new ArrayList<CategoryInfoVo>();// 商户类型
		CategoryInfoVo category = new CategoryInfoVo();
		category.setCategoryId(mm.getCategory());
		categoryList.add(category);
		merchantReq.setCategoryInfoVoList(categoryList);
	}

	/**
	 * 
	 * setTypeInfo4Add:商户分类
	 *
	 * @author 明灿 2015年9月30日 下午2:01:10
	 * @param mm
	 * @param merchantReq
	 *
	 */
	private void setTypeInfo4Add(MerchantManageVo mm,
			MerchantVoReq merchantReq) {
		List<TypeInfoVo> typeList = new ArrayList<TypeInfoVo>(); // 商户分类
		for (int i = 0; i < mm.getCategoryType().size(); i++) {
			if (mm.getCategoryType().get(i) != null) {
				TypeInfoVo typeInfo = new TypeInfoVo();
				typeInfo.setMerchantTypeId(mm.getCategoryType().get(i));
				typeList.add(typeInfo);
			}
		}
		merchantReq.setTypeInfoVoList(typeList);
	}

	/**
	 * 商户新增校验
	 * 
	 * @param mm
	 * @param reMap
	 * @return
	 */
	private Map<String, Object> notNull4MerchantAdd(MerchantManageVo mm) {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		if (!mm.getLoginName().matches("^[A-Za-z0-9]{4,36}$")) {// ^[a-zA-Z]{1}[a-zA-Z0-9|-|_]{3,19}$
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "商户用户名不合法");
			return reMap;
		}
		if (!mm.getPhone().matches("[\\d+-]{1,}$")) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "联系人手机不能为空");
			return reMap;
		}
		if (mm.getLicense().length() > 64) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "营业执照号不能超过64个字符");
			return reMap;
		}
		if (StringUtil.isNotEmpty(mm.getMerchantName())
				&& mm.getMerchantName().length() > 32) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "商户简称不能超过32个字符");
			return reMap;
		}
		if (mm.getContactPhone().matches("/^1[3|5|7|8][0-9]{9}$/")) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "请输入正确的手机号码");
			return reMap;
		}
		if (StringUtil.isBlank(mm.getContractStaff())) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "签约人不能为空");
			return reMap;
		}
		if (!(mm.getContractStaff()
				.matches("[\\u4e00-\\u9fa5a-zA-Z0-9]{1,32}"))) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "签约人格式不正确");
			return reMap;
		}
		if (mm.getCategoryType().isEmpty()) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "商户类型不能为空");
			return reMap;
		}
		if (StringUtil.isBlank(mm.getLegalName())) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "法人名不能为空");
			return reMap;
		}
		if (!mm.getLegalName().matches("^[\u4e00-\u9fa5_a-zA-Z]{1,16}$")) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "请输入正确的法人名");
			return reMap;
		}
		if (!mm.getAcctName().matches("^[\u4e00-\u9fa5]{1,64}$")) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "请输入正确的收款账户名");
			return reMap;
		}
		if (null == mm.getLegalCredentType()) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "请选择证件类型");
			return reMap;
		}
		if (StringUtil.isBlank(mm.getLegalCredentNo())) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "请输入正确的证件号码");
			return reMap;
		}
		if (null == mm.getCategory()) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "请选择所属分类");
			return reMap;
		}
		if (StringUtil.isBlank(mm.getOpeningBank())) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "开户行组织编码不能为空");
			return reMap;
		}
		if (mm.getIsOutsource() && null == mm.getCompanyId()) {
			reMap.put(ResultEnum.FAIL.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "外包公司id不能为空");
			return reMap;
		}
		return reMap;
	}

	/**
	 * 商户修改
	 * 
	 * @param merchantManage
	 * @return
	 * @throws TException
	 * @throws ParseException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */
	public HashMap<String, Object> merchantManageUpdate(MerchantManageVo mm,
			HttpServletRequest req, OriginVo originVo)
					throws TException, ParseException, IOException {

		LogCvt.info("=====>商户修改请求参数<=====" + JSON.toJSONString(mm));
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		// 审核状态
		LogCvt.info("======商户修改，根据商户Id" + mm.getMerchantId() + "商户:开始======");
		MerchantVo m = merchantService
				.getMerchantByMerchantId(mm.getMerchantId());
		LogCvt.info("======商户修改返回数据" + JSON.toJSONString(m));
		MerchantVoReq merchantReq = new MerchantVoReq();
		// 是否解约
		if (mm.getIsEnable() != null) {

			// 日志控制
			OriginVo orgVo = loginService.getOriginVo(req);
			InvalidProductBatchVo invalidVo = new InvalidProductBatchVo();
			invalidVo.setClientId(mm.getClientId());
			invalidVo.setMerchantId(mm.getMerchantId());
			/**
			 * 根据商户id下架所有商品
			 */
			LogCvt.info("======商户解约，根据商户Id" + mm.getMerchantId()
					+ "下架所有商品商户:开始======");
			ResultVo result = productService.invalidProductBatch(orgVo,
					invalidVo);
			LogCvt.info("======商户解约，根据商户Id" + mm.getMerchantId()
					+ "下架所有商品商户:结束======" + "  商户解约，下架商品商户返回:"
					+ JSON.toJSONString(result));
			if (result.getResultCode().equals(EnumTypes.success.getCode())) {
				/**
				 * 下架成功
				 */
				MerchantVo merchantVo = new MerchantVo();
				merchantVo.setMerchantId(mm.getMerchantId());
				merchantVo.setOperateTime(System.currentTimeMillis()); // 操作时间
				merchantVo.setOperateUser(mm.getOperator()); // 操作人
				LogCvt.info("======商户解约:调用接口deleteMerchant传入条件："
						+ JSON.toJSONString(merchantVo));
				result = merchantService.deleteMerchant(orgVo, merchantVo);
				LogCvt.info("======商户解约:返回结果" + JSON.toJSONString(result));
				if (result.getResultCode()
						.equals(EnumTypes.success.getCode())) {
					/**
					 * 商户解约成功
					 */
					reMap.put(ResultEnum.CODE.getCode(),
							result.getResultCode());
					reMap.put(ResultEnum.MESSAGE.getCode(),
							result.getResultDesc());
				} else {
					reMap.put(ResultEnum.CODE.getCode(),
							EnumTypes.fail.getCode());
					reMap.put(ResultEnum.MESSAGE.getCode(),
							result.getResultDesc());
				}
			} else {
				reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
				reMap.put(ResultEnum.MESSAGE.getCode(), result.getResultDesc());
			}
			return reMap;
		}
		// /**
		// * 修改时非空等校验
		// */
		// Map<String, Object> notNullMap = notNull4MerchantAdd(mm);
		// if (notNullMap.size() > 0) {
		// return (HashMap<String, Object>) notNullMap;
		// }

		BankOperatorVo bankOperatorVoRes = (BankOperatorVo) req
				.getAttribute(Constants.BANKOPERATOR);
		// 匹配机构号,判断是否有权修改
		if (StringUtil.isNotEmpty(m.getUserOrgCode())) {
			if (!m.getUserOrgCode().equals(bankOperatorVoRes.getOrgCode())) {
				reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
				reMap.put(ResultEnum.MESSAGE.getCode(), "不能修改他人录入的商户");
				return reMap;
			}
		}
		LogCvt.info("************审核状态" + m.getAuditState());
		if (m.getAuditState().equals(AuditFlagEnum.ACCEPTED.getCode())) { // 审核通过的走auditMerchant接口

			// 审核信息修改调用新接口
			MerchantAuditVoRes merchantAuditVoRes = null;

			// 审核通过后，调用auditMerchant接口的请求参数
			MerchantTempVoReq merchantTempVoReq = new MerchantTempVoReq();
			MerchantTempVo merchantTempVo = new MerchantTempVo();
			merchantTempVo.setAuditId("0");
			// 注入修改的属性值
			this.setMerchantTempVoReq(mm, merchantTempVo, merchantTempVoReq);
			LogCvt.info("更新商户审核查询条件:" + JSON.toJSONString(merchantTempVoReq));
			merchantAuditVoRes = merchantAuditService.auditMerchant(
					loginService.getOriginVo(req), merchantTempVoReq);
			LogCvt.info("merchantAuditService.auditMerchant 更新商户审核信息返回:"
					+ JSON.toJSONString(merchantAuditVoRes));
			if (EnumTypes.success.getCode()
					.equals(merchantAuditVoRes.getResult().getResultCode())) {

				// 审核流程号从配置文件读取
				// String processId =
				// Constants.get("processId.merchant.update");

				// 审核类型详情:0-新增,1-更新
				String processTypeDetail = "1"; // 更新

				// 创建审核实例
				CreateInstanceResVo createInstanceResVo = this.createInstance(mm,
						originVo, m, mm.getMerchantId(), mm.getMerchantUserId(),
						processTypeDetail);
				if (!EnumTypes.success.getCode().equals(
						createInstanceResVo.getResult().getResultCode())) {
					reMap.put(ResultEnum.CODE.getCode(),
							EnumTypes.fail.getCode());
					reMap.put(ResultEnum.MESSAGE.getCode(),
							createInstanceResVo.getResult().getResultDesc());
				}

				if (m.getAuditState()
						.equals(AuditFlagEnum.ACCEPTED.getCode())) {
					MerchantUserVo merchantUser = merchantUserService
							.getMerchantUserById(
									Long.parseLong(mm.getMerchantUserId()));
					SmsMessageVo smsMessage = new SmsMessageVo(); // 新增成功发送短信
					smsMessage.setClientId(mm.getClientId());
					smsMessage.setMobile(merchantUser.getPhone());
					smsMessage.setSendUser(merchantUser.getUsername());
					List<String> str = new ArrayList<String>();
					str.add(merchantUser.getUsername());
					str.add(merchantUser.getPhone());
					// 重置密码
					str.add("111111");
					smsMessage.setValues(str);
					smsMessage
							.setSmsType(SmsTypeEnum.merchantAddUser.getValue());
					LogCvt.info("======商户修改，审核状态为" + m.getAuditState()
							+ ",则发送短信:开始======");
					smsMessageService.sendSMS(smsMessage);
					LogCvt.info("======商户修改，审核状态为" + m.getAuditState()
							+ ",则发送短信:结束======");
				}

				reMap.put(ResultEnum.CODE.getCode(),
						merchantAuditVoRes.getResult().getResultCode());
				reMap.put(ResultEnum.MESSAGE.getCode(),
						merchantAuditVoRes.getResult().getResultDesc());
			} else {
				reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
				reMap.put(ResultEnum.MESSAGE.getCode(),
						merchantAuditVoRes.getResult().getResultDesc());
			}

		} else {
			MerchantUserVo merchantUser = new MerchantUserVo();
			if (StringUtil.isNotEmpty(mm.getMerchantUserId())) {
				LogCvt.info("======商户修改，根据商户用户Id" + mm.getMerchantUserId()
						+ "查询用户信息:开始======");
				merchantUser = merchantUserService.getMerchantUserById(
						Long.valueOf(mm.getMerchantUserId()));
				LogCvt.info("======商户修改，根据商户用户Id" + mm.getMerchantUserId()
						+ "查询用户信息:结束======" + "  商户修改，根据商户用户Id查询用户信息返回:"
						+ JSON.toJSONString(merchantUser));
				/**
				 * 如果修改登录名,则做唯一校验
				 */
				if (StringUtil.isNotBlank(mm.getLoginName())) {

					if (!mm.getLoginName().equals(merchantUser.getUsername())) {
						LogCvt.info("======商户修改，判断商户用户登录名："
								+ merchantUser.getUsername()
								+ "是否已存在:开始======");
						MerchantUserVo user = merchantUserService
								.getMerchantUserByUsername(mm.getLoginName(),
										loginService.getOriginVo(req));
						LogCvt.info("======商户修改，判断商户用户登录名："
								+ merchantUser.getUsername() + "是否已存在:结束======"
								+ "  返回:" + JSON.toJSONString(user));
						if (user != null
								&& StringUtil.isNotEmpty(user.getUsername())) {
							reMap.put(ResultEnum.CODE.getCode(),
									EnumTypes.fail.getCode());
							reMap.put(ResultEnum.MESSAGE.getCode(), "商户用户已存在");
							return reMap;
						} else {
							merchantUser.setUsername(mm.getLoginName());// 默认获取登录名
						}
					}
				}

				if (mm.getLoginPhone() != null) {
					merchantUser.setPhone(mm.getLoginPhone());// 默认登录人的手机
				}
				if (StringUtil.isNotBlank(mm.getLoginPassword())) {
					merchantUser.setPassword(mm.getLoginPassword());
				}
				// 修改商户用户
				LogCvt.info("======商户修改，修改商户用户信息:开始======");
				ResultVo result = merchantUserService.updateMerchantUser(
						loginService.getOriginVo(req), merchantUser);
				LogCvt.info("======商户修改，修改商户用户信息:结束======" + "  返回:"
						+ JSON.toJSONString(result));
				if (!EnumTypes.success.getCode()
						.equals(result.getResultCode())) {
					reMap.put(ResultEnum.CODE.getCode(),
							EnumTypes.fail.getCode());
					reMap.put(ResultEnum.MESSAGE.getCode(),
							result.getResultDesc());
					return reMap;
				}
			} else {// 没有商户用户Id则判断登录名是否有值，如果有则新增商户用户
				if (StringUtil.isNotEmpty(mm.getLoginName())
						&& StringUtil.isNotEmpty(mm.getLoginPhone())) {
					MerchantUserVo merchantUserVo = new MerchantUserVo();
					/**
					 * 部分属性注入
					 */
					this.setSomeValueIfAdd(mm, merchantUserVo);
					// 新增商户用户信息
					LogCvt.info("======商户修改，没有商户用户则新增商户用户:开始======");
					MerchantUserAddVoRes merchantUserRes = merchantUserService
							.addMerchantUser(loginService.getOriginVo(req),
									merchantUserVo);
					LogCvt.info("======商户修改，没有商户用户则新增商户用户:结束======" + "  返回:"
							+ JSON.toJSONString(merchantUserRes));
					if (!EnumTypes.success.getCode().equals(
							merchantUserRes.getResult().getResultCode())) {
						reMap.put(ResultEnum.CODE.getCode(),
								EnumTypes.fail.getCode());
						reMap.put(ResultEnum.MESSAGE.getCode(),
								merchantUserRes.getResult().getResultDesc());
						return reMap;
					}
					this.addFinityUserResource(merchantUserRes.getId(),
							mm.getClientId());// 增加商户资源信息
				}
			}
			// /**
			// * 免审字段注入
			// */
			// setSomeValueWithoutAccepted(mm, m, bankOperatorVoRes);
			// 审核状态为通过的不能修改
			// if(!StringUtil.equals(mm.getAuditState(),
			// AuditFlagEnum.ACCEPTED.getCode())){
			/**
			 * 部分属性注入
			 */
			this.setSomeValue4Update(mm, m);
			// }

			MerchantAccountVo merchantAccount = new MerchantAccountVo(); // 获取商户账户
			merchantAccount.setMerchantId(m.getMerchantId());
			merchantAccount.setOutletId("0");
			LogCvt.info("======商户修改，根据商户ID" + m.getMerchantId()
					+ "获取商户账户:开始======");
			List<MerchantAccountVo> merchantAccountList = merchantAccountService
					.getMerchantAccount(merchantAccount);
			LogCvt.info(
					"======商户修改，根据商户ID" + m.getMerchantId() + "获取商户账户:结束======"
							+ "  返回:" + JSON.toJSONString(merchantAccountList));
			if (merchantAccountList != null && merchantAccountList.size() > 0) {
				/**
				 * 修改商户账户信息
				 */
				this.setAccountInfo4Update(mm, merchantAccountList);
				// 修改商户账户
				LogCvt.info("======商户修改，修改商户账户:开始======");
				ResultVo resultVo = merchantAccountService
						.updateMerchantAccount(loginService.getOriginVo(req),
								merchantAccountList.get(0));
				LogCvt.info("======商户修改，修改商户账户:结束======" + "  返回:"
						+ JSON.toJSONString(resultVo));
				if (!EnumTypes.success.getCode()
						.equals(resultVo.getResultCode())) {
					reMap.put(ResultEnum.CODE.getCode(),
							EnumTypes.fail.getCode());
					reMap.put(ResultEnum.MESSAGE.getCode(),
							resultVo.getResultDesc());
					return reMap;
				}
			} else {// 没有商户账户则判断是否有账户名，如果有则新增商户账户
				if (StringUtil.isNotEmpty(mm.getAcctName())
						&& StringUtil.isNotEmpty(mm.getAcctNumber())) {
					MerchantAccountVo account = new MerchantAccountVo();
					account.setAcctName(mm.getAcctName());
					account.setAcctNumber(mm.getAcctNumber());
					account.setAcctType("1");
					account.setClientId(mm.getClientId());
					account.setMerchantId(mm.getMerchantId());
					account.setOpeningBank(mm.getOpeningBank());
					account.setOutletId("0");
					account.setIsDelete(false);
					// 新增商户门店账号
					LogCvt.info("======商户修改，没有商户门店账号则新增商户门店账号:开始======");
					MerchantAccountAddVoRes merchantAccountRes = merchantAccountService
							.addMerchantAccount(loginService.getOriginVo(req),
									account);
					LogCvt.info("======商户修改，没有商户门店账号则新增商户门店账号:结束======"
							+ "  返回:" + JSON.toJSONString(merchantAccountRes));
					if (!EnumTypes.success.getCode().equals(
							merchantAccountRes.getResult().getResultCode())) {
						reMap.put(ResultEnum.CODE.getCode(),
								EnumTypes.fail.getCode());
						reMap.put(ResultEnum.MESSAGE.getCode(),
								merchantAccountRes.getResult().getResultDesc());
						return reMap;
					}
				}
			}

			if (mm.getCategoryType() != null
					&& mm.getCategoryType().size() > 0) {
				this.setTypeInfo4Add(mm, merchantReq);
			}

			if (mm.getCategory() != null) {
				this.setCategoryInfo4Add(mm, merchantReq);
			}
			merchantReq.setMerchantVo(m);

			ResultVo merchantUpdateResult = null;

			LogCvt.info("编辑审核不通过的商户传入条件：" + JSON.toJSONString(merchantReq));
			merchantUpdateResult = merchantService
					.updateMerchant(loginService.getOriginVo(req), merchantReq);
			LogCvt.info("merchantService.updateMerchant更新商户返回:"
					+ JSON.toJSONString(merchantUpdateResult));
			if (EnumTypes.success.getCode()
					.equals(merchantUpdateResult.getResultCode())) {

				// //审核流程号从配置文件读取
				// String processId = Constants.get("processId.merchant.add");

				// 审核类型详情:0-新增,1-更新
				String processTypeDetail = "0"; // 新增

				// 修改成功后进入审核流程，创建审核实例
				CreateInstanceResVo createInstanceResVo = createInstance(mm,
						originVo, m, mm.getMerchantId(), mm.getMerchantUserId(),
						processTypeDetail);
				if (!EnumTypes.success.getCode().equals(
						createInstanceResVo.getResult().getResultCode())) {
					reMap.put(ResultEnum.CODE.getCode(),
							EnumTypes.fail.getCode());
					reMap.put(ResultEnum.MESSAGE.getCode(),
							createInstanceResVo.getResult().getResultDesc());
				}

				reMap.put(ResultEnum.CODE.getCode(),
						merchantUpdateResult.getResultCode());
				reMap.put(ResultEnum.MESSAGE.getCode(),
						merchantUpdateResult.getResultDesc());
			} else {
				reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
				reMap.put(ResultEnum.MESSAGE.getCode(),
						merchantUpdateResult.getResultDesc());
			}
		}

		return reMap;
	}

	/**
	 * 
	 * setMerchantTempVoReq:(封装merchantTempVoReq请求参数).
	 *
	 * @author wufei 2015-10-26 下午06:18:33
	 * @param mm
	 * @return
	 * @throws TException
	 *
	 */
	private MerchantTempVoReq setMerchantTempVoReq(MerchantManageVo mm,
			MerchantTempVo merchantTempVo, MerchantTempVoReq merchantTempVoReq)
					throws TException {

		// 审核通过后这些字段不允许修改
		changeNoUpdateValue(merchantTempVo);
		/**
		 * 添加外包公司字段
		 */
		if (null != mm.getIsOutsource()) {
			if (mm.getIsOutsource()) {
				merchantTempVo.setIsOutsource(true);
				merchantTempVo.setCompanyId(mm.getCompanyId());
			} else {
				merchantTempVo.setIsOutsource(false);
			}
		}
		// 如果外包公司更改外包公司
		if (null != mm.getCompanyId()) {
			merchantTempVo.setIsOutsource(true);
			merchantTempVo.setCompanyId(mm.getCompanyId());
		}
		merchantTempVo.setMerchantId(mm.getMerchantId());
		if (StringUtil.isNotBlank(mm.getMerchantName())) {
			merchantTempVo.setMerchantName(mm.getMerchantName()); // 商户简称
		}
		if (StringUtil.isNotBlank(mm.getLegalName())) {
			merchantTempVo.setLegalName(mm.getLegalName()); // 法人名
		}
		if (StringUtil.isNotBlank(mm.getContractStaff())) {
			merchantTempVo.setContractStaff(mm.getContractStaff()); // 签约人
		}
		if (StringUtil.isNotBlank(mm.getOpeningBank())) {
			merchantTempVo.setOrgCode(mm.getOpeningBank()); // 签约行机构号
		}
		if (StringUtil.isNotBlank(mm.getLegalCredentType())) {
			merchantTempVo.setLegalCredentType(
					String.valueOf(mm.getLegalCredentType())); // 证件类型
		}
		if (StringUtil.isNotBlank(mm.getLegalCredentNo())) {
			merchantTempVo.setLegalCredentNo(mm.getLegalCredentNo()); // 证件号码
		}
		if (StringUtil.isNotBlank(mm.getAcctName())) {
			merchantTempVo.setAccountName(mm.getAcctName()); // 收款账户名
		}
		if (StringUtil.isNotBlank(mm.getAcctNumber())) {
			merchantTempVo.setAcountNo(mm.getAcctNumber()); // 收款账户号
		}
		if (StringUtil.isNotEmpty(mm.getMerchantName())) {
			merchantTempVo.setMerchantName(mm.getMerchantName()); // 商户简称
		}
		if (StringUtil.isNotEmpty(mm.getContactPhone())) {
			merchantTempVo.setContactPhone(mm.getContactPhone()); // 联系电话
		}
		if (StringUtil.isNotEmpty(mm.getPhone())) {
			merchantTempVo.setPhone(mm.getPhone()); // 联系人手机
		}
		if (StringUtil.isNotEmpty(mm.getLoginPhone())) {
			merchantTempVo.setLoginMobile(mm.getLoginPhone()); // 登录人手机
		}
		LogCvt.info("商户类型：" + mm.getCategoryType());
		// 商户分类和类型
		if (StringUtil.isNotBlank(mm.getCategoryType())) {
			StringBuilder name = new StringBuilder();
			StringBuilder type = new StringBuilder();
			StringBuilder typeId = new StringBuilder();
			for (int i = 0; i < mm.getCategoryType().size(); i++) {
				typeId.append(mm.getCategoryType().get(i));
				typeId.append(",");
				MerchantTypeVo merchantType = merchantTypeService
						.getMerchantTypeById(mm.getCategoryType().get(i),
								mm.getClientId());
				LogCvt.info("**********商户类型***********"
						+ JSON.toJSONString(merchantType));
				name.append(merchantType.getTypeName());
				name.append(",");
				type.append(merchantType.getType());
				type.append(",");
			}
			merchantTempVo.setMerchantTypeId(
					typeId.substring(0, typeId.length() - 1));
			merchantTempVo
					.setMerchantTypeName(name.substring(0, name.length() - 1));
			merchantTempVo
					.setMerchantTypeValue(type.substring(0, type.length() - 1));
		}

		if (StringUtil.isNotBlank(mm.getCategory())) {
			merchantTempVo.setMerchantCategoryId(mm.getCategory());// 商户分类
			MerchantCategoryVo merchantCategory = merchantCategoryService
					.getMerchantCategoryById(mm.getClientId(),
							mm.getCategory());
			merchantTempVo.setMerchantCategoryName(merchantCategory.getName());
		}

		merchantTempVo.setClientId(mm.getClientId());
		merchantTempVoReq.setMerchantTempVo(merchantTempVo);
		return merchantTempVoReq;
	}

	/**
	 * 
	 * createInstance:(创建审核实例).
	 *
	 * @author wufei 2015-10-23 下午04:43:27
	 * @param mm
	 * @param originVo
	 * @param merchantReq
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws TException
	 *
	 */
	private CreateInstanceResVo createInstance(MerchantManageVo mm,
			OriginVo originVo, MerchantVo m, String merchantId,
			String merchantUserId, String processTypeDetail)
					throws JsonGenerationException, JsonMappingException,
					IOException, TException {
		CreateInstanceReqVo createInstanceReqVo = new CreateInstanceReqVo();
		createInstanceReqVo.setOrigin(originVo);

		// createInstanceReqVo.setProcessId(processId);//审核流程id
		createInstanceReqVo.setProcessType(ProcessTypeEnum.MERCHANT.getCode());
		createInstanceReqVo.setProcessTypeDetail(processTypeDetail); // 类型详情:0-新增,1-更新
		createInstanceReqVo.setBessId(merchantId);
		createInstanceReqVo.setOrgCode(m.getOrgCode());

		// 业务数据json对象
		MerchantDetailVo merchantDetail = merchantService
				.getMerchantDetail(merchantId);// 获取分类和类型
		LogCvt.info("根据商户详情获取商户分类和类型：" + JSON.toJSONString(merchantDetail));
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		if (StringUtil.isNotBlank(mm.getMerchantFullName() != null)) {
			jsonMap.put(BessDataEnum.merchantFullName.getKey(),
					mm.getMerchantFullName()); // 商户 全称
		} else {
			jsonMap.put(BessDataEnum.merchantFullName.getKey(),
					m.getMerchantFullname());
		}
		if (StringUtil.isNotBlank(mm.getMerchantName())) {
			jsonMap.put(BessDataEnum.merchantName.getKey(),
					mm.getMerchantName()); // 商户 简称
		} else {
			jsonMap.put(BessDataEnum.merchantName.getKey(),
					m.getMerchantName());
		}
		if (StringUtil.isNotBlank(mm.getCategory())) {
			jsonMap.put(BessDataEnum.categoryInfo.getKey(), mm.getCategory()); // 所属分类
		} else {
			if (null != merchantDetail.getCategoryInfo()
					&& merchantDetail.getCategoryInfo().size() > 0) {
				jsonMap.put(BessDataEnum.categoryInfo.getKey(), merchantDetail
						.getCategoryInfo()
						.get(0).getCategoryId());
			}
		}
		if (StringUtil.isNotBlank(mm.getCategoryType())) {
			jsonMap.put(BessDataEnum.typeInfo.getKey(), mm.getCategoryType()); // 商户类型
		} else {
			List<Long> types = new ArrayList<Long>();
			if (null != merchantDetail.getTypeInfo()
					&& merchantDetail.getTypeInfo().size() > 0) {
				for (int i = 0; i < merchantDetail.getTypeInfo().size(); i++) {
					types.add(merchantDetail.getTypeInfo().get(i)
							.getMerchantTypeId());
					jsonMap.put(BessDataEnum.typeInfo.getKey(), types);
				}
			}

		}
		if (StringUtil.isNotBlank(mm.getLicense())) {
			jsonMap.put(BessDataEnum.license.getKey(), mm.getLicense()); // 营业执照
		} else {
			jsonMap.put(BessDataEnum.license.getKey(), m.getLicense());
		}
		jsonMap.put(BessDataEnum.userOrgCode.getKey(), mm.getMyOrgCode()); // 用户录入机构
		if (StringUtil.isNotBlank(mm.getOpeningBank())) { // 所属机构
			jsonMap.put(BessDataEnum.newOrgCode.getKey(), mm.getOpeningBank());
		} else {
			jsonMap.put(BessDataEnum.newOrgCode.getKey(), m.getOrgCode());
		}
		if (StringUtil.isBlank(merchantId)) {
			jsonMap.put(BessDataEnum.merchantId.getKey(), mm.getMerchantId());
		} else {
			jsonMap.put(BessDataEnum.merchantId.getKey(), merchantId);
		}
		if (StringUtil.isNotBlank(merchantUserId)) {
			jsonMap.put(BessDataEnum.merchantUserId.getKey(), merchantUserId);
		}

		createInstanceReqVo
				.setBessData(BankStringUtil.parseToJsonString(jsonMap));
		LogCvt.info("创建审核实例参数：" + JSON.toJSONString(createInstanceReqVo));
		CreateInstanceResVo createInstanceResVo = fallowExecuteService
				.createInstance(createInstanceReqVo);
		LogCvt.info("创建审核实例返回result："
				+ JSON.toJSONString(createInstanceResVo.getResult())
				+ "返回的审核流水号：" + createInstanceResVo.getInstanceId());
		return createInstanceResVo;
	}

	/**
	 * 
	 * setSomeValueIfAdd:如果新增,部分属性注入
	 *
	 * @author 明灿 2015年9月30日 下午4:40:38
	 * @param mm
	 * @param merchantUserVo
	 *
	 */
	private void setSomeValueIfAdd(MerchantManageVo mm,
			MerchantUserVo merchantUserVo) {
		merchantUserVo.setMerchantId(mm.getMerchantId());
		merchantUserVo.setClientId(mm.getClientId());
		merchantUserVo.setEmail(null);
		// merchantUserVo.setMerchantRoleId(merchantRoleService.getMerchantRoleByClientIdAndRoleDesc(mm.getClientId(),
		// "Role_Administrators").getId());
		merchantUserVo.setMerchantRoleId(Constants.MERCHANT_ROLE_ID);
		if (StringUtil.isNotBlank(mm.getLoginName())) {
			merchantUserVo.setUsername(mm.getLoginName());
		}
		if (StringUtil.isNotBlank(mm.getLoginPhone())) {
			merchantUserVo.setPhone(mm.getLoginPhone());
		}
		merchantUserVo.setIsRest(false);
		merchantUserVo.setIsDelete(false);
		merchantUserVo.setOutletId("0");
		if (StringUtil.isNotBlank(mm.getLoginPassword())) {
			merchantUserVo.setPassword(mm.getLoginPassword());
		}

	}

	/**
	 * 
	 * setAccountInfo4Update:商户账户信息注入
	 *
	 * @author 明灿 2015年9月30日 下午4:37:31
	 * @param mm
	 * @param merchantAccountList
	 *
	 */
	private void setAccountInfo4Update(MerchantManageVo mm,
			List<MerchantAccountVo> merchantAccountList) {
		if (!StringUtil.equals(mm.getAuditState(),
				AuditFlagEnum.ACCEPTED.getCode())) {
			if (StringUtil.isNotEmpty(mm.getAcctName())) {
				merchantAccountList.get(0).setAcctName(mm.getAcctName());
			}
			if (StringUtil.isNotEmpty(mm.getAcctNumber())) {
				merchantAccountList.get(0).setAcctNumber(mm.getAcctNumber());
			}
		}
		if (StringUtil.isNotEmpty(mm.getOpeningBank())) {
			merchantAccountList.get(0).setOpeningBank(mm.getOpeningBank());
		}
	}

	/**
	 * 
	 * setSomeValue4Update:修改时部分属性注入
	 *
	 * @author 明灿 2015年9月30日 下午4:29:07
	 * @param mm
	 * @param m
	 *
	 */
	private void setSomeValue4Update(MerchantManageVo mm, MerchantVo m) {
		/**
		 * 添加外包公司字段
		 */
		if (null != mm.getIsOutsource()) {
			if (mm.getIsOutsource()) {
				m.setIsOutsource(true);
				m.setCompanyId(mm.getCompanyId());
			} else {
				m.setIsOutsource(false);
			}
		}
		// 如果外包公司更改
		if (null != mm.getCompanyId()) {
			m.setIsOutsource(true);
			m.setCompanyId(mm.getCompanyId());
		}
		// 保存全称
		if (mm.getMerchantName() != null) {
			m.setMerchantName(mm.getMerchantName());
		}
		if (mm.getPhone() != null) {
			m.setPhone(mm.getPhone());
		}
		if (mm.getContactPhone() != null) {
			m.setContactPhone(mm.getContactPhone());
		}
		if (mm.getContactName() != null) {
			m.setContactName(mm.getContactName());
		}
		if (mm.getLegalName() != null) {
			m.setLegalName(mm.getLegalName());
		}
		if (mm.getLegalCredentType() != null) {
			m.setLegalCredentType(mm.getLegalCredentType());
		}
		if (mm.getLegalCredentNo() != null) {
			m.setLegalCredentNo(mm.getLegalCredentNo());
		}
		if (mm.getOpeningBank() != null) {
			m.setOrgCode(mm.getOpeningBank());
		}
		if (mm.getContractStaff() != null) {
			m.setContractStaff(mm.getContractStaff());
		}
		// 需求变更,contactName可以被修改
		if (mm.getContactName() != null) {
			m.setContactName(mm.getContactName());
		}
		if (StringUtil.isNotBlank(mm.getCompanyCredential())) {
			m.setCompanyCredential(mm.getCompanyCredential());
		}
		if (StringUtil.isNotBlank(mm.getLicense())) {
			m.setLicense(mm.getLicense());
		}

	}

	/**
	 * 商户详情
	 * 
	 * @param merchantManageId
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	public HashMap<String, Object> merchantManageDetail(String merchantId,
			String merchantUserId, OriginVo originVo)
					throws TException, ParseException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();

		if (StringUtil.isNotEmpty(merchantUserId)
				&& merchantUserId.equals("null")) {
			merchantUserId = "";
		}
		MerchantManageVo mm = new MerchantManageVo();
		LogCvt.info("======商户详情，根据商户ID" + merchantId + "获取商户信息:开始======");
		MerchantVo merchant = merchantService
				.getMerchantByMerchantId(merchantId);
		LogCvt.info("======商户详情，根据商户ID" + merchantId + "获取商户信息:结束======"
				+ "  返回:" + JSON.toJSONString(merchant));
		/**
		 * 封装一部分属性字段
		 */
		this.setSomeValue(mm, merchant);
		// 商户组织机构代码
		if (StringUtil.isNotBlank(merchant.getCompanyCredential())) {
			mm.setCompanyCredential(merchant.getCompanyCredential());
		}
		/**
		 * 根据商户id获取地区详情,封装商户的地址详情
		 */
		this.setAddress(merchantId, reMap, merchant);
		if (StringUtil.isNotEmpty(merchant.getOrgCode())) {
			LogCvt.info("======商户详情，根据开户行orgCode" + merchant.getOrgCode()
					+ "获取开户行机构信息:开始======");
			OrgVo org = orgService.getOrgByIdSuperOrgName(
					merchant.getClientId(), merchant.getOrgCode());
			LogCvt.info("======商户详情，根据开户行orgCode" + merchant.getOrgCode()
					+ "获取开户行机构信息:结束======" + "  返回:" + JSON.toJSONString(org));
			if (org != null) {
				reMap.put("orgName", org.getOrgName());// 所属网点
				reMap.put("partenOrgName", org.getSuperOrgName());// 所属行社
			}
		}
		/**
		 * 获取商户账户信息,并且封装返回
		 */
		this.setMerchantAccountVo(merchantId, reMap);
		/**
		 * 获取商户用户信息,并且风转返回
		 */
		this.setMerchantUser(merchantUserId, reMap, mm);
		LogCvt.info("======商户详情，根据商户ID" + merchantId + "获取商户分类和类型:开始======");
		MerchantDetailVo merchantDetail = merchantService
				.getMerchantDetail(merchantId);// 获取分类和类型
		LogCvt.info("======商户详情，根据商户ID" + merchantId + "获取商户分类和类型:结束======"
				+ "  返回:" + JSON.toJSONString(merchantDetail));

		// 关联审核流水号
		GetInstanceIdByAttrReqVo instanceIdByAttrReqVo = new GetInstanceIdByAttrReqVo();
		instanceIdByAttrReqVo.setOrigin(originVo);
		instanceIdByAttrReqVo.setBessId(merchantId);
		/** 根据商户id查询审核流水号 */
		GetInstanceIdByAttrResVo instanceId = fallowQueryService
				.getInstanceIdByAttr(instanceIdByAttrReqVo);
		if (EnumTypes.success.getCode()
				.equals(instanceId.getResult().getResultCode())
				&& instanceId.getInstanceId() != null) {
			mm.setAuditId(instanceId.getInstanceId());
			// mm.setAuditType(instanceId.getProcessTypeDetail());
		}

		/**
		 * 封装返回商户类型和分类
		 */
		this.setMerchantTypeAndCatgory(reMap, mm, merchantDetail);
		/**
		 * 封装返回证件类型
		 */
		this.setLegalCredentType(reMap, merchant);

		return reMap;
	}

	/**
	 * 商户新增审核详情
	 * 
	 * @param merchantId
	 * @param merchantUserId
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	public HashMap<String, Object> merchantManageAddDetail(String merchantId,
			String auditId, OriginVo originVo, String clientId,
			String merchantUserId) throws TException, ParseException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();

		if (StringUtil.isNotEmpty(merchantUserId)
				&& merchantUserId.equals("null")) {
			merchantUserId = "";
		}
		// 查询审核订单信息
		setAuditTaskRes(auditId, originVo, clientId, reMap);

		MerchantManageVo mm = new MerchantManageVo();
		LogCvt.info("======商户详情，根据商户ID" + merchantId + "获取商户信息:开始======");
		MerchantVo merchant = merchantService
				.getMerchantByMerchantId(merchantId);
		LogCvt.info("======商户详情，根据商户ID" + merchantId + "获取商户信息:结束======"
				+ "  返回:" + JSON.toJSONString(merchant));
		/**
		 * 封装一部分属性字段
		 */
		this.setSomeValue(mm, merchant);
		// 商户组织机构代码
		if (StringUtil.isNotBlank(merchant.getCompanyCredential())) {
			mm.setCompanyCredential(merchant.getCompanyCredential());
		}
		/**
		 * 根据商户id获取地区详情,封装商户的地址详情
		 */
		this.setAddress(merchantId, reMap, merchant);
		if (StringUtil.isNotEmpty(merchant.getOrgCode())) {
			LogCvt.info("======商户详情，根据开户行orgCode" + merchant.getOrgCode()
					+ "获取开户行机构信息:开始======");
			OrgVo org = orgService.getOrgByIdSuperOrgName(
					merchant.getClientId(), merchant.getOrgCode());
			LogCvt.info("======商户详情，根据开户行orgCode" + merchant.getOrgCode()
					+ "获取开户行机构信息:结束======" + "  返回:" + JSON.toJSONString(org));
			if (org != null) {
				reMap.put("orgName", org.getOrgName());// 所属网点
				reMap.put("partenOrgName", org.getSuperOrgName());// 所属行社
			}
		}
		/**
		 * 获取商户账户信息,并且封装返回
		 */
		this.setMerchantAccountVo(merchantId, reMap);
		/**
		 * 获取商户用户信息,并且风转返回
		 */
		this.setMerchantUser(merchantUserId, reMap, mm);

		LogCvt.info("======商户详情，根据商户ID" + merchantId + "获取商户分类和类型:开始======");
		MerchantDetailVo merchantDetail = merchantService
				.getMerchantDetail(merchantId);// 获取分类和类型
		LogCvt.info("======商户详情，根据商户ID" + merchantId + "获取商户分类和类型:结束======"
				+ "  返回:" + JSON.toJSONString(merchantDetail));
		/**
		 * 封装返回商户类型和分类
		 */
		this.setMerchantTypeAndCatgory(reMap, mm, merchantDetail);
		/**
		 * 封装返回证件类型
		 */
		this.setLegalCredentType(reMap, merchant);

		// 审核任务单信息
		this.setAuditProcessRes(auditId, originVo, clientId, reMap);

		return reMap;
	}

	/**
	 * 
	 * setAuditProcessRes:(封装返回任务单列表信息).
	 *
	 * @author wufei 2015-10-26 下午04:13:14
	 * @param auditId
	 * @param originVo
	 * @param clientId
	 * @param reMap
	 * @throws TException
	 *
	 */
	private void setAuditProcessRes(String auditId, OriginVo originVo,
			String clientId, HashMap<String, Object> resMap) throws TException {
		List<AuditProcessRes> auditProcessResList = new ArrayList<AuditProcessRes>();
		GetTaskReqVo taskReqVo = new GetTaskReqVo();
		taskReqVo.setOrigin(originVo);
		taskReqVo.setInstanceId(auditId);
		LogCvt.info("查询审核任务单信息========请求参数：instanceId" + auditId
				+ "===：originVo" + JSON.toJSONString(originVo));
		GetTaskResVo taskResVo = fallowQueryService.getTaskList(taskReqVo);
		LogCvt.info("查询审核任务单返回数据：" + JSON.toJSONString(taskResVo));
		if (EnumTypes.success.getCode()
				.equals(taskResVo.getResult().getResultCode())) {
			if (taskResVo.getTaskListRes() != null
					&& taskResVo.getTaskListRes().size() > 0) {
				for (TaskListResVo taskListResVo : taskResVo.getTaskListRes()) {
					AuditProcessRes auditProcessRes = new AuditProcessRes();
					auditProcessRes.setTaskId(taskListResVo.getTaskId()); // 任务流水号
					if (taskListResVo.getCreateTime() != 0) {
						auditProcessRes
								.setCreateTime(taskListResVo.getCreateTime()); // 创建时间
					} else {
						auditProcessRes.setCreateTime(null);
					}
					if (taskListResVo.getAuditTime() != 0) {
						auditProcessRes
								.setAuditTime(taskListResVo.getAuditTime()); // 审核时间
					} else {
						auditProcessRes.setAuditTime(null);
					}
					if (taskListResVo.getOrgId() != null) {
						OrgVo orgVo = orgService.getOrgById(clientId,
								taskListResVo.getOrgId());
						auditProcessRes.setAuditOrgName(orgVo.getOrgName()); // 审核机构
					}
					if (taskListResVo.getAuditor() != null) {
						BankOperatorVo bankOperatorVo = bankOperatorService
								.getBankOperatorById(clientId, Long
										.parseLong(taskListResVo.getAuditor()));
						auditProcessRes
								.setAuditor(bankOperatorVo.getUsername()); // 审核人
					}
					auditProcessRes
							.setAuditStatus(taskListResVo.getAuditState()); // 审核状态
					auditProcessRes.setComment(taskListResVo.getRemark()); // 备注
					auditProcessResList.add(auditProcessRes);
				}
			}
		} else {
			resMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			resMap.put(ResultEnum.MESSAGE.getCode(),
					taskResVo.getResult().getResultDesc());
		}
		resMap.put("merchantTaskList", auditProcessResList);
	}

	/**
	 * 
	 * setAuditTaskRes:(封装返回订单信息).
	 *
	 * @author wufei 2015-10-26 下午04:13:46
	 * @param auditId
	 * @param originVo
	 * @param clientId
	 * @param reMap
	 * @throws TException
	 *
	 */
	private GetTaskOverviewResVo setAuditTaskRes(String auditId,
			OriginVo originVo, String clientId, HashMap<String, Object> reMap)
					throws TException {
		GetTaskOverviewResVo taskOverviewResVo = null;
		AuditTaskRes auditTaskRes = new AuditTaskRes();
		GetTaskOverviewReqVo taskOverviewReqVo = new GetTaskOverviewReqVo();
		taskOverviewReqVo.setOrigin(originVo);
		taskOverviewReqVo.setInstanceId(auditId);
		LogCvt.info("查询审核订单信息========请求参数：instanceId" + auditId + "===：originVo"
				+ JSON.toJSONString(originVo));
		taskOverviewResVo = fallowQueryService
				.getTaskOverview(taskOverviewReqVo);
		LogCvt.info("查询审核订单信息返回数据：" + JSON.toJSONString(taskOverviewResVo));
		if (taskOverviewResVo != null) {
			auditTaskRes.setAuditId(taskOverviewResVo.getInstanceId()); // 审核流水号
			auditTaskRes.setAuditStatus(taskOverviewResVo.getAuditState());// 审核状态

			if (taskOverviewResVo.getCreator() != null) {
				BankOperatorVo bankOperatorVo = bankOperatorService
						.getBankOperatorById(clientId,
								Long.parseLong(taskOverviewResVo.getCreator()));

				auditTaskRes.setCreater(bankOperatorVo.getUsername()); // 创建人
			}
			if (taskOverviewResVo.getOrgId() != null) {
				OrgVo orgVo = orgService.getOrgById(clientId,
						taskOverviewResVo.getOrgId());
				auditTaskRes.setCreateOrgName(orgVo.getOrgName()); // 创建机构
			}
			if (taskOverviewResVo.getCreateTime() != 0) {
				auditTaskRes.setCreateTime(taskOverviewResVo.getCreateTime()); // 创建时间
			} else {
				auditTaskRes.setCreateTime(null);
			}
		}
		reMap.put("auditTaskOrder", auditTaskRes);
		return taskOverviewResVo;
	}

	/**
	 * 商户更新审核详情
	 * 
	 * @param merchantId
	 * @param merchantUserId
	 * @param originVo
	 * @param clientId
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> merchantManageUpdateDetail(String merchantId,
			String auditId, OriginVo originVo, String clientId)
					throws TException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();

		// 查询审核订单信息
		GetTaskOverviewResVo taskOverviewResVo = this.setAuditTaskRes(auditId,
				originVo, clientId, resMap);

		MerchantAuditDetailRes merchant = new MerchantAuditDetailRes();

		MerchantTempVo merchantTempVo = null;
		if (taskOverviewResVo != null
				&& taskOverviewResVo.getInstanceId() != null) {
			// 查询商户新值信息
			LogCvt.info("======商户审核详情，根据审核流水号auditId"
					+ taskOverviewResVo.getInstanceId() + "查询商户新值信息");
			merchantTempVo = merchantAuditService.getAuditMerchant(originVo,
					taskOverviewResVo.getInstanceId());
			LogCvt.info("商户信息返回数据：" + JSON.toJSONString(merchantTempVo));
			if (merchantTempVo != null) {
				// 商户新值信息封装返回
				this.setMerchantNewValue(merchantId, merchant, merchantTempVo);
				// 商户原值信息通过一个字段primeval以json格式返回来
				LogCvt.info("======商户原值信息==="
						+ JSON.toJSONString(merchantTempVo.getPrimeval()));
				if (StringUtil.isNotBlank(merchantTempVo.getPrimeval())) {
					// 商户原值信息封装返回
					this.setMerchantOldValue(clientId, merchant,
							merchantTempVo);
				}

			}

		}
		resMap.put("merchant", merchant);

		// 审核任务单信息
		this.setAuditProcessRes(auditId, originVo, clientId, resMap);

		return resMap;
	}

	/**
	 * 
	 * setNewMerchantValue:(商户新值信息封装返回).
	 *
	 * @author wufei 2015-11-7 下午05:57:25
	 * @param merchantId
	 * @param merchant
	 * @param merchantTempVo
	 * @throws TException
	 *
	 */
	private void setMerchantNewValue(String merchantId,
			MerchantAuditDetailRes merchant, MerchantTempVo merchantTempVo)
					throws TException {
		/**
		 * 返回外包公司字段
		 */
		merchant.setIsOutsource(merchantTempVo.isIsOutsource());
		if (merchantTempVo.isIsOutsource()) {
			merchant.setCompanyId(merchantTempVo.getCompanyId());
			merchant.setCompanyName(
					getCompanyName(merchantTempVo.getCompanyId()));
		}
		merchant.setNewMerchantName(merchantTempVo.getMerchantName());
		merchant.setNewContactName(merchantTempVo.getContactName());
		merchant.setNewPhone(merchantTempVo.getPhone());
		merchant.setNewContactPhone(merchantTempVo.getContactPhone());
		merchant.setNewLegalName(merchantTempVo.getLegalName());
		if (merchantTempVo.getLegalCredentType() != null) {
			int legalCredentType = Integer
					.parseInt(merchantTempVo.getLegalCredentType());
			merchant.setNewLegalCredentType(
					getLegalCredentType(legalCredentType));
		}
		merchant.setNewLegalCredentNo(merchantTempVo.getLegalCredentNo());
		merchant.setNewAcctName(merchantTempVo.getAccountName());
		merchant.setNewAcctNumber(merchantTempVo.getAcountNo());
		merchant.setNewLoginPhone(merchantTempVo.getLoginMobile());
		merchant.setNewOrgNames(merchantTempVo.getCountyOrgName());
		merchant.setNewParentOrgNames(merchantTempVo.getCityOrgName());
		merchant.setNewCategory(merchantTempVo.getMerchantCategoryName());
		merchant.setNewCategoryType(merchantTempVo.getMerchantTypeName());
		merchant.setNewContractStaff(merchantTempVo.getContractStaff());
		MerchantVo merchantVo = merchantService
				.getMerchantByMerchantId(merchantId);
		merchant.setLicense(merchantVo.getLicense());
	}

	/**
	 * 
	 * getCompanyName:根据公司id获取公司名称
	 * 
	 * @author chenmingcan@froad.com.cn 2015年11月26日 下午5:37:44
	 * @param companyId
	 *            公司id
	 * @return 公司名称
	 * @throws TException
	 *
	 */
	private String getCompanyName(long companyId) throws TException {
		String companyName = "";
		DictionaryItemVo dictionaryItemVo = dictionaryService
				.getDictionaryitemById(companyId);
		if (null != dictionaryItemVo) {
			companyName = dictionaryItemVo.getItemName();
		}
		return companyName;
	}

	/**
	 * 
	 * setOldMerchantValue:(商户原值信息封装返回).
	 *
	 * @author wufei 2015-11-7 下午05:59:07
	 * @param clientId
	 * @param merchant
	 * @param merchantTempVo
	 * @throws TException
	 *
	 */
	private void setMerchantOldValue(String clientId,
			MerchantAuditDetailRes merchant, MerchantTempVo merchantTempVo)
					throws TException {
		String merchantPrimeval = merchantTempVo.getPrimeval();
		JSONObject json = JSONObject.fromObject(merchantPrimeval);
		/**
		 * 添加外包公司字段
		 */
		if (json.containsKey(BessDataEnum.isOutsource.getKey())) {
			merchant.setOldIsOutsource(
					json.getBoolean(BessDataEnum.isOutsource.getKey()));
			if (json.getBoolean(BessDataEnum.isOutsource.getKey())) {
				if (json.containsKey(BessDataEnum.companyId.getKey())) {
					merchant.setOldCompanyId(
							json.getLong(BessDataEnum.companyId.getKey()));
					merchant.setOldCompanyName(getCompanyName(
							json.getLong(BessDataEnum.companyId.getKey())));
				}
			}
		} else {
			merchant.setIsOutsource(false);
		}
		if (json.containsKey(BessDataEnum.merchantFullName.getKey())) {
			merchant.setMerchantFullName(
					json.getString(BessDataEnum.merchantFullName.getKey()));// 商户全称
		}
		if (json.containsKey(BessDataEnum.merchantName.getKey())) {
			merchant.setMerchantName(
					json.getString(BessDataEnum.merchantName.getKey())); // 商户简称
		}
		if (json.containsKey(BessDataEnum.merchantName.getKey())) {
			merchant.setOldMerchantName(
					json.getString(BessDataEnum.merchantName.getKey())); // 原商户简称
		}
		if (json.containsKey(BessDataEnum.phone.getKey())) {
			merchant.setOldPhone(json.getString(BessDataEnum.phone.getKey())); // 联系电话
		}
		if (json.containsKey(BessDataEnum.contactName.getKey())) {
			merchant.setOldContactPhone(
					json.getString(BessDataEnum.contactName.getKey())); // 联系人手机
		}
		if (json.containsKey(BessDataEnum.legalName.getKey())) {
			merchant.setOldLegalName(
					json.getString(BessDataEnum.legalName.getKey()));// 法人名
		}
		if (json.containsKey(BessDataEnum.legalCredentType.getKey())) {
			int legalCredentType = Integer.parseInt(
					json.getString(BessDataEnum.legalCredentType.getKey()));
			merchant.setOldLegalCredentType(
					getLegalCredentType(legalCredentType));// 证件类型
		}
		if (json.containsKey(BessDataEnum.legalCredentNo.getKey())) {
			merchant.setOldLegalCredentNo(
					json.getString(BessDataEnum.legalCredentNo.getKey()));// 证件号
		}
		if (json.containsKey(BessDataEnum.category.getKey())) {
			merchant.setOldCategory(
					json.getString(BessDataEnum.category.getKey())); // 所属分类
		}
		if (json.containsKey(BessDataEnum.categoryType.getKey())) {
			merchant.setOldCategoryType(
					json.getString(BessDataEnum.categoryType.getKey())); // 商户类型
		}
		if (json.containsKey(BessDataEnum.acctName.getKey())) {
			merchant.setOldAcctName(
					json.getString(BessDataEnum.acctName.getKey())); // 收款账户名
		}
		if (json.containsKey(BessDataEnum.acctNumber.getKey())) {
			merchant.setOldAcctNumber(
					json.getString(BessDataEnum.acctNumber.getKey())); // 收货人账号
		}
		if (json.containsKey(BessDataEnum.loginMobile.getKey())) {
			merchant.setOldLoginPhone(
					json.getString(BessDataEnum.loginMobile.getKey())); // 登录人手机
		}
		if (json.containsKey(BessDataEnum.contractStaff.getKey())) {
			merchant.setOldContractStaff(
					json.getString(BessDataEnum.contractStaff.getKey()));// 签约人
		}
		// 所属机构
		if (json.containsKey(BessDataEnum.orgCode.getKey())) {
			String orgCode = json.getString(BessDataEnum.orgCode.getKey());
			if (StringUtil.isNotBlank(orgCode)) {
				OrgVo org = orgService.getOrgByIdSuperOrgName(clientId,
						orgCode);
				if (org != null) {
					merchant.setOldOrgNames(org.getOrgName());// 机构名称
					merchant.setOldParentOrgNames(org.getSuperOrgName()); // 上级机构名称
				}
			}
		}
	}

	private String getLegalCredentType(int legalCredentType) {
		switch (legalCredentType) {
		case 1:
			return "身份证";
		case 2:
			return "护照";
		case 3:
			return "军官证";
		case 4:
			return "士兵证";
		case 5:
			return "户口本";
		case 6:
			return "警官证";
		case 7:
			return "台胞证";
		}
		return null;

	}

	/**
	 * 
	 * setLegalCredentType:获取并返回封装的证件类型
	 *
	 * @author 明灿 2015年8月10日 下午3:28:56
	 * @param reMap
	 * @param merchant
	 *
	 */
	private void setLegalCredentType(HashMap<String, Object> reMap,
			MerchantVo merchant) {
		int legalCredentType = merchant.getLegalCredentType();
		switch (legalCredentType) {
		case 1:
			reMap.put(BessDataEnum.legalCredentType.getKey(), "身份证");
			break;
		case 2:
			reMap.put(BessDataEnum.legalCredentType.getKey(), "护照");
			break;
		case 3:
			reMap.put(BessDataEnum.legalCredentType.getKey(), "军官证");
			break;
		case 4:
			reMap.put(BessDataEnum.legalCredentType.getKey(), "士兵证");
			break;
		case 5:
			reMap.put(BessDataEnum.legalCredentType.getKey(), "户口本");
			break;
		case 6:
			reMap.put(BessDataEnum.legalCredentType.getKey(), "警官证");
			break;
		case 7:
			reMap.put(BessDataEnum.legalCredentType.getKey(), "台胞证");
			break;
		}
	}

	/**
	 * 
	 * setMerchantTypeAndCatgory:获取并封装返回商户类型和分类
	 *
	 * @author 明灿 2015年8月10日 下午3:29:27
	 * @param reMap
	 * @param mm
	 * @param merchantDetail
	 *
	 */
	private void setMerchantTypeAndCatgory(HashMap<String, Object> reMap,
			MerchantManageVo mm, MerchantDetailVo merchantDetail) {
		if (merchantDetail != null) {
			if (merchantDetail.getCategoryInfo() != null
					&& merchantDetail.getCategoryInfo().size() > 0) {
				reMap.put(BessDataEnum.category.getKey(),
						merchantDetail.getCategoryInfo().get(0));
			} else {
				reMap.put(BessDataEnum.category.getKey(), "");
			}
			if (merchantDetail.getTypeInfo() != null
					&& merchantDetail.getTypeInfo().size() > 0) {
				List<TypeInfoVo> types = new ArrayList<TypeInfoVo>();
				for (int i = 0; i < merchantDetail.getTypeInfo().size(); i++) {
					types.add(merchantDetail.getTypeInfo().get(i));
				}
				reMap.put("types", types);
			} else {
				reMap.put("types", "");
			}
		}
		reMap.put("merchant", mm);
	}

	/**
	 * 
	 * setMerchantUser:获取商户信息,并且封装返回
	 *
	 * @author 明灿 2015年8月10日 下午3:21:55
	 * @param merchantUserId
	 * @param reMap
	 * @param mm
	 * @throws TException
	 *
	 */
	private void setMerchantUser(String merchantUserId,
			HashMap<String, Object> reMap, MerchantManageVo mm)
					throws TException {
		MerchantUserVo merchantUser = new MerchantUserVo();
		if (StringUtil.isNotEmpty(merchantUserId)) {
			if (!merchantUserId.equals("0")) {
				LogCvt.info("======商户详情，根据商户用户ID" + merchantUserId
						+ "获取商户用户信息:开始======");
				merchantUser = merchantUserService
						.getMerchantUserById(Long.valueOf(merchantUserId));// 获取商户用户
				LogCvt.info("======商户详情，根据商户用户ID" + merchantUserId
						+ "获取商户用户信息:结束======" + "  返回:"
						+ JSON.toJSONString(merchantUser));
				if (merchantUser != null) {
					mm.setMerchantUserId(merchantUser.getId() + "");
					MerchantManageUserVo userVo = new MerchantManageUserVo();
					userVo.setUsername(merchantUser.getUsername());
					userVo.setPhone(merchantUser.getPhone());
					userVo.setUserPassword(merchantUser.getPassword());
					reMap.put("merchantUser", userVo);
				}
			}
		} else {
			reMap.put("merchantUser", "");
		}
	}

	/**
	 * 
	 * setMerchantAccountVo:获取商户账户信息并且封装返回
	 *
	 * @author 明灿 2015年8月10日 下午3:19:52
	 * @param merchantId
	 * @param reMap
	 * @throws TException
	 *
	 */
	private void setMerchantAccountVo(String merchantId,
			HashMap<String, Object> reMap) throws TException {
		MerchantAccountVo merchantAccount = new MerchantAccountVo(); // 获取商户账户
		merchantAccount.setMerchantId(merchantId);
		merchantAccount.setOutletId("0");
		LogCvt.info("======商户详情，根据商户ID" + merchantId + "获取商户账户信息:开始======");
		List<MerchantAccountVo> merchantAccountList = merchantAccountService
				.getMerchantAccount(merchantAccount);
		LogCvt.info("======商户详情，根据商户ID" + merchantId + "获取商户账户信息:结束======"
				+ "  返回:" + JSON.toJSONString(merchantAccountList));
		if (merchantAccountList != null && merchantAccountList.size() > 0) {
			MerchantManageAccountVo accountVo = new MerchantManageAccountVo();
			accountVo.setId(merchantAccountList.get(0).getId());
			accountVo.setAcctName(merchantAccountList.get(0).getAcctName());
			accountVo.setAcctNumber(merchantAccountList.get(0).getAcctNumber());
			reMap.put("merchantAccount", accountVo);
		} else {
			reMap.put("merchantAccount", "");
		}
	}

	/**
	 * 
	 * setAddress:商户地址详情封装方法
	 *
	 * @author 明灿 2015年8月10日 下午3:17:45
	 * @param merchantId
	 * @param reMap
	 * @param merchant
	 *
	 */
	private void setAddress(String merchantId, HashMap<String, Object> reMap,
			MerchantVo merchant) {
		if (merchant.getAreaId() > 0) {
			LogCvt.info("======商户详情，根据地区ID" + merchant.getAreaId()
					+ "获取地区信息:开始======");
			AreaVo areaVo = bankOrgService.findAreaById(merchant.getAreaId());
			LogCvt.info("======商户详情，根据地区ID" + merchantId + "获取地区信息:结束======"
					+ "  返回:" + JSON.toJSONString(areaVo));
			if (areaVo != null && areaVo.getId() != null) {
				String[] treePath = null; // 获取省市区
				String[] treePathName = null; // 获取省市区名称
				if (StringUtils.isNotEmpty(areaVo.getTreePath())
						&& StringUtils.isNotEmpty(areaVo.getTreePathName())) {
					treePath = areaVo.getTreePath().split(",");
					treePathName = areaVo.getTreePathName().split(",");
					if (treePath.length > 2 && treePathName.length > 2) {
						reMap.put("areaCode", treePath[0]);// 省
						reMap.put("cityCode", treePath[1]);// 市
						reMap.put("countyCode", areaVo.getId());// 区
						if (treePathName[0].equals(treePathName[1])) {
							reMap.put("address",
									treePathName[1] + " " + areaVo.getName()
											+ " " + merchant.getAddress());
						} else {
							reMap.put("address",
									treePathName[0] + " " + treePathName[1]
											+ " " + areaVo.getName() + " "
											+ merchant.getAddress());
						}
					} else if (treePath.length > 1 && treePathName.length > 1) {
						reMap.put("areaCode", areaVo.getPartenId());// 省
						reMap.put("cityCode", areaVo.getId());// 市
						if (areaVo.getTreePathName().equals(areaVo.getName())) {
							reMap.put("address", areaVo.getName() + " "
									+ merchant.getAddress());
						} else {
							reMap.put("address",
									areaVo.getTreePathName() + " "
											+ areaVo.getName() + " "
											+ merchant.getAddress());
						}
					} else {
						reMap.put("areaCode", areaVo.getId());// 省
						reMap.put("address",
								areaVo.getName() + " " + merchant.getAddress());
					}
				}
			}
		}
	}

	/**
	 * 
	 * setSomeValue: 封装商户详情返回的一部分字段
	 *
	 * @author 明灿 2015年8月10日 下午3:04:10
	 * @param mm
	 * @param merchant
	 * @throws ParseException
	 * @throws TException
	 *
	 */
	private void setSomeValue(MerchantManageVo mm, MerchantVo merchant)
			throws ParseException, TException {
		/**
		 * 返回外包公司信息
		 */
		if (merchant.isIsOutsource()) {
			mm.setIsOutsource(true);
		} else {
			mm.setIsOutsource(false);
		}
		if (merchant.isIsOutsource()) {
			mm.setCompanyId(merchant.getCompanyId());
			mm.setCompanyName(this.getCompanyName(merchant.getCompanyId()));
		}
		mm.setMerchantId(merchant.getMerchantId());// 商户id
		mm.setMerchantName(merchant.getMerchantName());// 商户简称
		mm.setMerchantFullName(merchant.getMerchantFullname());// 商户全称
		mm.setAddress(merchant.getAddress());// 详细地址
		mm.setComplaintPhone(merchant.getComplaintPhone());// 投诉电话
		mm.setPhone(merchant.getPhone());// 联系人电话
		mm.setContactPhone(merchant.getContactPhone());// 联系人手机
		mm.setContactEmail(merchant.getContactEmail());// 联系邮箱
		mm.setContactName(merchant.getContactName());// 联系人
		mm.setContractBegintime(merchant.getContractBegintime() + "");// 签约时间
		mm.setContractEndtime(merchant.getContractEndtime() + "");// 到期时间
		mm.setContractStaff(merchant.getContractStaff());// 签约人
		mm.setLegalName(merchant.getLegalName());// 法人姓名
		mm.setLegalCredentType(merchant.getLegalCredentType());// 证件类型
		mm.setLegalCredentNo(merchant.getLegalCredentNo());// 证件号码
		mm.setLicense(merchant.getLicense());// 营业执照
		mm.setOpeningBank(merchant.getOrgCode());// 开户行机构号
		mm.setLogo(merchant.getLogo());// 商户图片
		mm.setTaxReg(merchant.getTaxReg());// 税务登记证
		mm.setAreaId(merchant.getAreaId());// 地址
		mm.setIntroduce(merchant.getIntroduce());// 商户介绍
		mm.setAuditComment(merchant.getAuditComment());// 审核备注
		LogCvt.info("商户审核状态：" + merchant.getAuditState());
		if (merchant.getAuditState() != null) {
			mm.setAuditState(merchant.getAuditState());// 审核状态
		}
		LogCvt.info("商户禁用解约状态(0正常;1禁用;2解约)：" + merchant.getDisableStatus());
		if (merchant.getDisableStatus() != null) {
			mm.setDisableStatus(merchant.getDisableStatus());// 禁用/解约状态
			if (merchant.getDisableStatus().equals("1")
					|| merchant.getDisableStatus().equals("2")) { // 禁用 / 解约
				mm.setOperateTime(merchant.getOperateTime()); // 操作时间
			}
		}
		mm.setOperator(merchant.getOperateUser()); // 操作人

	}

	/**
	 * 是否有权限录入商户
	 * 
	 * @param clientId
	 * @param myOrgCode
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> verifyAddMerchant(String clientId,
			String myOrgCode, String type) throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		ClientMerchantAuditOrgCodeVo merchantAuditVo = clientMerchantAuditService
				.getClientMerchantAuditByOrgCode(clientId, myOrgCode, type);
		// 罗丽提出这么判断更改
		if (StringUtil.isNotBlank(merchantAuditVo.getClientId())) {
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), false);
		} else {
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.success.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), true);
		}
		// if(!StringUtil.isNotEmpty(merchantAuditVo.getStartAuditOrgCode()) &&
		// !StringUtil.isNotEmpty(merchantAuditVo.getEndAuditOrgCode())){
		// reMap.put("code", EnumTypes.success.getCode());
		// reMap.put("message", true);
		// }else{
		// reMap.put("code", EnumTypes.fail.getCode());
		// reMap.put("message", false);
		// }
		return reMap;
	}

	/**
	 * 商户用户唯一性
	 * 
	 * @param merchantManageId
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> verifyMerchantUserName(String clientId,
			String loginName, HttpServletRequest req) throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		if (!loginName.matches("^[A-Za-z0-9]{4,16}$")) {// ^[a-zA-Z]{1}[a-zA-Z0-9|-|_]{3,19}$
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "商户用户名不合法");
			return reMap;
		}

		// 判断商户用户是否存在
		MerchantUserVo merchantUser = merchantUserService
				.getMerchantUserByUsername(loginName,
						loginService.getOriginVo(req));
		if (merchantUser != null
				&& StringUtil.isNotEmpty(merchantUser.getUsername())) {
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "商户用户已存在");
		}
		return reMap;
	}

	/**
	 * 营业执照唯一性
	 * 
	 * @param merchantManageId
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> verifyLicense(String clientId,
			String license) throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		LogCvt.info("=====>>>>营业执照校验请求参数<<<<=====:" + "执照号码:" + license
				+ "clientId:" + clientId);
		MerchantVo merchantVo = new MerchantVo();
		merchantVo.setClientId(clientId);
		merchantVo.setLicense(license);
		// 校验是否为有效的营业执照
		merchantVo.setIsEnable(true);
		Integer count = merchantService.countMerchant(merchantVo);
		LogCvt.info("=====>>>>营业执照校验返回个数<<<<=====:" + count);
		if (count > 0) {
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "营业执照已存在");
			return reMap;
		}

		// 校验是否为待审核的商户营业执照
		merchantVo.setIsEnable(false);
		merchantVo.setAuditState(AuditFlagEnum.NEW.getCode());// 待审核
		Integer count1 = merchantService.countMerchant(merchantVo);
		if (count1 > 0) {
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "营业执照已存在");
			return reMap;
		}
		return reMap;
	}

	/**
	 * 获取名优特惠所属商户
	 * 
	 * @param clientId
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> getAllMerchant(String clientId,
			String orgCode, Integer orgLevel, String merchantName,
			Integer pageNumber, Integer pageSize) throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		List<MerchantManageVo> merchantList = new ArrayList<MerchantManageVo>();
		MerchantDetailVo mc = new MerchantDetailVo();
		mc.setClientId(clientId);
		mc.setMerchantName(merchantName);
		if (orgLevel != null) {
			switch (orgLevel) {
			case 1:
				mc.setProOrgCode(orgCode);
				break;
			case 2:
				mc.setCityOrgCode(orgCode);
				break;
			case 3:
				mc.setCountyOrgCode(orgCode);
				break;
			default:
				mc.setOrgCode(orgCode);
				break;
			}
		}
		mc.setIsEnable(true);
		List<TypeInfoVo> typeInfoList = new ArrayList<TypeInfoVo>();
		TypeInfoVo typeInfoVo = new TypeInfoVo();
		typeInfoVo.setMerchantTypeId(Constants.MINGYOU_PRODUCT);
		typeInfoList.add(typeInfoVo);
		mc.setTypeInfo(typeInfoList);
		PageVo page = new PageVo();
		if (pageNumber == null || pageSize == null) {
			pageNumber = 1;
			pageSize = 20;
		}
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		MerchantDetailPageVoRes voRes = merchantService
				.getMerchantDetailByPage(page, mc);
		List<MerchantDetailVo> merchantVoList = voRes.getMerchantDetailVoList();
		if (merchantVoList != null && merchantVoList.size() > 0) {
			for (MerchantDetailVo merchantDetailVo : merchantVoList) {
				MerchantManageVo vo = new MerchantManageVo();
				vo.setMerchantId(merchantDetailVo.getId());
				vo.setMerchantName(merchantDetailVo.getMerchantName());
				merchantList.add(vo);
			}
		}
		reMap.put("merchantList", merchantList);
		return reMap;
	}

	/**
	 * 根据机构号获取所属商户Id
	 * 
	 * @param clientId
	 * @return
	 * @throws TException
	 */
	public String getMerchantByOrgCode(String clientId, String orgCode)
			throws TException {
		String merchantId = "";
		MerchantVo mc = new MerchantVo();
		mc.setClientId(clientId);
		mc.setOrgCode(orgCode);
		mc.setIsEnable(true);
		mc.setMerchantStatus(true);// 虚拟商户
		mc.setAuditState(AuditFlagEnum.ACCEPTED.getCode());
		List<MerchantVo> merchantVoList = merchantService.getMerchant(mc);
		if (merchantVoList != null && merchantVoList.size() > 0) {
			merchantId = merchantVoList.get(0).getMerchantId();
		}
		return merchantId;
	}

	/**
	 * 获取商户分类
	 * 
	 * @param clientId
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> getMerchantType(String clientId)
			throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		List<CategoryVo> categoryList = new ArrayList<CategoryVo>();
		MerchantCategoryVo mc = new MerchantCategoryVo();
		mc.setClientId(clientId);
		mc.setParentId(0);
		mc.setIsDelete(false);
		List<MerchantCategoryVo> mcList = merchantCategoryService
				.getMerchantCategory(mc);
		if (mcList != null && mcList.size() > 0) {
			for (int i = 0; i < mcList.size(); i++) {
				CategoryVo category = new CategoryVo();
				category.setCategoryId(mcList.get(i).getId());
				category.setName(mcList.get(i).getName());
				categoryList.add(category);
			}
		}
		reMap.put("categoryList", categoryList);
		return reMap;
	}

	/**
	 * 商户用户密码重置
	 * 
	 * @param mmu
	 * @return
	 * @throws TException
	 * 
	 */
	public HashMap<String, Object> merchantManageUserUpdate(
			MerchantManageUserVo mmu, HttpServletRequest req)
					throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();

		BankOperatorVo bankOperatorVoRes = (BankOperatorVo) req
				.getAttribute(Constants.BANKOPERATOR);
		ClientMerchantAuditOrgCodeVo merchantAuditVo = clientMerchantAuditService
				.getClientMerchantAuditByOrgCode(mmu.getClientId(),
						bankOperatorVoRes.getOrgCode(), "2");
		if (!StringUtil.isNotEmpty(merchantAuditVo.getStartAuditOrgCode())
				&& !StringUtil
						.isNotEmpty(merchantAuditVo.getEndAuditOrgCode())) {
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "无权限重置密码");
			return reMap;
		}

		if (StringUtil.isNotEmpty(mmu.getMerchantUserId())) {
			MerchantUserVo mu = new MerchantUserVo();
			if (StringUtil.isNotEmpty(mmu.getMerchantUserId())) {
				mu.setId(Long.valueOf(mmu.getMerchantUserId()));
			}
			if (StringUtil.isNotEmpty(mmu.getUserPassword())) {
				mu.setPassword(mmu.getUserPassword());
			}
			mu.setIsRest(false);
			ResultVo merchantUpdateResult = merchantUserService
					.updateMerchantUser(loginService.getOriginVo(req), mu);
			if (EnumTypes.success.getCode()
					.equals(merchantUpdateResult.getResultCode())) {
				mu = merchantUserService.getMerchantUserById(
						Long.valueOf(mmu.getMerchantUserId()));
				SmsMessageVo smsMessage = new SmsMessageVo(); // 重置密码成功发送短信
				smsMessage.setClientId(mmu.getClientId());
				smsMessage.setMobile(mu.getPhone());
				smsMessage.setSendUser(mu.getUsername());
				List<String> str = new ArrayList<String>();
				str.add(mu.getUsername());
				str.add(mu.getPhone());
				str.add("111111");
				smsMessage.setValues(str);
				smsMessage.setSmsType(
						SmsTypeEnum.merchantResetLoginPwd.getValue());
				LogCvt.info("======商户用户密码重置，发送短信:开始======");
				smsMessageService.sendSMS(smsMessage);
				LogCvt.info("======商户用户密码重置，发送短信:结束======");
				reMap.put(ResultEnum.CODE.getCode(),
						merchantUpdateResult.getResultCode());
				reMap.put(ResultEnum.MESSAGE.getCode(),
						merchantUpdateResult.getResultDesc());
			} else {
				reMap.put(ResultEnum.CODE.getCode(),
						merchantUpdateResult.getResultCode());
				reMap.put(ResultEnum.MESSAGE.getCode(),
						merchantUpdateResult.getResultDesc());
			}
		} else {
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "商户用户ID不能为空");
		}
		return reMap;
	}

	/**
	 * 商户禁/启用 （同时下架此商户的商品）
	 * 
	 * @param mm
	 * @param req
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> merchantManageIsEnable(MerchantManageVo mm,
			HttpServletRequest req) throws TException {

		HashMap<String, Object> reMap = new HashMap<String, Object>();
		MerchantVo m = merchantService
				.getMerchantByMerchantId(mm.getMerchantId());
		if (m != null) {
			// MerchantVoReq voReq = new MerchantVoReq();
			ResultVo resultVo = null;
			if (mm.getIsEnable() != null && mm.getIsEnable() == false) {// 禁用
				// 下架商品
				InvalidProductBatchVo invalidProductBatchVo = new InvalidProductBatchVo();
				invalidProductBatchVo.setClientId(mm.getClientId());
				invalidProductBatchVo.setMerchantId(m.getMerchantId());
				resultVo = productService.endisableProductBatch(
						loginService.getOriginVo(req), invalidProductBatchVo,
						"0");
				if (EnumTypes.success.getCode()
						.equals(resultVo.getResultCode())) {
					// 禁用商户
					MerchantVo merchant = new MerchantVo();
					merchant.setMerchantId(mm.getMerchantId());
					merchant.setOperateTime(System.currentTimeMillis()); // 操作时间
					merchant.setOperateUser(mm.getOperator()); // 操作人
					LogCvt.info(
							"商户禁用接口：merchantService.disableMerchantByMerchantId传入条件："
									+ JSON.toJSONString(merchant));
					resultVo = merchantService.disableMerchantByMerchantId(
							loginService.getOriginVo(req), merchant);
					if (EnumTypes.success.getCode()
							.equals(resultVo.getResultCode())) {
						reMap.put(ResultEnum.CODE.getCode(),
								resultVo.getResultCode());
						reMap.put(ResultEnum.MESSAGE.getCode(),
								resultVo.getResultDesc());
					} else {
						reMap.put(ResultEnum.CODE.getCode(),
								resultVo.getResultCode());
						reMap.put(ResultEnum.MESSAGE.getCode(),
								resultVo.getResultDesc());
					}
				} else {
					reMap.put(ResultEnum.CODE.getCode(),
							resultVo.getResultCode());
					reMap.put(ResultEnum.MESSAGE.getCode(),
							resultVo.getResultDesc());
				}

			} else if (mm.getIsEnable() != null && mm.getIsEnable() == true) {// 启用
				// 审核状态全都改成待审核，团购未提交的还是未提交状态
				InvalidProductBatchVo invalidProductBatchVo = new InvalidProductBatchVo();
				invalidProductBatchVo.setClientId(mm.getClientId());
				invalidProductBatchVo.setMerchantId(m.getMerchantId());
				resultVo = productService.endisableProductBatch(
						loginService.getOriginVo(req), invalidProductBatchVo,
						"1");
				if (EnumTypes.success.getCode()
						.equals(resultVo.getResultCode())) {
					// 启用商户
					resultVo = merchantService.enableMerchantByMerchantId(
							loginService.getOriginVo(req), m.getMerchantId());
					if (EnumTypes.success.getCode()
							.equals(resultVo.getResultCode())) {
						reMap.put(ResultEnum.CODE.getCode(),
								resultVo.getResultCode());
						reMap.put(ResultEnum.MESSAGE.getCode(),
								resultVo.getResultDesc());
					} else {
						reMap.put(ResultEnum.CODE.getCode(),
								resultVo.getResultCode());
						reMap.put(ResultEnum.MESSAGE.getCode(),
								resultVo.getResultDesc());
					}
				} else {
					reMap.put(ResultEnum.CODE.getCode(),
							resultVo.getResultCode());
					reMap.put(ResultEnum.MESSAGE.getCode(),
							resultVo.getResultDesc());
				}
			}
		} else {
			reMap.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			reMap.put(ResultEnum.MESSAGE.getCode(), "商户用户不存在");
		}
		return reMap;
	}

	/**
	 * 获取当前登录用户下的所有门店（根据当前机构号）
	 * 
	 * @param orgCode
	 * @return
	 */
	public List<BankOrgRes> getBankOrgList(String clientId, String orgCode) {
		List<BankOrgRes> bankOrgList = new ArrayList<BankOrgRes>();
		List<OutletVo> outletList = null;
		try {
			outletList = outletService.getSubBankOutlet(clientId, orgCode);
			if (outletList != null && outletList.size() > 0) {
				for (OutletVo outletVo : outletList) {
					BankOrgRes bankOrg = new BankOrgRes();
					bankOrg.setOrgCode(outletVo.getOutletId());
					bankOrg.setOrgName(outletVo.getOutletName());
					bankOrgList.add(bankOrg);
				}
			}
		} catch (TException e) {
			LogCvt.error(e.getMessage(), e);
		}
		return bankOrgList;
	}

	/**
	 * 获取商户类型
	 * 
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> getMerchantType(String clientId, String flag)
			throws TException {
		HashMap<String, Object> reMap = new HashMap<String, Object>();
		MerchantTypeVo mt = new MerchantTypeVo();

		List<MerchantTypeRes> resList = new ArrayList<MerchantTypeRes>();

		MerchantTypeRes mres = null;
		mt.setClientId(clientId);
		List<MerchantTypeVo> list = merchantTypeService.getMerchantType(mt);

		for (MerchantTypeVo mvo : list) {
			mres = new MerchantTypeRes();

			mres.setDelete(mvo.isDelete);
			mres.setTypeName(mvo.getTypeName());
			mres.setId(mvo.getId() + "");

			resList.add(mres);

		}

		reMap.put("merchantTypeList", resList);

		return reMap;
	}

	public MerchantTempVo changeNoUpdateValue(MerchantTempVo merchantTempVo) {
		// 这些不改动，商户审核通过
		// 登录名、营业执照、商户类型、
		merchantTempVo.setLoginName(null); // 登录人
		merchantTempVo.setLicense(null); // 营业执照

		// 需求已更改可以修改下面这些字段
		/*
		 * mm.setCategoryType(null); mm.setCategory(null);
		 * mm.setLegalName(null); mm.setLegalCredentType(null);
		 * mm.setLegalCredentNo(null); mm.setCompanyCredential(null);
		 * mm.setOpeningBank(null); mm.setContractBegintime(null);
		 * mm.setContractEndtime(null); // 需求已更改可以修改该字段 //
		 * mm.setContactName(null); mm.setAcctName(null);
		 * mm.setAcctNumber(null); mm.setLoginName(null);
		 * mm.setLoginPhone(null);
		 */
		return merchantTempVo;
	}

	/**
	 * 
	 * getMerchantListExport:(商户列表导出优化).
	 *
	 * @author wufei 2015-9-6 下午02:40:27
	 * @param merchant
	 * @return
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public Map<String, Object> getMerchantListExport(MerchantReq merchant)
			throws TException, ParseException {
		LogCvt.info("商户列表下载请求参数: " + JSON.toJSONString(merchant));
		Map<String, Object> resMap = new HashMap<String, Object>();
		MerchantVo m = new MerchantVo();
		// 设置请求参数
		setMerchantValueToReqVo(merchant, m);
		ExportResultRes result = merchantService.getMerchantExport(m);
		LogCvt.info("商户列表下载server返回数据: " + JSON.toJSONString(result));
		ResultVo resultVo = result.getResultVo();
		if (resultVo != null && EnumTypes.success.getCode()
				.equals(resultVo.getResultCode())) {
			resMap.put("url", result.getUrl());
		} else {
			resMap.put(ResultEnum.CODE.getCode(),
					result.getResultVo().getResultCode());
			resMap.put(ResultEnum.MESSAGE.getCode(),
					result.getResultVo().getResultDesc());
		}
		return resMap;

	}

	/**
	 * 
	 * setMerchantValue:待审核商户请求参数设置
	 *
	 * @author 明灿 2015年9月29日 上午10:47:41
	 * @param merchant
	 *            请求参数Vo
	 * @param m
	 *            server请求参数vo
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	private void setMerchantValueToReqVo(MerchantReq merchant, MerchantVo m)
			throws TException, ParseException {
		m.setClientId(merchant.getClientId());
		// 签约状态过滤(0为全部,不设查询条件)
		if ("1".equals(merchant.getSignStatus())) {
			// 签约
			// m.setIsEnable(true);//只有审核通过才是true，待审核和审核不通过为false
			m.setDisableStatus("0");
		}
		if ("2".equals(merchant.getSignStatus())) {
			// 禁用
			m.setIsEnable(false);
			m.setDisableStatus("1");
		}
		if ("3".equals(merchant.getSignStatus())) {
			// 解约
			m.setIsEnable(false);
			m.setDisableStatus("2");
		}

		if (StringUtils.isNotEmpty(merchant.getOrgCode())) {// 所属机构

			// 多机构查询时，只设置orgCOde值，后台服务进行区分
			if (merchant.getOrgCode().indexOf(",") != -1) {
				m.setOrgCode(merchant.getOrgCode());
			} else {
				OrgVo orgVo = orgService.getOrgById(merchant.getClientId(),
						merchant.getOrgCode());
				if (orgVo != null && orgVo.getOrgLevel() != null) {
					int level = Integer.parseInt(orgVo.getOrgLevel());
					switch (level) {
					case 1:
						m.setProOrgCode(merchant.getOrgCode());
						break;
					case 2:
						m.setCityOrgCode(merchant.getOrgCode());
						break;
					case 3:
						m.setCountyOrgCode(merchant.getOrgCode());
						break;
					default:
						m.setOrgCode(merchant.getOrgCode());
						break;
					}
				}
			}
		}

		if (merchant.getAuditState() != null) {// 审核状态
			m.setAuditState(merchant.getAuditState().toString());
		} else {
			m.setAuditState(null);
		}
		if (StringUtils.isNotEmpty(merchant.getMerchantName())) {// 商户名称
			m.setMerchantName(merchant.getMerchantName());
		}
		if (StringUtils.isNotEmpty(merchant.getStartDate())) {// 结束时间
			m.setStartCreateTime(DateUtil.DateFormat(merchant.getStartDate()));
		}
		if (StringUtils.isNotEmpty(merchant.getEndDate())) {// 开始时间
			m.setEndCreateTime(DateUtil.DateFormatEnd(merchant.getEndDate()));
		}
		if (StringUtils.isNotEmpty(merchant.getAuditStartTime())) {// 审核开始时间
			m.setStartAuditTime(
					DateUtil.DateFormat(merchant.getAuditStartTime()));
		}
		if (StringUtils.isNotEmpty(merchant.getAuditEndTime())) {// 审核结束时间
			m.setEndAuditTime(
					DateUtil.DateFormatEnd(merchant.getAuditEndTime()));
		}
		// if (StringUtils.isNotEmpty(merchant.getMerchantType())) {// 所属类型
		// List<Long> typeList = new ArrayList<Long>();
		// typeList.add(Long.valueOf(merchant.getMerchantType()));
		// m.setTypeInfoList(typeList);
		// }
		// if (StringUtils.isNotEmpty(merchant.getCategory())) {// 所属分类
		// List<Long> typeList = new ArrayList<Long>();
		// typeList.add(Long.valueOf(merchant.getCategory()));
		// m.setCategoryInfoList(typeList);
		// }
		if (StringUtil.isNotEmpty(merchant.getLicense())) {// 营业执照
			m.setLicense(merchant.getLicense());
		}
		if (StringUtil.isNotEmpty(merchant.getEditAuditStatus())) {
			m.setEditAuditState(merchant.getEditAuditStatus()); // 编辑审核状态
		}
		m.setMerchantStatus(false); // 只查普通商户
	}

	/**
	 * 
	 * checkAcctNumber:(这里用一句话描述这个方法的作用).
	 *
	 * @author wm 2015年9月21日 下午12:04:26
	 * @param car
	 * @return
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public Map<String, Object> checkAcctNumber(BankCarCheckReq car)
			throws TException, ParseException {
		LogCvt.info("银行卡号校验请求参数: " + JSON.toJSONString(car));
		Map<String, Object> resMap = new HashMap<String, Object>();

		ResultVo res = bankCardService.bankCardAccountCheck(car.getClientId(),
				car.getAccountName(), car.getAccountNo(),
				car.getCertificateType(), car.getCertificateNo());
		LogCvt.info("银行卡号校验server返回数据: " + JSON.toJSONString(res));

		if (EnumTypes.fail.getCode().equals(res.getResultCode())) {
			resMap.put(ResultEnum.CODE.getCode(), res.getResultCode());
			resMap.put(ResultEnum.MESSAGE.getCode(), res.getResultDesc());
		} else {
			if (res.getResultCode().equals("01")) {
				resMap.put(ResultEnum.CODE.getCode(), "9999");
				resMap.put(ResultEnum.MESSAGE.getCode(), "账户校验失败");
			} else {
				resMap.put(ResultEnum.CODE.getCode(), "0000");
				resMap.put(ResultEnum.MESSAGE.getCode(), "账户校验成功");
			}
		}
		return resMap;
	}

	/**
	 * @throws TException
	 *             新增超级管理员 @description @author liuhuangle@f-road.com.cn @date
	 *             2015年10月19日 上午10:52:00 @throws
	 */
	private ResultVo addFinityUserResource(Long userId, String clientId)
			throws TException {
		LogCvt.info("新增商户资源数据开始----" + userId);
		// 商户新增成功，需要绑定商户用户-资源，超级管理员默认绑定所有资源信息
		List<UserResourceVo> userResourceVoList = new ArrayList<UserResourceVo>();
		MerchantRoleResourceVo merchantRoleResourceVo = merchantRoleResourceService
				.getMerchantRoleResource(clientId + "_999999999");
		LogCvt.info("绑定商户用户-资源返回：" + JSON.toJSONString(merchantRoleResourceVo));
		if (merchantRoleResourceVo != null
				&& merchantRoleResourceVo.getResources() != null
				&& merchantRoleResourceVo.getResources().size() > 0) {
			for (ResourceVo vo : merchantRoleResourceVo.getResources()) {
				UserResourceVo urVo = new UserResourceVo();
				urVo.setResourceId(vo.getResource_id());
				urVo.setUserId(userId);
				urVo.setUserType(1);// 商户用户
				userResourceVoList.add(urVo);
			}
			LogCvt.info("新增商户资源数据开始----end : " + userResourceVoList.size());
			return finityUserResourceService
					.addUserResourceByBatch(userResourceVoList);// 批量新建商户资源关系end
		}
		return new ResultVo("9999", "未获取新增商户用户资源信息");
	}

	/**
	 * 
	 * getCompanyList:获取外包公司列表
	 * 
	 * @author chenmingcan@froad.com.cn 2015年11月26日 下午4:50:26
	 * @param orgCode
	 * @param clientId
	 * @return
	 * @throws TException
	 *
	 */
	public Map<String, Object> getCompanyList(String dicCode, String clientId)
			throws TException {
		LogCvt.info(
				"获取外包公司列表请求参数: dicCode=" + dicCode + "clientId=" + clientId);
		Map<String, Object> resMap = new HashMap<String, Object>();
		List<BankCompanyResVo> list = new ArrayList<BankCompanyResVo>();
		BankCompanyResVo companyResVo = null;
		List<DictionaryItemVo> dictionaryItemList = dictionaryService
				.getDictionaryItemList(dicCode, clientId);
		if (LogCvt.isDebugEnabled()) {
			LogCvt.debug(
					"获取外包公司列表返回数据:" + JSON.toJSONString(dictionaryItemList));
		}
		if (null != dictionaryItemList && dictionaryItemList.size() > 0) {
			for (DictionaryItemVo dictionaryItemVo2 : dictionaryItemList) {
				companyResVo = new BankCompanyResVo();
				companyResVo.setCompanyId(dictionaryItemVo2.getId());
				companyResVo.setCompanyName(dictionaryItemVo2.getItemName());
				list.add(companyResVo);
			}
		}
		resMap.put("comList", list);
		return resMap;
	}
}
