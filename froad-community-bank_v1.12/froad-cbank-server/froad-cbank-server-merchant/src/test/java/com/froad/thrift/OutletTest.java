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
 * @Title: OutletTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年3月18日
 */

package com.froad.thrift;

import java.text.MessageFormat;

import org.apache.commons.lang.ObjectUtils;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.froad.db.redis.impl.RedisManager;
import com.froad.util.Distance;


/**    
 * <p>Title: OutletTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月18日 下午4:41:00   
 */

public class OutletTest {

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
		
		
		System.exit(0);
		
		// TODO Auto-generated method stub
		float delta = 111000;  // 在经线上，相差一纬度约为111km   
		   // 在纬线上，相差一经度约为111* 1* cosα（α为该纬线的纬度）”
//		double bj = delta * Math.cos(39.92);
//		System.out.println("在北京相差250m经度约为:" + (0.25 * Math.cos(39.92)));
		
//		例如在北纬30度，经度相差5度，实际距离是：111*5*cos30度。
		// 0.250 = 111 * x * Math.cos(39.92)
		double dis = 0; // m
		dis = 100; // m
		System.out.println("在北京(北纬39.92度)同纬度上每相差" + dis + "m 经度相差:" + Math.abs((dis / (Math.cos(39.92) * delta))) + "度");
		System.out.println("在同一经度上每相差" + dis + "m 纬度相差:" + Math.abs(dis / delta) + "度");
		dis = 200; // m
		System.out.println("在北京(北纬39.92度)同纬度上每相差" + dis + "m 经度相差:" + Math.abs((dis / (Math.cos(39.92) * delta))) + "度");
		System.out.println("在同一经度上每相差" + dis + "m 纬度相差:" + Math.abs(dis / delta) + "度");
		dis = 250; // m
		System.out.println("在北京(北纬39.92度)同纬度上每相差" + dis + "m 经度相差:" + Math.abs((dis / (Math.cos(39.92) * delta))) + "度");
		System.out.println("在同一经度上每相差" + dis + "m 纬度相差:" + Math.abs(dis / delta) + "度");
		dis = 500; // m
		System.out.println("在北京(北纬39.92度)同纬度上每相差" + dis + "m 经度相差:" + Math.abs((dis / (Math.cos(39.92) * delta))) + "度");
		System.out.println("在同一经度上每相差" + dis + "m 纬度相差:" + Math.abs(dis / delta) + "度");
		dis = 1000; // m
		System.out.println("在北京(北纬39.92度)同纬度上每相差" + dis + "m 经度相差:" + Math.abs((dis / (Math.cos(39.92) * delta))) + "度");
		System.out.println("在同一经度上每相差" + dis + "m 纬度相差:" + Math.abs(dis / delta) + "度");
		// 在北京(北纬39.92)同纬度上每相差250m 经度相差0.003721134858262508度
		
		
		
		
//		Jedis jedis = RedisManager.getJedis();
//		String key = "cbbank:outlet_area_200:client_id:logitude:latitude";
//		
//		// 在经线上，相差一纬度约为111km   
//		   // 在纬线上，相差一经度约为111cosα（α为该纬线的纬度）”
//		String key_degree = "cbbank:outlet_200:" + (0.2/111.0);
//		String key_degree2 = "cbbank:outlet_500:" + (0.5/111.0);
//		String key_degree3 = "cbbank:outlet_1000:" + (1.0/111.0);
//		String key_degree4 = "cbbank:outlet_2000:" + (2.0/111.0);
//		System.out.println(key_degree);
//		System.out.println(key_degree2);
//		System.out.println(key_degree3);
//		System.out.println(key_degree4);
//		jedis.sadd(key, "");
		
		
//		cache(35.254841,116.95846655);
		cache(50.254841,80.95846655);
	}

	// 只适合北半球  和  东半球
	public static void cache(double longitude, double latitude){	
		float delta = 111000;  // 在经线上，相差一纬度约为111km   
		   // 在纬线上，相差一经度约为111* 1* cosα（α为该纬线的纬度）”
//		double bj = delta * Math.cos(39.92);
//		System.out.println("在北京相差250m经度约为:" + (0.25 * Math.cos(39.92)));
		
//		例如在北纬30度，经度相差5度，实际距离是：111*5*cos30度。
		// 0.250 = 111 * x * Math.cos(39.92)
		double dis = 250d; // m
		JWD o = new JWD(0d, 0d);
		double jingdu = Math.abs((dis / (Math.cos(39.92) * delta))); // 
		double weidu  = dis/delta; // 250m/度
		
		double jingdu_kedu = Math.floor(Math.abs(longitude - o.longitude) / jingdu); // 多少个刻度;
		System.out.println("左经度----经度相差" + jingdu_kedu + "个刻度(一个刻度是" + dis + "m距离," + jingdu + "经度)");
		System.out.println("右经度----经度相差" + (jingdu_kedu + 1) + "个刻度(一个刻度是" + dis + "m距离," + jingdu + "经度)");
		
		
		double weidu_kedu = Math.floor(Math.abs(latitude - o.latitude) / weidu);
		System.out.println("下纬度---纬度相差" + (weidu_kedu) + "个刻度(一个刻度是" + dis + "m距离," + weidu + "纬度)");
		System.out.println("上纬度---纬度相差" + (weidu_kedu + 1) + "个刻度(一个刻度是" + dis + "m距离," + weidu + "纬度)");
		
		
		JWD zuoxia = new JWD(jingdu_kedu * jingdu, weidu_kedu * weidu); // 左下角坐标
		JWD youshang = new JWD((jingdu_kedu + 1) * jingdu, (weidu_kedu + 1) * weidu); // 右上角坐标
		
		String key = "cbbank:outlet_" + ((weidu_kedu + 1) * dis) + ""; // 右上角key
		System.out.println("key============================>" + key);
		
		
		System.out.println("左下角:" + JSON.toJSON(zuoxia));
		System.out.println("右上角:" + JSON.toJSON(youshang));
		
		Distance distance = new Distance();
		System.out.println("距离左下角距离:" + distance.getDistance(longitude, latitude, zuoxia.longitude, zuoxia.latitude));
		System.out.println("距离右上角距离:" + distance.getDistance(longitude, latitude, youshang.longitude, youshang.latitude));
		
//		距离左下角距离:140.94706571192663
//		距离右上角距离:120.81097069671269
		System.out.println("距离左下角距离2:" + com.froad.util.JWD.getDistance(longitude, latitude, zuoxia.longitude, zuoxia.latitude));
		System.out.println("距离右上角距离2:" + com.froad.util.JWD.getDistance(longitude, latitude, youshang.longitude, youshang.latitude));
	}
	
	// 原点的经纬度
	public static class JWD {
		public double longitude; // 经度
		public double latitude; // 纬度
		public JWD() {
		}
		public JWD(double longitude, double latitude) {
			this.longitude = longitude;
			this.latitude = latitude;
		}
	}
}
