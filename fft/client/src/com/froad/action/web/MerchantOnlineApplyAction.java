package com.froad.action.web;

import java.util.ArrayList;
import java.util.List;
import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.MerchantIndustryActionSupport;
import com.froad.action.support.MerchantOnlineApplyActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.merchant.Merchant;
import com.froad.client.merchantIndustry.MerchantIndustry;
import com.froad.client.merchantOnlineApply.MerchantOnlineApply;
import com.froad.util.Assert;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-4  
 * @version 1.0
 */
public class MerchantOnlineApplyAction extends BaseActionSupport {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 6456256545928423229L;
	private MerchantActionSupport merchantActionSupport;
	private MerchantOnlineApplyActionSupport merchantOnlineApplyActionSupport;
	private MerchantIndustryActionSupport merchantIndustryActionSupport;
	private List<MerchantIndustry> merchantIndustryList;
	private Merchant merchant;//商户
	private MerchantOnlineApply merchantOnlineApply;
	private MerchantOnlineApply apply;
	private String message;
	
	/**
	 * 进入商户在线申请
	 * @return
	 */
	public String merchantOnlineApply(){
		//查询行业类别
		merchantIndustryList = merchantIndustryActionSupport.getMerchantIndustryList();
		if(merchantIndustryList == null){
			merchantIndustryList = new ArrayList<MerchantIndustry>();
		}
		return "merchant_apply_index";
	}
	
	/**
	 * 商户在线申请
	 * @return
	 */
	public String merchantApply(){
//		List<MerchantOnlineApply> merchantOnlineApplyList = null;
		if(merchantOnlineApply == null){
			merchantOnlineApply = new MerchantOnlineApply();
		}
		if(!Assert.empty(merchantOnlineApply.getMerchantName())
				&& !Assert.empty(merchantOnlineApply.getMobile())
				&& !Assert.empty(merchantOnlineApply.getMechantIndustryId())){
//		String userId = (String)getSession("userId");
//		if(!Assert.empty(userId)){
//			merchantOnlineApply.setUserId(userId);
//			merchantOnlineApplyList = merchantOnlineApplyActionSupport.getMerchantOnlineApply(merchantOnlineApply);
//			if(merchantOnlineApplyList != null && merchantOnlineApplyList.size()>0){
//				apply = merchantOnlineApplyList.get(0);
//				message = "您已经申请了在线商户。";
//			}else{
				//商户申请
				merchantOnlineApply.setState("10");
				merchantOnlineApply.setIsRelation("0");
				if(!Assert.empty(merchantOnlineApply.getMerchantName()) && !Assert.empty(merchantOnlineApply.getLinkman())
						&& !Assert.empty(merchantOnlineApply.getMobile()) && !Assert.empty(merchantOnlineApply.getMechantIndustryId())){
					apply = merchantOnlineApplyActionSupport.addMerchantOnlineApply(merchantOnlineApply);			
				}			
				if(apply == null){
					apply = new MerchantOnlineApply();
				}else{
					message = "";
				}			
//			}
//		}else{
//			apply = new MerchantOnlineApply();
//			apply.setId(0);
//			message = "请先登录系统";
//		}
		}else{
			apply = new MerchantOnlineApply();
			apply.setId(0);
			message = "请填写完申请信息并提交！";
		}
		return "merchant_apply_success";
	}
	
	
	public MerchantOnlineApplyActionSupport getMerchantOnlineApplyActionSupport() {
		return merchantOnlineApplyActionSupport;
	}
	public void setMerchantOnlineApplyActionSupport(
			MerchantOnlineApplyActionSupport merchantOnlineApplyActionSupport) {
		this.merchantOnlineApplyActionSupport = merchantOnlineApplyActionSupport;
	}
	public MerchantIndustryActionSupport getMerchantIndustryActionSupport() {
		return merchantIndustryActionSupport;
	}
	public void setMerchantIndustryActionSupport(
			MerchantIndustryActionSupport merchantIndustryActionSupport) {
		this.merchantIndustryActionSupport = merchantIndustryActionSupport;
	}

	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}

	public List<MerchantIndustry> getMerchantIndustryList() {
		return merchantIndustryList;
	}

	public void setMerchantIndustryList(List<MerchantIndustry> merchantIndustryList) {
		this.merchantIndustryList = merchantIndustryList;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public MerchantOnlineApply getMerchantOnlineApply() {
		return merchantOnlineApply;
	}

	public void setMerchantOnlineApply(MerchantOnlineApply merchantOnlineApply) {
		this.merchantOnlineApply = merchantOnlineApply;
	}

	public MerchantOnlineApply getApply() {
		return apply;
	}

	public void setApply(MerchantOnlineApply apply) {
		this.apply = apply;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
