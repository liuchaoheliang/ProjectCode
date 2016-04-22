package com.froad.test.payment;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.froad.logback.LogCvt;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.service.PaymentService.Processor;
import com.froad.thrift.service.impl.PaymentServiceImpl;
import com.froad.util.PropertiesUtil;
import com.froad.util.payment.Logger;



public class PaymentServer {
	
	private final static int PORT = 8899;
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		
		System.out.println("测试--> 支付服务启动  端口 : " + PORT);
		PaymentServer server = new PaymentServer();
		server.startServer();
	}

	public void startServer() {
		try {
			
			TServerSocket serverTransport = new TServerSocket(PORT);
			PaymentService.Processor process = new Processor(new PaymentServiceImpl());
			Factory portFactory = new TBinaryProtocol.Factory(true, true);
			Args args = new Args(serverTransport);
			args.processor(process);
			args.protocolFactory(portFactory);
			TServer server = new TThreadPoolServer(args);
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		}
	}
}
