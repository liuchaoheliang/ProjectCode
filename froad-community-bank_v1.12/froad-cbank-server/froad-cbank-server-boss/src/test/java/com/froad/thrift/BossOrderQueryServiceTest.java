/**
 * Project Name:froad-cbank-server-boss
 * File Name:BossOrderQueryServiceTest.java
 * Package Name:com.froad.thrift
 * Date:2015年8月10日上午9:54:00
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.thrift;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.common.beans.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.thrift.service.BossOrderQueryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.order.BossGroupOrderListReq;
import com.froad.thrift.vo.order.BossGroupOrderListRes;
import com.froad.thrift.vo.order.BossGroupOrderVo;
import com.froad.thrift.vo.order.BossPointOrderListReq;
import com.froad.thrift.vo.order.BossPointOrderListRes;
import com.froad.thrift.vo.order.BossPointOrderVo;
import com.froad.thrift.vo.order.BossPresellDetialRes;
import com.froad.thrift.vo.order.BossPresellOrderListReq;
import com.froad.thrift.vo.order.BossPresellOrderListRes;
import com.froad.thrift.vo.order.BossPresellOrderVo;
import com.froad.thrift.vo.settlement.BossSettlementVo;

/**
 * ClassName: BossOrderQueryServiceTest <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年8月10日 上午9:54:00
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class BossOrderQueryServiceTest {

	public static void main(String[] args) throws Exception {
		// 测试环境
		//TSocket transport = new TSocket("10.43.1.130", 16001);
		// 开发环境
		//TSocket transport = new TSocket("10.43.2.239", 16001);
		// 本地环境
		TSocket transport = new TSocket("127.0.0.1", 16001);
		//TSocket transport = new TSocket("10.43.1.132", 16001);
		transport.open();
		TBinaryProtocol protocol = new TBinaryProtocol(transport);

		TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol,
				BossOrderQueryService.class.getSimpleName());
		BossOrderQueryService.Iface orderService = new BossOrderQueryService.Client(multiplexedProtocol);

		// 团购
//		testQueryGroupOrderList(orderService);
		
		// 预售
		//testQueryPresellOrderList(orderService);
		
		//积分兑换
		//testQueryPointOrderList(orderService);
		
		//预售交易详情
		//testQueryPresellDetial(orderService);
		
		// 线上积分导出
		testPointExport(orderService);
		
		//testPresellExport(orderService);//预售导出
		//testGroupExport(orderService);//团购导出
	}

	/**
	 * 团购交易查询（以券为单位）
	 * 
	 * @author zachary.wang
	 * @throws Exception
	 */
	public static void testQueryGroupOrderList(BossOrderQueryService.Iface orderService) throws Exception {
		BossGroupOrderListReq req = new BossGroupOrderListReq();
		req.setMemberName("dvip66");
		// req.setOrderId("05C2F5F20024"); // 订单号
		// req.setTicketStatus(""); // 券状态
		req.setPhone("18672782013");
		req.setProductName("六月");
		//req.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-01").getTime()); // 开始时间
		//req.setEndTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-10").getTime()); // 结束时间
		//req.setTicketId("029076126859"); // 券号
		PageVo vo = new PageVo();
		vo.setPageNumber(1);
		vo.setPageSize(10);
		
		// 第二页需要的参数
		//vo.setTotalCount(20);
		//vo.setLastPageNumber(1);
		//vo.setFirstRecordTime(1436109793122l);
		//vo.setLastRecordTime(1436063344907l);
		
		req.setPageVo(vo);
		BossGroupOrderListRes res = orderService.queryGroupOrderList(req);
		System.out.println("pageSize: " + res.getPageVo().getPageSize());
		System.out.println("pageCount: " + res.getPageVo().getPageCount());
		System.out.println("totalCount: " + res.getPageVo().getTotalCount());
		System.out.println("firstRecordTime: " + res.getPageVo().getFirstRecordTime());
		System.out.println("lastPageNumber: " + res.getPageVo().getLastPageNumber());
		System.out.println("lastRecordTime: " + res.getPageVo().getLastRecordTime());
		
		List<BossGroupOrderVo> voList = res.getOrders();
		if (voList != null) {
			for (BossGroupOrderVo v : voList) {
				System.out.println("ticketId=" + v.getTicketId() 
						+ ", ticketStatus=" + v.getTicketStatus() 
						+ ", outletName=" + v.getOutletName() 
						+ ", productName=" + v.getProductName() 
						+ ", memberName=" + v.getMemberName() 
						+ ", orderId=" + v.getOrderId() 
						+ ", phone=" + v.getPhone() 
						+ ", createTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(v.getPaymentTime())));
			}
		}
	}
	
	
	/**
	 * 预售交易查询（以券为单位）
	 */
	public static void testQueryPresellOrderList(BossOrderQueryService.Iface orderService) throws Exception {
		BossPresellOrderListReq req = new BossPresellOrderListReq();
		req.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse("2014-04-01").getTime()); // 开始时间
		//req.setEndTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-10").getTime()); // 结束时间
		//req.setPhone("13416344823");
		//req.setProductName("遮阳帽");
		//req.setMemberName("asdasd");
		PageVo vo = new PageVo();
		vo.setPageNumber(1);
		vo.setPageSize(100);
		
		// 第二页需要的参数
		//vo.setTotalCount(8500);
		//vo.setLastPageNumber(1);
		//vo.setFirstRecordTime(1436109793122l);
		//vo.setLastRecordTime(1436063344907l);
		
		req.setPageVo(vo);
		BossPresellOrderListRes res = orderService.queryPresellOrderList(req);
		System.out.println("pageSize: " + res.getPageVo().getPageSize());
		System.out.println("pageCount: " + res.getPageVo().getPageCount());
		System.out.println("totalCount: " + res.getPageVo().getTotalCount());
		System.out.println("firstRecordTime: " + res.getPageVo().getFirstRecordTime());
		System.out.println("lastPageNumber: " + res.getPageVo().getLastPageNumber());
		System.out.println("lastRecordTime: " + res.getPageVo().getLastRecordTime());
		List<BossPresellOrderVo> voList = res.getOrders();
		if (voList != null) {
			for (BossPresellOrderVo v : voList) {
				System.out.println("ticketId=" + v.getTicketId() 
						+ ", ticketStatus=" + v.getTicketStatus() 
						+ ", productName=" + v.getProductName() 
						+ ", memberName=" + v.getMemberName() 
						+ ", orderId=" + v.getOrderId() 
						+ ", phone=" + v.getConsigneePhone() 
						+ ", createTime=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(v.getPaymentTime())));
			}
		}
	}
	
	/**
	 * 在线积分兑换订单查询
	 */
	public static void testQueryPointOrderList(BossOrderQueryService.Iface orderService) throws Exception {
		BossPointOrderListReq req = new BossPointOrderListReq();
		
		//分页信息
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(1);
		pageVo.setPageSize(2);
		//pageVo.setLastPageNumber(1);
		//pageVo.setFirstRecordTime(1438855830377l);
		//pageVo.setLastRecordTime(1438855666102l);
		//pageVo.setTotalCount(20);
		
		req.setPageVo(pageVo);
		req.setClientId("anhui");
		req.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-05-01").getTime());
		req.setEndTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-07").getTime());
		//req.setOrderStatus("6");
		//req.setProductName("酸奶");
		req.setSubOrderId("09FA64438000");
		
		BossPointOrderListRes response = orderService.queryPointOrderList(req);
		List<BossPointOrderVo> orders = response.getOrders();
		for(int i = 0; i < orders.size(); i++) {
			
			System.out.println("creatime:"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date(orders.get(i).getCreateTime())) + orders.get(i));
		}
		
		System.out.println("pageSize: " + response.getPageVo().getPageSize());
		System.out.println("pageCount: " + response.getPageVo().getPageCount());
		System.out.println("totalCount: " + response.getPageVo().getTotalCount());
		System.out.println("firstRecordTime: " + response.getPageVo().getFirstRecordTime());
		System.out.println("lastPageNumber: " + response.getPageVo().getLastPageNumber());
		System.out.println("lastRecordTime: " + response.getPageVo().getLastRecordTime());
		
	}
	
	/**
	 * 预售交易详情
	 */
	public static void testQueryPresellDetial(BossOrderQueryService.Iface orderService) throws Exception {
		String clientId = "anhui";
		String ticketId = "2598062973";
		/*ResultCode code = ResultCode.success;
		ResultBean result = new ResultBean(code);
		*/
		
		
		BossPresellDetialRes presellDetial = orderService.getPresellDetial(clientId, ticketId);
		
		System.out.println("提货开始日期：deliveryStartTime:" + new SimpleDateFormat("yyyyMMdd").format(new Date(presellDetial.getDeliveryStartTime())) + "\n" + 
				"提货结束日期：deliveryEndTime:" + new SimpleDateFormat("yyyyMMdd").format(new Date(presellDetial.getDeliveryEndTime())) + "\n" +
				"提货网点：consingeeAddress:" + presellDetial.getConsigneeAddress() +"\n"
				+ presellDetial);
	}
	
	//线上积分兑换导出
	@SuppressWarnings("unused")
	public static void testPointExport(BossOrderQueryService.Iface iface){
		try {
			BossPointOrderListReq req = new BossPointOrderListReq();
			String clientId = "anhui"; // 所属客户端
		    //long startTime = 0; // 开始时间
			//long endTime = 0; // 结束时间
			String productName = ""; // 商品名称
			String subOrderId = ""; // 订单编号
			String orderStatus = ""; // 订单状态
			
			PageVo pageVo = new PageVo(); // 分页
			
			req.setPageVo(pageVo);
			req.setClientId(clientId);
			req.setStartTime(new SimpleDateFormat("yyyy-MM-dd").parse("2014-05-01").getTime());
			req.setEndTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-07").getTime());
			req.setProductName(productName);
			req.setSubOrderId(subOrderId);
			req.setOrderStatus(orderStatus);
			long t1 = System.currentTimeMillis();
			ExportResultRes exportRes = iface.exportPointOrderTicket(req);
			long t2 = System.currentTimeMillis();
			System.out.println("总耗时：" + (t2 - t1) + "ms");
			System.out.println("url:" + exportRes.getUrl());
			System.out.println(exportRes.getResultVo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//预售导出
	@SuppressWarnings("unused")
	public static void testPresellExport(BossOrderQueryService.Iface iface){
		try {
			BossPresellOrderListReq req = new BossPresellOrderListReq();
			
			 String orderId = ""; // 订单编号
			 String ticketId = ""; // 券号
			 String phone = ""; // 手机号
			 /**
			  * 
			  * "1"   - 未消费：[1:已发送;]
			  * "2"   - 已消费：[2:已消费;]
			  * "3"   - 已过期：[3:已过期;]
			  * "4,7" - 已退款：[4:已退款;7:已过期退款;]
			  * "5,9" - 退款中：[5:退款中;9:已过期退款中;]
			  * "6"   - 退款失败：[6:退款失败;]
			  */
			  String ticketStatus = ""; // 券号状态
			  String settlementStatus = ""; //结算状态0:未结算;1:结算中;2:结算成功;3:结算失败;4:无效结算记录
			  String productName = ""; // 商品名称
			  String memberName = ""; // 用户名:提(收)货人姓名
			  //long startTime = 0; // 支付开始时间
			  //long endTime = 0; // 支付结束时间
			  String begStr = "2015-01-29";
			  long startTime = new SimpleDateFormat("yyyy-MM-dd").parse(begStr).getTime();
				
			  String endStr = "2015-09-11";
			  long endTime = new SimpleDateFormat("yyyy-MM-dd").parse(endStr).getTime();
				
			  
			  PageVo pageVo = new PageVo(); // 分页
			  int pageNumber = 1;
			  int pageSize = 100;
			  pageVo.setPageNumber(pageNumber);
			  pageVo.setPageSize(pageSize);
			  
			  req.setPageVo(pageVo);
			  req.setOrderId(orderId);
			  req.setTicketId(ticketId);
			  req.setPhone(phone);
			  req.setTicketStatus(ticketStatus);
			  req.setSettlementStatus(settlementStatus);
			  req.setProductName(productName);
			  req.setMemberName(memberName);
			  req.setStartTime(startTime);
			  req.setEndTime(endTime);
			long t1 = System.currentTimeMillis();
			ExportResultRes exportRes = iface.exportPresellOrderTicket(req);
			long t2 = System.currentTimeMillis();
			System.out.println("总耗时：" + (t2 - t1) + "ms");
			System.out.println("url:" + exportRes.getUrl());
			System.out.println(exportRes.getResultVo());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//团购导出
	@SuppressWarnings("unused")
	public static void testGroupExport(BossOrderQueryService.Iface iface){
		try {
			BossGroupOrderListReq req = new BossGroupOrderListReq();
			String orderId = ""; // 订单编号
			String ticketId = ""; // 券号
			String phone = ""; // 手机号
		    /** 
		     * 券号状态
		     * "1"   - 未消费：[1:已发送;]
		     * "2"   - 已消费：[2:已消费;]
		     * "3"   - 已过期：[3:已过期;]
		     * "4,7" - 已退款：[4:已退款;7:已过期退款;]
		     * "5,9" - 退款中：[5:退款中;9:已过期退款中;]
		     * "6"   - 退款失败：[6:退款失败;]
		     */
			 String ticketStatus = ""; // required
			 String settlementStatus = ""; // 结算状态0:未结算;1:结算中;2:结算成功;3:结算失败;4:无效结算记录
			 String productName = ""; // 商品名称
			 String memberName = ""; // 用户名称:登录账号
			 //long startTime = 0; // 支付开始时间
			 //long endTime = 0; // 支付结束时间
			 
			 PageVo pageVo = new PageVo(); // 分页
			 int pageNumber = 1;
			 int pageSize = 100;
			 pageVo.setPageNumber(pageNumber);
			 pageVo.setPageSize(pageSize);
			  
			 req.setPageVo(pageVo);
			 req.setOrderId(orderId);
			 req.setTicketId(ticketId);
			 req.setPhone(phone);
			 req.setTicketStatus(ticketStatus);
			 req.setSettlementStatus(settlementStatus);
			 req.setProductName(productName);
			 req.setMemberName(memberName);
			 //req.setStartTime(startTime);
			 //req.setEndTime(endTime);
			
			ExportResultRes exportRes = iface.exportGroupOrderTicket(req);
			System.out.println("url:" + exportRes.getUrl());
			System.out.println(exportRes.getResultVo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
}
