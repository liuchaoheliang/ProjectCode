/**
 * Project Name:coremodule-merchant
 * File Name:EncryptUtil.java
 * Package Name:com.froad.cbank.coremodule.module.normal.merchant.utils
 * Date:2015年9月28日上午11:28:20
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.merchant.utils;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;

/**
 * ClassName:EncryptUtil
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月28日 上午11:28:20
 * @author   mi
 * @version  
 * @see 	 
 */
public class EncryptUtil {


	/**
	 * 加密手机号
	 * @param mobile
	 * @return
	 */
	public static String encryptMobile(String mobile){
		if(StringUtil.isNotBlank(mobile) && !"".equals(mobile) && mobile.length() == 11){
			return mobile.substring(0,3) + "****" + mobile.substring(7);
		}
		return mobile;
	}
	
	
	
	/**
	 * 加密邮箱地址
	 * @param email
	 * @return
	 */
	public static String encryptEmail(String email){
		if(StringUtil.isNotBlank(email) && !"".equals(email) && email.indexOf("@") != -1){
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
		if(StringUtil.isNotBlank(identityNo) && !"".equals(identityNo) && identityNo.length() == 18){
			identityNo = identityNo.substring(0, 6) + "******" + identityNo.substring(14);
		}
		return identityNo;
	}
	
	
	/**
	 * 加密卷码
	 * @param identityNo
	 * @return
	 */
	public static String encryptTack(String tack){
		if(StringUtil.isNotBlank(tack) && !"".equals(tack)){
			return tack.substring(0,3) + "****" + tack.substring(8);
		}
		return tack;
	}
}
