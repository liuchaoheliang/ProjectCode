package com.froad.action.support;

import java.util.List;

import org.apache.log4j.Logger;

import com.froad.client.merchantOnlineApply.MerchantOnlineApply;
import com.froad.client.merchantOnlineApply.MerchantOnlineApplyService;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-19  
 * @version 1.0
 * 商户在线申请  client service  ActionSupport
 */
public class MerchantOnlineApplyActionSupport {
	private static Logger logger = Logger.getLogger(MerchantOnlineApplyActionSupport.class);
	private MerchantOnlineApplyService merchantOnlineApplyService;
	
	public MerchantOnlineApply addMerchantOnlineApply(MerchantOnlineApply merchantOnlineApply) {
		MerchantOnlineApply merchantOnlineApplyRes = null;
		Integer id = null;
		try {
			id = merchantOnlineApplyService.add(merchantOnlineApply);
		} catch (Exception e) {
			logger.error("MerchantOnlineApplyActionSupport.addMerchantOnlineApply.add增加申请记录出错", e);
		}
		try {
			if(!Assert.empty(id)){
				merchantOnlineApplyRes = merchantOnlineApplyService.getById(id);				
			}
		} catch (Exception e) {
			logger.error("MerchantOnlineApplyActionSupport.addMerchantOnlineApply.getById查询申请记录出错", e);
		}
		return merchantOnlineApplyRes;
	}
	
	public List<MerchantOnlineApply> getMerchantOnlineApply(MerchantOnlineApply merchantOnlineApply) {
		List<MerchantOnlineApply> merchantOnlineApplyList = null;
		try {
			merchantOnlineApplyList = merchantOnlineApplyService.getByUserId(merchantOnlineApply.getUserId());
		} catch (Exception e) {
			logger.error("MerchantOnlineApplyActionSupport.addMerchantOnlineApply.add增加申请记录出错", e);
		}
		return merchantOnlineApplyList;
	}
	
	public MerchantOnlineApplyService getMerchantOnlineApplyService() {
		return merchantOnlineApplyService;
	}
	public void setMerchantOnlineApplyService(
			MerchantOnlineApplyService merchantOnlineApplyService) {
		this.merchantOnlineApplyService = merchantOnlineApplyService;
	}
	
}
