package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.Merchant;

/**
 * 
 * 商户操作
 * 
 * @author: lf 2015.03.22
 * @modify: lf 2015.03.22
 * 
 * */
public interface MerchantMapper {

	/**
	 * 
	 * 查询过期商户
	 * 
	 * @author: lf 2015.03.22
	 * @modify: lf 2015.03.22
	 * 
	 * */
	public List<Merchant> selectByExpired();
	
	/**
	 * 
	 * 修改商户状态为无效
	 * 
	 * @author: lf 2015.03.22
	 * @modify: lf 2015.03.22
	 * 
	 * */
	public boolean updateNotEnableById(long id);
	
	/**
	 * 
	 * 查询可以续约的商户
	 * 
	 * @author: lf 2015.05.02
	 * @modify: lf 2015.05.02
	 * 
	 * */
	public List<Merchant> selectCanRenewal();
	/**
	 * 
	 * 查询可以续约的商户
	 * 
	 * @author: liangYaolong 2015.09.14
	 * @modify: liangYaolong 2015.09.14
	 * 
	 * */
	public boolean updateMerchantAuditState(Merchant merchant);
	/**
	 * 根据merchanId查找商户
	 * @Title: selectMerchantById 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年9月15日
	 * @modify: Yaolong Liang 2015年9月15日
	 * @param id
	 * @return
	 * @return Merchant
	 * @throws
	 */
	public Merchant selectMerchantById(@Param("merchantId") String merchantId,@Param("clientId") String clientId);
}
