package com.froad.po.order;

import java.util.HashMap;
import java.util.Map;

public class OrderConstants {
	
	//商户管理平台
	/**
	 * 商户管理平台-团购-订单导出-标题栏
	 */
	public static final String[] MERCHANT_PC_GROUP_ORDER_EXPORT_TITLES = new String[]{"序号","订单号","提交日期","总金额","商品名称","业务类型","订单状态","结算状态","提货人信息"};

	/**
	 * 商户管理平台-名优特惠-订单导出-标题栏
	 */
	public static final String[] MERCHANT_PC_SPECIAL_ORDER_EXPORT_TITLES = new String[]{"序号","订单号","提交日期","总金额","商品名称","业务类型","订单状态","结算状态","收货人信息","收货地址"};
	
	/**
	 * 商户管理平台-面对面-订单导出-标题栏
	 */
	public static final String[] MERCHANT_PC_F2F_ORDER_EXPORT_TITLES = new String[]{"序号","订单号","提交日期","总金额","商品名称","业务类型","订单状态","结算状态","操作人"};
    
	/**
	 * 业务类型-MAP
	 */
	public static final Map<String, String> BUSINESS_TYPE_MAP = new HashMap<String, String>() {
		{
			put("0", "面对面");
			put("1", "团购");
			put("3", "名优特惠");
		}
	};
	
	/**
	 * 订单状态-MAP
	 */
	public static final Map<String, String> ORDER_STATUS_MAP = new HashMap<String, String>() {
		{
			put("1", "待支付");
			put("2", "已关闭");
			put("3", "已关闭");
			put("4", "支付中");
			put("5", "支付失败");
			put("6", "已支付");
		}
	};
	
	/**
	 * 订单状态-MAP
	 */
	public static final Map<String, String> ANHUI_ORDER_STATUS_MAP = new HashMap<String, String>() {
		{
			put("1", "等待付款");
			put("2", "已取消");
			put("3", "已关闭");
			put("4", "支付中");
			put("5", "订单支付失败");
			put("6", "交易完成");
		}
	};
	
	/**
	 * 结算状态MAP
	 */
	public static final Map<String, String> SETTLEMENT_STATUS_MAP = new HashMap<String, String>() {
		{
			put("0", "未结算");
			put("1", "结算中");
			put("2", "已结算");
			put("3", "结算失败");
			put("4", "无效结算记录");
		}
	};
	
	
	//银行管理平台
	/**
	 * 银行管理平台-团购-订单导出-标题栏
	 */
	public static final String[] BANK_PC_GROUP_ORDER_EXPORT_TITLES = new String[]{"序号","订单编号","支付方式","订单状态","下单时间","订单金额","所属机构","商户","商品","销售数量"};
	
	/**
	 * 银行管理平台-精品预售（配送）-订单导出-标题栏
	 */
	public static final String[] BANK_PC_PRESELL_SHIP_ORDER_EXPORT_TITLES = new String[]{"序号","订单编号","支付方式","订单状态","下单时间","配送方式","所属机构","商品名称","销售数量","商品金额","收货人","手机号"};
	
	/**
	 * 银行管理平台-精品预售（自提）-订单导出-标题栏
	 */
	public static final String[] BANK_PC_PRESELL_DELIVERY_ORDER_EXPORT_TITLES = new String[]{"序号","订单编号","支付方式","订单状态","下单时间","配送方式","所属机构","商品名称","销售数量","商品金额","提货人","手机号","提货状态"};
	
	/**
	 * 银行管理平台-名优特惠-订单导出-标题栏
	 */
	public static final String[] BANK_PC_SPECIAL_ORDER_EXPORT_TITLES = new String[]{"序号","订单编号","支付方式","订单状态","下单时间","商品金额","商户","商品","销售数量","发货状态","收货人姓名","地址","手机"};
	
	/**
	 * 银行管理平台-积分兑换-订单导出-标题栏
	 */
	public static final String[] BANK_PC_POINT_ORDER_EXPORT_TITLES = new String[]{"序号","订单编号","子订单号","支付方式","订单状态","下单时间","商品名称","销售数量","积分","所属机构","操作员"};
	
	/**
	 * 银行管理平台-面对面-订单导出-标题栏
	 */
	public static final String[] BANK_PC_F2F_ORDER_EXPORT_TITLES = new String[]{"序号","订单编号","支付方式","下单时间","所属机构","商户名称","订单状态","订单金额","结算状态","手机号","操作员"};
	
	
	/**
	 * 银行管理平台-精品商城-订单导出-标题栏
	 */
	public static final String[] BANK_PC_BOUTIQUE_ORDER_EXPORT_TITLES = new String[]{"序号","订单编号","支付方式","订单状态","下单时间","订单金额","供应商","商品名称","销售数量"};
	
	
	
	
	
	/**
	 * 支付方式-MAP
	 */
	public static final Map<String, String> PAYMENT_METHOD_MAP = new HashMap<String, String>() {
		{
			put("1", "待支付");
			put("2", "已关闭");
			put("3", "已关闭");
			put("4", "支付中");
			put("5", "支付失败");
			put("6", "已支付");
		}
	};
}
