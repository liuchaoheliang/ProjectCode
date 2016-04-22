package com.froad.fft.controller.member.phone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.support.base.impl.UserEngineSupportImpl;
import com.froad.fft.util.NullValueCheckUtil;

@Controller("member.phone")
@RequestMapping("/member/phone")
public class PhoneController extends BaseController{

	@Resource
	private UserEngineSupportImpl userEngineSupportImpl;
	
	@RequestMapping(value = "index" , method = RequestMethod.GET)
	public void index(){}
	
	@RequestMapping(value = "update" , method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean updatePhone(HttpServletRequest req,String phone){
		Object obj = getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		if(obj == null){
			return new AjaxCallBackBean(false,"系统繁忙请稍后再试");
		}
		if(NullValueCheckUtil.isStrEmpty(phone)){
			return new AjaxCallBackBean(false,"请输入正确的手机号码");
		}
		
		UserEngineDto userEngineDto = (UserEngineDto)obj;
		if(userEngineDto.getMobile().equals(phone)){
			return new AjaxCallBackBean(false,"修改手机号码不能与当前手机号码相同");
		}
		userEngineDto = userEngineSupportImpl.updateUserMobile(userEngineDto.getMemberCode(), phone);
		if(userEngineDto.getResult()){
			updateUserAllInfo(req);
			return new AjaxCallBackBean(true,"手机号码修改成功！");
		}else{
			return new AjaxCallBackBean(false,userEngineDto.getErrorMsg());
		}
	}
}
