package com.froad.test.payment;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSONObject;
import com.froad.enums.CashType;
import com.froad.enums.PaymentMethod;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.vo.payment.DoPayOrdersVoReq;

public class PaymentClientToServer {

	
	public static void main(String[] args) throws TException {
		
//		String ip = "10.43.1.123";
		String ip = "127.0.0.1";

//		String ip = "dev.thankjava.com";
		
		TSocket transport = new TSocket(ip, 15501);
		
		transport.open();
		
        TBinaryProtocol protocol = new TBinaryProtocol(transport);
        
        TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol,PaymentService.class.getSimpleName());
        
//        MemberInformationService.Client service = new MemberInformationService.Client(multiplexedProtocol);
//        BankCardService.Client service = new BankCardService.Client(multiplexedProtocol);
        PaymentService.Client service = new PaymentService.Client(multiplexedProtocol);
        DoPayOrdersVoReq req = new DoPayOrdersVoReq();
		req.setPayType(Integer.valueOf(PaymentMethod.froadPointsAndCash.getCode()));
		req.setCashAmount(86.02);
		req.setCashOrgNo("243");
		req.setCashType(CashType.foil_card.code());
		req.setFoilCardNum("13527459070");
		req.setPointOrgNo("100010002");
		req.setPointAmount(47);
		req.setClientId("chongqing");
		req.setOrderId("13076AB20000");
//        
//        req.setPayType(Integer.valueOf(PaymentMethod.cash.getCode()));
////		req.setPointAmount(85.97);
////		req.setPointOrgNo("100010002");
//		req.setCashAmount(2085.97);
//		req.setCashOrgNo("243");
//		req.setCashType(CashType.foil_card.code());
//		req.setClientId(1000);
//		req.setFoilCardNum("13527459070");
//		req.setOrderId("01299A888000");
        
        
		System.out.println(JSONObject.toJSONString(service.doPayOrders(req)));
		
//        System.out.println(JSONObject.toJSONString(service.verifyPaymentResultForTask("022669F38000")));
        
//        PaymentQueryVo vo = new PaymentQueryVo();
//		vo.setPageSize(20);
//		vo.setPageIndex(1);
//		vo.setFromUserName("T哪有女流氓");
////		vo.setEndTime(new Date().getTime());
////		vo.setStartTime(new Date().getTime());
//		System.out.println(JSONObject.toJSONString(service.queryPaymentForBoss(vo)));

//        System.out.println(JSONObject.toJSONString(service.selectUserByMemberCode(40000000232L,"anhui")));
//        System.out.println(JSONObject.toJSONString(service.bankCardAccountCheck("chongqing", "", "", "", "")));

        
        
//        System.out.print(JSONObject.toJSONString(service.signBankCardByClientId("taizhou", 52001970709L, "6224271190391825", "234", "511423198509280038", "13533669988", "", "", "", "", "8880000080639688")));
//        System.out.print(JSONObject.toJSONString(service.cancelSignedBankCard("taizhou", 52001970709L, "6224271190391825")));
        transport.close();
	}
}
