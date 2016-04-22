package com.froad.util;

import org.bson.types.ObjectId;

import com.alibaba.fastjson.JSON;

/**
 * JsonUtil
 * 提供Json和对象之间的转换。
 * @author Kingo.Liang
 *
 */
public class JSonUtil {
	
	/**
	 * 把对象转换成Json字符串。
	 * @param obj 需要转换的对象。
	 * @return 转换好的字符串。如果出错会抛出RuntimeException
	 */
	public static String toJSonString(Object obj){
		try {
			return JSON.toJSONString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 把Json字符串转换成对象
	 * @param <T> 所要转换的对象类型
	 * @param json Json字符串
	 * @param clazz 转换对象有类型
	 * @return 转换好的对象，如果出错会抛出RuntimeException
	 */
	public static <T> T toObject(String json,Class<T> clazz){
		T t = null;
		try {
			t = JSON.parseObject(json, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return t;
	}
	
	/**
	 *  生成Mongo ObjectId
	  * @Title: toObjectId
	  * @Description: TODO
	  * @author: share 2015年4月15日
	  * @modify: share 2015年4月15日
	  * @param @param id
	  * @param @return    
	  * @return ObjectId    
	  * @throws
	 */
	public static ObjectId toObjectId(String id){
		if(id.startsWith("{") && id.endsWith("}")){
			id = id.substring(9, id.length()-2);
		}
		return new ObjectId(id);
	}
	
	public static void main(String[] args) {
		
		
	}
	
	
}
