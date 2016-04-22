package com.froad.action.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import net.sf.json.JSONObject;
import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.LoginManagerActionSupport;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.SellersActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.action.support.TempPointActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.action.support.UserCertificationActionSupport;
import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.buyers.Buyers;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.point.Points;
import com.froad.client.point.PointsAccount;
import com.froad.client.sellers.Seller;
import com.froad.client.tempPoint.TempPoint;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.common.SellerCommand;
import com.froad.common.SessionKey;
import com.froad.common.TranCommand;
import com.froad.sms.SmsService;
import com.froad.sso.SSOCons;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.PhoneSpecial;
import com.froad.util.SpringContextUtil;
import com.froad.util.Validate;
import com.froad.util.command.MallCommand;
import com.froad.util.command.PayCommand;
import com.opensymphony.xwork2.ActionContext;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-26  
 * @version 1.0
 */
public class LoginAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -4710694253873853176L;
	private MerchantActionSupport merchantActionSupport;
	private UserActionSupport userActionSupport;
	private PointActionSupport pointActionSupport;
	private LoginManagerActionSupport loginManagerActionSupport;
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	private BuyersActionSupport buyersActionSupport;
	private SellersActionSupport sellerActionSupport;
	private TempPointActionSupport tempPointActionSupport;
	private SmsService smsService;
	private UserCertificationActionSupport userCertificationActionSupport;
	private String userID;
	private String username;
	private String password;
	private String password1;
	private String oldpassword;
	private String uname;
	private String email;
	private String mobilephone;
	private int sessionID;
	//private List<Goods> goodslist;
	private String activeUrl;
	private String activatecode;
	private String backurl;//返回地址
	private String message;
	private String logicNo;//自动登录标示
	private String bank;
	private String lastTime;
	private String validateCode;
	private String loginKey;
	private String validateType;
	private String type;
	private String anticache;//避免ajax请求每次都一样页面获取请求缓存。设置实效参数。
	private String loginType;//1:个人登录 2:商户登录
	//private String err;
	private String points;
	private String fftPoints;
	//商户操作员
	private String loginName;//商户操作员登录名
	private String beCode;//工号
	private String clerkPwd;//操作员密码
	private MerchantUserSet merchantUserSet;
	private String phoneMessageType;
	private TempPoint tempPoint;
	private String validatePass;
	private StoreActionSupport storeActionSupport;
	
	//新用户注册成功信息
	private String registerInfo;
	/**
	 * 操作员(包括商户)登录 
	 * @return
	 * @throws AppException_Exception 
	 */
	public String clerkLogin() throws AppException_Exception{
		//String name = loginName;
		if(Assert.empty(loginName) || Assert.empty(beCode) || Assert.empty(clerkPwd)){
			return "failse";
		}else{
			loginName = urlDecode(urlDecode(loginName,"utf-8"), "utf-8");
			loginName = loginName.trim().toLowerCase();					
		}
		User user = null;
		//查询操作员表
		MerchantUserSet merchantUserSetReq = new MerchantUserSet();
		MerchantUserSet merchantUserSetRes = null;
		merchantUserSetReq.setLoginName(loginName);
		merchantUserSetReq.setBeCode(beCode);
		List<MerchantUserSet> clerkList = merchantUserSetActionSupport.getMerchantUserSetList(merchantUserSetReq);
		if(clerkList != null && clerkList.size() > 0){
			merchantUserSetRes = clerkList.get(0);
			user = userActionSupport.queryUserAllByUserID(merchantUserSetRes.getUserId());
		}
		if(user!=null && Command.respCode_SUCCESS.equals(user.getRespCode()) && merchantUserSetRes != null){
			String pw = merchantUserSetRes.getBeCodepwd();
			String pw2 = new Md5PasswordEncoder().encodePassword(clerkPwd, merchantUserSetRes.getLoginName()+merchantUserSetRes.getBeCode());
			//String pw2 = clerkPwd;
			if(pw.equals(pw2) && "30".equals(merchantUserSetRes.getState())){
				//查询商户信息
				Merchant m = merchantActionSupport.getMerchantInfo(user.getUserID());
    		   if(m!=null){
    			    setSession(SessionKey.MERCHANT, m);
    			   	setSession(MallCommand.LOGIN_MERCHANT_OR_CLERK, merchantUserSetRes.getIsAdmin());//是否是管理员 0-否 1-是
    			   	setSession(MallCommand.USER_ID, user.getUserID());
    			   	setSession(MallCommand.LOGIN_USER_ID, user.getUserID());
    			   	setSession(user.getUserID(), user.getUsername());
    			   	setSession("beCode", merchantUserSetRes.getBeCode());
    			   	setSession("preferentialType", m.getPreferentialType());
   					//setSession(MallCommand.MERCHANT_ID, m.getId());
   					//String  tt= (String)getSession(MallCommand.MERCHANT_ID);
   					setSession(MallCommand.USERID_BECODE, user.getUserID()+"|"+beCode);
   					setSession(MallCommand.LOGIN_MERCHANT_OR_USER, "1");
   					MerchantUserSet temp = new MerchantUserSet();
   					temp.setUserId(user.getUserID());
   					temp.setBeCode(beCode);
   					MerchantUserSet mus = merchantUserSetActionSupport.getMerchantUserSetList(temp).get(0);
   					String isClerk = mus.getRoleType();
   					setSession(SessionKey.BELONG_STORE_ID, mus.getBelongStoreId());
   					if(mus.getBelongStoreId() !=null && !"".equals(mus.getBelongStoreId())){;
   	   					setSession(SessionKey.BELONG_STORE_SHORT_NAME, storeActionSupport.getStoreById(mus.getBelongStoreId()).getShortName());
   					}
   					
   					String role;
   					if((isClerk == null || "0".equals(isClerk)) && "1000".equals(beCode)){
   						role="-1";
   					}else if("1".equals(isClerk)){
   						role="1";//财务
   					}else{
   						role="0";//普通操作员
   					}
   					setSession(SessionKey.MERCHANT_ROLE, role);
   					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   					user.setLastTime(df.format(new Date()));
   					user.setLastIP(getIpAddr(ServletActionContext.getRequest()));
   					userActionSupport.updateUserLastInfoByUsername(user);
   					
   					
   					
   					Map<String,Object> session=ActionContext.getContext().getSession();
   					
   					
   					//清空商户操作员名称和ID的Idkey的Map缓存
   					merchantUserSetActionSupport.removeCacheMerchantUserSet();
   					if(session.get(MallCommand.SELLER_LIST) == null) {
	   					 com.froad.client.merchant.Merchant merchant = null;
	   					 merchant = merchantActionSupport.getMerchantInfo(user.getUserID());
	   					 // 去商户卖家列表 放入session
	   					 List<Seller>  sellerList = sellerActionSupport.getSellerByMerchantId(merchant.getId().toString());
	   					 // 设置卖家类型
	   					 for (Seller seller : sellerList) {
	   						seller.setSellerTypeName(SellerCommand.SELLER_TYPE_NAME_MAP.get(seller.getSellerType()));
	   					 }
	   					 session.put(MallCommand.SELLER_LIST, sellerList);
   					}   					
    		   }else{
    			   return "failse";
    		   }
    		   setSession(SSOCons.SSO_MERCHANT, "merchant");
    		   return "success";
			}else{
				return "failse";//密码错误
			}
		}else{
			return "failse";//没有这个商户
		}
	}
	
	public String loginOut(){
		try{
			HttpServletRequest req = getRequest();
			HttpSession session = req.getSession(false);
			if(session == null){
				return "success";
			}
			String[] sesssionKys = session.getValueNames();
			Enumeration<String> sessionKeys = session.getAttributeNames(); //获取所有的session Key
			while (sessionKeys.hasMoreElements()) {
				session.removeAttribute(sessionKeys.nextElement());//移除所有session Key 对应的Object
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	public String toLoginOut(){
		return "success";
	}
	
	public String getIpAddr(HttpServletRequest request) {        
	    String ip = request.getHeader("x-forwarded-for");        
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {        
	        ip = request.getHeader("Proxy-Client-IP");        
	    }        
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {        
	        ip = request.getHeader("WL-Proxy-Client-IP");        
	    }        
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {        
	        ip = request.getRemoteAddr();        
	    }        
	    return ip;        
	} 
	
	/**
	 * 普通会员注销
	 * @return
	 */
	public void logout(){
		JSONObject json = new JSONObject();
		json.put("reno", Command.respCode_FAIL);
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		session.invalidate();
		json.put("reno", Command.respCode_SUCCESS);	
		sendMsg(json.toString());
	}
	
	/**
	 * 普通会员登录验证并显示信息
	 */
	public void queryLoginInfo(){
		JSONObject json = new JSONObject();
		json.put("reno", Command.respCode_FAIL);
		String usid = (String)getSession(MallCommand.LOGIN_USER_ID);
		String uname = (String)getSession(usid);
		if(!Assert.empty(usid)){
			//username = username.toLowerCase();
			User user=new User();
		   // user.setUsername(username);
		    //获得信息
		    try {
				user = userActionSupport.queryUserAllByUserID(usid);
			} catch (Exception e) {
				log.info("普通会员登录验证并显示信息出错！");
				json.put("reno", Command.respCode_FAIL);
			}
			
		    if(user.getRespCode().equals("0")){
		    	username = user.getUsername().toLowerCase();
		    	if(!Assert.empty(uname) && uname.toLowerCase().equals(username)){
		    		json.put("userName", user.getUsername());
		    		json.put("userId", usid);
		    		json.put("reno", Command.respCode_SUCCESS);		
		    		//判断是否商户登录
		    		String isMerchant = (String)getSession(MallCommand.LOGIN_MERCHANT_OR_USER);
					//Merchant m = merchantActionSupport.getMerchantInfo(user.getUserID());
	    		   if(!Assert.empty(isMerchant) && "1".equals(isMerchant)){
	    			   json.put("userType", "1");
	    		   }else{
	    			   json.put("userType", "2");
	    		   }
		    	}else{
		    		json.put("reno", Command.respCode_FAIL);		    		
		    	}
		    }
		  }
		sendMsg(json.toString());
	}
	
	/**
	 * fenfentong
	 * 查询登录用户是否是商户
	 * 查询积分值
	 */
	public void queryMerchant(){
		JSONObject json = new JSONObject();
		PointsAccount pointaccount = null;
		PointsAccount pointaccountBank = null;
		json.put("reno", Command.respCode_SUCCESS);	
		//获取是否第一次登陆标识
		//获得信息
		User userRep = null;
		String firstLogin = "";
		
		String usid = (String)getSession(MallCommand.USER_ID);
		String uname = null;
		String isMerchantFlag = (String)getSession(MallCommand.LOGIN_MERCHANT_OR_USER);
		String beCode = (String)getSession("beCode");
		//try {
			log.info("usid:"+usid);
			if(!Assert.empty(usid)){
				String loginName = (String)getSession(usid);
				log.info("loginName:"+loginName);
				userRep = userActionSupport.queryUserAllByUserID(usid);
				//String firstLogin = (String)getSession(MallCommand.FIRST_LOGIN);
				if(userRep != null && Command.respCode_SUCCESS.equals(userRep.getRespCode())){
					log.info("查询用户信息成功 username:"+userRep.getUsername()+" firstLogin:"+userRep.getFirstLogin()+" userID:"+userRep.getUserID());
					firstLogin = userRep.getFirstLogin();
					log.info("firstLogin===="+firstLogin);
				}else{
					log.info("查询用户信息失败");
				}
				
				if(!Assert.empty(firstLogin) && "0".equals(firstLogin)){
					log.info("======FIRST_LOGIN START=======username="+userRep.getUsername());
					//setSession(MallCommand.FIRST_LOGIN,"1");
					User usertmp = new User();
				//	usertmp.setUsername(userRep.getUsername());
					usertmp.setUserID(userRep.getMemberCode().toString());
					usertmp.setFirstLogin("1");
					if(userActionSupport.updateUserAppendData(usertmp)){
						log.info("======FIRST_LOGIN update end=======firstLogin="+usertmp.getFirstLogin());
					}else{
						log.info("queryMerchant更新firstLogin用户信息出错。");
					}
					
				}
			}
		//} catch (AppException_Exception e) {
			//log.info("queryMerchant更新firstLogin用户信息出错。",e);
		//}
		if(!Assert.empty(isMerchantFlag) && !Assert.empty(usid)){
			uname = (String)getSession(usid);
			if("0".equals(isMerchantFlag)){
				json.put("isMerchant", "0");//isMerchantFlag是否商户标识0：普通用户 1：商户	
				Map<String,PointsAccount> pointtmp = (Map<String,PointsAccount>)getSession(SessionKey.POINTS_ACCOUNT_MAP);
				if(pointtmp == null){
					//查询积分信息
					if(!Assert.empty(usid)){
						Map<String,PointsAccount> pointsTypePointsAccountMap =pointActionSupport.queryPointsByUsername(uname);
						if(!Assert.empty(pointsTypePointsAccountMap)){
							setSession(SessionKey.POINTS_ACCOUNT_MAP, pointsTypePointsAccountMap);
							pointaccount = pointsTypePointsAccountMap.get("FFTPlatform");
							pointaccountBank = pointsTypePointsAccountMap.get("ZHBank");
						}							
					}
				}else{
					Boolean changedPoints = true;
					changedPoints = (Boolean)getSession("changedPoints");
					if(changedPoints != null && changedPoints){
						if(!Assert.empty(usid)){
							Map<String,PointsAccount> pointsTypePointsAccountMap =pointActionSupport.queryPointsByUsername(uname);
							if(!Assert.empty(pointsTypePointsAccountMap)){
								setSession(SessionKey.POINTS_ACCOUNT_MAP, pointsTypePointsAccountMap);
								pointaccount = pointsTypePointsAccountMap.get("FFTPlatform");
								pointaccountBank = pointsTypePointsAccountMap.get("ZHBank");
							}							
						}
					}else{
						pointaccount = pointtmp.get("FFTPlatform");
						pointaccountBank = pointtmp.get("ZHBank");
					}
				}					
				json.put("name", uname);
			 }
			if("1".equals(isMerchantFlag)){
				 json.put("isMerchant", "1");
				 json.put("name", "1".equals(isMerchantFlag) ? getSession(MallCommand.MERCHANTUSERSET_LOGIN_NAME)+"("+beCode+")" :uname+"("+beCode+")");	
				 json.put("beCode", beCode);
			 }			
			json.put("titleName", "1".equals(isMerchantFlag) ? getSession(MallCommand.MERCHANTUSERSET_LOGIN_NAME) :uname);
		}else{
			json.put("reno", Command.respCode_FAIL);	
		}
		json.put("firstLogin", firstLogin);
		json.put("fftPoints", pointaccount==null || pointaccount.getPoints()==null?"0":pointaccount.getPoints());	
		json.put("bankPoints", pointaccountBank==null || pointaccountBank.getPoints()==null?"0":pointaccountBank.getPoints());
		log.info("name："+uname+" userID："+usid+" isMerchantFlag："+isMerchantFlag+" firstLogin:"+firstLogin);
		sendMsg(json.toString());
	}
	
	
	
	/**
	  * 方法描述：用户注册
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2011-6-1 下午04:50:21
	  */
	public String addUser() throws IOException, AppException_Exception {
		String falgpath = "failse";
		boolean falg = true;
		//String userId= (String)getSession("userId");
		String validateCode1 = getSessionValue(Command.REGISTER_VALIDATE_CODE);
		
		if(validateCode == null || validateCode1 == null || !validateCode1.equalsIgnoreCase(validateCode)){
			message = "请输入正确的验证码。";
			falg = false;
		} else if(Validate.validateOfUserName(username)){
			username = username.toLowerCase();
			User user=new User(); 
		    user.setUsername(username);
		    
		    //获得信息
		    user = userActionSupport.getUserInfoByUsername(username);
			
		    if(user.getRespCode().equals("0")){
		    	message = "该用户名已存在。";
		    	falg = false;
		    } else if(password == null && "".equals(password)) {
		    	message = "输入的密码不能为空";
		    	falg = false;
		    }  else if(!password.equals(password1)) {
		    	message = "两次输入的密码不一致";
		    	falg = false;
		    } else if(!Validate.validateOfPwd(password)) {
		    	message = "为提高密码安全性，密码请选用6位及以上字符。";
		    	falg = false;
		    } else if(mobilephone != null && !"".equals(mobilephone) && !Validate.validateOfPhone(mobilephone)) {
		    	message = "请输入正确的手机号码。";
		    	falg = false;
		    } else if(email!= null && email.length() != 0 && !Validate.validateOfMail(email)) {
		    	message = "邮箱格式输入错误。";
		    	falg = false;
		    }
		} else {
			message = "该用户名输入有误，请使用4-20位由英文、数字及“-”、“_”组成的用户名，。";
			falg = false;
		}
		
		if(falg){
			User user = new User();
			user.setUsername(username);
			//user.setPassword(new Md5PasswordEncoder().encodePassword(password, username));
			
			//使用账户账务平台之后不需要加密传输
			user.setPassword(password);
			user.setUname(uname);
			user.setMobilephone(mobilephone);
			user.setEmail(email);
			user.setSn(logicNo);
			user.setBank(bank);
			user.setFirstLogin("0");//0：标识还未登录系统(首次登录会弹出指引页面)   1：已经登录过系统  
			user.setRegisterIP(getIpAddr(ServletActionContext.getRequest()));
			user = userActionSupport.register(user);
				
			String respcode = user.getRespCode();
			String respmsg = user.getRespMsg();
			log.info("用户=>>>> " + username + " 注册.......");
			log.info("响应码：" + respcode);
			log.info("响应信息：" + respmsg);
			
			if ("0".equals(respcode)) {
				falgpath = "success";
				message = "恭喜，注册成功，您的帐号为："+ username +" 赶快登录吧！";
//				//自动认证买家，资金渠道
//				UserCertification cert = new UserCertification();
//				cert.setUserId(user.getUserID());
//				cert.setCertificationType(TranCommand.CHECK_PHONE);//手机贴膜卡
//				cert.setPhone(mobilephone);//手机号码
//				cert.setChannelId("100001001");//资金渠道
//				cert.setState("30");
//				userCertificationActionSupport.addUserCertification(cert);
				
				//创建分分通积分账户
				Points pointRes = new Points();
				pointRes.setAccountMarked(username);
				pointRes.setAccountMarkedType(PayCommand.ACCOUNT_MARKED_TYPE);
				pointRes.setPartnerNo(TranCommand.PARTNER_ID);
				pointRes.setOrgNo(TranCommand.FFT_ORG_NO);
				pointRes.setPoints("0");
				//pointRes.setPoints("1");
				pointActionSupport.presentPoints(pointRes);
				
				HttpServletRequest request = ServletActionContext.getRequest();
				HttpServletResponse response = ServletActionContext.getResponse();  
//				String path = "/j_spring_security_check?j_username="+username+"&j_password="+password;
//				try {
//					log.info("自动登录开始");
//					request.getRequestDispatcher(path).forward(request, response);
//				} catch (ServletException e) {
//					log.info("自动登录错误",e);
//				}
				registerInfo = "恭喜注册成功，请登录";
				return "success";
			} else {
				message = user.getRespMsg();
			}
			
		}
		return falgpath;
	}
	
	public String toResgiter(){
		message="";
		return BaseActionSupport.SUCCESS;
	}

	public String toLogin(){
		//err=err;
		if(Assert.empty(message)){
			message="";
		}
		//if(!Assert.empty(err)){
			//message = (String)getSession("SPRING_SECURITY_LAST_EXCEPTION");
		//}
		
		return BaseActionSupport.SUCCESS;
	}
	
	/**
	 * 页面ajax验证用户名
	 */
	public void resgiterValidate(){
		JSONObject json = new JSONObject();
		json.put("reno", Command.respCode_FAIL);
		String validateCode1 = getSessionValue(Command.REGISTER_VALIDATE_CODE);		
		if(validateCode == null || validateCode1 == null || !validateCode1.equalsIgnoreCase(validateCode)){
			json.put("msg", "请输入正确的验证码。");
		} else if(Validate.validateOfUserName(username)){
			username = username.toLowerCase();
			User user=new User();
			User userMbile=new User();
			User userMail=new User();
		    user.setUsername(username);
		    
		    //获得信息
		    try {
				user = userActionSupport.getUserInfoByUsername(username);
			} catch (AppException_Exception e) {
				log.error("LoginAction.resgiterValidate查询用户信息出错username:"+username);
				e.printStackTrace();
				json.put("msg", "查询用户信息出错");
			}
			
		    if(user.getRespCode().equals("0")){
		    	json.put("msg", "该用户名已存在。");
		    } else if(password == null && "".equals(password)) {
		    	json.put("msg", "输入的密码不能为空");
		    }  else if(!password.equals(password1)) {
		    	json.put("msg", "两次输入的密码不一致");
		    } else if(!Validate.validateOfPwd(password)) {
		    	json.put("msg", "为提高密码安全性，密码请选用6位及以上字符。");
		    } else if(mobilephone != null && !"".equals(mobilephone) && !Validate.validateOfPhone(mobilephone)) {
		    	json.put("msg", "请输入正确的手机号码。");
		    } else if(email!= null && email.length() != 0 && !Validate.validateOfMail(email)) {
		    	json.put("msg", "邮箱格式输入错误。");
		    } else{
		    	try {
	    			if(mobilephone!=null && !Assert.empty(mobilephone.trim())){
	    				userMbile = userActionSupport.queryUserByMobilephoneOrMail(mobilephone);		    					    				
	    				if("0".equals(userMbile.getRespCode())){
	    					json.put("msg", "这个手机号码已经被注册。");
	    				}else{
	    					if(email!=null && !Assert.empty(email)){
				    			userMail = userActionSupport.queryUserByMobilephoneOrMail(email);		    			
				    			if("0".equals(userMail.getRespCode())){
				    				json.put("msg", "这个邮箱已经被注册。");
				    			}else{
				    				json.put("reno", Command.respCode_SUCCESS);
				    			}
				    		}else{
				    			json.put("reno", Command.respCode_SUCCESS);
				    		}
	    				}
	    			}else{
	    				json.put("msg", "请输入手机号码");
	    			}		    		
				} catch (AppException_Exception e) {
					log.error("LoginAction.resgiterValidate查询用户信息出错mobilephone:"+mobilephone);
					e.printStackTrace();
					json.put("msg", "查询用户信息出错");
				}
		    }
		} else {
			json.put("msg", "该用户名输入有误，请使用4-20位由英文、数字及“-”、“_”组成的用户名，。");
		}
		sendMsg(json.toString());
	}
	
	public void mobilePhoneValidate(){
		JSONObject json = new JSONObject();		
		//手机验证
		json.put("reno",Command.respCode_SUCCESS);
		json.put("msg", "系统将发送验证码到您的手机");
		sendMsg(json.toString());
	}
	
	
	/**
	 * 发送手机验证码
	 * @return
	 */
	public void phoneValidateCode(){
		
		log.info("发送手机验证码开始！");
		JSONObject json = new JSONObject();
		Boolean flagRes = false;
		//获取随机六位数的整数验证码
		Random random = new Random();
		int iRand2 = 100000 + random.nextInt(899999);
		String valCode =String.valueOf(iRand2);
		//String valCode = "123456";
		if(mobilephone == null){
			//
		}else {
			boolean isForget = false;
			boolean isExist = false;
			boolean isSpecial = false;
			if(!Assert.empty(phoneMessageType) && "0".equals(phoneMessageType)){
				
				//开始验证是否存在系统特殊处理的手机列表
				List<String> list = PhoneSpecial.phoneList;
				
				
				if(list.contains(mobilephone)){
					log.info("手机号码"+mobilephone+"存在特殊列表中");
					isSpecial = true;
					isExist = true;
					json.put("msg", "尊敬的珠海农商银行手机银行卡客户：您的银行积分已可以在分分通上消费，请直接使用该手机号码登录，密码为您的身份证号码后六位（若最后一位为字母X，请大写输入），登录后请及时修改密码。若忘记密码，请点击<a href=\"forget.action\">找回密码</a>。");
					json.put("code", Command.SEND_VALMSG_FAILED);
				}
				if(!isSpecial){
					//注册发送手机短信验证码
					if(!checkIsPhoneExist(mobilephone)){
						flagRes = smsService.sendUserRegister(mobilephone, username==null?"":username, valCode);		
					}else{
						isExist = true;
						log.info("手机号码存在！手机号码为："+mobilephone);
						json.put("msg", "尊敬的用户您好，您填写的手机号在<a href=\"http://www.f-roadpay.com.cn/\" target=\"_blank\" >方付通商城</a>已注册，可直接使用该手机号登录分分通，若您忘记原账户密码，可通过<a href=\"forget.action\">找回密码功能找回</a>。");
						json.put("code", Command.SEND_VALMSG_FAILED);
					}
				}
								
			}else if(!Assert.empty(phoneMessageType) && "1".equals(phoneMessageType)){
				//修改密码 找回
				if(checkIsPhoneExist(mobilephone)){
					flagRes = smsService.sendModifyMobileCode(mobilephone, valCode);
				}else{
					json.put("msg", "该手机号码未注册,请核对信息");
					isForget = true;
				}
				
			}else{
				flagRes = smsService.sendModifyMobile(mobilephone, valCode);
			}
			
			//flagRes = true;
			if(!isExist){
				if(flagRes){
					log.info("发送手机验证码成功！ 验证码："+valCode+" 手机号码为："+mobilephone);				
					json.put("msg", "验证码发送成功");
					json.put("code", valCode);
				}else{
					if(!isForget){
						log.info("发送手机验证码失败！手机号码为："+mobilephone);
						json.put("msg", "验证码发送失败");
						json.put("code", Command.SEND_VALMSG_FAILED);
					}
					
				}
			}
			
		}
		sendMsg(json.toString());
	}
	  
	/**
	 * *******************************************************
	 * @函数名: checkIsPhoneExist  
	 * @功能描述: 检查手机号码是否存在
	 * @输入参数: @param phoneNum
	 * @输入参数: @return true : 存在   false: 不存在
	 * @返回类型: boolean
	 * @作者: 赵肖瑶 
	 * @日期: 2013-6-5 上午11:40:23
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	private boolean checkIsPhoneExist(String phoneNum){
		boolean flag = true;
		User u = userActionSupport.isPhoneExist(phoneNum);
		if(Command.respCode_FAIL.equals(u.getRespCode())){
			flag = false;
		}
		return flag;
	}
	
	
	/**
	 * 方法描述：显示用户信息
	 * @return
	 * @throws AppException_Exception
	 */
	public String showUserInfo() throws AppException_Exception{
		userID = (String)getSession(MallCommand.LOGIN_USER_ID);
	    User user=new User();
	    user.setUserID(userID);    
	    //获得信息
	    user = userActionSupport.queryUserAllByUserID(userID);
	    
	    //取出信息
	    uname = user.getUname()==null?"":user.getUname();
	    this.lastTime= user.getLastTime()==null?"":user.getLastTime();
	    email = user.getEmail()==null?"":user.getEmail();
	    mobilephone = user.getMobilephone()==null?"":user.getMobilephone();
	    if(!Assert.empty(mobilephone)){
			String xingStr = mobilephone.substring(3, 8);
			mobilephone = mobilephone.replace(xingStr, "*****");
		}
	    username = user.getUsername()==null?"":user.getUsername();
	    if(Assert.empty(message)){
	    	message = "";	    	
	    }
	    return "userinfo";
	}
	
	/**
	 * 进入会员管理页面
	 * @return
	 * @throws AppException_Exception
	 */
	public String toMain() throws AppException_Exception{
		String falgPath = "failse";
		userID = (String)getSession(MallCommand.LOGIN_USER_ID);	 
	    if(userID != null){
	    	falgPath = "main";
	    }
	    return falgPath;
	} 
	
	public String toUserTop(){			 
	    return "top";
	}
	
	public void getName() throws AppException_Exception{
		JSONObject json = new JSONObject();		
		json.put("reno",Command.respCode_FAIL);
		
		username = (String)getSession(MallCommand.LOGIN_FAILURE_SHOW_USERNAME);
		if(username == null){
			userID = (String)getSession(MallCommand.LOGIN_USER_ID);
		    User user=new User();
		    user.setUserID(userID);  
		    //获得信息
		    user = userActionSupport.queryUserAllByUserID(userID);
			username = user.getUsername()==null?"":user.getUsername();
			if(Assert.empty(username)){
				json.put("name", "");
			}else{
				json.put("name", username);
			}   
		}else{
			json.put("name", username);
		}
		json.put("reno",Command.respCode_SUCCESS);
		sendMsg(json.toString());
	}
	
	public String toChangPassword(){
		return "success";
	}
	
	public String toChangPasswordMerchant(){
		return "success";
	}
	/**
	 * 登录验证用户名和密码和验证码
	 */
	public void validateLogin(){
		JSONObject json = new JSONObject();
		json.put("reno", Command.respCode_FAIL);
		HttpServletRequest request = ServletActionContext.getRequest();
		MerchantUserSet clerkAdmin = null;
		if(Assert.empty(username)){
			json.put("msg", "账号不能为空");
		}else if(Assert.empty(password)){	
			json.put("msg", "密码不能为空");
		}else{
			username = urlDecode(urlDecode(username,"utf-8"), "utf-8");
			username = username.trim().toLowerCase();					
			User user = null;
			
			boolean isMerchantLogin = false;
			
			if(Assert.empty(loginType)){
				json.put("msg", "登录数据异常！");
				sendMsg(json.toString());
				return ;
			}else{
				isMerchantLogin = "1".equals(loginType) ? false : true;
			}
			try {
				
				if(isMerchantLogin){
					MerchantUserSet merchantUserSetTemp = new MerchantUserSet();
					merchantUserSetTemp.setLoginName(username);
					List merchantUserSets = merchantUserSetActionSupport.getMerchantUserSetList(merchantUserSetTemp);
					if(merchantUserSets == null || merchantUserSets.size() == 0){
						log.info("无该商户帐号：" + username);
						json.put("msg", "商户信息不存在");
						sendMsg(json.toString()); 
						return ;
					}else{						
						for (Object object : merchantUserSets) {
							merchantUserSetTemp = (MerchantUserSet)object;
							if("1000".equals(merchantUserSetTemp.getBeCode())){
								break;
							}
						}
						setSession(MallCommand.MERCHANTUSERSET_LOGIN_NAME, merchantUserSetTemp.getLoginName());
						user = userActionSupport.queryUserAllByUserID(merchantUserSetTemp.getUserId());
					}
				}else{
					user = userActionSupport.queryUserByMobilephoneOrUsername(username);
//					BuyersActionSupport buyersActionSupport = (BuyersActionSupport) SpringContextUtil.getBean("BuyersActionSupport");
//					Buyers buyer = buyersActionSupport.getBuyerByUserId(user.getUserID());
//					//自动添加买家信息
//					if(buyer == null){
//						log.info("用户帐号：" + user.getUsername() + " 无买家信息，系统将自动添加卖家信息");
//						buyer = new Buyers();
//						buyer.setUserId(user.getUserID());
//						buyer.setState("30");
//						buyersActionSupport.changeToBuyers(buyer);
//					}
					
					if(user.getUsername() != null){
						log.info(user.getUsername() + "注册渠道为" + user.getCreateChannel());						
						if(user.getFirstLogin() == null || user.getFirstLogin().length() == 0){ 
							//该用户不是分分通注册用户，没有user的相关追加信息
							boolean flag = userActionSupport.inserUserAppendInfo(user);
							log.info("注册渠道:"+ user.getCreateChannel() + "|membercode:"+user.getMemberCode()+"|添加追加信息结果:"+flag);
						}
					}
					
				}
			} catch (AppException_Exception e) {
				log.error("LoginAction.validateLogin查询用户信息出错username:"+username);
				e.printStackTrace();
				json.put("msg", "账号信息异常重新登录");
			}		
			if(user != null && Command.respCode_SUCCESS.equals(user.getRespCode())){
				//判断是否商户 默认否
				boolean isMerchant = false;
				
				MerchantUserSet clerk = null;
				if(!Assert.empty(loginType) && "2".equals(loginType)){//从商户窗口登录才判断是否商户标识，否则不需验证
					//判断是否存在此操作员
					if(merchantUserSet == null){
						merchantUserSet = new MerchantUserSet();
					}
					merchantUserSet.setUserId(user.getUserID());
					merchantUserSet.setBeCode(beCode==null?"999":beCode);
					MerchantUserSet merchantUserSetReq = new MerchantUserSet();
					merchantUserSetReq.setUserId(user.getUserID());
					merchantUserSetReq.setBeCode("1000");
					List<MerchantUserSet> merchantClerkList = merchantUserSetActionSupport.getMerchantUserSetList(merchantUserSet);
					List<MerchantUserSet> merchantClerkAdminList = merchantUserSetActionSupport.getMerchantUserSetList(merchantUserSetReq);
					
					if(merchantClerkAdminList!=null && merchantClerkAdminList.size()>0){
						clerkAdmin = merchantClerkAdminList.get(0);
					}
					if(merchantClerkList!=null && merchantClerkList.size()>0){
						clerk = merchantClerkList.get(0);
						isMerchant = true;
					}					
				}
				
				if(!Assert.empty(loginType) && "2".equals(loginType) && !isMerchant){				//如果登录类型为商户，但是没有操作员相关数据
					json.put("msg", "请从普通用户窗口登录");
				}else{
					if(user==null || Command.respCode_FAIL.equals(user.getRespCode())){
						json.put("msg", "账号不存在");
					}
					String pw = ""; //获取数据库密码
					String pw2 = ""; //用户输入的密码
					if(isMerchant && clerk != null && "30".equals(clerk.getState()) && clerkAdmin !=null && "30".equals(clerkAdmin.getState())){
						pw = clerk.getBeCodepwd();//操作员密码	
						pw2 = new Md5PasswordEncoder().encodePassword(password, clerk.getLoginName()+clerk.getBeCode());
						//pw2 = password;
					}else if(!Assert.empty(loginType) && "1".equals(loginType)){//从普通会员窗口登录的会员(包括商户)
						pw = user.getPassword();//普通会员密码
						//pw2 = new Md5PasswordEncoder().encodePassword(password, user.getUsername());
					}else if(isMerchant && clerk != null && (!"30".equals(clerk.getState()) || (clerkAdmin !=null && !"30".equals(clerkAdmin.getState())))){//属于商户操作员，但是操作员已经被删除
						//deleteClerk = true;
						pw = user.getPassword();//普通会员密码
						json.put("msg", "操作员不存在");
					}else{//属于商户操作员，但是操作员数据错误
						pw = user.getPassword();//普通会员密码
						json.put("msg", "操作员不存在");
					}

					boolean isPwdRight = false;
					if(!Assert.empty(pw) && !Assert.empty(pw2) && pw.equals(pw2)){
						isPwdRight = true;
					}
					
					if(clerk != null){
						if(!Validate.validateOfPhone(username)){							
							if("30".equals(clerk.getState())){
								userActionSupport.checkMerchant(isPwdRight,clerk,json);
							}else{
								json.put("msg", "该操作员已被停用");
							}
							//是商户
						}else{
							//纯手机号码 登录
							json.put("msg", "帐号或密码错误");
						}
						
						
					}else{
						//普通会员
					//	userActionSupport.checkUser(isPwdRight,user,json);
						User userRes = userActionSupport.loginPassAccountSys(username, password, request.getRemoteAddr());
						if("0".equals(userRes.getRespCode())){
							json.put("reno", Command.respCode_SUCCESS);	
						}else{
							json.put("msg", userRes.getRespMsg());
						}
					}					
				}
			}else{
				json.put("msg", "账号不存在，请注册");
			}
		}
		sendMsg(json.toString());
	}
	
	/**
	 * 普通会员登录验证用户名和密码
	 */
//	public void validateIndexLogin(){
//		JSONObject json = new JSONObject();
//		json.put("reno", Command.respCode_FAIL);
//		if(Assert.empty(username)){
//			json.put("msg", "账号不能为空");
//		}else if(Assert.empty(password)){	
//			json.put("msg", "密码错误");
//		}else{
//			username = urlDecode(urlDecode(username,"utf-8"), "utf-8");
//			username = username.trim().toLowerCase();					
//			User user = null;
//			try {
//				user = userActionSupport.getUserInfoByUsername(username);
//			} catch (AppException_Exception e) {
//				log.error("LoginAction.validateLogin查询用户信息出错username:"+username);
//				e.printStackTrace();
//				json.put("msg", "账号信息异常重新登录");
//			}
//
//		   if(user==null || Command.respCode_FAIL.equals(user.getRespCode())){
//				json.put("msg", "账号不存在");
//			}
//			if(Command.respCode_SUCCESS.equals(user.getRespCode())){
//				String pw = user.getPassword();
//				String pw2 = new Md5PasswordEncoder().encodePassword(password, username);
//				if(pw.equals(pw2)){
//					json.put("reno", Command.respCode_SUCCESS);	
//				}else{
//					json.put("msg", "密码错误");
//				}
//			}else{
//				json.put("msg", "账号不存在，请注册");
//			}		
//		}
//		sendMsg(json.toString());
//	}
	
	/**
	 * 修改密码
	 * @throws AppException_Exception
	 * @throws IOException
	 */
	public void changePassword() throws AppException_Exception, IOException{
		JSONObject jObject = new JSONObject();
		jObject.put("reno", "1");
		boolean falg=true;
		userID = (String)getSession(MallCommand.LOGIN_USER_ID);
	    
	    User user = new User();
	    user.setUserID(userID);
	    
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
//		String validateCode1 = (String)session.getAttribute("validateCode");
		
//		if(validateCode == null || validateCode1 == null || !validateCode1.equalsIgnoreCase(validateCode)){
//			jObject.put("desc", "请输入正确的验证码");
//			falg = false;
//		} 
		if(Assert.empty(userID)){
			jObject.put("desc", "请登录");
	    	falg = false;
		}else if(oldpassword == null || "".equals(oldpassword.trim())) {
	    	jObject.put("desc", "输入的原密码不能为空");
	    	falg = false;
	    }  else if(!Validate.validateOfPwd(password)) {
	    	jObject.put("desc", "为提高密码安全性，密码请选用6位及以上字符");
	    	falg = false;
	    } else if(!password.equals(password1)) {
	    	jObject.put("desc", "新密码和确认新密码输入不一致");
	    	falg = false;
	    }
	    if(falg){
	    //获得信息
	    user = userActionSupport.queryUserAllByUserID(userID);
		if(!user.getPassword().equals(new Md5PasswordEncoder().encodePassword(oldpassword, user.getUsername()))){
			jObject.put("desc", "原密码错误");
			log.info("用户=>>>> " + username + " 修改密码失败"+",详情:"+"原密码错误");
	    } else {	    
			//user.setPassword(new Md5PasswordEncoder().encodePassword(password, user.getUsername()));	    	
	    	try {
	    	   User userRes= userActionSupport.updPassword(user.getMemberCode(),oldpassword,password);
	    	   if(Command.respCode_SUCCESS.equals(userRes.getRespCode())){
	    		   log.info("用户=>>>> " + username + userRes.getRespMsg());
	    		   //session.invalidate();
	    		   jObject.clear();
	    		   jObject.put("desc", "修改密码成功");
	    		   jObject.put("reno", "0");
	    		   String idMerchant = (String)getSession(MallCommand.LOGIN_MERCHANT_OR_USER);
					//Merchant m = merchantActionSupport.getMerchantInfo(user.getUserID());
	    		   if(!Assert.empty(idMerchant) && "1".equals(idMerchant)){
	    			   session.invalidate();
	    			   jObject.put("userType", "1");
	    		   }else{
	    			   jObject.put("userType", "2");
	    		   }
	    	    }else{
	    	    	 log.info("用户=>>>> " + username + userRes.getRespMsg());
	    	    }
	    	   
	    	} catch (AppException_Exception e) {
	    	    log.info("用户=>>>> " + username + " 修改密码失败,发生异常!");
	    	    jObject.put("desc", "修改密码失败");
	    	}

	    }
	    }
	    sendMsg(jObject.toString());   
	}	
	
	public String toChangInfo() throws AppException_Exception{
		userID = (String)getSession(MallCommand.LOGIN_USER_ID);
		if(Assert.empty(userID)){
			message="请登录账号";
		}else{
			message="";			
		}
		User user = userActionSupport.queryUserAllByUserID(userID);
		uname = user.getUname()==null?"":user.getUname();
	    email = user.getEmail()==null?"":user.getEmail();
	    mobilephone = user.getMobilephone()==null?"":user.getMobilephone();
	    if(!Assert.empty(mobilephone)){
			String xingStr = mobilephone.substring(3, 8);
			mobilephone = mobilephone.replace(xingStr, "*****");
		}
	    username = user.getUsername()==null?"":user.getUsername();
		return "success";
	}
	
	/**
	 * 修改用户信息
	 * @return
	 * @throws AppException_Exception 
	 */
	public String changeUserInfo() throws AppException_Exception{
		userID = (String)getSession(MallCommand.LOGIN_USER_ID);
		//String oldUserName = (String)getSession(userID);
		//oldUserName = Assert.empty(oldUserName)?"":oldUserName.trim().toLowerCase();
		log.info("修改会员信息开始 userID:"+userID);
		//String flagpath = "failse";   
		boolean flag = false;	
		
		if(Assert.empty(userID)) {
			message = "您还没有登录";
		} else if( "".equals(uname)) {
			message = "用户姓名不能为空";
		} else if( "".equals(username)) {
			message = "用户名不能为空";
		} else if(!Assert.empty(email) && !Validate.validateOfMail(email)) {
	    	message = "邮箱格式输入错误。";
	    }else if(uname == null || username == null){
			try {
				showUserInfo();
			} catch (AppException_Exception e) {
				message = "获取用户信息失败";
			}
		} else {
	    	flag = true;
	    }				
		if(flag){		    
		    User user = new User();	
//		    if(!Assert.empty(oldUserName) && !username.trim().toLowerCase().equals(oldUserName)){
//		    	user.setUsername(username);		    	
//		    }
		    user.setUname(uname);
		    user.setEmail(email);
		    user.setUserID(userID);
		    user.setMemberCode(Long.parseLong(userID));
		    try {
			user = userActionSupport.updateUser(user);
			if(Command.respCode_SUCCESS.equals(user.getRespCode())) {
				message = "修改用户信息成功";
				//flagpath = "userinfo";
			} else {
				message = user.getRespMsg();
				//flagpath = "failse";
			}
			log.info("用户=>>>> " + username + " " + message);
		    } catch (AppException_Exception e) {
			
			log.info("用户=>>>> " + username + " 修改用户信息失败");
			message = "修改用户信息失败";
			//flagpath = "failse"; 
		    } 
		}	
		//if("userinfo".equals(flagpath)){
			return showUserInfo();
		//}
		//return flagpath;
	}
	
	public String toChangeMobilePhone(){
			log.info("进入修改会员手机号页面 ");
			userID = (String)getSession(MallCommand.LOGIN_USER_ID);
			User user = userActionSupport.queryUserAllByUserID(userID);
			PointsAccount pointaccountBank = null;
			BigDecimal pointBank = null;
			if(Assert.empty(userID)){
				log.info("尚未登录系统,跳到首页面 userID:"+userID);
				return "login_index";
			}
			if(user != null && "0".equals(user.getRespCode())){
				mobilephone = user.getMobilephone();
				//查询是否有银行积分
				Map<String,PointsAccount> pointsTypePointsAccountMap =pointActionSupport.queryPointsByUsername(user.getUsername());
				if(!Assert.empty(pointsTypePointsAccountMap)){
					pointaccountBank = pointsTypePointsAccountMap.get("ZHBank");
					pointBank = new BigDecimal(pointaccountBank == null || pointaccountBank.getPoints()==null?"0":pointaccountBank.getPoints());
				}
			}
			if(!Assert.empty(mobilephone)){
				String xingStr = mobilephone.substring(3, 8);
				mobilephone = mobilephone.replace(xingStr, "*****");
			}
			if(pointBank != null && pointBank.compareTo(new BigDecimal("0")) > 0){
				log.info("拥有银行积分 bankpoints:"+pointBank.toString());
				message="";
				return "bank_point_success";
			}
			
			
			message="";
		return "success";
	}
	
	/**
	 * 验证修改用户手机号码的前置条件
	 * @return
	 * @throws AppException_Exception 
	 */
	public String validateChangeMobilePhone() throws AppException_Exception{
		String isMerchantFlag = (String)getSession(MallCommand.LOGIN_MERCHANT_OR_USER);
		userID = (String)getSession(MallCommand.LOGIN_USER_ID);
		String uname = (String)getSession(userID);
		log.info("验证修改会员手机号条件开始 userID:"+userID);
		String flagpath = "failse";   
		User user = null;
		if(Assert.empty(password)){
			message = "请输入会员密码";
			return flagpath;
		}
		String pwd2 = new Md5PasswordEncoder().encodePassword(password, uname);
		if(!Assert.empty(userID)){
			user = userActionSupport.queryUserAllByUserID(userID);
		}else{
			message = "请登录";
			flagpath = "login_index";
			return flagpath;
		}
		
		if(user != null && "0".equals(user.getRespCode()) && pwd2.equals(user.getPassword()) 
				&& !Assert.empty(isMerchantFlag) && "0".equals(isMerchantFlag)){
			if(tempPoint!=null && !Assert.empty(tempPoint.getAccountName()) && !Assert.empty(tempPoint.getIdentificationCard())){
				List<TempPoint>	pointList=tempPointActionSupport.getTempPoint(tempPoint);//查询临时银行积分信息（一份身份证可能对应多个银行账户）
				if(pointList == null || pointList.size() == 0){
					message = "账户开户名与身份证号码有误，请重新输入！";
				}else{
					flagpath = "success";
					message="";
					validatePass = "1";
					log.info("验证修改会员手机号条件通过 userID:"+userID+"tempPoint.AccountName："+tempPoint.getAccountName()
							+"tempPoint.identificationCard："+tempPoint.getIdentificationCard());
					return flagpath;
				}
			}else{
				flagpath = "success";
				validatePass = "1";
				message="";
				log.info("验证修改会员手机号条件通过  userID:"+userID);
				return flagpath;
			}
		}else if(!Assert.empty(isMerchantFlag) && "1".equals(isMerchantFlag)){
			log.info("验证修改会员手机号条件不通过，不是普通会员登录。userID:"+userID);
			message = "不是普通会员登录，不能修改手机号码";
			flagpath = "login_index";
			return flagpath;
		}else{
			log.info("验证修改会员手机号条件不通过，密码错误。userID:"+userID);
			message = "登录密码错误";
			return flagpath;
		}

		return flagpath;
	}
	
	public void valChangePhone(){
		JSONObject json = new JSONObject();		
		json.put("reno",Command.respCode_SUCCESS);
		User user = null;
		if(Assert.empty(mobilephone)){
			message = "请输入新的手机号码";
			validatePass = "1";
		}else{
			try {
				user = userActionSupport.queryUserByMobilephoneOrUsername(mobilephone);
			} catch (AppException_Exception e) {
				log.info("valChangePhone修改手机号码验证手机号是否已经存在查询出错。mobilephone:"+mobilephone,e);
			}
		}
		if(user != null && Command.respCode_SUCCESS.equals(user.getRespCode())){
			json.put("reno",Command.respCode_FAIL);
		}
		sendMsg(json.toString());
	}
	
	/**
	 * 修改用户手机号码
	 * @return
	 * @throws AppException_Exception 
	 */
	public String changeMobilePhone() throws AppException_Exception{
		userID = (String)getSession(MallCommand.LOGIN_USER_ID);
		String uname = (String)getSession(userID);
		User userRep = null;
		log.info("修改会员手机号开始 userID:"+userID+" 手机号码："+mobilephone);   
		if(Assert.empty(userID)){
			log.info("修改手机号码：尚未登录系统,跳到首页面 userID:"+userID);
			return "login_index";
		}
		
		if(Assert.empty(mobilephone)){
			message = "请输入新的手机号码";
			validatePass = "1";
			return "failse";
		}else{
			userRep = userActionSupport.queryUserByMobilephoneOrUsername(mobilephone);
		}
		if(userRep != null && Command.respCode_SUCCESS.equals(userRep.getRespCode())){
			message = "该手机号码已存在，请重新输入手机号码！";
			validatePass = "1";
			return "failse";
		}
		//验证手机银行卡
		boolean flag = userActionSupport.updateChangeMobilePhone(mobilephone,userID);
		if(flag){
			message = "修改成功";
		}else{
			message = "修改失败";
			validatePass = "1";
			return "failse";
		}

		return "success";
	}
	
	
	public String toForget(){
		return "success";
	}
	
	/**
	  * 方法描述：忘记密码
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-3-21 下午2:51:17
	  */
	public String forgetPwd(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
			JSONObject jsonObject = new JSONObject();
			try {
				User user = userActionSupport.queryUserByMobilephoneOrMailOrSn(loginKey);
				if(user != null && Command.respCode_SUCCESS.equals(user.getRespCode())){
					username = user.getUsername();
					userID = user.getUserID();
					mobilephone = user.getMobilephone();
					// 随机数重置密码
					password = String.valueOf(new Random().nextInt(899999) + 100000);
					if(Validate.validateOfMail(loginKey)){
						message = "请用手机号码找回密码";
//						LoginManager loginManager = new LoginManager();
//						loginManager.setToEmailAddress(user.getEmail());
//						loginManager.setUsername(username);
//						loginManager.setUserID(userID);
//						loginManager.setPassword(new Md5PasswordEncoder().encodePassword(password, username));
//						loginManager.setActivatecode(password);
//						LoginManager loginManagerReq = userActionSupport.initPassword(loginManager);
//						if(Command.respCode_FAIL.equals(loginManagerReq.getRespCode())){
//					    	//jsonObject.put("msg", loginManagerReq.getRespMsg());
//							type="";
//							message = loginManagerReq.getRespMsg();
//						}
//						if(Command.respCode_SUCCESS.equals(loginManagerReq.getRespCode())){
//							jsonObject.put("reno", "0");
//							type = "邮箱";
//							message = "请使用它登录您的系统，然后重置密码!";
//						}
					} else {
						//user.setPassword(new Md5PasswordEncoder().encodePassword(password, username));
				    //	try {
				    		user = userActionSupport.forgetPwdToGetIt(user);
				    		if(user != null && Command.respCode_SUCCESS.equals(user.getRespCode())){
				    			log.info("用户=>>>> " + username+ " 修改密码成功");
				        	    //jsonObject.put("msg", user.getRespMsg());
				        		type="手机";
				        		message = "密码已发送到您的手机，请查收，建议您登陆后修改密码";
				        	    jsonObject.put("reno", "0");
				        	    
				        	    //修改锁定状态，如果有被锁定的则接触锁定
//				        	    User u = new User();
//				        	    u.setUsername(username);
//				        	    //将状态改为正常状态
//								u.setIsAccountLocked("1");								
//								//将连续错误次数归0
//								u.setLoginFailureCount(0);
//								u.setLastTryTime(sdf.format(new Date()));
//								u.setLockedDate("");
								//更新
//								try {
//									userActionSupport.updateUserLastInfoByUsername(u);
//									log.info("username"+username+"已经找回密码，系统将其解锁");
//								} catch (AppException_Exception e) {
//									log.info("在更新用户信息为，用户名："+u.getUsername()+"出现异常",e);
//								}
				    		}else{
				    			log.info("用户=>>>> " + username+ " 修改密码失败");
				        	    //jsonObject.put("msg", user.getRespMsg());
				        		type="手机";
				        		message = "重置密码失败";
				        	    jsonObject.put("reno", "1");
				    		}
//				    		
//				    	} catch (AppException_Exception e) {
//				    	    log.info("用户=>>>> " + username + " 修改密码异常");
//				    	    //jsonObject.put("msg", "重置密码失败");
//				    	    jsonObject.put("reno", "1");
//				    	    type="";
//				    	    message = "重置密码失败";
//				    	}
				    	//发送手机短信临时密码
//						boolean flag = smsService.sendForgePassword(mobilephone,username, password);	
//						if(flag){
//							log.info("发送手机短信临时密码成功！ 用户名："+username+" 手机号码为："+mobilephone);				
//						}else{
//							log.info("发送手机短信临时密码失败！ 用户名："+username+" 手机号码为："+mobilephone);	
//						}
					} 
					
					if(jsonObject.getString("reno").equals("0")){
						setSession("login_username", username);
					}
//					Merchant merchant = merchantActionSupport.getMerchantByUserId(userID);
//					if(merchant != null && userID.equals(merchant.getUserId())){//如果是商户找回密码，需要同时修改商户的操作员管理员密码
//						log.info("商户找回密码  password:"+password);
//						merchantUserSet = new MerchantUserSet();
//						merchantUserSet.setUserId(userID);
//						merchantUserSet.setBeCodepwd(password);
//						merchantUserSet.setBeCode("1000");
//						merchantUserSetActionSupport.updatePwdByUserIdAndBecode(merchantUserSet);
//					}
				} else {
					type="";
					message = "该用户未注册！";
				}
			} catch (Exception e) {
				log.info("找回密码出错",e);
			}
			return "success";
	}
	
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}
	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	public LoginManagerActionSupport getLoginManagerActionSupport() {
		return loginManagerActionSupport;
	}
	public void setLoginManagerActionSupport(LoginManagerActionSupport loginManagerActionSupport) {
		this.loginManagerActionSupport = loginManagerActionSupport;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getOldpassword() {
		return oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public int getSessionID() {
		return sessionID;
	}

	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}

	public String getActiveUrl() {
		return activeUrl;
	}

	public void setActiveUrl(String activeUrl) {
		this.activeUrl = activeUrl;
	}

	public String getActivatecode() {
		return activatecode;
	}

	public void setActivatecode(String activatecode) {
		this.activatecode = activatecode;
	}

	public String getBackurl() {
		return backurl;
	}

	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLogicNo() {
		return logicNo;
	}

	public void setLogicNo(String logicNo) {
		this.logicNo = logicNo;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getLastTime() {
		return lastTime;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getLoginKey() {
		return loginKey;
	}

	public void setLoginKey(String loginKey) {
		this.loginKey = loginKey;
	}

	public String getValidateType() {
		return validateType;
	}

	public void setValidateType(String validateType) {
		this.validateType = validateType;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}
	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
//	public String getErr() {
//		return err;
//	}

//	public void setErr(String err) {
//		this.err = err;
//	}
	
	public static void main(String args[]){
		//fcea920f7412b5da7be0cf42b8c93759 1234567 cb6ec2d88eb597d90bc0b812e6643bb3
		//c3bb0ee3c8085601255a45bd369b3593 123456   lqp5   185a940ef32bfefa52d40f50e341cde0
		//3543a6534e7e05768e1770d48014268f 123456   u1304115
//		String t1 = "lance";
//		String t2 = "1001";
//		String ttt=t1+t2;
//		String tt = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1000");
//		String tt1 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1001");
//		String tt2 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1002");
//		String tt3 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1003");
//		String tt4 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1000");
//		String tt5 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1001");
//		String tt6 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1002");
//		String tt7 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1003");
//		String tt8 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1000");
//		String tt9 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1001");
//		String tt10 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1002");
//		String tt11 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1003");
//		String tt12 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1000");
//		String tt13 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1001");
//		String tt14 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1002");
//		String tt15 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1003");
//		String tt16 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1000");
//		String tt17 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1001");
//		String tt18 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1002");
//		String tt19 = new Md5PasswordEncoder().encodePassword("123456","user15992680397"+"1003");
		String tt19 = new Md5PasswordEncoder().encodePassword("111111","guanghua");
		System.out.println(tt19);
		//String str1 = "2013-03-18|17:18:50";e7299e5fce232fb8e5428f7be4cfcd12
		
		//ba236a06922f38a349a60cfe8ff49b64
		//String str2 = "2013-03-18|17:18:51";
		//int result=str1.compareTo(str2);
		//BigDecimal currency = new BigDecimal("0.212345");
		//BigDecimal goodsNum = new BigDecimal("11111.433");
		//String tt = String.valueOf(currency.multiply(goodsNum,2,RoundingMode.DOWN));
//		System.out.println("***********************tt="+tt);
//		System.out.println("***********************tt1="+tt1);
//		System.out.println("***********************tt2="+tt2);
//		System.out.println("***********************t3t="+tt3);
//		System.out.println("***********************t3t="+tt19);
		//String mobilephone2 = "13918140393";
		//String valcode = "123455";
		//String context="感谢您注册会员，注册手机号为"+mobilephone2+",验证码是"+valcode;
		//PhoneUtil.sendMsg(mobilephone2, "", context);
	}
	
	public String getAnticache() {
		return anticache;
	}

	public void setAnticache(String anticache) {
		this.anticache = anticache;
	}

	
	/**
	 * 注册条款
	 */
	public String regProvision(){
		return "provision";
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getFftPoints() {
		return fftPoints;
	}

	public void setFftPoints(String fftPoints) {
		this.fftPoints = fftPoints;
	}

	public PointActionSupport getPointActionSupport() {
		return pointActionSupport;
	}

	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}

	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	}

	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getBeCode() {
		return beCode;
	}

	public void setBeCode(String beCode) {
		this.beCode = beCode;
	}

	public String getClerkPwd() {
		return clerkPwd;
	}

	public void setClerkPwd(String clerkPwd) {
		this.clerkPwd = clerkPwd;
	}

	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	}

	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
	}

	public MerchantUserSet getMerchantUserSet() {
		return merchantUserSet;
	}

	public void setMerchantUserSet(MerchantUserSet merchantUserSet) {
		this.merchantUserSet = merchantUserSet;
	}

	public SmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public UserCertificationActionSupport getUserCertificationActionSupport() {
		return userCertificationActionSupport;
	}

	public void setUserCertificationActionSupport(
			UserCertificationActionSupport userCertificationActionSupport) {
		this.userCertificationActionSupport = userCertificationActionSupport;
	}

	public String getPhoneMessageType() {
		return phoneMessageType;
	}

	public void setPhoneMessageType(String phoneMessageType) {
		this.phoneMessageType = phoneMessageType;
	}

	public SellersActionSupport getSellerActionSupport() {
		return sellerActionSupport;
	}

	public void setSellerActionSupport(SellersActionSupport sellerActionSupport) {
		this.sellerActionSupport = sellerActionSupport;
	}

	public String getRegisterInfo() {
		return registerInfo;
	}

	public void setRegisterInfo(String registerInfo) {
		this.registerInfo = registerInfo;
	}

	public TempPoint getTempPoint() {
		return tempPoint;
	}

	public void setTempPoint(TempPoint tempPoint) {
		this.tempPoint = tempPoint;
	}

	public TempPointActionSupport getTempPointActionSupport() {
		return tempPointActionSupport;
	}

	public void setTempPointActionSupport(
			TempPointActionSupport tempPointActionSupport) {
		this.tempPointActionSupport = tempPointActionSupport;
	}

	public String getValidatePass() {
		return validatePass;
	}

	public void setValidatePass(String validatePass) {
		this.validatePass = validatePass;
	}

	public StoreActionSupport getStoreActionSupport() {
		return storeActionSupport;
	}

	public void setStoreActionSupport(StoreActionSupport storeActionSupport) {
		this.storeActionSupport = storeActionSupport;
	}
}
