package com.froad.cbank.coremodule.normal.boss.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.enums.Platform;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.BossUser;
import com.froad.cbank.coremodule.normal.boss.pojo.Login;
import com.froad.cbank.coremodule.normal.boss.pojo.LoginResource;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.thrift.service.BossFFTUserService;
import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.service.FinityRoleService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.thrift.vo.bossfftuser.BossFFTUserBaseVoRes;
import com.froad.thrift.vo.bossfftuser.BossFFTUserLoginVoRes;
import com.froad.thrift.vo.bossfftuser.BossFFTUserVo;
import com.froad.thrift.vo.finity.FinityResourceVo;
import com.froad.thrift.vo.finity.FinityRoleListVoRes;
import com.froad.thrift.vo.finity.FinityRoleVo;

/**
 * 类描述：boss登录相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-7下午3:56:21 
 */
@Service
public class LoginBossSupport {
	
	@Resource
	SmsMessageService.Iface smsMessageService;
	
	@Resource
	BossFFTUserService.Iface bossFFTUserService;
	
	@Resource
	FinityResourceService.Iface finityResourceService;
	
	@Resource
	FinityRoleService.Iface finityRoleService;
	
	/**
	 * 登录验证
	 * @param loginName
	 * @param loginPassword
	 * @param identifyCode
	 * @return
	 * @throws OperatorException 
	 * @throws TException 
	 */
	public HashMap<String,Object> loginBoss(Login req,OriginVo originVo) throws Exception{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		if(req.getFailNumber() != 0 && req.getFailNumber() > 2){	//图片验证码
			ResultVo res = smsMessageService.verifyMobileToken(req.getToken(), req.getCode());
			if(!Constants.RESULT_SUCCESS.equals(res.getResultCode())){
				reMap.put("code", res.getResultCode());
				reMap.put("message", res.getResultDesc());
				return reMap;
			}
		}
		
		//登录
		BossFFTUserLoginVoRes res = bossFFTUserService.loginFFTUser(originVo, req.getLoginName(), req.getLoginPassword());
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			BossFFTUserVo bossUser = res.getBossFFTUserVo();
			
			reMap.put("userName", bossUser.getUsername());
			reMap.put("mobile", bossUser.getMobile());
			reMap.put("userId", bossUser.getId());
			reMap.put("email", bossUser.getEmail());
			reMap.put("token", res.getToken());			
			reMap.put("code", res.getResultVo().getResultCode());
			reMap.put("message", res.getResultVo().getResultDesc());
			
			//查询角色
			List<Long> roles = new ArrayList<Long>();
			boolean isAdmin = false;
			FinityRoleListVoRes roleRes = finityRoleService.getFinityRoleByUserIdLogin(bossUser.getId());
			if(Constants.RESULT_SUCCESS.equals(roleRes.getResultVo().getResultCode())){
				List<FinityRoleVo> list = roleRes.getVoList();
				if(list.size()>0){
					for(FinityRoleVo v : list){
						if(!isAdmin && v.isIsAdmin()){
							isAdmin = true;
						}
						roles.add(v.getId());
					}
				}else{
					throw new BossException("用户角色不存在!");
				}
			}else{
				throw new BossException(roleRes.getResultVo().getResultCode(),roleRes.getResultVo().getResultDesc());
			}
			
			reMap.put("isAdmin", isAdmin ? "1" :"0");
			
			//查询角色资源
			FinityResourceVo vo = new FinityResourceVo();
			vo.setPlatform(Platform.boss.getCode());
			List<FinityResourceVo> resources = finityResourceService.getFinityResourceByRole(vo, roles);
			if(resources !=null && resources.size()>0){
				//keys
				HashMap<String,String> keys = new HashMap<String,String>();
				//菜单
				List<LoginResource> list = new ArrayList<LoginResource>();
				
				for(FinityResourceVo r : resources){
					keys.put(r.getResourceKey(), r.getId()+"");
					if(r.getType()!=2 || !r.isMenu){ //非菜单跳过
						continue;
					}
					LoginResource lr = new LoginResource();
					lr.setId(r.getId());
					lr.setPid(r.getParentResourceId());
					lr.setName(r.getResourceName());
					lr.setUrl(r.getResourceUrl());
					lr.setIcon(r.getResourceIcon());
					list.add(lr);
				}
				
				//菜单整理
				List<LoginResource> menuList = new ArrayList<LoginResource>();
				for(LoginResource v : list){
					for(LoginResource v1 : list){
						if(v1.getPid().longValue()==v.getId().longValue()){
							if(v.getSublist()==null){
								List<LoginResource> l = new ArrayList<LoginResource>();
								l.add(v1);
								v.setSublist(l);
							}else{
								v.getSublist().add(v1);
							}
						}
					}
					if(v.getSublist()!=null){
						menuList.add(v);
					}
				}
				reMap.put("menuList", menuList);
				reMap.put("keys", keys);
				
			}else{
				throw new BossException("用户无权限资源!");
			}
		}else{
			reMap.put("code", res.getResultVo().getResultCode());
			reMap.put("failNumber", res.getLoginFailureCount());
			reMap.put("message", res.getResultVo().getResultDesc());
		}
		return reMap;
	}
	
	
	/**
	 * 获取验证码
	 * @return
	 * @throws TException
	 * @throws BankException 
	 */
	public HashMap<String,Object> getIdentifyCode(String clientId) throws Exception{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		SmsMessageVo smVo = new SmsMessageVo();
		smVo.setSmsType(-1);
		smVo.setClientId(clientId);
		SmsMessageResponseVo res = smsMessageService.createImgage(smVo);
		
		if(Constants.RESULT_SUCCESS.equals(res.getResultCode())){
			reMap.put("codeUrl", res.getUrl());
			reMap.put("token", res.getToken());
		}else{
			throw new BossException(res.getResultCode(),res.getResultDesc());
		}
		return reMap;
	}
	
	/**
	 * 用户退出
	 * @param token
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> logoutBoss(String token) throws TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		ResultVo res = bossFFTUserService.logoutFFTUser(token);
		if(Constants.RESULT_SUCCESS.equals(res.getResultCode())){
			reMap.put("code", Constants.RESULT_SUCCESS);
			reMap.put("message","退出登录成功");
		}else{
			reMap.put("code", Constants.RESULT_FAIL);
			reMap.put("message","退出登录失败");			
		}
		return reMap;
	}
	
	
	/**
	 * token验证
	 * @tilte tokenCheck
	 * @author zxl
	 * @date 2015年6月26日 上午11:30:54
	 * @param request
	 * @param token
	 * @param userId
	 * @throws Exception
	 */
	public void tokenCheck(HttpServletRequest request,String token,Long userId) throws Exception{
		if(token == null || userId == null){
			throw new BossException(ErrorEnums.timeout);
		}
		BossFFTUserBaseVoRes bossUserChenkRes = bossFFTUserService.tokenCheck(userId,token);
		if(Constants.RESULT_SUCCESS.equals(bossUserChenkRes.getResultVo().getResultCode())){
			BossFFTUserVo bossUserVo = bossUserChenkRes.getBossFFTUserVo();
			BossUser user = new BossUser();
			BeanUtils.copyProperties(user, bossUserVo);
			request.setAttribute(Constants.BOSS_USER, user);
			
			OriginVo originVo = new OriginVo();
			originVo.setDescription("");
			originVo.setOperatorId(userId);
			originVo.setOperatorIp(Constants.getIpAddr(request));
			originVo.setPlatType(PlatType.boss);
			
			request.setAttribute(Constants.ORIGIN,originVo);
		}else{
			throw new BossException(bossUserChenkRes.getResultVo().getResultCode(),bossUserChenkRes.getResultVo().getResultDesc());
		}
	}
}
