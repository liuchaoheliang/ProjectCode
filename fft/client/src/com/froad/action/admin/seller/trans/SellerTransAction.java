package com.froad.action.admin.seller.trans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.froad.action.support.TransActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.admin.trans.SellerTransActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSellerActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.user.Buyers;
import com.froad.client.merchantUserSeller.MerchantUserSeller;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.common.SellerCommand;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.Cache;
import com.froad.util.Result;
import com.froad.util.command.MallCommand;
import com.froad.util.command.PayCommand;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;





	/**
	 * 类描述：收银台 
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2013-4-19 下午1:32:44 
	 */
public class SellerTransAction extends BaseActionSupport {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -1513920035131904177L;
	private static final Logger logger = Logger.getLogger(SellerTransAction.class);	
	private SellerTransActionSupport sellerActionSupport;
	private TransActionSupport transActionSupport;
	private UserActionSupport userActionSupport;
	private MerchantUserSellerActionSupport merchantUserSellerActionSupport;
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	private String payResult;
	private String errorMessage;
	private List<OrderTypeVO> orderTypeVOList;
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	private Seller seller;
	private Trans trans;
	private String buyerPhone;
	private TransDetails tranDetail;
	
	
	private String beCode;//商户管理员查询某个员工交易，作为查询条件userId|beCode
	private Trans pager;
	
	private List<Seller>  sellerInfo = null;
	
	private List<String> sellerType = new ArrayList<String>();
	
	
	/**
	  * 方法描述：收银台首页
	  */
	public String getCashdeskInfo(){
		logger.info("===========准备进入收银台页面==========");
		Map<String,Object> session=ActionContext.getContext().getSession();
		Cache<Object> top10Cache = (Cache<Object>)session.get("top10Cache");
		Trans trans = new Trans();
		//普通操作员住查询本操作员的最近交易记录,管理员则查询所有
		String userId=(String)session.get("userId");
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		List<Seller>  sellerList = (List<Seller>)session.get(MallCommand.SELLER_LIST);
		
		
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				sellerInfo = sellerList;
				
				
			}else{//普通操作员
				
				MerchantUserSet clerk = new MerchantUserSet();
				clerk.setUserId(userId);
				clerk.setBeCode(clerkBeCode);
			    List<MerchantUserSet> clerkList = merchantUserSetActionSupport.getMerchantUserSetList(clerk);
				MerchantUserSeller condition = new MerchantUserSeller();
				condition.setMerchantUserId(clerkList.get(0).getId()+"");
				condition.setMerchantId(clerkList.get(0).getMerchantId()+"");
				
				
				//加载普通操作员权限
				List<MerchantUserSeller> userSet = merchantUserSellerActionSupport.getMerchantUserSellerList(condition);
				sellerInfo = new ArrayList<Seller>();
				for(int i = 0 ; i < userSet.size() ; i ++){
					Seller s = new Seller();
					s.setSellerType(userSet.get(i).getSellerType());
					s.setId(userSet.get(i).getId());
					sellerInfo.add(s);
				}
				
				trans.setPageSize(10);
				trans.setBelongUserBecode(userId+"|"+clerkBeCode);
			}			
		}
		
		
		//初始化前10交易
		if(top10Cache == null || System.currentTimeMillis() - top10Cache.getLastTime() >= top10Cache.getTime()){
			

			trans.setPageSize(10);
			
			
			
			List<Integer>  sellerIDList = trans.getSellerIdList();
			//判断是否具有送积分卖家
			for (Seller seller : sellerList) {
				if(SellerCommand.PRESENT_POINTS.equals(seller.getSellerType())){
					sellerIDList.add(seller.getId());
				}
				
				if(SellerCommand.POINTS_REBATE.equals(seller.getSellerType())){
					sellerIDList.add(seller.getId());
				}
				
				seller.setSellerTypeName(SellerCommand.SELLER_TYPE_NAME_MAP.get(seller.getSellerType()));
			}
			if(sellerIDList.size()>0){
				List<Object> top10TranList = transActionSupport.getTransByPager(trans).getList();
				if(top10TranList != null && top10TranList.size() > 0){
					top10Cache = new Cache<Object>(top10TranList, 30000, System.currentTimeMillis());
					session.put("top10Cache", top10Cache);
				}
			}
		} 
		
		if(top10Cache!=null&&top10Cache.getList()!=null){
			if(pager==null){
				pager=new Trans();
			}
			pager.getList().addAll(top10Cache.getList());
		}
		logger.info("===========跳转至收银台页面==========");
		
		if("1".equals(getSession(SessionKey.MERCHANT_ROLE))){
			return "finance";
		}
		
		return "success";
	}	
	
	/**
	  * 方法描述：验证卖家
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2013-4-6 下午1:11:45
	  */
	private boolean validateSeller(Seller seller){
		
		if(seller == null){
			errorMessage="您不具有该卖家权限";
			return false;
		}
		
		List<SellerChannel> sellerChannelList = seller.getSellerChannelList();
		
		if(sellerChannelList == null || sellerChannelList.size() == 0){
			errorMessage="您还没有绑定一种资金渠道";
			return false;
		}
		
		SellerChannel sellerChannel = sellerChannelList.get(0);
		if(Assert.empty(sellerChannel.getSellerRuleId()) || Assert.empty(sellerChannel.getSellerRule().getFftPointsRule())){
			errorMessage="您还没有与我们签订卖家规则";
			return false;
		}
		return true;
	}
	
	

	
	/**
	  * 方法描述：手机银行卡收款 下单
	  */
	public String createOrder(){

		
		//获取卖家信息
		Map<String,Object> session=ActionContext.getContext().getSession();
		
		//获取 seller 并验证
		if("failse".equals(querySellerBySellerType(SellerCommand.POINTS_REBATE))){
			return "check_fail";
		}
		
		
		//验证输入信息
		if(Assert.empty(buyerPhone)){
			errorMessage = "请输入手机号";
			return "check_fail";
		}
		if(tranDetail == null || Assert.empty(tranDetail.getGoodsNumber()) || tranDetail.getGoods() == null
				|| Assert.empty(tranDetail.getGoods().getGoodsName()) || Assert.empty(tranDetail.getGoods().getPrice())
				|| trans ==null || Assert.empty(trans.getSellerChannelId())){
			errorMessage = "接收参数有误";
			return "check_fail";
		}
		
		
		//验证卖家
		if(!validateSeller(seller)){
			return "check_fail";
		}
		
		//验证买家
		Buyers buyer = userActionSupport.bindingBuyer(buyerPhone,"FFT");
		logger.info("收银台收款，开始验证买家账户");
		if(!TranCommand.RESP_CODE_IS_BUYER.equals(buyer.getRespCode())){
			errorMessage = "该手机号用户不能付款";
			logger.info(errorMessage + "   " + buyer.getRespMsg());
			return "check_fail";
		}
		logger.info("收银台收款，结束验证买家账户，买家编号： "+buyer.getId());
		logger.info("收银台收款，买家资金渠道编号： "+buyer.getBuyerChannelList().get(0).getId());
		
		//操作工号
		String userBeCode = (String) session.get(MallCommand.USERID_BECODE);
		//数量
		String num = tranDetail.getGoodsNumber();
		//商品名（事由）
		
		String goodsName = tranDetail.getGoods().getGoodsName();
		//价格
		String price = tranDetail.getGoods().getPrice();
		
		
		trans = sellerActionSupport.createTran(goodsName, num, price, userBeCode, buyerPhone,
				TranCommand.POINTS_REBATE,PayCommand.CLIENT_PC, seller, buyer);
		
		if(trans == null){
			logger.error("交易的pay记录组装失败");
			errorMessage = "收款失败。";
			return "check_fail";
		}
		
		return Action.SUCCESS;
	}
	

	
	
	/**
	  * 方法描述：送积分订单
	  * 
	  */
	public String createSendPointsOrder(){
		//获取卖家信息
		Map<String,Object> session=ActionContext.getContext().getSession();
		
		//获取 seller 并验证
		if("failse".equals(querySellerBySellerType(SellerCommand.PRESENT_POINTS))){
			return "check_fail";
		}
		
		//验证输入信息
		if(Assert.empty(buyerPhone)){
			errorMessage = "请输入手机号";
			return "check_fail";
		}
		if(tranDetail == null || Assert.empty(tranDetail.getGoodsNumber()) || tranDetail.getGoods() == null
				|| Assert.empty(tranDetail.getGoods().getGoodsName()) || Assert.empty(tranDetail.getGoods().getPrice())
				|| trans ==null || Assert.empty(trans.getSellerChannelId())){
			errorMessage = "消费名称不能为空";
			return "check_fail";
		}
		
		//验证卖家
		if(!validateSeller(seller)){
			return "check_fail";
		}
		
		//验证买家
		Buyers buyer = userActionSupport.bindingBuyer(buyerPhone,"FFT");
		if(TranCommand.RESP_CODE_FAIL.equals(buyer.getRespCode())){
			logger.error("================使用手机号绑定会员失败，赠送积分失败================");
			errorMessage = "积分赠送失败，请稍后重试";
			return "check_fail";
		}
		
		//操作工号
		String userBeCode = (String) session.get(MallCommand.USERID_BECODE);
		//数量
		String num = tranDetail.getGoodsNumber();
		//商品名（事由）
		String goodsName = tranDetail.getGoods().getGoodsName();
		//价格
		String price = tranDetail.getGoods().getPrice();
		
		trans = sellerActionSupport.createTran(goodsName, num, price, userBeCode, buyerPhone,
				TranCommand.PRESENT_POINTS,PayCommand.CLIENT_PC, seller, buyer);
		
		if(trans == null){
			logger.error("交易的pay记录组装失败");
			errorMessage = "收款失败。";
			return "check_fail";
		}
		
		return Action.SUCCESS;
	}
	
	
	private String querySellerBySellerType(String sellerType) {
		//获取卖家信息
		Map<String,Object> session=ActionContext.getContext().getSession();
		
		List<Seller>  sellerList = (List<Seller>)session.get(MallCommand.SELLER_LIST);
		
		//判断是否具有送积分卖家
		for (Seller aSeller : sellerList) {
			if(sellerType.equals(aSeller.getSellerType())){
				seller = aSeller;
			}
		}
		
		//验证卖家
		if(!validateSeller(seller)){
			return "failse";
		}
		
		
		return Action.SUCCESS;
	}
	
	/**
	 * 
	  * 方法描述：进入到赠送页  查询 卖家信息
	 */
	public String sendPointsInfo(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//如果是管理员则直接进入收银台
				return querySellerBySellerType(SellerCommand.PRESENT_POINTS);
			}else{	
				//如果不是管理员则需要对普通操作员进行权限认证
				boolean isAllow = false;
				MerchantUserSet clerk = new MerchantUserSet();
				clerk.setUserId(userId);
				clerk.setBeCode(clerkBeCode);
			    List<MerchantUserSet> clerkList = merchantUserSetActionSupport.getMerchantUserSetList(clerk);
				MerchantUserSeller condition = new MerchantUserSeller();
				condition.setMerchantUserId(clerkList.get(0).getId()+"");
				condition.setMerchantId(clerkList.get(0).getMerchantId()+"");
				
				//加载普通操作员权限
				List<MerchantUserSeller> types = merchantUserSellerActionSupport.getMerchantUserSellerList(condition);
				for (MerchantUserSeller merchantUserSeller : types) {
					if("06".equals(merchantUserSeller.getSellerType())){
						isAllow = true;
						break;
					}
				}
				if(isAllow){
					return querySellerBySellerType(SellerCommand.PRESENT_POINTS);
				}else{
					//没有权限访问
					return "nopower";
				}
				
			}			
		}else{
			return "nopower";
		}
	}
	/**
	 * 
	 * 方法描述：进入到手机银行卡收款  查询 卖家信息
	 */
	public String receivePaymentInfo(){
		Map<String,Object> session=ActionContext.getContext().getSession();
		String userId=(String)session.get("userId");
		String isAdmin=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_CLERK);
		String clerkBeCode=(String)session.get("beCode");
		String isMerchant=(String)session.get(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){//商户
			if(!Assert.empty(isAdmin) && "1".equals(isAdmin)){//管理员
				//如果是管理员则直接进入收银台
				return querySellerBySellerType(SellerCommand.POINTS_REBATE);
			}else{	
				//如果不是管理员则需要对普通操作员进行权限认证
				boolean isAllow = false;
				MerchantUserSet clerk = new MerchantUserSet();
				clerk.setUserId(userId);
				clerk.setBeCode(clerkBeCode);
			    List<MerchantUserSet> clerkList = merchantUserSetActionSupport.getMerchantUserSetList(clerk);
				MerchantUserSeller condition = new MerchantUserSeller();
				condition.setMerchantUserId(clerkList.get(0).getId()+"");
				condition.setMerchantId(clerkList.get(0).getMerchantId()+"");
				
				//加载普通操作员权限
				List<MerchantUserSeller> types = merchantUserSellerActionSupport.getMerchantUserSellerList(condition);
				for (MerchantUserSeller merchantUserSeller : types) {
					if("03".equals(merchantUserSeller.getSellerType())){
						isAllow = true;
						break;
					}
				}
				if(isAllow){
					return querySellerBySellerType(SellerCommand.POINTS_REBATE);
				}else{
					//没有权限访问
					return "nopower";
				}
				
			}			
		}else{
			return "nopower";
		}
		
		
	}
	
	
	
	/**
	  * 方法描述：发起收款请求(收银台)
	  * @param: trans.id
	  * @return: 结果页面
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 22, 2013 6:31:27 PM
	  */
	public String collect(){
		if(trans == null || trans.getId()==null){
			logger.error("交易不存在或页面已过期！");
			payResult="error";
			errorMessage="交易不存在或页面已过期。";
			return Action.SUCCESS;
		}
		
		trans = transActionSupport.getTransById(trans.getId());
		
		if(trans == null || trans.getId() == null){
			logger.error("交易不存在");
			payResult="error";
			errorMessage="交易不存在。";
			return Action.SUCCESS;
		}
		
		if(trans.getBillNo() == null && TranCommand.TRAN_PROCESSING.equals(trans.getState())) {
			Result result = transActionSupport.pay(trans);
			if(Result.SUCCESS.equals(result.getCode())){
				payResult="waiting";
			}else{
				if(result.getMsg().startsWith(TranCommand.EXCEPTION_PREFIX)){
					errorMessage=result.getMsg();
				}
				payResult="fail";
			}
		}else{
			payResult="error";
			errorMessage="请不要重复发起收款。";
		}
		return Action.SUCCESS;
	}
	
	
	/**
	  * 方法描述：增送积分(收银台)
	  * @param: trans.id
	  * @return: 结果页面
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 22, 2013 6:33:44 PM
	  */
	public String presentPoints(){
		if(trans == null || trans.getId()==null){
			logger.error("交易不存在或页面已过期！");
			payResult="error";
			errorMessage="交易不存在或页面已过期。";
			return Action.SUCCESS;
		}
		
		trans = transActionSupport.getTransById(trans.getId());
		
		if(trans == null || trans.getId() == null){
			logger.error("交易不存在");
			payResult="error";
			errorMessage="交易不存在。";
			return Action.SUCCESS;
		}
		
		if(trans.getBillNo() == null && TranCommand.TRAN_PROCESSING.equals(trans.getState())) {
//			logger.info("=============发起赠送积分============");
//			Result result = transActionSupport.pay(trans);
//			logger.info("=============赠送积分发起完毕============");
//			if(Result.SUCCESS.equals(result.getCode())){
//				payResult="doing";//直接代扣,等待银行通知
//			}else{
//				if(result.getMsg().startsWith(TranCommand.EXCEPTION_PREFIX)){
//					errorMessage=result.getMsg();
//				}
//				payResult="fail";
//			}
			payResult="doing";
			threadPoolTaskExecutor.execute(new Runnable(){

				public void run() {
					logger.info("=============发起赠送积分============");
					Result result = transActionSupport.pay(trans);
					logger.info("=====赠送积分发起完毕，结果："+result.getMsg()+"=====");
				}
				
			});
		}else{
			payResult="error";
			errorMessage="赠送积分操作重复。";
		}
		return Action.SUCCESS;
	}


	public Trans getPager() {
		return pager;
	}

	public SellerTransActionSupport getSellerActionSupport() {
		return sellerActionSupport;
	}



	public void setSellerActionSupport(SellerTransActionSupport sellerActionSupport) {
		this.sellerActionSupport = sellerActionSupport;
	}



	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}



	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}



	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}



	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}



	public void setPager(Trans pager) {
		this.pager = pager;
	}

	public List<OrderTypeVO> getOrderTypeVOList() {
		return orderTypeVOList;
	}

	public void setOrderTypeVOList(List<OrderTypeVO> orderTypeVOList) {
		this.orderTypeVOList = orderTypeVOList;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public String getBeCode() {
		return beCode;
	}

	public void setBeCode(String beCode) {
		this.beCode = beCode;
	}


	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public TransDetails getTranDetail() {
		return tranDetail;
	}

	public void setTranDetail(TransDetails tranDetail) {
		this.tranDetail = tranDetail;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}
	public String getPayResult() {
		return payResult;
	}


	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public List<Seller> getSellerInfo() {
		return sellerInfo;
	}
	public void setSellerInfo(List<Seller> sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	public List<String> getSellerType() {
		return sellerType;
	}

	public void setSellerType(List<String> sellerType) {
		this.sellerType = sellerType;
	}
	public MerchantUserSellerActionSupport getMerchantUserSellerActionSupport() {
		return merchantUserSellerActionSupport;
	}
	public void setMerchantUserSellerActionSupport(
			MerchantUserSellerActionSupport merchantUserSellerActionSupport) {
		this.merchantUserSellerActionSupport = merchantUserSellerActionSupport;
	}




	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	}




	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}

	public void setThreadPoolTaskExecutor(
			ThreadPoolTaskExecutor threadPoolTaskExecutor) {
		this.threadPoolTaskExecutor = threadPoolTaskExecutor;
	}
	
}
