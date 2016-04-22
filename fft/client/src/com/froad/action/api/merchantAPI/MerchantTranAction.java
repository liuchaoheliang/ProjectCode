package com.froad.action.api.merchantAPI;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.froad.action.api.BaseApiAction;
import com.froad.action.api.command.ClientCommon;
import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.admin.trans.SellerTransActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.trans.Goods;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.trans.TransGoodsAttribute;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.common.TranCommand;
import com.froad.util.Command;
import com.froad.util.JsonUtil;

public class MerchantTranAction extends BaseApiAction{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MerchantTranAction.class);
	private UserActionSupport userActionSupport;
	private TransActionSupport transActionSupport;
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	private SellerTransActionSupport sellerTransActionSupport;
	private String reno;// 操作结果码
	private String remsg;// 响应信息
	private Map<String, Object> rebody = new HashMap<String, Object>();// 主体
	private String recount;// 返回请求标识号（原样返回）
	private String returnTime;//返回时间
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	
	/**
	 * 
	  * 方法描述：团购订单查询
	  * @param:   10051
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-4 下午04:26:22
	 */
	public String queryGroupTran(){
		logger.info("[MerchantTranAction- queryGroupTran]body:"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userId = jsonObject.getString("userId");
			String beCode =  jsonObject.getString("beCode"); 
			String enddate =  ((String)JsonUtil.getOptionalValue(jsonObject,"enddate")).replace(" ", "|");  
			String startdate =  ((String)JsonUtil.getOptionalValue(jsonObject,"startdate")).replace(" ", "|");
			
			enddate = getNowDate();
			
//			if(!clerkMap.containsKey(beCode)){
//				return SUCCESS;
//			}
//			
//			List<String>  typeList = new ArrayList();
//			typeList.add(TranCommand.GROUP);
//			List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
//			if(sellerIdList.size() == 0){
//				reno = "10022";
//				return SUCCESS;
//			}
			
			
			MerchantUserSet merchant=getMerchantUserInfo(userId,beCode);
			if(merchant==null){
				return SUCCESS;
			}
			
			MerchantUserSet merchantUserSet = new MerchantUserSet();
			merchantUserSet.setUserId(userId);
			Map<String,String> clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
			
			Trans trans = new Trans();
			trans.setMerchantId(Integer.parseInt(merchant.getMerchantId()));
//			trans.getSellerIdList().addAll(sellerIdList);
			trans.setState(TranCommand.TRAN_SUCCESS);
			trans.setPageSize(100);
			trans.setUpdateTime(startdate);
			trans.setBeginTime(startdate);
			trans.setEndTime(enddate);
			trans.setTransType(FroadAPICommand.trans_type_group);
			trans.setIsConsume("1");//已消费
			if(!"".equals(beCode) && !"1000".equals(beCode))
				trans.setBelongUserBecodeAuth(userId+"|"+beCode);
//			trans =transActionSupport.getTransByPager(trans);
			trans = transActionSupport.getGroupOrExchangeTransByPager(trans);
			JSONArray jsonlist = new JSONArray();
			if(trans.getList()!=null){ 
				String smallPic="",bigPic="",desc="",num="",beCodeS="";
				for(int i=0;i<trans.getList().size();i++){
					JSONObject tranjson= new JSONObject();
					Trans traninfo = (Trans)trans.getList().get(i);
					tranjson.put("ticketId",traninfo.getAuthTicketId());
					tranjson.put("phone",traninfo.getPhone()==null?"":traninfo.getPhone());
					tranjson.put("buyerTime",traninfo.getCreateTime());
					tranjson.put("spendingTime",traninfo.getConsumeTime()==null?"":traninfo.getConsumeTime());
					TransDetails tranDetails = traninfo.getTransDetailsList().get(0);
					num = tranDetails.getGoodsNumber();
					tranjson.put("goodsNumber","1");
					String cash = unitPrice(traninfo.getCurrencyValueRealAll(),num);
					String bankPoint = unitPrice(traninfo.getBankPointsValueRealAll(),num);
					String fftPoint = unitPrice(traninfo.getFftPointsValueRealAll(),num);
					String str = ClientCommon.payString(cash,fftPoint,bankPoint);
					tranjson.put("price",str);
					if(traninfo.getBelongUserBecodeAuth()!= null){
						beCodeS = traninfo.getBelongUserBecodeAuth().substring(traninfo.getBelongUserBecodeAuth().indexOf("|")+1);
						tranjson.put("beCode",beCodeS);
						tranjson.put("beCodeName",clerkMap.get(beCodeS));
					}else if(traninfo.getBelongUserBecode() != null){
						beCodeS = traninfo.getBelongUserBecode().substring(traninfo.getBelongUserBecode().indexOf("|")+1);
						tranjson.put("beCode",beCodeS);
						tranjson.put("beCodeName",clerkMap.get(beCodeS));
					} else {
						tranjson.put("beCode","");
						tranjson.put("beCodeName","");
					}
					desc = str + "\r\n用户联系方式:\r\n" + traninfo.getPhone();
					desc += "\r\n消费地址：\r\n"+traninfo.getMerchant().getMstoreAddress()+"\r\n联系电话：\r\n"+traninfo.getMerchant().getMstoreContactTel1() ;
					
					if(FroadAPICommand.Client_type.equals(traninfo.getClientType())){
						tranjson.put("goodsID",tranDetails.getGoodsGroupRack()==null?"":tranDetails.getGoodsGroupRack().getId());
						tranjson.put("goodsName",tranDetails.getGoodsGroupRack()==null||tranDetails.getGoodsGroupRack().getSeoTitle()==null?"":tranDetails.getGoodsGroupRack().getSeoTitle());
						Goods goods =tranDetails.getGoodsGroupRack() == null ? new Goods() : tranDetails.getGoodsGroupRack().getGoods();
						smallPic = FroadAPICommand.makePicURL(goods.getSourceUrl(),FroadAPICommand.PIC_THUMBNAIL);
						bigPic = FroadAPICommand.makePicURL(goods.getSourceUrl(),FroadAPICommand.PIC_SAMLL);
						if(tranDetails.getGoodsGroupRack() != null) {
							desc+=   "\r\n团购卷使用有效期：\r\n"+tranDetails.getGoodsGroupRack().getTicketBeginTime().replace("|", " ")+"至"+tranDetails.getGoodsGroupRack().getTicketEndTime().replace("|", " ");
							desc +=  tranDetails.getGoodsGroupRack().getBuyKnow()==null||"".equals(tranDetails.getGoodsGroupRack().getBuyKnow())?"":("\r\n团购须知：\r\n"+ClientCommon.getTxtWithoutHTMLElement(tranDetails.getGoodsGroupRack().getBuyKnow()));
						}
						
					}else{ 
						tranjson.put("goodsID",tranDetails.getClientGoodsGroupRack()==null?"":tranDetails.getClientGoodsGroupRack().getId());
						tranjson.put("goodsName",tranDetails.getClientGoodsGroupRack()==null||tranDetails.getClientGoodsGroupRack().getSeoTitle()==null?"":tranDetails.getClientGoodsGroupRack().getSeoTitle());
						if(tranDetails.getClientGoodsGroupRack()!=null&&tranDetails.getClientGoodsGroupRack().getClientGoodsGroupRackImagesList()!=null){
							List<com.froad.client.trans.ClientGoodsGroupRackImages>  images = tranDetails.getClientGoodsGroupRack().getClientGoodsGroupRackImagesList();
//							for(int j=0;j<images.size();j++){
//								if(FroadAPICommand.pic_184x141.equals(images.get(j).getPixel())){
//									smallPic=images.get(j).getImagesUrl();
//								}
//								if(FroadAPICommand.pic_440x267.equals( images.get(j).getPixel())){
//									bigPic=FroadAPICommand.makePicURL(images.get(j).getImagesUrl());
//								}
//							}
							if(images.size() > 0){
								String url = images.get(0).getImagesUrl();
								smallPic = FroadAPICommand.makePicURL(url,FroadAPICommand.PIC_THUMBNAIL);
								bigPic = FroadAPICommand.makePicURL(url,FroadAPICommand.PIC_SAMLL);
							}
							if(tranDetails.getGoodsGroupRack() != null) {
								desc += "\r\n团购卷使用有效期：\r\n"+tranDetails.getClientGoodsGroupRack().getTicketBeginTime().replace("|", " ")+"至"+tranDetails.getClientGoodsGroupRack().getTicketEndTime().replace("|", " ");
								desc +=  tranDetails.getClientGoodsGroupRack().getBuyKnow()==null||"".equals(tranDetails.getClientGoodsGroupRack().getBuyKnow())?"":("\r\n团购须知：\r\n"+tranDetails.getClientGoodsGroupRack().getBuyKnow());
							}
						}
						
					}
					tranjson.put("smallPic",FroadAPICommand.makePicURL(smallPic) );
					tranjson.put("bigPic",FroadAPICommand.makePicURL(bigPic) );
					tranjson.put("desc",desc);
					jsonlist.add(tranjson);
				}
			} 
			 
			reno="0000";
			rebody.put("list",jsonlist);
		}catch(JSONException js){
			reno="0009";
			logger.error("queryGroupTran 异常",js);
		}catch(Exception e){
			reno="9999";
			logger.error("queryGroupTran 异常",e);
		}
		return SUCCESS;
	}
	
	
	/**
	 * 
	  * 方法描述：收款订单查询
	  * @param:  10052
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-4 下午04:26:22
	 */
	public String queryReceiveTran(){
		logger.info("[MerchantTranAction- queryReceiveTran]body:"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userId = jsonObject.getString("userId");
			String beCode =  jsonObject.getString("beCode");  
			String enddate =  ((String)JsonUtil.getOptionalValue(jsonObject,"enddate")).replace(" ", "|");  
			String startdate =  ((String)JsonUtil.getOptionalValue(jsonObject,"startdate")).replace(" ", "|");
		
			enddate = getNowDate();
			
			
			MerchantUserSet merchantUserSet = new MerchantUserSet();
			merchantUserSet.setUserId(userId);
			Map<String,String> clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
//			
			if(!clerkMap.containsKey(beCode)){
				return SUCCESS;
			}
//			
			List<String>  typeList = new ArrayList();
			typeList.add(TranCommand.POINTS_REBATE);
			typeList.add(TranCommand.COLLECT);
			List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
			if(sellerIdList.size() == 0){
				reno = "10022";
				return SUCCESS;
			}
			
			
			Trans trans = new Trans();
			trans.getSellerIdList().addAll(sellerIdList);
//			trans.setState(TranCommand.TRAN_SUCCESS);
			trans.setPageSize(100);
			trans.setUpdateTime(startdate);
			trans.setBeginTime(startdate);
			trans.setEndTime(enddate);
//			trans.setTransType(TranCommand.POINTS_REBATE);
			if(!"".equals(beCode) && !"1000".equals(beCode))
				trans.setBelongUserBecode(userId+"|"+beCode);
			trans =transActionSupport.getTransByPager(trans);
			JSONArray jsonlist = new JSONArray();
			String beCodeS = "";
			if(trans.getList()!=null){ 
				for(int i=0;i<trans.getList().size();i++){
					JSONObject tranjson= new JSONObject();
					Trans traninfo = (Trans)trans.getList().get(i);
					TransDetails tranDetails = traninfo.getTransDetailsList().get(0);
					tranjson.put("tranID",traninfo.getId());
					tranjson.put("tel",traninfo.getPhone()==null?"":traninfo.getPhone());
					tranjson.put("buyerTime",traninfo.getCreateTime());
					tranjson.put("reason",tranDetails.getGoodsGatherRack()==null||tranDetails.getGoodsGatherRack().getGoods()==null||tranDetails.getGoodsGatherRack().getGoods().getGoodsName()==null?"":tranDetails.getGoodsGatherRack().getGoods().getGoodsName());
					tranjson.put("amount",traninfo.getCurrencyValueRealAll()==null?"":traninfo.getCurrencyValueRealAll() + "("+(traninfo.getFftPointsValueRealAll()==null?"":traninfo.getFftPointsValueRealAll())+")");
					beCodeS = traninfo.getBelongUserBecode().substring(traninfo.getBelongUserBecode().indexOf("|")+1);
					tranjson.put("beCode",beCodeS);
					tranjson.put("beCodeName",clerkMap.get(beCodeS));
					tranjson.put("status",TranCommand.TRAN_STATUS_MAP.get(traninfo.getState()));
					jsonlist.add(tranjson);
				}
			}
			
			
//			if(jsonlist.size()==0){
//				JSONObject  groupTran = new JSONObject(); 
//				groupTran.put("tranID", "100021");
//				groupTran.put("buyerTime", "2013-04-06|15:30:00");
//				groupTran.put("tel", "13989440940");
//				groupTran.put("amount", "30");
//				groupTran.put("reason", "reason");
//				groupTran.put("beCode", "1001");
//				groupTran.put("beCodeName", "管理员");
//				jsonlist.add(groupTran);
//			}
			reno="0000";
			rebody.put("list",jsonlist);
		}catch(JSONException js){
			reno="0009";
			logger.error("queryReceiveTran 异常",js);
		}catch(Exception e){
			reno="9999";
			logger.error("queryReceiveTran 异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	  * 方法描述：积分赠送订单
	  * @param:  10053
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-4 下午04:26:22
	 */
	public String queryPointsTran(){
		logger.info("[MerchantTranAction- queryPointsTran]body:"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userId = jsonObject.getString("userId");
			String beCode =  jsonObject.getString("beCode"); 
			String enddate =  (String)JsonUtil.getOptionalValue(jsonObject,"enddate");  
			String startdate =  (String)JsonUtil.getOptionalValue(jsonObject,"startdate");
			
			enddate = getNowDate();
			
			MerchantUserSet merchantUserSet = new MerchantUserSet();
			merchantUserSet.setUserId(userId);
			Map<String,String> clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
			
			if(!clerkMap.containsKey(beCode)){
				return SUCCESS;
			}
			
			List<String>  typeList = new ArrayList();
			typeList.add(TranCommand.PRESENT_POINTS);
			List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
			if(sellerIdList.size() == 0){
				reno = "10022";
				return SUCCESS;
			}
			
			Trans trans = new Trans();
			trans.getSellerIdList().addAll(sellerIdList);
//			trans.setState(TranCommand.TRAN_SUCCESS);
			trans.setPageSize(100);
			trans.setUpdateTime(startdate);
			trans.setBeginTime(startdate);
			trans.setEndTime(enddate);
//			trans.setTransType(TranCommand.PRESENT_POINTS);
			if(!"".equals(beCode) && !"1000".equals(beCode))
				trans.setBelongUserBecode(userId+"|"+beCode);
			trans =transActionSupport.getTransByPager(trans);
			JSONArray jsonlist = new JSONArray();
			String beCodeS = "";
			if(trans.getList()!=null){ 
				for(int i=0;i<trans.getList().size();i++){
					JSONObject tranjson= new JSONObject();
					Trans traninfo = (Trans)trans.getList().get(i);
					TransDetails tranDetails = traninfo.getTransDetailsList().get(0);
					tranjson.put("tranID",traninfo.getId());
					tranjson.put("tel",traninfo.getPhone()==null?"":traninfo.getPhone());
					tranjson.put("buyerTime",traninfo.getCreateTime());
					tranjson.put("reason",tranDetails.getGoodsGatherRack()==null||tranDetails.getGoodsGatherRack().getGoods()==null||tranDetails.getGoodsGatherRack().getGoods().getGoodsName()==null?"":tranDetails.getGoodsGatherRack().getGoods().getGoodsName());
					tranjson.put("points",traninfo.getFftPointsValueRealAll()==null?"":traninfo.getFftPointsValueRealAll());
					beCodeS = traninfo.getBelongUserBecode().substring(traninfo.getBelongUserBecode().indexOf("|")+1);
					tranjson.put("beCode",beCodeS);
					tranjson.put("beCodeName",clerkMap.get(beCodeS));
					tranjson.put("status",TranCommand.TRAN_STATUS_MAP.get(traninfo.getState()));
					jsonlist.add(tranjson);
				}
			}  
			rebody.put("list",jsonlist);
			reno="0000";
		}catch(JSONException js){
			reno="0009";
			logger.error("queryPointsTran 异常",js);	
		}catch(Exception e){
			reno="9999";
			logger.error("queryPointsTran 异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	  * 方法描述：积分兑换订单（no：10054）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-4-24 下午04:16:16
	 */
	public String queryExchangeTran(){
		logger.info("[MerchantTranAction- queryExchangeTran]body:"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userId = jsonObject.getString("userId");
			String beCode =  jsonObject.getString("beCode"); 
			String enddate =  ((String)JsonUtil.getOptionalValue(jsonObject,"enddate")).replace(" ", "|");  
			String startdate =  ((String)JsonUtil.getOptionalValue(jsonObject,"startdate")).replace(" ", "|");
			
			enddate = getNowDate();
			
			
			
			
//			List<String>  typeList = new ArrayList();
//			typeList.add(TranCommand.POINTS_EXCH_PRODUCT);
//			List<Integer> sellerIdList=sellerTransActionSupport.getSellerIdByUserId(userId,typeList);
//			if(sellerIdList.size() == 0){
//				reno = "10022";
//				return SUCCESS;
//			}
			
			MerchantUserSet merchant=getMerchantUserInfo(userId,beCode);
			if(merchant==null){
				return SUCCESS;
			}
			

			MerchantUserSet merchantUserSet = new MerchantUserSet();
			merchantUserSet.setUserId(userId);
			Map<String,String> clerkMap = merchantUserSetActionSupport.getMerchantClerktMap(merchantUserSet);
			
			Trans trans = new Trans();
			trans.setMerchantId(Integer.parseInt(merchant.getMerchantId()));
			trans.setState(TranCommand.TRAN_SUCCESS);
			trans.setPageSize(100);
			trans.setUpdateTime(startdate);
			trans.setBeginTime(startdate);
			trans.setEndTime(enddate);
			trans.setTransType(TranCommand.POINTS_EXCH_PRODUCT);
			trans.setIsConsume("1");//已消费
			if(!"".equals(beCode) && !"1000".equals(beCode))
				trans.setBelongUserBecodeAuth(userId+"|"+beCode);
//			trans =transActionSupport.getTransByPager(trans);
			trans = transActionSupport.getGroupOrExchangeTransByPager(trans);
			JSONArray jsonlist = new JSONArray();
			if(trans.getList()!=null){ 
				String smallPic="",bigPic="",desc="",num="",beCodeS="";
				for(int i=0;i<trans.getList().size();i++){
					JSONObject tranjson= new JSONObject();
					Trans traninfo = (Trans)trans.getList().get(i);
					tranjson.put("ticketId",traninfo.getAuthTicketId());
					tranjson.put("phone",traninfo.getPhone()==null?"":traninfo.getPhone());
					tranjson.put("buyerTime",traninfo.getCreateTime());
					tranjson.put("spendingTime",traninfo.getConsumeTime()==null?"":traninfo.getConsumeTime());
					TransDetails tranDetails = traninfo.getTransDetailsList().get(0);
					num = tranDetails.getGoodsNumber();
					tranjson.put("goodsNumber","1");
					String cash = unitPrice(traninfo.getCurrencyValueRealAll(),num);
					String bankPoint = unitPrice(traninfo.getBankPointsValueRealAll(),num);
					String fftPoint = unitPrice(traninfo.getFftPointsValueRealAll(),num);
					String str = ClientCommon.payString(cash,fftPoint,bankPoint);
					tranjson.put("price",str);
					
					System.out.println(traninfo.getBelongUserBecode());
					if(traninfo.getBelongUserBecodeAuth()!= null){
						beCodeS = traninfo.getBelongUserBecodeAuth().substring(traninfo.getBelongUserBecodeAuth().indexOf("|")+1);
						tranjson.put("beCode",beCodeS);
						tranjson.put("beCodeName",clerkMap.get(beCodeS));
					}else if(traninfo.getBelongUserBecode() != null){
						beCodeS = traninfo.getBelongUserBecode().substring(traninfo.getBelongUserBecode().indexOf("|")+1);
						tranjson.put("beCode",beCodeS);
						tranjson.put("beCodeName",clerkMap.get(beCodeS));
					} else {
						tranjson.put("beCode","");
						tranjson.put("beCodeName","");
					}
					
					tranjson.put("remark","");
					tranjson.put("remark1","");
					
					
					List<TransGoodsAttribute> goodsAttributes = traninfo.getTransGoodsAttrList();
					desc = str + "\r\n用户联系方式:\r\n"+traninfo.getPhone() + "\r\n";
					if(goodsAttributes!=null && goodsAttributes.size()>0){
						for(TransGoodsAttribute attribute: goodsAttributes){
							desc += (!"1".equals(attribute.getGoodsRackAttribute().getIsRequired()))?"":attribute.getGoodsRackAttribute().getName()+":"+attribute.getElement()+" ";
						}
					}else{
						desc+="取货地址：\r\n"+traninfo.getMerchant().getMstoreAddress()+" "+traninfo.getMerchant().getMstoreShortName() +
						      "\r\n联系电话：\r\n"+traninfo.getMerchant().getMstoreContactTel1() ;	
					} 
					
					if(FroadAPICommand.Client_type.equals(traninfo.getClientType())){
						tranjson.put("goodsID",tranDetails.getGoodsExchangeRack()==null?"":tranDetails.getGoodsExchangeRack().getId());
						tranjson.put("goodsName",tranDetails.getGoodsExchangeRack()==null||tranDetails.getGoodsExchangeRack().getGoods()==null||tranDetails.getGoodsExchangeRack().getGoods().getGoodsName()==null?"":tranDetails.getGoodsExchangeRack().getGoods().getGoodsName());
						smallPic=tranDetails.getGoodsExchangeRack()==null||tranDetails.getGoodsExchangeRack().getGoods()==null?"":tranDetails.getGoodsExchangeRack().getGoods().getSmallUrl();
						bigPic=smallPic;
					}else{
						tranjson.put("goodsID",tranDetails.getClientGoodsExchangeRack()==null?"":tranDetails.getClientGoodsExchangeRack().getId());
						tranjson.put("goodsName",tranDetails.getClientGoodsExchangeRack()==null||tranDetails.getClientGoodsExchangeRack().getGoods()==null||tranDetails.getClientGoodsExchangeRack().getGoods().getGoodsName()==null?"":tranDetails.getClientGoodsExchangeRack().getGoods().getGoodsName());
						if(tranDetails.getClientGoodsExchangeRack()!=null&&tranDetails.getClientGoodsExchangeRack().getClientGoodsExchangeRackImagesList()!=null){
							List<com.froad.client.trans.ClientGoodsExchangeRackImages>  images = tranDetails.getClientGoodsExchangeRack().getClientGoodsExchangeRackImagesList();
//							for(int j=0;j<images.size();j++){
//								if(FroadAPICommand.pic_184x141.equals(images.get(j).getPixel())){
//									smallPic=FroadAPICommand.makePicURL(images.get(j).getImagesUrl());
//								}
//								if(FroadAPICommand.pic_370x362.equals(images.get(j).getPixel())){
//									bigPic=FroadAPICommand.makePicURL(images.get(j).getImagesUrl());
//								}
//							}
							if(images.size() > 0){
								String url = images.get(0).getImagesUrl();
								smallPic = FroadAPICommand.makePicURL(url,FroadAPICommand.PIC_THUMBNAIL);
								bigPic = FroadAPICommand.makePicURL(url,FroadAPICommand.PIC_SAMLL);
							}
						}
					} 
					tranjson.put("smallPic",FroadAPICommand.makePicURL(smallPic) );
					tranjson.put("bigPic",FroadAPICommand.makePicURL(bigPic) );
					tranjson.put("desc",desc);
					jsonlist.add(tranjson);
				}
				rebody.put("list",jsonlist);
				reno="0000";
			}
		}catch(JSONException js){
			reno="0009";
			logger.error("queryPointsTran 异常",js);	
		}catch(Exception e){
			reno="9999";
			logger.error("queryPointsTran 异常",e);
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
	public  MerchantUserSet getMerchantUserInfo(String userId,String beCode) throws AppException_Exception{
		MerchantUserSet merchantUserSet = new MerchantUserSet();
		merchantUserSet.setBeCode("".equals(beCode)?null:beCode);
		merchantUserSet.setUserId(userId);
		List<MerchantUserSet> list = merchantUserSetActionSupport.getMerchantUserSetList(merchantUserSet);
		if(list==null||list.size()==0){
			reno="10011";
			return null;
		}
		return list.get(0);
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
		logger.info("调用结束[MerchantTranAction]reno:"+reno+",remsg:"+getRemsg()+",rebody:"+rebody + " ,ReturnTime:" + getReturnTime());
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
	 
	public String getReturnTime() {
		
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
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
	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	} 
	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}

	@JSON(serialize=false)
	public SellerTransActionSupport getSellerTransActionSupport() {
		return sellerTransActionSupport;
	}

	public void setSellerTransActionSupport(
			SellerTransActionSupport sellerTransActionSupport) {
		this.sellerTransActionSupport = sellerTransActionSupport;
	}
 
	 
	
}
