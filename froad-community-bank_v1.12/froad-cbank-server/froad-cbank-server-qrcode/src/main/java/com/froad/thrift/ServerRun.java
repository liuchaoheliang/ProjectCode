package com.froad.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.thrift.monitor.BizMonitor;
import com.froad.thrift.monitor.server.BaseServer;
import com.froad.thrift.service.QrCodeService;
import com.froad.thrift.service.impl.QrCodeServiceImpl;
import com.froad.util.PropertiesUtil;

/**
 * 多接口服务
 * @ClassName: ServerRun
 * @Description: Thrift服务主进程
 * @author FQ
 * @date 2015年3月11日 下午11:44:22
 *
 */
public class ServerRun extends BaseServer{

	private static final int SERVER_PORT = PropertiesUtil.port;

	public void run(){
		try {
			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			TServerTransport t = new TServerSocket(SERVER_PORT);
			
			// 线程池服务模型
			TServer server = new TThreadPoolServer(
					new TThreadPoolServer.Args(t).processor(processor));

			QrCodeService.Iface qrCodeIface=(QrCodeService.Iface) new BizMonitor(this).wrapper(new QrCodeServiceImpl("QrCodeService","1.0"));
			processor.registerProcessor("QrCodeService",new QrCodeService.Processor<QrCodeService.Iface>(qrCodeIface));
			
			System.out.println("the serveris started and is listening at "+SERVER_PORT+"...");

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
