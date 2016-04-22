package com.froad.fft.controller.shop;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.common.CookieKey;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.AgreementDto;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.enums.AgreementType;
import com.froad.fft.support.base.AgreementSupport;
import com.froad.fft.support.base.UserEngineSupport;
import com.froad.fft.util.NullValueCheckUtil;
import com.froad.fft.util.WebUtil;

/**
 * 会员注册
 * @author FQ
 *
 */

@Controller
@RequestMapping("/shop/register")
public class RegisterController extends BaseController {
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private AgreementSupport agreementSupport;
	
	/**
	 * 注册页面
	 */
	@RequestMapping(value = "index" , method = RequestMethod.GET)
	public String index(HttpServletRequest req, ModelMap model) {
		model.addAttribute("captchaId", UUID.randomUUID().toString());
		return "/shop/register/index";
	}
	
	/**
	 * 注册提交
	 */
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean submit(HttpServletRequest req,HttpServletResponse res,UserEngineDto userEngineDto,String phonecode) {
		String smsOirgCode = (String)getSessionValue(req, SessionKey.VALIDATION_COMMON_TEMP_VAL);
		String oirgCode = smsOirgCode.split("\\|")[0];
		if(!oirgCode.equals(oirgCode)){
			return new AjaxCallBackBean(false, "手机验证码无效");
		}
		removeSessionByKey(req, SessionKey.VALIDATION_COMMON_TEMP_VAL);
		//验证相关数据
		if(NullValueCheckUtil.isStrEmpty(userEngineDto.getLoginID())){
			return new AjaxCallBackBean(false, "请输入有效的帐号");
		}
		if (NullValueCheckUtil.isStrEmpty(userEngineDto.getLoginPwd())) {
			return new AjaxCallBackBean(false, "请输入有效的密码");
		}
		if (NullValueCheckUtil.isStrEmpty(userEngineDto.getMobile())) {
			return new AjaxCallBackBean(false, "请输入有效的手机号码");
		}
		//保存用户
		userEngineDto.setRegisterIP(req.getRemoteAddr());
		userEngineDto = userEngineSupport.registerUser(userEngineDto);
		if(!userEngineDto.getResult()){
			return new AjaxCallBackBean(false, userEngineDto.getErrorMsg());
		}else{
			//----------------由于SSO存在，不能实现注册后自动登录
			//写入会员登录Session
		//	createSessionObject(req, SessionKey.LOGIN_IDENTIFICATION, userEngineDto);
			//写入会员登录Cookie
		//	WebUtil.addCookie(req, res, CookieKey.COOKIE_LOGIN_NAME, userEngineDto.getLoginID());
		}
		return new AjaxCallBackBean(true);
	}
	
	/**
	*<p>注册协议获取</p>
	* @author larry
	* @datetime 2014-4-11下午05:26:56
	* @return AjaxCallBackBean
	* @throws 
	* @example<br/>
	*
	 */
	@RequestMapping(value="agreement",method=RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean  agreement(){
		AgreementDto agreementDto = agreementSupport.getAgreement(AgreementType.register);//注册协议
		if(agreementDto== null){
			return new AjaxCallBackBean(false,"协议获取失败");
		}
		return new AjaxCallBackBean(true,"获取成功",agreementDto.getContent());
	}
}
