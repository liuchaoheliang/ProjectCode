package com.froad.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.ReportMerchantContractService;
import com.froad.thrift.service.ReportMerchantInfoService;
import com.froad.thrift.service.ReportProductInfoService;
import com.froad.thrift.service.ReportProductService;
import com.froad.thrift.service.ReportUserService;
import com.froad.thrift.service.impl.ReportMerchantContractServiceImpl;
import com.froad.thrift.service.impl.ReportMerchantInfoServiceImpl;
import com.froad.thrift.service.impl.ReportProductInfoServiceImpl;
import com.froad.thrift.service.impl.ReportProductServiceImpl;
import com.froad.thrift.service.impl.ReportUserServiceImpl;
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

			processor.registerProcessor(ReportMerchantInfoService.class.getSimpleName(), 
					new ReportMerchantInfoService.Processor<ReportMerchantInfoService.Iface>((ReportMerchantInfoService.Iface)new BizMonitor(this).wrapper(new ReportMerchantInfoServiceImpl())));
			
			processor.registerProcessor(ReportProductInfoService.class.getSimpleName(), 
					new ReportProductInfoService.Processor<ReportProductInfoService.Iface>((ReportProductInfoService.Iface)new BizMonitor(this).wrapper(new ReportProductInfoServiceImpl())));
			
			processor.registerProcessor(ReportMerchantContractService.class.getSimpleName(), 
					new ReportMerchantContractService.Processor<ReportMerchantContractService.Iface>((ReportMerchantContractService.Iface)new BizMonitor(this).wrapper(new ReportMerchantContractServiceImpl())));
			
			
			processor.registerProcessor(ReportProductService.class.getSimpleName(), 
					new ReportProductService.Processor<ReportProductService.Iface>((ReportProductService.Iface)new BizMonitor(this).wrapper(new ReportProductServiceImpl())));
			
			processor.registerProcessor(ReportUserService.class.getSimpleName(), 
					new ReportUserService.Processor<ReportUserService.Iface>((ReportUserService.Iface)new BizMonitor(this).wrapper(new ReportUserServiceImpl())));
			
            
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
