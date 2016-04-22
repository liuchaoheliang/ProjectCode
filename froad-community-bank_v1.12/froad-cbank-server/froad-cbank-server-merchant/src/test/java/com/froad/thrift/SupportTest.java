/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
  
/**  
 * @Title: SupportTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年5月28日
 */

package com.froad.thrift;

import org.apache.thrift.TException;

import com.froad.support.Support;
import com.froad.thrift.vo.MerchantBankWhiteVo;
import com.froad.util.PropertiesUtil;

/**    
 * <p>Title: SupportTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年5月28日 下午3:05:55   
 */

public class SupportTest {
	static Support support = null;
	static {
		PropertiesUtil.load();
		
		support = new Support();
	}

	/** 
	 * @Title: main 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @return void    返回类型 
	 * @throws 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		"0432F7D88000","测试白名单3","123412341234","0","anhui","黑名单"
		MerchantBankWhiteVo merchantBankWhiteVo = new MerchantBankWhiteVo();
		merchantBankWhiteVo.setMerchantIdOrOutletId("0432F7D88000");
		merchantBankWhiteVo.setMerchantNameOrOutletName("测试白名单3");
		merchantBankWhiteVo.setAccountNo("123412341234");
		merchantBankWhiteVo.setClientId("anhui");
		merchantBankWhiteVo.setAccountName("黑名单");
		try {
			support.addMerchantBankWhiteList(merchantBankWhiteVo);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
