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
 * @Title: MerchantAccountLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.MerchantAccount;
import com.froad.po.Result;

/**
 * 
 * <p>@Title: MerchantAccountLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantAccountLogic {


    /**
     * 增加 MerchantAccount
     * @param merchantAccount
     * @return Long    主键ID
     */
	public Long addMerchantAccount(MerchantAccount merchantAccount)throws FroadServerException,Exception;



    /**
     * 删除 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	public Boolean deleteMerchantAccount(MerchantAccount merchantAccount)throws FroadServerException,Exception;

	/**
     * 物理删除 MerchantAccount
     * @param id
     * @return Boolean    是否成功
     */
	public Boolean removeMerchantAccount(Long id)throws FroadServerException,Exception ;

    /**
     * 修改 MerchantAccount
     * @param merchantAccount
     * @return Boolean    是否成功
     */
	public Boolean updateMerchantAccount(MerchantAccount merchantAccount)throws FroadServerException,Exception;


	/**
	 * 
	 * @Title: findMerchantAccountById 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param id
	 * @return
	 * @return MerchantAccount    返回类型 
	 * @throws
	 */
	public MerchantAccount findMerchantAccountById(Long id);
	
	/**
	 * 
	 * @Title: findMerchantAccountByOutletId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletId
	 * @return
	 * @return MerchantAccount    返回类型 
	 * @throws
	 */
	public MerchantAccount findMerchantAccountByOutletId(String outletId);

	
	/**
	 * 获取商户账号信息
	 * @Title: findMerchantAccountByMerchantId 
	 * @author ll
	 * @version 1.5
	 * @see: TODO
	 * @param clientId
	 * @param merchantId 商户Id
	 * @param outletId 门店Id
	 * @return MerchantAccount    返回类型 
	 * @throws
	 */
	public MerchantAccount findMerchantAccountByMerchantId(String clientId,String merchantId,String outletId);

	
	
    /**
     * 支付多银行客户端和boss查询
     * 查询 MerchantAccount
     * @param merchantAccount
     * @return List<MerchantAccount>    结果集合 
     */
	public List<MerchantAccount> findMerchantAccount(MerchantAccount merchantAccount);



    /**
     * 支付多银行客户端和boss查询
     * 分页查询 MerchantAccount
     * @param page
     * @param merchantAccount
     * @return Page<MerchantAccount>    结果集合 
     */
	public Page<MerchantAccount> findMerchantAccountByPage(Page<MerchantAccount> page, MerchantAccount merchantAccount);

	/**     
	 * 注册银行白名单
	 * @param account 账户对象  要求提供：client_id,merchant_id/outlet_id,acct_number,acct_name
	 * @param optionType 行方操作类型:0-新增,1-修改,2-删除
	 * @param moduleType 模块类型:0-商户,1-门店
	 * @return Result
	 */	
	public Result registBankWhiteList(MerchantAccount account,String optionType,String moduleType) throws FroadServerException,Exception;
	
	/**
	 * 白名单账户mac值计算
	 * @param clientId 客户编号
	 * @param acctNo 账号
	 * @param acctName 户名
	 * @return 返回mac
	 */
	public String findMerchantAccountMac(String clientId,String acctNo,String acctName);

}