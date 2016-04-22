package com.froad.test.refund;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import com.froad.thrift.service.RefundService;
import com.froad.thrift.service.RefundService.Processor;
import com.froad.thrift.service.impl.RefundServiceImpl;

public class RefundServer {

	private final static int PORT = 8899;
	
	public static void main(String[] args) {
		System.out.println("【测试 退款服务启动 端口 : " + PORT + "】");
		RefundServer server = new RefundServer();
		server.startServer();
	}

	public void startServer() {
		try {
			TServerSocket serverTransport = new TServerSocket(PORT);
			RefundService.Processor process = new Processor(new RefundServiceImpl());
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
