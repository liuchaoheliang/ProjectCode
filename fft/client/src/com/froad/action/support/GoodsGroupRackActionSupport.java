package com.froad.action.support;

import java.util.*;

import org.apache.log4j.Logger;

import com.froad.AppException.PointsUseError;
import com.froad.AppException.PriceError;
import com.froad.action.web.tran.TransAddtionalInfoVo;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.buyers.Buyers;
import com.froad.client.clientGoodsGroupRack.ClientGoodsGroupRack;
import com.froad.client.goodsGroupRack.GoodsGroupRack;
import com.froad.client.goodsGroupRack.GoodsGroupRackService;
import com.froad.client.merchant.Merchant;
import com.froad.client.sellers.Seller;
import com.froad.client.pointCashRule.PointCashRule;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.trans.Goods;
import com.froad.client.trans.GoodsCategory;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.trans.TransGoodsAttribute;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.server.tran.TranService;
import com.froad.util.Assert;
import com.froad.util.CommonUtil;
import com.froad.util.command.Command;
import com.froad.util.command.GoodCategory;
import com.froad.util.command.PayChannel;
import com.froad.util.command.PointsType;
import com.froad.util.command.TranGoodsAttributes;
import com.froad.util.command.TransState;
import com.froad.util.command.TransType;
import com.froad.util.command.UseTime;
import com.froad.vo.trans.GoodsGroupRackTran;
import com.sun.jmx.snmp.Timestamp;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 团购 client service  ActionSupport
 */
public class GoodsGroupRackActionSupport {
	private static Logger logger = Logger.getLogger(GoodsGroupRackActionSupport.class);
	private GoodsGroupRackService goodsGroupRackService;
	private TranService transServiceInClient;
	private SellersActionSupport sellerActionSupport;
	private TransActionSupport transActionSupport;
	private PointCashRuleActionSupport pointCashRuleActionSupport;
	private GoodsActionSupport goodsActionSupport;
	private MerchantActionSupport merchantActionSupport;
	
	public GoodsGroupRack findGoodsGroupByPager(GoodsGroupRack goodsGroupRack){
		try {
			goodsGroupRack = goodsGroupRackService.getGoodsGroupRackByPager(goodsGroupRack);
		} catch (Exception e) {
			logger.error("GoodsGroupRackTranActionSupport.findGoodsGroupByPager查询团购商品列表出错", e);
		}
		return goodsGroupRack;
	}

    /**
     *  首页数据列表
     * @return 首页数据列表
     */
    public  List<GoodsGroupRack> getIndexGoodsRack( ){
        List<GoodsGroupRack> result  = new ArrayList<GoodsGroupRack>();
  		try {
            result = goodsGroupRackService.getIndexGoodsRack();
  		} catch (Exception e) {
  			logger.error("GoodsGroupRackTranActionSupport.findGoodsGroupByPager查询团购商品列表出错", e);
  		}
  		return result;
  	}
	

	public GoodsGroupRackService getGoodsGroupRackService() {
		return goodsGroupRackService;
	}



	public void setGoodsGroupRackService(GoodsGroupRackService goodsGroupRackService) {
		this.goodsGroupRackService = goodsGroupRackService;
	}




//	public void getGoodsGroupByPager(GoodsGroupRack goodsGroupRack){
//		try {
//			goodsGroupRackService.getGoodsGroupRackByPager(goodsGroupRack);
//		} catch (Exception e) {
//			logger.error("GoodsGroupRackActionSupport.getGoodsGroupByPager查询团购商品详细信息出错 ", e);
//		}
//	}

	
	public GoodsGroupRack getGoodsGroupRackById(Integer id) {
		GoodsGroupRack goodsGroupRackRes = new GoodsGroupRack();
		try {
			goodsGroupRackRes = goodsGroupRackService.getGoodsGroupRackById(id);
		} catch (Exception e) {
			logger.error("GoodsGroupRackTranActionSupport.getGoodsGroupRackTranById查询团购商品详细信息出错 id:"+id, e);
		}
		return goodsGroupRackRes;
	}
	
	public GoodsGroupRackService getGoodsGroupRackTranService() {
		return goodsGroupRackService;
	}
	public void setGoodsGroupRackTranService(GoodsGroupRackService goodsGroupRackService) {
		this.goodsGroupRackService = goodsGroupRackService;
	}



	public GoodsGroupRack getGoodsGroupRackTransById(String id) {
		// TODO Auto-generated method stub
		return goodsGroupRackService.getGoodsGroupRackById(Integer.parseInt(id));
	}
	
	
	//创建团购交易  客户端
//	public Trans createTrans(ClientGoodsGroupRack goodsRack, Trans trans,
//			TransDetails transDetails, Buyers buyer, User user, 
//			TransAddtionalInfoVo transAddtionalInfoVo, String clientType){
//		GoodsGroupRackTran goodsGroupRackTran=new GoodsGroupRackTran();
//		if(goodsRack==null){
//			logger.error("客户版的ClientGoodsGroupRack为空！");
//			return null;
//		}
//		com.froad.client.clientGoodsGroupRack.Goods goods=goodsRack.getGoods();
//		if(goods==null){
//			logger.error("客户版的ClientGoodsGroupRack的good为空！");
//			return null;
//		}
//		
//		if(Assert.empty(goodsRack.getGoods().getMerchantId()) ){
//			logger.error("客户版的ClientGoodsGroupRack的merchantID为空！");
//			return null;
//		}
//		
//			
//		Goods goodsInTrans=new Goods();
//		GoodsCategory goodsCategory=new GoodsCategory();
//		goodsCategory.setId(goods.getGoodsCategory().getId());
//		goodsCategory.setName(goods.getGoodsCategory().getName());
//		goodsInTrans.setGoodsCategory(goodsCategory);
//		goodsInTrans.setGoodsCategoryId(goods.getGoodsCategoryId());
//		goodsInTrans.setId(goods.getId());
//		goodsGroupRackTran.setGoods(goodsInTrans);
//		goodsGroupRackTran.setMerchantId(goodsRack.getGoods().getMerchantId());
//		goodsGroupRackTran.setId(goodsRack.getId());
//		goodsGroupRackTran.setIsCash(goodsRack.getIsCash());
//		goodsGroupRackTran.setIsBankPoint(goodsRack.getIsBankPoint());
//		goodsGroupRackTran.setIsBankpointCash(goodsRack.getIsBankpointCash());
//		goodsGroupRackTran.setIsFftPoint(goodsRack.getIsFftPoint());
//		goodsGroupRackTran.setIsFftpointCash(goodsRack.getIsFftpointCash());
//		goodsGroupRackTran.setIsFftpointcashRatioPricing(goodsRack.getIsFftpointcashRatioPricing());
//		goodsGroupRackTran.setIsBankpointcashRatioPricing(goodsRack.getIsBankpointcashRatioPricing());
//		
//		goodsGroupRackTran.setCashPricing(goodsRack.getCashPricing());
//		goodsGroupRackTran.setBankPointPricing(goodsRack.getBankPointPricing());
//		goodsGroupRackTran.setBankpointCashPricing(goodsRack.getBankpointCashPricing());
//		goodsGroupRackTran.setFftPointPricing(goodsRack.getFftPointPricing());
//		goodsGroupRackTran.setFftpointCashPricing(goodsRack.getFftpointCashPricing());
//		goodsGroupRackTran.setFftpointcashRatioPricing(goodsRack.getFftpointcashRatioPricing());
//		goodsGroupRackTran.setBankpointcashRatioPricing(goodsRack.getBankpointcashRatioPricing());
//		
//		goodsGroupRackTran.setGroupPrice(goodsRack.getGroupPrice());
//		
//		goodsGroupRackTran.setSellerId(String.valueOf(goodsRack.getSellerId()));
//		return createTrans( goodsGroupRackTran,  trans,
//				 transDetails,  buyer,  user,  
//				 transAddtionalInfoVo,  clientType);
//	}
	
	//创建团购交易  PC端
//	public Trans createTrans(GoodsGroupRack goodsRack, Trans trans,
//			TransDetails transDetails, Buyers buyer, User user, 
//			TransAddtionalInfoVo transAddtionalInfoVo, String clientType){
//		GoodsGroupRackTran goodsGroupRackTran=new GoodsGroupRackTran();
//		if(goodsRack==null){
//			logger.error("客户版的GoodsGroupRack为空！");
//			return null;
//		}
//		com.froad.client.goodsGroupRack.Goods goods=goodsRack.getGoods();
//		if(goods==null){
//			logger.error("客户版的GoodsGroupRack的good为空！");
//			return null;
//		}
//		if(Assert.empty(goodsRack.getGoods().getMerchantId()) ){
//			logger.error("客户版的GoodsGroupRack的merchantID为空！");
//			return null;
//		}
//			
//		Goods goodsInTrans=new Goods();
//		GoodsCategory goodsCategory=new GoodsCategory();
//		goodsCategory.setId(goods.getGoodsCategory().getId());
//		goodsCategory.setName(goods.getGoodsCategory().getName());
//		goodsInTrans.setGoodsCategory(goodsCategory);
//		goodsInTrans.setGoodsCategoryId(goods.getGoodsCategoryId());
//		goodsInTrans.setId(goods.getId());
//		
//		goodsGroupRackTran.setGoods(goodsInTrans);
//		goodsGroupRackTran.setMerchantId(goodsRack.getGoods().getMerchantId());
//		goodsGroupRackTran.setId(goodsRack.getId());
//		goodsGroupRackTran.setIsCash(goodsRack.getIsCash());
//		goodsGroupRackTran.setIsBankPoint(goodsRack.getIsBankPoint());
//		goodsGroupRackTran.setIsBankpointCash(goodsRack.getIsBankpointCash());
//		goodsGroupRackTran.setIsFftPoint(goodsRack.getIsFftPoint());
//		goodsGroupRackTran.setIsFftpointCash(goodsRack.getIsFftpointCash());
//		goodsGroupRackTran.setIsFftpointcashRatioPricing(goodsRack.getIsFftpointcashRatioPricing());
//		goodsGroupRackTran.setIsBankpointcashRatioPricing(goodsRack.getIsBankpointcashRatioPricing());
//		
//		goodsGroupRackTran.setCashPricing(goodsRack.getCashPricing());
//		goodsGroupRackTran.setBankPointPricing(goodsRack.getBankPointPricing());
//		goodsGroupRackTran.setBankpointCashPricing(goodsRack.getBankpointCashPricing());
//		goodsGroupRackTran.setFftPointPricing(goodsRack.getFftPointPricing());
//		goodsGroupRackTran.setFftpointCashPricing(goodsRack.getFftpointCashPricing());
//		goodsGroupRackTran.setFftpointcashRatioPricing(goodsRack.getFftpointcashRatioPricing());
//		goodsGroupRackTran.setBankpointcashRatioPricing(goodsRack.getBankpointcashRatioPricing());
//		
//		goodsGroupRackTran.setGroupPrice(goodsRack.getGroupPrice());
//		goodsGroupRackTran.setSellerId(String.valueOf(goodsRack.getSellerId()));
//		return createTrans( goodsGroupRackTran,  trans,
//				 transDetails,  buyer,  user,  
//				 transAddtionalInfoVo,  clientType);
//	}


	//创建团购交易  
//	public Trans createTrans(GoodsGroupRackTran goodsRack, Trans trans,
//			TransDetails transDetails, Buyers buyer, User user, 
//			TransAddtionalInfoVo transAddtionalInfoVo, String clientType) {
//		// TODO Auto-generated method stub
//		Trans result=trans;
//		try {
//			if (trans == null)
//				trans = new Trans();
//			trans.setClientType(clientType);
//			
//			if (!Assert.empty(trans.getPayMethod())) {
//				PayMethod payMethod = PayMethod.getPayMethod(trans.getPayMethod());
//				price(goodsRack, transDetails, payMethod);
//			} else {
//				logger.error("支付方式为空!");
////				String currencyPriceStr = goodsRack.getCashPricing();
////				if (!Assert.empty(currencyPriceStr))
////					countPayInfoAndPayMethod(Double.valueOf(currencyPriceStr),transDetails, trans);
//			}
//			com.froad.client.merchant.Merchant merchant=merchantActionSupport.getMerchantById(goodsRack.getMerchantId()); 
//			String sellerId=goodsRack.getSellerId();
//			com.froad.client.sellers.Seller seller1 = sellerActionSupport.getSellerById(sellerId);
//			List<SellerChannel> sellerChannelList = seller1.getSellerChannelList();
//			trans.getTransDetailsList().add(transDetails);
//			updateTransBaseInfo(trans, transDetails, goodsRack, seller1, buyer);
////			updateTransAdditionalInfo(trans, transDetails, goodsRack,transAddtionalInfoVo);
//			List<BuyerChannel> buyersDepositChannelList = buyer.getBuyerChannelList();
//			BuyerChannel buyersDepositChannel = buyersDepositChannelList.get(0);
//			
//					
//			com.froad.client.sellers.SellerChannel sellerDepositChannel1 = sellerChannelList
//					.get(0);
//			
//			try {
//				transServiceInClient.countTansferInfoTran(trans, seller1,
//						sellerDepositChannel1, buyer,buyersDepositChannel,user,null, 
//						UseTime.COUNT_TRANS_CURRENCY);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				logger.error("计算积分流转信息失败", e);
//				return null;
//			}
//			result = transActionSupport.addTrans(trans);
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error("积分兑换下单时，报异常：", e);
//			result=null;
//		}
//		return result;
//	}
//	
//	//更新交易信息
//	private void updateTransBaseInfo(Trans trans,TransDetails transDetails,GoodsGroupRackTran goodsRack,Seller seller,Buyers buyer){
//		trans.setTrackNo(transActionSupport.generateTrackNo());
//		trans.setTransSn(transActionSupport.generateTrackNo());
////		trans.setClientType(ClientType.ClientType_PC);
//		trans.setPayChannel(PayChannel.PAYTYPE_MPhoneCard);
//		trans.setMerchantId(Integer.valueOf(goodsRack.getMerchantId()));
//		trans.setSellerType(seller.getSellerType());
//		
//		//因包不同
//		Goods goods=goodsRack.getGoods();
//		Goods goodsInTrans=new Goods();
//		GoodsCategory goodsCategory=new GoodsCategory();
//		goodsCategory.setId(goods.getGoodsCategory().getId());
//		goodsCategory.setName(goods.getGoodsCategory().getName());
//		goodsInTrans.setGoodsCategory(goodsCategory);
//		goodsInTrans.setGoodsCategoryId(goods.getGoodsCategoryId());
//		
//		transDetails.setGoods(goodsInTrans);
//		
//		List<com.froad.client.sellers.SellerChannel> sellerChannelList=seller.getSellerChannelList();
//		List<BuyerChannel> buyersDepositChannelList=buyer.getBuyerChannelList();
//		BuyerChannel buyersDepositChannel=buyersDepositChannelList.get(0);
//		trans.setSellerId(String.valueOf(seller.getId()));
//		if(!Assert.empty(sellerChannelList))
//			trans.setSellerChannelId(String.valueOf(sellerChannelList.get(0).getId()));
//		trans.setBuyersId(String.valueOf(buyer.getId()));
//		if(buyersDepositChannel!=null)
//			trans.setBuyerChannelId(String.valueOf(buyersDepositChannel.getId()));
//		
//		trans.setTransType(TransType.Trans_Group_buy.getValue());
//		
//		trans.setCostpriceTotal(goodsRack.getCashPricing());
//		trans.setCurrencyValueAll(transDetails.getCurrencyValue());
//		trans.setCurrencyValueRealAll(transDetails.getCurrencyValueReal());
//		trans.setFftPointsValueAll(transDetails.getFftPointsValueAll());
//		trans.setFftPointsValueRealAll(transDetails.getFftPointsValueRealAll());
//		trans.setBankPointsValueAll(transDetails.getBankPointsValueAll());
//		trans.setBankPointsValueRealAll(transDetails.getBankPointsValueRealAll());
//		
//		trans.setPayChannel(PayChannel.PAYTYPE_MPhoneCard);
//		trans.setState(TransState.doing);
//	}
//	
//	
//	private void countPayInfoAndPayMethod(double currencyPrice,TransDetails transDetails,Trans tran){
//		PointCashRule pointCashRule=transActionSupport.getPointCashRulesByPointType(PointsType.FFT_POINTS);
//		int points=Integer.valueOf(pointCashRule.getPointValue());
//		int currency=Integer.valueOf(pointCashRule.getCashValue());
//		double restCurrency=0;
//		String payMethod=PayMethod.Currency.getValue();
//		if(!Assert.empty(transDetails.getBankPointsValueRealAll())){
//			int bankPoints=Integer.valueOf(transDetails.getBankPointsValueRealAll());
//			restCurrency=currencyPrice-(currency*bankPoints)/points;
//			if(restCurrency>0)
//				payMethod=PayMethod.Bank_Points_Currency.getValue();
//		}
//		if(!Assert.empty(transDetails.getFftPointsValueRealAll())){
//			int fftPoints=Integer.valueOf(transDetails.getFftPointsValueRealAll());
//			 restCurrency=currencyPrice-(currency*fftPoints)/points;
//			 if(restCurrency>0)
//					payMethod=PayMethod.FFT_Points_Currency.getValue();
//		}
//		restCurrency=CommonUtil.scale2(restCurrency,2);
//		transDetails.setCurrencyValue(String.valueOf(restCurrency));
//		transDetails.setCurrencyValueReal(String.valueOf(restCurrency));
//		tran.setPayMethod(payMethod);
//	}
//	
//	
//	private void price(GoodsGroupRackTran goods,TransDetails transDetails,PayMethod payMethod) throws PriceError, PointsUseError{
//		if(Assert.empty(transDetails.getFftPointsValueAll()))
//			transDetails.setFftPointsValueAll("0");
//		if(Assert.empty(transDetails.getFftPointsValueRealAll()))
//			transDetails.setFftPointsValueRealAll("0");
//		if(Assert.empty(transDetails.getCurrencyValue()))
//			transDetails.setCurrencyValue("0");
//		if(Assert.empty(transDetails.getCurrencyValueReal()))
//			transDetails.setCurrencyValueReal("0");
//		if(Assert.empty(transDetails.getBankPointsValueAll()))
//			transDetails.setBankPointsValueAll("0");
//		if(Assert.empty(transDetails.getBankPointsValueRealAll()))
//			transDetails.setBankPointsValueRealAll("0");
//		String fftPointsStr=goods.getFftPointPricing();
//		double fftPoints=0;
//		String currencyStr=goods.getCashPricing();
//		double currency=0.0;
//		double groupPrice=Double.valueOf(goods.getGroupPrice());
//		String bankPointsStr=goods.getBankpointCashPricing();
//		double bankPoints=0;
//		String goodsNumStr=transDetails.getGoodsNumber();
//		int goodsNum=Integer.valueOf(goodsNumStr);;
//		
//		
//		switch(payMethod){
//		case FFT_Points:
//			transDetails.setFftPointsValueRealAll("0");
//			transDetails.setFftPointsValueAll("0");
//			transDetails.setCurrencyValue("0");
//			transDetails.setCurrencyValueReal("0");
//			transDetails.setBankPointsValueRealAll("0");
//			transDetails.setBankPointsValueAll("0");
//			if(!Command.price_availiable.equals(goods.getIsFftPoint())){
//				throw new PriceError("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
//			}
//			//注意话费按定价来收款,而不是按照话费的实际金额来收款
////			if(GoodCategory.Goods_Category_Recharge_Phone.getValue().equals(goods.getGoods().getGoodsCategory().getName())){
////				String cash=goods.getRemark();
////				fftPointsStr=pointCashRuleActionSupport.getPointByCash(cash, PointsType.FFT_POINTS);
////				Double fftPointsD=Double.valueOf(fftPointsStr);
////				fftPointsD=fftPointsD*goodsNum;
////				fftPoints=fftPointsD.intValue();
////				if(fftPoints!=fftPointsD){
////					int pointIndex=String.valueOf(fftPointsD).indexOf(".");
////					if(pointIndex!=-1){
////						String currencyValue="0."+String.valueOf(fftPointsD).substring(pointIndex+1);
////						transDetails.setCurrencyValue(currencyValue);
////						transDetails.setCurrencyValueReal(currencyValue);
////					}
////				}
////			}else{	
////				fftPoints=Integer.valueOf(fftPointsStr);
////				fftPoints=fftPoints*goodsNum;
////			}
//			fftPoints=Double.valueOf(fftPointsStr);
//			fftPoints=fftPoints*goodsNum;
//			transDetails.setFftPointsValueAll(String.valueOf(fftPoints));
//			transDetails.setFftPointsValueRealAll(String.valueOf(fftPoints));
//			break;
//		case Bank_Points:
//			transDetails.setFftPointsValueRealAll("0");
//			transDetails.setFftPointsValueAll("0");
//			transDetails.setCurrencyValue("0");
//			transDetails.setCurrencyValueReal("0");
//			transDetails.setBankPointsValueRealAll("0");
//			transDetails.setBankPointsValueAll("0");
//			if(!Command.price_availiable.equals(goods.getIsBankPoint())){
//				throw new PriceError("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
//			}
//			//注意话费按定价来收款,而不是按照话费的实际金额来收款
////			if(GoodCategory.Goods_Category_Recharge_Phone.getValue().equals(goods.getGoods().getGoodsCategory().getName())){
////				String cash=goods.getRemark();
////				bankPointsStr=pointCashRuleActionSupport.getPointByCash(cash, PointsType.BANK_POINTS);
////			}
//			bankPoints=Double.valueOf(bankPointsStr);
//			bankPoints=bankPoints*goodsNum;
//			transDetails.setBankPointsValueAll(String.valueOf(bankPoints));
//			transDetails.setBankPointsValueRealAll(String.valueOf(bankPoints));
//			break;
//		case Currency:
//			transDetails.setFftPointsValueRealAll("0");
//			transDetails.setFftPointsValueAll("0");
//			transDetails.setCurrencyValue("0");
//			transDetails.setCurrencyValueReal("0");
//			transDetails.setBankPointsValueRealAll("0");
//			transDetails.setBankPointsValueAll("0");
//			if(!Command.price_availiable.equals(goods.getIsCash())){
//				throw new PriceError("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
//			}
//			//注意话费按定价来收款,而不是按照话费的实际金额来收款
////			if(GoodCategory.Goods_Category_Recharge_Phone.getValue().equals(goods.getGoods().getGoodsCategory().getName())){
////				currencyStr=goods.getRemark();
////			}
//			currency=Double.valueOf(currencyStr);
//			currency=currency*goodsNum;
//			currency=CommonUtil.scale2(currency,2);
//			transDetails.setCurrencyValue(String.valueOf(currency));
//			transDetails.setCurrencyValueReal(String.valueOf(currency));
//			break;
//		case FFT_Points_Currency:
//			transDetails.setFftPointsValueRealAll("0");
//			transDetails.setFftPointsValueAll("0");
//			transDetails.setCurrencyValue("0");
//			transDetails.setCurrencyValueReal("0");
//			transDetails.setBankPointsValueRealAll("0");
//			transDetails.setBankPointsValueAll("0");
//			if(!Command.price_availiable.equals(goods.getIsFftpointCash())){
//				throw new PriceError("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
//			}
//			
//			String fftpointsAndCurrency=goods.getFftpointCashPricing();
//			fftpointsAndCurrency=fftpointsAndCurrency.replace("|", ",");
//			String[] fftpointsAndCurrencyArr=fftpointsAndCurrency.split(",");
//			if(fftpointsAndCurrencyArr!=null&&fftpointsAndCurrencyArr.length==2){
//				fftPoints=Integer.valueOf(fftpointsAndCurrencyArr[0]);
//				fftPoints=fftPoints*goodsNum;
//				currency=Double.valueOf(fftpointsAndCurrencyArr[1]);
//				currency=currency*goodsNum;
//				currency=CommonUtil.scale2(currency,2);
//				transDetails.setFftPointsValueAll(String.valueOf(fftPoints));
//				transDetails.setFftPointsValueRealAll(String.valueOf(fftPoints));
//				transDetails.setCurrencyValue(String.valueOf(currency));
//				transDetails.setCurrencyValueReal(String.valueOf(currency));
//			}
//			break;
//		case Bank_Points_Currency:
//			transDetails.setFftPointsValueRealAll("0");
//			transDetails.setFftPointsValueAll("0");
//			transDetails.setCurrencyValue("0");
//			transDetails.setCurrencyValueReal("0");
//			transDetails.setBankPointsValueRealAll("0");
//			transDetails.setBankPointsValueAll("0");
//			if(!Command.price_availiable.equals(goods.getIsBankpointCash())){
//				throw new PriceError("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
//			}
//			String bankpointsAndCurrency=goods.getBankpointCashPricing();
//			bankpointsAndCurrency=bankpointsAndCurrency.replace("|", ",");
//			String[] bankpointsAndCurrencyArr=bankpointsAndCurrency.split(",");
//			bankPoints=Double.valueOf(bankpointsAndCurrencyArr[0]);
//			bankPoints=bankPoints*goodsNum;
//			currency=Double.valueOf(bankpointsAndCurrencyArr[1]);
//			currency=currency*goodsNum;
//			currency=CommonUtil.scale2(currency,2);
//			if(bankpointsAndCurrencyArr!=null&&bankpointsAndCurrencyArr.length==2){
//				transDetails.setBankPointsValueAll(String.valueOf(bankPoints));
//				transDetails.setBankPointsValueRealAll(String.valueOf(bankPoints));
//				transDetails.setCurrencyValue(String.valueOf(currency));
//				transDetails.setCurrencyValueReal(String.valueOf(currency));
//			}
//			break;
//			
//		case FFT_Points_Currency_Scope:
//			if(!Command.price_availiable.equals(goods.getIsFftpointcashRatioPricing())){
//				throw new PriceError("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
//			}
//			if(Assert.empty(transDetails.getFftPointsValueRealAll()))
//				transDetails.setFftPointsValueRealAll("0");
//			String fftpointsMinMax=goods.getFftpointcashRatioPricing();
//			fftpointsMinMax=fftpointsMinMax.replace("|", ",");
//			String[] fftpointsMinMaxArr=fftpointsMinMax.split(",");
//			
//			double fftPointsMin=Double.valueOf(fftpointsMinMaxArr[0]);
//			String fftPointsMinStr=pointCashRuleActionSupport.getPointByCash(String.valueOf(fftPointsMin*0.01*groupPrice),PointsType.FFT_POINTS);
//			fftPointsMin= CommonUtil.scale2(Double.parseDouble(fftPointsMinStr),0);
//			
//			double fftPointsMax=Integer.valueOf(fftpointsMinMaxArr[1]);
//			String fftPointsMaxStr=pointCashRuleActionSupport.getPointByCash(String.valueOf(fftPointsMax*0.01*groupPrice),PointsType.FFT_POINTS);
//			fftPointsMax= CommonUtil.scale2(Double.parseDouble(fftPointsMaxStr),0);
//			
//			fftPoints=Double.parseDouble(transDetails.getFftPointsValueRealAll());
//			if(fftPoints>fftPointsMax)
//				throw new PointsUseError("使用的积分不在定价的积分范围内");
//			if(fftPoints<fftPointsMin)
//				throw new PointsUseError("使用的积分不在定价的积分范围内");
//			
//			String fftPointsRealCurrencyValStr=pointCashRuleActionSupport.getCashByPoint(transDetails.getFftPointsValueRealAll(), PointsType.FFT_POINTS);
//			//如何现金与积分换算有误，则用资金收款
//			if(Assert.empty(fftPointsRealCurrencyValStr)){
//				fftPointsRealCurrencyValStr="0";
//				fftPoints=0;
//				logger.info("现金与积分换算有误，则用资金收款!"+new Timestamp(new Date().getTime()));
//			}
//			double fftPointsRealD=Double.valueOf(fftPointsRealCurrencyValStr);
//			currency=groupPrice*goodsNum-fftPointsRealD;
//			currency=CommonUtil.scale2(currency,2);
//			
//			transDetails.setFftPointsValueRealAll(String.valueOf(fftPoints));
//			transDetails.setFftPointsValueAll(transDetails.getFftPointsValueRealAll());
//			transDetails.setCurrencyValue(String.valueOf(currency));
//			transDetails.setCurrencyValueReal(String.valueOf(currency));
//			transDetails.setBankPointsValueRealAll("0");
//			transDetails.setBankPointsValueAll("0");
//			
//			break;
//			
//		case Bank_Points_Currency_Scope:
//			if(!Command.price_availiable.equals(goods.getIsBankpointcashRatioPricing())){
//				throw new PriceError("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
//			}
//			if(Assert.empty(transDetails.getBankPointsValueRealAll()))
//				transDetails.setBankPointsValueRealAll("0");
//			String bankpointsMinMax=goods.getBankpointcashRatioPricing();
//			bankpointsMinMax=bankpointsMinMax.replace("|", ",");
//			String[] bankpointsMinMaxArr=bankpointsMinMax.split(",");
//			
//			double bankPointsMin=Double.valueOf(bankpointsMinMaxArr[0]);
//			String bankPointsMinStr=pointCashRuleActionSupport.getPointByCash(String.valueOf(bankPointsMin*0.01*groupPrice),PointsType.BANK_POINTS);
//			bankPointsMin=CommonUtil.scale2(Double.parseDouble(bankPointsMinStr),0);
//			
//			double bankPointsMax=Double.valueOf(bankpointsMinMaxArr[1]);
//			String bankPointsMaxStr=pointCashRuleActionSupport.getPointByCash(String.valueOf(bankPointsMax*0.01*groupPrice),PointsType.BANK_POINTS);
//			bankPointsMax= CommonUtil.scale2(Double.parseDouble(bankPointsMaxStr),0);
//			
//			bankPoints=Double.parseDouble(transDetails.getBankPointsValueRealAll());
//			if(bankPoints>bankPointsMax)
//				throw new PointsUseError("使用的积分不在定价的积分范围内");
//			if(bankPoints<bankPointsMin)
//				throw new PointsUseError("使用的积分不在定价的积分范围内");
//			
//			//计算应收金额
//			String bankPointsRealCurrencyValStr=pointCashRuleActionSupport.getCashByPoint(transDetails.getBankPointsValueRealAll(), PointsType.BANK_POINTS);
//			//如何现金与积分换算有误，则用资金收款
//			if(Assert.empty(bankPointsRealCurrencyValStr)){
//				bankPointsRealCurrencyValStr="0";
//				bankPoints=0;
//				logger.info("现金与积分换算有误，则用资金收款!"+new Timestamp(new Date().getTime()));
//			}
//			double bankPointsRealD=Double.valueOf(bankPointsRealCurrencyValStr);
//			currency=groupPrice*goodsNum-bankPointsRealD;
//			currency=CommonUtil.scale2(currency,2);
//			transDetails.setFftPointsValueRealAll("0");
//			transDetails.setFftPointsValueAll("0");
//			transDetails.setBankPointsValueRealAll(String.valueOf(bankPoints));
//			transDetails.setBankPointsValueAll(transDetails.getBankPointsValueRealAll());
//			transDetails.setCurrencyValue(String.valueOf(currency));
//			transDetails.setCurrencyValueReal(String.valueOf(currency));
//			
//			break;
//		};
//	}



	public TranService getTransServiceInClient() {
		return transServiceInClient;
	}



	public void setTransServiceInClient(TranService transServiceInClient) {
		this.transServiceInClient = transServiceInClient;
	}



	public SellersActionSupport getSellerActionSupport() {
		return sellerActionSupport;
	}



	public void setSellerActionSupport(SellersActionSupport sellerActionSupport) {
		this.sellerActionSupport = sellerActionSupport;
	}



	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}



	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}



	public PointCashRuleActionSupport getPointCashRuleActionSupport() {
		return pointCashRuleActionSupport;
	}



	public void setPointCashRuleActionSupport(
			PointCashRuleActionSupport pointCashRuleActionSupport) {
		this.pointCashRuleActionSupport = pointCashRuleActionSupport;
	}




	public GoodsActionSupport getGoodsActionSupport() {
		return goodsActionSupport;
	}




	public void setGoodsActionSupport(GoodsActionSupport goodsActionSupport) {
		this.goodsActionSupport = goodsActionSupport;
	}




	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}




	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	
	
}
