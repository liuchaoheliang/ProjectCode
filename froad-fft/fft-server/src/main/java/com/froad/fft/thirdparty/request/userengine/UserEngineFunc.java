package com.froad.fft.thirdparty.request.userengine;

import com.pay.user.dto.UserResult;
import com.pay.user.dto.UserSpecDto;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: UserEngineFunc.java </p>
 *<p> 描述: *-- <b>对接账户账务服务平台</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年1月24日 下午1:36:43 </p>
 ********************************************************
 */
public interface UserEngineFunc {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>用户注册</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:44:01 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult registerMember(UserSpecDto userSpecDto);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过MemberCode更新User</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:51:07 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult updateMemberInfoByMemberCode(UserSpecDto userSpecDto);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过MemberCode查询用户信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:52:12 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult queryByMemberCode(Long memberCode);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过登录帐号获取用户信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午3:04:23 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult queryByLoginID(String loginID);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>用户登录</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:52:56 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult login(String loginID,String loginPWD,String loginIP);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过memberCode找回密码</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:53:26 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult forgetPwd(Long memberCode);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>更改密码</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:54:11 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult updatePwd(Long memberCode,String oldPwd,String newPwd);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>绑定手机号码</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月24日 下午2:54:48 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult bindMobile(Long memberCode,String mobile);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>修改用户信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月2日 下午12:47:11 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserResult updateMemberInfo(UserSpecDto userSpecDto);
	
	/**
	*<p>重置用户密码</p>
	* @author larry
	* @datetime 2014-4-11下午03:06:45
	* @return UserEngineDto
	* @throws 
	* @example<br/>
	*
	 */
	public UserResult resetUserPwd(Long memCode, String password);
}
