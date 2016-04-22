package com.froad.action.web;

import java.util.Date;
import java.util.List;

import com.froad.action.support.MerchantActionSupport;
import com.froad.action.support.ShareMerchantActionSupport;
import com.froad.action.support.UserActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.merchant.Merchant;
import com.froad.client.shareMerchant.ShareMerchant;
import com.froad.client.user.User;
import com.froad.sms.SmsService;
import com.froad.util.DateUtil;
import com.froad.util.command.MallCommand;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-25  
 * @version 1.0
 * 分享商户action
 */
public class ShareMerchantAction extends BaseActionSupport {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1507121249072559905L;
	private ShareMerchantActionSupport shareMerchantActionSupport;
	private MerchantActionSupport merchantActionSupport;
	private UserActionSupport userActionSupport;
	private SmsService smsService;

	private ShareMerchant shareMerchant;

	/**
	 * 分享商户
	 * @return
	 */
	public String shareMerchant(){
		log.info("分享手机号码："+shareMerchant.getShareMobile()+" 分享的商户："+shareMerchant.getMerchantId());
		//当前用户
		String userId=(String)getSession(MallCommand.USER_ID);
		
		if(userId==null){
			log.info("会员未登录");
			return ajaxJsonWarnMessage("分享失败,请先登录！");
		}
		
		//是否是会员
		String isUser=(String) getSession(MallCommand.LOGIN_MERCHANT_OR_USER);
		if(isUser==null || !"0".equals(isUser)){
			log.info("不是会员");
			return ajaxJsonWarnMessage("分享失败,商户操作无效!");
		}
		
		//当前时间
		String newDate=DateUtil.formatDate2Str(new Date());
		
		//最后一次分享时间
		List<ShareMerchant> shareMerchantList=shareMerchantActionSupport.getShareMerchantListByUserId(userId);
		
		if(shareMerchantList.size()>0){
			ShareMerchant sm=shareMerchantList.get(0);
			String lastShareDate=sm.getCreateTime();//最后一次分享时间
			
			String diffStr=DateUtil.dateDiff(newDate, lastShareDate, "yyyy-MM-dd|HH:mm:ss");
			if(diffStr!=null){
				String[] diff=diffStr.split("-");
				Integer day=Integer.parseInt(diff[0]);
				Integer hour=Integer.parseInt(diff[1]);
				Integer min=Integer.parseInt(diff[2]);
				
				if(day==0 && hour==0 && min<10){
					return ajaxJsonWarnMessage("分享频繁,稍后再试！");
				}
			}
		}
		
		shareMerchant.setUserId(userId);
		
		boolean isSuccess=shareMerchantActionSupport.addShareMerchant(shareMerchant);
		
		//查询商户
		Merchant merchant=merchantActionSupport.getMerchantById(shareMerchant.getMerchantId());
		
		//查询用户
		User user=userActionSupport.queryUserAllByUserID(userId);
		
		//发送分享短信
		smsService.sendUserShare(shareMerchant.getShareMobile(),user.getMobilephone(), merchant.getCompanyFullName());
		
		if(isSuccess){
			return ajaxJsonSuccessMessage("分享成功！");
		}
		else{
			return ajaxJsonErrorMessage("分享失败！");
		}
		
	}

	public ShareMerchantActionSupport getShareMerchantActionSupport() {
		return shareMerchantActionSupport;
	}

	public void setShareMerchantActionSupport(
			ShareMerchantActionSupport shareMerchantActionSupport) {
		this.shareMerchantActionSupport = shareMerchantActionSupport;
	}


	public ShareMerchant getShareMerchant() {
		return shareMerchant;
	}

	public void setShareMerchant(ShareMerchant shareMerchant) {
		this.shareMerchant = shareMerchant;
	}
	
	public SmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}
	
	public MerchantActionSupport getMerchantActionSupport() {
		return merchantActionSupport;
	}

	public void setMerchantActionSupport(MerchantActionSupport merchantActionSupport) {
		this.merchantActionSupport = merchantActionSupport;
	}
	
	public UserActionSupport getUserActionSupport() {
		return userActionSupport;
	}

	public void setUserActionSupport(UserActionSupport userActionSupport) {
		this.userActionSupport = userActionSupport;
	}
}
