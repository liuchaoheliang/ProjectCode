package com.froad.cbank.coremodule.normal.boss.enums;

/**
 * 短信验证码调用
 */
public enum SmsTypeEnum implements org.apache.thrift.TEnum {
  /**
   * 图片验证码
   */
  image(-1),
  /**
   * 注册新用户
   */
  registerNewUser(1000),
  /**
   * 设置支付密码
   */
  setPaymentPwdSucc(1100),
  /**
   * 用户重置密码短信验证码
   */
  authResetPwd(1001),
  /**
   * 会员修改支付密码
   */
  authUpdatePayPwd(1002),
  /**
   * 会员绑定手机号
   */
  bindMobile(1300),
  /**
   * 会员更换手机号码
   */
  authBackMobile(1302),
  /**
   * 会员找回支付密码
   */
  authBackPayPwd(1303),
  /**
   * 用户修改登录密码
   */
  authcodeUpdateLoginPwd(1304),
  /**
   * 联合登录绑定手机号
   */
  UnionLoginBindMobile(1306),
  /**
   * 忘记密码短信验证码
   */
  codeforgetPwd(1307),
  /**
   * 银行卡签约验证码
   */
  bankcoderegister(1308),
  /**
   * 支付验证码
   */
  fastPaySignCode(1305),
  /**
   * 商户找回登录密码验证码
   */
  memberForgetLoginPwd(1301),
  /**
   * 商户商户总店新增管理员通知
   */
  merchantAddUser(1104),
  /**
   * 商户重置登录密码成功提示
   */
  merchantResetLoginPwd(1106),
  /**
   * 预售发送提货码
   */
  presellDelivery(1101),
  /**
   * 到达提货期
   */
  presellDeliveryRemind(1102),
  /**
   * 特惠订单发送消费码
   */
  privilegeDelivery(1103),
  /**
   * 团购发劵提示
   */
  groupTicket(1108),
  /**
   * 预售预订成功提示
   */
  presell(1109),
  /**
   * 线下积分兑换银行验证码
   */
  offPointsBankSecurityCode(1309),
  /**
   * boss解绑手机号提示
   */
  bossAbsolvedMobile(1310),
  /**
   * boss绑定手机号提示
   */
  bossBandMobile(1311),
  /**
   * 开通VIP提醒
   */
  openVipReminder(1312),
  /**
   * 登录密码错误5次提示
   * */
  pwdErrorLogin(1110),
  /**
   * 支付密码错误5此提示
   * */
  pwdErrorPay(1111);

  private final int value;

  private SmsTypeEnum(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static SmsTypeEnum findByValue(int value) { 
    switch (value) {
      case -1:
        return image;
      case 1000:
        return registerNewUser;
      case 1100:
        return setPaymentPwdSucc;
      case 1001:
        return authResetPwd;
      case 1002:
        return authUpdatePayPwd;
      case 1300:
        return bindMobile;
      case 1302:
        return authBackMobile;
      case 1303:
        return authBackPayPwd;
      case 1304:
        return authcodeUpdateLoginPwd;
      case 1306:
        return UnionLoginBindMobile;
      case 1307:
        return codeforgetPwd;
      case 1308:
        return bankcoderegister;
      case 1305:
        return fastPaySignCode;
      case 1301:
        return memberForgetLoginPwd;
      case 1104:
        return merchantAddUser;
      case 1106:
        return merchantResetLoginPwd;
      case 1101:
        return presellDelivery;
      case 1102:
        return presellDeliveryRemind;
      case 1103:
        return privilegeDelivery;
      case 1108:
        return groupTicket;
      case 1109:
        return presell;
      case 1309:
        return offPointsBankSecurityCode;
      default:
        return null;
    }
  }
}
