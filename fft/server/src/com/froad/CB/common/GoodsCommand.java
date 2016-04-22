package com.froad.CB.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


	/**
	 * 类描述：商品相关的常量
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 14, 2013 3:10:14 PM 
	 */
public class GoodsCommand {

	public static final String PREFIX="com.froad.CB.po.";
	
	public static final HashMap<String, String> CLAZZ_NAMES=new HashMap<String, String>();
	static{//key的前三位代表客户端版本 后两位代表交易类型
		//pc版本
		CLAZZ_NAMES.put("10001", "goodsExchangeRack");
		CLAZZ_NAMES.put("10002", "goodsGroupRack");
		CLAZZ_NAMES.put("10003", "goodsGatherRack");
		CLAZZ_NAMES.put("10004", "goodsCarryRack");
		CLAZZ_NAMES.put("10005", "goodsGatherRack");
		CLAZZ_NAMES.put("10006", "goodsGatherRack");
		CLAZZ_NAMES.put("10008", "goodsPresellRack");
		//android版本
		CLAZZ_NAMES.put("20001", "clientGoodsExchangeRack");
		CLAZZ_NAMES.put("20002", "clientGoodsGroupRack");
		CLAZZ_NAMES.put("20003", "goodsGatherRack");
		CLAZZ_NAMES.put("20004", "clientGoodsCarryRack");
		CLAZZ_NAMES.put("20005", "goodsGatherRack");
		CLAZZ_NAMES.put("20006", "goodsGatherRack");
		CLAZZ_NAMES.put("20008", "goodsPresellRack");
		//ios版本
		CLAZZ_NAMES.put("30001", "clientGoodsExchangeRack");
		CLAZZ_NAMES.put("30002", "clientGoodsGroupRack");
		CLAZZ_NAMES.put("30003", "goodsGatherRack");
		CLAZZ_NAMES.put("30004", "clientGoodsCarryRack");
		CLAZZ_NAMES.put("30005", "goodsGatherRack");
		CLAZZ_NAMES.put("30006", "goodsGatherRack");
		CLAZZ_NAMES.put("30008", "goodsPresellRack");
	}
	
	/**存在销售数量的类**/
	public static final List<String> SALE_NUMBER_CLAZZ_NAME=new ArrayList<String>();
	static{
		//pc版本
		SALE_NUMBER_CLAZZ_NAME.add("goodsGroupRack");
		SALE_NUMBER_CLAZZ_NAME.add("goodsExchangeRack");
		//非pc版本
		SALE_NUMBER_CLAZZ_NAME.add("clientGoodsGroupRack");
		SALE_NUMBER_CLAZZ_NAME.add("clientGoodsExchangeRack");
	}
}
