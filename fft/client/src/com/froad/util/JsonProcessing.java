package com.froad.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.util.List;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;


	/**
	 * 类描述：json处理工具类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2012-4-24 下午2:13:40 
	 */
public class JsonProcessing {
	
	
	/**
	  * 方法描述：json list 过滤器
	  * @param: 返回 不过滤的key list
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-4-28 下午4:39:12
	  */
	public static JsonConfig jsonConfigFilter(final List<String> conditions){
		JsonConfig jsonConfig = new JsonConfig();
		
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if(name != null){
					if(conditions.contains(name)) return false;
				}
				return true;
			}
		});
		
		return jsonConfig;
	}
	
	
	/**
	  * 方法描述：过滤为空数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-7-19 上午11:28:55
	  */
	public static JsonConfig jsonConfigFilterOfNull(){
		JsonConfig jsonConfig = new JsonConfig();
		
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if(value == null || "".equals(value)) return true;
				return false;
			}
		});
		
		return jsonConfig;
	}
	
}
