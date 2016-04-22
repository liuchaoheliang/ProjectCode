package com.froad.cbank.coremodule.module.normal.user.controller.code;

import static com.froad.cbank.coremodule.module.normal.user.utils.Constants.SMS_VALID_TIME;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.support.CodeSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.ResultBean;
import com.froad.cbank.coremodule.module.normal.user.utils.SimpleUtils;
import com.froad.cbank.coremodule.module.normal.user.vo.SmsVo;
import com.froad.thrift.vo.SmsTypeEnum;
import com.pay.user.dto.UserSpecDto;

/** 
 * 验证码
 * @ClassName: CodeController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @Create Author: hjz
 * @Create Date: 2015-3-30 上午11:32:53 
 */ 
@Controller
@RequestMapping
public class CodeController extends BasicSpringController {
	
	
	@Resource
	private CodeSupport codeSupport;
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	
	/**
	 * 生成图片验证码
	 * @Title: generateWordImage 
	 * @Project Froad Cbank CoreModule Module Normal User
	 * @Package com.froad.cbank.coremodule.module.normal.user.controller.code
	 * @Description TODO(用一句话描述该类做什么)
	 * @author hjz
	 * @date 2015-3-30 上午11:33:43
	 * @url /code/generate
	 * @method	get
	 */
	@RequestMapping(value = "/code/generate_image", method = RequestMethod.GET)
	public void generateWordImage(HttpServletRequest req, ModelMap model){
		String clientId = getClient(req);
		model.putAll(codeSupport.getImageCode(clientId, req));
	}
	
	
	
	/**
	 * 获取短信验证码 - 会员
	 *@description 
	 *@author Liebert
	 *@date 2015年4月3日 下午5:46:00
	 */
	@Token
	@RequestMapping(value="/code/send_sms_member", method = RequestMethod.POST)
	public void sendSmsMember(HttpServletRequest req, ModelMap model, @RequestHeader Long memberCode, @RequestBody SmsVo smsVo){
		String clientId = getClient(req);
		String ip = getIpAddr(req).split(",")[0];
		
		SmsTypeEnum smsEnumType = SimpleUtils.valueToSmsType(smsVo.getSmsType());
		String sendMobile = smsVo.getMobile();
		String imgCode = smsVo.getImgCode();
		String imgToken = smsVo.getImgToken();

		LogCvt.info(String.format("用户发送短信>> IP:%s, 会员编号：%s， 手机号：%s， 短信类型：%s， 图片验证码：%s, 验证码token：%s", ip, memberCode, sendMobile, smsEnumType, imgCode, imgToken));
		
		boolean codeResult = false;
		UserSpecDto user = null;
		String userName = null;
		
		switch (smsEnumType) {
		case authcodeUpdateLoginPwd:
			//会员修改登录密码
			user = userEngineSupport.queryMemberByMemberCode(memberCode);
			if(user == null){
				LogCvt.info("发送短信失败>> 用户不存在");
				model.put(Results.code, "9999");
				model.put(Results.msg, "用户不存在");
				return;
			}
			if(!user.getIsBindMobile() || StringUtil.isBlank(user.getMobile())){
				LogCvt.info("发送短信失败>> 用户未绑定手机号");
				model.put(Results.code, "9999");
				model.put(Results.msg, "用户未绑定手机号");
				return;
			}
			sendMobile = user.getMobile();
			userName = user.getLoginID();
			
			Monitor.send(MonitorEnums.user_sms_update_loginpwd, "1");
			
			break;
		
		case authUpdatePayPwd:
			//会员修改支付密码
			user = userEngineSupport.queryMemberByMemberCode(memberCode);
			if(user == null){
				LogCvt.info("发送短信失败>> 用户不存在");
				respError(model, "用户不存在");
				return;
			}
			if(!user.getIsBindMobile() || StringUtil.isBlank(user.getMobile())){
				LogCvt.info("发送短信失败>> 用户未绑定手机号");
				model.put(Results.code, "9999");
				model.put(Results.msg, "用户未绑定手机号");
				return;
			}
			sendMobile = user.getMobile();
			userName = user.getLoginID();

			Monitor.send(MonitorEnums.user_sms_update_paypwd, "1");
			
			break;
		
		case bindMobile:
			//用户绑定手机号
			
			Monitor.send(MonitorEnums.user_sms_bind_phone, "1");
			
			break;
			
		case authBackMobile:
			//用户更换手机号
			if(StringUtil.isNotBlank(imgCode) && StringUtil.isNotBlank(imgToken)){
				//修改手机号-验证原手机号
				codeResult = codeSupport.verifyCodeResult(imgCode, imgToken);
				if(!codeResult){
					LogCvt.info("发送短信失败>> 图片验证码错误。");
					respError(model, "图片验证码错误");
					return;
				}
				
				user = userEngineSupport.queryMemberByMemberCode(memberCode);
				if(user == null){
					LogCvt.info("发送短信失败>> 用户不存在");
					respError(model, "用户不存在");
					return;
				}
				if(!user.getIsBindMobile() || StringUtil.isBlank(user.getMobile())){
					LogCvt.info("发送短信失败>> 用户未绑定手机号");
					model.put(Results.code, "9999");
					model.put(Results.msg, "用户未绑定手机号");
					return;
				}
				sendMobile = user.getMobile();
			}
				

			Monitor.send(MonitorEnums.user_sms_change_phone, "1");
			
			break;
			
		case authBackPayPwd:
			//用户找回支付密码
			user = userEngineSupport.queryMemberByMemberCode(memberCode);
			if(user == null){
				LogCvt.info("发送短信失败>> 用户不存在");
				respError(model, "用户不存在");
				return;
			}
			if(!user.getIsBindMobile() || StringUtil.isBlank(user.getMobile())){
				LogCvt.info("发送短信失败>> 用户未绑定手机号");
				model.put(Results.code, "9999");
				model.put(Results.msg, "用户未绑定手机号");
				return;
			}
			sendMobile = user.getMobile();
			userName = user.getLoginID();

			Monitor.send(MonitorEnums.user_sms_find_paypwd, "1");
			
			break;
			
		case fastPaySignCode:
			//支付验证码
			user = userEngineSupport.queryMemberByMemberCode(memberCode);
			if(user == null){
				LogCvt.info("发送短信失败>> 用户不存在");
				respError(model, "用户不存在");
				return;
			}
			if(!user.getIsBindMobile()){
				LogCvt.info("发送短信失败>> 用户未绑定手机号");
				respError(model, "用户未绑定手机号");
				return;
			}
			sendMobile = user.getMobile();
			userName = user.getLoginID();

			Monitor.send(MonitorEnums.user_sms_fastpay_verify, "1");
			
			break;
			
		case bankcoderegister:
			//银行卡签约

			Monitor.send(MonitorEnums.user_sms_sign_bankcard, "1");
			
			break;

		default:
			LogCvt.info("发送短信失败>> 无法识别的短信类型");
			respError(model, "无法识别的短信类型");
			return;
		}
		
		if(StringUtil.isBlank(sendMobile)){
			LogCvt.info("发送短信失败>> 手机号不能为空");
			respError(model,  "手机号不能为空");
			return;
		}
		
		//MemberCode限制
		ResultBean checkResult = codeSupport.validMemberSendSmsRedis(memberCode);
		if(!checkResult.isSuccess()){
			respError(model, checkResult.getMsg());
			return;
		}
		
		Map<String,Object> result = codeSupport.sendSmsMessage(clientId, sendMobile, userName, SMS_VALID_TIME, smsEnumType, ip);
		
		if(StringUtil.isNotBlank(result.get("smsToken"))){
			//发送成功 缓存memberCode发送记录
			codeSupport.setMemberSendSmsRedis(memberCode);
		}
		
		model.putAll(result);

	}
	
	
	
	
	
	/**
	 * 游客请求发短信
	 *@description 
	 *@author Liebert
	 *@date 2015年4月3日 下午5:46:00
	 */
	@RequestMapping(value="/code/send_sms_visitor", method = RequestMethod.POST)
	public void sendSmsVisitor(HttpServletRequest req, ModelMap model, @RequestBody SmsVo smsVo){
		String clientId = getClient(req);
		String ip = getIpAddr(req).split(",")[0];
		SmsTypeEnum smsEnumType = SimpleUtils.valueToSmsType(smsVo.getSmsType());
		String sendMobile = smsVo.getMobile();
		String imgCode = smsVo.getImgCode();
		String imgToken = smsVo.getImgToken();

		LogCvt.info(String.format("用户发送短信>> IP:%s, 手机号：%s， 短信类型：%s， 图片验证码：%s, 验证码token：%s", ip, sendMobile, smsEnumType, imgCode, imgToken));
				
		boolean codeResult = false;
		UserSpecDto user = null;
		String userName = null;
		
		switch (smsEnumType) {
		case registerNewUser:
			//注册,验证图片验证码
			codeResult = codeSupport.verifyCodeResult(imgCode, imgToken);
			if(!codeResult){
				LogCvt.info("发送短信失败>> 图片验证码错误。");
				respError(model, "图片验证码错误");
				return;
			}

			Monitor.send(MonitorEnums.user_sms_register, "1");
			
			break;
			
		case codeforgetPwd:
			//用户找回登录密码
			codeResult = codeSupport.verifyCodeResult(imgCode, imgToken);
			if(!codeResult){
				LogCvt.info("发送短信失败>> 图片验证码错误。");
				respError(model, "图片验证码错误");
				return;
			}
			if(StringUtil.isBlank(sendMobile)){
				LogCvt.info("发送短信失败>> 用户名/手机号不能为空");
				respError(model, "用户名/手机号不能为空");
				return;
			}
			user = userEngineSupport.queryMemberByLoginID(sendMobile);
			if(user == null){
				LogCvt.info("发送短信失败>> 用户不存在");
				respError(model, "用户不存在");
				return;
			}
			if(!user.getIsBindMobile()){
				LogCvt.info("发送短信失败>> 用户未绑定手机号");
				respError(model, "用户未绑定手机号");
				return;
			}
			sendMobile = user.getMobile();
			userName = user.getLoginID();

			Monitor.send(MonitorEnums.user_sms_find_loginpwd, "1");
			
			break;
		
		case UnionLoginBindMobile:
			//联合登陆绑定手机号
			
			Monitor.send(MonitorEnums.user_sms_bind_unionlogin_phone, "1");
			
			break;
		default:
			LogCvt.info("发送短信失败>> 无法识别的短信类型");
			respError(model, "无法识别的短信类型");
			return;
		}
		

		if(StringUtil.isBlank(sendMobile)){
			LogCvt.info("发送短信失败>> 手机号不能为空");
			respError(model, "手机号不能为空");
			return;
		}
		

		//手机号 IP 限制
		ResultBean checkReuslt = codeSupport.validVisitorSendSmsRedis(sendMobile,ip);
		if(!checkReuslt.isSuccess()){
			respError(model, checkReuslt.getMsg());
			return;
		}
		
		Map<String,Object> result = codeSupport.sendSmsMessage(clientId, sendMobile, userName, SMS_VALID_TIME, smsEnumType, ip);
		
		if(StringUtil.isNotBlank(result.get("smsToken"))){
			//发送成功 缓存手机号 IP发送记录
			codeSupport.setVisitorSendSmsRedis(sendMobile, ip);
			
			//忘记密码添加验证手机逻辑，安全中心接口验证
			if(smsEnumType == SmsTypeEnum.codeforgetPwd){
				codeSupport.createMobileSmsRedis(clientId, result.get("smsToken").toString(), sendMobile);
			}
		}
		
		model.putAll(result);
		
	}
	
	/**
	 * 校验验证码
	 *@description 
	 *@author Liebert
	 *@date 2015年4月7日 上午9:33:01
	 */
	@RequestMapping(value = "/code/verify_code", method = RequestMethod.POST)
	public void verifyCode(HttpServletRequest req, ModelMap model, @RequestBody SmsVo smsVo){
		model.putAll(codeSupport.verifyCode(smsVo.getCode(), smsVo.getToken()));

	}
	
	/**
	 * 
	 * verifyCodeAndReturnToken:(校验短信验证码并创建authToken返回给前端做下一步校验).
	 *
	 * @author wufei
	 * 2015-10-28 下午05:45:03
	 * @param req
	 * @param model
	 * @param smsVo
	 *
	 */
	@RequestMapping(value = "/code/verify_code_new", method = RequestMethod.POST)
	public void verifyCodeAndReturnToken(HttpServletRequest req, ModelMap model, @RequestBody SmsVo smsVo){
		String clientId = getClient(req);
		model.putAll(codeSupport.verifyCodeAndReturnToken(smsVo.getCode(), smsVo.getToken(),smsVo.getMobile(),clientId));

	}
	
	
}
