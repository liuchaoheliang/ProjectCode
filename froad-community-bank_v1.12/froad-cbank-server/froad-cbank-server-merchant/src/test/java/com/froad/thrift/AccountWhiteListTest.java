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
 * @Title: AccountWhiteListTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年5月26日
 */

package com.froad.thrift;

import org.apache.thrift.TException;

import com.froad.support.Support;
import com.froad.thrift.vo.MerchantBankWhiteVo;
import com.froad.util.PropertiesUtil;


/**    
 * <p>Title: AccountWhiteListTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年5月26日 下午4:43:46   
 */

public class AccountWhiteListTest {
static {
	PropertiesUtil.load();
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
		Support support = new Support();
		MerchantBankWhiteVo merchantBankWhiteVo = new MerchantBankWhiteVo();
		merchantBankWhiteVo.setMerchantIdOrOutletId("5644454");
		merchantBankWhiteVo.setMerchantNameOrOutletName("逍遥牌神油责任有限公司");
		merchantBankWhiteVo.setAccountNo("62233838438");
		merchantBankWhiteVo.setClientId("anhui");
		merchantBankWhiteVo.setAccountName("逍遥赵");
		
		try {
			support.addMerchantBankWhiteList(merchantBankWhiteVo);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
