package com.froad.action.api.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.froad.action.api.BaseApiAction;
import com.froad.action.api.command.Encrypt;
import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.MemberCollectActionSupport;
import com.froad.action.support.MerchantTrainActionSupport;
import com.froad.action.support.PointActionSupport;
import com.froad.action.support.StoreActionSupport;
import com.froad.action.support.SuggestionActionSupport;
import com.froad.action.support.SuggestionReplyActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.client.Store.Store;
import com.froad.client.buyers.Buyers;
import com.froad.client.memberCollect.MemberCollect;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantTrain.MerchantTrain;
import com.froad.client.point.Points;
import com.froad.client.point.PointsAccount;
import com.froad.client.suggestion.Suggestion;
import com.froad.client.suggestionReply.SuggestionReply;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.User;
import com.froad.common.TranCommand;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.JsonUtil;
import com.froad.util.SpringContextUtil;
/** 
 * @author FQ 
 * @date 2013-2-7 上午09:38:59
 * @version 1.0
 * 用户
 */
public class UserAction extends BaseApiAction {
	private static Logger logger = Logger.getLogger(UserAction.class);
	private UserActionSupport userActionSupport;
	private PointActionSupport pointActionSupport;
	private SuggestionActionSupport  suggestionActionSupport;	
	private SuggestionReplyActionSupport  suggestionReplyActionSupport;
	private MemberCollectActionSupport memberCollectActionSupport;
	private MerchantTrainActionSupport merchantTrainActionSupport;	 
	private String reno;//操作结果码
	private String remsg;//响应信息
	private Map<String,Object> rebody=new HashMap<String,Object>();//主体
	private String recount;//返回请求标识号（原样返回）
	private String returnTime;//返回时间
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss"); 
	
	public String login1(){
		log.info("测试 login");
		System.out.println(body);
		
		reno="0000";
		remsg="测试成功";
		rebody.put("username", "张三");
		rebody.put("mobile", "13838384438");
		count="1";
		
		return SUCCESS;
	}
	public String registered(){
		logger.info("[UserAction- registered]body:"+body);
		body = new Encrypt().clientDeEncrypt("0101",body);
		logger.info("解密后body"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String mobilephone = jsonObject.getString("mobilephone");
			String password = jsonObject.getString("password");
			String code = jsonObject.getString("code");
			if(password.length()<6){
				reno="0003";
				return SUCCESS;
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String smCode = (String)session.getAttribute(FroadAPICommand.MOBILE_SM_SESSION_SMCODE_REGISERED);
			String mobile = (String)session.getAttribute(FroadAPICommand.MOBILE_SM_SESSION_MOBILE_REGISERED);
			if(!code.equals(smCode) || !mobilephone.equals(mobile)){
				reno="0004";
			}else{
				User user = new User();
				user.setMobilephone(mobilephone);
				user.setPassword(password);
				
//				String userName="u"+mobilephone;
//				log.info("手机端注册，自动生成UserName:"+userName);
//				user.setUsername(userName);				
				
				user = userActionSupport.clientRegister(user);
				if("0".equals(user.getRespCode())){
					rebody.put("userId", user.getUserID());
					rebody.put("username", user.getUsername());
					rebody.put("msg", "您已成功注册为会员，如需购买商品，还需使用pc进行激活操作！");
					reno="0000";
					logger.info(mobilephone+","+user.getRespMsg());
				}else if("1".equals(user.getRespCode()) &&  user.getMsgCode()== 2005){
					reno="0006";				 
				}else{
					reno="0007";
				}
				session.removeAttribute(FroadAPICommand.MOBILE_SM_SESSION_SMCODE_REGISERED);
				session.removeAttribute(FroadAPICommand.MOBILE_SM_SESSION_MOBILE_REGISERED);
			} 
			  
		} catch (Exception e) {
			reno="9999"; //异常
			logger.error("login Exception",e);
		}
		
		return SUCCESS;
		
	}
			
	/**
	 * 
	  * 方法描述：用户登录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-18 上午11:56:21
	 */
	public String login(){
		JSONObject json = new JSONObject();
		logger.info("[UserAction- login]body:"+body);
		body = new Encrypt().clientDeEncrypt("0102",body);
		logger.info("解密后body"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userInfo = jsonObject.getString("user");
			String type = jsonObject.getString("type");
			String os = jsonObject.getString("os");
			String password = (String)JsonUtil.getOptionalValue(jsonObject,"password");
			String pin = (String)JsonUtil.getOptionalValue(jsonObject,"pin");
			if(Assert.empty(userInfo) || Assert.empty(type)){
	 			 reno="0009";
	 			return SUCCESS;
	 		}
			if("10".equals(type)){
				if(Assert.empty(password) ){
		 			 reno="0009";
		 			return SUCCESS;
		 		}
			}else{
				if(Assert.empty(pin) ){
		 			 reno="0009";
		 			return SUCCESS;
		 		}
			}
			
			User user = userActionSupport.queryUserByMobilephoneOrMailOrSn(userInfo);
			BuyersActionSupport buyersActionSupport = (BuyersActionSupport) SpringContextUtil.getBean("BuyersActionSupport");
			if(type.equals("20") && (user.getUserID() != null && user.getUserID().length() != 0)){
				Buyers buyer = buyersActionSupport.getBuyerByUserId(user.getUserID());
				if(buyer == null){
					logger.info("用户帐号：" + user.getUsername() + " 无买家信息，系统将自动添加卖家信息");
					buyer = new Buyers();
					buyer.setUserId(user.getUserID());
					buyer.setState("30");
					buyersActionSupport.changeToBuyers(buyer);
				}
			}
			
			
			if(Command.respCode_FAIL.equals(user.getRespCode())){
				 reno="0101";
				 return SUCCESS;
			}
			
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
				
			if("10".equals(type)){			
				User u = userActionSupport.loginPassAccountSys(userInfo, password, "127.0.0.1");
				if("1".equals(u.getRespCode())){
					if(u.getMsgCode()== 2301){
						reno="0102";
						remsg=u.getRespMsg();
						return SUCCESS;
					}else if(u.getMsgCode()== 2306 || u.getMsgCode()== 2305){
						reno="0107";
						return SUCCESS;
					}else if(u.getMsgCode()== 2307){
						reno="0108";
						return SUCCESS;
					}else{
						reno="9999";
						return SUCCESS;
					}
				}
//				String pw2 = new Md5PasswordEncoder().encodePassword(password, user.getUsername());
//				boolean isPwdRight = user.getPassword().equals(pw2);		
//				userActionSupport.checkUser(isPwdRight,user,json);
//				if("0".equals(user.getIsAccountLocked())){
//					reno="0107";
//					return SUCCESS;
//				}	
//				if(Command.respCode_FAIL.equals(json.getString("reno"))){
//					reno="0102";
//					remsg=json.getString("msg");
//					return SUCCESS;
//				}							
			} else if("20".equals(type)) {
				//判断你是否过了锁定时间
//				boolean flag=true;
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
//				Date now = new Date();
//				String LockedDate=user.getLockedDate();
//				if(LockedDate!=null && !"".equals(LockedDate) && now.before(sdf.parse(LockedDate))){
//					flag=false;
//				}
//				if(flag){
//					//将状态改为正常状态
//					user.setIsAccountLocked("1");					
//					//将连续错误次数归0
//					user.setLoginFailureCount(0);
//					user.setLastTryTime(sdf.format(new Date()));
//					user.setLockedDate("");
//					//更新
//					userActionSupport.updateUser(user);
//				}else{
//					reno="0107";
//					return SUCCESS;
//				}
				if("3".equals(user.getStatus())){
					reno="0107";
					return SUCCESS;
				}else if("4".equals(user.getStatus())){
					reno="0108";
					return SUCCESS;
				}
				
				String smCode = (String)session.getAttribute(FroadAPICommand.MOBILE_SM_SESSION_SMCODE_LOGIN);
				String mobile = (String)session.getAttribute(FroadAPICommand.MOBILE_SM_SESSION_MOBILE_LOGIN);
				if(!pin.equals(smCode) || !userInfo.equals(mobile))
					reno="0004"; 
			} else {
				reno="0009"; 
			}
			if(reno==null||"".equals(reno)){
				rebody.put("username", user.getUsername());
				rebody.put("userId", user.getUserID());
				reno="0000";
				User user2 = new User();
				//记录最后一次登录系统到的信息
//				String ip =Util.getIpAddr(request);
				String ip="";
				user2.setUsername(user.getUsername());
				user2.setLastIP(ip);
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				user2.setLastTime(df.format(new Date()).toString());
				user = userActionSupport.updateUserLastInfoByUsername(user2);
				session.removeAttribute(FroadAPICommand.MOBILE_SM_SESSION_MOBILE_LOGIN);
				session.removeAttribute(FroadAPICommand.MOBILE_SM_SESSION_SMCODE_LOGIN);
			}
		} catch (Exception e) {
			reno="9999"; //异常
			logger.error("login Exception",e);
		}
		
		return SUCCESS;
	}
		
	/**
	 * 
	  * 方法描述：用户密码修改
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-18 上午11:56:21
	 */
	public String changePwd(){
		logger.info("[UserAction- changePwd]body:"+body);
		body = new Encrypt().clientDeEncrypt("0103",body);
		logger.info("解密后body"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid = jsonObject.getString("userId");
			String oldpw = jsonObject.getString("oldpw");
			String newpw = jsonObject.getString("newpw");
			User  userReq =  getUserInfo(userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				if(oldpw.equals(newpw)){//新旧密码相同，无需修改
					reno="0008";
					return SUCCESS;
				}
				String username = userReq.getUsername();
				String password1 = new Md5PasswordEncoder().encodePassword(oldpw, username);
				if(!userReq.getPassword().equals(password1)){
					 reno="0106";
					 return SUCCESS;
				}
				userReq = userActionSupport.updPassword(Long.valueOf(userReq.getUserID()),oldpw,newpw);
				if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
					reno="0000";
				}else{
					reno="9999";
				}
				
			}else{
				logger.info("该用户不存在。");
				reno="0101"; 
			} 
			
		} catch ( Exception e) {
			reno="9999";
			logger.error("changePwd 异常",e);
		} 
		return SUCCESS;
	}
	/**
	 * 
	  * 方法描述：用户手机号码修改
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-18 上午11:56:21
	 */
	public String changeUserPhone(){
		logger.info("[UserAction- changeUserPhone]body:"+body);
		body = new Encrypt().clientDeEncrypt("0105",body);
		logger.info("解密后body"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId");
			String oldphone  = jsonObject.getString("oldphone");
			String newphone  = jsonObject.getString("newphone");
			User userReq = getUserInfo(userid);
			if(Command.respCode_FAIL.equals(userReq.getRespCode())){
				logger.info("该用户不存在。");
				reno="0101"; 
				return SUCCESS;
			}
			if(!userReq.getMobilephone().equals(oldphone)){
				 logger.info("用户手机号码错误。");
				 reno="0105";  
				 return SUCCESS;
			}
			if(userReq.getMobilephone().equals(newphone)){
				 logger.info("用户手机号码错误。");
				 reno="0109";  
				 return SUCCESS;
			}
			boolean flag = userActionSupport.updateChangeMobilePhone(newphone, userReq.getUserID());
			if(flag)
			   reno="0000";
			else
				reno="0104";  
		}catch(Exception e){
			reno="9999";
			logger.error("changeUserPhone 异常",e);
		}
		return SUCCESS;
	}
	/**
	 * 
	  * 方法描述：用户信息修改
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-18 上午11:56:21
	 */
	public String changeUserInfo(){
		logger.info("[UserAction- changeUserInfo]body:"+body);
		body = new Encrypt().clientDeEncrypt("0106",body);
		logger.info("解密后body"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId");
			String uname = (String)JsonUtil.getOptionalValue(jsonObject,"uname");
			String email = (String)JsonUtil.getOptionalValue(jsonObject,"email");
			String sex = (String)JsonUtil.getOptionalValue(jsonObject,"sex");
			String age = (String)JsonUtil.getOptionalValue(jsonObject,"age");
			uname = urlDecode(uname, FroadAPICommand.Charset); 
			User userReq;
			userReq =  getUserInfo (userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				User user = new User();
				user.setMemberCode(Long.valueOf(userReq.getUserID()));
				user.setUname(uname);
				user.setEmail(email);
				user.setSex(sex);
				if(!"".equals(age))
				   user.setAge(Integer.parseInt(age));
				userReq = userActionSupport.updateUser(user);
				if(Command.respCode_SUCCESS.endsWith(userReq.getRespCode()))
					reno="0000";
				else
					reno="0104";  
			}else{
				logger.info("该用户不存在。");
				reno="0101"; 
				return SUCCESS;
			} 
		}catch(Exception e){
			reno="9999";
			logger.error(e);
		}
		
		return SUCCESS;
	}
	
	public String queryUserInfo(){
		logger.info("[UserAction- queryUserInfo]body:"+body);
		body = new Encrypt().clientDeEncrypt("0113",body);
		logger.info("解密后body"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId");
			User userReq;
			userReq =  getUserInfo (userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				reno="0000";
				rebody.put("userId",userReq.getUserID() );
				rebody.put("username",userReq.getUsername() );
				rebody.put("uname",userReq.getUname()==null?"":userReq.getUname() );
				rebody.put("email", userReq.getEmail()==null?"":userReq.getEmail());
				rebody.put("sex", userReq.getSex()==null?"":userReq.getSex());
				rebody.put("age", userReq.getAge()==null?"":userReq.getAge());
			}else{
				logger.info("该用户不存在。");
				reno="0101"; 
				return SUCCESS;
			}
		}catch(Exception e){
			reno="9999";
			logger.error(e);
		}
		
		return SUCCESS;
	}
	/**
	 * 
	  * 方法描述：积分账户查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-19 下午04:55:07
	 */
	public String queryPointAccount(){
		logger.info("[UserAction- queryPointAccount]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId");
			String orgNo = (String)JsonUtil.getOptionalValue(jsonObject,"orgNo");  
			User  userReq =getUserInfo(userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				Points point= new Points();
				point.setPartnerNo(TranCommand.PARTNER_ID);
				point.setOrgNo((orgNo==null?"":orgNo));
				point.setAccountMarked(userReq.getUsername());
				point.setAccountMarkedType(FroadAPICommand.ACCOUNT_MARKED_TYPE);
				Points pointRes=pointActionSupport.queryPoints(point);
				JSONArray array = new JSONArray();
				if(pointRes!=null&&"0000".equals(pointRes.getResultCode())){
					 List<PointsAccount> account =pointRes.getPointsAccountList();
					for(int i=0;i<account.size();i++){
						JSONObject orgs = new JSONObject();
						if(TranCommand.FFT_ORG_NO.equals( account.get(i).getOrgNo())){
							orgs.put("accountId",account.get(i).getAccountId());
							orgs.put("point",account.get(i).getPoints().replace(",", ""));
							orgs.put("orgNo",account.get(i).getOrgNo());
							orgs.put("orgName","分分通积分");
							orgs.put("frozenPoints", account.get(i).getFrozenPoints());
							array.add(orgs);
						}else if(TranCommand.BANK_ORG_NO.equals( account.get(i).getOrgNo())){
							orgs.put("accountId",account.get(i).getAccountId());
							orgs.put("point",account.get(i).getPoints().replace(",", ""));
							orgs.put("orgNo",account.get(i).getOrgNo());
							orgs.put("orgName","银行积分");
							orgs.put("frozenPoints", account.get(i).getFrozenPoints());
							array.add(orgs);
						}
						
					}
					reno="0000";
				}else{
					//失败
					/*JSONObject emptyaccount = new JSONObject();
					emptyaccount.put("accountId","");
					emptyaccount.put("point","0");
					emptyaccount.put("orgNo","");
					emptyaccount.put("orgName","分分通积分");
					emptyaccount.put("frozenPoints", "0" );
					array.add(emptyaccount);
					emptyaccount.put("accountId","");
					emptyaccount.put("point","0");
					emptyaccount.put("orgNo", "");
					emptyaccount.put("orgName","银行积分");
					emptyaccount.put("frozenPoints", "0");
					array.add(emptyaccount);*/
					reno="0000";
				}
				rebody.put("orgNoList", array);
			}else{
				logger.info("该用户不存在。");
				reno="0101"; 
				return SUCCESS;
			} 
			
		}catch(Exception e){
			reno="9999";
			logger.error("queryPointAccount异常",e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	  * 方法描述：意见建议查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-19 下午04:55:07
	 */
	public String querySuggestion(){
		logger.info("[UserAction- querySuggestion]body:"+body);
		try {
			int pagesize =1,maxsize=10;
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId");
			String page = (String)JsonUtil.getOptionalValue(jsonObject,"page"); 
			String count = (String)JsonUtil.getOptionalValue(jsonObject,"count"); 
			String suggestionId = (String)JsonUtil.getOptionalValue(jsonObject,"suggestionId");
			if(page!=null&&!"".equals(page)){
				pagesize = Integer.parseInt(page);
			}
			if(count!=null&&!"".equals(count)){
				maxsize =Integer.parseInt(count);
			}
			User userReq =getUserInfo(userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				Suggestion suggestion = new Suggestion();
			  	suggestion.setUserName(userReq.getUsername());
				suggestion.setUserId(userReq.getUserID());
				suggestion.setPageSize(maxsize);
				suggestion.setPageNumber(pagesize);
				if(!"".equals(suggestionId)){
					suggestion.setId(Integer.parseInt(suggestionId));
				} 
				suggestion = suggestionActionSupport.getSuggestionListByPager(suggestion);
				 if(suggestion.getList()!=null){ 
					 reno="0000";
					 long pagenum = suggestion.getTotalCount()%maxsize == 0 ? suggestion.getTotalCount()/maxsize : suggestion.getTotalCount()/maxsize+1;
					 rebody.put("page", pagesize);
					 rebody.put("hasNext", pagenum>pagesize?true:false);
					 JSONArray array = new JSONArray();
					 for(int i=0;i<suggestion.getList().size();i++){
						 JSONObject json = new JSONObject();
						 Suggestion member = (Suggestion)suggestion.getList().get(i);
						 json.put("suggestionId", member.getId());
						 json.put("title", member.getTitle());
						 json.put("content", member.getContent());
						 json.put("is_Reply", member.getIsReply());
						 json.put("createTime", member.getCreateTime());
						 array.add(json);
					 }
					 rebody.put("suggestlist", array);
				 }
			}else{
				logger.info("该用户不存在。");
				reno="0101"; 
				return SUCCESS;
			} 
		}catch(Exception e){
			reno="9999";
			logger.error("querySuggestion异常",e);
		}
		return SUCCESS;
	}
	/**
	 * 
	  * 方法描述：添加建议查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-19 下午04:55:07
	 */
	public String addSuggestion(){  
		logger.info("[UserAction- addSuggestion]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId");
			String title =  jsonObject.getString("title");
			String content =  jsonObject.getString("content");
			String remark =  (String)JsonUtil.getOptionalValue(jsonObject,"remark");
			title = urlDecode(title, FroadAPICommand.Charset);
			content = urlDecode(content, FroadAPICommand.Charset);  
			remark = urlDecode(remark, FroadAPICommand.Charset);
			User userReq =getUserInfo(userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				Suggestion suggestion = new Suggestion();
				suggestion.setUserName(userReq.getUsername());
				suggestion.setUserId(userReq.getUserID());
				suggestion.setUserMobile(userReq.getMobilephone());
				suggestion.setUserEmail(userReq.getEmail());
				suggestion.setTitle(title);
				suggestion.setContent(content);
				suggestion.setState("20");
				suggestion.setIsReply("0");
				suggestion.setRemark(remark);
				suggestionActionSupport.addSuggestion(suggestion);
				reno="0000"; 
			}else{
				logger.info("该用户不存在。");
				reno="0101"; 
				return SUCCESS;
			} 
		}catch(Exception e){
			reno="9999";
			logger.error("addSuggestion异常",e);
		}	
		return SUCCESS;
	}
	/**
	 * 
	  * 方法描述： 查询建议回复
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-19 下午04:55:07
	 */
	public String querySuggestionReply(){
		logger.info("[UserAction- querySuggestionReply]body:"+body);
		try {
			int pagesize =1,maxsize=10;	
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId");
			String suggestionId= jsonObject.getString("suggestionId");
			String page = (String)JsonUtil.getOptionalValue(jsonObject,"page"); 
			String count = (String)JsonUtil.getOptionalValue(jsonObject,"count"); 
			if(page!=null&&!"".equals(page)){
				pagesize = Integer.parseInt(page);
			}
			if(count!=null&&!"".equals(count)){
				maxsize =Integer.parseInt(count);
			}
			User userReq =getUserInfo(userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				SuggestionReply reply = new SuggestionReply();
				reply.setSuggestionId(suggestionId);
				reply.setPageSize(maxsize);
				reply.setPageNumber(pagesize);
				reply = suggestionReplyActionSupport.getSuggestionReplyListByPager(reply);
				if(reply.getList()!=null){ 
					 reno="0000";
					 long pagenum = reply.getTotalCount()%maxsize == 0 ? reply.getTotalCount()/maxsize : reply.getTotalCount()/maxsize+1;
					 rebody.put("page", pagesize);
					 rebody.put("hasNext", pagenum>pagesize?true:false);
					 JSONArray array = new JSONArray();
					 for(int i=0;i<reply.getList().size();i++){
						 JSONObject json = new JSONObject();
						 SuggestionReply member = (SuggestionReply)reply.getList().get(i);
						 json.put("replyId", member.getId());
						 json.put("title", member.getTitle());
						 json.put("content", member.getContent());
						 json.put("createTime", member.getCreateTime());
						 array.add(json);
					 }
					rebody.put("suggestReplylist", array);
				}
			}else{
				logger.info("该用户不存在。");
				reno="0101"; 
				return SUCCESS;
			}
		 
	}catch(Exception e){
		reno="9999";
		logger.error("querySuggestionReply异常",e);
	}
	return SUCCESS;
	}
	
	
	/**
	 * 
	  * 方法描述：收藏列表查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-19 上午11:34:34
	 */
	public String favorite(){
		logger.info("[UserAction- favorite]body:"+body);
		try {
			int pagesize =1,maxsize=100;
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId"); 
			String page = (String)JsonUtil.getOptionalValue(jsonObject,"page"); 
			String count = (String)JsonUtil.getOptionalValue(jsonObject,"count"); 
			String collectID = (String)JsonUtil.getOptionalValue(jsonObject,"collectId");
			if(page!=null&&!"".equals(page)){
				pagesize = Integer.parseInt(page);
			}
			if(count!=null&&!"".equals(count)){
				maxsize =Integer.parseInt(count);
			}
			User userReq;
			userReq =getUserInfo(userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				 MemberCollect memberCollectPager=new MemberCollect();
				 memberCollectPager.setUserid(userReq.getUserID());
				 memberCollectPager.setState("30");
				 memberCollectPager.setPageSize(maxsize);
				 memberCollectPager.setPageNumber(pagesize);
				 if(!"".equals(collectID)){
					 memberCollectPager.setId(Integer.parseInt(collectID));
				 } 
				 memberCollectPager=memberCollectActionSupport.getMemberCollectByPager(memberCollectPager);
				 logger.info("收藏列表查询 size:"+memberCollectPager.getList().size());
				 if(memberCollectPager.getList()!=null){ 
					 reno="0000";
					 long pagenum = memberCollectPager.getTotalCount()%maxsize == 0 ? memberCollectPager.getTotalCount()/maxsize : memberCollectPager.getTotalCount()/maxsize+1;
					 rebody.put("page", pagesize);
					 rebody.put("hasNext", pagenum>pagesize?true:false);
					 JSONArray array = new JSONArray();
					 for(int i=0;i<memberCollectPager.getList().size();i++){
						 JSONObject json = new JSONObject();
						 MemberCollect member = (MemberCollect)memberCollectPager.getList().get(i);						 
						 if(member.getMerchantId()!=null&&member.getMerchant().getId()!=null){ 
							 json.put("collectId", member.getId());
							 json.put("merchantId", member.getMerchantId());
							 json.put("merchantName", member.getMerchant().getMstoreFullName());
							 json.put("contactName", member.getMerchant().getMstoreContactName());
							 json.put("contactTel1", member.getMerchant().getMstoreContactTel1());
							 json.put("contactTel2", member.getMerchant().getMstoreContactTel2());
							 json.put("companyAddress", member.getMerchant().getMstoreAddress());
							 json.put("companyLogo", FroadAPICommand.makePicURL(member.getMerchant().getMstoreLogoUrl(),FroadAPICommand.PIC_THUMBNAIL));
							 json.put("link", member.getMerchant().getDcHyperlink());
							 json.put("createTime", member.getCreateTime());
							 json.put("remark", member.getRemark());
							 array.add(json);
						 }
					 }
					rebody.put("collectlist", array);
				 }
			}else{
				logger.info("该用户不存在。");
				reno="0101"; 
				return SUCCESS;
			} 
		}catch(Exception e){
			reno="9999";
			logger.error("favoritet异常",e);
		}
		return SUCCESS;
	}
	/**
	 * 
	  * 方法描述：添加收藏
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-19 上午11:34:34
	 */
	public String favoriteadd(){
		logger.info("[UserAction- favoriteadd]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId");
			String merchantID  = jsonObject.getString("merchantId");
			String remark = (String)JsonUtil.getOptionalValue(jsonObject,"remark");  
			User userReq;
			userReq = getUserInfo(userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				 MemberCollect memberCollectPager=new MemberCollect();
				 memberCollectPager.setUserid(userReq.getUserID());
				 memberCollectPager.setMerchantId(merchantID);
				 memberCollectPager=memberCollectActionSupport.getMemberCollectByPager(memberCollectPager);
				 if(memberCollectPager.getList().size()>0){
					 log.info("此商户已经收藏");
					 reno="0202";
					 return SUCCESS; 
				 }
				 MemberCollect memberCollect=new MemberCollect();
				 memberCollect.setUserid(userReq.getUserID());
				 memberCollect.setMerchantId(merchantID);
				 memberCollect.setRemark(remark);
				 memberCollect.setState("30");
				 boolean isSuccess=memberCollectActionSupport.addMemberCollect(memberCollect);
				 if(isSuccess){
					   reno="0000";
					   remsg="收藏成功";
						//增加直通车收藏数
						MerchantTrain merchantTran=merchantTrainActionSupport.getMerchantTrainByMerchantId(merchantID,null);
						if(merchantTran!=null){
							if(merchantTran.getCollectes()==null|| "".equals(merchantTran.getCollectes()))
								merchantTran.setCollectes("0");
							Integer oldCollect=Integer.parseInt(merchantTran.getCollectes());
							MerchantTrain newMerchantTran=new MerchantTrain();
							Integer newCollect=oldCollect+1;
							newMerchantTran.setId(merchantTran.getId());
							newMerchantTran.setCollectes(newCollect+"");
							merchantTrainActionSupport.updMerchantTrain(newMerchantTran);
						} 
					}
			}else{
				logger.info("该用户不存在。");
				reno="0101"; 
				return SUCCESS;
			}  
		}catch(Exception e){
			reno="9999";
			logger.error("favoriteadd异常",e);
		}
		return SUCCESS; 
	}
	/**
	 * 
	  * 方法描述：删除收藏
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-2-19 上午11:34:34
	 */
	public String favoritedel(){
		logger.info("[UserAction- favoritedel]body:"+body);
		try {
			JSONObject jsonObject = JSONObject.fromObject(body);
			String userid  = jsonObject.getString("userId");
			String collectID  = jsonObject.getString("collectId");
			String merchantId="";
			if(Assert.empty(collectID)){
				reno="0009";
				return SUCCESS;
			}
			User userReq = getUserInfo(userid);
			if(Command.respCode_SUCCESS.equals(userReq.getRespCode())){
				 MemberCollect memberCollectPager=new MemberCollect();
				 memberCollectPager.setId(Integer.parseInt(collectID));
				 memberCollectPager.setUserid(userReq.getUserID());
				 memberCollectPager=memberCollectActionSupport.getMemberCollectByPager(memberCollectPager);
				 if(memberCollectPager==null||memberCollectPager.getList().size()==0){
					 logger.info("没有收藏该商户!");
					 reno="0201";
					 return SUCCESS;
				 }else{
					 merchantId = ((MemberCollect)memberCollectPager.getList().get(0)).getMerchantId();
				 }
				boolean isSuccess=memberCollectActionSupport.deleteMemberCollect(Integer.parseInt(collectID));
				if(isSuccess){
					reno="0000";
					//增加直通车收藏数
					MerchantTrain merchantTran=merchantTrainActionSupport.getMerchantTrainByMerchantId(merchantId,null);
					if(merchantTran!=null){
						if(merchantTran.getCollectes()==null|| "".equals(merchantTran.getCollectes()))
							merchantTran.setCollectes("0");
						Integer oldCollect=Integer.parseInt(merchantTran.getCollectes());
						MerchantTrain newMerchantTran=new MerchantTrain();
						Integer newCollect=oldCollect+1;
						newMerchantTran.setId(merchantTran.getId());
						newMerchantTran.setCollectes(newCollect+"");
						merchantTrainActionSupport.updMerchantTrain(newMerchantTran);
					}
				}
			}else{
				logger.info("该用户不存在。");
				reno="0101"; 
				return SUCCESS;
			} 
		}catch(Exception e){
			reno="9999";
			logger.error("favoritedel异常",e);
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
		return userReq;
	}
	
	public String getReno() {
		logger.info("调用结束[UserAction]reno:"+reno+",remsg:"+getRemsg()+",rebody:"+rebody);
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
	@JSON(serialize=false)
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}
	 
	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
	@JSON(serialize=false)
	public MemberCollectActionSupport getMemberCollectActionSupport() {
		return memberCollectActionSupport;
	}
	
	public void setMemberCollectActionSupport(MemberCollectActionSupport memberCollectActionSupport) {
		this.memberCollectActionSupport = memberCollectActionSupport;
	}
	@JSON(serialize=false)
	public PointActionSupport getPointActionSupport() {
		return pointActionSupport;
	}
	public void setPointActionSupport(PointActionSupport pointActionSupport) {
		this.pointActionSupport = pointActionSupport;
	}
	@JSON(serialize=false)
	public SuggestionActionSupport getSuggestionActionSupport() {
		return suggestionActionSupport;
	}
	public void setSuggestionActionSupport(
			SuggestionActionSupport suggestionActionSupport) {
		this.suggestionActionSupport = suggestionActionSupport;
	}
	@JSON(serialize=false)
	public SuggestionReplyActionSupport getSuggestionReplyActionSupport() {
		return suggestionReplyActionSupport;
	}
	public void setSuggestionReplyActionSupport(
			SuggestionReplyActionSupport suggestionReplyActionSupport) {
		this.suggestionReplyActionSupport = suggestionReplyActionSupport;
	}
	@JSON(serialize=false)
	public MerchantTrainActionSupport getMerchantTrainActionSupport() {
		return merchantTrainActionSupport;
	}

	public void setMerchantTrainActionSupport(
			MerchantTrainActionSupport merchantTrainActionSupport) {
		this.merchantTrainActionSupport = merchantTrainActionSupport;
	}
	public String getReturnTime() {
		
		return sdf.format(new Date());
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
	
}
