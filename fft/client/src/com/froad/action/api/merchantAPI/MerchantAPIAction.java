package com.froad.action.api.merchantAPI;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.froad.action.api.BaseApiAction;
import com.froad.action.api.command.ClientCommon;
import com.froad.action.api.command.Encrypt;
import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.support.AuthTicketActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.SellersActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.admin.trans.SellerTransActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.client.authTicket.AuthTicket;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.Buyers;
import com.froad.client.user.User;
import com.froad.common.SellerCommand;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.DateUtil;
import com.froad.util.Result;
import com.froad.util.command.MallCommand;

public class MerchantAPIAction  extends BaseApiAction{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MerchantAPIAction.class);
	
	private TransActionSupport transActionSupport;
	private SellerTransActionSupport sellerTransActionSupport;
	private SellersActionSupport sellersActionSupport;
	private AuthTicketActionSupport authTicketActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	private UserActionSupport userActionSupport;
	private String reno;// 操作结果码
	private String remsg;// 响应信息
	private Map<String, Object> rebody = new HashMap<String, Object>();// 主体
	private String recount;// 返回请求标识号（原样返回）
	private String returnTime;//返回时间
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	
	/**
	 * 
	  * 方法描述：商户登录10011
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-4 下午04:01:22
	 */
	public String login(){
		JSONObject json = new JSONObject();
		logger.info("[MerchantAction- login]body:"+body);
		body = new Encrypt().clientDeEncrypt("10011",body);
		logger.info("解密后body"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String merchantName = jsonObject.getString("merchantName");
			String beCode = jsonObject.getString("beCode");
			String beCodePWD = jsonObject.getString("beCodePWD");
			String os = jsonObject.getString("os");
			logger.info("商户登录，平台："+os);
			if(Assert.empty(merchantName)||Assert.empty(beCode)||Assert.empty(beCodePWD)){
				reno="0009";
		 		return SUCCESS;
			}
			
			MerchantUserSet merchantUserSet = new MerchantUserSet();
			merchantUserSet.setBeCode(beCode);
			merchantUserSet.setLoginName(merchantName);
			List<MerchantUserSet> list =merchantUserSetActionSupport.getMerchantUserSetList(merchantUserSet);
			String userId="";
			if(Assert.empty(list)){
				logger.info("登录失败，商户不存在。");
				reno="10011";
		 		return SUCCESS;
			}else{
				MerchantUserSet userSet = (MerchantUserSet)list.get(0);
				boolean isPwdRight=new Md5PasswordEncoder().encodePassword(beCodePWD, merchantName+beCode).equals(userSet.getBeCodepwd());
//				System.out.println(userSet.getLockedDate());			
				userActionSupport.checkMerchant(isPwdRight, userSet, json);
				//判断是否为财务员
				if("1".equals(userSet.getRoleType())){
					reno="10025";
					return SUCCESS;
				}				
				//判断商户是否被锁定
				if("0".equals(userSet.getIsAccountLocked())){
					reno="0107";
					return SUCCESS;
				}
				if(Command.respCode_FAIL.equals(json.getString("reno"))){
					reno="0102";
					remsg=json.getString("msg");
					return SUCCESS;
				}
				
				if(isPwdRight){
					userId= userSet.getUserId();
					Merchant merchant = merchantActionSupport.getMerchantInfo(userId);
					if(merchant==null){
						reno="10011";
						return SUCCESS;
					}
					rebody.put("userId", userId );
					rebody.put("merchantType", merchant.getPreferentialType() );//1代表直接优惠  2代表积分返利
					rebody.put("merchantName", merchant.getMstoreShortName() );//
				}else{
					reno="10010";
					logger.info("登录失败，密码错误。");
			 		return SUCCESS;
				}
				logger.info("商户登录成功，userId:"+userId);
			}
			reno="0000";
		}catch(JSONException js){
			reno="0009";
			logger.error("sendTran 异常",js);
		}catch(Exception e){
			reno="9999";
			logger.error("versionSearch 异常",e);
		}
		return SUCCESS;
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
			logger.error("该商户不具有该卖家权限");
			return false;
		}
		
		List<SellerChannel> sellerChannelList = seller.getSellerChannelList();
		
		if(sellerChannelList == null || sellerChannelList.size() == 0){
			logger.error("该商户还没有绑定一种资金渠道");
			return false;
		}
		
		SellerChannel sellerChannel = sellerChannelList.get(0);
		if(Assert.empty(sellerChannel.getSellerRuleId()) || Assert.empty(sellerChannel.getSellerRule().getFftPointsRule())){
			logger.error("该商户还没有与我们签订卖家规则");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	  * 方法描述：手机银行卡收款  -发起10021
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-4 下午03:44:09
	 */
	public String sendTran(){
		logger.info("[MerchantAction- sendTran]body:"+body);
		body = new Encrypt().clientDeEncrypt("10021",body);
		logger.info("解密后body"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String tel = jsonObject.getString("tel");
			String amount = jsonObject.getString("amount");
			String reason = jsonObject.getString("reason");
			String userId = jsonObject.getString("userId");
			String beCode = jsonObject.getString("beCode");
			String os = jsonObject.getString("os");
			
			if(Assert.empty(userId)|| Assert.empty(os)||Assert.empty(beCode)||Assert.empty(tel)||Assert.empty(amount)||Assert.empty(reason) ){
				reno="0009";
		 		return SUCCESS;
			}
			
			Seller seller = new Seller();
			seller.setUserId(userId);
			//收款
			seller.setSellerType(SellerCommand.POINTS_REBATE);
			
			List<Seller>  sellerList = sellersActionSupport.getSellerListBySelective(seller);
			
			if(sellerList == null || sellerList.size() != 1) {
				reno = "10011";
				return SUCCESS;
			}
			
			seller = sellerList.get(0);
			
			//验证卖家
			if(!validateSeller(seller)){
				reno="10022";
				return SUCCESS;
			}
			
			//验证买家
			Buyers buyer = userActionSupport.bindingBuyer(tel,"FFT_MC");
			logger.info("收银台收款，开始验证买家账户");
			if(!TranCommand.RESP_CODE_IS_BUYER.equals(buyer.getRespCode())){
				logger.info("该手机号用户不能付款   " + buyer.getRespMsg());
				reno="0302";
				return SUCCESS;
			}
			logger.info("收银台收款，结束验证买家账户，买家编号： "+buyer.getId());
			logger.info("收银台收款，买家资金渠道编号： "+buyer.getBuyerChannelList().get(0).getId());
			
			String operator=userId+"|"+beCode;
			Trans trans = sellerTransActionSupport.createTran(reason, "1", amount, operator, tel,
					TranCommand.POINTS_REBATE,os, seller, buyer);
			
			if(trans == null) {
				reno="0009";
				return SUCCESS;
			}
			
			Result result = transActionSupport.pay(trans.getId());
			
			if(Result.SUCCESS.equals(result.getCode())){
				rebody.put("desc","收款发起成功");
			}else{
				rebody.put("desc","收款发起失败");
				if(result.getMsg().startsWith(TranCommand.EXCEPTION_PREFIX)){
					rebody.put("desc",result.getMsg());
				}
			}
			reno="0000";
		}catch(JSONException js){
			reno="0009";
			logger.error("sendTran 异常",js);
		}catch(Exception e){
			reno="9999";
			logger.error("sendTran 异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	  * 方法描述：手机银行卡收款  -确认 10022
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-4 下午03:44:09
	 */
	public String confirmTran(){
		logger.info("[MerchantAction- confirmTran]body:"+body);
		body = new Encrypt().clientDeEncrypt("10022",body);
		logger.info("解密后body"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String transId = jsonObject.getString("transId");
			String userId = jsonObject.getString("userId");
			String beCode = jsonObject.getString("beCode");
			User  userReq =  getUserInfo(userId);
			if(Assert.empty(userId)||Assert.empty(transId)||Assert.empty(beCode) ){
				reno="0009";
		 		return SUCCESS;
			}
			if(userReq==null){
				return SUCCESS;
			}    
			
			
			reno="0000";
		}catch(JSONException js){
			reno="0009";
			logger.error("confirmTran 异常",js);
		}catch(Exception e){
			reno="9999";
			logger.error("confirmTran 异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	  * 方法描述：发起积分赠送
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-4 下午04:03:27
	 */
	public String presentPoints(){
		logger.info("[MerchantAction- presentPoints]body:"+body);
		body = new Encrypt().clientDeEncrypt("10031",body);
		logger.info("解密后body"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String tel = jsonObject.getString("tel");
			String amount = jsonObject.getString("amount");
			String reason = jsonObject.getString("reason");
			String userId = jsonObject.getString("userId");
			String beCode = jsonObject.getString("beCode");
			String os = jsonObject.getString("os");
			
			if(Assert.empty(userId)|| Assert.empty(os)||Assert.empty(beCode)||Assert.empty(tel)||Assert.empty(amount)||Assert.empty(reason) ){
				reno="0009";
		 		return SUCCESS;
			}

			Seller seller = new Seller();
			seller.setUserId(userId);
			//收款
			seller.setSellerType(SellerCommand.PRESENT_POINTS);
			
			List<Seller>  sellerList = sellersActionSupport.getSellerListBySelective(seller);
			
			if(sellerList == null || sellerList.size() != 1) {
				reno = "10011";
				return SUCCESS;
			}
			
			seller = sellerList.get(0);
			
			//验证卖家
			if(!validateSeller(seller)){
				reno="10022";
				return SUCCESS;
			}
			
			//验证买家
			Buyers buyer = userActionSupport.bindingBuyer(tel,"FFT_MC");
			logger.info("收银台收款，开始验证买家账户");
			if(TranCommand.RESP_CODE_FAIL.equals(buyer.getRespCode())){
				logger.info("该手机号用户不能赠送积分   " + buyer.getRespMsg());
				reno="9999";
				return SUCCESS;
			}
			logger.info("收银台收款，结束验证买家账户，买家编号： "+buyer.getId());
			String operator=userId+"|"+beCode;
			Trans trans = sellerTransActionSupport.createTran(reason, "1", amount, operator, tel,
					TranCommand.PRESENT_POINTS,os, seller, buyer);    
			
			if(trans == null) {
				reno="0009";
				return SUCCESS;
			}
				
			
			Result result = transActionSupport.pay(trans.getId());
			
			if(Result.SUCCESS.equals(result.getCode())){
				rebody.put("desc", "赠送成功");
			}else{
				rebody.put("desc", "赠送失败");
				if(result.getMsg().startsWith(TranCommand.EXCEPTION_PREFIX)){
					rebody.put("desc", result.getMsg());
				}
			}
			reno="0000";
		}catch(JSONException js){
			reno="0009";
			logger.error("presentPoints 异常",js);
		}catch(Exception e){
			reno="9999";
			logger.error("presentPoints 异常",e);
		}
		return SUCCESS;
	}
	
	private String unitPrice(String price,String num){
		if(price == null || num == null)
			return null;
		else
			return new BigDecimal(price).divide(new BigDecimal(num),2,BigDecimal.ROUND_DOWN).toString();
	}
	
	/**
	 * 
	  * 方法描述：团购/积分兑换凭证查询10041
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-4 下午04:03:27
	 */
	public String queryGroup(){
		logger.info("[MerchantAPIAction- queryGroup]body:"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String credentialsNo  = jsonObject.getString("credentialsNo");
			String type = jsonObject.getString("type");
			String userId = jsonObject.getString("userId");
			  
			User  userReq =  getUserInfo(userId); 
			if(Assert.empty(userId)||Assert.empty(credentialsNo)||Assert.empty(type) ){
				reno="0009";
		 		return SUCCESS;
			}
			if(userReq==null){
				return SUCCESS;
			}  
			Merchant merchant = merchantActionSupport.getMerchantInfo(userId);
			if(merchant==null){
				reno="10011";
				return SUCCESS;
			}
			if("groupBuy".equals(type)){
				type="0";
			}else if("exchange".equals(type) ){
				type="1";
			}else{
				reno="10022";
				return SUCCESS;
			}
			AuthTicket authTicket = new AuthTicket();
			AuthTicket authTicketReq = new AuthTicket();
			Trans tran = new Trans();
			String status="";
			authTicket.setMerchantId(merchant.getId().toString());
			authTicket.setSecuritiesNo(credentialsNo);
			authTicket.setType(type);
			List<AuthTicket> authList = new ArrayList<AuthTicket>();
			authList = authTicketActionSupport.getAuthTickList(authTicket);
			if(authList != null && authList.size()>0){
				authTicketReq = authList.get(0);
				if(authTicketReq == null||authTicketReq.getTransId()==null){
					reno="10016";  //订单不存在
					return SUCCESS;
				}
				if("0".equals(authTicketReq.getIsConsume())){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
					String dataCurry = df.format(new Date());
					int resultNum = dataCurry.compareTo(authTicketReq.getExpireTime());
					if(resultNum>0){
						status="2";
						logger.info("团购或者兑换已经过期。");
					}else{
						logger.info("团购或者兑换未使用。");
						status="1";
					} 
					
				}else if("1".equals(authTicketReq.getIsConsume())){
					status="0";
				}
				logger.info("查詢此券的交易信息開始");
				tran = transActionSupport.getTransById(Integer.parseInt(authTicketReq.getTransId()));
				 
			}else{
				reno="10020";
				logger.info("团购或者兑换认证失败！没有查找到相关记录。");
				return SUCCESS;
			}		
		String goodsName="",goodsDesc="",goodsNumber="",goodsPic="",marketPrice="",price="",spendingTime="";
		if(tran != null && tran.getTransDetailsList() != null && tran.getTransDetailsList().size()>0){
			TransDetails detail =tran.getTransDetailsList().get(0);
			if(TranCommand.POINTS_EXCH_PRODUCT.equals(tran.getTransType())){//兑换
					if(detail.getClientGoodsExchangeRack()!=null && detail.getClientGoodsExchangeRack().getGoods()!=null){
						goodsName=   detail.getClientGoodsExchangeRack().getGoods().getGoodsName();
						goodsDesc=   detail.getClientGoodsExchangeRack().getGoods().getGoodsDesc();	
						goodsNumber= detail.getGoodsNumber();
						marketPrice= detail.getClientGoodsExchangeRack().getCashPricing();
						if(detail.getClientGoodsExchangeRack().getClientGoodsExchangeRackImagesList()!=null){
							List<com.froad.client.trans.ClientGoodsExchangeRackImages> images = detail.getClientGoodsExchangeRack().getClientGoodsExchangeRackImagesList();
							if(images.size() > 0 ){
									goodsPic=FroadAPICommand.makePicURL(images.get(0).getImagesUrl(),FroadAPICommand.PIC_SAMLL);
							}
						}
					}else{
						if(detail.getGoodsExchangeRack()!=null && detail.getGoodsExchangeRack().getGoods()!=null &&
								detail.getGoodsExchangeRack().getGoods().getGoodsName()!=null){
							goodsName=   detail.getGoodsExchangeRack().getGoods().getGoodsName();
							goodsDesc=   detail.getGoodsExchangeRack().getGoods().getGoodsDesc();	
							goodsNumber= detail.getGoodsNumber();
							marketPrice= detail.getGoodsExchangeRack().getGoods().getPrice();
							goodsPic=detail.getGoodsExchangeRack().getGoods().getSourceUrl();
						}
					}				
			}else if(TranCommand.GROUP.equals(tran.getTransType())){//团购
					if(detail.getClientGoodsGroupRack()!=null && detail.getClientGoodsGroupRack().getGoods()!=null ){
						goodsName=   detail.getClientGoodsGroupRack().getGoods().getGoodsName();
						goodsDesc=   detail.getClientGoodsGroupRack().getSeoDescription();	
						goodsNumber= detail.getGoodsNumber();
						marketPrice= detail.getClientGoodsGroupRack().getCashPricing();
						if(detail.getClientGoodsGroupRack().getClientGoodsGroupRackImagesList()!=null){
							List<com.froad.client.trans.ClientGoodsGroupRackImages>  images = detail.getClientGoodsGroupRack().getClientGoodsGroupRackImagesList();
							if(images.size() > 0 ){
								goodsPic=FroadAPICommand.makePicURL(images.get(0).getImagesUrl(),FroadAPICommand.PIC_SAMLL);
							}
						}
					}else{
						if(detail.getGoodsGroupRack()!=null && detail.getGoodsGroupRack().getGoods()!=null){
							goodsName=   detail.getGoodsGroupRack().getGoods().getGoodsName();
							goodsDesc=   detail.getGoodsGroupRack().getSeoDescription();	
							goodsNumber= detail.getGoodsNumber();
							marketPrice= detail.getGoodsGroupRack().getGoods().getPrice();
							goodsPic=FroadAPICommand.makePicURL(detail.getGoodsGroupRack().getGoods().getSourceUrl(),FroadAPICommand.PIC_SAMLL);
						}
					}
			} 
			String cash = unitPrice(tran.getCurrencyValueRealAll(),goodsNumber);
			String bankPoint = unitPrice(tran.getBankPointsValueRealAll(),goodsNumber);
			String fftPoint = unitPrice(tran.getFftPointsValueRealAll(),goodsNumber);
			spendingTime = tran.getConsumeTime();
			price =ClientCommon.payString(cash,fftPoint,bankPoint);
			
		}else{
			reno="10016";  //订单不存在
			return SUCCESS;
		}
			
			rebody.put("credentialsNo", credentialsNo);
			rebody.put("transId", tran.getId());
			rebody.put("buyerTel", tran.getPhone());
			rebody.put("goodsNumber", "1");
			rebody.put("tranDate", tran.getCreateTime());
			rebody.put("seoTitle", goodsName==null?"":goodsName);
			rebody.put("spendingTime", spendingTime==null?"":spendingTime);
			rebody.put("seoDescription", ClientCommon.getTxtWithoutHTMLElement(goodsDesc));
			rebody.put("marketPrice", marketPrice==null?"":marketPrice);
			rebody.put("price", price);
			rebody.put("goodsPic",FroadAPICommand.makePicURL(goodsPic) );
			rebody.put("status", status);
			rebody.put("desc", "");
			rebody.put("remark", "");
			rebody.put("remark1", "");
			
			
			reno="0000";
		}catch(JSONException js){
			reno="0009";
			logger.error("queryGroup 异常",js);
		}catch(Exception e){
			reno="9999";
			logger.error("queryGroup 异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	  * 方法描述：使用兑换券10042
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-4 下午04:03:27
	 */	
	public String inUseGroup(){
		logger.info("[MerchantAction- inUseGroup]body:"+body);
		body = new Encrypt().clientDeEncrypt("10042",body);
		logger.info("解密后body"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String credentialsNo = jsonObject.getString("credentialsNo");
			String beCode = jsonObject.getString("beCode");
			String userId = jsonObject.getString("userId");
			String transId = jsonObject.getString("transId");
			String type = jsonObject.getString("type");
			User  userReq =  getUserInfo(userId);
			if(Assert.empty(userId)||Assert.empty(credentialsNo)||Assert.empty(beCode)||Assert.empty(transId) ||Assert.empty(type)){
				reno="0009";
		 		return SUCCESS;
			}
			if(userReq==null){
				return SUCCESS;
			}  
			
			Merchant merchant = merchantActionSupport.getMerchantInfo(userId);
			if(merchant==null){
				reno="10011";
				return SUCCESS;
			}
			if(!"groupBuy".equals(type) && !"exchange".equals(type) ){
				reno="10022";
				return SUCCESS;
			}	
			AuthTicket authTicket = new AuthTicket();
			AuthTicket authTicketReq = new AuthTicket();
			authTicket.setMerchantId(merchant.getId().toString());
			authTicket.setSecuritiesNo(credentialsNo);
			List<AuthTicket> authList = new ArrayList<AuthTicket>();
			authList = authTicketActionSupport.getAuthTickList(authTicket);
			if(authList != null && authList.size()>0){
				authTicketReq = authList.get(0);
				if(authTicketReq == null||authTicketReq.getTransId()==null){
					reno="10016";
					return SUCCESS;
				}
				if("0".equals(authTicketReq.getIsConsume())){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
					String dataCurry = df.format(new Date());
					int resultNum = dataCurry.compareTo(authTicketReq.getExpireTime());
					if(resultNum>0){
						logger.info("团购或者兑换已经过期。");
						reno="10017";
						return SUCCESS;
					}else{
						logger.info("团购或者兑换未使用。");
						authTicket.setId(authTicketReq.getId());
						authTicket.setIsConsume("1");//消费券号
						String now=DateUtil.formatDate2Str(new Date());
						authTicket.setConsumeTime(now);//消费时间
						
						//记录操作员信息
						String BelongUserBecode=userId+"|"+beCode;
						authTicket.setBelongUserBecode(BelongUserBecode);
						
						authTicketActionSupport.updateAuthTicketState(authTicket);					
						Trans trans = new Trans();
						trans.setBelongUserBecode(userId+"|"+beCode);
						trans.setId( Integer.valueOf(transId) );
						transActionSupport.updateByTransId(trans);
					} 
					
				}else if("1".equals(authTicketReq.getIsConsume())){
					logger.info("团购或者兑换已经使用。");
					reno="10018";
					return SUCCESS;
				} 
			}else{
				reno="10020";
				logger.info("团购或者兑换认证失败！没有查找到相关记录。");
				return SUCCESS;
			} 
			
			reno="0000";
		}catch(JSONException js){
			reno="0009";
			logger.error("inUseGroup 异常",js);
		}catch(Exception e){
			reno="9999";
			logger.error("inUseGroup 异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	  * 方法描述：根据用户名查询该用户
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-19 上午11:00:00
	 */
	public  User getUserInfo(String userid) throws AppException_Exception{
		User userReq  = userActionSupport.queryUserAllByUserID(userid);
		if(!Command.respCode_SUCCESS.equals(userReq.getRespCode())){
			logger.info("该用户不存在。");
			reno="0101";
			return null;
		} 
		return userReq;
	}
	public String getReno() {
		logger.info("调用结束[MerchantAction]reno:"+reno+",remsg:"+getRemsg()+",rebody:"+rebody);
		return reno;
	}

	public void setReno(String reno) {
		this.reno = reno;
	}

	public String getRemsg() {
		if(Assert.empty(remsg)){
			return FroadAPICommand.getValidateMsg(reno);
		}
		return remsg;
	}

	public void setRemsg(String remsg) {
		this.remsg = remsg;
	}

	public Map<String, Object> getRebody() {
		 
		return rebody;
	}

	public void setRebody(Map<String, Object> rebody) {
		this.rebody = rebody;
	}

	public String getRecount() {
		return count;
	}

	public void setRecount(String recount) {
		this.recount = recount;
	} 
	public String getReturnTime() {
		
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	@JSON(serialize=false)
	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	} 
	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}

	@JSON(serialize=false)
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	} 
	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}

	@JSON(serialize=false)
	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}
	@JSON(serialize=false)
	public AuthTicketActionSupport getAuthTicketActionSupport() {
		return authTicketActionSupport;
	}

	public void setAuthTicketActionSupport(
			AuthTicketActionSupport authTicketActionSupport) {
		this.authTicketActionSupport = authTicketActionSupport;
	}
	@JSON(serialize=false)
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	@JSON(serialize=false)
	public SellerTransActionSupport getSellerTransActionSupport() {
		return sellerTransActionSupport;
	}


	public void setSellerTransActionSupport(
			SellerTransActionSupport sellerTransActionSupport) {
		this.sellerTransActionSupport = sellerTransActionSupport;
	}

	@JSON(serialize=false)
	public SellersActionSupport getSellersActionSupport() {
		return sellersActionSupport;
	}


	public void setSellersActionSupport(SellersActionSupport sellersActionSupport) {
		this.sellersActionSupport = sellersActionSupport;
	}
	
}
