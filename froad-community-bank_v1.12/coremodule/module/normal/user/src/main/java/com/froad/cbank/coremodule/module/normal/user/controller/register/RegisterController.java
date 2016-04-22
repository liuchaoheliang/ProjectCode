package com.froad.cbank.coremodule.module.normal.user.controller.register;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.util.web.WebUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.pojo.UserEnginePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.UserLoginPojo;
import com.froad.cbank.coremodule.module.normal.user.support.RedPacketSupport;
import com.froad.cbank.coremodule.module.normal.user.support.SafeSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.cbank.coremodule.module.normal.user.utils.ResultBean;
import com.froad.cbank.coremodule.module.normal.user.utils.SimpleUtils;
import com.pay.user.dto.UserResult;
import com.pay.user.helper.CreateChannel;

@Controller
public class RegisterController extends BasicSpringController{

	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private SafeSupport safeSupport;
	
	@Resource
	private RedPacketSupport redPacketSupport;
	
	/**
	 * 用户注册
	 *@description 
	 *@author Liebert
	 *@date 2015年4月14日 下午6:29:40
	 */
	@Token(createKey = true)
	@RequestMapping(value = "/register/register", method = RequestMethod.POST)
	public void register(HttpServletRequest req, ModelMap model, @RequestBody UserLoginPojo userPojo){
		long startTime = System.currentTimeMillis();
		String ipAddress = WebUtil.getClientIpAddr(req);
		String clientId = getClient(req);
		
		LogCvt.info(String.format("[用户注册] >> 用户名：%s, 注册IP：%s", userPojo.getLoginId(), ipAddress));
		
		if(StringUtil.isBlank(userPojo.getLoginId())){
			respError(model, "用户名不能为空");
			return;
		}
		if(StringUtil.isBlank(userPojo.getLoginPwd())){
			respError(model, "登录密码不能为空");
			return;
		}
		if(StringUtil.isBlank(userPojo.getMobile())){
			respError(model, "手机号不能为空");
			return;
		}

		//校验登录名规则
		if(!userPojo.getLoginId().matches(Constants.LOGIN_ID_REGEX)){
			respError(model, "用户名支持长度为4到20位，汉字、字母、数字及“-”、“_”的组合");
			return;
		}
		
		CreateChannel cc = SimpleUtils.valueToCreateChannel(userPojo.getCreateChannel());
//		String loginPwd = SimpleUtils.decodePwd(userPojo.getLoginPwd(), cc);
//		//校验登录密码是否符合规则
//		ResultBean validRes = safeSupport.validationLoginPwdRule(userPojo.getLoginId(), loginPwd);
//		if(!validRes.isSuccess()){
//			respError(model, validRes.getMsg());
//			return;
//		}
		
		//用户注册校验前置页面的token
		LogCvt.info("[用户注册] >> 校验前置接口Token");
		ResultBean tk =  safeSupport.verifyMobileCodeAuthTokenKey(clientId, userPojo.getMobile(), userPojo.getAuthToken());
		if(!tk.isSuccess()){
			LogCvt.info("[用户注册] >> 校验安全问题Token失败：" + tk.getMsg());
			respError(model, tk.getMsg());
			return;
		}
		String loginPwd = userPojo.getLoginPwd();
		if (CreateChannel.CB_P.equals(cc) || cc == null) {
			LogCvt.info("[密码加密] >> 渠道：PC，登录密码加密");
			loginPwd = userEngineSupport.encryptPwd(loginPwd);
            if (StringUtil.isBlank(loginPwd)) {
            	LogCvt.info("[密码加密] >> 登录密码加密失败");
            	respError(model, "登录密码加密失败");
				return;
			}
        }
		
		UserResult userResult = userEngineSupport.registerMember(userPojo.getLoginId(), loginPwd, userPojo.getMobile(), cc.getValue(), ipAddress, clientId);

		if(userResult.getResult()){
			UserEnginePojo userEngineDto = userEngineSupport.UserSpecToUserEngineDto(userResult.getUserList().get(0),clientId);
			
			LogCvt.info(String.format("[用户注册] >> 注册成功！ 登录名:%s, 注册IP:%s", userEngineDto.getLoginId(), ipAddress));
			
			// 添加注册送红包流程 
			redPacketSupport.registerActive(clientId, userEngineDto.getMemberCode(), userEngineDto.getLoginId());
			
			model.put("token", userResult.getToken());
			model.put("userAcct", userEngineDto);
			
			//注册统计开始
			Monitor.send(MonitorEnums.user_register_statistics, "1");
			Monitor.send(MonitorEnums.user_register_time, Long.toString(System.currentTimeMillis() - startTime));
			//注册统计结束
			
			return;
		}else{
			LogCvt.info(String.format("[用户注册] >> 注册失败！登录名:%s, 结果码:%s, 描述信息:%s", userPojo.getLoginId(), userResult.getMsgCode(), userResult.getErrorMsg()));
			respError(model, userResult.getErrorMsg());
			return;
		}
	}
	
	
	/**
	 * 查询手机号是否已被使用
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 下午3:45:31
	 */
	@RequestMapping(value = "/register/checkMobile", method = RequestMethod.POST)
	public void queryIsMobileUsed(HttpServletRequest req, ModelMap model, @RequestBody UserLoginPojo userPojo){
		if(StringUtil.isBlank(userPojo.getMobile())){
			respError(model, "手机号为空");
			return;
		}
		
		boolean result = userEngineSupport.queryIsMobileUsed(userPojo.getMobile());
		model.put("result",result);
		return;
	}
	
	
	/**
	 * 查询用户名是否已被使用
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 下午4:09:55
	 */
	@RequestMapping(value = "/register/checkLoginId", method = RequestMethod.POST)
	public void queryIsLoginUsed(HttpServletRequest req, ModelMap model, @RequestBody UserLoginPojo userPojo){
		if(StringUtil.isBlank(userPojo.getLoginId())){
			respError(model, "用户名为空");
			return;
		}
		
		boolean result = userEngineSupport.queryIsLoginIdUsed(userPojo.getLoginId());
		model.put("result",result);
		return;
	}
	
}
