package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;


public class ReqToMapStrUtil {

	@SuppressWarnings("unchecked")
	public static Map<String, String> toMap(HttpServletRequest req){
		String data = (String) req.getAttribute("data");
		if(data == null || data.length() == 0){
			return new HashMap<String, String>();
		}else{
			Map<String, Object> dataTemp = (Map<String, Object>)JSON.parse(data);
			Map<String, String> dataReq = new HashMap<String, String>();
			for (Map.Entry<String, Object> temp : dataTemp.entrySet()) {
				dataReq.put(temp.getKey(), temp.getValue().toString());
			}
			return dataReq;
		}
	}

}
