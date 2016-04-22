package com.froad.action.support;

import java.util.List;

import org.apache.log4j.Logger;
import com.froad.client.userCertification.UserCertification;
import com.froad.client.userCertification.UserCertificationService;
import com.froad.util.Assert;
import com.froad.util.command.Command;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-3-6  
 * @version 1.0
 * 会员银行积分激活认证 actionSupport
 */
public class UserCertificationActionSupport {
	private static Logger logger = Logger.getLogger(UserCertificationActionSupport.class);
	private UserCertificationService userCertificationService;
	
	public Integer addUserCertification(UserCertification cert){
		Integer num =null;
		try {
			num = userCertificationService.add(cert);
		} catch (Exception e) {
			logger.error("UserCertificationActionSupport.addUserCertification增加银行积分认证记录出错 userId："+cert.getUserId()
					+" accountName:"+cert.getAccountName()+" accountNo:"+cert.getAccountNo(), e);
		}
		return num;
	}
	
	
	public UserCertification getUserCertification(String userId,String channelId){
		UserCertification userCertification=new UserCertification();
//		userCertification.setAccountName("用户已认证通过的有效账户名");
//		userCertification.setAccountNo("用户已认证通过的有效账户号");
		userCertification.setChannelId(channelId);
		userCertification.setUserId(userId);
		userCertification.setState(Command.STATE_START);
		userCertification.setCertificationResult(Command.certificationResult_success);
		List<UserCertification> userCertifications=userCertificationService.getUserCertBySelective(userCertification);
		if(Assert.empty(userCertifications)){
			logger.error("userId："+userId+"，没有已认证通过的有效账户号！");
			return null;
		}else
			return userCertifications.get(0);
	}
	
	
	public UserCertification getUserCertByUserId(String userId){
		if(userId==null||"".equals(userId)){
			logger.error("用户编号为空");
			return null;
		}
		return userCertificationService.getUserCertByUserId(userId);
	}
	
	/**
	 * 查询已经认证成功的银行卡信息
	 * @param userId
	 * @return
	 */
	public UserCertification getUserCertificationBind(String userId){		
		return userCertificationService.getUserCertByUserId(userId);
	}
	
	/**
	 * 
	 * @param userCert
	 * @return
	 */
	public boolean checkAccount(UserCertification userCert){
		boolean flag = false;
		try {
			flag = userCertificationService.checkAccount(userCert);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("checkAccount ", e);
			flag = false;
		}
		logger.info("====================结束验证===================");
		return flag;
	}
	
	/**
	 * 积分提现认证成功后添加usercertification记录
	 * @param cert
	 * @return
	 */
	public Integer saveBingAccount(UserCertification cert){
		Integer num =null;
		try {
			num = userCertificationService.addOrUpdateBindingNew(cert);
		} catch (Exception e) {
			logger.error("UserCertificationActionSupport.addUserCertification增加银行积分认证记录出错 userId："+cert.getUserId()
					+" accountName:"+cert.getAccountName()+" accountNo:"+cert.getAccountNo(), e);
		}
		return num;
	}
	
	public UserCertificationService getUserCertificationService() {
		return userCertificationService;
	}
	public void setUserCertificationService(
			UserCertificationService userCertificationService) {
		this.userCertificationService = userCertificationService;
	}
	
	
}
