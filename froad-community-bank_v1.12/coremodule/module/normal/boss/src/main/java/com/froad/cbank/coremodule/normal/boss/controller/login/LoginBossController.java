package com.froad.cbank.coremodule.normal.boss.controller.login;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Login;
import com.froad.cbank.coremodule.normal.boss.support.LoginBossSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;

/**
 * 类描述：登录相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-7下午3:49:56 
 */

@Controller
@RequestMapping(value = "/login")
public class LoginBossController{
	@Resource
	private LoginBossSupport loginBossSupport;
	
	/**
	  * 方法描述：boss用户登录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: @f-road.com.cn
	  * @time: 2015年5月12日 下午1:44:01
	  */
	@RequestMapping(value="login", method=RequestMethod.POST)
	public void login(ModelMap model,HttpServletRequest request,@RequestBody Login voReq){
		try {	
			OriginVo originVo = new OriginVo();
			originVo.setDescription("");
			originVo.setOperatorIp(Constants.getIpAddr(request));
			originVo.setPlatType(PlatType.boss);
			
			if(StringUtil.isNotBlank(voReq.getLoginName()) && StringUtil.isNotBlank(voReq.getLoginPassword())){
				HashMap<String,Object> resMap = loginBossSupport.loginBoss(voReq,originVo);
				LogCvt.info("BOSS用户登录后台返回参数："+JSON.toJSONString(resMap));
				model.putAll(resMap);
			}else{
				new RespError(model, "用户名密码不能为空!");	
			}
		} catch(BossException e){ 
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
			new RespError(model,"登陆异常");
		}
		
	}
	
	/**
	 * 获取验证码
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "gic", method = RequestMethod.POST)
	public void getIdentifyCode(@RequestBody Login voReq,ModelMap model, HttpServletRequest req) {
		try {
			if(StringUtil.isNotBlank(voReq.getClientId())){
				model.putAll(loginBossSupport.getIdentifyCode(voReq.getClientId()));				
			}else{
				new RespError(model, "银行类型为空!!!");
			}
		}catch(BossException e){ 
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
			new RespError(model,"获取验证码异常");
		}
	}
	
	
	/**
	  * 方法描述：boss用户退出登录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: @f-road.com.cn
	  * @time: 2015年5月12日 下午1:44:01
	  */
	@RequestMapping(value="logout", method=RequestMethod.POST)
	public void logout(@RequestBody Login voReq,HttpServletRequest request,ModelMap model){
		LogCvt.info("BOSS用户退出登录参数："+JSON.toJSONString(voReq));
		try {
					
			if(StringUtil.isNotBlank(voReq.getToken())){
				HashMap<String,Object> resMap = loginBossSupport.logoutBoss(voReq.getToken());
				LogCvt.info("BOSS用户退出登录后台返回参数："+JSON.toJSONString(resMap));
				model.putAll(resMap);
			}else{
				new RespError(model, "token为空!!!");	
			}
		} catch (Exception e) {
			model.put("code", Constants.RESULT_FAIL);
			model.put("message", e.getMessage());
		}
		
	}
}
