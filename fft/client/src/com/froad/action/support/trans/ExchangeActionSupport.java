package com.froad.action.support.trans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.ClientGoodsExchangeRackActionSupport;
import com.froad.action.support.GoodsExchangeRackActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.SellersActionSupport;
import com.froad.bean.ExchangeTempBean;
import com.froad.client.PresentPointRule.PresentPointRule;
import com.froad.client.PresentPointRule.PresentPointRuleService;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.buyers.Buyers;
import com.froad.client.clientGoodsExchangeRack.ClientGoodsExchangeRack;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.hfcz.AppException_Exception;
import com.froad.client.hfcz.HFCZService;
import com.froad.client.hfcz.Hfcz;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.trans.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.trans.TransGoodsAttribute;
import com.froad.common.TranCommand;
import com.froad.util.Result;
import com.froad.util.command.Command;
import com.froad.util.command.GoodsCommand;
import com.froad.util.command.PayCommand;


	/**
	 * 类描述：积分兑换工具类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 6, 2013 10:13:22 PM 
	 */
public class ExchangeActionSupport {
	
	private static final Logger logger=Logger.getLogger(ExchangeActionSupport.class);
	
	private static final BigDecimal hundred=new BigDecimal("100");
	
	private static final SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private HFCZService hfczService;
	
	private PresentPointRuleService presentPointRuleService;
	
	private SellersActionSupport sellersActionSupport;
	
	private BuyersActionSupport buyersActionSupport;
	
	private PayActionSupport payActionSupport;
	
	private PointActionSupport pointActionSupport;
	
	private GoodsExchangeRackActionSupport goodsExchangeRackActionSupport;
	
	private ClientGoodsExchangeRackActionSupport clientGoodsExchangeRackActionSupport;

	public Trans makeExchangeTransForPC(ExchangeTempBean tempExch){
		if(tempExch==null||tempExch.getGoodsRackId()==null||
				"".equals(tempExch.getGoodsRackId().trim())){
			logger.error("传入的参数为空或传入的交易商品编号为空");
			Trans transRes=new Trans();
			transRes.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			transRes.setRespMsg("交易商品编号为空");
			return transRes;
		}
		GoodsExchangeRack exchange=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(tempExch.getGoodsRackId());
		if(exchange==null){
			logger.error("该上架商品不存在，商品编号： "+tempExch.getGoodsRackId());
			Trans transRes=new Trans();
			transRes.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			transRes.setRespMsg("交易商品不存在");
			return transRes;
		}
		if(GoodsCommand.IS_RACK_NO.equals(exchange.getIsRack())){
			logger.error("该商品已下架，商品编号： "+tempExch.getGoodsRackId());
			Trans transRes=new Trans();
			transRes.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			transRes.setRespMsg("该商品已下架");
			return transRes;
		}
		tempExch.setMerchantId(exchange.getMerchantId());
		tempExch.setCashPricing(exchange.getCashPricing());//现金定价
		tempExch.setFftPointPricing(exchange.getFftPointPricing());//分分通积分定价
		tempExch.setBankPointPricing(exchange.getBankPointPricing());//银行积分定价
		tempExch.setFftpointCashPricing(exchange.getFftpointCashPricing());//分分通积分+现金定价
		tempExch.setBankpointCashPricing(exchange.getBankpointCashPricing());//银行通积分+现金定价
		tempExch.setFftpointcashRatioPricing(exchange.getFftpointcashRatioPricing());//分分通积分+现金定价 范围
		tempExch.setBankpointcashRatioPricing(exchange.getBankpointcashRatioPricing());//银行通积分+现金定价 范围
		if(exchange.getIsPresentPoints()==1){
			PresentPointRule present = new PresentPointRule();
			present.setState(1);
			present.setGoodExchangeRackId(tempExch.getGoodsRackId());
			List<PresentPointRule> rules=presentPointRuleService.getByConditions(present);
			PresentPointRule rule=this.findRule(rules);
			if(rule!=null){
				tempExch.setPresentPointsValue(rule.getPointValue());
				tempExch.setPresentRuleId(rule.getId());
			}
		}
		return this.makeExchangeTrans(tempExch);
	}
	
	
	public Trans makeExchangeTransForPhone(ExchangeTempBean tempExch){
		if(tempExch==null||tempExch.getGoodsRackId()==null||
				"".equals(tempExch.getGoodsRackId().trim())){
			logger.error("传入的参数为空或传入的交易商品编号为空");
			Trans transRes=new Trans();
			transRes.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			transRes.setRespMsg("交易商品编号为空");
			return transRes;
		}
		ClientGoodsExchangeRack exchange=clientGoodsExchangeRackActionSupport.selectById(tempExch.getGoodsRackId());
		if(exchange==null){
			logger.error("该上架商品不存在，商品编号： "+tempExch.getGoodsRackId());
			Trans transRes=new Trans();
			transRes.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			transRes.setRespMsg("交易商品不存在");
			return transRes;
		}
		if(GoodsCommand.IS_RACK_NO.equals(exchange.getIsRack())){
			logger.error("该商品已下架，商品编号： "+tempExch.getGoodsRackId());
			Trans transRes=new Trans();
			transRes.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			transRes.setRespMsg("该商品已下架");
			return transRes;
		}
		tempExch.setMerchantId(exchange.getMerchantId());
		tempExch.setCashPricing(exchange.getCashPricing());//现金定价
		tempExch.setFftPointPricing(exchange.getFftPointPricing());//分分通积分定价
		tempExch.setBankPointPricing(exchange.getBankPointPricing());//银行积分定价
		tempExch.setFftpointCashPricing(exchange.getFftpointCashPricing());//分分通积分+现金定价
		tempExch.setBankpointCashPricing(exchange.getBankpointCashPricing());//银行通积分+现金定价
		tempExch.setFftpointcashRatioPricing(exchange.getFftpointcashRatioPricing());//分分通积分+现金定价 范围
		tempExch.setBankpointcashRatioPricing(exchange.getBankpointcashRatioPricing());//银行通积分+现金定价 范围
		return this.makeExchangeTrans(tempExch);
	}
	
	private Trans makeExchangeTrans(ExchangeTempBean tempExch){
		Trans trans=new Trans();
		if(tempExch.getUserId()==null||tempExch.getUser()==null){
			logger.error("会员信息为空，交易组装失败");
			trans.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			trans.setRespMsg("会员信息为空");
			return trans;
		}
		trans.setUserId(tempExch.getUserId());
		if(tempExch.getMobile()==null||"".equals(tempExch.getMobile().trim())){
			trans.setPhone(tempExch.getUser().getMobilephone());
		}else{
			trans.setPhone(tempExch.getMobile());
		}
		trans.setTransType(TranCommand.POINTS_EXCH_PRODUCT);//必填
		trans.setCurrency(Command.CURRENCY);
		trans.setState(TranCommand.TRAN_PROCESSING);//必填
		String sellerId=tempExch.getSellerId();
		Seller seller=sellersActionSupport.getSellerById(sellerId);
		if(seller==null){
			logger.error("卖家不存在，卖家编号为： "+sellerId);
			trans.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			trans.setRespMsg("请确认您是否为珠海农商银行手机银行卡用户！");
			return trans;
		}
		String merchantId=tempExch.getMerchantId();
		if(merchantId==null||"".equals(merchantId)){
			logger.error("卖家对应的商户编号为空，卖家编号为： "+sellerId);
			trans.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			trans.setRespMsg("请确认您是否为珠海农商银行手机银行卡用户！");
			return trans;
		}
		List<SellerChannel> sellerChannelList=seller.getSellerChannelList();
		if(sellerChannelList==null||sellerChannelList.size()==0){
			logger.error("卖家的资金渠道为空，卖家编号为： "+sellerId);
			trans.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			trans.setRespMsg("请确认您是否为珠海农商银行手机银行卡用户！");
			return trans;
		}
		Buyers buyer=buyersActionSupport.getBuyerByUserId(tempExch.getUserId());
		String payMethod=tempExch.getPayMethod();
		if(this.needBuyer(payMethod)){
			if(buyer==null){
				logger.error("该用户不是买家，不能进行资金支付，用户编号为： "+tempExch.getUserId());
				trans.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
				trans.setRespMsg("请确认您是否为珠海农商银行手机银行卡用户！");
				return trans;
			}else{
				List<BuyerChannel> buyerChannelList=buyer.getBuyerChannelList();
				if(buyerChannelList==null||buyerChannelList.size()==0){
					logger.error("该买家没有买家资金渠道，买家编号为： "+buyer.getId());
					trans.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
					trans.setRespMsg("请确认您是否为珠海农商银行手机银行卡用户！");
					return trans;
				}
				BuyerChannel buyerChannel=buyerChannelList.get(0);
				trans.setBuyerChannelId(buyerChannel.getId()+"");
				trans.setBuyersId(buyer.getId()+"");
			}
		}
		SellerChannel sellerChannel=sellerChannelList.get(0);
		logger.info("=====sellerChannel===>"+sellerChannel);
		logger.info("========merchantId===>"+merchantId);
		trans.setMerchantId(Integer.parseInt(merchantId));
		trans.setSellerId(sellerId);//必填
		trans.setSellerChannelId(sellerChannel.getId().toString());//必填
		trans.setClientType(tempExch.getClientType());//必填
		trans.setPayChannel(tempExch.getPayChannel());//必填
		trans.setPayMethod(payMethod);//必填
		String goodsCategoryId=tempExch.getGoodsCategoryId();
		trans.setVirtualType(goodsCategoryId);//虚拟商品的类型
		//为交易设置金额和积分
		Result result=this.calAmountAndPointsForTrans(trans,tempExch);
		if(Result.FAIL.equals(result.getCode())){
			trans.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			trans.setRespMsg(result.getMsg());
			return trans;
		}
		//组装TransDetails并设置到trans里
		this.makeExchangeDetailsForTrans(trans, tempExch);
		if(TranCommand.CATEGORY_LOTTORY.equals(goodsCategoryId)){
			//彩票 组装transGoodsAttribute
			this.makeLotteryAttrListForTrans(trans, tempExch);
		}else if(TranCommand.CATEGORY_HFCZ.equals(goodsCategoryId)){
			//话费 组装transGoodsAttribute
			Result hfczResult=this.makeHFCZAttrListForTrans(trans, tempExch);
			if(Result.FAIL.equals(hfczResult.getCode())){
				trans.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
				trans.setRespMsg(hfczResult.getMsg());
				return trans;
			}
		}
		Result pointsResult=pointActionSupport.checkPoints(tempExch.getUser().getUsername(),payMethod, trans.getBankPointsValueRealAll(), trans.getFftPointsValueRealAll());
		if(Result.FAIL.equals(pointsResult.getCode())){
			trans.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			trans.setRespMsg(pointsResult.getMsg());
			return trans;
		}
		//组装pay并设置到trans里
		List<Pay> payList=payActionSupport.makePay(trans);
		if(payList==null){
			logger.error("交易的pay记录组装失败");
			trans.setRespCode(TranCommand.RESP_CODE_CREATE_FAIL);
			trans.setRespMsg("交易的支付记录组装失败");
			return trans;
		}
		trans.getPayList().addAll(payList);
		trans.setRespCode(TranCommand.RESP_CODE_CREATE_OK);
		trans.setRespMsg("交易组装成功");
		return trans;
	}
	
	private boolean needBuyer(String payMethod){
		if(TranCommand.POINTS_FFT.equals(payMethod)||
				TranCommand.POINTS_BANK.equals(payMethod)||TranCommand.ALPAY.equals(payMethod)||
				TranCommand.POINTS_FFT_ALPAY_SCOPE.equals(payMethod)||
				TranCommand.POINTS_BANK_ALPAY_SCOPE.equals(payMethod)){
			return false;
		}
		return true;
	}
	
	
	/**
	  * 方法描述：为交易组织彩票的transGoodsAttribute
	  * @param: Trans
	  * @param: ExchangeTempBean
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 16, 2013 3:37:16 PM
	  */
	private void makeLotteryAttrListForTrans(Trans trans,ExchangeTempBean tempExch){
		List<TransGoodsAttribute> attrList=new ArrayList<TransGoodsAttribute>();
		TransGoodsAttribute lotteryNo=new TransGoodsAttribute();
		lotteryNo.setElement(tempExch.getLotteryNo());
		lotteryNo.setGoodsRackAttributeId(TranCommand.ID_LOTTERY_NO);
		attrList.add(lotteryNo);
		
		TransGoodsAttribute playType=new TransGoodsAttribute();
		playType.setElement(tempExch.getPlayType());
		playType.setGoodsRackAttributeId(TranCommand.ID_PLAY_TYPE);
		attrList.add(playType);
		
		TransGoodsAttribute numType=new TransGoodsAttribute();
		numType.setElement(tempExch.getNumType());
		numType.setGoodsRackAttributeId(TranCommand.ID_NUM_TYPE);
		attrList.add(numType);
		
		TransGoodsAttribute buyAmount=new TransGoodsAttribute();
		buyAmount.setElement(tempExch.getBuyAmount());
		buyAmount.setGoodsRackAttributeId(TranCommand.ID_BUY_AMOUNT);
		attrList.add(buyAmount);
		
		TransGoodsAttribute amount=new TransGoodsAttribute();
		amount.setElement(tempExch.getAmount());
		amount.setGoodsRackAttributeId(TranCommand.ID_AMOUNT);
		attrList.add(amount);
		
		TransGoodsAttribute lotteryPhone=new TransGoodsAttribute();
		lotteryPhone.setElement(trans.getPhone());
		lotteryPhone.setGoodsRackAttributeId(TranCommand.ID_LOTTERY_PHONE);
		attrList.add(lotteryPhone);
		
		TransGoodsAttribute period=new TransGoodsAttribute();
		period.setElement(tempExch.getPeriod());
		period.setGoodsRackAttributeId(TranCommand.ID_PERIOAD);
		attrList.add(period);
		
		TransGoodsAttribute number=new TransGoodsAttribute();
		number.setElement(tempExch.getLotteryValue());
		number.setGoodsRackAttributeId(TranCommand.ID_NUMBER);
		attrList.add(number);
		
		TransGoodsAttribute numCount=new TransGoodsAttribute();
		numCount.setElement(tempExch.getNumCount());
		numCount.setGoodsRackAttributeId(TranCommand.ID_NUM_COUNT);
		attrList.add(numCount);
		
		trans.getTransGoodsAttrList().addAll(attrList);
	}
	
	
	/**
	  * 方法描述：为交易组织话费充值的transGoodsAttribute
	  * @param: Trans
	  * @param: ExchangeTempBean
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 16, 2013 3:37:16 PM
	  */
	private Result makeHFCZAttrListForTrans(Trans trans,ExchangeTempBean tempExch){
		List<TransGoodsAttribute> attrList=new ArrayList<TransGoodsAttribute>();
		TransGoodsAttribute phoneNo=new TransGoodsAttribute();
		phoneNo.setElement(tempExch.getMobile());
		phoneNo.setGoodsRackAttributeId(TranCommand.ID_PHONE_NO);
		attrList.add(phoneNo);
		
		TransGoodsAttribute price=new TransGoodsAttribute();
		price.setElement(tempExch.getCashPricing());
		price.setGoodsRackAttributeId(TranCommand.ID_PRICE);
		attrList.add(price);
		Hfcz hfcz=new Hfcz();
		hfcz.setCZNo(tempExch.getMobile());
		hfcz.setMoney(new BigDecimal(tempExch.getCashPricing()));
		Hfcz hfczRes=null;
		try {
			hfczRes=hfczService.checkParaCZNo(hfcz);
		} catch (AppException_Exception e) {
			logger.error("话费充值查询异常",e);
			return new Result(Result.FAIL,"手机归属地查询异常");
		}
		if(!PayCommand.RESPCODE_SUCCESS.equals(hfczRes.getRespCode())){
			logger.error("话费充值查询失败，原因："+hfczRes.getRespMsg());
			return new Result(Result.FAIL,"手机归属地查询出错");
		}
		TransGoodsAttribute address=new TransGoodsAttribute();
		address.setElement(hfczRes.getArea());
		address.setGoodsRackAttributeId(TranCommand.ID_ADDRESS);
		attrList.add(address);
		
		TransGoodsAttribute operator=new TransGoodsAttribute();
		operator.setElement(hfczRes.getOperater());
		operator.setGoodsRackAttributeId(TranCommand.ID_OPERATOR);
		attrList.add(operator);
		
		trans.getTransGoodsAttrList().addAll(attrList);
		return new Result(Result.SUCCESS,"话费充值的交易属性组装成功");
	}
	
	private Result calAmountAndPointsForTrans(Trans trans,ExchangeTempBean tempExch){
		String payMethod=trans.getPayMethod();
		String singleCashPrice=tempExch.getCashPricing();
		if(singleCashPrice==null||"".equals(singleCashPrice.trim())){
			logger.error("交易品的现金定价为空");
			return new Result(Result.FAIL,"交易品的现金定价为空");
		}
		Integer goodsNumber=tempExch.getBuyNumber();
		if(goodsNumber==null){
			logger.error("购买数量为空");
			return new Result(Result.FAIL,"购买数量为空");
		}
		BigDecimal decimalGoodsNumber=new BigDecimal(goodsNumber);
		String presentPoints=tempExch.getPresentPointsValue();
		if(presentPoints!=null&&!"".equals(presentPoints)){
			BigDecimal present=new BigDecimal(presentPoints).multiply(decimalGoodsNumber);
			trans.setPresentPointsValue(present.toString());
			trans.setPresentRuleId(tempExch.getPresentRuleId());
		}
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
				return new Result(Result.FAIL,"分分通积分定价为空");
			}
			String realPoints=new BigDecimal(fftPoints).multiply(decimalGoodsNumber).toString();
			trans.setFftPointsValueRealAll(realPoints);
			trans.setFftPointsValueAll(realPoints);
			trans.setCurrencyValueRealAll("0");
		}else if(TranCommand.POINTS_BANK.equals(payMethod)){//银行积分支付
			String bankPoints=tempExch.getBankPointPricing();
			if(bankPoints==null||"".equals(bankPoints.trim())){
				logger.error("银行积分定价为空，上架商品编号为： "+tempExch.getGoodsRackId());
				return new Result(Result.FAIL,"银行积分定价为空");
			}
			String realPoints=new BigDecimal(bankPoints).multiply(decimalGoodsNumber).toString();
			trans.setBankPointsValueRealAll(realPoints);
			trans.setBankPointsValueAll(realPoints);
			trans.setCurrencyValueRealAll("0");
		}else if(TranCommand.CASH.equals(payMethod)||TranCommand.ALPAY.equals(payMethod)){//现金支付
			trans.setCurrencyValueRealAll(strCashPrice);
		}else if(TranCommand.POINTS_FFT_CASH.equals(payMethod)){//方付通积分+现金
			String fft_cash=tempExch.getFftpointCashPricing();
			if(fft_cash==null){
				logger.error("(分分通积分+现金)的定价方式为空");
				return new Result(Result.FAIL,"(分分通积分+现金)的定价为空");
			}
			String[] fft_cash_arr=fft_cash.split("\\|");
			if(fft_cash_arr.length!=2){
				logger.error("(分分通积分+现金)的定价方式有误，定价内容： "+fft_cash);
				return new Result(Result.FAIL,"(分分通积分+现金)的定价方式有误");
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
				return new Result(Result.FAIL,"(银行积分+现金)的定价方式为空");
			}
			String[] bankpoint_cash_arr=bankpoint_cash.split("\\|");
			if(bankpoint_cash_arr.length!=2){
				logger.error("(银行积分+现金)的定价方式有误，定价内容： "+bankpoint_cash);
				return new Result(Result.FAIL,"(银行积分+现金)的定价方式有误");
			}
			String realPrice=new BigDecimal(bankpoint_cash_arr[1]).multiply(decimalGoodsNumber).toString();
			String realPoints=new BigDecimal(bankpoint_cash_arr[0]).multiply(decimalGoodsNumber).toString();
			trans.setCurrencyValueRealAll(realPrice);
			trans.setBankPointsValueRealAll(realPoints);
			trans.setBankPointsValueAll(realPoints);
		}else if(TranCommand.POINTS_FFT_CASH_SCOPE.equals(payMethod)||
				TranCommand.POINTS_FFT_ALPAY_SCOPE.equals(payMethod)){//方付通积分+现金(范围)
			String fft_cash_ratio=tempExch.getFftpointcashRatioPricing();
			if(fft_cash_ratio==null){
				logger.error("[方付通积分+现金(范围)]的定价方式为空");
				return new Result(Result.FAIL,"[方付通积分+现金(范围)]的定价方式为空");
			}
			String[] fft_cash_ratio_arr=fft_cash_ratio.split("\\|");
			if(fft_cash_ratio_arr.length!=2){
				logger.error("[方付通积分+现金(范围)]的定价方式有误，定价内容： "+fft_cash_ratio);
				return new Result(Result.FAIL,"[方付通积分+现金(范围)]的定价方式有误");
			}
			BigDecimal pointsPricing=this.calPoints(singleCashPrice, TranCommand.PRICING_FFT_CASH);
			BigDecimal minPoints=pointsPricing.multiply(new BigDecimal(fft_cash_ratio_arr[0])).multiply(decimalGoodsNumber).divide(hundred,2,RoundingMode.DOWN);
			BigDecimal maxPoints=pointsPricing.multiply(new BigDecimal(fft_cash_ratio_arr[1])).multiply(decimalGoodsNumber).divide(hundred,2,RoundingMode.DOWN);
			String pageParam=tempExch.getUsePointRaioValue();//页面上输入的积分(银行或分分通积分)
			if(pageParam==null||"".equals(pageParam)){
				logger.error("使用的支付方式为:(分分通积分+现金)，但用户输入的积分为空");
				return new Result(Result.FAIL,"消费的积分值为空");
			}
			if(pageParam.indexOf(".")!=-1){
				logger.error("使用的支付方式为:(分分通积分+现金)，消费的积分值不能为小数");
				return new Result(Result.FAIL,"消费的积分值不能为小数");
			}
			BigDecimal pagePoints=new BigDecimal(pageParam);
			if(pagePoints.compareTo(minPoints)==-1){
				logger.error("使用的支付方式为:(分分通积分+现金)，但积分消费数小于最低消费值");
				return new Result(Result.FAIL,"积分消费数小于最低消费值");
			}
			if(pagePoints.compareTo(maxPoints)==1){
				logger.error("使用的支付方式为:(分分通积分+现金)，但积分消费数大于最高消费值");
				return new Result(Result.FAIL,"积分消费数大于最高消费值");
			}
			BigDecimal pagePrice=this.calAmount(pageParam, TranCommand.PRICING_FFT_CASH);
			BigDecimal realPayPrice=decimalCashPrice.subtract(pagePrice);
			trans.setCurrencyValueRealAll(realPayPrice.toString());
			trans.setFftPointsValueRealAll(pageParam);//由页面输入,但需要校验是否合法
			trans.setFftPointsValueAll(pageParam);
		}else if(TranCommand.POINTS_BANK_CASH_SCOPE.equals(payMethod)||
				TranCommand.POINTS_BANK_ALPAY_SCOPE.equals(payMethod)){//银行积分+现金(范围)
			String bank_cash_ratio=tempExch.getBankpointcashRatioPricing();
			if(bank_cash_ratio==null){
				logger.error("[银行积分+现金(范围)]的定价方式为空");
				return new Result(Result.FAIL,"[银行积分+现金(范围)]的定价方式为空");
			}
			String[] bank_cash_ratio_arr=bank_cash_ratio.split("\\|");
			if(bank_cash_ratio_arr.length!=2){
				logger.error("[银行积分+现金(范围)]的定价方式有误，定价内容： "+bank_cash_ratio);
				return new Result(Result.FAIL,"[银行积分+现金(范围)]的定价方式有误");
			}
			BigDecimal pointsPricing=this.calPoints(singleCashPrice, TranCommand.PRICING_BANK_CASH);
			BigDecimal minPoints=pointsPricing.multiply(new BigDecimal(bank_cash_ratio_arr[0])).multiply(decimalGoodsNumber).divide(hundred,2,RoundingMode.DOWN);
			BigDecimal maxPoints=pointsPricing.multiply(new BigDecimal(bank_cash_ratio_arr[1])).multiply(decimalGoodsNumber).divide(hundred,2,RoundingMode.DOWN);
			String pageParam=tempExch.getUsePointRaioValue();//页面上输入的积分(银行或分分通积分)
			if(pageParam==null||"".equals(pageParam)){
				logger.error("使用的支付方式为:(银行积分+现金)，但用户输入的积分为空");
				return new Result(Result.FAIL,"消费的积分值为空");
			}
			if(pageParam.indexOf(".")!=-1){
				logger.error("使用的支付方式为:(银行积分+现金)，消费的积分值不能为小数");
				return new Result(Result.FAIL,"消费的积分值不能为小数");
			}
			BigDecimal pagePoints=new BigDecimal(pageParam);
			if(pagePoints.compareTo(minPoints)==-1){
				logger.error("使用的支付方式为:(银行积分+现金)，但积分消费数小于最低消费值");
				return new Result(Result.FAIL,"消费的积分值小于最低消费值");
			}
			if(pagePoints.compareTo(maxPoints)==1){
				logger.error("使用的支付方式为:(银行积分+现金)，但积分消费数大于最高消费值");
				return new Result(Result.FAIL,"消费的积分值大于最高消费值");
			}
			BigDecimal pagePrice=this.calAmount(pageParam, TranCommand.PRICING_BANK_CASH);
			BigDecimal realPayPrice=decimalCashPrice.subtract(pagePrice);
			trans.setCurrencyValueRealAll(realPayPrice.toString());
			trans.setBankPointsValueRealAll(pageParam);//由页面输入,但需要校验是否合法
			trans.setBankPointsValueAll(pageParam);
		}else{
			logger.error("未知的支付方式："+payMethod);
			return new Result(Result.FAIL,"无效的支付方式");
		}
		return new Result(Result.SUCCESS,"该笔交易的金额和积分计算成功");
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
		String goodsCategoryId=tempExch.getGoodsCategoryId();
		TransDetails details=new TransDetails();
		details.setCurrency(Command.CURRENCY);
		details.setState(Command.STATE_START);
		details.setGoodsRackId(goodsRackId);
		details.setGoodsCategoryId(goodsCategoryId);
		details.setGoodsNumber(goodsNumber);
		details.setCurrency(Command.CURRENCY);
		//交易详情里的价格
		details.setBankPointsValueAll(trans.getBankPointsValueAll());
		details.setBankPointsValueRealAll(trans.getBankPointsValueRealAll());
		details.setFftPointsValueAll(trans.getFftPointsValueAll());
		details.setFftPointsValueRealAll(trans.getFftPointsValueRealAll());
		details.setCostpriceTotal(trans.getCostpriceTotal());
		details.setCurrencyValue(trans.getCurrencyValueAll());
		details.setCurrencyValueReal(trans.getCurrencyValueRealAll());
		
		trans.getTransDetailsList().add(details);
	}
	
	private PresentPointRule findRule(List<PresentPointRule> ruleList){
		if(ruleList==null||ruleList.size()==0){
			return null;
		}
		PresentPointRule rule=null;
		for (int i = 0; i < ruleList.size(); i++) {
			rule=ruleList.get(i);
			if(isRange(rule.getActiveTime(), rule.getExpireTime())){
				return rule;
			}
		}
		return null;
	}
	
	private boolean isRange(String begin,String end){
		try {
			Date beginDate=dateFormat.parse(begin);
			Date endDate=dateFormat.parse(end);
			Date now = new Date();
			if(now.before(beginDate)||now.after(endDate)){
				return false;
			}
			return true;
		} catch (ParseException e) {
			logger.error("日期格式不正确",e);
			return false;
		}
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

	public void setClientGoodsExchangeRackActionSupport(
			ClientGoodsExchangeRackActionSupport clientGoodsExchangeRackActionSupport) {
		this.clientGoodsExchangeRackActionSupport = clientGoodsExchangeRackActionSupport;
	}

	public void setHfczService(HFCZService hfczService) {
		this.hfczService = hfczService;
	}

	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}

	public void setPresentPointRuleService(
			PresentPointRuleService presentPointRuleService) {
		this.presentPointRuleService = presentPointRuleService;
	}

}
