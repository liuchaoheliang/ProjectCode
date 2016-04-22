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
 * @Title: SettlementServiceTest.java
 * @Package com.froad.test.settlement
 * @Description: TODO
 * @author vania
 * @date 2015年7月16日
 */

package com.froad.test.settlement;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.SettlementService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.settlement.SettlementPage;
import com.froad.util.PropertiesUtil;


/**    
 * <p>Title: SettlementServiceTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年7月16日 上午11:14:28   
 */

public class SettlementServiceTest {
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
		TSocket transport = null;
		try {
			String host= "10.43.2.3";
			int port = 0;
			host = "127.0.0.1";
//			host = "10.43.2.241";
//			host = "10.24.248.189";
			port = 15501;
			transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"SettlementService");
			SettlementService.Client service = new SettlementService.Client(mp);
			transport.open();
			
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.merchant_h5);
			originVo.setOperatorId(100000000);
			originVo.setOperatorIp("192.168.19.105");
			originVo.setDescription("");
			
			
			
			PageVo pageVo = new PageVo();
			pageVo.setPageNumber(10000);
			pageVo.setPageSize(10);
			pageVo.setTotalCount(0);
			pageVo.setPageCount(0);
//			pageVo.setBegDate(1434556800000l);
//			pageVo.setEndDate(1441209599000l);
//			pageVo.setLastPageNumber(62);
//			pageVo.setFirstRecordTime(1423196655000l);
//			pageVo.setLastRecordTime(1423182348000l);
			pageVo.setHasNext(false);
			
			List<String> notInSettleState = new ArrayList<String>();
			notInSettleState.add("2");
			
			SettlementPage page = new SettlementPage();
			page.setPage(pageVo);
//			page.setBillNo("2150202164440372");
//			page.setTicketId("123456789");
//			page.setNotInSettleState(notInSettleState);
//			page.setType("1");
			
//			page.setMerchantName("8888888888888888888");
			
			/*********************************queryByPage*************************************/
//			System.out.println("queryByPage返回结果:"+ JSON.toJSONString(service.queryByPage(page ), true));
			
			
			/*********************************exportSettlements*************************************/
			System.out.println("exportSettlements返回结果:"+ JSON.toJSONString(service.exportSettlements(page ), true));
			
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(transport != null)transport.close();
		}
	}

}
