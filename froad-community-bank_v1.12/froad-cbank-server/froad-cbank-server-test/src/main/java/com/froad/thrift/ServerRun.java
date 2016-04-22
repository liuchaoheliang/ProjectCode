package com.froad.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.UserService;
import com.froad.thrift.service.impl.UserServiceImpl;

public class ServerRun extends BaseServer{

	private static final int SERVER_PORT = 9999;

	public void run(){
		try {
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			
			TThreadPoolServer.Args tArgs=new TThreadPoolServer.Args(t).processor(processor);
			
			tArgs.maxWorkerThreads(10);
			
			//二进制协议 
	        tArgs.protocolFactory(new TBinaryProtocol.Factory());  
	        
	        // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。
			TServer server = new TThreadPoolServer(tArgs);

			UserService.Iface uIface=(UserService.Iface) new BizMonitor(this).wrapper(new UserServiceImpl("UserService","1.0"));
			processor.registerProcessor("UserService",new UserService.Processor<UserService.Iface>(uIface));
			
			System.out.println("the serveris started and is listening at 9999...");

			server.serve();
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
