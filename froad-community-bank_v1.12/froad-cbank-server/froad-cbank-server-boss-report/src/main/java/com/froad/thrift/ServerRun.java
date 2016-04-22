package com.froad.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.util.IPUtil;
import com.froad.util.PropertiesUtil;

/**
 * 多接口服务
 * @ClassName: MainRun
 * @Description: TODO
 * @author FQ
 * @date 2015年3月11日 下午11:44:22
 *
 */
public class ServerRun extends BaseServer implements Runnable {

	private int SERVER_PORT = PropertiesUtil.port;

    public void run(){
		try {
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			String ip = "";
			try{
	            ip = IPUtil.getLocalIp();
	            LogCvt.info("the service register at " + ip + ":" + SERVER_PORT + "...");
	        } catch(Exception e){
	        	LogCvt.error("the service register " + ip + ":" + SERVER_PORT + " error ...");
	        }
			
			// 线程池服务模型
			TServer server = new TThreadPoolServer(
					new TThreadPoolServer.Args(t).processor(processor));

			//processor.registerProcessor(ReportMerchantInfoService.class.getSimpleName(), 
			//		new ReportMerchantInfoService.Processor<ReportMerchantInfoService.Iface>((ReportMerchantInfoService.Iface)new BizMonitor(this).wrapper(new ReportMerchantInfoServiceImpl())));
			
			
			LogCvt.info("the service started and is listening at " + SERVER_PORT + "...");

			server.serve();
		} catch (Exception e) {
			LogCvt.error("启动thrift服务异常", e);
		}
	}
    

    @Override
    public void reinitialize() {
        
    }

    @Override
    public void stop() {
        
    }

}
