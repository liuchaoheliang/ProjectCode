package com.froad.cbank.coremodule.module.normal.user.controller.safe;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.DESUtil;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpUtil;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.enums.ClientChannelEnum;
import com.froad.cbank.coremodule.module.normal.user.enums.CreateSource;
import com.froad.cbank.coremodule.module.normal.user.enums.ResultCode;
import com.froad.cbank.coremodule.module.normal.user.pojo.BankCardPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.SafeAppealPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.UserSafePojo;
import com.froad.cbank.coremodule.module.normal.user.support.BankCardSupport;
import com.froad.cbank.coremodule.module.normal.user.support.CodeSupport;
import com.froad.cbank.coremodule.module.normal.user.support.SafeSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.cbank.coremodule.module.normal.user.utils.DateUtils;
import com.froad.cbank.coremodule.module.normal.user.utils.FileUtil;
import com.froad.cbank.coremodule.module.normal.user.utils.RedisKeys;
import com.froad.cbank.coremodule.module.normal.user.utils.ResultBean;
import com.froad.cbank.coremodule.module.normal.user.utils.SensEncryptUtil;
import com.froad.cbank.coremodule.module.normal.user.utils.SimpleUtils;
import com.froad.cbank.expand.redis.RedisManager;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.ClientChannel;

/**
 * @author luwanquan@f-road.com.cn
 * @date 创建时间：2015年3月24日 下午4:24:26
 * @version 1.0
 * @desc 安全中心类
 */
@Controller
@RequestMapping(value = "/safe")
public class SafeController extends BasicSpringController {

	
	@Resource
	private BankCardSupport bankCardSupport;
	
	@Resource
	private SafeSupport safeSupport;
	
	@Resource
	private CodeSupport codeSupport;
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private RedisManager redisManager;
	
	/**
	 * 首次设置支付密码 - 设置支付密码使用
	 *@description 
	 *@author Liebert
	 *@date 2015年4月16日 上午10:17:31
	 */
	@Token
	@RequestMapping(value = "/paypwd/set", method = RequestMethod.POST)
	public void firstSetPayPwd(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo){
		LogCvt.info(String.format("[设置支付密码] >> memberCode：%s", memberCode));
		
		CreateSource cs = SimpleUtils.codeToCreateSource(safePojo.getCreateSource());
		
//		//解密支付密码
//		String payPwd = SimpleUtils.decodePwd(safePojo.getCiphertextPwd(), cs);
//      String payPwdTemp = SimpleUtils.decodePwd(safePojo.getCiphertextPwdTemp(), cs);
		String payPwd = safePojo.getCiphertextPwd();
		String payPwdTemp = safePojo.getCiphertextPwdTemp();
        if(!payPwd.equals(payPwdTemp)){
        	respError(model, "两次输入的支付密码不一致");
            return;
        }
//        //校验支付密码规则
//		ResultBean valid = safeSupport.validationPayPwdRule(payPwd);
//		if(!valid.isSuccess()){
//			respError(model, valid.getMsg());
//            return;
//		}
        
        if (CreateSource.pc.equals(cs) || cs == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，设置支付密码");
            payPwd = userEngineSupport.encryptPwd(payPwd);
            if (StringUtil.isBlank(payPwd)) {
            	LogCvt.info("[密码加密] >> 支付密码加密失败");
            	respError(model, "支付密码加密失败");
				return;
			}
        }
        
        //是否已设置支付密码
		boolean isExistPayPwd = userEngineSupport.isMemberExistPayPwd(memberCode);
		if(isExistPayPwd){
			respError(model, "用户已设置支付密码");
            return;
		}
		
		//1.第一步设置安全问题
		LogCvt.info("[设置支付密码] >> 设置安全问题");
		
		//是否首次设置安全问题
		boolean isFirstSetQuestion = false;
		if(userEngineSupport.isMemberSetQuestion(memberCode)){
			LogCvt.info("[设置支付密码] >> 设置安全问题：用户已设置安全问题");
			if(StringUtil.isNotBlank(safePojo.getSafeQuestionPojoList())){
				respError(model, "用户已设置安全问题");
				return;
			}
		}else{
			//设置安全问题
			ResultBean result = userEngineSupport.setMemberQuestion(memberCode, safePojo.getSafeQuestionPojoList());
			if(!result.isSuccess()){
				LogCvt.info("[设置支付密码] >> 设置安全问题失败: " + result.getMsg());
				respError(model, result.getMsg());
				return;
			}
			
			LogCvt.info("[设置支付密码] >> 设置安全问题: 设置成功");
			isFirstSetQuestion = true;
		}
		
		
		
		//2.设置支付密码
		LogCvt.info("[设置支付密码] >> 设置支付密码");
//		String clientId = getClient(request);
		ResultBean pResult = userEngineSupport.setMemberPayPwd(memberCode, payPwd);
		
		if(!pResult.isSuccess()){

			LogCvt.info("[设置支付密码] >> 设置支付密码失败：" + pResult.getMsg() );
			if(isFirstSetQuestion){
				//如果是首次设置支付密码，删除已设置成功的支付密码
				LogCvt.info("[设置支付密码] >> 删除已设置成功的安全问题..");
				
				ResultBean deleteResult = userEngineSupport.disableMemberQuestion(memberCode);
				if(deleteResult.isSuccess()){
					LogCvt.info("[设置支付密码] >> 删除已设置成功的安全问题成功！ ");
				}else{
					LogCvt.info("[设置支付密码] >> 删除已设置成功的安全问题失败： " + deleteResult.getMsg());
				}
				
			}
			
			respError(model, pResult.getMsg());
			return;
		}else{
			LogCvt.info("[设置支付密码] >> 支付密码设置成功");
			respSuccess(model, "设置成功");
			return;
		}
		
		
	}
	
	
	
	/**
	 * 绑定手机号
	 *@description 
	 *@author Liebert
	 *@date 2015年4月22日 下午2:42:38
	 */
	@Token
	@RequestMapping(value = "/phone/bind", method = RequestMethod.POST)
	public void bindMobile(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo){
		
		LogCvt.info(String.format("[绑定手机号] >> memberCode：%s", memberCode));
		LogCvt.info(String.format("[绑定手机号] >> 校验短信验证码: Code:%s Token:%s  ", safePojo.getCode(), safePojo.getToken()));
		
		boolean codeResult = codeSupport.verifyCodeResult(safePojo.getCode(), safePojo.getToken());
		if(!codeResult){
			LogCvt.info("[绑定手机号] >> 校验短信验证码：失败");
			respError(model, "短信验证码错误");
			return;
		}
			
		LogCvt.info("[绑定手机号] >> 校验短信验证码：成功");
		LogCvt.info("[绑定手机号] >> 绑定手机号");
		
		ResultBean result = userEngineSupport.updateUserMobile(memberCode, safePojo.getMobile());

		if(!result.isSuccess()){
			LogCvt.info("[绑定手机号] >> 手机号绑定失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			LogCvt.info("[绑定手机号] >> 手机号绑定成功");
			respSuccess(model, "绑定成功");
			return;
		}
		
		
	}
	
	
	
	
	/**
	 * 修改登录密码 - 修改登录密码使用 - 校验旧登录密码
	 *@description 
	 *@author Liebert
	 *@date 2015年4月13日 下午5:46:24
	 */
	@Token
	@RequestMapping(value = "/loginpwd/reset", method = RequestMethod.POST)
	public void resetLoginPwd(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo) {
		
		LogCvt.info(String.format("[修改登录密码] >> memberCode:%s",memberCode));

		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		if(user == null){
			LogCvt.info("[修改登录密码] >> 查询用户是否存在：用户不存在");
			respError(model, ResultCode.notExistUser.getCode(), ResultCode.notExistUser.getMsg());
			return;
		}
		
		CreateSource cs = SimpleUtils.codeToCreateSource(safePojo.getCreateSource());
//		//解密登录密码
//		String newPwd = SimpleUtils.decodePwd(safePojo.getNewPwd(), cs);
//      String oldPwd = SimpleUtils.decodePwd(safePojo.getOldPwd(), cs);
		String newPwd = safePojo.getNewPwd();
		String oldPwd = safePojo.getOldPwd();
        if(newPwd.equals(oldPwd)){
        	respError(model, "新密码不能与原密码相同");
            return;
        }
//        //校验登录密码规则
//		ResultBean valid = safeSupport.validationLoginPwdRule(user.getLoginID(), newPwd);
//		if(!valid.isSuccess()){
//			respError(model, valid.getMsg());
//            return;
//		}
        
        if (CreateSource.pc.equals(cs) || cs == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，修改登录密码");
            newPwd = userEngineSupport.encryptPwd(newPwd);
            oldPwd = userEngineSupport.encryptPwd(oldPwd);
            if (StringUtil.isBlank(newPwd) && StringUtil.isBlank(oldPwd)) {
            	LogCvt.info("[密码加密] >> 登录密码加密失败");
            	respError(model, "登录密码加密失败");
				return;
			}
        }
        
        //修改密码
//        String clientId = getClient(request);
		ResultBean result = userEngineSupport.resetMemberPwdByOldPwd(memberCode,oldPwd,newPwd);
		
		if(!result.isSuccess()){
			LogCvt.info("[修改登录密码] >> 登录密码修改失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			LogCvt.info("[修改登录密码] >> 登录密码修改成功");
			respSuccess(model, "修改成功");
			return;
		}
	}
	
	
	
	

	/**
	 * 修改登录密码 - 修改登录密码使用 - 校验手机短信验证码
	 *@description 
	 *@author Liebert
	 *@date 2015年4月13日 下午5:46:24
	 */
	@Token
	@RequestMapping(value = "/loginpwd/resetByMobile", method = RequestMethod.POST)
	public void resetLoginPwdByMobileSMS(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo) {
		
		LogCvt.info(String.format("[修改登录密码] >> 通过校验手机短信验证码修改 memberCode:%s",memberCode));

		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		if(user == null){
			LogCvt.info("[修改登录密码] >> 查询用户是否存在：用户不存在");
			respError(model, ResultCode.notExistUser.getCode(), ResultCode.notExistUser.getMsg());
			return;
		}
		
		CreateSource cs = SimpleUtils.codeToCreateSource(safePojo.getCreateSource());
//		//解密登录密码
//		String newPwd = SimpleUtils.decodePwd(safePojo.getNewPwd(), cs);
//        //校验登录密码规则
//		ResultBean valid = safeSupport.validationLoginPwdRule(user.getLoginID(), newPwd);
//		if(!valid.isSuccess()){
//			respError(model, valid.getMsg());
//            return;
//		}
		String newPwd = safePojo.getNewPwd();
		
		if (CreateSource.pc.equals(cs) || cs == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，修改登录密码");
            newPwd = userEngineSupport.encryptPwd(newPwd);
            if (StringUtil.isBlank(newPwd)) {
            	LogCvt.info("[密码加密] >> 登录密码加密失败");
            	respError(model, "登录密码加密失败");
				return;
			}
        }
		
		//校验手机短信验证码
		boolean codeResult = codeSupport.verifyCodeResult(safePojo.getCode(), safePojo.getToken());
		if(!codeResult){
			LogCvt.info("[修改登录密码] >> 校验短信验证码：失败");
			respError(model, "短信验证码错误");
			return;
		}
		
		LogCvt.info("[修改登录密码] >> 校验短信验证码：成功");
		//重置登录密码
		ResultBean result = userEngineSupport.resetMemberPwd(memberCode,newPwd);
		
		if(!result.isSuccess()){
			LogCvt.info("[修改登录密码] >> 登录密码修改失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			LogCvt.info("[修改登录密码] >> 登录密码修改成功");
			respSuccess(model, "修改成功");
			return;
		}
	}
	
	
	
	
	/**
	 * 修改登录密码 - 修改登录密码使用 - 校验安全问题
	 *@description 
	 *@author Liebert
	 *@date 2015年4月13日 下午5:46:24
	 */
	@Token
	@RequestMapping(value = "/loginpwd/resetByQuestion", method = RequestMethod.POST)
	public void resetLoginPwdByQuestion(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo) {
		
		LogCvt.info(String.format("[修改登录密码] >> 通过校验安全问题修改 memberCode:%s",memberCode));

		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		if(user == null){
			LogCvt.info("[修改登录密码] >> 查询用户是否存在：用户不存在");
			respError(model, ResultCode.notExistUser.getCode(), ResultCode.notExistUser.getMsg());
			return;
		}
		
		CreateSource cs = SimpleUtils.codeToCreateSource(safePojo.getCreateSource());
//		//解密登录密码
//		String newPwd = SimpleUtils.decodePwd(safePojo.getNewPwd(), cs);
//        //校验登录密码规则
//		ResultBean valid = safeSupport.validationLoginPwdRule(user.getLoginID(), newPwd);
//		if(!valid.isSuccess()){
//			respError(model, valid.getMsg());
//            return;
//		}
		String newPwd = safePojo.getNewPwd();
		
		if (CreateSource.pc.equals(cs) || cs == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，修改登录密码");
            newPwd = userEngineSupport.encryptPwd(newPwd);
            if (StringUtil.isBlank(newPwd)) {
            	LogCvt.info("[密码加密] >> 登录密码加密失败");
            	respError(model, "登录密码加密失败");
				return;
			}
        }
		
		//校验安全问题
		ResultBean questionResult = userEngineSupport.verifyMemberQuestion(memberCode, safePojo.getSafeQuestionPojoList());
		if(!questionResult.isSuccess()){
			LogCvt.info("[修改登录密码] >> 校验安全问题：失败");
			respError(model, "安全问题答案错误");
			return;
		}
		
		LogCvt.info("[修改登录密码] >> 校验安全问题：成功");
		ResultBean result = userEngineSupport.resetMemberPwd(memberCode,newPwd);
		
		if(!result.isSuccess()){
			LogCvt.info("[修改登录密码] >> 登录密码修改失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			LogCvt.info("[修改登录密码] >> 登录密码修改成功");
			respSuccess(model, "修改成功");
			return;
		}
	}
	
	
	
	/**
	 * 获取预设问题
	 *@description 
	 *@author Liebert
	 *@date 2015年4月22日 上午11:27:13
	 */
	@RequestMapping(value = "/question/getPreinstallQuestion", method = RequestMethod.GET)
	public void getPreinstallQuestion(ModelMap model, HttpServletRequest request, int num){
		model.putAll(userEngineSupport.selectPreinstallQuestion(num));
	}
	
	
	/**
	 * 获取会员设置的问题
	 *@description 
	 *@author Liebert
	 *@date 2015年4月22日 上午11:53:35
	 */
	@Token
	@RequestMapping(value = "/question/getMemberQuestion", method = RequestMethod.GET)
	public void getMemberQuestion(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode,int num){
		model.putAll(userEngineSupport.getMemberQuestion(memberCode,num));
	}
	
	
	/** 
	 * 
	 * @param request
	 * @param memberCode
	 * @param safePojo
	 * @return
	 */
	@Token
	@RequestMapping(value = "/question/verify", method = RequestMethod.POST)
	public void verifyQuestion(HttpServletRequest request, ModelMap model, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo) {
		String clientId = getClient(request);
		LogCvt.info(String.format("[验证安全问题] >> memberCode:%s", memberCode));
		
		ResultBean result = userEngineSupport.verifyMemberQuestion(memberCode, safePojo.getSafeQuestionPojoList());
		
		if(!result.isSuccess()){
			LogCvt.info("[验证安全问题] >> 验证安全问题失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			LogCvt.info("[验证安全问题] >> 验证安全问题成功");

			//create token
			String authToken = safeSupport.createQuestionAuthTokenKey(clientId, memberCode);
			model.put("authToken", authToken);
			
			respSuccess(model, "验证通过");
			return;
		}
		
	}
	
	/**
	 * 
	 * verifyQuestionAndSms:(找回支付密码校验安全问题和短信验证码).
	 *
	 * @author wufei
	 * 2015-10-29 上午09:36:32
	 * @param request
	 * @param model
	 * @param memberCode
	 * @param safePojo
	 *
	 */
	@Token
	@RequestMapping(value = "/question/verifynew", method = RequestMethod.POST)
	public void verifyQuestionAndSms(HttpServletRequest request, ModelMap model, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo) {
		String clientId = getClient(request);
		LogCvt.info(String.format("[找回支付密码-验证安全问题] >> memberCode:%s", memberCode));
		
		ResultBean result = userEngineSupport.verifyMemberQuestion(memberCode, safePojo.getSafeQuestionPojoList());
		
		if(!result.isSuccess()){
			LogCvt.info("[找回支付密码-验证安全问题] >> 验证安全问题失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			LogCvt.info("[找回支付密码-验证安全问题] >> 验证安全问题成功");
			LogCvt.info("[找回支付密码-验证短信验证码] >> 验证短信验证码");

			boolean codeResult = codeSupport.verifyCodeResult(safePojo.getCode(),safePojo.getToken());
			if(!codeResult){
				LogCvt.info("[找回支付密码-验证短信验证码] >> 验证短信验证码失败");
				respError(model, "短信验证码错误");
				return;
			}
			
			LogCvt.info("[找回支付密码-验证短信验证码] >> 验证短信验证码成功");
			
			String authToken = safeSupport.createQuestionAuthTokenKey(clientId, memberCode);
			model.put("authToken", authToken);
			
			respSuccess(model, "验证通过");
			return;
		}
	
	}
	

	/**
	 * 用户修改安全问题
	 *@description 
	 *@author Liebert
	 *@date 2015年6月27日 下午2:10:15
	 */
	@Token
	@RequestMapping(value = "/question/update", method = RequestMethod.POST)
	public void setMemberQuestion(HttpServletRequest request, ModelMap model, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo){
		String clientId = getClient(request);
		LogCvt.info(String.format("[修改安全问题] >> memberCode:%s", memberCode));
		
		ResultBean tk =  safeSupport.verifyQuestionAuthTokenKey(clientId, memberCode, safePojo.getAuthToken());
		if(!tk.isSuccess()){
			LogCvt.info("[修改安全问题] >> 校验安全问题Token失败：" + tk.getMsg());
			respError(model, tk.getMsg());
			return;
		}
		
		ResultBean result = userEngineSupport.setMemberQuestion(memberCode, safePojo.getSafeQuestionPojoList());

		if(!result.isSuccess()){
			LogCvt.info("[修改安全问题] >> 修改安全问题失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			//删除校验原安全问题成功的key
			String vkey = RedisKeys.user_safe_verified_question(clientId, memberCode);
			redisManager.del(vkey);
			
			LogCvt.info("[修改安全问题] >> 修改安全问题成功");
			respSuccess(model, "修改成功");
			return;
		}
		
	}
	
	
	
	
	/**
	 * 补全安全问题 (验证支付密码)
	 * @param request
	 * @param memberCode
	 * @param safePojo
	 * @return
	 */
	@Token
	@RequestMapping(value = "/question/complete", method = RequestMethod.POST)
	public void setMemberQuestionByPayPwd(HttpServletRequest req, ModelMap model, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo){
		String clientId = getClient(req);
		LogCvt.info(String.format("[补全安全问题] >> memberCode:%s", memberCode));
		
		CreateSource cs = SimpleUtils.codeToCreateSource(safePojo.getCreateSource());
//		
//		//验证支付密码
//		String payPwd = SimpleUtils.decodePwd(safePojo.getCiphertextPwd(), cs);
		String payPwd = safePojo.getCiphertextPwd();
		
		if (CreateSource.pc.equals(cs) || cs == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，验证支付密码");
            payPwd = userEngineSupport.encryptPwd(payPwd);
            if (StringUtil.isBlank(payPwd)) {
            	LogCvt.info("[密码加密] >> 支付密码加密失败");
            	respError(model, "支付密码加密失败");
				return;
			}
        }
		ResultBean vresult = userEngineSupport.verifyMemberPayPwd(memberCode, payPwd,clientId);
		if(!vresult.isSuccess()){
			LogCvt.info("[修改支付密码] >> 原支付密码验证失败：" + vresult.getMsg());
			respError(model, vresult.getMsg());
			return;
		}
		
		ResultBean result = userEngineSupport.setMemberQuestion(memberCode, safePojo.getSafeQuestionPojoList());

		if(!result.isSuccess()){
			LogCvt.info("[补全安全问题] >> 补全安全问题失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			LogCvt.info("[补全安全问题] >> 补全安全问题成功");
			respSuccess(model, "设置成功");
			return;
		}
		
	}
	
	
	
	
	
	
	/**
	 * 忘记安全问题申诉 - 发送邮件
	 * @param request
	 * @param memberCode
	 * @param safePojo
	 * @return
	 */
	@Token
	@RequestMapping(value = "/appeal/sendMail", method = RequestMethod.POST)
	public void sendMail(HttpServletRequest request, ModelMap model, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo){
		String clientId = getClient(request);
		LogCvt.info(String.format("[忘记安全问题-发送邮件] >> clientId:%s, memberCode:%s, email:%s", clientId, memberCode, safePojo.getEmail()));
		//收件人邮箱地址
		String email = null;
		
		if(StringUtil.isBlank(safePojo.getEmail())){
			UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
			if(!user.getIsBindEmail()){
				respError(model, "用户未绑定邮箱");
				return;
			}else{
				email = user.getEmail();
			}
		}else{
			email = safePojo.getEmail();
		}
		
		//添加用户申请记录，用于后面流程校验。redis缓存记录
		redisManager.putExpire(RedisKeys.user_safe_appeal_record(clientId, memberCode), email, 24*60*60);
		
		//发送邮件
		boolean sendResult = safeSupport.sendMemberAccessAppealEmail(email, clientId, memberCode);
		
		if(!sendResult){
			LogCvt.info("[忘记安全问题-发送邮件] >> 邮件发送失败");
			respError(model, "邮件发送失败");
			return;
		}else{
			LogCvt.info("[忘记安全问题-发送邮件] >> 邮件发送成功");
			respSuccess(model, "邮件发送成功");
			return;
		}
		
	}
	
	
	
	
	/**
	 * 申诉跳转链接-校验链接有效性
	 * @param request
	 * @param model
	 * @param key
	 */
	@RequestMapping(value = "/appeal/check", method = RequestMethod.GET)
	public void appealVerify(HttpServletRequest request, ModelMap model, String key, String type){
		LogCvt.info(String.format("[申诉验证有效性] >> key=%s, type=%s", key, type));
		
		Long memberCode = null;
		Long createTime = null;
		Long auditTime = null;
		
		try{
			if("auth".equals(type)){
				LogCvt.info("[申诉验证有效性] >> 校验类型：用户申请申诉");
				
				//解密key值 clientId_memberCode_createTime
				String decKey = DESUtil.decrypt(key);
				String[] keyArr = decKey.split("_");
				String clientId = keyArr[0];
				memberCode = Long.valueOf(keyArr[1]);
				createTime = Long.valueOf(keyArr[2]);
				
				//是否过期
				if(new Date().after(DateUtils.addDays(new Date(createTime), 1))){
					respError(model, "链接已过期");
					return;
				}
				//是否存在记录
				String email = redisManager.getString(RedisKeys.user_safe_appeal_record(clientId, memberCode));
				if(StringUtil.isBlank(email)){
					respError(model, "无效链接");
					return;
				}
				//用户是否存在
				UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
				if(user == null){
					respError(model, "用户不存在");
					return;
				}
				
				LogCvt.info("[申诉验证有效性] >> 验证成功");
				//返回用户签约状态
				model.put("isSign", StringUtil.isNotBlank(user.getBankGroupId()));
				model.put("memberCode", memberCode);
				respSuccess(model, "验证通过");
				return;
				
			}else if("audit".equals(type)){
				//此处clientId为froad
				LogCvt.info("[申诉验证有效性] >> 校验类型：BOSS审核通过回执");
				
				//从boss过来的key 格式System.currentTimeMillis()+memberCode
				String decKey = DESUtil.decrypt(key);
				auditTime = Long.valueOf(decKey.substring(0,13));
				memberCode = Long.parseLong(decKey.substring(13));
				
				//是否过期
				if(new Date().after(DateUtils.addDays(new Date(auditTime), 1))){
					respError(model, "链接已过期");
					return;
				}
				
				//是否已设置成功过
				//clientId = getClient(request);
				String code = redisManager.getString(RedisKeys.user_safe_reset_question_success(memberCode));
				//boss过来的key和code相等表示已经重置成功，链接失效
				if(StringUtil.isNotBlank(code) && key.equals(code)){
					//有记录表示已经重置成功，链接失效
					respError(model, "链接已失效");
					return;
				}
				
				LogCvt.info("[申诉验证有效性] >> 验证成功");
				//授权Token，重置安全问题校验使用
				String authToken = safeSupport.createAppealResetQuestionAuthToken(memberCode);
				model.put("authToken", authToken);
				model.put("memberCode", memberCode);
				respSuccess(model, "验证通过");
				return;
			}else{
				respError(model, "校验类型为空");
				return;
			}
			
		}catch(Exception e){
			LogCvt.error("[申诉验证有效性] >> 验证异常",e);
			respError(model, "验证失败，无效请求");
			return;
		}
	
		
		
	}
	
	
	
	
	/**
	 * 针对申诉流程的证件上传所做的接口，不校验登录态
	 * @param request
	 * @param model
	 * @param memberCode
	 * @param file
	 * @throws IOException 
	 */
	@RequestMapping(value = "/image/upload", method = RequestMethod.POST)
	public void imgUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="file") MultipartFile file) throws IOException{
		HashMap<String,String> m = new HashMap<String, String>();
		String json = "";
		
		try{
			Long memberCode = Long.valueOf(request.getParameter("memberCode"));
			String clientId = getClient(request);
			
			LogCvt.info(String.format("[上传文件] >> clientId:%s, memberCode:%s", clientId, memberCode));
			
			//校验有效性
			String email = redisManager.getString(RedisKeys.user_safe_appeal_record(clientId, memberCode));
			if(StringUtil.isBlank(email)){
				m.put(Results.code, "9999");
				m.put(Results.msg, "无效请求");
				response.setStatus(608);
				json = JSON.toJSONString(m);
			}else{
				String oriFileName = file.getOriginalFilename();
				String suffix = oriFileName.substring(oriFileName.indexOf("."));
				String newFileName = UUID.randomUUID().toString() + suffix;
				
				//保存到本地
				String fileDir = FileUtil.saveLocalTemp(newFileName, file.getBytes());
				
				//上传到服务器
				ScpUtil.uploadFile(fileDir, newFileName, Constants.getScpUploadDir(), Constants.scpConfig);
				
				//删除临时文件
				FileUtils.deleteQuietly(new File(fileDir));
				
				String remoteDir = Constants.getImgRemoteUrl() + newFileName;
				
				m.put("imageUrl", remoteDir);
				json = JSON.toJSONString(m);
				response.setStatus(200);
			}
			
		}catch(Exception e){
			LogCvt.error("[上传文件] >> 上传文件失败",e);
			m.put(Results.code, "9999");
			m.put(Results.msg, "上传失败");
			response.setStatus(608);
			json = JSON.toJSONString(m);
		}finally{
			LogCvt.info("RESPONE: "+json);
			//兼容IE
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.getWriter().write(json);
			response.flushBuffer();
		}
	}
	
	
	
	/**
	 * 忘记安全问题申诉 - 申请人工处理
	 * @param request
	 * @param memberCode
	 * @param safePojo
	 * @return
	 */
	@RequestMapping(value = "/appeal/apply", method = RequestMethod.POST)
	public void memberQuestionAppeal(HttpServletRequest request, ModelMap model, @RequestBody SafeAppealPojo appealInfo){
		String clientId = getClient(request);
		final Long memberCode = appealInfo.getMemberCode();
		
		LogCvt.info(String.format("[忘记安全问题-提交申诉] >> clientId:%s, memberCode:%s", clientId, memberCode));
		
		
		//是否存在申请记录
		String email = redisManager.getString(RedisKeys.user_safe_appeal_record(clientId,memberCode));
		if(StringUtil.isBlank(email)){
			respError(model, "无效链接");
			return;
		}
		
		//获取申请渠道
		ClientChannel clientChannel = ClientChannelEnum.getClientChannel(request);
		ResultBean result = userEngineSupport.memberAppeal(clientChannel, memberCode, email, appealInfo);

		if(!result.isSuccess()){
			LogCvt.info("[忘记安全问题-提交申诉] >> 提交申请失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			//申请成功
			//1.链接失效
			redisManager.del(RedisKeys.user_safe_appeal_record(clientId, memberCode));
			
			//2.判断是否自动匹配失败
			if("3".equals(result.getCode())){
				
				LogCvt.info("[忘记安全问题-提交申诉] >> 申诉用户信息不匹配，自动审核失败，30分钟后自动发送回复邮件");
				//自动匹配失败，30分钟后发送失败邮件
				safeSupport.startSendAuditRejectedMail(email);
			}else{
				LogCvt.info("[忘记安全问题-提交申诉] >> 提交申请成功");
			}
			
			respSuccess(model, "申请成功");
			return;
		}
		
	}
	
	
	
	
	/**
	 * 忘记安全问题申诉 - 审核通过后重置安全问题
	 * @param request
	 * @param model
	 * @param appealInfo
	 */
	@RequestMapping(value = "/appeal/resetQuestion", method = RequestMethod.POST)
	public void memberResetQuestion(HttpServletRequest request, ModelMap model, @RequestBody UserSafePojo safePojo){
		//String clientId = getClient(request);
		Long memberCode = safePojo.getMemberCode();
		
		LogCvt.info(String.format("[申诉审核通过-重置安全问题] >> clientId:%s, memberCode:%s", getClient(request), memberCode));
		
		ResultBean tk =  safeSupport.verifyAppealResetQuestionAuthToken(memberCode, safePojo.getAuthToken());
		if(!tk.isSuccess()){
			LogCvt.info("[申诉审核通过-重置安全问题] >> 校验安全问题Token失败：" + tk.getMsg());
			respError(model, tk.getMsg());
			return;
		}
		
		ResultBean result = userEngineSupport.setMemberQuestion(memberCode, safePojo.getSafeQuestionPojoList());

		if(!result.isSuccess()){
			LogCvt.info("[申诉审核通过-重置安全问题] >> 重置安全问题失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			LogCvt.info("[申诉审核通过-重置安全问题] >> 重置安全问题成功");
			
			//删除授权重置安全问题的key
			String vkey = RedisKeys.user_safe_appeal_reset_question_auth(memberCode);
			redisManager.del(vkey);
			
			//向redis中缓存成功重置的记录,过期时间一天
			String key = RedisKeys.user_safe_reset_question_success(memberCode);
			//将加密串code缓存，在重复使用链接的时候校验
			redisManager.putExpire(key, safePojo.getCode(), 86400);
			
			respSuccess(model, "重置安全问题成功");
			return;
		}
		
	}
	
	
	
	
	/**
	 * 修改支付密码 - 修改支付密码使用（先验证短信验证码，再修改支付密码）
	 *@description 
	 *@author Liebert
	 *@date 2015年4月16日 上午10:51:54
	 */
	@Token
	@RequestMapping(value = "/paypwd/update", method = RequestMethod.POST)
	public void updatePayPwd(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo) {
		String clientId = getClient(req);
		LogCvt.info(String.format("[修改支付密码] >> memberCode:%s",memberCode));
		LogCvt.info("[修改支付密码] >> 验证原支付密码..");
		
		CreateSource cs = SimpleUtils.codeToCreateSource(safePojo.getCreateSource());
//		
//		//解密支付密码
//		String payPwdOld = SimpleUtils.decodePwd(safePojo.getCiphertextPwdOld(), cs);
//		String payPwd = SimpleUtils.decodePwd(safePojo.getCiphertextPwd(), cs);
//      String payPwdTemp = SimpleUtils.decodePwd(safePojo.getCiphertextPwdTemp(), cs);
        
        //校验支付密码
		String payPwdOld = safePojo.getCiphertextPwdOld();
		String payPwd = safePojo.getCiphertextPwd();
		String payPwdTemp = safePojo.getCiphertextPwdTemp();
		
		if (CreateSource.pc.equals(cs) || cs == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，修改支付密码");
            payPwdOld = userEngineSupport.encryptPwd(payPwdOld);
            if (StringUtil.isBlank(payPwdOld)) {
            	LogCvt.info("[密码加密] >> 原支付密码加密失败");
            	respError(model, "原支付密码加密失败");
				return;
			}
        }
		ResultBean verify = userEngineSupport.verifyMemberPayPwd(memberCode, payPwdOld, clientId);
		if(!verify.isSuccess()){
			LogCvt.info("[修改支付密码] >> 原支付密码验证失败：" + verify.getMsg());
			respError(model, verify.getMsg());
			return;
		}
        if(!payPwd.equals(payPwdTemp)){
        	respError(model, "两次输入的支付密码不一致");
            return;
        }
//		//校验支付密码规则
//		ResultBean valid = safeSupport.validationPayPwdRule(payPwd);
//		if(!valid.isSuccess()){
//			respError(model, valid.getMsg());
//            return;
//		}

        if (CreateSource.pc.equals(cs) || cs == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，修改支付密码");
            payPwd = userEngineSupport.encryptPwd(payPwd);
            if (StringUtil.isBlank(payPwd)) {
            	LogCvt.info("[密码加密] >> 新支付密码加密失败");
            	respError(model, "新支付密码加密失败");
				return;
			}
        }
		LogCvt.info("[修改支付密码] >> 验证短信验证码");

		boolean codeResult = codeSupport.verifyCodeResult(safePojo.getCode(),safePojo.getToken());
		if(!codeResult){
			LogCvt.info("[修改支付密码] >> 验证短信验证码失败");
			respError(model, "短信验证码错误");
			return;
		}
		
		LogCvt.info("[修改支付密码] >> 验证短信验证码成功，修改支付密码");
			
		ResultBean presult = userEngineSupport.setMemberPayPwd(memberCode, payPwd);
		
		if(!presult.isSuccess()){
			LogCvt.info("[修改支付密码] >> 支付密码修改失败：" + presult.getMsg());
			respError(model, presult.getMsg());
			return;
		}else{
			LogCvt.info("[修改支付密码] >> 支付密码修改成功");
			respSuccess(model, "支付密码修改成功");
			return;
		}
		
		
	}
	
	


	/**
	 * 重置支付密码 - 找回支付密码使用
	 *@description 
	 *@author Liebert
	 *@date 2015年4月13日 下午5:46:38
	 */
	@Token
	@RequestMapping(value = "/paypwd/reset", method = RequestMethod.POST)
	public void resetPayPwd(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo) {
		
		LogCvt.info(String.format("[找回支付密码] >> 重置支付密码： memberCode:%s", memberCode));
		LogCvt.info(String.format("[找回支付密码] >> 校验短信验证码: Code:%s, Token:%s", safePojo.getCode(), safePojo.getToken()));
		
		CreateSource cs = SimpleUtils.codeToCreateSource(safePojo.getCreateSource());
		
//		//解密支付密码
//		String payPwd = SimpleUtils.decodePwd(safePojo.getCiphertextPwd(), cs);
//      String payPwdTemp = SimpleUtils.decodePwd(safePojo.getCiphertextPwdTemp(), cs);
		String payPwd = safePojo.getCiphertextPwd();
		String payPwdTemp = safePojo.getCiphertextPwdTemp();
        if(!payPwd.equals(payPwdTemp)){
        	respError(model, "两次输入的支付密码不一致");
            return;
        }
//		//校验支付密码规则
//		ResultBean valid = safeSupport.validationPayPwdRule(payPwd);
//		if(!valid.isSuccess()){
//			respError(model, valid.getMsg());
//            return;
//		}
        if (CreateSource.pc.equals(cs) || cs == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，重置支付密码");
            payPwd = userEngineSupport.encryptPwd(payPwd);
            if (StringUtil.isBlank(payPwd)) {
            	LogCvt.info("[密码加密] >> 支付密码加密失败");
            	respError(model, "支付密码加密失败");
				return;
			}
        }
        
		//先校验手机验证码
		boolean codeResult = codeSupport.verifyCodeResult(safePojo.getCode(),safePojo.getToken());
		if(!codeResult){
			LogCvt.info("[找回支付密码] >> 校验短信验证码失败");
			respError(model, "短信验证码错误");
			return;
		}else{
			
			LogCvt.info("[找回支付密码] >> 校验短信验证码成功，重置支付密码");
//			String clientId = getClient(request);
			ResultBean result = userEngineSupport.setMemberPayPwd(memberCode, payPwd);
			
			if(!result.isSuccess()){
				LogCvt.info("[找回支付密码] >> 重置支付密码失败：" + result.getMsg());
				respError(model, result.getMsg());
				return;
			}else{
				LogCvt.info("[找回支付密码] >> 重置支付密码成功");
				respSuccess(model, "支付密码重置成功");
				return;
			}
		}
		
	}

	/**
	 * 
	 * resetPayPwdNew:(重置支付密码 - 找回支付密码使用新接口).
	 *
	 * @author wufei
	 * 2015-10-28 下午04:57:08
	 * @param model
	 * @param request
	 * @param memberCode
	 * @param safePojo
	 *
	 */
	@Token
	@RequestMapping(value = "/paypwd/resetnew", method = RequestMethod.POST)
	public void resetPayPwdNew(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo) {
		String clientId = getClient(request);
		LogCvt.info(String.format("[找回支付密码] >> 重置支付密码： memberCode:%s", memberCode));
		LogCvt.info(String.format("[找回支付密码] >> 校验前置接口Token:%s", safePojo.getAuthToken()));
		
		CreateSource cs = SimpleUtils.codeToCreateSource(safePojo.getCreateSource());
		
//		//解密支付密码
//		String payPwd = SimpleUtils.decodePwd(safePojo.getCiphertextPwd(), cs);
//      String payPwdTemp = SimpleUtils.decodePwd(safePojo.getCiphertextPwdTemp(), cs);
		String payPwd = safePojo.getCiphertextPwd();
		String payPwdTemp = safePojo.getCiphertextPwdTemp();
        if(!payPwd.equals(payPwdTemp)){
        	respError(model, "两次输入的支付密码不一致");
            return;
        }
//		//校验支付密码规则
//		ResultBean valid = safeSupport.validationPayPwdRule(payPwd);
//		if(!valid.isSuccess()){
//			respError(model, valid.getMsg());
//            return;
//		}
		
        if (CreateSource.pc.equals(cs) || cs == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，找回支付密码");
            payPwd = userEngineSupport.encryptPwd(payPwd);
            if (StringUtil.isBlank(payPwd)) {
            	LogCvt.info("[密码加密] >> 支付密码加密失败");
            	respError(model, "支付密码加密失败");
				return;
			}
        }
        
		//校验前置接口authToken
		ResultBean tokenResult = safeSupport.verifyQuestionAuthTokenKey(clientId, memberCode, safePojo.getAuthToken());
		if(!tokenResult.isSuccess()){
			LogCvt.info("[找回支付密码] >> 验证authToken失败:"+tokenResult.getMsg());
			respError(model, tokenResult.getMsg());
			return;
		}else{
			
			LogCvt.info("[找回支付密码] >> 校验authToken成功，开始重置支付密码");
			
			ResultBean result = userEngineSupport.setMemberPayPwd(memberCode, payPwd);
			
			if(!result.isSuccess()){
				LogCvt.info("[找回支付密码] >> 重置支付密码失败：" + result.getMsg());
				respError(model, result.getMsg());
				return;
			}else{
				LogCvt.info("[找回支付密码] >> 重置支付密码成功");
				
				//删除校验安全问题成功的key
				String vkey = RedisKeys.user_safe_verified_question(clientId, memberCode);
				redisManager.del(vkey);
				
				respSuccess(model, "支付密码重置成功");
				return;
			}
		}
		
	}
	
	
	
	/**
	 * 
	 * @Description: 修改手机号 - 验证原手机号
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月21日 下午8:27:36
	 */
	@Token
	@RequestMapping(value = "/phone/verifyOriginal", method = RequestMethod.POST)
	public void verifyOldPhone(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo){
		LogCvt.info(String.format("[修改手机号] >> 验证原手机号码 memberCode：%s", memberCode));
		String clientId = getClient(req);
		
		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		if(user == null){
			LogCvt.info("[修改手机号] >> 验证用户信息：用户不存在");
			respError(model, ResultCode.notExistUser.getCode(), ResultCode.notExistUser.getMsg());
			return;
		}else if(!user.getIsBindMobile()){
			LogCvt.info("[修改手机号] >> 验证用户信息：用户未绑定手机号");
			respError(model, ResultCode.notBoundMobile.getCode(), ResultCode.notBoundMobile.getMsg());
			return;
		}
		
		//先校验手机验证码
		boolean codeResult = codeSupport.verifyCodeResult(safePojo.getCode(),safePojo.getToken());
		if(!codeResult){
			LogCvt.info("[修改手机号] >> 校验短信验证码失败");
			respError(model, "短信验证码错误");
			return;
			
		}else{
			//创建token传递
			String authToken = safeSupport.createVerifyOringinalMobileAuthTokenKey(clientId, memberCode, user.getMobile());
			model.put("authToken", authToken);
			respSuccess(model, "验证通过");
			return;
		}
				
	}
	
	
	
	/**
	 * 修改手机号 - 新手机号、短信验证码、token
	 *@description 
	 *@author Liebert
	 *@date 2015年4月22日 下午2:53:13
	 */
	@Token
	@RequestMapping(value = "/phone/update", method = RequestMethod.POST)
	public void updateMobile(ModelMap model, HttpServletRequest req, @RequestHeader Long memberCode, @RequestBody UserSafePojo safePojo) {
		
		LogCvt.info(String.format("[修改手机号] >> memberCode：%s", memberCode));
		LogCvt.info("[修改手机号] >> 校验短信验证码");
		String clientId = getClient(req);
		
		//查询用户
		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		if(user == null){
			LogCvt.info("[修改手机号] >> 验证用户信息：用户不存在");
			respError(model, ResultCode.notExistUser.getCode(), ResultCode.notExistUser.getMsg());
			return;
		}else if(!user.getIsBindMobile()){
			LogCvt.info("[修改手机号] >> 验证用户信息：用户未绑定手机号");
			respError(model, ResultCode.notBoundMobile.getCode(), ResultCode.notBoundMobile.getMsg());
			return;
		}
		
		//先校验手机验证码
		boolean codeResult = codeSupport.verifyCodeResult(safePojo.getCode(),safePojo.getToken());
		if(!codeResult){
			LogCvt.info("[修改手机号] >> 校验短信验证码失败");
			respError(model, "短信验证码错误");
			return;
			
		}
		
		LogCvt.info("[修改手机号] >> 校验短信验证码成功，修改手机号");
		LogCvt.info("[修改手机号] >> 校验前置接口Token");
		
		//校验前置接口-token
		//校验原手机号成功token
		boolean mobileToken = false;
		ResultBean opRes = safeSupport.verifyOringinalMobileAuthTokenKey(clientId, memberCode, user.getMobile(), safePojo.getAuthToken());
		if(!opRes.isSuccess()){
			//2个前置接口，不成功则验证：校验安全问题成功token
			ResultBean qsRes = safeSupport.verifyQuestionAuthTokenKey(clientId, memberCode, safePojo.getAuthToken());
			if(!qsRes.isSuccess()){
				LogCvt.info("[修改手机号] >> 校验前置接口Token失败：" + qsRes.getMsg());
				respError(model, qsRes.getMsg());
				return;
			}
		}else{
			mobileToken = true;
		}

		LogCvt.info("[修改手机号] >> 校验前置接口Token成功, 修改手机号");

		ResultBean result = userEngineSupport.updateUserMobile(memberCode, safePojo.getMobile());

		if(!result.isSuccess()){
			LogCvt.info("[修改手机号] >> 修改手机号失败：" + result.getMsg());
			respError(model, result.getMsg());
			return;
		}else{
			//修改成功后删除前置接口token
			if(mobileToken){
				String key = RedisKeys.user_safe_verified_original_mobile_success(clientId, memberCode, user.getMobile());
				redisManager.del(key);
			}else{
				String key = RedisKeys.user_safe_verified_question(clientId, memberCode);
				redisManager.del(key);
			}
			LogCvt.info("[修改手机号] >> 修改手机号成功");
			respSuccess(model, "修改成功");
			return;
		}

		
	}
	
	
	
	
	
	/**
	 * 忘记密码 - 校验手机号和图片验证码
	 *@description 
	 *@author Liebert
	 *@date 2015年4月23日 下午4:00:17
	 */
	@RequestMapping(value = "/loginpwd/forget", method = RequestMethod.POST)
	public void forgetLoginPwd(ModelMap model, HttpServletRequest request, @RequestBody UserSafePojo safePojo){
		
		LogCvt.info(String.format("[忘记登录密码] >> 校验图片验证码： code:%s, token:%s", safePojo.getImgCode(), safePojo.getImgToken()));

		//先校验图片验证码
		boolean codeResult = codeSupport.verifyCodeResult(safePojo.getImgCode(),safePojo.getImgToken());
		if(!codeResult){
			LogCvt.info("[忘记登录密码] >> 校验图片验证码失败");
			respError(model, "图片验证码错误");
			return;
			
		}else{
			
			LogCvt.info(String.format("[忘记登录密码] >> 校验图片验证码成功，验证用户信息： loginId/mobile:%s", safePojo.getMobile()));
			
			UserSpecDto user = userEngineSupport.queryMemberByLoginID(safePojo.getMobile());
			if(user == null){
				LogCvt.info("[忘记登录密码] >> 验证用户信息：用户不存在");
				respError(model, ResultCode.notExistUser.getCode(), ResultCode.notExistUser.getMsg());
				return;
			}
			if(!user.getIsBindMobile()){
				LogCvt.info("[忘记登录密码] >> 验证用户信息：用户未绑定手机号");
				respError(model, ResultCode.notBoundMobile.getCode(), ResultCode.notBoundMobile.getMsg());
				return;
			}else{
				LogCvt.info("[忘记登录密码] >> 验证用户信息：验证通过");
				model.put("loginId",user.getLoginID());
				model.put("encryptMobile",SensEncryptUtil.encryptMobile(user.getMobile()));
				return;
			}
		}
		
	}
	
	
	
	
	
	/**
	 * 忘记密码 - 校验手机验证码/重置密码
	 *@description 
	 *@author Liebert
	 *@date 2015年4月23日 下午4:00:17
	 */
	@RequestMapping(value = "/loginpwd/find", method = RequestMethod.POST)
	public void findLoginPwd(ModelMap model, HttpServletRequest request, @RequestBody UserSafePojo safePojo){
		String clientId = getClient(request);
		LogCvt.info("[忘记登录密码] >> 重置登录密码");
		//查找用户
		UserSpecDto user = userEngineSupport.queryMemberByLoginID(safePojo.getLoginId());
		if(user == null){
			LogCvt.info("[忘记登录密码] >> 查询用户是否存在：用户不存在");
			respError(model, ResultCode.notExistUser.getCode(), ResultCode.notExistUser.getMsg());
			return;
		}
		
		CreateSource cs = SimpleUtils.codeToCreateSource(safePojo.getCreateSource());
//		String newPwd = SimpleUtils.decodePwd(safePojo.getNewPwd(), cs);
		
		String newPwd = safePojo.getNewPwd();
//		//校验登录密码规则
//		ResultBean loginPwdResult = safeSupport.validationLoginPwdRule(user.getLoginID(), newPwd);
//		if(!loginPwdResult.isSuccess()){
//			respError(model, loginPwdResult.getMsg());
//			return;
//		}
		
		if (CreateSource.pc.equals(cs) || cs == null) {
            LogCvt.info("[密码加密] >> 渠道：PC，重置支付密码");
            newPwd = userEngineSupport.encryptPwd(newPwd);
            if (StringUtil.isBlank(newPwd)) {
            	LogCvt.info("[密码加密] >> 支付密码加密失败");
            	respError(model, "支付密码加密失败");
				return;
			}
        }
		
		LogCvt.info(String.format("[忘记登录密码] >> 校验短信验证码： code:%s, token:%s", safePojo.getCode(), safePojo.getToken()));
		
		//验证短信与会员手机号是否匹配
		ResultBean validMobile = codeSupport.validMemberMobileSmsRedis(clientId, safePojo.getToken(), user.getMobile());
		if(!validMobile.isSuccess()){
			respError(model, validMobile.getMsg());
			return;
		}
		
		//校验验证码
		boolean codeResult = codeSupport.verifyCodeResult(safePojo.getCode(), safePojo.getToken());
		if(!codeResult){
			LogCvt.info("[忘记登录密码] >> 校验短信验证码失败");
			respError(model, "短信验证码错误");
			return;
		}else{
			LogCvt.info(String.format("[忘记登录密码] >> 校验短信验证码成功，重置登录密码： loginId:%s ", user.getLoginID()));

			ResultBean result = userEngineSupport.resetMemberPwd(user.getMemberCode(), newPwd);
			if(!result.isSuccess()){
				LogCvt.info("[忘记登录密码] >> 重置登录密码失败：" + result.getMsg());
				respError(model, result.getMsg());
				return;
			}else{
				LogCvt.info("[忘记登录密码] >> 重置登录密码成功");
				respSuccess(model, "登录密码重置成功");
				return;
			}
			
		}
		
	}
	
	
	
	
	
	
	/**
	 * 签约银行卡列表
	 *@description 
	 *@author Liebert
	 *@date 2015年4月10日 下午4:07:58
	 */
	@Token
	@RequestMapping(value = "/bankcard/list", method = RequestMethod.GET)
	public void getPaymentChannelList(ModelMap model, HttpServletRequest request, @RequestHeader Long memberCode) {
		String clientId = getClient(request);
		model.putAll(bankCardSupport.getSignCardList(clientId, memberCode));
	}
	
	
	/**
	 * 绑定银行卡
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 上午10:32:49
	 */
	@Token
	@RequestMapping(value = "/bankcard/sign", method = RequestMethod.POST)
	public void signBankCard(ModelMap model, HttpServletRequest request, @RequestBody BankCardPojo bankCardPojo, @RequestHeader Long memberCode){
		String clientId = getClient(request);
		ClientChannelEnum clientChannel = ClientChannelEnum.getClientChannelEnum(clientId);
		LogCvt.info(String.format("[绑定银行卡] >> clientId:%s, memberCode:%s",clientId, memberCode));
		LogCvt.info(String.format("[绑定银行卡] >> 签约信息: \n %s",JSON.toJSONString(bankCardPojo, true)));
		
		//校验短信验证码
		boolean codeResult = codeSupport.verifyCodeResult(bankCardPojo.getCode(),bankCardPojo.getToken());

		if (!codeResult) {
			respError(model, "短信验证码错误");
			return;
		} else {
			//台州银行，需要传入积分卡号
			if (ClientChannelEnum.taizhou == clientChannel) {
				if(StringUtil.isNotBlank(bankCardPojo.getId())){
					model.putAll(bankCardSupport.editPointCard(clientId, memberCode, bankCardPojo.getCardNo(), 
							bankCardPojo.getUname(), bankCardPojo.getIdcard(), bankCardPojo.getPhone(), 
							bankCardPojo.getPointCardNo()));
					return;
				}
				
			}
			model.putAll(bankCardSupport.signBankCard(clientId, memberCode, bankCardPojo.getCardNo(),
					bankCardPojo.getUname(), bankCardPojo.getIdcard(), bankCardPojo.getPhone(),
					bankCardPojo.getSinglePenLimit(), bankCardPojo.getDayLimit(), bankCardPojo.getMonthLimit(), null,
					bankCardPojo.getPointCardNo()));
			return;
		}

	}
	
	
	/**
	 * 解绑银行卡
	 *@description 
	 *@author Liebert
	 *@date 2015年4月9日 上午11:48:28
	 */
	@Token
	@RequestMapping(value = "/bankcard/cancelSign", method = RequestMethod.POST)
	public void cancelSignBankCard(ModelMap model, HttpServletRequest request, @RequestBody BankCardPojo bankCardPojo, @RequestHeader Long memberCode){
		String clientId = getClient(request);
		LogCvt.info(String.format("[解绑银行卡] >> clientId:%s, memberCode:%s",clientId, memberCode));
		
		model.putAll(bankCardSupport.cancelSignBankCard(clientId, memberCode));
	}
	

	
	/**
	 * 查询手机号是否已被使用
	 *@description 
	 *@author Liebert
	 *@date 2015年4月20日 下午3:45:31
	 */
	@Token
	@RequestMapping(value = "/checkUnionMobile", method = RequestMethod.GET)
	public void checkUnionMobile(ModelMap model, HttpServletRequest req, String mobile, @RequestHeader Long memberCode){

		if(StringUtil.isBlank(mobile)){
			respError(model, "手机号为空");
			return;
		}
		
		boolean result = userEngineSupport.queryIsUnionMobileUsed(memberCode, mobile);
		
		model.put("result",result);
		return;
	}

	

	
	
	
}
