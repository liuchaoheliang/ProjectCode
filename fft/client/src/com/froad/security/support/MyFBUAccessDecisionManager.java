package com.froad.security.support;

import java.util.Collection;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


	/**
	 * 类描述：权限过虑  判断是否有进入当前页面的权限
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2011 
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-14 下午05:14:50 
	 */
public class MyFBUAccessDecisionManager implements AccessDecisionManager {
	private static final Logger logger=Logger.getLogger(MyFBUAccessDecisionManager.class);

	public void decide(Authentication authentication, Object object,Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			return;
		}
//		System.out.println(object.toString()); // object is a URL.
		logger.info("决策请求路径:["+object.toString()+"]");
			Iterator<ConfigAttribute> ite = configAttributes.iterator();
			while (ite.hasNext()) {
				ConfigAttribute ca = ite.next();
				String needRole = ((SecurityConfig) ca).getAttribute();
				for (GrantedAuthority ga : authentication.getAuthorities()) {
					if (needRole.equals(ga.getAuthority())) { // ga is user's role.
						return;
					}
				}
			}
			throw new AccessDeniedException("您没有访问权限！");	
	}

	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}
