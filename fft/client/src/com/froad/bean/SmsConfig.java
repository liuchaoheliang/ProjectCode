package com.froad.bean;
/** 
 * @author FQ 
 * @date 2013-2-28 下午05:20:01
 * @version 1.0
 * 
 * 短信配置
 */
public class SmsConfig {
	
	public static final String USER_REGISTER = "userRegister";//用户注册
	public static final String MODIFY_MOBILE = "modifyMobile";//修改手机号码
	public static final String MODIFY_MOBILE_CODE = "findPwdCode";//忘记密码验证码
	public static final String FORGET_PASSWORD = "forgetPassword";//忘记密码
	public static final String USER_SHARE = "userShare";//用户分享
	public static final String GROUP_TICKET = "groupTicket";//团购券
	public static final String EXCHANGE_TICKET = "exchangeTicket";//兑换券
	
	private String name;//名称
	private String description;// 描述
	private String templateFilePath;// 模板文件路径
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTemplateFilePath() {
		return templateFilePath;
	}
	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}
	
}
