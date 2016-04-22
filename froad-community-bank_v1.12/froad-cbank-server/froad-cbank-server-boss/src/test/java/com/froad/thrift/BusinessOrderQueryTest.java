package com.froad.thrift;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.BusinessOrderQueryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.Business.BusinessOrderListReq;
import com.froad.thrift.vo.Business.BusinessOrderListRes;
import com.froad.thrift.vo.Business.BusinessOrderPyamentInfoRes;
import com.froad.thrift.vo.Business.BusinessOrderRefundInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderShippingInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderTradeInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderVo;
import com.froad.util.Checker;

public class BusinessOrderQueryTest {

	public static void main(String[] args) throws TException, ParseException {
		// 本地环境
		TSocket transport = new TSocket("127.0.0.1", 16001);
		transport.open();
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol,BusinessOrderQueryService.class.getSimpleName());
		BusinessOrderQueryService.Iface orderService = new BusinessOrderQueryService.Client(multiplexedProtocol);
		//queryBusinessOrderList(orderService);//查询运营订单列表
		//exportBusinessOrder(orderService);//运营订单列表导出
		//queryBusinessOrderPaymentInfo(orderService);//查询运营订单支付信息
		//queryBusinessOrderTradeInfo(orderService);//查询运营订单交易概要信息
		//queryBusinessOrderShippingInfo(orderService);//查询运营订单物流信息
		queryBusinessOrderRefundInfo(orderService);//查询运营订退货信息
	}

	/**查询运营订单列表*/
	public static void queryBusinessOrderList(BusinessOrderQueryService.Iface orderService) throws TException, ParseException {
		BusinessOrderListReq req = new BusinessOrderListReq();
		req.setClientId("chongqing");
		req.setOrderId("0D82B641800B");
		//req.setMemberCode(50000362098l);
		//req.setShippingStatus("");
		//req.setPaymentMethod("");
		req.setOrderStatus("3");
		req.setCreateStartTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-01").getTime()); // 开始时间
		req.setCreateEndTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-20").getTime()); // 结束时间
		
		PageVo pageVo = new PageVo();
		pageVo.setPageSize(10);
		pageVo.setPageNumber(1);
		//pageVo.setLastPageNumber(lastPageNumber);
		//pageVo.setLastRecordTime();
		//pageVo.setFirstRecordTime();
		req.setPageVo(pageVo);
		
		OriginVo originVo = new OriginVo();
		originVo.setClientId("chongqing");
		originVo.setDescription("运营订单列表查询");
		originVo.setOperatorId(2011007);
		originVo.setPlatType(PlatType.boss);
		originVo.setOperatorIp("127.0.0.1");
		BusinessOrderListRes res = orderService.queryBusinessOrderList(req);
		System.out.println(res.getResultVo().getResultDesc());
		System.out.println(res.getPageVo());
		
		if (res.getVoList() != null && res.getVoList().size() > 0) {
			for (BusinessOrderVo vo : res.getVoList()) {
				System.out.println(vo);
			}
		}
		
	}
	
	/**运营订单列表导出*/
	public static void exportBusinessOrder(BusinessOrderQueryService.Iface orderService) throws TException, ParseException {
		BusinessOrderListReq req = new BusinessOrderListReq();
		req.setClientId("chongqing");
		req.setOrderId("0D82B641800B");
		//req.setMemberCode();
		//req.setShippingStatus("");
		req.setPaymentMethod("");
		req.setOrderStatus("3");
		req.setCreateStartTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-01").getTime()); // 开始时间
		req.setCreateEndTime(new SimpleDateFormat("yyyy-MM-dd").parse("2015-08-20").getTime()); // 结束时间
		
		PageVo pageVo = new PageVo();
		pageVo.setPageSize(5);
		pageVo.setPageNumber(1);
		//pageVo.setLastPageNumber(lastPageNumber);
		//pageVo.setLastRecordTime();
		//pageVo.setFirstRecordTime();
		req.setPageVo(pageVo);
		OriginVo originVo = new OriginVo();
		originVo.setClientId("chongqing");
		originVo.setDescription("运营订单列表查询");
		originVo.setOperatorId(2011007);
		originVo.setPlatType(PlatType.boss);
		originVo.setOperatorIp("127.0.0.1");
		
		ExportResultRes res = orderService.exportBusinessOrder(req);
		System.out.println(res.getResultVo().getResultDesc());
		System.out.println(res.getUrl());
	}
	
	/**查询运营订单支付信息*/
	public static void queryBusinessOrderPaymentInfo(BusinessOrderQueryService.Iface orderService) throws TException {
		String clientId = "chongqing";
		String subOrderId = "0D82B6418003";
		
		OriginVo originVo = new OriginVo();
		originVo.setClientId("chongqing");
		originVo.setDescription("运营订单列表查询");
		originVo.setOperatorId(2011007);
		originVo.setPlatType(PlatType.boss);
		originVo.setOperatorIp("127.0.0.1");
		
		BusinessOrderPyamentInfoRes res = orderService.queryBusinessOrderPaymentInfo(clientId, subOrderId);
		System.out.println(res.getResultVo().getResultDesc());
		System.out.println(res.getInfoVo());
	}
	
	/**查询运营订单交易概要信息 */
	public static void queryBusinessOrderTradeInfo(BusinessOrderQueryService.Iface orderService) throws TException {
		String clientId = "chongqing";
		String subOrderId = "0D82B6418003";
		
		OriginVo originVo = new OriginVo();
		originVo.setClientId("chongqing");
		originVo.setDescription("运营订单列表查询");
		originVo.setOperatorId(2011007);
		originVo.setPlatType(PlatType.boss);
		originVo.setOperatorIp("127.0.0.1");
		
		BusinessOrderTradeInfoVoRes res = orderService.queryBusinessOrderTradeInfo(clientId, subOrderId);
//		System.out.println(res.getResultVo().getResultCode());
//		if (Checker.isNotEmpty(res.getInfoVo())) {
//			System.out.println(res.getInfoVo());
//		}
		
	}
	
	/**查询运营订单物流信息*/
	public static void queryBusinessOrderShippingInfo(BusinessOrderQueryService.Iface orderService) throws TException {
		String clientId = "";
		String subOrderId = "";
		
		OriginVo originVo = new OriginVo();
		originVo.setClientId("chongqing");
		originVo.setDescription("运营订单列表查询");
		originVo.setOperatorId(2011007);
		originVo.setPlatType(PlatType.boss);
		originVo.setOperatorIp("127.0.0.1");
		
		BusinessOrderShippingInfoVoRes res = orderService.queryBusinessOrderShippingInfo(clientId, subOrderId);
		System.out.println(res.getResultVo().getResultDesc());
		System.out.println(res.getInfoVo());
	}
	
	/**查询运营订单退款信息 */
	public static void queryBusinessOrderRefundInfo(BusinessOrderQueryService.Iface orderService) throws TException {
		String clientId = "chongqing";
		String subOrderId = "0D821DA1800A";
		
		OriginVo originVo = new OriginVo();
		originVo.setClientId("chongqing");
		originVo.setDescription("运营订单列表查询");
		originVo.setOperatorId(2011007);
		originVo.setPlatType(PlatType.boss);
		originVo.setOperatorIp("127.0.0.1");
		
		BusinessOrderRefundInfoVoRes res = orderService.queryBusinessOrderRefundInfo(clientId, subOrderId);
		System.out.println(res.getResultVo().getResultDesc());
		System.out.println(res.getInfoVo());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 时间转换方法
	 * 
	 *//*
	public static void main(String[] args) throws ParseException {
		//long startTime = new SimpleDateFormat("yyyy-MM-dd").parse("2015-01-01").getTime(); // 开始时间
		//long endTime = new SimpleDateFormat("yyyy-MM-dd").parse("2015-11-10").getTime(); // 结束时间
		long startTime = 1439448683000l;
		System.out.println("startTime:" + startTime);
		//System.out.println("endTime:" + endTime);
		
		Date date = new Date();
		String firstTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(startTime));
		//String lastTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(endTime));
		System.out.println("firstTime:" + firstTime);
		//System.out.println("lastTime:" + lastTime);
	}*/
}
