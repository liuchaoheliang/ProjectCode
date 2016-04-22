package com.froad.cbank.coremodule.framework.expand.thrift.proxy;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * 连接Thrift服务的客户端
 * 
 * @author TH
 *
 */
public class ThriftClient {

	/** 服务端IP */
	private String ip = "";
	
	/** 服务端port */
	private int port = 0;
	
	/** 服务名 */
	private String srvName = "";
	
	private TTransport transport = null;
	private TProtocol protocol = null;
	private TMultiplexedProtocol multiProtocol = null;
	
	/** 默认超时时间*/
	private int DEFAULT_TIME_OUT = 10000;
	
	
	
	/**
	 * 打开服务连接 (默认超时时间10秒)
	 * 
	 * @param ip 服务ip
	 * @param port 服务端口
	 * @param srvName 服务名
	 * @throws TTransportException
	 */
	public void open(String ip, int port, String srvName) throws TTransportException {
		open(ip, port, srvName, DEFAULT_TIME_OUT);
	}
	

	
	/**
	 * 打开服务连接
	 * 
	 * @param ip 服务ip
	 * @param port 服务端口
	 * @param srvName 服务名
	 * @param timeout 超时时间
	 * @throws TTransportException
	 */
	public void open(String ip, int port, String srvName, int timeout) throws TTransportException {
		this.ip = ip;
		this.port = port;
		this.srvName = srvName;

        
        transport = new TSocket(ip, port,timeout); 
        transport.open(); 
        
        // 设置传输协议为 TBinaryProtocol 
        protocol = new TBinaryProtocol(transport); 
        multiProtocol = new TMultiplexedProtocol(protocol, srvName);
        
//        TProtocol protocol = new TCompactProtocol(transport);
	}
	
	
	
	
	/**
	 * 关闭服务连接
	 */
	public void close() {
		if (transport != null) {
			transport.close();
			
			transport = null;
			transport = null;
			multiProtocol = null;
		}
	}
	
	/**
	 * 获取连接协议
	 * @return 连接协议
	 */
	public TProtocol getProtocol() {
		if (multiProtocol != null)
			return multiProtocol;
		else
			return protocol;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
