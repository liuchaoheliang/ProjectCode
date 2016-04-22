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
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.BizInstanceAttrService;
import com.froad.thrift.service.BizInstanceAttrValueService;
import com.froad.thrift.service.FallowExecuteService;
import com.froad.thrift.service.FallowQueryService;
import com.froad.thrift.service.HistoryInstanceService;
import com.froad.thrift.service.HistoryTaskActorService;
import com.froad.thrift.service.HistoryTaskService;
import com.froad.thrift.service.InstanceService;
import com.froad.thrift.service.ProcessNodeService;
import com.froad.thrift.service.ProcessService;
import com.froad.thrift.service.TaskActorService;
import com.froad.thrift.service.TaskService;
import com.froad.thrift.service.impl.BizInstanceAttrServiceImpl;
import com.froad.thrift.service.impl.BizInstanceAttrValueServiceImpl;
import com.froad.thrift.service.impl.FallowExecuteServiceImpl;
import com.froad.thrift.service.impl.FallowQueryServiceImpl;
import com.froad.thrift.service.impl.HistoryInstanceServiceImpl;
import com.froad.thrift.service.impl.HistoryTaskActorServiceImpl;
import com.froad.thrift.service.impl.HistoryTaskServiceImpl;
import com.froad.thrift.service.impl.InstanceServiceImpl;
import com.froad.thrift.service.impl.ProcessNodeServiceImpl;
import com.froad.thrift.service.impl.ProcessServiceImpl;
import com.froad.thrift.service.impl.TaskActorServiceImpl;
import com.froad.thrift.service.impl.TaskServiceImpl;
import com.froad.util.PropertiesUtil;

/**
 * 多接口服务
 * 
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
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			
			// 线程池服务模型
			TServer server = new TThreadPoolServer(
					new TThreadPoolServer.Args(t).processor(processor));
			
			/** 注册BizInstanceAttrService的服务 */
			processor.registerProcessor(BizInstanceAttrService.class.getSimpleName(),
				new BizInstanceAttrService.Processor<BizInstanceAttrService.Iface>((BizInstanceAttrService.Iface) new BizMonitor(this).wrapper(new BizInstanceAttrServiceImpl(BizInstanceAttrService.class.getSimpleName(), "1.0"))));

			/** 注册BizInstanceAttrValueService的服务 */
			processor.registerProcessor(BizInstanceAttrValueService.class.getSimpleName(),
				new BizInstanceAttrValueService.Processor<BizInstanceAttrValueService.Iface>((BizInstanceAttrValueService.Iface) new BizMonitor(this).wrapper(new BizInstanceAttrValueServiceImpl(BizInstanceAttrValueService.class.getSimpleName(), "1.0"))));

			/** 注册HistoryInstanceService的服务 */
			processor.registerProcessor(HistoryInstanceService.class.getSimpleName(),
				new HistoryInstanceService.Processor<HistoryInstanceService.Iface>((HistoryInstanceService.Iface) new BizMonitor(this).wrapper(new HistoryInstanceServiceImpl(HistoryInstanceService.class.getSimpleName(), "1.0"))));

			/** 注册HistoryTaskService的服务 */
			processor.registerProcessor(HistoryTaskService.class.getSimpleName(),
				new HistoryTaskService.Processor<HistoryTaskService.Iface>((HistoryTaskService.Iface) new BizMonitor(this).wrapper(new HistoryTaskServiceImpl(HistoryTaskService.class.getSimpleName(), "1.0"))));

			/** 注册HistoryTaskActorService的服务 */
			processor.registerProcessor(HistoryTaskActorService.class.getSimpleName(),
				new HistoryTaskActorService.Processor<HistoryTaskActorService.Iface>((HistoryTaskActorService.Iface) new BizMonitor(this).wrapper(new HistoryTaskActorServiceImpl(HistoryTaskActorService.class.getSimpleName(), "1.0"))));

			/** 注册InstanceService的服务 */
			processor.registerProcessor(InstanceService.class.getSimpleName(),
				new InstanceService.Processor<InstanceService.Iface>((InstanceService.Iface) new BizMonitor(this).wrapper(new InstanceServiceImpl(InstanceService.class.getSimpleName(), "1.0"))));

			/** 注册ProcessService的服务 */
			processor.registerProcessor(ProcessService.class.getSimpleName(),
				new ProcessService.Processor<ProcessService.Iface>((ProcessService.Iface) new BizMonitor(this).wrapper(new ProcessServiceImpl(ProcessService.class.getSimpleName(), "1.0"))));

			/** 注册ProcessNodeService的服务 */
			processor.registerProcessor(ProcessNodeService.class.getSimpleName(),
				new ProcessNodeService.Processor<ProcessNodeService.Iface>((ProcessNodeService.Iface) new BizMonitor(this).wrapper(new ProcessNodeServiceImpl(ProcessNodeService.class.getSimpleName(), "1.0"))));

			/** 注册TaskService的服务 */
			processor.registerProcessor(TaskService.class.getSimpleName(),
				new TaskService.Processor<TaskService.Iface>((TaskService.Iface) new BizMonitor(this).wrapper(new TaskServiceImpl(TaskService.class.getSimpleName(), "1.0"))));

			/** 注册TaskActorService的服务 */
			processor.registerProcessor(TaskActorService.class.getSimpleName(),
				new TaskActorService.Processor<TaskActorService.Iface>((TaskActorService.Iface) new BizMonitor(this).wrapper(new TaskActorServiceImpl(TaskActorService.class.getSimpleName(), "1.0"))));
			
			/** 注册FallowExecuteService的服务 */
			processor.registerProcessor(FallowExecuteService.class.getSimpleName(),
					new FallowExecuteService.Processor<FallowExecuteService.Iface>((FallowExecuteService.Iface) new BizMonitor(this).wrapper(new FallowExecuteServiceImpl(FallowExecuteService.class.getSimpleName(), "1.0"))));
			
			/** 注册FallowQueryService的服务 */
			processor.registerProcessor(FallowQueryService.class.getSimpleName(),
					new FallowQueryService.Processor<FallowQueryService.Iface>((FallowQueryService.Iface) new BizMonitor(this).wrapper(new FallowQueryServiceImpl(FallowQueryService.class.getSimpleName(), "1.0"))));


			LogCvt.info("the service started and is listening at " + SERVER_PORT + "...");
			LogCvt.info("工作流模块服务启动, 端口号为[" + SERVER_PORT + "]...");
			server.serve();

		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error("工作流模块服务启动失败, 原因:" + e.getMessage(), e);
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
