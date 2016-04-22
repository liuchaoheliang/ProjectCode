/**
 * Project Name:froad-cbank-server-bank
 * File Name:BankAccessModuleLogic.java
 * Package Name:com.froad.logic
 * Date:2015年10月16日下午2:19:26
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

import com.froad.thrift.vo.BankAccessModuleListRes;

/**
 * ClassName:BankAccessModuleLogic
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月16日 下午2:19:26
 * @author   kevin
 * @version  
 * @see 	 
 */
public interface BankAccessModuleLogic {

	/**
	 * 
	 * getBankAccessModuleList:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年10月16日 下午2:20:11
	 * @param clientId
	 * @return
	 *
	 */
	public BankAccessModuleListRes getBankAccessModuleList(String clientId) ;
}
