package com.froad.cbank.coremodule.module.normal.bank.util;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.alibaba.fastjson.JSON;

public class BankStringUtil {
	
	/**
	 * 
	* @Title: parseToJsonString 
	* @Description: 将一个对象转成json数据格式
	* @param data 对象
	* @return
	* @throws JsonGenerationException
	* @throws JsonMappingException
	* @throws IOException    
	* @return String json数据
	* @throws
	 */
	public static String parseToJsonString(Object data)
			throws JsonGenerationException, JsonMappingException, IOException {
		String jsonString = JSON.toJSONString(data);
		return jsonString;
		// ObjectMapper om = new ObjectMapper();
		// return om.writeValueAsString(data);
	}
}
