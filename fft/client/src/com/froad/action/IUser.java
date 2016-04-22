package com.froad.action;

import com.froad.client.user.User;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-20  
 * @version 1.0
 */
public interface IUser {
	public void setLoginUser(User loginUser);
	
	public User getLoginUser();
}
