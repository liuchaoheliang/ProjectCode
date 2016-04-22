/**
 * Project Name:coremodule-merchant
 * File Name:orgNameUtil.java
 * Package Name:com.froad.cbank.coremodule.module.normal.merchant.utils
 * Date:2015年9月20日下午5:18:01
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.merchant.utils;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;

/**
 * ClassName:orgNameUtil
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月20日 下午5:18:01
 * @author   mi
 * @version  
 * @see 	 
 */
public class orgNameUtil {
	
	public static String getOrgNames(String orgNames) {
		StringBuffer sb = new StringBuffer();
		//这里先没判断只有预售和名优特惠没有orgName
		if (StringUtil.isNotBlank(orgNames)) {
			String[] split = orgNames.split("-");
//			int splitLength = 0;
			
//			if(split.length >= 3)
//				splitLength = split.length -1;
//			else
//				splitLength = split.length;
			if (split != null && split.length > 1) {
				for (int i = 0; i < split.length; i++) {
					if (i>0) {
						sb.append(split[i]).append("-");
					} 
				}
				orgNames = sb.toString().substring(0,sb.toString().length()-1);
			} else {
				return orgNames;
			}
		}
		LogCvt.info("orgNames========： " + orgNames);
		return orgNames;
	}

}
