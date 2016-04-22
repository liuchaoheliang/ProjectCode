package com.froad.action.support.merchantUserSet;

import java.util.List;
import org.apache.log4j.Logger;
import com.froad.client.merchantUserSeller.MerchantUserSeller;
import com.froad.client.merchantUserSeller.MerchantUserSellerService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-6-3  
 * @version 1.0
 */
public class MerchantUserSellerActionSupport {
	private static Logger logger = Logger.getLogger(MerchantUserSellerActionSupport.class);
	private MerchantUserSellerService merchantUserSellerService;
	
	/**
	 * 添加操作员卖家
	 * @return boolean
	 */
	public boolean addMerchantUserSeller(MerchantUserSeller merchantUserSeller){
		Integer num = null;
		try {
			num = merchantUserSellerService.addMerchantUserSeller(merchantUserSeller);
		} catch (Exception e) {
			logger.error("MerchantUserSellerActionSupport添加操作员  id:"+merchantUserSeller.getId()+
					" merchantId:"+merchantUserSeller.getMerchantId()+" UserSetId:"+merchantUserSeller.getMerchantUserId()+" sellerId："+merchantUserSeller.getSellerId(), e);
		}
		if(num != null && num >0 ){
			return true;
		}else{
			return false;			
		}
	}
	
	/**
	 * 查询商户的所有操作员信息
	 * 商户名，工号
	 * @return List<MerchantUserSet>
	 */
	public List<MerchantUserSeller> getMerchantUserSellerList(MerchantUserSeller merchantUserSeller){
		List<MerchantUserSeller> list = null;
		try {
			list = merchantUserSellerService.getMerchantUserSellers(merchantUserSeller);
		} catch (Exception e) {
			logger.error("MerchantUserSellerActionSupport.getMerchantUserSellerList查询商户操作员卖家信息出错 id:"+merchantUserSeller.getId()+
					" merchantId:"+merchantUserSeller.getMerchantId()+" UserSetId:"+merchantUserSeller.getMerchantUserId()+" sellerId："+merchantUserSeller.getSellerId(), e);
		}
		return list;
	}
	
	/**
	 * 删除操作员卖家
	 * @return boolean
	 */
	public boolean deleteMerchantUserSeller(MerchantUserSeller merchantUserSeller){
		Integer num = 0;
		try {
			num = merchantUserSellerService.deleteByMerchantSeller(merchantUserSeller);
		} catch (Exception e) {
			logger.info("MerchantUserSellerActionSupport.deleteMerchantUserSeller删除操作员卖家失败 id:"+merchantUserSeller.getId()+
					" merchantId:"+merchantUserSeller.getMerchantId()+" UserSetId:"+merchantUserSeller.getMerchantUserId()+" sellerId："+merchantUserSeller.getSellerId(), e);
		}
		if(num != null && num >0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 更新操作员卖家
	 * @return boolean
	 */
	public boolean updateMerchantUserSeller(MerchantUserSeller merchantUserSeller){
		Integer num = 0;
		try {
			num = merchantUserSellerService.updateByMerchantSeller(merchantUserSeller);
		} catch (Exception e) {
			logger.info("MerchantUserSellerActionSupport.updateMerchantUserSeller更新操作员卖家失败 id:"+merchantUserSeller.getId()+
					" merchantId:"+merchantUserSeller.getMerchantId()+" UserSetId:"+merchantUserSeller.getMerchantUserId()+" sellerId："+merchantUserSeller.getSellerId(), e);
		}
		if(num != null && num >0){
			return true;
		}else{
			return false;
		}
	}
	
	public MerchantUserSellerService getMerchantUserSellerService() {
		return merchantUserSellerService;
	}
	public void setMerchantUserSellerService(
			MerchantUserSellerService merchantUserSellerService) {
		this.merchantUserSellerService = merchantUserSellerService;
	}
}
