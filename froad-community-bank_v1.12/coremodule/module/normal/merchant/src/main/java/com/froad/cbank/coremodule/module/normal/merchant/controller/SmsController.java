//package com.froad.cbank.coremodule.module.normal.merchant.controller;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
//import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
//import com.froad.cbank.coremodule.module.normal.merchant.service.Sms_Service;
//import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
//import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;
//
///**
// * 短信
// * @ClassName SmsController
// * @author zxl
// * @date 2015年3月21日 下午1:59:36
// */
//@Controller
//@RequestMapping(value = "/sms")
//public class SmsController {
//	
//	@Resource
//	Sms_Service sms;
//	
//	/**
//	 * 短信发送
//	 * @tilte queryAdminInfo
//	 * @author zxl
//	 * @date 2015年3月23日 上午11:01:55
//	 * @param model
//	 * @param req
//	 */
//	@RequestMapping(value = "s" , method = RequestMethod.GET)
//	public void send(ModelMap model, HttpServletRequest request,String mobile,int type,String userName){
//		try {
//			long clientId =Long.valueOf(request.getAttribute(Constants.CLIENT_ID)+"");
//			model.putAll(sms.smsSend(mobile,type,clientId,userName));
//		} catch (MerchantException e) {
//			new RespError(model, e);
//		} catch (Exception e) {
//			LogCvt.error(e.getMessage(), e);
//			new RespError(model);
//		}
//	}
//	
//	/**
//	 * 短信校验
//	 * @tilte valid
//	 * @author zxl
//	 * @date 2015年3月23日 上午11:07:04
//	 * @param model
//	 * @param req
//	 */
//	@RequestMapping(value = "v" , method = RequestMethod.GET)
//	public void valid(ModelMap model,String codeId,String code){
//		try {
//			model.putAll(sms.smsValid(codeId,code));
//		} catch (MerchantException e) {
//			new RespError(model, e);
//		} catch (Exception e) {
//			LogCvt.error(e.getMessage(), e);
//			new RespError(model);
//		}
//	}
//	
//}
