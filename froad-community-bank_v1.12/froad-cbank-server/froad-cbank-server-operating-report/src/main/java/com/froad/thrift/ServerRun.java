package com.froad.thrift;

import java.net.InetAddress;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.froad.logback.LogCvt;
import com.froad.thrift.monitor.server.BaseServer;
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
