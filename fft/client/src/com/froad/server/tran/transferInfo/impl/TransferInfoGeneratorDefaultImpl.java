package com.froad.server.tran.transferInfo.impl;

import org.apache.log4j.Logger;

import com.froad.client.buyers.Buyers;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.sellers.Seller;
import com.froad.client.trans.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.transRule.RuleDetail;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.exception.transaction.QuantityOfTransferCalculatIsNull;
import com.froad.server.tran.transferInfo.QuantityOfTransferCalculator;
import com.froad.server.tran.transferInfo.QuantityOfTransferCalculatorFactory;
import com.froad.server.tran.transferInfo.TransferInfoGenerator;
import com.froad.common.PayState;
import com.froad.util.command.RuleDetailCategory;

public abstract class TransferInfoGeneratorDefaultImpl implements TransferInfoGenerator {
	
	private static Logger log=Logger.getLogger(TransferInfoGeneratorDefaultImpl.class);
	private QuantityOfTransferCalculatorFactory quantityOfTransferCalculatorFactory;
	
	
	
	public QuantityOfTransferCalculatorFactory getQuantityOfTransferCalculatorFactory() {
		return quantityOfTransferCalculatorFactory;
	}

	public void setQuantityOfTransferCalculatorFactory(
			QuantityOfTransferCalculatorFactory quantityOfTransferCalculatorFactory) {
		this.quantityOfTransferCalculatorFactory = quantityOfTransferCalculatorFactory;
	}

	public Pay generateTransferInfo(Trans tran, Seller seller,
			RuleDetail ruleDetail, SellerChannel sellerDepositChannel,
			Buyers buyer, BuyerChannel buyerPayChannel,User user,UserCertification userCertification)
			throws Exception {
		// TODO Auto-generated method stub
		check(tran, seller,ruleDetail,sellerDepositChannel,buyer,buyerPayChannel);
		
		Pay transPay=new Pay();
		
		//计算资金流转的金额
		System.out.println("==============计算流转之前==============="+ruleDetail.getRuleDetailTemplet().getRuleDetailType());
		double quantity=calculateQuantityOfTransfer(tran,transPay,ruleDetail);
		System.out.println("==============计算流转之后==============="+ruleDetail.getRuleDetailTemplet().getRuleDetailType());
		//流转量为0，则不用流转，所以不生成流转信息。
		if(quantity==0)
			return null;
		transPay.setPayValue(String.valueOf(quantity));
		
		transPay.setTransId(String.valueOf(tran.getId()));
		transPay.setTrackNo(tran.getTrackNo());
		transPay.setType(ruleDetail.getRuleDetailTemplet().getRuleDetailCategory());
		updateTransferTypeDetail(tran, ruleDetail, transPay);
		transPay.setIsIntime(ruleDetail.getRuleDetailTemplet().getSettleType());
		
		if(sellerDepositChannel!=null)
			transPay.setChannelId(String.valueOf(sellerDepositChannel.getChannelId()));
		else if(buyerPayChannel!=null)
			transPay.setChannelId(String.valueOf(buyerPayChannel.getChannelId()));
		else if(userCertification!=null)
			transPay.setChannelId(userCertification.getChannelId());
		transPay.setRuleId(String.valueOf(ruleDetail.getRuleId()));
		transPay.setRuleDetailId(String.valueOf(ruleDetail.getId()));
		transPay.setState(PayState.PAY_WAIT);
		
		transPay.setStep(String.valueOf(tran.getPayList().size()));

		//更新资金流转信息的from信息，如：from角色，账户信息，如果from角色为买家，则记录from角色的手机号，不记买家账户信息
		updateTransferFromInfo(tran,transPay,seller,ruleDetail,sellerDepositChannel,buyer,buyerPayChannel, user, userCertification);
		//更新资金流转信息的to信息  ，如：to角色，账户信息，如果to角色为买家，则记录to角色的手机号，不记买家账户信息
		updateTransferToInfo(tran,transPay,seller,ruleDetail,sellerDepositChannel, buyer,buyerPayChannel, user, userCertification);
		//计算资金流转的金额
//		calculateQuantityOfTransfer(tran,transPay,ruleDetail);
		//
		updateTransferProductInfo(tran,transPay);
		
		log.info("交易跟踪号为："+tran.getTrackNo()+",的流转信息：流转物品的类型："+transPay.getType()+",流转类型："+transPay.getTypeDetail()+",流转的量:"+transPay.getPayValue()+"，从角色:"+transPay.getFromRole()+"，到角色:"+transPay.getToRole()+
				"，从id:"+transPay.getFromId()+"，到id:"+transPay.getToId()+"，从手机号:"+transPay.getFromPhone()+"，到手机号:"+transPay.getToPhone()+"，从账户号:"+transPay.getFromAccountNo()+"，到账户号:"+transPay.getToAccountNo()+"，从账户名:"+transPay.getFromAccountName()+"，到账户名:"+transPay.getToAccountName()
				+"，从userName:"+transPay.getFromUsername()+"，到userName:"+transPay.getToUsername()
				+"，流转状态:"+transPay.getState()+",是否及时流转："+transPay.getIsIntime()+",使用的流转渠道："+
				transPay.getChannelId()+",使用的规则明细为:"+transPay.getRuleDetailId()+",处理的序号："+transPay.getStep());
		return transPay;
	}
	
	protected void updateTransferProductInfo(Trans tran,Pay pay){
		
	}
	
	protected void updateTransferTypeDetail(Trans tran,RuleDetail ruleDetail,Pay pay){
		if(RuleDetailCategory.VProduct.getValue().equals(ruleDetail.getRuleDetailTemplet().getRuleDetailCategory()))
			pay.setTypeDetail(tran.getTransDetailsList().get(0).getGoods().getGoodsCategoryId());
		//else 交易明细中交易平台的类型，即交易品属于哪个分类
		else
			pay.setTypeDetail(ruleDetail.getRuleDetailTemplet().getRuleDetailType());
	}
	
	private double calculateQuantityOfTransfer(Trans tran, Pay pay,
			RuleDetail ruleDetail) throws Exception {
		// TODO Auto-generated method stub
		QuantityOfTransferCalculator quantityOfTransferCalculator=quantityOfTransferCalculatorFactory.getQuantityOfTransferCalculator(ruleDetail);
		if(quantityOfTransferCalculator==null)
			throw new QuantityOfTransferCalculatIsNull("转移数量的计算器为空！");
		double quantity=quantityOfTransferCalculator.calculateQuantityOfTransfer(tran, ruleDetail);
		return quantity;
	}
	
	
	
	/**
	  * 方法描述：更新流转信息的from信息，如：from角色，账户信息，如果from角色为买家，则记录from角色的手机号，不记买家账户信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-11-27 上午11:56:23
	 */
	protected abstract void updateTransferFromInfo(Trans tran,Pay transPay, Seller seller,
			RuleDetail ruleDetail, SellerChannel sellerDepositChannel,
			Buyers buyer, BuyerChannel buyerPayChannel,User user,UserCertification userCertification) throws Exception;
	
	/**
	  * 方法描述：更新流转信息的to信息  ，如：to角色，账户信息，如果to角色为买家，则记录to角色的手机号，不记买家账户信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-11-27 上午11:56:15
	 */
	protected abstract void updateTransferToInfo(Trans tran,Pay transPay, Seller seller,
			RuleDetail ruleDetail, SellerChannel sellerDepositChannel,
			Buyers buyer, BuyerChannel buyerPayChannel,User user,UserCertification userCertification) throws Exception;
	
	
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 上午11:57:22
	 */
	private void check(Trans tran, Seller seller,
			RuleDetail ruleDetail, SellerChannel sellerDepositChannel,
			Buyers buyer, BuyerChannel buyerPayChannel){
	
		checkSellerInfo( seller,ruleDetail,sellerDepositChannel);
		checkBuyerInfo(buyer,buyerPayChannel);
		
		checkRuleDetail(tran,ruleDetail);
	}
	
	
	private void checkSellerInfo(Seller seller,
			RuleDetail ruleDetail,
			SellerChannel sellerDepositChannel){
		//卖家是否为空，卖家ID是否为空，
		//卖家收款资金渠道是否为空，卖家收款资金渠道ID是否为空,即sellerDepositChannel 是否为空
		//sellerDepositChannel.getFundsChannel是否为空，sellerDepositChannel.getFundsChannel.getId是否为空
		//卖家账户是否为空，卖家账户ID是否为空
		//sellerAccount是否在sellerDepositChannel.getSellerAccount里面
		//卖家规则是否为空，  【因为卖家必须与方付通签规则协议，所以卖家一定使用一种规则】
		//sellerRuleDetail是否在卖家规则.getSellerRuleDetail里面
	}
	
	private void checkBuyerInfo(Buyers buyer,BuyerChannel buyerPayChannel){
		//买家是否为空，买家ID是否为空，
		//买家的手机号是否为空，手机号是否合法
		//买家buyerPayChannel否为空，buyerPayChannel的ID是否为空
		//buyerPayChannel.getFundsChannelId是否等于sellerDepositChannel.getFundsChannelId
	}
	
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-11-27 上午11:57:15
	 */
	private void checkRuleDetail(Trans tran,
			RuleDetail ruleDetail){
		//计算资金流转的金额 方面的检查
//		String totalCurrencyOfTran_String=tran.getCurrencyValueAll();
//		if(Assert.empty(totalCurrencyOfTran_String))
//			throw exception 总交易金额未知，该交易的具体资金流转无法计算
//		double totalCurrencyOfTran=Double.valueOf(totalCurrencyOfTran_String);//总交易金额
		//throw exception 总交易金额有误
//		if(totalCurrencyOfTran<0)
//			throw exception 总交易金额有误 
		
		//check sellerRuleDetail is not empty
		//check role is not empty and 是系统提供的角色
		//check formula of sellerRuleDetail  is not empty
		//check parameter of formula  is not empty
	}
	
	

}
