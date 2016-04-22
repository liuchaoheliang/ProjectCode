package com.froad.CB.dao.user.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.AppException.AppException;
import com.froad.CB.common.Command;
import com.froad.CB.common.SysCommand;
import com.froad.CB.common.UserCommand;
import com.froad.CB.dao.user.UserDao;
import com.froad.CB.po.user.Authorities;
import com.froad.CB.po.user.LoginManager;
import com.froad.CB.po.user.Resources;
import com.froad.CB.po.user.User;
import com.froad.CB.po.user.UserAppend;
import com.froad.util.Assert;
import com.froad.util.CoderSectury;
import com.froad.util.DateUtil;
import com.froad.util.MD5;
import com.froad.util.String2Xml;
import com.froad.util.Util;
import com.froad.util.mail.MailUtil;
import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.CreateChannel;
import com.pay.user.helper.MemberType;
import com.pay.user.helper.UserStatus;

public class UserDaoImpl implements UserDao {
	
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	private static Logger logger = Logger.getLogger(UserDaoImpl.class);	

	private UserSpecFuncImpl usfi;
	private UserAppendDaoImpl uadi; 
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>记录响应结果</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-26 上午10:04:43 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private void showUserSpecFuncResult(String methodName,UserResult ur){
		//logger.info("调用账户账务服务平台 方法:" + methodName + " | 响应结果为Result:" + ur.getResult() +" | MsgCode:" + ur.getMsgCode() +" | ErrorMsg:" + ur.getErrorMsg());
		
	}
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>用户获取完整user其在分分通平台的追加字段</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-26 上午11:21:16 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private User getUserAppendData(User user){
		if(user == null || Assert.empty(user.getUserID())){
			logger.error("试图获取User追加信息时传入查询条件User或UserID为空");
			return user;
		}
		user.userAppendData(uadi.getUserAppendByUserId(user.getUserID()));
		return user;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>统一检查处理返回数据</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-10-8 上午10:02:38 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private User checkUserRes(UserResult ur){
		User userRes = new User();
		userRes.setRespCode(Command.respCode_FAIL);
		userRes.setRespMsg("获取用户信息失败（调用账户账务服务平台）");		
		if(ur == null){
			return userRes;
		}
		if(!ur.getResult()){
			return userRes;
		}else{
			List<UserSpecDto> rs = ur.getUserList();
			if(Assert.empty(rs)){
				userRes.setRespMsg("获取用户信息失败（没有数据）");
				return userRes;
			}else{
				userRes = new User(rs.get(0));
				userRes = getUserAppendData(userRes);//获取user追加字段
				userRes.setRespCode(Command.respCode_SUCCESS);
				userRes.setRespMsg(UserCommand.getResMsg(ur.getMsgCode()));
				return userRes;
			}
		}
		
	}
	
	
	
	
	
	/**
	 * 方法描述：用户注册
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 勾君伟 goujunwei@f-road.com.cn
	 * @version: 2011-3-11 上午11:16:59
	 * @throws SQLException
	 * @throws SQLException
	 * @throws Exception
	 * @throws Exception
	 */
	
	/**
	 * 
	 * 新用户注册
	 */
	public User register(User user)  {
		//TODO:register已实现新逻辑
		User userRes = new User();
		userRes.setRespCode("1");//默认注册失败
		if(Assert.empty(user.getEmail())){
			user.setEmail(null);
		}
	
		user.setCreateChannel(Assert.empty(user.getCreateChannel()) ? CreateChannel.FFT.getValue() : user.getCreateChannel());
		user.setMemberType(MemberType.PERSONAL.getValue());
		
		// 默认激活
		user.setStatus(UserStatus.AVAILABLE.getValue() + "");
	
		UserResult ur = new UserResult();
	
		try {
			ur = usfi.registerMember(user.toUserSpecDto());
			showUserSpecFuncResult("registerMember",ur);
		} catch (Exception e) {
			ur.setResult(false);
			logger.error("调用账户账务服务平台方法registerMember出现异常 传入参数 User:" + JSONObject.fromObject(user), e);
		}		
		
		if(ur.getResult()){
			
			User currRegisterUser = new User(ur.getUserList().get(0));
			
			if(currRegisterUser.getUserID() ==null || currRegisterUser.getUserID().length()==0 ){
				logger.error("查询当前注册用户失败！=========================================>>>>>>>>>>>>>>>>>>后续注册过程无法进行");
				userRes.setRespMsg("抱歉，系统繁忙求稍后再试");			
				userRes.setRespCode(Command.respCode_FAIL);
				return userRes;
			}
			//向追user加表中添加数据
			UserAppend userAppend = new UserAppend();
			
			userAppend.setUserID(currRegisterUser.getMemberCode().toString());
			userAppend.setIsPointActivateLock("1");
			userAppend.setPointActivateFailureTimes(0);
			userAppend.setFirstLogin("0");
			userAppend.setState("30");	
			userAppend.setActivateCode(Util.createCode(UserDaoImpl.getActivatecode(user)));			
			
			Integer row = uadi.addUserAppend(userAppend);
			
			if(row != null){
				
				
				
				// 添加用户角色		
				String ID = null;
				Integer maxID = uadi.queryMaxRoleID();
				
				if (maxID == null) {
					ID = "1";
				} else {
					ID = String.valueOf(maxID + 1);
				}
				user.setUser_authorities_id(ID);
				user.setUserID(currRegisterUser.getMemberCode().toString());
				//添加角色
				uadi.addrole(user);
				
				userRes = new User(ur.getUserList().get(0));
				userRes = getUserAppendData(userRes);
				userRes.setUserrole(user.getUserrole());
				userRes.setUser_authorities_id(user.getUser_authorities_id());
				//注册成功
				userRes.setRespCode(Command.respCode_SUCCESS);
				userRes.setRespMsg("注册成功！");
				ur = usfi.bindMobile(currRegisterUser.getMemberCode(), currRegisterUser.getMobilephone());
				showUserSpecFuncResult("bindMobile",ur);
				logger.info("用户注册成功，调用绑定接口");
			}else{
				logger.error("---------------->注册失败 向userAppend 表添加追加用户信息失败 ***需要回滚账户账务服务新添加数据 UserId:" + user.getUserID());
				userRes.setRespMsg("抱歉，系统繁忙求稍后再试");			
				userRes.setRespCode(Command.respCode_FAIL);
			}
			
		}else{
			userRes.setRespMsg(UserCommand.getResMsg(ur.getMsgCode()));			
			userRes.setRespCode(Command.respCode_FAIL);
		}
		
		
//		以前逻辑 
//		
//		ArrayList<User> list = (ArrayList<User>)sqlMapClientTemplate.queryForList("user.selectAddedUser",user);
//		if (list.size() == 0) {//注册用户		
//			sqlMapClientTemplate.insert("user.add", user);
//			// 添加用户角色		
//			String ID = null;
//			Integer maxID = (Integer) sqlMapClientTemplate.queryForObject("user.queryMaxRoleID");
//			if (maxID == null) {
//				ID = "1";
//			} else {
//				ID = String.valueOf(maxID + 1);
//			}
//			user.setUser_authorities_id(ID);
//
//			sqlMapClientTemplate.insert("user.addrole", user);
//			userRes.setRespCode("0");
//			userRes.setRespMsg("注册成功！");
//			userRes.setUserID(user.getUserID());
//			userRes.setUsername(user.getUsername());
//			userRes.setChannelNo(user.getChannelNo());
//			userRes.setStatus(user.getStatus());
//			userRes.setUserrole(user.getUserrole());
//			userRes.setUser_authorities_id(user.getUser_authorities_id());
//			userRes.setEmail(user.getEmail());			
//			userRes.setActivatecode(user.getActivatecode());
//		} else {
//			User userr = queryUserAllByUsername(user.getUsername());
//			if(userr.getRespCode().equals(Command.respCode_SUCCESS)){
//				userRes.setUserID(userr.getUserID());
//				userRes.setUsername(userr.getUsername());
//				userRes.setRespMsg("该用户名已注册。");
//			} else {
//				if(user.getEmail()!=null && !"".equals(user.getEmail().trim())){//用户没有填写邮箱信息，如果邮箱可以不填，那跳过					
//					userr = selectUserEmail(user.getEmail());
//				}else{
//					userr.setRespCode(Command.respCode_FAIL);
//				}
//				if(userr.getRespCode().equals(Command.respCode_SUCCESS)){
//					userRes.setUserID(userr.getUserID());
//					userRes.setUsername(userr.getUsername());
//					userRes.setRespMsg("该邮箱注册。");
//				} else {
//					userr = selectUserMobilephone(user.getMobilephone());
//					if(userr.getRespCode().equals(Command.respCode_SUCCESS)){
//						userRes.setUserID(userr.getUserID());
//						userRes.setUsername(userr.getUsername());
//						userRes.setRespMsg("该手机号码已注册。");
//					} else {
//						userRes.setRespMsg("该用户名已注册。");
//					}
//				}				
//			}
//			userRes.setRespCode(Command.respCode_FAIL);
//		}
		
		userRes.setMsgCode(ur.getMsgCode());
		return userRes;
	}

	/**
	 * <p>
	 * description:获得激活码 MD5散列码
	 * 
	 * @param user
	 * @return
	 *            <p>
	 *            author 勾君伟 goujunwei@f-road.com.cn
	 * @time:2011-3-15 下午01:47:43
	 * @version 1.0
	 * @throws Exception
	 */
	public static String getActivatecode(User user)  {
		String md5 = user.getUserID() + user.getUsername();
		String str="";
		try {
			str = new BigInteger(CoderSectury.encryptMD5(md5.getBytes())).toString(16);
		} catch (AppException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 方法描述：用户激活
	 * 
	 * @param: activatecode
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-6-1 下午04:59:37
	 */
	public User addedUser(String username)  {
		User userRes = new User();
		sqlMapClientTemplate.update("user.updateUserActivatecode", username);
		userRes.setRespCode("0");
		userRes.setRespMsg("激活成功！");
		return userRes;
	}


	/**
	 * 方法描述：查询用户所有信息 根据用户名
	 * 
	 * @param: username
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-6-1 下午04:59:37
	 */
	
	/**
	 * 通过帐号查询用户
	 */
	public User queryUserAllByUsername(String username){
		//TODO:queryUserAllByUsername已实现新逻辑
		UserResult ur = new UserResult();
		try {
			ur = usfi.queryByLoginID(username);
			showUserSpecFuncResult("queryMemberByLoginID", ur);
		} catch (Exception e) {
			logger.error("调用账户账务服务平台方法queryMemberByLoginID出现异常 传入参数 username:" + username, e);
		}
		//以前逻辑
//		userRes = (User)sqlMapClientTemplate.queryForObject("user.queryUserAllByUsername",username);
//		if(userRes == null){
//			userRes = new User();
//			userRes.setRespCode(Command.respCode_FAIL);
//			userRes.setRespMsg("查询用户信息失败");
//		} else {
//			userRes.setRespCode(Command.respCode_SUCCESS);
//			userRes.setRespMsg("查询用户信息成功");
//		}
//		
		return checkUserRes(ur);
	}
	
	/**
	  * 方法描述：根据商户名和商户密码查询用户信息
	  * @param: username
	  * @param：SPpassword
	  * @return: user
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jul 16, 2012 5:29:55 PM
	  */
	public User queryUserByUsernameAndSPpassword(String username,String SPpassword){
		User userRes = null;
		HashMap<String,String> map=new HashMap<String, String>();
		map.put("username", username);
		map.put("SPpassword",SPpassword);
		userRes = (User) sqlMapClientTemplate.queryForObject("user.queryUserByUsernameAndSPpassword",map);
		if(userRes == null){
			userRes = new User();
			userRes.setRespCode(Command.respCode_FAIL);
			userRes.setRespMsg("查询用户信息失败");
		} else {
			userRes.setRespCode(Command.respCode_SUCCESS);
			userRes.setRespMsg("查询用户信息成功");
		}
		
		return userRes;
	}
	
	/**
	  * 方法描述：查询用户所有信息 根据手机号码 或邮箱 或 sn
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-3-13 下午1:26:01
	  */
	
	/**
	 * 通过手机号码或者邮箱查询用户
	 */
	public User queryUserByMobilephoneOrMailOrSn(String value)  {
		//TODO:已实现新逻辑
		UserResult ur = new UserResult();
		
		try {
			ur = usfi.queryByLoginID(value);
			showUserSpecFuncResult("queryMemberByLoginID", ur);
		} catch (Exception e) {
			ur.setResult(false);
			logger.error("调用账户账务服务平台方法queryMemberByLoginID出现异常 传入参数 value:" + value, e);
		}
//		User userRes = null;
//		List<User> userlist = new ArrayList<User>();
//		userlist = (ArrayList<User>) sqlMapClientTemplate.queryForList("user.queryUserByMobilephoneOrMailOrSn", value);
//		if(userlist.size() == 0){
//			userRes = new User();
//			userRes.setRespCode(Command.respCode_FAIL);
//			userRes.setRespMsg("查询用户信息失败");
//		} else {
//			userRes = userlist.get(0);
//			userRes.setRespCode(Command.respCode_SUCCESS);
//			userRes.setRespMsg("查询用户信息成功");
//		}		
		return checkUserRes(ur);
	}

	public User queryUserByMobilephoneOrMail(String value)  {
		//TODO:已实现新逻辑
		UserResult ur = new UserResult();
		
		try {
			ur = usfi.queryByLoginID(value);
			showUserSpecFuncResult("queryMemberByLoginID", ur);
		} catch (Exception e) {
			ur.setResult(false);
			logger.error("调用账户账务服务平台方法queryMemberByLoginID出现异常 传入参数 value:" + value, e);
		}
//		以前逻辑
//		User userRes = null;
//		List<User> userlist = new ArrayList<User>();
//		userlist = (ArrayList<User>) sqlMapClientTemplate.queryForList("user.queryUserByMobilephoneOrMail", value);
//		if(userlist.size() == 0){
//			userRes = new User();
//			userRes.setRespCode(Command.respCode_FAIL);
//			userRes.setRespMsg("查询用户信息失败");
//		} else {
//			userRes = userlist.get(0);
//			userRes.setRespCode(Command.respCode_SUCCESS);
//			userRes.setRespMsg("查询用户信息成功");
//		}
		
		return checkUserRes(ur);
	}
	
	
	/**
	 * 通过手机号码或帐号查询用户信息
	 */
	public User queryUserByMobilephoneOrUsername(String value)  {
		//TODO:queryUserByMobilephoneOrUsername已实现新逻辑
		UserResult ur  = new UserResult();
		try {
			ur = usfi.queryByLoginID(value);
			showUserSpecFuncResult("queryMemberByLoginID", ur);
		} catch (Exception e) {
			ur.setResult(false);
			logger.error("调用账户账务服务平台方法queryMemberByLoginID出现异常 传入参数 value:" + value, e);
		}		
		
//以前逻辑
//		List<User> userlist = new ArrayList<User>();
//		userlist = (ArrayList<User>) sqlMapClientTemplate.queryForList("user.queryUserByMobilephoneOrUsername", value);
//		if(userlist.size() == 0){
//			userRes = new User();
//			userRes.setRespCode(Command.respCode_FAIL);
//			userRes.setRespMsg("查询用户信息失败");
//		} else {
//			userRes = userlist.get(0);
//			userRes.setRespCode(Command.respCode_SUCCESS);
//			userRes.setRespMsg("查询用户信息成功");
//		}
//		
		return checkUserRes(ur);
	}
	
	/**
	 * 方法描述：查询用户所有信息 根据用户id
	 * 
	 * @param: userID
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-6-1 下午04:59:37
	 */
	/**
	 * 通过memberCode查询用户信息
	 */
	public User queryUserAllByUserID(String userID)  {
		//TODO:已实现新逻辑
		UserResult ur = new UserResult();
		if(userID == null || userID.length() == 0){
			User userRes = new User();
			userRes.setRespCode(Command.respCode_FAIL);
			userRes.setRespMsg("查询用户信息失败");
			return userRes;
		}
		try {
			long memberCode = Long.parseLong(userID);
			ur = usfi.queryByMemberCode(memberCode);
			showUserSpecFuncResult("queryMemberByMemberCode", ur);
		} catch (Exception e) {
			logger.info("调用账户账务服务平台方法queryMemberByMemberCode出现异常 没有传入UserId");
			ur.setResult(false);
		}
		
//		User userRes = null;
//		userRes = (User) sqlMapClientTemplate.queryForObject("user.queryUserAllByUserID", userID);
//		if(userRes == null){
//			userRes = new User();
//			userRes.setRespCode(Command.respCode_FAIL);
//			userRes.setRespMsg("查询用户信息失败");
//		} else {
//			userRes.setRespCode(Command.respCode_SUCCESS);
//			userRes.setRespMsg("查询用户信息成功");
//		}
//		
		return checkUserRes(ur);
	}
	
	
	/**
	  * 方法描述：查询用户所有信息 根据用户mobilephone
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-3-13 下午2:48:43
	  */
	
	/**
	 * 通过手机号码查询用户
	 */
	public User selectUserMobilephone(String mobilephone)  {
		
		UserResult ur = new UserResult();
		try {
			ur = usfi.queryByLoginID(mobilephone);
			showUserSpecFuncResult("queryMemberByLoginID", ur);
		} catch (Exception e) {
			logger.error("调用账户账务服务平台接口queryMemberByLoginID出现异常 传入参数为mobilephone" + mobilephone, e);
			ur.setResult(false);
		}		
		
//		List userList = new ArrayList<User>();
//		User userRes = null;
//		userList =  sqlMapClientTemplate.queryForList("user.selectUserMobilephone", mobilephone);
//		if(userList != null && userList.size()>0){
//			userRes = (User)userList.get(0);
//			userRes.setRespCode(Command.respCode_SUCCESS);
//			userRes.setRespMsg("查询用户信息成功");			
//		} else {
//			userRes = new User();
//			userRes.setRespCode(Command.respCode_FAIL);
//			userRes.setRespMsg("查询用户信息失败");
//		}
//		
		return checkUserRes(ur);
	}
	
	/**
	 * 
	  * 方法描述：通过传递参数查询用户
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	 * @return 
	  * @time: 2012-8-23 下午03:11:44
	 */
	public  List<User> queryUserByIdentityKey(String identityKey)  {
		List <User> userList = null;
		userList =   sqlMapClientTemplate.queryForList("user.queryUserByIdentityKey", identityKey);
		logger.info("身份证号："+identityKey+",查询用户，查询结果数量："+userList!=null?userList.size():null);
		
		return userList;
	}
	/**
	 * 方法描述：查询用户所有信息 根据用户email
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2012-3-13 下午2:48:43
	 */
	public User selectUserEmail(String email)  {
		User userRes = null;
		userRes = (User) sqlMapClientTemplate.queryForObject("user.selectUserEmail", email);
		if(userRes == null){
			userRes = new User();
			userRes.setRespCode(Command.respCode_FAIL);
			userRes.setRespMsg("查询用户信息失败");
		} else {
			userRes.setRespCode(Command.respCode_SUCCESS);
			userRes.setRespMsg("查询用户信息成功");
		}
		
		return userRes;
	}
	
	/**
	 * 方法描述：根据用户ID修改数据
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-5-9 下午06:03:00   updateUserAllByUsername
	 */
	public User updUserInfo(User user){
		//TODO:逻辑已更改
		User userRes = null;
		
//		//
//		if(!Assert.empty(user.getMobilephone())){
//			userRes = this.queryUserByMobilephoneOrMailOrSn(user.getMobilephone());
//			if(userRes != null){
//				userRes = new User();
//				userRes.setRespCode(Command.respCode_FAIL);
//				userRes.setRespMsg("该手机号码已被他人绑定，请换个手机号码再试");
//			}
//		}
		UserResult ur = new UserResult();
		ur.setResult(false);
		
		
		//开始判断并可能更新邮箱
		if(!Assert.empty(user.getEmail())){
			try {
				ur = usfi.bindMail(user.getMemberCode(), user.getEmail());
				showUserSpecFuncResult("bindMail", ur);
			} catch (Exception e) {
				logger.error("调用账户账务服务平台绑定邮箱失败");
			}
			if(ur.getResult()){
				userRes = new User();
				userRes.setRespCode(Command.respCode_SUCCESS);
				userRes.setRespMsg("更新邮箱成功");
			}else{
//				userRes = new User();
//				userRes.setRespCode(Command.respCode_FAIL);
//				userRes.setRespMsg(UserCommand.getResMsg(ur.getMsgCode()));
//				return userRes;
			}
		}
		
		
		try {
			ur = usfi.updateMemberInfoByMemberCode(user.toUserSpecDto());
			showUserSpecFuncResult("updateMemberInfo", ur);
		} catch (Exception e) {
			logger.error("调用账户账务服务平台修改用户信息失败");
		}
		
		if(ur.getResult()){
			userRes = new User();
			userRes.setRespCode(Command.respCode_SUCCESS);
			userRes.setRespMsg("更新用户信息成功");
		}else{
			userRes = new User();
			userRes.setRespCode(Command.respCode_FAIL);
			userRes.setRespMsg("系统繁忙，请稍后重试");
			return userRes;
		}
		return userRes;
		
//		以前逻辑
//		User userRes = new User();
//		userRes = user;
//		String validateMobilephone=null;
//		String validateEmail=null;
//		String validateUsername=null;
//		if(user.getMobilephone() != null && !"".equals(user.getMobilephone())){
//			validateMobilephone  = (String)sqlMapClientTemplate.queryForObject("user.validateMobilephone", user);
//		}
//		if(user.getEmail() != null && !"".equals(user.getEmail())){
//			validateEmail  = (String)sqlMapClientTemplate.queryForObject("user.validateEmail", user);
//		}
//		if(user.getUsername() != null && !"".equals(user.getUsername())){
//			validateUsername  = (String)sqlMapClientTemplate.queryForObject("user.validateUsername", user);
//		}
//		if(validateUsername==null){
//			if(validateEmail==null){
//				if(validateMobilephone==null){
//					//userRes = UserDaoImpl.addOrUpdateUserFroad(user,"2");//修改用户信息
//					int i = sqlMapClientTemplate.update("user.updateUserByUserID",user);
//					if(i > 0) {
//						userRes.setRespCode(Command.respCode_SUCCESS);
//						userRes.setRespMsg("修改成功");
//					} else {
//						userRes.setRespCode(Command.respCode_FAIL);
//						userRes.setRespMsg("修改失败");
//					}										
//				}else{
//					userRes.setRespCode(Command.respCode_FAIL);
//					userRes.setRespMsg("该手机号码已被他人绑定，请换个手机号码再试");
//				}
//			} else {				
//				userRes.setRespCode(Command.respCode_FAIL);
//				userRes.setRespMsg("该邮箱已被他人绑定，请换个邮箱再试");
//			}
//		} else {			
//			userRes.setRespCode(Command.respCode_FAIL);
//			userRes.setRespMsg("该用户名已被他人注册，请换个再试");
//		}
//		return userRes;
	}
	
	
	/**
	  * 方法描述：根据用户名修改用户余额
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: Nov 21, 2011 10:25:55 AM
	  */
	public int updateBalanceByUsername(User user)  {
//		User userRes = new User();
		return sqlMapClientTemplate.update("user.updateBalanceByUsername", user);
//		if(i > 0) {
//			userRes.setRespCode(Command.respCode_SUCCESS);
//			userRes.setRespMsg("修改成功");
//		} else {
//			userRes.setRespCode(Command.respCode_FAIL);
//			userRes.setRespMsg("修改失败");
//		}
//		
//		return userRes;
	}

	/**
	 * 方法描述：查询逻辑卡号 是否存在
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-9 上午11:58:56
	 */
	public User selectSN(String sn)  {
		User user = null ;
		user = (User) sqlMapClientTemplate.queryForObject("user.getUserInfoBySn", sn);

		if (user != null) {
			user.setRespCode("0");
			user.setRespMsg("查询逻辑卡号成功");
		}

		return user;
	}

	/**
	  * 方法描述：更新user表Ip地址和登录时间  并返回上一次登录时间和ip
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: Sep 27, 2011 3:47:43 PM
	  */
	public User updateUserLastInfoByUsername(User userReq)  {	
		//TODO:逻辑已更改
		User user = new User();
		if(uadi.updateUserAppend(userReq.toUserAppendData())){
			user.setRespCode("0");
			user.setRespMsg("登录成功,修改登录信息成功!");
		}else{
			user.setRespCode("1");
			user.setRespMsg("修改登录信息失败!");
		}
		//以前逻辑
//		if(user.getRespCode().equals(Command.respCode_SUCCESS)){
//			int res = -1;
//			res = sqlMapClientTemplate.update("user.updateUserLastInfoByUsername", userReq);
//			if (res > 0) {
//				user.setRespCode("0");
//				user.setRespMsg("登录成功,修改登录信息成功!");
//			} else {
//				user.setRespCode("1");
//				user.setRespMsg("修改登录信息失败!");
//			}
//		} 
//		
		return user;
	}

	/**
	 * <p>
	 * description:用户退出(暂不用)
	 * 
	 * @param user
	 * @return
	 *            <p>
	 *            author 勾君伟 goujunwei@f-road.com.cn
	 * @time:2011-3-11 下午03:04:35
	 * @version 1.0
	 */
	public LoginManager logout(LoginManager loginManager)  {
		int res = -1;
		res = sqlMapClientTemplate.delete("user.logout", loginManager);
		if (res >= 0) {
			loginManager.setRespCode("0");
			loginManager.setRespMsg("退出成功！");
		} else {
			loginManager.setRespCode("1");
			loginManager.setRespMsg("退出失败!");
		}
		return loginManager;
	}

	/**
	 * <p>
	 * description:接收激活超链接
	 * 
	 * @param loginManager
	 * @return
	 *            <p>
	 *            author 勾君伟 goujunwei@f-road.com.cn
	 * @time:2011-3-15 下午05:27:49
	 * @version 1.0
	 */
	public LoginManager receiveActivatecode(LoginManager loginManager)
			 {
		return MailUtil.send(loginManager);
	}

	/**
	 * 方法描述:用户根据用户名和注册邮箱找回密码
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-9 上午11:58:56
	 */
	public LoginManager initPassword(LoginManager loginManager)
			 {
		//TODO:废除该方法
		logger.error("===========================================================================方法已废除");
//		User user = new User();
//		user.setUsername(loginManager.getUsername());
//		user.setPassword(loginManager.getPassword());
//		user.setUserID(loginManager.getUserID());
//		loginManager.setPassword(loginManager.getActivatecode());
//		user = updPassword(user);
//		
//		if (user.getRespCode().equals(Command.respCode_SUCCESS)) {
//			// 向用户邮箱发送临时密码
//			loginManager = MailUtil.initPassword(loginManager);
//
//			if (Command.respCode_FAIL.equals(loginManager.getRespCode())) {
//				return loginManager;
//			}
//			loginManager.setRespCode(Command.respCode_SUCCESS);
//			loginManager.setRespMsg("密码重置成功！");
//		} else {
//			loginManager.setRespCode(Command.respCode_FAIL);
//			loginManager.setRespMsg("密码重置失败!");
//		}
		return loginManager;
	}

	/**
	 * 方法描述：修改密码
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-9 上午11:58:56
	 */
	public User updPassword(Long memberCode,String oldPwd,String newPwd) {
		//TODO:接口已更改
		User userRes = new User();
		UserResult ur = new UserResult();
		ur.setResult(false);
		try {
			ur = usfi.updatePwd(memberCode, oldPwd, newPwd);
			showUserSpecFuncResult("modifyMemberPwd", ur);
		} catch (Exception e) {
			logger.error("调用账户账务服务平台方法modifyMemberPwd更改密码出现异常", e);
		}
		if(ur.getResult()){
			userRes.setRespCode(Command.respCode_SUCCESS);
			userRes.setRespMsg("密码修改成功！");
		}else{
			userRes.setRespCode(Command.respCode_FAIL);
			userRes.setRespMsg(UserCommand.getResMsg(ur.getMsgCode()));
		}
		return userRes;
//		// 更新user密码     --------------------------以前逻辑
//		int res = -1;
//		res = sqlMapClientTemplate.update("user.updPassword", user);
//		if (res > 0) {
//			user.setRespCode(Command.respCode_SUCCESS);
//			user.setRespMsg("密码修改成功！");
//		} else {
//			user.setRespCode(Command.respCode_FAIL);
//			user.setRespMsg("密码修改失败!该用户不存在。");
//		}
//		return user;
	}

	/**
	 * 方法描述：获取用户权限
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 勾君伟 goujunwei@f-road.com.cn
	 * @throws SQLException
	 * @time: 2011-4-4 上午12:05:36
	 */
	@SuppressWarnings("unchecked")
	public List<Authorities> queryAuthoritiesByUsername(String username){
		//TODO:已实现新逻辑
		
		List<Authorities> list = new ArrayList<Authorities>();
		list = uadi.queryAuthoritiesByUserID(queryUserAllByUsername(username).getUserID());
		
		
		//以前逻辑
		//list = sqlMapClientTemplate.queryForList("user.queryAuthoritiesByUsername",username);
		
		
		return list;
	}

	/**
	 * 方法描述：获取所有权限
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 勾君伟 goujunwei@f-road.com.cn
	 * @throws SQLException
	 * @time: 2011-4-4 上午12:05:36
	 */
	@SuppressWarnings("unchecked")
	public List<Authorities> queryAuthorities()  {
		//TODO:逻辑已更改
		List<Authorities> list = new ArrayList<Authorities>();
		list = uadi.queryAuthorities();
		//============================================以前逻辑
//		 SqlMapClientTemplate sqlMapClientTemplate = SqlMapClientFactory.sqlMapClientTemplateUser;
//		User userRes2 = null;
//		userRes2 = (User)sqlMapClientTemplate.queryForObject("user.queryUserAllByUsername","rikouhou");//test
	//	list = sqlMapClientTemplate.queryForList("user.queryAuthorities");
		//==============================================
		return list;
	}

	/**
	 * 方法描述：根据用户权限获取资源信息
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 勾君伟 goujunwei@f-road.com.cn
	 * @throws SQLException
	 * @time: 2011-4-4 上午12:05:46
	 */
	public List<Resources> queryResourcesByAuthorities(String auth)
			 {
		List<Resources> list = new ArrayList<Resources>();
		list = uadi.queryResourcesByAuthorities(auth);
		
		//=============================================以前逻辑
		//list = sqlMapClientTemplate.queryForList("user.queryResourcesByAuthorities",
		//			auth);
		return list;
	}

	
	private static User addOrUpdateUserFroad(User user,String upType){
		User userRes = new User();
		MD5 md5 = new MD5();
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String requestTime = df2.format(new Date());
		 String currTime = DateUtil.currentTimeToString();
		 String version  = "1.0";//.USER_UP_VERSION ;
		 String type  = upType;//Command.UPUSER_UP_TYPE ;		 
		 String userID  = user.getUserID()==null?"":user.getUserID();
		 String username  = user.getUsername()==null?"":user.getUsername();
		 String password  = user.getPassword()==null?"":user.getPassword();
		 String uname  = user.getUname()==null?"":user.getUname();
		 String bank  = user.getBank()==null?"":user.getBank();
		 String email  = user.getEmail()==null?"":user.getEmail();
		 String lastIP  = user.getLastIP()==null?"":user.getLastIP();
		 String lastTime  = user.getLastTime()==null?currTime:user.getLastTime();
		 String userStatus  = user.getStatus()==null?"":user.getStatus();
		 String mobilephone  = user.getMobilephone()==null?"":user.getMobilephone();
		 String activatecode  = user.getActivatecode()==null?"":user.getActivatecode();
		 String blance  = user.getBalance()==null?"":user.getBalance();
		 String identityKey  = user.getIdentityKey()==null?"":user.getIdentityKey();		 
		 
		 MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager(); // 多线程
		 // 多线程
		 HttpClient httpClient = new HttpClient(connectionManager);
		 // 测试是否超时
		 HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
		 // 设置连接超时时间(单位毫秒)
		 managerParams.setConnectionTimeout(new Integer("600000"));//Command.HTTP_CONNECTIONTIMEOUT
		 // 设置读数据超时时间(单位毫秒)
		 managerParams.setSoTimeout(new Integer("180000"));//Command.HTTP_SOTIMEOUT
		
		 PostMethod postMethod = new PostMethod(SysCommand.USER_ADD_UPDATE_URL);//
		 try {
			 String key = "froaduser";
			 String signMsg;			
			 signMsg = md5.getMD5ofStr(version+type+key);
			 logger.info("signMsg:  " + signMsg);	
			 
			 String2Xml bodyXml = new String2Xml("FroadMallApiRequest");
			 bodyXml.addElementName("user");
			 bodyXml.append("userID", userID);
			 bodyXml.append("username", username);
			 bodyXml.append("uname", uname);
			  bodyXml.append("password", password);
			 bodyXml.append("bank", bank);
			  bodyXml.append("email", email);
			 bodyXml.append("lastIP", lastIP);
			  bodyXml.append("lastTime", lastTime);
			 bodyXml.append("status", userStatus);
			  bodyXml.append("mobilephone", mobilephone);
			 bodyXml.append("activatecode", activatecode);
			  bodyXml.append("blance", blance);
			 bodyXml.append("identityKey", identityKey);
			 bodyXml.addElementName("system");
			 bodyXml.append("version", version);
			 bodyXml.append("type", type);
			 bodyXml.append("signMsg", signMsg);
			 bodyXml.append("requestTime", requestTime);
			 String body = bodyXml.toXMLString();
			 
			 int status = 0;
			 postMethod.getParams().setContentCharset("UTF-8");				
			 logger.info("请求体:" + body);

			 postMethod.setRequestBody(body.toString());
			 long startTime = System.currentTimeMillis();
			 status = httpClient.executeMethod(postMethod);
			 long endTime = System.currentTimeMillis();
			 logger.info("发送请求,连接用时" + (endTime - startTime) + "ms");
			 logger.info("http返回状态:" + status);		
			 if (status == HttpStatus.SC_OK) {
				 BufferedReader br = new BufferedReader(new InputStreamReader(
				 postMethod.getResponseBodyAsStream(), "utf-8"));
				 String line = "";
				 StringBuffer buffer = new StringBuffer();
				 while ((line = br.readLine()) != null) {
					 buffer.append(line);
				 }
				 logger.info("响应体:" + buffer.toString());
				 String resultStr = buffer.toString();
				 String[] rstr = resultStr.split("|");
				 if (rstr[0]!=null && "0".equals(rstr[0])) {
					 userRes.setRespCode(Command.respCode_SUCCESS);
					 userRes.setRespMsg("成功...");
					 logger.info("响应码:" + userRes.getRespCode() + "响应信息:" + userRes.getRespMsg() + ".会员:" + userRes.getUsername());			 
				 } else {
					 //会员积分查询请求失败
					 userRes.setRespCode(Command.respCode_FAIL);
					 userRes.setRespMsg((rstr==null || rstr[1]==null?"失败":rstr[1])+ "会员:" + userRes.getUsername());
					 logger.info("响应码:" + userRes.getRespCode() + "响应信息:" + userRes.getRespMsg() + ".商户会员标识:" + userRes.getUsername());		
				 }
		
			 } else {
				 //会员积分查询请求失败
				 userRes.setRespCode(Command.respCode_FAIL);
				 userRes.setRespMsg("发送请求失败,HTTP通信失败," + "会员:" + userRes.getUsername());
			 }			 
		 } catch (HttpException e) {
			 userRes.setRespCode(Command.respCode_FAIL);
			 userRes.setRespMsg("发生Http异常!");
			 logger.info(userRes.getRespMsg() + "\t具体异常信息:" + e.getMessage());
		 } catch (java.net.NoRouteToHostException e) {
			 userRes.setRespCode(Command.respCode_FAIL);
			 userRes.setRespMsg("本机没联网:No route to host: connect!");
			 logger.info(userRes.getRespMsg() + "\t具体异常信息:" + e.getMessage());
		 } catch (java.net.ConnectException e) {
			 userRes.setRespCode(Command.respCode_FAIL);
			 userRes.setRespMsg("连接不上积分平台：Connect time out!");
			 logger.info(userRes.getRespMsg() + "\t具体异常信息:" + e.getMessage());
		 } catch (IOException e) {
			 userRes.setRespCode(Command.respCode_FAIL);
			 userRes.setRespMsg("与第三方通信异常!");
			 logger.info(userRes.getRespMsg() + "\t具体异常信息:" + e.getMessage());
		 } finally {
			 // 释放连接
			 postMethod.releaseConnection();
		 }	
		return userRes;
	}
	public static void main(String args[]){
		UserDaoImpl testimpl = new UserDaoImpl();
		User userreq = new User();
		userreq.setUsername("lqptest1");//FBUtest
		//userreq.setUserID("");//userID c3386163-842f-4351-b545-5e39837b725c
		userreq.setPassword("12346");//12346
		userreq.setEmail("liqiaopeng@f-road.com.cn");//liqiaopeng@f-road.com.cn
		userreq.setMobilephone("13918140393");//13918140393
		User usere = testimpl.addOrUpdateUserFroad(userreq,"1");//1:增加    2：修改
		//testimpl.queryAuthoritiesByUsername("test");
		//testimpl.queryAuthorities();
	}

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}

	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public User getUserByPager(User user) {
		List list=sqlMapClientTemplate.queryForList("user.getUserByPager",user);
		Integer count=(Integer)sqlMapClientTemplate.queryForObject("user.getUserByPagerCount",user);
		user.setList(list);
		user.setTotalCount(count);
		return user;
	}

	@Override
	public List<User> getUsersByIdList(List<String> userIdList) {
		List<User> users = new ArrayList<User>();
		User u = null;
		for (String string : userIdList) {
			u = queryUserAllByUserID(string);
			users.add(u);
		}
		return users;
		
		//return sqlMapClientTemplate.queryForList("user.getUsersByIdList",userIdList);
	}
	
	@Override
	public List<User> getUsers(User user) {
		return sqlMapClientTemplate.queryForList("user.getUserByPager",user);
	}

	@Override
	public UserResult updeatUserByMemberCode(User user) {
		//TODO:新增接口未完善
		UserResult ur = null;
		try {
			ur = usfi.updateMemberInfoByMemberCode(user.toUserSpecDto());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ur;
	}	
	
	public User userLogin(String loginID, String loginPWD, String loginIP) {
		User userRes = new User();
		//TODO:新增接口
		UserResult ur = new UserResult();
		try {
			ur = usfi.login(loginID, loginPWD, loginIP);
			showUserSpecFuncResult("login", ur);
		} catch (Exception e) {
			logger.error("调用账户账务服务平台方法login出现异常 传入参数 loginID ：" + loginID + "loginPWD：" + loginPWD + " loginIP：" + loginIP + "出现异常", e);
			ur.setResult(false);
		}
		if(ur.getResult()){
			userRes = new User(ur.getUserList().get(0));
			userRes = getUserAppendData(userRes);//获取user追加字段
			userRes.setRespCode(Command.respCode_SUCCESS);
			userRes.setRespMsg("登录成功");
		}else{
			userRes.setRespCode(Command.respCode_FAIL);
			//已被锁定
			if("2306".equals(ur.getMsgCode().toString())){
				userRes.setRespMsg(UserCommand.getResMsg(ur.getMsgCode()));
			//已被禁用
			}else if("2307".equals(ur.getMsgCode().toString())){
				userRes.setRespMsg(UserCommand.getResMsg(ur.getMsgCode()));
			}else if("2305".equals(ur.getMsgCode().toString())){
				userRes.setRespMsg(UserCommand.getResMsg(2305));
			}else{
				JSONObject json = JSONObject.fromObject(ur.getDemo());
				int limit = Integer.parseInt(json.get("loginLimit").toString());
				int error = Integer.parseInt(json.get("failureTime").toString());
				if(limit-error==0){
					userRes.setRespMsg(UserCommand.getResMsg(2305));
				}else{
					userRes.setRespMsg(UserCommand.getResMsg(ur.getMsgCode()) + error + "次，您还可以尝试" + (limit - error) + "次");				
				}

			}
			
		}
		userRes.setMsgCode(ur.getMsgCode());
		return userRes;
	}
	
	public UserSpecFuncImpl getUsfi() {
		return usfi;
	}
	public void setUsfi(UserSpecFuncImpl usfi) {
		this.usfi = usfi;
	}
	public UserAppendDaoImpl getUadi() {
		return uadi;
	}
	public void setUadi(UserAppendDaoImpl uadi) {
		this.uadi = uadi;
	}
	
	@Override
	public boolean updateUserAppendInfo(User user) {
		return uadi.updateUserAppend(user.toUserAppendData());
	}
	@Override
	public User forgetPwdToGetIt(Long memberCode) {
		
		UserResult ur = new UserResult();
		ur.setResult(false);
		User userRes = new User();
		
		try {
			ur = usfi.forgetPwdWithChannel(memberCode);
			showUserSpecFuncResult("forgetPwd", ur); 
		} catch (Exception e) {
			logger.error("调用账户账务服务平台方法forgetPwd出现异常 传入参数 memberCode ：" + memberCode + " 出现异常", e);
		}
		if(ur.getResult()){
			userRes.setRespCode(Command.respCode_SUCCESS);
			userRes.setRespMsg("找回密码，重置密码成功");
		}else{
			userRes.setRespCode(Command.respCode_FAIL);
			userRes.setRespMsg("找回密码，重置密码失败");
		}
		return userRes;
	}
	@Override
	public UserResult bingPhone(Long memberCode, String phoneNum) throws Exception{
		UserResult ur = new UserResult();
		ur.setResult(false);		
		try {
			ur = usfi.bindMobile(memberCode, phoneNum);
		} catch (Exception e) {
			logger.error("调用账户账务服务绑定手机号码异常", e);
			throw new AppException("绑定手机失败");
		}
		return ur;
	}
	@Override
	public boolean insertUserAppendInfo(User user) {
		UserAppend userAppend = new UserAppend();		
		userAppend.setUserID(user.getMemberCode().toString());
		userAppend.setIsPointActivateLock("1");
		userAppend.setPointActivateFailureTimes(0);
		userAppend.setFirstLogin("0");
		userAppend.setState("30");	
		userAppend.setActivateCode(Util.createCode(UserDaoImpl.getActivatecode(user)));			
		Integer row = uadi.addUserAppend(userAppend);
		if(row != null){
			
			// 添加用户角色		
			String ID = null;
			Integer maxID = uadi.queryMaxRoleID();
			
			if (maxID == null) {
				ID = "1";
			} else {
				ID = String.valueOf(maxID + 1);
			}
			user.setUser_authorities_id(ID);
			user.setUserID(user.getMemberCode().toString());
			//添加角色
			uadi.addrole(user);
			logger.info("为memberCode"+user.getMemberCode() +"添加追加信息成功");
			return true;
			
		}else{
			logger.error("---------------->失败 向userAppend 表添加追加用户信息失败 " + user.getUserID());
			return false;
		}		
		
	}
}
