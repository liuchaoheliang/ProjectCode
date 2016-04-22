package com.froad.test.ticket;

import java.util.Date;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.alibaba.fastjson.JSONObject;
import com.froad.enums.CashType;
import com.froad.enums.PaymentMethod;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.service.RefundService;
import com.froad.thrift.service.TicketService;
import com.froad.thrift.vo.payment.DoPayOrdersVoReq;
import com.froad.thrift.vo.payment.PaymentExceptionType;
import com.froad.thrift.vo.payment.PaymentQueryExcetionVo;
import com.froad.thrift.vo.payment.PaymentQueryVo;
import com.froad.thrift.vo.refund.RefundTicketVo;
import com.froad.thrift.vo.refund.RefundTicketsRequestVo;
import com.froad.util.PropertiesUtil;


public class TicketClient_ {

public static void main(String[] args) throws TException {
		
//		String ip = "10.43.1.9";

//		String ip = "10.43.2.3";
		String ip = "127.0.0.1";
		
		TSocket transport = new TSocket(ip, 15501);
		
		transport.open();
		
        TBinaryProtocol protocol = new TBinaryProtocol(transport);
        
        TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol,RefundService.class.getSimpleName());
        
        RefundService.Client service = new RefundService.Client(multiplexedProtocol);

        RefundTicketsRequestVo requestVo = new RefundTicketsRequestVo();
        RefundTicketVo elem = new RefundTicketVo();
        elem.setClientId("anhui");
        elem.setQuantity(10);
        elem.setProductId("06ADAEA20000");
        elem.setSuborderId("06ADB5C38000");
        elem.setTicketId("019468476414");
        requestVo.addToTicketList(elem);
        System.out.println(JSONObject.toJSONString(service.refundTickets(requestVo)));
        transport.close();
	}
}
