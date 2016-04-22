/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
  
/**  
 * @Title: NumberUtils.java
 * @Package com.froad.util
 * @Description: TODO
 * @author vania
 * @date 2015年3月20日
 */

package com.froad.util;


/**    
 * <p>Title: NumberUtils.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月20日 下午2:00:57   
 */

public class NumberUtils {
	public static String parseString(Number number) {		
		return parseString(number, null);
	}
	
	public static String parseString(Number number, String defaultString) {
		if(null == number)
			return defaultString;
		return number.toString();
	}
}
