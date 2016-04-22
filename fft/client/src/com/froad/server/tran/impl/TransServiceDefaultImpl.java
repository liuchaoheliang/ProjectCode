package com.froad.server.tran.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.froad.client.buyers.Buyers;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.sellers.Seller;
import com.froad.client.trans.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.transRule.Rule;
import com.froad.client.transRule.RuleDetail;
import com.froad.client.transRule.TransRule;
import com.froad.client.transRule.TransRuleService;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.exception.transaction.transferInfoGeneratorIsNull;
import com.froad.server.tran.TranService;
import com.froad.server.tran.transferInfo.TransferInfoGenerator;
import com.froad.server.tran.transferInfo.TransferInfoGeneratorFactory;
import com.froad.util.Assert;
import com.froad.util.command.GoodCategory;
import com.froad.util.command.PayType;
import com.froad.util.command.RoleCurrency;
import com.froad.util.command.RuleType;
import com.froad.util.command.State;
import com.froad.util.command.TransType;
import com.froad.util.command.UseTime;

public  class TransServiceDefaultImpl implements TranService {
	private TransferInfoGeneratorFactory transferInfoGeneratorFactory;
	private static final Logger log=Logger.getLogger(TransServiceDefaultImpl.class);
	private TransRuleService transRuleService;
	
	public void countTansferInfoTran(Trans tran, Seller seller,
			SellerChannel sellerDepositChannel, Buyers buyer,
			BuyerChannel buyerPayChannel,User user,UserCertification userCertification, UseTime useTime)
			throws Exception {
		// TODO Auto-generated method stub
		//计算交易（积分流向，资金流向）所要产生的流向信息 (流即转移，因交易是2者或多者之间发生的货币或积分等的一个转移。这里是计算一个交易发生的转移信息，包括：转移的东西【如资金，积分】，转移多少【如从买家转移到卖家50元】，从哪里转移到哪里，转移的状态，因什么原因产生这个转移信息【如赠送积分，购买积分，赠送积分手续费，补贴，纯买家到卖家的收款】)
		countTransferInfoOfTran(tran, seller,sellerDepositChannel,buyer,buyerPayChannel, user, userCertification,useTime);
		List<Pay> transferInfoOfTranList=tran.getPayList();
		
//		通过该交易的流转信息，来更新该交易各个角色的资金 ，【仅根据资金流转信息，来计算该交易的各个角色的资金】
		if(!Assert.empty(transferInfoOfTranList))
			updateCurrencyOfAllRoleForTheTran(tran,transferInfoOfTranList);
		//更新交易信息：交易相关金额，交易相关积分
		updateTranOtherInfo();
	}
	
	/**
	  * 方法描述：计算交易（积分流向，资金流向）所有的流向信息 (流即转移，因交易是2者或多者之间发生的货币或积分等的一个转移，这里是计算一个交易发生的转移信息：转移的东西【如资金，积分】，转移多少【如从买家转移到卖家50元】，从哪里转移到哪里，转移的状态，因什么原因产生这个转移信息【如赠送积分，购买积分，赠送积分手续费，补贴，纯买家到卖家的收款】)
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-12-19 下午03:18:07
	 */
	private void countTransferInfoOfTran(Trans tran,Seller seller,
			SellerChannel sellerDepositChannel, Buyers buyer,
			BuyerChannel buyerPayChannel,User user,UserCertification userCertification,UseTime useTime) throws Exception{
		countTransferInfoOfTranByRules(tran, seller,sellerDepositChannel,buyer,buyerPayChannel, user, userCertification, useTime);
	}
	
	public  List<Rule> getRules(Trans tran,SellerChannel sellerDepositChannel,BuyerChannel buyerPayChannel){
		return getRules(tran,sellerDepositChannel);
	}
	public  List<Rule> getRules(Trans tran,SellerChannel sellerDepositChannel){
		List<Rule> rules=new ArrayList();
		Map<String, Map> allRules=getRulesFromCache();
		Map<String,List<TransRule>> rules_transType=allRules.get("rules-transType");
		Map<String,List<TransRule>> rules_ruleType=allRules.get("rules-ruleType");
		Map<String,TransRule> rules_ruleId=allRules.get("rules-ruleId");
		
		if(Assert.empty(allRules))
			return null;
		TransType transType=TransType.getTransType(tran.getTransType());
		switch(transType){
		case Trans_Points_Exch_Product: ;
		case Trans_Group_buy: //根据交易类型来获取交易规则--上面三种类型的交易，每种类型的交易应该都只有一个启用了的规则
//			List<TransRule> rulesOfOneTransType=rules_transType.get(tran.getTransType());
//			if(!Assert.empty(rulesOfOneTransType))
//				rules.add(rulesOfOneTransType.get(0));
			if(Assert.empty(rules_ruleId))
				break;
			Rule rule1=rules_ruleId.get(sellerDepositChannel.getSellerRuleId());
			if(rule1!=null)
				rules.add(rule1);
			
			boolean isVProduct=false;
			GoodCategory goodCategory=GoodCategory.getGoodCategory(tran.getTransDetailsList().get(0).getGoods().getGoodsCategory().getName());
			if(goodCategory!=null){
				switch(goodCategory){
					case Goods_Category_Lottery:;
					case Goods_Category_Recharge_Phone:isVProduct=true;break;
				}
			}
			if(isVProduct){
				List<TransRule> sendVProduct=rules_ruleType.get(RuleType.SEND_Product.getValue());
				rules.addAll(sendVProduct);
			}
			break;
		case Trans_Points_Exch_Currency: 
			//每种规则类型应该都只有一个启用了的规则
//			List<TransRule> rulesOfOneRuleType=rules_ruleType.get(RuleType.WITHDRAW.getValue());
//			if(!Assert.empty(rulesOfOneRuleType))
//				rules.add(rulesOfOneRuleType.get(0));
//			break;
		case Trans_Points: 
			//根据卖家对应的交易规则
			if(Assert.empty(rules_ruleId))
				break;
			Rule rule=rules_ruleId.get(sellerDepositChannel.getSellerRuleId());
			if(rule!=null)
				rules.add(rule);
			break;
		};
		return rules;
	}
	
	//仅供临时使用
	public Map getRulesFromCache(){
		Map<String, Map> cache=new HashMap();
		Map<String,List<TransRule>> rules_transType=new HashMap();
		Map<String,List<TransRule>> rules_ruleType=new HashMap();
		Map<String,TransRule> rules_ruleId=new HashMap();
		
		TransRule ruleQueryCon=new TransRule();
		ruleQueryCon.setState(State.AVAILIABLE.getValue());
		List<TransRule> allTransRules=transRuleService.getTransRules(ruleQueryCon);
		if(Assert.empty(allTransRules))
			return cache;
		for(TransRule transRule:allTransRules){
			List<TransRule> rule_transType=rules_transType.get(transRule.getTransType());
			if(rule_transType==null){
				rule_transType=new ArrayList();
				rule_transType.add(transRule);
				rules_transType.put(transRule.getTransType(), rule_transType);
			}else{
				rule_transType.add(transRule);
			}
			
			List<TransRule> rule_ruleType=rules_ruleType.get(transRule.getRuleType());
			if(rule_ruleType==null){
				rule_ruleType=new ArrayList();
				rule_ruleType.add(transRule);
				rules_ruleType.put(transRule.getRuleType(), rule_ruleType);
			}else{
				rule_ruleType.add(transRule);
			}
			rules_ruleId.put(String.valueOf(transRule.getId()), transRule);
		}
		cache.put("rules-transType", rules_transType);
		cache.put("rules-ruleId", rules_ruleId);
		cache.put("rules-ruleType", rules_ruleType);
		return cache;
	}
	
	/**
	  * 方法描述：通过卖家的所有规则计算交易的流转信息
	  * for(卖家规则明细：卖家规则明细list){
			TransPay transferInfoOfTran=countTransferInfoOfTranBySellerRuleDetail(卖家规则明细);
			transPoints.getTransPayList().add(transferInfoOfTran);
		}
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-12-19 下午01:43:57
	 */
	private void countTransferInfoOfTranByRules(Trans tran,Seller seller,
			SellerChannel sellerDepositChannel, Buyers buyer,
			BuyerChannel buyerPayChannel,User user,UserCertification userCertification,UseTime useTime)  throws Exception{
		List<Rule> ruleList= getRules(tran,sellerDepositChannel,buyerPayChannel);
		log.info("交易号为："+tran.getId()+",作用的交易规则为："+ruleList);
		if(Assert.empty(ruleList)){
			log.info("该交易没有规则");
			return ;
		}
		for(Rule rule:ruleList){
			countTransferInfoOfTranByRule(tran,rule,seller,sellerDepositChannel,  buyer,
					  buyerPayChannel, user, userCertification, useTime);
		}
	}
	
	
	
	/**
	  * 方法描述：通过规则来获取规则明细
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-12-19 下午04:00:15
	 */
	public  List<RuleDetail> getRuleDetails(Trans tran,Rule rule){
		TransRule transRule=(TransRule)rule;
		return transRule.getRuleDetailList();
	}
	
	/**
	  * 方法描述：通过一个规则计算交易的流转信息
	  * for(卖家规则明细：卖家规则明细list){
			TransPay transferInfoOfTran=countTransferInfoOfTranBySellerRuleDetail(卖家规则明细);
			transPoints.getTransPayList().add(transferInfoOfTran);
		}
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-12-19 下午01:43:57
	 */
	private void countTransferInfoOfTranByRule(Trans tran,Rule rule,Seller seller,
			SellerChannel sellerDepositChannel, Buyers buyer,
			BuyerChannel buyerPayChannel,User user,UserCertification userCertification,UseTime useTime) throws Exception{
		
		List<RuleDetail> ruleDetails= getRuleDetails(tran,rule);
		if(Assert.empty(ruleDetails)){
			log.info("该规则没有规则明细");
			return ;
		}
		for(RuleDetail ruleDetail:ruleDetails){
			if(useTime!=UseTime.getUseTime(ruleDetail.getRuleDetailTemplet().getUseTime()))
				continue;
			Pay transferInfoOfTran=countTransferInfoOfTranByRuleDetail(tran,seller,ruleDetail,sellerDepositChannel,buyer, buyerPayChannel, user, userCertification);
			if(transferInfoOfTran!=null)
				tran.getPayList().add(transferInfoOfTran);
		}
	}
	
	
	
	
	/**
	  * 方法描述：计算该积分交易作用于该卖家规则明细产生的一个流转信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-12-19 下午01:58:30
	 */
	private Pay countTransferInfoOfTranByRuleDetail(Trans tran,Seller seller,RuleDetail ruleDetail,SellerChannel sellerDepositChannel,Buyers buyer,BuyerChannel buyerPayChannel,User user,UserCertification userCertification) throws Exception{
		TransferInfoGenerator transferInfoGenerator=transferInfoGeneratorFactory.getTransferInfoGenerator(ruleDetail);
		if(transferInfoGenerator==null)
			throw new transferInfoGeneratorIsNull("转移信息生成器为空!");
		Pay transferInfoForOneSellerRuleDetail=transferInfoGenerator.generateTransferInfo(tran, seller, ruleDetail, sellerDepositChannel, buyer,  buyerPayChannel, user, userCertification);
		return transferInfoForOneSellerRuleDetail;
	}
	
	/**
	  * 方法描述：获取流转的数量
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-12-27 下午03:06:53
	 */
//	private String getQuantityTransfer(Pay transPay){
//		if(PayType.SEND_POINTS==PayType.getPayType(transPay.getPayType())){
//			return transPay.getPoints();
//		}else{
//			return transPay.getCurrencyValue();
//		}
//			
//	}
	
	/**
	  * 方法描述：通过该交易的流转信息，来更新该交易各个角色的资金 ，【仅根据资金流转信息，来计算该交易的各个角色的资金】
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2012-12-19 下午02:57:55
	 */
	private void updateCurrencyOfAllRoleForTheTran(Trans tran,List<Pay> transferInfoOfTranList){
		for(Pay transferInfo:transferInfoOfTranList){
			String fromRole=transferInfo.getFromRole();
			String payType=transferInfo.getTypeDetail();
			String currencyVal=transferInfo.getPayValue();
			String setRoleCurrencyMethodStr=RoleCurrency.rolePayType_roleCurrency.get(payType);
			Class[] para={String.class};
			if(!Assert.empty(setRoleCurrencyMethodStr)){
				try{
					Method setRoleCurrencyMethod=tran.getClass().getMethod(setRoleCurrencyMethodStr,para);
					setRoleCurrencyMethod.invoke(tran, currencyVal);
				}catch(Exception e){
					log.error("在给角色："+fromRole+",支付类型为："+payType+"赋值时异常", e);
					//throw CountCurrencyOfAllRoleException();
				}
			}
//			String toRole=transferInfo.getToRole();
//			setRoleCurrencyMethodStr=RoleCurrency.rolePayType_roleCurrency.get(toRole+"-"+payType);
//			if(!Assert.empty(setRoleCurrencyMethodStr)){
//				try{
//					Method setRoleCurrencyMethod=tran.getClass().getMethod(setRoleCurrencyMethodStr,para);
//					setRoleCurrencyMethod.invoke(tran, currencyVal);
//				}catch(Exception e){
//					log.error("在给角色："+toRole+",支付类型为："+payType+"赋值时异常", e);
//					//throw CountCurrencyOfAllRoleException();
//				}
//			}
		}
	}
	
	public  void updateTranOtherInfo(){
		
	}

	public TransferInfoGeneratorFactory getTransferInfoGeneratorFactory() {
		return transferInfoGeneratorFactory;
	}

	public void setTransferInfoGeneratorFactory(
			TransferInfoGeneratorFactory transferInfoGeneratorFactory) {
		this.transferInfoGeneratorFactory = transferInfoGeneratorFactory;
	}

	public TransRuleService getTransRuleService() {
		return transRuleService;
	}

	public void setTransRuleService(TransRuleService transRuleService) {
		this.transRuleService = transRuleService;
	}

}
