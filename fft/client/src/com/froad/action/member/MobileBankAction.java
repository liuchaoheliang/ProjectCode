package com.froad.action.member;

import com.froad.action.support.BuyerChannelActionSupport;
import com.froad.action.support.BuyersActionSupport;
import com.froad.action.support.UserCertificationActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.buyerChannel.BuyerChannel;
import com.froad.client.userCertification.UserCertification;
import com.froad.util.Assert;
import com.froad.util.command.MallCommand;

/** 
 * @author FQ 
 * @date 2013-4-16 下午05:29:34
 * @version 1.0
 * 手机银行卡认证
 */
public class MobileBankAction extends BaseActionSupport{
	
	private UserCertificationActionSupport userCertificationActionSupport;
	private BuyersActionSupport buyersActionSupport;
	private BuyerChannelActionSupport buyerChannelActionSupport;

	private String mobile;//手机号
	private String isMobileBank="false";//是否是手机银行卡
	

	//手机银行卡认证页面
	public String index(){
		log.info("手机银行卡认证");
		
		String userId = (String)getSession(MallCommand.USER_ID);
		if(Assert.empty(userId)){
			return "login_page";
		}
		
		mobile=getLoginUser().getMobilephone();
		
		 if(!Assert.empty(mobile)){
				String xingStr = mobile.substring(3, 8);
				mobile = mobile.replace(xingStr, "*****");
			}
		
		BuyerChannel buyerChannel=buyerChannelActionSupport.getBuyerChannelByUserId(userId);
		if(buyerChannel!=null && buyerChannel.getPhone()!=null && !"".equals(buyerChannel.getPhone())){
			isMobileBank="true";
		}
		log.info("手机号码："+mobile +" isMobileBank：" +isMobileBank);
		
		return "index";
	}
	
	//认证
	public String ajaxMobileAuthentication(){
		log.info("手机银行卡确认认证");
		
		String userId = (String)getSession(MallCommand.USER_ID);
		if(Assert.empty(userId)){
			return "login_page";
		}
		
		boolean checkResult=false;
		UserCertification userCert=new UserCertification();
		userCert.setCertificationType("1");//手机号码验证
		userCert.setPhone(getLoginUser().getMobilephone());
		
		checkResult=userCertificationActionSupport.checkAccount(userCert);
		if(checkResult){
			
			boolean result=buyersActionSupport.updateBuyerAndBuyerChannel(userId, getLoginUser().getMobilephone());
			
			if(result){
				return ajaxJsonSuccessMessage("认证成功");
			}
			else{
				return ajaxJsonErrorMessage("认证失败");
			}
		}
		else{
			return ajaxJsonErrorMessage("认证失败");
		}
		
		
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public UserCertificationActionSupport getUserCertificationActionSupport() {
		return userCertificationActionSupport;
	}

	public void setUserCertificationActionSupport(
			UserCertificationActionSupport userCertificationActionSupport) {
		this.userCertificationActionSupport = userCertificationActionSupport;
	}
	
	public BuyersActionSupport getBuyersActionSupport() {
		return buyersActionSupport;
	}

	public void setBuyersActionSupport(BuyersActionSupport buyersActionSupport) {
		this.buyersActionSupport = buyersActionSupport;
	}

	public BuyerChannelActionSupport getBuyerChannelActionSupport() {
		return buyerChannelActionSupport;
	}

	public void setBuyerChannelActionSupport(
			BuyerChannelActionSupport buyerChannelActionSupport) {
		this.buyerChannelActionSupport = buyerChannelActionSupport;
	}
	
	public String getIsMobileBank() {
		return isMobileBank;
	}

	public void setIsMobileBank(String isMobileBank) {
		this.isMobileBank = isMobileBank;
	}
}
