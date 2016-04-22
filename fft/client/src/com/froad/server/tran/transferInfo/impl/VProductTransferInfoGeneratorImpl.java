package com.froad.server.tran.transferInfo.impl;

import com.froad.client.buyers.Buyers;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.trans.Pay;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.sellers.Seller;
import com.froad.client.trans.Trans;
import com.froad.client.transRule.RuleDetail;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.server.account.AccountService;
import com.froad.server.tran.transferInfo.TransferInfoGenerator;
import com.froad.util.command.Role;

public class VProductTransferInfoGeneratorImpl extends TransferInfoGeneratorDefaultImpl{
	private AccountService accountService;

	@Override
	protected void updateTransferFromInfo(Trans tran, Pay transPay,
			Seller seller, RuleDetail ruleDetail,
			SellerChannel sellerDepositChannel, Buyers buyer,
			BuyerChannel buyerPayChannel,User user,UserCertification userCertification) throws Exception {
		// TODO Auto-generated method stub
		Role fromRole=Role.getRole(ruleDetail.getRuleDetailTemplet().getFromRole());
		transPay.setFromRole(fromRole.getValue());
		if(fromRole==Role.BUYER){
			com.froad.client.buyers.User userOfBuyer=buyer.getUser();
			if(userOfBuyer!=null){
				transPay.setFromUsername(userOfBuyer.getUsername());
				transPay.setFromPhone(userOfBuyer.getMobilephone());
			}
			transPay.setFromId(String.valueOf(buyer.getId()));
			//以下信息从交易中拿，交易应该有个List《交易的附加交易品属性》
			//交易的附加交易品属性的账户号
			//交易的附加交易品属性的手机号--可以是给手机充值的充值手机号，也可以是表示发货通知的手机号
		}if(fromRole==Role.USER){
			if(user!=null){
				transPay.setFromPhone(user.getMobilephone());
				transPay.setFromId(user.getUserID());
				transPay.setFromUsername(user.getUsername());
			}
			//交易的附加交易品属性的账户号
			//交易的附加交易品属性的手机号--可以是给手机充值的充值手机号，也可以是表示发货通知的手机号
		}else if(fromRole==Role.SELLER){
			transPay.setFromId(String.valueOf(seller.getId()));
		}
	}

	@Override
	protected void updateTransferToInfo(Trans tran, Pay transPay,
			Seller seller, RuleDetail ruleDetail,
			SellerChannel sellerDepositChannel, Buyers buyer,
			BuyerChannel buyerPayChannel,User user,UserCertification userCertification) throws Exception {
		// TODO Auto-generated method stub
		Role toRole=Role.getRole(ruleDetail.getRuleDetailTemplet().getToRole());
		transPay.setToRole(toRole.getValue());
		if(toRole==Role.BUYER){
			com.froad.client.buyers.User userOfBuyer=buyer.getUser();
			if(userOfBuyer!=null)
				transPay.setToPhone(userOfBuyer.getMobilephone());
			transPay.setToId(String.valueOf(buyer.getId()));
			transPay.setToUsername(userOfBuyer.getUsername());
			//交易的附加交易品属性的账户号
			//交易的附加交易品属性的手机号--可以是给手机充值的充值手机号，也可以是表示发货通知的手机号
		}else if(toRole==Role.USER){
			if(user!=null){
				transPay.setToPhone(user.getMobilephone());
				transPay.setToId(user.getUserID());
				transPay.setToUsername(user.getUsername());
			}
			//交易的附加交易品属性的账户号
			//交易的附加交易品属性的手机号--可以是给手机充值的充值手机号，也可以是表示发货通知的手机号
		}else if(toRole==Role.SELLER){
			transPay.setToId(String.valueOf(seller.getId()));
		}
	}
	
	protected void updateTransferProductInfo(Trans tran,Pay transPay){
		transPay.setGoodsId(tran.getTransDetailsList().get(0).getGoodsRackId());
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

}
