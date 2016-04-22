package com.froad.action.api.carryPoints;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import com.froad.action.api.BaseApiAction;
import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.ClientGoodsCarryRackActionSupport;
import com.froad.action.support.ClientGoodsExchangeRackActionSupport;
import com.froad.action.support.GoodsExchangeRackActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.PointCashRuleActionSupport;
import com.froad.action.support.TransActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.UserCertificationActionSupport;
import com.froad.action.support.trans.TransCarryActionSupport;
import com.froad.client.buyers.Buyers;
import com.froad.client.clientGoodsCarryRack.ClientGoodsCarryRack;
import com.froad.client.clientGoodsExchangeRack.ClientGoodsExchangeRack;
import com.froad.client.point.Points;
import com.froad.client.point.PointsAccount;
import com.froad.client.trans.Trans;
import com.froad.client.trans.TransDetails;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.client.userCertification.UserCertification;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.Result;
import com.froad.util.command.Command;

/*
 * 积分提现
 */
@SuppressWarnings("unchecked")
public class CarryPointsAction extends BaseApiAction {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CarryPointsAction.class);
	private UserActionSupport userActionSupport;
	private PointActionSupport pointActionSupport;
	private PointCashRuleActionSupport pointCashRuleActionSupport;
	private BuyersActionSupport buyersActionSupport;
	private UserCertificationActionSupport userCertificationActionSupport;
	private TransActionSupport transActionSupport;
	
	private TransCarryActionSupport transCarryActionSupport;
	private ClientGoodsCarryRackActionSupport clientGoodsCarryRackActionSupport;
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
	  * 方法描述：查询分分通积分
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-26 下午08:28:54
	 */
	public PointsAccount queryfftPoints(String username){
		PointsAccount point_fft = new PointsAccount();
		Points point= new Points();
		point.setPartnerNo(TranCommand.PARTNER_ID);
		point.setOrgNo(TranCommand.FFT_ORG_NO);
		point.setAccountMarked(username);
		point.setAccountMarkedType(FroadAPICommand.ACCOUNT_MARKED_TYPE);
		Points pointRes=pointActionSupport.queryPoints(point);

		if(pointRes!=null&&"0000".equals(pointRes.getResultCode())){
			if(pointRes.getPointsAccountList()!=null&&pointRes.getPointsAccountList().size()!=0){
				point_fft = this.findPointsAccount(pointRes.getPointsAccountList(), TranCommand.FFT_ORG_NO);
			}
		}else{
			point_fft= null;
		}
		return point_fft;
	}
	
	/**
	  * 方法描述：按积分机构号查找积分账户
	  * @param: List<PointsAccount> 目标list
	  * @return: orgNo 积分机构号
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 8, 2013 6:47:04 PM
	  */
	private PointsAccount findPointsAccount(List<PointsAccount> acctList,String orgNo){
		for (int i = 0; i < acctList.size(); i++) {
			if(orgNo.equals(acctList.get(i).getOrgNo())){
				return acctList.get(i);
			}
		}
		return null;
	}
	
	/**
	  * 方法描述：查询可提积分
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-26 下午08:28:37
	 */
	public String queryCarryPoints(){
		logger.info("[CarryPoints- queryCarryPoints]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId");
			User  userReq =getUserInfo(userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				PointsAccount pointsAccount = queryfftPoints(userReq.getUsername());
				 
				if(pointsAccount!=null&&pointsAccount.getPoints()!=null){
					String cash = pointCashRuleActionSupport.getCashByPoint(pointsAccount.getPoints().replace(",", ""), TranCommand.PRICING_FFT_CASH);    
					if(cash==null){ 
						reno="0307";
					}else{
						rebody.put("carryPoint", pointsAccount.getPoints().replace(",", "")); 
						rebody.put("money", cash);
						rebody.put("serviceFee", "1|0.05|50");
						rebody.put("info", "按照提现金额5%收取，最低1元，最高50元");
						reno="0000"; 
					}
				}else{
					reno="0301";
				} 
			}
		}catch(Exception e){
			reno="9999";
			logger.error("queryCarryPoints异常",e);
		}
		return SUCCESS;
		
	}
	
	/**
	 * 
	  * 方法描述：积分提现
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-26 下午08:28:28
	 */
	public String carryPoints(){
		logger.info("[CarryPoints- CarryPoints]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userId  = jsonObject.getString("userId");
			String carryPoint  = jsonObject.getString("carryPoint");
			String clientType  = jsonObject.getString("clientType");
			Double points_d = -1.0;
			try{
				points_d=Double.valueOf(carryPoint);
				if(points_d<10||points_d % 10!=0.0){
					reno="0303";
					return SUCCESS;
				}
				//如果积分
				if(carryPoint.indexOf(".")!=-1)
					carryPoint = carryPoint.substring(0,carryPoint.indexOf("."));
				//logger.info("carryPoint==========:"+carryPoint);
			}catch(Exception e){
				reno="0303";
				logger.error("carryPoints：carryPoint值转换异常.");
				return SUCCESS;
			}
			User  userReq =getUserInfo(userId);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				/*Buyers buyers =buyersActionSupport.getBuyerByUserId(userId);
				if(buyers==null){
					reno="0302";
					logger.error("carryPoints：buyers为空.");
					return SUCCESS;
				}*/
				 
				PointsAccount pointsAccount  = queryfftPoints(userReq.getUsername());
				if(pointsAccount!=null&&pointsAccount.getPoints()!=null){
					//pointCashRuleActionSupport.getCashByPoint(pointsAccount.getPoints().replace(",", ""),FroadAPICommand.fftToCash );  
					Double pointsToWithDraw_d=Double.valueOf(pointsAccount.getPoints().replace(",", ""));
					if(points_d>pointsToWithDraw_d){
						reno="0304";
						logger.error("carryPoints：points_d值异常.");
						return SUCCESS;
					}else{ 
						
						//需修改成根据goods_category_id:100001007查询出ClientGoodsExchangeRack的list.get(0)获得
						ClientGoodsCarryRack  clientGoodsCarryRack = new ClientGoodsCarryRack();
						clientGoodsCarryRack.setGoodsCategoryId(FroadAPICommand.GoodsCategory_CarryPoints);
						clientGoodsCarryRack.setPageSize(100);
						clientGoodsCarryRack.setIsRack("1");
						clientGoodsCarryRack.setState(Command.STATE_START);
						clientGoodsCarryRack   = clientGoodsCarryRackActionSupport.getClientGoodsCarryRackListByPager(clientGoodsCarryRack);
						if(clientGoodsCarryRack.getList()==null||clientGoodsCarryRack.getList().size() == 0){
							throw new AppException_Exception("没有提现商品");
						}
						ClientGoodsCarryRack goodsRack =(ClientGoodsCarryRack)clientGoodsCarryRack.getList().get(0);
						UserCertification userCertification=userCertificationActionSupport.getUserCertification(userReq.getUserID(),TranCommand.CHANNEL_ID);
						if(userCertification==null||!"1".equals(userCertification.getCertificationResult())){
							reno="0306";
							 return SUCCESS;
						} 
						Trans trans = new Trans();
						trans.setUserId(userId);
						trans.setClientType(clientType);
						trans.setSellerId(goodsRack.getSellerId().toString());
						
						trans.setChannelId(userCertification.getChannelId());
						TransDetails details = new TransDetails();
						details.setGoodsRackId(goodsRack.getId()+"");
						details.setGoodsNumber(carryPoint);
						//(clientType,userId,sellerId,channelId)
						//TransDetails(goodsRackId,goodsNumber)
						
						Trans transRes =transCarryActionSupport.makeCarryTrans(trans, details); 
						 if(transRes == null ){
							 reno="0503";
							 logger.error("carryPoints：下单失败,返回trans异常.");
							 return SUCCESS;
						 }else{
							 trans = transActionSupport.addTrans(transRes);
							 rebody.put("transId", trans.getId());
							 rebody.put("desc", "积分提现成功.");
							 Result result=transActionSupport.pay(trans.getId());
							 if(Result.SUCCESS.equals(result.getCode())){
								 reno="0000";
								 rebody.put("desc", "申请积分提现成功");
							 }else{
								 reno="0305";
								 rebody.put("desc", "申请积分提现失败");
							 }   
						 	 
						 }
						 
					}
				}
				
			}
		} catch (AppException_Exception e) {
			reno="0402";
			logger.error("carryPoints 异常",e);
		}catch(Exception e){
			reno="9999";
			logger.error("carryPoints异常",e);
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
		reno="0101";
		return userReq;
	}
	
	public boolean check(ClientGoodsExchangeRack goodsRack,TransDetails transDetails,Trans trans,User user,Buyers buyer){
		boolean result = false;
		if(goodsRack == null){
			logger.error("ClientGoodsExchangeRack为空.");
			return result;
		}
		if(transDetails.getGoodsRackId() == null){
			logger.error("交易品为空！请选择要交易品.");
			return result;
		}
		if(Assert.empty(transDetails.getGoodsNumber())){
			logger.error("您未选择交易品的数量，请输入交易品的数量.");
			return result;
		}else if(!Assert.isIntOfGreaterEZero(transDetails.getGoodsNumber())){
			logger.error("交易品的数量不是数字.");
			return result;
		}
		if(Assert.empty(trans.getPayMethod())){
			logger.error("您未选择支付方式.");
			return result;
		}
		result = true;
		return result;
	}
	
	public String getReno() {
		logger.info("调用结束[UserAction]reno:"+reno+",remsg:"+getRemsg()+",rebody:"+rebody);
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
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	} 
	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
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
	@SuppressWarnings("unchecked")
	@JSON(serialize=false)
	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	} 
	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
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
	public TransActionSupport getTransActionSupport() {
		return transActionSupport;
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}
	public String getReturnTime() {
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	@JSON(serialize=false)
	public TransCarryActionSupport getTransCarryActionSupport() {
		return transCarryActionSupport;
	}

	public void setTransCarryActionSupport(
			TransCarryActionSupport transCarryActionSupport) {
		this.transCarryActionSupport = transCarryActionSupport;
	}
	@JSON(serialize=false)
	public ClientGoodsCarryRackActionSupport getClientGoodsCarryRackActionSupport() {
		return clientGoodsCarryRackActionSupport;
	}

	public void setClientGoodsCarryRackActionSupport(
			ClientGoodsCarryRackActionSupport clientGoodsCarryRackActionSupport) {
		this.clientGoodsCarryRackActionSupport = clientGoodsCarryRackActionSupport;
	}
	
}
