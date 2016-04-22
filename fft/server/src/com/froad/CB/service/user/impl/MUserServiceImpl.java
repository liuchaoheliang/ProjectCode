package com.froad.CB.service.user.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.log4j.Logger;
import com.froad.CB.AppException.AppException;
import com.froad.CB.common.Command;
import com.froad.CB.common.SmsLogType;
import com.froad.CB.common.SysCommand;
import com.froad.CB.common.constant.PointCommand;
import com.froad.CB.common.constant.TranCommand;
import com.froad.CB.dao.BuyerChannelDao;
import com.froad.CB.dao.BuyersDao;
import com.froad.CB.dao.user.UserDao;
import com.froad.CB.po.BuyerChannel;
import com.froad.CB.po.Buyers;
import com.froad.CB.po.SmsLog;
import com.froad.CB.po.UserCertification;
import com.froad.CB.po.transaction.Points;
import com.froad.CB.po.user.Authorities;
import com.froad.CB.po.user.LoginManager;
import com.froad.CB.po.user.Resources;
import com.froad.CB.po.user.User;
import com.froad.CB.service.BuyersService;
import com.froad.CB.service.MessageService;
import com.froad.CB.service.UserCertificationService;
import com.froad.CB.service.impl.PointServiceImpl;
import com.froad.CB.service.user.MUserService;
import com.froad.util.Assert;
import com.froad.util.CoderSectury;
import com.froad.util.DateUtil;
import com.froad.util.MD5;
import com.froad.util.MessageSourceUtil;
import com.froad.util.mail.MailUtil;
import com.pay.user.helper.CreateChannel;
import com.pay.user.helper.MemberType;

@WebService(endpointInterface = "com.froad.CB.service.user.MUserService")
public class MUserServiceImpl implements MUserService {
	private static Logger logger = Logger.getLogger(MUserServiceImpl.class);	
	private UserDao userDao;
	private BuyersDao buyersDao;
	private BuyerChannelDao buyerChannelDao;
	private UserCertificationService userCertificationService;
	private BuyersService buyersService;
	private MessageService messageService;
	
	
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
	public User register(User user) throws AppException {
		return userDao.register(user);
	}
	
//	public static void main(String args[]){
//		MUserServiceImpl m = new MUserServiceImpl();
//
//		User user = new User();
//		User userreq = new User();
//		user.setUsername("guagua00e142");
//		user.setUsername("user");
//		user.setBalance("111");
//		user.setLastIP("1111");
//		user.setLastTime("2222");
//		try {
//			userreq = m.queryUserAllByUsername("test");
//			//m.updateBalanceByUsername(user);
//			//m.updateUserLastInfoByUsername(user);
//			System.out.print("userreq.name"+userreq.toString());
//		} catch (Exception e) {
//			logger.error("异常", e);
//		}
//	}

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
	public static String getActivatecode(User user) throws AppException {
		String md5 = user.getUserID() + user.getUsername();
		return new BigInteger(CoderSectury.encryptMD5(md5.getBytes()))
				.toString(16);
		// return CoderSectury.encryptBASE64(md5.getBytes());
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
	public User addedUser(String username) throws AppException {
		return userDao.addedUser(username);
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
	public User queryUserAllByUsername(String username){
		return userDao.queryUserAllByUsername(username);
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
		try {
			userRes =userDao.queryUserByUsernameAndSPpassword(username, SPpassword);
			if(userRes == null){
				userRes = new User();
				userRes.setRespCode(Command.respCode_FAIL);
				userRes.setRespMsg("查询用户信息失败");
			} else {
				userRes.setRespCode(Command.respCode_SUCCESS);
				userRes.setRespMsg("查询用户信息成功");
			}
		} catch (Exception e) {
			userRes = new User();
			logger.error("异常", e);
			userRes.setRespCode(Command.respCode_FAIL);
			userRes.setRespMsg("查询用户信息异常");
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
	public User queryUserByMobilephoneOrMailOrSn(String value) throws AppException {
		return userDao.queryUserByMobilephoneOrMailOrSn(value);
	}
	/**
	 * 方法描述：查询用户所有信息 根据手机号码 或邮箱
	 */
	public User queryUserByMobilephoneOrMail(String value) throws AppException {
		return userDao.queryUserByMobilephoneOrMail(value);
	}
	/**
	 *  方法描述：查询用户所有信息 根据手机号码 或用户名
	 */
	public User queryUserByMobilephoneOrUsername (String value) throws AppException {
		return userDao.queryUserByMobilephoneOrUsername(value);
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
	public User queryUserAllByUserID(String userID) throws AppException {
		return userDao.queryUserAllByUserID(userID);
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
	public User selectUserMobilephone(String mobilephone) throws AppException {
		return userDao.selectUserMobilephone(mobilephone);
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
	public  List<User> queryUserByIdentityKey(String identityKey) throws AppException {
		return userDao.queryUserByIdentityKey(identityKey);
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
	public User selectUserEmail(String email){
		return userDao.selectUserEmail(email);
	}
	
	/**
	 * 方法描述：根据用户ID修改数据
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-5-9 下午06:03:00
	 */
	public User updUserInfo(User user) throws AppException {
		return userDao.updUserInfo(user);
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
	public User updateBalanceByUsername(User user) throws AppException {
		User userRes = new User();
		try {
			int i = userDao.updateBalanceByUsername(user);
			if(i > 0) {
				userRes.setRespCode(Command.respCode_SUCCESS);
				userRes.setRespMsg("修改成功");
			} else {
				userRes.setRespCode(Command.respCode_FAIL);
				userRes.setRespMsg("修改失败");
			}
		} catch (Exception e) {
			logger.error("异常", e);
		}
		
		return userRes;
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
	public User selectSN(String sn) throws AppException {
		return userDao.selectSN(sn);
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
	public User updateUserLastInfoByUsername(User userReq) throws AppException {
		return userDao.updateUserLastInfoByUsername(userReq);
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
	public LoginManager logout(LoginManager loginManager) throws AppException {
		return userDao.logout(loginManager);
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
			throws AppException {
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
			throws AppException {
		return userDao.initPassword(loginManager);
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
	public User updPassword(Long memberCode,String oldPwd,String newPwd) throws AppException {
		return userDao.updPassword(memberCode,oldPwd,newPwd);
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
	public List<Authorities> queryAuthoritiesByUsername(String username)
			throws AppException {
		return userDao.queryAuthoritiesByUsername(username);
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
	public List<Authorities> queryAuthorities() throws AppException {
		return userDao.queryAuthorities();
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
	@SuppressWarnings("unchecked")
	public List<Resources> queryResourcesByAuthorities(String auth)
			throws AppException {
		return userDao.queryResourcesByAuthorities(auth);
	}

	/**
	 * @param 异常处理
	 * @throws AppException
	 */
	public void handleException(Exception e) throws AppException {
		throw new AppException("抛出 " + e.getClass() + " 异常");
	}

	@Override
	public User getUserByPager(User user) {
		if(user==null){
			logger.error("参数为空，分页查询失败");
			return null;
		}
		return userDao.getUserByPager(user);
	}
	
	@Override
	public List<User> getUsers(User user) {
		return userDao.getUsers(user);
	}
	
	
	public Buyers bindingBuyer(String phone,String createChannel){
		Buyers buyerRes=new Buyers();
		if(phone==null||"".equals(phone)){
			logger.error("传入的手机号为空");
			buyerRes.setRespCode(TranCommand.RESP_CODE_FAIL);
			buyerRes.setRespMsg("传入的手机号为空");
			return buyerRes;
		}
		User userRes = null;
		try {
			userRes = queryUserByMobilephoneOrMail(phone);
			if(Command.respCode_FAIL.equals(userRes.getRespCode())){//用户未注册，进行注册
				User user= new User();
				user.setMobilephone(phone);
				user.setPassword(phone.substring(5));
				user.setCreateChannel(createChannel);
				user =this.clientRegister(user, true);
				if(!"0".equals(user.getRespCode())){
					buyerRes.setRespCode(TranCommand.RESP_CODE_FAIL);
					buyerRes.setRespMsg(user.getRespMsg());
					return buyerRes;
				}
				userRes=user;
			}
		} catch (AppException e) {
			logger.error("按手机号："+phone+"查询用户时出现异常",e);
			buyerRes.setRespCode(TranCommand.RESP_CODE_FAIL);
			buyerRes.setRespMsg("查询或注册用户时出现异常:"+e.getMessage());
			return buyerRes;
		}
		UserCertification cert=new UserCertification();
		cert.setCertificationType(TranCommand.CHECK_PHONE);
		cert.setPhone(phone);
		boolean isValid=false;
		try {
			isValid=userCertificationService.checkAccount(cert);
		} catch (AppException e) {
			logger.error("校验是否为贴膜卡用户时出现异常",e);
			buyerRes.setUserId(userRes.getUserID());
			buyerRes.setUser(userRes);
			buyerRes.setRespCode(TranCommand.RESP_CODE_IS_USER);
			buyerRes.setRespMsg("用户注册成功，但为该用户绑定买家失败，异常信息： "+e.getMessage());
			return buyerRes;
		}
		if(isValid){//手机校验通过,更新买家表,更新买家资金渠道表
			cert.setUserId(userRes.getUserID());
			buyerRes=this.addOrUpdateBuyer(cert);
			buyerRes.setUserId(userRes.getUserID());
			buyerRes.setUser(userRes);
			buyerRes.setRespCode(TranCommand.RESP_CODE_IS_BUYER);
			buyerRes.setRespMsg("用户注册成功，买家绑定成功");
			return buyerRes;
		}else{
			buyerRes.setUserId(userRes.getUserID());
			buyerRes.setUser(userRes);
			buyerRes.setRespCode(TranCommand.RESP_CODE_IS_USER);
			buyerRes.setRespMsg("用户注册成功，但买家绑定失败，原因：该手机号没有绑定贴膜卡");
			return buyerRes;
		}
	}
	
	
	/**
	  * 方法描述：添加或更新买家以及买家资金渠道
	  * @param: UserCertification(userId,certificationType[channelId,accountNo,accountName,phone])
	  * @return: 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 6, 2013 5:56:25 PM
	  */
	private Buyers addOrUpdateBuyer(UserCertification cert){
		String userId=cert.getUserId();
		String time=DateUtil.formatDate2Str(new Date());
		Buyers buyer=buyersDao.getBuyersByUserId(userId);
		if(buyer==null){//如果买家不存在，为该用户创建一个买家
			buyer=new Buyers();
			buyer.setUserId(userId);
			buyer.setState(Command.STATE_START);
			buyer.setCreateTime(time);
			buyer.setUpdateTime(time);
			Integer buyerId=buyersDao.insert(buyer);
			buyer.setId(buyerId);
			
			BuyerChannel buyerChannel=new BuyerChannel();
			buyerChannel.setUserId(userId);
			buyerChannel.setBuyersId(buyer.getId()+"");
			if(TranCommand.CHECK_PHONE.equals(cert.getCertificationType())){//手机贴膜卡认证
				buyerChannel.setPhone(cert.getPhone());
				buyerChannel.setChannelId(TranCommand.CHANNEL_ID);
			}else{
				buyerChannel.setChannelId(cert.getChannelId());
				buyerChannel.setAccountName(cert.getAccountName());
				buyerChannel.setAccountNumber(cert.getAccountNo());
			}
			buyerChannel.setState(Command.STATE_START);
			buyerChannel.setCreateTime(time);
			buyerChannel.setUpdateTime(time);
			Integer buyerChannelId=buyerChannelDao.insert(buyerChannel);
			buyerChannel.setId(buyerChannelId);
			List<BuyerChannel> channelList=new ArrayList<BuyerChannel>();
			channelList.add(buyerChannel);
			buyer.setBuyerChannelList(channelList);
			return buyer;
		}else{//买家已存在，如果买家资金渠道不存在执行则insert买家资金渠道，否则直接返回buyer
			BuyerChannel buyerChannel=new BuyerChannel();
			buyerChannel.setUserId(userId);
			buyerChannel.setBuyersId(buyer.getId()+"");
			if(TranCommand.CHECK_PHONE.equals(cert.getCertificationType())){//手机贴膜卡认证
				buyerChannel.setPhone(cert.getPhone());
				buyerChannel.setChannelId(TranCommand.CHANNEL_ID);
			}else{
				buyerChannel.setChannelId(cert.getChannelId());
				buyerChannel.setAccountName(cert.getAccountName());
				buyerChannel.setAccountNumber(cert.getAccountNo());
			}
			buyerChannel.setState(Command.STATE_START);
			buyerChannel.setCreateTime(time);
			buyerChannel.setUpdateTime(time);
			
			List<BuyerChannel> channelList=buyer.getBuyerChannelList();
			if(channelList==null||channelList.size()==0){
				Integer buyerChannelId=buyerChannelDao.insert(buyerChannel);
				buyerChannel.setId(buyerChannelId);
				List<BuyerChannel> buyerChannelList=new ArrayList<BuyerChannel>();
				buyerChannelList.add(buyerChannel);
				buyer.setBuyerChannelList(buyerChannelList);
				return buyer;
			}else{
				return buyer;
			}
		}
		
	}	
	
	/**
	 * 手机客户端注册用户
	 * @param userRes    mobilephone:13888888888  password:123456
	 * @return
	 */
	public User clientRegister(User userRes,boolean flag){
		userRes.setRegisterIP("127.0.0.1");//暂时预设
		userRes.setCreateChannel(CreateChannel.FFT_MC.getValue());//创建渠道
		userRes.setMemberType(MemberType.PERSONAL.getValue());//会员类型
		String passWord=userRes.getPassword();//默认密码为手机号   手机号后6位
		userRes.setPassword( Assert.empty(passWord)?userRes.getMobilephone():passWord);
		
		User userRep = new User();
		userRep=userDao.register(userRes);
		if(userRep.getRespCode().equals(Command.respCode_SUCCESS)){
			//创建积分账户
			PointServiceImpl pointImpl = new PointServiceImpl();
			Points pointRes = new Points();
			pointRes.setAccountMarked(userRep.getUsername());
			pointRes.setPartnerNo(TranCommand.PARTNER_ID);
			pointRes.setAccountMarkedType(PointCommand.ACCOUNT_MARKED_TYPE);
			pointRes.setOrgNo(TranCommand.FFT_ORG_NO);
			pointRes.setPoints("0");
			//pointRes.setPoints("1");
			try {
				pointImpl.presentPoints(pointRes);
			} catch (AppException e) {
				logger.info("创建积分账户异常，手机客户端注册失败");
			}
			//发送手机短信提示登录用户名和密码
			String[] msg={userRes.getMobilephone(),userRes.getPassword()};
			String context=MessageSourceUtil.getSource().getMessage("register", msg, null);
			logger.info("是否发短信："+flag);
			if(flag){
				SmsLog smsLog=new SmsLog();
				smsLog.setMobile(userRep.getMobilephone());
				smsLog.setMessage(context);
				smsLog.setType(SmsLogType.SMSLOG_USER_REGISTER);
				messageService.sendMessage(smsLog);
			}			
		}else if(userRep.getRespCode().equals(Command.respCode_FAIL)){
			logger.info("手机客户端注册失败");
		}else{
			logger.info("手机客户端注册用户操作异常");
		}
		
		
//		MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager(); // 多线程
//		MD5 md5 = new MD5();
//		// 多线程
//		HttpClient h = new HttpClient(connectionManager);
//		// 测试是否超时
//		HttpConnectionManagerParams managerParams = h
//				.getHttpConnectionManager().getParams();
//		// 设置连接超时时间(单位毫秒)
//		managerParams.setConnectionTimeout(Command.HTTP_CONNECTIONTIMEOUT);
//		// 设置读数据超时时间(单位毫秒)
//		managerParams.setSoTimeout(Command.HTTP_SOTIMEOUT);
//		String version ="1.0";
//		//String userName = userRes.getMobilephone();//默认用户名为手机号
//		String passWord = userRes.getPassword();//默认密码为手机号
//		JSONObject user = new JSONObject();
//		user.put("mobilephone", userRes.getMobilephone());
//		user.put("password", Assert.empty(passWord)?userRes.getMobilephone():passWord);
//		//user.put("username", userName);
//		user.put("createChannel", userRes.getCreateChannel());//createChannel
//		
//		String key = "00CBA177EA725E7C";
//		String mac = md5.getMD5ofStr(version+user.toString()+key);
//		
//		JSONObject systemp = new JSONObject();
//		systemp.put("mac", mac);
//		systemp.put("version", version);
//		
//		JSONObject json = new JSONObject();
//		json.put("user", user);
//		json.put("system", systemp);
//		String url = SysCommand.USER_AUTO_ADD_URL;
//		
//		PostMethod postMethod = new PostMethod(url);
//		try {			
//			int status = 0;
//			postMethod.getParams().setContentCharset("UTF-8");				
//			logger.info("请求体:" + json.toString());
//			
//			postMethod.setRequestBody(json.toString());
//			Long startTime = 0l;// 定义连接数据开始时间
//			Long endTime = 0l;// 定义连接数据结束时间
//			startTime = System.currentTimeMillis();// 创建开始时间
//			status = h.executeMethod(postMethod);
//			logger.info("status:"+status);
//			endTime = System.currentTimeMillis();// 创建结束时间
//			if (status == HttpStatus.SC_OK) {
//				logger.info("客户端手机注册连接成功：用时" + (endTime - startTime) + "ms");
//				logger.info("注册手机用户" + postMethod.getResponseBodyAsString());
//				// System.out.println(postMethod.getResponseBodyAsString());
//				BufferedReader br = new BufferedReader(new InputStreamReader(
//						postMethod.getResponseBodyAsStream(), "utf-8"));
//				String line = "";
//				StringBuffer buffer = new StringBuffer();
//				while ((line = br.readLine()) != null) {
//					buffer.append(line);
//				}
//				logger.info(buffer);
//				JSONObject jsonRes = new JSONObject();
//				jsonRes = JSONObject.fromObject(buffer.toString());
//				String reno = jsonRes.getString("reno");
//				if(!Assert.empty(reno) && "0".equals(reno)){
//					String remsg = jsonRes.getString("remsg");
//					String userId = jsonRes.getString("userId");
//					String username = jsonRes.getString("username");
//					String memberId = jsonRes.getString("memberID");
//					userRep.setRespCode(reno);
//					userRep.setRespMsg(remsg);
//					userRep.setUserID(userId);
//					userRep.setUsername(username);
//					userRep.setMemberID(memberId);
//					userRep.setMobilephone(userRes.getMobilephone());
					//自动认证买家，资金渠道
//					UserCertification cert = new UserCertification();
//					cert.setUserId(userId);
//					cert.setCertificationType(TranCommand.CHECK_CARD);//手机贴膜卡
//					cert.setPhone(userRes.getMobilephone());//手机号码
//					cert.setChannelId("100001001");//资金渠道
//					cert.setState("30");
//					Integer certId = null;
//					try {
//						certId = userCertificationService.add(cert);
//					} catch (Exception e) {
//						logger.error("手机客户端注册成功，认证买家失败!", e);
//						userRep.setRespCode("5");//注册成功，认证买家失败
//						userRep.setRespMsg("注册成功，认证买家失败");
//					}
//					if(certId!=null){
//						//注册成功，认证买家成功
//					}else{
//						userRep.setRespCode("5");//注册成功，认证买家失败
//						userRep.setRespMsg("注册成功，认证买家失败");
//						
//					}
//					//创建积分账户
//					PointServiceImpl pointImpl = new PointServiceImpl();
//					Points pointRes = new Points();
//					pointRes.setAccountMarked(userRep.getUsername());
//					pointRes.setPartnerNo(TranCommand.PARTNER_ID);
//					pointRes.setAccountMarkedType(PointCommand.ACCOUNT_MARKED_TYPE);
//					pointRes.setOrgNo(TranCommand.FFT_ORG_NO);
//					pointRes.setPoints("0");
//					//pointRes.setPoints("1");
//					pointImpl.presentPoints(pointRes);
					
//				}else if(!Assert.empty(reno) && "1".equals(reno)){
//					String remsg = jsonRes.getString("remsg");
//					String userId = jsonRes.getString("userId");
//					String username = jsonRes.getString("username");
//					String memberId = jsonRes.getString("memberID");
//					userRep.setRespCode(reno);
//					userRep.setRespMsg(remsg);
//					userRep.setUserID(userId);
//					userRep.setUsername(username);
//					userRep.setMemberID(memberId);
//					userRep.setMobilephone(userRes.getMobilephone());
//				}else{
//					String remsg = jsonRes.getString("remsg");
//					userRep.setRespCode(reno);
//					userRep.setRespMsg(remsg);
//				}
//				
//			} else {
//				logger.info("客户端手机注册用户失败！：" + postMethod.getStatusLine());
//			}
//		} catch (HttpException e) {
//			userRep.setRespCode(Command.respCode_EXCEPTION);
//			userRep.setRespMsg("发生Http异常!");
//			logger.info(userRep.getRespMsg() + "\t具体异常信息:" + e.getMessage());
//		} catch (java.net.NoRouteToHostException e) {
//			userRep.setRespCode(Command.respCode_EXCEPTION);
//			userRep.setRespMsg("本机没联网:No route to host: connect!");
//			 logger.info(userRep.getRespMsg() + "\t具体异常信息:" + e.getMessage());
//		} catch (java.net.ConnectException e) {
//			userRep.setRespCode(Command.respCode_EXCEPTION);
//			userRep.setRespMsg("连接不上商城平台：Connect time out!");
//			 logger.info(userRep.getRespMsg() + "\t具体异常信息:" + e.getMessage());
//		} catch (IOException e) {
//			userRep.setRespCode(Command.respCode_EXCEPTION);
//			userRep.setRespMsg("与第三方通信异常!");
//			logger.info(userRep.getRespMsg() + "\t具体异常信息:" + e.getMessage());
//		}catch (Exception e) {
//			userRep.setRespCode(Command.respCode_EXCEPTION);
//			userRep.setRespMsg("客户端手机注册发生异常!");
//			logger.info(userRep.getRespMsg() + "\t具体异常信息:" + e.getMessage());
//		} finally {
//			postMethod.releaseConnection();
//
//		}
		return userRep;
	}
	
	public User autoAddMerchantUser(User user,boolean flag){
		logger.info("注册商户开始注册会员信息开始");
		User userRep = new User();
		try {
			logger.info("手机号码："+user.getMobilephone());
			if(!Assert.empty(user.getMobilephone())){
				user.setPassword(Assert.empty(user.getPassword())?user.getMobilephone():user.getPassword());				
			}else{
				logger.info("手机号码为空,不能注册会员！");
				userRep.setRespCode(Command.respCode_FAIL);
				userRep.setRespMsg("手机号码为空");
			}
			user.setCreateChannel("FFT");//来源分分通平台,注册的会员
			logger.info("注册会员开始：mobilephone:"+user.getMobilephone()+ " 用户名：" +user.getUsername()+" 密码："+user.getPassword()+" 来源渠道:"+user.getCreateChannel());
			User userRes = register(user);
			logger.info("注册会员结束");
			
			if(!Assert.empty(userRes.getRespCode()) && "0".equals(userRes.getRespCode())){
				logger.info("注册会员成功：mobilephone:"+userRes.getMobilephone()+ " 用户名：" +userRes.getUsername());
				String remsg = userRes.getRespMsg();
				String userId = userRes.getUserID();
				String username = userRes.getUsername();
				userRep.setRespCode(userRes.getRespCode());
				userRep.setRespMsg(remsg);
				userRep.setUserID(userId);
				userRep.setUsername(username);
				userRep.setMobilephone(userRes.getMobilephone());
				//创建积分账户
				logger.info("创建积分账户开始");
				PointServiceImpl pointImpl = new PointServiceImpl();
				Points pointRes = new Points();
				pointRes.setAccountMarked(username);
				pointRes.setPartnerNo(TranCommand.PARTNER_ID);
				pointRes.setAccountMarkedType(PointCommand.ACCOUNT_MARKED_TYPE);
				pointRes.setOrgNo(TranCommand.FFT_ORG_NO);
				pointRes.setPoints("0");
				//pointRes.setPoints("1");
				logger.info("创建积分账户参数：accountMarked:"+pointRes.getAccountMarked()+ " partnerNo：" 
						+pointRes.getPartnerNo()+" AccountMarkedType："+pointRes.getAccountMarkedType()
						+" orgNo："+pointRes.getOrgNo()+" points："+pointRes.getPoints());
				pointImpl.presentPoints(pointRes);
				logger.info("创建积分账户结束");
				//发送手机短信提示登录用户名和密码
				String[] msg={userRes.getMobilephone(),user.getPassword()};
				String context=MessageSourceUtil.getSource().getMessage("register", msg, null);
				logger.info("是否发短信："+flag);
				if(flag){
					SmsLog smsLog=new SmsLog();
					smsLog.setMobile(userRes.getMobilephone());
					smsLog.setMessage(context);
					smsLog.setType(SmsLogType.SMSLOG_USER_REGISTER);
					messageService.sendMessage(smsLog);
					logger.info("短信发送完毕！");
				}
				
			}else if(!Assert.empty(userRes.getRespCode()) && "1".equals(userRes.getRespCode())){					
				if(!Assert.empty(userRes.getUserID())){
					logger.info("会员已经存在！");
				}else{
					logger.info("会员注册失败！");
				}
				String remsg = userRes.getRespMsg();
				String userId = userRes.getUserID();
				String username = userRes.getUsername();
				userRep.setRespCode(userRes.getRespCode());
				userRep.setRespMsg(remsg);
				userRep.setUserID(userId);
				userRep.setUsername(username);
				userRep.setMobilephone(userRes.getMobilephone());
			}
		} catch (AppException e) {
			logger.info("注册商户自动注册用户出错！username:"+user.getUsername()+"  password:"+user.getPassword(),e);
			e.printStackTrace();
		}
		return userRep;
	}
	
	public boolean updateChangeMobilePhone(String mobilePhone,String userId) throws AppException{
		boolean reruenResult = false;
		User user = new User();
		UserCertification userCert=new UserCertification();
		userCert.setCertificationType("1");//手机号码验证
		userCert.setPhone(mobilePhone);
		try {
			boolean flag = userCertificationService.checkAccount(userCert);
			if(flag){
				logger.info("手机银行验证通过");
				boolean result = buyersService.updateBuyerAndBuyerChannel(userId, mobilePhone);
								
				if(!result){
					throw new AppException("更新买家资金渠道失败: "+mobilePhone);
				}
			}else{
				logger.info("手机银行验证不通过");
				//更新资金渠道为尚未认证状态(删除原来资金渠道记录)
				Buyers buyer = buyersService.getBuyerByUserId(userId);
				BuyerChannel bc = buyerChannelDao.getBuyerChannelByUserId(userId);
				
				if(buyer!=null && !Assert.empty(buyer.getId())){
					logger.info("存在买家信息 buyers.id："+buyer.getId());
					Integer num = null;
					try {
						num = buyersService.deleteById(String.valueOf(buyer.getId()));
					} catch (Exception e) {
						logger.info("buyersService.deleteById操作出错",e);
						num = 0;
					}		
					logger.info("删除买家信息  返回num："+num);
					if(num == null || num < 1){
						throw new AppException("删除买家信息失败: buyersId"+buyer.getId()+" mobilePhone:"+mobilePhone+" userId:" +userId);
					}
					//记录日志信息
					if(num != null && num > 0){
						logger.info("修改手机号码删除买家信息. userId："+userId+" Buyers.id:"+buyer.getId()+" Buyers.userId:"+buyer.getUserId()+" Buyers.state:"+buyer.getState()
								+" Buyers.createTime:"+buyer.getCreateTime()+" Buyers.updateTime:"+buyer.getUpdateTime()+" Buyers.remark:"+buyer.getRemark());
					}
				}
				if(bc!=null && !Assert.empty(bc.getId())){
					logger.info("存在买家资金渠道信息 bc.id："+bc.getId());
					Integer num2 = null;
					try {
						num2 = buyerChannelDao.deleteByPrimaryKey(String.valueOf(bc.getId()));
					} catch (Exception e) {
						logger.info("buyerChannelDao.deleteByPrimaryKey操作出错",e);
						num2 = 0;
					}		
					logger.info("删除买家资金渠道信息  返回num2："+num2);
					if(num2 == null || num2 < 1){
						throw new AppException("删除买家资金渠道失败: buyerscCannelId"+bc.getId()+" mobilePhone:"+mobilePhone+" userId:" +userId);
					}
					//记录日志信息
					if(num2 != null && num2 > 0){
						logger.info("修改手机号码删除买家资金渠道信息. userId："+userId+" BuyerChannel.id:"+bc.getId()+" BuyerChannel.userId:"+bc.getUserId()+" BuyerChannel.buyersId:"+bc.getBuyersId()
								+" BuyerChannel.buyersRuleId:"+bc.getBuyersRuleId()+" BuyerChannel.channelId:"+bc.getChannelId()+" BuyerChannel.idDefault:"+bc.getIsDefault()
								+" BuyerChannel.accountName:"+bc.getAccountName()+" BuyerChannel.accountNumber:"+bc.getAccountNumber()+" BuyerChannel.state:"+bc.getState()
								+" BuyerChannel.createTime:"+bc.getCreateTime()+" BuyerChannel.updateTime:"+bc.getUpdateTime()+" BuyerChannel.remark:"+bc.getRemark()
								+" BuyerChannel.phone:"+bc.getPhone());
					}
				}
			}
			user.setUserID(userId);
			user.setMobilephone(mobilePhone);
			logger.info("更新会员手机号码开始");
			
			if(userDao.bingPhone(Long.parseLong(user.getUserID()), user.getMobilephone()).getResult() != true){
				reruenResult = false;
				throw new AppException("更新账务账户平台出现异常: "+mobilePhone);				
			}
			
			
			//updUserInfo(user);
			logger.info("更新会员手机号码结束");
			reruenResult = true;
			logger.info("返回的reruenResult："+reruenResult);
		} catch (Exception e) {
			logger.info("changeMobilePhone的认证手机银行卡出错",e);
			throw new AppException("未知的认证类型: "+mobilePhone);
		}
		return reruenResult;
	}
	
//	public static void main(String[] args) throws AppException, SQLException {
//
//		MUserServiceImpl mi = new MUserServiceImpl();
////		List<Resources> list = mi.queryResourcesByAuthorities("a1");
////		for (Resources auth : list) {
////			System.out.println(auth.getResources_id() + "========");
////		}
//		User user = new User();
//		user.setMobilephone("13918140393");
//		user.setPassword("123456");
//		mi.clientRegister(user);
//	}

	public BuyersDao getBuyersDao() {
		return buyersDao;
	}

	public void setBuyersDao(BuyersDao buyersDao) {
		this.buyersDao = buyersDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserCertificationService getUserCertificationService() {
		return userCertificationService;
	}

	public void setUserCertificationService(
			UserCertificationService userCertificationService) {
		this.userCertificationService = userCertificationService;
	}

	public void setBuyerChannelDao(BuyerChannelDao buyerChannelDao) {
		this.buyerChannelDao = buyerChannelDao;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public BuyersService getBuyersService() {
		return buyersService;
	}

	public void setBuyersService(BuyersService buyersService) {
		this.buyersService = buyersService;
	}

	@Override
	public boolean updateUserByMemberCode(User user) {
		return userDao.updeatUserByMemberCode(user).getResult();
	}

	@Override
	public User loginPassAccountSys(String loginID, String loginPWD,String loginIP) {
		return userDao.userLogin(loginID, loginPWD, loginIP);
	}

	@Override
	public boolean updateUserAppendInfo(User user) {
		return userDao.updateUserAppendInfo(user);
	}

	@Override
	public User forgetPwdToGetIt(Long memberCode) {
		return userDao.forgetPwdToGetIt(memberCode);
	}

	@Override
	public boolean insertUserAppendInfo(User user) {
		return userDao.insertUserAppendInfo(user);
	}
	
}
