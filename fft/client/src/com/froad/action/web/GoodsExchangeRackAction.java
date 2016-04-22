package com.froad.action.web;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.froad.action.support.AdvertActionSupport;
import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.GoodsExchangeRackActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.PointCashRuleActionSupport;
import com.froad.action.support.PointObtainActionSupport;
import com.froad.action.support.TagActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.UserCertificationActionSupport;
import com.froad.action.web.tran.TransAddtionalInfoVo;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.advert.Advert;
import com.froad.client.authTicket.AuthTicket;
import com.froad.client.authTicket.AuthTicketService;
import com.froad.client.buyers.Buyers;
import com.froad.client.fundsChannel.FundsChannel;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.hfcz.Hfcz;
import com.froad.client.lottery.AppException_Exception;
import com.froad.client.lottery.Lottery;
import com.froad.client.point.Points;
import com.froad.client.point.PointsAccount;
import com.froad.client.tag.TagClassifyA;
import com.froad.client.tag.TagClassifyB;
import com.froad.client.tag.TagDistrictB;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.transRule.TransRule;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.CommonUtil;
import com.froad.util.command.ClientType;
import com.froad.util.command.GoodCategory;
import com.froad.util.command.PointsType;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

public class GoodsExchangeRackAction extends BaseActionSupport{
	private GoodsExchangeRack pager;
	private GoodsExchangeRackActionSupport goodsExchangeRackActionSupport;
	private TransActionSupport transActionSupport;
	private BuyersActionSupport buyersActionSupport;
	
	private AuthTicketService authTicketService;
	private TagActionSupport tagActionSupport;
	private List<TagDistrictB> allTagDistrictBList;//所有商圈
	private List<TagDistrictB> logoTagDistrictBList;//logo 处 商圈
	private List<TagClassifyB> allTagClassifyBList;//所有二级分类
	private List<TagClassifyA> allTagClassifyAList;//所有一级分类
	
	private TransDetails transDetails;
	private Trans trans;
	private TransAddtionalInfoVo transAddtionalInfoVo;
	private String errorMsg;
	private TransRule pointsExchCurrencyRule;
	private UserActionSupport userActionSupport;
	private UserCertificationActionSupport userCertificationActionSupport;
	private UserCertification userCertification;
	private PointObtainActionSupport pointObtainActionSupport;
	private PointActionSupport pointActionSupport;
	private PointsAccount pointsAccount;
	
	private AdvertActionSupport advertActionSupport;//广告
	private List<Advert> advertList;//广告
	private User user;
	private PointCashRuleActionSupport pointCashRuleActionSupport;
	private double cashFftPointsRatio;
	private double cashBankPointsRatio;
	private static Logger log=Logger.getLogger("GoodsExchangeRackAction");
	
	private String authId = null;
	
	//详情显示验证券
	private String authIdElse = null;
	private String securitiesNo = null;//券号
	public String index(){
		
		//查询返利模块广告
		Advert advert=new Advert();
		advert.setModule("2");
		advert.setType("3");
		advert.setState("30");
		advertList=advertActionSupport.getAdvertById(advert);
		
		//全部商圈
		allTagDistrictBList=tagActionSupport.findAllDistrictB();		
		//全部分类
		allTagClassifyAList = tagActionSupport.findAllClassifyA();
		allTagClassifyBList = tagActionSupport.findAllClassifyB();
		pager=new GoodsExchangeRack();
		pager.setPageSize(9);
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
		return "index";
	}
	
	public String indexByCategoryId(){
		
		//查询返利模块广告
		Advert advert=new Advert();
		advert.setModule("2");
		advert.setType("3");
		advert.setState("30");
		advertList=advertActionSupport.getAdvertById(advert);
		
		//全部商圈
		allTagDistrictBList=tagActionSupport.findAllDistrictB();		
		//全部分类
		allTagClassifyAList = tagActionSupport.findAllClassifyA();
		allTagClassifyBList = tagActionSupport.findAllClassifyB();
		
		pager.setPageSize(9);
		pager.setState("30");
		pager.setIsRack("1");
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
		return "index";
		
	}
	
	//Q币充值
	public String chargeMoney(){
		return "success";
		
	}
	//支付宝
	public String alipay(){
		return "success";
		
	}
	
	
	public String queryGoodsExchangeRackDetail(){
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRack(pager.getGoodsId());
		queryPointsAndPointsRatio();
		return Action.SUCCESS;
	}
	
	public void queryPointsAndPointsRatio(){
		
		this.updatePoints();
			
		BigDecimal cashFftPointsRatioBD=pointCashRuleActionSupport.queryPointCashRule(PointsType.FFT_POINTS);
		if(cashFftPointsRatioBD!=null)
			cashFftPointsRatio=cashFftPointsRatioBD.doubleValue();
		BigDecimal cashBankPointsRatioBD=pointCashRuleActionSupport.queryPointCashRule(PointsType.BANK_POINTS);
		if(cashBankPointsRatioBD!=null)
			cashBankPointsRatio=cashBankPointsRatioBD.doubleValue();
	}
	
	//查询积分提现商品 和  积分提现规则
	public String queryExchCurrencyInfosBeforeExch(){
		this.updatePoints();
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
		
		if(Assert.empty(pager.getList()) )
			return Action.SUCCESS;
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		user=userActionSupport.queryUserAllByUserID(userId);
		pointsAccount=pointActionSupport.queryfftPoints(user.getUsername());
		List<FundsChannel> allFundsChannels=transActionSupport.queryFundsChannel(new FundsChannel());
		
		userCertification=userCertificationActionSupport.getUserCertification(userId,String.valueOf(allFundsChannels.get(0).getId()));
		pointsExchCurrencyRule=goodsExchangeRackActionSupport.getExchCurrencyRule();
		
		return Action.SUCCESS;
	}
	
	private void queryExchCurrencyInfosBeforeExch2(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		user=userActionSupport.queryUserAllByUserID(userId);
		pointsAccount=pointActionSupport.queryfftPoints(user.getUsername());
		List<FundsChannel> allFundsChannels=transActionSupport.queryFundsChannel(new FundsChannel());
		
		userCertification=userCertificationActionSupport.getUserCertification(userId,String.valueOf(allFundsChannels.get(0).getId()));
		pointsExchCurrencyRule=goodsExchangeRackActionSupport.getExchCurrencyRule();
	}
	
	//查询积分兑换话费的商品 和  话费的实际价格
	public String queryExchPhoneInfosBeforeExch(){
		this.updatePoints();
		
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
//		List<Object> goodsExchangeRacks=pager.getList();
//		if(Assert.empty(goodsExchangeRacks) )
//			return Action.SUCCESS;
//		Iterator it=goodsExchangeRacks.iterator();
//		while(it.hasNext()){
//			GoodsExchangeRack goodsExchangeRack=(GoodsExchangeRack) it.next();
//		}
//		
		queryPointsAndPointsRatio();
		return Action.SUCCESS;
	}
	
	//查询积分兑换彩票的商品 和  彩票的相关信息
	public String queryExchLotteryInfosBeforeExch(){
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
		if(Assert.empty(pager.getList()) ){
			return Action.SUCCESS;
		}
		getLotteryInfoBeforeOrder();
		queryPointsAndPointsRatio();
		return Action.SUCCESS;
	}
	
	
	public String queryExchangeByCategory(){
		pager.setPageSize(9);
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
		
//		if(!Assert.empty(pager.getList()) ){
//			GoodsExchangeRack one=(GoodsExchangeRack)pager.getList().get(0);
//		
//			pointsAccount=pointActionSupport.queryfftPoints("lance");
//			List<FundsChannel> allFundsChannels=transActionSupport.queryFundsChannel(new FundsChannel());
//			String userId="dc4fd752-91ef-4b23-8fa5-0ccff7b8435c";
//			userCertification=userCertificationActionSupport.getUserCertification(userId,String.valueOf(allFundsChannels.get(0).getId()));
//			if(GoodCategory.Goods_Category_Lottery.getValue().equals(one.getGoods().getGoodsCategory().getName())){
//				getLotteryInfoBeforeOrder();
//			}
//		}
		return Action.SUCCESS;
	}
	
	private void getLotteryInfoBeforeOrder(){
		String lotteryNo="FC_SSQ";
		Lottery lottery=pointObtainActionSupport.queryLotteryPeridListNow(lotteryNo);
		if(transAddtionalInfoVo==null)
			transAddtionalInfoVo=new TransAddtionalInfoVo();
		transAddtionalInfoVo.setLottery(lottery);
	}
	
	
	
	//验证订单信息
	private boolean checkTranDetail(TransDetails tranDetaill,Trans trans){
		boolean result = false;
		if(transDetails.getGoodsRackId()==null){
			errorMsg="交易品为空！请选择要交易品";
			return result;
		}
		pager=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(String.valueOf(transDetails.getGoodsRackId()));
		
		if(pager==null){
			errorMsg="您选择的交易品有误，系统不存在这个交易品。";
			return result;
		}
		if(pager.getGoods().getMerchant()==null ||pager.getSeller()==null){
			errorMsg="这个交易品没有商户，或该商户没有卖家。";
			return result;
		}
		
		if(Assert.empty(transDetails.getGoodsNumber())){
			errorMsg="您未选择交易品的数量，请输入交易品的数量。";
			return result;
		}else if(!Assert.isIntOfGreaterEZero(transDetails.getGoodsNumber())){
			errorMsg="交易品的数量不是数字！";
			return result;
		}
		
		if(GoodCategory.Points_Exch_Currency.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
			String pointsStr=transDetails.getGoodsNumber();
			if((Integer.parseInt(pointsStr))%10!=0){
				errorMsg="提现积分必须是10的倍数！";
				return result;
			}
		}
		
		if(Assert.empty(trans.getPayMethod())){
			errorMsg="您未选择支付方式。";
			return result;
		}
		
		result=true;
		return result;
	}
	
	
	private boolean checkUser(User user){
		boolean result = false;
		if(user==null)
			errorMsg="您还没有登录！";
		else
			result = true;
		return result;
	}
	
	private boolean checkUserCertification(TransDetails transDetails,Trans trans,Buyers buyer){
		boolean result = false;
		Map<String,Object> session=ActionContext.getContext().getSession();
		
		String userId=(String)session.get("userId");
//		String userId="dc4fd752-91ef-4b23-8fa5-0ccff7b8435c";
		String channelId=trans.getChannelId();
		if(Assert.empty(channelId)){
			if(buyer==null){
				errorMsg="您还没有进行账户绑定，请去认证！";
				return result;
			}
			channelId=buyer.getBuyerChannelList().get(0).getChannelId();
		}
		if(Assert.empty(channelId)){
			errorMsg="您还没有进行账户绑定，请去认证！";
			return result;
		}
		userCertification=userCertificationActionSupport.getUserCertification(userId,channelId);
		if(userCertification==null)
			errorMsg="您还没有进行账户绑定，请去认证！";
		else
			result=true;
		return result;
	}
	
	private boolean checkBuyer(Buyers buyer){
		boolean result = false;
		if(buyer==null)
			errorMsg="您不是买家！";
		else
			result = true;
		return result;
	}
	
	private boolean checkBuyerChannelAccountInfo(TransDetails transDetails,Trans trans,Buyers buyer){
		boolean result = false;
		if(buyer!=null && userCertification.getAccountName().equals(buyer.getBuyerChannelList().get(0).getAccountName()) && userCertification.getAccountNo().equals(buyer.getBuyerChannelList().get(0).getAccountNumber()))
			errorMsg="您的买家账户信息与认证通过的有效账户信息不一致！";
		else
			result=true;
		return result;
	}
	
//	private boolean check(TransDetails transDetails,Trans trans,User user,Buyers buyer,TransAddtionalInfoVo transAddtionalInfoVo){
//		boolean result = false;
//		result=checkTranDetail(transDetails, trans);
//		if(!result)
//			return result;
//		result=checkUser(user);
//		if(!result)
//			return result;
//		result=checkUserCertification(transDetails, trans,buyer);
//		if(!result)
//			return result;
//		if(!GoodCategory.Points_Exch_Currency.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
//			result=checkBuyer(buyer);
//			if(!result)
//				return result;
//			result=checkBuyerChannelAccountInfo(transDetails, trans,buyer);
//			if(!result)
//				return result;
//		}
//		
//		if(GoodCategory.Points_Exch_Currency.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
//			result=checkFFTPoints( transDetails, trans, user, buyer);
//			if(!result)
//				return result;
//		}
//		
//		if(GoodCategory.Goods_Category_Recharge_Phone.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
//			result=checkHFCZ( transDetails, trans, user, buyer, transAddtionalInfoVo);
//			if(!result)
//				return result;
//		}
//		
//		if(GoodCategory.Goods_Category_Lottery.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
//			result=checkLottery(transDetails, trans, user, buyer, transAddtionalInfoVo);
//			if(!result)
//				return result;
//		}
//		
//		result=checkPayMethod( transDetails, trans, user, buyer, transAddtionalInfoVo);
//
////		积分兑换不需要验证资金渠道,因为积分兑换是方付通收款,方付通资金机构都会有账户,即都能够收到客户的钱
////		if(!GoodCategory.Points_Exch_Currency.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
////			result=checkPayChannel( transDetails, trans, user, buyer);
////			if(!result)
////				return result;
////		}
//		return result;
//	}
	
	
//	private boolean checkPayMethod(TransDetails transDetails,Trans trans,User user,Buyers buyer,TransAddtionalInfoVo transAddtionalInfoVo){
//		boolean result = false;
//		double fftPoints=0;
//		double bankPoints=0;
//
//		Map<String,Object> session=ActionContext.getContext().getSession();
//		String userId=(String)session.get("userId");
//		Map<String,PointsAccount> pointsTypePointsAccountMap =(Map<String,PointsAccount>)session.get(SessionKey.POINTS_ACCOUNT_MAP);
//		
//		if(PayMethod.FFT_Points.getValue().equals(trans.getPayMethod()) ){
//			if(Assert.empty(pointsTypePointsAccountMap) ||pointsTypePointsAccountMap.get("FFTPlatform")==null){
//				errorMsg=" 没有分分通积分账户 ";
//				return result;
//			}
//			if(Assert.empty(pager.getFftPointPricing())){
//				errorMsg=" 该交易品没有分分通积分定价 ";
//				return result;
//			}
//			if(Double.parseDouble(pointsTypePointsAccountMap.get("FFTPlatform").getPoints())<Double.parseDouble(pager.getFftPointPricing())){
//				errorMsg=" 您的分分通积分不够 ";
//				return result;
//			}
//		}
//		if(PayMethod.Bank_Points.getValue().equals(trans.getPayMethod())){
//			if(Assert.empty(pointsTypePointsAccountMap) || pointsTypePointsAccountMap.get("ZHBank")==null){
//				errorMsg=" 没有珠海积分账户 ";
//				return result;
//			}
//			if(Assert.empty(pager.getBankPointPricing())){
//				errorMsg=" 该交易品没有珠海积分定价 ";
//				return result;
//			}
//			if(Double.parseDouble(pointsTypePointsAccountMap.get("ZHBank").getPoints())<Double.parseDouble(pager.getBankPointPricing())){
//				errorMsg=" 您的金海洋/信通卡积分不够 ";
//				return result;
//			}
//		}
//		if(PayMethod.FFT_Points_Currency.getValue().equals(trans.getPayMethod()) ){
//			if(Assert.empty(pointsTypePointsAccountMap) ||pointsTypePointsAccountMap.get("FFTPlatform")==null){
//				errorMsg=" 没有分分通积分账户 ";
//				return result;
//			}
//			if(Assert.empty(pager.getFftpointCashPricing())){
//				errorMsg=" 该交易品Id： "+transDetails.getGoodsRackId()+",没有分分通积分和现金固定定价 ";
//				return result;
//			}
//			String fftpointsAndCurrency=pager.getFftpointCashPricing();
//			fftpointsAndCurrency=fftpointsAndCurrency.replace("|", ",");
//			String[] fftpointsAndCurrencyArr=fftpointsAndCurrency.split(",");
//			
//			if(fftpointsAndCurrencyArr!=null&&fftpointsAndCurrencyArr.length==2){
//				fftPoints=Double.valueOf(fftpointsAndCurrencyArr[0]);
//				fftPoints=fftPoints*Integer.valueOf(transDetails.getGoodsNumber());
//			}else{
//				errorMsg=" 团购交易品Id： "+transDetails.getGoodsRackId()+",分分通积分和现金固定定价有误！";
//				return result;
//			}
//			if(Double.parseDouble(pointsTypePointsAccountMap.get("FFTPlatform").getPoints())<fftPoints){
//				errorMsg=" 您的分分通积分不够 ";
//				return result;
//			}
//		}
//		if(PayMethod.Bank_Points_Currency.getValue().equals(trans.getPayMethod())){
//			if(Assert.empty(pointsTypePointsAccountMap) || pointsTypePointsAccountMap.get("ZHBank")==null){
//				errorMsg=" 没有珠海积分账户 ";
//				return result;
//			}
//			if(Assert.empty(pager.getBankpointCashPricing())){
//				errorMsg=" 该交易品Id： "+transDetails.getGoodsRackId()+",没有金海洋/信通卡积分和现金固定定价 ";
//				return result;
//			}
//			
//			String bankpointsAndCurrency=pager.getBankpointCashPricing();
//			bankpointsAndCurrency=bankpointsAndCurrency.replace("|", ",");
//			
//			String[] bankpointsAndCurrencyArr=bankpointsAndCurrency.split(",");
//			if(bankpointsAndCurrencyArr!=null&&bankpointsAndCurrencyArr.length==2){
//				bankPoints=Double.valueOf(bankpointsAndCurrencyArr[0]);
//			}else{
//				errorMsg=" 团购交易品Id： "+transDetails.getGoodsRackId()+",金海洋/信通卡积分和现金固定定价有误！";
//				return result;
//			}
//			bankPoints=bankPoints*Integer.valueOf(transDetails.getGoodsNumber());
//			if(Double.parseDouble(pointsTypePointsAccountMap.get("ZHBank").getPoints())<bankPoints){
//				errorMsg=" 您的金海洋/信通卡积分不够 ";
//				return result;
//			}
//		}
//		
//		if(PayMethod.FFT_Points_Currency_Scope.getValue().equals(trans.getPayMethod()) ){
//			if(Assert.empty(pointsTypePointsAccountMap) ||pointsTypePointsAccountMap.get("FFTPlatform")==null){
//				errorMsg=" 没有分分通积分账户 ";
//				return result;
//			}
//			if(Assert.empty(pager.getFftpointcashRatioPricing())){
//				errorMsg=" 该交易品Id： "+transDetails.getGoodsRackId()+",没有分分通积分和现金范围定价 ";
//				return result;
//			}
//			String fftpointsMinMax=pager.getFftpointcashRatioPricing();
//			fftpointsMinMax=fftpointsMinMax.replace("|", ",");
//			String[] fftpointsMinMaxArr=fftpointsMinMax.split(",");
//			
//			double fftPointsMin=Double.valueOf(fftpointsMinMaxArr[0]);
//			String fftPointsMinStr=pointCashRuleActionSupport.getPointByCash(String.valueOf(fftPointsMin*0.01*Double.parseDouble(pager.getCashPricing())),PointsType.FFT_POINTS);
//			fftPointsMin= CommonUtil.scale2(Double.parseDouble(fftPointsMinStr),0);
//			
//			double fftPointsMax=Double.valueOf(fftpointsMinMaxArr[1]);
//			String fftPointsMaxStr=pointCashRuleActionSupport.getPointByCash(String.valueOf(fftPointsMax*0.01*Double.parseDouble(pager.getCashPricing())),PointsType.FFT_POINTS);
//			fftPointsMax= CommonUtil.scale2(Double.parseDouble(fftPointsMaxStr),0);
//			
//			fftPoints=Double.parseDouble(transDetails.getFftPointsValueRealAll());
//			if(fftPoints>fftPointsMax){
//				errorMsg=" 您使用的分分通积分大于在定价的积分范围的最大值 ";
//				return result;
//			}
//			if(fftPoints<fftPointsMin){
//				errorMsg=" 您使用的分分通积分小于在定价的积分范围的最小值 ";
//				return result;
//			}
//			if(Double.parseDouble(pointsTypePointsAccountMap.get("FFTPlatform").getPoints())<fftPoints){
//				errorMsg=" 您的分分通积分不够 ";
//				return result;
//			}
//		}
//		if(PayMethod.Bank_Points_Currency_Scope.getValue().equals(trans.getPayMethod())){
//			if(Assert.empty(pointsTypePointsAccountMap) || pointsTypePointsAccountMap.get("ZHBank")==null){
//				errorMsg=" 没有珠海积分账户 ";
//				return result;
//			}
//			if(Assert.empty(pager.getBankpointcashRatioPricing())){
//				errorMsg=" 该交易品没有珠海积分定价 ";
//				return result;
//			}
//			
//			String bankpointsMinMax=pager.getBankpointcashRatioPricing();
//			bankpointsMinMax=bankpointsMinMax.replace("|", ",");
//			String[] bankpointsMinMaxArr=bankpointsMinMax.split(",");
//			
//			double bankPointsMin=Double.valueOf(bankpointsMinMaxArr[0]);
//			String bankPointsMinStr=pointCashRuleActionSupport.getPointByCash(String.valueOf(bankPointsMin*0.01*Double.parseDouble(pager.getCashPricing())),PointsType.BANK_POINTS);
//			bankPointsMin=CommonUtil.scale2(Double.parseDouble(bankPointsMinStr),0);
//			
//			double bankPointsMax=Double.valueOf(bankpointsMinMaxArr[1]);
//			String bankPointsMaxStr=pointCashRuleActionSupport.getPointByCash(String.valueOf(bankPointsMax*0.01*Double.parseDouble(pager.getCashPricing())),PointsType.BANK_POINTS);
//			bankPointsMax= CommonUtil.scale2(Double.parseDouble(bankPointsMaxStr),0);
//			
//			bankPoints=Double.parseDouble(transDetails.getBankPointsValueRealAll());
//			if(bankPoints>bankPointsMax){
//				errorMsg=" 您使用的金海洋/信通卡积分大于在定价的积分范围的最大值 ";
//				return result;
//			}
//			if(bankPoints<bankPointsMin){
//				errorMsg=" 您使用的金海洋/信通卡积分小于在定价的积分范围的最小值 ";
//				return result;
//			}
//			if(Double.parseDouble(pointsTypePointsAccountMap.get("ZHBank").getPoints())<bankPoints){
//				errorMsg=" 您的金海洋/信通卡积分不够 ";
//				return result;
//			}
//		}
//		result=true;
//		return result;
//	}	
	
	private boolean checkLottery(TransDetails transDetails,Trans trans,User user,Buyers buyer,TransAddtionalInfoVo transAddtionalInfoVo){
		boolean result = false;
		String redBall = transAddtionalInfoVo.getLotteryContentRedBall();
		String blueBall = transAddtionalInfoVo.getLotteryContentBlueBall();
		System.out.println(redBall.split(",").length);
		System.out.println(blueBall.split(",").length);
		if(Assert.empty(redBall) || redBall.split(",").length != 6){
			errorMsg="请选择红球，必须选择 6 个红球！ ";
			return result;
		}
		if(Assert.empty(blueBall) || blueBall.split(",").length != 1){
			errorMsg="请选择蓝球，必须选择 1 个蓝球！ ";
			return result;
		}
		
		result=true;
		return result;
		
	}
	
	private boolean checkHFCZ(TransDetails transDetails,Trans trans,User user,Buyers buyer,TransAddtionalInfoVo transAddtionalInfoVo){
		boolean result = false;
		String phone=transAddtionalInfoVo.getPhone();
		String confirmedPhone=transAddtionalInfoVo.getConfirmedPhone();
		if(Assert.empty(phone)){
			errorMsg="充值的手机号为空！ ";
			return result;
		}
		if(Assert.empty(confirmedPhone)){
			errorMsg="确认充值的手机号为空！ ";
			return result;
		}	
		if(!phone.equals(confirmedPhone)){
			errorMsg="充值的手机号不一致！ ";
			return result;
		}	
			
		String originalPriceStr=pager.getCashPricing();
		if(Assert.empty(originalPriceStr)){
			errorMsg="充值的商品的面值为空";
			return result;
		}
		BigDecimal originalPrice=new BigDecimal(originalPriceStr);
		Hfcz hfcz=null;
		try {
			hfcz = pointObtainActionSupport.checkParaCZNo(phone, originalPrice);
		} catch (com.froad.client.hfcz.AppException_Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			log.error("通过webservice获取到的手机充值信息失败！",e);
		}
		if(hfcz==null){
			errorMsg="您的手机因归属地不同不能进行充值 ";
			return result;
		}else{
			transAddtionalInfoVo.setHfcz(hfcz);
			if(hfcz.getMoney()==null){
				errorMsg="系统有误，请重试 ";
				log.error("通过webservice获取到的手机充值信息为：充值金额为："+hfcz.getMoney());
				return result;
			}
			pager.setRemark(hfcz.getMoney().toString());
		}
		result=true;
		return result;
	}
	
	private boolean checkFFTPoints(TransDetails transDetails,Trans trans,User user,Buyers buyer){
		boolean result = false;
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		Map<String,PointsAccount> pointsTypePointsAccountMap =(Map<String,PointsAccount>)session.get(SessionKey.POINTS_ACCOUNT_MAP);
		if(!Assert.empty(pointsTypePointsAccountMap))
			pointsAccount=pointsTypePointsAccountMap.get("FFTPlatform");
		if(pointsAccount==null){
			errorMsg=" 没有分分通积分账户 ";
			return result;
		}
		String pointsStr=pointsAccount.getPoints();
		if(Assert.empty(pointsStr)){
			errorMsg=" 没有分分通积分 ";
			return result;
		}
		int points=Integer.parseInt(pointsStr);
		String userWithDrawPointsStr=transDetails.getGoodsNumber();
		int userWithDrawPoints=Integer.parseInt(userWithDrawPointsStr);
		if(points<userWithDrawPoints){
			errorMsg=" 分分通积分不够 ";
			return result;
		}
		result = true;
		return result;
	}
	
	private void updatePoints(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		Boolean changedPoints=false;
		changedPoints=(Boolean) session.get("changedPoints");
		String userId=(String)session.get("userId");
		if(Assert.empty(userId))
			return ;
		if(changedPoints==null ||changedPoints){
			Map<String,PointsAccount> pointsTypePointsAccountMap =null;
			pointsTypePointsAccountMap=pointActionSupport.queryBankAndFftPointsByUserId(userId);
			if(pointsTypePointsAccountMap!=null){
				session.put(SessionKey.POINTS_ACCOUNT_MAP,pointsTypePointsAccountMap);
			}
		}
	}
	
//	//积分兑换商品，下单
//	public String exchangeProduct(){
//		updatePoints();
//		Map<String,Object> session=ActionContext.getContext().getSession();
//		String userId=(String)session.get("userId");
////		String userId="dc4fd752-91ef-4b23-8fa5-0ccff7b8435c";
//		
//		
//		Trans param=trans;
//		user=userActionSupport.queryUserAllByUserID(userId);
//		
//		Map<String,PointsAccount> pointsTypePointsAccountMap =(Map<String,PointsAccount>)session.get(SessionKey.POINTS_ACCOUNT_MAP);
//		if(!Assert.empty(pointsTypePointsAccountMap)){
//			if(pointsTypePointsAccountMap.get("FFTPlatform")!=null)
//				trans.setFftPointsAccount(pointsTypePointsAccountMap.get("FFTPlatform").getAccountId());
//			if(pointsTypePointsAccountMap.get("ZHBank")!=null)
//				trans.setBankPointsAccount(pointsTypePointsAccountMap.get("ZHBank").getAccountId());
//		}
//		
//		Buyers buyer=buyersActionSupport.getBuyerByUserId(userId);
//		
//		boolean result = check(transDetails,trans,user,buyer,transAddtionalInfoVo);
//		if(!result){
//			if(!Assert.empty(pager.getGoodsCategoryId())){
//				pager.setPageSize(100);
//				pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
//				
//				if(GoodCategory.Points_Exch_Currency.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
//					queryExchCurrencyInfosBeforeExch2();
//				}
//				
//				if(GoodCategory.Goods_Category_Recharge_Phone.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
//				
//				}
//				if(GoodCategory.Goods_Category_Lottery.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
//					getLotteryInfoBeforeOrder();
//				}
//				
//				
//			}else if(!Assert.empty(transDetails.getGoodsRackId())){
//				pager=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(String.valueOf(transDetails.getGoodsRackId()));
//			}
//			log.error("userName :"+user.getUsername()+" 下单 验证失败！其失败原因为："+errorMsg);
//			return "error";
//		}
//		trans=goodsExchangeRackActionSupport.createPointsExchProductTrans(pager,trans, transDetails,  buyer,user,userCertification, transAddtionalInfoVo,ClientType.ClientType_PC);
//		if(trans==null){
//			errorMsg="下单失败！";
//			trans=param;
//			if(!Assert.empty(pager.getGoodsCategoryId())){
//				pager.setPageSize(100);
//				pager=goodsExchangeRackActionSupport.getGoodsExchangeRacks(pager);
//				if(GoodCategory.Points_Exch_Currency.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
//					queryExchCurrencyInfosBeforeExch2();
//				}
//				
//				if(GoodCategory.Goods_Category_Recharge_Phone.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
//				
//				}
//				if(GoodCategory.Goods_Category_Lottery.getValue().equals(pager.getGoods().getGoodsCategory().getName())){
//					getLotteryInfoBeforeOrder();
//				}
//			}else if(!Assert.empty(transDetails.getGoodsRackId())){
//				pager=goodsExchangeRackActionSupport.getGoodsExchangeRacksById(String.valueOf(transDetails.getGoodsRackId()));
//			}
//			return "error";
//		}
//		return "success";
//	}
	
	//下推积分兑换交易   --即收款
//	public String pay(){
////		check(transDetails,trans);
//		log.info("================交易ID为:"+trans.getId()+",开始支付===============");
//		boolean result=transActionSupport.pay(trans.getId());
//		log.info("================交易ID为:"+trans.getId()+",调用webserver支付返回的结果为:"+result+"===============");
//		if(!result){
//			errorMsg="支付失败！";
//		}else{
//			Map<String,Object> session=ActionContext.getContext().getSession();
//			session.put("changedPoints", true);
//			this.updatePoints();
//		}
//		return result?"success":"error";
//	}
	
	
	/**
	 * 查询积分兑换交易列表
	 * @return String
	 */
	public String exchangeTransList(){
		//查询积分兑换交易列表
		return "success";
	}
	
	/**
	 * 查询积分提现交易列表
	 * @return String
	 */
	public String drawCashTransList(){
		//查询积分提现交易列表
		return "success";
	}
	
	/**
	 * 查询交易详情
	 * @return
	 */
	
	//TODO:彩票状态查询
	public String getExchangeTranDetailInfo(){
		if(trans == null || Assert.empty(trans.getId())){
			trans = new Trans();
			return "success";
		}
		trans = transActionSupport.getTransById(trans.getId());	
//		if(authId != null && authId.length() > 0){
//		}
		if(authIdElse != null && authIdElse.length() > 0){
			AuthTicket aticket = new AuthTicket();
			aticket = authTicketService.getAuthTicketById(Integer.parseInt(authIdElse));
			securitiesNo = aticket!=null || !Assert.empty(aticket.getSecuritiesNo())?aticket.getSecuritiesNo():"";
			trans.setConsumeTime(aticket==null || Assert.empty(aticket.getConsumeTime())?"":aticket.getConsumeTime());
			trans.setIsConsume(aticket==null || Assert.empty(aticket.getIsConsume())?"":aticket.getIsConsume());
			authIdElse = null;
		}
		//兑换交易详情根据数量计算单价,页面只显示单价
		String num = trans.getTransDetailsList()!=null && trans.getTransDetailsList().size()>0?trans.getTransDetailsList().get(0).getGoodsNumber():"0";
		BigDecimal goodsNum = new BigDecimal(num);
		//实体商品（目前只有实体商品会发送消费券）
		if(TranCommand.TRAN_SUCCESS.equals(trans.getState()) && (Assert.empty(trans.getVirtualType()) || (!Assert.empty(trans.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(trans.getVirtualType()))) 
				&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
			if(!Assert.empty(trans.getCostpriceTotal()) && !"0".equals(trans.getCostpriceTotal())){
				BigDecimal costpriceTotal=new BigDecimal(trans.getCostpriceTotal());					
				costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
				trans.setCostpriceTotal(costpriceTotal.toString());
			}
			if(!Assert.empty(trans.getBankPointsValueRealAll()) && !"0".equals(trans.getBankPointsValueRealAll())){
				BigDecimal bankPoints=new BigDecimal(trans.getBankPointsValueRealAll());					
				bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
				trans.setBankPointsValueRealAll(bankPoints.toString());
			}
			if(!Assert.empty(trans.getFftPointsValueRealAll()) && !"0".equals(trans.getFftPointsValueRealAll())){
				BigDecimal fftPoints=new BigDecimal(trans.getFftPointsValueRealAll());					
				fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
				trans.setFftPointsValueRealAll(fftPoints.toString());
			}
			if(!Assert.empty(trans.getCurrencyValueRealAll()) && !"0".equals(trans.getCurrencyValueRealAll())){
				BigDecimal currencyValue=new BigDecimal(trans.getCurrencyValueRealAll());					
				currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
				trans.setCurrencyValueRealAll(currencyValue.toString());
			}
		}
		
		if(trans == null){
			trans = new Trans();
		}
//		if(trans.getTransDetailsList()!=null && trans.getTransDetailsList().size()>0){
//			for(TransDetails detalTran:trans.getTransDetailsList()){
//				if("100001005".equals(detalTran.getGoods().getGoodsCategoryId())){//彩票:100001005
//					
//				}
//				if("100001006".equals(detalTran.getGoods().getGoodsCategoryId())){//话费：100001006
//					
//				}
//				
//				GoodsExchangeRack ger = (GoodsExchangeRack)detalTran.getBaseGoods();
//				
//			}
//		}
		return "success";
	}
	
	public GoodsExchangeRack getPager() {
		return pager;
	}

	public void setPager(GoodsExchangeRack pager) {
		this.pager = pager;
	}

	public GoodsExchangeRackActionSupport getGoodsExchangeRackActionSupport() {
		return goodsExchangeRackActionSupport;
	}

	public void setGoodsExchangeRackActionSupport(
			GoodsExchangeRackActionSupport goodsExchangeRackActionSupport) {
		this.goodsExchangeRackActionSupport = goodsExchangeRackActionSupport;
	}

	public TagActionSupport getTagActionSupport() {
		return tagActionSupport;
	}

	public void setTagActionSupport(TagActionSupport tagActionSupport) {
		this.tagActionSupport = tagActionSupport;
	}

	public List<TagDistrictB> getAllTagDistrictBList() {
		return allTagDistrictBList;
	}

	public void setAllTagDistrictBList(List<TagDistrictB> allTagDistrictBList) {
		this.allTagDistrictBList = allTagDistrictBList;
	}

	public List<TagDistrictB> getLogoTagDistrictBList() {
		return logoTagDistrictBList;
	}

	public void setLogoTagDistrictBList(List<TagDistrictB> logoTagDistrictBList) {
		this.logoTagDistrictBList = logoTagDistrictBList;
	}

	public List<TagClassifyB> getAllTagClassifyBList() {
		return allTagClassifyBList;
	}

	public void setAllTagClassifyBList(List<TagClassifyB> allTagClassifyBList) {
		this.allTagClassifyBList = allTagClassifyBList;
	}

	public List<TagClassifyA> getAllTagClassifyAList() {
		return allTagClassifyAList;
	}

	public void setAllTagClassifyAList(List<TagClassifyA> allTagClassifyAList) {
		this.allTagClassifyAList = allTagClassifyAList;
	}

	public TransDetails getTransDetails() {
		return transDetails;
	}

	public void setTransDetails(TransDetails transDetails) {
		this.transDetails = transDetails;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}

	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	}

	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
	}

	public TransAddtionalInfoVo getTransAddtionalInfoVo() {
		return transAddtionalInfoVo;
	}

	public void setTransAddtionalInfoVo(TransAddtionalInfoVo transAddtionalInfoVo) {
		this.transAddtionalInfoVo = transAddtionalInfoVo;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public TransRule getPointsExchCurrencyRule() {
		return pointsExchCurrencyRule;
	}

	public void setPointsExchCurrencyRule(TransRule pointsExchCurrencyRule) {
		this.pointsExchCurrencyRule = pointsExchCurrencyRule;
	}

	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}

	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}

	public UserCertificationActionSupport getUserCertificationActionSupport() {
		return userCertificationActionSupport;
	}

	public void setUserCertificationActionSupport(
			UserCertificationActionSupport userCertificationActionSupport) {
		this.userCertificationActionSupport = userCertificationActionSupport;
	}

	public UserCertification getUserCertification() {
		return userCertification;
	}

	public void setUserCertification(UserCertification userCertification) {
		this.userCertification = userCertification;
	}

	

	public PointObtainActionSupport getPointObtainActionSupport() {
		return pointObtainActionSupport;
	}

	public void setPointObtainActionSupport(
			PointObtainActionSupport pointObtainActionSupport) {
		this.pointObtainActionSupport = pointObtainActionSupport;
	}

	public PointActionSupport getPointActionSupport() {
		return pointActionSupport;
	}

	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}

	public PointsAccount getPointsAccount() {
		return pointsAccount;
	}

	public void setPointsAccount(PointsAccount pointsAccount) {
		this.pointsAccount = pointsAccount;
	}

	public AdvertActionSupport getAdvertActionSupport() {
		return advertActionSupport;
	}

	public void setAdvertActionSupport(AdvertActionSupport advertActionSupport) {
		this.advertActionSupport = advertActionSupport;
	}
	
	public List<Advert> getAdvertList() {
		return advertList;
	}

	public void setAdvertList(List<Advert> advertList) {
		this.advertList = advertList;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PointCashRuleActionSupport getPointCashRuleActionSupport() {
		return pointCashRuleActionSupport;
	}

	public void setPointCashRuleActionSupport(
			PointCashRuleActionSupport pointCashRuleActionSupport) {
		this.pointCashRuleActionSupport = pointCashRuleActionSupport;
	}

	public double getCashFftPointsRatio() {
		return cashFftPointsRatio;
	}

	public void setCashFftPointsRatio(double cashFftPointsRatio) {
		this.cashFftPointsRatio = cashFftPointsRatio;
	}

	public double getCashBankPointsRatio() {
		return cashBankPointsRatio;
	}

	public void setCashBankPointsRatio(double cashBankPointsRatio) {
		this.cashBankPointsRatio = cashBankPointsRatio;
	}

	public AuthTicketService getAuthTicketService() {
		return authTicketService;
	}

	public void setAuthTicketService(AuthTicketService authTicketService) {
		this.authTicketService = authTicketService;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getAuthIdElse() {
		return authIdElse;
	}

	public void setAuthIdElse(String authIdElse) {
		this.authIdElse = authIdElse;
	}

	public String getSecuritiesNo() {
		return securitiesNo;
	}

	public void setSecuritiesNo(String securitiesNo) {
		this.securitiesNo = securitiesNo;
	}
	
}
