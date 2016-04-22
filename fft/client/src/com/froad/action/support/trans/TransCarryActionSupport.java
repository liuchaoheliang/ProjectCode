package com.froad.action.support.trans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.froad.action.support.SellersActionSupport;
import com.froad.client.goodsCarryRack.GoodsCarryRack;
import com.froad.client.goodsCarryRack.GoodsCarryRackService;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.sellers.SellerRule;
import com.froad.client.trans.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.common.TranCommand;
import com.froad.util.command.Command;


	/**
	 * 类描述：积分提现的工具类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 8, 2013 2:41:31 PM 
	 */
public class TransCarryActionSupport {
	
	private static final Logger logger=Logger.getLogger(TransCarryActionSupport.class);
	
	private static final BigDecimal hundred=new BigDecimal("100");
	
	private PayActionSupport payActionSupport;
	
	private SellersActionSupport sellersActionSupport;
	
	private GoodsCarryRackService goodsCarryRackService;

	/**
	  * 方法描述：组装积分提现交易
	  * @param: Trans(clientType,userId,sellerId,channelId)
	  * @param: TransDetails(goodsRackId,goodsNumber)
	  * @param: 提现积分数与goodsNumber的值一致
	  * @return: Trans
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 5, 2013 3:39:18 PM
	  */
	public Trans makeCarryTrans(Trans trans,TransDetails details){
		String clientType=trans.getClientType();
		String userId=trans.getUserId();
		String points=details.getGoodsNumber();
		String sellerId=trans.getSellerId();
		logger.info("传入的卖家编号为： "+sellerId);
		String channelId=trans.getChannelId();
		
		if(points==null||"".equals(points.trim())){
			logger.error("提现积分数为空");
			return null;
		}
		Integer point=Integer.parseInt(points);
		if(point<10||point%10!=0){
			logger.error("提现积分数必须大于9并且是10的整倍数");
			return null;
		}
		Seller seller=sellersActionSupport.getSellerById(sellerId);
		if(seller==null){
			logger.error("该笔交易对应的卖家不存在，传入的卖家编号为： "+sellerId);
			return null;
		}
		String merchantId=seller.getMerchantId();
		if(merchantId==null||"".equals(merchantId)){
			logger.error("卖家对应的商户不存在，卖家编号为： "+seller.getId());
			return null;
		}
		List<SellerChannel> sellerChannelList=seller.getSellerChannelList();
		if(sellerChannelList==null||sellerChannelList.size()==0){
			logger.error("卖家资金渠道不存在，卖家编号："+sellerId);
			return null;
		}
		SellerChannel sellerChannel=sellerChannelList.get(0);
		//关联出卖家提现规则来计算提现手续费 提现金额由积分和现金的比例来定
		SellerRule sellerRule=sellerChannel.getSellerRule();
		if(sellerRule==null){
			logger.error("卖家的提现规则不存在，卖家编号："+sellerId);
			return null;
		}
		String rule=sellerRule.getFftFeeRule();
		logger.info("规则内容："+rule+"所属卖家编号："+sellerId+"规则编号为："+sellerRule.getId());
		if(!this.checkRule(rule)){
			logger.error("用于计算提现手续费的规则校验不通过，规则内容："+rule+
					"所属卖家编号："+sellerId+"规则编号为："+sellerRule.getId());
			return null;
		}
		BigDecimal totalAmount=this.calCarryTotalAmount(points);//提现总金额
		BigDecimal fee=this.calCarryFee(totalAmount, rule);//提现手续费
		Trans transBean=new Trans();
		transBean.setMerchantId(Integer.parseInt(merchantId));
		transBean.setTransType(TranCommand.POINTS_EXCH_CASH);//必填
		transBean.setSellerId(sellerId);//必填
		transBean.setSellerChannelId(sellerChannel.getId().toString());//必填
		transBean.setCurrency(Command.CURRENCY);
		transBean.setCostpriceTotal(totalAmount.toString());//必填
		transBean.setCurrencyValueAll(totalAmount.toString());//必填
		transBean.setCurrencyValueRealAll(totalAmount.subtract(fee).toString());//必填
		transBean.setState(TranCommand.TRAN_PROCESSING);//必填
		transBean.setClientType(clientType);//必填
		transBean.setPayChannel(TranCommand.PAY_CHANNEL_CARD);//必填
		transBean.setPayMethod(TranCommand.CASH);//必填
		//积分提现的必填字段
		transBean.setFftPointsValueAll(points);//选填
		transBean.setFftPointsValueRealAll(points);
		transBean.setFftFactorage(fee.toString());
		transBean.setChannelId(channelId);
		transBean.setUserId(userId);
		//组装TransDetails并设置到trans里
		details.setSellerRuleId(sellerRule.getId().toString());
		this.makeCarryDetailsForTrans(transBean, details);
		//组装pay并设置到trans里
//		List<Pay> payList=payActionSupport.makePay(transBean);
//		if(payList==null){
//			logger.error("交易的pay记录组装失败");
//			return null;
//		}
//		transBean.getPayList().addAll(payList);
		return transBean;
	}

	
	/**
	  * 方法描述：为交易设置交易详情
	  * @param: Trans 需要设置交易详情的Trans
	  * @param: TransDetails(goodsRackId,goodsNumber,sellerRuleId)
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 5, 2013 5:12:00 PM
	  */
	private void makeCarryDetailsForTrans(Trans trans,TransDetails pageDetails){
		String goodsRackId=pageDetails.getGoodsRackId();
		String goodsNumber=pageDetails.getGoodsNumber();
		String sellerRuleId=pageDetails.getSellerRuleId();
		
		TransDetails details=new TransDetails();
		details.setGoodsRackId(goodsRackId);
		details.setGoodsNumber(goodsNumber);
		details.setSellerRuleId(sellerRuleId);
		details.setCurrency(Command.CURRENCY);
		//TODO 交易详情里的价格
		details.setCostpriceTotal(trans.getCostpriceTotal());
		details.setCurrencyValue(trans.getCurrencyValueAll());
		details.setCurrencyValueReal(trans.getCurrencyValueRealAll());
//		this.makeAmountForDetails(details,trans);//为交易详情的单价设值
		details.setFftPointsValueAll(trans.getFftPointsValueAll());
		details.setFftPointsValueRealAll(trans.getFftPointsValueRealAll());
		details.setState(Command.STATE_START);
		
		trans.getTransDetailsList().add(details);
	}
	
	
	/**
	  * 方法描述：为交易详情计算并设置原价,单价,实际单价
	  * @param: TransDetails(goodsNumber) 需要设值的TransDetails
	  * @param: Trans(currencyValueAll,currencyValueRealAll)
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 5, 2013 4:48:31 PM
	  */
//	private void makeAmountForDetails(TransDetails details,Trans trans){
//		BigDecimal totalAmount=new BigDecimal(trans.getCurrencyValueAll());
//		BigDecimal realAmount=new BigDecimal(trans.getCurrencyValueRealAll());
//		BigDecimal goodsNumber=new BigDecimal(details.getGoodsNumber());
//		BigDecimal single=totalAmount.divide(goodsNumber,2,RoundingMode.DOWN);
//		BigDecimal real=realAmount.divide(goodsNumber,2,RoundingMode.DOWN);
//		
//		details.setCostpriceTotal(single.toString());
//		details.setCurrencyValue(single.toString());
//		details.setCurrencyValueReal(real.toString());
//	}
	
	
	/**
	  * 方法描述：校验提现手续费计算规则的格式
	  * @param: rule 提现手续费的规则
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 5, 2013 3:12:37 PM
	  */
	private boolean checkRule(String rule){
		logger.info("待验证的提现规则： "+rule);
		if(rule==null||"".equals(rule)){
			logger.error("提现规则为空");
			return false;
		}
		String regex="\\d{1,3}\\[\\d{1,3},\\d{1,3}\\]";
		Pattern pattern=Pattern.compile(regex);
		Matcher matcher=pattern.matcher(rule);
		return matcher.matches();
	}
	
	
	/**
	  * 方法描述：按积分现金比例计算提现总金额
	  * @param: points 提现积分数
	  * @return: 提现总金额
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 5, 2013 1:19:26 PM
	  */
	private BigDecimal calCarryTotalAmount(String points){
		BigDecimal pointsValue=new BigDecimal(points);//提现积分数
		String[] rates=TranCommand.POINTS_CASH_RATE.split(":");
		BigDecimal pointsRatio=new BigDecimal(rates[0]);//积分比例值
		BigDecimal cashRatio=new BigDecimal(rates[1]);//现金比例值
		return pointsValue.multiply(cashRatio).divide(pointsRatio,2,RoundingMode.DOWN);
	}
	
	
	/**
	  * 方法描述：计算提现需要扣除的手续费
	  * @param: amount 提现总金额
	  * @param: rule 手续费计算规则 格式如5[1,15]
	  * @return: 手续费
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 5, 2013 3:03:07 PM
	  */
	private BigDecimal calCarryFee(BigDecimal amount,String rule){
		int begin=rule.indexOf("[");
		int middle=rule.indexOf(",");
		int end=rule.indexOf("]");
		BigDecimal percent=new BigDecimal(rule.substring(0,begin));
		BigDecimal min=new BigDecimal(rule.substring(begin+1,middle));
		BigDecimal max=new BigDecimal(rule.substring(middle+1,end));
		BigDecimal fee=amount.multiply(percent).divide(hundred,2,RoundingMode.DOWN);
		if(fee.compareTo(min)==1&&fee.compareTo(max)==-1){//在区间内,不包括边界值
			return fee;
		}else if(fee.compareTo(min)==0||fee.compareTo(min)==-1){//等于或小于下边界值
			return min;
		}else{//等于或大于上边界值
			return max;
		}
	}
	
	public List<GoodsCarryRack> getBySelective(GoodsCarryRack carry){
		return goodsCarryRackService.getGoodsCarryRack(carry);
	}
	
	
	
	public void setPayActionSupport(PayActionSupport payActionSupport) {
		this.payActionSupport = payActionSupport;
	}


	public void setSellersActionSupport(SellersActionSupport sellersActionSupport) {
		this.sellersActionSupport = sellersActionSupport;
	}


	public void setGoodsCarryRackService(GoodsCarryRackService goodsCarryRackService) {
		this.goodsCarryRackService = goodsCarryRackService;
	}

}
