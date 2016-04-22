/**
 * Project Name:froad-cbank-server-finity-1.3merchant
 * File Name:command.java
 * Package Name:com.froad.task
 * Date:2015年10月16日下午5:22:00
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.task;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:command
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月16日 下午5:22:00
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */
public class Command {
	
	public static Map<String, Long> resourceMap;
	
	static{
		resourceMap=new HashMap<String, Long>();

		//首页
		resourceMap.put("600000000", 200000001L); //安徽
		resourceMap.put("610000000", 200000001L); //重庆
		resourceMap.put("620000000", 200000001L); //台州
		resourceMap.put("630000000", 200000001L); //台州 
		//交易统计
		resourceMap.put("600000001", 200000002L);
		resourceMap.put("610000001", 200000002L);
		resourceMap.put("620000001", 200000002L);
		resourceMap.put("630000001", 200000002L);
		//商户管理
		resourceMap.put("100000000", 200000003L);
		resourceMap.put("110000000", 200000003L);
		resourceMap.put("120000000", 200000003L);
		resourceMap.put("130000000", 200000003L);
		//基本信息
		resourceMap.put("100000001", 200000004L);
		resourceMap.put("110000001", 200000004L);
		resourceMap.put("120000001", 200000004L);
		resourceMap.put("130000001", 200000004L);
		//门店管理
		resourceMap.put("100000002", 200000005L);
		resourceMap.put("110000002", 200000005L);
		resourceMap.put("120000002", 200000005L);
		resourceMap.put("130000002", 200000005L);
		//商品管理
		resourceMap.put("200000000", 200000006L);
		resourceMap.put("210000000", 200000006L);
		resourceMap.put("220000000", 200000006L);
		resourceMap.put("230000000", 200000006L);
		//商品查询
		resourceMap.put("200000001", 200000007L);
		resourceMap.put("210000001", 200000007L);
		resourceMap.put("220000001", 200000007L);
		resourceMap.put("230000001", 200000007L);
		//商品发布
		resourceMap.put("200000002", 200000008L);
		resourceMap.put("210000002", 200000008L);
		resourceMap.put("220000002", 200000008L);
		resourceMap.put("230000002", 200000008L);
		//名优特惠
		resourceMap.put("200000003", 200000009L);
		resourceMap.put("210000003", 200000009L);
		resourceMap.put("220000003", 200000009L);
		resourceMap.put("230000003", 200000009L);
		//交易管理
		resourceMap.put("300000000", 200000010L);
		resourceMap.put("310000000", 200000010L);
		resourceMap.put("320000000", 200000010L);
		resourceMap.put("330000000", 200000010L);
		//团购提货
		resourceMap.put("300000001", 200000011L);
		resourceMap.put("310000001", 200000011L);
		resourceMap.put("320000001", 200000011L);
		resourceMap.put("330000001", 200000011L);
		//订单查询
		resourceMap.put("300000002", 200000012L);
		resourceMap.put("310000002", 200000012L);
		resourceMap.put("320000002", 200000012L);
		resourceMap.put("330000002", 200000012L);
		//提货码管理
		resourceMap.put("300000003", 200000013L);
		resourceMap.put("310000003", 200000013L);
		resourceMap.put("320000003", 200000013L);
		resourceMap.put("330000003", 200000013L);
		//收银台，默认全部拥有
//		resourceMap.put("", 2000000014L);
//		resourceMap.put("", 2000000014L);
		//口碑管理(含有商户评价或者门店评价的默认都有口碑管理)
//		resourceMap.put("", 2000000015L);
//		resourceMap.put("", 2000000015L);
		//商品评价
		resourceMap.put("300000005", 200000016L);
		resourceMap.put("310000005", 200000016L);
		resourceMap.put("320000005", 200000016L);
		resourceMap.put("330000005", 200000016L);
		//商户评价
		resourceMap.put("300000004", 200000017L);
		resourceMap.put("310000004", 200000017L);
		resourceMap.put("320000004", 200000017L);
		resourceMap.put("330000004", 200000017L);
		//用户管理(一级菜单)
		resourceMap.put("500000000", 200000018L);
		resourceMap.put("510000000", 200000018L); 
		resourceMap.put("520000000", 200000018L);
		resourceMap.put("530000000", 200000018L);  
		//用户管理(二级菜单)
		resourceMap.put("500000001", 200000019L);
		resourceMap.put("510000001", 200000019L);
		resourceMap.put("520000001", 200000019L);
		resourceMap.put("530000001", 200000019L);
		
//		resourceMap.put("500000003", 200000019L);
//		resourceMap.put("510000003", 200000019L);
//		resourceMap.put("520000003", 200000019L);

	}
	
	public static Long getNewResourceId(Long key){
		return resourceMap.get(key.toString());
	} 
	
}
