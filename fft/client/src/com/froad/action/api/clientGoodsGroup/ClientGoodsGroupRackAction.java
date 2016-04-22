package com.froad.action.api.clientGoodsGroup;

import java.math.BigDecimal;
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
import com.froad.action.support.ClientGoodsGroupRackActionSupport;
import com.froad.action.support.GoodsGroupRackActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.PointCashRuleActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.action.support.TagActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.UserCertificationActionSupport;
import com.froad.action.support.trans.GroupActionSupport;
import com.froad.action.web.tran.TransAddtionalInfoVo;
import com.froad.bean.ExchangeTempBean;
import com.froad.client.Store.Store;
import com.froad.client.buyers.Buyers;
import com.froad.client.clientGoodsGroupRack.ClientGoodsGroupRack;
import com.froad.client.clientGoodsGroupRack.MerchantTrain;
import com.froad.client.point.PointsAccount;
import com.froad.client.tag.TagMAP;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
 
import com.froad.util.JsonUtil;
import com.froad.util.command.Command;
/*
 * 团购
 */
public class ClientGoodsGroupRackAction extends BaseApiAction {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ClientGoodsGroupRackAction.class);
	private ClientGoodsGroupRackActionSupport clientGoodsGroupRackActionSupport;
	private PointCashRuleActionSupport  pointCashRuleActionSupport;
	private TagActionSupport tagActionSupport;
	private UserActionSupport userActionSupport;
	@SuppressWarnings("unchecked")
	private BuyersActionSupport buyersActionSupport;
	private TransActionSupport transActionSupport;
	private PointActionSupport pointActionSupport;
	private GroupActionSupport groupActionSupport;
	private String reno;//操作结果码
	@SuppressWarnings("unused")
	private String remsg;//响应信息
	private Map<String,Object> rebody=new HashMap<String,Object>();//主体
	@SuppressWarnings("unused")
	private String recount;//返回请求标识号（原样返回）
	private String returnTime;//返回时间
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	/**
	 * 
	  * 方法描述： 	团购商品列表查询
	  * @param: 0901
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-3-14 下午02:06:25
	 */
	public String queryClientGoodsGroup(){
		logger.info("[ClientGoodsGroupRackAction- queryClientGoodsGroup]body:"+body);
		try {
			ClientGoodsGroupRack ClientGoodsGroup = new ClientGoodsGroupRack();
			ClientGoodsGroup.setState(Command.STATE_START);
			ClientGoodsGroup.setIsRack("1");
			//ClientGoodsGroup.setBeginTime("");
			
			ClientGoodsGroupRack clientGroupRack = clientGoodsGroupRackActionSupport.getGoodsGroupRackByPager(ClientGoodsGroup);
			if(clientGroupRack.getList()!=null){
				JSONArray goodsList = new JSONArray(); 
				for(int i=0;i<clientGroupRack.getList().size();i++){
					JSONObject goods = new JSONObject();
					ClientGoodsGroupRack cliengGroupGoods =(ClientGoodsGroupRack)clientGroupRack.getList().get(i);
					if(cliengGroupGoods.getGoods() == null || cliengGroupGoods.getGoods().getMerchant() == null)
						continue;
					
					List<TagMAP> tagList =tagActionSupport.getTagMapsByMerchantId(cliengGroupGoods.getGoods().getMerchantId()!=null?cliengGroupGoods.getGoods().getMerchantId():"");
					if(tagList!=null&&tagList.size()>0&&tagList.get(0).getTagClassifyA()!=null){
					 	goods.put("classify", tagList.get(0).getTagClassifyA().getTagValue()+"_"+tagList.get(0).getTagClassifyB().getTagValue());
					 	goods.put("district", tagList.get(0).getTagDistrictA().getTagValue()+"_"+tagList.get(0).getTagDistrictB().getTagValue());
					}
					goods.put("goodsId", cliengGroupGoods.getId());
					goods.put("seoTitle", cliengGroupGoods.getSeoTitle()==null?"":cliengGroupGoods.getSeoTitle());
					goods.put("seoDescription", cliengGroupGoods.getSeoDescription());
					goods.put("marketPrice", cliengGroupGoods.getGoods().getPrice()==null?"":cliengGroupGoods.getGoods().getPrice());
					goods.put("groupPrice", cliengGroupGoods.getGroupPrice()==null?"":cliengGroupGoods.getGroupPrice());
					if( cliengGroupGoods.getClientGoodsGroupRackImagesList().size() > 0){
						goods.put("goodsPic", FroadAPICommand.makePicURL(cliengGroupGoods.getClientGoodsGroupRackImagesList().get(0).getImagesUrl(),FroadAPICommand.PIC_THUMBNAIL));
					} else {
						goods.put("goodsPic", "");
					}

					goods.put("salesNum", cliengGroupGoods.getMarketTotalNumber()==null?"":cliengGroupGoods.getMarketTotalNumber());
					goods.put("companyName", cliengGroupGoods.getGoods().getMerchant().getCompanyShortName());
					goods.put("contactName", cliengGroupGoods.getGoods().getMerchant().getCompanyContactName());
					goods.put("contactTel", cliengGroupGoods.getGoods().getMerchant().getCompanyTel() );
					goods.put("hotLevel",cliengGroupGoods.getGoods().getMerchant().getHotLevel());
					goodsList.add(goods);
				}
				rebody.put("goodsList",goodsList);
			}
			reno="0000"; 
		
		}catch(Exception e){
			reno="9999";
			logger.error("queryClientGoodsGroup异常",e);
		}
		return SUCCESS;
	}
	/**
	 * 
	  * 方法描述： 	团购商品详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-3-14 下午02:06:41
	 */
	public String queryClientGroupDetail(){
		logger.info("[ClientGoodsGroupRackAction- queryClientGroupDetail]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String goodsRackId  = jsonObject.getString("goodsId");
			ClientGoodsGroupRack clientGroupRack = clientGoodsGroupRackActionSupport.getGoodsGroupRackById(goodsRackId );
			if(clientGroupRack!=null && "1".equals(clientGroupRack.getIsRack())){
				rebody.put("goodsId", clientGroupRack.getId());
				rebody.put("perminNumber", clientGroupRack.getPerminNumber()==null?"":clientGroupRack.getPerminNumber());
				rebody.put("perNumber", clientGroupRack.getPerNumber()==null?"":clientGroupRack.getPerNumber());
				rebody.put("seoTitle", clientGroupRack.getSeoTitle()==null?"":clientGroupRack.getSeoTitle());
				rebody.put("seoDescription", clientGroupRack.getSeoDescription()==null?"":clientGroupRack.getSeoDescription());
				rebody.put("marketPrice", clientGroupRack.getGoods().getPrice()==null?"":clientGroupRack.getGoods().getPrice());
				//rebody.put("groupPrice", clientGroupRack.getGroupPrice());
				rebody.put("beginTime", clientGroupRack.getBeginTime());
				rebody.put("endTime", clientGroupRack.getEndTime());
				rebody.put("ticketBeginTime", clientGroupRack.getTicketBeginTime());
				rebody.put("ticketEndTime", clientGroupRack.getTicketEndTime());
				rebody.put("storeTel", clientGroupRack.getGoods().getMerchant().getCompanyTel());
				rebody.put("storeAddress", clientGroupRack.getGoods().getMerchant().getMstoreAddress()+" "+clientGroupRack.getGoods().getMerchant().getCompanyShortName());
				rebody.put("salesNum", clientGroupRack.getMarketTotalNumber()==null?"":clientGroupRack.getMarketTotalNumber());
				rebody.put("instructions", clientGroupRack.getBuyKnow()==null||"".equals(clientGroupRack.getBuyKnow())?"本单无需预约，咨询请致电商家":clientGroupRack.getBuyKnow() );  //团购须知
				String collectNum="0";
				String clickNum="0";
				MerchantTrain merchantTran=clientGroupRack.getGoods().getMerchant().getMerchantTrain() ;
				//logger.info("==============merchantTranId======"+merchantTran.getId());
				if(merchantTran!=null){
					 collectNum= merchantTran.getCollectes()!=null?merchantTran.getCollectes():collectNum ;
					 clickNum= merchantTran.getClickes()!=null?merchantTran.getClickes():clickNum;
				}
				rebody.put("collectNum",collectNum);
				rebody.put("visitNum",clickNum);
				
				/*for(int j=0;j<clientGroupRack.getClientGoodsGroupRackImagesList().size();j++){
					if(FroadAPICommand.pic_440x267.equals( clientGroupRack.getClientGoodsGroupRackImagesList().get(j).getPixel())){
						rebody.put("goodsPic", FroadAPICommand.makePicURL(clientGroupRack.getClientGoodsGroupRackImagesList().get(j).getImagesUrl()));
							
					}
				}*/
				BigDecimal fffratio = pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_BANK_CASH);
				BigDecimal bankratio = pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_FFT_CASH);
				JSONObject clientGroupJson = JSONObject.fromObject(clientGroupRack);
				JSONObject payTypeList =ClientCommon.payTypeObject(clientGroupJson ,fffratio.toString(),bankratio.toString() ); 
		   
				if(clientGroupRack.getClientGoodsGroupRackImagesList()!=null){
					JSONArray images= new JSONArray();
					String pixel;
					for(int j=0;j<clientGroupRack.getClientGoodsGroupRackImagesList().size();j++){
						pixel =clientGroupRack.getClientGoodsGroupRackImagesList().get(j).getPixel();
						if(pixel==null||pixel.length()==0){
							JSONObject img = new JSONObject();
							img.put("imagesUrl", FroadAPICommand.makePicURL(clientGroupRack.getClientGoodsGroupRackImagesList().get(j).getImagesUrl(),FroadAPICommand.PIC_BIG));
							img.put("remark", clientGroupRack.getClientGoodsGroupRackImagesList().get(j).getRemark());
							images.add(img);
						}
						if(j == 0){
							rebody.put("goodsPic", FroadAPICommand.makePicURL(clientGroupRack.getClientGoodsGroupRackImagesList().get(j).getImagesUrl(),FroadAPICommand.PIC_SAMLL));
								
						}
					}
					rebody.put("images", images); 
				}
				 
				rebody.put("payType", payTypeList); 
				reno="0000";
			}else{
				reno="0402";
			} 
		}catch(Exception e){
			reno="9999";
			logger.error("queryClientGroupDetail异常",e);
		}
		return SUCCESS;
	}
	
	/**
	  * 
	  * 方法描述：客户端添加团购交易
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-3-4 下午02:01:32
	 */
	public String addGoodsGroupTran(){
		logger.info("[TranAction- addGoodsGroupTran]body:"+body);
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
				logger.error("addGoodsGroupTran：支付价格有误。");
				return SUCCESS;
			}
			JSONObject goodsVal=jsonObject.getJSONObject("goodsVal");
			String mobile = goodsVal.getString("tel");
			User user=userActionSupport.queryUserAllByUserID(userId);
			Buyers buyer =buyersActionSupport.getBuyerByUserId(userId);
			ClientGoodsGroupRack clientgroup = clientGoodsGroupRackActionSupport.getGoodsGroupRackById(goodsId);
			if(!check(clientgroup , user, buyer)){
				 return SUCCESS;
			}
			String usePointRaioValue="";
			if(!ClientCommon.validatePriceNull(fftPointPrice)){
				usePointRaioValue=fftPointPrice;
			}else if(!ClientCommon.validatePriceNull(bankPointPrice)){
				usePointRaioValue=bankPointPrice;
			}
			//1.验证积分账户积分
			 Map<String,PointsAccount> pointsTypePointsAccountMap = pointActionSupport.queryBankAndFftPointsByUserId(userId);
			if(!Assert.empty(pointsTypePointsAccountMap)){
				if(pointsTypePointsAccountMap.get("FFTPlatform")!=null){
					//trans.setFftPointsAccount(pointsTypePointsAccountMap.get("FFTPlatform").getAccountId());
					if(new BigDecimal(pointsTypePointsAccountMap.get("FFTPlatform").getPoints()).compareTo(new BigDecimal(fftPointPrice))<0){
						 reno="0505";
						 logger.info("addGoodsGroupTran：积分不足，已有"+pointsTypePointsAccountMap.get("FFTPlatform").getPoints()+"，需要"+fftPointPrice);
						 return SUCCESS;
					}
				}else if(!"0".equals(fftPointPrice)){
					 reno="0501";
					 logger.info("addTranaddGoodsGroupTran：fftPointPrice没有可用的分分通积分，需要"+bankPointPrice);
					 return SUCCESS;
				}
				if(pointsTypePointsAccountMap.get("ZHBank")!=null){
					//trans.setBankPointsAccount(pointsTypePointsAccountMap.get("ZHBank").getAccountId());
					if(new BigDecimal(pointsTypePointsAccountMap.get("ZHBank").getPoints()).compareTo(new BigDecimal(bankPointPrice))<0)
					{
						 reno="0505";
						 logger.info("addGoodsGroupTran：积分不足，已有"+pointsTypePointsAccountMap.get("ZHBank").getPoints()+"，需要"+bankPointPrice);
						 return SUCCESS;
					}		 
				}else if(!"0".equals(bankPointPrice)){
					 reno="0501";
					 logger.info("addTranaddGoodsGroupTran：bankPointPrice没有可用的银行积分，需要"+bankPointPrice);
					 return SUCCESS;
				}
			}else if(!ClientCommon.validatePriceNull(fftPointPrice)|| !ClientCommon.validatePriceNull(bankPointPrice)){
				 reno="0501";
				 logger.info("addTran_*：没有可用积分，需要bankPoint："+bankPointPrice+",fftPointPrice:"+fftPointPrice);
				 return SUCCESS;
			}
			//3.验证支付金额
			 BigDecimal fffratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_FFT_CASH);
			 BigDecimal bankratio =  pointCashRuleActionSupport.queryPointCashRule(TranCommand.PRICING_BANK_CASH);
			 if( !ClientCommon.checkGoodsPrice(goodsNum,clientgroup.getCashPricing(), cashPrice, fftPointPrice, bankPointPrice, fffratio.toString(), bankratio.toString())){
					reno="0506";
					logger.info("addTran4_*：支付价格返回错误.");
					return SUCCESS;
			 }
			//单价
			if(!"1".equals(goodsNum)){
				 cashPrice = new BigDecimal(cashPrice).divide(new BigDecimal(goodsNum)).setScale(0, BigDecimal.ROUND_UP).toString();
				 fftPointPrice = new BigDecimal(fftPointPrice).divide(new BigDecimal(goodsNum)).setScale(0, BigDecimal.ROUND_UP).toString();
				 bankPointPrice = new BigDecimal(bankPointPrice).divide(new BigDecimal(goodsNum)).setScale(0, BigDecimal.ROUND_UP).toString();
			} 
			//2.验证支付方式
			 String payMethod = ClientCommon.payMethod(clientgroup, cashPrice , fftPointPrice, bankPointPrice);
			 if(payMethod == null || "".equals(payMethod)){
				 reno="0502";
				 logger.info("addTran_hfcz：支付方式payMethod返回错误.");
				 return SUCCESS;
			 }
			 logger.info("======团购商品:"+goodsId+"的付款方式payMethod:"+payMethod);
			 
			
			 ExchangeTempBean  tempBean= new ExchangeTempBean();
			 tempBean.setUserId(userId);
			 tempBean.setUser(user);
			 tempBean.setGoodsRackId(clientgroup.getId().toString());
//			 tempBean.setGoodsCategoryId(clientgroup.getGoodsCategoryId());
			 tempBean.setSellerId(clientgroup.getSellerId().toString());
			 tempBean.setPayMethod(payMethod);
			 tempBean.setBuyNumber(Integer.parseInt(goodsNum));
			 tempBean.setUsePointRaioValue(usePointRaioValue);
			 tempBean.setMobile(mobile);	  
			 tempBean.setClientType(clientType);
				Trans trans = groupActionSupport.makeExchangeTransForPhone(tempBean);
				 if(trans==null){
						reno="0503";
						rebody.put("desc", "下单失败，请填写正确的数据。");
						logger.info("addTran 失败，下单终止。");
						return SUCCESS;
				} 
				
				 
			Trans transRes=transActionSupport.doTrans(trans);
			String returnWords = ClientCommon.getTranReturnByPayMethod(payMethod);
			  logger.info("后台返回：code:"+transRes.getRespCode()  +"msg;"+transRes.getRespMsg()  );
			  if(TranCommand.RESP_CODE_PAY_REQ_OK.equals(transRes.getRespCode())){
				  reno="0000";
				  rebody.put("desc", returnWords );
			  }else if(TranCommand.RESP_CODE_PAY_REQ_FAIL.equals(transRes.getRespCode())){
				  reno="0504";
			  }else if(TranCommand.RESP_CODE_ADD_FAIL.equals(transRes.getRespCode())){
				   reno="0503";
				   logger.error("addGoodsGroupTran：下单失败。下单返回："+transRes.getRespMsg());
			  }else if(TranCommand.RESP_CODE_EXCEPTION.equals(transRes.getRespCode())){
				  reno="0504";
			  }
			if(!"0000".equals(reno)){
//				if(!ClientCommon.validatePriceNull(cashPrice)) 
//					 returnWords= "支付失败,请确认已经开通手机银行卡。";
//				  else 
				returnWords="支付失败。";
				rebody.put("desc", returnWords);
				if(transRes.getRespMsg().startsWith(TranCommand.EXCEPTION_PREFIX)){
					rebody.put("desc", transRes.getRespMsg());
				}
			}
				
		} catch ( Exception e) {
			reno="9999";
			logger.error("addGoodsGroupTran 异常",e);
		}
		return SUCCESS;
		
	}
	
	public boolean check(ClientGoodsGroupRack goodsRack ,User user,Buyers buyer){
		boolean result = false;
		if(user == null){
			logger.info("该用户不存在。");
			reno="0101"; 
			return result;
		}
		if(buyer==null){
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
	
	//查询用户及买家
	public  Buyers UserInfo(String userid) throws AppException_Exception{
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
	
	
	
	public String getReno() {
		logger.info("调用结束[ClientGoodsGroupRackAction]reno:"+reno+",remsg:"+getRemsg()+",rebody:"+rebody);
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
	public ClientGoodsGroupRackActionSupport getClientGoodsGroupRackActionSupport() {
		return clientGoodsGroupRackActionSupport;
	}

	public void setClientGoodsGroupRackActionSupport(
			ClientGoodsGroupRackActionSupport clientGoodsGroupRackActionSupport) {
		this.clientGoodsGroupRackActionSupport = clientGoodsGroupRackActionSupport;
	}
	@JSON(serialize=false)
	public PointCashRuleActionSupport getPointCashRuleActionSupport() {
		return pointCashRuleActionSupport;
	}

	public void setPointCashRuleActionSupport(
			PointCashRuleActionSupport pointCashRuleActionSupport) {
		this.pointCashRuleActionSupport = pointCashRuleActionSupport;
	}
	@JSON(serialize=false)
	public TagActionSupport getTagActionSupport() {
		return tagActionSupport;
	}

	public void setTagActionSupport(TagActionSupport tagActionSupport) {
		this.tagActionSupport = tagActionSupport;
	}
	@JSON(serialize=false)
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}

	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	@JSON(serialize=false)
	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	}

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
	public PointActionSupport getPointActionSupport() {
		return pointActionSupport;
	}
	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}
	public String getReturnTime() {
		
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	@JSON(serialize=false)
	public GroupActionSupport getGroupActionSupport() {
		return groupActionSupport;
	}
	public void setGroupActionSupport(GroupActionSupport groupActionSupport) {
		this.groupActionSupport = groupActionSupport;
	}
	
}
