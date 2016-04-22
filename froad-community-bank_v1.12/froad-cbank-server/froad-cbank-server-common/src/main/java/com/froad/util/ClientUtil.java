package com.froad.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 需走行内审核clientId
 * @author ll
 *
 */
public class ClientUtil {
	
	private static Map<String,String> map = new HashMap<String, String>();
	//static{
    //	map.put("taizhou", "taizhou");
	//}

	/**
	 * 是否需要走行内审核
	 * @param client
	 * @return 
	 */
	public static boolean getClientId(String client){
		
		String s =  map.get(client);
		if(StringUtils.isBlank(s)){
			return false;
		}else{
			return true;
		}
	}
	
}
