package com.froad.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.ReportService;
import com.froad.thrift.service.impl.ReportServiceImpl;
import com.froad.util.PropertiesUtil;
 
/**
 * @author bruce
 *
 */
public class ServerRun extends BaseServer{

	private int SERVER_PORT = PropertiesUtil.port;

	 /** 
	 * @Title: run 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @return void    返回类型 
	 * @throws 
	 */ 
	public void run(){
		
		try { 
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			
			// 线程池服务模型
			TServer server = new TThreadPoolServer(
					new TThreadPoolServer.Args(t).processor(processor));
	 
			//资源Service
			processor.registerProcessor(ReportService.class.getSimpleName(),
					new ReportService.Processor<ReportService.Iface>((ReportService.Iface) new BizMonitor(this).wrapper(new ReportServiceImpl(ReportService.class.getSimpleName(),"1.0"))));
		 
			
			LogCvt.info("the coremodule serveris started and is listening at "+SERVER_PORT+"...");
	
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