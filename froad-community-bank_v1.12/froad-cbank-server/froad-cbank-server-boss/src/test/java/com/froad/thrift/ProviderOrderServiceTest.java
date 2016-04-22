/**
 * Project Name:froad-cbank-server-boss-1.8.0-SNAPSHOT
 * File Name:TTTTT.java
 * Package Name:com.froad.thrift
 * Date:2015年11月27日上午11:16:22
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.ProviderOrderService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.providerorder.ShippingExcelInfoVo;
import com.froad.util.PropertiesUtil;

/**
 * ClassName:TTTTT
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月27日 上午11:16:22
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class ProviderOrderServiceTest {
	
	private static final String host = "127.0.0.1";
	private static final int post = PropertiesUtil.port;
	
	public static void main(String[] args) {
		try {
			
			ProviderOrderService.Iface providerOrderService = 
					(ProviderOrderService.Iface)ThriftClientProxyFactory.createIface(ProviderOrderService.class.getName(), ProviderOrderService.class.getSimpleName(), host, post);
			
			
			PageVo pageVo = new PageVo();
//			ProviderOrderQueryReq req = new ProviderOrderQueryReq();
//			req.setOrderId("40001");
//			providerOrderService.queryProviderOrderInfoByPage(req, pageVo);
			
			
//			providerOrderService.exportProviderOrderInfo(req);
			
			
			List<ShippingExcelInfoVo> infos = new ArrayList<ShippingExcelInfoVo>();
			ShippingExcelInfoVo info1 = new ShippingExcelInfoVo();
//			info1.setOrderId("14231B718001");
//			info1.setClientName("重庆");
//			info1.setMemberCode(52001970855l);
//			info1.setPhone("15848856664");
//			info1.setTotalPrice(98);
//			info1.setRecvAddress("aaa");
//			info1.setShippingCorp("顺丰快递");
//			info1.setShippingId("121321");
//			info1.setShippingStatusDesc("已发货");
			
			infos.add(info1);
			providerOrderService.inputShippingInfo(infos);
			
			
//			UpdateShippingInfoReq updateReq = new UpdateShippingInfoReq();
//			updateReq.setOrderId("131BB0A40001");
//			updateReq.setShippingCorpCode("100000030");
//			updateReq.setShippingId("10099");
//			providerOrderService.updateShippingInfo(updateReq);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
}
