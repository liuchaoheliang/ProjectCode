package com.froad.util.froadMap;

import java.util.HashMap;
import java.util.Map;

public class CommandMap {
	public static final Map<String, String> TRAN_STATUS;
	public static final Map<String, String> TRAN_STATUS_SHOW;
	
	static {
		TRAN_STATUS = new HashMap<String, String>();
		TRAN_STATUS.put("0", "交易成功");
		TRAN_STATUS.put("1", "等待买家付款");
		TRAN_STATUS.put("2", "等待卖家发货 ");
		TRAN_STATUS.put("3", "交易关闭");
		TRAN_STATUS.put("4", "发送支付请求成功");
		TRAN_STATUS.put("5", "发送支付请求失败");
		TRAN_STATUS.put("6", "取消支付请求成功");
		TRAN_STATUS.put("7", "取消支付请求失败");
		TRAN_STATUS.put("8", "退款成功");
		TRAN_STATUS.put("9", "退款失败");
		TRAN_STATUS.put("10", "请求发货成功");
		TRAN_STATUS.put("11", "请求发货失败");
		TRAN_STATUS.put("12", "第三方发货失败");
		TRAN_STATUS.put("13", "支付失败");
		TRAN_STATUS.put("14", "预订成功");
		TRAN_STATUS.put("15", "预订失败");
		TRAN_STATUS.put("16", "申请退款");
		TRAN_STATUS.put("17", "请求发货超时");
		TRAN_STATUS.put("18", "请求取消订单成功");
		TRAN_STATUS.put("19", "请求取消订单失败");
		TRAN_STATUS.put("20", "异常");
		TRAN_STATUS.put("21", "异常处理中");
		TRAN_STATUS.put("22", "申请退货");
		TRAN_STATUS.put("23", "同意退货");
		TRAN_STATUS.put("24", "卖家已发货");
		TRAN_STATUS.put("25", "同意退款");
		TRAN_STATUS.put("26", "退货发货中");
		TRAN_STATUS.put("27", "退货成功");
		TRAN_STATUS.put("28", "等待系统确认订单");
		TRAN_STATUS.put("29", "发送退款请求成功");
		TRAN_STATUS.put("30", "发送退款请求失败");
		TRAN_STATUS.put("31", "消费积分成功");
		TRAN_STATUS.put("32", "消费积分异常");
		TRAN_STATUS.put("33", "消费积分失败");
		TRAN_STATUS.put("34", "退还积分成功");
		TRAN_STATUS.put("35", "退还积分异常");
		TRAN_STATUS.put("36", "退还积分失败");
		TRAN_STATUS.put("99", "赠送积分失败");
		
		TRAN_STATUS_SHOW = new HashMap<String, String>();
		TRAN_STATUS_SHOW.put("0", "交易成功");
		TRAN_STATUS_SHOW.put("1", "等待买家付款");
		TRAN_STATUS_SHOW.put("2", "等待卖家发货 ");
		TRAN_STATUS_SHOW.put("3", "交易关闭");
		TRAN_STATUS_SHOW.put("4", "发送支付请求成功");
		TRAN_STATUS_SHOW.put("5", "发送支付请求失败");
		TRAN_STATUS_SHOW.put("8", "退款成功");
		TRAN_STATUS_SHOW.put("9", "退款失败");
		TRAN_STATUS_SHOW.put("10", "请求发货成功");
		TRAN_STATUS_SHOW.put("11", "请求发货失败");
		TRAN_STATUS_SHOW.put("12", "第三方发货失败");
		TRAN_STATUS_SHOW.put("13", "支付失败");
		TRAN_STATUS_SHOW.put("14", "预订成功");
		TRAN_STATUS_SHOW.put("15", "预订失败");
		TRAN_STATUS_SHOW.put("16", "申请退款");
		TRAN_STATUS_SHOW.put("18", "请求取消订单成功");
		TRAN_STATUS_SHOW.put("19", "请求取消订单失败");
		TRAN_STATUS_SHOW.put("20", "异常");
		TRAN_STATUS_SHOW.put("22", "申请退货");
		TRAN_STATUS_SHOW.put("23", "同意退货");
		TRAN_STATUS_SHOW.put("24", "卖家已发货");
		TRAN_STATUS_SHOW.put("25", "同意退款");
		TRAN_STATUS_SHOW.put("26", "退货发货中");
		TRAN_STATUS_SHOW.put("27", "退货成功");
		TRAN_STATUS_SHOW.put("28", "等待系统确认订单");
		TRAN_STATUS_SHOW.put("29", "发送退款请求成功");
		TRAN_STATUS_SHOW.put("30", "发送退款请求失败");
		TRAN_STATUS_SHOW.put("31", "消费积分成功");
		TRAN_STATUS_SHOW.put("32", "消费积分异常");
		TRAN_STATUS_SHOW.put("33", "消费积分失败");
		TRAN_STATUS_SHOW.put("34", "退还积分成功");
		TRAN_STATUS_SHOW.put("35", "退还积分异常");
		TRAN_STATUS_SHOW.put("36", "退还积分失败");
	}
}
