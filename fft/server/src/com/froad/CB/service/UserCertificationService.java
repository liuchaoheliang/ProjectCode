package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.po.UserCertification;

@WebService
public interface UserCertificationService {

	
	/**
	  * 方法描述：添加认证信息
	  * @param: UserCertification(userId,certificationType,channelId[phone|(accountNo,accountName)])
	  * @param: certificationType为00时,phone为必填  certificationType为01时，accountNo,accountName为必填
	  * @return: Integer 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 5, 2013 5:41:56 PM
	  */
	public Integer add(UserCertification cert)throws AppException;
	
	
	/**
	  * 方法描述：验证手机号或(账户号和账户名)是否有效(体现认证)
	  * @param: UserCertification(certificationType,accountNo,accountName,phone)
	  * @param: certificationType为00时,phone为必填  certificationType为01时，accountNo,accountName为必填
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 22, 2013 6:03:49 PM
	  */
	public boolean checkAccount(UserCertification cert)throws AppException;
	
	
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
	  * 方法描述：积分认证成功后添加认证信息（如果已经存在认证成功的记录就更新）
	  * @param: UserCertification(userId,certificationResult,certificationType,[phone|(accountNo,accountName)])
	  * @return: Integer 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 5, 2013 5:41:56 PM
	  */
	public Integer addOrUpdateBindingNew(UserCertification cert)throws AppException;
}
