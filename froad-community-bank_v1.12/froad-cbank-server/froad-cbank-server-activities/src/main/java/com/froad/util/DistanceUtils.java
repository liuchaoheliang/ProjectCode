package com.froad.util;

 /**
  * @ClassName: DistanceUtils
  * @Description: 计算任意两点(经纬度)距离
  * @author froad-shenshaocheng 2015年11月14日
  * @modify froad-shenshaocheng 2015年11月14日
 */
public class DistanceUtils {

	 /**
	  * @Title: Distance
	  * @Description: 计算任意两点(经纬度)距离
	  * @author: shenshaocheng 2015年11月14日
	  * @modify: shenshaocheng 2015年11月14日
	  * @param long1  第一点经度
	  * @param lat1  第一点纬度
	  * @param long2 第二点经度
	  * @param lat2  第二点纬度
	  * @return 返回距离 单位：米
	 */
	public static double Distance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		return d;
	}
}
