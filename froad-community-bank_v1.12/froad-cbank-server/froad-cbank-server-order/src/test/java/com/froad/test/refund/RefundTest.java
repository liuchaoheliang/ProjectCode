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
 * @Title: Test.java
 * @Package com.froad.test.ticket
 * @Description: TODO
 * @author vania
 * @date 2015年6月8日
 */

package com.froad.test.refund;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.enums.ModuleID;
import com.froad.enums.ProductType;
import com.froad.enums.TicketStatus;
import com.froad.po.Ticket;
import com.froad.support.TicketSupport;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.thrift.service.RefundService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.refund.RefundProductVo;
import com.froad.thrift.vo.refund.RefundRequestVo;
import com.froad.thrift.vo.refund.RefundTicketVo;
import com.froad.thrift.vo.refund.RefundTicketsRequestVo;
import com.froad.thrift.vo.ticket.TicketListRequestVo;
import com.froad.thrift.vo.ticket.TicketSummaryVo;
import com.froad.thrift.vo.ticket.TicketVerifyOfMergerRequestVo;
import com.froad.util.PropertiesUtil;
import com.froad.util.SimpleID;


/**    
 * <p>Title: Test.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年6月8日 下午5:21:01   
 */

public class RefundTest {
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
//			host = "10.43.1.9";
			port = 15501;
			transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"RefundService");
			RefundService.Client service = new RefundService.Client(mp);
			transport.open();
			
//			OriginVo originVo = new OriginVo();
//			originVo.setPlatType(PlatType.merchant_h5);
//			originVo.setOperatorId(100000000);
//			originVo.setOperatorIp("192.168.19.105");
//			originVo.setDescription("团购验券");
//			
//			RefundTicketsRequestVo requestVo = new RefundTicketsRequestVo();
//			
//			RefundTicketVo re1 = new RefundTicketVo();
//			re1.setSuborderId("07FF7DF28000");
//			re1.setTicketId("023302796112");
//			re1.setProductId("07F9B0D18000");
//			re1.setQuantity(2);
//			re1.setRemark("团购券过期自动退款");
//			re1.setClientId("anhui");
//			
//			RefundTicketVo re2 = new RefundTicketVo();
//			re2.setSuborderId("07FF7DF28000");
//			re2.setTicketId("023302835981");
////			re2.setProductId("07F9A6710000");
//			re2.setProductId("07F9B0D18000");
//			re2.setQuantity(3);
//			re2.setRemark("团购券过期自动退款");
//			re2.setClientId("anhui");
//			
//			List<RefundTicketVo> ticketList = new ArrayList<RefundTicketVo>();
//			ticketList.add(re1);
//			ticketList.add(re2);
//			requestVo.setTicketList(ticketList );
//			service.refundTickets(requestVo );
			
			RefundRequestVo refund = new RefundRequestVo();
			refund.setClientId("chongqing");
			refund.setOrderId("13076AB20000");
			refund.setSubOrderId("13076AB28000");
			refund.setOption("1");

			List<RefundProductVo> refundProduct = new ArrayList<RefundProductVo>();
			RefundProductVo productA = new RefundProductVo();
			productA.setProductId("12EF94840000");
			productA.setQuantity(1);
			
			RefundProductVo productB = new RefundProductVo();
			productB.setProductId("12EF01540000");
			productB.setQuantity(1);
			
			RefundProductVo productC = new RefundProductVo();
			productC.setProductId("130455540000");
			productC.setQuantity(2);
			
//			refundProduct.add(productA);
			refundProduct.add(productB);
			refundProduct.add(productC);
			refund.setProductList(refundProduct);
			System.out.println(JSONObject.toJSONString(service.doOrderRefund(refund)));
			
			transport.close();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(transport != null)transport.close();
		}
	}
	

}
