package com.froad.CB.common.constant;


	/**
	 * 类描述：异常交易的类型(仅用于查询异常交易)
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: May 23, 2013 6:51:23 PM 
	 */
public enum ExceptionTransType {
	ALL_REFUND_POINTS,//所有的退积分情况
	REFUND_POINTS,//退积分(退失败)
	REFUND_TIMEOUT_POINTS,//退积分(超时)
	REFUND,//退款
	DEDUCT_REBATE,//代扣+返积分
	REBATE//返积分
}
