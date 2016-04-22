package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.MD5Util;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.ResultCode;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.cbank.coremodule.module.normal.user.utils.RedisKeys;
import com.froad.cbank.coremodule.module.normal.user.utils.ResultBean;
import com.froad.cbank.expand.redis.RedisManager;
import com.froad.thrift.vo.SmsTypeEnum;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;


/**
 * 验证码相关解接口支持
 * @description 
 * @author Liebert
 * @date 2015年4月7日 上午10:36:01
 *
 */
@Service
public class CodeSupport extends BaseSupport {

	@Resource
	private SmsMessageService.Iface smsMessageService;
	@Resource
	private RedisManager redisManager;
	
	/**
	 * 发送手机短信验证码
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 下午8:34:06
	 */
	public Map<String, Object> sendSmsMessage(String clientId, String mobile, String sendUser, Integer validTime, SmsTypeEnum smsType, String ip){
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		SmsMessageVo smsVo = new SmsMessageVo();
		smsVo.setClientId(clientId);
		smsVo.setSendUser(sendUser);
		smsVo.setMobile(mobile);
		smsVo.setSendIp(ip);
		smsVo.setSmsType(smsType.getValue());
		smsVo.setValidTime(validTime);
		
		SmsMessageResponseVo smsRes = new SmsMessageResponseVo();
		try {
			smsRes = smsMessageService.sendMobleTokenSMS(smsVo);
		} catch (TException e) {
			LogCvt.error("短信验证码发送接口异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "短信验证码发送异常");
			return resMap;
		}

		if(Constants.RESULT_CODE_SUCCESS.equals(smsRes.getResultCode())){
			resMap.put("smsToken", smsRes.getToken());
		}else{
			LogCvt.info(String.format("短信发送失败>> 结果码：%s, 结果描述：%s", smsRes.getResultCode(), smsRes.getResultDesc()));
			resMap.put(Results.code, smsRes.getResultCode());
			resMap.put(Results.msg, smsRes.getResultDesc());
		}
		return resMap;
	}
	
	
	/**
	 * 生成图片验证码
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 下午8:34:21
	 */
	public Map<String, Object> getImageCode(String clientId, HttpServletRequest req){
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		SmsMessageVo smsVo = new SmsMessageVo();
		smsVo.setClientId(clientId);
		smsVo.setSmsType(SmsTypeEnum.image.getValue());
		
		SmsMessageResponseVo smsRes = null;
		try {
			smsRes = smsMessageService.createImgage(smsVo);
		} catch (TException e) {
			LogCvt.error("图片验证码生成接口异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "图片验证码生成异常");
			return resMap;
			
		}

		if(Constants.RESULT_CODE_SUCCESS.equals(smsRes.getResultCode())){
			
			resMap.put("url", smsRes.getUrl());
			resMap.put("imgToken", smsRes.getToken());
		}else{
			LogCvt.info(String.format("图片验证码生成失败>> 结果码：%s, 结果描述：%s", smsRes.getResultCode(), smsRes.getResultDesc()));
			resMap.put(Results.code, smsRes.getResultCode());
			resMap.put(Results.msg, smsRes.getResultDesc());
		}
		
		return resMap;
	}
	
	
	/**
	 * 校验验证码
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 下午8:34:39
	 */
	public Map<String,Object> verifyCode(String code, String token){
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		if(StringUtil.isBlank(code)){
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "验证码不能为空");
			return resMap;
		}
		if(StringUtil.isBlank(token)){
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "验证码token不能为空");
			return resMap;
		}
		
		ResultVo result = null;
		try {
			result = smsMessageService.verifyMobileToken(token, code);
		} catch (TException e) {
			LogCvt.error("校验验证码接口异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "校验验证码异常");
			return resMap;
			
		}

		resMap.put(Results.code, result.getResultCode());
		resMap.put(Results.msg, result.getResultDesc());
		
		return resMap;
	}
	
	/**
	 * 
	 * verifyCodeAndReturnToken:(校验验证码并返回token).
	 *
	 * @author wufei
	 * 2015-10-28 下午05:13:45
	 * @param code
	 * @param token
	 * @return
	 *
	 */
	public Map<String,Object> verifyCodeAndReturnToken(String code, String token,String mobile,String clientId){
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		if(StringUtil.isBlank(code)){
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "验证码不能为空");
			return resMap;
		}
		if(StringUtil.isBlank(token)){
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "验证码token不能为空");
			return resMap;
		}
		
		ResultVo result = null;
		try {
			result = smsMessageService.verifyMobileToken(token, code);
		} catch (TException e) {
			LogCvt.error("校验验证码接口异常",e);
			resMap.put(Results.code, "9999");
			resMap.put(Results.msg, "校验验证码异常");
			return resMap;
			
		}
		
		//创建authtoken返回给前端
		String authToken = createRegistAuthToken(mobile, clientId);
		resMap.put("authToken", authToken);

		resMap.put(Results.code, result.getResultCode());
		resMap.put(Results.msg, result.getResultDesc());
		
		return resMap;
	}


	/**
	 * 创建校验短信验证码通过授权的令牌</br>
	 * 存入redis</br>
	 * 返回前端作下一步接口检验使用</br>
	 * @author wufei
	 * 2015-10-28 下午05:42:23
	 * @param mobile
	 * @param clientId
	 * @return
	 *
	 */
	private String createRegistAuthToken(String mobile, String clientId) {
		String key = RedisKeys.user_regist_verify_mobilecode_success(clientId, mobile);
		String authToken = MD5Util.getMD5Str(mobile + "createAuthToken" + System.nanoTime());
		String res = redisManager.putExpire(key, authToken, 60*30);//30分钟过期
		if(!"OK".equals(res)){
			LogCvt.error("创建token redis失败 >> ========= redis缓存数据失败 ==========");
		}
		return authToken;
	}
	
	
	
	/**
	 * 验证验证码 - 内部使用，不直接返回对外接口
	 *@description 
	 *@author Liebert
	 *@date 2015年4月27日 下午5:05:22
	 */
	public boolean verifyCodeResult(String code, String token){

		if(StringUtil.isBlank(code) || StringUtil.isBlank(token)){
			LogCvt.info(String.format("验证验证码失败>>缺少必要参数：code：%s, token:%s", code, token));
			return false;
		}
		
		ResultVo result = new ResultVo(ResultCode.failed.getCode(),ResultCode.failed.getMsg());
		try {
			result = smsMessageService.verifyMobileToken(token, code);
		} catch (TException e) {
			LogCvt.error("校验验证码接口异常",e);
			result.setResultCode("9999");
			result.setResultDesc("校验验证码异常");
			return false;
			
		}

		if(Constants.RESULT_CODE_SUCCESS.equals(result.getResultCode())){
			return true;
		}else{
			LogCvt.info(String.format("验证验证码失败>>：ResultCode：%s, ResultDesc:%s", result.getResultCode(), result.getResultDesc()));
			return false;
		}
		
		
	}
	
	
	
	
	/**
	 * 
	 * @Description: 登录密码错误超过次数发送短信
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月21日 下午6:55:09
	 * @return
	 */
	public boolean sendLoginPwdWarningSMS(String clientId, String loginId, String mobile, String ip){

		SmsMessageVo smsVo = new SmsMessageVo();
		smsVo.setClientId(clientId);
		smsVo.setSendUser(loginId);
		smsVo.setMobile(mobile);
		smsVo.setSendIp(ip);
		smsVo.setSmsType(SmsTypeEnum.pwdErrorLogin.getValue());
		
		SmsMessageResponseVo smsRes = new SmsMessageResponseVo();
		try {
			smsRes = smsMessageService.sendMobleTokenSMS(smsVo);
		} catch (TException e) {
			LogCvt.error("短信验证码发送接口异常",e);
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(smsRes.getResultCode())){
			return true;
		}else{
			LogCvt.info(String.format("短信发送失败>> 结果码：%s, 结果描述：%s", smsRes.getResultCode(), smsRes.getResultDesc()));
			return false;
		}

	}
	
	/**
	 * 支付密码错误超过次数发送短信
	 * @Description: TODO
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月21日 下午7:12:02
	 * @param clientId
	 * @param loginId
	 * @param mobile
	 * @param ip
	 * @return
	 */
	public boolean  sendPayPwdWarningSMS(String clientId, String loginId, String mobile, String ip){
		SmsMessageVo smsVo = new SmsMessageVo();
		smsVo.setClientId(clientId);
		smsVo.setSendUser(loginId);
		smsVo.setMobile(mobile);
		smsVo.setSendIp(ip);
		smsVo.setSmsType(SmsTypeEnum.pwdErrorPay.getValue());
		
		SmsMessageResponseVo smsRes = new SmsMessageResponseVo();
		try {
			smsRes = smsMessageService.sendMobleTokenSMS(smsVo);
		} catch (TException e) {
			LogCvt.error("短信验证码发送接口异常",e);
		}
		
		if(Constants.RESULT_CODE_SUCCESS.equals(smsRes.getResultCode())){
			return true;
		}else{
			LogCvt.info(String.format("短信发送失败>> 结果码：%s, 结果描述：%s", smsRes.getResultCode(), smsRes.getResultDesc()));
			return false;
		}
	}
	
	
	

	
	/**
	 * 缓存发送短信手机号记录
	 */
	public boolean createMobileSmsRedis(String clientId, String smsToken, String mobile){
		String key = RedisKeys.user_send_mobile_sms(clientId, smsToken);
		String result = redisManager.putExpire(key, mobile, 60*30);//30分钟过期
		if(!"OK".equals(result)){
			LogCvt.error("创建token redis失败 >> ========= redis缓存数据失败 ========= " + key);
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * 验证短信手机号
	 * @param memberMobile 需要判断与缓存中是否相同的手机号
	 */
	public ResultBean validMemberMobileSmsRedis(String clientId, String smsToken, String memberMobile){
		String key = RedisKeys.user_send_mobile_sms(clientId, smsToken);
		String redisMobile = redisManager.getString(key);
		if(StringUtil.isBlank(redisMobile)){
			LogCvt.info("验证短信手机号失败>> 没有匹配的短信token：" + smsToken);
			return new ResultBean(false, "短信token错误");
		}else if(!memberMobile.equals(redisMobile)){
			LogCvt.info(String.format("验证短信手机号失败>> 手机号不匹配：redisMobile:%s, memberMobile:%s", redisMobile, memberMobile));
			return new ResultBean(false, "验证短信与会员手机号不匹配");
		}
		return new ResultBean(true, "验证短信手机号成功");
	}
	
	
	/**
	 * 缓存会员发送短信记录
	 */
	public boolean setMemberSendSmsRedis(Long memberCode){
		long count = 0;
		//memberCode记录+1  3分钟过期
		String memberKey = RedisKeys.user_member_send_sms(memberCode);
		count = redisManager.incrBy(memberKey, 1L);
		redisManager.expire(memberKey, 180);
		if(count < 1){
			LogCvt.error("创建redis失败 >> ========= redis缓存短信记录数据失败 ========= "+memberKey);
			return false;
		}
		
		return true;
	}
	

	/**
	 * 验证会员发送短信记录
	 * <p>当前规则 每个memberCode 3次/3min
	 */
	public ResultBean validMemberSendSmsRedis(Long memberCode){
		String memberKey = RedisKeys.user_member_send_sms(memberCode);
		String memberCount = redisManager.getString(memberKey);
		if(memberCount != null && Integer.parseInt(memberCount) >= 3){
			return new ResultBean(false, "发送短信较为频繁，请稍候重试");
		}
		
		return new ResultBean(true,"校验通过");
	} 
	
	
	/**
	 * 缓存游客发送短信记录
	 */
	public boolean setVisitorSendSmsRedis(String mobile, String ip){
		long count = 0;
		//手机号记录+1  3分钟过期
		String mobileKey = RedisKeys.user_visitor_send_sms_mobile(mobile);
		count = redisManager.incrBy(mobileKey, 1L);
		redisManager.expire(mobileKey, 180);
		if(count < 1){
			LogCvt.error("创建redis失败 >> ========= redis缓存短信记录数据失败 ========= "+mobileKey);
			return false;
		}
		
		
		//IP记录+1 5分钟过期
		String ipKey = RedisKeys.user_visitor_send_sms_ip(ip);
		count = redisManager.incrBy(ipKey, 1L);
		redisManager.expire(ipKey, 300);
		if(count < 1){
			LogCvt.error("创建redis失败 >> ========= redis缓存短信记录数据失败 ========= "+ipKey);
			return false;
		}
		return true;
	} 
	
	
	/**
	 * 验证游客手机号，IP发送短信记录
	 * <p>当前规则 每个手机号 3次/3min
	 * <p>每个IP 100次/5min
	 */
	public ResultBean validVisitorSendSmsRedis(String mobile, String ip){
		String mobileKey = RedisKeys.user_visitor_send_sms_mobile(mobile);
		String mobileCount = redisManager.getString(mobileKey);
		if(mobileCount != null && Integer.parseInt(mobileCount) >= 3){
			return new ResultBean(false, "发送短信较为频繁，请稍候重试");
		}
		
		//IP记录+1 5分钟过期
		String ipKey = RedisKeys.user_visitor_send_sms_ip(ip);
		String ipCount = redisManager.getString(ipKey);
		if(ipCount != null && Integer.parseInt(ipCount) >= 100){
			return new ResultBean(false, "发送短信较为频繁，请稍候重试");
		}
		
		return new ResultBean(true,"校验通过");
	} 
	
	
}
