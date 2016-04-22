package com.froad.security.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.froad.action.support.UserActionSupport;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.Authorities;
import com.froad.util.ApplicationContextUtil;


/**
 * 类描述：读取用户 数据库信息 与登录信息比对
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2011 
 * @author: 何庆均 heqingjun@f-road.com.cn
 * @update:
 * @time: 2011-8-14 下午05:17:35 
 */
@Service
public class MyFBUUserDetailService implements UserDetailsService {
	private static final Logger logger=Logger.getLogger(MyFBUUserDetailService.class);
	@Autowired
    private static UserActionSupport USER=(UserActionSupport) ApplicationContextUtil.getApplicationContext().getBean("UserActionSupport");
	@Autowired
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException, DataAccessException {
//		JSONObject jObject = JSONObject.fromObject(username);
//		username = jObject.getString(MallCommand.AUTO_LOGIN_NAME);
		// 取得用户的密码
		com.froad.client.user.User user=new com.froad.client.user.User();
		user.setUsername(username);
		try {
			user = USER.getUserInfoByUsername(username);
			if(!"0".equals(user.getRespCode())){
				String message = "用户 "+username+" 不存在";  
				logger.error(message);  
				throw new BadCredentialsException(message);
				//throw new UsernameNotFoundException(message); 
			}
		} catch (AppException_Exception e) {
			e.printStackTrace();
		}
		
		String password = user.getPassword();
//		if(jObject.getBoolean(MallCommand.AUTO_LOGIN_FLAG)){
//			password = new Md5PasswordEncoder().encodePassword(MallCommand.AUTO_LOGIN_JUDGE_MARK_KEY, username);
//		}
		
		boolean isEnabled=user.getStatus().equals("1")? true :false;

		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		// 取得用户的权限
		List<Authorities> auth = USER.queryAuthoritiesByUsername(username);
		
		//使用户拥有所有权限
		//List<Authorities> auth = USER.queryAuthorities();
		
		for(Authorities subau:auth){
			auths.add(new GrantedAuthorityImpl(subau.getAuthoritiesName()));
		}

		User userRes = new User(username, password, isEnabled, true, true, true, auths);
		logger.debug("用户：["+username+"]拥有角色："+auths.toString());
		logger.debug("验证用户         loadUserByUsername(String username) - end");
		return userRes;
	}

}
