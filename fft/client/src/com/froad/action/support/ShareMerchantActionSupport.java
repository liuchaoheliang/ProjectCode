package com.froad.action.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.shareMerchant.ShareMerchant;
import com.froad.client.shareMerchant.ShareMerchantService;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-20  
 * @version 1.0
 * 分享商户 client service  ActionSupport
 */
public class ShareMerchantActionSupport {
	private static Logger logger = Logger.getLogger(ShareMerchantActionSupport.class);
	private ShareMerchantService shareMerchantService;
	
	/**
	 * 增加 分享商户
	 * @param shareMerchant
	 * @return
	 */
	public boolean addShareMerchant(ShareMerchant shareMerchant){
		boolean isSuccess=false;
		try{
			shareMerchantService.addShareMerchant(shareMerchant);
			isSuccess=true;
		}
		catch(Exception e){
			logger.error("增加 分享商户异常", e);
		}
		return isSuccess;
	}
	
	/**
	 * USERID 查询分享商户
	 * @param userId
	 * @return
	 */
	public List<ShareMerchant> getShareMerchantListByUserId(String userId){
		List<ShareMerchant> shareMerchantList=new ArrayList<ShareMerchant>();
		try{
			shareMerchantList=shareMerchantService.getShareMerchantByUserId(userId);
		}
		catch(Exception e){
			logger.error("userid 查询分享商户异常", e);
		}
		return shareMerchantList;
	}
	
	public ShareMerchantService getShareMerchantService() {
		return shareMerchantService;
	}
	public void setShareMerchantService(ShareMerchantService shareMerchantService) {
		this.shareMerchantService = shareMerchantService;
	}
	
}
