package com.froad.action.admin.seller.trans;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.froad.action.support.StoreActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.admin.trans.SellerTransActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.sellers.Seller;
import com.froad.client.trans.OrderType;
import com.froad.client.trans.Trans;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.command.MallCommand;
import com.froad.util.command.TransState;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-27  
 * @version 1.0
 */
public class MerchantTransAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 4746564449373583071L;
	private static final Logger logger=Logger.getLogger(MerchantTransAction.class);
	private SellerTransActionSupport sellerTransActionSupport;
	private TransActionSupport transActionSupport;
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	private String beCode;//商户管理员查询某个员工交易，作为查询条件userId|beCode
	private String beName;//查询交易条件
	private String phone;//手机号码
	private String gongHao;
	private Trans pager;
	private String isAdminSet;
	private Map<String,String> clerkMap = new HashMap<String,String>();
	private List<Store> stores = new ArrayList<Store>();
	private String storeId;
	private StoreActionSupport storeActionSupport;
	
	
	
	public String queryGroupTransFinance(){
		Merchant mt = (Merchant)getSession(SessionKey.MERCHANT);
		stores = storeActionSupport.getStoresByMerchantId(mt.getId());
		logger.info("商户查询团购交易开始");
		if(!Assert.empty(beName)){
			gongHao = beName;
		}
		if(pager==null)
			 pager=new Trans();
		
		if(!Assert.empty(pager.getPhone())){
			phone = pager.getPhone();
		}
		pager.setPageSize(10);//每页10条
		pager.setTransType(TranCommand.GROUP);
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
		pager.setMerchantId(m.getId());

		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		pager.setIsConsume("1");//已经消费
		
		
		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			//如果有操作员昵称查询条件工号找出来
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
				break;
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
			beCode = "999";
		}
		
		
		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecodeAuth(userId+"|"+beCode);					
				}
			}else{//普通操作员
				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
			}			
		}else{
			logger.info("==============非商户===============");
			return Action.SUCCESS;
		}
		//TODO:---------------------------------------------------------------------
		String becode = (String)getSession(SessionKey.BE_CODE);
		if(storeId == null || "".equals(storeId)){ //如果storeId为空，则是刚进入相关页面
			if(!"1000".equals(becode)) {//如果又不是管理员，则默认查询该财务人员所属的门店
				MerchantUserSet mus = new MerchantUserSet();
				mus.setUserId((String)getSession(SessionKey.USER_ID));
				mus.setBeCode(becode);
				storeId = merchantUserSetActionSupport.getMerchantUserSetList(mus).get(0).getBelongStoreId();
			}
		}
		String belongUserbecode = merchantUserSetActionSupport.getBelongUserBecode(storeId);
		pager.setBelongUserBecodeAuth(belongUserbecode);
		pager = transActionSupport.getGroupOrExchangeFinance(pager);
		
		//更新交易积分和价格信息改为单个商品的积分和价格
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
			//实体商品（目前只有实体商品会发送消费券）
			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType()))) 
					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());					
					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCostpriceTotal(costpriceTotal.toString());
				}
				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());					
					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setBankPointsValueRealAll(bankPoints.toString());
				}
				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());					
					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setFftPointsValueRealAll(fftPoints.toString());
				}
				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());					
					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCurrencyValueRealAll(currencyValue.toString());
				}
			}
			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
			}
			newList.add(t);
		}
		pager.getList().clear();
		pager.getList().addAll(newList);
		logger.info("商户查询兑换交易结束");
		return Action.SUCCESS;
	}
	
	public String queryTrans(){		

		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
					return "nopower";
				}
		logger.info("商户查询返利交易开始");
		if(!Assert.empty(beName)){
			gongHao = beName;
		}
		if(pager==null)
			 pager=new Trans();
		
		if(!Assert.empty(pager.getPhone())){
			phone = pager.getPhone();
		}
		pager.setPageSize(10);//每页10条
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		List<String>  typeList = new ArrayList<String>();
		typeList.add(TranCommand.POINTS_REBATE);
		typeList.add(TranCommand.COLLECT);
		typeList.add(TranCommand.PRESENT_POINTS);
		List<Seller>  sellerList = (List<Seller>)session.get(MallCommand.SELLER_LIST);
		List<Integer> sellerIdList= new ArrayList<Integer>();
		//List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
		for(Seller t:sellerList){
			if(TranCommand.POINTS_REBATE.equals(t.getSellerType())){
				sellerIdList.add(t.getId());				
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
			if(TranCommand.COLLECT.equals(t.getSellerType())){
				sellerIdList.add(t.getId());				
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
			if(TranCommand.PRESENT_POINTS.equals(t.getSellerType())){
				sellerIdList.add(t.getId());				
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
		}
		if(sellerIdList.size()==0)
			return Action.SUCCESS;
		//pager.setSellerId(String.valueOf(seller.getId()));
		pager.getSellerIdList().addAll(sellerIdList);
		
		//添加支付方式查询条件
		pager.getPayMethodList().addAll(typeList);//方付通积分支付
		
		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		
		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
			beCode = "999";
		}
		
		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecode(userId+"|"+beCode);					
				}
			}else{//普通操作员
				pager.setBelongUserBecode(userId+"|"+clerkBeCode);
			}			
		}
		
		pager = transActionSupport.getTransByPager(pager);
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			if(!Assert.empty(t.getBelongUserBecode()) && t.getBelongUserBecode().indexOf("|") != -1){
				String str1 = t.getBelongUserBecode().substring(t.getBelongUserBecode().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
				newList.add(t);
			}else{
				newList.add(t);
			}
		}
		pager.getList().clear();
		pager.getList().addAll(newList);
		logger.info("商户查询返利交易结束");
		return Action.SUCCESS;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>财务查询积分返利</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-11-22 下午04:59:39 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public String queryTransFinance(){
		Merchant m = (Merchant)getSession(SessionKey.MERCHANT);
		stores = storeActionSupport.getStoresByMerchantId(m.getId());
		logger.info("商户查询返利交易开始");
		if(!Assert.empty(beName)){
			gongHao = beName;
		}
		if(pager==null)
			 pager=new Trans();
		
		if(!Assert.empty(pager.getPhone())){
			phone = pager.getPhone();
		}
		pager.setPageSize(10);//每页10条
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		List<String>  typeList = new ArrayList<String>();
		typeList.add(TranCommand.POINTS_REBATE);
		typeList.add(TranCommand.COLLECT);
		typeList.add(TranCommand.PRESENT_POINTS);
		List<Seller>  sellerList = (List<Seller>)session.get(MallCommand.SELLER_LIST);
		List<Integer> sellerIdList= new ArrayList<Integer>();
		//List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
		for(Seller t:sellerList){
			if(TranCommand.POINTS_REBATE.equals(t.getSellerType())){
				sellerIdList.add(t.getId());				
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
			if(TranCommand.COLLECT.equals(t.getSellerType())){
				sellerIdList.add(t.getId());				
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
			if(TranCommand.PRESENT_POINTS.equals(t.getSellerType())){
				sellerIdList.add(t.getId());				
				logger.info("************************查詢積分返利交易的賣家sellerId為****************"+t.getId()+"*******************");
			}
		}
		if(sellerIdList.size()==0)
			return Action.SUCCESS;
		//pager.setSellerId(String.valueOf(seller.getId()));
		pager.getSellerIdList().addAll(sellerIdList);
		
		//添加支付方式查询条件
		pager.getPayMethodList().addAll(typeList);//方付通积分支付
		
		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		
		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
			beCode = "999";
		}
		
		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecode(userId+"|"+beCode);					
				}
			}else{//普通操作员
				pager.setBelongUserBecode(userId+"|"+clerkBeCode);
			}			
		}
		
		//TODO:---------------------------------------------------------------------
		String becode = (String)getSession(SessionKey.BE_CODE);
		if(storeId == null || "".equals(storeId)){ //如果storeId为空，则是刚进入相关页面
			if(!"1000".equals(becode)) {//如果又不是管理员，则默认查询该财务人员所属的门店
				MerchantUserSet mus = new MerchantUserSet();
				mus.setUserId((String)getSession(SessionKey.USER_ID));
				mus.setBeCode(becode);
				storeId = merchantUserSetActionSupport.getMerchantUserSetList(mus).get(0).getBelongStoreId();
			}
		}
		String belongUserbecode = merchantUserSetActionSupport.getBelongUserBecode(storeId);
		pager.setBelongUserBecode(belongUserbecode);
		pager = transActionSupport.getTransByPagerFinance(pager);
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			if(!Assert.empty(t.getBelongUserBecode()) && t.getBelongUserBecode().indexOf("|") != -1){
				String str1 = t.getBelongUserBecode().substring(t.getBelongUserBecode().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
				newList.add(t);
			}else{
				newList.add(t);
			}
		}
		pager.getList().clear();
		pager.getList().addAll(newList);
		logger.info("商户查询返利交易结束");
		return Action.SUCCESS;
	}
	
	public String queryGroupTrans(){
		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
			return "nopower";
		}
		logger.info("商户查询团购交易开始");
		if(!Assert.empty(beName)){
			gongHao = beName;
		}
		if(pager==null)
			 pager=new Trans();
		
		if(!Assert.empty(pager.getPhone())){
			phone = pager.getPhone();
		}
		pager.setPageSize(10);//每页10条
		pager.setTransType(TranCommand.GROUP);
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
		pager.setMerchantId(m.getId());
//		List<String>  typeList = new ArrayList<String>();
//		typeList.add(TranCommand.GROUP);
//		List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
//		if(sellerIdList.size()==0)
//			return Action.SUCCESS;
		//pager.setSellerId(String.valueOf(seller.getId()));
//		pager.getSellerIdList().addAll(sellerIdList);
		
		//添加支付方式查询条件
//		pager.getPayMethodList().addAll(typeList);//方付通积分支付
		
		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		pager.setIsConsume("1");//已经消费
		
		
		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			//如果有操作员昵称查询条件工号找出来
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
				break;
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
			beCode = "999";
		}
		
		
		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecodeAuth(userId+"|"+beCode);					
				}
			}else{//普通操作员
				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
			}			
		}else{
			logger.info("==============非商户===============");
			return Action.SUCCESS;
		}
		
		pager = transActionSupport.getGroupOrExchangeTransByPager(pager);
		
		//更新交易积分和价格信息改为单个商品的积分和价格
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
			//实体商品（目前只有实体商品会发送消费券）
			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType()))) 
					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());					
					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCostpriceTotal(costpriceTotal.toString());
				}
				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());					
					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setBankPointsValueRealAll(bankPoints.toString());
				}
				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());					
					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setFftPointsValueRealAll(fftPoints.toString());
				}
				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());					
					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCurrencyValueRealAll(currencyValue.toString());
				}
			}
			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
			}
			newList.add(t);
		}
		pager.getList().clear();
		pager.getList().addAll(newList);
		logger.info("商户查询兑换交易结束");
		return Action.SUCCESS;
	}

	/**
	 * 商户后台查询积分兑换交易记录
	 * @return
	 */
	public String queryExchangeTrans(){

		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
					return "nopower";
				}
		logger.info("商户查询兑换交易开始");
		if(!Assert.empty(beName)){
			gongHao = beName;
		}
		if(pager==null)
			 pager=new Trans();
		
		if(!Assert.empty(pager.getPhone())){
			phone = pager.getPhone();
		}
		pager.setPageSize(10);//每页10条
		pager.setTransType(TranCommand.POINTS_EXCH_PRODUCT);
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
		pager.setMerchantId(m.getId());
//		List<String>  typeList = new ArrayList<String>();
//		typeList.add(TranCommand.POINTS_EXCH_PRODUCT);//积分兑换："01"
//		List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
//		if(sellerIdList.size()==0)
//			return Action.SUCCESS;
//		//pager.setSellerId(String.valueOf(seller.getId()));
//		pager.getSellerIdList().addAll(sellerIdList);
//		
//		//添加支付方式查询条件
//		pager.getPayMethodList().addAll(typeList);//方付通积分支付
				
		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		pager.setIsConsume("1");//已经消费
		
		
		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
				break;
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999(返回的列表应该是查不到记录)
			beCode = "999";
		}
		
		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecode(userId+"|"+beCode);					
				}
			}else{//普通操作员
				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
			}			
		}else{
			logger.info("=============非商户============");
			return Action.SUCCESS;
		}
		
		
		
		pager = transActionSupport.getGroupOrExchangeTransByPager(pager);
		logger.info("查询兑换交易结果结束pager返回");
		//更新交易积分和价格信息改为单个商品的积分和价格
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		logger.info("设置兑换交易显示单价和操作员昵称");
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
			//实体商品（目前只有实体商品会发送消费券）
			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType()))) 
					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());					
					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCostpriceTotal(costpriceTotal.toString());
				}
				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());					
					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setBankPointsValueRealAll(bankPoints.toString());
				}
				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());					
					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setFftPointsValueRealAll(fftPoints.toString());
				}
				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());					
					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCurrencyValueRealAll(currencyValue.toString());
				}
			}
			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
			}
			newList.add(t);
		}
		logger.info("设置兑换交易显示单价和操作员昵称结束");
		pager.getList().clear();
		pager.getList().addAll(newList);
		logger.info("查询兑换交易结束");
		return Action.SUCCESS;
	}
	
	
	public String queryExchangeTransFinance(){
		Merchant mt = (Merchant)getSession(SessionKey.MERCHANT);
		stores = storeActionSupport.getStoresByMerchantId(mt.getId());
		logger.info("商户查询兑换交易开始");
		if(!Assert.empty(beName)){
			gongHao = beName;
		}
		if(pager==null)
			 pager=new Trans();
		
		if(!Assert.empty(pager.getPhone())){
			phone = pager.getPhone();
		}
		pager.setPageSize(10);//每页10条
		pager.setTransType(TranCommand.POINTS_EXCH_PRODUCT);
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
		pager.setMerchantId(m.getId());
//		List<String>  typeList = new ArrayList<String>();
//		typeList.add(TranCommand.POINTS_EXCH_PRODUCT);//积分兑换："01"
//		List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
//		if(sellerIdList.size()==0)
//			return Action.SUCCESS;
//		//pager.setSellerId(String.valueOf(seller.getId()));
//		pager.getSellerIdList().addAll(sellerIdList);
//		
//		//添加支付方式查询条件
//		pager.getPayMethodList().addAll(typeList);//方付通积分支付
				
		pager.setState(TransState.tran_success);
		pager.setOrderType(OrderType.DESC);
		pager.setIsConsume("1");//已经消费
		
		
		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setUserId(userId);
		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
		Set set=clerkMap.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext()) {
			Map.Entry entry=(Map.Entry)it.next();
			String name = (String)entry.getValue();
			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
				beCode=(String)entry.getKey();
				break;
			}
		}
		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999(返回的列表应该是查不到记录)
			beCode = "999";
		}
		
		//操作员查询交易条件
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		isAdminSet = isAdmin;
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//no set value
				if(!Assert.empty(beCode)){
					pager.setBelongUserBecode(userId+"|"+beCode);					
				}
			}else{//普通操作员
				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
			}			
		}else{
			logger.info("=============非商户============");
			return Action.SUCCESS;
		}
		//TODO:---------------------------------------------------------------------
		String becode = (String)getSession(SessionKey.BE_CODE);
		if(storeId == null || "".equals(storeId)){ //如果storeId为空，则是刚进入相关页面
			if(!"1000".equals(becode)) {//如果又不是管理员，则默认查询该财务人员所属的门店
				MerchantUserSet mus = new MerchantUserSet();
				mus.setUserId((String)getSession(SessionKey.USER_ID));
				mus.setBeCode(becode);
				storeId = merchantUserSetActionSupport.getMerchantUserSetList(mus).get(0).getBelongStoreId();
			}
		}
		String belongUserbecode = merchantUserSetActionSupport.getBelongUserBecode(storeId);
		pager.setBelongUserBecodeAuth(belongUserbecode);
		pager = transActionSupport.getGroupOrExchangeFinance(pager);
		logger.info("查询兑换交易结果结束pager返回");
		//更新交易积分和价格信息改为单个商品的积分和价格
		List<Object> templist = pager.getList();
		List<Trans> newList = new ArrayList<Trans>();
		logger.info("设置兑换交易显示单价和操作员昵称");
		for(int i=0;i< templist.size();i++){
			Trans t = (Trans)templist.get(i);
			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
			//实体商品（目前只有实体商品会发送消费券）
			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType()))) 
					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());					
					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCostpriceTotal(costpriceTotal.toString());
				}
				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());					
					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setBankPointsValueRealAll(bankPoints.toString());
				}
				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());					
					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
					t.setFftPointsValueRealAll(fftPoints.toString());
				}
				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());					
					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
					t.setCurrencyValueRealAll(currencyValue.toString());
				}
			}
			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
				t.setBelongUserBecode(clerkMap.get(str1));
			}
			newList.add(t);
		}
		logger.info("设置兑换交易显示单价和操作员昵称结束");
		pager.getList().clear();
		pager.getList().addAll(newList);
		logger.info("查询兑换交易结束");
		return Action.SUCCESS;
	}

    /**
     * 精品预售商户查询
     * @return
     */
    public String queryPresellTransFinance(){
    		Merchant mt = (Merchant)getSession(SessionKey.MERCHANT);
    		stores = storeActionSupport.getStoresByMerchantId(mt.getId());
    		logger.info("商户查询精品预售交易开始");
    		if(!Assert.empty(beName)){
    			gongHao = beName;
    		}
    		if(pager==null)
    			 pager=new Trans();

    		if(!Assert.empty(pager.getPhone())){
    			phone = pager.getPhone();
    		}
    		pager.setPageSize(10);//每页10条
    		pager.setTransType(TranCommand.PRE_SELL);
    		Map<String,Object> session=ActionContext.getContext().getSession();
    		String userId=(String)session.get("userId");
    		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
    		pager.setMerchantId(m.getId());

    		pager.setState(TransState.tran_success);
    		pager.setOrderType(OrderType.DESC);
    		pager.setIsConsume("1");//已经消费


    		MerchantUserSet merchantUserSet = new MerchantUserSet();
    		merchantUserSet.setUserId(userId);
    		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
    		Set set=clerkMap.entrySet();
    		Iterator it=set.iterator();
    		while(it.hasNext()) {
    			Map.Entry entry=(Map.Entry)it.next();
    			String name = (String)entry.getValue();
    			//如果有操作员昵称查询条件工号找出来
    			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
    				beCode=(String)entry.getKey();
    				break;
    			}
    		}
    		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
    			beCode = "999";
    		}


    		//操作员查询交易条件
    		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
    		isAdminSet = isAdmin;
    		String clerkBeCode=(String)session.get("beCode");
    		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
    		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
    			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
    				//no set value
    				if(!Assert.empty(beCode)){
    					pager.setBelongUserBecodeAuth(userId+"|"+beCode);
    				}
    			}else{//普通操作员
    				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
    			}
    		}else{
    			logger.info("==============非商户===============");
    			return Action.SUCCESS;
    		}
    		//TODO:---------------------------------------------------------------------
    		String becode = (String)getSession(SessionKey.BE_CODE);
    		if(storeId == null || "".equals(storeId)){ //如果storeId为空，则是刚进入相关页面
    			if(!"1000".equals(becode)) {//如果又不是管理员，则默认查询该财务人员所属的门店
    				MerchantUserSet mus = new MerchantUserSet();
    				mus.setUserId((String)getSession(SessionKey.USER_ID));
    				mus.setBeCode(becode);
    				storeId = merchantUserSetActionSupport.getMerchantUserSetList(mus).get(0).getBelongStoreId();
    			}
    		}
    		String belongUserbecode = merchantUserSetActionSupport.getBelongUserBecode(storeId);
    		pager.setBelongUserBecodeAuth(belongUserbecode);
    		pager = transActionSupport.getGroupOrExchangeFinance(pager);

    		//更新交易积分和价格信息改为单个商品的积分和价格
    		List<Object> templist = pager.getList();
    		List<Trans> newList = new ArrayList<Trans>();
    		for(int i=0;i< templist.size();i++){
    			Trans t = (Trans)templist.get(i);
    			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
    			//实体商品（目前只有实体商品会发送消费券）
    			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())))
    					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
//    				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
//    					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());
//    					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
//    					t.setCostpriceTotal(costpriceTotal.toString());
//    				}
//    				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
//    					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());
//    					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
//    					t.setBankPointsValueRealAll(bankPoints.toString());
//    				}
//    				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
//    					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());
//    					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
//    					t.setFftPointsValueRealAll(fftPoints.toString());
//    				}
//    				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
//    					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());
//    					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
//    					t.setCurrencyValueRealAll(currencyValue.toString());
//    				}
    			}
    			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
    				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
    				t.setBelongUserBecode(clerkMap.get(str1));
    			}
    			newList.add(t);
    		}
    		pager.getList().clear();
    		pager.getList().addAll(newList);
    		logger.info("商户查询精品预售交易结束");
    		return Action.SUCCESS;
    	}

    public String queryPresellTrans(){
   		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
   			return "nopower";
   		}
   		logger.info("商户查询精品预售交易开始");
   		if(!Assert.empty(beName)){
   			gongHao = beName;
   		}
   		if(pager==null)
   			 pager=new Trans();

   		if(!Assert.empty(pager.getPhone())){
   			phone = pager.getPhone();
   		}
   		pager.setPageSize(10);//每页10条
   		pager.setTransType(TranCommand.PRE_SELL);
   		Map<String,Object> session=ActionContext.getContext().getSession();
   		String userId=(String)session.get("userId");
   		Merchant m = (Merchant)session.get(SessionKey.MERCHANT);
   		pager.setMerchantId(m.getId());

   		pager.setState(TransState.tran_success);
   		pager.setOrderType(OrderType.DESC);
   		pager.setIsConsume("1");//已经消费


   		MerchantUserSet merchantUserSet = new MerchantUserSet();
   		merchantUserSet.setUserId(userId);
   		clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
   		Set set=clerkMap.entrySet();
   		Iterator it=set.iterator();
   		while(it.hasNext()) {
   			Map.Entry entry=(Map.Entry)it.next();
   			String name = (String)entry.getValue();
   			//如果有操作员昵称查询条件工号找出来
   			if(!Assert.empty(beName) && !Assert.empty(name) && (name.indexOf(beName) != -1 || beName.indexOf(name) != -1)) {
   				beCode=(String)entry.getKey();
   				break;
   			}
   		}
   		if(!Assert.empty(beName) && Assert.empty(beCode)){//没有匹配的操作员,默认查询的工号为999
   			beCode = "999";
   		}


   		//操作员查询交易条件
   		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
   		isAdminSet = isAdmin;
   		String clerkBeCode=(String)session.get("beCode");
   		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
   		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
   			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
   				//no set value
   				if(!Assert.empty(beCode)){
   					pager.setBelongUserBecodeAuth(userId+"|"+beCode);
   				}
   			}else{//普通操作员
   				pager.setBelongUserBecodeAuth(userId+"|"+clerkBeCode);
   			}
   		}else{
   			logger.info("==============非商户===============");
   			return Action.SUCCESS;
   		}

   		pager = transActionSupport.getGroupOrExchangeTransByPager(pager);

   		//更新交易积分和价格信息改为单个商品的积分和价格
   		List<Object> templist = pager.getList();
   		List<Trans> newList = new ArrayList<Trans>();
   		for(int i=0;i< templist.size();i++){
   			Trans t = (Trans)templist.get(i);
   			BigDecimal goodsNum = new BigDecimal(t.getTransDetailsList().get(0).getGoodsNumber());
   			//实体商品（目前只有实体商品会发送消费券）
   			if(TranCommand.TRAN_SUCCESS.equals(t.getState()) && (Assert.empty(t.getVirtualType()) || (!Assert.empty(t.getVirtualType()) && !TranCommand.VIRTUAL_TYPE.containsKey(t.getVirtualType())))
   					&& goodsNum != null && goodsNum.compareTo(new BigDecimal("1")) > 0){
//   				if(!Assert.empty(t.getCostpriceTotal()) && !"0".equals(t.getCostpriceTotal())){
//   					BigDecimal costpriceTotal=new BigDecimal(t.getCostpriceTotal());
//   					costpriceTotal = costpriceTotal.divide(goodsNum,2,RoundingMode.DOWN);
//   					t.setCostpriceTotal(costpriceTotal.toString());
//   				}
//   				if(!Assert.empty(t.getBankPointsValueRealAll()) && !"0".equals(t.getBankPointsValueRealAll())){
//   					BigDecimal bankPoints=new BigDecimal(t.getBankPointsValueRealAll());
//   					bankPoints = bankPoints.divide(goodsNum,2,RoundingMode.DOWN);
//   					t.setBankPointsValueRealAll(bankPoints.toString());
//   				}
//   				if(!Assert.empty(t.getFftPointsValueRealAll()) && !"0".equals(t.getFftPointsValueRealAll())){
//   					BigDecimal fftPoints=new BigDecimal(t.getFftPointsValueRealAll());
//   					fftPoints = fftPoints.divide(goodsNum,2,RoundingMode.DOWN);
//   					t.setFftPointsValueRealAll(fftPoints.toString());
//   				}
//   				if(!Assert.empty(t.getCurrencyValueRealAll()) && !"0".equals(t.getCurrencyValueRealAll())){
//   					BigDecimal currencyValue=new BigDecimal(t.getCurrencyValueRealAll());
//   					currencyValue = currencyValue.divide(goodsNum,2,RoundingMode.DOWN);
//   					t.setCurrencyValueRealAll(currencyValue.toString());
//   				}
   			}
   			if(!Assert.empty(t.getBelongUserBecodeAuth()) &&  t.getBelongUserBecodeAuth().indexOf("|") > 0){
   				String str1 = t.getBelongUserBecodeAuth().substring(t.getBelongUserBecodeAuth().indexOf("|")+1);
   				t.setBelongUserBecode(clerkMap.get(str1));
   			}
   			newList.add(t);
   		}
   		pager.getList().clear();
   		pager.getList().addAll(newList);
   		logger.info("商户查询精品预售交易结束");
   		return Action.SUCCESS;
   	}
	
	
	public SellerTransActionSupport getSellerTransActionSupport() {
		return sellerTransActionSupport;
	}
	public void setSellerTransActionSupport(
			SellerTransActionSupport sellerTransActionSupport) {
		this.sellerTransActionSupport = sellerTransActionSupport;
	}
	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}
	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}
	public String getBeCode() {
		return beCode;
	}
	public void setBeCode(String beCode) {
		this.beCode = beCode;
	}
	public Trans getPager() {
		return pager;
	}
	public void setPager(Trans pager) {
		this.pager = pager;
	}

	public String getIsAdminSet() {
		return isAdminSet;
	}

	public void setIsAdminSet(String isAdminSet) {
		this.isAdminSet = isAdminSet;
	}


	public String getGongHao() {
		return gongHao;
	}


	public void setGongHao(String gongHao) {
		this.gongHao = gongHao;
	}


	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	}


	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}


	public Map<String, String> getClerkMap() {
		return clerkMap;
	}


	public void setClerkMap(Map<String, String> clerkMap) {
		this.clerkMap = clerkMap;
	}


	public String getBeName() {
		return beName;
	}


	public void setBeName(String beName) {
		this.beName = beName;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public List<Store> getStores() {
		return stores;
	}

	public void setStores(List<Store> stores) {
		this.stores = stores;
	}

	public StoreActionSupport getStoreActionSupport() {
		return storeActionSupport;
	}

	public void setStoreActionSupport(StoreActionSupport storeActionSupport) {
		this.storeActionSupport = storeActionSupport;
	}

	
}
