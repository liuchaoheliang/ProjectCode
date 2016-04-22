package com.froad.util.froadMap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class HotelMap {

	public static final Map<String, String> HotelStatusMap;
	static {
		HotelStatusMap = new HashMap<String, String>();

		HotelStatusMap.put("0", "新需求单");
		HotelStatusMap.put("1","处理中");
		HotelStatusMap.put("2","预订失败");
		HotelStatusMap.put("3","无效");
		HotelStatusMap.put("4","已取消");
		HotelStatusMap.put("5","预订成功");
	}
	
	public static final Map<String, String> FroadHotelStatusMap=new HashMap<String, String>();
	static{
		FroadHotelStatusMap.put("19", "待确认");
		FroadHotelStatusMap.put("20", "预订成功");
		FroadHotelStatusMap.put("21", "预订失败");
		FroadHotelStatusMap.put("22", "取消");
		FroadHotelStatusMap.put("23", "手工单");
		FroadHotelStatusMap.put("24", "修改");
		FroadHotelStatusMap.put("25", "应到未到");
		FroadHotelStatusMap.put("26", "待定");
		FroadHotelStatusMap.put("27", "已入住");
		FroadHotelStatusMap.put("28", "已离店");
	}
}
