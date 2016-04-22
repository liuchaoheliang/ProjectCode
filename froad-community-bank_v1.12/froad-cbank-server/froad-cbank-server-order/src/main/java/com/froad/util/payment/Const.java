package com.froad.util.payment;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.enums.ModuleID;
import com.froad.thirdparty.common.OpenApiCommand;
import com.froad.thirdparty.common.PointsCommand;
import com.froad.thirdparty.dto.request.points.PointsReq.QueryOrderType;
import com.froad.util.MongoTableName;
import com.froad.util.payment.TimeHelper.TimeType;

/**
 * 常量值维护
* <p>Function: Const</p>
* <p>Description: </p>
* @author zhaoxy@thankjava.com
* @date 2015-5-20 下午3:49:29
* @version 1.0
 */
public class Const {

	public final static long POINT_NEW_RULE = TimeHelper.parseDate("2015-11-26|23:59:59", TimeType.DEFAULT).getTime();
	
	/**
	 * 支付流水表表名
	 */
	public final static String COLLECTION_PAYMENT_NAME = MongoTableName.CB_PAYMENT;
	
	/**
	 * 支付模块ID
	 */
	public final static ModuleID PAYMENT_MODULE_ID = ModuleID.payment;
	
	/**
	 * 货币（现金&积分值）精度系数
	 */
	public final static int HDOP_1000 = 1000;
	
	/**
	 * 方付通积分机构号
	 */
	public final static String FROAD_POINT_ORG_NO = PointsCommand.FROAD_ORG_NO;
	
	/**
	 * 请求成功代码
	 */
	public final static String SUCCESS_CODE = "0000";
	
	/**
	 * OpenAPI PC 订单类型
	 */
	public final static String OPENAPI_ORDER_SOURCES_CLIENT_PC = "100";
	
	public final static String OPENAPI_ORDER_TYPE_QUERY_SINGLE = OpenApiCommand.QUERY_SINGLE;
	
	public final static String OPENAPI_ORDER_TYPE_TRANS = OpenApiCommand.ORDER_TYPE_TRANS;
	
	public final static String OPENAPI_ORDER_TYPE_REFUND = OpenApiCommand.ORDER_TYPE_REFUND;

	public final static String POINTS_ORDER_TYPE_TRANS = QueryOrderType.consume.getCode();
	
	public final static String POINTS_ORDER_TYPE_REFUND = QueryOrderType.refund.getCode();
	
	public final static String POINTS_ORDER_TYPE_GIVE = QueryOrderType.give.getCode();
	
	
	/**
	 * 获取转意的内容
	* <p>Function: getSpecialRemarkValue</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-6-24 下午3:21:13
	* @version 1.0
	* @param key
	* @return
	 */
	public static String getSpecialRemarkValue(String key){return specialRemark.get(key);};
	
	private static Map<String, String> specialRemark = new HashMap<String, String>();
	private static Map<String, String> requestSuffix = new HashMap<String, String>();
	
	static{
		specialRemark.put("账户已销户", "1001");  //支付时银行返回该错误 转意为错误码
	
		//-------请求后缀数据初始-------
		requestSuffix.put("taizhou_" + RequestType.C_CONSUME.name() + "_" + RequestCashType.FOIL_CARD.name(), "本地惠支付订单");
	}
	

	enum RequestType{
		C_CONSUME,//现金消费
		C_REFUND,//现金退款
		C_SETTLEMENT,//现金结算
		P_CONSUME,//积分消费
		P_REFUND,//积分退款
		UNDEFINE//不指定
	}
	enum RequestCashType{
		BANK_FAST,
		FOIL_CARD,
		TIMELY_PAY,
		UNDEFINE//不指定
	}
	
	public static String getRequestSuffix (String clientId,RequestType requestType,RequestCashType requestCashType,String defaultSuffix){
		String suffix = requestSuffix.get(clientId + "_" + requestType.name() + "_" + requestCashType.name());
		return StringUtils.isEmpty(suffix) ? defaultSuffix : suffix;
	}
	
}
