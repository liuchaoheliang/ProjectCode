package com.froad.CB.dao.user;

import java.util.List;

import com.froad.CB.po.user.Authorities;
import com.froad.CB.po.user.Resources;
import com.froad.CB.po.user.User;
import com.froad.CB.po.user.UserAppend;

public interface UserAppendDao {
	
	
	/**
	  * 方法描述：根据UserId查询UserAppend
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-9-24 下午02:05:20
	  */
	public UserAppend getUserAppendByUserId(String userId);
	
	
	/**
	  * 方法描述：添加UserAppend
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-9-24 下午02:05:46
	  */
	public Integer addUserAppend(UserAppend userAppend);
	
	
	/**
	  * 方法描述：根据userAppend的userId属性更新UserAppend
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2013-9-24 下午02:06:21
	  */
	public boolean updateUserAppend(UserAppend userAppend);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取权限角色表最大id</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-27 下午03:20:31 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public Integer queryMaxRoleID();
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>添加角色</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-27 下午03:29:56 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public void addrole(User user);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取用户权限</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-27 下午04:56:26 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public List<Authorities> queryAuthoritiesByUserID(String username);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取所有权限</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-27 下午05:33:36 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public List<Authorities> queryAuthorities();
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>根据用户权限获取资源信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-9-27 下午05:47:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public List<Resources> queryResourcesByAuthorities(String auth);
}
