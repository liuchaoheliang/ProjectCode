package com.froad.fft.controller.merchant;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.froad.fft.bean.Message;
import com.froad.fft.bean.SystemConfig;
import com.froad.fft.controller.BaseController;
import com.froad.fft.shiro.AuthenticationToken;
import com.froad.fft.util.SystemConfigUtil;

/**
 * 商户 登录
 * @author FQ
 *
 */

@Controller("merchantLogin")
@RequestMapping("/merchant/login")
public class LoginController extends BaseController {
	
	/**
	 * 登录页面
	 */
	@RequestMapping(value = "/index" ,method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap model) {
		return "/merchant/login/index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody
	Message login(String username, String password, String captchaId,
			String captcha, boolean rememberMe, HttpServletRequest request,
			HttpServletResponse response) {
		
		SystemConfig systemConfig=SystemConfigUtil.getSystemConfig();
		
		Subject subject = SecurityUtils.getSubject();
		String ip = request.getRemoteAddr();

		AuthenticationToken token = new AuthenticationToken(username,password, captchaId, captcha, rememberMe, ip);
		
		try {
			subject.login(token);
			logger.info("Subject SessionTimeout:"+ subject.getSession().getTimeout());
		}
		catch (UnknownSessionException use) {
			subject = new Subject.Builder().buildSubject();
			subject.login(token);
			logger.error("异常会话!");
			return Message.error("异常会话!");
		} catch (UnknownAccountException ex) {
			return Message.error("账号错误!");
		} catch (IncorrectCredentialsException ice) {
			return Message.error("密码错误,若连续"+systemConfig.getLoginFailureLockCount()+"次密码错误账号将被锁定");
		} catch (LockedAccountException lae) {
			return Message.error("账号已锁定，请与系统管理员联系!");
		} catch (DisabledAccountException da){
			return Message.error("账号未启用，请与系统管理员联系!");
		} catch (UnsupportedTokenException e) {
			return Message.error("验证码错误!");
		} catch (AuthenticationException ae) {
			return Message.error("您没有授权!");
		} catch (Exception e) {
			return Message.error("出现未知异常,请与系统管理员联系!");
		}
		
		return SUCCESS_MESSAGE;
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "merchant/login/index";
	}
}
