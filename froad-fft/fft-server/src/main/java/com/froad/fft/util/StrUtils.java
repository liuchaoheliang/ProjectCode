package com.froad.fft.util;



	/**
	 * 类描述：字符串的工具类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年4月7日 下午5:48:43 
	 */
public class StrUtils {
	
	
	/**
	  * 方法描述：将template字符串中的占位符替换成obj数组里的值
	  * @param: template 带占位符的字符串
	  * @return: obj 动态数据
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: 2014年4月7日 下午6:05:54
	  */
	public static String getContent(String template,Object[] obj){
		if(obj==null||obj.length==0){
			return template;
		}
		if(template==null||"".equals(template)){
			return template;
		}
		String tmp=null;
		for (int i = 0; i < obj.length; i++) {
			tmp=obj[i]==null?"":obj[i].toString();
			template=template.replace("{"+i+"}", tmp);
		}
		return template;
	}
	
}
