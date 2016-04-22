/**
 * Project Name:froad-cbank-server-boss
 * File Name:BankAccessLogic.java
 * Package Name:com.froad.logic
 * Date:2015年9月17日下午4:46:56
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

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

/**
 * ClassName:BankAccessLogic
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月17日 下午4:46:56
 * @author   kevin
 * @version  
 * @see 	 
 */
public interface BankAccessLogic {

	/**
	 * 获取银行列表信息
	 *
	 */
	public BankAccessListRes  getBankAccessList(BankAccessListReq req);
	
	/**
	 * 获取银行详情
	 */
	public BankAccessDetailRes getBankAccessDetail(BankAccessDetailReq req) ;

	/**
	 * 添加银行信息
	 */
	public BankAccessAddRes addBankAccess(BankAccessAddReq req) ;

	/**
	 * 修改银行信息
	 */
	public BankAccessEditRes editBankAccess(BankAccessEditReq req) ;

	/**
	 * 删除银行信息
	 */
	public BankAccessDeleteRes deleteBankAccess(BankAccessDeleteReq req) ;
	
	/**
	 *获取客户端集合信息
	 */
	public BankAccessClientListRes getClientList(BankAccessClientListReq req);
}
