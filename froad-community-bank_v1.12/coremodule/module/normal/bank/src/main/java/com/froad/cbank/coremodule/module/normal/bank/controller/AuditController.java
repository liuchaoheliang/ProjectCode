package com.froad.cbank.coremodule.module.normal.bank.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.froad.cbank.coremodule.module.normal.bank.service.AuditService;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.AuditVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;

/**
 * 审核提交
 * @author yfy
 * @date 2015-3-21 上午11:51:06
 */
@Controller
@RequestMapping(value = "/audit")
public class AuditController extends BasicSpringController{

	@Resource
	private AuditService auditService; 
	@Resource
	private LoginService loginService;
	
	/**
	 * 
	 * merchantAuditNew:(商户审核 调用审核引擎新接口).
	 *
	 * @author wufei
	 * 2015-10-22 下午04:00:38
	 * @param model
	 * @param req
	 * @param voReq
	 * @throws TException 
	 *
	 */
	@CheckPermission(keys={"fomous_merchant_examinemerchant_examine"})
	@RequestMapping(value = "/ma",method = RequestMethod.POST)
    public void merchantAuditNew(ModelMap model,HttpServletRequest req,@RequestBody AuditReq auditReq) throws TException{
		MsgVo msgVo = auditService.verifyAuditVo(auditReq);
		if(msgVo.getResult()){
			msgVo = auditService.merchantAuditNew(auditReq,loginService.getOrigin(req));
			if(msgVo.getResult()){
				model.put("code", EnumTypes.success.getCode());
			}else{
				model.put("code", EnumTypes.fail.getCode());
			}
			model.put("message", msgVo.getMsg());
		}else{
			model.put("message", msgVo.getMsg());
			model.put("code", EnumTypes.empty.getCode());
		}
	}
	
	
	/**
	 * @Title: 商品审核提交
	 * @author yfy
	 * @date 2015-3-21 下午10:54:58
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"fomous_group_ground_detail_examine","audit_famous_detail_detail_examine","foumos_yushou_detail_detail_examine"})
	@RequestMapping(value = "/pa",method = RequestMethod.POST)
    public void productAudit(ModelMap model,HttpServletRequest req,@RequestBody AuditVoReq voReq) {
		 
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = auditService.verify(voReq);
			if(msgVo.getResult() && StringUtil.isNotEmpty(voReq.getProductId())){
				msgVo = auditService.productAudit(voReq,loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code",  EnumTypes.success.getCode());
				}else{
					model.put("code",  EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());
			}else{
				if(msgVo.getResult()){
					model.put("message", "商品ID不为空");
				}else{
					model.put("message", msgVo.getMsg());
				}
				model.put("code", EnumTypes.empty.getCode());
			}
		} catch (Exception e) {
			LogCvt.info("商品审核提交"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 秒杀商品审核提交
	 * @author wangzhangxu
	 * @date 2015-5-6 下午7:54:58
	 * @param model
	 * @param req
	 * @param voReq
	 */
	@CheckPermission(keys={"fomous_offline_detail_detail_examine","to_audit_seckilldetail_tg_examine","to_audit_seckilldetail_jpys_examine","to_audit_seckilldetail_myth_examine"})
	@RequestMapping(value = "/spa",method = RequestMethod.POST)
    public void seckillProductAudit(ModelMap model,HttpServletRequest req,@RequestBody AuditVoReq voReq) {
		
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = auditService.verify(voReq);
			if(msgVo.getResult() && StringUtil.isNotEmpty(voReq.getProductId())){
				msgVo = auditService.seckillProductAudit(voReq,loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code",  EnumTypes.success.getCode());
				}else{
					model.put("code",  EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());
			}else{
				if(msgVo.getResult()){
					model.put("message", "商品ID不为空");
				}else{
					model.put("message", msgVo.getMsg());
				}
				model.put("code", EnumTypes.empty.getCode());
			}
		} catch (Exception e) {
			LogCvt.info("秒杀商品审核提交"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message",  EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 线下积分礼品审核提交
	 * @author yfy
	 * @date 2015-4-7  下午17:54:58
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/lpa",method = RequestMethod.POST)
    public void lineProductAudit(ModelMap model,HttpServletRequest req,@RequestBody AuditVoReq voReq) {
		 
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = auditService.verify(voReq);
			if(msgVo.getResult() && StringUtil.isNotEmpty(voReq.getProductId())
					&& voReq.getProductIdList() != null && voReq.getProductIdList().size() > 0){
				msgVo = auditService.lineProductAudit(voReq,loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code",  EnumTypes.success.getCode());
				}else{
					model.put("code",  EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());
			}else{
				if(msgVo.getResult()){
					model.put("message", "商品ID不为空");
				}else{
					model.put("message", msgVo.getMsg());
				}
				model.put("code", EnumTypes.empty.getCode());
			}
		} catch (Exception e) {
			LogCvt.info("线下积分礼品审核提交"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message",  EnumTypes.thrift_err.getMessage());
		}
	}

}
