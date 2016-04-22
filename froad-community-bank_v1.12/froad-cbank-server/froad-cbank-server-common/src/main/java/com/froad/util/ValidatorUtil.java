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
 * @Title: ValidatorUtil.java
 * @Package com.froad.util
 * @Description: TODO
 * @author vania
 * @date 2015年5月4日
 */

package com.froad.util;

import java.util.Locale;

import com.froad.exceptions.FroadServerException;

import net.sf.oval.Validator;
import net.sf.oval.exception.ConstraintsViolatedException;
import net.sf.oval.localization.locale.ThreadLocalLocaleProvider;


/**    
 * <p>Title: ValidatorUtil.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年5月4日 下午5:16:09   
 */

public class ValidatorUtil {
	private ValidatorUtil(){}
	
	public static Validator getValidator() {
		ThreadLocalLocaleProvider localeProvider = (ThreadLocalLocaleProvider) Validator.getLocaleProvider();
		Locale locale = new Locale("cn"); // 设置用中文
		localeProvider.setLocale(locale);
		Validator.setLocaleProvider(localeProvider);
		Validator validator = new Validator();
		return validator;
	}
	
	public static void valid(Object obj) {
		try {
			getValidator().assertValid(obj); // 校验bean
		} catch (ConstraintsViolatedException e) {
			throw new FroadServerException(e.getMessage());
		}
	}
}
