package com.froad.fft.thirdparty.common;

import java.util.ArrayList;
import java.util.List;

public class ListCommand {
	
	
	
	//------------------------------------------积分平台
	/**
	 * 查询积分响应
	 */
	public static final List<String> QUERY_POINTS_RESPONSE ;	
	static {
		QUERY_POINTS_RESPONSE = new ArrayList<String>();
		QUERY_POINTS_RESPONSE.add("partnerAccount.accountMarked");
//		QUERY_POINTS_RESPONSE.add("partnerAccount.accountMarkedType");
		QUERY_POINTS_RESPONSE.add("system.partnerNo");
	}
	
	
	/**
	 * 消费积分验签
	 */
	public static final List<String> CONSUME_POINTS;	
	static{
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
	static{
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
	static{
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
	static{
		FILL_POINTS = new ArrayList<String>();
		FILL_POINTS.add("partnerAccount.accountMarked");
		FILL_POINTS.add("orgMember.phone");
		FILL_POINTS.add("system.partnerNo");
	}
	
	/**
	 * 提现申请验签
	 */
	public static final List<String> CASH_APP;
	static{
		CASH_APP = new ArrayList<String>();
		CASH_APP.add("partnerAccount.accountMarked");
		CASH_APP.add("cashPointsInfo.objectNo");
		CASH_APP.add("cashPointsInfo.points");
		CASH_APP.add("system.partnerNo");
	}

	//------------------------------------------openapi
	/**
	 * 退款申请验签
	 */
	public static final List<String> REFUND_CURRENCY;
	static{
		REFUND_CURRENCY = new ArrayList<String>();
		REFUND_CURRENCY.add("refundOrderID");
		REFUND_CURRENCY.add("resultCode");
	}
	
	/**
	 * 校验查询验签
	 */
	public static final List<String> ACCOUNT_CHECK;
	static{
		ACCOUNT_CHECK = new ArrayList<String>();
		ACCOUNT_CHECK.add("resultCode");
	}
	
	/**
	 * 转账验签
	 */
	public static final List<String> TRANSFER_CURRENCY;
	static{
		TRANSFER_CURRENCY = new ArrayList<String>();
		TRANSFER_CURRENCY.add("transferID");
		TRANSFER_CURRENCY.add("resultCode");
	}
	
	/**
	 * 代收代扣验签
	 */
	public static final List<String> AGENCY_CURRENCY_DEDUCT;
	static{
		AGENCY_CURRENCY_DEDUCT = new ArrayList<String>();
		AGENCY_CURRENCY_DEDUCT.add("orderID");
		AGENCY_CURRENCY_DEDUCT.add("resultCode");
	}
	
	/**
	 * 合并订单支付验签
	 */
	public static final List<String> COMBINE_TRANS;
	static{
		COMBINE_TRANS = new ArrayList<String>();
		COMBINE_TRANS.add("orderID");
		COMBINE_TRANS.add("paymentURL");
		COMBINE_TRANS.add("froadBillNo");
		COMBINE_TRANS.add("resultCode");
	}
}
