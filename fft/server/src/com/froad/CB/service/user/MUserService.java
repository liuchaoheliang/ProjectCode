package com.froad.CB.service.user;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.Buyers;
import com.froad.CB.po.user.Authorities;
import com.froad.CB.po.user.LoginManager;
import com.froad.CB.po.user.Resources;
import com.froad.CB.po.user.User;

@WebService
public interface MUserService {

	/**
	 * <p>
	 * description:用户注册
	 * 
	 * @param user
	 * @return
	 *            <p>
	 *            author 勾君伟 goujunwei@f-road.com.cn
	 * @time:2011-3-11 下午03:05:24
	 * @version 1.0
	 * @throws SQLException
	 * @throws Exception
	 */
	public User register(User user) throws AppException;

	/**
	 * 方法描述：激活用户
	 * 
	 * @param: user（activatecode）
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-14 下午05:58:20
	 */
	public User addedUser(String username) throws AppException;

	/**
	 * <p>
	 * description:用户退出（暂不用）
	 * 
	 * @param user
	 * @return
	 *            <p>
	 *            author 勾君伟 goujunwei@f-road.com.cn
	 * @time:2011-3-11 下午03:04:35
	 * @version 1.0
	 */
	public LoginManager logout(LoginManager loginManager) throws AppException;

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
			throws AppException;

	/**
	 * <p>
	 * description:用户根据用户名和注册邮箱找回密码
	 * 
	 * @param loginManager
	 * @return
	 *            <p>
	 *            author 勾君伟 goujunwei@f-road.com.cn
	 * @time:2011-3-16 下午01:35:09
	 * @version 1.0
	 * @throws Exception
	 */
	public LoginManager initPassword(LoginManager loginManager)
			throws AppException;

	/**
	 * 方法描述：根据用户名更新用户密码
	 * 
	 * @param: username password
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-14 下午06:00:18
	 */
	public User updPassword(Long memberCode,String oldPwd,String newPwd) throws AppException;

	public User queryUserAllByUsername(String username) throws AppException;

	public User queryUserAllByUserID(String UserID) throws AppException;

	/**
	 * 方法描述：更新user表Ip地址和登录时间
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: Sep 27, 2011 3:47:43 PM
	 */
	public User updateUserLastInfoByUsername(User user) throws AppException;

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
	public List<Authorities> queryAuthoritiesByUsername(String username)
			throws AppException, SQLException;

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
	public List<Authorities> queryAuthorities() throws AppException,
			SQLException;

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
			throws AppException;

	/**
	 * 方法描述：根据用户名修改数据
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-5-9 下午06:03:00
	 */
	public User updUserInfo(User user) throws AppException;

	/**
	 * 方法描述：查询手机登陆逻辑卡号是否存在
	 * 
	 * @param: User（sn）
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-9 下午12:52:00
	 */
	public User selectSN(String sn) throws AppException;

	/**
	 * 方法描述：查询用户所有信息 根据手机号码 或邮箱 或 sn
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2012-3-13 下午1:31:18
	 */
	public User queryUserByMobilephoneOrMailOrSn(String value)
			throws AppException;
	/**
	 * 方法描述：查询用户所有信息 根据手机号码 或邮箱
	 * @param value
	 * @return
	 * @throws AppException
	 */
	public User queryUserByMobilephoneOrMail(String value) throws AppException;
	
	/**
	 * 方法描述：查询用户所有信息 根据手机号码 或用户名
	 * @param value
	 * @return
	 * @throws AppException
	 */
	public User queryUserByMobilephoneOrUsername(String value) throws AppException;
	
	/**
	 * 方法描述：查询用户所有信息 根据用户mobilephone
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2012-3-13 下午2:48:43
	 */
	public User selectUserMobilephone(String mobilephone) throws AppException;

	/**
	 * 
	 * 方法描述：通过传递参数查询用户
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: guoming guoming@f-road.com.cn
	 * @time: 2012-8-23 下午03:11:44
	 */
	public List<User> queryUserByIdentityKey(String loginkey)
			throws AppException;

	/**
	 * 方法描述：查询用户所有信息 根据用户email
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2012-3-13 下午2:48:43
	 */
	public User selectUserEmail(String email);
	
	
	
	/**
	  * 方法描述：分页多条件查询用户信息
	  * @param: user{username,uname,authoritiesId,status,pageSize,startRow}
	  * @return: User
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 26, 2012 4:22:59 PM
	  */
	public User getUserByPager(User user);
	
	/**
	  * 方法描述：查询用户信息
	  * @param: user{username,uname,authoritiesId,status,pageSize,startRow}
	  * @return: pager
	  * @version: 1.0
	  */
	public List<User> getUsers(User user);
	
	/**
	 * 手机客户端注册用户
	 * @param userRes    mobilephone:13888888888  password:123456
	 * @return
	 */
	public User clientRegister(User userRes,boolean flag);
	
	/**
	 * 商户注册用户
	 * @param userRes    mobilephone:13888888888  password:123456
	 * @return
	 */
	public User autoAddMerchantUser(User user,boolean flag);
	
	/**
	  * 方法描述：绑定买家
	  * @param: phone 用户输入的手机号
	  * @return: Buyers
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 6, 2013 6:10:59 PM
	  */
	public Buyers bindingBuyer(String phone,String createChannel);
	
	public boolean updateChangeMobilePhone(String mobilePhone,String userId) throws AppException;
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过membercode更改相关信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-25 上午11:35:39 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public boolean updateUserByMemberCode(User user);
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>调用账户账务服务平台登录</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-26 下午05:14:37 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public User loginPassAccountSys(String loginID,String loginPWD,String loginIP);
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>修改用户追加信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-29 下午12:33:41 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public boolean updateUserAppendInfo(User user);
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>找回密码</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-10-8 上午10:34:30 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public User forgetPwdToGetIt(Long memberCode);

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>添加其他平台登录fft系统所需要的追加信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-12-25 下午12:03:12 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public boolean insertUserAppendInfo(User user);
}
