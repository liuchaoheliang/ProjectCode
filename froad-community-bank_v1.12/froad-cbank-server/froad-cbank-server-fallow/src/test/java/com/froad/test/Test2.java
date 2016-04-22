/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:Test2.java
 * Package Name:com.froad.test
 * Date:2015年11月12日下午4:33:58
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.test;

import com.alibaba.fastjson.JSONObject;
import com.froad.util.PropertiesUtil;
import com.froad.util.SerialNumberUtil;

/**
 * ClassName:Test2
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月12日 下午4:33:58
 * @author   vania
 * @version  
 * @see 	 
 */
public class Test2 {
	static {
		PropertiesUtil.load();
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("name", "zhangsan");
		
		System.out.println("json===>" + JSONObject.toJSONString(null));
		for (int i = 0; i < 10; i++) {
//			System.out.println("序列" + i + ":" +SerialNumberUtil.getTaskIdSerialNumber());
			
		}
	}

}
