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
 * @Title: Distance.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年3月18日
 */

package com.froad.util;


/**    
 * <p>Title: Distance.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月18日 下午4:45:53   
 */

public class Distance {
    private final double PI = 3.14159265358979323; //圆周率
    private final double R = 6371229;             //地球的半径
     
    public double getDistance(double longt1, double lat1, double longt2, double lat2){
        double x,y, distance;
        x=(longt2-longt1)*PI*R*Math.cos( ((lat1+lat2)/2)*PI/180)/180;
        y=(lat2-lat1)*PI*R/180;
        distance=Math.hypot(x,y);
        return distance;
    }
    
    public double getLongt(double longt1, double lat1, double distance){
        double a = (180*distance)/(PI*R*Math.cos(lat1*PI/180));
        return a;
    }
    
    public double getLat(double longt1, double lat1, double distance){
        double a = (180*distance)/(PI*R*Math.cos(lat1*PI/180));
        return a;
    }
    
    public static void main(String[] args){
    	Distance m = new Distance();
    //      double s = m.getDistance(112.0235, 23.2563, 110.1235, 20.3563);
    //      System.out.println(s);
       
		double longt = m.getLongt(112.0235, 23.2563, 10227.5985);
        System.out.println(longt);
        double lat = m.getLat(112.0235, 23.2563, 11131.9859);
        System.out.println(lat); 
    }
}
