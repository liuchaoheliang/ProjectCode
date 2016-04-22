package com.froad.fft.controller.member.retrysms;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.bean.Result;
import com.froad.fft.controller.BaseController;
import com.froad.fft.support.base.impl.RetrySMSSupportImpl;

@Controller("member.retrysms")
@RequestMapping("/member/retrysms")
public class RetrySMSController extends BaseController{

	@Resource
	private RetrySMSSupportImpl retrySMSSupportImpl;
	
	@RequestMapping(value = "retry_presell_sms" , method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean retryPresell(HttpServletRequest req,Long transId){
		Result result = retrySMSSupportImpl.retryPresell(transId, req.getRemoteAddr());
		if("true".equals(result.getCode())){
			return new AjaxCallBackBean(true,result.getMsg());
		}
		return new AjaxCallBackBean(false,result.getMsg());
	}
}
