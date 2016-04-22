package com.froad.CB.dao.user;

import java.sql.SQLException;
import java.util.List;

import com.froad.CB.po.user.Authorities;
import com.froad.CB.po.user.LoginManager;
import com.froad.CB.po.user.Resources;
import com.froad.CB.po.user.User;
import com.pay.user.dto.UserResult;

public interface UserDao {
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
	public User register(User user);

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
	public User addedUser(String username);

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
	public LoginManager logout(LoginManager loginManager);

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
	public LoginManager receiveActivatecode(LoginManager loginManager);

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
	public LoginManager initPassword(LoginManager loginManager);

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
	public User updPassword(Long memberCode,String oldPwd,String newPwd);
	
	
	/**
	  * 方法描述：修改用户余额
	  * @param: User
	  * @return: int
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 24, 2013 11:40:22 AM
	  */
	public int updateBalanceByUsername(User user);

	public User queryUserAllByUsername(String username);

	public User queryUserAllByUserID(String UserID);

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
	public User updateUserLastInfoByUsername(User user);
	
	
	/**
	  * 方法描述：查询会员信息
	  * @param: username 用户名
	  * @param: spPassword sp的密码
	  * @return: User
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 24, 2013 11:33:49 AM
	  */
	public User queryUserByUsernameAndSPpassword(String username,String spPassword);
	

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
	public List<Authorities> queryAuthoritiesByUsername(String username);

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
	public List<Authorities> queryAuthorities();

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
	public List<Resources> queryResourcesByAuthorities(String auth);

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
	public User updUserInfo(User user);

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
	public User selectSN(String sn);

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
	public User queryUserByMobilephoneOrMailOrSn(String value);

	/**
	 * 方法描述：查询用户所有信息 根据手机号码 或邮箱
	 * @param value
	 * @return
	 */
	public User queryUserByMobilephoneOrMail(String value);
	
	/**
	 * 方法描述：查询用户所有信息 根据手机号码 或用户名
	 * @param value
	 * @return
	 */
	public User queryUserByMobilephoneOrUsername(String value);
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
	public User selectUserMobilephone(String mobilephone);

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
	public List<User> queryUserByIdentityKey(String loginkey);

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
	  * 方法描述：分页查询用户信息
	  * @param: User
	  * @return: User
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 6:11:49 PM
	  */
	public User getUserByPager(User user);
	
	
	/**
	  * 方法描述：查询指定用户编号的用户信息
	  * @param: userIdList
	  * @return: List<User>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 4, 2013 6:13:00 PM
	  */
	public List<User> getUsersByIdList(List<String> userIdList);
	
	/**
	  * 方法描述：按条件查询用户信息
	  * @param: User
	  * @return: List<User>
	  * @version: 1.0
	  */
	public List<User> getUsers(User user);
	
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过membercode更新user</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-25 上午11:37:12 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult updeatUserByMemberCode(User user);
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>用户登录</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-26 下午05:02:41 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public User userLogin(String loginID,String loginPWD,String loginIP);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>修改用户追加信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-29 下午12:34:24 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public boolean updateUserAppendInfo(User user);
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>找回密码</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-10-8 上午10:36:35 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public User forgetPwdToGetIt(Long memberCode);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>绑定手机号码</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-10-8 下午03:53:49 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult bingPhone(Long memberCode,String phoneNum) throws Exception;
	
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
