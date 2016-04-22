package com.froad.security.support;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import com.froad.action.support.UserActionSupport;
import com.froad.client.user.Authorities;
import com.froad.client.user.Resources;
import com.froad.util.ApplicationContextUtil;

/*
 * 
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。
 * 注意，我例子中使用的是AntUrlPathMatcher这个path matcher来检查URL是否与资源定义匹配，
 * 事实上你还要用正则的方式来匹配，或者自己实现一个matcher。
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * 说明：对于方法的spring注入，只能在方法和成员变量里注入，
 * 如果一个类要进行实例化的时候，不能注入对象和操作对象，
 * 所以在构造函数里不能进行操作注入的数据。
 */

@Service
public class MyFBUInvocationSecurityMetadataSourceService  implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private static final Logger logger=Logger.getLogger(MyFBUInvocationSecurityMetadataSourceService.class);
	//private UrlMatcher urlMatcher = new AntUrlPathMatcher();
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private static Collection<ConfigAttribute> allConfigAttribute=null;
//	private static ApplicationContext cont=ApplicationContextUtil.getApplicationContext();
//    private MUserImpl user=(MUserImpl) cont.getBean("MUserImpl");
    private static UserActionSupport USER=(UserActionSupport) ApplicationContextUtil.getApplicationContext().getBean("UserActionSupport");
	public MyFBUInvocationSecurityMetadataSourceService() {
		loadResourceDefine();
	}

	private void loadResourceDefine() {
		logger.info(" 加载权限信息begin...... ");
		//获取权限信息
		List<Authorities> query = USER.queryAuthorities();
		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		allConfigAttribute = new HashSet<ConfigAttribute>();
		
		for (Authorities auth : query) {
			Collection<ConfigAttribute> atts = new HashSet<ConfigAttribute>();
			ConfigAttribute ca = new SecurityConfig(auth.getAuthoritiesName());// "ROLE_ADMIN"
			List<Resources> query1 = USER.queryResourcesByAuthorities(auth.getAuthoritiesId());//角色对应的资源  by goujw

			for (Resources res : query1) {
				String url = res.getResourcesValue();
				// 判断资源文件和权限的对应关系，如果已经存在，要进行增加
				if (resourceMap.containsKey(url)) {
					Collection<ConfigAttribute> value = resourceMap.get(url);
					value.add(ca);
					resourceMap.put(url, value);
					// "log.jsp","role_user,role_admin"
				} else {
					atts.add(ca);
					resourceMap.put(url, atts);
				}
				allConfigAttribute.add(ca);
			}
		}
		
		logger.info(" 加载权限信息end...... ");
	}

	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();
		if(resourceMap==null){
			loadResourceDefine();
		}
		Iterator<String> ite = resourceMap.keySet().iterator();
		while (ite.hasNext()) {
			String resURL = ite.next();
			if (url.contains(resURL)) {
				return resourceMap.get(resURL);
			}
		}
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return allConfigAttribute;
	}
	
	/**
	  * 方法描述：如果后台权限改动，调用该方法刷新内存
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 勾君伟 goujunwei@f-road.com.cn
	  * @time: Apr 13, 2011 2:35:03 PM
	  */
	public static void refesh(){
		logger.info(" 重新加载权限信息...... ");
		resourceMap=null;
	}

}
