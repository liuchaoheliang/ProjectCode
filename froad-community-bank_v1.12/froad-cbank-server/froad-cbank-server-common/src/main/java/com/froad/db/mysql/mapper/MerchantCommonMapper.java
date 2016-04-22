package com.froad.db.mysql.mapper;

import java.util.List;

import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.Outlet;
import com.froad.po.ProductOutletInfo;

/**
 * 
 * <p>@Title: MerchantMapper.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月10日
 */
public interface MerchantCommonMapper {


	/**
	 * 根据商户id查询Merchant对象
	 * @param merchantId
	 * @return
	 */
	public Merchant findMerchantByMerchantId(String merchantId);

	
	/**
	 * 根据商户id查询Merchant对象
	 * @param merchantId
	 * @return
	 */
	public List<Merchant> findMerchant(Merchant merchant);
	
	/**
	 * 
	 * findBankOutletsByClientId:(查询客户端的所有虚拟门店).
	 *
	 * @author wangyan
	 * 2015-8-13 下午1:48:22
	 * @param clientId
	 * @return
	 *
	 */
	public List<ProductOutletInfo> findBankOutletsByClientId(String clientId);

	
	  /**
     * 查询 MerchantAccount.
     * @param merchantAccount
     * @return List<MerchantAccount>    返回结果集
     */
	public List<MerchantAccount> findMerchantAccount(MerchantAccount merchantAccount);

		
	/**
     * 查询 Outlet
     * @param outlet
     * @return List<Outlet>    返回结果集.
     */
	public List<Outlet> findOutlet(Outlet outlet);		
	
	/**
	 * 通过门店ID获取门店对象信息.
	 */           
	public Outlet findOutletByOutletId(String outletId);	
}