package com.froad.cbank.coremodule.framework.common.util.web;

import java.math.BigDecimal;

public class CalculateUtil {
	/**
	 * 计算两点之间距离
	 * 
	 * @param _lat1
	 *            - start纬度
	 * @param _lon1
	 *            - start经度
	 * @param _lat2
	 *            - end纬度
	 * @param _lon2
	 *            - end经度
	 * @return km(四舍五入)
	 */
	public static void main(String[] args) {
		double lat1 = 31.278624;
		double lng1 = 121.439768;
		double lat2 = 31.271440;
		double lng2 = 121.479276;
		System.out.println(getDistance(lat1, lng1, lat2, lng2));
	}

	/**
	 * 计算两点之间距离
	 * 
	 * @param _lat1 - start纬度
	 * @param _lon1 - start经度
	 * @param _lat2 - end纬度
	 * @param _lon2 - end经度
	 * @return km(四舍五入)
	 */
	public static double getDistance(double _lat1, double _lon1, double _lat2, double _lon2) {  
        double x, y, distance;
        double R = 6371.393;
        x = (_lat2 - _lat1) * Math.PI * R * Math.cos(((_lon1 + _lon2) / 2) * Math.PI / 180) / 180;  
        y = (_lon2 - _lon1) * Math.PI * R / 180;  
        distance = Math.hypot(x, y);
        return new BigDecimal(distance).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
}
