package com.froad.fft.controller.shop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.common.CookieKey;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.support.base.UserEngineSupport;
import com.froad.fft.util.NullValueCheckUtil;
import com.froad.fft.util.WebUtil;

/**
 * 会员登录
 * @author FQ
 *
 */

@Controller
@RequestMapping("/shop/login")
public class LoginController extends BaseController {

	private static Logger logger = Logger.getLogger(LoginController.class);
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	//**登录首页
	@RequestMapping(value = "index")
	public void index(){}
	
	/**
	 * 登录检测
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean validation(HttpServletResponse res ,HttpServletRequest req,UserEngineDto userEngineDto) {
		
		String loginIP = req.getRemoteAddr();
		//操作
		if(NullValueCheckUtil.isStrEmpty(userEngineDto.getLoginID())){
			return new AjaxCallBackBean(false,"请输入帐号");
		}
		if(NullValueCheckUtil.isStrEmpty(userEngineDto.getLoginPwd())){
			return new AjaxCallBackBean(false,"请输入密码");
		}
		userEngineDto = userEngineSupport.login(userEngineDto, loginIP);
		if(userEngineDto.getResult()){
			logger.info("登录成功");
			createSessionObject(req, SessionKey.LOGIN_IDENTIFICATION, userEngineDto);
			createSessionObject(req, SessionKey.SSO_MEMBER_INFO, userEngineDto.getLoginID());
			WebUtil.addCookie(req, res, CookieKey.COOKIE_LOGIN_NAME, userEngineDto.getLoginID());
			return new AjaxCallBackBean(true);
		}else{
			if(NullValueCheckUtil.isStrEmpty(userEngineDto.getDemo())){
				return new AjaxCallBackBean(false,userEngineDto.getErrorMsg());
			}
			JSONObject jsonObject = JSON.parseObject(userEngineDto.getDemo());
			return new AjaxCallBackBean(false,(String)jsonObject.get("msg"));
		}
		
	}
	
	
}
