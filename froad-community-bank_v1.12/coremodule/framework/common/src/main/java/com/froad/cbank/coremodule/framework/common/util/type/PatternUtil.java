/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2014-3-2 上午11:45:32
 * @Create Author: xull@soap.com
 * @File Name: PatternUtil.java
 * @Function: TODO(用一句话描述该类做什么)
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
package com.froad.cbank.coremodule.framework.common.util.type;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.froad.cbank.coremodule.framework.common.Constants;

/**
 *********************************************** 
 * @Copyright (c) by soap All right reserved.
 * @Create Date: 2014-3-2 上午11:45:32
 * @Create Author: xull@soap.com
 * @File Name: PatternUtil
 * @Function: 正则表达式验证
 * @Last version: 1.0
 * @Last Update Date:
 * @Change Log:
 ************************************************* 
 */
public class PatternUtil {
	/**
	 * 判断是否是电话号码是否合法
	 * @param num
	 * @return
	 */
	public static boolean isMobile(String mobile){
		if(StringUtil.isBlank(mobile)){
			return false;
		}
        Pattern p = Pattern.compile(Constants.getPatternMobile());
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
}
