package com.froad.test.order;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

import com.froad.thrift.service.OrderQueryService;
import com.froad.thrift.service.impl.OrderQueryServiceImpl;
import com.froad.util.PropertiesUtil;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-8-31下午6:23:17 
 */
public class OrderQueryServer {
	public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {
            System.out.println("HelloWorld TSimpleServer start ....");
 
            OrderQueryService.Processor<OrderQueryService.Iface> tprocessor = new OrderQueryService.Processor<OrderQueryService.Iface>(new OrderQueryServiceImpl());
 
            // 简单的单线程服务模型，一般用于测试
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
//            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            tArgs.protocolFactory(new TCompactProtocol.Factory());
            // tArgs.protocolFactory(new TJSONProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();
 
        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    	PropertiesUtil.load();
        OrderQueryServer server = new OrderQueryServer();
        server.startServer();
    }
 
}
