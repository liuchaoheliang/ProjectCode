package com.froad.action.api.tran;
 

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
import com.froad.action.support.ClientGoodsExchangeRackActionSupport;
import com.froad.action.support.ClientGoodsGroupRackActionSupport;
import com.froad.action.support.GoodsExchangeRackActionSupport;
import com.froad.action.support.GoodsGroupRackActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.client.buyers.Buyers;
import com.froad.client.clientGoodsExchangeRack.ClientGoodsExchangeRack;
import com.froad.client.clientGoodsExchangeRack.ClientGoodsExchangeRackImages;
import com.froad.client.clientGoodsGroupRack.ClientGoodsGroupRack;
import com.froad.client.clientGoodsGroupRack.ClientGoodsGroupRackImages;
import com.froad.client.goodsExchangeRack.GoodsExchangeRack;
import com.froad.client.goodsGroupRack.GoodsGroupRack;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.trans.TransGoodsAttribute;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.util.Command;
import com.froad.util.JsonUtil;
import com.froad.util.command.RuleDetailType;

public class TranAction extends BaseApiAction {
	private static Logger logger = Logger.getLogger(TranAction.class);
	
	private TransActionSupport transActionSupport;
	private GoodsGroupRackActionSupport goodsGroupRackActionSupport;
	private ClientGoodsGroupRackActionSupport clientGoodsGroupRackActionSupport;
	private GoodsExchangeRackActionSupport goodsExchangeRackActionSupport;
	private ClientGoodsExchangeRackActionSupport clientGoodsExchangeRackActionSupport;
	private BuyersActionSupport buyersActionSupport;
	
	private String reno;//操作结果码
	private String remsg;//响应信息
	private String returnTime;//返回时间
	private Map<String,Object> rebody=new HashMap<String,Object>();//主体
	private String recount;//返回请求标识号（原样返回）
	private UserActionSupport userActionSupport;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	//查询用户及买家
	public  String UserInfo(String userid) throws AppException_Exception{
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
		return buyers.getId().toString();
	}
	
	private String getNowDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
		return df.format(new Date());
	}
	private String unitPrice(String price,String num){
		if(price == null || num == null)
			return null;
		else
			return new BigDecimal(price).divide(new BigDecimal(num),2,BigDecimal.ROUND_DOWN).toString();
	}
	
	/**
	 * 
	  * 方法描述：我的团购
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-25 下午04:23:38
	 */
	public String queryGoodsGroup(){
		logger.info("[TranAction- queryGoodsGroup]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid = jsonObject.getString("userId");
			String beginTime = (String)JsonUtil.getOptionalValue(jsonObject,"startdate");  
			String endTime = (String)JsonUtil.getOptionalValue(jsonObject,"enddate"); 
			beginTime = beginTime.replace(" ", "|");
			endTime = getNowDate();
//			String buyerId =  UserInfo(userid);
			if(userid!=null){
				String desc="";
				Trans trans = new Trans();
				trans.setUserId(userid);
				trans.setUpdateTime(beginTime);
				trans.setBeginTime(beginTime);
				trans.setEndTime(endTime);
				trans.setState(FroadAPICommand.TRAN_SUCCESS);
				trans.setPageSize(100);
				trans.setTransType(FroadAPICommand.trans_type_group);
//				trans =transActionSupport.getTransByPager(trans);
				trans = transActionSupport.getGroupOrExchangeTransByPager(trans);
				if(trans.getList()!=null){ 
					JSONArray jsonlist = new JSONArray();
					for(int i=0;i<trans.getList().size();i++){
						JSONObject tranjson= new JSONObject();
						Trans traninfo = (Trans)trans.getList().get(i);
						tranjson.put("ticketId",traninfo.getAuthTicketId()==null||"".equals(traninfo.getAuthTicketId())?"h"+traninfo.getId():traninfo.getAuthTicketId());
						tranjson.put("companyName",traninfo.getMerchant()==null?"":traninfo.getMerchant().getCompanyFullName());
						tranjson.put("buyerTime",traninfo.getCreateTime());
						List<TransDetails> transDetailsList=traninfo.getTransDetailsList();
						TransDetails tranDetails = transDetailsList.get(0);
						
						String goodsRackId =tranDetails.getGoodsRackId();
						String goodsName="",smallPic="",bigPic="",price="",num="";
						String cash = traninfo.getCurrencyValueRealAll();
						String bankPoint = traninfo.getBankPointsValueRealAll();
						String fftPoint = traninfo.getFftPointsValueRealAll();
						desc = "消费地址：\r\n"+traninfo.getMerchant().getCompanyAddress()+"\r\n联系电话：\r\n"+traninfo.getMerchant().getCompanyTel()+" "+traninfo.getMerchant().getCompanyContactTel2();
						if(FroadAPICommand.Client_type.equals(traninfo.getClientType())){
							GoodsGroupRack goodsGroupRack=goodsGroupRackActionSupport.getGoodsGroupRackById(Integer.parseInt(goodsRackId));
							if(goodsGroupRack!=null&&goodsGroupRack.getId()!=null){
								if(goodsGroupRack.getGoods()==null){
									goodsGroupRack.getGoods().getGoodsName();
								}
								goodsName =goodsGroupRack.getGoods().getGoodsName();
								smallPic=goodsGroupRack.getGoods().getSmallUrl();
								bigPic =smallPic;
								price = goodsGroupRack.getCashPricing();
								desc=desc+"\r\n团购卷使用有效期：\r\n"+goodsGroupRack.getTicketBeginTime().replace("|", " ")+"至"+goodsGroupRack.getTicketEndTime().replace("|", " ");
							}else{
								continue;
							} 
							
						}else{
							ClientGoodsGroupRack clientgoodsGroupRack=clientGoodsGroupRackActionSupport.getGoodsGroupRackById(goodsRackId);
							if(clientgoodsGroupRack!=null){
								goodsName =clientgoodsGroupRack.getGoods().getGoodsName();
								price =  clientgoodsGroupRack.getCashPricing();
								//bigPic =clientgoodsGroupRack.getGoods().getBigUrl();
								desc=desc+"\r\n团购卷使用有效期：\r\n"+clientgoodsGroupRack.getTicketBeginTime().replace("|", " ")+"至"+clientgoodsGroupRack.getTicketEndTime().replace("|", " ");
								List<ClientGoodsGroupRackImages> list =clientgoodsGroupRack.getClientGoodsGroupRackImagesList();
								if(list.size() > 0){
									smallPic=FroadAPICommand.makePicURL(list.get(0).getImagesUrl(),FroadAPICommand.PIC_THUMBNAIL);
									bigPic=FroadAPICommand.makePicURL(list.get(0).getImagesUrl(),FroadAPICommand.PIC_SAMLL);
								}
							}else{
								continue;
							} 
							 
						}
						num = tranDetails.getGoodsNumber();
						cash = unitPrice(cash, num);
						bankPoint = unitPrice(bankPoint, num);
						fftPoint = unitPrice(fftPoint, num);
						tranjson.put("cashPrice",cash);
						tranjson.put("bankPoint",bankPoint);
						tranjson.put("fftPoint", fftPoint);
						tranjson.put("price",price );
						tranjson.put("goodsId",goodsRackId);
						tranjson.put("goodsName",goodsName);
						tranjson.put("goodsNumber","1");
						tranjson.put("smallPic",FroadAPICommand.makePicURL(smallPic));
						tranjson.put("bigPic",FroadAPICommand.makePicURL(bigPic));
						tranjson.put("payPrice",payString(cash,fftPoint,bankPoint));
						tranjson.put("desc",desc);
						jsonlist.add(tranjson);
					} 
					rebody.put("goodsGroupList", jsonlist);
					reno="0000";
				}
				
			}else{
				return SUCCESS;
			}
		} catch ( Exception e) {
			reno="9999";
			logger.error("queryGoodsGroup 异常",e);
		} 
		
		return SUCCESS;
		
		
	}
	 
	
	/**
	 * 
	  * 方法描述：我的兑换
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-25 下午04:23:58
	 */
	public String queryGoodsExchange(){
		logger.info("[TranAction- queryGoodsExchange]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid = jsonObject.getString("userId");
			String beginTime = (String)JsonUtil.getOptionalValue(jsonObject,"startdate");  
			String endTime = (String)JsonUtil.getOptionalValue(jsonObject,"enddate"); 
			beginTime = beginTime.replace(" ", "|");
			endTime = getNowDate();
//			String buyerId =  UserInfo(userid);
			if(userid!=null){
				String desc="";
				Trans trans = new Trans();
				trans.setUserId(userid);
				trans.setUpdateTime(beginTime);
				trans.setBeginTime(beginTime);
				trans.setEndTime(endTime);
				trans.setState(FroadAPICommand.TRAN_SUCCESS);
				trans.setPageSize(100);
				trans.setTransType(FroadAPICommand.trans_type_Exchange);
//				trans =transActionSupport.getTransByPager(trans);
				trans = transActionSupport.getGroupOrExchangeTransByPager(trans);
				if(trans.getList()!=null){
					String goodsName="",smallPic="",bigPic="",price="",num = "";
					JSONArray jsonlist = new JSONArray();
					for(int i=0;i<trans.getList().size();i++){
						JSONObject tranjson= new JSONObject();
						Trans traninfo = (Trans)trans.getList().get(i);
						List<TransGoodsAttribute> goodsAttributes = traninfo.getTransGoodsAttrList();
						if(goodsAttributes!=null && goodsAttributes.size()>0){
							desc="";
							for(TransGoodsAttribute attribute: goodsAttributes){
								desc += (!"1".equals(attribute.getGoodsRackAttribute().getIsRequired()))?"":attribute.getGoodsRackAttribute().getName()+":"+attribute.getElement()+" ";
							}
						}else{
							desc="取货地址：\r\n"+traninfo.getMerchant().getMstoreAddress()+" "+traninfo.getMerchant().getCompanyShortName() +
							      "\r\n联系电话：\r\n"+traninfo.getMerchant().getCompanyTel()+" "+traninfo.getMerchant().getCompanyContactTel2();	
						} 
						tranjson.put("ticketId",traninfo.getAuthTicketId()==null||"".equals(traninfo.getAuthTicketId())?"h"+traninfo.getId():traninfo.getAuthTicketId());
						tranjson.put("companyName",traninfo.getMerchant().getCompanyFullName());
						tranjson.put("buyerTime",traninfo.getCreateTime());
						List<TransDetails> transDetailsList=traninfo.getTransDetailsList();
						if(transDetailsList.size() == 0)
							continue;
						String goodsRackId =transDetailsList.get(0).getGoodsRackId();
						String cash = traninfo.getCurrencyValueRealAll();
						String bankPoint = traninfo.getBankPointsValueRealAll();
						String fftPoint = traninfo.getFftPointsValueRealAll();
						if(FroadAPICommand.Client_type.equals(traninfo.getClientType())){
							GoodsExchangeRack goodsExchangeRack=goodsExchangeRackActionSupport.selectById(goodsRackId);
							if(goodsExchangeRack!=null){
							 	goodsName = goodsExchangeRack.getGoods().getGoodsName();
							 	smallPic=goodsExchangeRack.getGoods().getSmallUrl();
							 	bigPic =smallPic;//goodsExchangeRack.getGoods().getBigUrl();
							 	price=goodsExchangeRack.getCashPricing();
							} else{
								continue;
							} 
							
						}else{
							ClientGoodsExchangeRack clientGoodsExchangeRack=clientGoodsExchangeRackActionSupport.selectById(goodsRackId);
							if(clientGoodsExchangeRack!=null){
								goodsName = clientGoodsExchangeRack.getGoods().getGoodsName();
								price=clientGoodsExchangeRack.getCashPricing();
								//bigPic =clientGoodsExchangeRack.getGoods().getBigUrl();
								List<ClientGoodsExchangeRackImages> list=clientGoodsExchangeRack.getClientGoodsExchangeRackImagesList();
								if(clientGoodsExchangeRack.getClientGoodsExchangeRackImagesList().size()  > 0){
									smallPic=FroadAPICommand.makePicURL(list.get(0).getImagesUrl(),FroadAPICommand.PIC_THUMBNAIL);
									bigPic=FroadAPICommand.makePicURL(list.get(0).getImagesUrl(),FroadAPICommand.PIC_SAMLL);
								}
							}else{
								continue;
							} 
						}
						num = transDetailsList.get(0).getGoodsNumber();
						cash = unitPrice(cash,num);
						bankPoint = unitPrice(bankPoint,num);
						fftPoint = unitPrice(fftPoint,num);
						tranjson.put("cashPrice", cash);
						tranjson.put("bankPoint", bankPoint);
						tranjson.put("fftPoint", fftPoint);
						tranjson.put("goodsId",goodsRackId);
						tranjson.put("goodsName",goodsName);
						tranjson.put("goodsNumber","1");
						tranjson.put("smallPic",FroadAPICommand.makePicURL(smallPic));
						tranjson.put("bigPic",FroadAPICommand.makePicURL(bigPic));
						
						tranjson.put("price",price);
						tranjson.put("payPrice",payString(cash,fftPoint,bankPoint));
						tranjson.put("desc",desc);
						//logger.info("====desc:"+desc);
						jsonlist.add(tranjson);
					}
					 rebody.put("goodsExchangeList", jsonlist);
					 reno="0000";
				}
			}else{
				return SUCCESS;
			}
		}catch(Exception e){
			reno="9999";
			logger.error(" 异常",e);
		}
		return SUCCESS;
	}
	
	public String payString(String cash,String fftPoint,String bankPoint){
		String payPrice="";
		if(!ClientCommon.validatePriceNull(cash)){
			payPrice+="金额："+cash;
		}
		if(!ClientCommon.validatePriceNull(fftPoint)){
			payPrice+=" 分分通积分："+fftPoint;
		}
		if(!ClientCommon.validatePriceNull(bankPoint)){
			payPrice+=" 银行积分："+bankPoint;
		}
		//logger.info("payString:"+payPrice);
		return payPrice;
	}
	
	/**
	 * 
	  * 方法描述：我的积分提现
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-25 下午06:06:28
	 */
	public String queryGoodsCarry(){
		logger.info("[TranAction- queryGoodsCarry]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid = jsonObject.getString("userId");
			String beginTime = (String)JsonUtil.getOptionalValue(jsonObject,"startdate");  
			String endTime = (String)JsonUtil.getOptionalValue(jsonObject,"enddate"); 
			String state = (String)JsonUtil.getOptionalValue(jsonObject,"state");  
			String buyerId =  UserInfo(userid);
			beginTime = beginTime.replace(" ", "|");
			endTime = getNowDate();
			if(buyerId!=null){
				Trans trans = new Trans();
				//trans.setBuyersId(buyerId );
				trans.setUserId(userid);
				trans.setUpdateTime(beginTime);
				trans.setBeginTime(beginTime);
				trans.setEndTime(endTime);
//				trans.setState(FroadAPICommand.TRAN_SUCCESS);
				trans.setPageSize(100);
				trans.setTransType(FroadAPICommand.trans_type_carry);
				if(!"".equals(state))
				  trans.setState(state);
				trans =transActionSupport.getTransByPager(trans);
				if(trans.getList()!=null){ 
					JSONArray carrylist = new JSONArray();
					for(int i=0;i<trans.getList().size();i++){
						JSONObject tranjson= new JSONObject();
						Trans traninfo = (Trans)trans.getList().get(i);
						tranjson.put("carryPoint",traninfo.getFftPointsValueAll());
						tranjson.put("money",traninfo.getCurrencyValueAll());		
						tranjson.put("serviceFee",traninfo.getFftFactorage());
						tranjson.put("state",FroadAPICommand.TRAN_PROCESSING.equals(traninfo.getState())?"交易处理中":FroadAPICommand.TRAN_SUCCESS.equals(traninfo.getState())?"交易成功":FroadAPICommand.TRAN_FAIL.equals(traninfo.getState())?"交易失败":"");
						tranjson.put("createTime",traninfo.getCreateTime());
						carrylist.add(tranjson);
					}
					rebody.put("goodsCarryList", carrylist);
					reno="0000";
				}
			}else{
				return SUCCESS;
			}
		} catch ( Exception e) {
			reno="9999";
			logger.error("queryGoodsCarry 异常",e);
		} 
		return SUCCESS;
	}
	
	public String getReno() {
		logger.info("调用结束[TranAction]reno:"+reno+",remsg:"+getRemsg()+",rebody:"+rebody);
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
	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	} 
	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}
	@JSON(serialize=false)
	public GoodsGroupRackActionSupport getGoodsGroupRackActionSupport() {
		return goodsGroupRackActionSupport;
	}

	public void setGoodsGroupRackActionSupport(
			GoodsGroupRackActionSupport goodsGroupRackActionSupport) {
		this.goodsGroupRackActionSupport = goodsGroupRackActionSupport;
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
	public GoodsExchangeRackActionSupport getGoodsExchangeRackActionSupport() {
		return goodsExchangeRackActionSupport;
	}

	public void setGoodsExchangeRackActionSupport(
			GoodsExchangeRackActionSupport goodsExchangeRackActionSupport) {
		this.goodsExchangeRackActionSupport = goodsExchangeRackActionSupport;
	}
	@JSON(serialize=false)
	public ClientGoodsExchangeRackActionSupport getClientGoodsExchangeRackActionSupport() {
		return clientGoodsExchangeRackActionSupport;
	}

	public void setClientGoodsExchangeRackActionSupport(
			ClientGoodsExchangeRackActionSupport clientGoodsExchangeRackActionSupport) {
		this.clientGoodsExchangeRackActionSupport = clientGoodsExchangeRackActionSupport;
	}
	@JSON(serialize=false)
	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	}

	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
	}
	@JSON(serialize=false)
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}
	 
	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	public String getReturnTime() {
		
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	
}
