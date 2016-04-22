package com.froad.action.trans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.froad.AppException.AppException;
import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.GoodsGroupRackActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantTrafficMAPActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.SellersActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.trans.PayActionSupport;
import com.froad.action.support.trans.TempTrans;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.buyers.BuyerChannel;
import com.froad.client.buyers.Buyers;
import com.froad.client.goodsGroupRack.GoodsGroupRack;
import com.froad.client.merchantTrafficMAP.MerchantTrafficMAP;
import com.froad.client.point.PointsAccount;
import com.froad.client.trans.Pay;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.trans.Goods;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.user.User;
import com.froad.common.PayState;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.Result;
import com.froad.util.command.Command;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-4-5  
 * @version 1.0
 * 团购及交易类
 */
public class TransGroupAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -112768927609061741L;
	private static final Logger logger=Logger.getLogger(TransGroupAction.class);	
	private TransActionSupport transActionSupport;
	private PayActionSupport payActionSupport;	
	private SellersActionSupport sellersActionSupport;	
	private BuyersActionSupport buyersActionSupport;
	private GoodsGroupRackActionSupport goodsGroupRackActionSupport;
	private MerchantTrafficMAPActionSupport merchantTrafficMAPActionSupport;
	private PointActionSupport pointActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private MerchantTrafficMAP merchantTrafficMAP;//商户地图	
	private GoodsGroupRack pager;
	private GoodsGroupRack goodsGroupRack;
	private Trans trans;	
	private TempTrans tempTran;
	private String cash;
	private String fftPointPricing;//分分通积分支付的积分数
	private String bankPointPricing;//银行积分支付的积分数
	private String cashFftPointPricing;//分分通积分+现金的组合支付的积分数
	private String cashFftPointPricingCash;//分分通积分+现金的组合支付的现金
	private String cashBankPointPricing;//银行积分+现金的组合支付的积分数
	private String cashBankPointPricingCash;//银行积分+现金的组合支付的现金
	private String fftPoint;
	private String bankPoint;
	private String bankPointValueMin;
	private String fftPointValueMin;
	private String bankPointValueMax;
	private String fftPointValueMax;
	private String cashFftPointsRatio;
	private String cashBankPointsRatio;
	private String perNumber;//没人限购数量
	private String perminNumber;//最低购买数量
	private String payResult;
	private String errorMsg;
	private String startFalg = "1";
	
    private String paymentUrl;
	private String notfind = "";
	
	
	
	//===========================================================新版商户展示
	private StoreActionSupport storeActionSupport;
	private List<Store> storeList;
	
	
	
	/**
	 * 查询团购上架商品列表
	 * 进入团购首页面
	 * @return
	 */
	public String index(){
		//查询团购商品列表
		if(pager == null){
			pager = new GoodsGroupRack();
		}

		if(Assert.empty(pager.getState())){
			pager.setState(Command.STATE_START);			
		}
		pager.setPageSize(12);//每页12条
		pager.setIsRack("1");
        pager.setOrderBy(" priority ");
        pager.setOrderType(com.froad.client.goodsGroupRack.OrderType.DESC);
		pager = goodsGroupRackActionSupport.findGoodsGroupByPager(pager);
		return "success";
	}
	
	
	/**
	 * 查询团购上架goods的详细信息
	 * 进入团购商品的详情页面
	 * @return
	 */
	public String groupGoodsDetail(){
		logger.info("groupGoodsDetail:参数"+ JSONObject.fromObject(goodsGroupRack).toString());
		//查询上架团购商品的详细信息
		if(goodsGroupRack==null){
			goodsGroupRack = new GoodsGroupRack();
		}
		if(Assert.empty(goodsGroupRack.getId())){
			logger.info("传入ID为空");
			return "error";
		}
		goodsGroupRack = goodsGroupRackActionSupport.getGoodsGroupRackById(goodsGroupRack.getId());
//		if("0".equals(goodsGroupRack.getIsRack())){
//			notfind = "notfind";
//			return "notfind";
//		}
		if(goodsGroupRack != null && !Assert.empty(goodsGroupRack.getBeginTime()) && !Assert.empty(goodsGroupRack.getEndTime())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
			String now = df.format(new Date());
			int startNum = goodsGroupRack.getBeginTime().compareTo(now);
			int endNum = now.compareTo(goodsGroupRack.getEndTime());
			if(startNum>0 || endNum>0){//判断是否超过时间   过期：startNum = 1;  未过期 startNum = 0、-1
				//没有到团购时间  或者  超过团购时间
				startFalg = "0";
			}else{
				startFalg = "1";
			}			
		}
		//商户地图
		merchantTrafficMAP=new MerchantTrafficMAP();
		merchantTrafficMAP=merchantTrafficMAPActionSupport.getMerchantTrafficMapByMerchantId(goodsGroupRack.getGoods().getMerchantId());
		
		//===============================================新版商户展示 分页商家
		//分店信息查询 begin
		Store store = new Store();
		try{
			store.setMerchantId(Integer.parseInt(goodsGroupRack.getMerchantId()));
		}catch (Exception e) {
			log.error("查询Store信息时 传入参数转型Integer失败，参数为id="+id,e);
		}
		List<Object> storeObjList = storeActionSupport.getStoreByPager(store).getList();
		storeList = new ArrayList<Store>();
		for (Object object : storeObjList) {
			storeList.add((Store)object);
		}
		//分店信息查询 over
		return "success";
	}
	
	/**
	 * 进入团购商品交易页面
	 * @return
	 */
	public String group(){
		User user = getLoginUser();
		if(user == null || Assert.empty(user.getUserID())){
			logger.info("用户没有登陆");
			return "login_page";
		}
		Object merchant=getSession(SessionKey.MERCHANT);
		if(merchant != null){
			logger.info("商户暂时不能团购商品");
			errorMsg ="商户暂时不能团购商品";
			return "failse";
		}
		if(tempTran == null || tempTran.getGoodsRackId()==null){
			logger.info("goodsRackId参数为空 ");
			errorMsg ="该页面已过期。";
			return "failse";
		}
		pager=goodsGroupRackActionSupport.getGoodsGroupRackById(Integer.parseInt(tempTran.getGoodsRackId()));		
		
		//判断商品信息
		if(pager==null){
			logger.info("没有查到商品 id: "+tempTran.getGoodsRackId());
			errorMsg ="暂时没有该商品，敬请等待";
			return "failse";
		}
		
		//判断是否下架
		if(Assert.empty(pager.getIsRack()) || !"1".equals(pager.getIsRack())){
			logger.info("商品已经下架！id: "+tempTran.getGoodsRackId());
			errorMsg ="此商品已经下架!";
			return "failse";
		}
		
		//查询分分通，银行卡积分
		Map<String,PointsAccount> pointsTypePointsAccountMap = pointActionSupport.queryPointsByUsername(user.getUsername());

		if(!Assert.empty(pointsTypePointsAccountMap)){
			PointsAccount pointaccount = pointsTypePointsAccountMap.get("FFTPlatform");
			fftPoint = pointaccount == null?"0":pointaccount.getPoints();
			PointsAccount pointaccountBank = pointsTypePointsAccountMap.get("ZHBank");
			bankPoint = pointaccountBank==null?"0":pointaccountBank.getPoints();
		}else{
			logger.info("************进入团购抢购下单页面，积分查询失败***********! username:"+user.getUsername());
			fftPoint = "0";
			bankPoint = "0";
		}
		//计算两种最大最小积分值范围以及支持积分团购的积分值
		fftAndBankPointInfo(pager);
		return "success";
	}
	
	
	/**
	 * 组装交易信息并创建交易
	 * @return
	 */
	public String order(){
		User user = getLoginUser();
		if(user == null || Assert.empty(user.getUserID())){
			logger.info("用户没有登陆");
			return "login_page";
		}
		Object merchant=getSession(SessionKey.MERCHANT);
		if(merchant != null){
			logger.info("商户暂时不能团购商品");
			errorMsg ="商户暂时不能团购商品";
			return "failse";
		}
		if(tempTran==null||tempTran.getGoodsRackId()==null){
			errorMsg = "该页面已过期。";	
			return "failse";//返回
		}
		//获取交易品信息
		goodsGroupRack = goodsGroupRackActionSupport.getGoodsGroupRackById(Integer.valueOf(tempTran.getGoodsRackId()));
		
		Integer goodnum = Integer.valueOf(tempTran.getGoodsNumber()==null?"0":tempTran.getGoodsNumber());
		Integer pernumber = Integer.valueOf(goodsGroupRack.getPerNumber()==null?"0":goodsGroupRack.getPerNumber().trim());
		Integer perminnumber = Integer.valueOf(goodsGroupRack.getPerminNumber()==null?"0":goodsGroupRack.getPerminNumber().trim());
		if(goodnum != 0 && pernumber != null && pernumber != 0 && goodnum > pernumber){
			errorMsg = "每笔交易限购数量为。"+pernumber;	
			return "failse";//返回
		}else if(goodnum != 0 && perminnumber != null && perminnumber != 0 && goodnum < perminnumber){
			errorMsg = "每笔交易最低购买数量为"+perminnumber;	
			return "failse";//返回
		}else if(goodnum == 0){
			errorMsg = "请选择商品的购买数量!";	
			return "failse";//返回
		}
		
		//判断商品是否在团购期限内
		if(goodsGroupRack != null && !Assert.empty(goodsGroupRack.getBeginTime()) && !Assert.empty(goodsGroupRack.getEndTime())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
			String now = df.format(new Date());
			int startNum = goodsGroupRack.getBeginTime().compareTo(now);
			int endNum = now.compareTo(goodsGroupRack.getEndTime());
			if(startNum>0 || endNum>0){//判断是否超过时间   过期：startNum = 1;  未过期 startNum = 0、-1
				//没有到团购时间  或者  超过团购时间
				logger.info("交易商品不在团购的期限内  id:"+goodsGroupRack.getId());
				payResult="error";
				errorMsg = "交易商品不在团购的期限内";
				return "failse";//返回
			}		
		}
		//判断是否下架
		if(goodsGroupRack != null && (Assert.empty(goodsGroupRack.getIsRack()) || !"1".equals(goodsGroupRack.getIsRack()))){
			logger.info("商品已经下架！id: "+goodsGroupRack.getId());
			errorMsg ="此商品已经下架!";
			return "failse";//返回
		}
		
		logger.info("---------------交易信息组装开始---------------");
		
		tempTran.setUserId(user.getUserID());
		tempTran.setTransType(TranCommand.GROUP);
		tempTran.setPayChannel(TranCommand.PAY_CHANNEL_PHONE);
		
		//获取买家编号和支付渠道
		if(this.needBuyer(tempTran.getPayMethod())){
			Buyers buyer=buyersActionSupport.getBuyerByUserId(user.getUserID());
			if(buyer==null){
				logger.info("不存在买家支付信息，确认是否是手机银行卡用户!");
				errorMsg = "无法完成支付，请确认您是否为珠海农商行手机银行卡用户！";	
				return "failse";//返回
			}
			if(buyer.getBuyerChannelList()==null||buyer.getBuyerChannelList().size()==0){
				logger.info("不存在买家支付信息，确认是否是手机银行卡用户!");
				errorMsg = "无法完成支付，请确认您是否为珠海农商行手机银行卡用户！";	
				return "failse";//返回
			}
			tempTran.setBuyersId(String.valueOf(buyer.getId()));
			BuyerChannel bc = buyer.getBuyerChannelList().get(0);
			tempTran.setBuyerChannelId(String.valueOf(bc.getId()));				
		}
		
		//获取卖家信息和收款资金渠道
		Seller seller = null;
		if(goodsGroupRack != null && !Assert.empty(goodsGroupRack.getSellerId())){
			tempTran.setSellerId(String.valueOf(goodsGroupRack.getSellerId()));
			tempTran.setMerchantId(Integer.valueOf(goodsGroupRack.getMerchantId()));
			seller = sellersActionSupport.getSellerById(String.valueOf(goodsGroupRack.getSellerId()));
			logger.info("卖家信息查询完成");
		}
		if(seller != null && seller.getSellerChannelList() != null 
				&& seller.getSellerChannelList().size()>0){
			SellerChannel sellerChannel = seller.getSellerChannelList().get(0); 
			tempTran.setSellerChannelId(String.valueOf(sellerChannel.getId()));
			tempTran.setSellerRuleId(sellerChannel.getSellerRuleId());
		}else{
			logger.info("不存在卖家信息");
			//errorMsg = "不存在卖家信息";	
			errorMsg = "无法完成支付，请确认您是否为珠海农商银行手机银行卡用户！";
			return "failse";//返回
		}			
		
		//通过团购价,购买数量,商品定价,积分等信息确定交易相关价格
		TempTrans ttp = tempTran;
		try {
			tempTran = getTranPrice(tempTran,goodsGroupRack);
		} catch (Exception e) {
			logger.error("计算价格出错", e);
			logger.info("*****************计算价格异常，输入的积分或者价格不在商品的定价范围内！*********************");
			tempTran = ttp;
			errorMsg = "您输入的积分或价格不在商品定价范围内";	
			return "failse";
		}
		logger.info("-------------交易详情信息组装开始----------------");
		TransDetails tranDetail =  makeCarryTransDetails(tempTran,goodsGroupRack);
		logger.info("---------------交易详情信息组装结束---------------");
		tempTran.setPhone(user.getMobilephone());
		trans = makeTrans(tempTran);
		trans.getTransDetailsList().add(tranDetail);		
		logger.info("---------------交易信息组装结束---------------");
		
		//组装pay并设置到trans里
		List<Pay> payList = payActionSupport.makePay(trans);
		if(payList != null){
			logger.info("-----------------生成paylist成功-------------------");
			trans.getPayList().addAll(payList);		
			trans = transActionSupport.addTrans(trans);		
			if(trans == null){
				logger.info("----------------交易添加失败----------------");
				errorMsg = "交易添加失败";	
				return "failse";
			}else{
				logger.info("------------------交易添加成功---------------");
			}
		}else{
			logger.info("-------------生成paylist失败---------------------");
			logger.info("您不是手机贴膜卡用户，请到珠海农商银行办理。");
			//errorMsg = "您不是手机贴膜卡用户，请到珠海农商银行办理。";	
			errorMsg = "无法完成支付，请确认您是否为珠海农商银行手机银行卡用户！";
			return "failse";
		}				
		
		//返回到下一页的商品名展示
		if(pager == null){
			pager = new GoodsGroupRack();
		}
		return "success";
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
	 * 确认支付
	 * @return
	 */
	public String pay(){
		User user = getLoginUser();

		if(user == null || Assert.empty(user.getUserID())){
			logger.info("没有登陆");
			return "login_page";
		}
		
		Object merchant=getSession(SessionKey.MERCHANT);
		if(merchant != null){//商户暂时不能团购商品
			logger.info("商户暂时不能团购商品");
			payResult="error";
			errorMsg = "商户暂时不能团购商品";	
			return Action.SUCCESS;
		}
		
		if(trans==null||trans.getId()==null){
			logger.info("页面已过期");
			payResult="error";
			errorMsg = "页面已过期";
			return Action.SUCCESS;
		}
		trans=transActionSupport.getTransById(trans.getId());
		if(trans==null){
			logger.info("不存在交易");
			payResult="error";
			errorMsg = "交易不存在";
	
			return Action.SUCCESS;
		}
		//判断商品是否在团购期限内
//		for(TransDetails detail:trans.getTransDetailsList()){
//			goodsGroupRack = goodsGroupRackActionSupport.getGoodsGroupRackById(Integer.valueOf(detail.getGoodsRackId()));
//			if(goodsGroupRack != null && !Assert.empty(goodsGroupRack.getBeginTime()) && !Assert.empty(goodsGroupRack.getEndTime())){
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
//				String now = df.format(new Date());
//				int startNum = goodsGroupRack.getBeginTime().compareTo(now);
//				int endNum = now.compareTo(goodsGroupRack.getEndTime());
//				if(startNum>0 || endNum>0){//判断是否超过时间   过期：startNum = 1;  未过期 startNum = 0、-1
//					//没有到团购时间  或者  超过团购时间
//					logger.info("交易商品不在团购的期限内  id:"+detail.getGoodsRackId());
//					payResult="error";
//					errorMsg = "交易商品不在团购的期限内";
//					return Action.SUCCESS;
//				}		
//			}
//			//判断是否下架
//			if(goodsGroupRack != null && (Assert.empty(goodsGroupRack.getIsRack()) || !"1".equals(goodsGroupRack.getIsRack()))){
//				logger.info("商品已经下架！id: "+detail.getGoodsRackId());
//				errorMsg ="此商品已经下架!";
//				return "error";
//			}
//		}
		
		Result result=transActionSupport.pay(trans);	
		log.info("交易ID为："+trans.getId()+",支付，调用webserver去支付的结果为："+result);
		if(Result.SUCCESS.equals(result.getCode())){
			logger.info("支付请求发送成功");
			String payMethod=trans.getPayMethod();
			if(TranCommand.POINTS_FFT.equals(payMethod)||TranCommand.POINTS_BANK.equals(payMethod)){
				payResult="success";
			}else if(TranCommand.ALPAY.equals(payMethod)){
                this.paymentUrl = result.getMsg();
                return "alipay";
			}else{
				payResult="waiting";
			}
			Map<String,Object> session=ActionContext.getContext().getSession();
			session.put("changedPoints", true);
		}else{
			logger.info("支付失败，原因： "+result.getMsg());
			payResult="fail";
			errorMsg="支付失败！";
			if(result.getMsg().startsWith(TranCommand.EXCEPTION_PREFIX)){
				errorMsg=result.getMsg();
			}
		}
		return Action.SUCCESS;
	}
	
	
	/**组织交易的模板方法：这个方法的入参自定**/
	public Trans makeTrans(TempTrans tempTran){			
			Trans trans=new Trans();
			trans.setPhone(tempTran.getPhone());
			trans.setTransType(tempTran.getTransType());//必填
			trans.setSellerId(tempTran.getSellerId());//必填
			trans.setSellerChannelId(tempTran.getSellerChannelId());//必填
			trans.setCurrency("RMB");//包含现金的时候需要设置为“RMB”,否则不设值
			trans.setCostpriceTotal(tempTran.getCostpriceTotal());//必填
			trans.setCurrencyValueAll(tempTran.getCurrencyValueAll());//必填
			trans.setCurrencyValueRealAll(tempTran.getCurrencyValueRealAll());//必填
			trans.setState(TranCommand.TRAN_PROCESSING);//必填
			trans.setClientType(tempTran.getClientType());//必填
			trans.setPayChannel(tempTran.getPayChannel());//必填
			trans.setPayMethod(tempTran.getPayMethod());//必填
			//TODO 待加入特定交易的必填字段
			
			trans.setMerchantId(tempTran.getMerchantId());
			trans.setUserId(tempTran.getUserId());
			trans.setBuyersId(tempTran.getBuyersId());
			trans.setBuyerChannelId(tempTran.getBuyerChannelId());
			trans.setFftPointsValueRealAll(tempTran.getFftPointsValueRealAll());
			trans.setFftPointsValueAll(tempTran.getFftPointsValueAll());
			trans.setBankPointsValueRealAll(tempTran.getBankPointsValueRealAll());
			trans.setBankPointsValueAll(tempTran.getBankPointsValueAll());
			return trans;
	}
	
	
	//交易详情的java方法模板
	public TransDetails makeCarryTransDetails(TempTrans tempTran,GoodsGroupRack goodsGroupRack){
			TransDetails details=new TransDetails();			
			Goods good = new Goods();
			if(!Assert.empty(tempTran.getGoodsName())){
				good.setGoodsName(tempTran.getGoodsName());				
			}else if(goodsGroupRack.getGoods() != null){
				good.setGoodsName(goodsGroupRack.getGoods().getGoodsName());
			}
			//TODO 待设值
			details.setGoodsRackId(tempTran.getGoodsRackId());
			details.setGoodsNumber(tempTran.getGoodsNumber());
			details.setGoods(good);
			details.setSellerRuleId(tempTran.getSellerRuleId());
			details.setCurrency("RMB");
			details.setCostpriceTotal(tempTran.getCostpriceTotal());
			details.setCurrencyValue(tempTran.getCurrencyValueAll());
			details.setCurrencyValueReal(tempTran.getCurrencyValueRealAll());
			details.setBankPointsValueAll(tempTran.getBankPointsValueAll());
			details.setBankPointsValueRealAll(tempTran.getBankPointsValueRealAll());
			details.setFftPointsValueAll(tempTran.getFftPointsValueAll());
			details.setFftPointsValueRealAll(tempTran.getFftPointsValueRealAll());
			details.setState(Command.STATE_START);			
			return details;
		}
	
	/**
	 * 根据交易品信息，购买数量，交易品定价，启用哪种定价模式，积分数计算出结果给下面几种价格和积分赋值(不同交易需要设置的值不同)
	 * 
	 * 1:trans.setCostpriceTotal();  				原价总和
	 * 2:trans.setCurrencyValueAll();				交易价格(买家所付现金额度，不包括手续费)
	 * 3:trans.setCurrencyValueRealAll();			买家实际支付的现金额
	 * 4:trans.setFftPointsValueRealAll();			分分通积分数
	 * 5:trans.setBankPointsValueRealAll();			银行积分数
	 * 
	 *      每笔交易分分通积分和银行积分不同时存在
	 * 
	 * @param tempTran
	 * @param goods
	 * @return TempTrans
	 * @throws Exception
	 */
	public TempTrans getTranPrice(TempTrans tempTran,GoodsGroupRack goods) throws Exception{
		logger.info("-------------计算交易积分额和价格信息开始---------------------");
		if(tempTran==null){
			logger.error("无效的交易");
			throw new AppException("交易参数为空");
		}
		if(tempTran.getGoodsNumber()==null){
			logger.error("商品数量为空");
			throw new AppException("请输入需要购买的商品数量！");
		}
		String payMethod=tempTran.getPayMethod();
		if(payMethod==null){
			logger.error("支付方式为空");
			throw new AppException("请选择支付方式！");
		}
		if(!Assert.empty(goods.getCashPricing())){
			BigDecimal currencyDindjia=new BigDecimal(goods.getCashPricing());
			BigDecimal goodsNumTotal = new BigDecimal(tempTran.getGoodsNumber());
			currencyDindjia = currencyDindjia.multiply(goodsNumTotal).setScale(2, BigDecimal.ROUND_DOWN);
			tempTran.setCostpriceTotal(String.valueOf(currencyDindjia));
			tempTran.setCurrencyValueAll(String.valueOf(currencyDindjia));
		}
		if(TranCommand.POINTS_FFT.equals(payMethod)){
			logger.info("-------------方付通积分计算开始"+"payMethod:"+payMethod+"---------------------");
			//方付通积分支付
			tempTran.setCurrency("");
//			tempTran.setCostpriceTotal("0");
//			tempTran.setCurrencyValueAll("0");
			tempTran.setCurrencyValueRealAll("0");
			tempTran.setFftPointsValueRealAll("0");
			tempTran.setFftPointsValueAll("0");
			tempTran.setBankPointsValueRealAll("0");
			tempTran.setBankPointsValueAll("0");
			
			if(!Command.price_availiable.equals(goods.getIsFftPoint())){
				throw new Exception("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
			}
			
			BigDecimal fftPoints=new BigDecimal(goods.getFftPointPricing());
			BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());
			fftPoints = fftPoints.multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);
			tempTran.setFftPointsValueAll(String.valueOf(fftPoints));
			tempTran.setFftPointsValueRealAll(String.valueOf(fftPoints));
			logger.info("-------------方付通积分计算结束"+"fftPoint:"+String.valueOf(fftPoints)+"---------------------");
		}else if(TranCommand.POINTS_BANK.equals(payMethod)){
			//银行积分支付
			logger.info("-------------银行积分计算开始"+"payMethod:"+payMethod+"---------------------");
			tempTran.setCurrency("");
//			tempTran.setCostpriceTotal("0");
//			tempTran.setCurrencyValueAll("0");
			tempTran.setCurrencyValueRealAll("0");
			tempTran.setFftPointsValueRealAll("0");
			tempTran.setFftPointsValueAll("0");
			tempTran.setBankPointsValueRealAll("0");
			tempTran.setBankPointsValueAll("0");
			
			if(!Command.price_availiable.equals(goods.getIsBankPoint())){
				throw new Exception("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
			}
			
			BigDecimal bankPoints=new BigDecimal(goods.getBankPointPricing());
			BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());
			bankPoints = bankPoints.multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);
			tempTran.setBankPointsValueAll(String.valueOf(bankPoints));
			tempTran.setBankPointsValueRealAll(String.valueOf(bankPoints));
			logger.info("-------------银行积分计算结束"+"bankPoint:"+String.valueOf(bankPoints)+"---------------------");
		}else if(TranCommand.CASH.equals(payMethod)||TranCommand.ALPAY.equals(payMethod)){
			//现金支付
			logger.info("-------------现金计算开始"+"payMethod:"+payMethod+"---------------------");
//			tempTran.setCostpriceTotal("0");
//			tempTran.setCurrencyValueAll("0");
			tempTran.setCurrencyValueRealAll("0");
			tempTran.setFftPointsValueRealAll("0");
			tempTran.setFftPointsValueAll("0");
			tempTran.setBankPointsValueRealAll("0");
			tempTran.setBankPointsValueAll("0");
			if(!Command.price_availiable.equals(goods.getIsCash())){
				throw new Exception("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
			}

			BigDecimal currency=new BigDecimal(goods.getCashPricing());
			BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());
			currency = currency.multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);
//			tempTran.setCostpriceTotal(String.valueOf(currency));
//			tempTran.setCurrencyValueAll(String.valueOf(currency));
			tempTran.setCurrencyValueRealAll(String.valueOf(currency));
			logger.info("-------------现金计算结束"+"现金:"+String.valueOf(currency)+"元。---------------------");
		}else if(TranCommand.POINTS_FFT_CASH.equals(payMethod)){
			//方付通积分+现金
			logger.info("-------------方付通积分+现金计算开始"+"payMethod:"+payMethod+"---------------------");
//			tempTran.setCostpriceTotal("0");
//			tempTran.setCurrencyValueAll("0");
			tempTran.setCurrencyValueRealAll("0");
			tempTran.setFftPointsValueRealAll("0");
			tempTran.setFftPointsValueAll("0");
			tempTran.setBankPointsValueRealAll("0");
			tempTran.setBankPointsValueAll("0");
			if(!Command.price_availiable.equals(goods.getIsFftpointCash())){
				throw new Exception("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
			}
			
			String fftpointsAndCurrency=goods.getFftpointCashPricing();
			fftpointsAndCurrency=fftpointsAndCurrency.replace("|", ",");
			String[] fftpointsAndCurrencyArr=fftpointsAndCurrency.split(",");
			BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());
			if(fftpointsAndCurrencyArr!=null&&fftpointsAndCurrencyArr.length==2){
				BigDecimal fftPoints=new BigDecimal(fftpointsAndCurrencyArr[0]);
				fftPoints = fftPoints.multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);
				BigDecimal currency=new BigDecimal(fftpointsAndCurrencyArr[1]);
				currency = currency.multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);
				
				tempTran.setFftPointsValueAll(String.valueOf(fftPoints));
				tempTran.setFftPointsValueRealAll(String.valueOf(fftPoints));
				
//				tempTran.setCurrencyValueAll(String.valueOf(currency));
//				tempTran.setCostpriceTotal(String.valueOf(currency));
				tempTran.setCurrencyValueRealAll(String.valueOf(currency));
				logger.info("-------------方付通积分+现金计算结束"+"fftPoints:"+String.valueOf(fftPoints)+"   现金:"+String.valueOf(currency)+"元。---------------------");
			}
		}else if(TranCommand.POINTS_BANK_CASH.equals(payMethod)){
			//银行积分+现金
			logger.info("-------------银行积分+现金计算开始"+"payMethod:"+payMethod+"---------------------");
//			tempTran.setCostpriceTotal("0");
//			tempTran.setCurrencyValueAll("0");
			tempTran.setCurrencyValueRealAll("0");
			tempTran.setFftPointsValueRealAll("0");
			tempTran.setFftPointsValueAll("0");
			tempTran.setBankPointsValueRealAll("0");
			tempTran.setBankPointsValueAll("0");
			if(!Command.price_availiable.equals(goods.getIsBankpointCash())){
				throw new Exception("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
			}
			BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());
			String bankpointsAndCurrency=goods.getBankpointCashPricing();
			bankpointsAndCurrency=bankpointsAndCurrency.replace("|", ",");
			String[] bankpointsAndCurrencyArr=bankpointsAndCurrency.split(",");
			
			BigDecimal bankPoints=new BigDecimal(bankpointsAndCurrencyArr[0]);
			bankPoints = bankPoints.multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);
			
			BigDecimal currency=new BigDecimal(bankpointsAndCurrencyArr[1]);
			currency = currency.multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);
			
			if(bankpointsAndCurrencyArr!=null&&bankpointsAndCurrencyArr.length==2){
				tempTran.setBankPointsValueAll(String.valueOf(bankPoints));
				tempTran.setBankPointsValueRealAll(String.valueOf(bankPoints));
				
//				tempTran.setCurrencyValueAll(String.valueOf(currency));
//				tempTran.setCostpriceTotal(String.valueOf(currency));
				tempTran.setCurrencyValueRealAll(String.valueOf(currency));
				logger.info("-------------银行积分+现金计算结束"+"bankPoints:"+String.valueOf(bankPoints)+"   现金:"+String.valueOf(currency)+"元。---------------------");
			}
		}else if(TranCommand.POINTS_FFT_CASH_SCOPE.equals(payMethod)||
				TranCommand.POINTS_FFT_ALPAY_SCOPE.equals(payMethod)){
			//方付通积分+现金 范围
			logger.info("-------------方付通积分+现金比率计算开始"+"payMethod:"+payMethod+"---------------------");
			if(!Command.price_availiable.equals(goods.getIsFftpointcashRatioPricing())){
				throw new Exception("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
			}
			if(Assert.empty(tempTran.getFftPointsValueRealAll()))
				tempTran.setFftPointsValueRealAll("0");
			String fftpointsMinMax=goods.getFftpointcashRatioPricing();
			fftpointsMinMax=fftpointsMinMax.replace("|", ",");
			String[] fftpointsMinMaxArr=fftpointsMinMax.split(",");
			
			BigDecimal ratio = new BigDecimal("0.01");//百分比
			BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());//购买商品数量
			BigDecimal cashPring=new BigDecimal(goods.getCashPricing());//团购定价
			BigDecimal groupPring=new BigDecimal(goods.getGroupPrice());//团购价
			
			BigDecimal fftPointsMin=new BigDecimal(fftpointsMinMaxArr[0]);//配置的积分最小百分数
			BigDecimal fftPointsMinValue = fftPointsMin.multiply(ratio).multiply(cashPring).multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);//最小使用分分通积分数(保留两位小数)
			
			BigDecimal fftPointsMax=new BigDecimal(fftpointsMinMaxArr[1]);//配置的积分最大百分数
			BigDecimal fftPointsMaxValue = fftPointsMax.multiply(ratio).multiply(cashPring).multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);//最大使用分分通积分数(保留两位小数)
			
			BigDecimal fftPoints = new BigDecimal(tempTran.getFftPointsValueRealAll()==null?"0":tempTran.getFftPointsValueRealAll());
			
			if(fftPoints.compareTo(fftPointsMaxValue) > 0){
				throw new Exception("使用的积分不在定价的积分范围内");
			}
			
			if(fftPoints.compareTo(fftPointsMinValue) < 0){
				throw new Exception("使用的积分不在定价的积分范围内");
			}
			
			String radioValue = fftCashPointRio();//获取现金分分通积分比率
			BigDecimal fftRio = new BigDecimal(radioValue);
			BigDecimal fftPointsRealPrice=null;
			fftPointsRealPrice=fftPoints.multiply(fftRio);//根据积分现金比率算出积分折合的现金额				
			//BigDecimal fftPointsRealPrice=fftPoints.multiply(new BigDecimal("1"));//根据积分现金比率算出积分折合的现金额
			
			//BigDecimal currency=cashPring.multiply(goodsNum).subtract(fftPointsRealPrice.multiply(goodsNum)).setScale(2, BigDecimal.ROUND_DOWN);//出去积分部分还需支付现金(单品定价 X 数量  - 积分折合现金 = 支付现金)
			BigDecimal currency=cashPring.multiply(goodsNum).subtract(fftPointsRealPrice).setScale(2, BigDecimal.ROUND_DOWN);//出去积分部分还需支付现金(单品定价 X 数量  - 积分折合现金 = 支付现金)
			tempTran.setFftPointsValueAll(String.valueOf(fftPoints));
			tempTran.setFftPointsValueRealAll(String.valueOf(fftPoints));
			
//			tempTran.setCostpriceTotal(String.valueOf(currency));
//			tempTran.setCurrencyValueAll(String.valueOf(currency));
			tempTran.setCurrencyValueRealAll(String.valueOf(currency));
			tempTran.setBankPointsValueRealAll("0");
			tempTran.setBankPointsValueAll("0");
			logger.info("-------------方付通积分+现金比率计算结束"+"fftPoints:"+String.valueOf(fftPoints)+"   现金:"+String.valueOf(currency)+"元。---------------------");
		}else if(TranCommand.POINTS_BANK_CASH_SCOPE.equals(payMethod)||
				TranCommand.POINTS_BANK_ALPAY_SCOPE.equals(payMethod)){
			//银行积分+现金  范围
			logger.info("-------------银行积分+现金比率计算开始"+"payMethod:"+payMethod+"---------------------");
			if(!Command.price_availiable.equals(goods.getIsBankpointcashRatioPricing())){
				throw new Exception("交易品定价有误,请检查交易品id:"+goods.getId()+"的定价");
			}
			if(Assert.empty(tempTran.getBankPointsValueRealAll()))
				tempTran.setBankPointsValueRealAll("0");
			String bankpointsMinMax=goods.getBankpointcashRatioPricing();
			bankpointsMinMax=bankpointsMinMax.replace("|", ",");
			String[] bankpointsMinMaxArr=bankpointsMinMax.split(",");
			
			BigDecimal ratio = new BigDecimal("0.01");//百分比
			BigDecimal goodsNum = new BigDecimal(tempTran.getGoodsNumber());//购买商品数量
			BigDecimal cashPring=new BigDecimal(goods.getCashPricing());//团购定价(团购中不管现金定价是否启用，此值都部位空。换算比率用这个值作为计算参数)
			BigDecimal groupPring=new BigDecimal(goods.getGroupPrice());//团购价
			
			BigDecimal bankPointsMin=new BigDecimal(bankpointsMinMaxArr[0]);//配置的积分最小百分数
			BigDecimal bankPointsMinValue = bankPointsMin.multiply(ratio).multiply(cashPring).multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);//最小使用分分通积分数(保留两位小数)
			
			BigDecimal bankPointsMax=new BigDecimal(bankpointsMinMaxArr[1]);//配置的积分最大百分数
			BigDecimal bankPointsMaxValue = bankPointsMax.multiply(ratio).multiply(cashPring).multiply(goodsNum).setScale(2, BigDecimal.ROUND_DOWN);//最大使用分分通积分数(保留两位小数)
			
			BigDecimal bankPoints = new BigDecimal(tempTran.getBankPointsValueRealAll()==null?"0":tempTran.getBankPointsValueRealAll());
			
			if(bankPoints.compareTo(bankPointsMaxValue) > 0){
				throw new Exception("使用的积分不在定价的积分范围内");
			}
			
			if(bankPoints.compareTo(bankPointsMinValue) < 0){
				throw new Exception("使用的积分不在定价的积分范围内");
			}
			

			String radioValue = bankCashPointRio();//获取现金银行积分比率
			BigDecimal bankRio = new BigDecimal(radioValue);
			BigDecimal bankPointsRealPrice=null;
			bankPointsRealPrice=bankPoints.multiply(bankRio);//根据积分现金比率算出积分折合的现金额				
			//BigDecimal bankPointsRealPrice=bankPoints.multiply(new BigDecimal("1"));//根据积分现金比率算出积分折合的现金额
			
			//BigDecimal currency=cashPring.multiply(goodsNum).subtract(bankPointsRealPrice.multiply(goodsNum)).setScale(2, BigDecimal.ROUND_DOWN);//出去积分部分还需支付现金(单品定价 X 数量  - 积分折合现金 = 支付现金)
			BigDecimal currency=cashPring.multiply(goodsNum).subtract(bankPointsRealPrice).setScale(2, BigDecimal.ROUND_DOWN);//出去积分部分还需支付现金(单品定价 X 数量  - 积分折合现金 = 支付现金)
			tempTran.setFftPointsValueAll("0");
			tempTran.setFftPointsValueRealAll("0");			

//			tempTran.setCostpriceTotal(String.valueOf(currency));
//			tempTran.setCurrencyValueAll(String.valueOf(currency));
			tempTran.setCurrencyValueRealAll(String.valueOf(currency));
			tempTran.setBankPointsValueRealAll(String.valueOf(bankPoints));
			tempTran.setBankPointsValueAll(String.valueOf(bankPoints));
			logger.info("-------------银行积分+现金比率计算结束"+"bankPoints:"+String.valueOf(bankPoints)+"   现金:"+String.valueOf(currency)+"元。---------------------");
		}
		return tempTran;
	}
	
	/**
	 * 现金积分比例以及商品使用积分最大最小值
	 * @param groupRack
	 */
	public void fftAndBankPointInfo(GoodsGroupRack groupRack){
		cashFftPointsRatio = fftCashPointRio();//获取现金分分通积分比率;//cash/fftPoint
		cashBankPointsRatio = bankCashPointRio();//获取现金银行积分比率;//cash/bankPoint
		if(!Assert.empty(groupRack.getIsFftpointcashRatioPricing()) && "1".equals(groupRack.getIsFftpointcashRatioPricing())){
			String fftpointsMinMax=groupRack.getFftpointcashRatioPricing();
			fftpointsMinMax=fftpointsMinMax.replace("|", ",");
			String[] fftpointsMinMaxArr=fftpointsMinMax.split(",");
			
			BigDecimal ratio = new BigDecimal("0.01");//百分比
			BigDecimal cashPring=new BigDecimal(groupRack.getCashPricing());//团购定价
			BigDecimal cashPricePoints = cashPring.divide(new BigDecimal(cashFftPointsRatio),2,RoundingMode.DOWN);//根据定价和现金积分比率算出相应的积分
			
			BigDecimal fftPointsMin=new BigDecimal(fftpointsMinMaxArr[0]);//配置的积分最小百分数
			BigDecimal fftPointsMinValue = fftPointsMin.multiply(ratio).multiply(cashPricePoints).setScale(2, BigDecimal.ROUND_DOWN);//最小使用分分通积分数(保留两位小数)
			
			fftPointValueMin = String.valueOf(fftPointsMinValue);
			logger.info("-------------分分通积分现金比率定价，单个商品fft积分使用最小值:"+fftPointValueMin+"---------------------");
			
			BigDecimal fftPointsMax=new BigDecimal(fftpointsMinMaxArr[1]);//配置的积分最大百分数
			BigDecimal fftPointsMaxValue = fftPointsMax.multiply(ratio).multiply(cashPricePoints).setScale(2, BigDecimal.ROUND_DOWN);//最小使用分分通积分数(保留两位小数)
			
			fftPointValueMax = String.valueOf(fftPointsMaxValue);
			logger.info("-------------分分通积分现金比率定价，单个商品fft积分使用最大值:"+fftPointValueMax+"---------------------");
		}
		if(!Assert.empty(groupRack.getIsBankpointcashRatioPricing()) && "1".equals(groupRack.getIsBankpointcashRatioPricing())){
			String bankpointsMinMax=groupRack.getBankpointcashRatioPricing();
			bankpointsMinMax=bankpointsMinMax.replace("|", ",");
			String[] bankpointsMinMaxArr=bankpointsMinMax.split(",");
			
			BigDecimal ratio = new BigDecimal("0.01");//百分比
			BigDecimal cashPring=new BigDecimal(groupRack.getCashPricing());//团购定价
			
			BigDecimal cashPricePoints = cashPring.divide(new BigDecimal(cashBankPointsRatio),2,RoundingMode.DOWN);//根据定价和现金积分比率算出相应的积分
			
			BigDecimal bankPointsMin=new BigDecimal(bankpointsMinMaxArr[0]);//配置的积分最小百分数
			BigDecimal bankPointsMinValue = bankPointsMin.multiply(ratio).multiply(cashPricePoints).setScale(2, BigDecimal.ROUND_DOWN);//最小使用银行积分数(保留两位小数)
			
			bankPointValueMin = String.valueOf(bankPointsMinValue);
			logger.info("-------------银行积分现金比率定价，单个商品bank积分使用最小值:"+bankPointValueMin+"---------------------");
			
			BigDecimal bankPointsMax=new BigDecimal(bankpointsMinMaxArr[1]);//配置的积分最大百分数
			BigDecimal bankPointsMaxValue = bankPointsMax.multiply(ratio).multiply(cashPricePoints).setScale(2, BigDecimal.ROUND_DOWN);//最小使用银行积分数(保留两位小数)
			
			bankPointValueMax = String.valueOf(bankPointsMaxValue);
			logger.info("-------------银行积分现金比率定价，单个商品bank积分使用最大值:"+bankPointValueMax+"---------------------");
		}
		if(!Assert.empty(groupRack.getIsCash()) && "1".equals(groupRack.getIsCash())){
			cash = groupRack.getCashPricing();	
			logger.info("-------------现金定价，单个商品现金:"+cash+"---------------------");
		}
		if(!Assert.empty(groupRack.getIsFftPoint()) && "1".equals(groupRack.getIsFftPoint())){
			fftPointPricing = groupRack.getFftPointPricing();	
			logger.info("-------------纯fft积分定价，单个商品fft积分使用值:"+fftPointPricing+"---------------------");
		}
		if(!Assert.empty(groupRack.getIsBankPoint()) && "1".equals(groupRack.getIsBankPoint())){
			bankPointPricing = groupRack.getBankPointPricing();	
			logger.info("-------------纯bank积分定价，单个商品bank积分使用值:"+bankPointPricing+"---------------------");
		}
		if(!Assert.empty(groupRack.getIsFftpointCash()) && "1".equals(groupRack.getIsFftpointCash())){
			String fftPointTemp = groupRack.getFftpointCashPricing();
			fftPointTemp = fftPointTemp.replace("|", ",");
			String[] fftPointArr = fftPointTemp.split(",");
			if(fftPointArr != null && fftPointArr.length == 2){
				cashFftPointPricing = fftPointArr[0];	
				cashFftPointPricingCash = fftPointArr[1];
				logger.info("-------------fft积分+现金定价，单个商品fft积分使用值:"+cashFftPointPricing+"  现金："+cashFftPointPricingCash+"---------------------");
			}
		}
		if(!Assert.empty(groupRack.getIsBankpointCash()) && "1".equals(groupRack.getIsBankpointCash())){
			String bankPointTemp = groupRack.getBankpointCashPricing();
			bankPointTemp = bankPointTemp.replace("|", ",");
			String[] bankPointArr = bankPointTemp.split(",");
			if(bankPointArr != null && bankPointArr.length == 2){
				cashBankPointPricing = bankPointArr[0];	
				cashBankPointPricingCash = bankPointArr[1];
				logger.info("-------------bank积分+现金定价，单个商品bank积分使用值:"+cashBankPointPricing+"  现金："+cashBankPointPricingCash+"---------------------");
			}		
		}
		if(!Assert.empty(groupRack.getPerNumber()) && !"0".equals(groupRack.getPerNumber().trim())){
			perNumber = groupRack.getPerNumber();
		}else{
			perNumber = "0";
		}
		if(!Assert.empty(groupRack.getPerminNumber()) && !"0".equals(groupRack.getPerminNumber().trim())){
			perminNumber = groupRack.getPerminNumber();
		}else{
			perminNumber = "0";
		}
	}
	
	/**
	 * 获取现金分分通积分比率
	 * @return
	 */
	public String fftCashPointRio(){
		logger.info("查询现金分分通积分比率");
		String radioValue = TranCommand.PRICING_FFT_CASH;//trans.properties配置现金分分通积分比率
		String rio = "1";
		String[] radioValueArr = radioValue.split(":");
		if(radioValueArr != null && radioValueArr.length == 2 && !"0".equals(radioValueArr[1])){
			BigDecimal cash = new BigDecimal(radioValueArr[0]);
			BigDecimal point = new BigDecimal(radioValueArr[1]);
			rio = cash.divide(point,2,RoundingMode.DOWN).toString();	
			logger.info("现金分分通积分比率 :"+rio);
			return rio;
		}else{
			logger.info("现金分分通积分比率 :"+rio);
			return rio;
		}
	}
	/**
	 * 获取现金银行积分比率
	 * @return
	 */
	public String bankCashPointRio(){
		logger.info("查询现金银行积分比率");
		String radioValue = TranCommand.PRICING_BANK_CASH;//trans.properties配置现金银行积分比率
		String rio = "1";
		String[] radioValueArr = radioValue.split(":");
		if(radioValueArr != null && radioValueArr.length == 2 && !"0".equals(radioValueArr[1])){
			BigDecimal cash = new BigDecimal(radioValueArr[0]);
			BigDecimal point = new BigDecimal(radioValueArr[1]);
			rio = cash.divide(point,2,RoundingMode.DOWN).toString();
			logger.info("现金银行积分比率 :"+rio);
			return rio;
		}else{
			logger.info("现金银行积分比率 :"+rio);
			return rio;
		}
	}
	
	
    /**
     * 支付宝支付结果检查
     *
     * @return
     */
    public String aliPayCheck()
    {
        User user = getLoginUser();
        if (user == null || Assert.empty(user.getUserID()))
        {
            logger.info("没有登陆");
            return "login_page";
        }
        
        if (trans == null || trans.getId() == null)
        {
            logger.info("======交易查询参数为空=====");
            payResult = "error";
            errorMsg = "页面已过期";
            return Action.SUCCESS;
        }
        logger.info("查询支付结果，交易ID："+ trans.getId());
        String aliPayCheckResult = transActionSupport.aliPayResultCheck(String.valueOf(trans.getId()));

        //        aliPayCheckResult = true;
        if (PayState.PAY_SUCCESS.equals(aliPayCheckResult))
        {
            return Action.SUCCESS;
        }
        else if (PayState.PAY_REQUEST_SUCCESS.equals(aliPayCheckResult))
        {
            return "wait";
        }
        else
        {
            errorMsg = "支付失败！";
            return "failse";
        }
    }
	
	
	
	
	
	public TempTrans getTempTran() {
		return tempTran;
	}
	public void setTempTran(TempTrans tempTran) {
		this.tempTran = tempTran;
	}

	public PayActionSupport getPayActionSupport() {
		return payActionSupport;
	}

	public void setPayActionSupport(PayActionSupport payActionSupport) {
		this.payActionSupport = payActionSupport;
	}

	public SellersActionSupport getSellersActionSupport() {
		return sellersActionSupport;
	}

	public void setSellersActionSupport(SellersActionSupport sellersActionSupport) {
		this.sellersActionSupport = sellersActionSupport;
	}

	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	}

	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
	}

	public GoodsGroupRack getPager() {
		return pager;
	}

	public void setPager(GoodsGroupRack pager) {
		this.pager = pager;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public GoodsGroupRackActionSupport getGoodsGroupRackActionSupport() {
		return goodsGroupRackActionSupport;
	}

	public void setGoodsGroupRackActionSupport(
			GoodsGroupRackActionSupport goodsGroupRackActionSupport) {
		this.goodsGroupRackActionSupport = goodsGroupRackActionSupport;
	}

	public GoodsGroupRack getGoodsGroupRack() {
		return goodsGroupRack;
	}

	public void setGoodsGroupRack(GoodsGroupRack goodsGroupRack) {
		this.goodsGroupRack = goodsGroupRack;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public MerchantTrafficMAPActionSupport getMerchantTrafficMAPActionSupport() {
		return merchantTrafficMAPActionSupport;
	}

	public void setMerchantTrafficMAPActionSupport(
			MerchantTrafficMAPActionSupport merchantTrafficMAPActionSupport) {
		this.merchantTrafficMAPActionSupport = merchantTrafficMAPActionSupport;
	}

	public MerchantTrafficMAP getMerchantTrafficMAP() {
		return merchantTrafficMAP;
	}

	public void setMerchantTrafficMAP(MerchantTrafficMAP merchantTrafficMAP) {
		this.merchantTrafficMAP = merchantTrafficMAP;
	}

	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}

	public String getFftPoint() {
		return fftPoint;
	}

	public void setFftPoint(String fftPoint) {
		this.fftPoint = fftPoint;
	}

	public String getBankPoint() {
		return bankPoint;
	}

	public void setBankPoint(String bankPoint) {
		this.bankPoint = bankPoint;
	}

	public String getBankPointValueMin() {
		return bankPointValueMin;
	}

	public void setBankPointValueMin(String bankPointValueMin) {
		this.bankPointValueMin = bankPointValueMin;
	}

	public String getFftPointValueMin() {
		return fftPointValueMin;
	}

	public void setFftPointValueMin(String fftPointValueMin) {
		this.fftPointValueMin = fftPointValueMin;
	}

	public String getBankPointValueMax() {
		return bankPointValueMax;
	}

	public void setBankPointValueMax(String bankPointValueMax) {
		this.bankPointValueMax = bankPointValueMax;
	}

	public String getFftPointValueMax() {
		return fftPointValueMax;
	}

	public void setFftPointValueMax(String fftPointValueMax) {
		this.fftPointValueMax = fftPointValueMax;
	}

	public String getCashFftPointsRatio() {
		return cashFftPointsRatio;
	}

	public void setCashFftPointsRatio(String cashFftPointsRatio) {
		this.cashFftPointsRatio = cashFftPointsRatio;
	}

	public String getCashBankPointsRatio() {
		return cashBankPointsRatio;
	}

	public void setCashBankPointsRatio(String cashBankPointsRatio) {
		this.cashBankPointsRatio = cashBankPointsRatio;
	}

	public PointActionSupport getPointActionSupport() {
		return pointActionSupport;
	}

	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}

	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	public String getFftPointPricing() {
		return fftPointPricing;
	}

	public void setFftPointPricing(String fftPointPricing) {
		this.fftPointPricing = fftPointPricing;
	}

	public String getBankPointPricing() {
		return bankPointPricing;
	}

	public void setBankPointPricing(String bankPointPricing) {
		this.bankPointPricing = bankPointPricing;
	}

	public String getCashFftPointPricing() {
		return cashFftPointPricing;
	}

	public void setCashFftPointPricing(String cashFftPointPricing) {
		this.cashFftPointPricing = cashFftPointPricing;
	}

	public String getCashBankPointPricing() {
		return cashBankPointPricing;
	}

	public void setCashBankPointPricing(String cashBankPointPricing) {
		this.cashBankPointPricing = cashBankPointPricing;
	}

	public String getStartFalg() {
		return startFalg;
	}


	public void setStartFalg(String startFalg) {
		this.startFalg = startFalg;
	}
	public String getPayResult() {
		return payResult;
	}


	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}


	public String getCash() {
		return cash;
	}


	public void setCash(String cash) {
		this.cash = cash;
	}


	public String getCashFftPointPricingCash() {
		return cashFftPointPricingCash;
	}


	public void setCashFftPointPricingCash(String cashFftPointPricingCash) {
		this.cashFftPointPricingCash = cashFftPointPricingCash;
	}


	public String getCashBankPointPricingCash() {
		return cashBankPointPricingCash;
	}


	public void setCashBankPointPricingCash(String cashBankPointPricingCash) {
		this.cashBankPointPricingCash = cashBankPointPricingCash;
	}


	public String getPerNumber() {
		return perNumber;
	}


	public void setPerNumber(String perNumber) {
		this.perNumber = perNumber;
	}


	public String getPerminNumber() {
		return perminNumber;
	}


	public void setPerminNumber(String perminNumber) {
		this.perminNumber = perminNumber;
	}


	public String getNotfind() {
		return notfind;
	}


	public void setNotfind(String notfind) {
		this.notfind = notfind;
	}


	public StoreActionSupport getStoreActionSupport() {
		return storeActionSupport;
	}


	public void setStoreActionSupport(StoreActionSupport storeActionSupport) {
		this.storeActionSupport = storeActionSupport;
	}


	public List<Store> getStoreList() {
		return storeList;
	}


	public void setStoreList(List<Store> storeList) {
		this.storeList = storeList;
	}


	public String getPaymentUrl() {
		return paymentUrl;
	}


	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}
	
	
}
