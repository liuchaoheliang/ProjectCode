package com.froad.logic;

import java.util.List;

import com.froad.db.mongo.bean.BankUserResource;
import com.froad.po.BankResource;

/**
 * 
 * <p>@Title: BankRoleResourceLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface BankRoleResourceLogic {


    /**
     * 查询 BankResource资源列表
     * @param clientId
     * @param roleId 
     * @return List<BankResource>    结果集合 
     */
	public List<BankResource> findBankRoleResource(String clientId,Long roleId);

	
	 /**
     * 查询 全部BankResource角色资源列表
     * @return List<BankResource>    结果集合 
     */
	public List<BankUserResource> findBankRoleResourceAll();
	

}