
package com.froad.cbank.coremodule.module.normal.user.utils;

import java.text.MessageFormat;

/**
 * Redis Keys Collection
 */
public class RedisKeys {

	
	/*  个人版 */
	//安全中心申诉链接数据<key结构user:safe_appeal:clientId:memberCode>
	public static String user_safe_appeal_record(String clientId, Long memberCode) {
		return MessageFormat.format("user:safe_appeal_record:{0}:{1}", clientId,Long.toString(memberCode));
	}
	
	public static String user_safe_appeal_reset_question_auth(Long memberCode) {
		return MessageFormat.format("user:safe_reset_question_auth:{0}", Long.toString(memberCode));
	}
	
	public static String user_safe_reset_question_success(Long memberCode){
		return MessageFormat.format("user:safe_reset_question_success:{0}", Long.toString(memberCode));
	}
	
	
	public static String user_safe_verified_question(String clientId, Long memberCode){
		return MessageFormat.format("user:safe_verify_question:{0}:{1}", clientId, Long.toString(memberCode));
	}
	
	public static String user_safe_verified_original_mobile_success(String clientId, Long memberCode, String mobile){
		return MessageFormat.format("user:safe_verify_original_mobile_success:{0}:{1}:{2}", clientId, Long.toString(memberCode), mobile);
	}
	
	public static String user_regist_verify_mobilecode_success(String clientId, String mobile){
		return MessageFormat.format("user:user_regist_verify_mobilecode_success:{0}:{1}", clientId, mobile);
	}
	
	/**
	 * 缓存手机号发送短信记录，用于校验短信验证码是否和手机号匹配
	 */
	public static String user_send_mobile_sms(String clientId, String smsToken){
		return MessageFormat.format("user:send_mobile_sms:{0}:{1}", clientId, smsToken);
	}
	
	public static String user_member_send_sms(Long memberCode){
		return MessageFormat.format("user:member_send_sms:{0}", memberCode);
	}
	
	public static String user_visitor_send_sms_ip(String ip){
		return MessageFormat.format("user:visitor_send_sms_ip:{0}", ip);
	}
	
	public static String user_visitor_send_sms_mobile(String mobile){
		return MessageFormat.format("user:visitor_send_sms_mobile:{0}", mobile);
	}
}
