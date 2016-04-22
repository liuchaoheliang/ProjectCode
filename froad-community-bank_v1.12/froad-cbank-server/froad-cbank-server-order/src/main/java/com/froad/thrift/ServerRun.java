package com.froad.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.service.MemberSecurityService;
import com.froad.thrift.service.OrderQueryService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.PaymentService;
import com.froad.thrift.service.PointSettlementService;
import com.froad.thrift.service.RefundService;
import com.froad.thrift.service.SeckillOrderService;
import com.froad.thrift.service.SettlementService;
import com.froad.thrift.service.ShoppingCartService;
import com.froad.thrift.service.ThirdpartyPayService;
import com.froad.thrift.service.TicketService;
import com.froad.thrift.service.impl.BankCardServiceImpl;
import com.froad.thrift.service.impl.MemberInformationServiceImpl;
import com.froad.thrift.service.impl.MemberSecurityServiceImpl;
import com.froad.thrift.service.impl.OrderQueryServiceImpl;
import com.froad.thrift.service.impl.OrderServiceImpl;
import com.froad.thrift.service.impl.PaymentServiceImpl;
import com.froad.thrift.service.impl.PointSettlementServiceImpl;
import com.froad.thrift.service.impl.RefundServiceImpl;
import com.froad.thrift.service.impl.SeckillOrderServiceImpl;
import com.froad.thrift.service.impl.SettlementServiceImpl;
import com.froad.thrift.service.impl.ShoppingCartServiceImpl;
import com.froad.thrift.service.impl.ThirdpartyPayServiceImpl;
import com.froad.thrift.service.impl.TicketServiceImpl;
import com.froad.util.PropertiesUtil;

/**
 * 多接口服务
 * @ClassName: MainRun
 * @Description: Thrift服务主进程
 * @author FQ
 * @date 2015年3月11日 下午11:44:22
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

			// 购物车
//			processor.registerProcessor(ShoppingCartService.class.getSimpleName(),
//					new ShoppingCartService.Processor<ShoppingCartService.Iface>(
//							new ShoppingCartServiceImpl()));
			processor.registerProcessor(ShoppingCartService.class.getSimpleName(),
					new ShoppingCartService.Processor<ShoppingCartService.Iface>((ShoppingCartService.Iface) new BizMonitor(this).wrapper(new ShoppingCartServiceImpl(ShoppingCartService.class.getSimpleName(),"1.0"))));
			
			//订单管理接口
//			processor.registerProcessor(OrderService.class.getSimpleName(),
//					new OrderService.Processor<OrderService.Iface>(
//							new OrderServiceImpl()));
			processor.registerProcessor(OrderService.class.getSimpleName(),
						new OrderService.Processor<OrderService.Iface>((OrderService.Iface) new BizMonitor(this).wrapper(new OrderServiceImpl(OrderService.class.getSimpleName(),"1.0"))));
				
			
//			// 订单查询
//            processor.registerProcessor(OrderQueryService.class.getSimpleName(), new OrderQueryService.Processor<OrderQueryService.Iface>(
//                    new OrderQueryServiceImpl()));
            
            processor.registerProcessor(OrderQueryService.class.getSimpleName(),
					new OrderQueryService.Processor<OrderQueryService.Iface>((OrderQueryService.Iface) new BizMonitor(this).wrapper(new OrderQueryServiceImpl(OrderQueryService.class.getSimpleName(),"1.0"))));
			
			
			// 退款子模块
//			processor.registerProcessor(RefundService.class.getSimpleName(),
//					new RefundService.Processor<RefundService.Iface>(
//							new RefundServiceImpl()));
			processor.registerProcessor(RefundService.class.getSimpleName(),
					new RefundService.Processor<RefundService.Iface>((RefundService.Iface) new BizMonitor(this).wrapper(new RefundServiceImpl(RefundService.class.getSimpleName(),"1.0"))));
			
			// 券子模块
//			processor.registerProcessor(TicketService.class.getSimpleName(),
//					new TicketService.Processor<TicketService.Iface>(
//							new TicketServiceImpl()));
			// 券子模块
			processor.registerProcessor(TicketService.class.getSimpleName(),
					new TicketService.Processor<TicketService.Iface>((TicketService.Iface) new BizMonitor(this).wrapper(new TicketServiceImpl(TicketService.class.getSimpleName(),"1.0"))));
			
			// 结算模块
//			processor.registerProcessor(SettlementService.class.getSimpleName(),
//					new SettlementService.Processor<SettlementService.Iface>(
//							new SettlementServiceImpl()));
			processor.registerProcessor(SettlementService.class.getSimpleName(),
					new SettlementService.Processor<SettlementService.Iface>((SettlementService.Iface) new BizMonitor(this).wrapper(new SettlementServiceImpl(SettlementService.class.getSimpleName(),"1.0"))));
			
			
			//银行验证
//            processor.registerProcessor(BankCardService.class.getSimpleName(),
//                    new BankCardService.Processor<BankCardService.Iface>(
//                            new BankCardServiceImpl()));
			//银行验证
			processor.registerProcessor(BankCardService.class.getSimpleName(),
					new BankCardService.Processor<BankCardService.Iface>((BankCardService.Iface) new BizMonitor(this).wrapper(new BankCardServiceImpl(BankCardService.class.getSimpleName(),"1.0"))));
			
            
			//会员安全相关
//            processor.registerProcessor(MemberSecurityService.class.getSimpleName(),
//                    new MemberSecurityService.Processor<MemberSecurityService.Iface>(
//                            new MemberSecurityServiceImpl()));
			//会员安全相关
            processor.registerProcessor(MemberSecurityService.class.getSimpleName(),
					new MemberSecurityService.Processor<MemberSecurityService.Iface>((MemberSecurityService.Iface) new BizMonitor(this).wrapper(new MemberSecurityServiceImpl(MemberSecurityService.class.getSimpleName(),"1.0"))));
			
            //会员信息相关
//            processor.registerProcessor(MemberInformationService.class.getSimpleName(),
//                    new MemberInformationService.Processor<MemberInformationService.Iface>(
//                            new MemberInformationServiceImpl()));
            //会员信息相关
            processor.registerProcessor(MemberInformationService.class.getSimpleName(),
					new MemberInformationService.Processor<MemberInformationService.Iface>((MemberInformationService.Iface) new BizMonitor(this).wrapper(new MemberInformationServiceImpl(MemberInformationService.class.getSimpleName(),"1.0"))));
			

            //支付模块
//            processor.registerProcessor(PaymentService.class.getSimpleName(), 
//            		new PaymentService.Processor<PaymentService.Iface>(
//            				new PaymentServiceImpl()));
            //支付模块
            processor.registerProcessor(PaymentService.class.getSimpleName(),
					new PaymentService.Processor<PaymentService.Iface>((PaymentService.Iface) new BizMonitor(this).wrapper(new PaymentServiceImpl(PaymentService.class.getSimpleName(),"1.0"))));
			
            
            //秒杀订单模块
//          processor.registerProcessor(SeckillOrderService.class.getSimpleName(), 
//            		new SeckillOrderService.Processor<SeckillOrderService.Iface>(
//            				new SeckillOrderServiceImpl()));
            processor.registerProcessor(SeckillOrderService.class.getSimpleName(),
					new SeckillOrderService.Processor<SeckillOrderService.Iface>((SeckillOrderService.Iface) new BizMonitor(this).wrapper(new SeckillOrderServiceImpl(SeckillOrderService.class.getSimpleName(),"1.0"))));
            
            //BOSS使用的第三方远程接口
            processor.registerProcessor(ThirdpartyPayService.class.getSimpleName(),
            		new ThirdpartyPayService.Processor<ThirdpartyPayService.Iface>((ThirdpartyPayService.Iface) new BizMonitor(this).wrapper(new ThirdpartyPayServiceImpl(ThirdpartyPayService.class.getSimpleName(),"1.0"))));
			
            //积分报表模块
            processor.registerProcessor(PointSettlementService.class.getSimpleName(),
					new PointSettlementService.Processor<PointSettlementService.Iface>((PointSettlementService.Iface) new BizMonitor(this).wrapper(new PointSettlementServiceImpl(PointSettlementService.class.getSimpleName(),"1.0"))));
			
            
			LogCvt.info("the serveris started and is listening at "+SERVER_PORT+"...");
			LogCvt.info("订单模块服务启动, 端口号为[" + SERVER_PORT + "]...");
			
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
