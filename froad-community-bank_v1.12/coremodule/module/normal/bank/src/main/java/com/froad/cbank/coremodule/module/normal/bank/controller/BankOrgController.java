package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.service.BankOrgService;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.AreaVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;

/**
 * 银行机构信息管理
 * 
 * @author yfy
 * @date 2015-3-20 下午13:36:28
 */
@Controller
@RequestMapping(value = "/bankOrg")
public class BankOrgController extends BasicSpringController {

	@Resource
	private BankOrgService bankOrgService;
	@Resource
	private LoginService loginService;

	/**
	 * @Title: 银行机构信息列表条件查询
	 * @author yfy
	 * @date 2015-3-20 下午13:38:41
	 * @param model
	 * @param req
	 */

	@CheckPermission(keys = { "information_main_menu",
			"organizationmnage_role_menu", "organizationmnage_user_menu",
			"organizationmnage_sure_lt_bind" })
	@RequestMapping(value = "/lt", method = RequestMethod.POST)
	public void bankOrgList(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (StringUtil.isNotEmpty(voReq.getMyOrgCode())) {
				QueryResult<BankOrgVoRes> queryVo = bankOrgService
						.findPageByConditions(voReq);
				model.put("orgList", queryVo.getResult());
				model.put("totalCount", queryVo.getTotalCount());
				model.put("pageCount", queryVo.getPageCount());
				model.put("pageNubmer", queryVo.getPageNumber());
				model.put("lastPageNumber", queryVo.getLastPageNumber());
				model.put("firstRecordTime", queryVo.getFirstRecordTime());
				model.put("lastRecordTime", queryVo.getLastRecordTime());

				model.put("message", "银行机构信息列表条件查询成功");
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全");
			}

		} catch (Exception e) {
			LogCvt.info("银行机构信息列表条件查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title: 银行机构信息列表条件查询
	 * @author xiangkw
	 * @date 2015-10-21
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/ltree", method = RequestMethod.POST)
	public void bankOrgListTree(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (StringUtil.isNotEmpty(voReq.getMyOrgCode())) {
				List<Map<String, Object>> resultList = bankOrgService
						.findOrgTree(voReq);
				model.put("orgList", resultList);

				model.put("message", "银行机构树信息查询成功");
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全");
			}

		} catch (Exception e) {
			LogCvt.info("银行机构信息列表条件查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title: 查询子机构，并把条件机构加入返回的集合
	 * @author xiangkw
	 * @date 2015-10-21
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/lstree", method = RequestMethod.POST)
	public void bankOrgListSubTree(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (StringUtil.isNotEmpty(voReq.getOrgCode())) {
				List<Map<String, Object>> resultList = bankOrgService
						.findOrgTreeByOrgCode(voReq);
				model.put("orgList", resultList);

				model.put("message", "银行机构树信息查询成功");
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "参数不全");
			}

		} catch (Exception e) {
			LogCvt.info("查询子机构树" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title: 银行机构信息新增
	 * @author yfy
	 * @date 2015-3-20 下午13:58:13
	 * @param model
	 * @param req
	 */

	@CheckPermission(keys = { "add_org_ad", "organizationmnage_sure_ad",
			"organizationmnage_right_add_org" })
	@RequestMapping(value = "/ad", method = RequestMethod.POST)
	public void bankOrgAdd(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVo bankOrgVo) {
		try {
			long begin = System.currentTimeMillis();
			bankOrgVo.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			MsgVo msgVo = bankOrgService.verify(bankOrgVo);
			if (msgVo.getResult()) {
				LogCvt.info("机构新增类型: " + bankOrgVo.getOrgType());
				// 业务机构
				if (bankOrgVo.getOrgType()) {
					msgVo = bankOrgService.bankOrgAdd(bankOrgVo, loginService.getOriginVo(req));
				} else {
					// 部门机构
					msgVo = bankOrgService.bankOrgAdd4Department(bankOrgVo, loginService.getOriginVo(req));
				}
				if (msgVo.getResult()) {
					model.put("code", EnumTypes.success.getCode());
				} else {
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());
				Monitor.send(MonitorEnums.bank_bankOrg_ad,
						String.valueOf(System.currentTimeMillis() - begin));
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", msgVo.getMsg());
			}
		} catch (Exception e) {
			LogCvt.info("银行机构信息新增" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title: 银行机构信息修改
	 * @author yfy
	 * @date 2015-3-20 下午14:13:18
	 * @param model
	 * @param req
	 */

	@CheckPermission(keys = { "mod_org_ue", "organizationmnage_sure_ue" })
	@RequestMapping(value = "/ue", method = RequestMethod.PUT)
	public void bankOrgUpdate(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVo bankOrgVo) {
		try {
			long begin = System.currentTimeMillis();
			bankOrgVo.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			MsgVo msgVo = bankOrgService.verify(bankOrgVo);
			if (msgVo.getResult() && bankOrgVo.getOrgId() != null) {
				LogCvt.info("机构修改类型: " + bankOrgVo.getOrgType());
				// 业务机构
				if (bankOrgVo.getOrgType()) {
					msgVo = bankOrgService.bankOrgUpdate(bankOrgVo,
							loginService.getOriginVo(req));
				} else {
					// 部门机构
					msgVo = bankOrgService.bankOrgUpdate4Department(bankOrgVo,
							loginService.getOriginVo(req));
				}
				if (msgVo.getResult()) {
					model.put("code", EnumTypes.success.getCode());
				} else {
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());
				Monitor.send(MonitorEnums.bank_bankOrg_ad,
						String.valueOf(System.currentTimeMillis() - begin));
			} else {
				if (msgVo.getResult()) {
					model.put("message", "机构ID不为空");
				} else {
					model.put("message", msgVo.getMsg());
				}
				model.put("code", EnumTypes.empty.getCode());
			}
		} catch (Exception e) {
			LogCvt.info("银行机构信息修改" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * * @Title: 银行机构信息禁用/启用
	 * 
	 * @author yfy
	 * @date 2015-3-20 下午14:33:42
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/it", method = RequestMethod.POST)
	public void bankOrgIsEnable(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (StringUtil.isNotEmpty(voReq.getOrgId())
					&& StringUtil.isNotEmpty(voReq.getOrgCode())) {
				MsgVo msgVo = null;
				// 业务机构
				if (voReq.getOrgType()) {
					msgVo = bankOrgService.bankOrgIsEnable(voReq,
							loginService.getOriginVo(req));
				} else {
					// 部门机构
					msgVo = bankOrgService.bankOrgIsEnable4Department(voReq,
							loginService.getOriginVo(req));
				}
				if (msgVo != null && msgVo.getResult()) {
					model.put("code", EnumTypes.success.getCode());
				} else {
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "机构ID或组织编码不能为空");
			}

		} catch (Exception e) {
			LogCvt.info("银行机构信息" + (voReq.getState() ? "启用" : "禁用")
					+ e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title: 银行机构信息详情
	 * @author yfy
	 * @date 2015-3-20 下午15:02:20
	 * @param model
	 * @param req
	 */

	@CheckPermission(keys = { "mod_org", "organizationmnage_role_menu",
			"organizationmnage_sure_ue", "organizationmnage_right_add_org" })
	@RequestMapping(value = "/dl", method = RequestMethod.GET)
	public void bankOrgDetail(ModelMap model, HttpServletRequest req,
			String orgCode) {

		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			LogCvt.info("clientId:" + clientId);
			if (StringUtil.isNotEmpty(orgCode)) {
				BankOrgVo bankOrg = bankOrgService.bankOrgDetail(clientId,
						orgCode);
				if (bankOrg != null) {
					model.put("bankOrg", bankOrg);
				}
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "组织编码不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("银行机构信息详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title: 根据机构号验证是否已存在此机构
	 * @author yfy
	 * @date 2015-3-23 下午14:26:10
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/vo", method = RequestMethod.POST)
	public void verifyOrgCode(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			int count = bankOrgService.getOrgByOrgCode(voReq.getClientId(),
					voReq.getOrgCode());
			if (count > 0) {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "已存在此组织编码");
			}
		} catch (Exception e) {
			LogCvt.info("验证组织编码" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * 获取上一级机构名称以及本身的机构名称
	 * 
	 * @param clientId
	 * @param orgLevel
	 * @return
	 */
	@RequestMapping(value = "/bs", method = RequestMethod.POST)
	public void selectBankOrg(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (StringUtil.isNotEmpty(voReq.getOrgCode())
					&& StringUtil.isNotEmpty(voReq.getMyOrgCode())) {
				BankOrgRes bankOrgVo = bankOrgService.getBankOrgByOrgCode(
						voReq.getClientId(), voReq.getOrgCode(),
						voReq.getMyOrgCode());
				if (StringUtil.isNotEmpty(bankOrgVo.getOrgName())) {
					model.put("bankOrg", bankOrgVo);
				} else {
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", bankOrgVo.getPartenOrgName());
				}
			} else {
				model.put("code", EnumTypes.empty.getCode());
				if (!StringUtil.isNotEmpty(voReq.getOrgCode())) {
					model.put("message", "组织编码不能为空");
				}
				if (!StringUtil.isNotEmpty(voReq.getMyOrgCode())) {
					model.put("message", "当前组织编码不能为空");
				}
			}
		} catch (Exception e) {
			LogCvt.info("获取上一级机构名称以及本身的机构名称查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 获取所有机构
	 * 
	 * @param clientId
	 * @param orgLevel
	 * @return
	 */
	@RequestMapping(value = "/ls", method = RequestMethod.POST)
	public void selectAllOrg(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (StringUtil.isNotEmpty(voReq.getOrgCode())) {
				List<BankOrgRes> bankOrgList = bankOrgService
						.getAllOrg(voReq.getClientId(), voReq.getOrgCode());
				model.put("bankOrgList", bankOrgList);
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "组织编码不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("获取所有机构查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 获取所有机构
	 * 
	 * @param clientId
	 * @param orgLevel
	 * @return
	 */
	@RequestMapping(value = "/als", method = RequestMethod.POST)
	public void selectAllOrgCode(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (StringUtil.isNotEmpty(voReq.getOrgCode())) {
				List<BankOrgRes> bankOrgList = bankOrgService
						.getAllOrgCode(voReq.getClientId(), voReq.getOrgCode());
				model.put("bankOrgList", bankOrgList);
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "组织编码不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("获取所有机构查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * @Title: 当前机构获取下级机构（联动查询）
	 * @author yfy
	 * @date 2015-3-23 下午14:26:10
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys = { "organizationmnage_right_ad_user", "bankorg_menu",
			"organizationmnage_role_menu", "merchant_list_menu",
			"form_merchantinformation_menu", "form_merchantcontractors_menu",
			"form_userstatistics_menu", "form_businesssales_menu",
			"form_product_menu" })
	@RequestMapping(value = "/sc", method = RequestMethod.POST)
	public void selectOrgCode(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID) + "");
			if (StringUtil.isNotEmpty(voReq.getOrgCode())
					|| StringUtil.isNotEmpty(voReq.getOrgLevel())) {
				List<BankOrgRes> bankOrgList = bankOrgService
						.selectOrgCode(voReq);
				model.put("bankOrgList", bankOrgList);
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", EnumTypes.empty.getMessage());
			}

		} catch (Exception e) {
			LogCvt.info("当前机构获取下级机构查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title: 当前机构获取上级机构名称
	 * @author yfy
	 * @date 2015-3-23 下午14:26:10
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/spc", method = RequestMethod.GET)
	public void selectPartenOrgNameByOrgCode(ModelMap model,
			HttpServletRequest req, String orgCode) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			if (StringUtil.isNotEmpty(orgCode)) {
				String partenOrgName = bankOrgService
						.getPartenOrgNameByOrgCode(clientId, orgCode);
				model.put("partenOrgName", partenOrgName);
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "组织编码不能为空");
			}

		} catch (Exception e) {
			LogCvt.info("当前机构获取上级机构名称查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title: 当前机构获取上级机构名称(新增方法:返回当前org的上级对象id和name)
	 * @author yfy
	 * @date 2015-3-23 下午14:26:10
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/nspc", method = RequestMethod.GET)
	public void newSelectPartenOrgNameByOrgCode(ModelMap model,
			HttpServletRequest req, String orgCode) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			if (StringUtil.isNotEmpty(orgCode)) {
				Map<String, String> map = bankOrgService
						.getPartenOrgByOrgCode(clientId, orgCode);
				model.put("partenOrgName", map.get("partenOrgName"));
				model.put("partenOrgCode", map.get("partenOrgCode"));
			} else {
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "组织编码不能为空");
			}

		} catch (Exception e) {
			LogCvt.info("当前机构获取上级机构名称查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * @Title: 地区联动查询(根据id获取子集地区)
	 * @author yfy
	 * @date 2015-3-23 下午14:26:10
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/sa", method = RequestMethod.POST)
	public void selectArea(ModelMap model, HttpServletRequest req,
			@RequestBody BankOrgVoReq voReq) {
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID) + "";
			List<AreaVo> areaList = bankOrgService.findChildrenInfoById(
					voReq.getAreaId(), voReq.getAreaCode(), clientId);
			model.put("areaList", areaList);
		} catch (Exception e) {
			LogCvt.info("地区联动查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}

	}

	/**
	 * 
	 * getOrgListByName:根据机构名称查询机构树(模糊查询)
	 * 
	 * @author chenmingcan@froad.com.cn 2015年11月27日 上午11:03:02
	 * @param model
	 * @param req
	 * @param orgName
	 *
	 */
	@RequestMapping(value = "/gln", method = RequestMethod.GET)
	public void getOrgListByName(ModelMap model, HttpServletRequest req,
			String orgName, String loginOrgCode) {
		try {
			// String name = new String(orgName.getBytes("ISO-8859-1"),
			// "UTF-8");
			String clientId = (String) req.getAttribute(Constants.CLIENT_ID);
			model.putAll(bankOrgService.getOrgListByName(orgName, loginOrgCode,
					clientId));
		} catch (Exception e) {
			LogCvt.info("根据机构名称查询机构树" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		}

	}

}
