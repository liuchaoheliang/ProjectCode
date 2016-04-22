/////////////////////////////////////////////////////////////
//系统名称：RPC实现之Thrift
//
//Copyright (c) 2014 Top-Gun Corporation. All Rights Reserved.
//
//Class：HelloServerDemo.java
//
//【 大模块名称 】：子模块名称_Action/DTO/DAO/Service/ServiceImpl处理类
//
//日期   ：2015年3月10日 下午4:01:36
//
//作者：Administrator
//
//说明：列表/查询/删除/添加/修改/复制
//
//履历： 2015年3月10日 下午4:01:36 open  新建
//       2015年3月10日 下午4:01:36 open  修改XXX
/////////////////////////////////////////////////////////////

package com.froad.test.order;


import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.impl.OrderServiceImpl;
import com.froad.util.PropertiesUtil;

/**

 * 模块名称_Action/DTO/Service/ServiceImpl

 * @author Administrator

 */
public class OrderServer {
    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {
            System.out.println("HelloWorld TSimpleServer start ....");
 
//          TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new  HelloWorldImpl());
            OrderService.Processor<OrderService.Iface> tprocessor = new OrderService.Processor<OrderService.Iface>(new OrderServiceImpl());
 
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
        OrderServer server = new OrderServer();
        server.startServer();
    }
 
}
