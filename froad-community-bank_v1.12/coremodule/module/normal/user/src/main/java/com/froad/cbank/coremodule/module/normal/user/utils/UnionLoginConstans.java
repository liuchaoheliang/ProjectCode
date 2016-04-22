package com.froad.cbank.coremodule.module.normal.user.utils;


/**
 * @Description: TODO
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年10月9日 上午10:19:38
 */
public class UnionLoginConstans {
	
	
	public static final String TAIZHOU_UNIONLOGIN_RSAPUBKEY = get("taizhou.unionlogin.RSAPublicKey");
	public static final String TAIZHOU_UNIONLOGIN_3DESKEY = get("taizhou.unionlogin.3DESKey");
	
	
	public static String get(String key){
		return Constants.get(key);
	}
}
