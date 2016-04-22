package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProcessTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProductTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.service.GroupProductService;
import com.froad.cbank.coremodule.module.normal.bank.util.BankHandle;
import com.froad.cbank.coremodule.module.normal.bank.util.BessDataUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.PageUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.util.TargetObjectFormat;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankAuditProductListReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankAuditProductReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BaseVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.GroupProductVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductInfoVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductVoReq;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.GetPendAuitListReqVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.Restrictions;

/**
 * 团购商品管理
 * @author ylchu
 *
 */
@Controller
@RequestMapping(value = "/groupProduct")
public class GroupProductController extends BasicSpringController{

	public final static String ADD = "0";
	public final static String UPDATE = "1";
	@Resource
	private GroupProductService groupProductService;
	
	/**
	 * 
	 * queryGroupProductList:待审核团购列表
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月26日 下午4:33:57
	 * @param model
	 * @param req
	 * @param reqVo
	 */
	@CheckPermission(keys = { "fomous_group_menu", "fomous_group_select_bind" })
	@RequestMapping(value = "auditLt", method = RequestMethod.POST)
	public void queryGroupProductList(ModelMap model, HttpServletRequest req,
			@RequestBody BankAuditProductListReqVo reqVo) {
		LogCvt.debug("待审核团购请求参数:" + JSON.toJSONString(model));
		try {
			model.clear();
			BankOperatorVo bankOperatorVo = (BankOperatorVo) req
					.getAttribute(Constants.BANKOPERATOR);
			reqVo.setClientId(req.getAttribute(Constants.CLIENT_ID).toString());
			reqVo.setUserId(req.getAttribute(Constants.USER_ID).toString());
			// 请求参数封装
			GetPendAuitListReqVo getPendAuitListReqVo = this.requestParams(reqVo);
			// 操作源对象
			OriginVo originVo = BankHandle.getOriginVo(bankOperatorVo);
			getPendAuitListReqVo.setOrigin(originVo);
			// 分页信息
			PageVo pageVo = PageUtil.getPageVo(reqVo);
			model.putAll(groupProductService.getAuditProductList(getPendAuitListReqVo, pageVo));
		} catch (TException e) {
			LogCvt.info("银行管理平台待审核团购列表请求:" + e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), EnumTypes.thrift_err.getMessage());
		} catch (ParseException e) {
			LogCvt.info("银行管理平台待审核团购列表请求:" + e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(), "时间格式不正确");
		}
	}
	
	/**
	 * 
	 * requestParams: 团购商品待审核列表请求参数封装
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月25日 下午5:15:43
	 * @param reqVo
	 * @return
	 * @throws ParseException
	 */
	private GetPendAuitListReqVo requestParams(BankAuditProductListReqVo reqVo) throws ParseException {
		GetPendAuitListReqVo auditReqVo = new GetPendAuitListReqVo();
		Map<Restrictions, String> orBessData = new HashMap<Restrictions, String>();
		Map<Restrictions, String> andBessData = new HashMap<Restrictions, String>();
		if (StringUtil.isNotBlank(reqVo.getStartDate())) {
			auditReqVo.setStarTime(DateUtil.DateFormat(reqVo.getStartDate()));
		}
		if (StringUtil.isNotBlank(reqVo.getEndDate())) {
			auditReqVo.setEndTime(DateUtil.DateFormat(reqVo.getEndDate()));
		}
		// XXX 分类查询改成like,查询子分类
		orBessData = BessDataUtil.getOrBessDataMapWithMerchantNameAndOutName(
				reqVo.getMerchantName(), null, reqVo.getProductName(),
				reqVo.getProductCategory(), orBessData);
		if (orBessData.size() > 0) {
			auditReqVo.setOrBessData(orBessData);
		}
		if (StringUtil.isNotBlank(reqVo.getOrgCode())) {
			auditReqVo.setUserOrgCode(reqVo.getOrgCode());
		}
		if (andBessData.size() > 0) {
			auditReqVo.setOrBessData(andBessData);
		}
		// 请求待审核的类型
		auditReqVo.setProcessType(ProcessTypeEnum.GROUPPRODUCT.getCode());
		return auditReqVo;

	}

	/**
	 * @Title: 团购商品列表查询
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"fomous_group_menu","fomous_group_select_bind","group_product_list_menu","group_product_select_bind"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
	public void productList(ModelMap model,HttpServletRequest req,@RequestBody ProductVoReq voReq){
		LogCvt.info("团购商品待审核列表请求参数:" + JSON.toJSONString(model));
		try {
			long begin = System.currentTimeMillis();
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			// 条件过滤
			Map<String, String> filter = new HashMap<String, String>();
			filter.put("orgCode", voReq.getOrgCode());
			// 审核状态
			filter.put("auditOrgCode", voReq.getAuditOrgCode());
			// 上下架状态
			filter.put("isMarketable", voReq.getIsMarketable());
			filter.put("name", voReq.getProductName());
			filter.put("merchantName", voReq.getMerchantName());
			filter.put("categoryId", voReq.getCategoryId());

			if (StringUtil.isNotEmpty(voReq.getStartDate())) {
				filter.put("startTimeStart",
						DateUtil.DateFormat(voReq.getStartDate()) + "");
			}
			if (StringUtil.isNotEmpty(voReq.getEndDate())) {
				filter.put("startTimeEnd",
						DateUtil.DateFormatEnd(voReq.getEndDate()) + "");// 结束时间
			}
			if (StringUtil.isNotEmpty(voReq.getAuditStartTime())) {
				filter.put("auditStartTime",
						DateUtil.DateFormat(voReq.getAuditStartTime()) + "");// 审核开始时间
			}
			if (StringUtil.isNotEmpty(voReq.getAuditEndTime())) {
				filter.put("auditEndTime",
						DateUtil.DateFormatEnd(voReq.getAuditEndTime()) + "");// 审核结束时间
			}
			filter.put("auditState", voReq.getAuditState());
			filter.put("type", ProductTypeEnum.GROUP.getCode());
			filter.put("filterAuditState", AuditFlagEnum.NO_NEW.getCode());// 过滤未提交审核状态

			QueryResult<GroupProductVoRes> queryVo = groupProductService
					.findPageByConditions(voReq.getClientId(),
							JSON.toJSONString(filter), voReq.getPageNumber(),
							voReq.getPageSize(), voReq.getLastPageNumber(),
							voReq.getFirstRecordTime(),
							voReq.getLastRecordTime());

			model.put("productList", queryVo.getResult());// 商品列表
			model.put("totalCount", queryVo.getTotalCount());// 总记录数
			model.put("pageCount", queryVo.getPageCount());// 总页数
			// LogCvt.info("2===========返回的pageNumber:" +
			// queryVo.getPageNumber());
			model.put("pageNumber", queryVo.getPageNumber());// 总页
			model.put("lastPageNumber", queryVo.getLastPageNumber());
			model.put("firstRecordTime", queryVo.getFirstRecordTime());
			model.put("lastRecordTime", queryVo.getLastRecordTime());

			Monitor.send(MonitorEnums.bank_groupProduct_lt,
					String.valueOf(System.currentTimeMillis() - begin));
			Monitor.send(MonitorEnums.bank_groupProduct_lt,
					String.valueOf(System.currentTimeMillis() - begin));
			model.put(ResultEnum.MESSAGE.getCode(), "团购商品信息列表条件查询成功");
		} catch (ParseException e) {
			LogCvt.info("团购商品信息列表条件查询"+e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(), "时间格式不正确");
		} catch (TException e) {
			LogCvt.info("团购商品信息列表条件查询"+e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 团购商品详情
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"seckill_product_detail_bind","group_product_detail_bind","fomous_group_ground_detail","to_audit_seckilldetail_tg","grounp_list_detail","seckilldetail_tg","fomous_group_detail_bind","to_audit_seckill_list_detail_bind"})
	@RequestMapping(value = "/dl",method = RequestMethod.GET)
	public void productDetail(ModelMap model,HttpServletRequest req,String productId){
		
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(StringUtil.isNotEmpty(productId)){
				ProductInfoVoReq product = groupProductService.productDetail(clientId,productId);
				if(product != null){
					model.put("product", product);
				}
			}else{
				model.put(ResultEnum.CODE.getCode(), EnumTypes.empty.getCode());
				model.put(ResultEnum.MESSAGE.getCode(), "商品ID不能为空");
			}				
		} catch (Exception e) {
			LogCvt.info("团购商品信息详情查询"+e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 查询团购商品分类
	 * @author wm
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "qc", method = RequestMethod.POST)
	public void query_product_categorye(ModelMap model,HttpServletRequest request,@RequestBody BaseVo req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			model.putAll(groupProductService.query_product_categorye(req));
		} catch (Exception e) {
			LogCvt.info("商品分类"+e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 
	 * addAuditProductDetail:团购商品新增审核详情
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月26日 下午4:33:01
	 * @param model
	 * @param request
	 * @param auditNumber
	 * @param productId
	 */
	@CheckPermission(keys = { "fomous_group_detail_bind" })
	@RequestMapping(value = "addl", method = RequestMethod.GET)
	public void addAuditProductDetail(ModelMap model,
			HttpServletRequest request, String auditNumber, String productId) {
		LogCvt.info("团购商品新增审核详情请求参数:" + JSON.toJSONString(model));
		// 请求参数非空校验
		OriginVo originVo = BankHandle.getOriginVo(request);
		model = this.validateWhenAddl(model, auditNumber, productId);
		if (model.size() > 0) {
			model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			return;
		}
		try {
			model.putAll(groupProductService.getProductDetail(originVo,
					auditNumber, productId, ADD));
		} catch (TException e) {
			LogCvt.info("团购商品新增审核详情异常" + e.getMessage(), e);
			model.put(ResultEnum.CODE.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * 
	 * validateWhenAddl:新增详情参数校验
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月28日 下午4:23:36
	 * @param model
	 * @param auditNumber
	 * @param productId
	 * @return
	 */
	private ModelMap validateWhenAddl(ModelMap model, String auditNumber,
			String productId) {

		model.clear();
		if (!StringUtil.isNotBlank(auditNumber)) {
			model.put(ResultEnum.MESSAGE.getCode(), "审核流水号不能为空");
			return model;
		}
		if (!StringUtil.isNotBlank(productId)) {
			model.put(ResultEnum.MESSAGE.getCode(), "商品id不能为空");
			return model;
		}
		return model;
	}

	/**
	 * 
	 * upAuditProductDetail:团购商品更新审核详情
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月26日 下午4:30:46
	 * @param model
	 * @param request
	 * @param auditNumber
	 * @param productId
	 */
	@CheckPermission(keys = { "fomous_group_detail_bind" })
	@RequestMapping(value = "updl", method = RequestMethod.GET)
	public void upAuditProductDetail(ModelMap model, HttpServletRequest request,
			String auditNumber, String productId) {
		LogCvt.info("团购商品更新审核详情请求参数:" + JSON.toJSONString(model));
		// 请求参数非空校验
		OriginVo originVo = BankHandle.getOriginVo(request);
		model = this.validateWhenAddl(model, auditNumber, productId);
		if (model.size() > 0) {
			model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			return;
		}
		try {
			model.putAll(groupProductService.getProductDetail(originVo,
					auditNumber, productId, UPDATE));
		} catch (TException e) {
			LogCvt.info("团购商品更新审核详情异常" + e.getMessage(), e);
			model.put(ResultEnum.CODE.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}

	}
	
	/**
	 * 
	 * auditProduct:团购商品审核
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月26日 下午4:29:31
	 * @param model
	 * @param request
	 * @param reqVo
	 */
	@CheckPermission(keys = { "fomous_group_ground_detail_examine" })
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	public void auditProduct(ModelMap model, HttpServletRequest request,
			@RequestBody BankAuditProductReqVo reqVo) {
		LogCvt.info("团购商品审核请求参数:" + JSON.toJSONString(model));
		// 请求参数非空校验
		model = this.validateWhenAudit(reqVo, model);
		if (model.size() > 0) {
			model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			return;
		}
		BankOperatorVo bankoperator = (BankOperatorVo) request
				.getAttribute(Constants.BANKOPERATOR);
		LogCvt.info("操作员信息返回" + JSON.toJSONString(bankoperator));
		OriginVo originVo = BankHandle.getOriginVo(bankoperator);
		originVo.setOperatorIp(TargetObjectFormat.getIpAddr(request));
		// originVo.setOperatorUserName(bankoperator.getUsername());
		// 请求service并返回数据
		try {
			model.putAll(groupProductService.auditProdcut(reqVo, originVo));
		} catch (TException e) {
			LogCvt.info("团购商品审核异常" + e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * 
	 * validateWhenAudit:审核请求参数校验
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月28日 下午3:33:52
	 * @param reqVo
	 * @param model
	 * @return
	 */
	private ModelMap validateWhenAudit(BankAuditProductReqVo reqVo,
			ModelMap model) {
		model.clear();
		if (!StringUtil.isNotBlank(reqVo.getAuditState())) {
			model.put(ResultEnum.MESSAGE.getCode(), "审核状态不能为空");
			return model;
		}
		if (!StringUtil.isNotBlank(reqVo.getInstanceId())) {
			model.put(ResultEnum.MESSAGE.getCode(), "审核流水号不能为空");
			return model;
		}
		if (!StringUtil.isNotBlank(reqVo.getProductId())) {
			model.put(ResultEnum.MESSAGE.getCode(), "商品id不能为空");
			return model;
		}
		if (!StringUtil.isNotBlank(reqVo.getTaskId())) {
			model.put(ResultEnum.MESSAGE.getCode(), "任务id不能为空");
			return model;
		}
		// 如果不通过,则审核备注也不能为空
		if ("2".equals(reqVo.getAuditState())
				&& !StringUtil.isNotBlank(reqVo.getRemark())) {
			if (reqVo.getRemark().length() > 200) {
				model.put(ResultEnum.MESSAGE.getCode(), "审核备注不能超过200个字符");
				return model;
			}
			model.put(ResultEnum.MESSAGE.getCode(), "审核备注不能为空");
			return model;
		}
		return model;
	}

}
