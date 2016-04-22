package com.froad.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.UserService;
import com.froad.thrift.service.impl.UserServiceImpl;

public class ServerRun1 extends BaseServer{

	private static final int SERVER_PORT = 9990;

	public void run(){
		try {
			
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			
	        //传输通道 - 非阻塞方式    
	        TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(SERVER_PORT);  
	          
	        //多线程半同步半异步  
	        TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);  
	        
	        tArgs.transportFactory(new TFramedTransport.Factory());  
	        //二进制协议  
	        tArgs.protocolFactory(new TBinaryProtocol.Factory());
	        // 多线程半同步半异步的服务模型  
	        TServer server = new TThreadedSelectorServer(tArgs);  
	        
	        UserService.Iface uIface=(UserService.Iface) new BizMonitor(this).wrapper(new UserServiceImpl("UserService","1.0"));
			TProcessor userProcessor = new UserService.Processor<UserService.Iface>(uIface);  
	        processor.registerProcessor("UserService",userProcessor);
	        
	        System.out.println("the serveris started and is listening at 9990...");

	        server.serve(); // 启动服务  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reinitialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
