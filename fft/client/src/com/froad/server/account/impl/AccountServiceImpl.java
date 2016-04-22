package com.froad.server.account.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.froad.client.buyers.BuyerChannel;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.trans.Trans;
import com.froad.server.account.AccountService;
import com.froad.util.command.AccountType;
import com.froad.util.command.Role;

public class AccountServiceImpl implements AccountService {
	private static final Logger log=Logger.getLogger(AccountServiceImpl.class);
	
	
	/**
	  * 方法描述：角色在某个资金渠道的，某种账户类型的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:25
	 */
	public Map<String,String> getAccountInfo(Trans trans,Role role,SellerChannel SellerChannel,AccountType accountType){
		Map<String,String> accountInfo=new HashMap();
		switch(role){
			case BUYER:
				accountInfo=getBuyerAccountInfo(trans,null,accountType);
				break;
			case SELLER:
				accountInfo=getSellerAccountInfo(trans,SellerChannel.getFundsChannel(),accountType);
				break;
			case BANK:
				break;
			case FFT:
				accountInfo=getFFTAccountInfo(trans,SellerChannel.getFundsChannel(),accountType);
				break;
		}
		return accountInfo;
	}
	
	/**
	  * 方法描述：方付通在某个资金渠道的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:26
	 */
	private Map<String,String> getFFTAccountInfo(Trans trans,BuyerChannel buyerPayChannel,AccountType accountType){
		Map<String,String> accountInfo=new HashMap();
		com.froad.client.buyers.FundsChannel fundsChannel=buyerPayChannel.getFundsChannel();
		switch(accountType){
		case Receive: 
			accountInfo.put("AccountName", fundsChannel.getFftGatheringAccountName());
			accountInfo.put("AccountNum", fundsChannel.getFftGatheringAccountNumber());
			break ;
		case Points_Currency: 
			accountInfo.put("AccountName", fundsChannel.getFftPointsAccountName());
			accountInfo.put("AccountNum", fundsChannel.getFftPointsAccountNumber());
			break ;
		case Factorage: 
			accountInfo.put("AccountName", fundsChannel.getFftPointsPoundageAccountName());
			accountInfo.put("AccountNum", fundsChannel.getFftPointsPoundageAccountNumber());
			break;
		case Allowance: 
			accountInfo.put("AccountName", fundsChannel.getFftAllowanceAccountName());
			accountInfo.put("AccountNum", fundsChannel.getFftAllowanceAccountNumber());
			break;
		case Withdraw_For_Points: 
			accountInfo.put("AccountName", fundsChannel.getPointsWithdrawAccountName());
			accountInfo.put("AccountNum", fundsChannel.getPointsWithdrawAccountNumber());
			break;
		case Withdraw_Factorage_For_Points:
			accountInfo.put("AccountName", fundsChannel.getPointsWithdrawPoundageAccountName());
			accountInfo.put("AccountNum", fundsChannel.getPointsWithdrawPoundageAccountNumber());
			break;
		case Points:
			log.info("生活平台，不需要提供获取方付通的积分账户的接口!若是要实现方付通,在某个积分平台的商户上[接入积分平台来消费积分的系统如生活平台，某商城，称为积分平台的商户，或称积分平台的合作伙伴],给会员赠送积分，不用知道方付通的积分账户，直接调用积分平台的相应接口即可");
			break;
		}
		return accountInfo;
	}
	
	
	/**
	  * 方法描述：方付通在某个资金渠道的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:26
	 */
	private Map<String,String> getFFTAccountInfo(Trans trans,com.froad.client.sellers.FundsChannel fundsChannel,AccountType accountType){
		Map<String,String> accountInfo=new HashMap();
		switch(accountType){
			case Receive: 
				accountInfo.put("AccountName", fundsChannel.getFftGatheringAccountName());
				accountInfo.put("AccountNum", fundsChannel.getFftGatheringAccountNumber());
				break ;
			case Points_Currency: 
				accountInfo.put("AccountName", fundsChannel.getFftPointsAccountName());
				accountInfo.put("AccountNum", fundsChannel.getFftPointsAccountNumber());
				break ;
			case Factorage: 
				accountInfo.put("AccountName", fundsChannel.getFftPointsPoundageAccountName());
				accountInfo.put("AccountNum", fundsChannel.getFftPointsPoundageAccountNumber());
				break;
			case Allowance: 
				accountInfo.put("AccountName", fundsChannel.getFftAllowanceAccountName());
				accountInfo.put("AccountNum", fundsChannel.getFftAllowanceAccountNumber());
				break;
			case Withdraw_For_Points: 
				accountInfo.put("AccountName", fundsChannel.getPointsWithdrawAccountName());
				accountInfo.put("AccountNum", fundsChannel.getPointsWithdrawAccountNumber());
				break;
			case Withdraw_Factorage_For_Points:
				accountInfo.put("AccountName", fundsChannel.getPointsWithdrawPoundageAccountName());
				accountInfo.put("AccountNum", fundsChannel.getPointsWithdrawPoundageAccountNumber());
				break;
			case Points:
				log.info("生活平台，不需要提供获取方付通的积分账户的接口!若是要实现方付通,在某个积分平台的商户上[接入积分平台来消费积分的系统如生活平台，某商城，称为积分平台的商户，或称积分平台的合作伙伴],给会员赠送积分，不用知道方付通的积分账户，直接调用积分平台的相应接口即可");
				break;
		}
		return accountInfo;
	}
	
	/**
	  * 方法描述：方付通在某个资金渠道的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:26
	 */
	private Map<String,String> getFFTAccountInfo(Trans trans,com.froad.client.sellerChannel.FundsChannel fundsChannel,AccountType accountType){
		Map<String,String> accountInfo=new HashMap();
		switch(accountType){
		case Receive: 
			accountInfo.put("AccountName", fundsChannel.getFftGatheringAccountName());
			accountInfo.put("AccountNum", fundsChannel.getFftGatheringAccountNumber());
			break ;
		case Points_Currency: 
			accountInfo.put("AccountName", fundsChannel.getFftPointsAccountName());
			accountInfo.put("AccountNum", fundsChannel.getFftPointsAccountNumber());
			break ;
		case Factorage: 
			accountInfo.put("AccountName", fundsChannel.getFftPointsPoundageAccountName());
			accountInfo.put("AccountNum", fundsChannel.getFftPointsPoundageAccountNumber());
			break;
		case Allowance: 
			accountInfo.put("AccountName", fundsChannel.getFftAllowanceAccountName());
			accountInfo.put("AccountNum", fundsChannel.getFftAllowanceAccountNumber());
			break;
		case Withdraw_For_Points: 
			accountInfo.put("AccountName", fundsChannel.getPointsWithdrawAccountName());
			accountInfo.put("AccountNum", fundsChannel.getPointsWithdrawAccountNumber());
			break;
		case Withdraw_Factorage_For_Points:
			accountInfo.put("AccountName", fundsChannel.getPointsWithdrawPoundageAccountName());
			accountInfo.put("AccountNum", fundsChannel.getPointsWithdrawPoundageAccountNumber());
			break;
		case Points:
			log.info("生活平台，不需要提供获取方付通的积分账户的接口!若是要实现方付通,在某个积分平台的商户上[接入积分平台来消费积分的系统如生活平台，某商城，称为积分平台的商户，或称积分平台的合作伙伴],给会员赠送积分，不用知道方付通的积分账户，直接调用积分平台的相应接口即可");
			break;
		}
		return accountInfo;
	}
	
	
	/**
	  * 方法描述：方付通在某个资金渠道的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:26
	 */
	private Map<String,String> getSellerAccountInfo(Trans trans,com.froad.client.sellers.FundsChannel fundsChannel,AccountType accountType){
		Map<String,String> accountInfo=new HashMap();
		switch(accountType){
			case Receive: 
				break ;
			case Points_Currency: 
				break ;
			case Factorage: 
				break;
			case Allowance: 
				break;
//			case Points:
//				if(trans!=null ){
//					accountInfo.put("AccountName", "积分账户");
//					accountInfo.put("AccountNum", trans.getFftPointsAccount());
//				}
//				
//				break;
//			case Points_Bank:
//				if(trans!=null ){
//					accountInfo.put("AccountName", "积分账户");
//					accountInfo.put("AccountNum", trans.getBankPointsAccount());
//				}
//				
//				break;
		}
		return accountInfo;
	}

	
	/**
	  * 方法描述：方付通在某个资金渠道的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:26
	 */
	private Map<String,String> getBuyerAccountInfo(Trans trans,com.froad.client.buyers.FundsChannel fundsChannel,AccountType accountType){
		Map<String,String> accountInfo=new HashMap();
		switch(accountType){
			case Receive: 
				break ;
			case Points_Currency: 
				break ;
			case Factorage: 
				break;
			case Allowance: 
				break;
			case Points:
				if(trans!=null ){
					accountInfo.put("AccountName", "积分账户");
					accountInfo.put("AccountNum", trans.getFftPointsAccount());
				}
				
				break;
			case Points_Bank:
				if(trans!=null ){
					accountInfo.put("AccountName", "积分账户");
					accountInfo.put("AccountNum", trans.getBankPointsAccount());
				}
				
				break;
		}
		return accountInfo;
	}



	public Map<String, String> getAccountInfo(Role role,
			SellerChannel SellerChannel, AccountType accountType) {
		// TODO Auto-generated method stub
		return this.getAccountInfo(null, role, SellerChannel, accountType);
	}
	

	public Map<String, String> getAccountInfo(Role role,
			BuyerChannel buyerPayChannel, AccountType accountType) {
		// TODO Auto-generated method stub
		return this.getAccountInfo(null, role, buyerPayChannel, accountType);
	}

	
	/**
	  * 方法描述：角色在某个资金渠道的，某种账户类型的账户号 和该账户的账户名  key为：AccountName,AccountNum
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 下午12:00:25
	 */
	public Map<String,String> getAccountInfo(Trans trans,Role role,BuyerChannel buyerPayChannel,AccountType accountType){
		Map<String,String> accountInfo=new HashMap();
		if(buyerPayChannel==null)
			return accountInfo;
		switch(role){
			case BUYER:
				accountInfo=getBuyerAccountInfo(trans,buyerPayChannel.getFundsChannel(),accountType);
				break;
			case SELLER:
				accountInfo=getSellerAccountInfo(trans,null,accountType);
				break;
			case BANK:
				break;
			case FFT:
				accountInfo=getFFTAccountInfo(trans,buyerPayChannel,accountType);
				break;
		}
		return accountInfo;
	}

}

