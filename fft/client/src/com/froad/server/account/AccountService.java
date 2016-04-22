package com.froad.server.account;

import java.util.Map;

import com.froad.client.buyers.BuyerChannel;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.trans.Trans;
import com.froad.util.command.AccountType;
import com.froad.util.command.Role;

public interface AccountService {
	/**
	  * 方法描述：角色在某个资金渠道的，某种账户类型的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:25
	 */
	public Map<String,String> getAccountInfo(Role role,SellerChannel sellerDepositChannel,AccountType accountType);
	
	/**
	  * 方法描述：角色在某个资金渠道的，某种账户类型的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:25
	 */
	public Map<String,String> getAccountInfo(Trans trans,Role role,SellerChannel sellerDepositChannel,AccountType accountType);
	
	
	/**
	  * 方法描述：角色在某个资金渠道的，某种账户类型的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:25
	 */
	public Map<String,String> getAccountInfo(Role role,BuyerChannel buyerPayChannel,AccountType accountType);
	
	/**
	  * 方法描述：角色在某个资金渠道的，某种账户类型的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:25
	 */
	public Map<String,String> getAccountInfo(Trans trans,Role role,BuyerChannel buyerPayChannel,AccountType accountType);
	
}
