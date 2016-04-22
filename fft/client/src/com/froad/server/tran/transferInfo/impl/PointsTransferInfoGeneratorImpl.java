package com.froad.server.tran.transferInfo.impl;

import java.util.HashMap;
import java.util.Map;
import com.froad.client.trans.Pay;
import com.froad.client.buyers.Buyers;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.user.User;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.sellers.Seller;
import com.froad.client.trans.Trans;
import com.froad.client.transRule.RuleDetail;
import com.froad.client.userCertification.UserCertification;
import com.froad.server.account.AccountService;
import com.froad.util.command.AccountType;
import com.froad.util.command.Role;

public class PointsTransferInfoGeneratorImpl  extends TransferInfoGeneratorDefaultImpl{
	private AccountService accountService;

	/**
	  * 方法描述：更新积分流转信息的from信息，如：from角色，账户信息，如果from角色为买家，则记录from角色的手机号，不记买家账户信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 上午11:56:23
	 */
	@Override
	protected void updateTransferFromInfo(Trans tran, Pay transPay,
			Seller seller, RuleDetail ruleDetail,
			SellerChannel sellerDepositChannel, Buyers buyer,
			BuyerChannel buyerPayChannel,User user,UserCertification userCertification) throws Exception {
		// TODO Auto-generated method stub
		
		Role fromRole=Role.getRole(ruleDetail.getRuleDetailTemplet().getFromRole());
		
		transPay.setFromRole(fromRole.getValue());
		AccountType accountType=AccountType.getAccountType(ruleDetail.getRuleDetailTemplet().getFromAccountType());
		
		Map<String,String> fromAccountInfo=new HashMap();
		String fromMPhone="";
		if(fromRole==Role.BUYER){
			com.froad.client.buyers.User userOfBuyer=buyer.getUser();
			if(userOfBuyer!=null)
				fromMPhone=userOfBuyer.getMobilephone();
			transPay.setFromId(String.valueOf(buyer.getId()));
			transPay.setFromUsername(userOfBuyer.getUsername());
			fromAccountInfo=accountService.getAccountInfo(tran,fromRole, buyerPayChannel, accountType);
		}else if(fromRole==Role.USER){
			if(user!=null){
				fromMPhone=user.getMobilephone();
				transPay.setFromId(user.getUserID());
				transPay.setFromUsername(user.getUsername());
			}
//			fromAccountInfo=this.getAccountInfo(fromRole, sellerDepositChannel, accountType);
		}else if(fromRole==Role.SELLER){
			com.froad.client.sellers.User userOfSeller=seller.getUser();
			if(userOfSeller!=null)
				fromMPhone=userOfSeller.getMobilephone();
			transPay.setFromId(String.valueOf(seller.getId()));
			transPay.setFromUsername(userOfSeller.getUsername());
//			fromAccountInfo.put("AccountName", sellerDepositChannel.getAccountName());
//			fromAccountInfo.put("AccountNum", sellerDepositChannel.getAccountNumber());
//			fromAccountInfo=this.getAccountInfo(fromRole, sellerDepositChannel, accountType);
		}
		transPay.setFromAccountName(fromAccountInfo.get("AccountName"));
		transPay.setFromAccountNo(fromAccountInfo.get("AccountNum"));
		transPay.setFromPhone(fromMPhone);
		transPay.setFromAccountType(accountType.getValue());
		transPay.setFromRole(fromRole.getValue());
	}
	
	/**
	  * 方法描述：更新积分流转信息的to信息  ，如：to角色，账户信息，如果to角色为买家，则记录to角色的手机号，不记买家账户信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 上午11:56:15
	 */
	@Override
	protected void updateTransferToInfo(Trans tran, Pay transPay,
			Seller seller, RuleDetail ruleDetail,
			SellerChannel sellerDepositChannel, Buyers buyer,
			BuyerChannel buyerPayChannel,User user,UserCertification userCertification) throws Exception {
		// TODO Auto-generated method stub
		
		Role toRole=Role.getRole(ruleDetail.getRuleDetailTemplet().getToRole());
		
		transPay.setToRole(toRole.getValue());
		AccountType accountType=AccountType.getAccountType(ruleDetail.getRuleDetailTemplet().getToAccountType());
		Map<String,String> toAccountInfo=new HashMap();
		String toMPhone="";
		if(toRole==Role.BUYER){
			com.froad.client.buyers.User userOfBuyer=buyer.getUser();
			if(userOfBuyer!=null)
				toMPhone=userOfBuyer.getMobilephone();
			transPay.setToId(String.valueOf(buyer.getId()));
			transPay.setToUsername(userOfBuyer.getUsername());
			
			toAccountInfo=accountService.getAccountInfo(tran,toRole, buyerPayChannel, accountType);
		}else if(toRole==Role.USER){
			if(user!=null){
				toMPhone=user.getMobilephone();
				transPay.setToId(user.getUserID());
				transPay.setToUsername(user.getUsername());
			}
//			fromAccountInfo=this.getAccountInfo(fromRole, sellerDepositChannel, accountType);
		}else if(toRole==Role.SELLER){
			com.froad.client.sellers.User userOfSeller=seller.getUser();
			if(userOfSeller!=null)
				toMPhone=userOfSeller.getMobilephone();
			transPay.setToId(String.valueOf(seller.getId()));
			transPay.setToUsername(userOfSeller.getUsername());
//			toAccountInfo.put("AccountName", sellerDepositChannel.getAccountName());
//			toAccountInfo.put("AccountNum", sellerDepositChannel.getAccountNumber());
//			toAccountInfo=this.getAccountInfo(toRole, sellerDepositChannel, accountType);
		}
		transPay.setToAccountName(toAccountInfo.get("AccountName"));
		transPay.setToAccountNo(toAccountInfo.get("AccountNum"));
		transPay.setToPhone(toMPhone);
		transPay.setToAccountType(accountType.getValue());
		transPay.setToRole(toRole.getValue());
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}


}
