package com.froad.fft.test.thirdparty;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.froad.fft.common.AppException;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiOrderDetail;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.CheckType;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.Client;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.OrderType;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.PayDirect;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.PayeeType;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.PayeeWay;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.PayerType;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.PayerWay;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.RefundType;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.ReqType;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq.TransferType;
import com.froad.fft.thirdparty.request.openapi.impl.OpenApiFuncImpl;

public class OpenApiFunc_Test {

	private static Logger logger = Logger.getLogger(OpenApiFunc_Test.class);
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:context/**/*.xml");
		OpenApiFuncImpl openApiFuncImpl = (OpenApiFuncImpl) context.getBean("openApiFuncImpl");
		
//		logger.info("\n退款申请测试  开始");
//		try {
//			System.out.println(openApiFuncImpl.refundCurrency(new OpenApiReq("fbu100349962x101348642", "fbu100349962x101348642", "1.00", "1.00", RefundType.ALL, "orderSupplier", "测试退款接口")).getResultDesc());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		logger.info("\n退款申请测试  结束");
//		
//		logger.info("\n校验查询测试  开始");
//		try {
//			System.out.println(openApiFuncImpl.accountCheck(new OpenApiReq("243", CheckType.ACCOUNT_MOBILE, "13111111111", "测试")).getResultCode());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		logger.info("\n校验查询测试  结束");
//		
//		logger.info("\n转账测试  开始");
//		try {
//			System.out.println(openApiFuncImpl.transferCurrency(new OpenApiReq("100001002", TransferType.ONE,"1", "0000053868464012", "上海方付通商务服务有限公司珠海分公司", "0000053868464011", "小二", "测试", Client.PC,"")).getResultCode());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		logger.info("\n转账测试  结束");
//		
//		logger.info("\n代收代扣测试  开始");
//		try {
//			System.out.println(openApiFuncImpl.agencyCollectOrDeduct(
//					new OpenApiReq("100009001", OrderType.ORDER_TYPE_CASH, "1.00", "测试", PayType.FROAD_CARD, "000", PayerType.CUSTOMER, PayerWay.BANK_NUM, "NONE|0000053868464010|张三", "1.00", PayeeType.MERCHANT, PayeeWay.BANK_NUM, "NONE|0000053868464012|上海方付通商务服务有限公司珠海分公司", "1.00", Client.PC,ReqType.COLLECT)
//					).getResultCode());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		logger.info("\n代收代扣测试  结束");
//		
//		logger.info("\n合并支付测试  开始");
//		try {
//			List<OpenApiOrderDetail> orderDetails = new ArrayList<OpenApiOrderDetail>();
//			orderDetails.add(new OpenApiOrderDetail("20140321857819128", "100.00", "10000004021", "方付通|"));
//			orderDetails.add(new OpenApiOrderDetail("20140321758819410", "200.00", "10000004021", "方付通|"));
//			System.out.println(openApiFuncImpl.combineTransaction(new OpenApiReq(orderDetails, OrderType.ORDER_TYPE_ALIPAY, "300.00", OrderCurrency.RMB, PayType.ALIPAY, "700", "13527459070",PayDirect.F_MERCHANT,Client.PC,"展示")).getPaymentURL());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
//		logger.info("\n合并支付测试  结束");
	}
}
