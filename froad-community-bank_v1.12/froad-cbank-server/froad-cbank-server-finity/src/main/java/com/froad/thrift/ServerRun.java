package com.froad.thrift;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.FFTOrgService;
import com.froad.thrift.service.FFTUserOrgService;
import com.froad.thrift.service.FFTUserRoleService;
import com.froad.thrift.service.FinityMerchantUserService;
import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.service.FinityRoleService;
import com.froad.thrift.service.FinityUserResourceService;
import com.froad.thrift.service.impl.FFTOrgServiceImpl;
import com.froad.thrift.service.impl.FFTUserOrgServiceImpl;
import com.froad.thrift.service.impl.FFTUserRoleServiceImpl;
import com.froad.thrift.service.impl.FinityMerchantUserServiceImpl;
import com.froad.thrift.service.impl.FinityResourceServiceImpl;
import com.froad.thrift.service.impl.FinityRoleServiceImpl;
import com.froad.thrift.service.impl.FinityUserResourceServiceImpl;
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
			//银行端同步任务
//			Task.runTaskBank();
			//商户端同步任务
//			Task.runTaskMerchant();
			
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			
			// 线程池服务模型
			TServer server = new TThreadPoolServer(
					new TThreadPoolServer.Args(t).processor(processor));
	 
			//资源Service
			processor.registerProcessor(FinityResourceService.class.getSimpleName(),
					new FinityResourceService.Processor<FinityResourceService.Iface>((FinityResourceService.Iface) new BizMonitor(this).wrapper(new FinityResourceServiceImpl(FinityResourceService.class.getSimpleName(),"1.0"))));
			
			//角色Service
			processor.registerProcessor(FinityRoleService.class.getSimpleName(),
					new FinityRoleService.Processor<FinityRoleService.Iface>((FinityRoleService.Iface) new BizMonitor(this).wrapper(new FinityRoleServiceImpl(FinityRoleService.class.getSimpleName(),"1.0"))));
			
			//角色Service
			processor.registerProcessor(FinityMerchantUserService.class.getSimpleName(),
					new FinityMerchantUserService.Processor<FinityMerchantUserService.Iface>((FinityMerchantUserService.Iface) new BizMonitor(this).wrapper(new FinityMerchantUserServiceImpl(FinityMerchantUserService.class.getSimpleName(),"1.0"))));
			  
			
			//用户资源Service
			processor.registerProcessor(FinityUserResourceService.class.getSimpleName(),
					new FinityUserResourceService.Processor<FinityUserResourceService.Iface>((FinityUserResourceService.Iface) new BizMonitor(this).wrapper(new FinityUserResourceServiceImpl(FinityUserResourceService.class.getSimpleName(),"1.0"))));
			  		
			//组织Service
			processor.registerProcessor(FFTOrgService.class.getSimpleName(),
					new FFTOrgService.Processor<FFTOrgService.Iface>((FFTOrgService.Iface) new BizMonitor(this).wrapper(new FFTOrgServiceImpl(FFTOrgService.class.getSimpleName(),"1.0"))));

			//用户组织Service
			processor.registerProcessor(FFTUserOrgService.class.getSimpleName(),
					new FFTUserOrgService.Processor<FFTUserOrgService.Iface>((FFTUserOrgService.Iface) new BizMonitor(this).wrapper(new FFTUserOrgServiceImpl(FFTUserOrgService.class.getSimpleName(),"1.0"))));

			//用户角色Service
			processor.registerProcessor(FFTUserRoleService.class.getSimpleName(),
					new FFTUserRoleService.Processor<FFTUserRoleService.Iface>((FFTUserRoleService.Iface) new BizMonitor(this).wrapper(new FFTUserRoleServiceImpl(FFTUserRoleService.class.getSimpleName(),"1.0"))));

			
			LogCvt.info("the finitys serveris started and is listening at "+SERVER_PORT+"...");
	
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
