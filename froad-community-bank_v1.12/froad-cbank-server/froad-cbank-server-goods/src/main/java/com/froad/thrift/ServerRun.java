package com.froad.thrift;

import java.net.InetAddress;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.enums.AuditType;
import com.froad.logback.LogCvt;
import com.froad.logic.ProductLogic;
import com.froad.logic.impl.ProductAuditProcess;
import com.froad.logic.impl.ProductLogicImpl;
import com.froad.mq.redis.RedisMQService;
import com.froad.mq.redis.impl.RedisMQServiceImpl;
import com.froad.mq.redis.impl.RedisMQThread;
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.ProductCategoryService;
import com.froad.thrift.service.ProductSeckillService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.VipProductService;
import com.froad.thrift.service.impl.ProductCategoryServiceImpl;
import com.froad.thrift.service.impl.ProductSeckillServiceImpl;
import com.froad.thrift.service.impl.ProductServiceImpl;
import com.froad.thrift.service.impl.VipProductServiceImpl;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisKeyUtil;

/**
 * 多接口服务
 * @ClassName: MainRun
 * @Description: 
 * @author FQ
 * @date 2015年3月11日 下午11:44:22
 *
 */
public class ServerRun extends BaseServer {

	private int SERVER_PORT = PropertiesUtil.port;

    public void run(){
		try {
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			
			try{
		        InetAddress addr = InetAddress.getLocalHost();
	            String ip = addr.getHostAddress().toString();
	            ProductLogic productLogic = new ProductLogicImpl();
	            productLogic.registerProductService(ip + ":" + SERVER_PORT);
	            LogCvt.info("the service register at " + ip + ":" + SERVER_PORT + "...");
	        } catch(Exception e){
	        	LogCvt.info("the service register ip:port error ...");
	        }
			/**
			 *  Redis 服务监听启动
			 */
			RedisMQService service = new RedisMQServiceImpl();
			
			RedisMQThread product = new RedisMQThread(RedisKeyUtil.cbbank_fallow_audit_mq_audit_type(AuditType.group_product.name()), new ProductAuditProcess());
			
			// 注册Redis监听服务
			service.registerMQService(product);
			service.start();
			// 线程池服务模型
			TServer server = new TThreadPoolServer(
					new TThreadPoolServer.Args(t).processor(processor));

			//商品分类
			processor.registerProcessor(ProductCategoryService.class.getSimpleName(),
                    new ProductCategoryService.Processor<ProductCategoryService.Iface>((ProductCategoryService.Iface) new BizMonitor(this).wrapper(new ProductCategoryServiceImpl(ProductCategoryService.class.getSimpleName(),"1.0"))));
            
			//商品
			processor.registerProcessor(ProductService.class.getSimpleName(),
                    new ProductService.Processor<ProductService.Iface>((ProductService.Iface) new BizMonitor(this).wrapper(new ProductServiceImpl(ProductService.class.getSimpleName(),"1.0"))));
			
			//商品敏感词
//            processor.registerProcessor("ProductSenseService",
//                    new ProductSenseService.Processor<ProductSenseService.Iface>(
//                            new ProductSenseServiceImpl()));

			processor.registerProcessor("ProductSeckillService",
                    new ProductSeckillService.Processor<ProductSeckillService.Iface>(
                            new ProductSeckillServiceImpl()));
			
			//VIP规则
            processor.registerProcessor(VipProductService.class.getSimpleName(),
                    new VipProductService.Processor<VipProductService.Iface>(
                            (VipProductService.Iface) new BizMonitor(this).wrapper(
                                    new VipProductServiceImpl(VipProductService.class.getSimpleName(),"1.0"))));
            
			LogCvt.info("the service started and is listening at " + SERVER_PORT + "...");

			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    @Override
    public void reinitialize() {
        
    }

    @Override
    public void stop() {
        
    }

}
