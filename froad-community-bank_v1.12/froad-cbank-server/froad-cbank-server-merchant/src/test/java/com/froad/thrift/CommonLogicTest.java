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
 * @Title: CommonLogicTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年5月16日
 */

package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import com.froad.logic.CommonLogic;
import com.froad.logic.impl.CommonLogicImpl;
import com.froad.util.PropertiesUtil;


/**    
 * <p>Title: CommonLogicTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年5月16日 下午3:03:20   
 */

public class CommonLogicTest {
	public static void main(String[] args) {
		PropertiesUtil.load();
		CommonLogic commonLogic = new CommonLogicImpl();
		List<String> orgCodes = new ArrayList<String>();
		orgCodes.add("020701");
		orgCodes.add("050000");
		orgCodes.add("060000");
		System.out.println(commonLogic.queryLastOrgCode("chongqing", orgCodes));
	}
}
