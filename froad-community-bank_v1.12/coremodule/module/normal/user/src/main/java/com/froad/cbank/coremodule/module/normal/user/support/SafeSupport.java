package com.froad.cbank.coremodule.module.normal.user.support;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.DESUtil;
import com.froad.cbank.coremodule.framework.common.util.MD5Util;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.util.type.TimeUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.utils.MessageServiceClient;
import com.froad.cbank.coremodule.module.normal.user.utils.RedisKeys;
import com.froad.cbank.coremodule.module.normal.user.utils.ResultBean;
import com.froad.cbank.expand.redis.RedisManager;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.service.MemberSecurityService;

/**
 * 安全中心支持类
 * @description 
 * @author Liebert
 * @date 2015年5月19日 下午1:28:02
 * 
 */
@Service
public class SafeSupport extends BaseSupport {

	@Resource
	private MemberSecurityService.Iface memberSecurityService;
	
	@Resource
	private MemberInformationService.Iface memberInformationService;
	
	@Resource
	private RedisManager redisManager;
	
	
	
	
	/**
	 * 发送申诉请求页的跳转链接邮件
	 * @param emailAddress
	 * @param memberCode
	 * @param createTime
	 */
	public boolean sendMemberAccessAppealEmail(String emailAddress, String clientId, Long memberCode){
		
		//生成邮件内容
		//加密key值 clientId_memberCode_createTime
		String key = clientId + "_" +memberCode + "_" +System.currentTimeMillis();
		String encKey = DESUtil.encrypt(key);
		//邮件跳转链接
		//11.09 修改内容，申诉链接clientId统一改为froad
		String url = MessageFormat.format(com.froad.cbank.coremodule.module.normal.user.utils.Constants.SAFE_APPEAL_URL, clientId) + "?code=" + encKey;
		//邮件内容
		StringBuilder sb = new StringBuilder();
		sb.append("<div style=\" width: 100%; background: #fff;font: 16px/1.5 Tahoma, 'Microsoft Yahei', 'Simsun'; color: #000;\">");
		sb.append("<div style=\" margin: 0 auto; padding: 0 10px;\">");
		sb.append("<h4>尊敬的会员：</h4><p style=\"text-indent:50px;\">您好！请<a style=\"color:#0033ff;\" href=\"");
		sb.append(url);
		sb.append("\">点此链接</a>提交您的相关信息，感谢您对社区银行的关注与支持! 如果您点击上述链接无效，请把下面的链接复制到浏览器的地址栏中： </p><p><em>");
		sb.append("<a style=\"color:#0033ff;\" href=\"" + url + "\">" + url + "</a>");
		sb.append("</em></p><p>此信由系统发出，系统不接受回信，因此请勿直接回复。</p></div><div style=\" margin: 0 auto; padding: 0 10px;\">");
		sb.append("<h4>安全使用您的“账户”</h4><p style=\" text-indent: 50px;\">请不要将您的密码使用在其他网站上。</p>");
		sb.append("<p style=\" text-indent: 50px;\">请不要在电子邮件中输入您的“社区银行账户”密码信息，包括社区银行发送的邮件。</p>");
		sb.append("<p style=\" text-indent: 50px;\">请不要告知任何人您的“社区银行账户”密码信息，包括社区银行的工作人员。</p></div></div>");
		
		//邮件主题
		String subject = "人工处理申诉";
		//发件人名称
		String fromUserName = "services";
		//收件人地址
		List<String> recAddress = new ArrayList<String>();
		recAddress.add(emailAddress);
		
		//发送邮件
		return MessageServiceClient.sendEmail(subject, sb.toString(), fromUserName, recAddress, null);
	}

	
	
	/**
	 * 发送申诉失败邮件
	 * @param emailAddress
	 * @param memberCode
	 * @return
	 */
	public boolean sendAuditRejectedEmail(String emailAddress){
		StringBuilder sb = new StringBuilder();
		sb.append("<div style=\" width: 100%; background: #fff;font: 16px/1.5 Tahoma, 'Microsoft Yahei', 'Simsun'; color: #000;\">");
		sb.append("<div style=\" margin: 0 auto; padding: 0 10px;\">");
		sb.append("<h4>尊敬的会员：</h4><p style=\"text-indent:50px;\">您好！很遗憾通知您，您的人工申述未成功。请检查您所提供的");
		sb.append("信息资料是否正确无误，确认后可再次申请人工申述。 </p>");
		sb.append("<p>此信由系统发出，系统不接受回信，因此请勿直接回复。</p></div><div style=\" margin: 0 auto; padding: 0 10px;\">");
		sb.append("<h4>安全使用您的“账户”</h4><p style=\" text-indent: 50px;\">请不要将您的密码使用在其他网站上。</p>");
		sb.append("<p style=\" text-indent: 50px;\">请不要在电子邮件中输入您的“社区银行账户”密码信息，包括社区银行发送的邮件。</p>");
		sb.append("<p style=\" text-indent: 50px;\">请不要告知任何人您的“社区银行账户”密码信息，包括社区银行的工作人员。</p></div></div>");

		//邮件主题
		String subject = "申诉结果回复";
		//发件人名称
		String fromUserName = "services";
		//收件人地址
		List<String> recAddress = new ArrayList<String>();
		recAddress.add(emailAddress);
		
		//发送邮件
		boolean sendResult = MessageServiceClient.sendEmail(subject, sb.toString(), fromUserName, recAddress, null);
		
		return sendResult;
	}
	
	
	
	
	
	/**
	 * 启动一个线程去发送申诉失败回复邮件
	 * @param email
	 * @param memberCode
	 */
	public void startSendAuditRejectedMail(final String email){
		Thread task = new Thread(
				new Runnable(){
					@Override
					public void run() {
						try {
							//after 30 min
							Thread.sleep(TimeUtil.MINUTE * 30);
							
							LogCvt.info("开始发送申诉失败邮件>>");
							
							boolean flag = sendAuditRejectedEmail(email);
							
							LogCvt.info("开始发送申诉失败邮件>>发送结果：" + flag);
						} catch (InterruptedException e) {
							LogCvt.error("发送邮件出现异常中断",e);
						}
					}
				}
				);
		
		task.start();
	}
	
	
	
	
	
	
	/**
	 * 创建校验安全问题通过授权的令牌</br>
	 * 存入redis</br>
	 * 返回前端作下一步接口检验使用</br>
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public String createQuestionAuthTokenKey(String clientId, Long memberCode){
		String key = RedisKeys.user_safe_verified_question(clientId, memberCode);
		String authToken = MD5Util.getMD5Str(memberCode + "createAuthToken" + System.nanoTime());
		String result = redisManager.putExpire(key, authToken, 60*30);//30分钟过期
		if(!"OK".equals(result)){
			LogCvt.error("创建token redis失败 >> ========= redis缓存数据失败 ==========");
		}
		return authToken;
		
	}
	
	
	/**
	 * 校验修改安全问题的授权令牌
	 * @param clientId
	 * @param memberCode
	 * @param authToken
	 * @return
	 */
	public ResultBean verifyQuestionAuthTokenKey(String clientId, Long memberCode, String authToken){
		ResultBean result = new ResultBean();
		if(StringUtil.isNotBlank(authToken)){
			String key = RedisKeys.user_safe_verified_question(clientId, memberCode);
			String redisToken = redisManager.getString(key);
			if(authToken.equals(redisToken)){
				result.setSuccess(true);
				return result;
			}else{
				LogCvt.error(String.format("校验token redis失败 >> 传入token:%s, 缓存token:%s", authToken, redisToken));
				result.setSuccess(false);
				result.setMsg("authToken检验失败");
				return result;
			}
		}else{
			result.setSuccess(false);
			result.setMsg("authToken为空");
			return result;
		}
	}
	
	/**
	 * 
	 * verifyMobileCodeAuthTokenKey:(校验注册第一个页面校验短信验证码成功的authToken).
	 *
	 * @author wufei
	 * 2015-10-28 下午05:59:46
	 * @param clientId
	 * @param mobile
	 * @param authToken
	 * @return
	 *
	 */
	public ResultBean verifyMobileCodeAuthTokenKey(String clientId, String mobile,String authToken){
		ResultBean result = new ResultBean();
		if(StringUtil.isNotBlank(authToken)){
			String key = RedisKeys.user_regist_verify_mobilecode_success(clientId, mobile);
			String redisToken = redisManager.getString(key);
			if(authToken.equals(redisToken)){
				result.setSuccess(true);
				return result;
			}else{
				LogCvt.error(String.format("校验token redis失败 >> 传入token:%s, 缓存token:%s", authToken, redisToken));
				result.setSuccess(false);
				result.setMsg("authToken检验失败");
				return result;
			}
		}else{
			result.setSuccess(false);
			result.setMsg("authToken为空");
			return result;
		}
	}
	

	public String createVerifyOringinalMobileAuthTokenKey(String clientId, Long memberCode, String mobile){
		String key = RedisKeys.user_safe_verified_original_mobile_success(clientId, memberCode, mobile);
		String authToken = MD5Util.getMD5Str(memberCode + "createAuthToken" + System.nanoTime());
		String result = redisManager.putExpire(key, authToken, 60*30);//30分钟过期
		if(!"OK".equals(result)){
			LogCvt.error("创建token redis失败 >> ========= redis缓存数据失败 ==========");
		}
		return authToken;
		
	}
	

	public ResultBean verifyOringinalMobileAuthTokenKey(String clientId, Long memberCode, String mobile, String authToken){
		ResultBean result = new ResultBean();
		if(StringUtil.isNotBlank(authToken)){
			String key = RedisKeys.user_safe_verified_original_mobile_success(clientId, memberCode, mobile);
			String redisToken = redisManager.getString(key);
			if(authToken.equals(redisToken)){
				result.setSuccess(true);
				return result;
			}else{
				LogCvt.error(String.format("校验token redis失败 >> 传入token:%s, 缓存token:%s", authToken, redisToken));
				result.setSuccess(false);
				result.setMsg("authToken检验失败");
				return result;
			}
		}else{
			result.setSuccess(false);
			result.setMsg("authToken为空");
			return result;
		}
	}
	
	/**
	 * 
	 * @Description: 检验登陆密码规则
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月17日 上午10:36:37
	 * @return
	 */
	public ResultBean validationLoginPwdRule(String loginId, String newPwd){
		ResultBean result = new ResultBean();
		result.setSuccess(false);
		
		//全数字匹配
		String allNumberReg = "^\\d+$";
		//相同字符匹配
		int length=newPwd.length();
		String sameReg = "^(\\S)\\1{5,"+length+"}$";
		//非法字符，空格匹配
		String notSpaceReg = "^\\S+$";

		//校验流程
		if(newPwd.length() < 6 || newPwd.length() > 20){
			result.setMsg("密码长度需6-20位");
			return result;
		}
		if(newPwd.matches(allNumberReg)){
			result.setMsg("密码不能全是数字");
			return result;
		}
		if(newPwd.matches(sameReg)){
			result.setMsg("密码不能是相同的字母或符号");
			return result;
		}
		if(!newPwd.matches(notSpaceReg)){
			result.setMsg("只能包含大小写字母、数字以及符号（除空格）");
			return result;
		}
		if(loginId.indexOf(newPwd) != -1){
			result.setMsg("密码不允许是用户名的一部分");
			return result;
		}
		
		result.setSuccess(true);
		return result;
	}
	
	
	
	
	/**
	 * 
	 * @Description: 检验支付密码规则
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月17日 上午11:16:13
	 * @return
	 */
	public ResultBean validationPayPwdRule(String payPwd){
		ResultBean result = new ResultBean();
		result.setSuccess(false);
		
		//支付密码规则
		String payPwdReg = "^[A-Za-z0-9]{6}$";
		//相同字符匹配
		int length=payPwd.length();
		String sameReg = "^(\\S)\\1{5,"+length+"}$";
		//连续数字
		String continuousNumbers = "0123456789";
		String continuousNumbers1="9876543210";
		
		//校验流程
		if(!payPwd.matches(payPwdReg)){
			result.setMsg("支付密码支持6位数字、字母的组合");
			return result;
		}
		if(payPwd.matches(sameReg)){
			result.setMsg("支付密码不能为相同数字或字母");
			return result;
		}
		if(continuousNumbers.indexOf(payPwd) != -1 || continuousNumbers1.indexOf(payPwd) != -1){
			result.setMsg("支付密码不能是连续数字");
			return result;
		}
		
		result.setSuccess(true);
		return result;
	}

	
	
	
	/**
	 * 创建人工申诉审核通过后可重置安全问题的授权令牌</br>
	 * 存入redis</br>
	 * 返回前端作下一步接口检验使用</br>
	 * @param clientId
	 * @param memberCode
	 * @return
	 */
	public String createAppealResetQuestionAuthToken(Long memberCode){
		String key = RedisKeys.user_safe_appeal_reset_question_auth(memberCode);
		String authToken = MD5Util.getMD5Str(memberCode + "createAuthToken" + System.nanoTime());
		String result = redisManager.putExpire(key, authToken, 60*30);//30分钟过期
		if(!"OK".equals(result)){
			LogCvt.error("创建token redis失败 >> ========= redis缓存数据失败 ==========");
		}
		return authToken;
		
	}
	
	
	
	/**
	 * 校验修改安全问题的授权令牌
	 * @param clientId
	 * @param memberCode
	 * @param authToken
	 * @return
	 */
	public ResultBean verifyAppealResetQuestionAuthToken(Long memberCode, String authToken){
		ResultBean result = new ResultBean();
		if(StringUtil.isNotBlank(authToken)){
			String key = RedisKeys.user_safe_appeal_reset_question_auth(memberCode);
			String redisToken = redisManager.getString(key);
			if(authToken.equals(redisToken)){
				result.setSuccess(true);
				return result;
			}else{
				LogCvt.error(String.format("校验token redis失败 >> 传入token:%s, 缓存token:%s", authToken, redisToken));
				result.setSuccess(false);
				result.setMsg("authToken检验失败");
				return result;
			}
		}else{
			result.setSuccess(false);
			result.setMsg("authToken为空");
			return result;
		}
	}
}
