/**
 * Project Name:froad-cbank-server-bank
 * File Name:BankAccessModuleServiceImpl.java
 * Package Name:com.froad.thrift.service.impl
 * Date:2015年10月16日上午11:27:40
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift.service.impl;

import org.apache.thrift.TException;

import com.froad.logic.BankAccessModuleLogic;
import com.froad.logic.impl.BankAccessModuleLogicImpl;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BankAccessModuleService;
import com.froad.thrift.vo.BankAccessModuleListRes;
import com.froad.util.PropertiesUtil;


/**
 * ClassName:BankAccessModuleServiceImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月16日 上午11:27:40
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BankAccessModuleServiceImpl extends BizMonitorBaseService implements BankAccessModuleService.Iface {
	
	
	private BankAccessModuleLogic bankModuleLogic = new BankAccessModuleLogicImpl();
	
	public BankAccessModuleServiceImpl() {};
	
	public BankAccessModuleServiceImpl(String name, String version){
		super(name, version);
	};

	@Override
	public BankAccessModuleListRes getBankAccessModuleList(String clientId) throws TException {
		return bankModuleLogic.getBankAccessModuleList(clientId);
	}
	
	public static void main(String args[]) {
		PropertiesUtil.load();
		try {
			BankAccessModuleServiceImpl b = new BankAccessModuleServiceImpl();
			b.getBankAccessModuleList("anhui");
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
}
