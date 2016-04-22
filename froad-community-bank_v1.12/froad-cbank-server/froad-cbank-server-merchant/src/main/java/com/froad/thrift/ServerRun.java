//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖镇楼                  BUG辟易
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//                  不见满街漂亮妹，哪个归得程序员？

package com.froad.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.logic.process.MerchantAuditProcess;
import com.froad.logic.process.OutletAuditProcess;
import com.froad.mq.redis.RedisMQService;
import com.froad.mq.redis.impl.RedisMQServiceImpl;
import com.froad.mq.redis.impl.RedisMQThread;
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.MerchantAuditService;
import com.froad.thrift.service.MerchantCategoryService;
import com.froad.thrift.service.MerchantMonthCountService;
import com.froad.thrift.service.MerchantOutletFavoriteService;
import com.froad.thrift.service.MerchantOutletPhotoService;
import com.froad.thrift.service.MerchantResourceService;
import com.froad.thrift.service.MerchantRoleResourceService;
import com.froad.thrift.service.MerchantRoleService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.MerchantTypeService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.service.OutletCommentService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.impl.MerchantAccountServiceImpl;
import com.froad.thrift.service.impl.MerchantAuditServiceImpl;
import com.froad.thrift.service.impl.MerchantCategoryServiceImpl;
import com.froad.thrift.service.impl.MerchantMonthCountServiceImpl;
import com.froad.thrift.service.impl.MerchantOutletFavoriteServiceImpl;
import com.froad.thrift.service.impl.MerchantOutletPhotoServiceImpl;
import com.froad.thrift.service.impl.MerchantResourceServiceImpl;
import com.froad.thrift.service.impl.MerchantRoleResourceServiceImpl;
import com.froad.thrift.service.impl.MerchantRoleServiceImpl;
import com.froad.thrift.service.impl.MerchantServiceImpl;
import com.froad.thrift.service.impl.MerchantTypeServiceImpl;
import com.froad.thrift.service.impl.MerchantUserServiceImpl;
import com.froad.thrift.service.impl.OutletCommentServiceImpl;
import com.froad.thrift.service.impl.OutletServiceImpl;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisMQKeys;


//import com.froad.thrift.service.HelloService;
//import com.froad.thrift.service.UserService;
//import com.froad.thrift.service.impl.HelloServiceImpl;
//import com.froad.thrift.service.impl.UserServiceImpl;

/**
 * 多接口服务
 * @ClassName: MainRun
 * @Description: TODO
 * @author FQ
 * @date 2015年3月11日 下午11:44:22
 *
 */
public class ServerRun extends BaseServer {
	
	private static int SERVER_PORT = PropertiesUtil.port;

	
	
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
			
			/**
			 *  Redis 服务监听启动
			 */
			RedisMQService service = new RedisMQServiceImpl();
			
			RedisMQThread merchant = new RedisMQThread(RedisMQKeys.merchant_audit_mq_key, new MerchantAuditProcess());
			RedisMQThread outlet = new RedisMQThread(RedisMQKeys.outlet_audit_mq_key, new OutletAuditProcess());
			
			// 注册Redis监听服务
			service.registerMQService(merchant);
			service.registerMQService(outlet);
			service.start();
			
			
			/**
			 * Thrift 服务
			 */
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			
			// 线程池服务模型
			TServer server = new TThreadPoolServer(
					new TThreadPoolServer.Args(t).processor(processor));
			
			
			processor.registerProcessor(MerchantAccountService.class.getSimpleName(),
					new MerchantAccountService.Processor<MerchantAccountService.Iface>((MerchantAccountService.Iface) new BizMonitor(this).wrapper(new MerchantAccountServiceImpl(MerchantAccountService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(MerchantCategoryService.class.getSimpleName(),
					new MerchantCategoryService.Processor<MerchantCategoryService.Iface>((MerchantCategoryService.Iface) new BizMonitor(this).wrapper(new MerchantCategoryServiceImpl(MerchantCategoryService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(MerchantOutletPhotoService.class.getSimpleName(),
					new MerchantOutletPhotoService.Processor<MerchantOutletPhotoService.Iface>((MerchantOutletPhotoService.Iface) new BizMonitor(this).wrapper(new MerchantOutletPhotoServiceImpl(MerchantOutletPhotoService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(MerchantOutletFavoriteService.class.getSimpleName(),
					new MerchantOutletFavoriteService.Processor<MerchantOutletFavoriteService.Iface>((MerchantOutletFavoriteService.Iface) new BizMonitor(this).wrapper(new MerchantOutletFavoriteServiceImpl(MerchantOutletFavoriteService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(MerchantResourceService.class.getSimpleName(),
					new MerchantResourceService.Processor<MerchantResourceService.Iface>((MerchantResourceService.Iface) new BizMonitor(this).wrapper(new MerchantResourceServiceImpl(MerchantResourceService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(MerchantRoleService.class.getSimpleName(),
					new MerchantRoleService.Processor<MerchantRoleService.Iface>((MerchantRoleService.Iface) new BizMonitor(this).wrapper(new MerchantRoleServiceImpl(MerchantRoleService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(MerchantService.class.getSimpleName(),
					new MerchantService.Processor<MerchantService.Iface>((MerchantService.Iface) new BizMonitor(this).wrapper(new MerchantServiceImpl(MerchantService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(MerchantTypeService.class.getSimpleName(),
					new MerchantTypeService.Processor<MerchantTypeService.Iface>((MerchantTypeService.Iface) new BizMonitor(this).wrapper(new MerchantTypeServiceImpl(MerchantTypeService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(MerchantUserService.class.getSimpleName(),
					new MerchantUserService.Processor<MerchantUserService.Iface>((MerchantUserService.Iface) new BizMonitor(this).wrapper(new MerchantUserServiceImpl(MerchantUserService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(OutletService.class.getSimpleName(),
					new OutletService.Processor<OutletService.Iface>((OutletService.Iface) new BizMonitor(this).wrapper(new OutletServiceImpl(OutletService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(OutletCommentService.class.getSimpleName(),
					new OutletCommentService.Processor<OutletCommentService.Iface>((OutletCommentService.Iface) new BizMonitor(this).wrapper(new OutletCommentServiceImpl(OutletCommentService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(MerchantRoleResourceService.class.getSimpleName(),
					new MerchantRoleResourceService.Processor<MerchantRoleResourceService.Iface>((MerchantRoleResourceService.Iface) new BizMonitor(this).wrapper(new MerchantRoleResourceServiceImpl(MerchantRoleResourceService.class.getSimpleName(),"1.0"))));
			processor.registerProcessor(MerchantMonthCountService.class.getSimpleName(),
					new MerchantMonthCountService.Processor<MerchantMonthCountService.Iface>((MerchantMonthCountService.Iface) new BizMonitor(this).wrapper(new MerchantMonthCountServiceImpl(MerchantMonthCountService.class.getSimpleName(),"1.0"))));
			
			processor.registerProcessor(MerchantAuditService.class.getSimpleName(),
					new MerchantAuditService.Processor<MerchantAuditService.Iface>((MerchantAuditService.Iface) new BizMonitor(this).wrapper(new MerchantAuditServiceImpl(MerchantAuditService.class.getSimpleName(),"1.0"))));
			
			LogCvt.info("the service started and is listening at " + SERVER_PORT + "...");
			LogCvt.info("商户模块服务启动, 端口号为[" + SERVER_PORT + "]...");
			server.serve();
			
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error("商户模块服务启动失败, 原因:" + e.getMessage(), e);
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
