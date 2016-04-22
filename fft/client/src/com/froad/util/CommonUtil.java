package com.froad.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;




	/**工具类
	 * 类描述：
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2011 
	 * @author: 勾君伟 goujunwei@f-road.com.cn
	 * @time: Apr 25, 2011 2:14:23 PM 
	 */
public final class CommonUtil {
	
	public static final String YYYYMMDDHHMMSS="yyyy-MM-dd HH:mm:ss";//yyyy-MM-dd HH:mm:ss格式
	public static final String YYYYMMDD="yyyy-MM-dd";//yyyy-MM-dd格式
	public static final String HHMMSS="HH:mm:ss";//HH:mm:ss格式

	
	/**
	  * 方法描述：将list装换成map
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 勾君伟 goujunwei@f-road.com.cn
	  * @time: Apr 25, 2011 2:20:43 PM
	  */
//	public static TreeMap<String,List<Ofcard>> changeListToHashMap(List<KeyValues> list){
////		TreeMap<String,List<Ofcard>> hashmap=new TreeMap<String,List<Ofcard>>();
////		for(KeyValues kv:list){
////			hashmap.put(kv.getKey(), kv.getValue());
////		}
////		return hashmap;
//	}
	

    
        /**
          * 方法描述：提供精确的加法运算。
          * @param: v1 被加数  v2 加数
          * @return: 两个参数的和 
          * @version: 1.0
          * @author: 何庆均 heqingjun@f-road.com.cn
          * @time: 2011-4-27 下午12:01:29
          */
        public static float add(float v1,float v2){ 
        
            BigDecimal b1 = new BigDecimal(Float.toString(v1)); 
            
            BigDecimal b2 = new BigDecimal(Float.toString(v2)); 
            
            return b1.add(b2).floatValue(); 
        
        } 
   
        
        /**
          * 方法描述：提供精确的减法运算。 
          * @param: v1 被减数  v2 减数 
          * @return: 两个参数的差 
          * @version: 1.0
          * @author: 何庆均 heqingjun@f-road.com.cn
          * @update:
          * @time: 2011-4-27 下午12:39:43
          */
        public static float sub(float v1,float v2){ 
        
            BigDecimal b1 = new BigDecimal(Float.toString(v1)); 
            
            BigDecimal b2 = new BigDecimal(Float.toString(v2)); 
            
            return b1.subtract(b2).floatValue(); 
        
        } 


        
        /**
          * 方法描述：提供精确的乘法运算。
          * @param:  v1 被乘数  v2 乘数 
          * @return: 两个参数的积 
          * @version: 1.0
          * @author: 何庆均 heqingjun@f-road.com.cn
          * @update:
          * @time: 2011-4-27 下午12:41:02
          */
        public static float mul(float v1,float v2){ 
        
            BigDecimal b1 = new BigDecimal(Float.toString(v1)); 
            
            BigDecimal b2 = new BigDecimal(Float.toString(v2)); 
            
            return b1.multiply(b2).floatValue(); 
        
        } 


        
        /**
          * 方法描述：提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。 
          * @param: v1 被除数   v2 除数   scale 表示表示需要精确到小数点以后几位。 
          * @return: 两个参数的商
          * @version: 1.0
          * @author: 何庆均 heqingjun@f-road.com.cn
          * @update:
          * @time: 2011-4-27 下午12:43:25
          */
        public static float div(float v1,float v2,int scale){ 
        
            if(scale<0){ 
            
                throw new IllegalArgumentException("The scale must be a positive integer or zero"); 
            
            } 
            
            BigDecimal b1 = new BigDecimal(Float.toString(v1)); 
            
            BigDecimal b2 = new BigDecimal(Float.toString(v2)); 
            
            return b1.divide(b2,scale,BigDecimal.ROUND_HALF_UP).floatValue(); 
        
        } 
 
        
        /**
          * 方法描述：提供精确的小数位四舍五入处理。 
          * @param: v 需要四舍五入的数字   scale 小数点后保留几位 
          * @return: 四舍五入后的结果 
          * @version: 1.0
          * @author: 何庆均 heqingjun@f-road.com.cn
          * @update:
          * @time: 2011-4-27 下午12:46:00
          */
        public static float round(float v,int scale){ 
        
            if(scale<0){ 
            
        	throw new IllegalArgumentException("The scale must be a positive integer or zero"); 
            
            } 
            
            BigDecimal b = new BigDecimal(Double.toString(v)); 
            
            BigDecimal one = new BigDecimal("1"); 
            
            return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).floatValue(); 
        
        }
        
        
        /**
          * 方法描述：取当前时间 【yyyy-MM-dd HH:mm:ss】
          * @param: 
          * @return: 
          * @version: 1.0
          * @author: 勾君伟 goujunwei@f-road.com.cn
         * @throws ParseException 
          * @time: Apr 27, 2011 2:06:43 PM
          */
        public static String getCurrentDate(String date_type){
        	String currentdate=new SimpleDateFormat(date_type).format(new Date());
            return currentdate;
        }
        

        /**
          * 方法描述：四舍五入 保留 2位
          * @param: 
          * @return: 
          * @version: 1.0
          * @author: 刘丽 liuli@f-road.com.cn
          * @time: 2012-12-21 下午05:24:21
         */
        public static double scale2(double d,int n){
            BigDecimal bd=new BigDecimal(String.valueOf(d)); 
            bd=bd.setScale(n, BigDecimal.ROUND_HALF_UP); 
            return bd.doubleValue();
      	}

        /**
    	 * 随机获取UUID字符串(无中划线)
    	 * 
    	 * @return UUID字符串
    	 */
    	public static String getUUID() {
    		String uuid = UUID.randomUUID().toString();
    		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
    	}
    	
    	
        public static void main(String[] arg) throws ParseException{
//        	Calendar c=Calendar.getInstance();
//        	System.out.println(new Date());
//        	System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        	System.out.println(scale2(3.335,2));
        }
}
