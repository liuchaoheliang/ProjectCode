/**
 * Project Name:coremodule-bank
 * File Name:OrderTypeUtil.java
 * Package Name:com.froad.cbank.coremodule.module.normal.bank.util
 * Date:2015年8月26日上午11:08:48
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.cbank.coremodule.module.normal.bank.util;

import java.util.ArrayList;
import java.util.List;

import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVoReq;
import com.froad.thrift.vo.order.QueryOrderByBankManageVo;
import com.froad.thrift.vo.order.QueryOrderByBankManageVoReq;

/**
 * ClassName:OrderTypeUtil Reason: 订单状态转化相关类 Date: 2015年8月26日 上午11:08:48
 * 
 * @author 明灿
 * @version
 * @see
 */
public class OrderTypeUtil {

	/**
	 * 
	 * getOrderStatus:将返回的订单状态转化成对应的展示名称
	 *
	 * @author 明灿 2015年8月28日 下午4:32:55
	 * @param orderStatus
	 *            订单状态码
	 * @return 订单状态
	 *
	 */
	public static String getOrderStatus(String orderStatus) {
		String reslut = "";
		if (orderStatus.equals("1")) {
			reslut = "待支付";
		}
		if (orderStatus.equals("2") || orderStatus.equals("3")) {
			reslut = "订单关闭";
		}
		if (orderStatus.equals("4")) {
			reslut = "支付中";
		}
		if (orderStatus.equals("5")) {
			reslut = "支付失败";
		}
		if (orderStatus.equals("6")) {
			reslut = "已支付";
		}
		return reslut;
	}

	/**
	 * 
	 * getOrderStatus:将请求的订单状态名称转化成对应的转态码
	 *
	 * @author 明灿 2015年8月26日 上午11:33:41
	 * @param orderStatus
	 *            前端状态码
	 * @param req
	 *            server端请求Vo
	 * @return
	 *
	 */
	public static void getOrderStatus(String orderStatus, QueryOrderByBankManageVoReq req) {
		List<String> str = new ArrayList<String>();
		if ("1".equals(orderStatus)) {
			str.add("1");
		}
		if ("5".equals(orderStatus)) {
			str.add("2");
			str.add("3");
		}
		if ("2".equals(orderStatus)) {
			str.add("4");
		}
		if ("4".equals(orderStatus)) {
			str.add("5");
		}
		if ("3".equals(orderStatus)) {
			str.add("6");
		}
		req.setOrderStatus(str);
	}

	/**
	 * 
	 * getOrderStatus:返回server对应的订单状态
	 * 
	 * @author chenmingcan@froad.com.cn 2015年12月15日 下午2:45:07
	 * @param orderStatus
	 * @param req
	 * @return
	 *
	 */
	public static String getOrderStatus(String orderStatus, QueryBoutiqueOrderByBankManageVoReq req) {
		String status = "";
		if ("1".equals(orderStatus)) {
			status = "1";
		}
		if ("5".equals(orderStatus)) {
			status = "2,3";
		}
		if ("2".equals(orderStatus)) {
			status = "4";
		}
		if ("4".equals(orderStatus)) {
			status = "5";
		}
		if ("3".equals(orderStatus)) {
			status = "6";
		}
		return status;
	}

	/**
	 * 
	 * getPresaleStatus:精品预售状态
	 *
	 * @author 明灿 2015年9月7日 上午11:44:59
	 * @param bankOrder
	 * @return
	 *
	 */
	public static String getPresaleStatus(QueryOrderByBankManageVo bankOrder) {
		String status = "";
		// 1:订单创建,2:订单用户关闭,3:订单系统关闭,4:订单支付中,5:订单支付失败,6:订单支付成功
		String orderStatus = bankOrder.getOrderStatus();
		// 1.未消费，2.已消费，3.部分消费,4.已退款,5.退款中
		String consumeStatus = bankOrder.getConsumeStatus();
		// 1. 未退款， 2. 退款中， 3. 退款完成， 4. 部分退款
		String refundState = bankOrder.getRefundState();
		// 0:送货上门,1:网点自提
		String deliveryOption = bankOrder.getDeliveryOption();
		if ("1".equals(orderStatus)) {
			status = "待支付";
		}
		if ("4".equals(orderStatus)) {
			status = "支付中";
		}
		if ("5".equals(orderStatus)) {
			status = "支付失败";
		}
		if ("6".equals(orderStatus)) {
			// 6:成功+自提
			if ("1".equals(deliveryOption)) {
				if ("1".equals(consumeStatus)) {
					status = "未提货";
				}
				if ("2".equals(consumeStatus)) {
					status = "已提货";
				}
				if ("3".equals(consumeStatus)) {
					status = "部分提货";
				}
			} else {
				status = "已支付";
			}
			if ("2".equals(refundState)) {
				status = "退款中";
			}

		}
		// 订单关闭
		if ("2".equals(orderStatus) || "3".equals(orderStatus)) {
			if ("3".equals(refundState)) {
				status = "退款成功";
			} else {
				status = "订单关闭";
			}
		}
		return status;
	}

	/**
	 * 
	 * getFamousPreferentStatus:名优特惠状态
	 *
	 * @author 明灿 2015年9月7日 上午11:44:51
	 * @param bankOrder
	 * @param type
	 * @return
	 *
	 */
	public static String getFamousPreferentStatus(QueryOrderByBankManageVo bankOrder, String type) {
		String status = "";
		// 1:订单创建,2:订单用户关闭,3:订单系统关闭,4:订单支付中,5:订单支付失败,6:订单支付成功
		String orderStatus = bankOrder.getOrderStatus();
		// 配送状态 0:未发货,1:已发货,2:已收货,3:未提货,4:已提货
		String deliveryState = bankOrder.getDeliveryState();
		String refundState = bankOrder.getRefundState();
		if ("1".equals(orderStatus)) {
			status = "待支付";
		}
		if ("4".equals(orderStatus)) {
			status = "支付中";
		}
		if ("5".equals(orderStatus)) {
			status = "支付失败";
		}
		if ("6".equals(orderStatus)) {
			if ("0".equals(deliveryState)) {
				status = "待发货";
			} else if ("1".equals(deliveryState)) {
				status = "待确认收货";
			} else if ("2".equals(deliveryState)) {
				status = "已确认收货";
			} else {
				status = "已支付";
			}
		}
		// 订单关闭
		if ("2".equals(orderStatus) || "3".equals(orderStatus)) {
			if ("3".equals(refundState)) {
				status = "退款成功";
			} else {
				status = "订单关闭";
			}
		}
		return status;
	}
}
