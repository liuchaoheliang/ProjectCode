package com.froad.fft.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.froad.fft.shiro.AuthenticationToken;

/**
 * 权限
 * @author FQ
 *
 */
public class AuthenticationFilter extends FormAuthenticationFilter {
	
	final static Logger logger = Logger.getLogger(AuthenticationFilter.class);
	
	private static final String DEFAULT_CAPTCHA_ID_PARAM = "captchaId";//默认"验证ID"参数名称
	private static final String DEFAULT_CAPTCHA_PARAM = "captcha";//默认"验证码"参数名称

	private String captchaIdParam = DEFAULT_CAPTCHA_ID_PARAM;//"验证ID"参数名称
	private String captchaParam = DEFAULT_CAPTCHA_PARAM;//"验证码"参数名称
	
	/**
	 * 创建 Token
	 */
	@Override
	protected org.apache.shiro.authc.AuthenticationToken createToken(ServletRequest servletRequest,
			ServletResponse servletResponse) {

		String username = getUsername(servletRequest);
		String password = getPassword(servletRequest);
		String captchaId = getCaptchaId(servletRequest);
		String captcha = getCaptcha(servletRequest);
		boolean rememberMe = isRememberMe(servletRequest);
		String host = getHost(servletRequest);
		
		return new AuthenticationToken(username, password, captchaId, captcha, rememberMe, host);
	}
	
	/**
	 * 获取验证ID
	 * 
	 * @param servletRequest ServletRequest
	 * @return 验证ID
	 */
	protected String getCaptchaId(ServletRequest servletRequest) {
		String captchaId = WebUtils.getCleanParam(servletRequest, captchaIdParam);
		if (captchaId == null) {
			captchaId = ((HttpServletRequest) servletRequest).getSession().getId();
		}
		return captchaId;
	}
	
	/**
	 * 获取验证码
	 * 
	 * @param servletRequest ServletRequest
	 * @return 验证码
	 */
	protected String getCaptcha(ServletRequest servletRequest) {
		return WebUtils.getCleanParam(servletRequest, captchaParam);
	}
	
	public String getCaptchaIdParam() {
		return captchaIdParam;
	}

	public void setCaptchaIdParam(String captchaIdParam) {
		this.captchaIdParam = captchaIdParam;
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}
}
