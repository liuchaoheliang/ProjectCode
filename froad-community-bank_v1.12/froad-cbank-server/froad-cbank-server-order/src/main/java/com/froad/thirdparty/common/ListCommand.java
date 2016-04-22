package com.froad.thirdparty.common;

import java.util.ArrayList;
import java.util.List;

public class ListCommand {

	// ------------------------------------------积分平台
	/**
	 * 查询积分响应
	 */
	public static final List<String> QUERY_POINTS_RESPONSE;

	static {
		QUERY_POINTS_RESPONSE = new ArrayList<String>();
		QUERY_POINTS_RESPONSE.add("partnerAccount.accountMarked");
		// QUERY_POINTS_RESPONSE.add("partnerAccount.accountMarkedType");
		QUERY_POINTS_RESPONSE.add("system.partnerNo");
	}

	/**
	 * 消费积分验签
	 */
	public static final List<String> CONSUME_POINTS;

	static {
		CONSUME_POINTS = new ArrayList<String>();
		CONSUME_POINTS.add("partnerAccount.accountMarked");
		CONSUME_POINTS.add("payPoints.objectNo");
		CONSUME_POINTS.add("payPoints.accountId");
		CONSUME_POINTS.add("payPoints.points");
		CONSUME_POINTS.add("system.partnerNo");
	}

	/**
	 * 消费积分验签
	 */
	public static final List<String> REFUND_POINTS;

	static {
		REFUND_POINTS = new ArrayList<String>();
		REFUND_POINTS.add("partnerAccount.accountMarked");
		REFUND_POINTS.add("refundPoints.objectNo");
		REFUND_POINTS.add("refundPoints.accountId");
		REFUND_POINTS.add("refundPoints.points");
		REFUND_POINTS.add("system.partnerNo");
	}

	/**
	 * 赠送积分验签
	 */
	public static final List<String> DONATE_POINTS;

	static {
		DONATE_POINTS = new ArrayList<String>();
		DONATE_POINTS.add("partnerAccount.accountMarked");
		DONATE_POINTS.add("presentPointsInfo.orgNo");
		DONATE_POINTS.add("presentPointsInfo.points");
		DONATE_POINTS.add("system.partnerNo");
		DONATE_POINTS.add("system.resultCode");
	}

	/**
	 * 兑充积分验签
	 */
	public static final List<String> FILL_POINTS;

	static {
		FILL_POINTS = new ArrayList<String>();
		FILL_POINTS.add("partnerAccount.accountMarked");
		FILL_POINTS.add("orgMember.phone");
		FILL_POINTS.add("system.partnerNo");
	}

	/**
	 * 提现申请验签
	 */
	public static final List<String> CASH_APP;

	static {
		CASH_APP = new ArrayList<String>();
		CASH_APP.add("partnerAccount.accountMarked");
		CASH_APP.add("cashPointsInfo.objectNo");
		CASH_APP.add("cashPointsInfo.points");
		CASH_APP.add("system.partnerNo");
	}

	// ------------------------------------------openapi
	/**
	 * 退款申请验签
	 */
	public static final List<String> REFUND_CURRENCY;

	static {
		REFUND_CURRENCY = new ArrayList<String>();
		REFUND_CURRENCY.add("refundOrderID");
		REFUND_CURRENCY.add("resultCode");
	}

	/**
	 * 校验查询验签
	 */
	public static final List<String> ACCOUNT_CHECK;

	static {
		ACCOUNT_CHECK = new ArrayList<String>();
		ACCOUNT_CHECK.add("resultCode");
	}

	/**
	 * 转账验签
	 */
	public static final List<String> TRANSFER_CURRENCY;

	static {
		TRANSFER_CURRENCY = new ArrayList<String>();
		TRANSFER_CURRENCY.add("transferID");
		TRANSFER_CURRENCY.add("resultCode");
	}

	/**
	 * 代收代扣验签
	 */
	public static final List<String> AGENCY_CURRENCY_DEDUCT;

	static {
		AGENCY_CURRENCY_DEDUCT = new ArrayList<String>();
		AGENCY_CURRENCY_DEDUCT.add("orderID");
		AGENCY_CURRENCY_DEDUCT.add("resultCode");
	}

	/**
	 * 合并订单支付验签
	 */
	public static final List<String> COMBINE_TRANS;

	static {
		COMBINE_TRANS = new ArrayList<String>();
		COMBINE_TRANS.add("resultCode");
		COMBINE_TRANS.add("froadBillNo");
		COMBINE_TRANS.add("orderID");
		COMBINE_TRANS.add("paymentURL");
	}
	
	public static final List<String> WHITE_LIST;

	static {
		WHITE_LIST = new ArrayList<String>();
		WHITE_LIST.add("resultCode");
		WHITE_LIST.add("signType");
	}
	
	public static final List<String> CODE_VERIFY;
	static {
		CODE_VERIFY = new ArrayList<String>();
		CODE_VERIFY.add("verifyResultCode");
		CODE_VERIFY.add("verifyResultContent");
		CODE_VERIFY.add("resultCode");
		CODE_VERIFY.add("signType");
	}
	public static final List<String> AUDIT_STATUS_QUERY;
	static {
		AUDIT_STATUS_QUERY = new ArrayList<String>();
		AUDIT_STATUS_QUERY.add("resultCode");
		AUDIT_STATUS_QUERY.add("signType");
	}
	
	/**
	 * 快捷支付 认证验签
	 */
	public static final List<String> FAST_CARD_SIGN;

	static {
		FAST_CARD_SIGN = new ArrayList<String>();
		FAST_CARD_SIGN.add("resultCode");
		FAST_CARD_SIGN.add("signNo");
		FAST_CARD_SIGN.add("status");
		FAST_CARD_SIGN.add("signType");
	}

	/**
	 * 快捷支付 解约验签
	 */
	public static final List<String> FAST_CARD_SIGN_CANCEL;

	static {
		FAST_CARD_SIGN_CANCEL = new ArrayList<String>();
		FAST_CARD_SIGN_CANCEL.add("resultCode");
		FAST_CARD_SIGN_CANCEL.add("signNo");
		FAST_CARD_SIGN_CANCEL.add("status");
		FAST_CARD_SIGN_CANCEL.add("signType");
	}

	/**
	 * 快捷支付 产生手机验证码
	 */
	public static final List<String> FAST_CREAT_MOBILE_TOCKEN;

	static {
		FAST_CREAT_MOBILE_TOCKEN = new ArrayList<String>();
		FAST_CREAT_MOBILE_TOCKEN.add("resultCode");
		FAST_CREAT_MOBILE_TOCKEN.add("signType");
	}

	/**
	 * 快捷支付 发送手机短信
	 */
	public static final List<String> FAST_SEND_SMS;

	static {
		FAST_SEND_SMS = new ArrayList<String>();
		FAST_SEND_SMS.add("resultCode");
		FAST_SEND_SMS.add("signType");
	}

	/**
	 * 快捷支付 发送手机短信
	 */
	public static final List<String> FAST_SET_LIMIT;

	static {
		FAST_SET_LIMIT = new ArrayList<String>();
		FAST_SET_LIMIT.add("resultCode");
		FAST_SET_LIMIT.add("signType");
	}

	/**
	 * 签约关系通知
	 */
	public static final List<String> NOTIFY_ACCOUNT;

	static {
		NOTIFY_ACCOUNT = new ArrayList<String>();
		NOTIFY_ACCOUNT.add("partnerAccount.accountMarked");
		NOTIFY_ACCOUNT.add("bindAccountInfo.bankId");
		NOTIFY_ACCOUNT.add("bindAccountInfo.bankCard");
		NOTIFY_ACCOUNT.add("system.partnerNo");
		NOTIFY_ACCOUNT.add("system.resultCode");
	}

	/**
	 * 账户充值
	 */
	public static final List<String> DEPOSIT;
	static {
		DEPOSIT = new ArrayList<String>();
		DEPOSIT.add("partnerAccount.accountMarked");
		DEPOSIT.add("system.partnerNo");
		DEPOSIT.add("system.resultCode");
	}

	public static final List<String> QUERY_TRANS;
	static {
		QUERY_TRANS = new ArrayList<String>();
		QUERY_TRANS.add("queryParam.queryType");
		QUERY_TRANS.add("queryParam.queryOrderType");
		QUERY_TRANS.add("queryParam.queryOrderID");
		QUERY_TRANS.add("queryParam.queryOrderState");
		QUERY_TRANS.add("queryParam.queryTime");

		QUERY_TRANS.add("orders[0].orderID");
		QUERY_TRANS.add("orders[0].orderAmount");
		QUERY_TRANS.add("orders[0].stateCode");

		QUERY_TRANS.add("system.resultCode");
		QUERY_TRANS.add("system.partnerID");
	}

	/** 发送验证码 **/
	public static final List<String> SEND_CHECK_CODE;

	static {
		SEND_CHECK_CODE = new ArrayList<String>();
//		SEND_CHECK_CODE.add("partnerAccount.accountMarked");
//		SEND_CHECK_CODE.add("checkResult.mobile");
//		SEND_CHECK_CODE.add("checkResult.mobileType");
//		SEND_CHECK_CODE.add("checkResult.checkCode");
//		SEND_CHECK_CODE.add("checkResult.expireTime");
//		SEND_CHECK_CODE.add("system.partnerNo");
//		SEND_CHECK_CODE.add("system.resultCode");
		SEND_CHECK_CODE.add("system.partnerNo");
		SEND_CHECK_CODE.add("partnerAccount.accountMarked");
		SEND_CHECK_CODE.add("system.resultCode");
		SEND_CHECK_CODE.add("checkResult.mobile");
		SEND_CHECK_CODE.add("checkResult.mobileType");
		SEND_CHECK_CODE.add("checkResult.checkCode");
		SEND_CHECK_CODE.add("checkResult.expireTime");
	}

	/** 校验验证码 **/
	public static final List<String> VALIDATE_CHECK_CODE;

	static {
		VALIDATE_CHECK_CODE = new ArrayList<String>();
		VALIDATE_CHECK_CODE.add("partnerAccount.accountMarked");
		VALIDATE_CHECK_CODE.add("checkResult.checkResult");
		VALIDATE_CHECK_CODE.add("system.partnerNo");
		VALIDATE_CHECK_CODE.add("system.resultCode");
	}

	/** 按手机号查询银行积分 **/
	public static final List<String> QUERY_BANK_POINTS;

	static {
		QUERY_BANK_POINTS = new ArrayList<String>();
		QUERY_BANK_POINTS.add("mobilePhone");
		QUERY_BANK_POINTS.add("orgNo");
		QUERY_BANK_POINTS.add("system.partnerNo");
	}

	/** 查询积分比例 **/
	public static final List<String> QUERY_RATIO;

	static {
		QUERY_RATIO = new ArrayList<String>();
		QUERY_RATIO.add("exchangeRate");
		QUERY_RATIO.add("orgNo");
		QUERY_RATIO.add("system.partnerNo");
	}

	/** 银行账户签约 **/
	public static final List<String> BING_BANK_ACCOUNT;

	static {
		BING_BANK_ACCOUNT = new ArrayList<String>();
		BING_BANK_ACCOUNT.add("partnerAccount.accountMarked");
		BING_BANK_ACCOUNT.add("bindAccountInfo.bankId");
		BING_BANK_ACCOUNT.add("bindAccountInfo.bankCard");
		BING_BANK_ACCOUNT.add("system.partnerNo");
		BING_BANK_ACCOUNT.add("system.resultCode");
	}

	/** 银行账户解约 **/
	public static final List<String> UNBING_BANK_ACCOUNT;

	static {
		UNBING_BANK_ACCOUNT = new ArrayList<String>();
		UNBING_BANK_ACCOUNT.add("partnerAccount.accountMarked");
		UNBING_BANK_ACCOUNT.add("system.partnerNo");
		UNBING_BANK_ACCOUNT.add("system.resultCode");
	}

	/** 用户消费积分分页查询 **/
	public static final List<String> QUERY_MEMBER_PROTOCOL;

	static {
		QUERY_MEMBER_PROTOCOL = new ArrayList<String>();
		QUERY_MEMBER_PROTOCOL.add("system.partnerNo");
		QUERY_MEMBER_PROTOCOL.add("system.resultCode");
	}
	
	public static final List<String> CONTRACTRELATIONSHIPQUERY;
	static{
		CONTRACTRELATIONSHIPQUERY = new ArrayList<String>();
		CONTRACTRELATIONSHIPQUERY.add("partnerAccount.accountMarked");
	}
	
	/** 用户消费积分分页查询 **/
	public static final List<String> QUERY_ORDER_STATUS;
	
	static {
		QUERY_ORDER_STATUS = new ArrayList<String>();
		QUERY_ORDER_STATUS.add("queryParam.queryOrderID");
		QUERY_ORDER_STATUS.add("queryParam.stateCode");
		QUERY_ORDER_STATUS.add("system.partnerNo");
		QUERY_ORDER_STATUS.add("system.resultCode");
	}

}
