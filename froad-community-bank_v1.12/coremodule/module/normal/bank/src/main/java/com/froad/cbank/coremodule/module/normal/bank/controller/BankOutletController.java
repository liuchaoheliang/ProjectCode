package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.enums.ResultEnum;
import com.froad.cbank.coremodule.module.normal.bank.service.BankOutletSupport;
import com.froad.cbank.coremodule.module.normal.bank.util.BankHandle;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QrcodeDownUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.RedisKeys;
import com.froad.cbank.coremodule.module.normal.bank.util.RespError;
import com.froad.cbank.coremodule.module.normal.bank.util.TargetObjectFormat;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletAuditReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletDetailReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletListReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOutletReqVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OutletQrcodeReq;
import com.froad.cbank.expand.redis.RedisManager;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletVo;

/**
 * 
 * @ClassName: BankOutletController
 * @Description: 银行管理平台门店审核相关类
 * @author ming
 * @date 2015年10月22日 上午11:03:31
 *
 */
@Controller
@RequestMapping(value = "outlet")
public class BankOutletController extends BasicSpringController {
	
	public static final String OPEN = "1";
	@Resource
	private BankOutletSupport bankOutletSupport;
	
	@Resource
	OutletService.Iface outletService;
	
	@Resource
	private RedisManager redisManager;
	
	/**
	 * 
	* @Title: queryOutletList 
	* @Description: 待审核列表查询
	* @param  model
	* @param  req
	* @param  reqVo    
	* @return void    
	* @throws
	 */
	@CheckPermission(keys={"examinestore_menu","examinestore_select_bind"})
	@RequestMapping(value = "lt", method = RequestMethod.POST)
	public void queryOutletList(ModelMap model, HttpServletRequest req, @RequestBody BankOutletReqVo reqVo) {
		try {
			model.clear();
			//参数规格检验
			Map<String, String> map = verify4QueryList(reqVo);
			if (map != null && map.size() > 0) {
				model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				model.put(ResultEnum.MESSAGE.getCode(), map.get(ResultEnum.MESSAGE.getCode()));
				return;
			}
			reqVo.setClientId(req.getAttribute(Constants.CLIENT_ID).toString());
			reqVo.setUserId(req.getAttribute(Constants.USER_ID).toString());
			reqVo.setIp(TargetObjectFormat.getIpAddr(req));
			model.putAll(bankOutletSupport.queryOutletList(reqVo));
		} catch (Exception e) {
			LogCvt.info("银行管理平台待审核门店列表请求:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 
	 * verify4QueryList:请求参数校验
	 *
	 * @author asus 2015年10月27日 下午2:25:37
	 * @param reqVo
	 * @throws ParseException
	 *
	 */
	private Map<String, String> verify4QueryList(BankOutletReqVo reqVo) throws ParseException {
		Map<String, String> resMap = new HashMap<String, String>();
		if (StringUtil.isNotBlank(reqVo.getStartDate()) && StringUtil.isNotBlank(reqVo.getEndDate())) {
			long startDate = DateUtil.DateFormat(reqVo.getStartDate());
			long endDate = DateUtil.DateFormat(reqVo.getEndDate());
			if ((endDate - startDate) < 0) {
				resMap.put(ResultEnum.MESSAGE.getCode(), "结束时间不能早于开始时间");
				return resMap;
			}
		}
		if (StringUtil.isNotBlank(reqVo.getMerchantName()) && reqVo.getMerchantName().length() > 32) {
			resMap.put(ResultEnum.MESSAGE.getCode(), "商户名称不能超过32个字符");
			return resMap;
		}
		if (StringUtil.isNotBlank(reqVo.getOutletName()) && reqVo.getOutletName().length() > 16) {
			resMap.put(ResultEnum.MESSAGE.getCode(), "门店名称不能超过16个字符");
			return resMap;
		}
		return null;
	}

	/**
	 * 
	 * addOutletDetail:新增门店审核详情
	 *
	 * @author asus 2015年10月27日 下午2:58:24
	 * @param model
	 * @param req
	 * @param reqVo 
	 *
	 */
	@CheckPermission(keys={"examinestore_detail_bind"})
	@RequestMapping(value = "addl", method = RequestMethod.GET)
	public void addOutletDetail(ModelMap model, HttpServletRequest req, BankOutletDetailReqVo reqVo) {
		try {
			model.clear();
			// 请求参数非空校验
			Map<String, String> map = verify4Detail(reqVo);
			if (map != null && map.size() > 0) {
				model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				model.put(ResultEnum.MESSAGE.getCode(), map.get(ResultEnum.MESSAGE.getCode()));
				return;
			}
			reqVo.setClientId(req.getAttribute(Constants.CLIENT_ID).toString());
			reqVo.setUserId(req.getAttribute(Constants.USER_ID).toString());
			reqVo.setIp(TargetObjectFormat.getIpAddr(req));
			model.putAll(bankOutletSupport.addOutletDetail(reqVo));
		} catch (Exception e) {
			LogCvt.info("银行管理平台待新增门店详情请求:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * 
	 * verify4Detail:详情请求参数校验
	 *
	 * @author asus 2015年10月27日 下午3:19:50
	 * @param reqVo
	 * @param model
	 *
	 */
	private Map<String, String> verify4Detail(BankOutletDetailReqVo reqVo) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (!StringUtil.isNotBlank(reqVo.getAuditNumber())) {
			resMap.put(ResultEnum.MESSAGE.getCode(), "审核流水号不能为空");
			return resMap;
		}
		if (!StringUtil.isNotBlank(reqVo.getOutletId())) {
			resMap.put(ResultEnum.MESSAGE.getCode(), "门店编号不能为空");
			return resMap;
		}
		return null;

	}

	/**
	 * 
	 * @Title: updateOutletDetail @Description: 更新门店审核详情 @param model @param
	 * req @param reqVo @return void @throws
	 */
	@CheckPermission(keys={"examinestore_detail_bind"})
	@RequestMapping(value = "updl", method = RequestMethod.GET)
	public void updateOutletDetail(ModelMap model, HttpServletRequest req, BankOutletDetailReqVo reqVo) {
		try {
			model.clear();
			// 请求参数非空校验
			Map<String, String> map = verify4Detail(reqVo);
			if (map != null && map.size() > 0) {
				model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				model.put(ResultEnum.MESSAGE.getCode(), map.get(ResultEnum.MESSAGE.getCode()));
				return;
			}
			reqVo.setClientId(req.getAttribute(Constants.CLIENT_ID).toString());
			reqVo.setUserId(req.getAttribute(Constants.USER_ID).toString());
			reqVo.setIp(TargetObjectFormat.getIpAddr(req));
			model.putAll(bankOutletSupport.updateOutletDetail(reqVo));
		} catch (Exception e) {
			LogCvt.info("银行管理平台待更新门店详情请求:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * outletAudit:商户门店银行管理平台审核接口
	 *
	 * @author asus 2015年10月27日 下午2:59:24
	 * @param model
	 * @param req
	 * @param reqVo
	 *
	 */
	@CheckPermission(keys={"examinestore_examine"})
	@RequestMapping(value = "audit", method = RequestMethod.POST)
	public void outletAudit(ModelMap model, HttpServletRequest req, @RequestBody BankOutletAuditReqVo reqVo) {
		try {
			model.clear();
			// 请求参数非空校验
			Map<String, String> map = verify4Audit(reqVo);
			if (map != null && map.size() > 0) {
				model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				model.put(ResultEnum.MESSAGE.getCode(), map.get(ResultEnum.MESSAGE.getCode()));
				return;
			}
			reqVo.setClientId(req.getAttribute(Constants.CLIENT_ID).toString());
			reqVo.setUserId(req.getAttribute(Constants.USER_ID).toString());
			reqVo.setIp(TargetObjectFormat.getIpAddr(req));
			model.putAll(bankOutletSupport.outletAudit(reqVo));
		} catch (Exception e) {
			LogCvt.info("银行管理平台门店审核请求:" + e.getMessage(), e);
			model.clear();
			model.put(ResultEnum.FAIL.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * verify4Audit:审核接口请求参数校验
	 *
	 * @author asus 2015年10月27日 下午3:43:54
	 * @param reqVo
	 * @param model
	 *
	 */
	private Map<String, String> verify4Audit(BankOutletAuditReqVo reqVo) {
		Map<String, String> resMap = new HashMap<String, String>();
		if (!StringUtil.isNotBlank(reqVo.getInstanceId())) {
			resMap.put(ResultEnum.MESSAGE.getCode(), "审核流水号不能为空");
			return resMap;
		}
		if (!StringUtil.isNotBlank(reqVo.getOutletId())) {
			resMap.put(ResultEnum.MESSAGE.getCode(), "门店编号不能为空");
			return resMap;
		}
		if (!StringUtil.isNotBlank(reqVo.getTaskId())) {
			resMap.put(ResultEnum.MESSAGE.getCode(), "任务id不能为空");
			return resMap;
		}
		if (!StringUtil.isNotBlank(reqVo.getAuditState())) {
			resMap.put(ResultEnum.MESSAGE.getCode(), "审核状态不能为空");
			return resMap;
		}
		//审核不通过时
		if ("2".equals(reqVo.getAuditState()) && !StringUtil.isNotBlank(reqVo.getRemark())) {
			if (reqVo.getRemark().length() > 200) {
				resMap.put(ResultEnum.MESSAGE.getCode(), "审核备注不能超过200个字符");
				return resMap;
			}
			resMap.put(ResultEnum.MESSAGE.getCode(), "审核备注不能为空");
			return resMap;
		}
		return null;
	}

	/**
	 * 
	 * getOutletList:银行管理平台门店列表请求接口
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月4日 上午11:33:50
	 * @param model
	 * @param req
	 * @param reqVo
	 *
	 */
	@CheckPermission(keys = { "bank_merchant_outlet_menu",
			"bank_merchant_outlet_select_bind" })
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public void getOutletList(ModelMap model, HttpServletRequest req, @RequestBody BankOutletListReqVo reqVo) {
		model.clear();
		// 请求参数非空校验
		reqVo.setClientId(req.getAttribute(Constants.CLIENT_ID).toString());
		try {
			model.putAll(bankOutletSupport.getOutletList(reqVo));
		} catch (TException e) {
			LogCvt.info("银行管理平台门店列表请求:" + e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		} catch (ParseException e) {
			LogCvt.info("银行管理平台门店列表请求:" + e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(),
					ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(), "日期转化异常");
		}
	}

	/**
	 * 
	 * getOutletDetail:门店详情
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月4日 下午2:39:32
	 * @param model
	 * @param req
	 * @param outletId
	 *
	 */
	@CheckPermission(keys = { "bank_merchant_outlet_detail_bind" })
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public void getOutletDetail(ModelMap model, HttpServletRequest req, String outletId) {
		try {
			model.clear();
			// 请求参数非空校验
			if (!StringUtil.isNotBlank(outletId)) {
				model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				model.put(ResultEnum.MESSAGE.getCode(), "门店id不能为空");
				return;
			}
			String clientId = req.getAttribute(Constants.CLIENT_ID).toString();
			model.putAll(bankOutletSupport.getOutletDetail(outletId, clientId));
		} catch (TException e) {
			LogCvt.info("银行管理平台门店详情请求:" + e.getMessage(), e);
			model.put(ResultEnum.CODE.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * OutletPreference:开通/关闭惠付功能
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月4日 下午3:48:55
	 * @param model
	 * @param req
	 * @param outletId
	 *
	 */
	@CheckPermission(keys = { "bank_merchant_outlet_preference_open",
			"bank_merchant_outlet_preference_close" })
	@RequestMapping(value = "preference", method = RequestMethod.GET)
	public void changeOutletPreference(ModelMap model, HttpServletRequest req, String outletId, String isOpen) {
		try {
			model.clear();
			// 请求参数非空校验
			if (!StringUtil.isNotBlank(outletId)) {
				model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				model.put(ResultEnum.MESSAGE.getCode(), "门店id不能为空");
				return;
			}
			if (!StringUtil.isNotBlank(isOpen)) {
				model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
				model.put(ResultEnum.MESSAGE.getCode(), "惠付状态不能为空");
				return;
			}
			OutletVo outletVo = new OutletVo();
			outletVo.setOutletId(outletId);
			if (isOpen.equals(OPEN)) {
				outletVo.setPreferStatus(true);
			} else {
				outletVo.setPreferStatus(false);
			}
			outletVo.setClientId(req.getAttribute(Constants.CLIENT_ID).toString());
			OriginVo originVo = BankHandle.getOriginVo(req);
			model.putAll(bankOutletSupport.changeOutletPreference(outletVo, originVo));
		} catch (TException e) {
			LogCvt.info("银行管理平台门店详情请求:" + e.getMessage(), e);
			model.put(ResultEnum.CODE.getCode(), EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(), EnumTypes.thrift_err.getMessage());
		}
	}

	/**
	 * 
	 * getOutletListExport:门店列表导出接口
	 * 
	 * @author chenmingcan@froad.com.cn 2016年1月5日 下午2:08:50
	 * @param model
	 * @param req
	 * @param reqVo
	 *
	 */
	@CheckPermission(keys = { "bank_merchant_outlet_export" })
	@RequestMapping(value = "export", method = RequestMethod.GET)
	public void getOutletListExport(ModelMap model, HttpServletRequest req,BankOutletListReqVo reqVo) {
		model.clear();
		// 请求参数非空校验
		reqVo.setClientId(req.getAttribute(Constants.CLIENT_ID).toString());
		try {
			model.putAll(bankOutletSupport.getOutletListExport(reqVo));
		} catch (TException e) {
			LogCvt.info("银行管理平台门店列表导出请求:" + e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(),
					EnumTypes.thrift_err.getCode());
			model.put(ResultEnum.MESSAGE.getCode(),
					EnumTypes.thrift_err.getMessage());
		} catch (ParseException e) {
			LogCvt.info("银行管理平台门店列表导出请求:" + e.getMessage(), e);
			model.put(ResultEnum.FAIL.getCode(),
					ResultEnum.FAIL.getDescrition());
			model.put(ResultEnum.MESSAGE.getCode(), "日期转化异常");
		}
	}

	/**
	 * 
	 * qrcodeDown:(门店二维码下载).
	 *
	 * @author wufei
	 * 2016-1-8 下午05:30:20
	 * @param response
	 * @param request
	 * @param model
	 * @param req
	 * @throws Exception
	 *
	 */
	@CheckPermission(keys = { "bank_merchant_outlet_qrcode" })
	@RequestMapping(value = "qrcodeDown", method = RequestMethod.GET)
	public void qrcodeDown(HttpServletResponse response, HttpServletRequest request,ModelMap model,
			OutletQrcodeReq req) throws Exception{
		
			try {
				if (StringUtils.isBlank(req.getOutletId())) {
					model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
					model.put(ResultEnum.MESSAGE.getCode(), "门店id不能为空");
					return;
				}
				if (StringUtils.isBlank(req.getType())) {
					model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
					model.put(ResultEnum.MESSAGE.getCode(), "下载类型不能为空");
					return;
				}
				String clientId = request.getAttribute(Constants.CLIENT_ID).toString();
				
				OutletVo outletVo=outletService.getOutletByOutletId(req.getOutletId());
				
				LogCvt.info("门店id："+outletVo.getOutletId()+" qrcodeUrl："+outletVo.getQrcodeUrl());
				if(outletVo != null &&  StringUtil.isNotBlank(outletVo.getQrcodeUrl())){
					if(req.getType().equals("0")){ //保存二维码
						 QrcodeDownUtil.qrcodeDown(outletVo.getQrcodeUrl(), response,model,outletVo,req.getType());
						
					}else if(req.getType().equals("1")){//保存完整图片
						//从redis缓存中获取图片
						String key = RedisKeys.merchant_outlet_extend(outletVo.getMerchantId(), outletVo.getOutletId());
						String qrcodeUrl = redisManager.getString(key);
						LogCvt.info("qrcodeUrl："+qrcodeUrl);
						if(StringUtil.isBlank(qrcodeUrl)){
							//合成图片并下载
							String qrcodeUrlScp = QrcodeDownUtil.qrcodeMixed(outletVo,response,model,clientId);
							//生成一张图片放入redis缓存中
							redisManager.putString(key, qrcodeUrlScp);
						}else{
							//缓存中有就直接下载
							QrcodeDownUtil.qrcodeDown(qrcodeUrl, response,model,outletVo,req.getType());
						}
						
					}
				}else{
					model.put(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getDescrition());
					model.put(ResultEnum.MESSAGE.getCode(), "下载失败");
					return;
				}
			} catch (Exception e) {
				new RespError(model, e);
			}
		}

}
