package com.froad.enums;

/**
 * 短信类型
 * @author  <mailto>wuhelian@f-road.com.cn</mailto>
 *
 */
public enum SmsType {
	
	/**
	 * 说明：定义系统用到的短信类型分类，用于直接对接消息系统接口
	 * 短信类型：
	   -1                       -图片验证码
		1000~1099:PAYMENT 		- 1支付验证码  PAYMENT
		1100~1199:REMINDER 		- 2消息提醒    REMINDER
		1200~1299:REGISTRATION 	- 3注册验证码  REGISTRATION
		1300~1399:VERIFICATION 	- 4普通验证码 VERIFICATION
		1400~1499:ANNOUNCEMENT 	- 5业务通告   ANNOUNCEMENT
		1500~1599:MARKETING 	- 6营销短信  MARKETING
		1600~1699:BULK 			- 7批量短信   BULK

	 */
	/**
	 * 图片验证码
	 */
	image(-1,"","图片验证码"),
	
	/**
	 * 用户模块
	 */
	registerNewUser(1000,"REGISTRATION","注册新用户"),
	setPaymentPwdSucc(1100,"REMINDER","设置支付密码"),
	authResetPwd(1001,"VERIFICATION","用户重置密码短信验证码"),
	authUpdatePayPwd(1002,"VERIFICATION","会员修改支付密码"),
	bindMobile(1300,"VERIFICATION","会员绑定手机号"),
	authBackMobile(1302,"VERIFICATION","会员更换手机号码"),
	authBackPayPwd(1303,"VERIFICATION","会员找回支付密码"),
	authcodeUpdateLoginPwd(1304,"VERIFICATION","用户修改登录密码"),
	UnionLoginBindMobile(1306,"VERIFICATION","联合登录绑定手机号"),
	codeforgetPwd(1307,"VERIFICATION","忘记密码短信验证码"),
	bankcoderegister(1308,"VERIFICATION","银行卡签约验证码"),
	fastPaySignCode(1305,"PAYMENT","支付验证码"),
	bossAbsolvedMobile(1310,"REMINDER","boss解绑手机号提示"),
	bossBandMobile(1311,"REMINDER","boss绑定手机号提示"),
	openVipReminder(1312,"REMINDER","开通VIP提醒"),
	/**
	 * 商户模块
	 */
	memberForgetLoginPwd(1301,"VERIFICATION","商户找回登录密码验证码"),
	merchantAddUser(1104,"REMINDER","商户商户总店新增管理员通知"),
	merchantResetLoginPwd(1106,"REMINDER","商户重置登录密码成功提示"),
	merchantResetLoginPwd1(1105,"REMINDER","商户重置登录密码成功提示"),
	
	/**
	 * 订单
	 */
	presellDelivery(1101,"REMINDER","预售发送提货码"),
	presellDeliveryRemind(1102,"REMINDER","到达提货期"),
	privilegeDelivery(1103,"REMINDER","特惠订单发送消费码"),
	groupTicket(1108,"REMINDER","团购发劵提示"),
	presell(1109,"REMINDER","预售预订成功提示"),
	offPointsBankSecurityCode(1309,"VERIFICATION","线下积分兑换银行验证码"),

	/**
	 * 微信银行
	 */
	WxregisterNewUser(1200,"REGISTRATION","注册新用户"),
	WxbindUser(1313,"VERIFICATION","绑定已有手机号"),
	WxsetPaymentPwdSucc(1112,"REMINDER","设置支付密码"),
	WxauthUpdatePayPwd(1314,"VERIFICATION","修改支付密码"),
	WxauthBackPayPwd(1315,"VERIFICATION","会员找回支付密码"),
	WxsetSafePwd(1316,"VERIFICATION","设置安全密码"),
	WxSetPayCard(1317,"VERIFICATION","设置支付卡"),
	Wxbankcoderegister(1318,"VERIFICATION","微信银行卡绑定验证码"),
//	WxSTKregister(1319,"VERIFICATION","微信贴膜卡绑定验证码"),
//	WxSTKToBankregister(1320,"VERIFICATION","贴膜卡转账验证码"),
	
	pwdErrorLogin(1110,"REMINDER","登录密码错误5次提示"),
	pwdErrorPay(1111,"REMINDER","支付密码错误5此提示"),
	
	
	
	
//	
//	autoRegisterSuccess("1000","自动注册成功短信"),
//	authcodeRegister("1001","用户注册短信验证码"),
//	authcodeResetPwd("1002","用户重置密码短信验证码"),
//	authcodeModifiedMobile("1003","用户修改手机号码短信验证码"),
//	presellDelivery("1004","精品预售提货短信"),
//	presellRefund("1005","精品预售退款短信"),
//    presellClusterFail("1006","精品预售不成团短信"),
//    presellReturnSale("1007","精品预售申请退货"),
//	presellBookSuccess("1008","精品预售预订成功"),
//    group("1009","团购"),
//    exchange("1010","积分兑换"),
//    image("-1","图片验证码"),
//	
//   // ----PAYMENT 支付验证码---
//    fastPaySignCode("1011","快捷支付签约验证码"),
//	fastPayVerifyCode("1012","快捷支付付款短信验证码"),
//	fastPaySetPwdCode("1013","快捷支付设置支付密码短信验证码"),
//	fastPayUpdatePwdCode("1014","快捷支付修改支付密码短信验证码"),
//	fastPayGetBackPwdCode("1015","快捷支付找回支付密码短信验证码"),
//	fastPaySetQuestionCode("1016","快捷支付设置安全问题短信验证码"),
//	
//	//商户用户忘记密码短信
//	authcodeforget("1017","商户用户忘记密码短信验证码"),
//	hfcz_success("1018","话费充值成功"),
//	paysuccess("1019","支付成功送积分"),
//	prefer_pay_success("1020","惠购自提支付成功"), //针对选择商家自提类型的
//	prefer_book_success("1021","惠购提货短信"),
//	authentic_securities_success("1022","兑换码认证成功短信"),
//	points_card_cz_success("1023","积分卡充值成功短信"),
//	
//	
//	//----验证码
//	advancePresellDelivery("1024","精品预售提前发码短信"),
//	fixPresell("1025","修复重发预售商品提货短信"),
//	preferDistribution("1026","惠购配送支付成功"),   //针对选择送货上门类型的
//	
//	//---银行校验短信码
//	offPointsBankSecurityCode("1027","线下积分兑换银行验证码"),
//	merchantAuthcodeResetPwd("1028","商户用户密码重置通知"),
//	merchantAuthcodePwd("1029", "商户总店管理员密码通知")
	;
	
	private int code;
	private String bizType;
	private String describe;
	
	private SmsType(int code,String bizType,String describe){
		this.code = code;
		this.bizType = bizType;
		this.describe = describe;
	}
	
	public static SmsType getByCode(int code){
		if(code != 0){
			for(SmsType st : values()){
				if(st.getCode()== code){
					return st;
				}
			}
		}
		return null;
	}
	/**
	 *  按短信种类获取业务类型
	  * @Title: getBizType
	  * @Description: TODO
	  * @author: share 2015年3月31日
	  * @modify: share 2015年3月31日
	  * @param @param code
	  * @param @return    
	  * @return String    
	  * @throws
	 */
	public static String getBizType(int code){
		if(code != 0){
			for(SmsType st : values()){
				if(st.getCode() == code){
					return st.getBizType();
				}
			}
		}
		return null;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}
}
