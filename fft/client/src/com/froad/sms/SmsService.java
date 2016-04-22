package com.froad.sms;

/** 
 * @author FQ 
 * @date 2013-3-1 下午01:28:22
 * @version 1.0
 * 短信发送
 */
public interface SmsService {
	
	/**
	 * 用户注册
	 * @param mobile   手机号码
	 * @param userName 用户名
	 * @param code     验证码
	 * @return
	 */
	public boolean sendUserRegister(String mobile,String userName,String code); 
	
	/**
	 * 修改手机号码
	 * @param mobile   手机号码
	 * @param code     验证码
	 * @return
	 */
	public boolean sendModifyMobile(String mobile,String code);
	
	/**
	 * 找回密码验证码发送
	 * @param mobile   手机号码
	 * @param code     验证码
	 * @return
	 */
	public boolean sendModifyMobileCode(String mobile,String code);
	
	/**
	 * 忘记密码
	 * @param mobile    手机号码
	 * @param password  临时密码
	 * @return
	 */
	public boolean sendForgePassword(String mobile,String username,String password);
	
	/**
	 * 用户分享
	 * @param mobile     手机号码
	 * @param formMobile 分享人的手机号码
	 * @param name       商户名称或者商品名称
	 * @return
	 */
	public boolean sendUserShare(String mobile,String formMobile,String name);
	
	/**
	 * 团购券
	 * @param mobile             手机号码
	 * @param code               验证码
	 * @param validityStateTime  有期开始时间 
	 * @param validityEndTime    有期结束时间
	 * @param telephone          咨询/预约电话
	 * @return
	 */
	public boolean sendGroupTicket(String mobile,String code,String validityStateTime,String validityEndTime,String telephone);
	
	/**
	 * 兑换券
	 * @param mobile             手机号码
	 * @param goodsName          商品名称
	 * @param code               验证码
	 * @param useValidityEndTime 使用有效结束时间
	 * @param telephone          咨询/预约电话
	 * @return
	 */
	public boolean sendExchangeTicket(String mobile,String goodsName,String code,String useValidityEndTime,String telephone);

	/**
	 * 
	  * 方法描述：手机客户端验证码信息发送
	  * @param: mobile             手机号码
	  * @param: text               短信内容
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-16 下午12:03:39
	 */
	public boolean sendClientMsg(String mobile, String text) ;
}

