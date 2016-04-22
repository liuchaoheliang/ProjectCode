package com.froad.fft.support.base;

import com.froad.fft.dto.UserEngineDto;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: UserEngineSupport.java </p>
 *<p> 描述: *-- <b>用户系统相关服务</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年3月29日 下午4:26:50 </p>
 ********************************************************
 */
public interface UserEngineSupport {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>进行注册用户操作</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月29日 下午4:27:57 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto registerUser(UserEngineDto userEngineDto);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>用户登录</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月31日 下午5:49:30 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto login(UserEngineDto userEngineDto,String loginIP);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>修改用户密码</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月1日 下午3:30:03 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto updatePwd(Long memberCode,String oldPwd,String newPwd);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>修改用户信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月2日 下午12:53:12 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto updateMemberInfo(UserEngineDto userEngineDto);
	
	/**
	  * 方法描述：根据用户名查找用户信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月2日 下午5:07:43
	  */
	public UserEngineDto queryByLoginId(String LoginId);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过用户帐号重新获取用户信息（用于立刻更新用户积分信息）</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月3日 下午4:39:29 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto updateUserPoints(String loginID);
	
	
	/**
	*<p>重置密码</p>
	* @author larry
	* @datetime 2014-4-11下午03:05:56
	* @return UserEngineDto
	* @throws 
	* @example<br/>
	*
	 */
	public UserEngineDto resetUserPwd(Long memCode,String password);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>重新绑定用户信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月14日 下午1:07:34 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto updateUserMobile(Long memCode, String mobile);
	
}
