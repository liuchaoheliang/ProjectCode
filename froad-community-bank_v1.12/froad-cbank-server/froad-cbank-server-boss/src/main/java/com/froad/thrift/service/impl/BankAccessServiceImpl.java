/**
 * Project Name:froad-cbank-server-boss
 * File Name:BankAccessServiceImpl.java
 * Package Name:com.froad.thrift.service.impl
 * Date:2015年9月17日下午4:28:04
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.logic.BankAccessLogic;
import com.froad.logic.impl.BankAccessLogicImpl;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BankAccessService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.bankaccess.BankAccessAddReq;
import com.froad.thrift.vo.bankaccess.BankAccessAddRes;
import com.froad.thrift.vo.bankaccess.BankAccessClientListReq;
import com.froad.thrift.vo.bankaccess.BankAccessClientListRes;
import com.froad.thrift.vo.bankaccess.BankAccessDeleteReq;
import com.froad.thrift.vo.bankaccess.BankAccessDeleteRes;
import com.froad.thrift.vo.bankaccess.BankAccessDetailReq;
import com.froad.thrift.vo.bankaccess.BankAccessDetailRes;
import com.froad.thrift.vo.bankaccess.BankAccessEditReq;
import com.froad.thrift.vo.bankaccess.BankAccessEditRes;
import com.froad.thrift.vo.bankaccess.BankAccessListReq;
import com.froad.thrift.vo.bankaccess.BankAccessListRes;
import com.froad.thrift.vo.bankaccess.BankAccessVo;
import com.froad.thrift.vo.bankaccess.FunctionModuleVo;
import com.froad.thrift.vo.bankaccess.PaymentMethodVo;
import com.froad.util.PropertiesUtil;

/**
 * ClassName:BankAccessServiceImpl
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月17日 下午4:28:04
 * @author   kevin
 * @version  
 * @see 	 
 */
public class BankAccessServiceImpl extends BizMonitorBaseService implements BankAccessService.Iface{

	private BankAccessLogic logic = new BankAccessLogicImpl();
	
	public BankAccessServiceImpl(){
		
	};
	
	public BankAccessServiceImpl(String name, String version) {
		super(name, version);
	}
	 
	
	/**
	 * 获取多银行列表
	 */
	public BankAccessListRes getBankAccessList(BankAccessListReq req) throws TException {
		return logic.getBankAccessList(req);
	}

	/**
	 * 获取银行详情
	 */
	public BankAccessDetailRes getBankAccessDetail(BankAccessDetailReq req) throws TException {
		return logic.getBankAccessDetail(req);
	}

	/**
	 * 添加银行信息
	 */
	public BankAccessAddRes addBankAccess(BankAccessAddReq req) throws TException {
		return logic.addBankAccess(req);
	}

	/**
	 * 修改银行信息
	 */
	public BankAccessEditRes editBankAccess(BankAccessEditReq req) throws TException {
		return logic.editBankAccess(req);
	}

	/**
	 * 删除银行信息
	 */
	public BankAccessDeleteRes deleteBankAccess(BankAccessDeleteReq req) throws TException {
		return logic.deleteBankAccess(req);
	}


	/**
	 * 获取客户端集合信息
	 */
	public BankAccessClientListRes getClientList(BankAccessClientListReq req) throws TException {
		return logic.getClientList(req);
	}
	
	
}
