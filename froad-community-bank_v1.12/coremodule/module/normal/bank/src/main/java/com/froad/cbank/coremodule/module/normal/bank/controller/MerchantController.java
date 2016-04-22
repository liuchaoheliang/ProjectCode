package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.service.MerchantManageService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankCarCheckReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantManageUserVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantManageVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.MerchantReq;
import com.froad.thrift.vo.OriginVo;

/**
 * 商户
 * 
 * @author ylchu
 *
 */
@Controller
@RequestMapping(value = "/merchant")
public class MerchantController extends BasicSpringController {

	@Resource
	private MerchantManageService mmService;
	@Resource
	private LoginService loginService;

	/**
	 * @Title: 商户（待审核）列表查询
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys = { "fomous_merch_menu",
			"fomous_merchant_select_bind", "merchant_list_menu",
			"merchant_list_select_bind" })
	@RequestMapping(value = "/lt", method = RequestMethod.POST)
	public void merchantList(ModelMap model, HttpServletRequest req,
			@RequestBody MerchantReq merchant) {
		try {
			long begin = System.currentTimeMillis();
			merchant.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(mmService.findPageByConditions(merchant));
			Monitor.send(MonitorEnums.bank_merchant_lt,
					String.valueOf(System.currentTimeMillis() - begin));
		} catch (Exception e) {
			LogCvt.info("商户列表条件查询" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 待审核商户列表查询
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/approvallt", method = RequestMethod.POST)
	public void ApprovalMerchantList(ModelMap model, HttpServletRequest req,
			@RequestBody MerchantReq merchant) {
		try {
			merchant.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			LogCvt.info("originVo:"
					+ JSON.toJSONString(loginService.getOrigin(req)));
			model.putAll(mmService.findApprovalList(merchant,
					loginService.getOrigin(req)));
		} catch (Exception e) {
			LogCvt.info("商户列表条件查询" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 商户添加
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys = { "addmerchant_ad" })
	@RequestMapping(value = "/ad", method = RequestMethod.POST)
	public void merchantAdd(ModelMap model, HttpServletRequest req,
			@RequestBody MerchantManageVo mm) {
		LogCvt.info("商户添加请求参数:" + JSON.toJSONString(mm));
		try {
			long begin = System.currentTimeMillis();
			if (StringUtil.isBlank(mm.getOperator())) {
				mm.setOperator(
						loginService.getBankOperatorVo(req).getUsername());
			}
			mm.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(mmService.merchantManageAdd(mm, req,
					loginService.getOrigin(req)));
			Monitor.send(MonitorEnums.bank_merchant_ad,
					String.valueOf(System.currentTimeMillis() - begin));
		} catch (ParseException e) {
			LogCvt.info("商户添加-日期格式转换" + e.getMessage(), e);
		} catch (Exception e) {
			LogCvt.info("商户添加" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 商户信息修改
	 * @author ylchu
	 * @param model
	 * @param req
	 */

	@CheckPermission(keys = { "editormerchantstart_ue" })
	@RequestMapping(value = "/ue", method = RequestMethod.PUT)
	public void merchantUpdate(ModelMap model, HttpServletRequest req,
			@RequestBody MerchantManageVo mm) {
		try {
			long begin = System.currentTimeMillis();
			if (StringUtil.isBlank(mm.getOperator())) {
				mm.setOperator(
						loginService.getBankOperatorVo(req).getUsername());
			}
			mm.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(mmService.merchantManageUpdate(mm, req,
					loginService.getOrigin(req)));
			Monitor.send(MonitorEnums.bank_merchant_ue,
					String.valueOf(System.currentTimeMillis() - begin));
		} catch (ParseException e) {
			LogCvt.info("商户信息修改-日期格式转换" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), "日期格式转换错误");
		} catch (Exception e) {
			LogCvt.info("商户信息修改" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 商户信息详情
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys = { "merchantdetail",
			"fomous_merchant_examinemerchant", "merchant_list_detail_bind",
			"merchant_list_edit" })
	@RequestMapping(value = "/dl", method = RequestMethod.GET)
	public void merchantDetail(ModelMap model, HttpServletRequest req,
			String merchantId, String merchantUserId, OriginVo originVo) {
		try {
			model.putAll(mmService.merchantManageDetail(merchantId,
					merchantUserId, loginService.getOriginVo(req)));
		} catch (Exception e) {
			LogCvt.info("商户详情查询" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 商户新增审核详情
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/addl", method = RequestMethod.GET)
	public void merchantAddDetail(ModelMap model, HttpServletRequest req,
			String merchantId, String merchantUserId, String auditId) {
		try {
			if (StringUtil.isNotBlank(merchantId)
					|| StringUtil.isNotBlank(auditId)) {
				String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
				model.putAll(mmService.merchantManageAddDetail(merchantId,
						auditId, loginService.getOrigin(req), clientId,
						merchantUserId));
			} else {
				model.put(ResultEnum.CODE.getCode(), EnumTypes.empty.getCode());
				model.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.empty.getMessage());
			}
		} catch (Exception e) {
			LogCvt.info("商户新增审核详情查询" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 商户更新审核详情
	 * 
	 * @param model
	 * @param req
	 * @param merchantId
	 */
	@RequestMapping(value = "/editdl", method = RequestMethod.GET)
	public void merchantUpdateDetail(ModelMap model, HttpServletRequest req,
			String merchantId, String auditId) {
		try {
			if (StringUtil.isNotBlank(merchantId)
					|| StringUtil.isNotBlank(auditId)) {
				String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
				model.putAll(mmService.merchantManageUpdateDetail(merchantId,
						auditId, loginService.getOrigin(req), clientId));
			} else {
				model.put(ResultEnum.CODE.getCode(), EnumTypes.empty.getCode());
				model.put(ResultEnum.MESSAGE.getCode(),
						EnumTypes.empty.getMessage());
			}
		} catch (Exception e) {
			LogCvt.info("商户更新审核详情查询" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 是否有权限录入商户
	 * @author yfy
	 * @param model
	 * @param myOrgCode
	 * @param type
	 *            1-审核 2-重置密码
	 */
	@RequestMapping(value = "/vam", method = RequestMethod.GET)
	public void verifyAddMerchant(ModelMap model, HttpServletRequest req,
			String myOrgCode, String type) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(
					mmService.verifyAddMerchant(clientId, myOrgCode, type));
		} catch (Exception e) {
			LogCvt.info("是否有权限录入商户" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 校验商户用户唯一性
	 * @author yfy
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/mone", method = RequestMethod.GET)
	public void verifyMerchantUserName(ModelMap model, HttpServletRequest req,
			String loginName) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(
					mmService.verifyMerchantUserName(clientId, loginName, req));
		} catch (Exception e) {
			LogCvt.info("校验商户用户唯一性" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 校验营业执照唯一性
	 * @author yfy
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/lone", method = RequestMethod.GET)
	public void verifyLicense(ModelMap model, HttpServletRequest req,
			String license) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(mmService.verifyLicense(clientId, license));
		} catch (Exception e) {
			LogCvt.info("校验营业执照唯一性" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 商户用户重置密码
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys = { "merchant_list_rp" })
	@RequestMapping(value = "/rp", method = RequestMethod.PUT)
	public void merchantUserResetPassword(ModelMap model,
			HttpServletRequest req, @RequestBody MerchantManageUserVo mmu) {
		try {
			mmu.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(mmService.merchantManageUserUpdate(mmu, req));
		} catch (TException e) {
			LogCvt.info("商户用户密码重置" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 商户用户禁用
	 * @Description 访问方式:http://localhost:6001/bank/merchant/
	 *              merchantUserForbidden
	 * @author ylchu
	 * @param model
	 * @param req
	 */

	@CheckPermission(keys = { "merchant_list_uf_close",
			"merchant_list_uf_open" })
	@RequestMapping(value = "/uf", method = RequestMethod.PUT)
	public void merchantUserForbidden(ModelMap model, HttpServletRequest req,
			@RequestBody MerchantManageVo mm) {
		try {
			if (StringUtil.isBlank(mm.getOperator())) {
				mm.setOperator(
						loginService.getBankOperatorVo(req).getUsername());
			}
			mm.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(mmService.merchantManageIsEnable(mm, req));
		} catch (Exception e) {
			LogCvt.info("商户用户禁/启用" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 商户解约
	 * @Description 访问方式:http://localhost:6001/bank/merchant/merchantTermination
	 * @author ylchu
	 * @param model
	 * @param req
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonGenerationException
	 */

	@CheckPermission(keys = { "merchant_list_tn" })
	@RequestMapping(value = "/tn", method = RequestMethod.PUT)
	public void merchantTermination(ModelMap model, HttpServletRequest req,
			@RequestBody MerchantManageVo mm) throws JsonGenerationException,
					JsonMappingException, IOException {
		try {
			if (StringUtil.isBlank(mm.getOperator())) {
				mm.setOperator(
						loginService.getBankOperatorVo(req).getUsername());
			}
			mm.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			model.putAll(mmService.merchantManageUpdate(mm, req,
					loginService.getOriginVo(req)));
		} catch (ParseException e) {
			LogCvt.info("商户解约-日期格式转换" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(), EnumTypes.fail.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), "日期格式转换错误");
		} catch (Exception e) {
			LogCvt.info("商户解约" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 获取所属商户
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "lm", method = RequestMethod.GET)
	public void getAllMerchant(ModelMap model, HttpServletRequest req,
			String orgCode, Integer orgLevel, String merchantName,
			Integer pageNumber, Integer pageSize) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(mmService.getAllMerchant(clientId, orgCode, orgLevel,
					merchantName, pageNumber, pageSize));
		} catch (TException e) {
			LogCvt.info("获取发货商户" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 获取商户分类
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "gmc", method = RequestMethod.POST)
	public void getMerchantCategory(ModelMap model, HttpServletRequest req) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			// LogCvt.info("merchant/gmc============获取到的clientId: "
			// + merchant.getClientId());
			model.putAll(mmService.getMerchantType(clientId));
		} catch (TException e) {
			LogCvt.info("获取商户分类" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 获取商户类型
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "gmt", method = RequestMethod.POST)
	public void getMerchantType(ModelMap model, HttpServletRequest req) {
		try {
			String clientid = req.getAttribute(Constants.CLIENT_ID) + "";
			model.putAll(mmService.getMerchantType(clientid, null));
		} catch (TException e) {
			LogCvt.info("获取商户类型" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * checkAcctNumber:验证商户账户有效性
	 *
	 * @author wm 2015年9月21日 上午11:34:21
	 * @param model
	 * @param req
	 * @throws ParseException
	 *
	 */
	@RequestMapping(value = "bcan", method = RequestMethod.POST)
	public void checkAcctNumber(ModelMap model, HttpServletRequest req,
			@RequestBody BankCarCheckReq car) throws ParseException {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			car.setClientId(clientId);
			model.putAll(mmService.checkAcctNumber(car));
		} catch (TException e) {
			LogCvt.info("验证商户账户有效性： " + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.CODE.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * companyList:获取外包公司列表的接口
	 * 
	 * @author chenmingcan@froad.com.cn 2015年11月26日 下午4:41:05
	 * @param model
	 * @param request
	 *            机构号码
	 *
	 */
	@RequestMapping(value = "/comlt", method = RequestMethod.GET)
	public void companyList(ModelMap model, HttpServletRequest request,
			String dicCode) {
		try {
			String clientId = (String) request
					.getAttribute(Constants.CLIENT_ID);
			model.putAll(mmService.getCompanyList(dicCode, clientId));
		} catch (TException e) {
			model.clear();
			LogCvt.info("获取外包公司列表异常:" + e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}

	}
}
