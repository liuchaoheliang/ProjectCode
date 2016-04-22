package com.froad.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.ActiveRuleInfoService;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.service.ActiveSearchService;
import com.froad.thrift.service.ActiveSustainRelationService;
import com.froad.thrift.service.RegisteredRuleInfoService;
import com.froad.thrift.service.RegisteredRunService;
import com.froad.thrift.service.VouchersRuleInfoService;
import com.froad.thrift.service.VouchersRunService;
import com.froad.thrift.service.VouchersSearchService;
import com.froad.thrift.service.impl.ActiveRuleInfoServiceImpl;
import com.froad.thrift.service.impl.ActiveRunServiceImpl;
import com.froad.thrift.service.impl.ActiveSearchServiceImpl;
import com.froad.thrift.service.impl.ActiveSustainRelationServiceImpl;
import com.froad.thrift.service.impl.RegisteredRuleInfoServiceImpl;
import com.froad.thrift.service.impl.RegisteredRunServiceImpl;
import com.froad.thrift.service.impl.VouchersRuleInfoServiceImpl;
import com.froad.thrift.service.impl.VouchersRunServiceImpl;
import com.froad.thrift.service.impl.VouchersSearchServiceImpl;
import com.froad.util.PropertiesUtil;

/**
 * 多接口服务
 * @ClassName: MainRun
 * @Description: Thrift服务主进程
 * @author FQ - lf
 * @date 2015年11月04日 下午18:13:45 
 *
 */
public class ServerRun extends BaseServer {

	private int SERVER_PORT = PropertiesUtil.port;

	public ServerRun(){
		
	}
	
	public void run() {
		try {
			
			
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			
			// 线程池服务模型
			TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(t).processor(processor));

			/**
			 * 发布-部署服务
			 * 两种方式任选其一
			 * */
            // 1
//            processor.registerProcessor(PaymentService.class.getSimpleName(), 
//            		new PaymentService.Processor<PaymentService.Iface>(
//            				new PaymentServiceImpl()));
            // 1
//            processor.registerProcessor(PaymentService.class.getSimpleName(),
//					new PaymentService.Processor<PaymentService.Iface>((PaymentService.Iface) new BizMonitor(this).wrapper(new PaymentServiceImpl(PaymentService.class.getSimpleName(),"1.0"))));
			
			// 2
//			AdLocationService.Iface adLocationServiceIface=(AdLocationService.Iface) new BizMonitor(this).wrapper(new AdLocationServiceImpl("AdLocationService","1.0"));
//			processor.registerProcessor("AdLocationService",new AdLocationService.Processor<AdLocationService.Iface>(adLocationServiceIface));
			
			// 2
//			AdvertisingService.Iface advertisingServiceIface=(AdvertisingService.Iface) new BizMonitor(this).wrapper(new AdvertisingServiceImpl("AdvertisingService","1.0"));
//			processor.registerProcessor("AdvertisingService",new AdvertisingService.Processor<AdvertisingService.Iface>(advertisingServiceIface));
            
			ActiveRunService.Iface activeRunServiceIface=(ActiveRunService.Iface) new BizMonitor(this).wrapper(new ActiveRunServiceImpl("ActiveRunService","1.0"));
			processor.registerProcessor(ActiveRunService.class.getSimpleName(),new ActiveRunService.Processor<ActiveRunService.Iface>(activeRunServiceIface));

			ActiveRuleInfoService.Iface activeRuleInfoServiceIface=(ActiveRuleInfoService.Iface) new BizMonitor(this).wrapper(new ActiveRuleInfoServiceImpl("ActiveRuleInfoService","1.0"));
			processor.registerProcessor("ActiveRuleInfoService",new ActiveRuleInfoService.Processor<ActiveRuleInfoService.Iface>(activeRuleInfoServiceIface));

			ActiveSearchService.Iface activeSearchServiceIface=(ActiveSearchService.Iface) new BizMonitor(this).wrapper(new ActiveSearchServiceImpl("ActiveSearchService","1.0"));
			processor.registerProcessor("ActiveSearchService",new ActiveSearchService.Processor<ActiveSearchService.Iface>(activeSearchServiceIface));

			VouchersRunService.Iface vouchersRunServiceIface=(VouchersRunService.Iface) new BizMonitor(this).wrapper(new VouchersRunServiceImpl("VouchersRunService","1.0"));
			processor.registerProcessor("VouchersRunService",new VouchersRunService.Processor<VouchersRunService.Iface>(vouchersRunServiceIface));
			
			VouchersRuleInfoService.Iface vouchersruleServiceIface=(VouchersRuleInfoService.Iface) new BizMonitor(this).wrapper(new VouchersRuleInfoServiceImpl("VouchersRuleInfoService","1.0"));
			processor.registerProcessor("VouchersRuleInfoService",new VouchersRuleInfoService.Processor<VouchersRuleInfoService.Iface>(vouchersruleServiceIface));
			
			VouchersSearchService.Iface vouchersSearchService=(VouchersSearchService.Iface) new BizMonitor(this).wrapper(new VouchersSearchServiceImpl("VouchersSearchService","1.0"));
			processor.registerProcessor("VouchersSearchService",new VouchersSearchService.Processor<VouchersSearchService.Iface>(vouchersSearchService));
			
			RegisteredRuleInfoService.Iface registeredRuleInfoService=(RegisteredRuleInfoService.Iface) new BizMonitor(this).wrapper(new RegisteredRuleInfoServiceImpl("RegisteredRuleInfoService","1.0"));
			processor.registerProcessor("RegisteredRuleInfoService",new RegisteredRuleInfoService.Processor<RegisteredRuleInfoService.Iface>(registeredRuleInfoService));
			
			RegisteredRunService.Iface registeredRunService=(RegisteredRunService.Iface) new BizMonitor(this).wrapper(new RegisteredRunServiceImpl("RegisteredRunService","1.0"));
			processor.registerProcessor("RegisteredRunService",new RegisteredRunService.Processor<RegisteredRunService.Iface>(registeredRunService));
			
			ActiveSustainRelationService.Iface activeSustainRelationService=(ActiveSustainRelationService.Iface) new BizMonitor(this).wrapper(new ActiveSustainRelationServiceImpl("activeSustainRelationService","1.0"));
			processor.registerProcessor("ActiveSustainRelationService",new ActiveSustainRelationService.Processor<ActiveSustainRelationService.Iface>(activeSustainRelationService));
			
			LogCvt.info("the serveris started and is listening at "+SERVER_PORT+"...");
			LogCvt.info("营销活动模块服务启动, 端口号为[" + SERVER_PORT + "]...");
			
			server.serve();
			
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error("订单模块服务启动失败, 原因:" + e.getMessage(), e);
		}
	}

	
	/**         
	 * @see com.froad.thrift.monitor.server.BaseServer#reinitialize()    
	 */
	
	@Override
	public void reinitialize() {
		// TODO Auto-generated method stub
		
	}

	
	/**         
	 * @see com.froad.thrift.monitor.server.BaseServer#stop()    
	 */
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
