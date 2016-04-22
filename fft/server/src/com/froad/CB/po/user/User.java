package com.froad.CB.po.user;

import java.io.Serializable;

import com.froad.CB.common.Pager;
import com.froad.CB.po.UserCertification;
import com.froad.util.Assert;
import com.froad.util.TimeFn;
import com.pay.user.dto.UserSpecDto;


	/**
	 * 类描述：用户信息
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:50:07 PM 
	 */
public class User extends Pager implements Serializable {

	private static final long serialVersionUID = 4812721554030141843L;
	private String userID;
	private String user_authorities_id;//用户_权限ID	
	private String username;// 用户名
	private String password;// 密码
	private String bank;// 签约银行
	private String securityCode;// 验证码
	private String uname;// 用户姓名
	private String email;// 邮箱
	private String lastIP;// 上次登录的IP
	private String lastTime;// 上次登录时间
	private String status;// 用户活动状态 0：激活状态 1：未激活
	private String mobilephone;// 电话号码
	private String activatecode;//激活码
	private Integer userrole;// 用户角色  0：管理员  1：卖家   2:买家   3：客服
	private String activateURL;//激活超链接
	private String sn;//手机登陆逻辑卡号
	private String balance;//用户余额
	private String SPpassword;//商户验证密码
	private String actprojectID;//活动方案ID
	private String joinedActprojectID;//活动方案ID
	private String identityKey;//身份证号
	private String sex;//性别
	private Integer age ;//年龄
	private String channelNo;//
	private String respCode;
	private String respMsg;	
	private UserCertification userCertification;  //用于传数据,不用在sql上做关联
	
	/***************tmp field用于查询******************/
	private String authoritiesId;
	
	private String isAccountLocked;//账号是否锁定 0 锁定 1正常
	private Integer loginFailureCount;//连续登陆失败次数
	private String lockedDate;//账号锁定日期	
	private String lastTryTime;
	
	private String isPointActivateAccountLocked;//激活积分是否锁定
	private Integer pointActivateFailureCount;//激活积分失败次数
	private String pointActivateLockedDate;//激活积分锁定日期	
	private String lastPointActivateTime;//激活积分的尝试时间
	
	private String firstLogin;//是否刚注册用户，0：刚注册第一次登陆系统   1：已经登录过系统
	private String createChannel;//会员注册创建渠道：FFT 分分通,MALL 商城，FFT_MC 分分通手机客户端，MAll_MC 商城手机客户端
	private String memberID;//员工号，用于账户账务服务	
	
	public User(){}   //  无参构造函数
	
	
	
	
	
	
	//=====================================================账户账务平台新加字段	
	private String updateTime;
	private String createTime;
	private Integer memberType; //1 个人会员  2企业会员
	private Long MemberCode;
	private String registerIP;
	private Integer msgCode;   //手机客户端Code使用
	//=====================================================
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>该构造函数用于将账户账务平台对象转换成分分通user对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-24 上午10:00:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public User(UserSpecDto usd){
	
		if(usd == null){      //如果传入对象为空，则无须转换对应字段
			return;
		}
		
		this.username = usd.getLoginID();    //设置分分通对象登录帐号
		
		this.password = usd.getLoginPwd();    //设置分分通对象登录密码
		
		this.uname = usd.getUname();         //设置分分通对象用户自然名
		
		this.email = usd.getEmail();         //设置分分通对象邮箱
		
		this.status = usd.getStatus() + "";       //设置分分通对象用户活动状态
		
		this.mobilephone = usd.getMobile();       //设置分分通对象手机号码
		
		this.identityKey = usd.getIdentityKey();	//设置分分通对象身份证号码
		
		this.sex = usd.getSex();					//设置分分通对象性别
		
		this.age = usd.getAge();    				//设置分分通对象年龄
		
		this.updateTime = TimeFn.formatTimeToString(usd.getUpdateTime());	//设置数据更新时间
		
		this.createTime = TimeFn.formatTimeToString(usd.getCreateTime());	 //设置数据创建时间
		
		this.createChannel = usd.getCreateChannel();						//设置创建渠道
		
		this.lastIP = usd.getLastLoginIP();									//设置最后登录IP
		
		this.lastTime = TimeFn.formatTimeToString(usd.getLastLoginTime());   //最后登录时间（含尝试）
		
		this.lastTryTime = TimeFn.formatTimeToString(usd.getLastLoginTime());  //最后尝试时间（同登录）
		
		this.loginFailureCount = usd.getLoginFailureCount();					//连续登录尝试次数
		
		this.lockedDate = TimeFn.formatTimeToString(usd.getLockDate());			//帐号锁定时间
		
		this.isAccountLocked = usd.getStatus() == 3 ? "0" : "1";               //帐号是否锁定    账户账务服务对应3锁定，本系统对应0锁定1正常
		
		this.userID = usd.getMemberCode().toString();											//设置用户ID（外键关联信息）
		
		this.memberType = usd.getMemberType();
		
		this.MemberCode = usd.getMemberCode();
		
		this.registerIP = usd.getRegisterIP();
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>该方法将分分通user对象转换成账户账务平台UserSpecDto对象</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-24 上午10:58:10 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserSpecDto toUserSpecDto(){
		
		UserSpecDto usd = new UserSpecDto();
		
		usd.setLoginID(this.username);
		
		usd.setLoginPwd(this.password);
		
		usd.setUname(this.uname);
		
		usd.setEmail(this.email);
		
		usd.setStatus(Assert.empty(this.status) ? null : Integer.parseInt(this.status));
		
		usd.setMobile(this.mobilephone);
		
		usd.setIdentityKey(this.identityKey);
		
		usd.setSex(this.sex);
		
		usd.setAge(this.age);
		
		usd.setUpdateTime(TimeFn.formatTimeToDate(this.updateTime));
		
		usd.setCreateTime(TimeFn.formatTimeToDate(this.createTime));
		
		usd.setCreateChannel(this.createChannel);
		
		usd.setLastLoginIP(this.lastIP);
		
		usd.setLastLoginTime(TimeFn.formatTimeToDate(this.lastTime));
		
		usd.setLoginFailureCount(this.loginFailureCount);
		
		usd.setLockDate(TimeFn.formatTimeToDate(this.lockedDate));
		
		//usd.setUserID(this.userID);
		
		usd.setMemberType(this.memberType);
		
		usd.setMemberCode(this.MemberCode);
		if(MemberCode == null && !Assert.empty(this.userID)){
			usd.setMemberCode(Long.parseLong(this.userID));
		}
		usd.setRegisterIP(this.registerIP);
		
		return usd;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>追加user分分通库的本地字段属性值</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-25 上午09:42:35 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public void userAppendData(UserAppend userAppend){
		
		if(userAppend == null){
			return;
		}
		
		this.userID = userAppend.getUserID();
		
		this.isPointActivateAccountLocked = userAppend.getIsPointActivateLock();
		
		this.pointActivateFailureCount = userAppend.getPointActivateFailureTimes();
		
		this.pointActivateLockedDate = userAppend.getPointActivateLockTime();
		
		this.lastPointActivateTime = userAppend.getPointActivateTryTime();
		
		this.activatecode = userAppend.getActivateCode();
		
		this.firstLogin = userAppend.getFirstLogin();
		
		
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>从user中获取追加字段</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-29 下午12:40:48 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserAppend toUserAppendData(){
		UserAppend ua = new UserAppend();
		ua.setUserID(this.MemberCode==null?null:this.MemberCode.toString());
		if(Assert.empty(ua.getUserID())){
			ua.setUserID(this.userID);
		}
		ua.setActivateCode(this.activatecode);
		ua.setBank(this.getBank());
		ua.setBlance(this.balance);
		ua.setFirstLogin(this.firstLogin);
		ua.setIsPointActivateLock(this.isPointActivateAccountLocked);
		ua.setPointActivateFailureTimes(this.pointActivateFailureCount);
		ua.setPointActivateLockTime(this.pointActivateLockedDate);
		ua.setPointActivateTryTime(this.lastPointActivateTime);
		return ua;
	}
	
	
	
	
	
	
	
	//======================================================================             setter   getter
	public UserCertification getUserCertification() {
		return userCertification;
	}
	public void setUserCertification(UserCertification userCertification) {
		this.userCertification = userCertification;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}	
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
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
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUser_authorities_id() {
		return user_authorities_id;
	}
	public void setUser_authorities_id(String user_authorities_id) {
		this.user_authorities_id = user_authorities_id;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getLastIP() {
		return lastIP;
	}
	public void setLastIP(String lastIP) {
		this.lastIP = lastIP;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getActivatecode() {
		return activatecode;
	}
	public void setActivatecode(String activatecode) {
		this.activatecode = activatecode;
	}	
	public String getActivateURL() {
		return activateURL;
	}
	public void setActivateURL(String activateURL) {
		this.activateURL = activateURL;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getSPpassword() {
		return SPpassword;
	}
	public void setSPpassword(String sPpassword) {
		SPpassword = sPpassword;
	}
	public String getActprojectID() {
		return actprojectID;
	}
	public void setActprojectID(String actprojectID) {
		this.actprojectID = actprojectID;
	}
	public String getJoinedActprojectID() {
		return joinedActprojectID;
	}
	public void setJoinedActprojectID(String joinedActprojectID) {
		this.joinedActprojectID = joinedActprojectID;
	}
	public String getIdentityKey() {
		return identityKey;
	}
	public void setIdentityKey(String identityKey) {
		this.identityKey = identityKey;
	}
	public Integer getUserrole() {
		return userrole;
	}
	public void setUserrole(Integer userrole) {
		this.userrole = userrole;
	}
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getAuthoritiesId() {
		return authoritiesId;
	}
	public void setAuthoritiesId(String authoritiesId) {
		this.authoritiesId = authoritiesId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getIsAccountLocked() {
		return isAccountLocked;
	}
	public void setIsAccountLocked(String isAccountLocked) {
		this.isAccountLocked = isAccountLocked;
	}
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}
	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}
	public String getLockedDate() {
		return lockedDate;
	}
	public void setLockedDate(String lockedDate) {
		this.lockedDate = lockedDate;
	}
	public String getLastTryTime() {
		return lastTryTime;
	}
	public void setLastTryTime(String lastTryTime) {
		this.lastTryTime = lastTryTime;
	}
	public String getIsPointActivateAccountLocked() {
		return isPointActivateAccountLocked;
	}
	public void setIsPointActivateAccountLocked(String isPointActivateAccountLocked) {
		this.isPointActivateAccountLocked = isPointActivateAccountLocked;
	}
	public Integer getPointActivateFailureCount() {
		return pointActivateFailureCount;
	}
	public void setPointActivateFailureCount(Integer pointActivateFailureCount) {
		this.pointActivateFailureCount = pointActivateFailureCount;
	}
	public String getPointActivateLockedDate() {
		return pointActivateLockedDate;
	}
	public void setPointActivateLockedDate(String pointActivateLockedDate) {
		this.pointActivateLockedDate = pointActivateLockedDate;
	}
	public String getLastPointActivateTime() {
		return lastPointActivateTime;
	}
	public void setLastPointActivateTime(String lastPointActivateTime) {
		this.lastPointActivateTime = lastPointActivateTime;
	}
	public String getFirstLogin() {
		return firstLogin;
	}
	public void setFirstLogin(String firstLogin) {
		this.firstLogin = firstLogin;
	}
	public String getCreateChannel() {
		return createChannel;
	}
	public void setCreateChannel(String createChannel) {
		this.createChannel = createChannel;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public Long getMemberCode() {
		return MemberCode;
	}
	public void setMemberCode(Long memberCode) {
		MemberCode = memberCode;
	}
	public String getRegisterIP() {
		return registerIP;
	}
	public void setRegisterIP(String registerIP) {
		this.registerIP = registerIP;
	}

	public Integer getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(Integer msgCode) {
		this.msgCode = msgCode;
	}
}
