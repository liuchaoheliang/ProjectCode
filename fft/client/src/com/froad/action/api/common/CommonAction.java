package com.froad.action.api.common;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.froad.action.api.BaseApiAction;
import com.froad.action.api.command.Encrypt;
import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.api.task.TaskAction;
import com.froad.action.support.SellersActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.trans.RepeatMsgActionSupport;
import com.froad.client.authTicket.AuthTicket;
import com.froad.client.authTicket.Result;
import com.froad.client.sellers.Seller;
import com.froad.client.sellers.SellerChannel;
import com.froad.client.user.User;
 
import com.froad.common.SellerCommand;
import com.froad.sms.SmsService;
import com.froad.util.Assert;
 

/**
 * 类描述：处理类 *
 * 
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2013
 * @author: tongxueliang@f-road.com.cn
 * @CreateTime: 2013-3-8下午02:07:25
 */
public class CommonAction extends BaseApiAction{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(TaskAction.class);
	private SmsService smsService;
	private SellersActionSupport sellersActionSupport;
	private RepeatMsgActionSupport repeatMsgSupport;
	private UserActionSupport userActionSupport;
	private String reno;// 操作结果码
	private String remsg;// 响应信息
	private Map<String, Object> rebody = new HashMap<String, Object>();// 主体
	private String recount;// 返回请求标识号（原样返回）
	private String returnTime;//返回时间
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	/**
	 * @方法描述：版本查询  0000
	 * @return:
	 * @version: 1.0
	 * @author: tongxueliang@f-road.com.cn
	 * @CreateTime: 2013上午10:58:42
	 */	
	public String versionSearch(){
		logger.info("[CommonAction- versionSearch]body:"+body);

		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String clientKey = jsonObject.getString("clientKey");
			String version = jsonObject.getString("version");
			String serverVesion = FroadAPICommand.serverVersion;
			if(Double.parseDouble(serverVesion)>Double.parseDouble(version)){
				reno="0000";
				if("zrcb_fft_android".equals(clientKey)){
					rebody.put("url", FroadAPICommand.apkPath);
				}else if("zrcb_fft_iphone".equals(clientKey)){
					rebody.put("url",FroadAPICommand.ipaPath);
				}
				else if("zrcb_fft_merchant_android".equals(clientKey)){
					rebody.put("url", FroadAPICommand.apkPath);
				}else if("zrcb_fft_ merchant_iphone".equals(clientKey)){
					rebody.put("url",FroadAPICommand.ipaPath);
				}else{
					rebody.put("url", "");
				}
				rebody.put("flag", "0");
		    }else{
		    	reno="0000";
		    	rebody.put("flag", "1");
		    	rebody.put("url", "");
		    }
		}catch(Exception e){
			reno="9999";
			logger.error("versionSearch 异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	  * 方法描述：短信验证发送 0001
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-18 上午11:56:21
	 */
	public String shortmsg(){
		logger.info("[CommonAction- shortmsg]body:"+body);
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String type = jsonObject.getString("type");
			String mobliePhone = jsonObject.getString("mobilephone");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			StringBuffer mail = new StringBuffer("您于" + df.format(new Date()) + "提交了");
			if("01".equals(type)){
				mail.append("手机验证码登陆");
				User user=userActionSupport.selectUserMobilephone(mobliePhone);
				if(user.getRespCode().equals("1")){
					reno="10024";
					return SUCCESS;
				}
				if("3".equals(user.getStatus())){
					reno="0107";
					return SUCCESS;
				}else if("4".equals(user.getStatus())){
					reno="0108";
					return SUCCESS;
				}
			} else if("02".equals(type)) {
				
				mail.append("手机注册");
				User user=userActionSupport.selectUserMobilephone(mobliePhone);
				if("0".equals(user.getRespCode())){
					reno="0006";
					return SUCCESS;
				}
			} else {
				reno="0009"; 
				return SUCCESS;
			}
			Random random = new Random();
			String n = String.valueOf(random.nextInt(999999) + 1000000);
			n = n.substring(1, n.length());
			mail.append("请求6位验证码为" + n + "。如果没有该请求请忽略本短信。【珠海农商银行】");
			Boolean flag = false;
			// flag = PhoneUtil.sendMsg(mobliePhone, "", mail.toString());
			flag = smsService.sendClientMsg(mobliePhone, mail.toString());
			
			logger.info(mobliePhone + " 短信发出  ,    结果：" + flag);
			 
			HttpServletRequest request = ServletActionContext.getRequest();
			request.getSession().setAttribute(FroadAPICommand.FROAD_CLIENT_SESSION_SMCODE + type,n);
			request.getSession().setAttribute(FroadAPICommand.FROAD_CLIENT_SESSION_SMCODE + type + FroadAPICommand.MOBILE_SM_NO_KEY,mobliePhone);
			n = new Encrypt().clientEncrypt(mobliePhone+type,n);
			reno="0000";
			rebody.put("flag", flag);
			rebody.put("code", n);
			 
		}catch(Exception e){
			reno="9999";
			logger.error("shortmsg 异常",e);
		}
		return SUCCESS;
	}

	
	
	/**
	  * 方法描述：获取规则
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2013-4-17 下午2:35:10
	  */
	public String queryRule(){
		logger.info("[CommonAction- queryRule]body:"+body);
		
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String ruleNo = jsonObject.getString("ruleNo");
			String userId = jsonObject.getString("userId");
			
			Seller seller = new Seller();
			seller.setUserId(userId);
			//收款
			if("01".equals(ruleNo)){
				seller.setSellerType(SellerCommand.POINTS_REBATE);
			//送积分
			} else if("02".equals(ruleNo)) {
				seller.setSellerType(SellerCommand.PRESENT_POINTS);
			} else {
				reno = "10012";
				return SUCCESS;
			}
			
			
			List<Seller>  sellerList = sellersActionSupport.getSellerListBySelective(seller);
			
			if(sellerList == null || sellerList.size() != 1) {
				reno = "10011";
				return SUCCESS;
			}
			
			seller = sellerList.get(0);
			SellerChannel sellerChannel = seller.getSellerChannelList().get(0);
			rebody.put("ruleType","01");//暂时固定返回 积分赠送
			rebody.put("rule",new BigDecimal(sellerChannel.getSellerRule().getFftPointsRule()).divide(new BigDecimal(100)).toString());
			reno="0000";
		}catch(Exception e){
			reno="9999";
			logger.error("shortmsg 异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	  * 方法描述：短信重发0003
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-5-6 下午07:05:33
	 */
	public String msgResend(){
		logger.info("[CommonAction- msgResend]body:"+body);
		 
		try{
			JSONObject jsonObject = JSONObject.fromObject(body);
			String ticketId = jsonObject.getString("ticketId");
			
			if(Assert.empty(ticketId)){
				reno="0009";
				return SUCCESS;
			}
			
			if(ticketId.startsWith("h")){
				reno="0000";
				remsg = "该订单无需获取短信";
				rebody.put("desc", remsg);
				return SUCCESS;
			}
			
			AuthTicket authTicket =repeatMsgSupport.getAuthTicketById(Integer.parseInt(ticketId));
			if(authTicket==null||authTicket.getId()==null){
				reno="10019";
				rebody.put("desc", "验证券不存在");
				return SUCCESS;
			}
			Result result = repeatMsgSupport.repeatMsg(Integer.parseInt(authTicket.getTransId()),Integer.parseInt(ticketId));	
			if("success".equals(result.getCode())){
				reno="0000";
			}else{
				reno="0601";
			}
			remsg = result.getMsg();
			rebody.put("desc", result.getMsg());
		}catch(JSONException js){
			reno="0009";
			logger.error("msgResend 异常",js);
		}catch(Exception e){
			reno="9999";
			logger.error("msgResend 异常",e);
		}
		return SUCCESS;
	}
	

	public String getReno() {
		logger.info("调用结束[CommonAction]reno:"+reno+",remsg:"+getRemsg()+",rebody:"+rebody);
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
	public SmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	@JSON(serialize=false)
	public SellersActionSupport getSellersActionSupport() {
		return sellersActionSupport;
	}

	public void setSellersActionSupport(SellersActionSupport sellersActionSupport) {
		this.sellersActionSupport = sellersActionSupport;
	}
	@JSON(serialize=false)
	public RepeatMsgActionSupport getRepeatMsgSupport() {
		return repeatMsgSupport;
	}

	public void setRepeatMsgSupport(RepeatMsgActionSupport repeatMsgSupport) {
		this.repeatMsgSupport = repeatMsgSupport;
	}

	@JSON(serialize=false)
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}

	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	
}
