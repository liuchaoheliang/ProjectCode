package com.froad.action.support.trans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.ClientGoodsGroupRackActionSupport;
import com.froad.action.support.GoodsExchangeRackActionSupport;
import com.froad.action.support.SellersActionSupport;
import com.froad.bean.ExchangeTempBean;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.buyers.Buyers;
import com.froad.client.clientGoodsGroupRack.ClientGoodsGroupRack;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.trans.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.common.TranCommand;
import com.froad.util.command.Command;




	/**
	 * 类描述： 手机版团购工具类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 6, 2014 11:12:22 AM 
	 */
public class GroupActionSupport {
	
	private static final Logger logger=Logger.getLogger(GroupActionSupport.class);
	
	private static final BigDecimal hundred=new BigDecimal("100");
	
	private SellersActionSupport sellersActionSupport;
	
	private BuyersActionSupport buyersActionSupport;
	
	private PayActionSupport payActionSupport;
	
	private GoodsExchangeRackActionSupport goodsExchangeRackActionSupport;
	
	private ClientGoodsGroupRackActionSupport clientGoodsGroupRackActionSupport;

	public Trans makeExchangeTransForPC(ExchangeTempBean tempExch){
		if(tempExch==null||tempExch.getGoodsRackId()==null||
				"".equals(tempExch.getGoodsRackId().trim())){
			logger.error("传入的参数为空，或传入的上架商品编号为空");
			return null;
		}
		GoodsExchangeRack exchange=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(tempExch.getGoodsRackId());
		if(exchange==null){
			logger.error("该上架商品不存在，传入的上架商品编号为： "+tempExch.getGoodsRackId());
			return null;
		}
		tempExch.setCashPricing(exchange.getCashPricing());//现金定价
		tempExch.setFftPointPricing(exchange.getFftPointPricing());//分分通积分定价
		tempExch.setBankPointPricing(exchange.getBankPointPricing());//银行积分定价
		tempExch.setFftpointCashPricing(exchange.getFftpointCashPricing());//分分通积分+现金定价
		tempExch.setBankpointCashPricing(exchange.getBankpointCashPricing());//银行通积分+现金定价
		tempExch.setFftpointcashRatioPricing(exchange.getFftpointcashRatioPricing());//分分通积分+现金定价 范围
		tempExch.setBankpointcashRatioPricing(exchange.getBankpointcashRatioPricing());//银行通积分+现金定价 范围
		return this.makeExchangeTrans(tempExch);
	}
	
	public Trans makeExchangeTransForPhone(ExchangeTempBean tempExch){
		if(tempExch==null||tempExch.getGoodsRackId()==null||
				"".equals(tempExch.getGoodsRackId().trim())){
			logger.error("传入的参数为空，或传入的上架商品编号为空");
			return null;
		}
		ClientGoodsGroupRack group=clientGoodsGroupRackActionSupport.getGoodsGroupRackById(tempExch.getGoodsRackId());
		if(group==null){
			logger.error("该上架商品不存在，传入的上架商品编号为： "+tempExch.getGoodsRackId());
			return null;
		}
		tempExch.setMerchantId(group.getMerchantId());
		tempExch.setCashPricing(group.getCashPricing());//现金定价
		tempExch.setFftPointPricing(group.getFftPointPricing());//分分通积分定价
		tempExch.setBankPointPricing(group.getBankPointPricing());//银行积分定价
		tempExch.setFftpointCashPricing(group.getFftpointCashPricing());//分分通积分+现金定价
		tempExch.setBankpointCashPricing(group.getBankpointCashPricing());//银行通积分+现金定价
		tempExch.setFftpointcashRatioPricing(group.getFftpointcashRatioPricing());//分分通积分+现金定价 范围
		tempExch.setBankpointcashRatioPricing(group.getBankpointcashRatioPricing());//银行通积分+现金定价 范围
		return this.makeExchangeTrans(tempExch);
	}
	
	private Trans makeExchangeTrans(ExchangeTempBean tempExch){
		Trans trans=new Trans();
		trans.setUserId(tempExch.getUserId());
		trans.setTransType(TranCommand.GROUP);//必填
		trans.setCurrency(Command.CURRENCY);
		trans.setState(TranCommand.TRAN_PROCESSING);//必填
		String sellerId=tempExch.getSellerId();
		Seller seller=sellersActionSupport.getSellerById(sellerId);
		if(seller==null){
			logger.error("卖家不存在，卖家编号为： "+sellerId);
			return null;
		}
		String merchantId=tempExch.getMerchantId();
		if(merchantId==null||"".equals(merchantId)){
			logger.error("卖家对应的商户编号为空，卖家编号为： "+sellerId);
			return null;
		}
		List<SellerChannel> sellerChannelList=seller.getSellerChannelList();
		if(sellerChannelList==null||sellerChannelList.size()==0){
			logger.error("卖家资金渠道为空，卖家编号为： "+sellerId);
			return null;
		}
		Buyers buyer=buyersActionSupport.getBuyerByUserId(tempExch.getUserId());
		String payMethod=tempExch.getPayMethod();
		if(buyer==null&&!TranCommand.POINTS_FFT.equals(payMethod)&&
				!TranCommand.POINTS_BANK.equals(payMethod)){
			logger.error("该用户不是买家，不能进行资金支付，用户编号为： "+tempExch.getUserId());
			return null;
		}
		List<BuyerChannel> buyerChannelList=buyer.getBuyerChannelList();
		if(buyerChannelList==null||buyerChannelList.size()==0){
			logger.error("该买家没有买家资金渠道，买家编号为： "+buyer.getId());
			return null;
		}
		BuyerChannel buyerChannel=buyerChannelList.get(0);
		SellerChannel sellerChannel=sellerChannelList.get(0);
		trans.setPhone(tempExch.getMobile())  ;
		trans.setMerchantId(Integer.parseInt(merchantId));
		trans.setSellerId(sellerId);//必填
		trans.setSellerChannelId(sellerChannel.getId().toString());//必填
		trans.setClientType(tempExch.getClientType());//必填
		trans.setPayChannel(tempExch.getPayChannel());//必填
		trans.setPayMethod(payMethod);//必填
		//积分兑换交易的必填字段
		trans.setBuyersId(buyer.getId()+"");
		trans.setBuyerChannelId(buyerChannel.getId()+"");
		//为交易设置金额和积分
		this.calAmountAndPointsForTrans(trans,tempExch);
		
		//组装TransDetails并设置到trans里
		this.makeExchangeDetailsForTrans(trans, tempExch);
		//组装pay并设置到trans里
		List<Pay> payList=payActionSupport.makePay(trans);
		if(payList==null){
			logger.error("交易的pay记录组装失败");
			return null;
		}
		trans.getPayList().addAll(payList);
		return trans;
	}
	
	
	private void calAmountAndPointsForTrans(Trans trans,ExchangeTempBean tempExch){
		String payMethod=trans.getPayMethod();
		String singleCashPrice=tempExch.getCashPricing();
		if(singleCashPrice==null||"".equals(singleCashPrice.trim())){
			logger.error("现金定价方式为空");
			return;
		}
		Integer goodsNumber=tempExch.getBuyNumber();
		if(goodsNumber==null){
			logger.error("购买数量为空");
			return;
		}
		BigDecimal decimalGoodsNumber=new BigDecimal(goodsNumber);
		BigDecimal decimalCashPrice=new BigDecimal(singleCashPrice).multiply(decimalGoodsNumber);
		String strCashPrice=decimalCashPrice.toString();
		trans.setCostpriceTotal(strCashPrice);
		trans.setCurrencyValueAll(strCashPrice);
		trans.setFftPointsValueAll("0");
		trans.setFftPointsValueRealAll("0");
		trans.setBankPointsValueAll("0");
		trans.setBankPointsValueRealAll("0");
		if(TranCommand.POINTS_FFT.equals(payMethod)){//方付通积分支付
			String fftPoints=tempExch.getFftPointPricing();
			if(fftPoints==null||"".equals(fftPoints.trim())){
				logger.error("分分通积分定价为空，上架商品编号为： "+tempExch.getGoodsRackId());
				return;
			}
			String realPoints=new BigDecimal(fftPoints).multiply(decimalGoodsNumber).toString();
			trans.setFftPointsValueRealAll(realPoints);
			trans.setFftPointsValueAll(realPoints);
			trans.setCurrencyValueRealAll("0");
		}else if(TranCommand.POINTS_BANK.equals(payMethod)){//银行积分支付
			String bankPoints=tempExch.getBankPointPricing();
			if(bankPoints==null||"".equals(bankPoints.trim())){
				logger.error("银行积分定价为空，上架商品编号为： "+tempExch.getGoodsRackId());
				return;
			}
			String realPoints=new BigDecimal(bankPoints).multiply(decimalGoodsNumber).toString();
			trans.setBankPointsValueRealAll(realPoints);
			trans.setBankPointsValueAll(realPoints);
			trans.setCurrencyValueRealAll("0");
		}else if(TranCommand.CASH.equals(payMethod)){//现金支付
			trans.setCurrencyValueRealAll(strCashPrice);
		}else if(TranCommand.POINTS_FFT_CASH.equals(payMethod)){//方付通积分+现金
			String fft_cash=tempExch.getFftpointCashPricing();
			if(fft_cash==null){
				logger.error("(分分通积分+现金)的定价方式为空");
				return;
			}
			String[] fft_cash_arr=fft_cash.split("\\|");
			if(fft_cash_arr.length!=2){
				logger.error("(分分通积分+现金)的定价方式有误，定价内容： "+fft_cash);
				return;
			}
			String realPrice=new BigDecimal(fft_cash_arr[1]).multiply(decimalGoodsNumber).toString();
			String realPoints=new BigDecimal(fft_cash_arr[0]).multiply(decimalGoodsNumber).toString();
			trans.setCurrencyValueRealAll(realPrice);
			trans.setFftPointsValueRealAll(realPoints);
			trans.setFftPointsValueAll(realPoints);
		}else if(TranCommand.POINTS_BANK_CASH.equals(payMethod)){//银行积分+现金
			String bankpoint_cash=tempExch.getBankpointCashPricing();
			if(bankpoint_cash==null){
				logger.error("(银行积分+现金)的定价方式为空");
				return;
			}
			String[] bankpoint_cash_arr=bankpoint_cash.split("\\|");
			if(bankpoint_cash_arr.length!=2){
				logger.error("(银行积分+现金)的定价方式有误，定价内容： "+bankpoint_cash);
				return;
			}
			String realPrice=new BigDecimal(bankpoint_cash_arr[1]).multiply(decimalGoodsNumber).toString();
			String realPoints=new BigDecimal(bankpoint_cash_arr[0]).multiply(decimalGoodsNumber).toString();
			trans.setCurrencyValueRealAll(realPrice);
			trans.setCurrencyValueRealAll(realPrice);
			trans.setBankPointsValueRealAll(realPoints);
			trans.setBankPointsValueAll(realPoints);
		}else if(TranCommand.POINTS_FFT_CASH_SCOPE.equals(payMethod)){//方付通积分+现金(范围)
			String fft_cash_ratio=tempExch.getFftpointcashRatioPricing();
			if(fft_cash_ratio==null){
				logger.error("[方付通积分+现金(范围)]的定价方式为空");
				return;
			}
			String[] fft_cash_ratio_arr=fft_cash_ratio.split("\\|");
			if(fft_cash_ratio_arr.length!=2){
				logger.error("[方付通积分+现金(范围)]的定价方式有误，定价内容： "+fft_cash_ratio);
				return;
			}
			BigDecimal pointsPricing=this.calPoints(singleCashPrice, TranCommand.PRICING_FFT_CASH);
			BigDecimal minPoints=pointsPricing.multiply(new BigDecimal(fft_cash_ratio_arr[0])).multiply(decimalGoodsNumber).divide(hundred,2,RoundingMode.DOWN);
			BigDecimal maxPoints=pointsPricing.multiply(new BigDecimal(fft_cash_ratio_arr[1])).multiply(decimalGoodsNumber).divide(hundred,2,RoundingMode.DOWN);
			String pageParam=tempExch.getUsePointRaioValue();//页面上输入的积分(银行或分分通积分)
			if(pageParam==null||"".equals(pageParam)){
				logger.error("使用的支付方式为:(分分通积分+现金)，但用户输入的积分为空");
				return;
			}
			if(pageParam.indexOf(".")!=-1){
				logger.error("使用的支付方式为:(分分通积分+现金)，消费的积分值不能为小数");
				return;
			}
			BigDecimal pagePoints=new BigDecimal(pageParam);
			if(pagePoints.compareTo(minPoints)==-1){
				logger.error("使用的支付方式为:(分分通积分+现金)，但积分消费数小于最低消费值");
				return;
			}
			if(pagePoints.compareTo(maxPoints)==1){
				logger.error("使用的支付方式为:(分分通积分+现金)，但积分消费数大于最高消费值");
				return;
			}
			BigDecimal pagePrice=this.calAmount(pageParam, TranCommand.PRICING_FFT_CASH);
			BigDecimal realPayPrice=decimalCashPrice.subtract(pagePrice);
			trans.setCurrencyValueRealAll(realPayPrice.toString());
			trans.setFftPointsValueRealAll(pageParam);//由页面输入,但需要校验是否合法
			trans.setFftPointsValueAll(pageParam);
		}else if(TranCommand.POINTS_BANK_CASH_SCOPE.equals(payMethod)){//银行积分+现金(范围)
			String bank_cash_ratio=tempExch.getBankpointcashRatioPricing();
			if(bank_cash_ratio==null){
				logger.error("[银行积分+现金(范围)]的定价方式为空");
				return;
			}
			String[] bank_cash_ratio_arr=bank_cash_ratio.split("\\|");
			if(bank_cash_ratio_arr.length!=2){
				logger.error("[银行积分+现金(范围)]的定价方式有误，定价内容： "+bank_cash_ratio);
				return;
			}
			BigDecimal pointsPricing=this.calPoints(singleCashPrice, TranCommand.PRICING_BANK_CASH);
			BigDecimal minPoints=pointsPricing.multiply(new BigDecimal(bank_cash_ratio_arr[0])).multiply(decimalGoodsNumber).divide(hundred,2,RoundingMode.DOWN);
			BigDecimal maxPoints=pointsPricing.multiply(new BigDecimal(bank_cash_ratio_arr[1])).multiply(decimalGoodsNumber).divide(hundred,2,RoundingMode.DOWN);
			String pageParam=tempExch.getUsePointRaioValue();//页面上输入的积分(银行或分分通积分)
			if(pageParam==null||"".equals(pageParam)){
				logger.error("使用的支付方式为:(银行积分+现金)，但用户输入的积分为空");
				return;
			}
			if(pageParam.indexOf(".")!=-1){
				logger.error("使用的支付方式为:(银行积分+现金)，消费的积分值不能为小数");
				return;
			}
			BigDecimal pagePoints=new BigDecimal(pageParam);
			if(pagePoints.compareTo(minPoints)==-1){
				logger.error("使用的支付方式为:(银行积分+现金)，但积分消费数小于最低消费值");
				return;
			}
			if(pagePoints.compareTo(maxPoints)==1){
				logger.error("使用的支付方式为:(银行积分+现金)，但积分消费数大于最高消费值");
				return;
			}
			BigDecimal pagePrice=this.calAmount(pageParam, TranCommand.PRICING_BANK_CASH);
			BigDecimal realPayPrice=decimalCashPrice.subtract(pagePrice);
			trans.setCurrencyValueRealAll(realPayPrice.toString());
			trans.setBankPointsValueRealAll(pageParam);//由页面输入,但需要校验是否合法
			trans.setBankPointsValueAll(pageParam);
		}else{
			logger.error("未知的支付方式："+payMethod);
			return;
		}
	}
	
	
	 /**
	  * 方法描述：将积分转换成现金
	  * @param: cashPricing
	  * @return: rule
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 7, 2013 6:21:32 PM
	  */
	private BigDecimal calAmount(String points,String rule){
		BigDecimal tmpPoints=new BigDecimal(points);//积分
		String[] rates=rule.split(":");
		BigDecimal pointsRatio=new BigDecimal(rates[0]);//积分比例值
		BigDecimal cashRatio=new BigDecimal(rates[1]);//现金比例值
		return tmpPoints.multiply(cashRatio).divide(pointsRatio,2,RoundingMode.DOWN);
	}
	
	
	/**
	  * 方法描述：将现金转换成积分
	  * @param: cashPricing
	  * @return: rule
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 7, 2013 6:21:32 PM
	  */
	private BigDecimal calPoints(String price,String rule){
		BigDecimal tmpPrice=new BigDecimal(price);//金额
		String[] rates=rule.split(":");
		BigDecimal pointsRatio=new BigDecimal(rates[0]);//积分比例值
		BigDecimal cashRatio=new BigDecimal(rates[1]);//现金比例值
		return tmpPrice.multiply(pointsRatio).divide(cashRatio,2,RoundingMode.DOWN);
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
	private void makeExchangeDetailsForTrans(Trans trans,ExchangeTempBean tempExch){
		String goodsRackId=tempExch.getGoodsRackId();
		String goodsNumber=tempExch.getBuyNumber()+"";
		
		TransDetails details=new TransDetails();
		details.setCurrency(Command.CURRENCY);
		details.setState(Command.STATE_START);
		details.setGoodsRackId(goodsRackId);
		details.setGoodsNumber(goodsNumber);
		details.setCurrency(Command.CURRENCY);
		//TODO 交易详情里的价格
		details.setBankPointsValueAll(trans.getBankPointsValueAll());
		details.setBankPointsValueRealAll(trans.getBankPointsValueRealAll());
		details.setFftPointsValueAll(trans.getFftPointsValueAll());
		details.setFftPointsValueRealAll(trans.getFftPointsValueRealAll());
		details.setCostpriceTotal(trans.getCostpriceTotal());
		details.setCurrencyValue(trans.getCurrencyValueAll());
		details.setCurrencyValueReal(trans.getCurrencyValueRealAll());
		
		trans.getTransDetailsList().add(details);
	}


	public void setSellersActionSupport(SellersActionSupport sellersActionSupport) {
		this.sellersActionSupport = sellersActionSupport;
	}


	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
	}


	public void setPayActionSupport(PayActionSupport payActionSupport) {
		this.payActionSupport = payActionSupport;
	}


	public void setGoodsExchangeRackActionSupport(
			GoodsExchangeRackActionSupport goodsExchangeRackActionSupport) {
		this.goodsExchangeRackActionSupport = goodsExchangeRackActionSupport;
	}

	public void setClientGoodsGroupRackActionSupport(
			ClientGoodsGroupRackActionSupport clientGoodsGroupRackActionSupport) {
		this.clientGoodsGroupRackActionSupport = clientGoodsGroupRackActionSupport;
	}

	 
	
}
