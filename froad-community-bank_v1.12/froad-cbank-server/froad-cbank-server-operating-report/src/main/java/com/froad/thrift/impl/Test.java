/**
 * Project Name:froad-cbank-server-boss-operating-report-0.0.1-SNAPSHOT
 * File Name:Test.java
 * Package Name:com.froad.thrift.impl
 * Date:2016年1月29日下午12:48:40
 * Copyright (c) 2016, F-Road, Inc.
 *
*/

package com.froad.thrift.impl;

import com.froad.enums.ResultCode;
import com.froad.pool.ServicePool;
import com.froad.process.impl.Process1;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.PropertiesUtil;

/**
 * ClassName:Test
 * Reason:	 TODO ADD REASON.
 * Date:     2016年1月29日 下午12:48:40
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class Test {
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		ResultVo resultVo = null;
		Process1 process = new Process1("processTest1");
		ServicePool.execute(process);
		System.out.println("bbbbbb");
		resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getCode());
		System.out.println(resultVo);
	}
	
}
