package com.froad.thrift;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.ArtificialPersonService;
import com.froad.thrift.service.AuditProcessService;
import com.froad.thrift.service.AuditTaskService;
import com.froad.thrift.service.BankAccessModuleService;
import com.froad.thrift.service.BankAuditService;
import com.froad.thrift.service.BankOperateLogService;
import com.froad.thrift.service.BankOperatorService;
import com.froad.thrift.service.BankResourceService;
import com.froad.thrift.service.BankRoleResourceService;
import com.froad.thrift.service.BankRoleService;
import com.froad.thrift.service.ClientMerchantAuditService;
import com.froad.thrift.service.ClientPaymentChannelService;
import com.froad.thrift.service.ClientProductAuditService;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.service.OrgLevelService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OrgUserRoleService;
import com.froad.thrift.service.impl.ArtificialPersonServiceImpl;
import com.froad.thrift.service.impl.AuditProcessServiceImpl;
import com.froad.thrift.service.impl.AuditTaskServiceImpl;
import com.froad.thrift.service.impl.BankAccessModuleServiceImpl;
import com.froad.thrift.service.impl.BankAuditServiceImpl;
import com.froad.thrift.service.impl.BankOperateLogServiceImpl;
import com.froad.thrift.service.impl.BankOperatorServiceImpl;
import com.froad.thrift.service.impl.BankResourceServiceImpl;
import com.froad.thrift.service.impl.BankRoleResourceServiceImpl;
import com.froad.thrift.service.impl.BankRoleServiceImpl;
import com.froad.thrift.service.impl.ClientMerchantAuditServiceImpl;
import com.froad.thrift.service.impl.ClientPaymentChannelServiceImpl;
import com.froad.thrift.service.impl.ClientProductAuditServiceImpl;
import com.froad.thrift.service.impl.ClientServiceImpl;
import com.froad.thrift.service.impl.OrgLevelServiceImpl;
import com.froad.thrift.service.impl.OrgServiceImpl;
import com.froad.thrift.service.impl.OrgUserRoleServiceImpl;
import com.froad.util.PropertiesUtil;


/**
 * 多接口服务
 * @ClassName: MainRun
 * @Description: TODO
 * @author FQ
 * @date 2015年3月11日 下午11:44:22
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
	
			
			
			//银行用户Service
			processor.registerProcessor(BankOperatorService.class.getSimpleName(),
					new BankOperatorService.Processor<BankOperatorService.Iface>((BankOperatorService.Iface) new BizMonitor(this).wrapper(new BankOperatorServiceImpl(BankOperatorService.class.getSimpleName(),"1.0"))));
			//法人行社Service
			processor.registerProcessor(ArtificialPersonService.class.getSimpleName(),
					new ArtificialPersonService.Processor<ArtificialPersonService.Iface>((ArtificialPersonService.Iface) new BizMonitor(this).wrapper(new ArtificialPersonServiceImpl(ArtificialPersonService.class.getSimpleName(),"1.0"))));
			//银行资源Service
			processor.registerProcessor(BankResourceService.class.getSimpleName(),
					new BankResourceService.Processor<BankResourceService.Iface>((BankResourceService.Iface) new BizMonitor(this).wrapper(new BankResourceServiceImpl(BankResourceService.class.getSimpleName(),"1.0"))));
			//银行角色资源Service
			processor.registerProcessor(BankRoleResourceService.class.getSimpleName(),
					new BankRoleResourceService.Processor<BankRoleResourceService.Iface>((BankRoleResourceService.Iface) new BizMonitor(this).wrapper(new BankRoleResourceServiceImpl(BankRoleResourceService.class.getSimpleName(),"1.0"))));
			//银行角色Service
			processor.registerProcessor(BankRoleService.class.getSimpleName(),
					new BankRoleService.Processor<BankRoleService.Iface>((BankRoleService.Iface) new BizMonitor(this).wrapper(new BankRoleServiceImpl(BankRoleService.class.getSimpleName(),"1.0"))));
			//银行审核Service
			processor.registerProcessor(BankAuditService.class.getSimpleName(),
					new BankAuditService.Processor<BankAuditService.Iface>((BankAuditService.Iface) new BizMonitor(this).wrapper(new BankAuditServiceImpl(BankAuditService.class.getSimpleName(),"1.0"))));
			//银行用户操作记录表Service
			processor.registerProcessor(BankOperateLogService.class.getSimpleName(),
					new BankOperateLogService.Processor<BankOperateLogService.Iface>((BankOperateLogService.Iface) new BizMonitor(this).wrapper(new BankOperateLogServiceImpl(BankOperateLogService.class.getSimpleName(),"1.0"))));
			//客户端支付渠道Service
			processor.registerProcessor(ClientPaymentChannelService.class.getSimpleName(),
					new ClientPaymentChannelService.Processor<ClientPaymentChannelService.Iface>((ClientPaymentChannelService.Iface) new BizMonitor(this).wrapper(new ClientPaymentChannelServiceImpl(ClientPaymentChannelService.class.getSimpleName(),"1.0"))));
			//客户端数据Service
			processor.registerProcessor(ClientService.class.getSimpleName(),
					new ClientService.Processor<ClientService.Iface>((ClientService.Iface) new BizMonitor(this).wrapper(new ClientServiceImpl(ClientService.class.getSimpleName(),"1.0"))));
			//银行联合登录帐号Service
			processor.registerProcessor(OrgLevelService.class.getSimpleName(),
					new OrgLevelService.Processor<OrgLevelService.Iface>((OrgLevelService.Iface) new BizMonitor(this).wrapper(new OrgLevelServiceImpl(OrgLevelService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(OrgUserRoleService.class.getSimpleName(),
					new OrgUserRoleService.Processor<OrgUserRoleService.Iface>((OrgUserRoleService.Iface) new BizMonitor(this).wrapper(new OrgUserRoleServiceImpl(OrgUserRoleService.class.getSimpleName(),"1.0"))));
			//机构信息Service
			processor.registerProcessor(OrgService.class.getSimpleName(),
					new OrgService.Processor<OrgService.Iface>((OrgService.Iface) new BizMonitor(this).wrapper(new OrgServiceImpl(OrgService.class.getSimpleName(),"1.0"))));
			//商户审核配置Service
			processor.registerProcessor(ClientMerchantAuditService.class.getSimpleName(),
					new ClientMerchantAuditService.Processor<ClientMerchantAuditService.Iface>((ClientMerchantAuditService.Iface) new BizMonitor(this).wrapper(new ClientMerchantAuditServiceImpl(ClientMerchantAuditService.class.getSimpleName(),"1.0"))));
			//商品审核配置Service
			processor.registerProcessor(ClientProductAuditService.class.getSimpleName(),
					new ClientProductAuditService.Processor<ClientProductAuditService.Iface>((ClientProductAuditService.Iface) new BizMonitor(this).wrapper(new ClientProductAuditServiceImpl(ClientProductAuditService.class.getSimpleName(),"1.0"))));
			//审核任务订单Service
			processor.registerProcessor(AuditTaskService.class.getSimpleName(),
					new AuditTaskService.Processor<AuditTaskService.Iface>((AuditTaskService.Iface) new BizMonitor(this).wrapper(new AuditTaskServiceImpl(AuditTaskService.class.getSimpleName(),"1.0"))));
			//审核任务Service
			processor.registerProcessor(AuditProcessService.class.getSimpleName(),
					new AuditProcessService.Processor<AuditProcessService.Iface>((AuditProcessService.Iface) new BizMonitor(this).wrapper(new AuditProcessServiceImpl(AuditProcessService.class.getSimpleName(),"1.0"))));
			//多银行功能列表获取
			processor.registerProcessor(BankAccessModuleService.class.getSimpleName(),
					new BankAccessModuleService.Processor<BankAccessModuleService.Iface>((BankAccessModuleService.Iface) new BizMonitor(this).wrapper(new BankAccessModuleServiceImpl(BankAccessModuleService.class.getSimpleName(),"1.0"))));

			
			
			
			LogCvt.info("the bank serveris started and is listening at "+SERVER_PORT+"...");
	
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
