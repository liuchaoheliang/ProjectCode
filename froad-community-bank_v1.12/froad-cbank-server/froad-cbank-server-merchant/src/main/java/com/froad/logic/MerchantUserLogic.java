/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: MerchantUserLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.MerchantUser;
import com.froad.po.MerchantUserAddRes;
import com.froad.po.MerchantUserCheckRes;
import com.froad.po.MerchantUserLoginRes;
import com.froad.po.Result;
import com.froad.thrift.vo.OriginVo;

/**
 * 
 * <p>@Title: MerchantUserLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantUserLogic {


    /**
     * 增加 MerchantUser
     * @param merchantUser
     * @return MerchantUserAddRes
     */
	public MerchantUserAddRes addMerchantUser(MerchantUser merchantUser)throws FroadServerException,Exception;



    /**
     * 删除 MerchantUser
     * @param merchantUser
     * @return Result
     */
	public Result deleteMerchantUser(MerchantUser merchantUser)throws FroadServerException,Exception;



    /**
     * 修改 MerchantUser
     * @param merchantUser
     * @return Result
     */
	public Result updateMerchantUser(MerchantUser merchantUser)throws FroadServerException,Exception;

	
	
	/**
     * 查询 MerchantUser
     * @param id
     * @return MerchantUser
     * 
     * @param id
     */
	public MerchantUser getMerchantUserById(long id);
	


    /**
     * 查询 MerchantUser
     * @param merchantUser
     * @return List<MerchantUser>    结果集合 
     */
	public List<MerchantUser> findMerchantUser(MerchantUser merchantUser);



    /**
     * 分页查询 MerchantUser
     * @param page
     * @param merchantUser
     * @return Page<MerchantUser>    结果集合 
     */
	public Page<MerchantUser> findMerchantUserByPage(Page<MerchantUser> page, MerchantUser merchantUser);


	/**
	 * 登录login
	 * @param username
	 * @param password
	 * @param operatorIp
	 * @return MerchantUserLoginRes
	 * */
	public MerchantUserLoginRes login(String username, String password, OriginVo originVo);


	/**
     * 用户登出
     * @param token
     * @return boolean
     * 
     * @param token
     */
    public Boolean logout(String token);
	
	/**
     * 校验 token
     * @param token值
     * @param userId
     * @return MerchantUserCheckRes
     */
	public MerchantUserCheckRes tokenCheck(String token, long userId);
	
	/**
     * 查询 MerchantUserList
     * @return List<MerchantUserVo>
     * 
     * @param roleId
     * @param merchantIdList
     */
	public List<MerchantUser> getMerchantUserByRoleIdAndMerchantIds(
			long roleId, List<String> merchantIdList);
	
	/**
     * 查询 MerchantUser
     * @param username
     * @return MerchantUser
     * 
     * @param username
     */
	public MerchantUser getMerchantUserByUsername(String username,com.froad.thrift.vo.OriginVo originVo);
	
	/**
	 * 查询登录错误次数
	  * @Title: findLoginErrorCount
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-6-15
	  * @modify: zhangxiaohua 2015-6-15
	  * @param @param clientId
	  * @param @param merchantId
	  * @param @param userId
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public int findLoginErrorCount(String clientId, String merchantId, long userId) throws Exception;
	
	/**
	 * 通过用户名和客户端id获取用户登录失败次数.
	 * @param originVo
	 * @param username
	 * @return
	 */
	public MerchantUserLoginRes getLoginFailureCount(OriginVo originVo,String username);
}