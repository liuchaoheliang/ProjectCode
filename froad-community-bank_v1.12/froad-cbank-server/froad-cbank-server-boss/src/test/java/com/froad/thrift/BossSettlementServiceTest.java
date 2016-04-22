/**
 * Project Name:froad-cbank-server-boss
 * File Name:BossSettlementServiceTest.java
 * Package Name:com.froad.thrift
 * Date:2015年8月6日下午4:01:27
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.thrift;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.logback.LogCvt;
import com.froad.thrift.service.BossOrderQueryService;
import com.froad.thrift.service.BossSettlementService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.order.BossGroupOrderListReq;
import com.froad.thrift.vo.order.BossPointOrderListReq;
import com.froad.thrift.vo.order.BossPresellOrderListReq;
import com.froad.thrift.vo.settlement.BossSettlementPage;
import com.froad.thrift.vo.settlement.BossSettlementVo;

/**
 * ClassName: BossSettlementServiceTest <br/>
 * Reason: 结算接口测试 <br/>
 * Date: 2015年8月6日 下午4:01:27
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class BossSettlementServiceTest {

	public static void main(String[] args) throws TException {
		// 测试环境
		//TSocket transport = new TSocket("10.43.1.130", 16001);
		// 开发环境
		//TSocket transport = new TSocket("10.43.2.239", 16001);
		// 本地环境
		//TSocket transport = new TSocket("10.43.2.7", 16001);
		// 本地环境
		//TSocket transport = new TSocket("127.0.0.1", 16001);
		TSocket transport = new TSocket("10.43.1.132", 16001);
		transport.open();
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		
		
		TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, BossSettlementService.class.getSimpleName());
		BossSettlementService.Iface settlementService = new BossSettlementService.Client(multiplexedProtocol);
		//testQueryByPage(settlementService);
		
		//结算导出
		testSettlementExport(settlementService);
		
		
		//TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, BossOrderQueryService.class.getSimpleName());
		//BossOrderQueryService.Iface orderQueryService = new BossOrderQueryService.Client(multiplexedProtocol);
		//testPresellExport(orderQueryService);//预售导出
		//testGroupExport(orderQueryService);//团购导出
		//testPointExport(orderQueryService);//线上积分兑换导出
		
	}
	
	/**
	 * 测试结算分页查询
	 */
	@SuppressWarnings("unused")
	public static void testQueryByPage(BossSettlementService.Iface iface){
		try {
			Long beg = null, end = null;
			Integer pageNumber = null, pageSize = null;
			String clientId = null, orderId = null, billNo = null, merchantName = null, outletName = null, productName = null, state = null;
			
			pageNumber = 1;
			pageSize = 5000;
			clientId = "anhui"; // 银行
			//orderId = "075BFE620000"; // 订单编号
			//billNo = "2150705232452201"; // 账单编号
			//merchantName = "wszf"; // 商户名称
			//outletName = ""; // 门店名称
			//productName = "牛奶"; // 商品名称
			//state = "0"; // 结算状态
			String begStr = "2015-07-05";
			//beg = new SimpleDateFormat("yyyy-MM-dd").parse(begStr).getTime();
			
			String endStr = "2015-08-10";
			//end = new SimpleDateFormat("yyyy-MM-dd").parse(endStr).getTime();
			
			BossSettlementPage page = new BossSettlementPage();
			
			page.setClientId(clientId);
			page.setOrderId(orderId);
			page.setBillNo(billNo);
			page.setMerchantName(merchantName);
			page.setOutletName(outletName);
			page.setProductName(productName);
			page.setSettleState(state);
			
			PageVo pageVo = new PageVo();
			pageVo.setPageNumber(pageNumber);
			pageVo.setPageSize(pageSize);
			//pageVo.setLastPageNumber(1);
			//pageVo.setLastRecordTime(1436069425203l);
			//pageVo.setFirstRecordTime(1438930302402l);
			//pageVo.setTotalCount(20);
			if (beg != null) {
				pageVo.setBegDate(beg);
			}
			if (end != null) {
				pageVo.setEndDate(end);
			}
			page.setPage(pageVo);
			page = iface.queryByPage(page);
			
			System.out.println("size: " + page.getRespListSize());
			System.out.println("pageSize: " + page.getPage().getPageSize());
			System.out.println("pageCount: " + page.getPage().getPageCount());
			System.out.println("totalCount: " + page.getPage().getTotalCount());
			System.out.println("firstRecordTime: " + page.getPage().getFirstRecordTime());
			System.out.println("lastPageNumber: " + page.getPage().getLastPageNumber());
			System.out.println("lastRecordTime: " + page.getPage().getLastRecordTime());
			
			for (int i = 0; page.getRespList() != null && i < page.getRespList().size(); i++) {
				BossSettlementVo bsvo = page.getRespList().get(i);
				/*
				System.out.println(i + " : billNo=" + bsvo.getBillNo() 
						+ ", productName=" + bsvo.getProductName() 
						+ ", createTime=" 
						+ new SimpleDateFormat("yyyy-MM-dd").format(new Date(bsvo.getCreateTime())));
			*/
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//结算导出
	@SuppressWarnings("unused")
	public static void testSettlementExport(BossSettlementService.Iface iface){
		try {
			Long beg = null, end = null;
			Integer pageNumber = null, pageSize = null;
			String clientId = null, orderId = null, billNo = null, merchantName = null, outletName = null, productName = null, state = null;
			
			pageNumber = 1;
			pageSize = 10;
			//clientId = "anhui"; // 银行
			//orderId = "0BC3BB200000"; // 订单编号
			//billNo = "2150705232452201"; // 账单编号
			//merchantName = "wszf"; // 商户名称
			//outletName = ""; // 门店名称
			//productName = "徽芝园"; // 商品名称
			//state = "0"; // 结算状态
			//String begStr = "2015-08-28";
			//beg = new SimpleDateFormat("yyyy-MM-dd").parse(begStr).getTime();
			
			//String endStr = "2015-09-11";
			//end = new SimpleDateFormat("yyyy-MM-dd").parse(endStr).getTime();
			
			BossSettlementPage page = new BossSettlementPage();
			
			page.setClientId(clientId);
			page.setOrderId(orderId);
			page.setBillNo(billNo);
			page.setMerchantName(merchantName);
			page.setOutletName(outletName);
			page.setProductName(productName);
			page.setSettleState(state);
			
			PageVo pageVo = new PageVo();
			pageVo.setPageNumber(pageNumber);
			pageVo.setPageSize(pageSize);
			//pageVo.setLastPageNumber(1);
			//pageVo.setLastRecordTime(1436069425203l);
			//pageVo.setFirstRecordTime(1438930302402l);
			//pageVo.setTotalCount(20);
			/*if (beg != null) {
				pageVo.setBegDate(beg);
			}
			if (end != null) {
				pageVo.setEndDate(end);
			}*/
			page.setPage(pageVo);
			long t1 = System.currentTimeMillis();
			ExportResultRes exportRes = iface.exportSettlementQueryByPage(page);
			long t2 = System.currentTimeMillis();
			System.out.println("总耗时：t2-t1=" +(t2 - t1)  + "ms,");
			System.out.println("url:" + exportRes.getUrl());
			System.out.println(exportRes.getResultVo());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
