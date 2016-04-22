package com.froad.action.support.admin.trans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.action.admin.seller.trans.CashdeskService;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.trans.PayActionSupport;
import com.froad.client.user.Buyers;
import com.froad.client.trans.GoodsGatherRack;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellersService;
import com.froad.client.trans.Goods;
import com.froad.client.trans.Pay;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.common.TranCommand;
import com.froad.server.tran.TranService;
import com.froad.util.command.Command;
import com.froad.util.command.PayCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 卖家 client service  ActionSupport
 */
public class SellerTransActionSupport {
	private static Logger logger = Logger.getLogger(SellerTransActionSupport.class);
	private SellersService sellersService;
	private PayActionSupport payActionSupport;
	private TransActionSupport transActionSupport;
	
	/**
	  * 方法描述：计算金额
	  * @param: amount 金额  fftPointsRule 返积分比例
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2013-4-7 下午2:25:31
	  */
	private BigDecimal calculationOfPoints(BigDecimal amount,BigDecimal fftPointsRule) {
		return amount.multiply(fftPointsRule).divide(new BigDecimal(100),2,RoundingMode.DOWN);
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
	 * 
	  * 方法描述：下单
	  * @param: buyerPhone 用户支付手机  goodsName 商品名（事由）userBeCode操作员  transType订单类型  clientType客户端类型
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2013-4-19 上午10:55:28
	 */
	public Trans  createTran(String goodsName,String num,String price,String userBeCode,String buyerPhone,
			String transType,String clientType,Seller seller,Buyers buyer){
		
		
		BigDecimal totalAmount= new BigDecimal(num).multiply(new BigDecimal(price));//总金额
		
		SellerChannel sellerChannel = seller.getSellerChannelList().get(0);
		//返利积分
		String point = calculationOfPoints(totalAmount,new BigDecimal(sellerChannel.getSellerRule().getFftPointsRule())).toString();
		//支付金额
		String amount = calCarryTotalAmount(point).toString();
		
		Trans trans = new Trans();
		TransDetails tranDetail = new TransDetails();
		Goods goods = new Goods();
		
		trans.setTransType(transType);//必填
		trans.setSellerId(seller.getId().toString());//必填
		trans.setSellerChannelId(sellerChannel.getId().toString());//必填
		trans.setCurrency(Command.CURRENCY);
		trans.setCostpriceTotal(totalAmount.toString());//必填
		trans.setCurrencyValueAll(totalAmount.toString());//必填
		trans.setCurrencyValueRealAll(totalAmount.toString());//必填
		trans.setState(TranCommand.TRAN_PROCESSING);//必填
		trans.setClientType(clientType);//必填
		trans.setPayChannel(TranCommand.PAY_CHANNEL_PHONE);//必填
		trans.setPayMethod(TranCommand.CASH);//必填
		trans.setBelongUserBecode(userBeCode);
		trans.setPhone(buyerPhone);
		
		trans.setPointsAmountValue(amount);
		//卖家买积分所需实际金额
		trans.setPointsAmountRealValue(amount);
		//方付通手续费(提现手续费或卖家买积分手续费)
		trans.setFftFactorage("0");
		//分分通总实际积分数值，由规则计算出
		trans.setFftPointsValueRealAll(point);
		trans.setFftPointsValueAll(point);
		trans.setMerchantId(Integer.parseInt(seller.getMerchantId()));
		
		trans.setUserId(buyer.getUserId());
		
		//积分返利 设置买家信息
		if(TranCommand.POINTS_REBATE.equals(transType)) {
			trans.setBuyersId(buyer.getId().toString());
			if(buyer.getBuyerChannelList()!=null&&buyer.getBuyerChannelList().size()>0){
				trans.setBuyerChannelId(buyer.getBuyerChannelList().get(0).getId().toString());
			}else{
				logger.info("买家资金渠道为空，交易类型："+transType+"买家编号："+buyer.getId());
			}
		}
		
		
		tranDetail.setGoodsNumber(num);
		tranDetail.setCurrency(Command.CURRENCY);
		//原价总和
		tranDetail.setCostpriceTotal(totalAmount.toString());
		//货币值
		tranDetail.setCurrencyValue(totalAmount.toString());
		//实际货币值
		tranDetail.setCurrencyValueReal(totalAmount.toString());
		//分分通总积分值，所有交易明细累计
		tranDetail.setFftPointsValueAll(point);
		//分分通总实际积分数值，由规则计算出
		tranDetail.setFftPointsValueRealAll(point);
		tranDetail.setState(Command.STATE_START);
		
		goods.setMerchantId(seller.getMerchantId());
		goods.setState(Command.STATE_START);
		goods.setGoodsName(goodsName);
		
		tranDetail.setGoods(goods);
		
		GoodsGatherRack goodsGatherRack = new GoodsGatherRack();
		goodsGatherRack.setMerchantId(seller.getMerchantId());
		goodsGatherRack.setCashPricing(totalAmount.toString());
		goodsGatherRack.setGoods(goods);
		trans.setGoodsGatherRack(goodsGatherRack);
		
		trans.getTransDetailsList().add(tranDetail);
		
		List<Pay> payList= payActionSupport.makePay(trans);
		
		
		if(payList==null){
			logger.error("交易的pay记录组装失败");
			return null;
		}
		
		
		trans.getPayList().addAll(payList);
		
		return transActionSupport.addTrans(trans);
	}
	
	
	
//	private MerchantActionSupport merchantActionSupport;
	
	/*public Seller getSellerById(String sellerId){
		return sellersService.selectById(Integer.parseInt(sellerId));
	}*/
	/*
	public List<Seller> getSellerByUserId(String userId,List<String> sellerType){
		List<Seller> sellerList=null;
		sellerList=sellersService.getSellerByUserId(userId);
//		List<Seller> list  =new ArrayList<Seller>();
//		if(Assert.empty(sellerList))
//			return list;
//		for(Seller seller:sellerList){
//			for(String tt:sellerType){
//				if(tt.equals(seller.getSellerType())){
//					list.add(seller);
//					break;
//				}
//			}
//		}
		return sellerList;
	}*/
	
	public List<Integer> getSellerIdByUserId(String userId,List<String> sellerType){
		List<Seller> list= sellersService.getSellerByUserId(userId);;
		List<Integer> idList=new ArrayList<Integer>();
		for(String type:sellerType){
			for (int i = 0; i < list.size(); i++) {
				Seller seller = list.get(i);
				if(type.equals(seller.getSellerType())){
					idList.add(seller.getId());	
					break;
				}
			}			
		}
		return idList;
	}
/*
	public List getTopTranList(Integer sellerId, Integer topNum) {
		Trans queryCon=new Trans();
		queryCon.setSellerId(String.valueOf(sellerId));
		queryCon.setTransType(TransType.Trans_Points.getValue());
		queryCon.setPageSize(10);
//		queryCon.setOrderBy("createTime");
		queryCon.setOrderType(OrderType.DESC);
		Trans result=transActionSupport.getTransByPager(queryCon);
		if(result==null)
			return null;
		
		return result.getList();
	}
	*/
	/*
	
	public Cashdesk getCashdesk(String userId) {
		Cashdesk cashdesk = null;
		try {
//			Seller seller = getSellerByUserId(userId);
			Seller seller=null;
			if (seller == null)
				return cashdesk;
			cashdesk=cashdeskService.getCashdesk(seller);
		} catch (Exception e) {
			log.error("调用服务失败！", e);
		}
		return cashdesk;
	}*/
	
	
//	// 创建现金交易  ok now
//	public Trans createTran(Seller seller,
//			SellerChannel sellerDepositChannel,
//			 Buyers buyer, User userOfBuyer,
//			 BuyerChannel buyerPayChannel, List<TransDetails> detailList,
//			OrderVO orderVO,String clientType,String userBeCode) {
//		Trans tran =null;
//		try{
//			tran = this.createTran(seller, sellerDepositChannel, buyer,
//				buyerPayChannel, detailList, orderVO,clientType);
//			tran.setUserId(userOfBuyer.getUserID());
//			tran.setBelongUserBecode(userBeCode);
//		// 只有手机贴膜卡的，系统才会帮卖家把钱收到卖家的账户中去，所以非手机贴膜卡支付不用产生资金流，否则卖家账户上的金额与我们资金流转到账户的信息就不一致
//			transServiceInClient.countTansferInfoTran(tran, seller,
//					sellerDepositChannel, buyer, buyerPayChannel,userOfBuyer,null,
//					UseTime.COUNT_TRANS_CURRENCY);
//			if (!PayChannel.PAYTYPE_MPhoneCard.equals(orderVO.getPayChannel())) {
//				deletePayFromTransferTran(tran);
//			}
//			
////			if (PayChannel.PAYTYPE_MPhoneCard.equals(orderVO.getPayChannel())) {
////				if(orderVO.getIsPresentPoints().equals("0")){
////				transServiceInClient.countTansferInfoTran(tran, seller,
////						sellerDepositChannel, buyer, buyerPayChannel,
////						UseTime.COUNT_TRANS_CURRENCY);
////				}
////			} else {
////				
////				deletePayFromTransferTran(tran);
////			}
//	
//			log.info("计算完后的买家到卖家的资金流向交易信息：交易跟踪号：" + tran.getTrackNo()
//					+ "，买家应付给卖家总金额：" + tran.getCurrencyValueAll() 
//					+"，买家实际给卖家总金额：" + tran.getCurrencyValueRealAll()
//					+"卖家应赠送给买家的分分通积分额："+tran.getFftPointsValueAll()+ "，买家付款金额，"
//					+ tran.getCurrencyValueRealAll() + "该交易,其交易跟踪号："
//					+ tran.getTrackNo() + "，交易Id：" + tran.getId()
//					+ ",收款信息的流转信息条数为：" + tran.getPayList().size()
//					+ "，交易的状态：" + tran.getState());
//			
//		} catch (Exception e) {
//			log.error("生成交易失败！", e);
//			return  null;
//		}
//		Trans tranAfterSave = null;
//		try {
//			tranAfterSave = transActionSupport.addTrans(tran);
//
//			if (tranAfterSave != null) {
//				tran.setId(tranAfterSave.getId());
//				String  createTime=tranAfterSave.getCreateTime();
//				if(!Assert.empty(createTime))
//					createTime=createTime.replace("|", " ");
//				tranAfterSave.setCreateTime(createTime);
//			}
//		} catch (Exception e) {
//			log.error("调用webserver，tranService.addTrans(tran)，失败！", e);
//		}
//		return tranAfterSave;
//	}
	/*
	private void deletePayFromTransferTran(Trans tran){
		List<Pay> payList=tran.getPayList();
		if(Assert.empty(payList))
			return ;
		for(Pay pay:payList){
			if(RuleDetailType.PAY_Currency.getValue().equals(pay.getTypeDetail())){
				payList.remove(pay);
				break;
			}
		}
		Iterator it=payList.iterator();
		while(it.hasNext()){
			Pay tpay=(Pay) it.next();
			int step=Integer.valueOf(tpay.getStep());
			tpay.setStep(String.valueOf(step-1));
		}
	}*/
	
	
	/*public Trans createTran(Seller seller,
			SellerChannel sellerDepositChannel, Buyers buyer,
			BuyerChannel buyerPayChannel, List<TransDetails> detailList,
			OrderVO orderVO,String clientType) {

		String trackNo = orderVO.getTrackNo();
		Integer sellerDepositChannelId=sellerDepositChannel.getId();
		Integer buyerPayChannelId=buyerPayChannel==null?null:buyerPayChannel.getId();
		String sellerDepositChannelIdStr=String.valueOf(sellerDepositChannelId);
		String buyerPayChannelIdStr=buyerPayChannelId==null?null:String.valueOf(buyerPayChannelId);
		double currencyValueAll = updateGoodsDetailAndCountTotalPrice(
				detailList, sellerDepositChannelIdStr,
				buyerPayChannelIdStr, trackNo,orderVO.getGoodsGatherRack());
		Trans tran = new Trans();
		tran.setClientType(clientType);
		tran.setBuyersId(buyer.getId()==null?null:String.valueOf(buyer.getId()));
		tran.setSellerId(String.valueOf(seller.getId()));
		if (detailList != null && detailList.size() > 0){
			tran.setCurrency("RMB");
		}
		tran.setBuyerChannelId(buyerPayChannelIdStr);
		tran.setSellerChannelId(sellerDepositChannelIdStr);
		
		tran.setTransType(TransType.Trans_Points.getValue());
		tran.setPayMethod(PayMethod.Currency.getValue());
		tran.setCurrencyValueAll(String.valueOf(currencyValueAll));
		tran.setCostpriceTotal(String.valueOf(currencyValueAll));
		tran.setPayChannel(orderVO.getPayChannel());
		if (PayChannel.PAYTYPE_MPhoneCard.equals(orderVO.getPayChannel()))
			tran.setState(TransState.doing);
		else
			tran.setState(TransState.doing);
		tran.setTrackNo(trackNo);
		tran.getTransDetailsList().addAll(detailList);
		tran.setGoodsGatherRack(orderVO.getGoodsGatherRack());
		if(orderVO.getGoodsGatherRack()!=null)
			orderVO.getGoodsGatherRack().setSellerId(seller.getId());
		if(orderVO.getGoodsGatherRack()!=null && orderVO.getGoodsGatherRack().getGoods()!=null){
			orderVO.getGoodsGatherRack().getGoods().setMerchantId(seller.getMerchantId());
			orderVO.getGoodsGatherRack().getGoods().setState(State.AVAILIABLE.getValue());
		}
//		com.froad.client.merchant.Merchant merchant=merchantActionSupport.getMerchantByUserId(seller.getUserId());
//		Integer merchantId=null;
//		if(merchant!=null)
//			merchantId=merchant.getId();
		tran.setMerchantId(Integer.valueOf(seller.getMerchantId()));
		tran.setSellerType(seller.getSellerType());
		
		log.info("创建的新的交易信息:" + tran);
		return tran;

	}*/
	/*
	private double updateGoodsDetailAndCountTotalPrice(
			List<TransDetails> detailList, String sellerRuleId,
			String buyerRuleId, String trackNo,GoodsGatherRack goodsGatherRack) {
		double currency_value_all = 0.0;
		if (detailList == null || detailList.size() == 0)
			return currency_value_all;
		for (TransDetails tranDetail : detailList) {
			String currencyValue = goodsGatherRack.getCashPricing();
			goodsGatherRack.setState(State.AVAILIABLE.getValue());
			String num = tranDetail.getGoodsNumber();
			double currencyValueDouble = 0.0;

			// double numDouble=0.0;
			try {
				currencyValueDouble = Double.valueOf(currencyValue)
						* Double.valueOf(num);
			} catch (Exception e) {
				currencyValueDouble = 0.0;
			}
			currencyValueDouble=CommonUtil.scale2(currencyValueDouble,2);
			tranDetail.setCurrencyValue(String.valueOf(currencyValueDouble));
			tranDetail.setCurrencyValueReal(String.valueOf(currencyValueDouble));
			tranDetail.setState(Command.TRAN_WAIT_PAY);// 与交易的状态是相同的
//			tranDetail.getTranGoods().setTrackNo(trackNo);
			currency_value_all = currency_value_all + currencyValueDouble;
		}
		return currency_value_all;
	}*/
	/*
	public boolean payOfTrans(Trans tran) {
		boolean result=false;
		try{
			result=transActionSupport.pay(tran.getId());
		}catch(Exception e){
			log.error("调用支付交易的webserver超时", e);
		}
		return result;
	}*/
	
	
	
	/**
	  * 方法描述：是否是送积分的卖家
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	  * @time: 2013-1-7 下午05:01:57
	 
	public boolean isSellerSendPoints(String userId) {
		boolean result =false;
		if(Assert.empty(userId))
			return result;
		Cashdesk cashdesk = getCashdesk(userId);
		if(cashdesk==null)
			return result;
		List<SellerChannel> sellerDepositChannelList = cashdesk.getSellerDepositChannelList();
		if(Assert.empty(sellerDepositChannelList))
			return result;
		Map<String, Map> ruleCache=new HashMap();
		ruleCache=transActionSupport.getRulesFromCache();
		if(Assert.empty(ruleCache)||Assert.empty(ruleCache.get("rules-ruleId")))
			return result;
		
		SellerChannel sellerDepositChannel=sellerDepositChannelList.get(0);
		if(Assert.empty(sellerDepositChannel.getSellerRuleId()))
			return result;
		TransRule sellerRule=(TransRule) ruleCache.get("rules-ruleId").get(sellerDepositChannel.getSellerRuleId());
		if(sellerRule!=null && SellerRuleType.POINTS.getValue().equals(sellerRule.getRuleType()))
			result=true;
		return result;
	}
	*/
	
//	public Trans getTrans(Trans queryCon,String sellerId) {
//		Trans result=null;
//		if(queryCon==null||Assert.empty(sellerId))
//			return result;
//		queryCon.setSellerId(String.valueOf(sellerId));
//		queryCon.setTransType(TransType.Trans_Points.getValue());
//		queryCon.setPageSize(10);
////		queryCon.setOrderBy("createTime");
//		queryCon.setOrderType(OrderType.DESC);
//		try {
//			result = transActionSupport.getTransByPager(queryCon);
//		} catch (Exception e) {
//			// TODO: handle exception
//			log.error("调用查询交易的webserver失败", e);
//		}
//		return result;
//	}
	
	public SellersService getSellersService() {
		return sellersService;
	}
	public void setSellersService(SellersService sellersService) {
		this.sellersService = sellersService;
	}

	public PayActionSupport getPayActionSupport() {
		return payActionSupport;
	}

	public void setPayActionSupport(PayActionSupport payActionSupport) {
		this.payActionSupport = payActionSupport;
	}

	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}

}
