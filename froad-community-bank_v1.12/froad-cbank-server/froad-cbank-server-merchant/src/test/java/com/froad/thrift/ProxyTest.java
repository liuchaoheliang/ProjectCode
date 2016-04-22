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
 * @Title: ProxyTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年4月10日
 */

package com.froad.thrift;

import org.apache.thrift.TException;

import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.OutletVo;
import com.froad.util.PropertiesUtil;


/**    
 * <p>Title: ProxyTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年4月10日 下午7:41:25   
 */

public class ProxyTest {
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
//		orgServeice();
		
		outletService();
	}


	
	/** 
	 * @Title: outletService 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return void    返回类型 
	 * @throws 
	 */ 
	private static void outletService() {
		OutletService.Iface client = (OutletService.Iface) ThriftClientProxyFactory.createIface(OutletService.class.getName(), "OutletService", "127.0.0.1", 15201, 1000);
		try {
			System.out.println("调用getSuperOrgCodeByType方法:" + client.getHottestOutletDetailByPage(null, null));
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/** 
	 * @Title: orgServeice 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return void    返回类型 
	 * @throws 
	 */ 
	private static void orgServeice() {
		OrgService.Iface client = (OrgService.Iface) ThriftClientProxyFactory.createIface(OrgService.class.getName(), "OrgService", "10.43.2.3", 9090, 1000);
		try {
			System.out.println("调用getSuperOrgCodeByType方法:" + client.getSuperOrgCodeByType("1233", "1000"));
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
