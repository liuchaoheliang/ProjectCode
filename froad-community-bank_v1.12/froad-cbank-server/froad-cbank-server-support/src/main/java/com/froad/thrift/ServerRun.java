package com.froad.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.ActivitiesService;
import com.froad.thrift.service.AdLocationService;
import com.froad.thrift.service.AdPositionService;
import com.froad.thrift.service.AdService;
import com.froad.thrift.service.AdvertisingService;
import com.froad.thrift.service.AgreementService;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.BossRoleService;
import com.froad.thrift.service.BossSenseWordsService;
import com.froad.thrift.service.BossUserService;
import com.froad.thrift.service.CashService;
import com.froad.thrift.service.DeliveryCorpService;
import com.froad.thrift.service.DeliveryWayBillService;
import com.froad.thrift.service.DictionaryService;
import com.froad.thrift.service.HotWordService;
import com.froad.thrift.service.SenseWordsService;
import com.froad.thrift.service.SmsContentService;
import com.froad.thrift.service.SmsLogService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.service.TerminalStartService;
import com.froad.thrift.service.VIPWhiteListService;
import com.froad.thrift.service.impl.ActivitiesServiceImpl;
import com.froad.thrift.service.impl.AdLocationServiceImpl;
import com.froad.thrift.service.impl.AdPositionServiceImpl;
import com.froad.thrift.service.impl.AdServiceImpl;
import com.froad.thrift.service.impl.AdvertisingServiceImpl;
import com.froad.thrift.service.impl.AgreementServiceImpl;
import com.froad.thrift.service.impl.AreaServiceImpl;
import com.froad.thrift.service.impl.BossRoleServiceImpl;
import com.froad.thrift.service.impl.BossSenseWordsServiceImpl;
import com.froad.thrift.service.impl.BossUserServiceImpl;
import com.froad.thrift.service.impl.CashServiceImpl;
import com.froad.thrift.service.impl.DeliveryCorpServiceImpl;

import com.froad.thrift.service.impl.DictionaryServiceImpl;

import com.froad.thrift.service.impl.DeliveryWayBillServiceImpl;
import com.froad.thrift.service.impl.DictionaryServiceImpl;

import com.froad.thrift.service.impl.HotWordServiceImpl;
import com.froad.thrift.service.impl.SenseWordsServiceImpl;
import com.froad.thrift.service.impl.SmsContentServiceImpl;
import com.froad.thrift.service.impl.SmsLogServiceImpl;
import com.froad.thrift.service.impl.SmsMessageServiceImpl;
import com.froad.thrift.service.impl.TerminalStartServiceImpl;
import com.froad.thrift.service.impl.VIPWhiteListServiceImpl;
import com.froad.util.PropertiesUtil;

/**
 * 多接口服务
 * @ClassName: MainRun
 * @Description: TODO
 * @author FQ
 * @date 2015年3月11日 下午11:44:22
 *
 */
public class ServerRun extends BaseServer {

	private static final int SERVER_PORT = PropertiesUtil.port;
	
	public void run(){
		try {
			
			
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			
			// 线程池服务模型
			TServer server = new TThreadPoolServer(
					new TThreadPoolServer.Args(t).processor(processor));

			ActivitiesService.Iface activitiesIface=(ActivitiesService.Iface) new BizMonitor(this).wrapper(new ActivitiesServiceImpl("ActivitiesService","1.0"));
			processor.registerProcessor("ActivitiesService",new ActivitiesService.Processor<ActivitiesService.Iface>(activitiesIface));
			

			AdPositionService.Iface adPositionIface=(AdPositionService.Iface) new BizMonitor(this).wrapper(new AdPositionServiceImpl("AdPositionService","1.0"));
			processor.registerProcessor("AdPositionService",new AdPositionService.Processor<AdPositionService.Iface>(adPositionIface));
			
			AgreementService.Iface agreementIface=(AgreementService.Iface) new BizMonitor(this).wrapper(new AgreementServiceImpl("AgreementService","1.0"));
			processor.registerProcessor("AgreementService",new AgreementService.Processor<AgreementService.Iface>(agreementIface));
			
			AdService.Iface adIface=(AdService.Iface) new BizMonitor(this).wrapper(new AdServiceImpl("AdService","1.0"));
			processor.registerProcessor("AdService",new AdService.Processor<AdService.Iface>(adIface));
			
			AreaService.Iface areaIface=(AreaService.Iface) new BizMonitor(this).wrapper(new AreaServiceImpl("AreaService","1.0"));
			processor.registerProcessor("AreaService",new AreaService.Processor<AreaService.Iface>(areaIface));
			
			DeliveryCorpService.Iface deliveryCorpIface=(DeliveryCorpService.Iface) new BizMonitor(this).wrapper(new DeliveryCorpServiceImpl("DeliveryCorpService","1.0"));
			processor.registerProcessor("DeliveryCorpService",new DeliveryCorpService.Processor<DeliveryCorpService.Iface>(deliveryCorpIface));
			
			SmsContentService.Iface smsContentIface=(SmsContentService.Iface) new BizMonitor(this).wrapper(new SmsContentServiceImpl("SmsContentService","1.0"));
			processor.registerProcessor("SmsContentService",new SmsContentService.Processor<SmsContentService.Iface>(smsContentIface));
			
			SmsLogService.Iface smsLogIface=(SmsLogService.Iface) new BizMonitor(this).wrapper(new SmsLogServiceImpl("SmsLogService","1.0"));
			processor.registerProcessor("SmsLogService",new SmsLogService.Processor<SmsLogService.Iface>(smsLogIface));
			
			SmsMessageService.Iface smsMessageIface=(SmsMessageService.Iface) new BizMonitor(this).wrapper(new SmsMessageServiceImpl("SmsMessageService","1.0"));
			processor.registerProcessor("SmsMessageService",new SmsMessageService.Processor<SmsMessageService.Iface>(smsMessageIface));
			
			CashService.Iface cashIface=(CashService.Iface) new BizMonitor(this).wrapper(new CashServiceImpl("CashService","1.0"));
			processor.registerProcessor("CashService",new CashService.Processor<CashService.Iface>(cashIface));
			
			BossSenseWordsService.Iface bossSenseWordsIface=(BossSenseWordsService.Iface) new BizMonitor(this).wrapper(new BossSenseWordsServiceImpl("BossSenseWordsService","1.0"));
			processor.registerProcessor("BossSenseWordsService",new BossSenseWordsService.Processor<BossSenseWordsService.Iface>(bossSenseWordsIface));
			
			BossUserService.Iface bossUserServiceIface=(BossUserService.Iface) new BizMonitor(this).wrapper(new BossUserServiceImpl("BossUserService","1.0"));
			processor.registerProcessor("BossUserService",new BossUserService.Processor<BossUserService.Iface>(bossUserServiceIface));
			
			BossRoleService.Iface bossRoleServiceIface=(BossRoleService.Iface) new BizMonitor(this).wrapper(new BossRoleServiceImpl("BossRoleService","1.0"));
			processor.registerProcessor("BossRoleService",new BossRoleService.Processor<BossRoleService.Iface>(bossRoleServiceIface));
			
			SenseWordsService.Iface senseWordsServiceIface=(SenseWordsService.Iface) new BizMonitor(this).wrapper(new SenseWordsServiceImpl("SenseWordsService","1.0"));
			processor.registerProcessor("SenseWordsService",new SenseWordsService.Processor<SenseWordsService.Iface>(senseWordsServiceIface));
			
			TerminalStartService.Iface terminalStartServiceIface=(TerminalStartService.Iface) new BizMonitor(this).wrapper(new TerminalStartServiceImpl("TerminalStartService","1.0"));
			processor.registerProcessor("TerminalStartService",new TerminalStartService.Processor<TerminalStartService.Iface>(terminalStartServiceIface));
			
			HotWordService.Iface hotWordServiceIface=(HotWordService.Iface) new BizMonitor(this).wrapper(new HotWordServiceImpl("HotWordService","1.0"));
			processor.registerProcessor("HotWordService",new HotWordService.Processor<HotWordService.Iface>(hotWordServiceIface));
			
			AdLocationService.Iface adLocationServiceIface=(AdLocationService.Iface) new BizMonitor(this).wrapper(new AdLocationServiceImpl("AdLocationService","1.0"));
			processor.registerProcessor("AdLocationService",new AdLocationService.Processor<AdLocationService.Iface>(adLocationServiceIface));
			
			AdvertisingService.Iface advertisingServiceIface=(AdvertisingService.Iface) new BizMonitor(this).wrapper(new AdvertisingServiceImpl("AdvertisingService","1.0"));
			processor.registerProcessor("AdvertisingService",new AdvertisingService.Processor<AdvertisingService.Iface>(advertisingServiceIface));
			

			DictionaryService.Iface dictionaryService=(DictionaryService.Iface) new BizMonitor(this).wrapper(new DictionaryServiceImpl("DictionaryService","1.0"));
			processor.registerProcessor("DictionaryService",new DictionaryService.Processor<DictionaryService.Iface>(dictionaryService));

			DeliveryWayBillService.Iface deliveryWayBillServiceIface=(DeliveryWayBillService.Iface) new BizMonitor(this).wrapper(new DeliveryWayBillServiceImpl("DeliveryWayBillService","1.0"));
			processor.registerProcessor("DeliveryWayBillService",new DeliveryWayBillService.Processor<DeliveryWayBillService.Iface>(deliveryWayBillServiceIface));

			VIPWhiteListService.Iface vipWhiteListService=(VIPWhiteListService.Iface) new BizMonitor(this).wrapper(new VIPWhiteListServiceImpl(VIPWhiteListService.class.getSimpleName(),"1.0"));
			processor.registerProcessor(VIPWhiteListService.class.getSimpleName(),new VIPWhiteListService.Processor<VIPWhiteListService.Iface>(vipWhiteListService));
			LogCvt.info("the serveris started and is listening at"+SERVER_PORT+"....");

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
