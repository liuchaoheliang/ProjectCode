package com.froad.action.api.pointObtain;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.froad.action.api.BaseApiAction;
import com.froad.action.api.command.ClientCommon;
import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.ClientGoodsExchangeRackActionSupport;
import com.froad.action.support.GoodsExchangeRackActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.PointCashRuleActionSupport;
import com.froad.action.support.PointObtainActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.UserCertificationActionSupport;
import com.froad.action.support.trans.ExchangeActionSupport;
import com.froad.bean.ExchangeTempBean;
import com.froad.client.Store.Store;
import com.froad.client.buyers.Buyers;
import com.froad.client.clientGoodsExchangeRack.ClientGoodsExchangeRack;
import com.froad.client.hfcz.AppException_Exception;
import com.froad.client.hfcz.Hfcz;
import com.froad.client.lottery.Lottery;
import com.froad.client.point.PointsAccount;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
 
import com.froad.util.JsonUtil;
import com.froad.util.command.Command;

/*
 * 积分兑换
 */

public class PointObtainAction extends BaseApiAction {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PointObtainAction.class);
	
	private String reno;//操作结果码
	@SuppressWarnings("unused")
	private String remsg;//响应信息
	private Map<String,Object> rebody=new HashMap<String,Object>();//主体
	@SuppressWarnings("unused")
	private String recount;//返回请求标识号（原样返回）
	
	private PointObtainActionSupport pointObtainActionSupport; 
	private ClientGoodsExchangeRackActionSupport exchangeRackActionSupport; 
	private GoodsExchangeRackActionSupport goodsExchangeRackActionSupport;
	private UserCertificationActionSupport userCertificationActionSupport;
 	private PointCashRuleActionSupport pointCashRuleActionSupport;
	
 	private ExchangeActionSupport exchangeActionSupport;
	private UserActionSupport userActionSupport;
	@SuppressWarnings("unchecked")
	private BuyersActionSupport buyersActionSupport;
	private TransActionSupport transActionSupport;
	private PointActionSupport pointActionSupport;
	private String returnTime;//返回时间
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	 
	 

	/**积分兑换
	  * 方法描述：获取话费充值商品
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2013-3-1 下午4:29:29
	  */
	public String validate_hfcz(){
		logger.info("[PointObtainAction- validate_hfcz]body:"+body);
		JSONObject jsonObject = JSONObject.fromObject(body);
		String mobilephone = jsonObject.getString("czno");
		try {
			Hfcz hfcz = pointObtainActionSupport.checkParaCZNo(mobilephone);
			if(FroadAPICommand.CODE_FAIL.equals(hfcz.getRespCode())){
				reno="0401";
				return SUCCESS;
			}
			rebody.put("czno", mobilephone);
			rebody.put("area", hfcz.getArea());
			rebody.put("operater", hfcz.getOperater());
			ClientGoodsExchangeRack clientGoodsExchangeRack = new ClientGoodsExchangeRack();
			clientGoodsExchangeRack.setGoodsCategoryId(FroadAPICommand.GoodsCategory_HFCZ);
			clientGoodsExchangeRack.setPageSize(10);
			clientGoodsExchangeRack.setState(Command.STATE_START);
			clientGoodsExchangeRack.setIsRack(FroadAPICommand.ON_RACK);
			ClientGoodsExchangeRack  clientGoodsExchangeRackRe = exchangeRackActionSupport.getClientGoodsExchangeRackListByPager(clientGoodsExchangeRack);
			if(clientGoodsExchangeRackRe.getList().size() == 0) {
				reno="0402";
			} else {
				reno="0000";
				JSONArray array = new JSONArray();
				for (Object object : clientGoodsExchangeRackRe.getList()) {
					JSONObject goods = new JSONObject();
					BigDecimal fffratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_FFT_CASH);
					BigDecimal bankratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_BANK_CASH);
					ClientGoodsExchangeRack rack = (ClientGoodsExchangeRack)object;
					JSONObject json = ClientCommon.payTypeObject(JSONObject.fromObject(rack),rack.getCashPricing(),fffratio.toString(),bankratio.toString() );
					goods.put("payType", json);
					goods.put("mz", rack.getCashPricing());
					goods.put("goodsId", rack.getId());
					array.add(goods);
				}
				array = sortJSONArray(array);
				rebody.put("goodsList", array);
			}
		} catch (Exception e) {
			reno="0401";
			rebody.clear();
			logger.error("validate_hfcz 异常",e);
		}
		return SUCCESS;
	}
	public JSONArray sortJSONArray(JSONArray jsonArr){  
        JSONObject jObject = null;  
     for(int i = 0;i<jsonArr.size();i++){  
          String l =jsonArr.getJSONObject(i).get("mz").toString();  
         for(int j = i+1; j<jsonArr.size();j++){  
        	 String nl =jsonArr.getJSONObject(j).get("mz").toString();  
                 if(Double.parseDouble(l)>Double.parseDouble(nl)){  
                     jObject = jsonArr.getJSONObject(j);  
                     jsonArr.set(j, jsonArr.getJSONObject(i));  
                     jsonArr.set(i, jObject);  
                 }  
         }  
     }  
     return jsonArr;
}  
	
	/**
	  * 方法描述：彩票
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2013-3-1 下午6:40:55
	  */
	public String validate_lottery(){
		logger.info("[PointObtainAction- validate_lottery]body:"+body);
		try {
			//by_gm. 彩票返回制定数据
			Lottery lottery = pointObtainActionSupport.queryLotteryPeridListNow("FC_SSQ");
			rebody.put("period", lottery.getPeriod());
			
			rebody.put("endTime", lottery.getEndTime());
//			rebody.put("period", "2013038");
//			SimpleDateFormat sd  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//			Date d =DateUtil.getDateBefore(new Date(),-1);
//			rebody.put("endTime", sd.format(d)); 
			ClientGoodsExchangeRack clientGoodsExchangeRack = new ClientGoodsExchangeRack();
			clientGoodsExchangeRack.setGoodsCategoryId(FroadAPICommand.GoodsCategory_Lottery);
			clientGoodsExchangeRack.setPageSize(10);
			clientGoodsExchangeRack.setState(Command.STATE_START);
			clientGoodsExchangeRack.setIsRack(FroadAPICommand.ON_RACK);
			ClientGoodsExchangeRack  clientGoodsExchangeRackRe = exchangeRackActionSupport.getClientGoodsExchangeRackListByPager(clientGoodsExchangeRack);
			if(clientGoodsExchangeRackRe.getList()==null||clientGoodsExchangeRackRe.getList().size() == 0){
				throw new com.froad.client.lottery.AppException_Exception("2元双色球彩票未上架");
			}
			BigDecimal fffratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_FFT_CASH);
			BigDecimal bankratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_BANK_CASH);
			ClientGoodsExchangeRack rack = (ClientGoodsExchangeRack) clientGoodsExchangeRackRe.getList().get(0);
			JSONObject jsonObject = ClientCommon.payTypeObject(JSONObject.fromObject(rack),rack.getCashPricing(),fffratio.toString(),bankratio.toString() );
			rebody.put("payType", jsonObject);
			rebody.put("goodsId", rack.getId());
			reno="0000";
		} catch (Exception e) {
			reno="0402";
			logger.error("validate_lottery 异常",e);
		}
		return SUCCESS;
	}
	
	
	
	/**
	  * 方法描述：积分商品
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2013-3-1 下午6:40:48
	  */
	public String validate_goods(){
		logger.info("[PointObtainAction- validate_goods]body:"+body);
		try {
			ClientGoodsExchangeRack clientGoodsExchangeRack = new ClientGoodsExchangeRack();
			clientGoodsExchangeRack.setGoodsCategoryId(FroadAPICommand.GoodsCategory_PointsGoods);
			clientGoodsExchangeRack.setPageSize(100);
			clientGoodsExchangeRack.setState(Command.STATE_START);
			clientGoodsExchangeRack.setIsRack(FroadAPICommand.ON_RACK);
			ClientGoodsExchangeRack  clientGoodsExchangeRackRe = exchangeRackActionSupport.getClientGoodsExchangeRackListByPager(clientGoodsExchangeRack);
			if(clientGoodsExchangeRackRe.getList().size() == 0){
				throw new AppException_Exception("没有积分商品上架的积分商品");
			}
			List<Object> racks =  clientGoodsExchangeRackRe.getList();
			JSONArray array = new JSONArray();
			for (Object object : racks) {
				JSONObject goods = new JSONObject();
				ClientGoodsExchangeRack rack = (ClientGoodsExchangeRack)object;
				JSONObject json = JSONObject.fromObject(rack);
				BigDecimal fffratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_FFT_CASH);//FroadAPICommand.fftToCash);
				BigDecimal bankratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_BANK_CASH);//FroadAPICommand.bankToCash);
				JSONObject jsonObject = ClientCommon.payTypeObject(json,rack.getCashPricing(),fffratio.toString(),bankratio.toString() );
				goods.put("payType", jsonObject);		
				goods.put("goodsName", rack.getGoods().getGoodsName());
				goods.put("price", rack.getCashPricing());
				goods.put("marketPrice", rack.getGoods().getPrice()==null?"":rack.getGoods().getPrice());
				goods.put("imgUrl_b","");
				goods.put("imgUrl_s", "");
//				for(int img=0;img<rack.getClientGoodsExchangeRackImagesList().size();img++){
//					if(FroadAPICommand.pic_293x323.equals(rack.getClientGoodsExchangeRackImagesList().get(img).getPixel()) ){
//						goods.put("imgUrl_s", FroadAPICommand.makePicURL(rack.getClientGoodsExchangeRackImagesList().get(img).getImagesUrl()));
//					}
//					if(FroadAPICommand.pic_370x362.equals(rack.getClientGoodsExchangeRackImagesList().get(img).getPixel()) ){
//						goods.put("imgUrl_b", FroadAPICommand.makePicURL(rack.getClientGoodsExchangeRackImagesList().get(img).getImagesUrl()));
//					}
//				}
				if( rack.getClientGoodsExchangeRackImagesList().size() > 0 ){
					String url = rack.getClientGoodsExchangeRackImagesList().get(0).getImagesUrl();
//					int l = url.length();
//					url = url.substring(l-4) + FroadAPICommand.PIC_SAMLL + url.substring(0, l-4);
					goods.put("imgUrl_b",FroadAPICommand.makePicURL(url, FroadAPICommand.PIC_SAMLL));
					goods.put("imgUrl_s",FroadAPICommand.makePicURL(url, FroadAPICommand.PIC_BIG));
				}
				//goods.put("imgUrl", FroadAPICommand.makePicURL(rack.getGoods().getSourceUrl()));
				goods.put("desc", ClientCommon.getTxtWithoutHTMLElement(rack.getGoods().getGoodsDesc()));
				goods.put("store", rack.getGoods().getStore()==null?"":rack.getGoods().getStore());
				goods.put("goodsId", rack.getId());
				goods.put("sales", rack.getMarketTotalNumber()==null?"":rack.getMarketTotalNumber());
				if(rack.getMerchant()==null){
					goods.put("address","");
					goods.put("tel", "");
				}else{
					goods.put("address",  rack.getMerchant().getMstoreAddress()+" "+rack.getMerchant().getCompanyShortName());
					goods.put("tel",  rack.getMerchant().getCompanyTel() );
				}
				array.add(goods);
			}
			rebody.put("goodsList", array);
			reno="0000";
		} catch (Exception e) {
			reno="0402";
			logger.error("validate_goods 异常",e);
		} 
		return SUCCESS;
	}
	
	//查询用户及买家
	private  Buyers UserInfo(String userid) throws com.froad.client.user.AppException_Exception  {
		User userReq  = userActionSupport.queryUserAllByUserID(userid);
		Buyers buyers;
		if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
			buyers =buyersActionSupport.getBuyerByUserId(userid);
			if(buyers==null){
				logger.info("该用户不是买家。");
				reno="0302"; 
				return null;
			}
		}else{
			logger.info("该用户不存在。");
			reno="0101"; 
			return null;
		}
		return buyers;
	}
	private String checkPointAccount(User user,String fftPointPrice,String bankPointPrice){
		//1.验证积分账户积分
		 Map<String,PointsAccount> pointsTypePointsAccountMap = pointActionSupport.queryBankAndFftPointsByUserId(user.getUserID());
		if(!Assert.empty(pointsTypePointsAccountMap)){
			if(pointsTypePointsAccountMap.get("FFTPlatform")!=null){
				//trans.setFftPointsAccount(pointsTypePointsAccountMap.get("FFTPlatform").getAccountId());
				if(new BigDecimal(pointsTypePointsAccountMap.get("FFTPlatform").getPoints()).compareTo(new BigDecimal(fftPointPrice))<0){
					 reno="0505";
					 logger.info("addGoodsGroupTran：积分不足，已有"+pointsTypePointsAccountMap.get("FFTPlatform").getPoints()+"，需要"+fftPointPrice);
					 return null;
				}
			}else if(!"0".equals(fftPointPrice)){
				 reno="0501";
				 logger.info("addTranaddGoodsGroupTran：fftPointPrice没有可用的分分通积分，需要"+bankPointPrice);
				 return null;
			}
			if(pointsTypePointsAccountMap.get("ZHBank")!=null){
				//trans.setBankPointsAccount(pointsTypePointsAccountMap.get("ZHBank").getAccountId());
				if(new BigDecimal(pointsTypePointsAccountMap.get("ZHBank").getPoints()).compareTo(new BigDecimal(bankPointPrice))<0)
				{
					 reno="0505";
					 logger.info("addGoodsGroupTran：积分不足，已有"+pointsTypePointsAccountMap.get("ZHBank").getPoints()+"，需要"+bankPointPrice);
					 return null;
				}		 
			}else if(!"0".equals(bankPointPrice)){
				 reno="0501";
				 logger.info("addTranaddGoodsGroupTran：bankPointPrice没有可用的银行积分，需要"+bankPointPrice);
				 return null;
			}
		}else if(!ClientCommon.validatePriceNull(fftPointPrice)|| !ClientCommon.validatePriceNull(bankPointPrice)){
			 reno="0501";
			 logger.info("addTran_*：没有可用积分，需要bankPoint："+bankPointPrice+",fftPointPrice:"+fftPointPrice);
			 return null;
		}
		return "pass";
	}
	private Trans addTran(User user,ClientGoodsExchangeRack goodsRack ,String cashPrice,String fftPointPrice,String bankPointPrice,
			String goodsNum,String clientType ,String mobile,String period,String lotteryValue){
		
		String usePointRaioValue="";
		if(!ClientCommon.validatePriceNull(fftPointPrice)){
			usePointRaioValue=fftPointPrice;
		}else if(!ClientCommon.validatePriceNull(bankPointPrice)){
			usePointRaioValue=bankPointPrice;
		}
		if(checkPointAccount(user,fftPointPrice,bankPointPrice)==null){
			logger.info("积分校验失败。");
			return null;
		}
			
		//3.验证支付金额
		BigDecimal fffratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_FFT_CASH);
		BigDecimal bankratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_BANK_CASH);
		if( !ClientCommon.checkGoodsPrice(goodsNum,goodsRack.getCashPricing(), cashPrice, fftPointPrice, bankPointPrice, fffratio.toString(), bankratio.toString())){
			reno="0506";
			logger.info("addTran4_*：支付价格返回错误.");
			return null;
		}
		logger.info("usePointRaioValue:"+usePointRaioValue);
		if(!"1".equals(goodsNum)){
			cashPrice = new BigDecimal(cashPrice).divide(new BigDecimal(goodsNum)).setScale(0, BigDecimal.ROUND_UP).toString();
			logger.info("cashPrice:"+cashPrice);
			fftPointPrice = new BigDecimal(fftPointPrice).divide(new BigDecimal(goodsNum)).setScale(0, BigDecimal.ROUND_UP).toString();
			bankPointPrice = new BigDecimal(bankPointPrice).divide(new BigDecimal(goodsNum)).setScale(0, BigDecimal.ROUND_UP).toString();
		}
		//2.验证支付方式
		String payMethod = ClientCommon.payMethod(goodsRack, cashPrice, fftPointPrice, bankPointPrice);
		logger.info("payMethod:"+payMethod);
		if(payMethod == null || "".equals(payMethod)){
			 reno="0502";
			 logger.info("addTran_hfcz：支付方式payMethod返回错误.");
			 return null;
		 }
		ExchangeTempBean  tempBean= new ExchangeTempBean();
		 tempBean.setUserId(user.getUserID());
		 tempBean.setUser(user);
		 tempBean.setGoodsRackId(goodsRack.getId().toString());
		 tempBean.setGoodsCategoryId(goodsRack.getGoodsCategoryId());
		 tempBean.setSellerId(goodsRack.getSellerId().toString());
		 tempBean.setPayMethod(payMethod);
		 tempBean.setBuyNumber(Integer.parseInt(goodsNum));
		 tempBean.setUsePointRaioValue(usePointRaioValue);
		 tempBean.setMobile(mobile);
		 tempBean.setPeriod(period);
		 tempBean.setLotteryValue(lotteryValue);
		 tempBean.setClientType(clientType);
		 return exchangeActionSupport.makeExchangeTransForPhone(tempBean);
	}
	
	
	private String doTranPay(Trans trans,String cashPrice){
		 Trans transRes=transActionSupport.doTrans(trans);
		 String returnWords = ClientCommon.getTranReturnByPayMethod(trans.getPayMethod());
		 if(transRes == null){
			 reno="0503";
			 logger.error("addTran_hfcz：下单失败,返回trans异常.");
		  }else{
			  rebody.put("transId",transRes.getId());
			  logger.info("后台返回：code:"+transRes.getRespCode()  +"msg;"+transRes.getRespMsg()  );
			  if(TranCommand.RESP_CODE_ADD_FAIL.equals(transRes.getRespCode())){
				   reno="0503";
				   logger.error("addTran_hfcz：下单失败。下单返回："+transRes.getRespMsg());
				    
			  }else if(TranCommand.RESP_CODE_PAY_REQ_OK.equals(transRes.getRespCode())){
				  reno="0000";
				  rebody.put("desc", returnWords );
				  
			  }else if(TranCommand.RESP_CODE_PAY_REQ_FAIL.equals(transRes.getRespCode())){
				  reno="0504";
			  }else if(TranCommand.RESP_CODE_EXCEPTION.equals(transRes.getRespCode())){
				  reno="0504";
			  }else{
				  logger.info("未知返回。");
				  reno="0504";
			  }
		 }
		 if(!"0000".equals(reno)){
				if(!ClientCommon.validatePriceNull(cashPrice)) 
					 returnWords= "支付失败,请确认已经开通手机银行卡。";
				 else 
					  returnWords="支付失败。";
				rebody.put("desc", returnWords);
				if(transRes.getRespMsg().startsWith(TranCommand.EXCEPTION_PREFIX)){
					rebody.put("desc", transRes.getRespMsg());
				}
		}
		 return SUCCESS;
	}
	
//	//添加交易信息，校验价格  ---暂未使用
//	private Trans addTran( String goodsId,ClientGoodsExchangeRack goodsRack ,String userId,String goodsNum,String cashPrice,String fftPointPrice,String bankPointPrice){
//		Trans trans = new Trans();	 
//		TransDetails transDetails = new TransDetails();
//		transDetails.setGoodsRackId(goodsId);
//		transDetails.setGoodsNumber(goodsNum);
//		transDetails.setBankPointsValueRealAll(bankPointPrice);
//		transDetails.setFftPointsValueRealAll(fftPointPrice);
//		transDetails.setCurrencyValueReal(cashPrice);
//		trans.getTransDetailsList().add(transDetails);
//		trans.setCurrencyValueAll(cashPrice);
//		trans.setCurrencyValueRealAll(cashPrice);
//		trans.setBankPointsValueAll(bankPointPrice);
//		trans.setBankPointsValueRealAll(bankPointPrice);
//		trans.setFftPointsValueAll(fftPointPrice);
//		trans.setFftPointsValueRealAll(fftPointPrice);
//		//1.验证积分账户积分
//	    Map<String,PointsAccount> pointsTypePointsAccountMap =pointActionSupport.queryBankAndFftPointsByUserId(userId);
//		if(!Assert.empty(pointsTypePointsAccountMap)){
//			if(pointsTypePointsAccountMap.get("FFTPlatform")!=null){
//				trans.setFftPointsAccount(pointsTypePointsAccountMap.get("FFTPlatform").getAccountId());
//				if(new BigDecimal(pointsTypePointsAccountMap.get("FFTPlatform").getPoints()).compareTo(new BigDecimal(fftPointPrice))<0){
//					 reno="0505";
//					 logger.info("addTran_*：fftPointPrice积分不足，已有"+pointsTypePointsAccountMap.get("FFTPlatform").getPoints()+"，需要"+fftPointPrice);
//					 return null;
//				}
//						 
//			}else if(!"0".equals(fftPointPrice)){
//				 reno="0501";
//				 logger.info("addTran_*：fftPointPrice没有可用的分分通积分，需要"+fftPointPrice);
//				 return null;
//			}
//			if(pointsTypePointsAccountMap.get("ZHBank")!=null){
//				trans.setBankPointsAccount(pointsTypePointsAccountMap.get("ZHBank").getAccountId());
//				if(new BigDecimal(pointsTypePointsAccountMap.get("ZHBank").getPoints()).compareTo(new BigDecimal(bankPointPrice))<0)
//				{
//					 reno="0505";
//					 logger.info("addTran_*：bankPointPrice积分不足，已有"+pointsTypePointsAccountMap.get("ZHBank").getPoints()+"，需要"+bankPointPrice);
//					 return null;
//				}		 
//			}else if(!"0".equals(bankPointPrice)){
//				 reno="0501";
//				 logger.info("addTran_*：bankPointPrice没有可用的银行积分，需要"+bankPointPrice);
//				 return null;
//			}
//		}else if(!ClientCommon.validatePriceNull(fftPointPrice)|| !ClientCommon.validatePriceNull(bankPointPrice)){
//			 reno="0501";
//			 logger.info("addTran_*：没有可用积分，需要"+bankPointPrice);
//			 return null;
//		}
//		//3.验证支付金额
//		BigDecimal fffratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_FFT_CASH);
//		BigDecimal bankratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_BANK_CASH);
//		if( !ClientCommon.checkGoodsPrice(goodsNum,goodsRack.getCashPricing(), cashPrice, fftPointPrice, bankPointPrice, fffratio.toString(), bankratio.toString())){
//			reno="0506";
//			logger.info("addTran4_*：支付价格返回错误.");
//			return null;
//		}
//		if(!"1".equals(goodsNum)){
//			cashPrice = new BigDecimal(cashPrice).divide(new BigDecimal(goodsNum)).setScale(0, BigDecimal.ROUND_UP).toString();
//			fftPointPrice = new BigDecimal(fftPointPrice).divide(new BigDecimal(goodsNum)).setScale(0, BigDecimal.ROUND_UP).toString();
//			bankPointPrice = new BigDecimal(bankPointPrice).divide(new BigDecimal(goodsNum)).setScale(0, BigDecimal.ROUND_UP).toString();
//		}
//		//2.验证支付方式
//		String payMethod = ClientCommon.payMethod(goodsRack, cashPrice, fftPointPrice, bankPointPrice);
//		logger.info("payMethod:"+payMethod);
//		if(payMethod == null || "".equals(payMethod)){
//			 reno="0502";
//			 logger.info("addTran_hfcz：支付方式payMethod返回错误.");
//			 return null;
//		 }
//		trans.setPayMethod(payMethod);
//		
//		return trans;
//	}
//	//未使用
//	public UserCertification checkUserCertification(Buyers buyers,User user){
//		if(buyers.getBuyerChannelList().size()==0){
//			UserCertification cert = new UserCertification();
//			cert.setUserId(user.getUserID());
//			cert.setCertificationType(TranCommand.CHECK_PHONE);
//			cert.setPhone(user.getMobilephone());
//			cert.setChannelId(TranCommand.CHANNEL_ID);
//			cert.setState("30");
//			userCertificationActionSupport.addUserCertification(cert);
//		}
//		UserCertification userCertification=userCertificationActionSupport.getUserCertification(user.getUserID(),TranCommand.CHANNEL_ID);
//		if(userCertification==null){
//			reno="0306";
//			return null;
//		}
//		return userCertification;
//	}
	/**
	 * 
	  * 方法描述：话费下单，支付
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-1 下午06:59:44
	 */
 	public String addTran_hfcz() {
		logger.info("[PointObtainAction- addTran_hfcz]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userId = jsonObject.getString("userId");
			String goodsId = jsonObject.getString("goodsId");
			String clientType = jsonObject.getString("clientType");
			JSONObject goodsVal = jsonObject.getJSONObject("goodsVal");
			String mobile = goodsVal.getString("czno");
			if(Assert.empty(goodsId) || Assert.empty(mobile)){
				reno="0009";
				logger.error("addTran_hfcz：购买商品id或充值手机号码czno为空.");
				return SUCCESS;
			}
			String cashPrice="",fftPointPrice="",bankPointPrice="";
			try{
				 cashPrice = (String)JsonUtil.getOptionalMoneyValue(jsonObject,"cashPrice");  
				 fftPointPrice = (String)JsonUtil.getOptionalMoneyValue(jsonObject,"fftPointPrice");  
				 bankPointPrice = (String)JsonUtil.getOptionalMoneyValue(jsonObject,"bankPointPrice"); 
			}catch(NumberFormatException ne){
				reno="0506";
				logger.error("addTran_hfcz：支付价格有误。");
				return SUCCESS;
			}
			User user=userActionSupport.queryUserAllByUserID(userId);
			Buyers buyer =buyersActionSupport.getBuyerByUserId(userId); 
			ClientGoodsExchangeRack goodsRack = exchangeRackActionSupport.selectById(goodsId);
			if(!checkInput(goodsRack,user,buyer )){
				 return SUCCESS;
			}
//			UserCertification userCertification =checkUserCertification(buyer,user);
//			if(userCertification==null){
//				return SUCCESS;
//			}
			Trans trans =addTran(user,goodsRack , cashPrice, fftPointPrice, bankPointPrice,"1",clientType,  mobile,null,null);
			
			if(trans==null){
				logger.info("addTran 失败，客户端校验失败，下单终止。");
				return SUCCESS;
			}else if(!TranCommand.RESP_CODE_CREATE_OK.equals(trans.getRespCode())){
				reno="0503";
				logger.info("addTran 失败，客户端调用下单失败，下单终止。");
				rebody.put("desc", trans.getRespMsg());
				return SUCCESS;
			} 
			doTranPay(trans,cashPrice);
			 
		
		} catch ( Exception e) {
			reno="9999";
			logger.error("addTran_hfcz 异常",e);
		}
		return SUCCESS; 
	}
	/**
	 * 
	  * 方法描述：彩票下单，支付
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-1 下午06:59:44
	 */
	public String addTran_Lottery() {
		/*try {
		   body = URLDecoder.decode(body,  FroadAPICommand.Charset);
		} catch (UnsupportedEncodingException e1) {
			 logger.error("addTran_Lottery[URLDecoder.decode]"+e1);
		}*/
		logger.info("[PointObtainAction- addTran_Lottery]body:"+body);
		
		try {
			//input
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userId = jsonObject.getString("userId");
			String goodsId = jsonObject.getString("goodsId");
			String clientType = jsonObject.getString("clientType");
			JSONObject goodsVal = jsonObject.getJSONObject("goodsVal");
			String content = goodsVal.getString("content");
			String period = goodsVal.getString("period");
			String cashPrice="",fftPointPrice="",bankPointPrice="";
			try{
				 cashPrice = (String)JsonUtil.getOptionalMoneyValue(jsonObject,"cashPrice");  
				 fftPointPrice = (String)JsonUtil.getOptionalMoneyValue(jsonObject,"fftPointPrice");  
				 bankPointPrice = (String)JsonUtil.getOptionalMoneyValue(jsonObject,"bankPointPrice"); 
			}catch(NumberFormatException ne){
				reno="0506";
				logger.error("addTran_hfcz：支付价格有误。");
				return SUCCESS;
			}
			if(Assert.empty(goodsId) || Assert.empty(content)  || Assert.empty(period)){
				reno="0009";
				logger.error("addTran_Lottery：数据为空.goodsId："+goodsId+",投注号码："+content+",期号："+period);
				return SUCCESS;
			} 
			period= period.replace("第", "").replace("期","" );
			//Object
			User user=userActionSupport.queryUserAllByUserID(userId);
			Buyers buyer =buyersActionSupport.getBuyerByUserId(userId); 
			ClientGoodsExchangeRack goodsRack = exchangeRackActionSupport.selectById(goodsId);
			if(!checkInput(goodsRack,user,buyer)){
				 return SUCCESS;
			}
			/*UserCertification userCertification =checkUserCertification(buyer,user);
			if(userCertification==null){
				return SUCCESS;
			}*/
			
			Trans trans =addTran(user ,goodsRack , cashPrice, fftPointPrice, bankPointPrice,"1",clientType,  null,period,content);
			
			if(trans==null){
				logger.info("addTran 失败，客户端校验失败，下单终止。");
				return SUCCESS;
			}else if(!TranCommand.RESP_CODE_CREATE_OK.equals(trans.getRespCode())){
				reno="0503";
				logger.info("addTran 失败，客户端调用下单失败，下单终止。");
				rebody.put("desc", trans.getRespMsg());
				return SUCCESS;
			} 
			doTranPay(trans,cashPrice); 
			 
			
		} catch (Exception e) {
			reno="9999";
			logger.error("addTran_Lottery异常",e);
		}		
		return SUCCESS; 
	}
	
	
	
	/**
	 * 
	  * 方法描述：积分商品下单，支付
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-1 下午06:59:44
	 */
	public String addTran_goods() {
		logger.info("[PointObtainAction- addTran_goods]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userId = jsonObject.getString("userId");
			String goodsId = jsonObject.getString("goodsId");
			String goodsNum = jsonObject.getString("goodsNum");
			String clientType = jsonObject.getString("clientType");
			
			String cashPrice="",fftPointPrice="",bankPointPrice="";
			try{
				 cashPrice = (String)JsonUtil.getOptionalMoneyValue(jsonObject,"cashPrice");  
				 fftPointPrice = (String)JsonUtil.getOptionalMoneyValue(jsonObject,"fftPointPrice");  
				 bankPointPrice = (String)JsonUtil.getOptionalMoneyValue(jsonObject,"bankPointPrice"); 
				 if(goodsNum.indexOf(".")!=-1)
					 goodsNum = goodsNum.substring(0,goodsNum.indexOf("."));
			}catch(NumberFormatException ne){
				reno="0506";
				logger.error("addTran_goods：支付价格有误。");
				return SUCCESS;
			}
			if(Assert.empty(goodsId) || Assert.empty(goodsNum)  ){
				reno="0009";
				logger.error("addTran_goods：数据为空.goodsId："+goodsId+",数量："+goodsNum );
				return SUCCESS;
			} 
			//Object
			User user=userActionSupport.queryUserAllByUserID(userId);
			Buyers buyer =buyersActionSupport.getBuyerByUserId(userId); 
			ClientGoodsExchangeRack goodsRack = exchangeRackActionSupport.selectById(goodsId);
			if(!checkInput(goodsRack,user,buyer )){
				 return SUCCESS;
			}
			Trans trans =addTran(user,goodsRack , cashPrice, fftPointPrice, bankPointPrice,goodsNum,clientType,  user.getMobilephone(),null,null);
			if(trans==null){
				logger.info("addTran 失败，客户端校验失败，下单终止。");
				return SUCCESS;
			}else if(!TranCommand.RESP_CODE_CREATE_OK.equals(trans.getRespCode())){
				reno="0503";
				logger.info("addTran 失败，客户端调用下单失败，下单终止。");
				rebody.put("desc", trans.getRespMsg());
				return SUCCESS;
			}
			//调用接口下单支付
			doTranPay(trans,cashPrice); 
		} catch (Exception e) {
			reno="9999";
			logger.error("addTran_goods异常",e);
		}		
		return SUCCESS; 
	}
	
	public boolean checkInput(ClientGoodsExchangeRack goodsRack,User user,Buyers buyers ){
		boolean result = false;
		if(user == null||!Command.respCode_SUCCESS.equals(user.getRespCode())){
			logger.info("该用户不存在。");
			reno="0101"; 
			return result;
		}
		if(buyers==null){
			logger.info("该用户不是买家。");
			reno="0302"; 
			return result;
		}  
		if(goodsRack == null){
			reno="0402"; 
			logger.error("ClientGoodsExchangeRack为空.");
			return result;
		}
		return true;
	}
 
	
	public String getReno() {
		logger.info("调用结束[PointObtainAction]reno:"+reno+",remsg:"+getRemsg()+",rebody:"+rebody);
		return reno;
	}

	public void setReno(String reno) {
		this.reno = reno;
	}

	public String getRemsg() {
		return FroadAPICommand.getValidateMsg(reno);
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
	@JSON(serialize=false)
	public PointObtainActionSupport getPointObtainActionSupport() {
		return pointObtainActionSupport;
	}

	public void setPointObtainActionSupport(
			PointObtainActionSupport pointObtainActionSupport) {
		this.pointObtainActionSupport = pointObtainActionSupport;
	}
	@JSON(serialize=false)
	public ClientGoodsExchangeRackActionSupport getExchangeRackActionSupport() {
		return exchangeRackActionSupport;
	}

	public void setExchangeRackActionSupport(
			ClientGoodsExchangeRackActionSupport exchangeRackActionSupport) {
		this.exchangeRackActionSupport = exchangeRackActionSupport;
	}
	@JSON(serialize=false)
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}

	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	@SuppressWarnings("unchecked")
	@JSON(serialize=false)
	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	}

	@SuppressWarnings("unchecked")
	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
	}
	@JSON(serialize=false)
	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}
	
	@JSON(serialize=false)
	public GoodsExchangeRackActionSupport getGoodsExchangeRackActionSupport() {
		return goodsExchangeRackActionSupport;
	}
	
	public void setGoodsExchangeRackActionSupport(
			GoodsExchangeRackActionSupport goodsExchangeRackActionSupport) {
		this.goodsExchangeRackActionSupport = goodsExchangeRackActionSupport;
	}

	@JSON(serialize=false)
	public UserCertificationActionSupport getUserCertificationActionSupport() {
		return userCertificationActionSupport;
	}

	public void setUserCertificationActionSupport(
			UserCertificationActionSupport userCertificationActionSupport) {
		this.userCertificationActionSupport = userCertificationActionSupport;
	}
	@JSON(serialize=false)
	public PointActionSupport getPointActionSupport() {
		return pointActionSupport;
	}

	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}
	@JSON(serialize=false)
	public PointCashRuleActionSupport getPointCashRuleActionSupport() {
		return pointCashRuleActionSupport;
	}

	public void setPointCashRuleActionSupport(
			PointCashRuleActionSupport pointCashRuleActionSupport) {
		this.pointCashRuleActionSupport = pointCashRuleActionSupport;
	}
	
	public String getReturnTime() {
		
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	@JSON(serialize=false)
	public ExchangeActionSupport getExchangeActionSupport() {
		return exchangeActionSupport;
	}

	public void setExchangeActionSupport(ExchangeActionSupport exchangeActionSupport) {
		this.exchangeActionSupport = exchangeActionSupport;
	}
}
