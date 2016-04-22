/**
 * Project Name:coremodule-merchant
 * File Name:RedisKeys.java
 * Package Name:com.froad.cbank.coremodule.module.normal.merchant.utils
 * Date:2016-1-4下午02:14:07
 * Copyright (c) 2016, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.text.MessageFormat;

/**
 * ClassName:RedisKeys
 * Reason:	 TODO ADD REASON.
 * Date:     2016-1-4 下午02:14:07
 * @author   wufei
 * @version  
 * @see 	 
 */
public class RedisKeys {

	public static String merchant_outlet_extend(String merchantId, String outletId) {
		return MessageFormat.format("merchant:outlet_extend:{0}:{1}", merchantId,outletId);
	}
	
}
