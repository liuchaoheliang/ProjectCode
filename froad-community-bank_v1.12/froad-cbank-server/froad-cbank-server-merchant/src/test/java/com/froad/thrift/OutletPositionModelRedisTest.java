/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
  
/**  
 * @Title: OutletPositionModelRedisTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年3月28日
 */

package com.froad.thrift;

import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.froad.db.redis.OutletPositionModelRedis;
import com.froad.po.Outlet;


/**    
 * <p>Title: OutletPositionModelRedisTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月28日 上午9:56:29   
 */

public class OutletPositionModelRedisTest {

	/** 
	 * @Title: main 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @return void    返回类型 
	 * @throws 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Outlet let1 = new Outlet();
		let1.setOutletId("0000001");
		let1.setLongitude("116.23548822255");
		let1.setLatitude("35.32652556558");
		let1.setClientId("1000000");
		
		Outlet let2 = new Outlet();
		let2.setOutletId("0000002");
		let2.setLongitude("116.23548822455");
		let2.setLatitude("35.32652556589");
		let2.setClientId("1000000");
		
		Outlet let3 = new Outlet();
		let3.setOutletId("0000003");
		let3.setLongitude("117.23548822999");
		let3.setLatitude("38.32652556566");
		let3.setClientId("1000000");
		
		
//		OutletPositionModelRedis.setCache(let1);
//		OutletPositionModelRedis.setCache(let2);
		
		
//		Set<String> list = OutletPositionModelRedis.getCache((long)1000000, 116.23548822353, 35.32652556489, 20000);
				
//		set.addAll(c)
		
//		System.out.println("获取的门店id====>"+JSON.toJSON(list));
	}

}
