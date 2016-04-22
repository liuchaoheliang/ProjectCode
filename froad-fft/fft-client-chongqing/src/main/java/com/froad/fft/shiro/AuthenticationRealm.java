package com.froad.fft.shiro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.alibaba.fastjson.JSON;
import com.froad.fft.bean.SystemConfig;
import com.froad.fft.bean.SystemConfig.CaptchaType;
import com.froad.fft.dto.SysResourceDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.support.base.CaptchaSupport;
import com.froad.fft.support.base.SysUserSupport;
import com.froad.fft.util.SystemConfigUtil;

/**
 * 权限认证
 * 
 * @author FQ
 * 
 */
public class AuthenticationRealm extends AuthorizingRealm {

	final static Logger logger = Logger.getLogger(AuthenticationRealm.class);

	@Resource
	private CaptchaSupport captchaSupport;
	
	@Resource
	private SysUserSupport sysUserSupport;

	/**
	 * 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		if (principals == null) {
			throw new AuthorizationException(
					"PrincipalCollection method argument cannot be null");
		}

		// 当前登录的用户
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		List<String> authorities = new ArrayList<String>();
		if (principal != null) {
			logger.info("Principal.id: " + principal.getId()+ " Principal.username: " + principal.getUsername());

			List<SysResourceDto> sysRoleResourceDtoList=sysUserSupport.findSysResourceListByUserId(principal.getId());
			for(SysResourceDto sysResourceDto:sysRoleResourceDtoList){
				authorities.add(sysResourceDto.getCode());
			}
		}
		
		if(authorities.size() > 0){
			logger.info("Authorities："+JSON.toJSONString(authorities));
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.addStringPermissions(authorities);
			return info;
		}

		return null;
	}

	/**
	 * 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			org.apache.shiro.authc.AuthenticationToken token)
			throws AuthenticationException {
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword());
		String captchaId = authenticationToken.getCaptchaId();
		String captcha = authenticationToken.getCaptcha();
		String ip = authenticationToken.getHost();

		logger.info("username:" + username + " password:" + password
		+ " captchaId:" + captchaId + " captcha:" + captcha + " ip:"
		+ ip);
		
		// 验证码验证
//		if (!captchaSupport.isValid(CaptchaType.merchantLogin, captchaId, captcha)) {
//			logger.info("验证码验证不通过");
//			throw new UnsupportedTokenException();
//		}
		
		if (username != null && password != null) {
			SysUserDto sysUser = sysUserSupport.findSysUserByUsername(username);

			if (sysUser == null) {
				logger.error(username+" 用户不存在");
				throw new UnknownAccountException();
			}
			
			// 未启用
			if (!sysUser.getIsEnabled()) {
				logger.info(username+" 用户未启用");
				throw new DisabledAccountException();
			}
			
			SystemConfig systemConfig=SystemConfigUtil.getSystemConfig();
			
			// 锁定
			if (sysUser.getIsLocked()) {
				int loginFailureLockTime = systemConfig.getLoginFailureLockTime();
				if (loginFailureLockTime == 0) {
					logger.info(username+" 用户永久锁定");
					throw new LockedAccountException();
				}
				Date lockedDate = sysUser.getLockedDate();
				Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
				if (new Date().after(unlockDate)) {
					sysUser.setLoginFailureCount(0);
					sysUser.setIsLocked(false);
					sysUser.setLockedDate(null);
					sysUserSupport.updateSysUserById(sysUser);
				} else {
					logger.info(username+" 用户锁定中");
					throw new LockedAccountException();
				}
			}
			
			// 密码
			if (!DigestUtils.md5Hex(password).equals(sysUser.getPassword())) {
				logger.info(username+" 用户密码错误");
				int loginFailureCount = sysUser.getLoginFailureCount() + 1;
				if (loginFailureCount >= systemConfig.getLoginFailureLockCount()) {
					sysUser.setIsLocked(true);
					sysUser.setLockedDate(new Date());
				}
				logger.info("登录失败次数：" + loginFailureCount);
				sysUser.setLoginFailureCount(loginFailureCount);
				sysUserSupport.updateSysUserById(sysUser);
				throw new IncorrectCredentialsException();
			}
			sysUser.setLoginIp(ip);
			sysUser.setLoginDate(new Date());
			sysUser.setLoginFailureCount(0);
			sysUserSupport.updateSysUserById(sysUser);
			
			return new SimpleAuthenticationInfo(new Principal(sysUser.getId(),
					username), password, getName());
		}
		throw new UnknownAccountException();
	}

}
