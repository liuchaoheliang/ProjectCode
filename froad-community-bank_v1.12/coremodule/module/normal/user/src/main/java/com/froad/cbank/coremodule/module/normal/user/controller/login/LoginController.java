package com.froad.cbank.coremodule.module.normal.user.controller.login;

import java.util.Enumeration;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.enums.ClientChannelEnum;
import com.froad.cbank.coremodule.module.normal.user.enums.CreateSource;
import com.froad.cbank.coremodule.module.normal.user.pojo.UnionLoginPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.UserEnginePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.UserLoginPojo;
import com.froad.cbank.coremodule.module.normal.user.support.CodeSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UnionLoginSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.DateUtils;
import com.froad.cbank.coremodule.module.normal.user.utils.SimpleUtils;
import com.froad.cbank.coremodule.module.normal.user.vo.UnionLoginVo;
import com.pay.pe.helper.ErrorCodeType;
import com.pay.user.dto.Result;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.CreateChannel;

@Controller
public class LoginController extends BasicSpringController{

	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private UnionLoginSupport unionLoginSupport;
	
	@Resource
	private CodeSupport codeSupport;

	
	/**
	 * 登录
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 下午4:58:03
	 */
	@Token(createKey = true)
	@RequestMapping(value = "/login/login", method = RequestMethod.POST)
	public void login(HttpServletRequest req, ModelMap model, @RequestBody UserLoginPojo userLoginDto){
		
		long startTime = System.currentTimeMillis();
		String clientId = getClient(req);
		String loginIP = getIpAddr(req);

		LogCvt.info(String.format("[用户登录] >> 客户端：%s, 用户名:%s, 登录IP:%s", clientId, userLoginDto.getLoginId(),userLoginDto.getRegisterIP()));
		
		if(StringUtil.isBlank(userLoginDto.getLoginId()) || StringUtil.isBlank(userLoginDto.getLoginPwd())){
			respError(model, "用户名/密码不能为空");
			return;
		}
		
		CreateChannel createChannel = SimpleUtils.valueToCreateChannel(userLoginDto.getCreateChannel());
//		String decodeLoginPwd = SimpleUtils.decodePwd(userLoginDto.getLoginPwd(), createChannel);
		String LoginPwd = userLoginDto.getLoginPwd();
		if (CreateChannel.CB_P.equals(createChannel) || createChannel == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，登录密码：加密处理");
            LoginPwd = userEngineSupport.encryptPwd(LoginPwd);
            if (StringUtil.isBlank(LoginPwd)) {
            	LogCvt.info("[密码加密] >> 登录密码加密失败");
            	respError(model, "登录密码加密失败");
				return;
			}
        }
		UserResult userResult = userEngineSupport.loginMember(userLoginDto.getLoginId(), LoginPwd, loginIP, createChannel, clientId);
		
		if(userResult.getResult()){
			UserEnginePojo userEngineDto = userEngineSupport.UserSpecToUserEngineDto(userResult.getUserList().get(0), clientId);
			
			LogCvt.info(String.format("[用户登录] >> 登录成功！  用户名:%s, 登录IP:%s", userEngineDto.getLoginId(), loginIP));
			
			model.put("token",userResult.getToken());
			model.put("userAcct",userEngineDto);
			
			//登录统计
			Monitor.send(MonitorEnums.user_login_statistics, "1");
			Monitor.send(MonitorEnums.user_login_time, Long.toString(System.currentTimeMillis() - startTime));

		}else{
			LogCvt.info(String.format("[用户登录] >> 登录失败！ 用户名：%s, 结果码: %s, 描述信息: %s", userLoginDto.getLoginId(), userResult.getMsgCode(), userResult.getErrorMsg()));
			respError(model, userEngineSupport.getLoginFailureMessage(userResult.getDemo(), userResult.getErrorMsg()));

		}
		
	}
	
	
	
	/**
	 * 
	 * <br>联合登录<br>
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 下午4:58:12
	 */
	@Token(createKey = true)
	@RequestMapping(value = "/login/loginUnion", method = RequestMethod.POST)
	public void loginUnion(HttpServletRequest req, ModelMap model, @RequestBody UnionLoginPojo loginPojo){
		long startTime = System.currentTimeMillis();
		String loginIP = getIpAddr(req);
		String clientId = getClient(req);
		ClientChannelEnum clientChannel = ClientChannelEnum.getClientChannelEnum(clientId);
		
		LogCvt.info(String.format("[联合登陆] >> 客户端:%s  登录IP:%s", clientId, loginIP));
		
		try {
			
			UnionLoginVo loginVo = unionLoginSupport.extractUnionLoginInfo(loginPojo, clientChannel);
			
			if(StringUtil.isNotBlank(loginPojo.getConfirmMobile())){
				//校验新手机号之后重新登录，使用新的手机号
				loginVo.setMobile(loginPojo.getConfirmMobile());
				LogCvt.info("[联合登陆] >> 使用确认的手机号:" + loginVo.getMobile());
			}
			
			Result result = unionLoginSupport.handlerUnionLoginLogic(loginVo, clientChannel, loginIP);
			
			if(result.getResult()){
				//登陆成功流程
				UserEnginePojo userEngineDto = userEngineSupport.UserSpecToUserEngineDto((UserSpecDto)result.getData(),clientId);
				LogCvt.info(String.format("[联合登陆] >> 成功。用户名:%s, 手机号 ：%s, 登录IP：%s", userEngineDto.getLoginId(), userEngineDto.getMobile(), loginIP));
				model.put("token",result.getToken());
				model.put("userAcct",userEngineDto);
				
				//登录统计
				Monitor.send(MonitorEnums.user_login_statistics, "1");
				Monitor.send(MonitorEnums.user_login_time, Long.toString(System.currentTimeMillis() - startTime));
				
			}else{
				//登录失败流程
				if(ErrorCodeType.USER_BANK_ID_NOT_EXIST_BUT_MOBILE_EXIST.getValue().equals(result.getCode())){
					//银行用户ID不存在,但手机号已存在，返回手机号
					LogCvt.info(String.format("[联合登陆] >> 失败。银行用户ID不存在,但手机号已存在;返回被绑定的手机号:%s", loginVo.getMobile()));
					model.put(Results.code, ErrorCodeType.USER_BANK_ID_NOT_EXIST_BUT_MOBILE_EXIST.getValue()); 
					model.put(Results.msg, ErrorCodeType.USER_BANK_ID_NOT_EXIST_BUT_MOBILE_EXIST.getDesc());
					model.put("mobile", loginVo.getMobile());
					
				}else if(ErrorCodeType.USER_MOBILE_IS_BIND_UNION.getValue().equals(result.getCode())){
					//手机号已被绑定
					LogCvt.info(String.format("[联合登陆] >> 失败。手机号已绑定手机银行联合账户; 返回被绑定的手机号:%s", loginVo.getMobile()));
					model.put(Results.code, ErrorCodeType.USER_MOBILE_IS_BIND_UNION.getValue());
					model.put(Results.msg, ErrorCodeType.USER_MOBILE_IS_BIND_UNION.getDesc());
					model.put("mobile", loginVo.getMobile());

				}else{
					model.put(Results.code, result.getCode());
					model.put(Results.msg,result.getMessage());

				}
				
			}
		
		}catch(Exception e){
			LogCvt.error("[用户联合登陆] >> 联合登录异常",e);
			respError(model, "联合登录异常");
			
		}
		
	}
	
	
	
	
	/**
	 * 联合登陆校验手机号
	 *@description 
	 *@author Liebert
	 *@date 2015年4月15日 下午3:02:08
	 */
	@RequestMapping(value = "/login/isExistUnionMobile", method = RequestMethod.POST)
	public void isExistUnionMobile(HttpServletRequest req, ModelMap model, @RequestBody UnionLoginPojo loginPojo){

		if(StringUtil.isBlank(loginPojo.getMobile())){
			respError(model, "手机号为空");
		}else{
			// true 已绑定 / false 未绑定
			Result result = unionLoginSupport.isExistUnionMobile(loginPojo.getMobile());
			model.put(Results.result, result.getResult());
			model.put("errorCode", result.getCode());
			model.put(Results.code, "0000");
		}
		
	}

	
	
	
	
	
	/**
	 * 无手机号联合登陆
	 *@description 
	 *@author Liebert
	 *@date 2015年4月15日 下午3:17:22
	 */
	@Token(createKey = true)
	@RequestMapping(value = "/login/unionLoginExceptMobile", method = RequestMethod.POST)
	public void unionLoginExceptMobile(HttpServletRequest req, ModelMap model, @RequestBody UnionLoginPojo loginPojo){
		long startTime = System.currentTimeMillis();
		String clientId = getClient(req);
		String loginIP = getIpAddr(req);
		ClientChannelEnum clientChannel = ClientChannelEnum.getClientChannelEnum(clientId);
		
		LogCvt.info(String.format("[联合登陆 - 无手机号联合登陆] >> 客户端:%s  登录IP:%s", clientId, loginIP));
		
		try {
			
			UnionLoginVo loginVo = unionLoginSupport.extractUnionLoginInfo(loginPojo, clientChannel);
			
			Result result = unionLoginSupport.unionLoginExceptMobile(loginVo.getUserBankId(), loginVo.getBankOrgNo(), DateUtils.parseDateTime(loginVo.getLoginTime()), loginVo.getCreateChannel(), loginIP);
			
			if(result.getResult()){
				UserEnginePojo userEngineDto = userEngineSupport.UserSpecToUserEngineDto((UserSpecDto)result.getData(),clientId);
				
				LogCvt.info(String.format("[联合登陆 - 无手机号联合登陆] >> 成功！用户名 ：%s，登录IP：%s ", userEngineDto.getLoginId(), loginIP));
				
				model.put("token",result.getToken());
				model.put("userAcct",userEngineDto);

				//登录统计
				Monitor.send(MonitorEnums.user_login_statistics, "1");
				Monitor.send(MonitorEnums.user_login_time, Long.toString(System.currentTimeMillis() - startTime));
				
			}else{
				LogCvt.info(String.format("[联合登陆 - 无手机号联合登陆] >> 失败！结果码: %s，描述信息: %s", result.getCode(), result.getMessage()));
				respError(model, result.getMessage());

			}

		}catch(Exception e){
			LogCvt.error("[联合登陆 - 无手机号联合登陆] >> 联合登录异常",e);
			respError(model, "联合登录异常");
			
		}
		
	}
	
	
	
	
	
	
	
	
	/**
	 * 绑定已存在会员登录
	 *@description 
	 *@author Liebert
	 *@date 2015年4月15日 下午3:27:39
	 */
	@Token(createKey = true)
	@RequestMapping(value = "/login/unionLoginBind", method = RequestMethod.POST)
	public void unionLoginBind(HttpServletRequest req, ModelMap model, @RequestBody UnionLoginPojo loginPojo){
		long startTime = System.currentTimeMillis();
		String clientId = getClient(req);
		String loginIP = getIpAddr(req);
		ClientChannelEnum clientChannel = ClientChannelEnum.getClientChannelEnum(clientId);
		
		LogCvt.info(String.format("[联合登陆 - 绑定已存在会员登录] >> clientId:%s  ip:%s", clientId, loginIP));
		
		try {
			//校验原有账户的密码
//			String loginPwd = SimpleUtils.decodePwd(loginPojo.getLoginPwd(), CreateChannel.CB_P_IPHONE);
			String loginPwd = loginPojo.getLoginPwd();
			UserResult pwdResult = userEngineSupport.verifyUnionAccountLoginPwd(loginPojo.getConfirmMobile(), loginPwd);
			LogCvt.info("[联合登陆 - 绑定已存在会员登录] >> 校验原有账户密码结果："+JSON.toJSONString(pwdResult));
			if(!pwdResult.getResult()){
				respError(model, "原账户密码错误");
				return;
			}
			
			UnionLoginVo loginVo = unionLoginSupport.extractUnionLoginInfo(loginPojo, clientChannel);
			
			Result result = unionLoginSupport.unionLoginBind(loginVo.getUserBankId(), loginVo.getBankOrgNo(), loginPojo.getConfirmMobile(), DateUtils.parseDateTime(loginVo.getLoginTime()), loginVo.getCreateChannel(), loginIP);
			
			if(result.getResult()){
				
				UserEnginePojo userEngineDto = userEngineSupport.UserSpecToUserEngineDto((UserSpecDto)result.getData(),clientId);
				
				LogCvt.info(String.format("[联合登陆 - 绑定已存在会员登录] >> 成功！用户名： %s， 登录IP： %s", userEngineDto.getLoginId(), loginIP));

				model.put("token",result.getToken());
				model.put("userAcct",userEngineDto);
				
				//登录统计
				Monitor.send(MonitorEnums.user_login_statistics, "1");
				Monitor.send(MonitorEnums.user_login_time, Long.toString(System.currentTimeMillis() - startTime));
				
			}else{
				LogCvt.info(String.format("[联合登陆 - 绑定已存在会员登录] >> 失败！绑定手机号：%s，结果码: %s，描述信息: %s", loginPojo.getConfirmMobile(), result.getCode(), result.getMessage()));
				respError(model, result.getMessage());
			}
			
		} catch (Exception e) {
			LogCvt.error("[用户联合登陆] >> 联合登录异常",e);
			respError(model, "联合登录异常");
		}

	}
	
	
	
	
	/**
	 * 解绑已存在会员登录
	 * 最后确定绑定的手机号ConfirmMobile不能为空
	 *@description 
	 *@author Liebert
	 *@date 2015年4月15日 下午3:35:46
	 */
	@Token(createKey = true)
	@RequestMapping(value = "/login/unionLoginUnbind", method = RequestMethod.POST)
	public void unionLoginUnbind(HttpServletRequest req, ModelMap model, @RequestBody UnionLoginPojo loginPojo){
		long startTime = System.currentTimeMillis();
		String clientId = getClient(req);
		String loginIP = getIpAddr(req);
		ClientChannelEnum clientChannel = ClientChannelEnum.getClientChannelEnum(clientId);
		
		LogCvt.info(String.format("[联合登陆 - 解绑已存在会员登录] >> clientId:%s  ip:%s", clientId, loginIP));
		
		try {
			UnionLoginVo loginVo = unionLoginSupport.extractUnionLoginInfo(loginPojo, clientChannel);
			
			Result result = unionLoginSupport.unionLoginUnbind(loginVo.getUserBankId(), loginVo.getBankOrgNo(), loginPojo.getConfirmMobile(), DateUtils.parseDateTime(loginVo.getLoginTime()), loginVo.getCreateChannel(), loginIP);
			
			if(result.getResult()){
				UserEnginePojo userEngineDto = userEngineSupport.UserSpecToUserEngineDto((UserSpecDto)result.getData(),clientId);
				
				LogCvt.info(String.format("[联合登陆 - 解绑已存在会员登录] >> 成功！用户名：%s, 登录IP：%s", userEngineDto.getLoginId(), loginIP));
				
				model.put("token",result.getToken());
				model.put("userAcct",userEngineDto);
				
				//登录统计
				Monitor.send(MonitorEnums.user_login_statistics, "1");
				Monitor.send(MonitorEnums.user_login_time, Long.toString(System.currentTimeMillis() - startTime));

			}else{
				LogCvt.info(String.format("[联合登陆 - 解绑已存在会员登录] >> 失败！绑定手机号：%s, 结果码: %s, 描述信息: %s", loginPojo.getConfirmMobile(), result.getCode(), result.getMessage()));
				respError(model, result.getMessage());

			}
			
		} catch (Exception e) {
			LogCvt.error("[用户联合登陆] >> 联合登录异常",e);
			respError(model, "联合登录异常");
		}
		
		
	}
	
	
	
	
	
	

	/**
	 * 用户退出 - 注销token、清除客户端cookie
	 *@description 
	 *@author Liebert
	 *@date 2015年4月15日 下午3:01:17
	 */
	@Token
	@RequestMapping(value = "/logout/logout", method = RequestMethod.POST)
	public ModelAndView memberLogout(HttpServletRequest req, HttpServletResponse res, @RequestHeader Long memberCode){
		ModelAndView model = new ModelAndView();
		
		HttpSession session = req.getSession(false);
		Enumeration<String> sessionKeys = session.getAttributeNames();
		while(sessionKeys.hasMoreElements()){
			session.removeAttribute(sessionKeys.nextElement());
		}
		
		boolean result = userEngineSupport.invalidateToken(req.getHeader("token"));
		
		if(result){
			//清除cookie
			userEngineSupport.clearUbankTokenCookie(req, res);
			
			LogCvt.info("[退出登陆] >> 成功：用户"+memberCode);
			
			model.addObject(Results.code, "0000");
			model.addObject(Results.msg, "退出成功");
			return model;
		}else{
			LogCvt.info("[退出登陆] >> 失败：用户"+memberCode);
			model.addObject(Results.code, "9999");
			model.addObject(Results.msg, "退出失败：用户Token注销失败");
			return model;
		}
	}
	
	
	
}
