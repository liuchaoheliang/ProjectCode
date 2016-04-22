package com.froad.util.refund;

/**
 * 代理用户发起退款流程的退款类型
 * @author Zxy
 *
 */
public enum ProxyRefundType {

	/**
	 * VIP资格开通失败，代理用户自动发起退款
	 */
	VIP_ORDER_AUTH_FAILED,
	
	/**
	 * 用户申请退款
	 */
	VIP_ORDER_REFUND,
}
