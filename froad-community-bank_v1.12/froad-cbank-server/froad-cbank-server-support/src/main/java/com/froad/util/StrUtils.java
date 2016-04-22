package com.froad.util;

import java.text.MessageFormat;



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
	
	/**
	 *  模板替换，模板内容{xxx}
	  * @Title: getContentReg
	  * @Description: TODO
	  * @author: share 2015年3月31日
	  * @modify: share 2015年3月31日
	  * @param @param template
	  * @param @param obj
	  * @param @return    
	  * @return String    
	  * @throws
	 */
	public static String getContentReg(String template,Object[] obj){
		if(obj==null||obj.length==0){
			return template;
		}
		if(template==null||"".equals(template)){
			return template;
		}
		StringBuilder sb = new StringBuilder();
		String[] templates = template.split("\\{(.*?)\\}");
		for(int i = 0; i < templates.length ; i++){
			sb.append(templates[i]);
			if(i != templates.length -1){
				sb.append("{").append(i).append("}");
			}
		}
		return MessageFormat.format(sb.toString(), obj);
	}
	
	/**
	*<p>str省略</p>
	* @author 赵荆州
	* @datetime 2014-4-23上午10:26:57
	* @return String
	* @throws 
	* @example<br/>
	*
	 */
	public static String strEllipsis(String str,int maxLength){
		if(str!=null){
			if(str.length()>maxLength){
				str= str.substring(0, maxLength)+"...";
			}
		}
		return str;
	}
	
	/**
	 *  去除最后一个字符
	 *  比如：AA,BB,CC,返回AA,BB,CC
	  * @Title: removeLastChar
	  * @Description: TODO
	  * @author: share 2014年12月31日
	  * @modify: share 2014年12月31日
	  * @param @param str
	  * @param @param chart
	  * @param @return    
	  * @return String    
	  * @throws
	 */
	public static String removeLastChar(String str,String chart){
		if(str.lastIndexOf(chart) == str.length() -1 && str.length()>0){
			return str.substring(0,str.length()-1);
		}
		return str;
	}
	
	public static void main(String[] args) {
		
		System.out.println(removeLastChar("AA,BB,CC,", ","));
	}
	
}
