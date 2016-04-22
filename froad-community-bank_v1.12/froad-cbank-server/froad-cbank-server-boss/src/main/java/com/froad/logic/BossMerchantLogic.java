package com.froad.logic;

import java.util.List;

import com.froad.po.Outlet;

public interface BossMerchantLogic {

	/**
	 * 查询提货网点(预售用到)  
	 * @Title: findSubBankOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @return List<Outlet>    返回类型 
	 * @throws
	 */
	public List<Outlet> findSubBankOutlet(String clientId,String orgCode);
}
