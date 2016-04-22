package com.froad.cbank.coremodule.module.normal.user.utils;


/**
 * 敏感信息加密工具
 */
public class SensEncryptUtil {

	
	/**
	 * 加密手机号
	 * @param mobile
	 * @return
	 */
	public static String encryptMobile(String mobile){
		if(mobile != null && mobile.length() == 11){
			return mobile.substring(0,3) + "****" + mobile.substring(7);
		}
		return mobile;
	}
	
	/**
	 * 
	 * encryptLoginID:(加密longinId).
	 *
	 * @author wufei
	 * 2015-10-29 下午06:56:28
	 * @param loginID
	 * @return
	 *
	 */
	public static String encryptLoginID(String loginID){
		if(loginID != null){
			return  loginID.substring(0,1) + "****" + loginID.substring(loginID.length()-1,loginID.length());
		}
		return loginID;
	}
	
	
	/**
	 * 加密邮箱地址
	 * @param email
	 * @return
	 */
	public static String encryptEmail(String email){
		if(email != null && email.indexOf("@") != -1){
			String[] mail = email.split("@");
			int eNameLen = mail[0].length();
			if(eNameLen == 3){
				mail[0] = mail[0].substring(0, 1) + "**";
			}else if(eNameLen == 4){
				mail[0] = mail[0].substring(0, 2) + "**";
			}else if(eNameLen > 4){
				mail[0] = mail[0].substring(0, eNameLen-3) + "***";
			}
			
			email = mail[0] + "@" + mail[1];
			
		}
		
		return email;
	}
	
	
	
	/**
	 * 加密身份证号码
	 * @param identityNo
	 * @return
	 */
	public static String encryptIdentityNo(String identityNo){
		if(identityNo != null && identityNo.length() == 18){
			identityNo = identityNo.substring(0, 6) + "******" + identityNo.substring(14);
		}
		return identityNo;
	}
	
	
	
	
	public static void main(String[] args) {
		String email = "liaol@qq.com";
		
		System.out.println(encryptEmail(email));
		
	}
	
}
