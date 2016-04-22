package com.froad.CB.dao.user;

import com.froad.CB.po.user.LoginManager;

public interface LoginManagerDao {
	/**
	 * 方法描述：查询sessionid是否存在
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-14 下午05:54:03
	 */
	public LoginManager selectSessionID(String sessionid);

	/**
	 * 方法描述：创建seddionid
	 * 
	 * @param: LoginManager（sessionID,userID,username,uname）
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-14 下午05:54:06
	 */
	public LoginManager createSession(LoginManager lm);

	/**
	 * 方法描述：查询用户id根据sessionID
	 * 
	 * @param: sessionID
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-14 下午05:54:08
	 */
	public LoginManager selectUserIDBYsessionID(LoginManager lm);

	/**
	 * 方法描述：删除sessionID
	 * 
	 * @param: sessionID
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-14 下午05:54:10
	 */
	public LoginManager delSessionID(String SessionID);

	/**
	 * 方法描述：查询用户信息根据sessionID
	 * 
	 * @param: sessionID
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-14 下午05:54:13
	 */
	public LoginManager selectUserInfoBYsessionID(String SessionID);

	/**
	 * 方法描述：查询用户登录信息根据sessionID
	 * 
	 * @param: sessionID
	 * @return:
	 * @version: 1.0
	 * @author: 何庆均 heqingjun@f-road.com.cn
	 * @update:
	 * @time: 2011-8-14 下午05:54:16
	 */
	public LoginManager selectUserLoginInfoBYsessionID(String sessionID);
}
