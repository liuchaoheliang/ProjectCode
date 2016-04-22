package com.froad.fft.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.froad.fft.bean.SystemConfig;
import com.froad.fft.common.CookieKey;

/**
 * web 工具类
 * @author FQ
 *
 */
public class WebUtil {
	
	/**
	 * 添加cookie
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name cookie名称
	 * @param value cookie值
	 * @param maxAge 有效期(单位: 秒)
	 * @param path 路径
	 * @param domain 域
	 * @param secure 是否启用加密
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			value = URLEncoder.encode(value, "UTF-8");
			Cookie cookie = new Cookie(name, value);
			if (maxAge != null) {
				cookie.setMaxAge(maxAge);
			}
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			if (secure != null) {
				cookie.setSecure(secure);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加cookie
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name cookie名称
	 * @param value cookie值
	 * @param maxAge 有效期(单位: 秒)
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		addCookie(request, response, name, value, maxAge, systemConfig.getCookiePath(), systemConfig.getCookieDomain(), null);
	}

	/**
	 * 添加cookie
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name cookie名称
	 * @param value cookie值
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, CookieKey cookieKey, String value) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		addCookie(request, response, cookieKey.key(), value, null, systemConfig.getCookiePath(), systemConfig.getCookieDomain(), null);
	}
	
	/**
	 * 添加cookie
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name cookie名称
	 * @param value cookie值
	 */
	public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		addCookie(request, response, name, value, null, systemConfig.getCookiePath(), systemConfig.getCookieDomain(), null);
	}

	/**
	 * 获取cookie
	 * 
	 * @param request HttpServletRequest
	 * @param name cookie名称
	 * @return 若不存在则返回null
	 */
	public static String getCookie(HttpServletRequest request,CookieKey cookieKey) {
		String cookiesName = cookieKey.key();
		Assert.notNull(request);
		Assert.hasText(cookiesName);		
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				cookiesName = URLEncoder.encode(cookiesName, "UTF-8");
				for (Cookie cookie : cookies) {
					if (cookiesName.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取cookie
	 * 
	 * @param request HttpServletRequest
	 * @param name cookie名称
	 * @return 若不存在则返回null
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request);
		Assert.hasText(name);
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			try {
				name = URLEncoder.encode(name, "UTF-8");
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						return URLDecoder.decode(cookie.getValue(), "UTF-8");
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 移除cookie
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name cookie名称
	 * @param path 路径
	 * @param domain 域
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path, String domain) {
		Assert.notNull(request);
		Assert.notNull(response);
		Assert.hasText(name);
		try {
			name = URLEncoder.encode(name, "UTF-8");
			Cookie cookie = new Cookie(name, null);
			cookie.setMaxAge(0);
			if (StringUtils.isNotEmpty(path)) {
				cookie.setPath(path);
			}
			if (StringUtils.isNotEmpty(domain)) {
				cookie.setDomain(domain);
			}
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 移除cookie
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name cookie名称
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, CookieKey cookieKey) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		removeCookie(request, response, cookieKey.key(), systemConfig.getCookiePath(), systemConfig.getCookieDomain());
	}
	
	/**
	 * 移除cookie
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param name cookie名称
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		SystemConfig systemConfig = SystemConfigUtil.getSystemConfig();
		removeCookie(request, response, name, systemConfig.getCookiePath(), systemConfig.getCookieDomain());
	}
	
}
