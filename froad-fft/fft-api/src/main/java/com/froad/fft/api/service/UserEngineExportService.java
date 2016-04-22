package com.froad.fft.api.service;


import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;


/**
 * *******************************************************
 *<p> 工程: fft-api </p>
 *<p> 类名: UserEngineService.java </p>
 *<p> 描述: *-- <b>调用账户账务服务平台的相关接口</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年3月28日 下午4:13:11 </p>
 ********************************************************
 */
public interface UserEngineExportService {

 
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>注册用户 </b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月28日 下午5:00:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto registerUser(ClientAccessType clientAccessType, ClientVersion clientVersion,UserEngineDto userEngineDto)throws FroadException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>用户登录</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月31日 下午5:26:38 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto login(ClientAccessType clientAccessType, ClientVersion clientVersion,String loginID,String loginPWD,String loginIP)throws FroadException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过帐号查询用户信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月1日 上午9:33:29 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto queryByLoginID(ClientAccessType clientAccessType, ClientVersion clientVersion,String loginID)throws FroadException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过原密码修改新密码</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月1日 下午3:21:45 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto updatePwd(ClientAccessType clientAccessType, ClientVersion clientVersion,Long memberCode,String oldPwd,String newPwd)throws FroadException;

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>修改用户信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月2日 下午12:50:39 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto updateMemberInfo(ClientAccessType clientAccessType, ClientVersion clientVersion,UserEngineDto userEngineDto)throws FroadException;

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过用户帐号重新获取用户信息（用于立刻更新用户积分信息）</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月3日 下午4:40:51 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto updateUserPoints(ClientAccessType clientAccessType, ClientVersion clientVersion,String loginID);

	/**
	*<p>重置用户密码</p>
	* @author larry
	* @datetime 2014-4-11下午03:06:45
	* @return UserEngineDto
	* @throws 
	* @example<br/>
	*
	 */
	public UserEngineDto resetUserPwd(ClientAccessType clientAccessType,ClientVersion clientVersion, Long memCode, String password);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>修改用户手机号</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月14日 下午12:32:19 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public UserEngineDto updateUserMobile(ClientAccessType clientAccessType,ClientVersion clientVersion, Long memCode, String mobile);

}
