package com.froad.CB.dao;

import java.util.List;

import com.froad.CB.po.UserCertification;

public interface UserCertificationDao {
	

	
	/**
	  * 方法描述：添加认证信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 5, 2013 5:41:56 PM
	  */
	public Integer add(UserCertification cert);
	
	
	/**
	  * 方法描述：查询用户认证信息
	  * @param: userId
	  * @return: UserCertification
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 5, 2013 5:30:36 PM
	  */
	public UserCertification getUserCertByUserId(String userId);
	
	
	/**
	  * 方法描述：多条件查询用户认证信息
	  * @param: UserCertification
	  * @return: List<UserCertification>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 11, 2013 9:19:05 PM
	  */
	public List<UserCertification> getUserCertBySelective(UserCertification cert);
	
	/**
	  * 方法描述：更新用户认证信息
	  * @param: UserCertification
	  * @return: Integer
	  * @version: 1.0
	  * @author: 李巧鹏 lijinkui@f-road.com.cn
	  * @time: Mar 11, 2013 9:19:05 PM
	  */
	public Integer updateUserCertByUserId(UserCertification cert);
}
