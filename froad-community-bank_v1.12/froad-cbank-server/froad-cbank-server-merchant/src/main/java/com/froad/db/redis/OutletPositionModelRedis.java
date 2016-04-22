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
 * @Title: OutletPositionModelRedis.java
 * @Package com.froad.db.redis
 * @Description: TODO
 * @author vania
 * @date 2015年3月27日
 */

package com.froad.db.redis;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.math.NumberUtils;

//import redis.clients.jedis.Jedis;

import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.po.Outlet;
import com.froad.util.RedisKeyUtil;


/**    
 * <p>Title: OutletPositionModelRedis.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月27日 上午9:33:07   
 */

public class OutletPositionModelRedis {
	private static RedisManager redisManager = new RedisManager();
	
	public final static int DISTANCE_MODEL_200 = 200; // 建模距离
	public final static int DISTANCE_MODEL_250 = 250; // 建模距离
	public final static int DISTANCE_MODEL_500 = 500; // 建模距离
	public final static int DISTANCE_MODEL_1000 = 1000; // 建模距离
	
	private static JWD o = new JWD(0d, 0d); // 坐标原点的经纬度(默认为本初子午线和赤道交界点)
	
	
	static double LATITUDE_BJ = 39.92; // 以 北京的纬度  来建模
	static {		
		
		
		float delta = JWD.DELTA;   // 在经线上，相差一纬度约为111km   
		   						// 在纬线上，相差1经度约为111* 1 * cosα（α为该纬线的纬度）”
		long distance = 0; // m
		double degree = 0d; // 在纬线上每隔dis的距离  经度相差 多少 度
		
		
			distance = DISTANCE_MODEL_200; // m
			degree = Math.abs((distance / (Math.cos(LATITUDE_BJ) * delta)));
			redisManager.putString(RedisKeyUtil.cbbank_outlet_area_distance_degree(distance), Double.toString(degree));
			LogCvt.info("在北京(北纬" + LATITUDE_BJ + "度)同纬度上每相差" + distance + "m 经度相差:" + Double.toString(degree) + "度");
			
			distance = DISTANCE_MODEL_250; // m
			degree = Math.abs((distance / (Math.cos(LATITUDE_BJ) * delta)));
			redisManager.putString(RedisKeyUtil.cbbank_outlet_area_distance_degree(distance), Double.toString(degree));
			LogCvt.info("在北京(北纬" + LATITUDE_BJ + "度)同纬度上每相差" + distance + "m 经度相差:" + Double.toString(degree) + "度");
			
			distance = DISTANCE_MODEL_500; // m
			degree = Math.abs((distance / (Math.cos(LATITUDE_BJ) * delta)));
			redisManager.putString(RedisKeyUtil.cbbank_outlet_area_distance_degree(distance), Double.toString(degree));
			LogCvt.info("在北京(北纬" + LATITUDE_BJ + "度)同纬度上每相差" + distance + "m 经度相差:" + Double.toString(degree) + "度");
			
			
			distance = DISTANCE_MODEL_1000; // m
			degree = Math.abs((distance / (Math.cos(LATITUDE_BJ) * delta)));
			redisManager.putString(RedisKeyUtil.cbbank_outlet_area_distance_degree(distance), Double.toString(degree));
			LogCvt.info("在北京(北纬" + LATITUDE_BJ + "度)同纬度上每相差" + distance + "m 经度相差:" + Double.toString(degree) + "度");
			// 在北京(北纬latitude_bj)同纬度上每相差250m 经度相差0.003721134858262508度
	}
	
	// 只适合北纬   东经
	public static boolean setCache(Outlet... outlets) throws Exception{
//		setCache(DISTANCE_MODEL_200, outlets); // 每隔200 米建模
		setCache(DISTANCE_MODEL_250, outlets); // 每隔250 米建模
//		setCache(DISTANCE_MODEL_500, outlets); // 每隔500 米建模
//		setCache(DISTANCE_MODEL_1000, outlets); // 每隔1000 米建模
		return true;
	}	

	/**
	 * 门店按照 每隔distance米建模缓存
	 * @Title: setCache 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param distance
	 * @param outlets
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	private static boolean setCache(double distance, Outlet... outlets)throws Exception{
		
//			Set<String> set = new HashSet<String>();
			for (Outlet outlet : outlets) {
				double longitude = Double.parseDouble(outlet.getLongitude());
				double latitude = Double.parseDouble(outlet.getLatitude());
				
				String client_id = outlet.getClientId();
				String outlet_id = outlet.getOutletId();
			
				double[] fanwei = getModel(longitude, latitude, distance);
				double lng1 = fanwei[0]; // 获得左下角的经度
				double lat1 = fanwei[1]; // 获得左下角的纬度
				double lng2 = fanwei[2]; // 获得右上角的经度
				double lat2 = fanwei[3]; // 获得右上角的纬度
				
				
	//			JWD zuoxia = new JWD(lng1, lat1); // 左下角坐标
				JWD youshang = new JWD(lng2, lat2); // 右上角坐标
				
				long weidu_kedu = latitudeMark(latitude, distance);
				
		//		String key = "cbbank:outlet_" + ((weidu_kedu + 1) * dis) + ""; // 右上角key
				String key = RedisKeyUtil. cbbank_outlet_area_distance_client_id_longitude_latitude(((long)((weidu_kedu + 1) * distance)), client_id, youshang.longitude, youshang.latitude); // 右上角key
				LogCvt.info("建模的key=============>" + key);
				LogCvt.info("建模的value=============>" + ObjectUtils.toString(outlet_id, ""));
//				Long result = jedis.sadd(key, ObjectUtils.toString(outlet_id, ""));
				Long result = redisManager.put(key, ObjectUtils.toString(outlet_id, ""));
				
		//		LogCvt.info("左下角:" + JSON.toJSON(zuoxia));
		//		LogCvt.info("右上角:" + JSON.toJSON(youshang));
				
		//		Distance distance = new Distance();
		//		LogCvt.info("距离左下角距离:" + distance.getDistance(longitude, latitude, zuoxia.longitude, zuoxia.latitude));
		//		LogCvt.info("距离右上角距离:" + distance.getDistance(longitude, latitude, youshang.longitude, youshang.latitude));
			}
		
		return true;
	}
	
	public static boolean delCache(Outlet... outlets) throws Exception{
//		delCache(DISTANCE_MODEL_200, outlets); // 每隔200 米建模
		delCache(DISTANCE_MODEL_250, outlets); // 每隔250 米建模
//		delCache(DISTANCE_MODEL_500, outlets); // 每隔500 米建模
//		delCache(DISTANCE_MODEL_1000, outlets); // 每隔1000 米建模
		return true;
	}
	
	public static boolean delCache(double distance, Outlet... outlets)throws Exception{
		
			for (Outlet outlet : outlets) {
				double longitude = Double.parseDouble(outlet.getLongitude());
				double latitude = Double.parseDouble(outlet.getLatitude());
				
				String client_id = outlet.getClientId();
				String outlet_id = outlet.getOutletId();
			
				double[] fanwei = getModel(longitude, latitude, distance);
				double lng1 = fanwei[0]; // 获得左下角的经度
				double lat1 = fanwei[1]; // 获得左下角的纬度
				double lng2 = fanwei[2]; // 获得右上角的经度
				double lat2 = fanwei[3]; // 获得右上角的纬度
				
				
	//			JWD zuoxia = new JWD(lng1, lat1); // 左下角坐标
				JWD youshang = new JWD(lng2, lat2); // 右上角坐标
				
				long weidu_kedu = latitudeMark(latitude, distance);
				
		//		String key = "cbbank:outlet_" + ((weidu_kedu + 1) * dis) + ""; // 右上角key
				String key = RedisKeyUtil. cbbank_outlet_area_distance_client_id_longitude_latitude(((long)((weidu_kedu + 1) * distance)), client_id, youshang.longitude, youshang.latitude); // 右上角key
				LogCvt.info("删除建模的key=============>" + key);
				LogCvt.info("删除建模的value=============>" + ObjectUtils.toString(outlet_id, ""));
				Long result = redisManager.srem(key, ObjectUtils.toString(outlet_id, ""));
				
		//		LogCvt.info("左下角:" + JSON.toJSON(zuoxia));
		//		LogCvt.info("右上角:" + JSON.toJSON(youshang));
				
		//		Distance distance = new Distance();
		//		LogCvt.info("距离左下角距离:" + distance.getDistance(longitude, latitude, zuoxia.longitude, zuoxia.latitude));
		//		LogCvt.info("距离右上角距离:" + distance.getDistance(longitude, latitude, youshang.longitude, youshang.latitude));
			}
		
		return true;
	
	}
	
	/** 
     * 根据距离建模,并且返回经纬度所在矩形范围的4个点,
     *  
     * @param lng 
     *            经度 
     * @param lat 
     *            纬度 
     * @param distance 
     *            距离(单位为米) 
     * @return [lng1,lat1, lng2,lat2] 矩形的左下角(lng1,lat1)和右上角(lng2,lat2) 
     */  
    public static double[] getModel(double longitude, double latitude, double distance) throws Exception{ 
		   // 在纬线上，相差一经度约为111* 1* cosα（α为该纬线的纬度）”
	//			double bj = delta * Math.cos(39.92);
	//			System.out.println("在北京相差250m经度约为:" + (0.25 * Math.cos(latitude_bj)));
		
	//			例如在北纬30度，经度相差5度，实际距离是：111*5*cos30度。
			// 0.250 = 111 * x * Math.cos(39.92)
	//			distance = 250d; // m
	//			double jingdu = Math.abs((dis / (Math.cos(latitude_bj)latitude_bjlta))); // 在纬线上，相差一经度约为111* 1* cosα（α为该纬线的纬度）”
			String degreeStr = redisManager.getString(RedisKeyUtil.cbbank_outlet_area_distance_degree((long)distance)); // 在纬线上 每跨dis 米  经度相差  多少 度(相对于北京的纬度)  
			double jingdu = NumberUtils.isNumber(degreeStr) ? Double.parseDouble(degreeStr) : Math.abs((distance / (Math.cos(LATITUDE_BJ) * JWD.DELTA))); // 在纬线上，相差一经度约为111* 1* cosα（α为该纬线的纬度）”
			double weidu  = distance / JWD.DELTA; // 在经线上，相差一纬度约为111km  
			
	//			long jingdu_kedu = Math.floor(Math.abs(longitude - o.longitude) / jingdu); // 多少个刻度;
			long jingdu_kedu = longitudeMark(longitude, distance); // 经度多少个刻度;
	//		System.out.println("左经度----经度相差" + jingdu_kedu + "个刻度(一个刻度是" + distance + "m距离," + jingdu + "经度)");
	//		System.out.println("右经度----经度相差" + (jingdu_kedu + 1) + "个刻度(一个刻度是" + distance + "m距离," + jingdu + "经度)");
			
			
	//			long weidu_kedu = Math.floor(Math.abs(latitude - o.latitude) / weidu);
			long weidu_kedu = latitudeMark(latitude, distance);
	//		System.out.println("下纬度---纬度相差" + (weidu_kedu) + "个刻度(一个刻度是" + distance + "m距离," + weidu + "纬度)");
	//		System.out.println("上纬度---纬度相差" + (weidu_kedu + 1) + "个刻度(一个刻度是" + distance + "m距离," + weidu + "纬度)");
			
			
			JWD zuoxia = new JWD(jingdu_kedu * jingdu, weidu_kedu * weidu); // 左下角坐标
			JWD youshang = new JWD((jingdu_kedu + 1) * jingdu, (weidu_kedu + 1) * weidu); // 右上角坐标
			
			return new double[] { zuoxia.longitude, zuoxia.latitude, youshang.longitude, youshang.latitude }; 
		
    }
    
    /**
     * 经度多少个刻度
     * @Title: longitudeMark 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param longitude
     * @param distance
     * @return
     * @return long    返回类型 
     * @throws
     */
    private static long longitudeMark(double longitude, double distance) throws Exception {
	    	String degreeStr = redisManager.getString(RedisKeyUtil.cbbank_outlet_area_distance_degree((long)distance)); // 在纬线上 每跨dis 米  经度相差  多少 度(相对于北京的纬度)
	    	double jingdu = NumberUtils.isNumber(degreeStr) ? Double.parseDouble(degreeStr) : Math.abs((distance / (Math.cos(LATITUDE_BJ) * JWD.DELTA))); // 在纬线上，相差一经度约为111* 1* cosα（α为该纬线的纬度）”
	    	
	    	return (long)Math.floor(Math.abs(longitude - o.longitude) / jingdu); // 多少个刻度;
    }
    
    /**
     * 纬度多少个刻度
     * @Title: latitudeMark 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param longitude
     * @param distance
     * @return
     * @return long    返回类型 
     * @throws
     */
    private static long latitudeMark(double latitude, double distance) throws Exception {
    	double weidu  = distance / JWD.DELTA; // 在经线上，相差一纬度约为111km  
    	return (long)Math.floor(Math.abs(latitude - o.latitude) / weidu); // 多少个刻度;
    }
	
	/**
	 * 根据经纬度查询指定距离内的所有的门店ID
	 * @Title: getOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param client_id
	 * @param longitude
	 * @param latitude
	 * @param distance
	 * @return
	 * @return List<String>    返回类型 
	 * @throws
	 */
//	public static List<String> getCache(String client_id, double longitude, double latitude, double distance) {
//		List<String> list = new ArrayList<String>();
//		if(distance != DISTANCE_MODEL_200 && distance != DISTANCE_MODEL_500 && distance != DISTANCE_MODEL_1000 ) {
////			throw new Exception("不支持的距离查询");
//		}
//		double[] fanwei = getModel(longitude, latitude, distance);
//		double lng1 = fanwei[0]; // 获得左下角的经度
//		double lat1 = fanwei[1]; // 获得左下角的纬度
//		double lng2 = fanwei[2]; // 获得右上角的经度
//		double lat2 = fanwei[3]; // 获得右上角的纬度
//		
//		long weidu_kedu = latitudeMark(latitude, distance);
//		String key = RedisKeyUtil. cbbank_outlet_area_distance_client_id_longitude_latitude(((long)((weidu_kedu + 1) * distance)), client_id, lng2, lat2); // 右上角key
//		LogCvt.info("获取建模的key=============>" + key);
//		Set<String> set = jedis.smembers(key);
//		list.addAll(set);
//		return list;
//	}
	
    /**
     * 获取指定距离内的所有门店ID
     * @Title: getCache 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param client_id
     * @param longitude
     * @param latitude
     * @param distance
     * @return
     * @return List<String>    返回类型 
     * @throws
     */
	public static Set<String> getCache(String client_id, double longitude, double latitude, double distance) throws Exception{
		long kedushu = distance < DISTANCE_MODEL_250 + 1 ? 0 : (long) Math.floor(distance / DISTANCE_MODEL_250); // 横轴(纵轴)有多少个刻度
		kedushu = kedushu % 2 != 0 ? kedushu = kedushu + 1 : kedushu; // 如果传入的距离不是250米的偶数倍  则+1让其变成偶数倍
//		long gezishu = kedushu < 2 ? 0 : (long) Math.pow((kedushu + 1), 2); // 总共有多少个格子
		return getCache(client_id, longitude, latitude, kedushu);
	}
	
	private static Set<String> getCache(String client_id, double longitude, double latitude, long kedushu) throws Exception{
		
	//		List<String> list = new ArrayList<String>();
			Set<String> set = new HashSet<String>();
			double distance = DISTANCE_MODEL_250;
			double[] fanwei = getModel(longitude, latitude, distance);
			double lng1 = fanwei[0]; // 获得左下角的经度
			double lat1 = fanwei[1]; // 获得左下角的纬度
			double lng2 = fanwei[2]; // 获得右上角的经度
			double lat2 = fanwei[3]; // 获得右上角的纬度
			
			long weidu_kedu = latitudeMark(latitude, DISTANCE_MODEL_250);
			String key = RedisKeyUtil. cbbank_outlet_area_distance_client_id_longitude_latitude(((long)((weidu_kedu + 1) * DISTANCE_MODEL_250)), client_id, lng2, lat2); // 右上角key
			LogCvt.info("获取传入的经纬度所在的区域的 右上角 的经纬度建模的key=============>" + key);
			if (kedushu < 1) { // 如果只查250米之内的  直接返回
	//			Set<String> set = jedis.smembers(key);
	//			list.addAll(set);
	//			return list;
				set.addAll(redisManager.getSet(key));
				
				return set;
			}
			
	//		long weidu_kedu = latitudeMark(latitude, DISTANCE_MODEL_250); // 纬度距离原点多少个刻度
			long jingdu_kedu = longitudeMark(longitude, DISTANCE_MODEL_250);// 经度距离原点多少个刻度
			
			// 以传入的经纬度所在的区域的 右上角 的经纬度为原点 建立虚拟坐标
			long o_weidu_kedu = weidu_kedu + 1; // 以传入的经纬度的点所在的区域的右上角的点作为虚拟坐标的原点
			long o_jingdu_kedu = jingdu_kedu +1;// 以传入的经纬度的点所在的区域的右上角的点作为虚拟坐标的原点
			
			kedushu = kedushu == 1 ? 1 : kedushu / 2; // 坐标轴两边都有  所以此处要处以二
			for (int i = (int) (o_jingdu_kedu - kedushu); i <= o_jingdu_kedu + kedushu; i++) {
	//			if (i == o_jingdu_kedu - kedushu) { // 排除最左边的坐标
	//				continue;
	//			}
				for (int j = (int) (o_weidu_kedu - kedushu); j <= (o_weidu_kedu + kedushu); j++) {
	//				if (j == o_weidu_kedu - kedushu) { // 排除最下面坐标
	//					continue;
	//				}
//			LogCvt.infoug("i==========>" + i + "\t j===================>" + j);
					
					
					
					String degreeStr = redisManager.getString(RedisKeyUtil.cbbank_outlet_area_distance_degree((long)distance)); // 在纬线上 每跨dis 米  经度相差  多少 度(相对于北京的纬度)  
					double jingdu = NumberUtils.isNumber(degreeStr) ? Double.parseDouble(degreeStr) : Math.abs((distance / (Math.cos(LATITUDE_BJ) * JWD.DELTA))); // 在纬线上，相差一经度约为111* 1* cosα（α为该纬线的纬度）”
					double weidu  = distance / JWD.DELTA; // 在经线上，相差一纬度约为111km
					
					
					
	//				key = RedisKeyUtil. cbbank_outlet_area_distance_client_id_longitude_latitude(((long)((weidu_kedu + 1) * distance)), client_id, jingdu_kedu * jingdu, weidu_kedu * weidu); // 右上角key
					key = RedisKeyUtil. cbbank_outlet_area_distance_client_id_longitude_latitude(((long)((j) * distance)), client_id, i * jingdu,  j * weidu); // 右上角key
					LogCvt.info("获取建模的key=============>" + key);
					
	//				Set<String> set = jedis.smembers(key);
	//				list.addAll(set);
	//				return list;
					set.addAll(redisManager.getSet(key));
					
					
	//				Set<String> set = jedis.smembers(key);
	//				if(CollectionUtils.isNotEmpty(set))
	//					list.addAll(set);
				}
			}		
			
	//		return list;
			return set;
		
	}
	
	// 经纬度
	private static class JWD {
		static double Rc = 6378137;  // 赤道半径
		static double Rj = 6356725;  // 极半径 
		static float DELTA = 111000;  // 在经线上，相差一纬度约为111km   
		
		public double longitude; // 经度
		public double latitude; // 纬度
		private JWD() {
		}
		public JWD(double longitude, double latitude) {
			this.longitude = longitude;
			this.latitude = latitude;
		}
			
		
	}
	

}
