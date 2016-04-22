package com.froad.fft.controller.member.pay;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.bean.PayResult;
import com.froad.fft.bean.PayResult.PayCode;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.enums.trans.TransPayState;
import com.froad.fft.enums.trans.TransState;
import com.froad.fft.support.base.impl.TransSupportImpl;

@Controller("member.pay")
@RequestMapping("/member/pay")
public class CommonController extends BaseController{

	@Resource
	private TransSupportImpl transSupportImpl;
	
	final String waitMsg = "我们还未收到您的款项。若您已完成付款，可能系统有延迟，请稍后到我的订单中查询";
	final String errorMsg = "支付失败，系统繁忙";
	final String successMsg = "交易成功";
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询支付宝|网银订单交易状态</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月8日 下午12:00:49 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "check_trans_state")
	public String checkTransState(HttpServletRequest req,ModelMap modelMap){
		Object sessionVal = getSessionValue(req, SessionKey.SYSTEM_TEMP_VAL);
		if(sessionVal == null){
			logger.info("查询交易状态时无有效的transSn");
			modelMap.put("illMsg","抱歉，您查询的交易不存在：您的交易未成功创建");
			return ILLEGALITY;
		}
		String tranSn = (String)sessionVal;
		TransQueryDto trans = transSupportImpl.getTransByTransSn(tranSn);
		if(trans == null){
			logger.info("transSn" + tranSn + " 无有效对应的订单交易");
			modelMap.put("illMsg","抱歉，您查询的交易不存在：您的交易未成功创建");
			return ILLEGALITY;
		}
		
		logger.info("transQueryDto:" + JSONObject.toJSONString(trans));
		
		PayCode flag = null;
		String msg = null;
		
		removeSessionByKey(req, SessionKey.SYSTEM_TEMP_VAL);
		
		//开始判断支付宝|网银交易是否成功
		if(trans.getPayState().equals(TransPayState.paid)){ //支付成功
			updateUserAllInfo(req);
			flag = PayCode.success;
			msg = successMsg;
			modelMap.put("payResult", new PayResult(msg, flag));
			return "/member/pay/order_result";
		}
		if(trans.getState().equals(TransState.fail) || trans.getState().equals(TransState.exception)){//支付失败
			flag = PayCode.error;
			msg = errorMsg;
			modelMap.put("payResult", new PayResult(msg, flag));
			return "/member/pay/order_result";
		}
		msg = waitMsg;
		flag = PayCode.wait;
		modelMap.put("payResult", new PayResult(msg, flag));
		return "/member/pay/order_result";//等待支付
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>检查贴膜卡支付状态</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月9日 下午2:51:06 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "check_bank_card_state" , method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean checkBankTransState(HttpServletRequest req){
		Object sessionVal = getSessionValue(req, SessionKey.SYSTEM_TEMP_VAL);
		if(sessionVal == null){
			logger.info("查询交易状态时无有效的transSn");
			return new AjaxCallBackBean(false);
		}
		String tranSn = (String)sessionVal;
		TransQueryDto trans = transSupportImpl.getTransByTransSn(tranSn);
		if(trans == null){
			logger.info("transSn" + tranSn + " 无有效对应的订单交易");
			return new AjaxCallBackBean(false);
		}
		logger.info("transQueryDto:" + JSONObject.toJSONString(trans));
		if(trans.getPayState().equals(TransPayState.paid)){ //支付成功
			updateUserAllInfo(req);
			removeSessionByKey(req, SessionKey.SYSTEM_TEMP_VAL);
			createSessionObject(req, SessionKey.SYSTEM_TEMP_VAL, new PayResult("支付成功", PayCode.success));
			return new AjaxCallBackBean(true,"success");
		}
		if(trans.getState().equals(TransState.fail) || trans.getState().equals(TransState.exception)){//支付失败
			removeSessionByKey(req, SessionKey.SYSTEM_TEMP_VAL);
			createSessionObject(req, SessionKey.SYSTEM_TEMP_VAL, new PayResult("支付失败", PayCode.error));
			return new AjaxCallBackBean(true,"error");
		}
		return new AjaxCallBackBean(true,"wait");
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>手机银行贴膜卡最终支付状态</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月9日 下午3:15:47 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "pay_bank_card_result")
	public String staticOrderResult(HttpServletRequest req,ModelMap modelMap){
		Object sessionVal = getSessionValue(req, SessionKey.SYSTEM_TEMP_VAL);
		if(sessionVal == null){
			return ILLEGALITY;
		}
		PayResult payResult = (PayResult)sessionVal;
		removeSessionByKey(req, SessionKey.SYSTEM_TEMP_VAL);
		modelMap.put("payResult", payResult);
		return "/member/pay/order_result";
	}
}
