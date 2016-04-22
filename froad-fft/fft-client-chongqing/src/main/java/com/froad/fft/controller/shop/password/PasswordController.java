package com.froad.fft.controller.shop.password;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.support.base.UserEngineSupport;

@Controller("shop.password")
@RequestMapping("/shop/password")
public class PasswordController extends BaseController{
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@RequestMapping(value="index",method=RequestMethod.GET)
	public String index(){
		return "/shop/password/index";
	}
	
	@RequestMapping(value="reset_password",method=RequestMethod.POST)
	public String resetPassword(String mobile,ModelMap modelMap,HttpServletRequest req){
		if(StringUtils.isBlank(mobile)){
			modelMap.put("msg", "手机号码为空，无法重置");
			return "/shop/password/find_password";
		}else{
			if(getSessionValue(req, SessionKey.SYSTEM_TEMP_VAL)!=null){
				UserEngineDto userEngineDto =(UserEngineDto)getSessionValue(req, SessionKey.SYSTEM_TEMP_VAL);
				if(!userEngineDto.getMobile().equals(mobile)){//验证手机，是否请求验证码的手机。
					modelMap.put("illMsg", "错误的访问请求");
					return ILLEGALITY;
				}
			}else{
				modelMap.put("illMsg", "错误的访问请求");
				return ILLEGALITY;
			}
			
		}
		return "/shop/password/reset_password";
	}
	
	@RequestMapping(value="reset_pwd",method=RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean resetPwd(String password,HttpServletRequest req){
		if(StringUtils.isBlank(password)){
			return new AjaxCallBackBean(false,"新密码不能为空");
		}
		if(getSessionValue(req, SessionKey.SYSTEM_TEMP_VAL)==null){
			return new AjaxCallBackBean(false,"错误的访问请求");
		}
		//需要重置密码的用户
		UserEngineDto userEngineDto =(UserEngineDto)getSessionValue(req, SessionKey.SYSTEM_TEMP_VAL);
		//调接口修改
		userEngineDto = userEngineSupport.resetUserPwd(userEngineDto.getMemberCode(), password);
		if(userEngineDto!=null&&userEngineDto.getResult()){
			//验证通过后，移除当前修改密码的手机。
			removeSessionByKey(req, SessionKey.SYSTEM_TEMP_VAL);
			return new AjaxCallBackBean(true,"修改成功，请重新登录");
		}
		return new AjaxCallBackBean(false,"修改失败,请稍后再试");
	}
	
	/**
	*<p>重置密码(验证手机是否存在)</p>
	* @author larry
	* @datetime 2014-4-11下午02:05:09
	* @return AjaxCallBackBean
	* @throws 
	* @example<br/>
	*
	 */
	@RequestMapping(value="validateUserPhoneExists",method=RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean validateUserPhoneExists(HttpServletRequest req,String mobile){
		if(StringUtils.isBlank(mobile)){
			return new AjaxCallBackBean(false,"验证手机号码不能为空");
		}
		UserEngineDto userEngineDto = new UserEngineDto();
		userEngineDto =  getUserEngineByLoginID(mobile);
		if(userEngineDto!=null){
//			return new AjaxCallBackBean(false,"手机号码不存在");
			//记录当前需要重置密码的用户,防止短信验证通过后，更换手机号重置密码
			createSessionObject(req, SessionKey.SYSTEM_TEMP_VAL, userEngineDto);
		}
		return new AjaxCallBackBean(true);
	}
}
