package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadBusinessException;
import com.froad.exceptions.FroadDBException;
import com.froad.exceptions.FroadServerException;
import com.froad.logic.res.LoginBankOperatorRes;
import com.froad.logic.res.OrgUserRoleCheckRes;
import com.froad.po.BankOperator;
import com.froad.po.OrgUserRole;

/**
 * 
 * <p>@Title: OrgUserRoleLogic.java</p>
 * <p>Description: 描述 </p> 银行联合登录账号Logic接口类
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OrgUserRoleLogic {


    /**
     * 增加 OrgUserRole
     * @param orgUserRole
     * @return Long    主键ID
     */
	public Long addOrgUserRole(OrgUserRole orgUserRole, BankOperator bankOperator) throws FroadServerException, Exception;



    /**
     * 删除 OrgUserRole
     * @param orgUserRole
     * @return Boolean    是否成功
     */
	public Boolean deleteOrgUserRole(OrgUserRole orgUserRole) throws FroadServerException, Exception;



    /**
     * 修改 OrgUserRole
     * @param orgUserRole
     * @return Boolean    是否成功
     */
	public Boolean updateOrgUserRole(OrgUserRole orgUserRole) throws FroadServerException, Exception;


	/**
     * 查询 OrgUserRole
     * @param orgUserRole
     * @return role_id角色id
     */
	public Long findOrgUserRoleByRedis(String clientId,String orgCode,String username);
	

    /**
     * 查询 OrgUserRole
     * @param orgUserRole
     * @return List<OrgUserRole>    结果集合 
     */
	public List<OrgUserRole> findOrgUserRole(OrgUserRole orgUserRole);



    /**
     * 分页查询 OrgUserRole
     * @param page
     * @param orgUserRole
     * @return Page    结果集合 
     */
	public Page<OrgUserRole> findOrgUserRoleByPage(Page<OrgUserRole> page, OrgUserRole orgUserRole);


	
	/**
    * 银行联合登录
    * @param clientId 客户端Id
    * @param username 登录名
    * @param password 登录密码
    * @param operatorIp 登录ip
    * @return 返回LoginOrgUserRoleRes对象，包含token和联合登录帐号对象
	* @throws FroadDBException 
	* @throws FroadBusinessException 
    */
	public LoginBankOperatorRes loginOrgUserRole(String clientId, String username,String password,String operatorIp)  throws FroadServerException, Exception;
	
	
	/**
    * token校验
    * @param orgCode 机构编号
    * @param username 登录名
    * @param token
    * @return token和orgCode/username值是否匹配
    */
	public OrgUserRoleCheckRes checkToken(String clientId,String orgCode, String username,String token) throws FroadServerException, Exception;
		
		
	/**
    * 银行联合登录
    * @param clientId 客户端Id
    * @param username 登录名
    * @return 返回LoginOrgUserRoleRes对象，包含token和联合登录帐号对象
	* @throws FroadDBException 
	* @throws FroadBusinessException 
    */
	public LoginBankOperatorRes getLoginFailureCount(String clientId, String username)  throws FroadServerException, Exception;
		
		
}