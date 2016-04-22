package com.froad.thrift;

import java.net.InetAddress;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.BankAccessService;
import com.froad.thrift.service.BossDictionaryCategoryService;
import com.froad.thrift.service.BossDictionaryItemService;
import com.froad.thrift.service.BossDictionaryService;
import com.froad.thrift.service.BossMerchantCategoryService;
import com.froad.thrift.service.BossMerchantService;
import com.froad.thrift.service.BossOrderQueryService;
import com.froad.thrift.service.BossProductCategoryService;
import com.froad.thrift.service.BossProductService;
import com.froad.thrift.service.BossQueryPaymentService;
import com.froad.thrift.service.BossQueryRefundOrderService;
import com.froad.thrift.service.BossSettlementService;
import com.froad.thrift.service.BusinessOrderQueryService;
import com.froad.thrift.service.BusinessZoneTagService;
import com.froad.thrift.service.ExceptionOfPaymentService;
import com.froad.thrift.service.MerchantActivityTagService;
import com.froad.thrift.service.OutletActivityService;
import com.froad.thrift.service.ProductActivityTagService;
import com.froad.thrift.service.ProviderOrderService;
import com.froad.thrift.service.ProviderService;
import com.froad.thrift.service.impl.BankAccessServiceImpl;
import com.froad.thrift.service.impl.BossDictionaryCategoryServiceImpl;
import com.froad.thrift.service.impl.BossDictionaryItemServiceImpl;
import com.froad.thrift.service.impl.BossDictionaryServiceImpl;
import com.froad.thrift.service.impl.BossMerchantCategoryServiceImpl;
import com.froad.thrift.service.impl.BossMerchantServiceImpl;
import com.froad.thrift.service.impl.BossOrderQueryServiceImpl;
import com.froad.thrift.service.impl.BossProductCategoryServiceImpl;
import com.froad.thrift.service.impl.BossProductServiceImpl;
import com.froad.thrift.service.impl.BossQueryPaymentServiceImpl;
import com.froad.thrift.service.impl.BossQueryRefundOrderServiceImpl;
import com.froad.thrift.service.impl.BossSettlementServiceImpl;
import com.froad.thrift.service.impl.BusinessOrderQueryServiceImpl;
import com.froad.thrift.service.impl.BusinessZoneTagServiceImpl;
import com.froad.thrift.service.impl.ExceptionOfPaymentServiceImpl;
import com.froad.thrift.service.impl.MerchantActivityTagServiceImpl;
import com.froad.thrift.service.impl.OutletActivityServiceImpl;
import com.froad.thrift.service.impl.ProductActivityTagServiceImpl;
import com.froad.thrift.service.impl.ProviderOrderServiceImpl;
import com.froad.thrift.service.impl.ProviderServiceImpl;
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

	private int SERVER_PORT = PropertiesUtil.port;

    public void run(){
		try {
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			
			try{
		        InetAddress addr = InetAddress.getLocalHost();
	            String ip = addr.getHostAddress().toString();
	            LogCvt.info("the service register at " + ip + ":" + SERVER_PORT + "...");
	        } catch(Exception e){
	        	LogCvt.info("the service register ip:port error ...");
	        }
			
			// 线程池服务模型
			TServer server = new TThreadPoolServer(
					new TThreadPoolServer.Args(t).processor(processor));

			//商品
			processor.registerProcessor(BossProductService.class.getSimpleName(),
                    new BossProductService.Processor<BossProductService.Iface>((BossProductService.Iface) new BizMonitor(this).wrapper(new BossProductServiceImpl(BossProductService.class.getSimpleName(),"1.0"))));
            

			//商户门店
			processor.registerProcessor(BossMerchantService.class.getSimpleName(),
                  new BossMerchantService.Processor<BossMerchantService.Iface>((BossMerchantService.Iface) new BizMonitor(this).wrapper(new BossMerchantServiceImpl(BossMerchantService.class.getSimpleName(),"1.0"))));
          
			//支付模块
            processor.registerProcessor(BossQueryPaymentService.class.getSimpleName(),
					new BossQueryPaymentService.Processor<BossQueryPaymentService.Iface>((BossQueryPaymentService.Iface) new BizMonitor(this).wrapper(new BossQueryPaymentServiceImpl(BossQueryPaymentService.class.getSimpleName(),"1.0"))));
			
            //结算模块
            processor.registerProcessor(BossSettlementService.class.getSimpleName(),
					new BossSettlementService.Processor<BossSettlementService.Iface>((BossSettlementService.Iface) new BizMonitor(this).wrapper(new BossSettlementServiceImpl(BossSettlementService.class.getSimpleName(),"1.0"))));
			
            //订单模块
            processor.registerProcessor(BossOrderQueryService.class.getSimpleName(),
					new BossOrderQueryService.Processor<BossOrderQueryService.Iface>((BossOrderQueryService.Iface) new BizMonitor(this).wrapper(new BossOrderQueryServiceImpl(BossOrderQueryService.class.getSimpleName(),"1.0"))));
			
            
            //退款模块
            processor.registerProcessor(BossQueryRefundOrderService.class.getSimpleName(),
					new BossQueryRefundOrderService.Processor<BossQueryRefundOrderService.Iface>((BossQueryRefundOrderService.Iface) new BizMonitor(this).wrapper(new BossQueryRefundOrderServiceImpl(BossQueryRefundOrderService.class.getSimpleName(),"1.0"))));
			
            //支付异常模块
            processor.registerProcessor(ExceptionOfPaymentService.class.getSimpleName(),
					new ExceptionOfPaymentService.Processor<ExceptionOfPaymentService.Iface>((ExceptionOfPaymentService.Iface) new BizMonitor(this).wrapper(new ExceptionOfPaymentServiceImpl(ExceptionOfPaymentService.class.getSimpleName(),"1.0"))));
            //商品分类模块
            processor.registerProcessor(BossProductCategoryService.class.getSimpleName(),
            		new BossProductCategoryService.Processor<BossProductCategoryService.Iface>((BossProductCategoryService.Iface) new BizMonitor(this).wrapper(new BossProductCategoryServiceImpl(BossProductCategoryService.class.getSimpleName(),"1.0"))));
			
            //商户分类模块
            processor.registerProcessor(BossMerchantCategoryService.class.getSimpleName(),
            		new BossMerchantCategoryService.Processor<BossMerchantCategoryService.Iface>((BossMerchantCategoryService.Iface) new BizMonitor(this).wrapper(new BossMerchantCategoryServiceImpl(BossMerchantCategoryService.class.getSimpleName(),"1.0"))));
            
            //多银行接入模块
            processor.registerProcessor(BankAccessService.class.getSimpleName(),
					new BankAccessService.Processor<BankAccessService.Iface>((BankAccessService.Iface) new BizMonitor(this).wrapper(new BankAccessServiceImpl(BankAccessService.class.getSimpleName(),"1.0"))));
           
            // 商户活动标签管理
            processor.registerProcessor(MerchantActivityTagService.class.getSimpleName(),
					new MerchantActivityTagService.Processor<MerchantActivityTagService.Iface>((MerchantActivityTagService.Iface) new BizMonitor(this).wrapper(new MerchantActivityTagServiceImpl(MerchantActivityTagService.class.getSimpleName(),"1.0"))));
            
            // 商圈标签管理
            processor.registerProcessor(BusinessZoneTagService.class.getSimpleName(),
					new BusinessZoneTagService.Processor<BusinessZoneTagService.Iface>((BusinessZoneTagService.Iface) new BizMonitor(this).wrapper(new BusinessZoneTagServiceImpl(BusinessZoneTagService.class.getSimpleName(),"1.0"))));
            
            // 门店活动标签管理
            processor.registerProcessor(OutletActivityService.class.getSimpleName(),
					new OutletActivityService.Processor<OutletActivityService.Iface>((OutletActivityService.Iface) new BizMonitor(this).wrapper(new OutletActivityServiceImpl(OutletActivityService.class.getSimpleName(),"1.0"))));
            
            //商品活动标签管理
            processor.registerProcessor(ProductActivityTagService.class.getSimpleName(),
					new ProductActivityTagService.Processor<ProductActivityTagService.Iface>((ProductActivityTagService.Iface) new BizMonitor(this).wrapper(new ProductActivityTagServiceImpl(ProductActivityTagService.class.getSimpleName(),"1.0"))));
            
            /***字典管理****/
            //字典类别服务
            processor.registerProcessor(BossDictionaryCategoryService.class.getSimpleName(),
					new BossDictionaryCategoryService.Processor<BossDictionaryCategoryService.Iface>((BossDictionaryCategoryService.Iface) new BizMonitor(this).wrapper(new BossDictionaryCategoryServiceImpl(BossDictionaryCategoryService.class.getSimpleName(),"1.0"))));
            //字典服务
            processor.registerProcessor(BossDictionaryService.class.getSimpleName(),
					new BossDictionaryService.Processor<BossDictionaryService.Iface>((BossDictionaryService.Iface) new BizMonitor(this).wrapper(new BossDictionaryServiceImpl(BossDictionaryService.class.getSimpleName(),"1.0"))));
            //字典条目服务
            processor.registerProcessor(BossDictionaryItemService.class.getSimpleName(),
					new BossDictionaryItemService.Processor<BossDictionaryItemService.Iface>((BossDictionaryItemService.Iface) new BizMonitor(this).wrapper(new BossDictionaryItemServiceImpl(BossDictionaryItemService.class.getSimpleName(),"1.0"))));
            
            /***VIP精品商城****/
            //运营订单管理
            processor.registerProcessor(BusinessOrderQueryService.class.getSimpleName(), 
            		new BusinessOrderQueryService.Processor<BusinessOrderQueryService.Iface>((BusinessOrderQueryService.Iface) new BizMonitor(this).wrapper(new BusinessOrderQueryServiceImpl(BusinessOrderQueryService.class.getSimpleName(),"1.0"))));
            //供应商订单管理
            processor.registerProcessor(ProviderOrderService.class.getSimpleName(),
					new ProviderOrderService.Processor<ProviderOrderService.Iface>((ProviderOrderService.Iface) new BizMonitor(this).wrapper(new ProviderOrderServiceImpl(ProviderOrderService.class.getSimpleName(),"1.0"))));
            //供应商管理
            processor.registerProcessor(ProviderService.class.getSimpleName(), 
            		new ProviderService.Processor<ProviderService.Iface>((ProviderService.Iface) new BizMonitor(this).wrapper(new ProviderServiceImpl(ProviderService.class.getSimpleName(),"1.0"))));
            
            
            LogCvt.info("the service started and is listening at " + SERVER_PORT + "...");

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
