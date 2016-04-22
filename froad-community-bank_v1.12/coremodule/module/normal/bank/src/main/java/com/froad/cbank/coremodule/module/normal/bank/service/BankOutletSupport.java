package com.froad.cbank.coremodule.module.normal.bank.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.BessDataEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.OutletDisableStatusEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProcessTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.BankHandle;
import com.froad.cbank.coremodule.module.normal.bank.util.BessDataUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.OrgCodeUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.PageUtil;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletAuditReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletCategoryInfoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletDetailReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletDetailRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletDetailResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletListReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletListResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankTaskResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OldBankOutletResVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OutletImageInfoRes;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.FallowExecuteService;
import com.froad.thrift.service.FallowQueryService;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.MerchantOutletPhotoService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.ActorVo;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.AuditInstanceDetailVo;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.ExecuteTaskReqVo;
import com.froad.thrift.vo.ExecuteTaskResVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.GetPendAuitListPageVo;
import com.froad.thrift.vo.GetPendAuitListReqVo;
import com.froad.thrift.vo.GetTaskOverviewReqVo;
import com.froad.thrift.vo.GetTaskOverviewResVo;
import com.froad.thrift.vo.GetTaskReqVo;
import com.froad.thrift.vo.GetTaskResVo;
import com.froad.thrift.vo.MerchantAccountVo;
import com.froad.thrift.vo.MerchantOutletPhotoVo;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletPreferPageVoRes;
import com.froad.thrift.vo.OutletPreferVo;
import com.froad.thrift.vo.OutletTempVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.Restrictions;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.TaskListResVo;

import net.sf.json.JSONObject;

@Service
public class BankOutletSupport {

	@Resource
	FallowQueryService.Iface fallowQueryService;
	@Resource
	BankOperatorService.Iface bankOperatorService;
	@Resource
	OutletService.Iface outletService;
	@Resource
	MerchantService.Iface merchantService;
	@Resource
	MerchantUserService.Iface merchantUserService;
	@Resource
	MerchantAccountService.Iface merchantAccountService;
	@Resource
	MerchantOutletPhotoService.Iface merchantOutletPhotoService;
	@Resource
	OrgService.Iface orgService;
	@Resource
	FallowExecuteService.Iface fallowExecuteService;
	@Resource
	AreaService.Iface areaService;
	
	public static final String DELETE = "4";
	public static final String ALL = "5";
	public Map<String, Object> queryOutletList(BankOutletReqVo reqVo) throws Exception {
		LogCvt.info("银行管理平台待审核门店列表请求参数" + JSON.toJSONString(reqVo));
		Map<String, Object> resMap = new HashMap<String, Object>();
		GetPendAuitListReqVo req = new GetPendAuitListReqVo();
		// 请求参数封装
		this.packageReqValue4ListQuery(reqVo, req);
		// 分页参数
		PageVo pageVo = this.reqPageValue(reqVo);
		// 调用server接口
		GetPendAuitListPageVo pendAuit = fallowQueryService.getPendAuitList(req, pageVo);
		LogCvt.info("银行管理平台待审核门店列表请求返回数据" + JSON.toJSONString(pendAuit));
		// 返回数据封装
		if (pendAuit != null) {
			PageVo page = pendAuit.getPage();
			ResultVo result = pendAuit.getResult();
			if (result != null && result.getResultCode() != null) {
				// 成功
				if (EnumTypes.success.getCode().equals(result.getResultCode())) {
					List<AuditInstanceDetailVo> auitList = pendAuit.getAuditDetailList();
					if (auitList != null && auitList.size() > 0) {
						// 属性拷贝
						List<BankOutletResVo> outletList = this
								.copyOutletList(auitList);
						resMap.put("outletList", outletList);
					}
				} else {
					// 失败
					resMap.put("code", result.getResultCode());
					resMap.put("message", pendAuit.getResult().getResultDesc());
				}
			}
			// 分页信息封装返回
			setPagesValueToResMap(resMap, page);
		}
		return resMap;
	}
	/**
	 * 
	* @Title: reqPageValue 
	* @Description: 分页请求参数封装
	* @param BankOutletReqVo
	* @return    
	* @return PageVo     
	* @throws
	 */
	private PageVo reqPageValue(BankOutletReqVo reqVo) {
		PageVo page = new PageVo();
		page.setPageNumber(reqVo.getPageNumber() == null ? 1 : reqVo.getPageNumber());
		page.setPageSize(reqVo.getPageSize() == null ? 10 : reqVo.getPageSize());
		if (reqVo.getLastPageNumber() != null) {
			page.setLastPageNumber(reqVo.getLastPageNumber());
		}
		if (reqVo.getFirstRecordTime() != null) {
			page.setFirstRecordTime(reqVo.getFirstRecordTime());
		}
		if (reqVo.getLastRecordTime() != null) {
			page.setLastRecordTime(reqVo.getLastRecordTime());
		}
		return page;
	}
	
	/**
	 * @throws TException 
	 * @throws NumberFormatException 
	 * 
	* @Title: packageReqValue4ListQuery 
	* @Description: 待审核门店列表查询请求参数封装  
	* @param reqVo
	* @param req
	* @throws ParseException
	* @throws JsonGenerationException
	* @throws JsonMappingException
	* @throws IOException    
	* @return void     
	* @throws
	 */
	private void packageReqValue4ListQuery(BankOutletReqVo reqVo, GetPendAuitListReqVo req) throws ParseException, JsonGenerationException, JsonMappingException, IOException, NumberFormatException, TException {
		Map<Restrictions, String> orBessData = new HashMap<Restrictions, String>();
		if (StringUtil.isNotBlank(reqVo.getStartDate())) {
			req.setStarTime(DateUtil.DateFormat(reqVo.getStartDate()));
		}
		if (StringUtil.isNotBlank(reqVo.getEndDate())) {
			req.setEndTime(DateUtil.DateFormat(reqVo.getEndDate()));
		}
		orBessData = BessDataUtil.getOrBessDataMapWithMerchantNameAndOutName(
				reqVo.getMerchantName(), reqVo.getOutletName(), null, null,
				orBessData);
		if (orBessData.size()>0) {
			req.setOrBessData(orBessData);
		}
		// 获取操作员对象
		req.setOrigin(getOrigin(reqVo));
		// 请求待审核的类型
		req.setProcessType(ProcessTypeEnum.OUTLET.getCode());
		// System.out.println(ProcessTypeEnum.OUTLET.getCode());
	}
	
	/**
	 * @throws TException 
	 * @throws NumberFormatException 
	 * 
	* @Title: getOrigin 
	* @Description: 门店审核时获取操作源对象
	* @param userId
	* @return    
	* @return OriginVo     
	* @throws
	 */
	private OriginVo getOrigin(BankOutletReqVo reqVo) throws NumberFormatException, TException {
		BankOperatorVo bankOperatorVo = bankOperatorService.getBankOperatorById(reqVo.getClientId(),
				Long.parseLong(reqVo.getUserId()));
		OriginVo originVo = BankHandle.getOriginVo(bankOperatorVo);
		originVo.setOperatorIp(reqVo.getIp());
		return originVo;
	}
	
	/**
	 * 
	* @Title: getOrigin 
	* @Description: 门店审核时获取操作源对象
	* @param reqVo
	* @return
	* @throws NumberFormatException
	* @throws TException    
	* @return OriginVo     
	* @throws
	 */
	private OriginVo getOrigin(BankOutletAuditReqVo reqVo) throws NumberFormatException, TException {
		BankOperatorVo bankOperatorVo = bankOperatorService.getBankOperatorById(reqVo.getClientId(),
				Long.parseLong(reqVo.getUserId()));
		OriginVo originVo = BankHandle.getOriginVo(bankOperatorVo);
		originVo.setOperatorIp(reqVo.getIp());
		return originVo;
	}

	/**
	 * 
	* @Title: getOrigin 
	* @Description: 重载获取操作对象
	* @param reqVo
	* @return
	* @throws NumberFormatException
	* @throws TException    
	* @return OriginVo     
	* @throws
	 */
	private OriginVo getOrigin(BankOutletDetailReqVo reqVo) throws NumberFormatException, TException {
		BankOperatorVo bankOperatorVo = bankOperatorService.getBankOperatorById(reqVo.getClientId(),
				Long.parseLong(reqVo.getUserId()));
		OriginVo originVo = BankHandle.getOriginVo(bankOperatorVo);
		originVo.setOperatorIp(reqVo.getIp());
		return originVo;
	}
	/**
	 * 
	 * @Title: copyOutletList 
	 * @Description: 待审核门店列表属性拷贝 
	 * @param auitList 
	 * @return
	 * List<BankOutletResVo> @throws
	 */
	private List<BankOutletResVo> copyOutletList(List<AuditInstanceDetailVo> auitList) {
		List<BankOutletResVo> outletList = new ArrayList<BankOutletResVo>();
		BankOutletResVo bankOutletResVo = null;
		for (AuditInstanceDetailVo outletResVo : auitList) {
			bankOutletResVo = new BankOutletResVo();
			// 属性拷贝
			this.copyBankOutletValue(outletResVo, bankOutletResVo);
			outletList.add(bankOutletResVo);
		}
		return outletList;
	}
	
	/**
	 * 
	* @Title: copyBankOutletValue 
	* @Description: 对象属性拷贝
	* @param outletResVo 数据源
	* @param bankOutletResVo  目标  
	* @return void     
	* @throws
	 */
	private void copyBankOutletValue(AuditInstanceDetailVo outletResVo, BankOutletResVo bankOutletResVo) {
		bankOutletResVo.setAuditNumber(outletResVo.getInstanceId());// 审核流水号
		bankOutletResVo.setCreateTime(outletResVo.getCreateTime());// 创建时间
		ActorVo nextActor = outletResVo.getNextActor();
		bankOutletResVo.setAuditType(outletResVo.getProcessTypeDetail());// 审核类型
		if (nextActor != null) {
			bankOutletResVo.setTaskId(nextActor.getTaskId());
		}
		String josnData = outletResVo.getBessData();
		JSONObject jsonObject = JSONObject.fromObject(josnData);
		if (jsonObject != null) {
			bankOutletResVo.setMerchantName(
					jsonObject.get(BessDataEnum.merchantName.getKey()) == null
							? null
							: jsonObject.get(BessDataEnum.merchantName.getKey())
									.toString());// 商户名称
			// bankOutletResVo.setOutletId(
			// jsonObject.get(BessDataEnum.outletId.getKey()) == null
			// ? null
			// : jsonObject.get(BessDataEnum.outletId.getKey())
			// .toString());// 门店编号
			bankOutletResVo.setOutletName(
					jsonObject.get(BessDataEnum.outletName.getKey()) == null
							? null
							: jsonObject.get(BessDataEnum.outletName.getKey())
									.toString());// 门店名称
		}
		bankOutletResVo.setOutletId(outletResVo.getBessId());// 门店编号
	}

	/**
	 * 
	 * setPagesValueToResMap:封装分页信息
	 *
	 * @author 明灿 2015年9月29日 上午10:42:10
	 * @param reMap
	 *            返回前端的Map集合
	 * @param pageVo
	 *            server返回的page信息
	 *
	 */
	private void setPagesValueToResMap(Map<String, Object> reMap, PageVo pageVo) {
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
	 * @throws TException 
	 * 
	* @Title: addOutletDetail 
	* @Description: 新增门店详情 
	* @param reqVo
	* @return    
	* @return Map<String,Object>     
	* @throws
	 */
	public Map<String, Object> addOutletDetail(BankOutletDetailReqVo reqVo) throws TException {
		LogCvt.info("新增门店审核详情请求参数:" + JSON.toJSONString(reqVo));
		Map<String, Object> resMap = new HashMap<String, Object>();
		OriginVo origin = getOrigin(reqVo);
		BankOutletDetailResVo detailResVo = new BankOutletDetailResVo();
		// 门店信息获取
		OutletVo outletVo = outletService.getOutletByOutletId(reqVo.getOutletId());
		
		LogCvt.info("新增门店审核详情请求门店信息返回:" + JSON.toJSONString(outletVo));
		// 获取对应的商户对象
		MerchantVo merchantVo = getMerchantById(outletVo.getMerchantId());
		LogCvt.info("门店审核详情请求之商户信息返回:" + JSON.toJSONString(merchantVo));
		// 封装门店信息
		if (outletVo != null) {
			packageAddOutletValue(outletVo, detailResVo);
			// 拼接商户名称
			detailResVo.setMerchantName(getMerchantName(merchantVo));// 商户名称
		}
		// 审核订单详情获取
		GetTaskOverviewReqVo req = new GetTaskOverviewReqVo();
		req.setInstanceId(reqVo.getAuditNumber());
		req.setOrigin(origin);
		GetTaskOverviewResVo taskOverview = fallowQueryService.getTaskOverview(req);
		LogCvt.info("新增门店审核详情请求订单详情返回:" + JSON.toJSONString(taskOverview));
		// 封装审核订单详情
		if (taskOverview != null) {
			this.packageTaskOrderValue(taskOverview, detailResVo,
					reqVo.getClientId());
			String creator = taskOverview.getCreator();
			LogCvt.info("任务概括创建人id:" + creator);
			if(StringUtil.isNotBlank(creator)){
				MerchantUserVo merchantUserVo = merchantUserService.getMerchantUserById(Long.parseLong(creator));
				LogCvt.info("任务概括创建人返回数据:" + JSON.toJSONString(merchantUserVo));
				if (merchantUserVo != null) {
					detailResVo.setCreater(merchantUserVo.getUsername());// 创建人
				}
			}
			// 创建机构
			detailResVo.setCreateOrgName(getOrgNameByOrgCode(reqVo.getClientId(), merchantVo.getOrgCode()));// 创建机构
		}
		// 任务单列表获取
		GetTaskReqVo reqTask = new GetTaskReqVo();
		reqTask.setInstanceId(reqVo.getAuditNumber());
		reqTask.setOrigin(origin);
		GetTaskResVo taskList = fallowQueryService.getTaskList(reqTask);
		LogCvt.info("新增门店审核详情请求任务表单返回:" + JSON.toJSONString(taskList));
		// 封装任务表单列表详情
		if (taskList != null) {
			packageTaskListValue(taskList, detailResVo, reqVo.getClientId());
		}
		resMap.put("outlet", detailResVo);
		return resMap;
	}
	
	/**
	 * @throws TException 
	 * 
	* @Title: packageTaskListValue 
	* @Description: 任务表单列表封装返回
	* @param taskOverview
	* @param taskList    
	* @return void     
	* @throws
	 */
	private void packageTaskListValue(GetTaskResVo taskList, BankOutletDetailResVo detailResVo,String clientId) throws TException {
		if (taskList != null) {
			List<TaskListResVo> taskListRes = taskList.getTaskListRes();
			List<BankTaskResVo> bankTaskList = new ArrayList<BankTaskResVo>();
			BankTaskResVo bankTaskResVo = null;
			if (taskListRes != null && taskListRes.size() > 0) {
				for (TaskListResVo taskListResVo : taskListRes) {
					bankTaskResVo = new BankTaskResVo();
					bankTaskResVo.setAuditor(getAuditorNameByUserId(clientId, taskListResVo.getAuditor()));// 审核人userId
					bankTaskResVo.setAuditOrgName(getOrgNameByOrgCode(clientId, taskListResVo.getOrgId()));// 审核机构
					bankTaskResVo.setAuditStatus(taskListResVo.getAuditState());// 状态
					bankTaskResVo.setAuditTime(taskListResVo.getAuditTime());// 审核时间
					bankTaskResVo.setComment(taskListResVo.getRemark());// 备注
					bankTaskResVo.setCreateTime(taskListResVo.getCreateTime());// 创建时间
					bankTaskResVo.setTaskId(taskListResVo.getTaskId());// 任务流水号
					bankTaskList.add(bankTaskResVo);
				}
			}
			detailResVo.setTaskList(bankTaskList);
		}
	}
	
	/**
	 * 
	 * getAuditorNameByUserId:根据审核人的id获取审核人名称
	 *
	 * @author user 2015年11月4日 上午11:14:56
	 * @param auditor
	 * @throws TException
	 * @throws NumberFormatException
	 *
	 */
	private String getAuditorNameByUserId(String clientId, String auditorUserId)
			throws NumberFormatException, TException {
		String auditorName = "";
		if (!StringUtil.isNotBlank(auditorUserId)) {
			return auditorName;
		}
		BankOperatorVo operatorVo = bankOperatorService.getBankOperatorById(clientId, Long.parseLong(auditorUserId));
		LogCvt.info("门店详情之操作员对象返回:" + JSON.toJSONString(operatorVo));
		if (operatorVo != null) {
			auditorName = operatorVo.getUsername();
		}
		return auditorName;
	}

	/**
	 * 
	 * @Title: getOrgNameByOrgCode
	 * @Description: 返回机构名称
	 * @param clientId
	 * @param orgCode
	 * @return @throws TException
	 * @return String @throws
	 */
	private String getOrgNameByOrgCode(String clientId, String orgCode) throws TException {
		String orgName = "";
		OrgVo orgVo = orgService.getOrgById(clientId, orgCode);
		LogCvt.info("门店审核详情请求orgName返回:" + JSON.toJSONString(orgVo));
		if (orgVo != null) {
			orgName = orgVo.getOrgName();
		}
		return orgName;
	}
	/**
	 * @throws TException 
	 * 
	* @Title: packageTaskOrderValue 
	* @Description: 封装返回审核订单数据
	* @param taskOverview
	* @param detailResVo    
	* @return void     
	* @throws
	 */
	private void packageTaskOrderValue(GetTaskOverviewResVo taskOverview, BankOutletDetailResVo detailResVo,
			String clientId) throws TException {
		detailResVo.setAuditNumber(taskOverview.getInstanceId());// 审核流水号
		detailResVo.setOutletId(taskOverview.getBessId());// 门店编号
		detailResVo.setAuditStatus(taskOverview.getAuditState());// 审核状态
		detailResVo.setCreateTime(taskOverview.getCreateTime());// 创建时间
	}
	/**
	 * @throws TException 
	 * 
	* @Title: packageAddOutletValue 
	* @Description: 封装返回门店详情信息
	* @param outletVo
	* @param detailResVo    
	* @return void     
	* @throws
	 */
	private void packageAddOutletValue(OutletVo outletVo, BankOutletDetailResVo detailResVo) throws TException {
		detailResVo.setOutletName(outletVo.getOutletName());// 门店名称
		detailResVo.setTellphone(outletVo.getPhone());// 联系电话
		detailResVo.setOutletDescription(outletVo.getDescription());// 门店描述
		detailResVo.setSellTime(outletVo.getBusinessHours());// 营业时间
		/**
		 * 详情地址
		 */
		String address = getAddress(outletVo.getAreaId());// 地区
		detailResVo.setAddress(address + outletVo.getAddress());// 地区+详情位置=地址
		detailResVo.setContactName(outletVo.getContactName());// 联系人名字
		detailResVo.setContactPhone(outletVo.getContactPhone());// 联系人电话
		detailResVo.setDiscount(outletVo.getDiscount());// 折扣
		detailResVo.setDiscountCode(outletVo.getDiscountCode());// 折扣码
		detailResVo.setDiscountRate(outletVo.getDiscountRate());// 折扣比
		detailResVo.setPreferDetails(outletVo.getPreferDetails());// 优惠详情
		/**
		 * 照片获取
		 */
		MerchantOutletPhotoVo merchantOutletPhotoVo = new MerchantOutletPhotoVo();// 照片
		detailResVo.setDefaultImage(
				getOutletPhoto(merchantOutletPhotoVo, outletVo.getMerchantId(), outletVo.getOutletId()));
		MerchantAccountVo accountVo = getAccount(outletVo.getClientId(),outletVo.getMerchantId(),outletVo.getOutletId());
		if (accountVo != null) {
			detailResVo.setAcctName(accountVo.getAcctName());// 收款账户名
			detailResVo.setAcctNumber(accountVo.getAcctNumber());// 收款账户号
		}
		/**
		 * 门店所属分类获取
		 */
		String categories = getCategories(outletVo, detailResVo);
		detailResVo.setCategories(categories);
	}

	private String getCategories(OutletVo outletVo, BankOutletDetailResVo detailResVo) {
		List<CategoryInfoVo> cateList = outletVo.getCategoryInfo();
		StringBuffer categories = new StringBuffer();
		if (cateList != null && cateList.size() > 0) {
			for (int i = 0; i < cateList.size(); i++) {
				if (i != (cateList.size() - 1)) {
					categories.append(cateList.get(i).getName()).append(",");
				} else {
					categories.append(cateList.get(i).getName());
				}
			}
		}
		return categories.toString();
	}
	
	/**
	 * 
	 * getAddress:门店详情的具体地址
	 *
	 * @author user 2015年11月4日 下午2:55:01
	 * @param areaVo
	 * @return
	 * @throws TException
	 *
	 */
	private String getAddress(long areaId) throws TException {
		AreaVo areaVo = areaService.findAreaById(areaId);
		LogCvt.info("门店详情地址返回:" + JSON.toJSONString(areaVo));
		String countyName = "";
		String cityName = "";
		String areaName = "";
		if (areaVo != null) {
			String[] pathid = areaVo.getTreePath().split(",");
			String[] name = areaVo.getTreePathName().split(",");
			if (pathid.length > 0 && name.length > 0) {
				countyName = name[0];
				if (pathid.length > 1) {
					cityName = name[1];
				}
				if (pathid.length > 2) {
					areaName = name[2];
				}
			}

		}
		return countyName + cityName + areaName;
	}
	/**
	 * 
	* @Title: getOutletPhoto 
	* @Description: 获取门店照片信息
	* @param merchantOutletPhotoVo
	* @param outletVo
	* @return
	* @throws TException    
	* @return List<String>     
	* @throws
	 */
	private List<String> getOutletPhoto(MerchantOutletPhotoVo merchantOutletPhotoVo, String merchantId, String outletId)
			throws TException {
		merchantOutletPhotoVo.setMerchantId(merchantId);
		merchantOutletPhotoVo.setOutletId(outletId);
		List<MerchantOutletPhotoVo> outletPhoto = merchantOutletPhotoService
				.getMerchantOutletPhoto(merchantOutletPhotoVo);
		LogCvt.info("新增门店审核详情请求照片返回:" + JSON.toJSONString(outletPhoto));
		List<String> potoList = returnPhotoUrl(outletPhoto);
		return potoList;
	}

	/**
	 * 
	 * returnPhotoUrl:返回相册路劲集合,第一张为logo
	 *
	 * @author user 2015年11月12日 下午3:35:18
	 * @param outletPhoto
	 * @return
	 *
	 */
	private List<String> returnPhotoUrl(List<MerchantOutletPhotoVo> outletPhoto) {
		List<String> potoList = new ArrayList<String>();
		if (outletPhoto != null && outletPhoto.size() > 0) {
			int i = 1;
			for (MerchantOutletPhotoVo photoVo : outletPhoto) {
				if(photoVo.isDefault){
					potoList.add(0, photoVo.getSource());
				} else {
					potoList.add(i, photoVo.getSource());
					i++;
				}
			}
		}
		return potoList;
	}
	/**
	 * 
	* @Title: getAccount 
	* @Description: 获取收款帐号
	* @param outletVo
	* @return
	* @throws TException    
	* @return MerchantAccountVo     
	* @throws
	 */
	private MerchantAccountVo getAccount(String clientId, String merchantId, String outletId) throws TException {
		MerchantAccountVo merchantAccountVo = new MerchantAccountVo();
		merchantAccountVo.setClientId(clientId);
		merchantAccountVo.setMerchantId(merchantId);
		merchantAccountVo.setOutletId(outletId);
		// 查询未删除的账户
		merchantAccountVo.setIsDelete(false);
		List<MerchantAccountVo> account = merchantAccountService.getMerchantAccount(merchantAccountVo);
		LogCvt.info("新增门店审核详情请求收款账户返回:" + JSON.toJSONString(account));
		if (!account.isEmpty()) {
			return account.get(0);
		}
		return null;

	}

	/**
	 * 
	 * getMerchantName:返回商户的名称
	 *
	 * @author user 2015年11月4日 下午4:07:53
	 * @param merchantVo
	 * @return
	 * @throws TException
	 *
	 */
	private String getMerchantName(MerchantVo merchantVo) throws TException {
		String merchantName = "";
		if (merchantVo != null) {
			merchantName = merchantVo.getMerchantName();
		}
		return merchantName;
	}

	/**
	 * 
	 * getMerchantById:根据商户id查询商户
	 *
	 * @author user 2015年11月4日 下午4:01:33
	 * @param merchantId
	 * @return
	 * @throws TException
	 *
	 */
	private MerchantVo getMerchantById(String merchantId) throws TException {
		MerchantVo merchantVo = merchantService.getMerchantByMerchantId(merchantId);
		LogCvt.info("新增门店审核详情请求商户返回:" + JSON.toJSONString(merchantVo));
		return merchantVo;
	}

	/**
	 * @throws TException 
	 * @throws NumberFormatException 
	 * 
	* @Title: updateOutletDetail 
	* @Description: 更新门店审核详情 
	* @param reqVo
	* @return    
	* @return Map<String,Object>     
	* @throws
	 */
	public Map<String, Object> updateOutletDetail(BankOutletDetailReqVo reqVo) throws NumberFormatException, TException {
		LogCvt.info("更新门店审核详情请求参数:" + JSON.toJSONString(reqVo));
		Map<String, Object> resMap = new HashMap<String, Object>();
		OriginVo origin = getOrigin(reqVo);
		BankOutletDetailResVo detailResVo = new BankOutletDetailResVo();
		// 门店信息获取
		OutletTempVo outletTempVo = outletService.getOutletModifyInfoByAuditBox(
				reqVo.getOutletId(), reqVo.getAuditNumber());
		LogCvt.info("更新门店审核详情请求门店信息返回:" + JSON.toJSONString(outletTempVo));
		// 获取对应的商户对象
		MerchantVo merchantVo = getMerchantById(outletTempVo.getMerchantId());
		LogCvt.info("门店审核详情请求之商户信息返回:" + JSON.toJSONString(outletTempVo));
		// 封装门店信息
		if (outletTempVo != null) {
			this.packageUpdateOutletValue(outletTempVo, detailResVo);
			detailResVo.setMerchantName(getMerchantName(merchantVo));// 商户名称
		}
		// 审核订单详情获取
		GetTaskOverviewReqVo req = new GetTaskOverviewReqVo();
		req.setInstanceId(reqVo.getAuditNumber());
		req.setOrigin(origin);
		GetTaskOverviewResVo taskOverview = fallowQueryService.getTaskOverview(req);
		LogCvt.info("更新门店审核详情请求订单详情返回:" + JSON.toJSONString(taskOverview));
		// 封装审核订单详情
		if (taskOverview != null) {
			packageTaskOrderValue(taskOverview, detailResVo, reqVo.getClientId());
			/**
			 * 创建人名称获取
			 */
			String creator = taskOverview.getCreator();
			LogCvt.info("任务概括创建人id:" + creator);
			if (StringUtil.isNotBlank(creator)) {
				MerchantUserVo merchantUserVo = merchantUserService.getMerchantUserById(Long.parseLong(creator));
				LogCvt.info("任务概括创建人返回数据:" + JSON.toJSONString(merchantUserVo));
				if (merchantUserVo != null) {
					detailResVo.setCreater(merchantUserVo.getUsername());// 创建人
				}
			}
			// 创建机构
			detailResVo.setCreateOrgName(getOrgNameByOrgCode(reqVo.getClientId(), merchantVo.getOrgCode()));// 创建机构

		}
		// 任务单列表获取
		GetTaskReqVo reqTask = new GetTaskReqVo();
		reqTask.setInstanceId(reqVo.getAuditNumber());
		reqTask.setOrigin(origin);
		GetTaskResVo taskList = fallowQueryService.getTaskList(reqTask);
		LogCvt.info("更新门店审核详情请求任务表单返回:" + JSON.toJSONString(taskList));
		// 封装任务表单列表详情
		if (taskList != null) {
			packageTaskListValue(taskList, detailResVo, reqVo.getClientId());
		}
		resMap.put("outlet", detailResVo);
		return resMap;
	}
	
	/**
	 * @throws TException 
	 * 
	* @Title: packageUpdateOutletValue 
	* @Description: 更新门店审核详情
	* @param outletTempVo
	* @param detailResVo    
	* @return void     
	* @throws
	 */
	private void packageUpdateOutletValue(OutletTempVo outletVo, BankOutletDetailResVo detailResVo) throws TException {
		detailResVo.setOutletName(outletVo.getOutletName());// 门店名称
		detailResVo.setOutletFullName(outletVo.getOutletFullName());// 门店全称
		detailResVo.setTellphone(outletVo.getPhone());// 联系电话
		detailResVo.setSellTime(outletVo.getBusinessHours());// 营业时间
		String address = null;
		if (StringUtil.isNotBlank(outletVo.getAreaId())) {
			address = this.getAddress(Long.parseLong(outletVo.getAreaId()));
		}
		detailResVo.setAddress(address + outletVo.getAddress());// 地址
		detailResVo.setContactName(outletVo.getContactName());// 联系人名字
		detailResVo.setContactPhone(outletVo.getContactPhone());// 联系人电话
		detailResVo.setDiscount(outletVo.getDiscount());// 折扣
		detailResVo.setDiscountCode(outletVo.getDiscountCode());// 折扣码
		detailResVo.setDiscountRate(outletVo.getDiscountRate());// 折扣比
		detailResVo.setPreferDetails(outletVo.getPreferDetails());// 优惠详情
		String photoList = outletVo.getPhotoList();
		if(StringUtil.isNotBlank(photoList)){
			List<MerchantOutletPhotoVo> photos = JSON.parseArray(photoList, MerchantOutletPhotoVo.class);
			detailResVo.setDefaultImage(returnPhotoUrl(photos));
		}
		// MerchantOutletPhotoVo merchantOutletPhotoVo = new
		// MerchantOutletPhotoVo();// 照片
		// MerchantAccountVo accountVo = getAccount(outletVo.getClientId(),
		// outletVo.getMerchantId(),
		// outletVo.getOutletId());
		// if (accountVo != null) {
		// detailResVo.setAcctName(accountVo.getAcctName());// 收款账户名
		// detailResVo.setAcctNumber(accountVo.getAcctNumber());// 收款账户号
		// }
		detailResVo.setAcctName(outletVo.getAcctName());// 收款账户名
		detailResVo.setAcctNumber(outletVo.getAcctNumber());// 收款账户号
		// 门店所属分类获取
		String categoryName = outletVo.getOutletCategoryName();// 去掉末尾的,
		if (StringUtil.isNotBlank(categoryName)&&categoryName.endsWith(",")) {
			categoryName = categoryName.substring(0, categoryName.length() - 1);
		}
		detailResVo.setCategories(categoryName);
		// 原值拷贝封装

		// OutletVo reqOutletVo = new OutletVo();
		// List<OutletVo> outlet = outletService.getOutlet(reqOutletVo);
		// if (outlet != null && outlet.get(0) != null) {
		// OutletVo oldOutlet = outlet.get(0);
		// detailResVo.setAddress(oldOutlet.getAddress());
		// detailResVo.setOldContactName(oldOutlet.getContactName());// 联系人姓名
		// detailResVo.setOldContactPhone(oldOutlet.getContactPhone());// 联系人电话
		// detailResVo.setOldDiscountCode(oldOutlet.getDiscountCode());// 折扣码
		// detailResVo.setOldDiscountRate(oldOutlet.getDiscountRate());// 折扣率
		// detailResVo.setOldOutletFullName(oldOutlet.getOutletFullname());//
		// 门店全称
		// detailResVo.setOldOutletName(oldOutlet.getOutletName());// 门店简称
		// detailResVo.setOldTellphone(oldOutlet.getPhone());// 联系电话
		// String categories = getCategories(oldOutlet, detailResVo);
		// detailResVo.setOldCategories(categories);// 所属分类
		//
		// }
		String jsonData = outletVo.getPrimeval();
		if (StringUtil.isNotBlank(jsonData)) {
			LogCvt.info("门店更新审核返回原值json数据" + jsonData);
			OldBankOutletResVo oldBankOutletResVo = JSON.parseObject(jsonData, OldBankOutletResVo.class);
			LogCvt.info("门店更新审核返回原值json数据转成对象" + JSON.toJSONString(oldBankOutletResVo));
			if (oldBankOutletResVo != null) {
				detailResVo.setOldAcctName(oldBankOutletResVo.getAcctName());// 收款账户名
				detailResVo.setOldAcctNumber(oldBankOutletResVo.getAcctNumber());// 收款账户号
				String oldAddress = getAddress(oldBankOutletResVo.getAreaId());
				detailResVo.setOldAddress(oldAddress + oldBankOutletResVo.getAddress());// 地址
				detailResVo.setOldContactName(oldBankOutletResVo.getContactName());// 联系人姓名
				detailResVo.setOldContactPhone(oldBankOutletResVo.getContactPhone());// 联系人电话
				detailResVo.setOldDiscountCode(oldBankOutletResVo.getDiscountCode());// 折扣码
				detailResVo.setOldDiscountRate(oldBankOutletResVo.getDiscountRate());// 折扣率
				detailResVo.setOldOutletFullName(oldBankOutletResVo.getOutletFullName());// 门店全称
				detailResVo.setOldOutletName(oldBankOutletResVo.getOutletName());// 门店简称
				detailResVo.setOldTellphone(oldBankOutletResVo.getPhone());// 联系电话
				detailResVo.setOldCategories(oldBankOutletResVo.getOutletCategoryName());// 所属分类
			}
		}
	}

	/**
	 * @throws TException 
	 * 
	* @Title: outletAudit 
	* @Description: 银行管理平台门店审核
	* @param reqVo
	* @return    
	* @return Map<String,Object>     
	* @throws
	 */
	public Map<String, Object> outletAudit(BankOutletAuditReqVo reqVo) throws TException {
		LogCvt.info("银行管理平台商户门店审核请求参数:" + JSON.toJSONString(reqVo));
		Map<String, Object> resMap = new HashMap<String, Object>();
		ExecuteTaskReqVo req = new ExecuteTaskReqVo();
		// 审核请求参数封装
		this.packageOutletAuditReqValue(reqVo, req);
		OriginVo origin = this.getOrigin(reqVo);
		req.setOrigin(origin);
		// 银行端orgCode和orgId两个值一样
		req.setOrgCode(origin.getOrgId());
		ExecuteTaskResVo taskResVo = fallowExecuteService.executeTask(req);
		LogCvt.info("银行管理平台商户门店审核请求返回数据:" + JSON.toJSONString(taskResVo));
		if (taskResVo != null && taskResVo.getResult() != null) {
			resMap.put("code", taskResVo.getResult().getResultCode());
			resMap.put("message", taskResVo.getResult().getResultDesc());
			if (EnumTypes.success.equals(taskResVo.getResult().getResultCode())) {
				resMap.put("taskId", taskResVo.getTaskId());
			}
		} else {
			resMap.put(ResultEnum.FAIL.getCode(),
					ResultEnum.FAIL.getDescrition());
			resMap.put(ResultEnum.MESSAGE.getCode(), "商户门店审核异常");
		}
		return resMap;
	}
	
	/**
	 * @throws TException 
	 * @throws NumberFormatException 
	 * 
	* @Title: packageOutletAuditReqValue 
	* @Description: 商户门店审核请求参数封装
	* @param reqVo
	* @param req    
	* @return void     
	* @throws
	 */
	private void packageOutletAuditReqValue(BankOutletAuditReqVo reqVo, ExecuteTaskReqVo req) throws NumberFormatException, TException {
		OriginVo origin = getOrigin(reqVo);
		req.setOrgCode(req.getOrgCode());
		req.setOrigin(origin);
		req.setBessId(reqVo.getOutletId());
		req.setInstanceId(reqVo.getInstanceId());
		req.setRemark(reqVo.getRemark());
		req.setAuditState(reqVo.getAuditState());
		req.setTaskId(reqVo.getTaskId());

	}

	/**
	 * 
	 * getOutletList:银行管理平台门店列表请求接口
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月4日 上午11:34:26
	 * @param reqVo
	 * @return
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public Map<String, Object> getOutletList(BankOutletListReqVo reqVo)
			throws TException, ParseException {
		LogCvt.info("银行管理平台门店列表请求参数:" + JSON.toJSONString(reqVo));
		Map<String, Object> resMap = new HashMap<String, Object>();
		PageVo pageVo = new PageVo();
		pageVo = PageUtil.getPageVo(reqVo);
		OutletPreferVo outletPreferVo = new OutletPreferVo();
		outletPreferVo = this.requestParams(outletPreferVo, reqVo);
		// server端接口
		OutletPreferPageVoRes perferPageVoRes = outletService
				.getOutletPreferByPage(pageVo, outletPreferVo);
		LogCvt.info("银行管理平台门店列表请求返回:" + JSON.toJSONString(perferPageVoRes));
		List<BankOutletListResVo> resList = null;
		BankOutletListResVo bankOutletVo = null;
		if (perferPageVoRes != null) {
			List<OutletPreferVo> outletPerferVoList = perferPageVoRes
					.getOutletPreferVoList();
			resList = new ArrayList<BankOutletListResVo>();
			if (outletPerferVoList != null && outletPerferVoList.size() > 0) {
				for (OutletPreferVo outletVo : outletPerferVoList) {
					bankOutletVo = new BankOutletListResVo();
					// 拷贝属性
					this.copyProperties(bankOutletVo, outletVo);
					resList.add(bankOutletVo);
				}
			}
			PageVo page = perferPageVoRes.getPage();
			PageUtil.setPagesValueToResMap(resMap, page);
		}
		resMap.put("outletList", resList);
		return resMap;
	}

	/**
	 * 
	 * requestParams:门店列表请求参数封装
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月5日 下午1:45:34
	 * @param outletPerferVo
	 *            目标
	 * @param reqVo
	 *            源数据
	 * @return
	 * @throws ParseException
	 * @throws TException
	 *
	 */
	private OutletPreferVo requestParams(OutletPreferVo outletPreferVo,
			BankOutletListReqVo reqVo) throws ParseException, TException {
		if (StringUtil.isNotBlank(reqVo.getMerchantName())) {
			outletPreferVo.setMerchantName(reqVo.getMerchantName());
		}
		if (StringUtil.isNotBlank(reqVo.getOutletName())) {
			outletPreferVo.setOutletName(reqVo.getOutletName());
		}
		if (StringUtil.isNotBlank(reqVo.getLicense())) {
			outletPreferVo.setLicense(reqVo.getLicense());
		}
		if (StringUtil.isNotBlank(reqVo.getAuditState())) {
			outletPreferVo.setAuditState(reqVo.getAuditState());
		}
		if (StringUtil.isNotBlank(reqVo.getStartDate())) {
			outletPreferVo.setStartCreateTime(
					DateUtil.DateFormat(reqVo.getStartDate()));
		}
		if (StringUtil.isNotBlank(reqVo.getEndDate())) {
			outletPreferVo
					.setEndCreateTime(DateUtil.DateFormat(reqVo.getEndDate()));
		}
		if (StringUtil.isNotBlank(reqVo.getAuditStartTime())) {
			outletPreferVo.setStartAuditTime(
					DateUtil.DateFormat(reqVo.getAuditStartTime()));
		}
		if (StringUtil.isNotBlank(reqVo.getAuditEndTime())) {
			outletPreferVo.setEndAuditTime(
					DateUtil.DateFormat(reqVo.getAuditEndTime()));
		}
		if (StringUtils.isNotEmpty(reqVo.getOrgCode())) {// 所属机构
			// 多机构查询时，只设置orgCOde值，后台服务进行区分
			if (reqVo.getOrgCode().indexOf(",") != -1) {
				outletPreferVo.setOrgCode(reqVo.getOrgCode());
			} else {
				// 根据级别注入机构号
				this.setOrgCode(outletPreferVo, reqVo);
			}
		}
		//过滤是否删除门店
		//0=待审核 ,1=审核通过 , 2=审核不通过 , 3=未提交 , 4=删除，5=全部
		List<String> disableStatusList = new ArrayList<String>();
		disableStatusList.add(OutletDisableStatusEnum.normal.getCode());
		disableStatusList.add(OutletDisableStatusEnum.disable.getCode());
		if(reqVo.getAuditState().equals(DELETE)||reqVo.getAuditState().equals(ALL)){
			disableStatusList.add(OutletDisableStatusEnum.delete.getCode());
		}
		outletPreferVo.setDisableStatusList(disableStatusList);
		return outletPreferVo;
	}

	/**
	 * 
	 * setOrgCode:根据级别注入机构号
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月5日 下午2:06:43
	 * @param outletPerferVo
	 * @param reqVo
	 * @throws TException
	 *
	 */
	private void setOrgCode(OutletPreferVo outletPreferVo,
			BankOutletListReqVo reqVo) throws TException {
		OrgVo orgVo = orgService.getOrgById(reqVo.getClientId(),
				reqVo.getOrgCode());
		LogCvt.info("门店列表-机构server端返回:" + JSON.toJSONString(orgVo));
		if (orgVo != null && orgVo.getOrgLevel() != null) {
			int level = Integer.parseInt(orgVo.getOrgLevel());
			switch (level) {
			case 1:
				outletPreferVo.setProOrgCode(reqVo.getOrgCode());
				break;
			case 2:
				outletPreferVo.setCityOrgCode(reqVo.getOrgCode());
				break;
			case 3:
				outletPreferVo.setCountyOrgCode(reqVo.getOrgCode());
				break;
			default:
				outletPreferVo.setOrgCode(reqVo.getOrgCode());
				break;
			}
		}
	}
	/**
	 * 
	 * copyProperties:复制属性
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月5日 上午11:27:54
	 * @param bankOutletVo
	 *            目标
	 * @param outletVo
	 *            源数据
	 * @return
	 *
	 */
	private BankOutletListResVo copyProperties(BankOutletListResVo bankOutletVo,
			OutletPreferVo outletVo) {
		bankOutletVo.setAddress(outletVo.getAddress());// 门店地址
		bankOutletVo.setAreaName(outletVo.getAreaName());// 所属地区
		bankOutletVo.setAuditState(outletVo.getAuditState());// 门店状态
		bankOutletVo.setAuditTime(outletVo.getAuditTime());// 审核时间
		bankOutletVo.setCreateTime(outletVo.getCreateTime());// 创建时间
		bankOutletVo.setLicense(outletVo.getLicense());// 营业执照
		bankOutletVo.setMerchantName(outletVo.getMerchantName());// 商户名称
		bankOutletVo.setOrgName(outletVo.getOrgName());// 所属机构
		bankOutletVo.setOutletId(outletVo.getOutletId());// 门店id
		bankOutletVo.setOutletName(outletVo.getOutletName());// 门店简称
		bankOutletVo.setEditAuditState(outletVo.getEditAuditState());// 子状态
		bankOutletVo.setOpen(outletVo.isPreferStatus());// 是否惠付
		bankOutletVo.setDiscountFlag(this.getDiscountFlag(outletVo));
		bankOutletVo.setQrcodeUrl(outletVo.getQrcodeUrl());// 二维码地址
		bankOutletVo.setDisableStatus(outletVo.getDisableStatus());//是否有效
		return bankOutletVo;
	}

	/**
	 * 
	 * getDiscountFlag:获取原优惠折扣的情况
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月7日 上午11:35:46
	 * @param outletVo
	 * @return
	 *
	 */
	private boolean getDiscountFlag(OutletPreferVo outletVo) {
		boolean flag = false;
		if (StringUtil.isNotBlank(outletVo.getDiscountRate())) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 
	 * getOutletDetail:银行管理平台门店详情请求
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月4日 下午2:23:55
	 * @param outletId
	 * @param clientId
	 * @return
	 * @throws TException
	 *
	 */
	public Map<String, Object> getOutletDetail(String outletId, String clientId) throws TException {
		LogCvt.info("银行管理平台门店详情请求参数:" + JSON.toJSONString(outletId));
		Map<String, Object> resMap = new HashMap<String, Object>();
		BankOutletDetailRes bankOutletDetailRes = new BankOutletDetailRes();
		OutletVo outletVo = outletService.getOutletByOutletId(outletId);
		LogCvt.info("银行管理平台门店详情请求返回:" + JSON.toJSONString(outletVo));
		if (outletVo != null) {
			BeanUtils.copyProperties(bankOutletDetailRes, outletVo);
			bankOutletDetailRes.setIsEnable(outletVo.isIsEnable());
			// bankOutletDetailRes.setPreferStatus(outletVo.isPreferStatus());
			/** 查询区域id、name */
			AreaVo listvo = areaService.findAreaById(outletVo.getAreaId());
			LogCvt.info("门店区域server端返回:" + JSON.toJSONString(outletVo));
			Map<String, String> area = OrgCodeUtil.getArea(listvo);
			bankOutletDetailRes.setAreaName(area.get("areaName"));
			bankOutletDetailRes.setCityId(area.get("cityId"));
			bankOutletDetailRes.setCityName(area.get("cityName"));
			bankOutletDetailRes.setCountyId(area.get("countyId"));
			bankOutletDetailRes.setCountyName(area.get("countyName"));
			/** 2015-11-05 门店二级类目 */
			List<CategoryInfoVo> infoList = outletVo.getCategoryInfo();
			BankOutletCategoryInfoRes catRes =null;
			List<BankOutletCategoryInfoRes> listRes = new ArrayList<BankOutletCategoryInfoRes>();
			if (null != infoList) {
				for (CategoryInfoVo cvo : infoList) {
					catRes = new BankOutletCategoryInfoRes();
					catRes.setCategoryId(cvo.getCategoryId());
					catRes.setName(cvo.getName());
					listRes.add(catRes);
				}
			}
			bankOutletDetailRes.setCategoryList(listRes);
			/** 查询相册 */
			MerchantOutletPhotoVo mevo = new MerchantOutletPhotoVo();
			mevo.setMerchantId(outletVo.getMerchantId());
			mevo.setOutletId(outletVo.getOutletId());
			List<MerchantOutletPhotoVo> listphoto = merchantOutletPhotoService.getMerchantOutletPhoto(mevo);
			LogCvt.info("门店相册数据server端返回; ： " + JSON.toJSONString(listphoto));
			List<OutletImageInfoRes> list = new ArrayList<OutletImageInfoRes>();
			if (listphoto != null && listphoto.size() > 0) {
				for (MerchantOutletPhotoVo photo : listphoto) {
					OutletImageInfoRes po = new OutletImageInfoRes();
					po.setLarge(photo.getLarge());
					po.setIsDefault(photo.isIsDefault());
					po.setId(String.valueOf(photo.getId()));
					po.setSource(photo.getSource());
					po.setMedium(photo.getMedium());
					po.setThumbnail(photo.getThumbnail());
					list.add(po);
				}
			}
			bankOutletDetailRes.setImgList(list);
			// 门店折扣规范 0819
			bankOutletDetailRes.setDiscountCode(outletVo.getDiscountCode());
			bankOutletDetailRes.setDiscountRate(outletVo.getDiscountRate());

			/** 查询账号 */
			MerchantAccountVo vo = this.setMerchantAccountVo(clientId, outletVo.getMerchantId(), outletId, null, null);
			List<MerchantAccountVo> listAcct = merchantAccountService.getMerchantAccount(vo);
			LogCvt.info("门店账户数据server端返回 ： " + JSON.toJSONString(listAcct));
			if (listAcct != null && listAcct.size() > 0) {
				bankOutletDetailRes.setAcctName(listAcct.get(0).getAcctName());
				bankOutletDetailRes.setAcctNumber(listAcct.get(0).getAcctNumber());
				bankOutletDetailRes.setOpeningBank(listAcct.get(0).getOpeningBank());
			}

			/** 查询开户行 级联 */
			if (StringUtils.isNotBlank(bankOutletDetailRes.getOpeningBank())) {
				List<OrgVo> orgList = orgService.getSuperOrgList(clientId, bankOutletDetailRes.getOpeningBank());
				LogCvt.info("门店开户行数据server端返回 ： " + JSON.toJSONString(orgList));
				if (orgList != null && orgList.size() > 0) {
					for (OrgVo org : orgList) {
						if ("1".equals(org.getOrgLevel())) {
							bankOutletDetailRes.setOrgCodeLev1(org.getOrgCode());
							bankOutletDetailRes.setOrgNameLev1(org.getOrgName());
						}
						if ("2".equals(org.getOrgLevel())) {
							bankOutletDetailRes.setOrgCodeLev2(org.getOrgCode());
							bankOutletDetailRes.setOrgNameLev2(org.getOrgName());
						}
						if (org.getOrgCode().equals(bankOutletDetailRes.getOpeningBank())) {
							bankOutletDetailRes.setOrgCodeLev3(org.getOrgCode());
							bankOutletDetailRes.setOrgNameLev3(org.getOrgName());
						}
					}
				}
			}
		}
		resMap.put("outlet", bankOutletDetailRes);
		return resMap;
	}

	/**
	 * 
	 * setMerchantAccountVo:账户查询请求Vo封装
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月4日 下午3:18:10
	 * @param clientId
	 * @param merchantId
	 * @param outleId
	 * @param acctNumber
	 * @param acctName
	 * @return
	 * @throws TException
	 *
	 */
	public MerchantAccountVo setMerchantAccountVo(String clientId, String merchantId, String outleId, String acctNumber,
			String acctName) throws TException {
		MerchantVo vo = merchantService.getMerchantByMerchantId(merchantId);
		LogCvt.info("门店开户行数据server端返回 ： " + JSON.toJSONString(vo));
		MerchantAccountVo merchantAccountVo = new MerchantAccountVo();
		merchantAccountVo.setClientId(clientId);
		merchantAccountVo.setMerchantId(merchantId);
		merchantAccountVo.setOutletId(outleId);
		merchantAccountVo.setOpeningBank(vo.getOrgCode());
		if (StringUtils.isNotEmpty(acctName))
			merchantAccountVo.setAcctName(acctName);
		if (StringUtils.isNotEmpty(acctNumber))
			merchantAccountVo.setAcctNumber(acctNumber);
		merchantAccountVo.setAcctType("1");
		merchantAccountVo.setIsDelete(false);
		return merchantAccountVo;
	}

	/**
	 * 
	 * changeOutletPreference:开启或者关闭惠付功能
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月4日 下午4:06:46
	 * @param outletVo
	 * @param originVo
	 * @return
	 * @throws TException
	 *
	 */
	public Map<String, Object> changeOutletPreference(OutletVo outletVo, OriginVo originVo) throws TException {
		LogCvt.info("门店开关惠付请求参数:" + JSON.toJSONString(outletVo));
		Map<String, Object> resMap = new HashMap<String, Object>();
		ResultVo resultVo = outletService.updateOutlet(originVo, outletVo);
		LogCvt.info("门店开关惠付请求返回: " + JSON.toJSONString(resultVo));
		if (resultVo!=null&&resultVo.getResultCode().equals(ResultEnum.SUCCESS.getCode())) {
			resMap.put(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getDescrition());
			resMap.put(ResultEnum.MESSAGE.getCode(), resultVo.getResultDesc());
		}else{
			resMap.put(ResultEnum.CODE.getCode(), resultVo.getResultCode());
			resMap.put(ResultEnum.MESSAGE.getCode(), resultVo.getResultDesc());
		}
		return resMap;
	}

	/**
	 * 
	 * getOutletListExport:门店列表下载接口
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月5日 下午2:19:55
	 * @param reqVo
	 * @return
	 * @throws ParseException
	 * @throws TException
	 *
	 */
	public Map<String, Object> getOutletListExport(BankOutletListReqVo reqVo)
			throws ParseException, TException {
		LogCvt.info("门店列表请求参数:" + JSON.toJSONString(reqVo));
		Map<String, Object> resMap = new HashMap<String, Object>();
		OutletPreferVo outletPreferVo = new OutletPreferVo();
		this.requestParams(outletPreferVo, reqVo);
		ExportResultRes resultRes = outletService
				.getOutletExport(outletPreferVo);
		LogCvt.info("门店列表请求返回:" + JSON.toJSONString(resultRes));
		if (resultRes != null) {
			ResultVo resultVo = resultRes.getResultVo();
			if (resultVo != null && resultVo.getResultCode()
					.equals(ResultEnum.SUCCESS.getDescrition())) {
				resMap.put("url", resultRes.getUrl());
				resMap.put(ResultEnum.SUCCESS.getCode(),
						ResultEnum.SUCCESS.getDescrition());
				resMap.put(ResultEnum.MESSAGE.getCode(), "操作成功");
			} else {
				resMap.put(ResultEnum.CODE.getCode(), resultVo.getResultCode());
				resMap.put(ResultEnum.MESSAGE.getCode(),
						resultVo.getResultDesc());
			}
		}
		return resMap;
	}
}
