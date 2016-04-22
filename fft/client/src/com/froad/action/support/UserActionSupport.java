package com.froad.action.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.froad.action.support.merchantUserSet.MerchantUserSetActionSupport;
import com.froad.client.merchantUserSet.MerchantUserSet;
import com.froad.client.user.AppException_Exception;
import com.froad.client.user.Authorities;
import com.froad.client.user.Buyers;
import com.froad.client.user.LoginManager;
import com.froad.client.user.MUserService;
import com.froad.client.user.Resources;
import com.froad.client.user.SQLException_Exception;
import com.froad.client.user.User;
import com.froad.util.Assert;
import com.froad.util.Command;

public class UserActionSupport {
	private static Logger logger = Logger.getLogger(UserActionSupport.class);
	private MUserService userService;
	
	private MerchantUserSetActionSupport merchantUserSetActionSupport;
	/**
	 * 绑定买家
	 */
	public Buyers bindingBuyer(String phone,String createChannel){
		return userService.bindingBuyer(phone,createChannel);
	}

	/**
	  * 方法描述：修改用户登录的 ip  时间  并返回上一次的 ip  时间
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: Sep 27, 2011 4:29:15 PM
	  */
	public User updateUserLastInfoByUsername(User user) throws AppException_Exception{
		user = userService.updateUserLastInfoByUsername(user);
		return user;
	}
	
	/**
	  * 方法描述：获取所有权限
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 勾君伟 goujunwei@f-road.com.cn
	  * @time: 2011-4-4 上午12:05:36
	  */
	public List<Authorities> queryAuthorities() {
		List<Authorities> list= new ArrayList<Authorities>();
		try {
			list=userService.queryAuthorities();
		} catch (AppException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	  * 方法描述：根据用户权限获取资源信息
	  * @param: muserImpl.u
	  * @return: 
	  * @version: 1.0
	  * @author: 勾君伟 goujunwei@f-road.com.cn
	  * @time: 2011-4-4 上午12:05:46
	  */
	public List<Resources> queryResourcesByAuthorities(String auth) {
		List<Resources> list= new ArrayList<Resources>();
		try {
			list=userService.queryResourcesByAuthorities(auth);
		} catch (AppException_Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	  * 方法描述：获取用户权限
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 勾君伟 goujunwei@f-road.com.cn
	  * @time: 2011-4-4 上午12:05:36
	  */
	public List<Authorities> queryAuthoritiesByUsername(String username) {
		List<Authorities> list= new ArrayList<Authorities>();
		try {
			list=userService.queryAuthoritiesByUsername(username);
		} catch (AppException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	  * 方法描述：根据用户名获取用户信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: meicy meiwenxiang@f-road.com.cn
	  * @time: Aug 24, 2011 9:53:24 PM
	  */
	public  User getUserInfoByUsername(String username) throws AppException_Exception {
		User user=userService.queryUserAllByUsername(username);
		return user; 
	}
	
	/**
	  * 方法描述：根据用户名获取用户信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: meicy meiwenxiang@f-road.com.cn
	  * @time: Aug 24, 2011 9:53:24 PM
	  */
	public  User selectUserEmail(String email) throws AppException_Exception {
		User user=userService.selectUserEmail(email);
		return user; 
	}
	
	
	
	/**
	 * *******************************************************
	 * @函数名: checkMerchant  
	 * @功能描述: 检查商户登录
	 * @输入参数: @param isPwdRight 密码是否正确
	 * @返回类型: void
	 * @作者: 赵肖瑶 
	 * @日期: 2013-5-30 上午11:00:27
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public void checkMerchant(boolean isPwdRight,MerchantUserSet clerk,JSONObject json){
		MerchantUserSet mUserSet = new MerchantUserSet();
		mUserSet.setId(clerk.getId());
		mUserSet.setBeCode(clerk.getBeCode());
		mUserSet.setUserId(clerk.getUserId());
		json.put("reno", Command.respCode_FAIL);
		if(isPwdRight){
			//这里证明帐号和密码通过了验证
			if("0".equals(clerk.getIsAccountLocked())){								
				//判断你是否过了锁定时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
				Date now = new Date();
				try {
					Date lockTime = sdf.parse(clerk.getLockedDate());
					if(now.after(lockTime)){
						//过了锁定时间
						
						//将状态改为正常状态
						mUserSet.setIsAccountLocked("1");
						
						//将连续错误次数归0
						mUserSet.setLoginFailureCount(0);
						mUserSet.setLastTryTime(sdf.format(new Date()));
						mUserSet.setLockedDate("");
						//更新
						merchantUserSetActionSupport.updateMerchantClerk(mUserSet);
						logger.info("商户 id"+clerk.getId() + " 工号" +clerk.getBeCode() +"系统已将其解锁");

						json.put("reno", Command.respCode_SUCCESS);	
						
					}else{
						//没有过锁定时间
						mUserSet.setLastTryTime(sdf.format(new Date()));
						
						//更新
						
						merchantUserSetActionSupport.updateMerchantClerk(mUserSet);
						json.put("msg", "该帐号已被锁定，请两小时之后再尝试" );
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				
				//账户不处于锁定状态登录成功
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
				//将状态改为正常状态
				mUserSet.setIsAccountLocked("1");
				
				//将连续错误次数归0
				mUserSet.setLoginFailureCount(0);
				mUserSet.setLastTryTime(sdf.format(new Date()));
				mUserSet.setLockedDate("");
				mUserSet.setLastTryTime(sdf.format(new Date()));
				//更新
				merchantUserSetActionSupport.updateMerchantClerk(mUserSet);
				json.put("reno", Command.respCode_SUCCESS);	
			}
		}else{
			//密码错误
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
			if("1".equals(clerk.getIsAccountLocked())){
				//该帐号还没有被锁定
				//密码错误						
				int apartTime = 0;//当前时间和现在尝试的时间差(分钟)								
				//判断你连续登录的时间
				if(!Assert.empty(clerk.getLastTryTime())){
					
					Date now = new Date();
					if(!Assert.empty(clerk.getLastTryTime())){
						try {
							Date lockTime = sdf.parse(clerk.getLastTryTime());
							apartTime = (int) ((now.getTime() - lockTime.getTime())/1000/60);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
				}
				
				if(apartTime <= 15 && !Assert.empty(clerk.getLastTryTime())){
					//在15分钟之内
					if(clerk.getLoginFailureCount() >= Command.LOING_FAILURE_MAX_TIMES){
						//如果连续错误次数达到规定次数 锁定用户，设置解锁时间
						Date date = new Date();
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						c.add(Calendar.HOUR, 2);
						mUserSet.setLockedDate(sdf.format(c.getTime()));//添加解锁时间
						mUserSet.setIsAccountLocked("0");
						mUserSet.setLastTryTime(sdf.format(new Date()));
						
						//更新
						merchantUserSetActionSupport.updateMerchantClerk(mUserSet);
						//log.info("商户 id"+clerk.getId() + " 工号" +clerk.getBeCode() +"已被系统锁定");

						json.put("msg", "该帐号已被锁定，请两小时之后再尝试");
						
					}else {
						//错误，但是不被锁定
						mUserSet.setLoginFailureCount(clerk.getLoginFailureCount() + 1);
						mUserSet.setLastTryTime(sdf.format(new Date()));
						
						//更新
						merchantUserSetActionSupport.updateMerchantClerk(mUserSet);
						if(mUserSet.getLoginFailureCount() != Command.LOING_FAILURE_MAX_TIMES){
							json.put("msg", "密码已错误" + mUserSet.getLoginFailureCount() +"次,您还有"+(Command.LOING_FAILURE_MAX_TIMES - mUserSet.getLoginFailureCount())+"次尝试机会");
						}else{											
							Date date = new Date();
							Calendar c = Calendar.getInstance();
							c.setTime(date);
							c.add(Calendar.HOUR, 2);
							mUserSet.setLockedDate(sdf.format(c.getTime()));//添加解锁时间
							mUserSet.setIsAccountLocked("0");
							mUserSet.setLastTryTime(sdf.format(new Date()));							
							//更新
							merchantUserSetActionSupport.updateMerchantClerk(mUserSet);
							json.put("msg", "该帐号已被锁定，请两小时之后再尝试");
						}
						
					}
				}else{
					//超过15分钟连续
					mUserSet.setLoginFailureCount(1);
					mUserSet.setLastTryTime(sdf.format(new Date()));
					//更新									
					merchantUserSetActionSupport.updateMerchantClerk(mUserSet);
					json.put("msg", "密码已错误1次,您还有"+(Command.LOING_FAILURE_MAX_TIMES - 1)+"次尝试机会");
				}
			}else{
				//被锁定也要判断是否过了解锁时间
				Date now = new Date();
				Date lockTime;
				try {
					lockTime = sdf.parse(clerk.getLockedDate());
					if(now.after(lockTime)){
						//过了被锁时间
						//将状态改为正常状态
						mUserSet.setIsAccountLocked("1");
						
						//记录这次错误次数
						mUserSet.setLoginFailureCount(1);
						mUserSet.setLastTryTime(sdf.format(new Date()));
						mUserSet.setLockedDate(sdf.format(new Date()));
						//更新
						merchantUserSetActionSupport.updateMerchantClerk(mUserSet);
						json.put("msg", "密码已错误1次,您还有"+(Command.LOING_FAILURE_MAX_TIMES - 1)+"次尝试机会");
					}else{
						mUserSet.setLastTryTime(sdf.format(new Date()));
						merchantUserSetActionSupport.updateMerchantClerk(mUserSet);
						json.put("msg", "该帐号已被锁定，请两小时之后再尝试");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}				
				
			}
			
		}
	}
	
	/**
	 * *******************************************************
	 * @函数名: checkUser  
	 * @功能描述: 检查用户登录
	 * @输入参数: @param isPwdRight
	 * @输入参数: @param user
	 * @输入参数: @param json <说明>
	 * @返回类型: void
	 * @作者: 赵肖瑶 
	 * @日期: 2013-5-30 上午11:14:30
	 * @修改: Zxy 
	 * @日期: 
	 **********************************************************
	 */
	public void checkUser(boolean isPwdRight,User user,JSONObject json){
		User u = new User();
		u.setMemberCode(user.getMemberCode()); //以membercode作为更新条件
		//u.setUsername(user.getUsername());
		json.put("reno", Command.respCode_FAIL);
		if(isPwdRight){
			//登录成功
			//这里证明帐号和密码通过了验证
			if("0".equals(user.getIsAccountLocked())){								
				//判断你是否过了锁定时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
				Date now = new Date();
				try {
					Date lockTime = sdf.parse(user.getLockedDate());
					if(now.after(lockTime)){
						//过了锁定时间
						
						//将状态改为正常状态
						u.setIsAccountLocked("1");
						
						//将连续错误次数归0
						u.setLoginFailureCount(0);
						u.setLastTryTime(sdf.format(new Date()));
						u.setLockedDate("");
						//更新
						try {
							this.updateUserLastInfoByUsername(u);
						//	log.info("userId"+user.getUserID()+"已经过了锁定时间，系统将其解锁");
						} catch (AppException_Exception e) {
						//	log.info("在更新用户名为"+u.getUsername()+"出现异常",e);
						}
						json.put("reno", Command.respCode_SUCCESS);	
						
					}else{
						//没有过锁定时间
						u.setLastTryTime(sdf.format(new Date()));
						
						//更新
						try {
							this.updateUserLastInfoByUsername(u);
						} catch (AppException_Exception e) {
						//	log.info("在更新用户名为"+u.getUsername()+"出现异常",e);
						}
						json.put("msg", "该帐号已被锁定，请两小时之后再尝试" );
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				
				//账户不处于锁定状态登录成功
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
				//将状态改为正常状态
				u.setIsAccountLocked("1");
				
				//将连续错误次数归0
				u.setLoginFailureCount(0);
				u.setLastTryTime(sdf.format(new Date()));
				u.setLockedDate("");
				u.setLastTryTime(sdf.format(new Date()));
				try {
					this.updateUserLastInfoByUsername(u);
				} catch (AppException_Exception e) {
				//	log.info("在更新用户名为"+u.getUsername()+"出现异常",e);
				}
				json.put("reno", Command.respCode_SUCCESS);	
			}
		}else{
			//登录失败
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd|HH:mm:ss");
			if("1".equals(user.getIsAccountLocked())){
				//该帐号还没有被锁定
				//密码错误						
				int apartTime = 0;//当前时间和现在尝试的时间差(分钟)								
				//判断你连续登录的时间
				if(!Assert.empty(user.getLastTryTime())){
					
					Date now = new Date();
					if(!Assert.empty(user.getLastTryTime())){
						try {
							Date lockTime = sdf.parse(user.getLastTryTime());
							apartTime = (int) ((now.getTime() - lockTime.getTime())/1000/60);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
				}
				
				if(apartTime <= 15 && !Assert.empty(user.getLastTryTime())){
					//在15分钟之内
					if(user.getLoginFailureCount() >= Command.LOING_FAILURE_MAX_TIMES){
						//如果连续错误次数达到规定次数 锁定用户，设置解锁时间
						Date date = new Date();
						Calendar c = Calendar.getInstance();
						c.setTime(date);
						c.add(Calendar.HOUR, 2);
						u.setLockedDate(sdf.format(c.getTime()));//添加解锁时间
						u.setIsAccountLocked("0");
						u.setLastTryTime(sdf.format(new Date()));
						
						//更新
						try {
							this.updateUserLastInfoByUsername(u);
						//	log.info("用户userId"+user.getUserID()+"由于连续错误达上限，系统将其锁定");

						} catch (AppException_Exception e) {
						//	log.info("在更新用户名为"+u.getUsername()+"出现异常",e);
						}
						json.put("msg", "该帐号已被锁定，请两小时之后再尝试");
						
					}else {
						//错误，但是不被锁定
						u.setLoginFailureCount(user.getLoginFailureCount() + 1);
						u.setLastTryTime(sdf.format(new Date()));
						
						//更新
						try {
							this.updateUserLastInfoByUsername(u);
						} catch (AppException_Exception e) {
						//	log.info("在更新用户名为"+u.getUsername()+"出现异常",e);
						}
						if(u.getLoginFailureCount() != Command.LOING_FAILURE_MAX_TIMES){
							json.put("msg", "密码已错误" + u.getLoginFailureCount() +"次,您还有"+(Command.LOING_FAILURE_MAX_TIMES - u.getLoginFailureCount())+"次尝试机会");
						}else{											
							Date date = new Date();
							Calendar c = Calendar.getInstance();
							c.setTime(date);
							c.add(Calendar.HOUR, 2);
							u.setLockedDate(sdf.format(c.getTime()));//添加解锁时间
							u.setIsAccountLocked("0");
							u.setLastTryTime(sdf.format(new Date()));
							
							//更新
							try {
								this.updateUserLastInfoByUsername(u);
							} catch (AppException_Exception e) {
								logger.info("在更新用户名为"+u.getUsername()+"出现异常",e);
							}
							json.put("msg", "该帐号已被锁定，请两小时之后再尝试");
						}
						
					}
				}else{
					//超过15分钟连续
					u.setLoginFailureCount(1);
					u.setLastTryTime(sdf.format(new Date()));
					//更新									
					try {
						this.updateUserLastInfoByUsername(u);
					} catch (AppException_Exception e) {
						logger.info("在更新用户名为"+u.getUsername()+"出现异常",e);
					}
					json.put("msg", "已错误1次,您还有"+(Command.LOING_FAILURE_MAX_TIMES - 1)+"次尝试机会");
					
				}
			}else{
				//被锁定也要判断是否过了解锁时间
				Date now = new Date();
				Date lockTime;
				try {
					lockTime = sdf.parse(Assert.empty(user.getLockedDate())?"":user.getLockedDate());
					if(now.after(lockTime)){
						//过了被锁时间
						//将状态改为正常状态
						u.setIsAccountLocked("1");
						
						//记录这次错误次数
						u.setLoginFailureCount(1);
						u.setLastTryTime(sdf.format(new Date()));
						u.setLockedDate(sdf.format(new Date()));
						//更新
						try {
							this.updateUserLastInfoByUsername(u);
						} catch (AppException_Exception e) {
							logger.info("在更新用户名为"+u.getUsername()+"出现异常",e);
						}
						json.put("msg", "密码已错误1次,您还有"+(Command.LOING_FAILURE_MAX_TIMES - 1)+"次尝试机会");
					}else{
						u.setLastTryTime(sdf.format(new Date()));
						try {
							this.updateUserLastInfoByUsername(u);
						} catch (AppException_Exception e) {
							logger.info("在更新用户名为"+u.getUsername()+"出现异常",e);
						}
						json.put("msg", "该帐号已被锁定，请两小时之后再尝试");
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}	
				
				
			}
		}
	}
	
	
	/**
	  * 方法描述：查询用户所有信息 根据手机号码 或邮箱 或 sn
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-3-13 下午2:02:59
	  */
	public  User queryUserByMobilephoneOrMailOrSn(String v) throws AppException_Exception {
		User user =userService.queryUserByMobilephoneOrMailOrSn(v);
		return user;
	}
	public  User queryUserByMobilephoneOrMail(String v) throws AppException_Exception {
		User user =userService.queryUserByMobilephoneOrMail(v);
		return user;
	}
	
	public  User queryUserByMobilephoneOrUsername(String v) throws AppException_Exception {
		User user =userService.queryUserByMobilephoneOrUsername(v);
		return user;
	}
	
	/**
	  * 方法描述：根据用户ID修改数据
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2011-5-9 下午06:14:57
	  */
	public User updateUser(User user) throws AppException_Exception {
		user = userService.updUserInfo(user);
		return user;
	}
	
	/**
	 * 修改密码
	 * @param user
	 * @return
	 * @throws AppException_Exception
	 */
	public  User updPassword(Long memberCode,String oldPwd,String newPwd) throws AppException_Exception {
		User user=userService.updPassword(memberCode,oldPwd,newPwd);
		return user;
	}
	
	/**
	 * <p>
	 * description:用户注册
	 * 
	 * @param user
	 * @return
	 *            <p>
	 *            author 勾君伟 goujunwei@f-road.com.cn
	 * @time:2011-3-14 上午10:53:27
	 * @version 1.0
	 * @throws AppException_Exception 
	 */
	public  User register(User user) throws AppException_Exception {
		User userReq = new User();
		userReq=userService.register(user);
		return userReq;
	}
	
	/**
	 *描述：查询用户信息userId
	 * @param userId
	 * @return
	 * @throws AppException_Exception
	 */
	public  User queryUserAllByUserID(String userId) {
//		throw new AppException_Exception("故意抛出异常");
		User user = null;
		try {
			user=userService.queryUserAllByUserID(userId);
		} catch (AppException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * 方法描述:用户根据用户名和注册邮箱找回密码
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @throws AppException_Exception 
	 * @update:
	 * @time: 2011-8-9 上午11:58:56
	 */
	public LoginManager initPassword(LoginManager loginManager) throws AppException_Exception{
		LoginManager lm = new LoginManager();
		lm = userService.initPassword(loginManager);
		return lm;
	}
	
	/**
	 * 手机客户端注册用户
	 * @param userRes    mobilephone:13888888888  password:123456
	 * @return
	 */
	public User clientRegister(User userRes){
		  
		try {
			userRes = userService.clientRegister(userRes,true);
		} catch (Exception e) {
			logger.error("this.clientRegister自动出错.手机号码："+userRes.getMobilephone(), e);
		}
		return userRes;
	}
	
	/**
	 * 手机客户端注册用户
	 * @param userRes    mobilephone:13888888888  password:123456
	 * @return
	 */
	public User selectUserMobilephone(String mobliePhone){
		User user=null;
		try {
			user=userService.selectUserMobilephone(mobliePhone);
		} catch (AppException_Exception e) {
			logger.error("查询用户出现异常",e);
			user=new User();
			user.setRespCode("1");
		}
		return user;
		
	}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>方法说明</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-27 上午09:09:56 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public User loginPassAccountSys(String loginID,String loginPwd,String loginIP){
		return userService.loginPassAccountSys(loginID, loginPwd, loginIP);
	}
	
	
	
	public MUserService getUserService() {
		return userService;
	}

	public void setUserService(MUserService userService) {
		this.userService = userService;
	}
	
	public User isPhoneExist(String phoneNumber){
		try {
			return userService.selectUserMobilephone(phoneNumber);
		} catch (AppException_Exception e) {
			logger.equals("查询手机号码"+phoneNumber+"失败");
			return null;
		}
	}

	public boolean updateChangeMobilePhone(String mobilePhone,String userId){
		try {
			return userService.updateChangeMobilePhone(mobilePhone,userId);
		} catch (AppException_Exception e) {
			logger.info("UserActionSupport.updateChangeMobilePhone出错",e);
		}
		return false;
	}
	
	public MerchantUserSetActionSupport getMerchantUserSetActionSupport() {
		return merchantUserSetActionSupport;
	}

	public void setMerchantUserSetActionSupport(
			MerchantUserSetActionSupport merchantUserSetActionSupport) {
		this.merchantUserSetActionSupport = merchantUserSetActionSupport;
	}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>更新用户追加字段信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-29 下午12:50:09 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public boolean updateUserAppendData(User user){
		return userService.updateUserAppendInfo(user);
	}
	public User forgetPwdToGetIt(User user){
		return userService.forgetPwdToGetIt(user.getMemberCode());
	}
	
	public boolean inserUserAppendInfo(User u) {
		return userService.insertUserAppendInfo(u);		
	}
	
}
